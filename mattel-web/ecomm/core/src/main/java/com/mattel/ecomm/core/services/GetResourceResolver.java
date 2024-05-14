package com.mattel.ecomm.core.services;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = GetResourceResolver.class)
public class GetResourceResolver {
private static final Logger LOGGER = LoggerFactory.getLogger(GetResourceResolver.class);
  @Reference
  private ResourceResolverFactory resourceResolverFactory;
  public ResourceResolver getResourceResolver () {
    ResourceResolver resourceResolver = null;
    try {
      if (null != resourceResolverFactory) {
      Map<String, Object> usermap = new HashMap<>();
      usermap.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");

        LOGGER.debug("Resource Resolver Factory is not null");
        resourceResolver = resourceResolverFactory.getServiceResourceResolver(usermap);
      }
    } catch (LoginException e) {
      LOGGER.error("Login Exception Occured {}", e);
    }
    return resourceResolver;
  }
}
