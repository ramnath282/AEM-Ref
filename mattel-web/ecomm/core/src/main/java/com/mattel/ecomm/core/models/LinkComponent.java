package com.mattel.ecomm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.mattel.ecomm.core.utils.EcomUtil;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinkComponent {

  @Self
  private Resource resource;

  @Inject
  private String text;

  @Inject
  private boolean linkType;

  @Inject
  private String link;

  @Inject
  private String ctaType;

  @Inject
  private String ctaStyle;

  public String getCtaStyle() {
    return ctaStyle;
  }

  public String getCtaType() {
    return ctaType;
  }

  public String getText() {
    return text;
  }

  public boolean isLinkType() {
    return linkType;
  }

  /**
   * Deliver the vanityUrl() over the getPath() if it exists
   * @return link final URL for the authored path
   * */
  public String getLink() {
	  return EcomUtil.checkLink(link, resource);
  }
}
