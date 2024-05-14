package com.mattel.ecomm.core.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.helper.ReportTask;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;

/**
 * Intermediary Service which triggers the Report generation task and determines the report location.
 */
@Component(service = ProductAPIValidatorService.class)
@Designate(ocd = ProductAPIValidatorService.Config.class)
public class ProductAPIValidatorService {
  private static final String ERROR = "error";
  private static final String MSG_VALIDATOR_DISABLED = "Product validation is disabled";
  private static final String REPORT_PATH = "validationReportPath";
  private static final String KEY_ACTIVE = "active";
  private static final String KEY_PRODUCT_TYPE = "product_type";
  private static final String KEY_PART_NUMBER = "partNumber";
  public static final Logger LOGGER = LoggerFactory.getLogger(ProductAPIValidatorService.class);
  private final ExecutorService es = Executors.newSingleThreadScheduledExecutor();
  @Reference
  private ProductAvailability productAvailability;
  @Reference
  private GetResourceResolver getResourceResolver;
  private String damPath;
  private String productFeedEndPointUrl;
  private boolean enableFeedValidation;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Generate validation report for all active products in data feed.
   *
   * @return The response object containing path of report file
   * @throws IOException
   */
  public Map<String, String> getFullReportFile() throws IOException {
    final Map<String, String> respObj = new HashMap<>();

    if (enableFeedValidation) {
      final String damUploadPath = getFileName();
      final List<String> inactiveProducts = new ArrayList<>();
      final Map<String, List<Map<String, String>>> resp = fetchFeedResponse();
      final Map<String, String> products = resp.get("response").stream().filter(m -> {
        if (Objects.nonNull(m.get(ProductAPIValidatorService.KEY_ACTIVE))
            && "false".equals(m.get(ProductAPIValidatorService.KEY_ACTIVE))) {
          Optional.ofNullable(m.get(ProductAPIValidatorService.KEY_PART_NUMBER)).ifPresent(inactiveProducts::add);
          return false;
        }

        return true;
      }).collect(Collectors.toMap(m -> m.get(ProductAPIValidatorService.KEY_PART_NUMBER),
          m -> m.get(ProductAPIValidatorService.KEY_PRODUCT_TYPE)));
      ProductAPIValidatorService.LOGGER.info("Report Path : {}", damUploadPath);

      respObj.put(ProductAPIValidatorService.REPORT_PATH, damUploadPath);
      es.submit(new ReportTask(damUploadPath, products, inactiveProducts, productAvailability, getResourceResolver));
    } else {
      respObj.put(ProductAPIValidatorService.ERROR, ProductAPIValidatorService.MSG_VALIDATOR_DISABLED);
    }

    return respObj;
  }

  /**
   * Invoke the data feed endpoint url and fetch details of all products.
   *
   * @return The data feed response.
   */
  private Map<String, List<Map<String, String>>> fetchFeedResponse()
      throws MalformedURLException, IOException, JsonParseException, JsonMappingException {
    final URL url = new URL(productFeedEndPointUrl);
    final URLConnection connection = url.openConnection();
    return mapper.readValue(connection.getInputStream(), new TypeReference<Map<String, List<Map<String, String>>>>() {
    });
  }

  /**
   * @return The full path of Validation Report.
   */
  private String getFileName() {
    return new StringBuilder(damPath).append("/")
        .append("validation_report_" + ThreadLocalRandom.current().nextLong() + ".xlsx").toString();
  }

  /**
   * Generate validation report for given products part numbers.
   *
   * @return The response object containing path of report file.
   * @throws IOException
   */
  public Map<String, String> getPartialReportFile(List<String> partNumbers) {
    final Map<String, String> respObj = new HashMap<>();

    if (enableFeedValidation) {
      final String damUploadPath = getFileName();
      respObj.put(ProductAPIValidatorService.REPORT_PATH, damUploadPath);
      es.submit(new ReportTask(damUploadPath, partNumbers, productAvailability, getResourceResolver));
    } else {

      respObj.put(ProductAPIValidatorService.ERROR, ProductAPIValidatorService.MSG_VALIDATOR_DISABLED);
    }

    return respObj;
  }

  /**
   * Generate validation report for all the products of given product type.
   *
   * @return The response object containing path of report file.
   * @throws IOException
   */
  public Map<String, String> getPartialReportFile(String productType) throws IOException {
    final Map<String, String> respObj = new HashMap<>();

    if (enableFeedValidation) {
      final String damUploadPath = getFileName();
      final Map<String, List<Map<String, String>>> resp = fetchFeedResponse();
      final List<String> inactiveProducts = new ArrayList<>();
      final Map<String, String> products = resp.get("response").stream()
          .filter(m -> productType.equals(m.get(ProductAPIValidatorService.KEY_PRODUCT_TYPE))).filter(m -> {
            if (Objects.nonNull(m.get(ProductAPIValidatorService.KEY_ACTIVE))
                && "false".equals(m.get(ProductAPIValidatorService.KEY_ACTIVE))) {
              Optional.ofNullable(m.get(ProductAPIValidatorService.KEY_PART_NUMBER)).ifPresent(inactiveProducts::add);
              return false;
            }

            return true;
          }).collect(Collectors.toMap(m -> m.get(ProductAPIValidatorService.KEY_PART_NUMBER),
              m -> m.get(ProductAPIValidatorService.KEY_PRODUCT_TYPE)));
      ProductAPIValidatorService.LOGGER.info("Report Path : {}", damUploadPath);

      es.submit(new ReportTask(damUploadPath, products, inactiveProducts, productAvailability, getResourceResolver));
      respObj.put(ProductAPIValidatorService.REPORT_PATH, damUploadPath);
    } else {
      respObj.put(ProductAPIValidatorService.ERROR, ProductAPIValidatorService.MSG_VALIDATOR_DISABLED);
    }

    return respObj;
  }

  @ObjectClassDefinition(name = "Product API validator service", description = "This service validates the shopify products ")
  public @interface Config {
    @AttributeDefinition(name = "DAM path of validation report file", description = "DAM path where validation report file would be stored")
    String damPath() default "/content/dam/ag/shopifyapivalidator";

    @AttributeDefinition(name = "End Point URL for Product Feed", description = "Please provide the endpoint url for full product feed")
    String productFeedEndPointUrl() default "https://storage.googleapis.com/test_ag_com/aem_live_full.json";

    @AttributeDefinition(name = "Flag to enable/disable feed validator", description = "Flag to disable/enable feed validation on the fly")
    boolean enableFeedValidation() default false;
  }

  @Activate
  public void activate(Config config) {
    damPath = config.damPath();
    productFeedEndPointUrl = config.productFeedEndPointUrl();
    enableFeedValidation = config.enableFeedValidation();
  }
}
