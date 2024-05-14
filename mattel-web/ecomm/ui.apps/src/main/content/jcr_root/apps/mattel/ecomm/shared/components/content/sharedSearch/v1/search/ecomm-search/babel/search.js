import eventBinding from '../shared/eventBinding';
import { handleBarTemplate } from '../shared/templateSetter';
import { setCookie } from '../shared/browserCookie';
const config = {
    searchUrl: (() => {
        let searchVal = $("#snpEP").val() || "";
        let isAEMPage = !$(".aem-target-header").length;
        if(isAEMPage){
            return searchVal;
        } else if(searchVal.indexOf('prod_preview')!= -1){
            return searchVal.replace(/_preview/g,"").replace(/stage-/g,"")
        }
        return searchVal.replace(/_preview/g,"");
    })(),
    relatedProductUrl: $("#taEP").val(),
    typeHint: true,
    typeHighlight: true,
    typeMinLength: parseInt($("#characterLimit").val()),
    typeMaxLength: false,
    searchList: 6,
    searchLimit: parseInt($("#suggestionLimit").val()),
    DisMaxItem: 6,
    typeOrder: "desc",
    typeDelay: 300,
    cache: true,
    detailUrlPrefix: location.protocol + '//' + location.hostname, //window.location.href.split('?')[0],
    action: $("#searchFormInput").attr("action"),
    sType: $("#sType").val(),
    showResultsPage: $(".showResultsPage").val(),
    searchSource: $(".searchSource").val(),
    pageSize: parseInt($(".pageSize").val()),
    storeId: parseInt($(".storeId").val()),
    catalogId: parseInt($(".catalogId").val()),
    langId: parseInt($(".langId").val()),
    resultCatEntryType: parseInt($(".resultCatEntryType").val()),
    pageView: $(".pageView").val(),
    beginIndex: parseInt($(".beginIndex").val()),
    currentPagePath: location.pathname,
    pageName: location.pathname.indexOf('explore') != -1 ? 'article' : 'product',
    searchContainerEle: "#popularProducts",
    searchCookieName: "SEARCH_KEYWORD_ACTION",
    tileFallbackImage: $("#tileFallbackImage").val(),
    StoerdarticleResults: []
};


class mySearch {
    constructor() {
        self = this;
        evtBinding.bindLooping(self.bindingEventsConfig(), self);
        self.getDefaultSearchValues();
        self.init();
        self.typeahedhData();
        self.apiData = [];
        self.searchBox = $(".search-dropdown, .search-overlay");
        self.searchKey = $('.nav-search #plpSearchTxt');
        self.hamBurgerSearch = $('.ham-view #plpSearchTxt');
        self.emptySearch = false;
    }

    getApiConfig(name) {
        let obj = {
            "searchResult": {
                "url": config.searchUrl + 'search=product&q=' + self.globalSearch + '*&count=6&page=1',
                "body": "",
                "type": "POST",
                "dataType": 'json',
                "beforeSend": self.loaderAddRemove(true)
            },

            "relatedProduct": {
                "url": config.relatedProductUrl,
                "type": "GET"
            }

        }
        return obj[name];
    }
    bindingEventsConfig() {
        let eventsArr = {
            "click .expand-search-bar": "expandSearchbar",
            //Desktop Only
            "keyup .nav-search #searchFormInput input": "inputChange",
            "click .nav-search #searchFormInput .search-clear-icon": "inputChange",
            //Mobile Only
            "keyup .ham-view #searchFormInput input": "inputChangeMob",
            "click .ham-view #searchFormInput .search-clear-icon": "inputChangeMob",
            //Search Content Clr
            "keyup #mobSearchFormInput input": "ham_search",
            "click #mobSearchFormInput .search-clear-icon": "ham_search",
            //Search Submit
            "submit #searchFormInput": "searchSubmit",
            //Search Dropdown Display
            "focus .nav-search #plpSearchTxt": "searchDropdownDisplay",
            "click .search-overlay": "searchDropdownClose",
            "click .aghamburger": "searchDropdownClose",
            "mouseup .main-nav-container": "searchDropdownCloseonHeader",
            //Related
            "mouseover #popularSearch li": "hoverGetParams",
            "focus #popularSearch li": "hoverGetParams",
            "keydown #popularSearch li": "hoverGetParams",
            "click #popularSearch li": "searchSubmit",
            //Categories
            "mouseover #staticCategory li": "hoverGetCategory",
            "focus #staticCategory li": "hoverGetCategory",
            "keydown #staticCategory li": "hoverGetCategory",
            "click #staticCategory li": "searchSubmit",
            //Article
            "mouseover #staticArtical li": "hoverGetArtical",
            "focus #staticArtical li": "hoverGetArtical",
            "keydown #staticArtical li": "hoverGetArtical",
            //Accessibility
            "keydown .left-tiles #popularProducts li.search_result a": "leftTilesTabBound",
            "click .search-dropdown .search_result .product-image": "trackImageGrid",
            //On promotion click Close
            "click .toggle-drawer-section": "searchDropdownClose",
            //Mobile Search Only
            "click .header-spark-menu": "mobileSeacrhOpenClose",
            "click .tt-suggestion": "searchSubmit",
        };
        return eventsArr;
    }
    trackImageGrid(ele) {
        let hrefLink = $(ele).attr('href');
        setCookie(config.searchCookieName, JSON.stringify({ searchVal: hrefLink, actionName: 'visual_search' }));
    }
    expandSearchbar(ele, evt) {
        let datasets = ele.dataset;
        let $targetElem = $(`#${datasets.toggle}`);
        if (!$targetElem.length) {
            return;
        }
        $targetElem.removeClass("hide").addClass("in");
        $targetElem.find('input[type="text"]').focus();
        self.equalHeight();

    }
    inputChange(ele, evt, mob = 0) {
        let formEle = $(ele).closest("#searchFormInput");
        let hasdata = $(ele).val();
        let topSearch = self.searchKey.val();
        if (topSearch != "") {
            $("#staticCategory").show();
        } else {
            $("#staticCategory").hide();
        }
        hasdata ? formEle.addClass("has-data") : formEle.removeClass("has-data");

        if (hasdata.length === (config.typeMinLength - 1) && evt.keyCode === 8) {
            self.globalSearch = '';
            self.getDefaultSearchValues();
        } else if (hasdata.length >= config.typeMinLength) {
            const relatedChange = 1;
            let relatedQuery = self.searchKey.val();
            self.globalSearch = relatedQuery.trim("");
            self.getDefaultSearchValues(relatedChange, self.globalSearch);
        } else {
            if ($(ele).hasClass('search-clear-icon')) {
                self.globalSearch = '';
                $('.typeahead').typeahead('val', '');
                self.getDefaultSearchValues();
            }
            if (evt.keyCode === 13) {
                self.searchSubmit('', evt);
            }
        }


        if (hasdata.length >= config.typeMinLength) {
            handleBarTemplateInst.loadTemplate('#topsearchTemp', '#topsearchTypeahed', topSearch, 'replace');
        } else {
            handleBarTemplateInst.loadTemplate('#topsearchTemp', '#topsearchTypeahed', '', 'replace');
        }
    }
    inputChangeMob(ele, evt) {
        const mob = 1;
        self.inputChange(ele, evt, mob);
        if ($(".tt-menu").is(':visible')) {
            $(ele).addClass("search-active");
            $(".tt-menu").find('.suggestion-keyword').remove();
            $(".tt-menu").prepend('<div class="suggestion-keyword">Suggested Keywords</div>');
        } else {
            $(ele).removeClass("search-active");
        }
    }
    mobileSeacrhOpenClose(ele, evt) {
        if ($(".tt-menu").is(':visible')) {
            $(".ham-view #plpSearchTxt").addClass("search-active");
        } else {
            $(".ham-view #plpSearchTxt").removeClass("search-active");
        }
    }
    ham_search(ele) {
        let $formEle = $(ele).closest("#mobSearchFormInput");
        let hasdata = $(ele).val();
        hasdata ? $formEle.addClass("has-data") : $formEle.removeClass("has-data");
    }    
    searchSubmit(ele, evt) {
        if (!self.hamBurgerSearch.length) {
            self.hamBurgerSearch = $('.ham-view #plpSearchTxt');
        }
        let searchKeyword = ele.children[0].innerText;
        let encodedKeyword = encodeURIComponent(searchKeyword);
        if (ele.className === "tt-popular tt-cursor") {
            evt.preventDefault();
            $.getJSON(config.searchUrl + 'search=product&q=' + encodedKeyword + "&count=" + config.pageSize + "&page=1", function(res) {
                sessionStorage.setItem("spGridData", JSON.stringify(res));
                let redirectURL = res.general.redirect;
                if (redirectURL === "" || redirectURL === undefined) {
                    redirectURL = config.action + "?storeId=" + config.storeId + "&catalogId=" + config.catalogId + "&langId=" + config.langId + "&sType=" + config.sType + "&resultCatEntryType=" + config.resultCatEntryType + "&showResultsPage=" + config.showResultsPage + "&searchSource=" + config.searchSource + "&pageView=" + config.pageView + "&beginIndex=" + config.beginIndex + "&pageSize=" + config.pageSize + "&searchTerm=" + encodedKeyword + "&searchCategory=" + config.pageName;
                }
                setCookie(config.searchCookieName, JSON.stringify({ searchVal: encodedKeyword, actionName: 'typeahead search' }));
                window.location = redirectURL;
            }).fail(function(error) {
                console.log(error);
            });
        } else {
            evt.preventDefault();
            if (self.searchKey.is(":visible")) {
                searchKeyword = self.searchKey.val();
            } else if (self.hamBurgerSearch.is(":visible")) {
                searchKeyword = self.hamBurgerSearch.val();
            } else {
                searchKeyword = $('#plpSearchTxt').val();
            }            
            if (searchKeyword != "") {
                encodedKeyword = encodeURIComponent(searchKeyword);
                $.getJSON(config.searchUrl + 'search=product&do=' + encodedKeyword + '&q=' + encodedKeyword + "&count=6&page=1", function(res) {
                    let redirectURL = res.general.redirect;
                    if (redirectURL === "" || redirectURL === undefined) {
                        redirectURL = config.action + "?storeId=" + config.storeId + "&catalogId=" + config.catalogId + "&langId=" + config.langId + "&sType=" + config.sType + "&resultCatEntryType=" + config.resultCatEntryType + "&showResultsPage=" + config.showResultsPage + "&searchSource=" + config.searchSource + "&pageView=" + config.pageView + "&beginIndex=" + config.beginIndex + "&pageSize=" + config.pageSize + "&searchTerm=" + encodedKeyword + "&searchCategory=" + config.pageName;
                    }
                    setCookie(config.searchCookieName, JSON.stringify({ searchVal: encodedKeyword, actionName: 'user entered search' }));
                    window.location = redirectURL;
                }).fail(function(error) {
                    console.log(error);
                });
            }
        }


    }
    searchDropdownDisplay(ele) {
        if (ele && !self.emptySearch) {
            self.getheaderHeight();
            if ($(".promo-header-dropdown").hasClass('in')) {
                $(".promo-header-dropdown").collapse('hide');
            }
            self.searchBox.addClass("open-dropdown");
            $("body").addClass("search-open");
            $(".top-search-typeahed").hide();
        }
    }
    getheaderHeight() {
        let marginHeight = 0;
        if (!$('.non-sticky-header').length) {
            const headerWrapperHeight = $("#header-wrapper");
            let headerHeight = headerWrapperHeight.height();
            self.searchBox.css('top', headerHeight + 'px');
            if ($(".promoPencilContainer+.navigation").length) {
                let promoBannerHeight = $('.promoPencilContainer').height();
                let scrollheight = $(window).scrollTop();
                if (scrollheight < promoBannerHeight) {
                    marginHeight = promoBannerHeight - scrollheight;
                }
            }
            if ($('.header-sticky-active').length) {
                marginHeight = 0;
                self.searchBox.css('margin-top', marginHeight + 'px');
            } else {
                self.searchBox.css('margin-top', marginHeight + 'px');
                if ($("#contentRecommendationWidget_AGHeaderEspot1").length) {
                    self.searchBox.css({ 'margin-top': 0, 'top': $("#header-wrapper")[0].getBoundingClientRect().bottom || 0 });
                }
            }
        }
    }

    searchDropdownClose(ele, evt) {
        const navSearchInput = $(".nav-search #searchFormInput");
        const navSearchButton = $(".nav-search .expand-search-bar");
        self.searchBox.removeClass("open-dropdown");
        $("body").removeClass("search-open");
        navSearchInput.removeClass("in");
        navSearchInput.addClass("hide");
        navSearchButton.removeClass("hide");
    }

    searchDropdownCloseonHeader(ele, evt) {
        const nearestForm = $(evt.target).closest("form").length;
        const searchResult = $(evt.target).closest(".search-dropdown-innerwrapper").length;
        if (nearestForm==0 && searchResult==0) {
            self.searchDropdownClose();
        }
    }

    loaderAddRemove(flag) {
        flag ? $(".loader-outer").addClass("visible") : $(".loader-outer").removeClass("visible");
    }

    emptyElement(search = 0, hover = 0) {
        if (search === 0) {
            $("#popularSearch").html('');
        }
        $("#popularProducts").html('');
        if (!hover) {
            $("#staticCategory").html('');
            $("#staticArtical").html('');
        }
    }
    getDefaultSearchValues(search = 0, globalSearch = '') {
        self.globalSearch = globalSearch;
        const apiconfig = self.getApiConfig('searchResult');
        let productType = {};
        $.ajax(apiconfig)
            .done(response => {
                self.apiData = response.resultsets;
                self.itemcount = response.resultcount.total
                self.searchDisplayHTML(response.resultsets, search);
                $('.search-dropdown .left-tiles .search-col-1').attr('data-list', self.itemcount);
                self.loaderAddRemove();
                self.focuscontrol();
                self.apiData && _.each(self.apiData[0].results, (item) => {
                    productType[item.PartNumber] = {
                        product_type: item.product_type,
                        itemType: item.itemType || false,
                        DisableQuickView: item.DisableQuickView || false
                    }
                });
                productType && sessionStorage.setItem("recommendedProductTypes", JSON.stringify(productType));
            })
            .fail(err => {})
    }
    focuscontrol() {
        if (!self.hamBurgerSearch.length) {
            self.hamBurgerSearch = $('.ham-view #plpSearchTxt');
        }
        // const triggerOnPageLoad = self.searchKey.closest('form').hasClass('hide');
        // if (self.searchKey.is(":visible") && !triggerOnPageLoad) {
        if (self.searchKey.is(":visible")) {
            self.searchKey.get(0).focus();
        } else if (self.hamBurgerSearch.is(":visible")) {
            self.hamBurgerSearch.get(0).focus();
        }
    }
    searchDisplayHTML(obj, search = 0, hover = 0) {
        self.emptyElement(search, hover);
        let categories = [];
        const usedUrl = config.detailUrlPrefix;
        const concatUrl = '/shop/p/';
        const formattedUrl = usedUrl + concatUrl;
        //Left Side Product Display
        let listedproduct = config.pageName == "article" ? obj[1].results : obj[0].results;
        listedproduct = listedproduct.filter((_, index) => index < config.searchList).map(results => {
            return _.extend({}, results, {
                isScene7Img: (results.imageLink || "").indexOf("scene7") != -1 ? true : false,
                urlFormatted: formattedUrl,
                titleFormatted: self.textAbstract(results.title, 8),
                priceFormated: self.getSelePrice(config.pageName == "article" ? [] : results.prices),
                fallbackImage: config.tileFallbackImage,
            });
        });
        handleBarTemplateInst.loadTemplate('#topProductTemp', '#popularProducts', listedproduct, '');
        //Related Product Display
        let populartemp = obj[0].results;
        populartemp = populartemp.filter((_, index) => index < config.searchLimit).map(results => {
            return _.extend({}, results, {
                titleFormatted: self.textAbstract(results.title, 10)
            });
        });
        if (search === 0) {
            if(config.pageName == "article"){
                handleBarTemplateInst.loadTemplate('#relatedHeaderTemp', '#searchListExplore #relatedHeader', populartemp, '');
                handleBarTemplateInst.loadTemplate('#popularTemp', '#searchListExplore #popularSearch', populartemp, '');
                $("#searchListAg").html('');
            }else{
                handleBarTemplateInst.loadTemplate('#relatedHeaderTemp', '#searchListAg #relatedHeader', populartemp, '');
                handleBarTemplateInst.loadTemplate('#popularTemp', '#searchListAg #popularSearch', populartemp, '');
                $("#searchListExplore").html('');
            }
        }
        //Artical Display
        if (hover) {
            //If required condition will go here
        } else {
            let articleResults = obj[1].results;
            articleResults = articleResults.filter((_, index) => index < config.searchLimit).map((article, index) => {
                return _.extend({}, article, {
                    titleFormatted: self.textAbstract(article.title, 8),
                    id: index
                });
            });
            config.StoerdarticleResults = articleResults;
            if(config.pageName == "article"){
                handleBarTemplateInst.loadTemplate('#articalTemp', '#searchListExplore #staticArtical', articleResults, '');
                $("#searchListAg").html('');
            }else{
                handleBarTemplateInst.loadTemplate('#articalTemp', '#searchListAg #staticArtical', articleResults, '');
                $("#searchListExplore").html('');
            }
        }

        //Filtering Display
        if (!hover || self.globalSearch != '') {
            let categoriesResult = obj[0].results;
            categoriesResult = categoriesResult.filter((_, index) => index < config.searchLimit).map(results => {
                return _.extend({}, results);
            });
            categoriesResult.forEach((item) => {
                if (item.category) {
                    categories.push(item.category);
                }
            });
            // Filtering unique categories
            categories = categories.filter((value, index) => categories.indexOf(value) === index);
            categories = categories.filter((_, index) => index < config.searchLimit).map(results => {
                return _.extend({}, { results });
            });
            self.displayCategories(categories);
        }
        self.equalHeight();
    }

    getSelePrice(priceArray) {
        let salesPriceObj = null;
        priceArray.forEach(item => {
            let elm = item;
            if (elm && elm.sale_price) {
                if (elm.sale_price[0].price && parseInt(elm.sale_price[0].price) > 0) {
                    salesPriceObj = elm.sale_price[0].price;
                } else {
                    salesPriceObj = '';
                }
            }
        })
        return salesPriceObj;
    }

    textAbstract(sentence, wordLength) {
        if (!sentence) {
            return "";
        }

        // split the sentence into words
        const words = sentence.split(" ");

        // wordLength - Max no of allowed words
        // If sentence contains less or equal words - return sentence
        if (words.length <= wordLength) {
            return words.join(" ");
        } else {
            const newWordsList = [];
            for (let count = 0; count < wordLength; count++) {
                newWordsList.push(words[count]);
            }
            return newWordsList.join(" ") + "...";
        }
    }
    displayCategories(categories) {
            // handleBarTemplateInst.loadTemplate('#categoryTemp', '#staticCategory', categories, '');
        }
        //Typeahed
    typeahedhData() {
        const searconfig = self.getApiConfig('relatedProduct');
        $.ajax(searconfig)
            .done(response => {
                const res = response.replace(/[\(\)\"\[\]{}]/g, "").split(',');
                const result = res.map(el => el.trim());
                self.typeahedSearchResult(result);
            })
            .fail(err => {})
    }
    typeahedSearchResult(result) {
        $('.typeahead').typeahead({
            hint: config.typeHint,
            highlight: config.typeHighlight,
            minLength: config.typeMinLength,
            maxLength: config.typeMaxLength,
            maxItem: config.searchList,
            items: config.searchList,
            order: config.typeOrder,
            delay: config.typeDelay,
            cache: config.cache,
        }, {
            name: 'result',
            limit: config.searchLimit,
            source: self.typeahedsubstringMatcher(result),
            templates: {
                empty: ['<span class="league-name"> Related product not found</span>'].join('\n'),
            }

        });
    }
    typeahedsubstringMatcher(strs) {
            return function findMatches(q, cb) {
                let matches;
                // an array that will be populated with substring matches
                matches = [];
                const related = [];
                $('#popularSearch').html('');
                // regex used if a string contains the substring `q`
                q = q.trim("");
                self.substrRegex = new RegExp(q, 'i');
                // contains the substring `q`, add it to the `matches` array
                $.each(strs, function(i, str) {
                    if (self.substrRegex.test(str)) {
                        matches.push(str);
                        if (related.length < config.searchLimit) {
                            related.push({
                                "title": str,
                                "titleFormatted": self.textAbstract(str, 10)
                            });
                        }
                    }
                });
                const mobileinput = $(".ham-view");
                if (!mobileinput.is(":visible")) {
                    if (related.length) {
                        self.searchBox.addClass("open-dropdown");
                        $("body").addClass("search-open");
                        self.emptySearch = false;
                    } else {
                        self.searchBox.removeClass("open-dropdown");
                        $("body").removeClass("search-open");
                        self.emptySearch = true;
                    }
                }
                if(config.pageName == "article"){
                    handleBarTemplateInst.loadTemplate('#popularHeaderTemp', '#searchListExplore #relatedHeader', related, 'replace');
                    handleBarTemplateInst.loadTemplate('#popularTemp', '#searchListExplore #popularSearch', related, 'replace');
                    $("#searchListAg").html('');
                }else{
                    handleBarTemplateInst.loadTemplate('#popularHeaderTemp', '#searchListAg #relatedHeader', related, 'replace');
                    handleBarTemplateInst.loadTemplate('#popularTemp', '#searchListAg #popularSearch', related, 'replace');
                    $("#searchListExplore").html('');
                }
                cb(matches);
            };
        }
        //Hover
    hoverGetParams(ele, evt) {
        const active = "tt-cursor";
        const currentActive = ".tt-popular.tt-cursor";
        const currentActiveText = ".tt-popular.tt-cursor a";
        const hoverParam = 1;
        $(currentActive).removeClass(active);
        $(ele).addClass(active);
        const query = encodeURIComponent($(currentActiveText).data("title"));
        if (query != "undefined") {
            if (self.globalSearch !== query) {
                self.hoverRelatedProductDisplay("searchResult", $.trim(query), hoverParam)
            } else {
                const relatedChange = 1;
                self.searchDisplayHTML(self.apiData, relatedChange, hoverParam);
            }
        }

        //Added for Accessibility
        if (evt.keyCode === 37) {
            self.focusToLeftFirstChild();
        }
    }
    hoverGetCategory(ele, evt) {
        const active = "tt-cursor";
        const currentActive = ".tt-popular.tt-cursor";
        const currentActiveText = ".tt-popular.tt-cursor a";
        const hoverParam = 1;

        $(currentActive).removeClass(active);
        $(ele).addClass(active);
        const query = $(currentActiveText).text();
        //self.hoverDisplayCategories(query);
        if (self.globalSearch !== query) {
            self.hoverRelatedProductDisplay("searchResult", $.trim(query), hoverParam)
        } else {
            const relatedChange = 1;
            self.searchDisplayHTML(self.apiData, relatedChange, hoverParam);
        }

        //Added for Accessibility
        if (evt.keyCode === 37) {
            self.focusToLeftFirstChild();
        }
    }
    hoverDisplayCategories(queryText) {
        const usedUrl = config.detailUrlPrefix;
        const concatUrl = '/shop/p/';
        const formattedUrl = usedUrl + concatUrl;
        const product = $(config.searchContainerEle);
        const details = self.apiData[0].results;
        let artical = [];
        details.forEach((item) => {
            if (item.category === queryText) {
                artical.push(item);
            }
        });
        product.html('');
        artical = artical.filter((_, index) => index < config.searchList).map(results => {
            return _.extend({}, results, {
                isScene7Img: (results.imageLink || "").indexOf("scene7") != -1 ? true : false,
                urlFormatted: formattedUrl,
                titleFormatted: self.textAbstract(results.title, 8),
                priceFormated: self.getSelePrice(results.prices),
                fallbackImage: config.tileFallbackImage,
            });
        });
        handleBarTemplateInst.loadTemplate('#topProductTemp', '#popularProducts', artical, '');
        self.equalHeight();
    }
    equalHeight() {
        let productListHeight = []
        $("#popularProducts li").each(function() {
            productListHeight.push($(this).outerHeight())
        });
        if (productListHeight != 0) {
            productListHeight.sort();
        }
        $("#popularProducts li").css("min-height", productListHeight[productListHeight.length - 1]);
    }
    hoverRelatedProductDisplay(apiUrl, query, hoverParam = 0) {
        self.globalSearch = query;
        const apiconfig = self.getApiConfig(apiUrl);
        $.ajax(apiconfig)
            .done(response => {
                self.apiData = response.resultsets;
                self.itemcount = response.resultcount.total
                self.searchDisplayHTML(response.resultsets, 1, hoverParam);
                $('.search-dropdown .left-tiles .search-col-1').attr('data-list', self.itemcount);
                self.loaderAddRemove();
            })
            .fail(err => {})
    }
    hoverGetArtical(evt) {
        $(".tt-popular.tt-cursor").removeClass("tt-cursor");
        $(evt).addClass("tt-cursor");
        self.hoverDisplayArticleDetails($(evt).data("index"));
    }
    hoverDisplayArticleDetails(index) {
        if (!isNaN(index)) {
            const details = config.StoerdarticleResults[index];
            const product = $("#popularProducts");
            details.fallbackImage = config.tileFallbackImage;
            product.html('');
            handleBarTemplateInst.loadTemplate('#articalProductTemp', '#popularProducts', details, '');
        }
    }
    focusToLeftFirstChild() {
        $(".left-tiles #popularProducts li:first-child .sr_image a").focus();
    }
    focusToLeftLastChild() {
        $(".left-tiles #popularProducts li:last-child .sr_text a").letTiles.focus();
    }
    leftTilesTabBound(ele, evt) {
        let leftPanelLength = ($("#popularProducts li").length - 1);

        if (evt.keyCode === 39) {
            const letTiles = $(".search-list .tt-popular.tt-cursor a");
            letTiles.focus();
        }

        if (evt.keyCode === 9) {
            if ($(ele).parent().hasClass("sr_text") && $(ele).parent().parent().index() === leftPanelLength)
                self.focusToLeftFirstChild();
        }
        if (evt.shiftKey && evt.keyCode === 9) {
            if ($(ele).parent().hasClass("sr_image") && $(ele).parent().parent().index() === 0)
                self.focusToLeftLastChild();
        }
    }

    init() {
        $(window).on('resize scroll load', function() {
            if ($(".search-dropdown.open-dropdown").length) {
                self.getheaderHeight();
            }
        });
        $('.promo-header-dropdown').on('hidden.bs.collapse', function() {
            if ($(".search-dropdown.open-dropdown").length) {
                setTimeout(function() {
                    self.getheaderHeight();
                }, 200);
            }
        });
    }
}

let self;
const evtBinding = new eventBinding();
const handleBarTemplateInst = new handleBarTemplate();
$(document).ready(function() {
    const mySearchInstance = new mySearch();
    mySearchInstance.init();
});