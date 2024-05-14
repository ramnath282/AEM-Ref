package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;
import com.mattel.ecomm.core.utils.LanguageMastersMapping;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = ProductService.class)
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	static final String SERVICE = "readwriteservice";
	static final String DESCRIPTION = "description";
	static final String BULLETITEMS = "bulletItems";
	static final String TITLE = "title";
	@Override
	public ValueMap productProperties(String commerceProductPath, Resource resource) {
		LOGGER.info("Product Info metheod started here");
		ValueMap prodProperties = null;
		if (null != resource) {
			ResourceResolver resolver = resource.getResourceResolver();
			Resource commerceResource = resolver.getResource(commerceProductPath);
			if (null != commerceResource) {
				prodProperties = commerceResource.getValueMap();
			}
		}
		return prodProperties;
	}

	@Override
	public Page getCurrentPagePath(PageManager pageManager, Resource resource, SlingHttpServletRequest request) {
		Page page = null;
		if (resource.getPath().contains("/content/experience-fragments/")) {
			String currentPagePath = request.getPathInfo();
			int lastIndex = currentPagePath.lastIndexOf('/');
			currentPagePath = currentPagePath.substring(0, lastIndex);
			ResourceResolver currentResourceResolver = request.getResourceResolver();
			Resource currentResource = currentResourceResolver.getResource(currentPagePath);
			if (null != currentResource) {
				page = pageManager.getContainingPage(currentPagePath);
			}
		} else {
			page = pageManager.getContainingPage(resource);
		}
		return page;
	}

	@Override
	public List<ProductFeaturePojo> getProductFeatures(ValueMap prodProperties,
			List<ProductFeaturePojo> prodFeaturePojoList, String productAccordTitle) {
		LOGGER.info("getProductFeatures starts here --->");
		String[] prodFeatures = prodProperties.get(productAccordTitle, String[].class);
		if (!EcommHelper.isNullOrEmpty(prodFeatures)) {
			getProductValueBasedOnKey(prodFeaturePojoList, prodFeatures);
			LOGGER.info("getProductFeatures ends here ---> {}", prodFeaturePojoList);
		}
		return prodFeaturePojoList;
	}

	private void getProductValueBasedOnKey(List<ProductFeaturePojo> prodFeaturePojoList, String[] prodFeatures) {
		LOGGER.info("getProductValueBasedOnKey starts here --->");
		for (int array = 0; array < prodFeatures.length; array++) {
			ProductFeaturePojo prodFeaturePojo = new ProductFeaturePojo();
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(prodFeatures[array]);
				if (jsonObj.has(DESCRIPTION)) {
					prodFeaturePojo.setProdFeatureDescription(jsonObj.get(DESCRIPTION).toString());
					LOGGER.info("description {}", jsonObj.get(DESCRIPTION));
				}
				if (jsonObj.has(TITLE)) {
					prodFeaturePojo.setProdFeatureTitle(jsonObj.get(TITLE).toString());
				}
				if (jsonObj.has(BULLETITEMS)) {
					int bulletLength = jsonObj.getJSONArray(BULLETITEMS).length();
					List<String> bulletPoints = new ArrayList<>();
					for (int i = 0; i < bulletLength; i++) {
						LOGGER.info("bulletItems {}", jsonObj.getJSONArray(BULLETITEMS).get(i));
						bulletPoints.add(jsonObj.getJSONArray(BULLETITEMS).get(i).toString());
						prodFeaturePojo.setProdFeatureBulletItems(bulletPoints);
					}
				}
				prodFeaturePojoList.add(prodFeaturePojo);
			} catch (JSONException e) {
				LOGGER.error("getProductFeatures json error ---> {}", e);
			}
		}
	}

	@Override
	public String checkLocale(Page currentPage, Resource resource) {
		String tempLocale = EcommHelper.getRelativePath(currentPage.getPath(), resource);
		if (tempLocale.contains(Constant.LANG_MASTERS)) {
			tempLocale = getPageLocaleFromMappings(currentPage.getPath(), tempLocale);
			return Constant.SLASH + tempLocale;
		} else {
			String[] splitString = tempLocale.split("/");
			return Constant.SLASH + splitString[2];
		}
	}

  /**
   * @param parentProductPage
   * @return
   */
  @Override
  public ValueMap fetchProductProperties(Page parentProductPage, Resource resource, String productSKUId) {
    String pageLocale = StringUtils.EMPTY;
    if (parentProductPage.getPath().contains("/language-masters/")) {
      pageLocale = getPageLocaleFromMappings(parentProductPage.getPath(), pageLocale);
    } else {
      pageLocale = EcommHelper.getPageLocale(parentProductPage.getPath());
    }
    LOGGER.debug("pageLocale : {}",pageLocale);
    String commerceProductPath = Constant.COMMERCE_PRODUCTS_PATH + EcommHelper.getBrandName(resource) + '/'
        + pageLocale + "/product_" + productSKUId;
    LOGGER.debug("commerceProductPath : {}",commerceProductPath);
    return productProperties(commerceProductPath, resource);
  }

    
    /**
	 * Method to fetch the Country Locale From Current Page
	 * 
	 * @param currentPagePath
	 * @param pageLocale
	 * @return
	 */
	@Override
	public String getPageLocaleFromMappings(String currentPagePath, String pageLocale) {
		String[] languagemapings = LanguageMastersMapping.getLanguageMapping();
		if (null != languagemapings && languagemapings.length > 0) {
			for (String mapping : languagemapings) {
				if (mapping.contains(":") && mapping.split(":").length > 1) {
					String language = mapping.split(":")[0];
					String locale = mapping.split(":")[1];
					if (currentPagePath.contains("/" + language + "/")) {
						pageLocale = locale;
					}
				}
			}
		}
		return pageLocale;
	}

}