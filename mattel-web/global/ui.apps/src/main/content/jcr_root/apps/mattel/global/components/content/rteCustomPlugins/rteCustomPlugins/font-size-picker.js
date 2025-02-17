(function($, CUI){
    var GROUP = "ecm-font-size",
        FONT_SIZE_PICKER_FEATURE = "fontSizePicker",
        TCP_DIALOG = "eaemTouchUIFontSizePickerDialog",
        PICKER_NAME_IN_POPOVER = "font-size",
        REQUESTER = "requester",
        PICKER_URL = "/apps/mattel/ecomm/ag/components/content/exploreRteCustomPlugins/font-size-popover/cq:dialog.html";

    addPluginToDefaultUISettings();

    addDialogTemplate();

    var EAEMFontSizePickerDialog = new Class({
        extend: CUI.rte.ui.cui.AbstractDialog,

        toString: "EAEMFontSizePickerDialog",

        initialize: function(config) {
            this.exec = config.execute;
        },

        getDataType: function() {
            return TCP_DIALOG;
        }
    });

    var TouchUIFontSizePickerPlugin = new Class({
        toString: "TouchUIFontSizePickerPlugin",

        extend: CUI.rte.plugins.Plugin,

        pickerUI: null,

        getFeatures: function() {
            return [ FONT_SIZE_PICKER_FEATURE ];
        },

        initializeUI: function(tbGenerator) {
            var plg = CUI.rte.plugins;

            if (!this.isFeatureEnabled(FONT_SIZE_PICKER_FEATURE)) {
                return;
            }

            this.pickerUI = tbGenerator.createElement(FONT_SIZE_PICKER_FEATURE, this, false, { title: "Font Size Picker" });
            tbGenerator.addElement(GROUP, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

            var groupFeature = GROUP + "#" + FONT_SIZE_PICKER_FEATURE;
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
                fontsize = $(tag).css("font-size"),
                dm = ek.getDialogManager(),
                $container = CUI.rte.UIUtils.getUIContainer($(context.root)),
                propConfig = {
                    'parameters': {
                        'command': this.pluginId + '#' + FONT_SIZE_PICKER_FEATURE
                    }
                };

            if(this.eaemFontSizePickerDialog){
                dialog = this.eaemFontSizePickerDialog;
            }else{
                dialog = new EAEMFontSizePickerDialog();

                dialog.attach(propConfig, $container, this.editorKernel);

                dialog.$dialog.css("-webkit-transform", "scale(0.9)").css("-webkit-transform-origin", "0 0")
                    .css("-moz-transform", "scale(0.9)").css("-moz-transform-origin", "0px 0px");

                dialog.$dialog.find("iframe").attr("src", getPickerIFrameUrl(fontsize));

                this.eaemFontSizePickerDialog = dialog;
            }

            dm.show(dialog);

            registerReceiveDataListener(receiveMessage);

            function isValidSelection(){
                var winSel = window.getSelection();
                return winSel && winSel.rangeCount == 1 && winSel.getRangeAt(0).toString().length > 0;
            }

            function getPickerIFrameUrl(fontsize){
                var url = PICKER_URL + "?" + REQUESTER + "=" + GROUP;

                if(!_.isEmpty(fontsize)){
                    url = url + "&" + PICKER_NAME_IN_POPOVER + "=" + fontsize;
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
                    plugin.eaemFontSizePickerDialog = null;
                }

                dialog.hide();

                removeReceiveDataListener(receiveMessage);
            }
        },

        //to mark the icon selected/deselected
        updateState: function(selDef) {
            var hasUC = this.editorKernel.queryState(FONT_SIZE_PICKER_FEATURE, selDef);

            if (this.pickerUI != null) {
                this.pickerUI.setSelected(hasUC);
            }
        }
    });

    CUI.rte.plugins.PluginRegistry.register(GROUP,TouchUIFontSizePickerPlugin);

    var TouchUIFontSizePickerCmd = new Class({
        toString: "TouchUIFontSizePickerCmd",

        extend: CUI.rte.commands.Command,

        isCommand: function(cmdStr) {
            return (cmdStr.toLowerCase() == FONT_SIZE_PICKER_FEATURE);
        },

        getProcessingOptions: function() {
            var cmd = CUI.rte.commands.Command;
            return cmd.PO_SELECTION | cmd.PO_BOOKMARK | cmd.PO_NODELIST;
        },

        _getTagObject: function(fontsize) {
            return {
                "tag": "span",
                "attributes": {
                    "style" : "font-size: " + fontsize+"px"
                }
            };
        },

        execute: function (execDef) {
            var fontsize = execDef.value ? execDef.value[PICKER_NAME_IN_POPOVER] : undefined,
                selection = execDef.selection,
                nodeList = execDef.nodeList;

            if (!selection || !nodeList) {
                return;
            }

            var common = CUI.rte.Common,
                context = execDef.editContext,
                tagObj = this._getTagObject(fontsize);

            //if no fontsize value passed, assume delete and remove fontsize
            if(_.isEmpty(fontsize)){
                //nodeList.removeNodesByTag(execDef.editContext, tagObj.tag, undefined, true);
                return;
            }

            var tags = common.getTagInPath(context, selection.startNode, tagObj.tag);


            //remove existing fontsize before adding new fontsize
           /* if (tags != null) {
                nodeList.removeNodesByTag(execDef.editContext, tagObj.tag, undefined, true);
            }*/

            nodeList.surround(execDef.editContext, tagObj.tag, tagObj.attributes);

            if (tags != null && tags.innerHTML != tags.innerText) {
                	if(tags.attributes[0].nodeValue.split(":")[0] == "font-size"){
                		tags.outerHTML = tags.innerHTML;
                	}
            }


        }
    });

    CUI.rte.commands.CommandRegistry.register(FONT_SIZE_PICKER_FEATURE, TouchUIFontSizePickerCmd);

    function addPluginToDefaultUISettings(){
        var toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.inline.toolbar;
        toolbar.splice(3, 0, GROUP + "#" + FONT_SIZE_PICKER_FEATURE);

        toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.fullscreen.toolbar;
        toolbar.splice(3, 0, GROUP + "#" + FONT_SIZE_PICKER_FEATURE);
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
    var SENDER = "ecm-font-size",
        REQUESTER = "requester",
        FONTSIZE = "font-size",
        ADD_FONT_SIZE_BUT = "#EAEM_CP_ADD_FONT_SIZE",
        REMOVE_FONT_SIZE_BUT = "#EAEM_CP_REMOVE_FONT_SIZE";

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

       var $addFontSize = $dialog.find(ADD_FONT_SIZE_BUT),
           $removeFontSize = $dialog.find(REMOVE_FONT_SIZE_BUT),
           fontsize = queryParameters()[FONTSIZE],
            //$fontsizePicker = $document.find("coral-colorinput");
             $inputField = $addFontSize.parent().find(".coral-Textfield");
              fontsize = decodeURIComponent(fontsize);
           /*  if(!_.isEmpty(fontsize)){
                fontsize = decodeURIComponent(fontsize);

                  if(fontsize.indexOf("rgb") == 0){
                    fontsize = CUI.util.fontsize.RGBAToHex(fontsize);
               }

                  $(".coral-Form-fieldwrapper input").val(fontsize);
              }*/
              if(!_.isEmpty(queryParameters()[FONTSIZE])){
                   $inputField.val(queryParameters()[FONTSIZE].substr(0,queryParameters()[FONTSIZE].length-2));
              }

        adjustHeader($dialog);

        //$fontsizePicker.css("margin-bottom", "285px");

        $(ADD_FONT_SIZE_BUT).css("margin-left", "220px");

        $addFontSize.click(sendDataMessage);

        $removeFontSize.click(sendRemoveMessage);
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
        }, $dialog, fontsize;

        $dialog = $(".cq-dialog");

        fontsize = $dialog.find("[name='./" + FONTSIZE + "']").val();

        /*if(fontsize && fontsize.indexOf("rgb") >= 0){
            fontsize = CUI.util.fontsize.RGBAToHex(fontsize);
        }*/

        message.data[FONTSIZE] = fontsize;

        parent.postMessage(JSON.stringify(message), "*");
    }
})(jQuery, jQuery(document));
