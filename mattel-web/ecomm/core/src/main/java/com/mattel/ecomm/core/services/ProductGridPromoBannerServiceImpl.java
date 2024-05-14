package com.mattel.ecomm.core.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductGridPromoBannerService;
import com.mattel.ecomm.core.pojos.ESpotViewPortPojo;
import com.mattel.ecomm.core.pojos.MarketingBannerDisplayPojo;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = ProductGridPromoBannerService.class)
public class ProductGridPromoBannerServiceImpl implements ProductGridPromoBannerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductGridPromoBannerServiceImpl.class);

	@Reference
	GetResourceResolver getResourceResolver;
	@Reference
	QueryBuilder queryBuilder;

	@Override
	public JSONObject getProductGridPromoBannerJson(String pagePath) {

		LOGGER.info("Start of Category Page JSON method");
		JSONObject masterJosn = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		ResourceResolver resolver = getResourceResolver.getResourceResolver();
		Session session = resolver.adaptTo(Session.class);
		try {
			if (StringUtils.isNotBlank(pagePath)) {
				Map<String, String> querrymap = new HashMap<>();
				querrymap.put("path", pagePath+"/jcr:content");
				querrymap.put("type", "nt:unstructured");
				querrymap.put("property", "sling:resourceType");
				querrymap.put("property.value", "mattel/ecomm/ag/components/content/ecomm/productGridPromoBanner");
				querrymap.put("orderby", "@jcr:lastModified");
				querrymap.put("orderby.sort", "desc");
				querrymap.put("p.limit", "-1");
				Query pageQuery = queryBuilder.createQuery(PredicateGroup.create(querrymap), session);
				generateMarketingContentJson(masterJosn, jsonArray, pageQuery);
			}
		} catch (IOException e) {
			LOGGER.error("Exception Occured {}", e);
		} finally {
            if (resolver.isLive()) {
                resolver.close();
            }
        }
		LOGGER.info("End of Category Page JSON method");
		return masterJosn;
	}

	/**
	 * @param response
	 * @param masterJosn
	 * @param jsonArray
	 * @param pageQuery
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private void generateMarketingContentJson(JSONObject masterJosn, JSONArray jsonArray, Query pageQuery)
			throws IOException {
		if (null != pageQuery) {
			try {
				SearchResult result = pageQuery.getResult();
				if (result.getTotalMatches() > 0) {
					for (Hit hit : result.getHits()) {
						if (null != hit.getProperties()) {
							JSONObject propJson = new JSONObject();

							ObjectMapper objectMapper = new ObjectMapper();

							ESpotViewPortPojo desktopProps = new ESpotViewPortPojo();
							desktopProps.setRowNo(getIntPropertyValue(hit.getProperties(), "desktopRowNo"));
							desktopProps.setColumnNo(getIntPropertyValue(hit.getProperties(), "desktopColumnNo"));
							desktopProps.setSpanLength(getIntPropertyValue(hit.getProperties(), "desktopSpanLenth"));
							MarketingBannerDisplayPojo desktopDisplayObject = getDesktopDisplayObject(hit);
							desktopProps.setDisplayObject(desktopDisplayObject);

							String desktopJsonStr = objectMapper.writeValueAsString(desktopProps);
							JSONObject desktopJson = new JSONObject(desktopJsonStr);
							propJson.put("desktop", desktopJson);

							ESpotViewPortPojo tabletProps = new ESpotViewPortPojo();
							tabletProps.setRowNo(getIntPropertyValue(hit.getProperties(), "tabletRowNo"));
							tabletProps.setColumnNo(getIntPropertyValue(hit.getProperties(), "tabletColumnNo"));
							tabletProps.setSpanLength(getIntPropertyValue(hit.getProperties(), "tabletSpanLenth"));
							MarketingBannerDisplayPojo tabletDisplayObject = getTabletDisplayObject(hit);
							tabletProps.setDisplayObject(tabletDisplayObject);

							String tabletJsonStr = objectMapper.writeValueAsString(tabletProps);
							JSONObject tabletJson = new JSONObject(tabletJsonStr);
							propJson.put("tablet", tabletJson);

							ESpotViewPortPojo mobileProp = new ESpotViewPortPojo();
							mobileProp.setRowNo(getIntPropertyValue(hit.getProperties(), "mobileRowNo"));
							mobileProp.setColumnNo(getIntPropertyValue(hit.getProperties(), "mobileColumnNo"));
							mobileProp.setSpanLength(getIntPropertyValue(hit.getProperties(), "mobileSpanLength"));
							MarketingBannerDisplayPojo mobileDisplayObject = getMobileDisplayObject(hit);
							mobileProp.setDisplayObject(mobileDisplayObject);

							String mobileJsonStr = objectMapper.writeValueAsString(mobileProp);
							JSONObject mobileJson = new JSONObject(mobileJsonStr);
							propJson.put("mobile", mobileJson);

							propJson.put("bannerId", hit.getProperties().get("bannerId", String.class));
							jsonArray.put(propJson);
						}
					}
				}
				masterJosn.put("productGridMareketingData", jsonArray);
				EcommHelper.getLeakingResResolver(result);
			} catch (JSONException e) {
				LOGGER.error("JSONException Occured {}", e);
			} catch (RepositoryException e) {
				LOGGER.error("RepositoryException Occured {}", e);
			}
		}
	}

	private MarketingBannerDisplayPojo getDesktopDisplayObject(Hit hit) throws RepositoryException {
		MarketingBannerDisplayPojo desktopDisplayObject = new MarketingBannerDisplayPojo();
		desktopDisplayObject.setMediaType(hit.getProperties().get("desktopMediaType", String.class));
		desktopDisplayObject.setPromoImage(hit.getProperties().get("desktopPromoImage", String.class));
		String desktopPromoImageUrl = hit.getProperties().get(Constant.DESKTOP_PROMO_IMAGE_URL, String.class);
		if(StringUtils.isNotBlank(desktopPromoImageUrl)){
			boolean isPLPPage = checkIfPLPPage(desktopPromoImageUrl,hit.getResource());
			if(isPLPPage){
				desktopDisplayObject.setPromoImageUrl(EcomUtil.getPlpPageLink(desktopPromoImageUrl, hit.getResource()));
				LOGGER.debug("desktop image PLP URL is {}",desktopDisplayObject.getPromoImageUrl());
			} else {
				desktopDisplayObject.setPromoImageUrl(EcomUtil.checkLink(desktopPromoImageUrl,hit.getResource()));
				LOGGER.debug("desktop image URL is {}",desktopDisplayObject.getPromoImageUrl());
			}
		}
		desktopDisplayObject.setImageAltText(hit.getProperties().get("desktopImageAltText", String.class));
		desktopDisplayObject.setPromoText(hit.getProperties().get("desktopPromoText", String.class));
		desktopDisplayObject.setVideoUrl(hit.getProperties().get("desktopVideoUrl", String.class));
		desktopDisplayObject.setVideoType(hit.getProperties().get("desktopVideoType", String.class));
		desktopDisplayObject.setAutoPlayVideo(StringUtils.isNotBlank(hit.getProperties().get("autoPlayVideoDesktop",String.class)) ? hit.getProperties().get("autoPlayVideoDesktop",String.class) : "false");
		desktopDisplayObject.setPlayVidInModal(StringUtils.isNotBlank(hit.getProperties().get("playVidInModalDesktop",String.class))? hit.getProperties().get("playVidInModalDesktop",String.class) : "false");
		return desktopDisplayObject;
	}

	private MarketingBannerDisplayPojo getTabletDisplayObject(Hit hit) throws RepositoryException {
		MarketingBannerDisplayPojo desktopDisplayObject = new MarketingBannerDisplayPojo();
		desktopDisplayObject.setMediaType(hit.getProperties().get("tabletMediaType", String.class));
		desktopDisplayObject.setPromoImage(hit.getProperties().get("tabletPromoImage", String.class));
		String tabletPromoImageUrl = hit.getProperties().get(Constant.TABLET_PROMO_IMAGE_URL, String.class);
		if(StringUtils.isNotBlank(tabletPromoImageUrl)){
			boolean isPLPPage = checkIfPLPPage(tabletPromoImageUrl,hit.getResource());
			if(isPLPPage){
				desktopDisplayObject.setPromoImageUrl(EcomUtil.getPlpPageLink(tabletPromoImageUrl, hit.getResource()));
				LOGGER.debug("tablet image PLP URL is {}",desktopDisplayObject.getPromoImageUrl());
			} else {
				desktopDisplayObject.setPromoImageUrl(EcomUtil.checkLink(tabletPromoImageUrl,hit.getResource()));
				LOGGER.debug("tablet image URL is {}",desktopDisplayObject.getPromoImageUrl());
			}
		}
		desktopDisplayObject.setImageAltText(hit.getProperties().get("tabletImageAltText", String.class));
		desktopDisplayObject.setPromoText(hit.getProperties().get("tabletPromoText", String.class));
		desktopDisplayObject.setVideoUrl(hit.getProperties().get("tabletVideoUrl", String.class));
		desktopDisplayObject.setVideoType(hit.getProperties().get("tabletVideoType", String.class));
		desktopDisplayObject.setAutoPlayVideo(StringUtils.isNotBlank(hit.getProperties().get("autoPlayVideoTablet",String.class))? hit.getProperties().get("autoPlayVideoTablet",String.class) : "false");
		desktopDisplayObject.setPlayVidInModal(StringUtils.isNotBlank(hit.getProperties().get("playVidInModalTablet",String.class))? hit.getProperties().get("playVidInModalTablet",String.class) : "false");
		return desktopDisplayObject;
	}

	private MarketingBannerDisplayPojo getMobileDisplayObject(Hit hit) throws RepositoryException {
		MarketingBannerDisplayPojo desktopDisplayObject = new MarketingBannerDisplayPojo();
		desktopDisplayObject.setMediaType(hit.getProperties().get("mobileMediaType", String.class));
		desktopDisplayObject.setPromoImage(hit.getProperties().get("mobilePromoImage", String.class));
		String mobilePromoImageUrl = hit.getProperties().get(Constant.MOBILE_PROMO_IMAGE_URL, String.class);
		if(StringUtils.isNotBlank(mobilePromoImageUrl)){
			boolean isPLPPage = checkIfPLPPage(mobilePromoImageUrl,hit.getResource());
			if(isPLPPage){
				desktopDisplayObject.setPromoImageUrl(EcomUtil.getPlpPageLink(mobilePromoImageUrl, hit.getResource()));
				LOGGER.debug("mobile image PLP URL is {}",desktopDisplayObject.getPromoImageUrl());
			} else {
				desktopDisplayObject.setPromoImageUrl(EcomUtil.checkLink(mobilePromoImageUrl, hit.getResource()));
				LOGGER.debug("mobile image URL is {}",desktopDisplayObject.getPromoImageUrl());
			}
		}
		desktopDisplayObject.setImageAltText(hit.getProperties().get("mobileImageAltText", String.class));
		desktopDisplayObject.setPromoText(hit.getProperties().get("mobilePromoText", String.class));
		desktopDisplayObject.setVideoUrl(hit.getProperties().get("mobileVideoUrl", String.class));
		desktopDisplayObject.setVideoType(hit.getProperties().get("mobileVideoType", String.class));
		desktopDisplayObject.setAutoPlayVideo(StringUtils.isNotBlank(hit.getProperties().get("autoPlayVideoMobile",String.class))? hit.getProperties().get("autoPlayVideoMobile",String.class) : "false");
		desktopDisplayObject.setPlayVidInModal(StringUtils.isNotBlank(hit.getProperties().get("playVidInModalMobile",String.class))? hit.getProperties().get("playVidInModalMobile",String.class) : "false");
		return desktopDisplayObject;
	}

	private boolean checkIfPLPPage(String url, Resource resource) {
		LOGGER.info("Start of checkIfPLPPage method");
		if(url.startsWith("/content/") && !url.startsWith("/content/dam/")){
			PageManager pgMgr =  resource.getResourceResolver().adaptTo(PageManager.class);
			if(Objects.nonNull(pgMgr)){
				Page page = pgMgr.getPage(url);
				if(Objects.nonNull(page) && StringUtils.equalsIgnoreCase(page.getTemplate().getPath(), Constant.PRODUCT_LISTING_PAGE_PATH)){
					LOGGER.info("End of checkIfPLPPage method");
					return true;
				}
			}
		}
		LOGGER.info("End of checkIfPLPPage method");
		return false;
	}

	private int getIntPropertyValue(ValueMap properties, String property) {
		int indexval = 0;
		if (properties.containsKey(property)) {
			indexval = properties.get(property, Integer.class);
		}
		return indexval;

	}
}
