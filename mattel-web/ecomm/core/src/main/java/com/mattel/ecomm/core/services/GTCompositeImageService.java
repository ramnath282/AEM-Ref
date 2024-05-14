package com.mattel.ecomm.core.services;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = GTCompositeImageService.class)
@Designate(ocd = GTCompositeImageService.Config.class)
public class GTCompositeImageService {

private static final Logger LOGGER = LoggerFactory.getLogger(GTCompositeImageService.class);
	
    private String baseImageUrl;
    private String[] layer1Url;
    private String layer2Url;
    private String layer3Url;
    private String layer4Url;
    private String layerCompParams;
    private String trulyMeImageParams;
    private String nonTMImageParams;
    
    /**
	 * @return the trulyMeImageParams
	 */
	public String getTrulyMeImageParams() {
		return trulyMeImageParams;
	}

	/**
	 * @param trulyMeImageParams the trulyMeImageParams to set
	 */
	public void setTrulyMeImageParams(String trulyMeImageParams) {
		this.trulyMeImageParams = trulyMeImageParams;
	}

	/**
	 * @return the nonTMImageParams
	 */
	public String getNonTMImageParams() {
		return nonTMImageParams;
	}
	
	/**
	 * @param nonTMImageParams the nonTMImageParams to set
	 */
	public void setNonTMImageParams(String nonTMImageParams) {
		this.nonTMImageParams = nonTMImageParams;
	}

	/**
	 * @return the baseImageUrl
	 */
	public String getBaseImageUrl() {
		return baseImageUrl;
	}

	/**
	 * @param baseImageUrl the baseImageUrl to set
	 */
	public void setBaseImageUrl(String baseImageUrl) {
		this.baseImageUrl = baseImageUrl;
	}

	/**
	 * @return the layer1Url
	 */
	public String[] getLayer1Url() {
		return layer1Url;
	}

	/**
	 * @param layer1Url the layer1Url to set
	 */
	public void setLayer1Url(String[] layer1Url) {
		this.layer1Url = layer1Url;
	}

	/**
	 * @return the layer2Url
	 */
	public String getLayer2Url() {
		return layer2Url;
	}

	/**
	 * @param layer2Url the layer2Url to set
	 */
	public void setLayer2Url(String layer2Url) {
		this.layer2Url = layer2Url;
	}

	/**
	 * @return the layer3Url
	 */
	public String getLayer3Url() {
		return layer3Url;
	}

	/**
	 * @param layer3Url the layer3Url to set
	 */
	public void setLayer3Url(String layer3Url) {
		this.layer3Url = layer3Url;
	}

	/**
	 * @return the layer4Url
	 */
	public String getLayer4Url() {
		return layer4Url;
	}

	/**
	 * @param layer4Url the layer4Url to set
	 */
	public void setLayer4Url(String layer4Url) {
		this.layer4Url = layer4Url;
	}

	/**
	 * @return the layerCompParams
	 */
	public String getLayerCompParams() {
		return layerCompParams;
	}

	/**
	 * @param layerCompParams the layerCompParams to set
	 */
	public void setLayerCompParams(String layerCompParams) {
		this.layerCompParams = layerCompParams;
	}

	@Activate
    public void activate(Config config){
		   baseImageUrl=config.baseImageUrl();
		   layer1Url=config.layer1Url();
		   layer2Url=config.layer2Url();
		   layer3Url=config.layer3Url();
		   layer4Url=config.layer4Url();
		   nonTMImageParams = config.nonTMImageParams();
		   trulyMeImageParams = config.trulyMeImageParams();
		   layerCompParams=config.layerCompUrl();
		   LOGGER.debug("Base URL Configured: {}", baseImageUrl);
    }
	
	@ObjectClassDefinition(name="GT Composite Image Configurations", description = "This service contains " +
            "all GT Composite Image Related  OSGI Configuration")
    public @interface Config {
        @AttributeDefinition(name = "Scene7 Base Image Url" , description = "Please enter GT Composite Base Image Endpoint URL")
        String baseImageUrl() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Scene7 Common paramerters Layer" , description = "Please enter GT Scene7 Common Parameters for image")
        String layerCompUrl() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Non TM dolls Image Parameters" , description = "Please enter Image Parameters for Non Truly Me dolls")
        String nonTMImageParams() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Truly Me dolls Image Parameters" , description = "Please enter Image Parameters for Truly Me dolls")
        String trulyMeImageParams() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Scene7 Layer 1 Image Params for Trunks" , description = "Please enter GT Scene7 Layer 1 Image Parameters for Trunks")
        String[] layer1Url() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Scene7 Layer 2 Image Params for bundle 1" , description = "Please enter GT Scene7 Layer 2 Image Parameters for bundle 1")
        String layer2Url() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Scene7 Layer 3 Image Params for bundle 2" , description = "Please enter GT Scene7 Layer 3 Image Parameters for bundle 2")
        String layer3Url() default StringUtils.EMPTY;
        @AttributeDefinition(name = "Scene7 Layer 4 Image Params for doll" , description = "Please enter GT Scene7 Layer 4 Image Parameters for doll")
        String layer4Url() default StringUtils.EMPTY;
    }
}
