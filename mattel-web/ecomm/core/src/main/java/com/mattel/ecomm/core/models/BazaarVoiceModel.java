package com.mattel.ecomm.core.models;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.utils.GiftCardSkuProcessor;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BazaarVoiceModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(BazaarVoiceModel.class);
	@Inject
	Resource resource;

	@SlingObject
	private SlingHttpServletRequest request;

	@Inject
	private Page currentPage;

	/** Used in AG track */
	@Inject
	@Via("resource")
	@Named("extractPartNumberFromSelector")
	private String extractPartNumberFromSelector;

	private Boolean enableCustomerReview = Boolean.FALSE;
	private Boolean disableCustReviewMobile = Boolean.FALSE;
	private Boolean enableQuestAnswer = Boolean.FALSE;
	private Boolean disableQuenAnsMobile = Boolean.FALSE;
	private String custReviewTitle = StringUtils.EMPTY;
	private String questAnswTitle = StringUtils.EMPTY;
	private String productSKUId = StringUtils.EMPTY;
	private String custReviewScript = StringUtils.EMPTY;
	private String questAnsScript = StringUtils.EMPTY;
	private String pageCustReviewTitle = StringUtils.EMPTY;
	private Boolean pageEnablecustReview = Boolean.FALSE;
	private Boolean pageDisablecustReviewMobile = Boolean.FALSE;
	private String pageQuestAnswTitle = StringUtils.EMPTY;
	private Boolean pageEnablequestAnswer = Boolean.FALSE;
	private Boolean pagedisableQuenAnsMobile = Boolean.FALSE;
	private String pageCustReviewScript = StringUtils.EMPTY;
	private String pageQuestAnswScript = StringUtils.EMPTY;
	private Boolean isCustRatingsEnabled = Boolean.FALSE;
	private Boolean isQuesAnsEnabled = Boolean.FALSE;
	private Boolean showPageCRRSpinner = Boolean.FALSE;
	private Boolean showPageQnSSpinner = Boolean.FALSE;
	private Boolean showDialogCRRSpinner = Boolean.FALSE;
	private Boolean showDialogQnsSpinner = Boolean.FALSE;

	@PostConstruct
	protected void init() {
		BazaarVoiceModel.LOGGER.debug("BazaarVoiceModel init method  ----> Start");
		if (null != resource && !resource.getPath().contains("/conf/") && Objects.nonNull(currentPage)) {
			final ValueMap nodeProperties = resource.getValueMap();

			extractProductSkuId();

			if (StringUtils.isNotBlank(productSKUId)) {
				String custReviewNQuesNAnswer = EcommHelper.getValueMapNodeValue(nodeProperties, "bazarVoiceFor");
				readBVPropertiesFromComponentORPage(nodeProperties, custReviewNQuesNAnswer);
				if(StringUtils.isNotBlank(custReviewNQuesNAnswer) && custReviewNQuesNAnswer.equals(Constant.CUSTOMERREVIEW)) {
					checkIfCustReviewsNRatingsEnabled();
				}
				if(StringUtils.isNotBlank(custReviewNQuesNAnswer) && custReviewNQuesNAnswer.equals(Constant.QUESTIONANSWERS)) {
					checkIfQuesNAnsEnabled();
				}
			}
		}
		BazaarVoiceModel.LOGGER.debug("BazaarVoiceModel init method  ----> End");
	}

	private void readBVPropertiesFromComponentORPage(final ValueMap nodeProperties, String custReviewNQuesNAnswer) {
		BazaarVoiceModel.LOGGER.debug("readBVPropertiesFromComponentORPage method  ----> Start");
		if (StringUtils.isNotBlank(custReviewNQuesNAnswer)) {
			if (custReviewNQuesNAnswer.equals(Constant.CUSTOMERREVIEW)) {
				getComponentLevelCRRProperties(nodeProperties);
			} else if (custReviewNQuesNAnswer.equals(Constant.QUESTIONANSWERS)) {
				getComponentLevelQnAProperties(nodeProperties);
			}
			getBVPageProperties(custReviewNQuesNAnswer);
		}
		BazaarVoiceModel.LOGGER.debug("readBVPropertiesFromComponentORPage method  ----> End");
	}

	private void checkIfCustReviewsNRatingsEnabled() {
		if((pageEnablecustReview || enableCustomerReview) && (StringUtils.isNotBlank(pageCustReviewScript) || StringUtils.isNotBlank(custReviewScript)))
		{
			isCustRatingsEnabled = Boolean.TRUE;
		}
		
	}
	
	private void checkIfQuesNAnsEnabled() {
		if((pageEnablequestAnswer || enableQuestAnswer) && (StringUtils.isNotBlank(pageQuestAnswScript) || StringUtils.isNotBlank(questAnsScript)))
		{
			isQuesAnsEnabled = Boolean.TRUE;
		}
		
	}

	private void getComponentLevelCRRProperties(final ValueMap nodeProperties) {
		enableCustomerReview = EcommHelper.getBooleanValuefromValueMap(nodeProperties, "enableCustReview",
				Boolean.FALSE);
		if (enableCustomerReview) {
			disableCustReviewMobile = EcommHelper.getBooleanValuefromValueMap(nodeProperties, "disableCustReviewMobile",
					Boolean.FALSE);
			showDialogCRRSpinner = EcommHelper.getBooleanValuefromValueMap(nodeProperties, "showDialogCRRSpinner", Boolean.FALSE);
			custReviewTitle = EcommHelper.getValueMapNodeValue(nodeProperties, "custReviewTitle");
			custReviewScript = EcommHelper.getValueMapNodeValue(nodeProperties, "custReviewScript");
		}
	}

	private void getComponentLevelQnAProperties(final ValueMap nodeProperties) {
		enableQuestAnswer = EcommHelper.getBooleanValuefromValueMap(nodeProperties, "enableQuestAnswer", Boolean.FALSE);
		if (enableQuestAnswer) {
			disableQuenAnsMobile = EcommHelper.getBooleanValuefromValueMap(nodeProperties, "disableQuenAnsMobile",
					Boolean.FALSE);
			showDialogQnsSpinner = EcommHelper.getBooleanValuefromValueMap(nodeProperties, "showDialogQnsSpinner", Boolean.FALSE);
			questAnswTitle = EcommHelper.getValueMapNodeValue(nodeProperties, "questAnswTitle");
			questAnsScript = EcommHelper.getValueMapNodeValue(nodeProperties, "questAnsScript");
		}
	}

	private void getBVPageProperties(String custReviewNQuesNAnswer) {
		BazaarVoiceModel.LOGGER.debug("getBVPageProperties method  ----> Start");
		final InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(
				currentPage.getContentResource());
		if (!enableCustomerReview && StringUtils.isEmpty(custReviewScript) && custReviewNQuesNAnswer.equals(Constant.CUSTOMERREVIEW)) {
			pageEnablecustReview = null != inheritanceValueMap.getInherited("pageEnablecustReview", Boolean.class) ?
					inheritanceValueMap.getInherited("pageEnablecustReview", Boolean.class) : Boolean.FALSE;
			if (pageEnablecustReview) {
				getPageLevelCRRProperties(inheritanceValueMap);
			}
		}
		if (!enableQuestAnswer && StringUtils.isEmpty(questAnsScript) && custReviewNQuesNAnswer.equals(Constant.QUESTIONANSWERS)) {
			pageEnablequestAnswer = null!= inheritanceValueMap.getInherited("pageEnablequestAnswer", Boolean.class) ?
						inheritanceValueMap.getInherited("pageEnablequestAnswer", Boolean.class) : Boolean.FALSE;
			if (pageEnablequestAnswer) {
				getPageLevelQnAProperties(inheritanceValueMap);
			}
		}
		BazaarVoiceModel.LOGGER.debug("getBVPageProperties method  ----> end");
	}

	private void getPageLevelQnAProperties(final InheritanceValueMap inheritanceValueMap) {
		BazaarVoiceModel.LOGGER.info("getPageLevelQnAProperties method  ----> start");
		pageQuestAnswTitle = null!= inheritanceValueMap.getInherited("pageQuestAnswTitle", String.class) ?
				inheritanceValueMap.getInherited("pageQuestAnswTitle", String.class) : StringUtils.EMPTY;
		pageQuestAnswScript = null!= inheritanceValueMap.getInherited("pageQuestAnswScript", String.class) ?
				 inheritanceValueMap.getInherited("pageQuestAnswScript", String.class) : StringUtils.EMPTY;
		pagedisableQuenAnsMobile = null!= inheritanceValueMap.getInherited("pagedisableQuenAnsMobile", Boolean.class) ?
				inheritanceValueMap.getInherited("pagedisableQuenAnsMobile", Boolean.class) : Boolean.FALSE;
		showPageQnSSpinner = null!= inheritanceValueMap.getInherited("showPageQnSSpinner", Boolean.class) ?
						inheritanceValueMap.getInherited("showPageQnSSpinner", Boolean.class) : Boolean.FALSE;
		BazaarVoiceModel.LOGGER.debug("getPageLevelQnAProperties method  ----> end");
	}

	private void getPageLevelCRRProperties(final InheritanceValueMap inheritanceValueMap) {
		BazaarVoiceModel.LOGGER.debug("getPageLevelCRRProperties method  ----> start");
		pageCustReviewTitle = null != inheritanceValueMap.getInherited("pageCustReviewTitle", String.class)
				? inheritanceValueMap.getInherited("pageCustReviewTitle", String.class) : StringUtils.EMPTY;
		pageCustReviewScript = null != inheritanceValueMap.getInherited("pageCustReviewScript", String.class)
				? inheritanceValueMap.getInherited("pageCustReviewScript", String.class) : StringUtils.EMPTY;
		pageDisablecustReviewMobile = null != inheritanceValueMap.getInherited("pageDisablecustReviewMobile",
				Boolean.class) ? inheritanceValueMap.getInherited("pageDisablecustReviewMobile", Boolean.class) : Boolean.FALSE;
		showPageCRRSpinner = null!= inheritanceValueMap.getInherited("showPageCRRSpinner", Boolean.class) ?
						inheritanceValueMap.getInherited("showPageCRRSpinner", Boolean.class) : Boolean.FALSE;
		BazaarVoiceModel.LOGGER.debug("getPageLevelCRRProperties method  ----> end");
	}

	/**
	 * To Retrieve product sku-id/partnumber from incoming request.
	 * <p>
	 * For AG track we check whether flag {@link #extractPartNumberFromSelector} is
	 * true and then extract sku-id/partnumber from selector
	 * </p>
	 * <p>
	 * For others the sku-id/partnumber are retrieved using method
	 * {@link EcommHelper#fetchProductSKUId(SlingHttpServletRequest)}
	 * </p>
	 */
	private void extractProductSkuId() {
		BazaarVoiceModel.LOGGER.debug("Start extractProductSkuId method");
		final String[] selectors = request.getRequestPathInfo().getSelectors();
		String partNumber;

		if (selectors.length >= 2){
		    partNumber = selectors[1];
		    if(StringUtils.isNotBlank(partNumber)&& StringUtils.isNotBlank(selectors[0])) {
    			final String requestUri = request.getRequestURI();
    			if (StringUtils.isNotBlank(requestUri) && StringUtils.isNotBlank(extractPartNumberFromSelector)
    					&& "true".equals(extractPartNumberFromSelector)) {
    				LOGGER.trace("AG track, parsing request uri to fetch sku id");
    				productSKUId = GiftCardSkuProcessor.checkAndUnescapeDelimiter(partNumber);
    			} else {
    				productSKUId = EcommHelper.fetchProductSKUId(request);
    			}
    		}
		} else {
			productSKUId = EcommHelper.fetchProductSKUId(request);
		}
		BazaarVoiceModel.LOGGER.debug("End extractProductSkuId method");
	}

	public Boolean getEnableCustomerReview() {
		return enableCustomerReview;
	}

	public Boolean getDisableCustReviewMobile() {
		return disableCustReviewMobile;
	}

	public Boolean getEnableQuestAnswer() {
		return enableQuestAnswer;
	}

	public Boolean getDisableQuenAnsMobile() {
		return disableQuenAnsMobile;
	}

	public String getCustReviewTitle() {
		return custReviewTitle;
	}

	public String getQuestAnswTitle() {
		return questAnswTitle;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public String getCustReviewScript() {
		return custReviewScript;
	}

	public String getQuestAnsScript() {
		return questAnsScript;
	}

	public String getPageCustReviewTitle() {
		return pageCustReviewTitle;
	}

	public Boolean getPageEnablecustReview() {
		return pageEnablecustReview;
	}

	public Boolean getPageDisablecustReviewMobile() {
		return pageDisablecustReviewMobile;
	}

	public String getPageQuestAnswTitle() {
		return pageQuestAnswTitle;
	}

	public Boolean getPageEnablequestAnswer() {
		return pageEnablequestAnswer;
	}

	public Boolean getPagedisableQuenAnsMobile() {
		return pagedisableQuenAnsMobile;
	}

	public String getPageCustReviewScript() {
		return pageCustReviewScript;
	}

	public String getPageQuestAnswScript() {
		return pageQuestAnswScript;
	}
	
	public Boolean getIsCustRatingsEnabled() {
		return isCustRatingsEnabled;
	}

	public Boolean getIsQuesAnsEnabled() {
		return isQuesAnsEnabled;
	}

	public Boolean getShowPageCRRSpinner() {
		return showPageCRRSpinner;
	}

	public Boolean getShowPageQnSSpinner() {
		return showPageQnSSpinner;
	}

	public Boolean getShowDialogCRRSpinner() {
		return showDialogCRRSpinner;
	}

	public Boolean getShowDialogQnsSpinner() {
		return showDialogQnsSpinner;
	}

}