package com.mattel.global.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.global.core.model.ButtonDetails;
import com.mattel.global.core.pojo.TagPojo;
import com.mattel.global.master.core.constants.Constants;

public class GlobalUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalUtils.class);
  private static final String IMAGE_ORIGINAL_RENDITION_PATH = "/jcr:content/renditions/original";

  /* Added Private Constructor for Sonar */
  private GlobalUtils() {
  }

  /**
   * Method will return the formatted URL based on the conditions
   *
   * @param url
   * @return
   */
  public static String checkLink(String url, Resource resource) {
    if (null != resource) {
      ResourceResolver resolver = resource.getResourceResolver();
			if (StringUtils.isNotBlank(url) && url.startsWith("/content") && !url.startsWith("/content/dam") && !url.contains(".html")) {
				if (url.contains("#")) {
					String[] urlSplitByHash = url.split("#");
					if (urlSplitByHash.length > 1) {
						String urlWithoutParam = urlSplitByHash[0];
						String param = urlSplitByHash[1];
						url = checkResolverMapping(urlWithoutParam, resolver);
						url = StringUtils.isNotBlank(param) ? url + "#" + param : url;
					}
				} else {
					url = checkResolverMapping(url, resolver);
				}
			}
		}
		return url;
	}

  /**
   *
   * @param url
   * @param resolver
   * @return
   */
  private static String checkResolverMapping(String url, ResourceResolver resolver) {
    String resolvedUrl = url;
    if (null != resolver) {
      resolvedUrl = resolver.map(url);
    }
    if (url.startsWith("/content") && null != resolver) {
      PageManager pgMgr = resolver.adaptTo(PageManager.class);
      if (null != pgMgr) {
        Page page = pgMgr.getPage(resolvedUrl);
        if (page != null) {
          if (StringUtils.isNotBlank(page.getVanityUrl())) {
            resolvedUrl = page.getVanityUrl();
          } else if (null != page.getProperties()
              && page.getProperties().containsKey("cq:redirectTarget")) {
            resolvedUrl = page.getProperties().get("cq:redirectTarget", String.class);
          } else {
            resolvedUrl = url + ".html";
          }
        }
      }
    }
    return resolvedUrl;
  }

  public static List<ButtonDetails> buttonDetails(Resource buttonList, Resource resource) {
    List<ButtonDetails> buttonDetailslist;
    LOGGER.info("Global Utils -> buttonDetails Method :: Start");
    buttonDetailslist = new ArrayList<>();
    Iterator<Resource> childList = buttonList.listChildren();
    childList.forEachRemaining(bd -> {
      ButtonDetails buttonDetail = bd.adaptTo(ButtonDetails.class);
      if (Optional.ofNullable(buttonDetail.getCtaLink()).isPresent()) {
        buttonDetail.setCtaLink(GlobalUtils.checkLink(buttonDetail.getCtaLink(), resource));
      } else if (Optional.ofNullable(buttonDetail.getLink()).isPresent()) {
        buttonDetail.setLink(GlobalUtils.checkLink(buttonDetail.getLink(), resource));
      }
      LOGGER.debug("Label :: {}", buttonDetail.getCtaLabel());
      LOGGER.debug("Link :: {}", buttonDetail.getCtaLink());
      LOGGER.debug("Label :: {}", buttonDetail.getText());
      LOGGER.debug("Link :: {}", buttonDetail.getLink());
      LOGGER.debug("Style :: {}", buttonDetail.getCtaStyle());
      LOGGER.debug("Type :: {}", buttonDetail.getCtaType());
      LOGGER.debug("LinkOptions :: {}", buttonDetail.getLinkOptions());
      buttonDetailslist.add(buttonDetail);
    });
    LOGGER.info("Global Utils -> buttonDetails Method :: End");
    return buttonDetailslist;
  }

  /**
   * Method to check for the property when an object has been Passed
   *
   * @param value
   * @return
   */
  public static String checkPropertyObject(Object value) {
    return value != null ? value.toString() : "";
  }

  /**
   *
   * @param page
   * @param property
   * @param defaultBoolean
   * @return
   */
  public static Boolean checkBooleanProperty(Page page, String property, Boolean defaultBoolean) {
    return page.getProperties().get(property, Boolean.class) != null
        ? page.getProperties().get(property, Boolean.class)
        : defaultBoolean;
  }

  /**
   *
   * @param node
   * @param property
   * @return This method checks and returns the property if available
   * @throws RepositoryException
   */
  public static String checkForProperty(Node node, String property) throws RepositoryException {
    return node.hasProperty(property) ? node.getProperty(property).getString() : "";

  }

  /**
   * @param navigationalLinks
   * @return
   */
  public static Boolean isNullOrEmpty(String[] navigationalLinks) {
    return navigationalLinks == null || navigationalLinks.length < 1;
  }

  /**
   * @param nodeValues
   * @param properties
   * @return
   */
  public static String getValueMapNodeValue(ValueMap nodeValues, String properties) {
    return nodeValues.containsKey(properties) ? nodeValues.get(properties, String.class) : StringUtils.EMPTY;
  }
  
  /**
   * @param nodeValues
   *          The Value Map.
   * @param properties
   *          The property name.
   * @param defaultValue
   *          The default value to return if property not present.
   * @return The property value in Value Map if property is present, otherwise defaultValue.
   */
  public static boolean getValueMapNodeValue(ValueMap nodeValues, String properties, boolean defaultValue) {
    return nodeValues.containsKey(properties) ? nodeValues.get(properties, Boolean.class) : defaultValue;
  }

  /**
   * @checkfornull
   * @param dateFormat
   * @param date
   * @return Parsed date or else <code>null</code>.
   */
  public static Date getParsedDate(String dateFormat, String date) {
    GlobalUtils.LOGGER.info("getParsedDate -> start");
    if (StringUtils.isNotBlank(dateFormat) && StringUtils.isNotBlank(date)) {
      try {
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.parse(date);
      } catch (final ParseException | RuntimeException e) {
        GlobalUtils.LOGGER.error("Date parsing exception : {}", e);
      }
    }
    return null;
  }

  /**
   * This method will remove P tags from String if any
   * 
   * @param text
   * @param searchList
   * @param replacementList
   * @return
   */
  public static String removeTags(String text, String[] searchList, String[] replacementList) {
    return StringUtils.replaceEach(text, searchList, replacementList);
  }

  /**
   * @param resource
   * @return
   */
  public static Resource getCtaURL(Resource resource) {
    Resource ctaResource = null;
    LOGGER.info("Global Utils :: getCtaURL Method Start");
    if (Objects.nonNull(resource) && resource.hasChildren()) {
      Stream<Resource> resourceStream = StreamSupport.stream(
          Spliterators.spliteratorUnknownSize(resource.listChildren(), Spliterator.ORDERED), false);
      if (Objects.nonNull(resourceStream)) {
        Optional<Resource> res = resourceStream.filter(Objects::nonNull).findFirst();
        if (res.isPresent()) {
          ctaResource = res.get();
        }
      }
    }
    LOGGER.info("Global Utils :: getCtaURL Method End");
    return ctaResource;
  }

  /**
   * @param parentResource
   * @param mainResource
   * @return
   */
  public static Boolean isFirstCTA(Resource parentResource, Resource mainResource) {
    LOGGER.info("Global Utils :: isFirstCTA Method Start");
    Resource ctaResource = null;
    Boolean isFirstCTA = false;
    ctaResource = GlobalUtils.getCtaURL(parentResource);
    if (Objects.nonNull(ctaResource) && Objects.nonNull(mainResource)) {
      isFirstCTA = StringUtils.equals(ctaResource.getPath(), mainResource.getPath());
    }
    LOGGER.debug("isFirstCTA :: {}", isFirstCTA);
    LOGGER.info("Global Utils :: isFirstCTA Method End");
    return isFirstCTA;
  }

    public static Map<String, String> getInterstitialDetails(Resource resource) {
        Map<String, String> interstitialDetail = new HashMap<>();
        String interstitialType = null;
        String interstitialSelectionFragmentPath = null;
        LOGGER.info("CTA ITEM MODEL :: getInterstitialDetails Method Start");
        interstitialType = resource.getValueMap().get("interstitialType", StringUtils.EMPTY);
        interstitialSelectionFragmentPath = Objects
                .nonNull(resource.getValueMap().get("interstitialSelection", String.class))
                ? resource.getValueMap().get("interstitialSelection", String.class) : StringUtils.EMPTY;
        LOGGER.debug("Interstitial fragment path :: {}", interstitialSelectionFragmentPath);
        Resource interstitialResource = resource.getResourceResolver()
                .getResource(interstitialSelectionFragmentPath + "/jcr:content/root");
        if (Objects.nonNull(interstitialResource) && interstitialResource.hasChildren()
                && StringUtils.isNoneBlank(interstitialType)) {
            interstitialDetail.put(Constants.INTERSTITIAL_PATH, interstitialResource.getPath());
            interstitialDetail.put(Constants.INTERSTITIAL_TYPE, interstitialType);
            interstitialDetail.put(Constants.INTERSTITIAL_RESOURCETYPE,
                    getResourceTypeName(interstitialResource.getResourceType()));
            LOGGER.debug("Interstitial Path :: {}", interstitialDetail.get("interstitialPath"));
            LOGGER.debug("Interstitial ResourceType Name :: {}",
                    interstitialDetail.get(Constants.INTERSTITIAL_RESOURCETYPE));
        }

        LOGGER.info("CTA ITEM MODEL :: getInterstitialDetails Method End");
        return interstitialDetail;
    }
  
  /**
   * This method gets the resource type name by getting 
   * the last string after /
   * @param resourceType resource type string
   * @return resTypeName Resource type name
   */
  private static String getResourceTypeName(String resourceType) {
	String resTypeName = StringUtils.EMPTY;
	if(StringUtils.isNotBlank(resourceType)){
		resTypeName = resourceType.substring(resourceType.lastIndexOf(Constants.SLASH)+1);
	}
	return resTypeName;
  }
  
  public static String getUrl(String linkURL, String linkAltText, Resource resource) {
    String url = checkLink(linkURL, resource);
    if (StringUtils.isNotBlank(linkAltText) && StringUtils.isNotEmpty(url)) {
      url = url + "#" + linkAltText;
      LOGGER.debug("URL :: {}", url);
    }
    return url;
  }
  
  public static String getUrl(String linkURL, Resource resource) {
    return checkLink(linkURL, resource);
  }

  /**
   * @param parentResource
   * @return isOnlyCTA
   */
  public static boolean isOnlyCTA(Resource parentResource) {
    LOGGER.info("Global Utils IsOnlyCTA Method Start");
    Boolean isOnlyCTA = false;
    if (Objects.nonNull(parentResource) && parentResource.hasChildren()) {
      int count = 0;
      Iterator<Resource> itr = parentResource.listChildren();
      while (itr.hasNext()) {
        String resourceTypeValue = "";
        resourceTypeValue = itr.next().getResourceType();
        LOGGER.debug("Resource Type is {}", resourceTypeValue);
        if (Objects.nonNull(resourceTypeValue)
            && resourceTypeValue.contains("mattel/ecomm/fisher-price/components/content/ctaItem")) {
          count++;
        }
      }
      if (count == 1) {
        isOnlyCTA = true;
      } else {
        isOnlyCTA = false;
      }
      LOGGER.debug("Value of the counter to check number of CTA item components is {}", count);
      LOGGER.debug("Is Only CTA {}", isOnlyCTA);
    }
    LOGGER.info("Global Utils IsOnlyCTA Method End");
    return isOnlyCTA;
  }
	/**
	 * @param tagManager
	 * @param tagDetail
	 * @return
	 */
	public static TagPojo getTagDetails(TagManager tagManager,String tagDetail) {
		LOGGER.info("Global Utils getTagDetails Method Start");
		LOGGER.debug("Resolve Tag :: {}" , tagDetail);
		Tag tag = tagManager.resolve(tagDetail);		
	
		TagPojo tagPojo =  new TagPojo();
		if(Objects.nonNull(tag) && Objects.nonNull(tag.getParent())) {		
			LOGGER.debug("Parent title :: {}",tag.getParent().getTitle());	
			tagPojo.setTagID(tag.getTagID());
			tagPojo.setTagValue(encodeString(tag.getTitle()));			
			tagPojo.setTagTitle(tag.getTitle());
			tagPojo.setParent(tag.getParent().getTitle());
			LOGGER.debug("Tag ID :: {}" , tag.getTagID());
			LOGGER.debug("Tag NAME :: {}" , tag.getName());
			LOGGER.debug("Tag TITLE :: {}" , tag.getTitle());
		}	
		LOGGER.info("Global Utils getTagDetails Method End");	
		return tagPojo;
	}
	
	public static String encodeString(String title) {
		String encodedString = StringUtils.EMPTY;
		try {
			encodedString = URLEncoder.encode(title,"UTF-8").replace("+", "%20");
			LOGGER.debug("Encoded String : {}",encodedString);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("_ERROR : {}",e.getStackTrace() );
			
		}
		return encodedString;
	}
	
	public static ValueMap getParentDetails(Resource parentResource) {		
	 return Optional.ofNullable(parentResource).map(pr -> pr.getValueMap()).orElse(null);		
	}
	
	/**
   * method is to provide set original background image path
   */
  public static String setBackgroundPath(ResourceResolver resourceResolver, String imagePath) {
    LOGGER.info("Global Utils setBackgroundPath -> START");
    String backgroundImagePath = StringUtils.EMPTY;
    if (null != resourceResolver) {
      Resource imageResource = resourceResolver.resolve(imagePath + GlobalUtils.IMAGE_ORIGINAL_RENDITION_PATH);
      if (!StringUtils.isEmpty(imageResource.getPath())) {
        backgroundImagePath = imageResource.getPath();
      }
    }
    LOGGER.info("Global Utils setBackgroundPath -> END, BackgroundImagePath : {}", backgroundImagePath);
    return backgroundImagePath;
  }

  public static CloseableHttpClient createCustom(int connectionTimeout) {
	    final RequestConfig config = RequestConfig.custom().setConnectTimeout(connectionTimeout)
	        .build();
	    return HttpClientBuilder.create().setDefaultRequestConfig(config).disableAutomaticRetries()
	        .build();
	  }
  
  /**
   * This method triggers the S&P indexing end point and logs the response
   * 
   * @param connectionTimeout
   * @throws IOException
   * @throws ClientProtocolException
   */
  public static void triggerSnpIndexing(int connectionTimeout, String siteKey,PropertyReaderUtils propertyReaderUtils) {
    LOGGER.info("Start of triggerSnpIndexing method");
    try (CloseableHttpClient httpClient = GlobalUtils.createCustom(connectionTimeout)) {
      String snpEndPoint = propertyReaderUtils.getSnpIndexUrl(siteKey);
      if (StringUtils.isNotBlank(snpEndPoint)) {
        LOGGER.debug("Search & Promote Index URL is: {}", snpEndPoint);
        final HttpGet getMethod = new HttpGet(snpEndPoint);
        HttpResponse httpResponse = httpClient.execute(getMethod);
        final int status = httpResponse.getStatusLine().getStatusCode();
        if (Objects.nonNull(httpResponse.getEntity()) && status == 200) {
          if (EntityUtils.toString(httpResponse.getEntity()).contains("OK")) {
            LOGGER.debug("S&P Indexing triggered successfully");
          } else {
            LOGGER.debug("Error in connecting to S&P Indexing endpoint");
          }
        } else {
          LOGGER.debug("Error in connecting to S&P Indexing endpoint");
        }
      }
    } catch (IOException e) {
      LOGGER.error("Unable to connect to S&P index endpoint", e);
    }
    LOGGER.info("End of triggerSnpIndexing method");
  }
  
  
}
