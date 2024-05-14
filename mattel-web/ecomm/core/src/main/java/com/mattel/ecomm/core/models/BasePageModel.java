package com.mattel.ecomm.core.models;

import java.util.Arrays;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables =
	    SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BasePageModel {
	
  @SlingObject
  SlingHttpServletRequest request;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(BasePageModel.class);

  public boolean checkClientLibsSelector(SlingHttpServletRequest request) {
    LOGGER.info("BasePageModel checkClientLibsSelector Start");
    LOGGER.debug("Request path info {}",request.getPathInfo());
    boolean disableClientLibs = false;
    String[] selectors = request.getRequestPathInfo().getSelectors();
    if (selectors.length > 0
        && Arrays.stream(selectors).anyMatch(Constant.CLIENT_LIBRARY_SELECTOR::equals)) {
      disableClientLibs = true;
    }
    LOGGER.debug("Disable client library selector value is - {}",disableClientLibs);
    LOGGER.info("BasePageModel checkClientLibsSelector End");
    return disableClientLibs;
  }
  
  public boolean isDisableClientLibs() {
    return checkClientLibsSelector(request);
  }

}