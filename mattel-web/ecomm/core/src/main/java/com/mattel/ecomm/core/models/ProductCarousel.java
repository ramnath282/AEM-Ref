package com.mattel.ecomm.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.coreservices.core.constants.Constant;

/**
 * @author CTS
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductCarousel {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductCarousel.class);

  private static final String SNP_ORDER_FILTER = "m_sort_sale";
  private static final String SNP_PAGE_FILTER = "page";
  private static final String SNP_COUNT_FILTER = "count";
  private static final String SNP_PAGE_FILTER_VALUE = "1";
  private static final String SNP_COUNT_FILTER_VALUE = "9";
  private static final String SNP_COLLECTIONS_FILTER = "collections";

  @Inject
  private String categoryQP;

  @Inject
  private String productOrder;

  @Inject
  private String maxProductCount;

  private String snpQueryParam;

  @PostConstruct
  protected void init() {
    LOGGER.info("Init Method - Start");
    snpQueryParam = prepareSnPQueryParam();
    LOGGER.debug("S&P QueryParam = {}", snpQueryParam);
    LOGGER.info("Init Method - End");
  }

  /**
   * prepare s&p query parameter using different parameters EX :
   * AGCategory=Doll|Clothing|Book&m_sort_sale=ProductReviewRating&count=6&page=1
   * 
   * @return QueryParam
   */
  private String prepareSnPQueryParam() {
    LOGGER.info("prepareSnPQueryParam Method - Start");
    StringBuilder sb = new StringBuilder();
    if (StringUtils.isNotBlank(categoryQP)) {
      LOGGER.debug("Authore category param {}, order {}", categoryQP, productOrder);
      sb.append(categoryQP.trim());
      if (!checkForCollection(categoryQP)) {
        sb.append(Constant.AND_OPR);
        sb.append(ProductCarousel.SNP_ORDER_FILTER);
        sb.append(Constant.EQUALS_TO);
        sb.append(productOrder);
      }
      sb.append(Constant.AND_OPR);
      sb.append(ProductCarousel.SNP_COUNT_FILTER);
      sb.append(Constant.EQUALS_TO);

      if (StringUtils.isNotBlank(maxProductCount)) {
        sb.append(maxProductCount);
      } else {
        sb.append(ProductCarousel.SNP_COUNT_FILTER_VALUE);
      }

      sb.append(Constant.AND_OPR);
      sb.append(ProductCarousel.SNP_PAGE_FILTER);
      sb.append(Constant.EQUALS_TO);
      sb.append(ProductCarousel.SNP_PAGE_FILTER_VALUE);
    }
    LOGGER.info("prepareSnPQueryParam Method - End");
    return sb.toString();
  }

  /**
   * Method is used to check s&p query to be formed for collections or
   * categories
   * 
   * @param categoryQP
   * @return
   */
  private boolean checkForCollection(String categoryQP) {
    LOGGER.info("checkForCollection Method - Start");
    boolean isCollection = false;
    isCollection = categoryQP.startsWith(SNP_COLLECTIONS_FILTER) ? true : false;
    LOGGER.info("checkForCollection Method - End, value : {}", isCollection);
    return isCollection;
  }

  public String getSnpQueryParam() {
    return snpQueryParam;
  }

}
