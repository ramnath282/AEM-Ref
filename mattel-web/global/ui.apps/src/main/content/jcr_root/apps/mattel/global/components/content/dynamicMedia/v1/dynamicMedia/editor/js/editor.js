/*
 * ADOBE CONFIDENTIAL
 *
 * Copyright 2016 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

(function ($, document) {
    "use strict";

    var VIEWER_PRESET = '#viewerPreset',
        IMAGE_PRESET = '#imagePreset',
        ASSET_TYPE = '#s7AssetType',
        URL_MOD = '#s7URLModifiers',
        BREAKPOINT = '#s7Breakpoints',
        PRESET_TYPE = '#s7PresetType',
        VIEWER_MODIFIERS = '#s7ViewerModifiers',
        LINK_URL = '#dmLinkUrl',
        LINK_TARGET = '#dmLinkTarget',
        LINK_ALT = '#dmAltText';

    var EMPTY_VP = "||",
        EMPTY_IP = "",
        IMAGE_TYPE = "Image";

    var PRE63_IP_ROOT = "/etc/dam/imageserver/macros",
        IP_ROOT = "/conf/global/settings/dam/dm/presets/macros",
        CHILD_SELECTOR = ".children.3.json";


	$(document).on("dialog-ready", function() {

		//clear event listener
        $(PRESET_TYPE).off('change', switchField);
        $(IMAGE_PRESET).off('change', setupBreakpoint);

        //register the new one
        $(PRESET_TYPE).on('change', switchField);
        $(IMAGE_PRESET).on('change', setupBreakpoint);

        Coral.commons.ready($(PRESET_TYPE).get(0), function() {
            initFields();
        });
    });

    /**
     * Initialize field setup
     * This function checks the asset type and viewer preset, image preset and breakpoint data to validate the preset
     * type and override if necessary.
     */
    function initFields() {
        // get viewer preset or image preset selections
        var vpVal = $(VIEWER_PRESET).val(),
            ipVal = $(IMAGE_PRESET).val(),
            bpVal = $(BREAKPOINT).val(),
            presetType = getSelectedRadio($(PRESET_TYPE), "image");
        bpVal = bpVal ? bpVal.trim() : bpVal;

        // hide image preset for non-image asset
        if (!isImage()) {
            // only viewer preset applies to non-image asset
            $(IMAGE_PRESET).parent().hide();
            $(PRESET_TYPE).parent().hide();
            $(URL_MOD).parent().hide();
            $(BREAKPOINT).parent().hide();
            setupAdvanceTab(false);
            selectRadio($(PRESET_TYPE), "smartCrop");
        }
        else {
            // for image asset, all image asset, viewer preset and smart crop apply

            // if viewer preset has been set, set preset type to "viewer"
            if (vpVal && vpVal != EMPTY_VP) {
                setupAdvanceTab(false);
                setupField("smartCrop");
                selectRadio($(PRESET_TYPE), "viewer");
            }
            else {
                setupAdvanceTab(true);

                // if image preset or breakpoint has been set, set preset type to "image"
                // validate any presetType and assume "image" for unrecognized value
                if ((ipVal && ipVal != EMPTY_IP) || bpVal || presetType != "smartCrop") {
                    presetType = "image";
                }

                setupField(presetType);
                selectRadio($(PRESET_TYPE), presetType);
            }
        }

    }

    /**
     * Switch field setup
     * @param e event
     */
    function switchField(e) {
        setupField($(e.target).val());
    }

    /**
     * Setup field based on field name
     * @param fieldName
     */
    function setupField(fieldName) {
        if (fieldName == "image") {
            $(VIEWER_PRESET).parent().hide();
            $(IMAGE_PRESET).parent().show();
            $(URL_MOD).parent().show();
            $(BREAKPOINT).parent().show();
            $(VIEWER_MODIFIERS).parent().hide();
            resetSelectField($(VIEWER_PRESET), EMPTY_IP);
        } else if (fieldName == "smartCrop") {
            $(VIEWER_PRESET).parent().hide();
            $(IMAGE_PRESET).parent().hide();
            $(URL_MOD).parent().show();
            $(BREAKPOINT).parent().hide();
            $(VIEWER_MODIFIERS).parent().hide();
            resetSelectField($(VIEWER_PRESET), EMPTY_IP);
            resetSelectField($(IMAGE_PRESET), EMPTY_VP);
        } else {
            $(VIEWER_PRESET).parent().show();
            $(IMAGE_PRESET).parent().hide();
            $(URL_MOD).parent().hide();
            $(BREAKPOINT).parent().hide();
            $(VIEWER_MODIFIERS).parent().show();
            resetSelectField($(IMAGE_PRESET), EMPTY_VP);
        }
    }

    /**
     * Setup breakpoint to show only when the image preset contain size
     */
    function setupBreakpoint(){
        checkSize($(IMAGE_PRESET), function(hadSize){
            if (hadSize) {            
            	$(BREAKPOINT).parent().hide();
                //reset field to blank when image preset with size is selected.
                $(BREAKPOINT).val("");
        	}
        	else {
            	$(BREAKPOINT).parent().show();
        	}
        });
    }

    /**
     * Setup Link related fields in advance tab
     * @param {Boolean} shown true to show | false to hide
     */
    function setupAdvanceTab(shown){
        if (shown) {
            $(LINK_URL).parent().show();
            $(LINK_TARGET).parent().show();
            $(LINK_ALT).parent().show();
        }
        else {
            $(LINK_URL).parent().hide();
            $(LINK_TARGET).parent().hide();
            $(LINK_ALT).parent().hide();
        }
    }

    /**
     * Select radio option helper
     * @param component
     * @param val
     */
    function selectRadio(component, val) {
		var radioComp = component.find('[type="radio"]');
        radioComp.each( function(){
        	$(this).prop('checked', ($(this).val() == val));
        });
    }

    /**
     * Get selected radio option helper
     * @param component The radio option component
     * @param defVal Default value if none is selected
     * @returns {String} Value of the selected radio option
     */
    function getSelectedRadio(component, defVal) {
        var radioComp = component.find('[type="radio"]');
        var val = defVal; // = radioComp[0].val(); // default to first selection

        radioComp.each( function(){
            if ($(this).prop('checked')) {
                val = $(this).val();
            }
        });

        return val;
    }

    /**
     * Reset selection field
     * @param field
     * @param defVal
     */
    function resetSelectField(field, defVal) {
        field.find('select').val(defVal);
        field.find('button').find('span').html('NONE');
    }

    /**
     * Check whether the asset is image
     * @returns {boolean} True for image
     */
	function isImage(){
        return ($(ASSET_TYPE).val() == 'Image')
    }

    /**
     * Check whether the preset has size and issue callback
     * @param field preset field
     */
    function checkSize(field, callback) {
        var selectedPreset = field.val();
        if (selectedPreset == "") {
            //no preset so do not show breakpoint
            callback(true); 
        }
        else {
            //since we don't have full path to the image preset, we have to construct it to fetch size data
            var presetPath = IP_ROOT + "/" + selectedPreset + CHILD_SELECTOR;
            //look at conf first for image preset 
            $.get(presetPath, function(resp) { 
                if (resp.length > 0) {
                    if (resp[0]['hei'] && resp[0]['wid']) {
                        //with size - don't show breakpoint
                        callback(true);
                    }
                    else {
                        //without size - show breakpoint
                        callback(false);
                    }
                }
                else {
                    //look at /etc for pre63 location
                    presetPath = PRE63_IP_ROOT  + "/" + selectedPreset + CHILD_SELECTOR;
                    $.get(presetPath, function(resp2){
                        if (resp2.length > 0 && resp2[0]['hei'] && resp2[0]['wid']) {
                            //with size - don't show breakpoint
                            callback(true);
                        }
                        else {
                            //without size - show breakpoint
                            callback(false);
                        }
                    });
                }

            });
        }
    }

})($, document);

