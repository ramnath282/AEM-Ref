package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.pojos.CTAPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildCtaModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(BuildCtaModel.class);

  private BuildCtaModel() {
    // no-op
  }

  /**
   * Method for setting all the values in dialog field into Pojo.
   *
   * @return ArrayList.
   */
  public static List<CTAPojo> listCTA(MultifieldReader multifieldReader, Node node,
      Resource resource, String labelField, String linkPropertyName) {
    BuildCtaModel.LOGGER.info("listCTA - Start");
    final Map<String, ValueMap> ctaGroupMap = multifieldReader.propertyReader(node);
    final List<CTAPojo> ctaList = new ArrayList<>();
    for (final Map.Entry<String, ValueMap> entry : ctaGroupMap.entrySet()) {
      final CTAPojo ctaPojo = new CTAPojo();
      Optional.ofNullable(entry.getValue().get(labelField, String.class))
          .ifPresent(ctaPojo::setCtaLabel);
      Optional.ofNullable(entry.getValue().get("linkType", String.class))
          .ifPresent(ctaPojo::setCtaType);
      final String navLink = entry.getValue().get(linkPropertyName, String.class);
      if (StringUtils.isNotBlank(navLink)) {
        ctaPojo.setCtaLink(EcomUtil.checkLink(navLink, resource));
      }
      BuildCtaModel.LOGGER.debug("CTA Pojo  {}", ctaPojo);
      ctaList.add(ctaPojo);
    }
    BuildCtaModel.LOGGER.info("listCTA - End  : CTA List {}", ctaList);
    return ctaList;
  }
}
