package com.mattel.global.core.model;

import com.mattel.global.core.pojo.CommonPojo;
import com.mattel.global.core.pojo.FooterLinkPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PathUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CTS Footer Link Model to get links in footer.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterLinkModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(FooterLinkModel.class);
  @Inject
  private Node linkDetails;
  @Inject
  private MultifieldReader multifieldReader;
  CommonPojo commonPojo = new CommonPojo();
  private List<FooterLinkPojo> footerLinkItems = new ArrayList<>();
  
  @Inject
	private String imgUrl;
	
	
	public void setLinkDetails(Node linkDetails) {
	this.linkDetails = linkDetails;
}

public void setMultifieldReader(MultifieldReader multifieldReader) {
	this.multifieldReader = multifieldReader;
}

public void setCommonPojo(CommonPojo commonPojo) {
	this.commonPojo = commonPojo;
}

public void setFooterLinkItems(List<FooterLinkPojo> footerLinkItems) {
	this.footerLinkItems = footerLinkItems;
}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

  /**
   * The init method.
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("Footer Link init start");
    if(null != imgUrl){
    	commonPojo.setExternal(PathUtils.isExternal(imgUrl));}
    if (linkDetails != null) {
      Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(linkDetails);
      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        FooterLinkPojo linkItems = new FooterLinkPojo();
        linkItems.setLabel(entry.getValue().get("label", String.class));
        linkItems.setUrl(entry.getValue().get("url", String.class));
        linkItems.setAlt(entry.getValue().get("alt", String.class));
        linkItems.setTargetUrl(entry.getValue().get("targetUrl", String.class));
        if(null != entry.getValue().get("url", String.class)){
        linkItems.setExternal(PathUtils.isExternal(entry.getValue().get("url", String.class)));
        }
        footerLinkItems.add(linkItems);
      }
    }
    LOGGER.info("Footer Link init end");
  }

  /**
   * @return This method return list of Footer Links.
   */
  public List<FooterLinkPojo> getFooterLinkItemsList() {
    return footerLinkItems;
  }
}
