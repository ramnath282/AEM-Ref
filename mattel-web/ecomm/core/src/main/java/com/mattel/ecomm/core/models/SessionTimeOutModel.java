package com.mattel.ecomm.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SessionTimeOutModel extends BasePageModel {

  @SlingObject
  SlingHttpServletRequest sessionTimeOutRequest;

  @Override
  public boolean isDisableClientLibs() {
    return checkClientLibsSelector(sessionTimeOutRequest);
  }

}