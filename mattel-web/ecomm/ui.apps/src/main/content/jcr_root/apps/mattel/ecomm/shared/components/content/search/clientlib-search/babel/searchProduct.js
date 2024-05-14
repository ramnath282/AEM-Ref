/**
 * FP Search Typeahead.js
 * Version 1.0
 */
import eventBinding from '../shared/eventBinding';
import fpApiConfig from '../shared/fpApiConfig';
import ajaxRequest from '../shared/ajaxbinding';


const config = {
    el: '.nav-search',
    diplayCount: $("#searchInput_field").attr('display-count'),
    inputValue: document.getElementById("searchInput_field").value,
    searchapiUrl: document.getElementById("snpAccountUrl").value,
    autoComplete: document.getElementById("aCompleteapiUrl").value,
    relatedSearch: $('.search-con').attr('data-related'),
    popularSearch: $('.search-con').attr('data-popular'),
    displayCount: $('#searchInput_field').attr('display-count'),
    productCount: $('#searchInput_field').attr('product-count'),
    typeHint: true,
    typeHighlight: true,
    typeMinLength: $('#searchSubmit').attr('data-minchar-typeahead') ? parseFloat($('#searchSubmit').attr('data-minchar-typeahead')) : 3,
    typeMaxLength:4,
    searchList: 6,
    DisMaxItem: 4,
    articalDisMaxItem: 3,
    typeOrder: "asn",
    typeDelay: 300,
    globalData: 0,
    globalSearch: '',
    typeCache: true,
    popularArray: [],
    popularTitle: '',
}

class searchProduct {

    constructor() {
        self = this;
        eventBindingInst.bindLooping(self.bindingEventsConfig(), self);

    }

    bindingEventsConfig() {
        var events = {
            "keyup #searchInput_field": "relativeSearchFun",
            "focus #searchInput_field": "defaultDisplay",
            "mouseover .tt-suggestion": "hoverGetParams",
            "click .overlay": "searchDivClose",
            "click .tt-suggestion": "searchPageResult",
            "click .tt-popular": "popularPageResult",
            "click #basic-addon1": "submitValueFun",
            "click .viewall-list": "viewsubmitValueFun",
            "click .close-icon": "defaultSetFun",
            "click .menu-sliding-div-mask": "searchListClose",
            "click .header-close-menu": "searchListClose",
            "click .hamburger-menu": "searchListClose"
        }
        return events;
    }


    searchData() {
        request.ajaxCall(apiConfigInst.getApiConfig('autoSearchResult').call(this, config.DisMaxItem, config.globalSearch))
            .then(data => {
                if (data != null) {

                    var dataArr = data.replace(/[\(\)\"\[\]{}]/g, "").split(',');
                    var result = dataArr.map(function(el) {
                        return el.trim();
                    })
                    $('.typeahead').typeahead({
                        hint: config.typeHint,
                        highlight: config.typeHighlight,
                        minLength: config.typeMinLength,
                        maxLength: config.typeMaxLength,
                        maxItem: config.searchList,
                        items: config.searchList,
                        sorter: config.typeOrder,
                        delay: config.typeDelay,
                        cache: false,

                    }, {
                        name: 'result',
                        source: self.substringMatcher(result),
                        limit: config.DisMaxItem,
                        templates: {
                            suggestion: function(data) {
                                return '<div ><span class="suggestion-text" data-auto-list="' + data + '">' + data + '</span></div>';
                            },
                            footer: function(data) {
                                return '<div class="article-header"><div class="loader">Loading...</div></div>';
                            }
                        }
                    }).on('keyup', function(el) {
                        var txt = $(el.target).val().toLowerCase();
                        var query = $(el.target).val(txt);
                        if (query) {
                            $('.close-x').addClass("icon-show");
                        } else {
                            $('.close-x').removeClass("icon-show");
                        }
                        window._satellite.cookie.set("searchType", "user-entered-search", 0);
                        if (el.which == 13) {
                            self.redirectPDPpage('getRedirectPage', txt);
                        }

                    });
                }

            });
        $('.typeahead').bind('typeahead:cursorchange', function(ev, suggestion) {
            self.relatedProductDisplay("relatedProduct", $.trim(suggestion))
        });

    }

    relativeSearchFun(el) {
        var txt = $('#searchInput_field').val();
        var query = $.trim($(el).val().toLowerCase());
        $('.viewall-list').attr('id', txt);

        if (txt.length >= config.typeMinLength) {
            setTimeout(function() {

                self.relatedProductDisplay("relatedProduct", query);

            }, 100);
        } else if (txt.length <= config.typeMinLength) {
            self.popularProductDisplay();
            $('.search-con').html(config.popularSearch);
            $('#static-search').show();
            $('.viewall-list').hide();
        }

    }
    defaultSetFun() {
        var isMobile = $(window).width() <= 767;
        $('#searchInput_field').typeahead("close");
        self.triggerFun();
        isMobile && $('.search-dropdown').removeClass(' open-dropdown');
        isMobile && $('.sub-header-wrapper').removeClass('menu-hide');
        isMobile && $('.sub-header-wrapper').removeClass('removescroll');
    }
    submitValueFun() {
        var query = $.trim($('#searchInput_field').val());
        if (query.length > 0) {
            self.redirectPDPpage('getRedirectPage', query);
        }
    }

    viewsubmitValueFun(el) {
        var txt = $(el).attr('id');
        var query = $.trim($('#searchInput_field').val(txt));

        if (query.length > 0) {
            self.redirectPDPpage('getRedirectPage', txt);
        }
    }

    searchPageResult(el) {
        window._satellite.cookie.set("searchType", "typeahead", 0);
        self.formSubmittion(el);
    }

    popularPageResult(el) {
        window._satellite.cookie.set("searchType", "popular searches", 0);
        self.formSubmittion(el);
    }

    defaultDisplay(el) {

        $('#searchInput_field').trigger("input").typeahead("open");
        setTimeout(function() {
            $('#searchSubmit').find('.input-group').addClass('focusInput');
            $('.search-dropdown').addClass('open-dropdown');
            // self.triggerFun();
        }, 100);
        $('.search-con').html(config.popularSearch);
        $('#static-search').show();
        $('.sub-header-wrapper').addClass('menu-hide');
        $('.sub-header-wrapper').addClass('removescroll');

    }
    searchListClose(el) {
        $('#searchSubmit').find('.input-group').removeClass('focusInput');
        $('.search-dropdown').removeClass('open-dropdown');
        $('.nav-search').removeClass('popup');
        $('.sub-header-wrapper').removeClass('menu-hide');
        $('.sub-header-wrapper').removeClass('removescroll');
        $('.close-icon').removeClass('icon-show')
        $('.close-div').remove();
        $('.typeahead').val('');
        $('#searchInput_field').typeahead("close");
        self.triggerFun();
    }
    hoverGetParams(el) {
        self.emptyElement();
        $('.tt-suggestion.tt-cursor').removeClass('tt-cursor');
        $(el).addClass('tt-cursor');
        var query = $('.tt-suggestion.tt-cursor').text();
        $('.viewall-list').attr('id', query)
        self.relatedProductDisplay("relatedProduct", $.trim(query))

    }

    formSubmittion(str) {
        var value = $.trim($(str).text());
        $('#searchInput_field').val(value);
        self.redirectPDPpage('getRedirectPage', value);

    }

    triggerFun() {
        self = this;
        self.emptyElement();
        $('#static-search').show();
        $('.search-con').html(config.popularSearch);
        self.popularProductDisplay();
    }

    emptyElement() {
        $('.left-tiles .loader').show();
        $('#popular-product').html('');
    }
    substringMatcher(strs) {
        return function findMatches(q, cb) {
            var matches, substrRegex;
            // an array that will be populated with substring matches
            matches = [];
            // regex used if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');
            // contains the substring `q`, add it to the `matches` array
            $.each(strs, function(i, str) {
                if (substrRegex.test(str)) {
                    matches.push(str);
                }
            });
            cb(matches);
        };
    }

    popularProductDisplay() {
        const count = config.DisMaxItem;
        request.ajaxCall(apiConfigInst.getApiConfig('popularProduct').call(this, count))
            .then(data => {
                if (data != null) {
                    self.emptyElement();
                    $('.loader').hide();
                    $(".viewall-list").hide();
                    var isArticlePresent = !_.isEmpty(data.resultsets[1]) && !_.isEmpty(data.resultsets[1].results),
                        title_name = data.resultsets[0].name,
                        result = data.resultsets[0].results,
                        popularArticle_name = isArticlePresent && data.resultsets[1].name,
                        popularArticle_result = isArticlePresent && data.resultsets[1].results;
                    config.popularTitle = isArticlePresent && data.resultsets[1].name;
                    config.popularArray = popularArticle_result;
                    $('.product-con').html(title_name);
                    $('.popular-header').html(popularArticle_name);
                    $('#popular-article').html('');
                    $.each(result, function(i) {
                        self.searchResultHTML(result, i);
                        isArticlePresent && self.articalPopularDisplayHTML(popularArticle_result, i);
                    })
                    self.sliceList('#popular-search', config.DisMaxItem);
                    self.sliceList('#popular-article', config.articalDisMaxItem);
                }
            })
    }

    relatedProductDisplay(apiUrl, query) {
        const globalSearch = query;
        request.ajaxCall(apiConfigInst.getApiConfig(apiUrl).call(this, config.DisMaxItem, globalSearch))
            .then(data => {
                if (data.general.total != 0) {
                    $('.search-con').html(config.relatedSearch);
                }
                if (data) {
                    if (data.general.total == 0) {
                        $('.search-con').html(config.popularSearch);
                        self.popularProductDisplay();
                    } else {

                        self.emptyElement();
                        $('.loader').hide();
                        $(".viewall-list").show();
                        var isArticlePresent = !_.isEmpty(data.resultsets[1]) && !_.isEmpty(data.resultsets[1].results),
                            title_name = data.resultsets[0].name,
                            result = data.resultsets[0].results,
                            articletitle_name = isArticlePresent && data.resultsets[1].name,
                            articleResult = isArticlePresent && data.resultsets[1].results;

                        $('.product-con').html(title_name);
                        if (isArticlePresent && articleResult.length > 0) {
                            $('.article-header').html(articletitle_name);
                            $(".popular-header").html(articletitle_name);	
                        } else {
                            $('.article-header').html(config.popularTitle);
                            $(".popular-header").html(config.popularTitle);
                        }
                        $('#popular-article').html('');

                        $.each(result, function(i) {
                            self.searchResultHTML(result, i);                            
                        });

                        if (isArticlePresent) {
                            for(let p = 0; p < 3; p++) {		
                                self.articalDisplayHTML(articleResult, p);											
                                self.articalPopularDisplayHTML(articleResult, p);		
                            }                            
                        }else {	
                            for(let j = 0; j < 3; j++) {
                                self.articalDisplayHTML(config.popularArray, j)
                                self.articalPopularDisplayHTML(config.popularArray,j);
                            }
                        }
                    }
                }
            })
            .catch(error => {
                console.log("Search API Error");
            })

    }
    redirectPDPpage(apiUrl, query) {
        const globalSearch = query;
        request.ajaxCall(apiConfigInst.getApiConfig(apiUrl).call(this, globalSearch))
            .then(data => {
                if (data.general.redirect && data.general.redirect != '') {
                    window.location = data.general.redirect;
                    return
                }
                $('#searchSubmit').submit();
            })
            .catch(error => {
                console.log(error);
            })



    }

    searchResultHTML(obj, i) {
        var listPrice = obj[i].list_price;
        //iterate through product display
        $('#popular-product').append('<li list-count="' + i + '"><a href="' + obj[i].pdpLink + '" class="product-image image-loaded"><img alt="" src="' + obj[i].fullimage + '"></a><div class="product-info"><div class="grid-title-wrapper"><a href="' + obj[i].pdpLink + '"  data-title="' + obj[i].title + '">' + obj[i].title + '</a></div><div class="product-price sale_price"><span><span class="price price-currency">$</span>' + obj[i].list_price + '</span></div></div></li>');
        if (listPrice == "0.00" || listPrice == " " || listPrice == null) {
            $('#popular-product').find('.product-price').remove();
        }
    }

    articalDisplayHTML(obj, i) {
        if(obj.length) {
            $('.tt-dataset-result').append('<div class="airtical-list" data-article-title="' + obj[i].title + '" data-index="' + i + '"><a href="' + obj[i].url + '">' + obj[i].title + '</a></div>');
            $(".airtical-list").not(":lt(" + obj.length + ")").remove();
            $(".airtical-list").not(':lt(' + config.articalDisMaxItem + ')').remove();
        }       
    }

    articalPopularDisplayHTML(obj, i) {
        if(obj.length) {
            $('#popular-article').append('<li list-count="' + i + '" data-article-title="' + obj[i].title + '"><a href="' + obj[i].url + '" class="link-productlist">' + obj[i].title + '</a></li>');
        }
    }


    sliceList(id, count) {
        $(id).find('li').not(':lt(' + count + ')').remove();
    }
    listPopular() {
        var popular = [{
            "name": "Popular Products",
            "results": []
        }]
        var data = popular[0].results;
        config.globalData = data;
        if (data.length > 0) {
            $('#static-search').html('');
            $.each(data, function(i) {
                $('#static-search').append('<li list-count="' + i + '" class="tt-popular">' + data[i] + '</li>');
            });
            self.sliceList('#static-search', config.searchList);
        }
    }

    searchDivClose(el) {
        $('#searchSubmit').find('.input-group').removeClass('focusInput');
        $('.search-dropdown').removeClass('open-dropdown');
        $('.nav-search').removeClass('popup');
        $('.sub-header-wrapper').removeClass('menu-hide');
        $('.sub-header-wrapper').removeClass('removescroll');
        $('.close-icon').removeClass('icon-show')
        $('.close-div').remove();
        $('.typeahead').val('');
        $('#searchInput_field').typeahead("close");
        $(el).remove();
        self.triggerFun();
    }


    render() {
        self.triggerFun();
        var url_string = window.location.href;
        // var searchResultURL = new URL(url_string);
        var isGiftFinder = url_string.split("giftAge")[1];
        var isSearchResult = url_string.split("searchTerm")[1];

        if (isGiftFinder != null) {
            $('.bc-title.searchResultTitle').css({
                'visibility': 'hidden'
            });
            $('.page-view').addClass('giftFinderSearchResultPage');
        }
        if (isSearchResult != null) {
            $('.page-view').addClass('searchResultPage');
        }
        this.listPopular();
        this.searchData();

        if ($('#pagename-home').find(".searchResultTitle").length > 0) {

            var locationUrl = window.location.href;
            var arr = locationUrl.split("=")[1];
            arr = arr.split("+").join(' ');
            var resultData = decodeURIComponent(arr);
            $('.bc-title.searchResultTitle').append("<span>" + '“' + resultData + '”' + "</span>");
        }
    }
    init() {
        self = this;
        this.render();
        self.sliceList('#static-search', config.searchList);
    }

}

let self,
    eventBindingInst = new eventBinding(),
    apiConfigInst = new fpApiConfig(),
    request = new ajaxRequest(),
    SearchProductInstance = new searchProduct();
SearchProductInstance.init();
