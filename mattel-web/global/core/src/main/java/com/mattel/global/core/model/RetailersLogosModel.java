package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.pojo.RetailersLogoDetailPojo;
import com.mattel.global.core.services.MultifieldReader;

@Model(adaptables = Resource.class)
public class RetailersLogosModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailersLogosModel.class);

	@Inject
	@Optional
	private Node retailerLogoDetails;

	@Inject
	@Optional
	private String onClickOption;

	@Inject
	@Optional
	private String confirmationButtonText;

	@Inject
	@Optional
	private String cancelButtonText;

	@Inject
	@Optional
	private String interstitialTitle;

	@Inject
	@Optional
	private String interstitialSubTitle;

	@Inject
	@Optional
	private MultifieldReader multifieldReader;

	private List<RetailersLogoDetailPojo> retailersLogoDetailPojos;

	/**
	 * init method.
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("Init Method - Start");
		if (retailerLogoDetails != null) {
			retailersLogoDetailPojos = listRetailers();
		}
		LOGGER.info("Init Method - End");
	}

	/**
	 * Method for setting all the values in dialog field into Pojo.
	 *
	 * @return ArrayList.
	 */
	private List<RetailersLogoDetailPojo> listRetailers() {
		LOGGER.info("Retaile Logo Details -> Start");
		Map<String, ValueMap> brandsMap = multifieldReader.propertyReader(retailerLogoDetails);
		List<RetailersLogoDetailPojo> allLogoDetails = new ArrayList<>();
		for (Map.Entry<String, ValueMap> entry : brandsMap.entrySet()) {
			RetailersLogoDetailPojo retailersLogoDetailPojo = new RetailersLogoDetailPojo();
			retailersLogoDetailPojo.setLogoAltTxt(entry.getValue().get("logoAltTxt", String.class));
			retailersLogoDetailPojo.setLogoImage(entry.getValue().get("logoImage", String.class));
			retailersLogoDetailPojo.setRetailerLogoLink(entry.getValue().get("retailerLogoLink", String.class));
			retailersLogoDetailPojo.setAnalyticsText(entry.getValue().get("analyticsText", String.class));
			LOGGER.debug("Pojo of Retaile Logo Details {}", retailersLogoDetailPojo);
			allLogoDetails.add(retailersLogoDetailPojo);
		}
		LOGGER.info("Retaile Logo Details -> End {}", allLogoDetails);
		return allLogoDetails;
	}

	public List<RetailersLogoDetailPojo> getRetailersLogoDetailPojos() {
		return retailersLogoDetailPojos;
	}

	public String getConfirmationButtonText() {
		return confirmationButtonText;
	}

	public String getCancelButtonText() {
		return cancelButtonText;
	}

	public String getInterstitialTitle() {
		return interstitialTitle;
	}

	public String getInterstitialSubTitle() {
		return interstitialSubTitle;
	}

	public String getOnClickOption() {
		return onClickOption;
	}

	public void setRetailerLogoDetails(Node retailerLogoDetails) {
		this.retailerLogoDetails = retailerLogoDetails;
	}

	public void setOnClickOption(String onClickOption) {
		this.onClickOption = onClickOption;
	}

	public void setConfirmationButtonText(String confirmationButtonText) {
		this.confirmationButtonText = confirmationButtonText;
	}

	public void setCancelButtonText(String cancelButtonText) {
		this.cancelButtonText = cancelButtonText;
	}

	public void setInterstitialTitle(String interstitialTitle) {
		this.interstitialTitle = interstitialTitle;
	}

	public void setInterstitialSubTitle(String interstitialSubTitle) {
		this.interstitialSubTitle = interstitialSubTitle;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	public void setRetailersLogoDetailPojos(List<RetailersLogoDetailPojo> retailersLogoDetailPojos) {
		this.retailersLogoDetailPojos = retailersLogoDetailPojos;
	}

}
