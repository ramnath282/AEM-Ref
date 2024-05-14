import ajaxRequest from "../shared/ajaxbinding";
import apiConfig from "../shared/apiConfig";
import {
    getDeviceName
} from "../shared/constant";
import eventBinding from "../shared/eventBinding";
import {
    handleBarsHelper
} from "../shared/handleBarsHelper";
import Constants from "../shared/constant";
import {
    getStorage,
    setStorage,
    deleteStorage
} from "../shared/sessionStorage";
import {
    getCookie
} from "../shared/browserCookie";
import {
    promoDateFilter,
    checkAnyFeaturedPrice
} from "../shared/promo-sale-grid";
import {
    renderCategoryTemplate
} from "../shared/product-category";
import {
    onFilterResizeAction,
    renderFilterTemplate
} from "../shared/product-filter";
import {
    gridActions,
    datasets,
    renderGridTemplate,
    relatedGridTemplate,
    lazyLoadGrid,
    loadPreviewList
} from "../shared/product-grid";
import {
    renderArticleTemplate
} from "../shared/product-article";

const config = {
    desktopCol: 4,
    tabletCol: 3,
    mobileCol: 2,
    categoryParam: `${datasets.marketingParam}`,
    marketingParam: `${datasets.marketingParam}`,
    clearMarketingStorage: $("#storeCategoryJson").val() == "nocache",
    isAuthorModeON: $("#isAuthorMode").val() || false,
    isSearchModeON: $("#isSearchMode").val() || false,
    marketingDataOFF: false,
    categoryDataOFF: ($("#hideLeftNavigation").val() == "true" || $("#isSearchMode").val()) ? true : false,
    facetsDataOFF: ($("#hideFacets").val() == "true" || $("#hideLeftNavigation").val() == "true" ) ? true : false,
    clearAllStorage: false,
    isRelatedProductsON: $("#isSearchMode").val() || false, //$("#isRelatedProductsMode").val() || false,
    storageName: {
        marketing: "ESpotFilteredData",
        category: "categoryListData",
        sandp: "spGridData",
        cacheParam: "cacheFilterParam"
    },
    intialDirectLoad: 0,
    intialDirectLoadQuery: 0,
    isBackButtonClicked: window.performance && window.performance.navigation.type == 2,
    errorPageUrl: $("#siteErrorPage").val() || false,
    isFPPage: $("#isFPPage").val() ? true : false,
    storageExpirationInMin: 15 || parseInt($("#sessionStorageTimeout").val() || 15) || 15,
    cachedFilteredData: getStorage("cacheFilterParam") || {
        filterParams: "",
        filterNames: "",
        activeProductId: ""
    }
};

export class productLanding {
    constructor() {
        // config.isBackButtonClicked && $(window).scrollTop(0);
        self = this;
        this.deviceName = getDeviceName();
        this.priceObjName = self.getActivePriceName();
        this.bindingHelperFn();
        self.isSearchRedirectFrom =
            self.getCategoryFromURL("searchCategory") || "product";
        self.searchValue = self.getCategoryFromURL("searchTerm") || "";
        self.gridSchema = self.schemaFields(self.isSearchRedirectFrom);
        this.clearAllStorage(config.clearAllStorage);
        this.getCategoryData();
        if (config.isBackButtonClicked && "scrollRestoration" in history) {
            // Back off, browser, I got this...
            history.scrollRestoration = "manual";
        }
        // config.facetsDataOFF && $(".columnControl .image-text-container").addClass("no-filter-layout");
        if (config.isBackButtonClicked) {
            if (!_.isEmpty(config.cachedFilteredData.filterNames)) {
                filteredParam = config.cachedFilteredData.filterParams;
                sAndPcalledFrom = "checkboxAction";
                self.getSAndPData(
                    undefined,
                    undefined,
                    filteredParam,
                    undefined,
                    config.cachedFilteredData.count
                );
            } else {
                this.getSAndPData(
                    undefined,
                    undefined,
                    undefined,
                    undefined,
                    config.cachedFilteredData.count || undefined
                );
            }
        } else {
            this.getSAndPData();
        }
        this.getMarketingData();
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.bindingResize();
        if (navigator.userAgent.match(/Android|iPad|iPod/i)) {
            $("body").addClass("device-view");
        }
    }
    bindingEventsConfig() {
        let eventsArr = {
            'change #product-filter-section .products-list-item input[type="checkbox"]': "checkboxAction",
            "click .grid-sort .custom-dropdown a": "selectBoxAction",
            "change .grid-sort .custom-dropdown select": "fPselectBoxAction",
            "click #see-more button": "seeMoreAction",
            "click .category-aside-section .clear-all-filters": "clearAllFilter",
            "submit #gridSearchBox": "callSnP",
            "click .grid-lists .grid-title": "getActiveProductId",
            "click .grid-lists .product-image": "getActiveProductId",
            "change .facetContainer .custom-check": "imageFacetAction"
        };
        return eventsArr;
    }
    bindingHelperFn() {
        handleBarsHelperInst.callRegisterHelper("changeToHyphen");
        handleBarsHelperInst.callRegisterHelper("getSelectedTitle");
        handleBarsHelperInst.callRegisterHelper("getMMDD");
        handleBarsHelperInst.callRegisterHelper("getMMDDYYYY");
        handleBarsHelperInst.callRegisterHelper("forLoop");
        handleBarsHelperInst.checkIFConditions("parseFloat");
        handleBarsHelperInst.checkIFConditions("ifEquals");
        handleBarsHelperInst.checkIFConditions("ifNotEquals");
        handleBarsHelperInst.checkIFConditions("checkIndexExist");
        handleBarsHelperInst.checkIFConditions("dynamicKeyVal");
        handleBarsHelperInst.checkIFConditions("greaterThan");
        handleBarsHelperInst.checkIFConditions("isLessThanMonths");
        handleBarsHelperInst.checkIFConditions("finalActualPrice");
        handleBarsHelperInst.getArticleSocialShare("socialElements");
        handleBarsHelperInst.getArticleSocialShare("relatedSocialElements");
    }
    callSnP(ele, evt) {
        evt.preventDefault();
        var searchKeyword = $(".plpSearchTxt").val();

        if (searchKeyword != "") {
            var snpUrl =
                $("#snpAccountUrl").val() ||
                "//sp1004f984.guided.ss-omtrdc.net/?index=prod";

            var pageSize =
                $("#SimpleSearchForm_SearchTerm").attr("data-pagesize") || "36";

            $.getJSON(
                snpUrl +
                "&search=product;q=" +
                searchKeyword +
                "&count=" +
                pageSize +
                "&page=1",
                function(res) {
                    sessionStorage.setItem("spGridData", JSON.stringify(res));
                    var action = $("#gridSearchBox").attr("action");
                    var categoryId =
                        $("#SimpleSearchForm_SearchTerm").attr("data-categoryid") ||
                        "10601";
                    var storeId =
                        $("#SimpleSearchForm_SearchTerm").attr("data-storeid") || "10651";
                    var langId =
                        $("#SimpleSearchForm_SearchTerm").attr("data-langid") || "-1";
                    var redirectURL =
                        action +
                        "?storeId=" +
                        storeId +
                        "&catalogId=" +
                        categoryId +
                        "&langId=" +
                        langId +
                        "&sType=SimpleSearch&resultCatEntryType=2&showResultsPage=true&searchSource=Q&pageView=&beginIndex=0&pageSize=" +
                        pageSize +
                        "&searchTerm=" +
                        searchKeyword;

                    if (res.general.redirect && res.general.redirect != "") {
                        redirectURL = res.general.redirect;
                    }

                    window.location = redirectURL;
                }
            ).fail(function(error) {
                console.log(error);
            });
        }
    }
    checkboxAction(ele) {
        const $ele = $(ele);
        const filterValues = $ele.data("action");
        const parentCategoryName = $ele.parents("fieldset").data("index");

        sAndPcalledFrom = "checkboxAction";
        let index = filterLabelNames.indexOf(parentCategoryName);
        if (index > -1) {
            filterLabelNames.splice(index, 1);
        }
        let $checkedItems = self.getFilteredElem();
        if ($ele.is(":checked")) {
            filteredParam = filterValues;
            filterLabelNames.push(parentCategoryName);
        } else {
            filteredParam = !$checkedItems.length ? datasets.snpParam : filterValues;
            if (config.isSearchModeON == "true") {
                filteredParam = !$checkedItems.length ?
                    self.getSnPParamAction("plp_search") :
                    filterValues;
            }
        }
        if (!$checkedItems.length) {
            config.intialDirectLoadQuery = 1;
            self.resetCachedData();
        } else {
            self.updateCachedData(filteredParam, filterLabelNames);
        }
        filteredParam = `${filteredParam};count=${datasets.initialLoadcount}`;
        self.enableLoading(true);
        self.getSAndPData(
            data => self.getMarketingData(),
            undefined,
            filteredParam
        );
        if ($("#isFPPage").val() == "true") {
            $("html, body").animate({
                    scrollTop: $(".productGrid").offset().top - 60 - $("header").height()
                },
                500
            );
        } else {
            $("html, body").animate({
                    scrollTop: $(".productGrid").offset().top - 50
                },
                500
            );
        }
    }
    selectBoxAction(ele, evt) {
        evt.preventDefault();
        const $ele = $(ele);
        const selectedVal = $ele.html();
        const $parentEle = $ele
            .parents(".grid-sort")
            .find(".custom-dropdown-toggle");
        const filterValues = $ele.attr("href");
        sAndPcalledFrom = "selectBoxAction";

        if (!filterValues) {
            return;
        }
        filteredParam = filterValues;
        $parentEle.length &&
            $parentEle.attr("title", selectedVal).html(selectedVal);
        self.getSAndPData(
            data => self.getMarketingData(),
            undefined,
            filteredParam
        );
    }
    fPselectBoxAction(ele, evt) {
        evt.preventDefault();
        const $ele = $(ele);
        const filterValues = $ele.val();
        sAndPcalledFrom = "selectBoxAction";

        if (!filterValues) {
            return;
        }
        filteredParam = filterValues;
        self.getSAndPData(
            data => self.getMarketingData(),
            undefined,
            filteredParam
        );
    }
    seeMoreAction(ele, evt) {
        evt.preventDefault();
        let productLoadcount = datasets.productLoadcount;
        let filterNames =
            filteredParam == undefined || filteredParam == null ?
            filteredParam :
            filteredParam.split(";");
        let $listEl = $(".grid-lists").find(
            "li:not(.preview-list):not(.espot-grid)"
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
            data => self.getMarketingData(),
            undefined,
            filteredParam,
            pageNo,
            productLoadcount,
            true
        );
    }
    clearAllFilter(ele, evt) {
        evt.preventDefault();
        const $ele = $(ele, ".clear-wrapper");

        if (window.location.search.indexOf('directQuery') > -1) {
            config.intialDirectLoad = 0;
            config.intialDirectLoadQuery = 1;
        }

        self.resetCachedData();
        let $checkedItems = self.getFilteredElem();
        if (!$checkedItems.length &&
            !(filteredParam && filteredParam.indexOf("sort") != -1)
        ) {
            $ele.addClass("hide");
            return;
        }
        $ele.removeClass("hide");
        $checkedItems.prop("checked", false);
        filteredParam = datasets.snpParam;
        if (config.isSearchModeON == "true") {
            filteredParam = self.getSnPParamAction("plp_search");
        }
        self.getSAndPData(
            data => self.getMarketingData(),
            undefined,
            filteredParam
        );
        if ($("#isFPPage").val() != "true") {
            $(
                "#product-filter-section .products-list-item:not(:first-child) .collapse.in"
            ).collapse("hide");
        } else {
            $(".facetContainer .imageFacetColor").removeClass("active");
            if ($(window).width() >= 768) {
                $(
                    "#product-filter-section .products-list-item.checkbox-cont:not(:first-child) fieldset"
                ).removeClass("in");
                $(
                    "#product-filter-section .products-list-item.checkbox-cont:not(:first-child) h2 a"
                ).attr("aria-expanded", "false");
            }
        }
    }
    scrollToTop() {
        const ele = $(".grid-lists")[0];
        const topVal = ele.scrollTop;
        if (ele.getBoundingClientRect().top < 0) {
            window.requestAnimationFrame(self.scrollToTop);
            window.scrollTo(0, topVal - topVal / 8);
        }
    }
    getCategoryFromURL(attrName) {
        const urlParam = urlName => {
            urlName = urlName.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            let regexS = "[\\?&]" + urlName + "=([^&#]*)";
            let regex = new RegExp(regexS);
            let results = regex.exec(window.location.search);
            if (results) return results[1];
            else return results;
        };
        return urlParam(attrName);
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
    getCacheFilterParams(fieldName) {
        if (!config.isBackButtonClicked) {
            return false;
        }
        let cacheFilterParams = getStorage(config.storageName.cacheParam);
        return (
            cacheFilterParams &&
            (typeof cacheFilterParams == "string" ?
                JSON.parse(cacheFilterParams) :
                cacheFilterParams)[fieldName]
        );
    }
    getSAndPParam(param, page, count) {
            let isCacheParams = false;
            param =
                config.isSearchModeON == "true" ?
                sAndPcalledFrom == "checkboxAction" ||
                sAndPcalledFrom == "selectBoxAction" ?
                `${param}&` :
                self.getSnPParamAction("plp_search") :
                `${datasets.snpParam ? `${param || datasets.snpParam}&` : "&"}`;

        if (window.performance && !cacheParamTriggered) {
            let checkStorageType = self.getCacheFilterParams("filterParams");
            if (
                config.isBackButtonClicked &&
                checkStorageType != null &&
                checkStorageType != ""
            ) {
                let checkStorageType = self.getCacheFilterParams("filterParams");
                param = checkStorageType ? checkStorageType + "&" : param;
                deleteStorage(config.storageName.sandp);
                isCacheParams = true;
                filterLabelNames = self.getCacheFilterParams("filterNames") || [];
            } else {
                let cacheFilterParams = getStorage(config.storageName.cacheParam);
                if (
                    !config.isBackButtonClicked ||
                    !cacheFilterParams ||
                    !cacheFilterParams.activeProductId
                )
                    deleteStorage(config.storageName.cacheParam);
            }
            cacheParamTriggered = true;
        }
        if (
            sAndPcalledFrom == "checkboxAction" ||
            sAndPcalledFrom == "selectBoxAction" ||
            isCacheParams
        ) {
            if (count) {
                config.intialDirectLoadQuery = 1;
                return `?${param.replace(/&amp;/g, "&")}page=${page ||
                    pageNo}&count=${count}`;
            }
            return `?${param.replace(/&amp;/g, "&")}page=${page || pageNo}`;
        } else {
            const isDirectQuery = self.getCategoryFromURL("directQuery");

            if (config.isSearchModeON == "true" && isDirectQuery && "true" == isDirectQuery.trim() && config.intialDirectLoadQuery == 0) {
                const filterCount = self.getCategoryFromURL("filterCount");
                let directQuery = window.location.search.replace('directQuery=true&', '');
                directQuery = directQuery.replace('searchTerm=direct&', '');
                directQuery = directQuery.replace('wcmmode=disabled&', '');
                directQuery = directQuery.replace('filterCount=' + filterCount + '&', '');
                param = `${param}`+(directQuery.replace("?", ""))+'&';
            } else if (config.isSearchModeON == "true" && isDirectQuery && "true" == isDirectQuery.trim() && config.intialDirectLoadQuery == 1) {
                // param = "";
            }

            return config.isSearchModeON == "true" ?
                `?${param.replace(/&amp;/g, "&").replace("?", "")}count=${count ||
                datasets.initialLoadcount ||
                20}&page=${page || pageNo}` :
                `?${param.replace(/&amp;/g, "&")}count=${count ||
                datasets.initialLoadcount ||
                20}&page=${page || pageNo}&price=${self.priceObjName.replace(
                    "_price",
                    ""
                )}&plp=true${self.getSAndPTrackingParams()}`;
        }
    }
    getSAndPTrackingParams() {
        let targetAffinity = getCookie("targetAffinity");
        let audienceStream = getCookie("ss_lastone_fan");
        let paramValue = targetAffinity || audienceStream || "";
        return paramValue ? `&TACategory=${paramValue}` : "";
    }
    getSnPParamAction(pageType) {
        switch (pageType) {
            case "selectBoxAction":
                return `?${param.replace(/&amp;/g, "&")}page=${page || pageNo}`;
            case "checkboxAction":
                return `?${param.replace(/&amp;/g, "&")}page=${page || pageNo}`;
            case "plp_search":
                isSearchTriggered = true;
                if (
                    $("#isFPPage").val() == "true" &&
                    self.getCategoryFromURL("giftAge") != undefined
                ) {
                    let giftAgeParam =
                        self.getCategoryFromURL("giftAge") == null ||
                        self.getCategoryFromURL("giftAge") == undefined ?
                        "" :
                        self.getCategoryFromURL("giftAge");

                    let giftAgeCategoryParam =
                        self.getCategoryFromURL("x1") == null ||
                        self.getCategoryFromURL("x1") == undefined ?
                        "" :
                        self.getCategoryFromURL("x1");

                    let giftPriceCategoryParam =
                        self.getCategoryFromURL("x2") == null ||
                        self.getCategoryFromURL("x2") == undefined ?
                        "" :
                        self.getCategoryFromURL("x2");

                    let giftPriceParam =
                        self.getCategoryFromURL("giftPrice") == null ||
                        self.getCategoryFromURL("giftPrice") == undefined ?
                        "" :
                        self.getCategoryFromURL("giftPrice");
                    plpSearchParam = `${giftAgeParam}`;
                    return `q1=${giftAgeParam};q2=${giftPriceParam};x1=${giftAgeCategoryParam};x2=${giftPriceCategoryParam}&`;
                } else {
                    plpSearchParam =
                        self.getCategoryFromURL("searchTerm") == null ||
                        self.getCategoryFromURL("searchTerm") == undefined ?
                        "" :
                        self.getCategoryFromURL("searchTerm");
                    return `?search=${self.isSearchRedirectFrom};q=${plpSearchParam}&`;
                }
        }
    }
    updateCachedData(filteredParam, filterLabelNames, activeProductId, count) {
        let checkedElemLen = $(".checkbox-cont fieldset input:checkbox:checked")
            .length;
        filteredParam = filteredParam ?
            "?" + filteredParam.split("?")[1] :
            undefined;
        config.cachedFilteredData = {
            filterParams: checkedElemLen && !filteredParam ?
                config.cachedFilteredData.filterParams :
                filteredParam || null,
            filterNames: checkedElemLen && !filterLabelNames ?
                config.cachedFilteredData.filterNames :
                filterLabelNames || null,
            activeProductId: activeProductId || null,
            count: count || datasets.initialLoadcount
        };
        setStorage(config.storageName.cacheParam, config.cachedFilteredData);
    }
    resetCachedData() {
        config.cachedFilteredData = {
            filterParams: "",
            filterNames: "",
            activeProductId: ""
        };
        deleteStorage(config.storageName.cacheParam);
        sAndPcalledFrom = "";
    }
    getActiveProductId(ele) {
        let partNumber = $(ele)
            .closest("li")
            .data("partno");
        // let cacheFilterParams = getStorage(config.storageName.cacheParam);
        // if (!cacheFilterParams) return;
        config.cachedFilteredData.activeProductId = partNumber;
        setStorage(config.storageName.cacheParam, config.cachedFilteredData);
    }
    getSAndPData(cb, liCnt, queryParams, pageNo, count, isTriggeredFromSeeMore) {
        let $listEl = $(".grid-lists").find(
            "li:not(.preview-list):not(.espot-grid)"
        );
        if (config.cachedFilteredData.activeProductId && count) {
            deleteStorage(config.storageName.sandp);
        }
        if (isTriggeredFromSeeMore) {
            pageNo = 1;
            count = parseInt(count) + parseInt($listEl.length);
            self.updateCachedData(undefined, undefined, undefined, count);
        }
        self.priceObjName = self.getActivePriceName();
        liCnt = liCnt || 8;
        self.showPreviewList(cb, liCnt, isTriggeredFromSeeMore);
        let errorMessage;
        let $checkedItems = self.getFilteredElem();
        const $imageCheckedItems = $(".imageFacet input:checkbox:checked");
        if ($imageCheckedItems.length && !$checkedItems.length) {
            $checkedItems = $imageCheckedItems;
        }
        const payload = apiData("products")["getSAndP"].apply({
            queryString: self.getSAndPParam(queryParams, pageNo, count)
        });
        const sessionData = self.getStorageData(config.storageName.sandp);
        let isStorageExpired =
            sessionData.expireTime && new Date(sessionData.expireTime);
        if (
            isStorageExpired > new Date() &&
            !$checkedItems.length &&
            !(queryParams && queryParams.indexOf("sort") != -1) &&
            sessionData &&
            !isTriggeredFromSeeMore &&
            !isSearchTriggered
        ) {
            self.sAndPSuccessCB(sessionData, cb, isTriggeredFromSeeMore);
            return;
        }
        sessionData && console.log("S&P Storage Refreshed..");
        // let expirationDate = new Date(new Date().getTime() + (60000 * config.storageExpirationInMin));
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
        let expirationDate = new Date(
            currentDate.getFullYear(),
            currentDate.getMonth(),
            currentDate.getDate(),
            currentDate.getHours(),
            minutes,
            0
        );
        request(payload)
            .then(data => {
                try {
                    isSearchTriggered = false;
                    const response = typeof data == "string" ? JSON.parse(data) : data;
                    const redirect = response.general.redirect;
                    if (redirect && redirect != "") {
                        self.redirectSearchPage(redirect);
                    }
                    $.each(response, (k, v) => {
                        if (k == "facets") {
                            $.each(v, (k1, v1) => {
                                if (v1.label == "Doll Skin Tone") {
                                    v1["showColorTone"] = true;
                                } else if (v1.label == "Rating") {
                                    v1["rating"] = true;
                                } else {
                                    v1["other"] = true;
                                }
                            });
                        }
                    });
                    response.expireTime = expirationDate.toString();
                    !queryParams &&
                        (pageNo == undefined || pageNo < 1) &&
                        !config.isBackButtonClicked &&
                        self.setStorageData(config.storageName.sandp, response);
                    self.sAndPSuccessCB(response, cb, isTriggeredFromSeeMore);
                    if (config.isFPPage) {
                        $(".custom-dropdown select.inner")
                            .find('option[aria-selected="true"]')
                            .prop("selected", true);

                        $(".clear-all-filters .total-cnt-inner").html(
                            $(".checkbox-cont fieldset input:checkbox:checked").length ||
                            $(".imageFacet input:checkbox:checked").length ||
                            0
                        );

                        if (window.location.search.indexOf('directQuery') > -1 && config.intialDirectLoad == 0) {
                            config.intialDirectLoad = 1;
                        }

                        if (!$(".checkbox-cont fieldset input:checkbox:checked").length) {
                            $(".slider-h2.total-filtered-cnt .total-itemcnt-inner").html(
                                response.general.total || 0
                            );
                        }
                    } else {
                        $(".total-cnt-inner").html(
                            $(".checkbox-cont fieldset input:checkbox:checked") ?
                            $(".checkbox-cont fieldset input:checkbox:checked").length :
                            0
                        );
                    }
                } catch (e) {
                    errorMessage = e.message;
                    self.enableLoading(false);
                    console.log(`S&P JSON Format error: ${errorMessage}`);
                    self.mergedResponse = null;
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
    sAndPSuccessCB(response, cb, triggerFromSeeMore = false) {
        let $listEl = $(".grid-lists").find(
            "li:not(.preview-list):not(.espot-grid)"
        );
        self.productResponse = response.resultsets[0].results;
        if (triggerFromSeeMore) {
            self.productResponse = response.resultsets[0].results.slice(
                $listEl.length,
                self.productResponse.length
            );
        }
        self.articleResonse =
            response.resultsets[1] && response.resultsets[1].results.slice(0, 4);
        self.totalProductCnt = response.resultcount.total;
        self.totalPageCount = response.general.page_total;
        self.productResponse.seeMoreAction = triggerFromSeeMore;
        self.productResponse.nextIndex = parseInt($listEl.length) + 1;
        self.generalResponse = response.general;
        let $sortElem = $(".details-list-item.grid-sort");
        holdScroll = false;
        if(config.facetsDataOFF){
            response.facets = [];
        }
        if (!triggerFromSeeMore) {
            renderFilterTemplate(
                response,
                filteredParam ? true : false,
                self.getCacheFilterParams("filterNames") || []
            );
            self.showClearBtn();
        }
        typeof cb == "function" && cb(self.productResponse);
        self.showSeeMoreButton(response);
        if (!articleDateLoaded && config.isSearchModeON) {
            articleDateLoaded = true;
            if (self.isSearchRedirectFrom == "article") {
                let $headingEle = $(".product-grid-heading");
                $headingEle.html(
                    $headingEle.data("articleHeading") || $headingEle.html()
                );
            }
            if (
                !response.facets.length ||
                typeof response.facets[0].values == "undefined"
            ) {
                self.applyNonFilterLayout();
            }
            if (
                self.generalResponse.redirect &&
                self.generalResponse.redirect != ""
            ) {
                self.redirectSearchPage(self.generalResponse.redirect);
            }
            if (
                self.productResponse.length == 1 &&
                plpSearchParam == self.productResponse[0].PartNumber
            ) {
                self.redirectToDetailsPage(self.productResponse[0].url);
            }
            if (config.isRelatedProductsON) {
                self.relatedProductInSearchResultPage();
                // if (self.isSearchRedirectFrom == "article") {
                $(".article-search-grid").addClass("hide");
                // }
            } else {
                if (!self.articleResonse) {
                    console.log(`Article data is not available for ${plpSearchParam}`);
                    return;
                }
                renderArticleTemplate(self.generalResponse, self.articleResonse);
            }
        }
        if (self.totalProductCnt == 0) {
            if (sAndPcalledFrom == "" && !config.isAuthorModeON) {
                self.errorCB();
            }
            $sortElem.addClass("hide-forcely");
        } else if ($sortElem.hasClass("hide-forcely")) {
            $sortElem.removeClass("hide-forcely");
        }
        if (config.isSearchModeON) {
            // Need to check with Dinesh  for analytics issues TODO
            // typeof trackingSearchResultPage == "function" && trackingSearchResultPage(response || {});
        }
    }
    applyNonFilterLayout() {
        let $ele = $(".ecomm-search-results-page .image-text-container");
        $ele.length && $ele.addClass("no-filter-layout");
    }
    relatedProductInSearchResultPage() {
        // itemsToShow, - 3/4
        // queryString, - dolls*
        // categoryName - article/product
        let $targetElem = $("#relatedGridLists");
        let $headingEle = $(".related-grid-lists h2");
        if (!$targetElem.length) {
            return;
        }
        let dataAttrs = $targetElem[0].dataset;
        let count = dataAttrs.itemToShow || 3;
        let category =
            self.isSearchRedirectFrom == "product" ? "article" : "product";
        let payloadConfig = apiData("searchResult")["getRelatedProducts"];
        let payload;
        const relatedGridSchema = self.schemaFields(category);
        if (isFPPage) {
            payload = payloadConfig.apply({
                queryString: self.searchValue,
                itemsToShow: count,
                categoryName: "product"
            });
            Object.assign(relatedGridSchema, {
                socialIcons: false,
                title: true,
                promoLabel: true,
                tagLabel: true,
                categoryName: true,
                description: true
            });
            payload.url = self.removeParameterFromURL(payload.url, 'do');
        } else {
            payload = payloadConfig.apply({
                queryString: `${self.searchValue}*`,
                itemsToShow: count,
                categoryName: category
            });
        }
        request(payload)
            .then(data => {
                let {
                    results
                } = data.resultsets[0];
                let finalResult = _.map(results, (item, indx) => {
                    return checkAnyFeaturedPrice(item, "sale_price");
                });
                if (isFPPage) {
                    finalResult = data.resultsets[1] && data.resultsets[1].results;
                    if (!finalResult.length) return;
                }
                relatedGridTemplate(finalResult, relatedGridSchema);
                let {
                    noArticleresults
                } = data.general;
                let heading = category == "product" ?
                    dataAttrs.productTitle :
                    dataAttrs.articleTitle;
                if (noArticleresults) {
                    heading = $headingEle.data('popularRecordsTitle');
                }
                $headingEle.html(heading);
                $(".related-grid-lists .view-all-datas a").attr(
                    "href",
                    category == "product" ?
                    dataAttrs.productViewall :
                    dataAttrs.articleViewall
                );
            })
            .catch(error => {
                console.log("Related products API Error..");
            });
    }
    showPreviewList(cb, liCnt, isTriggeredFromSeeMore) {
        if (typeof cb == "function" && !isTriggeredFromSeeMore) {
            $(".grid-lists").empty();
            holdScroll = true;
            pageNo = 1;
        }
        let displayGridCnt = config[`${self.deviceName}Col`] * 2;
        loadPreviewList(liCnt && displayGridCnt >= liCnt ? liCnt : displayGridCnt);
    }
    imageFacetAction(ele) {
        const $ele = $(ele);
        const $filterEle = $(".category-aside-section .clear-all-filters");
        const $closestEle = $(ele).closest(".imageFacet");
        $(`.imageFacet .custom-check`)
            .not($ele)
            .prop("checked", false);
        if (!$ele.is(":checked") || $closestEle.hasClass("active-facet")) {
            // image facet should be a single selection
            return;
        }
        let facetQuery = $ele.data("action");
        if (facetQuery.indexOf("category=") == -1) {
            facetQuery = `${datasets.snpParam};${facetQuery};count=${datasets.initialLoadcount}`;
        }
        if (!$filterEle.hasClass("hide")) {
            self.resetCachedData();
            let $checkedItems = self.getFilteredElem();
            $checkedItems.prop("checked", false);
            $filterEle.addClass("hide");
        }
        //added css style here - temp
        $(".imageFacet")
            .removeClass("active-facet")
            .css({
                "pointer-events": "inherit",
                opacity: "1"
            });
        $closestEle
            .addClass("active-facet")
            .css({
                "pointer-events": "none"
            });
        sAndPcalledFrom = "checkboxAction";
        filteredParam = facetQuery;
        self.enableLoading(true);
        self.getSAndPData(
            data => self.getMarketingData(),
            undefined,
            filteredParam
        );
        if ($("#isFPPage").val() == "true") {
            let topHeaderHeight = $("header").height() || 0;
            let plusScroll =
                window.innerWidth > 765 ?
                window.innerWidth > 1200 ?
                ($(".newsletter-sign-up:visible").height() || 0) - 50 :
                -40 :
                $(".filter-btn-slider .slide-header").height() + 20;
            $("html, body").animate({
                    scrollTop: $(".productGrid").offset().top - topHeaderHeight - plusScroll
                },
                500
            );
        }
    }

    showSeeMoreButton(res) {
        let $listEl = $(".grid-lists").find(
            "li:not(.preview-list):not(.espot-grid)"
        );
        let {
            page_total
        } = res.general;
        let $ele = $("#see-more"),
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
    getMarketingData() {
        if (config.marketingDataOFF) {
            self.mktgFailureCB();
            return;
        }
        const sessionData =
            (!config.clearMarketingStorage || resizeBrowser) &&
            self.getStorageData(config.storageName.marketing);
        if (sessionData) {
            self.mktgSuccessCB(sessionData.productGridMareketingData);
            return;
        }
        const payload = apiData("products")["getESpot"].apply({
            queryString: config.marketingParam
        });
        request(payload)
            .then(data => {
                const res = typeof data == "string" ? JSON.parse(data) : data;
                self.setStorageData(config.storageName.marketing, res);
                self.mktgSuccessCB(res.productGridMareketingData);
            })
            .catch(error => {
                self.mktgFailureCB();
            });
    }
    getCategoryData() {
        if (config.categoryDataOFF) {
            return;
        }
        const sessionData = self.getStorageData(config.storageName.category);
        if (sessionData) {
            if (!config.isFPPage) {
                self.categoryCB(sessionData.category);
                return;
            }
        }
        const payload = apiData("products")["getCategoryList"].apply({
            queryString: config.categoryParam
        });
        request(payload)
            .then(data => {
                const res = typeof data == "string" ? JSON.parse(data) : data;
                self.setStorageData(config.storageName.category, res);
                if (!config.isFPPage) {
                    self.categoryCB(res.category);
                }
            })
            .catch(error => {
                console.log("Category service failed.please try again..");
            });
    }
    categoryCB(res) {
        renderCategoryTemplate(res || []);
        const parentCategoryName =
            $("#product-category-list .submenu a").html() || datasets.categorytype;
        const subCategoryName = $("#product-category-list .active").html();
        categoryForTracking = subCategoryName ?
            `${parentCategoryName}:${subCategoryName}` :
            parentCategoryName;
    }
    setStorageData(storageName, res) {
        if (!storageName) {
            return;
        }
        if (!res) {
            return;
        }
        res.categoryName = datasets.snpParam;
        setStorage(storageName, res);
    }
    getStorageData(storageName) {
        const sessionData = getStorage(storageName);
        if (sessionData && sessionData.categoryName == datasets.snpParam) {
            return sessionData;
        }
        return false;
    }
    mktgSuccessCB(response) {
        if (!self.productResponse) {
            setTimeout(a => {
                waitAPIRes++;
                if (waitAPIRes > 25) {
                    console.log("S&P service failed.please try again..");
                    self.enableLoading(false);
                    return;
                }
                self.mktgSuccessCB(response);
            }, 1500);
            return;
        }
        promoGridColCnt = 0;
        self.promoResponse = response;
        const rowSorting = _.sortBy(
            self.promoResponse || response,
            item => item[self.deviceName]["rowNo"]
        );
        self.promoUpdatedData = self.setMarketingPosition(rowSorting);
        if(typeof videojs != "function" && self.promoUpdatedData.length){
            const isDeluxeVideoExist = _.filter(self.promoUpdatedData,(item)=>{
                return item[self.deviceName]['displayObject']['videoType'] == "deluxe";
            });
            if(isDeluxeVideoExist.length){
                if(!window.global.initDeluxePlayer){
                    console.log(`%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.`, "background: red; color:white");
                } else{
                    window.global.initDeluxePlayer(undefined, true);
                }
                handleBarsHelperInst.callRegisterHelper("getDeluxePoster");
            }
        }
        self.mergingResponse();
    }
    mktgFailureCB() {
        self.promoResponse = [];
        self.mktgSuccessCB(self.promoResponse);
    }
    errorCB() {
        self.redirectToErrorPage();
    }
    setMarketingPosition(res) {
        let checkMultipleInaRow = [],
            currentDeviceObj;
        return _.each(res, item => {
            currentDeviceObj = item[self.deviceName];
            currentDeviceObj["displayObject"].type = "promo";
            currentDeviceObj["displayObject"].columnClassName = self.setColClassName(
                item
            );
            self.checkIFPromoImageNull(item, self.deviceName);
            item.spanLength = currentDeviceObj.spanLength;
            item.index =
                self.getMktgPosition(item)[`${self.deviceName}Pos`] -
                (checkMultipleInaRow.indexOf(currentDeviceObj.rowNo) != -1 ?
                    1 :
                    promoGridColCnt);
            promoGridColCnt += item.spanLength - 1;
            checkMultipleInaRow.push(currentDeviceObj.rowNo);
        });
    }
    checkIFPromoImageNull(obj, deviceName) {
        if (deviceName == "tablet") {
            let {
                displayObject
            } = obj[deviceName];
            if (
                displayObject &&
                displayObject.mediaType == "image" &&
                !displayObject.promoImage
            ) {
                displayObject.promoImage =
                    typeof obj["desktop"] == "object" &&
                    obj["desktop"].displayObject.promoImage;
            }
        }
        return;
    }
    getCorrectColValue(val, deviceName) {
        return (gridDefaultCol / config[`${deviceName}Col`]) * val;
    }
    setColClassName(res) {
        return `col-xs-${self.getCorrectColValue(
            res.mobile.spanLength,
            "mobile"
        )} col-sm-${self.getCorrectColValue(
            res.tablet.spanLength,
            "tablet"
        )} col-lg-${self.getCorrectColValue(res.desktop.spanLength, "desktop")}`;
    }
    getMktgPosition(res) {
        _.each(deviceNames, item => {
            res[`${item}Pos`] =
                config[`${item}Col`] * (res[item].rowNo - 1) + (res[item].columnNo - 1);
        });
        return res;
    }
    mergingResponse(lazyLoadIndx) {
        let matchedItem,
            newObj = [],
            mergeData = self.productResponse.concat(self.promoResponse),
            cnt = 0;
        lazyLoadIndx = self.productResponse.seeMoreAction ?
            self.productResponse.nextIndex :
            0;
        _.map(mergeData, (item, indx) => {
            matchedItem = _.findWhere(self.promoUpdatedData, {
                index: lazyLoadIndx
            });
            if (matchedItem) {
                newObj.push(matchedItem[self.deviceName].displayObject);
            } else if (self.productResponse[cnt]) {
                newObj.push(
                    checkAnyFeaturedPrice(self.productResponse[cnt], self.priceObjName)
                );
                cnt++;
            }
            lazyLoadIndx++;
        });
        self.mergedResponse = config.isAuthorModeON ?
            promoDateFilter(newObj, self.priceObjName) :
            newObj;
        if (config.isAuthorModeON && _.isEmpty(self.mergedResponse)) {
            if (!$("#see-more").hasClass("hide")) {
                $("#see-more button")[0].click();
                return;
            }
        }
        renderGridTemplate(
            self.generalResponse,
            self.mergedResponse,
            filteredParam ? true : false,
            resizeBrowser,
            self.priceObjName,
            self.gridSchema,
            self.productResponse
        );
        let cacheFilterParams = getStorage(config.storageName.cacheParam);
        if (cacheFilterParams && cacheFilterParams.activeProductId) {
            $(
                `#product-grid-container li[data-partno=${cacheFilterParams.activeProductId}] a.product-image`
            ).focus();
            cacheFilterParams.activeProductId = "";
            setStorage(config.storageName.cacheParam, cacheFilterParams);
        }
        self.enableLoading(false);
        if (config.isFPPage) {
            typeof getImpressionVariable == "function" && getImpressionVariable();
        }
        self.trackingParams();
    }
    redirectToErrorPage() {
        if (!config.isSearchModeON && config.errorPageUrl) {
            window.location.href = config.errorPageUrl;
        }
    }
    redirectSearchPage(redirect) {
        window.location.href = redirect;
    }
    redirectToDetailsPage(urlParam) {
        window.location.href =
            "//" + window.location.hostname + "/shop/p/" + urlParam;
    }
    getActivePriceName() {
        const { customerSegment,employeePrice} = window.global.getUserCookie();
        if (employeePrice == true || employeePrice == "true") {
            if (customerSegment) {
                return "employee_loyalty_price";
            }
            return "employee_price";
        } else if (customerSegment == "SILVER" || customerSegment == "GOLD" ||customerSegment == "BERRY") {
            return "loyalty_price";
        } else {
            return "sale_price";
        }
    }
    getFilteredElem() {
        let $ele = $(".checkbox-cont fieldset input:checkbox:checked");
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
    showClearBtn() {
        let $checkedItems = $(".checkbox-cont fieldset input:checkbox:checked");
        let $curElem = $(".clear-all-filters, .clear-wrapper");
        if (
            $checkedItems.length ||
            (filteredParam && filteredParam.indexOf("sort") != -1)
        ) {
            $curElem.removeClass("hide");
            if (config.isFPPage) {
                $(".filter-header").removeClass("clearAllShow");
            }
        } else {
            $curElem.addClass("hide");
            if (config.isFPPage) {
                $(".filter-header").addClass("clearAllShow");
            }
        }
    }
    resizingPromoGrid() {
        resizeBrowser = true;
        onFilterResizeAction();
    }
    bindingResize() {
        let resizedDeviceName;
        $(window).resize(
            _.debounce(() => {
                resizedDeviceName = getDeviceName();
                if (self.deviceName != resizedDeviceName) {
                    self.deviceName = resizedDeviceName;
                    self.resizingPromoGrid();
                    resizeBrowser = false;
                }
            }, 500)
        );
    }
    getViewedCount(elem, obj) {
        if (!obj.categoryName) {
            obj.categoryName = categoryForTracking;
        }
        if (!obj.totalCnt) {
            obj.totalCnt = parseInt(self.totalProductCnt || 0);
        }
        let $ele = $(elem),
            scrollTop = $(window).scrollTop(),
            $listElem = $ele.find(">li:not(.scroll-loaded)");
        if (!$listElem.length) return;
        let liPosition = $listElem.filter(function () {
            return (
                $(this).offset().top + $(this).outerHeight(!0) / 2 <=
                scrollTop + $(window).height()
            );
        });
        liPosition.addClass("scroll-loaded");
        obj.viewedCnt = $ele.find(">li.scroll-loaded").length;
        if (liPosition.length && typeof gridScroll == "function") {
            gridScroll(obj);
            return false;
        }
    }
    trackingParams() {
        let timer;
        const $gridELem = "#product-grid-container",
            obj = {
                categoryName: categoryForTracking,
                columnVal: config[`${self.deviceName}Col`],
                totalCnt: parseInt(self.totalProductCnt || 0),
                viewedCnt: ""
            };
        self.getViewedCount($gridELem, obj);
        if (trackingParamsInit) return;
        trackingParamsInit = true;
        $(window).on("scroll", () => {
            if (timer) clearTimeout(timer);

            timer = setTimeout(() => {
                self.getViewedCount($gridELem, obj);
                !holdScroll &&
                    lazyLoadGrid(
                        self.mergedResponse,
                        self.totalPageCount,
                        pageNo,
                        self.productResponse
                    );
            }, 50);
        });
    }
    schemaFields(schemaName) {
        let schema = [{
            article: {
                readMore: true,
                socialIcons: true,
                rating: false,
                price: false,
                title: false,
                inventoryStatus: false,
                promoLabel: false,
                tagLabel: true,
                quickView: false
            },
            product: {
                readMore: false,
                socialIcons: false,
                rating: true,
                price: true,
                title: true,
                promoLabel: true,
                inventoryStatus: true,
                tagLabel: false,
                quickView: true
            }
        }];
        return schema[0][schemaName] || {};
    }
}

let self,
    categoryForTracking,
    trackingParamsInit = false,
    waitAPIRes = 0, // checking response time
    promoGridColCnt = 0,
    filteredParam,
    gridDefaultCol = 12, // bootstrap grid count
    resizeBrowser = false,
    holdScroll = true,
    pageNo = 1,
    deviceNames = ["desktop", "tablet", "mobile"],
    sAndPcalledFrom = "",
    articleDateLoaded = false,
    plpSearchParam = "",
    isSearchTriggered = false,
    giftAgeParam = "",
    giftPriceParam = "",
    filterLabelNames = [],
    cacheParamTriggered = false;

const request = new ajaxRequest().ajaxCall;
const apiData = new apiConfig().getApiConfig;
const handleBarsHelperInst = new handleBarsHelper();
const evtBinding = new eventBinding();
const gridActionInst = new gridActions(config);
const productLandingInst = new productLanding();
