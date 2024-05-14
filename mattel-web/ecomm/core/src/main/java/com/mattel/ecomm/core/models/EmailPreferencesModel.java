package com.mattel.ecomm.core.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.PrimaryPreferencesMattelBrandsPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@Model(adaptables = {Resource.class,SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EmailPreferencesModel {

	Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private SlingHttpServletRequest request;

	@Inject
	Resource resource;

	@Inject
	@Via("resource")
	private Node primaryPreferencesList;

	@Inject
	private MultifieldReader multifieldReader;

	@Inject
	@Via("resource")
	private String image;

	private ResourceResolver resourceResolver;

	private String backgroundImagePath;
    private String decodedEmailId;
	private boolean isValidEmail = false;
	private String formAPIUrl;
	private String formAPIKey;
	private String prefAPIKey;
	private String prefAPIUrl;

	private List<PrimaryPreferencesMattelBrandsPojo> preferencesList = new ArrayList<>();

	@PostConstruct
	public void postConstruct() {
		try {
			log.debug("Start EmailPreferencesModel - Init Method");
			if (null != resource) {
				resourceResolver = resource.getResourceResolver();
			}
			formAPIUrl = EcommConfigurationUtils.getFormAPIUrl();
			formAPIKey = EcommConfigurationUtils.getFormAPIKey();
			prefAPIKey = EcommConfigurationUtils.getPrefAPIKey();
			prefAPIUrl = EcommConfigurationUtils.getPrefAPIUrl();
			String key = request.getParameter("Key");
			String hash = request.getParameter("hash");
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(hash)) {
				Base64.Decoder decoder = Base64.getDecoder();
				decodedEmailId = new String(decoder.decode(key));
				String decodedBase64Key = "ONEST0REcRYPT" + decodedEmailId;
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] encodedBytes = digest.digest(decodedBase64Key.getBytes(StandardCharsets.UTF_8));
				StringBuilder sb = new StringBuilder();
				for(int i=0; i< encodedBytes.length ;i++)
				{
					sb.append(Integer.toString((encodedBytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				String sha256EncodedHash = sb.toString();
				if (sha256EncodedHash.equals(hash)) {
					isValidEmail = true;
				} else {
					log.error("Invalid email Id {} ", decodedEmailId);
				}
			}
			populatePrimaryPreferencesList(preferencesList);
			setBackgroundPath();
			log.debug("End EmailPreferencesModel - Init Method");
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException: {}", e);
		}
	}

	/**
	 * method is to provide set original background image path
	 */
	private void setBackgroundPath() {
		if (null != resourceResolver) {
			Resource imageResource = resourceResolver.resolve(image + "/jcr:content/renditions/original");
			if(!StringUtils.isEmpty(imageResource.getPath())) {
				backgroundImagePath = imageResource.getPath();
			}
		}
	}

	public void populatePrimaryPreferencesList(List<PrimaryPreferencesMattelBrandsPojo> preferencesList) {
		Map<String, ValueMap> multifieldProperty;
		multifieldProperty = multifieldReader.propertyReader(primaryPreferencesList);
		for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
			PrimaryPreferencesMattelBrandsPojo primaryPreference = new PrimaryPreferencesMattelBrandsPojo();
			primaryPreference.setTitle(entry.getValue().get("primaryPrefTitle", String.class));
			primaryPreference.setDescription(entry.getValue().get("primaryPrefDescription", String.class));
			primaryPreference.setPreferenceID(entry.getValue().get("primaryPrefID", String.class));
			primaryPreference.setAlwaysEnglish(entry.getValue().get("alwaysEnglish", String.class));

			preferencesList.add(primaryPreference);
			log.debug("preferencesList size of MattelBrandsListModel is {}",
					preferencesList.size());
		}
	}

	public boolean getIsValidEmail() {
		return isValidEmail;
	}

	public String getDecodedEmailId() {
		return decodedEmailId;
	}

	public List<PrimaryPreferencesMattelBrandsPojo> getPreferencesList() {
		return preferencesList;
	}

	public void setPreferencesList(List<PrimaryPreferencesMattelBrandsPojo> preferencesList) {
		this.preferencesList = preferencesList;
	}

	public String getFormAPIUrl() {
		return formAPIUrl;
	}

	public String getFormAPIKey() {
		return formAPIKey;
	}

	public String getPrefAPIKey() {
		return prefAPIKey;
	}

	public String getPrefAPIUrl() {
		return prefAPIUrl;
	}

	public void setRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

}
