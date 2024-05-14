/*******************************************************************************
 * Copyright 2016 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 ******************************************************************************/

"use strict";

use( function(){

    var EDITOR_CONST = {
        	DD_TARGET: com.day.cq.wcm.api.components.DropTarget.CSS_CLASS_PREFIX + "image ",
        	TOUCHUI_PLACEHOLDER: "cq-placeholder ",
        	CLASSIC_PLACEHOLDER: "cq-image-placeholder"
    	},
        METADATA_NODE = com.day.cq.dam.api.s7dam.constants.S7damConstants.S7_ASSET_METADATA_NODE,
        RUNMODE_DMS7 = "dynamicmedia_scene7",
        S7_TYPE_NODE = com.day.cq.dam.api.s7dam.constants.S7damConstants.PN_S7_TYPE,
        SCENE7_FILE_PROP = METADATA_NODE + "/metadata/dam:scene7File",
        SCENE7_FILE_AVS_PROP = METADATA_NODE + "/metadata/dam:scene7FileAvs",
        SCENE7_FILE_AVS = "videoavs",       
        VIEWER_PRESET_JCR = "/libs/dam/viewers/default/jcr:content",
        PROP_KEY = {
            sdkRoot : "sdkRootPath",
            sdkVersion: "sdkVersion",
            viewerRoot: "viewerRootPath",
            viewerVersion: "viewerVersion",
            lastReplication: "cq:lastReplicationAction"
        },
        /*
        	Map between asset type and viewer preset type to viewer constructor
            Note that 'none' means default constructor to render the asset type when there is no preset.
         */
        ASSETTYPE_TO_VIEWER = {
            'imageset': {
                'none': 'ZoomViewer',
                'image_set': 'ZoomViewer',
                'flyout_zoom': 'FlyoutViewer',
				'vertical_zoom': 'ZoomVerticalViewer'
            },
            'video': {
                'none': 'VideoViewer',
                'video': 'VideoViewer'
            },
            'videoavs': {
                'none': 'VideoViewer',
                'video': 'VideoViewer'
            },
            'image': {
                'none': '',
                'zoom': 'BasicZoomViewer',
                'flyout_zoom':  'FlyoutViewer',
				'vertical_zoom': 'ZoomVerticalViewer'
            },
            'spinset': {
                'none': 'SpinViewer',
                'spin_set': 'SpinViewer'
            },
            'mixedmediaset': {
                'none': 'MixedMediaViewer',
                'mixed_media': 'MixedMediaViewer'
            }
        },
    	//OOTB Default viewer preset as fallback except image CQ-74275
    	OOTB_DEFAULT_VP = {
        	"video": "/conf/global/settings/dam/dm/presets/viewer/Video_social|VIDEO|false",
        	"videoavs": "/conf/global/settings/dam/dm/presets/viewer/Video_social|VIDEO|false",
        	"imageset": "/conf/global/settings/dam/dm/presets/viewer/InlineZoom|FLYOUT_ZOOM|false",
        	"spinset": "/conf/global/settings/dam/dm/presets/viewer/SpinSet_light|SPIN_SET|false",
        	"mixedmediaset": "/conf/global/settings/dam/dm/presets/viewer/InlineMixedMedia_light|MIXED_MEDIA|false"
    	},
        //String representing empty viewer preset
        EMPTY_VP = "||";

    //locale
    var pageLocale = inheritedPageProperties["jcr:language"];
    if (!pageLocale) {
        pageLocale = "en"; //default to English when page doesn't have language setup.
    }
	//Is DM enabled?
    var isDMEnabled = com.day.cq.dam.commons.util.DynamicMediaHelper.isDynamicMediaEnabled(resource.getResourceResolver());
    //Is S7 enabled?
    var isS7Enabled = sling.getService(org.apache.sling.settings.SlingSettingsService).getRunModes().contains(RUNMODE_DMS7);
    //Is WCM mode disabled? - for publish side
    var isWCMDisabled = (com.day.cq.wcm.api.WCMMode.fromRequest(request) == com.day.cq.wcm.api.WCMMode.DISABLED);
    //Adding variable to remove ambiguity caused by ln (!isS7Enabled && isWCMDisabled) below...
    var isS7Remote = isS7Enabled;

    //if not dms7 runmode is enabled and it is the publish node then check for scene7file
    if (!isS7Enabled && isWCMDisabled) {
	    	if (properties['assetID'] && properties['assetID'] == fileReference) {
	    		isS7Enabled = true;
	    	}
    }

    //Is the editor in touchUI mode?
	var isTouchUI = com.day.cq.wcm.foundation.Placeholder.isAuthoringUIModeTouch(request);
	var placeholderCSS = EDITOR_CONST.DD_TARGET;
    if (isTouchUI) {
        placeholderCSS = EDITOR_CONST.TOUCHUI_PLACEHOLDER + placeholderCSS;
    }
    else if (!isWCMDisabled) {
		placeholderCSS += EDITOR_CONST.CLASSIC_PLACEHOLDER
    }

    //Current component ID
    var random = "" + (Math.floor(Math.random() * 1000) + 1);
    var componentId = resource.getName() + "_" +  random + "_"+this.imageDetail;

	var viewerNode = null,
     // s7sdkPath = "",
        viewerPath = "",
		s7viewerPath = "";
    if (currentSession.nodeExists(VIEWER_PRESET_JCR)) {
        viewerNode = currentSession.getNode(VIEWER_PRESET_JCR);
        var viewerRootPath = viewerNode.getProperty(PROP_KEY.viewerRoot).getString();
        var viewerVersion = viewerNode.getProperty(PROP_KEY.viewerVersion).getString();
	 	s7viewerPath = request.contextPath + viewerRootPath + viewerVersion + "/";
    }

    //Asset Info
    	var fileReference = properties.get(this.imageDetail);
		var assetID = fileReference;
		var assetNode = null;
		var assetName = "";
		var assetPath = "";
		var assetType = "";
		var isRemoteAsset = false;
		var s7assetType = "";
		var viewerPreset = properties['s7ViewerPreset'];
		var imagePreset = properties['s7ImagePreset'];
		var width = properties['width'];
		var height = properties['height'];
		var stageSize = "";
        var mode = "";
		var breakpoints = properties['breakpoints'];
		var urlModifiers = properties['urlModifiers'];
		var linkURL = properties['linkUrl'];
		var linkTarget = properties['linkTarget'];
		var alt = properties["./"+this.imageDetail+"AltText"];
		var presetType = properties['s7PresetType'];
		var videoServer = properties['videoserverurl'];
		var viewerModifiers = properties['viewermodfiers'];
		var viewerPresetPath = viewerPreset;

    // trim breakpoints it will be used to distinguish if responsive image is applicable
    breakpoints = breakpoints ? breakpoints.trim() : breakpoints;

    //default viewer preset from design dialog except image CQ-74275
    var defaultVP = {
        "video": currentStyle['./defVPVideo'],
        "videoavs": currentStyle['./defVPVideo'],
        "imageset": currentStyle['./defVPImageSet'],
        "spinset": currentStyle['./defVPSpinSet'],
        "mixedmediaset": currentStyle['./defVPMixedMediaSet']
   	};

	if (fileReference && currentSession.nodeExists(fileReference) ) {
		assetPath = fileReference.substring(1);
		assetNode = currentSession.getNode(fileReference);
        assetName = assetNode.getName();
        if( assetNode.hasProperty(METADATA_NODE + "/" + S7_TYPE_NODE)) {
            assetType = assetNode.getProperty(METADATA_NODE + "/" + S7_TYPE_NODE).getString();
            s7assetType = assetType;
            assetType = assetType.toLowerCase();
        }

        if(isWCMDisabled) {
        		assetID = properties["./assetID"+this.imageDetail];
        }
        else { //author node
            if (s7assetType == "") {
                //unrecognized asset - do not render
				assetID = "";
            }
            else {
                // check if this is a remote asset
                if( assetNode.hasProperty(SCENE7_FILE_PROP)) {

                    if(assetType.equals(SCENE7_FILE_AVS) && assetNode.hasProperty(SCENE7_FILE_AVS_PROP)) {
                        // check if this is a remote asset and avs video file
                        assetID = assetNode.getProperty(SCENE7_FILE_AVS_PROP).getString();
                    }
                    else {
                        assetID = assetNode.getProperty(SCENE7_FILE_PROP).getString();
                    }
                    isRemoteAsset = true;
                }
            }
        }

        if(!viewerPresetPath) {
        	viewerPresetPath = "";
        }

        videoServer = getPublicVideoServerURL(isWCMDisabled, fileReference, isRemoteAsset);
    }

	// get remote vp if using remote asset
    if(isRemoteAsset && viewerPreset) {

    	var vpPath = viewerPreset.substring(0,viewerPreset.indexOf("|"));
		if (vpPath && currentSession.nodeExists(vpPath)) {
    	var vpNode = currentSession.getNode(vpPath);

    	if(vpNode.hasProperty(SCENE7_FILE_PROP)) {
    		viewerPresetPath = viewerPreset.replace(vpPath, "/"+vpNode.getProperty(SCENE7_FILE_PROP).getString());
    	}

    	if(vpNode.hasProperty(SCENE7_FILE_AVS_PROP)) {
    		viewerPresetPath = viewerPreset.replace(vpPath, "/"+vpNode.getProperty(SCENE7_FILE_AVS_PROP).getString());
    	}
    }
	}
    else {
        // DM Hybrid case - use viewerPreset as-is
        viewerPresetPath = viewerPreset
    }


    //Author side only to save extra parameters to be used in publish side
    if (!isWCMDisabled) {
    	
	    	if(!viewerPreset) {
	        	viewerPreset = "";
	        	viewerPresetPath = "";
        }
	    	
		var props = resource.adaptTo(org.apache.sling.api.resource.ModifiableValueMap);
		props.put("assetID"+this.imageDetail, assetID);
		props.put("assetType", s7assetType);
        props.put("imageserverurl", getPublishImageServerURL(assetPath, isRemoteAsset));
        props.put("videoserverurl", videoServer);
        props.put("s7ViewerPreset", viewerPreset);
        props.put("./s7PresetType", presetType);
        props.put("s7ViewerPresetPath", viewerPresetPath);
        
        if (s7assetType == "") {
            props.put("fileReference", "");
        }

        //get rid of existing viewer preset if the type is not matched
        if (!isMatchedViewerPreset(ASSETTYPE_TO_VIEWER, assetType, properties['s7ViewerPreset']) ||
            presetType == "image" || presetType == "smartCrop") {
        		props.put("s7ViewerPreset", "");
        		props.put("viewerPresetPath", "");
            viewerPreset = "";
            viewerPresetPath = "";
        }
        // get rid of existing image preset, breakpoints and image url modifiers if viewer preset has been selected
        if (assetType != 'image' || viewerPreset) {
            // asset is either not an image or has viewer preset
            props.put("s7ImagePreset", "");
            props.put("breakpoints", "");
            props.put("urlModifiers", "");
            imagePreset = "";
            urlModifiers = "";
			breakpoints = "";
        } else if (presetType == "smartCrop") {
            // asset is an image, does not have viewer preset and smart crop has been selected
            // get rid of image preset, breakpoints and viewer presets if smart crop is selected
            props.put("s7ImagePreset", "");
            props.put("breakpoints", "");
            props.put("s7ViewerPreset", "");
            imagePreset = "";
            breakpoints = "";
            viewerPreset = "";
        }
        
        
        //make sure viewer is returned from delivery server if DMS7 runmode
        if(isS7Remote) {
        		viewerPath = getPublishImageServerURL(assetPath, isRemoteAsset);
	 		viewerPath += s7viewerPath.replace("/etc/dam/viewers/","");
	 		viewerPath = viewerPath.replace("/is/image", "");
	 		//save viewer path
	 		props.put("viewerPath", viewerPath);
	 	}
        
        
        resource.getResourceResolver().commit();
    }
    else {
        viewerPresetPath = properties["s7ViewerPresetPath"];

        //Update viewer path if applicable
        if(properties["viewerPath"]) {
            s7viewerPath = properties["viewerPath"];
        }
        //for publish side we don't have runmode so we rely on fileReference not match with ID
        if (assetID != fileReference) {
            isS7Enabled = true;
        }
    }

    // set mode to smartcrop only for author in DMS7 runmode and publish node
    if ((isS7Enabled || isWCMDisabled) && presetType == "smartCrop") {
        mode = "smartcrop";
    }

    if (!width && width != 0) {
		width = -1;
    }
    if (!height && height != 0) {
        height = -1;
    }

    if (width > -1 && height > -1) {
        stageSize = width + "," + height;
    }

    //default viewer preset fallback except image
    if (assetType != "image") {
        if (!viewerPreset || viewerPreset == EMPTY_VP) {
            viewerPreset = defaultVP[assetType]; //fallback to default settings from design mode
            if (!viewerPreset || viewerPreset == EMPTY_VP) {
                viewerPreset = OOTB_DEFAULT_VP[assetType]; //fallback to OOTB viewer preset
            }
        }
    }

    /*code path DM issue fix - start*/
    /* following code patch added to get scene7path and scene7 domain from 
       authored image's metadata property */ 
	var scene7file_path = "";
	var scene7domain_path = "";
    function getMetadataDetail(){
		var assetResource = resource.getResourceResolver().getResource(fileReference + "/jcr:content/metadata");
		if(assetResource) {
			var assetNode = assetResource.adaptTo(javax.jcr.Node);
			if(assetNode != null){
				scene7file_path = assetNode.hasProperty("dam:scene7File") ? assetNode.getProperty("dam:scene7File").getString() : "";
				scene7domain_path = assetNode.hasProperty("dam:scene7Domain") ? assetNode.getProperty("dam:scene7Domain").getString() : "";
			}
		}
	}   
	getMetadataDetail();
	/*code path DM issue fix - end*/
	
    return {
		fileReference:fileReference,
        componentId: componentId,
        isDMEnabled: isDMEnabled,
        isS7Enabled: isS7Enabled,
        isWCMDisabled: isWCMDisabled,
        pageLocale: pageLocale,
        placeholder: {
            dropCSS: EDITOR_CONST.DD_TARGET,
        	css: placeholderCSS,
            text: component.title
        },
        asset: {
        	// we are getting this path property from  authored image's metadata property
            path: scene7file_path,
            name: assetName,
            s7damType: assetType,
            // we are getting this imageserver, videoserver property from  authored image's metadata property + required suffix
            imageserver: isWCMDisabled ? scene7domain_path + "is/image/" : getImageServerURL(isWCMDisabled, assetID, isRemoteAsset),
            videoserver: isWCMDisabled ? scene7domain_path + "is/content/" : getVideoServerURL(isWCMDisabled, request.contextPath, isRemoteAsset),
            contenturl: getContentURL(isWCMDisabled, getImageServerURL(isWCMDisabled, assetID), request.contextPath, viewerPreset, isS7Enabled),
            config: viewerPresetPath,
            mode: mode,
            imagePreset: imagePreset,
            viewerType: getViewer(ASSETTYPE_TO_VIEWER, assetType, viewerPreset),
            //viewerPath: s7viewerPath,
            // we are getting this path property from  authored image's metadata property
            viewerPath: scene7domain_path + "s7viewers/",
            stageSize: stageSize,
            breakpoints: breakpoints,
            urlModifiers: urlModifiers,
            linkURL: linkURL,
            linkTarget: linkTarget,
            alt: alt,
            viewerModifiers: viewerModifiers
        }
    };
});

/**
 * @param assetToViewerList asset to viewer list
 * @param assetType current asset type
 * @param viewerPreset current viewer preset string
 * @return viewer constructor
 */
function getViewer(assetToViewerList, assetType, viewerPreset){
    if (!assetType) {
        return '';
    }
    var presetType = getViewerPresetType(viewerPreset);
    var viewer = assetToViewerList[assetType][presetType];
    if (typeof viewer != 'undefined') {
        return viewer;
    }
    return '';
}

/**
 * @param isWCMDisabled
 * @return video server URL depending on WCM disabled
 */
function getVideoServerURL(isWCMDisabled, contextPath, isRemote){
    var videoServerURL = "";
    
    if (isWCMDisabled) {
		videoServerURL = properties['videoserverurl'];
    }
    else {
    	if(isRemote) {
    		videoServerURL = contextPath + "/is/content/";
    	}
    	else {
    		videoServerURL = getPrivateVideoServerURL();
    	}
    }
    return videoServerURL;
}

/**
 * @param isWCMDisabled is WCM in disabled mode
 * @param imageServer image server url
 * @param contextPath
 * @param viewerPreset viewer preset string
 * @return content URL for static content (CSS, static viewer assets)
 */

function getContentURL(isWCMDisabled, imageServer, contextPath, viewerPreset, isS7Enabled){
	var contentPath;
	
	if (isCustomPreset(viewerPreset)) {
        if (isWCMDisabled) {
            contentPath = imageServer.replace("/is/image", "/is/content");
        } else {
            contentPath = contextPath + "/is/content";
        }
    }
    else {
        if (isS7Enabled){
            //DMS7 case
            if (isWCMDisabled) {
                //Publish DMS7 - we got the content from publish IS
                contentPath = imageServer.replace("is/image", "is/content");
            }
            else {
                //Author DMS7 - we got the content from IS proxy
                contentPath = contextPath + "/is/content";
            }
        }
        else {
            //DM Hybrid - always get OOTB from publish AEM
            contentPath = contextPath + "/";
        }
    }
    return contentPath;
}

/**
 * @return video proxy URL
 */
function getVideoProxyURL() {
    var videoProxyURL = com.day.cq.dam.commons.util.DynamicMediaServicesConfigUtil.getServiceUrl(resource.getResourceResolver());
    if (videoProxyURL != null){
        if (!videoProxyURL.endsWith("/")) {
        	//Adjust path by adding trailing /
        	videoProxyURL += "/";
        }
        return videoProxyURL;
    }
    return "";
}

/**
 * @return public video server URL
 */
function getPublicVideoServerURL(isWCMDisabled, assetPath, isRemote) {
	
	if (isWCMDisabled) {
		return properties['videoserverurl'];
    }
	else if(isRemote) {
		var assetResource = resource.getResourceResolver().getResource("/"+assetPath);
		var serverUrl = sling.getService(com.day.cq.dam.api.s7dam.utils.PublishUtils).externalizeImageDeliveryAsset(assetResource, "");
		serverUrl = serverUrl.replace("is/image","is/content");
		log.info("server url: ["+serverUrl+"]");
		
		return serverUrl;
	}
	else {
		var videoProxyURL = getVideoProxyURL();
	    if (videoProxyURL != null && videoProxyURL != "") {
	        //get video public key for publish instance
	        var videoPublicKey = com.day.cq.dam.commons.util.DynamicMediaServicesConfigUtil.getPublicKey(resource.getResourceResolver());
	        if (videoPublicKey != null) {
	            return videoProxyURL + "public/" + videoPublicKey;
	        }
	    }
	}
    
	return "";
}

/**
 * @return private video server URL
 */
function getPrivateVideoServerURL() {
    var videoProxyURL = getVideoProxyURL();
    if (videoProxyURL != null && videoProxyURL != "") {
        //get video registration ID for temporary token
        var videoRegistrationId = com.day.cq.dam.commons.util.DynamicMediaServicesConfigUtil.getRegistrationId(resource.getResourceResolver());
        if (videoRegistrationId != null && videoRegistrationId.contains("|")){
            videoRegistrationId = videoRegistrationId.substring(0, videoRegistrationId.indexOf("|"));
        }
        if (videoRegistrationId != null) {
            return videoProxyURL + "private/" + videoRegistrationId;
        }
    }
	return "";
}

/**
 * @return publish image server URL
 */
function getPublishImageServerURL(assetPath, isRemote) {
	
	var productionImageServerUrl = "";
	var assetResource = resource.getResourceResolver().getResource("/"+assetPath);
	
	if(isRemote && assetResource) {
		// get publish server
        return sling.getService(com.day.cq.dam.api.s7dam.utils.PublishUtils).externalizeImageDeliveryAsset(assetResource, "");
	}
	else {
		var defImageServerUrl = request.contextPath + "/is/image/"; //default embed IS
	    var publishUtils = sling.getService(com.day.cq.dam.api.s7dam.utils.PublishUtils);
	    productionImageServerUrl = publishUtils.externalizeImageDeliveryUrl(resource, defImageServerUrl);
	}
    
    return productionImageServerUrl;
}

function getImageServerURL(isWCMDisabled, assetPath, isRemote){
    var imageServerURL = "";
    if (isWCMDisabled) {
		imageServerURL = properties['imageserverurl'];
    }
    else {
        imageServerURL = request.contextPath + "/is/image/";
        // get publish server
        var assetResource = resource.getResourceResolver().getResource(assetPath);
        if(isRemote && assetResource) {
        	return sling.getService(com.day.cq.dam.api.s7dam.utils.PublishUtils).externalizeImageDeliveryAsset(assetResource, "");
        }
    }
    return imageServerURL;
}

/**
 * Check viewer preset type matching asset type
 * @param assetToViewerList list for conversion between asset to viewer
 * @param assetType
 * @param viewerPreset viewer preset string
 * @return true if the viewer preset can be used for the current asset
 */
function isMatchedViewerPreset(assetToViewerList, assetType, viewerPreset){
    if (!assetType || !assetToViewerList[assetType]) {
        return true;
    }
    var presetType = getViewerPresetType(viewerPreset);
    if (typeof assetToViewerList[assetType][presetType] != 'undefined') {
        return true;
    }
	return false;
}

/**
 * Get viewer preset type
 * @param viewerPreset original viewer preset string
 * @return viewer preset type
 */
function getViewerPresetType(viewerPreset) {
    var presetParts = [],
        presetType = "none";
    if (viewerPreset) {
        presetParts = viewerPreset.split('|');
    }
    if (presetParts.length > 1){
		presetType = presetParts[1].toLowerCase();
    }
	return presetType;
}

/**
 * Is the viewer preset a custom preset?
 * @param viewerPreset viewer preset string
 * @return true if the preset is custom
 */
function isCustomPreset(viewerPreset){
    var presetParts = [];
    if (!viewerPreset) {
        return false;
    }
    else {
    	presetParts = viewerPreset.split('|');
        if (presetParts.length > 2) {
            if (presetParts[2] == "") {
                return false;
            }
            else {
                return (presetParts[2] == "true");
            }
        }
        else {
            return false;
        }
    }
}