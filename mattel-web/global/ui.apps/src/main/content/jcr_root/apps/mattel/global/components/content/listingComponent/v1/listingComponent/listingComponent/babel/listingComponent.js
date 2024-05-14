const config = {
    el: '.listingComponent',
    gridEl: '#listingComponentInfo',
    facetsEl: '#filterComponentInfo',
    isSingleSelectionFacet: $("#filterComponentInfo").data('selectionType') === 'radio',
    isFacetsON: $("#filterComponentInfo").data('facets') === 'on',
    gridCol: {
        desktop: 3,
        tablet: 3,
        tabletPortrait: 2,
        mobile: 2,
        mobilePortrait: 1,
    },
    clearAllStorage: false,
    storageName: {
        sandp: "listingGridData"
    },
    SAndPParams: {
        productName: $("#searchType").val(),
        initialLoadcount: parseInt($("#DefaultLoad").val()),
        productLoadcount: parseInt($("#ProductLimit").val()),
    },
    errorPageUrl: $("#siteErrorPage").val() || false,
    storageExpirationInMin: 15 || parseInt($("#sessionStorageTimeout").val() || 15) || 15,
    lazyScroll: false,
    lazyLoadEnabled: {
        desktop: true,
        tablet: true,
        mobile: false
    },
    routingPrefixName: $("#pageName").val() || 'news'
};

class listingComponent {
    constructor() {
        self = this;
        deviceName = getDeviceName();
        if ($(config.el).find('.col-100').length) {
            config.gridCol.desktop = 4;
            config.gridCol.tablet = 3;
        }
        this.deviceName = deviceName;
        this.bindingHelperFn();
        this.onLoadScrollCnt();
        this.clearAllStorage(config.clearAllStorage);
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.getSAndPData();
        this.bindingResize();
    }
    bindingEventsConfig() {
        let eventsArr = {
            [`click ${config.el} .view-all-btn`]: "seeMoreAction",
            [`change ${config.el} .products-list-item .filter-action`]: "filterClickAction"
        };
        return eventsArr;
    }
    bindingHelperFn() {
        handleBarsHelperInst.callRegisterHelper("forLoop");
        handleBarsHelperInst.callRegisterHelper('seoFormat');
        handleBarsHelperInst.checkIFConditions('ifEquals');
        handleBarsHelperInst.checkIFConditions('ifNotEquals');
        handleBarsHelperInst.checkIFConditions('checkIndexExist');
    }
    filterClickAction(ele) {
        const $ele = $(ele);
        const filterValues = $ele.data("action");
        const count = $ele.data("totalCount");
        $(".view-all-btn").addClass('hide');
        sAndPcalledFrom = filterValues ? "checkboxAction" : 'clearFilter';
        filteredParam = filterValues;
        if (config.isSingleSelectionFacet) {
            const seoUrl = self.toggleCategoryNameFromUrl(undefined, $ele.data("seoText"));
            seoUrl && history.pushState(null, null, seoUrl);
            $(config.el).find('#selected-value').html($(ele).siblings('label').html());
        }

        self.enableLoading(true);
        self.getSAndPData(
            count,
            filteredParam
        );
        if (!isDeepLinking && self.deviceName != "mobile") {
            $("html, body").animate({
                scrollTop: $(config.gridEl).offset().top - 100
            }, 500);
        }
    }
    toggleCategoryNameFromUrl(type, categoryName) {
        const domain = `${location.origin}`,
            pathName = location.pathname.split("/"),
            indexOfPrefix = pathName.indexOf(config.routingPrefixName);

        if (indexOfPrefix == -1) {
            console.log("routung prefix name not found");
            return;
        }

        if (type == "get") {
            isDeepLinking = true;
            return pathName[indexOfPrefix + 1] || undefined;
        }
        if (categoryName == pathName[indexOfPrefix + 1]) {
            return false;
        }
        pathName[indexOfPrefix + 1] = categoryName;
        const updatedPathName = pathName.join("/");
        return `${domain}${updatedPathName}${location.search}`;
    }
    seeMoreAction(ele, evt) {
        evt.preventDefault();
        let productLoadcount = config.SAndPParams.productLoadcount;
        let filterNames =
            filteredParam == undefined || filteredParam == null ?
            filteredParam :
            filteredParam.split(";");
        let $listEl = $(config.gridEl).find(
            "li:not(.rich-grid-skeleton)"
        );
        $.each(filterNames, (k, v) => {
            if (v.indexOf("count") != -1) {
                let paramValue = v.split("=");
                paramValue[1] = parseInt(productLoadcount) + parseInt($listEl.length);
                let finalParam = paramValue[0].concat("=", paramValue[1]);
                v = finalParam;
            }
            filterNames[k] = v;
        });
        filteredParam =
            filteredParam == undefined || filteredParam == null ?
            filteredParam :
            filterNames.join(";");
        self.getSAndPData(
            undefined,
            filteredParam,
            pageNo,
            productLoadcount,
            true
        );
    }
    scrollToTop() {
        const ele = $(config.gridEl)[0];
        const topVal = ele.scrollTop;
        if (ele.getBoundingClientRect().top < 0) {
            window.requestAnimationFrame(self.scrollToTop);
            window.scrollTo(0, topVal - topVal / 8);
        }
    }
    removeParameterFromURL(url, keyName) {
        const urlParts = url.split('?');
        if (urlParts.length >= 2) {
            // Get first part, and remove from array
            const urlBase = urlParts.shift();

            // Join it back up
            const queryString = urlParts.join('?');

            const prefix = encodeURIComponent(keyName) + '=';
            const parts = queryString.split(/[&;]/g);

            // Reverse iteration as may be destructive
            for (let i = parts.length; i-- > 0;) {
                // Idiom for string.startsWith
                if (parts[i].lastIndexOf(prefix, 0) !== -1) {
                    parts.splice(i, 1);
                }
            }

            url = urlBase + '?' + parts.join('&');
        }

        return url;
    }
    getSAndPParam(param, page, count) {
        if (sAndPcalledFrom == "checkboxAction") {
            // if (count) {
            //     return ``;
            // }
            return param;
        } else {
            return '?search=' + config.SAndPParams.productName + '&sort=pubDate&count=' + (count || config.SAndPParams.initialLoadcount) + '&page=' + (page || pageNo)
        }
    }
    getSAndPData(liCnt, queryParams, pageNo, count, isTriggeredFromSeeMore) {
        let $listEl = $(config.gridEl).find(
            "li:not(.rich-grid-skeleton)"
        );
        if (isTriggeredFromSeeMore) {
            pageNo = 1;
            count = parseInt(count) + parseInt($listEl.length);
        }
        liCnt = liCnt || 8;
        self.showPreviewList(liCnt, isTriggeredFromSeeMore);
        let errorMessage;
        const payload = apiData.getApiConfig("getListingData")["getSAndP"].apply({
            queryString: self.getSAndPParam(queryParams, pageNo, count)
        });
        let $checkedItems = self.getFilteredElem();
        const sessionData = self.getStorageData(config.storageName.sandp);
        let isStorageExpired =
            sessionData.expireTime && new Date(sessionData.expireTime);
        let checkInitialLoadCount = sessionData.countParam || 0;
        if (isStorageExpired > new Date() && sessionData && !isTriggeredFromSeeMore && !$checkedItems.length && (checkInitialLoadCount == config.SAndPParams.initialLoadcount)) {
            self.sAndPSuccessCB(sessionData, isTriggeredFromSeeMore);
            return;
        }
        sessionData && console.log("S&P Storage Refreshed..");

        let expirationDate = self.getExpirationdate();
        request(payload)
            .then(data => {
                try {
                    const response = typeof data == "string" ? JSON.parse(data) : data;
                    response.expireTime = expirationDate.toString();
                    response.countParam = config.SAndPParams.initialLoadcount;
                    !queryParams &&
                        (pageNo == undefined || pageNo < 1) &&
                        self.setStorageData(config.storageName.sandp, response);
                    self.sAndPSuccessCB(response, isTriggeredFromSeeMore);
                } catch (e) {
                    errorMessage = e.message;
                    self.enableLoading(false);
                    if (sAndPcalledFrom == "") {
                        self.errorCB();
                    }
                }
            })
            .catch(error => {
                self.enableLoading(false);
                if (sAndPcalledFrom == "") {
                    self.errorCB();
                }
                console.log(`S&P service failed.please try again..`);
            });
    }
    sAndPSuccessCB(response, triggerFromSeeMore = false) {
        let categoryName = !filteredParam ? self.toggleCategoryNameFromUrl("get") : '';
        let $listEl = $(config.gridEl).find(
            "li:not(.rich-grid-skeleton)"
        );
        self.productResponse = response.resultsets[0].results;
        if (triggerFromSeeMore) {
            self.productResponse = response.resultsets[0].results.slice(
                $listEl.length,
                self.productResponse.length
            );
        }
        self.totalProductCnt = response.resultcount.total;
        self.totalPageCount = response.general.page_total;
        self.productResponse.seeMoreAction = triggerFromSeeMore;
        self.productResponse.nextIndex = parseInt($listEl.length) + 1;
        self.generalResponse = response.general;
        self.sAndPResponse = response;
        holdScroll = false;
        if (self.totalProductCnt == 0) {
            if (sAndPcalledFrom == "") {
                self.errorCB();
            }
        }
        if (!triggerFromSeeMore && sAndPcalledFrom != "clearFilter") {
            if (!config.isSingleSelectionFacet || !filteredParam) {
                self.renderFilterTemplate(
                    self.sAndPResponse,
                    filteredParam ? true : false
                );
            }
        }
        if (!isDeepLinking || !config.isFacetsON || !categoryName) {
            self.renderGridTemplate();
            self.showSeeMoreButton(response);
        } else {
            let $matchedFilter = $(`.products-list-item input[data-seo-text="${categoryName}"]`);
            if ($matchedFilter.length) {
                $matchedFilter.attr('checked', true);
                self.filterClickAction($matchedFilter[0]);
            }
            isDeepLinking = false;
        }
    }
    renderFilterTemplate(res, isFilterActive, triggerResize) {
        if (!config.isFacetsON) {
            $(config.el).addClass("no-facets-container");
            return;
        }
        self.filterResponse = config.isSingleSelectionFacet ? (self.filterResponse || res) : (res || self.filterResponse);
        let selectedValue = _.filter(self.sAndPResponse.facets[0].values, function(item) {
            return item.selected == "true";
        });
        templateInit(
            "#filterComponentTemplate",
            config.facetsEl, {
                isSingleSelection: config.isSingleSelectionFacet,
                facets: self.filterResponse.facets,
                totalCount: self.filterResponse.resultcount.total,
                enableDropdownMenu: self.deviceName == "mobile" || self.deviceName == "mobilePortrait",
                selectedValue: !_.isEmpty(selectedValue) && selectedValue[0].value
            },
            (isFilterActive || triggerResize) ? "replace" : ""
        );
    }
    getExpirationdate() {
        let currentDate = new Date();
        let minutes =
            Math.ceil(currentDate.getMinutes() / config.storageExpirationInMin) *
            config.storageExpirationInMin;
        if (currentDate.getMinutes() == minutes) {
            currentDate.setMinutes(currentDate.getMinutes() + 1);
            minutes =
                Math.ceil(currentDate.getMinutes() / config.storageExpirationInMin) *
                config.storageExpirationInMin;
        }
        return new Date(
            currentDate.getFullYear(),
            currentDate.getMonth(),
            currentDate.getDate(),
            currentDate.getHours(),
            minutes,
            0
        );
    }
    showPreviewList(liCnt, isTriggeredFromSeeMore) {
        if (!isTriggeredFromSeeMore) {
            $(config.gridEl).empty();
            holdScroll = true;
            pageNo = 1;
        }
        if (self.deviceName == "desktop") {
            let displayGridCnt = config.gridCol[`${self.deviceName}`] * 2;
            templateInit('#previewListTemp', '#listingComponentInfo', liCnt && displayGridCnt >= liCnt ? liCnt : displayGridCnt, "");
        } else {
            $(".loader-outer").show();
        }
    }
    hidePreviewList() {
        if (self.deviceName == "desktop") {
            let $previewList = $(config.gridEl).find('.rich-grid-skeleton');
            $previewList.remove();
        } else {
            $(".loader-outer").hide();
        }
    }
    showSeeMoreButton(res) {
        let $listEl = $(config.gridEl).find(
            "li:not(.rich-grid-skeleton)"
        );
        let {
            page_total
        } = res.general;
        let $ele = $(".view-all-btn"),
            finalProductsArr = res.resultsets;
        if (finalProductsArr[0].results.length == 0) {
            $("li.zero-result").addClass("hide");
        }
        if (parseInt(page_total) > pageNo) {
            $ele.removeClass("hide");
            return;
        }
        $("li.zero-result").addClass("hide");
        if (res.general.total == $listEl.length) {
            $ele.addClass("hide");
        }
    }
    setStorageData(storageName, res) {
        if (!storageName) {
            return;
        }
        if (!res) {
            return;
        }
        res.categoryName = config.SAndPParams.productName;
        setStorage(storageName, res);
    }
    getStorageData(storageName) {
        const sessionData = getStorage(storageName);
        if (sessionData && sessionData.categoryName == config.SAndPParams.productName) {
            return sessionData;
        }
        return false;
    }
    errorCB() {
        self.redirectToErrorPage();
    }
    onFilterResizeAction() {
        self.renderFilterTemplate(
            '',
            filteredParam ? true : false,
            true
        );
    }
    renderGridTemplate() {
        let {
            seeMoreAction
        } = self.productResponse;
        let {
            nextIndex
        } = self.productResponse;
        let isFilterClick = seeMoreAction ? false : (filteredParam ? true : false);
        config.defaultLoadCnt = self.getDefaultScrollCnt(isFilterClick);
        (isFilterClick || seeMoreAction) && self.clearStoredData();
        self.gridResponse = !resizeBrowser ? self.productResponse : (self.gridResponse || self.productResponse || {});
        const slicedObj = self.sliceObject(self.gridResponse, resizeBrowser, isFilterClick, seeMoreAction) || {};

        self.hidePreviewList();
        $.each(slicedObj.obj, function(key,val){
            val.category = val.category ? val.category.split("|")[0] : false;
        });
        templateInit(
            '#listingComponentTemplate',
            config.gridEl,
            slicedObj.obj,
            (isFilterClick || resizeBrowser) && 'replace'
        );

        self.equalColumnHeight();
        self.checkImageLoaded($(config.gridEl));
        // checkTruncateText();

        (slicedObj.focusIndex || seeMoreAction) &&
        $(config.el).find(`>li:eq(${slicedObj.focusIndex || nextIndex}) a:first`).focus();
        self.enableLoading(false);
    }
    sliceObject(res, browserResizing, isFilterClick, isTriggeredFromSeeMore) {
        if (!res.length) return;
        let listCount = $(config.gridEl).find('>li:not(.rich-grid-skeleton)').length;
        if (browserResizing) {
            return {
                obj: res.slice(0, listCount).map(result => result)
            };
        }
        if (config.SAndPParams.initialLoadcount <= listCount || isTriggeredFromSeeMore || (!config.lazyLoadEnabled[deviceName])) {
            return {
                obj: res
            };
        }
        let getActualRange = self.fromAndToCount(res, isFilterClick);
        let startCnt = getActualRange.start;
        let endCnt = getActualRange.end;

        return {
            obj: res.slice(startCnt, endCnt).map(result => result),
            focusIndex: startCnt
        };
    };
    fromAndToCount(res, isFilterClick) {
        let listCount = $(config.gridEl).find('>li:not(.rich-grid-skeleton)').length;
        let fromCnt = isFilterClick ? 0 : listCount || 0;
        let colVal = parseInt(config.gridCol[`${self.deviceName}`] || 4);

        if (!fromCnt) {
            let remLiCount = res.length % colVal;
            let newColVal = colVal * 2;
            if (res.length > newColVal && res.length < colVal * 3 && remLiCount != 0) {
                config.defaultLoadCnt = newColVal + remLiCount;
            }
        } else {
            config.defaultLoadCnt = listCount;
        }
        let isStillScrollingActive = fromCnt && config.SAndPParams.initialLoadcount - config.defaultLoadCnt;
        let nextScrollCnt = isStillScrollingActive ?
            isStillScrollingActive % colVal == 0 ?
            colVal :
            isStillScrollingActive % colVal :
            0;

        return {
            start: fromCnt,
            end: config.SAndPParams.initialLoadcount == -1 || (config.SAndPParams.productLoadcount < 1 && config.pageNo) ?
                res.length : isStillScrollingActive > 0 ?
                fromCnt + nextScrollCnt : fromCnt + (fromCnt ? config.SAndPParams.productLoadcount : config.defaultLoadCnt),
        };
    }
    getDefaultScrollCnt(isFilterClick) {
        return !config.lazyScroll || (onScrollLoadCnt > config.SAndPParams.initialLoadcount && !isFilterClick) || !onScrollLoadCnt ?
            config.SAndPParams.initialLoadcount :
            onScrollLoadCnt;
    }
    onLoadScrollCnt() {
        let $el = $(config.gridEl);
        let $listEl = $el.find('>li:not(.rich-grid-skeleton)');
        let liHeight = $listEl.length ? $($listEl[0]).height() : 393;
        let deviceCol = config.gridCol[`${self.deviceName}`];
        let val =
            Math.ceil(($(window).scrollTop() + $el.offset().top - liHeight + $(window).height()) / (liHeight - 30)) *
            (deviceCol || 4);
        onScrollLoadCnt = val >= deviceCol * 2 ? val : deviceCol * 2;
    }
    checkImageLoaded(elem) {
        let $closestEle,
            $imageElem = $(elem).find('.img-wrapper:not(.image-loaded) img');
        if (!$imageElem.length) {
            return;
        }
        $imageElem
            .imagesLoaded()
            .progress((instance, image) => {
                $closestEle = $(image.img).closest('.img-wrapper');
                $closestEle.addClass('image-loaded');
                if (!image.isLoaded) {
                    $closestEle.addClass('broken-image');
                }
            })
            .done((instance, image) => {});
    }
    clearStoredData() {
        self.gridResponse = '';
        config.pageNo = 0;
    };
    redirectToErrorPage() {
        if (config.errorPageUrl) {
            window.location.href = config.errorPageUrl;
        }
    }
    getFilteredElem() {
        let $ele;
        if (config.isSingleSelectionFacet) {
            $ele = $(".checkbox-cont fieldset input:not(.no-fiter):checked");
        } else {
            $ele = $(".checkbox-cont fieldset input:checkbox:checked");
        }
        return $ele;
    }
    clearAllStorage(bool) {
        bool && sessionStorage.clear();
    }
    enableLoading(bool) {
        let $ele = $("#preloader");
        if (bool) {
            $ele.addClass("loading");
        } else {
            $ele.removeClass("loading");
        }
    }

    bindingResize() {
        let resizedDeviceName;
        $(window).resize(
            _.debounce(() => {
                resizedDeviceName = getDeviceName();
                if (self.deviceName != resizedDeviceName) {
                    self.deviceName = resizedDeviceName;
                    self.onFilterResizeAction();
                    self.equalColumnHeight(true);
                    resizeBrowser = false;
                }
            }, 500)
        );
    }
    equalColumnHeight(triggerResize) {
        if (self.deviceName == "mobilePortrait" && !triggerResize) return;
        const gridColumn = config.gridCol[`${self.deviceName}`];
        const $loadedItem = $(config.gridEl).find('.grid-item.loaded');
        const isAllIteminSameRow = $loadedItem.length % gridColumn;
        if (isAllIteminSameRow && !triggerResize) {
            $loadedItem.slice($loadedItem.length - isAllIteminSameRow, $loadedItem.length).removeClass('loaded');
        }
        const $gridItem = triggerResize ? $(config.gridEl).find('.grid-item') : $(config.gridEl).find('.grid-item:not(.loaded)');
        const totalGrid = $gridItem.length;
        if (!totalGrid) return;
        let totalRows = Math.ceil(totalGrid / gridColumn),
            maxTitleHeight, maxImageHeight, rowEle, $imageField, $titleField;
        $gridItem.addClass('loaded');
        for (let index = 0; index < totalRows; index++) {
            rowEle = $gridItem.slice((index * gridColumn), (gridColumn + (index * gridColumn)));
            self.isGridImageLoaded(rowEle, index, (res, indx) => {
                if (!res) return;
                $titleField = $(res).find('.listingComponent-headline');
                $imageField = $(res).find('.img-wrapper');
                $imageField.css('height', 'auto');
                $titleField.css('height', 'auto');
                maxTitleHeight = Math.max.apply(null, $titleField.map(function() {
                    return $(this).height();
                }).get());
                maxImageHeight = Math.max.apply(null, $imageField.find('img').map(function() {
                    return $(this).height();
                }).get());
                $imageField.css('height', maxImageHeight);
                $titleField.css('height', maxTitleHeight);
                $(res).addClass(`row-${indx}`)
            });
        }
    }
    isGridImageLoaded(elem, indx, cb) {
        let $imgEle = $(elem).find('img');
        if (!$imgEle.length) {
            cb(false);
            return;
        }
        $imgEle.imagesLoaded().done(() => {
            setTimeout(function() { cb(elem, indx); },1000);
        });
    }
}

let self,
    filteredParam,
    gridDefaultCol = 12, // bootstrap grid count
    resizeBrowser = false,
    holdScroll = true,
    pageNo = 1,
    onScrollLoadCnt,
    isDeepLinking = false,
    deviceName,
    deviceNames = ["desktop", "tablet", "mobile"],
    sAndPcalledFrom = "";

const request = window.global.ajaxRequest.ajaxCall;
const apiData = window.global.apiConfig;
const handleBarsHelperInst = window.global.handleBarsHelperInst;
const templateInit = window.global.handleBarTemplateInst.loadTemplate;
const evtBinding = window.global.eventBindingInst;
const getDeviceName = window.global.deviceName;
const getStorage = window.global.getStorage;
const setStorage = window.global.setStorage;

const listingComponentInst = new listingComponent();
