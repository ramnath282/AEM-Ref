/**
 * search product.js
 * Version 1.0
 */
(function(global, PLAYAEM) {
    var self;
    var searchProduct = {
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
       // typeMinLength: 1,
        typeMinLength: 3,
        typeMaxLength:4,
        //typeMaxLength:5,
        searchList: 6,
        DisMaxItem: 4,
        typeOrder: "asn",
        typeDelay: 300,
        globalData: 0,
        globalSearch: '',
        typeCache: true,
        apiConfig: function(name) {
            var obj = {

                "searchResult": {
                    "url": '' + this.autoComplete + '?max_results=' + this.DisMaxItem + '&query=' + self.globalSearch,
                    "body": "",
                    "type": "POST",
                    "contentType": 'application/json',

                },

                "relatedProduct": {
                    "url": 'https:' + this.searchapiUrl + '?do=related&i=1&count=' + this.DisMaxItem + '&q=' + self.globalSearch +'*',
                    "body": "",
                    "type": "GET",
                    "contentType": 'application/json',
                   
                },
                "getRedirectPage": {
                    "url": 'https:' + this.searchapiUrl + '?q=' + self.globalSearch,
                    "body": "",
                    "type": "POST",
                    "contentType": 'application/json',

                },
                "popularProduct": {
                    "url": 'https:' + this.searchapiUrl + '?do=popular&i=1&count=4&sort=affiliateSiteExits',
                    //"url": 'https://sp1004f9de.guided.ss-omtrdc.net/?do=popular&i=1&count=' + this.DisMaxItem + '&q=' + self.globalSearch,
                    "body": "",
                    "type": "GET",
                    "contentType": 'application/json',

                },

                "productSearchPage": {
                    "url": 'https:' + this.searchapiUrl + '?q=' + self.globalSearch + '&count=8&page=1',
                    //"url": 'https://sp1004f984.guided.ss-omtrdc.net/?index=prod&search=product;q=' + self.globalSearch + '&count=8&page=1',
                    "body": "",
                    "type": "POST",
                    "Accept": "application/json",
                    "contentType": 'application/json',


                },

            }
            return obj[name];
        },
        bindingEventsConfig: function() {
            var events = {
                "keyup #searchInput_field": "relativeSearchFun", //"SearchResultDisplay",
                "focus #searchInput_field": "defaultDisplay",
                "mouseover .tt-suggestion": "hoverGetParams",
                //"mouseover .tt-popular": "hoverGetPopular",
                "click .overlay": "searchDivClose",
                "click .tt-suggestion": "searchPageResult",
                "click .tt-popular": "popularPageResult",
                "click #basic-addon1": "submitValueFun",
                "click .viewall-list": "viewsubmitValueFun",
                "click .close-icon":"defaultSetFun",
		        "click .menu-sliding-div-mask":"searchListClose",
                "click .header-close-menu":"searchListClose",
                "click .hamburger-menu":"searchListClose"

            }
            return events;
        },
        ajaxCall: function(obj, cb) {
            $.ajax({
                url: obj.url,
                data: obj.body || '',
                type: obj.type,
                headers: obj.headers,
                beforeSend: function(xhr) {
                    if (obj.beforeSend) {
                        xhr.setRequestHeader('Authorization', obj.beforeSend);
                    }
                },
                success: function(data) {
                    if (typeof cb == "function") cb(data);
                },
                error: function(xhr, textStatus, errorThrown) {
                    if (typeof cb == "function") cb(false, xhr.responseJSON || errorThrown);
                }
            });
        },


        searchData: function() {
            this.ajaxCall(this.apiConfig('searchResult'), function(res, err) {
                if(res) {
                var data = res.replace(/[\(\)\"\[\]{}]/g, "").split(',');
                var result = data.map(function (el) {
                    return el.trim();
                })

                $('.typeahead').typeahead({
                    hint: self.typeHint,
                    highlight: self.typeHighlight,
                    minLength: self.typeMinLength,
                    maxLength: self.typeMaxLength,
                    maxItem: self.searchList,

                    items: self.searchList,
                    sorter : self.typeOrder,
                    delay: self.typeDelay,
                    cache: false,

                }, {
                    name: 'result',
                    source:self.substringMatcher(result),
                    limit:self.DisMaxItem

                }).on('keyup', function(el) {
              var txt =  $(el.target).val().toLowerCase();
              var query = $(el.target).val(txt);
             if (query) {$('.close-x').addClass("icon-show");} else {$('.close-x').removeClass("icon-show");}
              window._satellite.cookie.set("searchType","user-entered-search", 0);
             if (el.which == 13) {
                self.redirectPDPpage('getRedirectPage', txt);
             }
  });
            }
            });
            $('.typeahead').bind('typeahead:cursorchange', function(ev, suggestion) {
                    self.relatedProductDisplay("relatedProduct", $.trim(suggestion))
            });

        },

relativeSearchFun:function(el) {
    var txt =$('#searchInput_field').val();
    var query = $.trim($(el).val().toLowerCase());
    $('.viewall-list').attr('id',txt);

    if (txt.length >= self.typeMinLength ) {
    setTimeout(function() {

    self.relatedProductDisplay("relatedProduct",query)
        },100);
   } else if(txt.length <= self.typeMinLength){
               self.popularProductDisplay("popularProduct");
               $('.search-con').html(self.popularSearch);
               $('#static-search').show();
               $('.viewall-list').hide();
     }

},
        defaultSetFun:function() {
            var isMobile = $(window).width() <= 767;
            $('#searchInput_field').typeahead("close");
             self.triggerFun();
             isMobile && $('.search-dropdown').removeClass(' open-dropdown');
             isMobile && $('.sub-header-wrapper').removeClass('menu-hide');
             isMobile && $('.sub-header-wrapper').removeClass('removescroll');
        },
        submitValueFun: function() {
            var query = $.trim($('#searchInput_field').val());
            if (query.length > 0) {
                self.redirectPDPpage('getRedirectPage', query);
            }
        },

        viewsubmitValueFun: function(el) {
             var txt = $(el).attr('id');
             var query = $.trim($('#searchInput_field').val(txt));

            if (query.length > 0) {
                self.redirectPDPpage('getRedirectPage', txt);
            }
        },

        searchPageResult: function(el) {
            window._satellite.cookie.set("searchType","typeahead", 0);
            self.formSubmittion(el);
        },

        popularPageResult: function(el) {
            window._satellite.cookie.set("searchType","popular searches", 0) ;
            self.formSubmittion(el);
        },

        defaultDisplay: function(el) {

            $('#searchInput_field').trigger("input").typeahead("open");
            setTimeout(function() {
		$('#searchSubmit').find('.input-group').addClass('focusInput');
                $('.search-dropdown').addClass('open-dropdown');
		   // self.triggerFun();
            }, 100);
             $('.search-con').html(self.popularSearch);
             $('#static-search').show();
             $('.sub-header-wrapper').addClass('menu-hide');
             $('.sub-header-wrapper').addClass('removescroll');
        },
	searchListClose:function(el){
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
        },
        hoverGetParams: function(el) {
            self.emptyElement();
            $('.tt-suggestion.tt-cursor').removeClass('tt-cursor');
            $(el).addClass('tt-cursor');
            var query = $('.tt-suggestion.tt-cursor').text();
              $('.viewall-list').attr('id',query)
            self.relatedProductDisplay("relatedProduct", $.trim(query))

        },

        formSubmittion: function(str) {
            var value = $.trim($(str).text());
            $('#searchInput_field').val(value);
            self.redirectPDPpage('getRedirectPage', value);

        },

          triggerFun: function() {
          self. emptyElement();
          $('#static-search').show();
          $('.search-con').html(self.popularSearch);
          self.popularProductDisplay("popularProduct");
        },

        emptyElement: function() {
            $('.left-tiles .loader').show();
            $('#popular-product').html('');
        },
        substringMatcher: function(strs) {
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
        },

        popularProductDisplay:function(apiUrl) {
            var ajaxSettings = self.apiConfig(apiUrl);
            self.ajaxCall(ajaxSettings, function(res, err) {
                if(res) {

                self.emptyElement();
                $('.loader').hide();
                $(".viewall-list").hide();
                var title_name = res.resultsets[0].name
                var result = res.resultsets[0].results;
                $('.product-con').html(title_name)
                $.each(result, function(i) {
                  self.DOMDisplayHTML(result, i);
                })
            }
            });

        },

        relatedProductDisplay: function(apiUrl, query) {

            self.globalSearch = query;
            var ajaxSettings = self.apiConfig(apiUrl);
            //ajaxSettings.body = params
            self.ajaxCall(ajaxSettings, function(res, err) {
		    if(res.general.total != 0){
					$('.search-con').html(self.relatedSearch);
				}
                
                if(res.general.total == 0) {
                    $('.search-con').html(self.popularSearch);
                    self.popularProductDisplay("popularProduct");
                } else {

                self.emptyElement();
                $('.loader').hide();
                $(".viewall-list").show();
		//$('.search-con').html(self.relatedSearch);
                var title_name = res.resultsets[0].name;
                var result = res.resultsets[0].results;
                $('.product-con').html(title_name)
                  $.each(result, function(i) {
                    self.DOMDisplayHTML(result, i);
                });
                self.sliceList('#popular-product', self.DisMaxItem);
            }
            
            });
        },
        redirectPDPpage: function (apiUrl, query) {
            self.globalSearch = query;
            var ajaxSettings = self.apiConfig(apiUrl);
            self.ajaxCall(ajaxSettings, function (res, err) {
                if (res.general.redirect && res.general.redirect != '') {
                    window.location = res.general.redirect;
                    return
                }
                $('#searchSubmit').submit();

            });
        },
        DOMDisplayHTML: function(obj, i) {
	var listPrice = obj[i].list_price;
        //iterate through product display
        $('#popular-product').append('<li list-count="' + i + '"><a href="' + obj[i].pdpLink + '" class="product-image image-loaded"><img alt="" src="' + obj[i].fullimage + '"></a><div class="product-info"><div class="grid-title-wrapper"><a href="' + obj[i].pdpLink + '"  data-title="' + obj[i].title + '">' + obj[i].title + '</a></div><div class="product-price sale_price"><span><span class="price price-currency">$</span>' + obj[i].list_price + '</span></div></div></li>');
        if(listPrice == "0.00" || listPrice == " " || !listPrice) {
        $('#popular-product').find('.product-price').remove();
     }
        },

        sliceList: function(id, count) {
            $(id).find('li').not(':lt(' + count + ')').remove();
        },
        listPopular: function() {
            var popular = [{
                "name": "Popular Products",
                "results": []
            }]
            var data = popular[0].results;
            self.globalData = data;
            if (data.length > 0) {
                $('#static-search').html('');
                $.each(data, function(i) {
                    $('#static-search').append('<li list-count="' + i + '" class="tt-popular">' + data[i] + '</li>');
                });
                self.sliceList('#static-search', self.searchList);
               }
        },

        searchDivClose:function(el){
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
        },


        render: function() {
            self.triggerFun();
            var url_string = window.location.href;
           // var searchResultURL = new URL(url_string);
            var isGiftFinder = url_string.split("giftAge")[1];
            var isSearchResult = url_string.split("searchTerm")[1];

            if(isGiftFinder){
                $('.bc-title.searchResultTitle').css({'visibility':'hidden'});
                $('.page-view').addClass('giftFinderSearchResultPage');
            }
            if(isSearchResult){
				$('.page-view').addClass('searchResultPage');
            }
            this.listPopular();
            self.searchData();

        if ($('#pagename-home').find(".searchResultTitle").length > 0) {

	        var locationUrl = window.location.href;
            var arr = locationUrl.split("?")[1];
            arr = arr.split("&")[0];
            arr = arr.split("=")[1];
	    	arr = arr.split("+").join(' ');
            var resultData = decodeURIComponent(arr);
            $('.bc-title.searchResultTitle').append('<span>"'+ resultData + '"</span>');
            }
        },
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.searchProduct) return;
            self = this;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
            self.sliceList('#static-search', self.searchList);

       }
    }
    searchProduct.init();
    PLAYAEM.searchProduct = searchProduct;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            searchProduct.init();
        }
    }, false);
}(window, PLAYAEM));
