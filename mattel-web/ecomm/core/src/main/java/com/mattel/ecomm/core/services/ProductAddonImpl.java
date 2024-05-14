package com.mattel.ecomm.core.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAddonsService;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.ResponseBody;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.BaseErrorResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = ProductAddonsService.class)
public class ProductAddonImpl implements ProductAddonsService {
  private static final String EXECUTE_SERVICE = "fetch";
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductAddonImpl.class);
  private static final ObjectWriter ERR_WRITER = ResourceMapper.getWriterInstance(BaseErrorResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  @Reference
  private ProductAvailability productAvailability;

  @Override
  public JSONObject fetch(Map<String, Object> requestMap) throws ServiceException {
    ProductAddonImpl.LOGGER.info("fetch - start");
    final long startTime = System.currentTimeMillis();
    final String partNumber = (String) requestMap.get(Constant.PART_NUMBER);

    if (StringUtils.isNotEmpty(partNumber)) {
      requestMap.put("partial", "associations");
      final ProductServiceResponse productServiceResponse = productAvailability.fetch(requestMap);
      final Product product = productServiceResponse.getProduct();
      final List<Association> assocs = product.getAssociations();

      product.setAssociations(assocs.stream().filter(assoc -> "ADDONSERVICES".equals(assoc.getAssociation_type()))
          .collect(Collectors.toList()));
      productServiceResponse.setProduct(product);
      ProductAddonImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ProductAddonImpl.EXECUTE_SERVICE,
          (System.currentTimeMillis() - startTime));
      return productAvailability.getResponseValueAsJson(productServiceResponse);
    } else {
      final ResponseBody error = new ResponseBody();
      final BaseErrorResponse errorResponse = new BaseErrorResponse();
      errorResponse.setError("Missing or invalid partnumber, request cannot be processed");

      try {
        error.setContent(ProductAddonImpl.ERR_WRITER.writeValueAsString(errorResponse));
        throw new ServiceException(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND), error, true);
      } catch (final IOException e) {
        ProductAddonImpl.LOGGER.error("Encountered error:", e);
        throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
      }
    }
  }
}
