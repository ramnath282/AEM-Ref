package com.mattel.ecomm.core.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.AssetManager;
import com.mattel.ecomm.core.pojos.ProductValidationResult;
import com.mattel.ecomm.core.services.GetResourceResolver;
import com.mattel.ecomm.core.utils.shopify.BaseProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.AssociatedProduct;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Core;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Option;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Pricing;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Variant;
import com.mattel.ecomm.coreservices.core.pojos.shopify.VariantCore;
import com.mattel.ecomm.coreservices.core.utilities.DateUtil;

/**
 * Asynchronous Task which validates the product data and generates validation report.
 */
public class ReportTask implements Callable<String> {
  private static final String OPTION_AMOUNT = "amount";
  private static final String COMPARE_AT_PRICE = "compare_at_price";
  private static final String VALIDATION_STOP_MESSAGE = "Product Cannot be validated Further";
  private static final String MSG_LEVEL_WARN = "warning";
  private static final String MSG_LEVEL_ERR = "error";
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportTask.class);
  final static List<String> OPTION_TYPES = Arrays.asList(BaseProductUIAdapter.CLOTHING_SIZE_DEF_ATTRIBUTE,
      BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE2, BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE,
      "Default Title", ReportTask.OPTION_AMOUNT, "Title", "Location");
  final static List<String> SWATCH_ATTRIBUTES = Arrays.asList(BaseProductUIAdapter.CLOTHING_SIZE_DEF_ATTRIBUTE,
      BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE2, ReportTask.OPTION_AMOUNT, "Location");
  final static List<String> INVENTORY_TYPES = Arrays.asList("Available", "Unavailable", "noLongerAvailable",
      Constant.AVAILABILITY_BACKORDER, Constant.AVAILABILITY_BACKORDERABLE, Constant.AVAILABILITY_BACKORDERED,
      Constant.AVAILABILITY_LIMITED, Constant.AVAILABILITY_LIMITED_QTY, "Sold Out");
  final static Map<String, List<String>> REQUIRED_ASSOCIATION_TYPES = new HashMap<>();
  final static List<String> VALIDATE_ASSOCIATION_TYPES = Arrays.asList(ProductAssociationMapping.QUICK_TYPE,
      "ADDONSERVICES", ProductAssociationMapping.COMPONENT_TYPE, "KITCOMPONENT", "BITTY_TWIN_COMP");
  final static String IMAGE_LINK_PATTERN = "https://mattel.scene7.com/is/image/Mattel/";

  static {
    ReportTask.REQUIRED_ASSOCIATION_TYPES.put(Constant.PRODUCT_TYPE_BUNDLE_BEAN,
        Arrays.asList(ProductAssociationMapping.COMPONENT_TYPE));
    ReportTask.REQUIRED_ASSOCIATION_TYPES.put("GiftCard", Arrays.asList(ProductAssociationMapping.COMPONENT_TYPE));
    ReportTask.REQUIRED_ASSOCIATION_TYPES.put("DynamicKitBean", Arrays.asList("BITTY_TWIN_COMP", "KITCOMPONENT"));
    ReportTask.REQUIRED_ASSOCIATION_TYPES.put("PackageBean", Arrays.asList(ProductAssociationMapping.COMPONENT_TYPE));
  }

  private final String damUploadPath;
  private List<String> partNumbers;
  private final ProductAvailability productAvailability;
  private final GetResourceResolver getResourceResolver;
  private List<String> inactiveProducts;
  private Map<String, String> products;

  public ReportTask(String damUploadPath, List<String> partNumbers, ProductAvailability productAvailability,
      GetResourceResolver getResourceResolver) {
    this.damUploadPath = damUploadPath;
    this.partNumbers = partNumbers;
    this.productAvailability = productAvailability;
    this.getResourceResolver = getResourceResolver;
  }

  public ReportTask(String damUploadPath, Map<String, String> products, ProductAvailability productAvailability,
      GetResourceResolver getResourceResolver) {
    this.damUploadPath = damUploadPath;
    this.products = products;
    this.productAvailability = productAvailability;
    this.getResourceResolver = getResourceResolver;
  }

  public ReportTask(String damUploadPath, List<String> partNumbers, List<String> inactiveProducts,
      ProductAvailability productAvailability, GetResourceResolver getResourceResolver) {
    this.damUploadPath = damUploadPath;
    this.partNumbers = partNumbers;
    this.productAvailability = productAvailability;
    this.getResourceResolver = getResourceResolver;
    this.inactiveProducts = inactiveProducts;
  }

  public ReportTask(String damUploadPath, Map<String, String> products, List<String> inactiveProducts,
      ProductAvailability productAvailability, GetResourceResolver getResourceResolver) {
    this.damUploadPath = damUploadPath;
    this.products = products;
    this.productAvailability = productAvailability;
    this.getResourceResolver = getResourceResolver;
    this.inactiveProducts = inactiveProducts;
  }

  @Override
  public String call() throws Exception {
    final List<String> datavalidationfailures = new ArrayList<>();
    final List<String> servicevalidationfailures = new ArrayList<>();
    final List<String> warnings = new ArrayList<>();
    final AtomicInteger totalProductCount = new AtomicInteger();
    final List<String> skippedPartNumbers = new ArrayList<>();
    final Map<String, ProductValidationResult> report = new HashMap<>();
    final List<String> skus = new ArrayList<>();

    if (Objects.nonNull(products)) {
      skus.addAll(products.keySet());
    } else {
      skus.addAll(partNumbers);
    }

    for (final String partNumber : skus) {
      totalProductCount.incrementAndGet();
      if (partNumber.contains("-") || partNumber.contains("_")) {
        skippedPartNumbers.add(partNumber);
        continue;
      }

      try {
        final Map<String, Object> requestMap = new HashMap<>();
        final ProductServiceResponse resp;
        requestMap.put(Constant.PART_NUMBER, partNumber);
        requestMap.put(Constant.STORE_KEY, "ag_en");
        resp = productAvailability.fetch(requestMap);
        final ProductValidationResult validationResult = new ProductValidationResult();
        validationResult.setPartnumber(partNumber);
        Optional.ofNullable(products).ifPresent(ps -> validationResult.setProductType(products.get(partNumber)));

        validateProduct(partNumber, resp, validationResult);
        if (validationResult.isError()) {
          datavalidationfailures.add(partNumber);
        }

        if (validationResult.isWarning()) {
          warnings.add(partNumber);
        }
        ReportTask.LOGGER.debug("messages *** :{}", validationResult.getAllmessages());
        report.put(partNumber, validationResult);
      } catch (final ServiceException se) {
        ReportTask.LOGGER.error("se error", se);
        servicevalidationfailures.add(partNumber);
      } catch (final Exception e) {
        ReportTask.LOGGER.error(ReportTask.MSG_LEVEL_ERR, e);
        servicevalidationfailures.add(partNumber);
      }
    }

    buildUpReport(datavalidationfailures, servicevalidationfailures, warnings, totalProductCount, skippedPartNumbers,
        report);
    ReportTask.LOGGER.debug("totalProductCount *** :{}", totalProductCount.get());
    ReportTask.LOGGER.error("warninglist *** :{}", warnings);
    ReportTask.LOGGER.error("skippedPartNumbers *** :{}", skippedPartNumbers);
    ReportTask.LOGGER.error("servicevalidationfailures *** :{}", servicevalidationfailures);
    ReportTask.LOGGER.error("failureList *** :{}", datavalidationfailures);
    ReportTask.LOGGER.debug("Report Path : {}", damUploadPath);
    return null;
  }

  /**
   * Fetch the Asset Manager and build report.
   */
  public void buildUpReport(final List<String> datavalidationfailures, final List<String> servicevalidationfailures,
      final List<String> warnings, final AtomicInteger totalProductCount, final List<String> skippedPartNumbers,
      final Map<String, ProductValidationResult> report) throws IOException {
    ResourceResolver rr = null;
    try {
      rr = getResourceResolver.getResourceResolver();
      final AssetManager assetManager = rr.adaptTo(AssetManager.class);
      if (Objects.nonNull(assetManager)) {
        createReport(damUploadPath, assetManager, datavalidationfailures, servicevalidationfailures, warnings,
            totalProductCount, skippedPartNumbers, report);
      }
    } finally {
      if (Objects.nonNull(rr)) {
        rr.close();
      }
    }
  }

  /**
   * Validate the Product API response.
   *
   * @param partNumber
   *          The part-number of the product.
   * @param resp The Product API response.
   * @param validationResult
   *          The Validation result of the product.
   */
  private void validateProduct(final String partNumber, final ProductServiceResponse resp,
      final ProductValidationResult validationResult) {
    final Product product;
    String productType = StringUtils.EMPTY;
    try {
      validationResult.addMessage(String.format("Validation result for product###%s : ", partNumber));
      ReportTask.validateRootNode(resp, partNumber, validationResult);
      product = resp.getProduct();
      ReportTask.validateForNonEmpty(product.getPartnumber(), "product partnumber", ReportTask.MSG_LEVEL_ERR,
          validationResult);
      ReportTask.validateCore(product.getCore(), validationResult);
      if (Objects.nonNull(product.getCore())) {
        productType = product.getCore().getProduct_type();

        if ("DynamicKitBean".equals(productType) && !"BTD01".equals(partNumber)) {
          return;
        }
      }
      ReportTask.validateOptions(product.getOptions(), validationResult);
      ReportTask.validateAttributes(product.getAttributes(), validationResult);

      if (!Constant.PRODUCT_TYPE_BUNDLE_BEAN.equals(productType)) {
        validateVariants(validationResult, product);
      }

      ReportTask.validateForNull(product.getAssociations(), "product associations", false, validationResult);

      if (Objects.nonNull(ReportTask.REQUIRED_ASSOCIATION_TYPES.get(product.getCore().getProduct_type()))) {
        ReportTask.validateAssociationTypes(product.getAssociations(),
            ReportTask.REQUIRED_ASSOCIATION_TYPES.get(product.getCore().getProduct_type()), true, validationResult);
      }

      final String parentProductType = productType;
      product.getAssociations().stream()
          .filter(a -> ReportTask.VALIDATE_ASSOCIATION_TYPES.contains(a.getAssociation_type()))
          .forEach(assoc -> validateAssociation(partNumber, validationResult, assoc, parentProductType));
    } catch (final Exception e) {
      ReportTask.LOGGER.error("error Product is null", e);
    }
  }

  /**
   * Create the excel validation report using the validation result for all products
   */
  private void createReport(String filename, AssetManager assetManager, final List<String> datavalidationfailures,
      final List<String> servicevalidationfailures, final List<String> warnings, final AtomicInteger totalProductCount,
      final List<String> skippedPartNumbers, final Map<String, ProductValidationResult> report) {
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      final XSSFSheet spreadsheet = workbook.createSheet("Product Validator Summary");
      spreadsheet.setDefaultColumnWidth(150);
      spreadsheet.createRow(0).createCell(0).setCellValue("Resport Summary:");
      Optional.ofNullable(inactiveProducts)
          .ifPresent(x -> spreadsheet.createRow(1).createCell(0)
              .setCellValue(String.format(
                  "Total numbers of inactive products in wcs-informatica data feed: %d, these products were not validated",
                  x.size())));
      spreadsheet.createRow(2).createCell(0).setCellValue(
          String.format("Total numbers of active products in wcs-informatica data feed: %d", totalProductCount.get()));
      spreadsheet.createRow(3).createCell(0)
          .setCellValue(String.format(
              "%d number of products for which validation was skipped, as these are component products  %s",
              skippedPartNumbers.size(), skippedPartNumbers));
      spreadsheet.createRow(4).createCell(0)
          .setCellValue(String.format(
              "%d number of products for which shopify service did not return any data (service failure):  %s",
              servicevalidationfailures.size(), servicevalidationfailures));
      spreadsheet.createRow(5).createCell(0)
          .setCellValue(String.format("Important!: %d number of products with data validation errors: %s",
              datavalidationfailures.size(), datavalidationfailures));
      spreadsheet.createRow(6).createCell(0).setCellValue(
          String.format("%d number of products with data validation warnings: %s", warnings.size(), warnings));

      final XSSFSheet spreadsheet2 = workbook.createSheet("Product Validator Report");
      spreadsheet2.setDefaultColumnWidth(20);
      final XSSFRow row20 = spreadsheet2.createRow(0);
      row20.createCell(0).setCellValue("Part Number");
      row20.createCell(1).setCellValue("Product Type");
      row20.createCell(2).setCellValue("Log Level");
      row20.createCell(3).setCellValue("Description");
      int x = 1;

      final XSSFSheet spreadsheet3 = workbook.createSheet("SKU Wise Breakup");
      spreadsheet3.setDefaultColumnWidth(15);
      final XSSFRow row30 = spreadsheet3.createRow(0);
      row30.createCell(0).setCellValue("Part Number");
      row30.createCell(1).setCellValue("Product Type");
      row30.createCell(2).setCellValue("Error Count");
      row30.createCell(3).setCellValue("Warning Count");
      int sx = 1;

      for (final ProductValidationResult productValidationResult : report.values()) {
        if (productValidationResult.isError() || productValidationResult.isWarning()) {
          final String partnumber = productValidationResult.getPartnumber();
          final String productType = Optional.ofNullable(productValidationResult.getProductType())
              .orElse(StringUtils.EMPTY);
          final XSSFRow row3x = spreadsheet3.createRow(sx);
          row3x.createCell(0).setCellValue(partnumber);
          row3x.createCell(1).setCellValue(productType);
          row3x.createCell(2).setCellValue(productValidationResult.getErrors().size());
          row3x.createCell(3).setCellValue(productValidationResult.getWarnings().size());
          sx++;
          for (final String message : productValidationResult.getAllmessages()) {
            final XSSFRow row2x = spreadsheet2.createRow(x);
            x++;
            row2x.createCell(0).setCellValue(partnumber);
            row2x.createCell(1).setCellValue(productType);
            final XSSFCell cell2 = row2x.createCell(2);
            final XSSFCell cell3 = row2x.createCell(3);

            if (message.contains("error")) {
              cell2.setCellValue("ERROR");
              cell3.setCellValue(message.replace("error ", StringUtils.EMPTY));
            } else if (message.contains("warning")) {
              cell2.setCellValue("WARNING");
              cell3.setCellValue(message.replace("warning ", StringUtils.EMPTY));
            } else {
              cell2.setCellValue(StringUtils.EMPTY);
              cell3.setCellValue(message);
            }
          }
        }
      }

      createWorkbook(filename, assetManager, workbook);
    } catch (final IOException e) {
      ReportTask.LOGGER.error("unable to generate excel report", e);
    }
  }

  /**
   * Create the excel workbook.
   */
  private void createWorkbook(String filename, AssetManager assetManager, XSSFWorkbook workbook) {
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      workbook.write(byteArrayOutputStream);
      final byte[] barr = byteArrayOutputStream.toByteArray();
      byteArrayOutputStream.close();
      try (InputStream is = new ByteArrayInputStream(barr)) {
        assetManager.createAsset(filename.replace("txt", "xlsx"), is,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", true);
      }
    } catch (final IOException e) {
      ReportTask.LOGGER.error("unable to generate excel report", e);
    }
  }

  /**
   * Validate the product association
   */
  private void validateAssociation(final String partNumber, final ProductValidationResult validationResult,
      Association assoc, String parentProductType) {
    try {
      final AssociatedProduct associatedProduct;
      String productType = StringUtils.EMPTY;
      ReportTask.validateForNull(assoc.getProduct(), "association product node", false, validationResult);
      associatedProduct = assoc.getProduct();
      final int index = validationResult.msgsize();
      validationResult.addMessage(String.format("Validation result of association(%s) of product (%s)",
          associatedProduct.getPartnumber(), partNumber));

      ReportTask.validateForNonEmpty(associatedProduct.getPartnumber(), "association partnumber",
          ReportTask.MSG_LEVEL_ERR, validationResult);
      ReportTask.validateCore(associatedProduct.getCore(), validationResult);
      if (Objects.nonNull(associatedProduct.getCore())) {
        productType = associatedProduct.getCore().getProduct_type();

        if (Constant.PRODUCT_TYPE_BUNDLE_BEAN.equals(parentProductType)
            && "COMPONENT".equals(assoc.getAssociation_type())) {
          ReportTask.validateForNonEmpty(associatedProduct.getCore().getProduct_thumnail(), "product_thumnail",
              ReportTask.MSG_LEVEL_ERR, validationResult);
        }
      }
      ReportTask.validateOptions(associatedProduct.getOptions(), validationResult);
      ReportTask.validateAssociationAttributes(associatedProduct.getAttributes(), validationResult);

      if (!"GiftCard".equals(parentProductType)) {
        validateAssociationVariants(validationResult, associatedProduct, assoc.getAssociation_type(), productType);
      }

      if (validationResult.msgsize() == index + 1) {
        validationResult.removeMessage(index);
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error("error associationProduct is null", e);
    }
  }

  /**
   * Validate all the product association variants
   */
  private void validateAssociationVariants(final ProductValidationResult validationResult,
      final AssociatedProduct associatedProduct, String associationType, String productType) {
    try {
      ReportTask.validateForSize(associatedProduct.getVariants(), "variants payload", ReportTask.MSG_LEVEL_ERR, true,
          validationResult);
      final Option o1 = Optional.ofNullable(associatedProduct.getOptions()).filter(o -> !o.isEmpty()).map(o -> o.get(0))
          .orElse(new Option());
      int count = 0;
      for (final Variant variant : associatedProduct.getVariants()) {
        count++;

        ReportTask.validateVariant(variant, count, o1, validationResult);
        if ("ADDONSERVICES".equals(associationType) && "ProductBean".equals(productType)
            && Objects.nonNull(variant.getCore())) {
          ReportTask.validateForNull(variant.getCore().getVariant_definingAttributes(),
              "ADDONSERVICES productbean variant_definingAttributes", true, validationResult);
        }
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error(ReportTask.MSG_LEVEL_ERR, e);
    }
  }

  /**
   * Validate all the main product variants.
   */
  private void validateVariants(final ProductValidationResult validationResult, final Product product) {
    try {
      ReportTask.validateForSize(product.getVariants(), "variants payload", ReportTask.MSG_LEVEL_ERR, true,
          validationResult);
      final Option option1 = Optional.ofNullable(product.getOptions()).filter(o -> !o.isEmpty()).map(o -> o.get(0))
          .orElse(new Option());
      int count = 0;
      for (final Variant variant : product.getVariants()) {
        count++;
        ReportTask.validateVariant(variant, count, option1, validationResult);
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error(ReportTask.MSG_LEVEL_ERR, e);
    }
  }

  /**
   * Validate all the associations of main product.
   */
  private static void validateAssociationTypes(List<Association> associations, List<String> expectedAssocTypes,
      boolean canContinue, ProductValidationResult validationResult) throws Exception {
    final List<String> actualAssocTypes = new ArrayList<>(
        associations.stream().map(Association::getAssociation_type).distinct().collect(Collectors.toList()));
    final List<String> copy = new ArrayList<>(expectedAssocTypes);

    copy.removeAll(actualAssocTypes);

    if (!copy.isEmpty()) {
      validationResult.addError(String.format("error missing association types: %s for product", copy));

      if (!canContinue) {
        throw new Exception(ReportTask.VALIDATION_STOP_MESSAGE);
      }
    }
  }

  /**
   * Validate all the variant of main product.
   */
  private static void validateVariant(Variant variant, int count, Option option,
      ProductValidationResult validationResult) {
    final int index = validationResult.msgsize();
    validationResult.addMessage(String.format("Validation result of variant(%d) :", count));
    ReportTask.validateForNumber(variant.getId(), "variant id", ReportTask.MSG_LEVEL_ERR, validationResult);

    try {
      ReportTask.validateForNull(variant.getCore(), "variant core node", false, validationResult);
      final VariantCore core = variant.getCore();
      ReportTask.validateForNonEmpty(core.getSku(), "variant sku", ReportTask.MSG_LEVEL_ERR, validationResult);
      ReportTask.validateForNonEmpty(core.getVariant_parentpartnumber(), "variant_parentpartnumber",
          ReportTask.MSG_LEVEL_ERR, validationResult);
      ReportTask.validateForNonEmpty(core.getVariant_imageLink(), "variant_imageLink", ReportTask.MSG_LEVEL_WARN,
          validationResult);
      ReportTask.validateForNonEmpty(core.getTitle(), "title", ReportTask.MSG_LEVEL_ERR, validationResult);
      ReportTask.validateForNonEmpty(core.getOption1(), "option1", ReportTask.MSG_LEVEL_WARN, validationResult);
      ReportTask.validateForNonEmpty(core.getVariant_inventorystatus(), "variant_inventorystatus",
          ReportTask.MSG_LEVEL_ERR, validationResult);
      ReportTask.validateForNonEmpty(core.getVariant_fullimage(), "variant_fullimage", ReportTask.MSG_LEVEL_WARN,
          validationResult);
      ReportTask.validateForNonEmpty(core.getVariant_thumbnail(), "variant_thumbnail", ReportTask.MSG_LEVEL_WARN,
          validationResult);

      if (!ReportTask.INVENTORY_TYPES.contains(core.getVariant_inventorystatus())) {
        validationResult.addError(
            String.format("error Unknown inventory status: %s for variant", core.getVariant_inventorystatus()));
      }

      if (Constant.AVAILABILITY_BACKORDER.equals(core.getVariant_inventorystatus())
          || Constant.AVAILABILITY_BACKORDERABLE.equals(core.getVariant_inventorystatus())
          || Constant.AVAILABILITY_BACKORDERED.equals(core.getVariant_inventorystatus())) {
        ReportTask.validateForNonEmpty(core.getVariant_backorderdate(), "variant_backorderdate",
            ReportTask.MSG_LEVEL_ERR, validationResult);

        if (Objects.isNull(
            DateUtil.getParsedDate("MM-dd-yyyy", core.getVariant_backorderdate()))) {
          validationResult.addError(
              String.format("error invalid backorder date format: %s for variant", core.getVariant_backorderdate()));
        }
      }

      ReportTask.validateForNull(option, "option1 node", false, validationResult);
      if (!option.getValues().contains(core.getOption1())) {
        validationResult.addError("error Incorrect option1 field value, not matching the option name");
      }

      if (ReportTask.SWATCH_ATTRIBUTES.contains(option.getName()) && "Default Title".equals(core.getOption1())) {
        validationResult.addError("error Incorrect/unknown option1 field value");
      }

      if (BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE.equals(option.getName())
          || BaseProductUIAdapter.CLOTHING_COLOR_DEF_ATTRIBUTE2.equals(option.getName())) {
        ReportTask.validateForNonEmpty(core.getVariant_swatch(), "variant_swatch", ReportTask.MSG_LEVEL_ERR,
            validationResult);
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error("error Incorrect data", e);
    }

    if (Objects.nonNull(option) && ReportTask.OPTION_AMOUNT.equals(option.getName())) {
      ReportTask.validateGiftCardPricing(variant, validationResult);
    } else {
      ReportTask.vaidatePricing(variant, validationResult);
    }

    if (validationResult.msgsize() == index + 1) {
      validationResult.removeMessage(index);
    }
  }

  /**
   * Validate gift card product pricing.
   */
  private static void validateGiftCardPricing(Variant variant, ProductValidationResult validationResult) {
    try {
      ReportTask.validateForNull(variant.getPricing(), "gift card pricing node", false, validationResult);
      final Pricing pricing = variant.getPricing();
      final boolean isValidP = ReportTask.validateForNonEmpty(pricing.getPrice(), "gift card price",
          ReportTask.MSG_LEVEL_ERR, validationResult);
      if (isValidP) {
        ReportTask.validateIsNumeric(pricing.getPrice(), "gift card price", ReportTask.MSG_LEVEL_ERR, validationResult);
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error("error Incorrect gift card pricing data", e);
    }
  }

  /**
   * Validate pricing of non-gift card products.
   */
  private static void vaidatePricing(Variant variant, ProductValidationResult validationResult) {
    try {
      ReportTask.validateForNull(variant.getPricing(), "pricing node", false, validationResult);
      final Pricing pricing = variant.getPricing();
      Double compare_at_price = 0D;
      Double price = 0D;
      Double employeeLoyalty = 0D;
      Double employee = 0D;
      Double loyalty = 0D;

      final boolean isValidCP = ReportTask.validateForNonEmpty(pricing.getCompare_at_price(),
          ReportTask.COMPARE_AT_PRICE, ReportTask.MSG_LEVEL_ERR, validationResult);
      final boolean isValidP = ReportTask.validateForNonEmpty(pricing.getPrice(), "price", ReportTask.MSG_LEVEL_ERR,
          validationResult);
      final boolean isValidEL = ReportTask.validateForNonEmpty(pricing.getEmployeeLoyalty(), "employeeLoyalty",
          ReportTask.MSG_LEVEL_ERR, validationResult);
      final boolean isValidE = ReportTask.validateForNonEmpty(pricing.getEmployee(), "employee",
          ReportTask.MSG_LEVEL_ERR, validationResult);
      final boolean isValidL = ReportTask.validateForNonEmpty(pricing.getLoyalty(), "loyalty", ReportTask.MSG_LEVEL_ERR,
          validationResult);

      if (isValidCP) {
        ReportTask.validateIsNumeric(pricing.getCompare_at_price(), ReportTask.COMPARE_AT_PRICE,
            ReportTask.MSG_LEVEL_ERR, validationResult);
        compare_at_price = Double.parseDouble(pricing.getCompare_at_price());
        // ReportTask.validateForNumber(compare_at_price,
        // ReportTask.COMPARE_AT_PRICE, "error", validationResult);
      }

      if (isValidP) {
        ReportTask.validateIsNumeric(pricing.getPrice(), "price", ReportTask.MSG_LEVEL_ERR, validationResult);
        price = Double.parseDouble(pricing.getPrice());
      }

      if (isValidEL) {
        ReportTask.validateIsNumeric(pricing.getEmployeeLoyalty(), "employeeLoyalty", ReportTask.MSG_LEVEL_ERR,
            validationResult);
        employeeLoyalty = Double.parseDouble(pricing.getEmployeeLoyalty());
      }

      if (isValidE) {
        ReportTask.validateIsNumeric(pricing.getEmployee(), "employee", ReportTask.MSG_LEVEL_ERR, validationResult);
        employee = Double.parseDouble(pricing.getEmployee());
      }

      if (isValidL) {
        ReportTask.validateIsNumeric(pricing.getLoyalty(), "loyalty", ReportTask.MSG_LEVEL_ERR, validationResult);
        loyalty = Double.parseDouble(pricing.getLoyalty());
      }

      if (isValidCP && isValidP && compare_at_price < price) {
        validationResult.addError("error compare_at_price cannot be lesser than price");
      }

      if (isValidCP && isValidEL && compare_at_price < employeeLoyalty) {
        validationResult.addError("error compare_at_price cannot be lesser than employeeLoyalty");
      }
      if (isValidCP && isValidE && compare_at_price < employee) {
        validationResult.addError("error compare_at_price cannot be lesser than employee");
      }
      if (isValidCP && isValidL && compare_at_price < loyalty) {
        validationResult.addError("error compare_at_price cannot be lesser than loyalty");
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error("errors in pricing nodes", e);
    }
  }

  /**
   * Check if the string contains valid number value
   */
  private static boolean validateIsNumeric(String number, String string, String errorlevel,
      ProductValidationResult validationResult) {
    if (!NumberUtils.isParsable(number)) {
      final String message = String.format("%s field: %s value not a number.", errorlevel, string);

      if (ReportTask.MSG_LEVEL_ERR.equalsIgnoreCase(errorlevel)) {
        validationResult.addError(message);
      } else {
        validationResult.addWarning(message);
      }

      return false;
    }

    return true;
  }

  /**
   * Check if the number value is greater than 0.
   */
  private static boolean validateForNumber(Number id, String string, String errorlevel,
      ProductValidationResult validationResult) {
    if (id.longValue() <= 0) {
      final String message = String.format("%s value of field:  %s must be greater than zero.", errorlevel, string);

      if (ReportTask.MSG_LEVEL_ERR.equalsIgnoreCase(errorlevel)) {
        validationResult.addError(message);
      } else {
        validationResult.addWarning(message);
      }

      return false;
    }

    return true;
  }

  /**
   * To validate main product attributes.
   */
  private static void validateAttributes(Map<String, Object> attributes, ProductValidationResult validationResult) {
    try {
      ReportTask.validateForNull(attributes, "product attributes node", false, validationResult);
      ReportTask.validateForNonEmpty(BaseProductUIAdapter.transform(attributes.get(Constant.MARKETING_DESCRIPTION)),
          Constant.MARKETING_DESCRIPTION, ReportTask.MSG_LEVEL_WARN, validationResult);
      if ("Y".equals(BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_ENABLED)))) {
        ReportTask.validateForNonEmpty(BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_RATING)),
            Constant.PRODUCT_REVIEW_RATING, ReportTask.MSG_LEVEL_WARN, validationResult);
        ReportTask.validateForNonEmpty(BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_REVIEW_COUNT)),
            Constant.PRODUCT_REVIEW_COUNT, ReportTask.MSG_LEVEL_WARN, validationResult);
      }
      ReportTask.validateForNonEmpty(BaseProductUIAdapter.transform(attributes.get(Constant.CHARACTER)),
          Constant.CHARACTER, ReportTask.MSG_LEVEL_WARN, validationResult);
      ReportTask.validateForNonEmpty(BaseProductUIAdapter.transform(attributes.get(Constant.PRODUCT_GTIN)),
          Constant.PRODUCT_GTIN, ReportTask.MSG_LEVEL_WARN, validationResult);
    } catch (final Exception e) {
      ReportTask.LOGGER.error("errors in attributes", e);
    }
  }

  /**
   * To validate associated product attributes.
   */
  private static void validateAssociationAttributes(Map<String, Object> attributes,
      ProductValidationResult validationResult) {
    try {
      ReportTask.validateForNull(attributes, "product attributes node", false, validationResult);
      ReportTask.validateForNonEmpty(BaseProductUIAdapter.transform(attributes.get(Constant.MARKETING_DESCRIPTION)),
          "MarketingDescription", ReportTask.MSG_LEVEL_WARN, validationResult);
    } catch (final Exception e) {
      ReportTask.LOGGER.error("errors in attributes", e);
    }
  }

  /**
   * To validate options payload.
   */
  private static void validateOptions(List<Option> options, ProductValidationResult validationResult) {
    try {
      ReportTask.validateForNull(options, "option payload node", false, validationResult);
      ReportTask.validateForSize(options, "option payload", ReportTask.MSG_LEVEL_ERR, false, validationResult);

      if (!ReportTask.OPTION_TYPES.contains(options.get(0).getName())) {
        validationResult
            .addError(String.format("error Unknown option name: %s, not supported", options.get(0).getName()));
      }
    } catch (final Exception e) {
      ReportTask.LOGGER.error("errors in option", e);
    }
  }

  /**
   * Check if the given collection is empty or not.
   */
  private static void validateForSize(List<?> list, String fieldName, String errorlevel, boolean canContinue,
      ProductValidationResult validationResult) throws Exception {
    if (list.isEmpty()) {
      final String message = String.format("%s field: %s cannot be empty", errorlevel, fieldName);

      if (ReportTask.MSG_LEVEL_ERR.equalsIgnoreCase(errorlevel)) {
        validationResult.addError(message);
      } else {
        validationResult.addWarning(message);
      }

      if (!canContinue) {
        throw new Exception(ReportTask.VALIDATION_STOP_MESSAGE);
      }
    }
  }

  /**
   * Check the product core payload.
   */
  private static void validateCore(Core core, ProductValidationResult validationResult) {
    try {
      ReportTask.validateForNull(core, "core payload node", false, validationResult);
      ReportTask.validateForNonEmpty(core.getProduct_type(), "productype", ReportTask.MSG_LEVEL_ERR, validationResult);
      // ReportTask.validateForNonEmpty(core.getProduct_partnumber(),
      // "partnumber", "error");
      ReportTask.validateForNonEmpty(core.getHandle(), "handle", ReportTask.MSG_LEVEL_WARN, validationResult);
      ReportTask.validateForNonEmpty(core.getTitle(), "title", ReportTask.MSG_LEVEL_ERR, validationResult);
      ReportTask.validateForNonEmpty(core.getProduct_custsegexcl(), "product_custsegexcl", ReportTask.MSG_LEVEL_WARN,
          validationResult);
      ReportTask.validateForNonEmpty(core.getGlobal_title_tag(), "global_title_tag", ReportTask.MSG_LEVEL_WARN,
          validationResult);
      ReportTask.validateForNonEmpty(core.getGlobal_description_tag(), "global_description_tag",
          ReportTask.MSG_LEVEL_WARN, validationResult);
      if (ReportTask.validateForNonEmpty(core.getProduct_imagelink(), "product_imagelink", ReportTask.MSG_LEVEL_WARN,
          validationResult) && !core.getProduct_imagelink().startsWith(ReportTask.IMAGE_LINK_PATTERN)) {
        validationResult.addError(String.format("error Invalid image link value: %s", core.getProduct_imagelink()));
      }
      ReportTask.validateForNonEmpty(core.getProduct_fullimage(), "product_fullimage", ReportTask.MSG_LEVEL_WARN,
          validationResult);
      ReportTask.validateForNonEmpty(core.getProduct_thumnail(), "product_thumnail", ReportTask.MSG_LEVEL_WARN,
          validationResult);
    } catch (final Exception e) {
      ReportTask.LOGGER.error(ReportTask.MSG_LEVEL_ERR, e);
    }
  }

  /**
   * Check if the String is valid.
   */
  private static boolean validateForNonEmpty(String product_type, String fieldName, String loggerlevel,
      ProductValidationResult validationResult) {
    if (StringUtils.isEmpty(product_type)) {
      final String message = String.format("%s %s field value cannot be empty.", loggerlevel, fieldName);

      if (ReportTask.MSG_LEVEL_ERR.equalsIgnoreCase(loggerlevel)) {
        validationResult.addError(message);
      } else {
        validationResult.addWarning(message);
      }

      return false;
    }

    return true;
  }

  /**
   * Validate the root product node.
   */
  private static void validateRootNode(ProductServiceResponse sp, String partnumber,
      ProductValidationResult validationResult) throws Exception {
    ReportTask.validateForNull(sp.getProduct(), String.format("product node (%s)", partnumber), false,
        validationResult);
  }

  /**
   * Validate the object for non-null value.
   */
  private static boolean validateForNull(Object obj, String nodeName, boolean canContinue,
      ProductValidationResult validationResult) throws Exception {
    if (Objects.isNull(obj)) {
      final String message = String.format("value cannot be null: %s", nodeName);
      if (!canContinue) {
        validationResult.addError("error " + message);
        throw new Exception(ReportTask.VALIDATION_STOP_MESSAGE);
      }

      validationResult.addWarning("warning " + message);
      return false;
    }

    return true;
  }
}