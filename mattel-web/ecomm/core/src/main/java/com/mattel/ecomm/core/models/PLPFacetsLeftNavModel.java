package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PLPFacetsLeftNavModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(PLPFacetsLeftNavModel.class);

  @Inject
  private Node excludedFacets;

  @Inject
  private MultifieldReader multifieldReader;
  
  private String hiddenFacets;

  @PostConstruct
  protected void init() {
    LOGGER.info("PLPFacetsLeftNavModel Init Start");
    long startTime = System.currentTimeMillis();

    if (Objects.nonNull(excludedFacets)) {
      LOGGER.debug("excludedFacets mutifield is authored and is not null");
      final Map<String, ValueMap> multifieldProperty = multifieldReader
          .propertyReader(excludedFacets);
      final List<String> facetList = new ArrayList<>();
      for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        final String keyset = Optional.ofNullable(entry.getValue())
            .map(v -> v.get("facetName", String.class)).orElse(StringUtils.EMPTY);
        if (StringUtils.isNotEmpty(keyset)) {
          facetList.add(keyset);
        }
      }
      hiddenFacets = String.join("~", facetList);
      LOGGER.debug("hidden facets are - {}",hiddenFacets);
    }
    long endTime = System.currentTimeMillis();
    LOGGER.info(Constant.EXECUTION_TIME_LOG, "PLPFacetsLeftNavModel Init end", endTime - startTime);
  }
  
  public Node getExcludedFacets() {
    return excludedFacets;
  }
  
  public void setExcludedFacets(Node excludedFacets) {
    this.excludedFacets = excludedFacets;
  }
  
  public MultifieldReader getMultifieldReader() {
    return multifieldReader;
  }
  
  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public String getHiddenFacets() {
    return hiddenFacets;
  }

  public void setHiddenFacets(String hiddenFacets) {
    this.hiddenFacets = hiddenFacets;
  }
}
