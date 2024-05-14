package com.mattel.global.core.model;

import com.mattel.global.core.pojo.PrimaryPreferencesMattelBrandsPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.CryptoSupportUtils;
import com.mattel.global.core.utils.GlobalPropertyReaderUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL) public class EmailPreferencesModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailPreferencesModel.class);

  @Inject private SlingHttpServletRequest request;

  @Inject Resource resource;

  @Inject @Via("resource") private Node primaryPreferencesList;

  @Inject private MultifieldReader multifieldReader;

  @Inject @Via("resource") private String image;

  @OSGiService
  GlobalPropertyReaderUtils globalPropertyReaderUtils;

  private ResourceResolver resourceResolver;

  private String backgroundImagePath;
  private String decodedEmailId;
  private boolean isValidEmail = false;
  private String prefAPIKey;
  private String prefAPIUrl;
  private String encryptedEmailId;

  private List<PrimaryPreferencesMattelBrandsPojo> preferencesList = new ArrayList<>();

  @PostConstruct public void postConstruct() {
    try {
      LOGGER.debug("Start EmailPreferencesModel - Init Method");
      if (null != resource) {
        resourceResolver = resource.getResourceResolver();
      }
      prefAPIKey = globalPropertyReaderUtils.getPrefAPIKey();
      prefAPIUrl = globalPropertyReaderUtils.getPrefAPIUrl();
      String key = request.getParameter("Key");
      String hash = request.getParameter("hash");
      if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(hash)) {
        Base64.Decoder decoder = Base64.getDecoder();
        decodedEmailId = new String(decoder.decode(key));
        String decodedBase64Key = "ONEST0REcRYPT" + decodedEmailId;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedBytes = digest.digest(decodedBase64Key.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encodedBytes.length; i++) {
          sb.append(Integer.toString((encodedBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        String sha256EncodedHash = sb.toString();
        if (sha256EncodedHash.equals(hash)) {
          isValidEmail = true;
          encryptedEmailId = CryptoSupportUtils.encryptString(decodedEmailId);
        } else {
          LOGGER.error("Invalid email Id {} ", decodedEmailId);
        }
      }
      populatePrimaryPreferencesList(preferencesList);
      setBackgroundPath();
      LOGGER.debug("End EmailPreferencesModel - Init Method");
    } catch (NoSuchAlgorithmException e) {
      LOGGER.error("NoSuchAlgorithmException in EmailPreferenceModel Class {}: ", e.getMessage());
    }
  }

  /**
   * method is to provide set original background image path
   */
  private void setBackgroundPath() {
    if (null != resourceResolver) {
      Resource imageResource = resourceResolver.resolve(image + "/jcr:content/renditions/original");
      if (!StringUtils.isEmpty(imageResource.getPath())) {
        backgroundImagePath = imageResource.getPath();
      }
    }
  }

  public void populatePrimaryPreferencesList(
      List<PrimaryPreferencesMattelBrandsPojo> preferencesList) {
    Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(primaryPreferencesList);
    for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
      PrimaryPreferencesMattelBrandsPojo primaryPreference = new PrimaryPreferencesMattelBrandsPojo();
      primaryPreference.setTitle(entry.getValue().get("primaryPrefTitle", String.class));
      primaryPreference
          .setDescription(entry.getValue().get("primaryPrefDescription", String.class));
      primaryPreference.setPreferenceID(entry.getValue().get("primaryPrefID", String.class));
      primaryPreference.setAlwaysEnglish(entry.getValue().get("alwaysEnglish", String.class));

      preferencesList.add(primaryPreference);
    }
    LOGGER.debug("preferencesList size of MattelBrandsListModel is {}", preferencesList.size());
  }

  public boolean getIsValidEmail() {
    return isValidEmail;
  }

  public String getDecodedEmailId() {
    return decodedEmailId;
  }

  public String getEncryptedEmailId() { return encryptedEmailId; }

  public List<PrimaryPreferencesMattelBrandsPojo> getPreferencesList() {
    return preferencesList;
  }

  public void setPreferencesList(List<PrimaryPreferencesMattelBrandsPojo> preferencesList) {
    this.preferencesList = preferencesList;
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

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

}
