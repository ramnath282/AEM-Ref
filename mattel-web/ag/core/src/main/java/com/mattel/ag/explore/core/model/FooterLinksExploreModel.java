package com.mattel.ag.explore.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.explore.core.pojos.FooterLinksExplorePojo;
import com.mattel.ag.retail.core.services.MultifieldReader;

/**
 * @author CTS. A Model class for FooterLinksExplore
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterLinksExploreModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(FooterLinksExploreModel.class);
  List<FooterLinksExplorePojo> helpFooterLinksList = new ArrayList<>();
  List<FooterLinksExplorePojo> hearFooterLinksList = new ArrayList<>();
  List<FooterLinksExplorePojo> visitFooterLinksList = new ArrayList<>();
  List<FooterLinksExplorePojo> cantFindFooterLinksList = new ArrayList<>();
  @Inject
  private Node helpFooterLinks;
  @Inject
  private Node hearFooterLinks;
  @Inject
  private Node visitFooterLinks;
  @Inject
  private Node cantFindFooterLinks;
  @Inject
  private MultifieldReader multifieldReader;
  public MultifieldReader getMultifieldReader() {
	return multifieldReader;
}

public void setMultifieldReader(MultifieldReader multifieldReader) {
	this.multifieldReader = multifieldReader;
}


  public Node getHelpFooterLinks() {
	return helpFooterLinks;
}

public void setHelpFooterLinks(Node helpFooterLinks) {
	this.helpFooterLinks = helpFooterLinks;
}

public Node getHearFooterLinks() {
	return hearFooterLinks;
}

public void setHearFooterLinks(Node hearFooterLinks) {
	this.hearFooterLinks = hearFooterLinks;
}

public Node getVisitFooterLinks() {
	return visitFooterLinks;
}

public void setVisitFooterLinks(Node visitFooterLinks) {
	this.visitFooterLinks = visitFooterLinks;
}

public Node getCantFindFooterLinks() {
	return cantFindFooterLinks;
}

public void setCantFindFooterLinks(Node cantFindFooterLinks) {
	this.cantFindFooterLinks = cantFindFooterLinks;
}

@PostConstruct
  protected void init() {
	  LOGGER.info("Footer Links Explore Model Start");
	 
	  if (helpFooterLinks != null) {
		  Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(helpFooterLinks);
	      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	    	  FooterLinksExplorePojo footerLinksPojo = new FooterLinksExplorePojo();
	        LOGGER.debug("Help Link texts{}", entry.getValue().get("helpLinkText", String.class));
	        footerLinksPojo.setHelpLinkText(entry.getValue().get("helpLinkText", String.class));
	        LOGGER.debug("Help Link url{}", entry.getValue().get("helpLinkUrl", String.class));
	        footerLinksPojo.setHelpLinkUrl(entry.getValue().get("helpLinkUrl", String.class));
	        footerLinksPojo.setHelpRenderOption(entry.getValue().get("helpRenderOption", String.class));
	        LOGGER.debug("helpFooterLinks{} ", helpFooterLinks);
	        helpFooterLinksList.add(footerLinksPojo);
	      }
	    }
	    if (hearFooterLinks != null) {
	    	Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(hearFooterLinks);
	        for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	        	FooterLinksExplorePojo footerLinksPojo = new FooterLinksExplorePojo();
	          LOGGER.debug("Hear Link texts{}", entry.getValue().get("hearLinkText", String.class));
	          footerLinksPojo.setHearLinkText(entry.getValue().get("hearLinkText", String.class));
	          LOGGER.debug("Hear Link url{}", entry.getValue().get("hearLinkUrl", String.class));
	          footerLinksPojo.setHearLinkUrl(entry.getValue().get("hearLinkUrl", String.class));
	          footerLinksPojo.setHearRenderOption(entry.getValue().get("hearRenderOption", String.class));
	          LOGGER.debug("hearFooterLinks{} ", hearFooterLinks);
	          hearFooterLinksList.add(footerLinksPojo);
	        }
	      }
	    if (visitFooterLinks != null) {
	    	Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(visitFooterLinks);
	        for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	        	FooterLinksExplorePojo footerLinksPojo = new FooterLinksExplorePojo();
	          LOGGER.debug("Visit Link texts{}", entry.getValue().get("visitLinkText", String.class));
	          footerLinksPojo.setVisitLinkText(entry.getValue().get("visitLinkText", String.class));
	          LOGGER.debug("Visit Link url{}", entry.getValue().get("visitLinkUrl", String.class));
	          footerLinksPojo.setVisitLinkUrl(entry.getValue().get("visitLinkUrl", String.class));
	          footerLinksPojo.setVisitRenderOption(entry.getValue().get("visitRenderOption", String.class));
	          LOGGER.debug("visitFooterLinks{} ", visitFooterLinks);
	          visitFooterLinksList.add(footerLinksPojo);
	        }
	      }
	    if (cantFindFooterLinks != null) {
	    	Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(cantFindFooterLinks);
	        for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	        	FooterLinksExplorePojo footerLinksPojo = new FooterLinksExplorePojo();
	          LOGGER.debug("Find Link texts{}", entry.getValue().get("cantFindLinkText", String.class));
	          footerLinksPojo.setCantFindLinkText(entry.getValue().get("cantFindLinkText", String.class));
	          LOGGER.debug("Find Link url{}", entry.getValue().get("cantFindLinkUrl", String.class));
	          footerLinksPojo.setCantFindLinkUrl(entry.getValue().get("cantFindLinkUrl", String.class));
	          footerLinksPojo.setFindRenderOption(entry.getValue().get("findRenderOption", String.class));
	          LOGGER.debug("cantFindFooterLinks{} ", cantFindFooterLinks);
	          cantFindFooterLinksList.add(footerLinksPojo);
	        }
	      }
    LOGGER.info("Init method in FooterLinkModel Explore end");
  }

  /**
   * @return This method return list of helpFooterLinksList
   */
  public List<FooterLinksExplorePojo> getHelpFooterLinksList() {
    return helpFooterLinksList;
  }
  /**
   * @return This method return list of hearFooterLinksList
   */
  public List<FooterLinksExplorePojo> getHearFooterLinksList() {
    return hearFooterLinksList;
  }
  /**
   * @return This method return list of visitFooterLinksList
   */
  public List<FooterLinksExplorePojo> getVisitFooterLinksList() {
    return visitFooterLinksList;
  }
  /**
   * @return This method return list of cantFindFooterLinksList
   */
  public List<FooterLinksExplorePojo> getCantFindFooterLinksList() {
    return cantFindFooterLinksList;
  }
  public void setHelpFooterLinksList(List<FooterLinksExplorePojo> helpFooterLinksList) {
		this.helpFooterLinksList = helpFooterLinksList;
  }
  public void setHearFooterLinksList(List<FooterLinksExplorePojo> hearFooterLinksList) {
		this.hearFooterLinksList = hearFooterLinksList;
}
  public void setVisitFooterLinksList(List<FooterLinksExplorePojo> visitFooterLinksList) {
		this.visitFooterLinksList = visitFooterLinksList;
}
  public void setCantFindFooterLinksList(List<FooterLinksExplorePojo> cantFindFooterLinksList) {
		this.cantFindFooterLinksList = cantFindFooterLinksList;
}

}
