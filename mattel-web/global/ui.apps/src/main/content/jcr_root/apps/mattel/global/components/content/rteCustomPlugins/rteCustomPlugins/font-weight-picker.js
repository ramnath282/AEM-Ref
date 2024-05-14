(function($, CUI){
    var GROUP = "ecm-font-weight",
        FONT_WEIGHT_PICKER_FEATURE = "fontWeightPicker",
        TCP_DIALOG = "eaemTouchUIFontWeightPickerDialog",
        PICKER_NAME_IN_POPOVER = "font-weight",
        REQUESTER = "requester",
        PICKER_URL = "/apps/mattel/ecomm/ag/components/content/exploreRteCustomPlugins/font-weight-popover/cq:dialog.html";

    addPluginToDefaultUISettings();

    addDialogTemplate();

    var EAEMFontWeightPickerDialog = new Class({
        extend: CUI.rte.ui.cui.AbstractDialog,

        toString: "EAEMFontWeightPickerDialog",

        initialize: function(config) {
            this.exec = config.execute;
        },

        getDataType: function() {
            return TCP_DIALOG;
        }
    });

    var TouchUIFontWeightPickerPlugin = new Class({
        toString: "TouchUIFontWeightPickerPlugin",

        extend: CUI.rte.plugins.Plugin,

        pickerUI: null,

        getFeatures: function() {
            return [ FONT_WEIGHT_PICKER_FEATURE ];
        },

        initializeUI: function(tbGenerator) {
            var plg = CUI.rte.plugins;

            if (!this.isFeatureEnabled(FONT_WEIGHT_PICKER_FEATURE)) {
                return;
            }

            this.pickerUI = tbGenerator.createElement(FONT_WEIGHT_PICKER_FEATURE, this, false, { title: "Font Weight Picker" });
            tbGenerator.addElement(GROUP, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

            var groupFeature = GROUP + "#" + FONT_WEIGHT_PICKER_FEATURE;
            tbGenerator.registerIcon(groupFeature, "textSize");

        },

        execute: function (id, value, envOptions) {
            if(!isValidSelection()){
                return;
            }

            var context = envOptions.editContext,
                selection = CUI.rte.Selection.createProcessingSelection(context),
                ek = this.editorKernel,
                startNode = selection.startNode;

            if ( (selection.startOffset === startNode.length) && (startNode != selection.endNode)) {
                startNode = startNode.nextSibling;
            }

            var tag = CUI.rte.Common.getTagInPath(context, startNode, "span"), plugin = this, dialog,
                fontweight = $(tag).css("font-weight"),
                dm = ek.getDialogManager(),
                $container = CUI.rte.UIUtils.getUIContainer($(context.root)),
                propConfig = {
                    'parameters': {
                        'command': this.pluginId + '#' + FONT_WEIGHT_PICKER_FEATURE
                    }
                };

            if(this.eaemFontWeightPickerDialog){
                dialog = this.eaemFontWeightPickerDialog;
            }else{
                dialog = new EAEMFontWeightPickerDialog();

                dialog.attach(propConfig, $container, this.editorKernel);

                dialog.$dialog.css("-webkit-transform", "scale(0.9)").css("-webkit-transform-origin", "0 0")
                    .css("-moz-transform", "scale(0.9)").css("-moz-transform-origin", "0px 0px");

                dialog.$dialog.find("iframe").attr("src", getPickerIFrameUrl(fontweight));

                this.eaemFontWeightPickerDialog = dialog;
            }

            dm.show(dialog);

            registerReceiveDataListener(receiveMessage);

            function isValidSelection(){
                var winSel = window.getSelection();
                return winSel && winSel.rangeCount == 1 && winSel.getRangeAt(0).toString().length > 0;
            }

            function getPickerIFrameUrl(fontweight){
                var url = PICKER_URL + "?" + REQUESTER + "=" + GROUP;

               if(!_.isEmpty(fontweight)){
                   url = url + "&" + PICKER_NAME_IN_POPOVER + "=" + fontweight;
                }

                return url;
            }

            function removeReceiveDataListener(handler) {
                if (window.removeEventListener) {
                    window.removeEventListener("message", handler);
                } else if (window.detachEvent) {
                    window.detachEvent("onmessage", handler);
                }
            }

            function registerReceiveDataListener(handler) {
                if (window.addEventListener) {
                    window.addEventListener("message", handler, false);
                } else if (window.attachEvent) {
                    window.attachEvent("onmessage", handler);
                }
            }

            function receiveMessage(event) {
                if (_.isEmpty(event.data)) {
                    return;
                }

                var message = JSON.parse(event.data),
                    action;

                if (!message || message.sender !== GROUP) {
                    return;
                }

                action = message.action;

                if (action === "submit") {
                    if (!_.isEmpty(message.data)) {
                        ek.relayCmd(id, message.data);
                    }
                }else if(action === "remove"){
                    ek.relayCmd(id);
                }else if(action === "cancel"){
                    plugin.eaemFontWeightPickerDialog = null;
                }

                dialog.hide();

                removeReceiveDataListener(receiveMessage);
            }
        },

        //to mark the icon selected/deselected
        updateState: function(selDef) {
            var hasUC = this.editorKernel.queryState(FONT_WEIGHT_PICKER_FEATURE, selDef);

            if (this.pickerUI != null) {
                this.pickerUI.setSelected(hasUC);
            }
        }
    });

    CUI.rte.plugins.PluginRegistry.register(GROUP,TouchUIFontWeightPickerPlugin);

    var TouchUIFontWeightPickerCmd = new Class({
        toString: "TouchUIFontWeightPickerCmd",

        extend: CUI.rte.commands.Command,

        isCommand: function(cmdStr) {
            return (cmdStr.toLowerCase() == FONT_WEIGHT_PICKER_FEATURE);
        },

        getProcessingOptions: function() {
            var cmd = CUI.rte.commands.Command;
            return cmd.PO_SELECTION | cmd.PO_BOOKMARK | cmd.PO_NODELIST;
        },

        _getTagObject: function(fontweight) {
            return {
                "tag": "span",
                "attributes": {
                    "style" : "font-weight: " + fontweight
                }
            };
        },

        execute: function (execDef) {
            var fontweight = execDef.value ? execDef.value[PICKER_NAME_IN_POPOVER] : undefined,
                selection = execDef.selection,
                nodeList = execDef.nodeList;

            if (!selection || !nodeList) {
                return;
            }

            var common = CUI.rte.Common,
                context = execDef.editContext,
                tagObj = this._getTagObject(fontweight);

            //if no fontweight value passed, assume delete and remove fontweight
            if(_.isEmpty(fontweight)){
                //nodeList.removeNodesByTag(execDef.editContext, tagObj.tag, undefined, true);
                return;
            }

            var tags = common.getTagInPath(context, selection.startNode, tagObj.tag);


            //remove existing fontweight before adding new fontweight
           /* if (tags != null) {
                nodeList.removeNodesByTag(execDef.editContext, tagObj.tag, undefined, true);
            }*/

            nodeList.surround(execDef.editContext, tagObj.tag, tagObj.attributes);

            if (tags != null && tags.innerHTML != tags.innerText) {
                	if(tags.attributes[0].nodeValue.split(":")[0] == "font-weight"){
                		tags.outerHTML = tags.innerHTML;
                	}
            }


        }
    });

    CUI.rte.commands.CommandRegistry.register(FONT_WEIGHT_PICKER_FEATURE, TouchUIFontWeightPickerCmd);

    function addPluginToDefaultUISettings(){
        var toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.inline.toolbar;
        toolbar.splice(3, 0, GROUP + "#" + FONT_WEIGHT_PICKER_FEATURE);

        toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.fullscreen.toolbar;
        toolbar.splice(3, 0, GROUP + "#" + FONT_WEIGHT_PICKER_FEATURE);
    }

    function addDialogTemplate(){
        var url = PICKER_URL + "?" + REQUESTER + "=" + GROUP;

        var html = "<iframe width='600px' height='500px' frameBorder='0' src='" + url + "'></iframe>";

        if(_.isUndefined(CUI.rte.Templates)){
            CUI.rte.Templates = {};
        }

        if(_.isUndefined(CUI.rte.templates)){
            CUI.rte.templates = {};
        }

        CUI.rte.templates['dlg-' + TCP_DIALOG] = CUI.rte.Templates['dlg-' + TCP_DIALOG] = Handlebars.compile(html);
    }
}(jQuery, window.CUI));

(function($, $document){
    var SENDER = "ecm-font-weight",
        REQUESTER = "requester",
        FONTWEIGHT = "font-weight",
        ADD_FONT_WEIGHT_BUT = "#EAEM_CP_ADD_FONT_WEIGHT",
        REMOVE_FONT_WEIGHT_BUT = "#EAEM_CP_REMOVE_FONT_WEIGHT";

    if(queryParameters()[REQUESTER] !== SENDER ){
        return;
    }

    $(function(){
        _.defer(stylePopoverIframe);
    });

   function queryParameters() {
        var result = {}, param,
            params = document.location.search.split(/\?|\&/);

        params.forEach( function(it) {
            if (_.isEmpty(it)) {
                return;
            }

            param = it.split("=");
            result[param[0]] = param[1];
        });

        return result;
    }

    function stylePopoverIframe(){
            $dialog = $("coral-dialog");

        if(_.isEmpty($dialog)){
            return;
        }

        $dialog.css("overflow", "hidden").css("background-color", "#fff");

        $dialog[0].open = true;

        var $addFontWeight = $dialog.find(ADD_FONT_WEIGHT_BUT),
            $removeFontWeight = $dialog.find(REMOVE_FONT_WEIGHT_BUT),
            fontweight = queryParameters()[FONTWEIGHT],
           // $fontweightPicker = $document.find("coral-colorinput");
           fontweight = decodeURIComponent(fontweight);
       /* if(!_.isEmpty(fontweight)){
            fontweight = decodeURIComponent(fontweight);

            if(fontweight.indexOf("rgb") == 0){
                fontweight = CUI.util.fontweight.RGBAToHex(fontweight);
            }

            $fontweightPicker[0].value = fontweight;
        }*/

        $inputField = $(ADD_FONT_WEIGHT_BUT).parent().find(".coral-Textfield");

        if(!_.isEmpty(queryParameters()[FONTWEIGHT])){
            $inputField.val(queryParameters()[FONTWEIGHT]);
        }

        adjustHeader($dialog);

        //$fontweightPicker.css("margin-bottom", "285px");

        $(ADD_FONT_WEIGHT_BUT).css("margin-left", "220px");

        $addFontWeight.click(sendDataMessage);

        $removeFontWeight.click(sendRemoveMessage);
    }

    function adjustHeader($dialog){
        var $header = $dialog.css("background-color", "#fff").find(".coral3-Dialog-header");

        $header.find(".cq-dialog-submit").remove();

        $header.find(".cq-dialog-cancel").click(function(event){
            event.preventDefault();

            $dialog.remove();

            sendCancelMessage();
        });
    }

    function sendCancelMessage(){
        var message = {
            sender: SENDER,
            action: "cancel"
        };

        parent.postMessage(JSON.stringify(message), "*");
    }

    function sendRemoveMessage(){
        var message = {
            sender: SENDER,
            action: "remove"
        };

        parent.postMessage(JSON.stringify(message), "*");
    }

    function sendDataMessage(){
        var message = {
            sender: SENDER,
            action: "submit",
            data: {}
        }, $dialog, fontweight;

        $dialog = $(".cq-dialog");

        fontweight = $dialog.find("[name='./" + FONTWEIGHT + "']").val();

        /*if(fontweight && fontweight.indexOf("rgb") >= 0){
            fontweight = CUI.util.fontweight.RGBAToHex(fontweight);
        }*/

        message.data[FONTWEIGHT] = fontweight;

        parent.postMessage(JSON.stringify(message), "*");
    }
})(jQuery, jQuery(document));
