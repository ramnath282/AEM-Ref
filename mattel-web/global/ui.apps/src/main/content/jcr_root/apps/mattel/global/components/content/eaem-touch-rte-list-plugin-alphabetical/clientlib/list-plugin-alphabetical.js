(function($) {
    //"use strict";
    var _ = window._,
        Class = window.Class,
        GROUP = "ecm-alpha-one",
        ALPHA_LIST_FEATURE = "upperList",
        ORDERED_LIST_CMD = "extinsertorderedlist",
        CUI = window.CUI;

    addPluginToDefaultUISettings();

    var EAEMAlphaListCmd = new Class({
        toString: 'EAEMAlphaListCmd',
        extend: CUI.rte.commands.Command,
        isCommand: function(cmdStr) {
            var cmdStrLC = cmdStr.toLowerCase();
            return (cmdStrLC === 'extinsertorderedlist') || (cmdStrLC === 'extinsertunorderedlist');
        },

        getProcessingOptions: function() {
            var cmd = CUI.rte.commands.Command;
            return cmd.PO_SELECTION | cmd.PO_BOOKMARK | cmd.PO_NODELIST;
        },

        /**
         * Gets all list items of the current selection. Using this method will not include
         * items of a nested list if a nested list is completely covered in the selection.
         * @private
         */
        getListItems: function(execDef) {
            var context = execDef.editContext;
            return execDef.nodeList.getTags(context, [{
                'extMatcher': function(dom) {
                    return {
                        'isMatching': CUI.rte.Common.isTag(dom, 'li'),
                        'preventRecursionIfMatching': true
                    };
                }
            }], true, true);
        },

        /**
         * Gets all list items of the current selection. Using this method will include
         * items of a nested list as well.
         * @private
         */
        getAllListItems: function(execDef) {
            var context = execDef.editContext;
            var allItems = execDef.nodeList.getTags(context, [{
                'matcher': function(dom) {
                    return CUI.rte.Common.isTag(dom, 'li');
                }
            }], true, true);
            CUI.rte.ListUtils.postprocessSelectedItems(allItems);
            return allItems;
        },

        /**
         * Gets the defining list element for the specified node list. The defining list element
         * is the list element that belongs to the first node contained in the list.
         * @param {CUI.rte.EditContext} context The edit context
         * @param {CUI.rte.NodeList} nodeList The node list
         * @return {HTMLElement} The defining list DOM; null if the first node of the list
         *         is not part of a list
         */
        getDefiningListDom: function(context, nodeList) {
            var com = CUI.rte.Common;
            var determNode = nodeList.getFirstNode();
            if (determNode === null || determNode === undefined) {
                return null;
            }
            var determDom = determNode.dom;
            while (determDom) {
                if (com.isTag(determDom, com.LIST_TAGS)) {
                    return determDom;
                }
                determDom = com.getParentNode(context, determDom);
            }
            return null;
        },

        /**
         * Splits the specified array of list items into separate arrays of items for each
         * top-level list.
         * @private
         */
        splitToTopLevelLists: function(execDef, listItems) {
            var context = execDef.editContext;
            var itemsPerList = [];
            var topLevelLists = [];
            var itemCnt = listItems.length;
            for (var i = 0; i < itemCnt; i++) {
                var itemToCheck = listItems[i];
                var listDom = CUI.rte.ListUtils.getTopListForItem(context, itemToCheck.dom);
                var listIndex = CUI.rte.Common.arrayIndex(topLevelLists, listDom);
                if (listIndex < 0) {
                    topLevelLists.push(listDom);
                    itemsPerList.push([itemToCheck]);
                } else {
                    itemsPerList[listIndex].push(itemToCheck);
                }
            }
            return itemsPerList;
        },

        /**
         * Changes the list type of all selected list items, inserting additional tables
         * as required.
         * @private
         */
        changeItemsListType: function(execDef, listItems, listType) {
            var com = CUI.rte.Common;
            var context = execDef.editContext;
            var itemCnt = listItems.length;
            for (var i = 0; i < itemCnt; i++) {
                var item = listItems[i].dom;
                var list = item.parentNode;
                if (!com.isTag(list, listType)) {
                    // Change item ...
                    var prevSib = list.previousSibling;
                    var nextSib = list.nextSibling;
                    var isFirst = (com.getChildIndex(item) === 0);
                    var isLast = (com.getChildIndex(item) === (list.childNodes.length - 1));
                    if (isFirst && prevSib && com.isTag(prevSib, listType)) {
                        // move to preceding list of correct type
                        list.removeChild(item);
                        prevSib.appendChild(item);
                        if (list.childNodes.length === 0) {
                            list.parentNode.removeChild(list);
                        }
                    } else if (isLast && nextSib && com.isTag(nextSib, listType)) {
                        // move to succeeding list of correct type
                        list.removeChild(item);
                        com.insertBefore(nextSib, item, nextSib.firstChild);
                        if (list.childNodes.length === 0) {
                            list.parentNode.removeChild(list);
                        }
                    } else {
                        // we need a new list
                        var newList = context.createElement(listType);
                        if (item === list.firstChild) {
                            // create new list before existing list
                            com.insertBefore(list.parentNode, newList, list);
                        } else if (item === list.lastChild) {
                            // create new list after existing list
                            com.insertBefore(list.parentNode, newList, list.nextSibling);
                        } else {
                            // split list
                            var splitList = list.cloneNode(false);
                            com.insertBefore(list.parentNode, splitList, list);
                            com.insertBefore(list.parentNode, newList, list);
                            while (list.childNodes[0] !== item) {
                                var domToMove = list.childNodes[0];
                                list.removeChild(domToMove);
                                splitList.appendChild(domToMove);
                            }
                        }
                        list.removeChild(item);
                        newList.appendChild(item);
                        if (list.childNodes.length === 0) {
                            list.parentNode.removeChild(list);
                        }
                    }
                }
            }
        },

        /**
         * Creates a new list from all (allowed) block nodes defined in the selection.
         * @private
         */
        createListFromSelection: function(execDef, listType) {
            var nodeList = execDef.nodeList;
            var context = execDef.editContext;
            // todo distinguish between entire cell and parts of a cell
            var blockLists = nodeList.getEditBlocksByAuxRoots(context, true);
            var listCnt = blockLists.length;
            for (var l = 0; l < listCnt; l++) {
                CUI.rte.ListUtils.createList(context, blockLists[l], listType);
            }
        },

        /**
         * Removes items from a list by appending them to their respective parent item
         * (including a separating "br" line break).
         * @private
         */
        unlistItems: function(execDef, listItems, keepStructure) {
            if (!listItems) {
                listItems = this.getAllListItems(execDef);
            }
            var context = execDef.editContext;
            var itemCnt = listItems.length;
            var itemsDom = [];
            for (var i = 0; i < itemCnt; i++) {
                itemsDom.push(listItems[i].dom);
            }
            CUI.rte.ListUtils.unlistItems(context, itemsDom, keepStructure);
        },

        execute: function(execDef) {
            var com = CUI.rte.Common;
            var context = execDef.editContext;
            var nodeList = execDef.nodeList;
            var command = execDef.command;
            var value = execDef.value;
            var val = new String(execDef.value);

            if (execDef.value == "upper-alpha" || execDef.value == "lower-alpha" || execDef.value == "lower-roman" ||
                execDef.value == "upper-roman" || execDef.value == "circle" || execDef.value == "arrow-list-item" || execDef.value == "caret-list-item" || execDef.value == "angle-list-item" || execDef.value == "long-arrow-list-item") {
                execDef.value = false
            }

            var listType = null;
            switch (command.toLowerCase()) {
                case 'extinsertorderedlist':
                    listType = 'ol';
                    break;
                case 'extinsertunorderedlist':
                    listType = 'ul';
                    break;
            }
            if (listType) {
                var listItems;
                var refList = this.getDefiningListDom(context, nodeList);
                var oldValue = null;
                var oldClassValue = null;

                //console.log("********refList", refList);
                if ($(refList).length > 0) {
                    //console.log(val, $(refList).attr('style'));
                    if ($(refList).attr('style') && $(refList).attr('style') != "") {
                        oldValue = $(refList).attr('style').replace("list-style-type: ", "");
                    }
                    if ($(refList).attr('class') && $(refList).attr('class') != "") {
                        oldClassValue = $(refList).attr('class').replace("");
                    }
                }
                if (refList === null || refList === undefined) {
                    // creating new list (and joining existing lists)
                    this.createListFromSelection(execDef, listType);
                    var list = this.getDefiningListDom(execDef.editContext, execDef.nodeList);
                    //console.log("*****list", list);
                    if (val == "upper-alpha") {
                        $(list).attr("style", "list-style-type: upper-alpha");
                    } else if (val == "lower-alpha") {
                        $(list).attr("style", "list-style-type: lower-alpha");
                    } else if (val == "lower-roman") {
                        $(list).attr("style", "list-style-type: lower-roman");
                    } else if (val == "upper-roman") {
                        $(list).attr("style", "list-style-type: upper-roman");
                    } else if (val == "circle") {
                        $(list).attr("style", "list-style-type: circle");
                    } else if (val == "arrow-list-item") {
                        $(list).attr("class", "arrow-list-item");
                    } else if (val == "caret-list-item") {
                        $(list).attr("class", "caret-list-item");
                    } else if (val == "angle-list-item") {
                        $(list).attr("class", "angle-list-item");
                    } else if (val == "long-arrow-list-item") {
                        $(list).attr("class", "long-arrow-list-item");
                    }
                } else if (!com.isTag(refList, listType)) {
                    // change list type of selected items (or entire list)
                    listItems = this.getListItems(execDef);
                    //console.log("*****listItems", listItems);
                    this.changeItemsListType(execDef, listItems, listType);
                    var list = this.getDefiningListDom(execDef.editContext, execDef.nodeList);
                    //console.log("*****list", list);
                    if (val == "upper-alpha") {
                        $(list).attr("style", "list-style-type: upper-alpha");
                    } else if (val == "lower-alpha") {
                        $(list).attr("style", "list-style-type: lower-alpha");
                    } else if (val == "lower-roman") {
                        $(list).attr("style", "list-style-type: lower-roman");
                    } else if (val == "upper-roman") {
                        $(list).attr("style", "list-style-type: upper-roman");
                    } else if (val == "circle") {
                        $(list).attr("style", "list-style-type: circle");
                    } else if (val == "arrow-list-item") {
                        $(list).attr("class", "arrow-list-item");
                    } else if (val == "caret-list-item") {
                        $(list).attr("class", "caret-list-item");
                    } else if (val == "angle-list-item") {
                        $(list).attr("class", "angle-list-item");
                    } else if (val == "long-arrow-list-item") {
                        $(list).attr("class", "long-arrow-list-item");
                    }
                } else if (refList && (val == "upper-alpha" || val == "lower-alpha" || val == "lower-roman" || val == "upper-roman") && oldValue == null && listType == "ol") {
                    $(refList).attr('style', "list-style-type: " + val);
                } else if (refList && null != oldValue && null != val && val != oldValue) {
                    $(refList).attr('style', "list-style-type: " + val);
                }else if (refList && null != oldClassValue && null != val && val != oldClassValue) {
                    $(refList).attr('class', val);
                } else if (refList && (val=="circle" || val == "arrow-list-item" || val == "caret-list-item" || val == "angle-list-item" || val == "long-arrow-list-item") && oldClassValue == null && listType == "ul"){
                    if(val == "circle"){
                        $(refList).attr('style', "list-style-type: " + val);
                    } else {
                        $(refList).attr('class', val);
                     }
                } else {
                    // unlist all items of lead list
                    listItems = this.getAllListItems(execDef);
                    if (listItems.length > 0) {
                        var itemsByList = this.splitToTopLevelLists(execDef, listItems);
                        var listCnt = itemsByList.length;
                        for (var l = 0; l < listCnt; l++) {
                            listItems = itemsByList[l];
                            this.unlistItems(execDef, listItems, value === true);
                        }
                    }
                }
            }
        },

        queryState: function(selectionDef, cmd) {
            var com = CUI.rte.Common;
            var context = selectionDef.editContext;
            var nodeList = selectionDef.nodeList;
            var tagName;
            switch (cmd.toLowerCase()) {
                case 'extinsertorderedlist':
                    tagName = 'ol';
                    break;
                case 'extinsertunorderedlist':
                    tagName = 'ul';
                    break;
            }
            var definingList = this.getDefiningListDom(context, nodeList);
            return ((definingList !== null && definingList !== undefined) && com.isTag(definingList, tagName));
        }

    });


    CUI.rte.commands.CommandRegistry.register("_extendedlist", EAEMAlphaListCmd);


    var basePlugin = {
        toString: "EAEMAlphaListPlugin",

        extend: CUI.rte.plugins.Plugin,

        pickerUI: null,

        iconClass: "textLetteredUppercase",

        featureName: ALPHA_LIST_FEATURE,

        title: "Alphabetical Uppercase list...",

        listCommand: ORDERED_LIST_CMD,

        listValue: "upper-alpha",

        groupName: GROUP,

        getFeatures: function() {
            return [this.featureName];
        },

        initializeUI: function(tbGenerator) {
            var plg = CUI.rte.plugins;

            if (!this.isFeatureEnabled(this.featureName)) {
                return;
            }

            this.pickerUI = tbGenerator.createElement(this.featureName, this, false, {
                title: this.title
            });
            tbGenerator.addElement(this.groupName, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

            var groupFeature = this.groupName + "#" + this.featureName;
            tbGenerator.registerIcon(groupFeature, this.iconClass);
        },

        execute: function(id, value, envOptions) {
            if (!isValidSelection()) {
                return;
            }

            this.editorKernel.relayCmd(this.listCommand, this.listValue);
            //console.log(this.listCommand, this.listValue);

            function isValidSelection() {
                var winSel = window.getSelection();
                return winSel && (winSel.rangeCount == 1) && (winSel.getRangeAt(0).toString().length > 0);
            }
        },

        updateState: function(selDef) {
            var hasUC = this.editorKernel.queryState(this.featureName, selDef);

            if (this.pickerUI != null) {
                this.pickerUI.setSelected(hasUC);
            }
        }
    };

    var baseAlphaLowListPlugin = _.extend(_.clone(basePlugin), {
        toString: "EAEMAlphaLowListCmd",
        iconClass: "textLetteredLowercase",
        title: "Alphabetical Lowercase list...",
        listValue: "lower-alpha",
        groupName: "ecm-alpha-two",
        featureName: "lowList"
    });
    var baseRomanLowListPlugin = _.extend(_.clone(basePlugin), {
        toString: "EAEMRomanLowListCmd",
        iconClass: "textRomanLowercase",
        title: "Roman Lowercase list...",
        listValue: "lower-roman",
        groupName: "ecm-roman-two",
        featureName: "lowList"
    });
    var baseRomanUpperListPlugin = _.extend(_.clone(basePlugin), {
        toString: "EAEMRomanUpperListCmd",
        iconClass: "textRomanUppercase",
        title: "Roman Uppercase list...",
        listValue: "upper-roman",
        groupName: "ecm-roman-one",
        featureName: "upperList"
    });
    var baseCircleListPlugin = _.extend(_.clone(basePlugin), {
        toString: "EAEMCircleListCmd",
        iconClass: "opera",
        title: "Marker Circle list...",
        listCommand: "insertunorderedlist",
        groupName: "ecm-circle",
        listValue: "circle",
        featureName: "hollowList"
    });

    var EAEMAlphaListPlugin = new Class(basePlugin);
    var EAEMAlphaLowListPlugin = new Class(baseAlphaLowListPlugin);
    var EAEMRomanLowListPlugin = new Class(baseRomanLowListPlugin);
    var EAEMRomanUpperListPlugin = new Class(baseRomanUpperListPlugin);
    var EAEMCircleListPlugin = new Class(baseCircleListPlugin);

    function addPluginToDefaultUISettings() {
        var toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.inline.toolbar;
        toolbar.splice(3, 0, "ecm-alpha-one" + "#" + ALPHA_LIST_FEATURE);

        toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.fullscreen.toolbar;
        toolbar.splice(3, 0, "ecm-alpha-one" + "#" + ALPHA_LIST_FEATURE);
    }

    CUI.rte.plugins.PluginRegistry.register(GROUP, EAEMAlphaListPlugin);
    CUI.rte.plugins.PluginRegistry.register("ecm-alpha-two", EAEMAlphaLowListPlugin);
    CUI.rte.plugins.PluginRegistry.register("ecm-roman-one", EAEMRomanUpperListPlugin);
    CUI.rte.plugins.PluginRegistry.register("ecm-roman-two", EAEMRomanLowListPlugin);
    CUI.rte.plugins.PluginRegistry.register("ecm-circle", EAEMCircleListPlugin);


    var EAEMExtendedListPlugin = new Class({

        toString: 'ExtendedListPlugin',

        id: 'extlists',

        extend: CUI.rte.plugins.Plugin,

        /**
         * @private
         */
        orderedListUI: null,

        /**
         * @private
         */
        unorderedListUI: null,

        /**
         * @private
         */
        indentUI: null,

        /**
         * @private
         */
        outdentUI: null,


        list3: null,

        list4: null,

        list5: null,

        list6: null,

        list7: null,

        list8: null,

        list9: null,

        _init: function(editorKernel) {
            this.inherited(arguments);
            editorKernel.addPluginListener('beforekeydown', this.handleOnKey, this, this, false);
        },

        /**
         * This function creates new list entries (<li>) by pressing CRTL+ENTER
         * This workaround should be used if there is a block node within a list entry
         *
         * @param event
         */
        handleOnKey: function(event) {
            var range, rangeNode, parentNode, parentParentNode, newListNode;

            if (event.isEnter() && event.isCtrl()) {
                try {
                    if (window.getSelection) { // all browsers, except IE before version 9
                        range = CUI.rte.Selection.getLeadRange(event.editContext);
                    } else {
                        if (document.selection) { // Internet Explorer 6/7/8
                            range = document.selection.createRange();
                        }
                    }

                    rangeNode = range.commonAncestorContainer ? range.commonAncestorContainer :
                        range.parentElement ? range.parentElement() : range.item(0);
                    parentNode = rangeNode.parentNode ? rangeNode.parentNode : rangeNode.parentElement();

                    while (rangeNode !== parentNode && parentNode.tagName !== 'LI') {
                        rangeNode = rangeNode.parentNode ? rangeNode.parentNode : rangeNode.parentElement();
                        parentNode = rangeNode.parentNode ? rangeNode.parentNode : rangeNode.parentElement();
                    }

                    if (rangeNode.tagName && rangeNode.tagName !== 'LI' && parentNode.tagName === 'LI') {
                        parentParentNode = parentNode.parentNode ? parentNode.parentNode : parentNode.parentElement();

                        newListNode = event.editContext.doc.createElement('li');
                        newListNode.appendChild(rangeNode);

                        if (parentNode.nextSibling) {
                            parentParentNode.insertBefore(newListNode, parentNode.nextSibling);
                        } else {
                            parentParentNode.appendChild(newListNode);
                        }

                        CUI.rte.Selection.selectNode(event.editContext, newListNode, 1);
                    }
                } catch (err) {
                    // sometimes a node might be undefined
                }
            }
        },

        getFeatures: function() {
            return ['upperalpha', 'loweralpha', 'upperroman', 'lowerroman', 'arrowright', 'caretright', 'angleright', 'longarrowaltright', 'circle', 'indent', 'outdent'];
        },

        initializeUI: function(tbGenerator) {
            var plg = CUI.rte.plugins;
            if (this.isFeatureEnabled('upperalpha')) {
                this.unorderedListUI = tbGenerator.createElement("upperalpha", this,
                    true, this.getTooltip('upperalpha'));
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.unorderedListUI,
                    10);
                var groupFeature = 'extlists' + "#" + "upperalpha";
                tbGenerator.registerIcon(groupFeature, "textLetteredUppercase");
            }
            if (this.isFeatureEnabled('loweralpha')) {
                this.orderedListUI = tbGenerator.createElement("loweralpha", this, true,
                    this.getTooltip('loweralpha'));
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.orderedListUI, 20);
                var groupFeature = 'extlists' + "#" + "loweralpha";
                tbGenerator.registerIcon(groupFeature, "textLetteredLowercase");
            }
            if (this.isFeatureEnabled('upperroman')) {
                this.list3 = tbGenerator.createElement("upperroman", this, true,
                    this.getTooltip('upperroman'));
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list3, 30);
                var groupFeature = 'extlists' + "#" + "upperroman";
                tbGenerator.registerIcon(groupFeature, "textRomanUppercase");
            }
            if (this.isFeatureEnabled('lowerroman')) {
                this.list4 = tbGenerator.createElement("lowerroman", this, true,
                    this.getTooltip('lowerroman'));
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list4, 40);
                var groupFeature = 'extlists' + "#" + "lowerroman";
                tbGenerator.registerIcon(groupFeature, "textRomanLowercase");
            }
            if (this.isFeatureEnabled('circle')) {
                this.list5 = tbGenerator.createElement("circle", this, true,
                    this.getTooltip('circle'));
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list5, 50);
                var groupFeature = 'extlists' + "#" + "circle";
                tbGenerator.registerIcon(groupFeature, "opera", "XS");
            }
            if (this.isFeatureEnabled('arrowright')) {
                this.list6 = tbGenerator.createElement('arrowright', this, false,
                    this.getTooltip('arrowright'));
                var groupFeature = 'extlists' + "#" + "arrowright";
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list6, 60);
                tbGenerator.registerIcon(groupFeature, "arrowRight");
            }
            if (this.isFeatureEnabled('caretright')) {
                this.list7 = tbGenerator.createElement('caretright', this, false,
                    this.getTooltip('caretright'));
                var groupFeature = 'extlists' + "#" + "caretright";
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list7, 70);
                tbGenerator.registerIcon(groupFeature, "play");
            }
            if (this.isFeatureEnabled('angleright')) {
                this.list8 = tbGenerator.createElement('angleright', this, false,
                    this.getTooltip('angleright'));
                var groupFeature = 'extlists' + "#" + "angleright";
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list8, 80);
                tbGenerator.registerIcon(groupFeature, "chevronRight");
            }
            if (this.isFeatureEnabled('longarrowaltright')) {
                this.list9 = tbGenerator.createElement('longarrowaltright', this, false,
                    this.getTooltip('longarrowaltright'));
                var groupFeature = 'extlists' + "#" + "longarrowaltright";
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.list9, 90);
                tbGenerator.registerIcon(groupFeature, "chevronDoubleRight");
            }
            if (this.isFeatureEnabled('indent')) {
                this.indentUI = tbGenerator.createElement('indent', this, false,
                    this.getTooltip('indent'));
                var groupFeature = 'extlists' + "#" + "indent";
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.indentUI, 100);
                tbGenerator.registerIcon(groupFeature, "textIndentIncrease");
            }
            if (this.isFeatureEnabled('outdent')) {
                this.outdentUI = tbGenerator.createElement('outdent', this, false,
                    this.getTooltip('outdent'));
                var groupFeature = 'extlists' + "#" + "outdent";
                tbGenerator.addElement('extlists', plg.Plugin.SORT_LISTS, this.outdentUI, 110);
                tbGenerator.registerIcon(groupFeature, "textIndentDecrease");
            }
            try {
                tbGenerator.registerIcon('#extlists', "textLetteredUppercase");
            } catch (err) {
                console.error(err);
            }
        },

        notifyPluginConfig: function(pluginConfig) {
            pluginConfig = pluginConfig || {};
            CUI.rte.Utils.applyDefaults(pluginConfig, {
                'features': '*',
                'indentSize': 40,
                'keepStructureOnUnlist': false,
                'tooltips': {
                    'upperalpha': {
                        'title': "Alphabetical Uppercase list...",
                        'text': CUI.rte.Utils.i18n('plugins.list.ulText')
                    },
                    'loweralpha': {
                        'title': "Alphabetical Uppercase list...",
                        'text': CUI.rte.Utils.i18n('plugins.list.olText')
                    },
                    'upperroman': {
                        'title': "Roman Uppercase list...",
                        'text': CUI.rte.Utils.i18n('plugins.list.indentText')
                    },
                    'lowerroman': {
                        'title': "Roman Lowercase list...",
                        'text': CUI.rte.Utils.i18n('plugins.list.outdentText')
                    },
                    'circle': {
                        'title': "Unordered Circle list (Not Nested)",
                        'text': CUI.rte.Utils.i18n('plugins.list.outdentText')
                    },

                    'arrowright': {
                        'title': "Arrow Right List ..",
                        'text': CUI.rte.Utils.i18n('plugins.list.arrowrightText')
                    },
                    'caretright': {
                        'title': "Caret Right List ..",
                        'text': CUI.rte.Utils.i18n('plugins.list.caretrightText')
                    },
                    'angleright': {
                        'title': "Angle Right List ..",
                        'text': CUI.rte.Utils.i18n('plugins.list.anglerightText')
                    },
                    'longarrowaltright': {
                        'title': "Long Arrow Alt Right List ..",
                        'text': CUI.rte.Utils.i18n('plugins.list.longarrowaltrightText')
                    },
                    'indent': {
                        'title': CUI.rte.Utils.i18n('plugins.list.indentTitle'),
                        'text': CUI.rte.Utils.i18n('plugins.list.indentText')
                    },
                    'outdent': {
                        'title': CUI.rte.Utils.i18n('plugins.list.outdentTitle'),
                        'text': CUI.rte.Utils.i18n('plugins.list.outdentText')
                    },
                }
            });
            this.config = pluginConfig;
        },

        execute: function(id) {
            var value;
            if ((id === 'indent') || (id === 'outdent')) {
                value = this.config.indentSize;
                this.editorKernel.relayCmd(id, value);
            } else if (CUI.rte.Common.strStartsWith(id, 'insert')) {
                value = this.config.keepStructureOnUnlist;
                this.editorKernel.relayCmd(id, value);
            } else {
                //id = 'extinsert' + id + 'list';
                //value = this.config.keepStructureOnUnlist;

                if (id == "upperalpha") {
                    this.editorKernel.relayCmd("extinsertorderedlist", "upper-alpha");
                } else if (id == "loweralpha") {
                    this.editorKernel.relayCmd("extinsertorderedlist", "lower-alpha");
                } else if (id == "upperroman") {
                    this.editorKernel.relayCmd("extinsertorderedlist", "upper-roman");
                } else if (id == "lowerroman") {
                    this.editorKernel.relayCmd("extinsertorderedlist", "lower-roman");
                } else if (id == "circle") {
                    this.editorKernel.relayCmd("extinsertunorderedlist", "circle");
                } else if (id == "arrowright") {
                    this.editorKernel.relayCmd("extinsertunorderedlist", "arrow-list-item");
                } else if (id == "caretright") {
                    this.editorKernel.relayCmd("extinsertunorderedlist", "caret-list-item");
                } else if (id == "angleright") {
                    this.editorKernel.relayCmd("extinsertunorderedlist", "angle-list-item");
                } else if (id == "longarrowaltright") {
                    this.editorKernel.relayCmd("extinsertunorderedlist", "long-arrow-list-item");
                }
            }
            //console.log("#########id", id);

        },

        updateState: function(selDef) {
            var context = selDef.editContext;
            var state, isDisabled;
            if (this.orderedListUI) {
                state = this.editorKernel.queryState('upperalpha', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.orderedListUI.setSelected((state === true) || (state === null || state === undefined));
                this.orderedListUI.setDisabled(isDisabled);
            }
            if (this.unorderedListUI) {
                state = this.editorKernel.queryState('loweralpha', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.unorderedListUI.setSelected((state === true) || (state === null || state === undefined));
                this.unorderedListUI.setDisabled(isDisabled);
            }
            if (this.list3) {
                state = this.editorKernel.queryState('upperroman', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list3.setSelected((state === true) || (state === null || state === undefined));
                this.list3.setDisabled(isDisabled);
            }
            if (this.list4) {
                state = this.editorKernel.queryState('lowerroman', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list4.setSelected((state === true) || (state === null || state === undefined));
                this.list4.setDisabled(isDisabled);
            }
            if (this.list5) {
                state = this.editorKernel.queryState('circle', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list5.setSelected((state === true) || (state === null || state === undefined));
                this.list5.setDisabled(isDisabled);
            }
            if (this.list6) {
                state = this.editorKernel.queryState('arrowright', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list6.setSelected((state === true) || (state === null || state === undefined));
                this.list6.setDisabled(isDisabled);
            }
            if (this.list7) {
                state = this.editorKernel.queryState('caretright', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list7.setSelected((state === true) || (state === null || state === undefined));
                this.list7.setDisabled(isDisabled);
            }
            if (this.list8) {
                state = this.editorKernel.queryState('angleright', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list8.setSelected((state === true) || (state === null || state === undefined));
                this.list8.setDisabled(isDisabled);
            }
            if (this.list9) {
                state = this.editorKernel.queryState('longarrowaltright', selDef);
                isDisabled = (state === null || state === undefined) ||
                    (state === CUI.rte.commands.List.NO_LIST_AVAILABLE);
                this.list9.setSelected((state === true) || (state === null || state === undefined));
                this.list9.setDisabled(isDisabled);
            }
            if (this.outdentUI) {
                // outdent is only available if the current selection has some indent
                this.outdentUI.setDisabled(!this.editorKernel.queryState('indent', selDef));
            }
            if (this.indentUI) {
                // indent is basically always available - but not if the selection contains
                // "first items", which cannot be indented using reasonable HTML
                var isEnabled = true;
                var listItems = selDef.nodeList.getTags(context, [{
                    'matcher': function(dom) {
                        return CUI.rte.Common.isTag(dom, 'li');
                    }
                }], true, true);
                var itemCnt = listItems.length;
                for (var i = 0; i < itemCnt; i++) {
                    var itemDom = listItems[i].dom;
                    if (!itemDom.previousSibling) {
                        var parentItemDom = itemDom.parentNode.parentNode;
                        var isParentInSelection = false;
                        for (var c = 0; c < itemCnt; c++) {
                            if (listItems[c].dom === parentItemDom) {
                                isParentInSelection = true;
                                break;
                            }
                        }
                        if (!isParentInSelection) {
                            isEnabled = false;
                            break;
                        }
                    }
                }
                this.indentUI.setDisabled(!isEnabled);
            }
            if ($("button[data-action=\"#extlists\"]").length > 0) {
                if ($("button[data-action=\"#extlists\"]").attr("title") == "popover.trigger.plugins.Extlists") {
                    $("button[data-action=\"#extlists\"]").attr("title", "Advanced List");
                }
            }
        }
    });

    // register plugin
    CUI.rte.plugins.PluginRegistry.register('extlists', EAEMExtendedListPlugin);
})(jQuery);