package com.mattel.ecomm.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author CTS. Service for properties configuration of Ecomm Site.
 */
@Component(service = EcommConfigurationUtils.class, immediate = true)
@Designate(ocd = EcommConfigurationUtils.Config.class)
public class EcommConfigurationUtils {

  private static String tealiumUrl;
  private static String targetUrl;
  private static String rootCatgoryPagePath;
  private static String rootDomainSizeChart;
  private static String scenesevenServerUrl;
  private static String scenesevenContentUrl;
  private static String scenesevenVideoserverUrl;
  private static String formAPIUrl;
  private static String formAPIKey;
  private static String prefAPIKey;
  private static String prefAPIUrl;
  private static String atPropertyTarget;
  private static String usableNetScriptPath;
  private static boolean accessibilitySwitch;

  @Activate
  public void activate(final Config config) {
    EcommConfigurationUtils.buildConfigs(config);
  }

  private static void buildConfigs(Config config) {
    EcommConfigurationUtils.tealiumUrl = config.tealiumUrl();
    EcommConfigurationUtils.targetUrl = config.targetUrl();
    EcommConfigurationUtils.rootCatgoryPagePath = config.rootCatgoryPagePath();
    EcommConfigurationUtils.rootDomainSizeChart = config.rootDomainSizeChart();
    EcommConfigurationUtils.scenesevenServerUrl = config.scenesevenServerUrl();
    EcommConfigurationUtils.scenesevenContentUrl = config.scenesevenContentUrl();
    EcommConfigurationUtils.scenesevenVideoserverUrl = config.scenesevenVideoserverUrl();
    EcommConfigurationUtils.formAPIUrl = config.formAPIUrl();
    EcommConfigurationUtils.formAPIKey = config.formAPIKey();
    EcommConfigurationUtils.prefAPIKey = config.prefAPIKey();
    EcommConfigurationUtils.prefAPIUrl = config.prefAPIUrl();
    EcommConfigurationUtils.atPropertyTarget = config.atPropertyTarget();
    EcommConfigurationUtils.usableNetScriptPath = config.usableNetScriptPath();
    EcommConfigurationUtils.accessibilitySwitch = config.accessibilitySwitch();
  }

  @ObjectClassDefinition(name = "Ecomm property Configurations")
  public @interface Config {
    @AttributeDefinition(name = "Analytics Script Url", description = "Please enter script URL "
        + "for analytics tracking ")
    String tealiumUrl();

    @AttributeDefinition(name = "Target Url", description = "Please enter Adobe Target script URL ")
    String targetUrl();

    @AttributeDefinition(name = "Root Category Page Path", description = "Please enter Root Category Page Path")
    String rootCatgoryPagePath() default "/content/ag/en/shop/categories/";

    @AttributeDefinition(name = "Root Domain and URI for size chart", description = "Please Enter combination of root Domain and URI for size chart")
    String rootDomainSizeChart() default "https://www.americangirl.com/shop/";

    @AttributeDefinition(name = "Sceneseven Server End Point Url", description = "Please Enter Sceneseven Server End Point Url")
    String scenesevenServerUrl() default "https://s7d9.scene7.com/is/image";

    @AttributeDefinition(name = "Sceneseven Content Server End Point Url", description = "Please Enter Sceneseven Content Server End Point Url")
    String scenesevenContentUrl() default "https://s7d9.scene7.com/skins/";

    @AttributeDefinition(name = "Sceneseven Video Server End Point Url", description = "Please Enter Sceneseven Video Server End Point Url")
    String scenesevenVideoserverUrl() default "https://s7d9.scene7.com/is/content/";

    @AttributeDefinition(name = "Form API Url for Email Preferences", description = "Please Enter the Form API Url for Email Preferences")
    String formAPIUrl() default "https://api.sdn.mattel.com/";

    @AttributeDefinition(name = "Form API Key for Email Preferences", description = "Please Enter the Form API Key for Email Preferences")
    String formAPIKey() default "y6kg2b2y67gmnwep9cz53ymw";

    @AttributeDefinition(name = "Preference API Key for Email Preferences", description = "Please Enter the Preference API Key for Email Preferences")
    String prefAPIKey() default "py37r2fs7jtyvucd6fn5898b";

    @AttributeDefinition(name = "Preference API Url for Email Preferences", description = "Please Enter the Preference API Url for Email Preferences")
    String prefAPIUrl() default "https://api.sdn.mattel.com/";

    @AttributeDefinition(name = "AT property for Recommended Products", description = "Please enter the value for at_property used in Recommended Products")
    String atPropertyTarget() default "8b26e862-74b9-9aa2-9eb7-711af3f502c1";

    @AttributeDefinition(name = "Accessibility Switch", description = "Switch for enabling or disabling Accessibility", type = AttributeType.BOOLEAN)
    boolean accessibilitySwitch() default false;

    @AttributeDefinition(name = "UsableNet Script Url", description = "Please enter the UsableNet Accessibility script URL")
    String usableNetScriptPath();

  }

  public static String getTealiumUrl() {
    return EcommConfigurationUtils.tealiumUrl;
  }

  public static String getTargetUrl() {
    return EcommConfigurationUtils.targetUrl;
  }

  public static String getRootCatgoryPagePath() {
    return EcommConfigurationUtils.rootCatgoryPagePath;
  }

  public static String getRootDomainSizeChart() {
    return EcommConfigurationUtils.rootDomainSizeChart;
  }

  public static void setRootDomainSizeChart(String rootDomainSizeChart) {
    EcommConfigurationUtils.rootDomainSizeChart = rootDomainSizeChart;
  }

  public static String getScenesevenServerUrl() {
    return EcommConfigurationUtils.scenesevenServerUrl;
  }

  public static String getScenesevenContentUrl() {
    return EcommConfigurationUtils.scenesevenContentUrl;
  }

  public static String getScenesevenVideoserverUrl() {
    return EcommConfigurationUtils.scenesevenVideoserverUrl;
  }

  public static String getFormAPIUrl() {
    return EcommConfigurationUtils.formAPIUrl;
  }

  public static String getFormAPIKey() {
    return EcommConfigurationUtils.formAPIKey;
  }

  public static String getPrefAPIKey() {
    return EcommConfigurationUtils.prefAPIKey;
  }

  public static String getPrefAPIUrl() {
    return EcommConfigurationUtils.prefAPIUrl;
  }

  public static String getAtPropertyTarget() {
    return EcommConfigurationUtils.atPropertyTarget;
  }

  public static String getUsableNetScriptPath() {
    return EcommConfigurationUtils.usableNetScriptPath;
  }

  public static boolean getAccessibilitySwitch() {
    return EcommConfigurationUtils.accessibilitySwitch;
  }

}
