package com.mattel.global.core.transformers.extension;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.cocoon.xml.sax.AbstractSAXPipe;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.services.UrlShorteningService;
import com.mattel.global.core.utils.PropertyReaderUtils;
import com.mattel.global.master.core.model.UrlShorteningConfig;

public class ExtendedTargetHtmlTransformer extends AbstractSAXPipe implements Transformer {
  private static final String ATTRIBUTE_VALUE_AFTER = "Attribute value after: {}";
private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedTargetHtmlTransformer.class);
  private static final String NULL_LINK = Constant.PUBLISH_DOMAIN_VARIATION + "null";
  private static final String HASH_LINK = Constant.PUBLISH_DOMAIN + "#";
  private static final String JS_ACTION = "javascript:void(0);";
  private PropertyReaderUtils propertyReaderUtils;
  private UrlShorteningService urlShorteningService;
  private String siteDomain = StringUtils.EMPTY;
  private String siteKey = StringUtils.EMPTY;
  private AtomicInteger shortenedUrlCount = new AtomicInteger(0);
  
  @Override
  public void init(ProcessingContext processingContext,
      ProcessingComponentConfiguration processingComponentConfiguration) throws IOException {
    try {
      ExtendedTargetHtmlTransformer.LOGGER.info("ExtendedTargetHtmlTransformer init called");
      String uri = processingContext.getRequest().getRequestURI();
      ExtendedTargetHtmlTransformer.LOGGER.debug("Request URI is {}", uri);

      if (uri.contains(Constant.NOCLOUDCONFIGS_ATOFFER_SELECTOR)) {
        uri = uri.replace(Constant.NOCLOUDCONFIGS_ATOFFER_SELECTOR, "");
        final ResourceResolver resolver = processingContext.getRequest().getResourceResolver();
        final Resource resource = Optional.ofNullable(resolver.getResource(uri))
            .orElse(processingContext.getRequest().getResource());

        ExtendedTargetHtmlTransformer.LOGGER.debug("Experience fragment resource : {}", resource);

        if (Objects.nonNull(resource)) {
          final String siteKey = resource.getValueMap().get(Constant.SITE_KEY, String.class);
          ExtendedTargetHtmlTransformer.LOGGER.info("Site Key value extracted: {}", siteKey);

          if (StringUtils.isNotBlank(siteKey)) {
            ExtendedTargetHtmlTransformer.LOGGER.debug("Site Key URI is {}", siteKey);
            siteDomain = propertyReaderUtils.getSiteDomain(siteKey);
            this.siteKey = siteKey;
            ExtendedTargetHtmlTransformer.LOGGER.debug("Site Domain URI is {}", siteDomain);
          }
        }
      }
    } catch (final Exception e) {
      ExtendedTargetHtmlTransformer.LOGGER.error("Unknown exception occured", e);
    }
  }

  public ExtendedTargetHtmlTransformer(PropertyReaderUtils propertyReaderUtils, UrlShorteningService urlShorteningService) {
    super();
    this.propertyReaderUtils = propertyReaderUtils;
    this.urlShorteningService = urlShorteningService;
    ExtendedTargetHtmlTransformer.LOGGER.info("ExtendedTargetHtmlTransformer initialized");
  }

  /**
   * @param uri
   * @param localName
   * @param qName
   * @param attributes
   * @throws SAXException
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    ExtendedTargetHtmlTransformer.LOGGER.debug(
        "start of startElement method, name : {}, qname: {}, attributes:{}",
        new Object[] { localName, qName, attributes });

    final AttributesImpl processed = new AttributesImpl(attributes);

    if (Objects.nonNull(siteDomain)) {
      if ("img".equalsIgnoreCase(localName) || "script".equalsIgnoreCase(localName)) {
        handleElementURL("src", attributes, processed, siteDomain);
      } else if ("a".equals(localName) || "link".equals(localName)) {
        handleElementURL("href", attributes, processed, siteDomain);
      } else if ("form".equalsIgnoreCase(localName)) {
        handleElementURL("action", attributes, processed, siteDomain);
      } else if ("source".equalsIgnoreCase(localName) && StringUtils.isNotBlank(siteDomain))  {
    	handleRelativeElementURL("srcset", attributes, processed, siteDomain);  
      }
    }

    super.startElement(uri, localName, qName, processed);
    ExtendedTargetHtmlTransformer.LOGGER.debug("End of startElement method");
  }
  
  /**
  *
  * @param elementAttribute
  * @param attributes
  * @param processed
  * @param siteDomain
  */
 private void handleRelativeElementURL(String elementAttribute, Attributes attributes,
     AttributesImpl processed, String siteDomain) {
   ExtendedTargetHtmlTransformer.LOGGER.info("start of handleElementURL method");

   for (int i = 0; i < attributes.getLength(); i++) {
     final String attrName = attributes.getLocalName(i);
     final int index = attributes.getIndex(attrName);
     
     if (attrName.contains(elementAttribute)) {
       String attrValue = null;
       String value = attributes.getValue(i);

       ExtendedTargetHtmlTransformer.LOGGER.debug("Attribute value before: {}", value);
       
       attrValue = parseValue(elementAttribute, value);

       if (StringUtils.isBlank(attrValue)) {
         continue;
       }

       if (attrValue.startsWith("/")) {
    	   attrValue = new StringBuilder(siteDomain).append(attrValue).toString();
    	   processed.setValue(index, attrValue);
           ExtendedTargetHtmlTransformer.LOGGER.debug(ATTRIBUTE_VALUE_AFTER, attrValue);
       }
     }
   }

   ExtendedTargetHtmlTransformer.LOGGER.info("End of handleElementURL method");
 }

  /**
   *
   * @param elementAttribute
   * @param attributes
   * @param processed
   * @param siteDomain
   */
  private void handleElementURL(String elementAttribute, Attributes attributes,
      AttributesImpl processed, String siteDomain) {
    ExtendedTargetHtmlTransformer.LOGGER.info("start of handleElementURL method");

    for (int i = 0; i < attributes.getLength(); i++) {
      final String attrName = attributes.getLocalName(i);
      final int index = attributes.getIndex(attrName);
      
      if (attrName.contains(elementAttribute)) {
        String attrValue = null;
       
        String value = attributes.getValue(i);
        ExtendedTargetHtmlTransformer.LOGGER.debug("Attribute value before: {}", value);
        
        attrValue = parseValue(elementAttribute, value);

        if (StringUtils.isBlank(attrValue)) {
          continue;
        }

       
        attrValue = shortenUrls(elementAttribute, attrValue, this.siteKey);
        attrValue = setUpProcessedLink(attrValue, processed,index);

        if (attrValue.contains(Constant.PUBLISH_DOMAIN)) {
          attrValue = attrValue.replace(Constant.PUBLISH_DOMAIN, siteDomain + "/");
          processed.setValue(index, attrValue);

          ExtendedTargetHtmlTransformer.LOGGER.debug(ATTRIBUTE_VALUE_AFTER, attrValue);
        }

        if (attrValue.contains(Constant.PUBLISH_DOMAIN_VARIATION)) {
            attrValue = attrValue.replace(Constant.PUBLISH_DOMAIN_VARIATION, siteDomain + "/");
            processed.setValue(index, attrValue);

            ExtendedTargetHtmlTransformer.LOGGER.debug(ATTRIBUTE_VALUE_AFTER, attrValue);
          }
      }
    }

    ExtendedTargetHtmlTransformer.LOGGER.info("End of handleElementURL method");
  }

  private String shortenUrls(String elementAttribute, String attrValue, String siteKey) {
	LOGGER.info("start of shortenUrls method");

	if ("href".equals(elementAttribute) && attrValue.contains("/content/") && !attrValue.contains("/content/dam")) {
		UrlShorteningConfig	urlShorteningConfig = propertyReaderUtils.getShortneningConfig(siteKey);
		
		LOGGER.debug("Shortening url, configs are : {}, {}, {}, {}", 
				new Object[]{siteKey, elementAttribute, attrValue, urlShorteningConfig});
		if (Objects.nonNull(urlShorteningConfig)) {
			String newAttrValue = urlShorteningService.transform(attrValue, urlShorteningConfig);

			shortenedUrlCount.incrementAndGet();
			LOGGER.info("end of shortenUrls method");
		  return newAttrValue;
		}
	}
	
	LOGGER.info("end of shortenUrls method");
	return attrValue;
}

private String setUpProcessedLink(String attrValue, AttributesImpl processed,int index) {
    String newAttrValue = attrValue;
    if (attrValue.contains(NULL_LINK)) {
      newAttrValue = JS_ACTION;
      processed.setValue(index, newAttrValue);

      ExtendedTargetHtmlTransformer.LOGGER.debug(ATTRIBUTE_VALUE_AFTER, newAttrValue);
    }

    if (attrValue.contains(HASH_LINK)) {
      newAttrValue = "#";
      processed.setValue(index, newAttrValue);

      ExtendedTargetHtmlTransformer.LOGGER.debug(ATTRIBUTE_VALUE_AFTER, newAttrValue);
    }
    
    return newAttrValue;
  }

private String parseValue(String elementAttribute, String value) {
	String attrValue;
	try {
	  attrValue = URLDecoder.decode(value, Constant.UTF_8);
	} catch (final UnsupportedEncodingException e) {
	  ExtendedTargetHtmlTransformer.LOGGER.error(String.format(
	      "Unable to retrieve attribute value,  elementAttribute: %s", elementAttribute), e);
	  attrValue = value;
	}
	return attrValue;
}

  @Override
  public void dispose() {
	ExtendedTargetHtmlTransformer.LOGGER.info("ExtendedTargetHtmlTransformer dispose, number of urls shortened : {}", shortenedUrlCount.get());
	siteDomain = StringUtils.EMPTY;
	siteKey = StringUtils.EMPTY;
	shortenedUrlCount.set(0);
    ExtendedTargetHtmlTransformer.LOGGER.info("ExtendedTargetHtmlTransformer dispose complete");
  }

  public String getSiteDomain() {
    return siteDomain;
  }

  public void setSiteDomain(String siteDomain) {
    this.siteDomain = siteDomain;
  }
}
