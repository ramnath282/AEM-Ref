
package com.mattel.ecomm.core.models;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Template;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.core.utils.LanguageMastersMapping;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.StringTransformer;
import com.mattel.ecomm.coreservices.core.pojos.Metatag;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EcommPageModel {
  private static final String HOMEPAGE_VAL = "homepage";
  private static final String DISCOVER_VAL = "discover";
  private static final String SHOP_VAL = "shop";
  private static final Logger LOGGER = LoggerFactory.getLogger(EcommPageModel.class);
  private static final Map<String, String> SITE_TO_SECTION_TYPE = new HashMap<>();

  static {
    EcommPageModel.SITE_TO_SECTION_TYPE.put("/shop", SHOP_VAL);
    EcommPageModel.SITE_TO_SECTION_TYPE.put("/discover", EcommPageModel.DISCOVER_VAL);
    EcommPageModel.SITE_TO_SECTION_TYPE.put("/retail", "retail");
    EcommPageModel.SITE_TO_SECTION_TYPE.put("/explore", "explore");
    EcommPageModel.SITE_TO_SECTION_TYPE.put("/doll-hospital", "doll hospital");
  }
	
	@Inject
	PropertyReaderService propertyReaderService;
	@Self
	@Via("resource")
	private Resource resource;
	@Inject
	private MultifieldReader multifieldReader;
	@SlingObject
	private SlingHttpServletRequest request;
	@Inject
	GetProductTypeService getProductTypeService;
  private List<Metatag> analyticsMetaTags;
	private final Map<String, String> metaKeywordsDescription = new LinkedHashMap<>();
	private final Map<String, String> ogTags = new LinkedHashMap<>();
	private final List<String> robotTags = new LinkedList<>();
	private String storeName = StringUtils.EMPTY;
	private String domainKey = StringUtils.EMPTY;
	private String catalogId = StringUtils.EMPTY;
	private String langId = StringUtils.EMPTY;
	private String pageType;
	private String storeId;
	private String tealiumUrl;
	private String targetUrl;
	private String snpAccountUrl;
	private String snpArticleAccountURLs;
	private String errorPage;
	private String siteKey;
	private String pageTitleValue;
	private String seoVarPageTitle;
	private String seoMetaDescription;
	private String seoUrlKeyword;
	private String canonicalTag;
	private boolean keywordsFlag;
	private boolean descriptionFlag;
	private boolean titleFlag;
	private boolean templateFlag;
	private boolean viewportFlag;
	private String pageDescription;
	private boolean seoPageTitleFlag;
	private boolean seoMetaDescrptionFlag;
	private String sessionTimeout;
	private String bazarVoicePassKey;
	private String pageTypeGT;
	private String pageNameGT;
	private String tileFallbackImage;
	private String sessionStorageTimeout;
	private String shopifyDomain;
	private Page pageRef;
	private String currentPageforPlpTitle =StringUtils.EMPTY;

	@PostConstruct
	protected void init() {
		EcommPageModel.LOGGER.info("EcommPageModel Init Start");
		tealiumUrl = EcommConfigurationUtils.getTealiumUrl();
		targetUrl = EcommConfigurationUtils.getTargetUrl();
		final String[] selectors = request.getRequestPathInfo().getSelectors();
		String skuId;
		if (null != resource) {
			collectSiteProperties();
			checkPageType(resource.getPath());
			checkSeoPageTitle();
			if (selectors.length > 2) {
				skuId = selectors[2];
				getSeoProperties(skuId, siteKey);
			} else {
				collectMetaKeywordDescription();
				collectOgTags();
				collectRobotTags();
			}

      populateAnalyticsMetatags();
		}
		EcommPageModel.LOGGER.info("EcommPageModel Init End");
	}

  private void populateAnalyticsMetatags() {
    // final String requestURL = request.getRequestURL().toString();
    final String productListingPathPrefix = Optional
        .ofNullable(propertyReaderService.getProductCategoryPath(siteKey)) //
        .orElse(StringUtils.EMPTY); //
    final Template template = pageRef.getTemplate();
    final String templateName = Optional.ofNullable(template) //
        .map(Template::getName) //
        .orElse(StringUtils.EMPTY);
    final String templatePath = Optional.ofNullable(template).map(Template::getPath)
        .orElse(StringUtils.EMPTY);
    final String requestUri = Optional.ofNullable(request.getRequestURI())
        .map(uri -> StringTransformer.mergePrefixOnCondition(
            Constant.PRODUCT_LISTING_PAGE_PATH.equals(templatePath), productListingPathPrefix, uri))
        .map(uri -> StringTransformer.substringOnConditon(
            Constant.PRODUCT_DETAIL_PAGE_PATH.equals(templatePath), 0,
            uri.indexOf(Constant.PERIOD_PLACEHOLDER), uri))
        .orElse(StringUtils.EMPTY);
    final ValueMap properties = pageRef.getProperties();
    final String metaPageName = Optional.ofNullable(requestUri) //
        .map(str -> str.split("/")) //
        .map(arr -> String.join(":", arr)) //
        .map(str -> str.replace(".html", "")) //
        .map(str -> StringTransformer.orElseOnCondition(str.length() > 1, str.substring(1), str)) //
        .map(str -> StringTransformer.orElseOnCondition(
            "doll-hospital-template".equals(templateName),
            properties.get("pageNameDH", StringUtils.EMPTY), str))
        .map(
            str -> StringTransformer.mergePrefixOnCondition("ag_en".equals(siteKey), "ag:us:", str)) //
        .map(str -> StringTransformer.orElseOnCondition(StringUtils.isNotBlank(pageNameGT),
            pageNameGT, str)) //
        .orElse(StringUtils.EMPTY);
    final String metaPageType = Optional
        .ofNullable(Optional.ofNullable(pageType).orElse(StringUtils.EMPTY))
        .map(str -> StringTransformer.orElseOnCondition(
            "doll-hospital-template".equals(templateName),
            properties.get("pageTypeDH", StringUtils.EMPTY), str))
        .map(str -> StringTransformer.orElseOnCondition(StringUtils.isNotBlank(pageTypeGT),
            pageTypeGT, str))
        // .map(str -> StringTransformer
        // .orElseOnCondition("ag-search-results-page".equals(templateName), templateName, str))
        .orElse(StringUtils.EMPTY);
    String metaSiteName = EcommPageModel.SITE_TO_SECTION_TYPE.keySet().stream() //
        .filter(expr -> requestUri.indexOf(expr) != -1) //
        .map(EcommPageModel.SITE_TO_SECTION_TYPE::get) //
        .reduce(StringUtils.EMPTY, StringTransformer::merge);
    String metaSiteType = "commerce";

    if (Constant.PRODUCT_LISTING_PAGE_PATH.equals(templatePath)
        || Constant.PRODUCT_DETAIL_PAGE_PATH.equals(templatePath)) {
      metaSiteName = EcommPageModel.SHOP_VAL;
    }

    final String metaSiteSection = metaPageType.indexOf(EcommPageModel.HOMEPAGE_VAL) != -1
        ? EcommPageModel.HOMEPAGE_VAL : metaSiteName;

    final String metaCompanyDivision = new StringBuilder("american girl ") //
        .append(metaSiteName.toLowerCase()) //
        .toString();

    if (metaPageType.indexOf("homepage") != -1 || metaPageType.indexOf("coppa") != -1) {
      metaSiteType = StringUtils.EMPTY;
    }

    analyticsMetaTags = new ArrayList<>();
    analyticsMetaTags.add(new Metatag("page_name", metaPageName));
    analyticsMetaTags.add(new Metatag("page_type", metaPageType));
    analyticsMetaTags.add(new Metatag("site_section", metaSiteSection));
    analyticsMetaTags.add(new Metatag("site_type", metaSiteType));
    analyticsMetaTags.add(new Metatag("company_division", metaCompanyDivision));
    analyticsMetaTags.add(new Metatag("page_template_id", templateName));
    analyticsMetaTags.add(new Metatag("store_id", storeId));
  }

	private void getSeoProperties(String skuId, String siteKey) {
		EcommPageModel.LOGGER.info("EcommPageModel getSeoProperties Start");
		final Map<String, String> seoPropertiesMap = getProductTypeService.getProductType(skuId, siteKey);
		seoVarPageTitle = seoPropertiesMap.get(Constant.SEO_PAGE_TITLE);
		seoMetaDescription = seoPropertiesMap.get(Constant.SEO_META_DESCRIPTION);
		seoUrlKeyword = seoPropertiesMap.get(Constant.SEO_URL_KEYWORD);
		robotTags.add(seoPropertiesMap.get(Constant.SEO_NON_INDEX_TAG));
		canonicalTag = EcomUtil.buildCanonicalTag(seoPropertiesMap, request);
		metaKeywordsDescription.put(Constant.SEO_PAGE_TITLE, seoVarPageTitle);
		metaKeywordsDescription.put(Constant.SEO_META_DESCRIPTION, seoMetaDescription);
		metaKeywordsDescription.put(Constant.SEO_URL_KEYWORD, seoUrlKeyword);
		metaKeywordsDescription.put(Constant.SEO_NON_INDEX_TAG, robotTags.get(0));
		EcommPageModel.LOGGER.debug("Seo properties are {}", metaKeywordsDescription);
		setFlags(metaKeywordsDescription);
		EcommPageModel.LOGGER.info("EcommPageModel getSeoProperties End");
	}

	private void collectSiteProperties() {
		EcommPageModel.LOGGER.info("EcommPageModel collectSiteProperties Start");
		final Resource storeNodeResource = resource.getResourceResolver().getResource(resource.getPath());
		Node storeNode = null;
		if (storeNodeResource != null) {
			storeNode = storeNodeResource.adaptTo(Node.class);
			if (Objects.isNull(storeNode)) {
			  EcommPageModel.LOGGER.error("EcommPageModel invalid page resource");
			  return;
			}

				try {
					EcommPageModel.LOGGER.debug("Analytics path node is {}", storeNode.getPath());
					final InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
					storeName = inheritanceValueMap.getInherited("storeName", String.class);
					domainKey = inheritanceValueMap.getInherited("domainKey", String.class);
					siteKey = inheritanceValueMap.getInherited("siteKey", String.class);
					getPageNameAndType(storeNode);
					storeId = propertyReaderService.getStoreId(siteKey);
					LOGGER.debug("storeName : {}",storeName);
					LOGGER.debug("domainKey : {}",domainKey);
					LOGGER.debug("siteKey : {}",siteKey);
					LOGGER.debug("pageNameGT : {}",pageNameGT);
					LOGGER.debug("pageTypeGT : {}",pageTypeGT);
					if (!StringUtils.isEmpty(siteKey) && resource.getPath().contains("/content/ag")) {
							snpAccountUrl = propertyReaderService.getSnpAccountURL(siteKey);
							snpArticleAccountURLs = propertyReaderService.getSnpArticleAccountURLs(siteKey);
							tileFallbackImage = propertyReaderService.getTileFallbackImage(siteKey);
							sessionStorageTimeout = propertyReaderService.getSessionStorageTimeout(siteKey);
							shopifyDomain = propertyReaderService.getShopifyDomain(siteKey);
							LOGGER.debug("snpAccountUrl : {}",snpAccountUrl);
							LOGGER.debug("snpArticleAccountURLs : {}",snpArticleAccountURLs);
							LOGGER.debug("tileFallbackImage : {}",tileFallbackImage);
							LOGGER.debug("sessionStorageTimeout : {}",sessionStorageTimeout);
							LOGGER.debug("shopifyDomain is : {}",shopifyDomain);
						}
		  if (resource.getPath().contains("/content/fisher-price") || resource.getPath().contains("/experience-fragments/fisher-price/")) {
						snpAccountUrl = fetchSnpAccountUrl();
						LOGGER.debug("FP snpAccountUrl : {}",snpAccountUrl);
					}		  			
					catalogId = inheritanceValueMap.getInherited("catalogId", String.class);
					langId = inheritanceValueMap.getInherited("langId", String.class);
					LOGGER.debug("catalogId : {}",catalogId);
					LOGGER.debug("langId : {}",langId);
					if (StringUtils.isNotBlank(inheritanceValueMap.getInherited("errorPages", String.class))) {
						errorPage = EcomUtil.checkLink(inheritanceValueMap.getInherited("errorPages", String.class),
								resource);
					}
					if (!StringUtils.isEmpty(siteKey)) {
						sessionTimeout = propertyReaderService.getSessionTimeout(siteKey);
						bazarVoicePassKey = propertyReaderService.getBvPassKey(siteKey);
						EcommPageModel.LOGGER.debug("sessionTimeout {}", sessionTimeout);
						EcommPageModel.LOGGER.debug("bazarVoicePassKey is {}", bazarVoicePassKey);
					}

				} catch (final RepositoryException e) {
					EcommPageModel.LOGGER.error("Exception from Ecomm Page Model", e);
				}

		}
		EcommPageModel.LOGGER.info("EcommPageModel collectSiteProperties End");
	}

  /** This method will capture the page_name and page_type
   * authorable analytics field values from page properties.
   * @param storeNode
   * @throws RepositoryException
   */
  private void getPageNameAndType(Node storeNode)
      throws RepositoryException {
    if(storeNode.hasProperty("pageNameGT")){
      pageNameGT =  storeNode.getProperty("pageNameGT").getString();
    }
    if(storeNode.hasProperty("pageTypeGT")){
      pageTypeGT =  storeNode.getProperty("pageTypeGT").getString();
    }
  }

	private void checkPageType(String path) {
		EcommPageModel.LOGGER.info("EcommPageModel checkPageType Start");
		if (path != null) {
			if (path.contains("my-account")) {
				pageType = "account";
			} else if (path.contains("categories")) {
				pageType = "product listing page";
			} else if (path.contains("shop")) {
				pageType = "product detail page";
			} else if (path.contains(DISCOVER_VAL)) {
				pageType = DISCOVER_VAL;
			} else {
				pageType = "other";
			}
		}
		EcommPageModel.LOGGER.info("EcommPageModel checkPageType End");
	}

	private void checkSeoPageTitle() {
		EcommPageModel.LOGGER.info("EcommPageModel checkSeoPageTitle Start");
		final Page currentPage = getCurrentPage();
		EcommPageModel.LOGGER.debug("EcommPageModel current page object {}", currentPage);
		if (Objects.nonNull(currentPage)) {
			final ValueMap valuemap = currentPage.getProperties();
			EcommPageModel.LOGGER.debug("EcommPageModel value map object {}", valuemap);

			final String vanityUrl = currentPage.getVanityUrl();
      pageRef = currentPage;

			if (StringUtils.isNotBlank(vanityUrl)
					&& Constant.PRODUCT_LISTING_PAGE_PATH.equals(currentPage.getTemplate().getPath())) {
				final String productListingPath = propertyReaderService.getProductCategoryPath(siteKey);
				final String sitedomain = propertyReaderService.getSiteDomain(siteKey);
        final StringBuilder canonicalTagBuilder = buildCanonicalTag(currentPage, vanityUrl,
            productListingPath, sitedomain, true);

				canonicalTag = canonicalTagBuilder.toString();
			} else if (StringUtils.isNotBlank(vanityUrl)
					&& Constant.ECOMM_TEMPLATE_PATH.equals(currentPage.getTemplate().getPath())) {
				final String productPathGT = propertyReaderService.getProductCategoryPathForGT(siteKey);
				final String sitedomain = propertyReaderService.getSiteDomain(siteKey);
        final StringBuilder canonicalTagBuilder = buildCanonicalTag(currentPage, vanityUrl,
            productPathGT, sitedomain, currentPage.getPath().contains("shop"));

				if(vanityUrl.contains("/landing")){
					canonicalTag = valuemap.get("canonicalUrl", String.class);
				}else{
                    canonicalTag = canonicalTagBuilder.toString();
				}
			}else {
				canonicalTag = valuemap.get("canonicalUrl", String.class);
			}

			if (valuemap.containsKey("seoTitle")) {
				pageTitleValue = valuemap.get("seoTitle", String.class);
				EcommPageModel.LOGGER.debug("EcommPageModel page Title {}", pageTitleValue);
			} else {
				pageTitleValue = currentPage.getTitle();
				EcommPageModel.LOGGER.debug("EcommPageModel page Title needed from current page {}", pageTitleValue);
			}
		}
		EcommPageModel.LOGGER.info("EcommPageModel checkSeoPageTitle End");
	}

  /**
   * To build canonical tag for page.
   *
   * @param currentPage
   *          The reference of current page.
   * @param vanityUrl
   *          Page vanity url.
   * @param productListingPath
   *          Vanity url prefix path.
   * @param sitedomain
   *          Site Domain.
   * @return The canonical tag.
   */
  private StringBuilder buildCanonicalTag(final Page currentPage, final String vanityUrl,
      final String productListingPath, final String sitedomain, final boolean appendCustomPath) {
    final StringBuilder canonicalTagBuilder = new StringBuilder();

    LOGGER.debug("Page path : {}", currentPage.getPath());
    //Retrieving the site domain from configuration to avoid the issues related to incorrect domain in SEO tags.
    if(StringUtils.isNotBlank(sitedomain)){
    	canonicalTagBuilder.append(sitedomain);
    } else {
    	canonicalTagBuilder.append(
    			request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI())));
    }

    if (StringUtils.isNotEmpty(productListingPath) && vanityUrl.indexOf(productListingPath) == -1 && appendCustomPath) {
      canonicalTagBuilder.append(productListingPath).append(vanityUrl);
    } else {
    	canonicalTagBuilder.append(vanityUrl);
    }
    return canonicalTagBuilder;
  }

	private void collectMetaKeywordDescription() {
		EcommPageModel.LOGGER.info("EcommPageModel collectMetaKeywordDescription Start");
		final Map<String, ValueMap> stringValueMapLinkedHashMap = getPropertyMap("metaKeywordsDescription");
		stringValueMapLinkedHashMap.forEach((String key, ValueMap valuemap) -> metaKeywordsDescription
				.put(valuemap.get("metaName", String.class), valuemap.get("metaContent", String.class)));
		setFlags(metaKeywordsDescription);
		if (descriptionFlag) {
			pageDescription = metaKeywordsDescription.get("description");
		} else {
			final Page currentPage = getCurrentPage();
			if (Objects.nonNull(currentPage)) {
				final ValueMap valuemap = currentPage.getProperties();
				pageDescription = valuemap.get("jcr:description", String.class);
			}
		}
		LOGGER.debug("pageDescription : {}",pageDescription);
		EcommPageModel.LOGGER.info("EcommPageModel collectMetaKeywordDescription End");
	}

	private Page getCurrentPage() {
		EcommPageModel.LOGGER.info("EcommPageModel getCurrentPage Start");
		Page currentPage = null;
		final PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
		if (Objects.nonNull(pageManager)) {
			currentPage = pageManager.getContainingPage(resource);
		}
		 LOGGER.debug("Current Page : {}",currentPage);
		EcommPageModel.LOGGER.info("EcommPageModel getCurrentPage End");
		return currentPage;
	}

	private void setFlags(Map<String, String> metaKeywordsDescriptions) {
		EcommPageModel.LOGGER.info("EcommPageModel setFlags Start");
		keywordsFlag = metaKeywordsDescriptions.containsKey("keywords");
		descriptionFlag = metaKeywordsDescriptions.containsKey("description");
		titleFlag = metaKeywordsDescriptions.containsKey("title");
		viewportFlag = metaKeywordsDescriptions.containsKey("viewport");
		templateFlag = metaKeywordsDescriptions.containsKey("template");
		seoPageTitleFlag = metaKeywordsDescriptions.containsKey("seo_PageTitle");
		seoMetaDescrptionFlag = metaKeywordsDescriptions.containsKey("seo_metaDescription");
		LOGGER.debug("keywordsFlag : {}",keywordsFlag);
	    LOGGER.debug("descriptionFlag : {}",descriptionFlag);
	    LOGGER.debug("titleFlag : {}",titleFlag);
	    LOGGER.debug("viewportFlag : {}",viewportFlag);
	    LOGGER.debug("templateFlag : {}",templateFlag);
	    LOGGER.debug("seoPageTitleFlag: {}",seoPageTitleFlag);
	    LOGGER.debug("seoMetaDescrptionFlag : {}",seoMetaDescrptionFlag);
		EcommPageModel.LOGGER.info("EcommPageModel setFlags End");
	}

	private void collectOgTags() {
		EcommPageModel.LOGGER.info("EcommPageModel collectOgTags Start");
		final Map<String, ValueMap> stringValueMapLinkedHashMap = getPropertyMap("ogTags");
		stringValueMapLinkedHashMap.forEach((String key, ValueMap valuemap) -> ogTags
				.put(valuemap.get("ogProperty", String.class), valuemap.get("ogContent", String.class))

		);
		LOGGER.debug("OGTags : {}",Arrays.asList(ogTags));
		EcommPageModel.LOGGER.info("EcommPageModel collectOgTags End");
	}

	private void collectRobotTags() {
		EcommPageModel.LOGGER.info("EcommPageModel collectRobotTags Start");
		final Map<String, ValueMap> stringValueMapLinkedHashMap = getPropertyMap("robotTags");
		stringValueMapLinkedHashMap
				.forEach((String key, ValueMap valuemap) -> robotTags.add(valuemap.get("robotContent", String.class)));
		LOGGER.debug("robotTags : {}",Arrays.asList(robotTags));
		EcommPageModel.LOGGER.info("EcommPageModel collectRobotTags End");
	}

	private Map<String, ValueMap> getPropertyMap(String tagPath) {
		EcommPageModel.LOGGER.info("EcommPageModel getPropertyMap Start");
		Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
		try {
			final ResourceResolver resolver = resource.getResourceResolver();
			final Resource metaKeywordsNodeResource = resolver.getResource(resource.getPath() + "/" + tagPath);
			Node metaKeywordsNode = null;
			if (null != metaKeywordsNodeResource) {
				metaKeywordsNode = metaKeywordsNodeResource.adaptTo(Node.class);
				if (null != metaKeywordsNode) {
					EcommPageModel.LOGGER.debug("metaKeywords path node is {}", metaKeywordsNode.getPath());
					stringValueMapLinkedHashMap = multifieldReader.propertyReader(metaKeywordsNode);
				}
				EcommPageModel.LOGGER.info("Start of PageModel end");
			}
		} catch (final RepositoryException e) {
			EcommPageModel.LOGGER.error("Exception from PlayPageModel page Model", e);
		}
		EcommPageModel.LOGGER.info("EcommPageModel getPropertyMap End");
		return stringValueMapLinkedHashMap;
	}

	/**
	 * 
	 * @param currentPagePath
	 * @returns LANG-COUNTRY for both language-masters and country pages
	 *
	 */
	private String fetchSnpAccountUrl() {
		LOGGER.info("EcommPageModel fetchSnpAccountUrl Start");
		String snpSiteKey;
		if (resource.getPath().contains("experience-fragments") && siteKey != null) {
			snpSiteKey = siteKey;
		} else {
			snpSiteKey = "fp" + Constant.UNDERSCORE + fetchCountryLocaleForSNP(resource.getPath());
		}
		LOGGER.debug("snpSiteKey : {}",snpSiteKey);
		LOGGER.info("EcommPageModel fetchSnpAccountUrl End");
		return propertyReaderService.getSnpAccountURL(snpSiteKey);
	}

	/**
	 * 
	 * @param currentPagePath
	 * @returns LANG-COUNTRY for both language-masters and country pages
	 *
	 */
	private String fetchCountryLocaleForSNP(String currentPagePath) {
		LOGGER.info("EcommPageModel fetchCountryLocaleForSNP Start");
		String tempPath = EcommHelper.getRelativePath(currentPagePath, resource);
		if (tempPath.contains("language-masters")) {
			LOGGER.info("CountryLocaleForPDPRedirect Path is {}", tempPath);
			return getPageLocaleFromMappings(currentPagePath);
		} else {
			String[] sepBySlash = tempPath.split("/");
			return sepBySlash[2];
		}
	}

	/**
	 * Method to fetch the Country Locale From Current Page
	 * 
	 * @param currentPagePath
	 * @param pageLocale
	 * @return
	 */
	private String getPageLocaleFromMappings(String currentPagePath) {
		LOGGER.info("EcommPageModel getPageLocaleFromMappings Start");
		String pageLocale = StringUtils.EMPTY;
		String[] languagemapings = LanguageMastersMapping.getLanguageMapping();
		if (null != languagemapings && languagemapings.length > 0) {
			for (String mapping : languagemapings) {
				if (mapping.contains(":") && mapping.split(":").length > 1) {
					String language = mapping.split(":")[0];
					String locale = mapping.split(":")[1];
					if (currentPagePath.contains("/" + language + "/")
							|| currentPagePath.contains("/" + language + Constant.UNDERSCORE + language + "/")) {
						pageLocale = locale;
					}
				}
			}
		}
		LOGGER.debug("Page Locale : {}" , pageLocale);
		LOGGER.info("EcommPageModel getPageLocaleFromMappings End");
		return pageLocale;
	}
	
	public String getCurrentPageforPlpTitle() {
		LOGGER.info("EcommPageModel getCurrentPageforPlpTitle Start");
		final PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
		if (Objects.nonNull(pageManager)) {
			Page currentPagePlpTitle = pageManager.getContainingPage(resource);
			String currentTitle = currentPagePlpTitle.getTitle();
			currentPageforPlpTitle  = EcommHelper.convertSpecialCharacters(currentTitle);
		}
		LOGGER.debug("currentPageforPlpTitle : {}" , currentPageforPlpTitle);
		LOGGER.info("EcommPageModel getCurrentPageforPlpTitle End");
		return currentPageforPlpTitle;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getDomainKey() {
		return domainKey;
	}

	public void setDomainKey(String domainKey) {
		this.domainKey = domainKey;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getTealiumUrl() {
		return tealiumUrl;
	}

	public void setTealiumUrl(String tealiumUrl) {
		this.tealiumUrl = tealiumUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getLangId() {
		return langId;
	}

	public void setLangId(String langId) {
		this.langId = langId;
	}

	public String getSnpAccountUrl() {
		return snpAccountUrl;
	}

	public void setSnpAccountUrl(String snpAccountUrl) {
		this.snpAccountUrl = snpAccountUrl;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getSiteKey() {
		return siteKey;
	}

	public Map<String, String> getMetaKeywordsDescription() {
		return metaKeywordsDescription;
	}

	public Map<String, String> getOgTags() {
		return ogTags;
	}

	public List<String> getRobotTags() {
		return robotTags;
	}

	public boolean isKeywordsFlag() {
		return keywordsFlag;
	}

	public void setKeywordsFlag(boolean keywordsFlag) {
		this.keywordsFlag = keywordsFlag;
	}

	public boolean isDescriptionFlag() {
		return descriptionFlag;
	}

	public void setDescriptionFlag(boolean descriptionFlag) {
		this.descriptionFlag = descriptionFlag;
	}

	public boolean isTitleFlag() {
		return titleFlag;
	}

	public void setTitleFlag(boolean titleFlag) {
		this.titleFlag = titleFlag;
	}

	public boolean isTemplateFlag() {
		return templateFlag;
	}

	public void setTemplateFlag(boolean templateFlag) {
		this.templateFlag = templateFlag;
	}

	public boolean isViewportFlag() {
		return viewportFlag;
	}

	public void setViewportFlag(boolean viewportFlag) {
		this.viewportFlag = viewportFlag;
	}

	public String getSeoVarPageTitle() {
		return seoVarPageTitle;
	}

	public void setSeoVarPageTitle(String seoVarPageTitle) {
		this.seoVarPageTitle = seoVarPageTitle;
	}

	public String getSeoMetaDescription() {
		return seoMetaDescription;
	}

	public void setSeoMetaDescription(String seoMetaDescription) {
		this.seoMetaDescription = seoMetaDescription;
	}

	public String getSeoUrlKeyword() {
		return seoUrlKeyword;
	}

	public void setSeoUrlKeyword(String seoUrlKeyword) {
		this.seoUrlKeyword = seoUrlKeyword;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public boolean isSeoPageTitleFlag() {
		return seoPageTitleFlag;
	}

	public void setSeoPageTitleFlag(boolean seoPageTitleFlag) {
		this.seoPageTitleFlag = seoPageTitleFlag;
	}

	public boolean isSeoMetaDescrptionFlag() {
		return seoMetaDescrptionFlag;
	}

	public void setSeoMetaDescrptionFlag(boolean seoMetaDescrptionFlag) {
		this.seoMetaDescrptionFlag = seoMetaDescrptionFlag;
	}

	public String getPageTitleValue() {
		return pageTitleValue;
	}

	public void setPageTitleValue(String pageTitleValue) {
		this.pageTitleValue = pageTitleValue;
	}

	public String getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(String sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public String getBazarVoicePassKey() {
		return bazarVoicePassKey;
	}

	public void setBazarVoicePassKey(String bazarVoicePassKey) {
		this.bazarVoicePassKey = bazarVoicePassKey;
	}

	public String getCanonicalTag() {
		return canonicalTag;
	}

	public void setCanonicalTag(String canonicalTag) {
		this.canonicalTag = canonicalTag;
	}

	public String getPageTypeGT() {
		return pageTypeGT;
	}

	public void setPageTypeGT(String pageTypeGT) {
		this.pageTypeGT = pageTypeGT;
	}

	public String getPageNameGT() {
		return pageNameGT;
	}

	public void setPageNameGT(String pageNameGT) {
		this.pageNameGT = pageNameGT;
	}

	public String getSnpArticleAccountURLs() {
		return snpArticleAccountURLs;
	}

	public void setSnpArticleAccountURLs(String snpArticleAccountURLs) {
		this.snpArticleAccountURLs = snpArticleAccountURLs;
	}

	public String getTileFallbackImage() {
		return tileFallbackImage;
	}

	public void setTileFallbackImage(String tileFallbackImage) {
		this.tileFallbackImage = tileFallbackImage;
	}

	public String getSessionStorageTimeout() {
		return sessionStorageTimeout;
	}

	public void setSessionStorageTimeout(String sessionStorageTimeout) {
		this.sessionStorageTimeout = sessionStorageTimeout;
	}

  public void setAnalyticsMetaTags(List<Metatag> analyticsMetaTags) {
    this.analyticsMetaTags = analyticsMetaTags;
  }

  public List<Metatag> getAnalyticsMetaTags() {
    return analyticsMetaTags;
  }
  
  public String getShopifyDomain() {
	return shopifyDomain;
  }

  public void setShopifyDomain(String shopifyDomain) {
	this.shopifyDomain = shopifyDomain;
  }
}
