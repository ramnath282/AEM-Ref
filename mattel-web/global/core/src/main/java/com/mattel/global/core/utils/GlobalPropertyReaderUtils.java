package com.mattel.global.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Service for properties configuration.
 */
@Component(service = GlobalPropertyReaderUtils.class)
@Designate(ocd = GlobalPropertyReaderUtils.Config.class)
public class GlobalPropertyReaderUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalPropertyReaderUtils.class);
  private String [] snpUrl;
  private String [] typeAheadUrl;

  private String prefAPIKey;
  private String prefAPIUrl;

  @Activate
  public void activate(final Config config) {
    snpUrl = config.snpUrl();
    typeAheadUrl = config.typeAheadUrl();
    prefAPIKey = config.prefAPIKey();
    prefAPIUrl = config.prefAPIUrl();
    LOGGER.debug(" Snp URL {}", snpUrl);
    LOGGER.debug("typeAhead URL : {}", typeAheadUrl);
  }

  @ObjectClassDefinition(name = "Mattel CORP Site Properties Configuration") public @interface Config {
    @AttributeDefinition(name = "Snp Url", description = "Please enter Snp URL") String[] snpUrl() default StringUtils.EMPTY;

    @AttributeDefinition(name = "TypeAhead EndPoint Url", description = "Please enter TypeAhead URL") String[] typeAheadUrl() default StringUtils.EMPTY;

    @AttributeDefinition(name = "Preference API Key for Email Preferences", description = "Please Enter the Preference API Key for Email Preferences")
    String prefAPIKey() default "py37r2fs7jtyvucd6fn5898b";

    @AttributeDefinition(name = "Preference API Url for Email Preferences", description = "Please Enter the Preference API Url for Email Preferences")
    String prefAPIUrl() default "https://api.sdn.mattel.com/";
  }

  public String getSnpUrl(String clientKey) {
    return PropertyUtils.getValueFromKeyMappings(snpUrl, clientKey);
	}

  public void setSnpUrl(String[] snpUrl) {
    this.snpUrl = snpUrl;
  }

  public String getTypeAheadUrl(String clientKey) {
    return PropertyUtils.getValueFromKeyMappings(typeAheadUrl, clientKey);
  }

  public String getPrefAPIKey() {
    return prefAPIKey;
  }

  public String getPrefAPIUrl() {
    return prefAPIUrl;
  }
}
