/**
 * products-landing.js
 * Version 1.0
 */
(function(global, PLAYAEM, galleryObj) {
    var isInitTriggered = false;
    var playLanding = {
        el: '.play-products',
        bindingEventsConfig: function() {
            var eventss = {
                "click .retailers-list": "showRetailersList",
            }
            return eventss;
        },
        productLimit: 0,
        imgRenderer: function(res, ele) {
            var producthtml = '';
            this.productLimit = 0;
            var productData = this.productData.products || res; //this.pageLoadActions();
            var limit = $('#play-products li').length;
            var lazyLoadLimit = parseInt(this.productData.lazyLoadLimit);
            var titleAlign = this.productData.titleAlign;
            var colLayout = this.productData.colLayout;
            if(limit>=productData.length){
                // console.log("All Datas are loaded..")
                return;
            }
            for (var j = limit; j < limit + lazyLoadLimit; j++) {
                if (productData[j] != undefined && productData[j].productPagePath != undefined) {
                    producthtml = producthtml + '<li class=' + colLayout + '><a href=' + productData[j].productPagePath + ' data-gallery=' + productData[j].productCategory + ' aria-label=" ' + productData[j].productTitle + ' ' + productData[j].productId  +  ' " data-product-id=' + productData[j].productId + ' data-product-id=' + productData[j].productId + ' data-tracking-id="Product Thumbnail Section|' + productData[j].alwaysEnglish + '|' + productData[j].alwaysEnglish + '|Product Thumbnail" ><div class="gallery-image"><img class="image-container" src=' + productData[j].productThumbnail + ' data-src=' + productData[j].productThumbnail + ' data-hover=' + productData[j].productThumbnailHover + ' alt="' + productData[j].thumbnailAltTxt + '" hoveralt="' + productData[j].thumbnailHoverAltTxt + '"/></div><span class="tile-content" style="text-align:' + titleAlign + '">' + productData[j].productTitle + '</span></a></li>';
                } else if (productData[j] != undefined && productData[j].productPagePath == undefined) {
                    producthtml = producthtml + '<li class=' + colLayout + '><a href="javascript:void(0);" data-gallery=' + productData[j].productCategory + ' aria-label=" ' + productData[j].productTitle + ' ' + productData[j].productId  +  ' " data-product-id=' + productData[j].productId + ' data-product-id=' + productData[j].productId + ' data-tracking-id="Product Thumbnail Section|' + productData[j].alwaysEnglish + '|' + productData[j].alwaysEnglish + '|Product Thumbnail" ><div class="gallery-image"><img class="image-container" src=' + productData[j].productThumbnail + ' data-src=' + productData[j].productThumbnail + ' data-hover=' + productData[j].productThumbnailHover + ' alt="' + productData[j].thumbnailAltTxt + '" hoveralt="' + productData[j].thumbnailHoverAltTxt + '"/></div><span class="tile-content" style="text-align:' + titleAlign + '">' + productData[j].productTitle + '</span></a></li>';
                }

            }
            this.productLimit = this.productLimit + lazyLoadLimit;
            $('#play-products .product-list').append(producthtml);
            if ($('#play-products .product-list li').length == 0) $('#play-products .product-list').empty().append('<div    class="no-data">No Data Found</div>');
            //$("#play-products .product-list li:nth-child(4n+4)").next().css('clear', 'both');
            this.imgHoverOver();
            this.heightSyncLabel('#play-products .product-list li');


        },
        imgHoverOver: function() {
            var heightVal = $('#player-thumbnail-datas .gallery-image').height();
            $("#player-thumbnail-datas .gallery-image").css({
                'height': heightVal + "px",
                'line-height': heightVal + "px"
            });
            //mouseover&mouseout function
             $(".image-container[data-hover!=undefined]").mouseover(function() {
                if ($(this).data("hover") == "undefined") {
                    $(this).attr('src', $(this).data("src"));
                } else {
                    $(this).attr('src', $(this).data("hover"));
                }
                //hover alt function
                if ($(this).data("hoveralt") == "undefined") {
                    $(this).attr('alt', $(this).data("alt"));
                } else {
                    $(this).attr('alt', $(this).data("hoveralt"));
                }
                //mouseout function
            }).mouseout(function() {
                $(this).attr('src', $(this).data("src"));
                $(this).attr('alt', $(this).data("alt"));
            });
        },
        heightSyncLabel: function(elem) {
            var max = -1;
            var $heightElem = $(elem).find(".tile-content");
            $heightElem.css('height', 'auto');
            $(elem).find('img').imagesLoaded(function() { // image ready
                _.each($heightElem, function(el) {
                    var height = $(el).innerHeight();
                    max = height > max ? height : max;
                });
                $heightElem.css('height', max + 'px');
            });
            return;
        },
        // added by dinesh - Maxsteel product page
        // commented by Ramnath -never called this function
        /* heightSync: function(elem) {
            if (!elem) return;
            var $heightSyncElem = $(elem);
            _.each($heightSyncElem, function(item) {
                var max = -1;
                $(item).find($(item).data('height-sync')).css({'height': 'auto', 'line-height': 'normal'});
                if ($(item).find('img').length && typeof $.fn.imagesLoaded == "function") {
                    $(item).find('img').imagesLoaded(function() {
                        // image ready
                        _.each($(item).find($(item).data('height-sync')), function(el) {
                            var height = $(el).height();
                            max = height > max ? height : max;
                        });
                        $(item).find($(item).data('height-sync')).css({
                            'height': max + 'px',
                            'line-height': max + 'px',
                        });
                    });
                    return;
                }
                _.each($(item).find($(item).data('height-sync')), function(el) {
                    var height = $(el).height();
                    max = height > max ? height : max;
                });
                $(item).find($(item).data('height-sync')).css({
                    'height': max + 'px',
                    'line-height': max + 'px',
                });
                console.log($(item), max)
            });
        },*/
        htmlLazyload: function() {
            $(window).scroll(function() {
                //var self = this;
                if ($('#play-products').is(":visible")) {
                    playLanding.loadMoreProducts(document.querySelector("#play-products"));
                    $('#play-products .playing-overlay').remove();
                }
            });
        },
        loadMoreProducts: function(curVal) {
            var self = this;
            var productData = self.productData.products;
            if (window.innerHeight >= curVal.getBoundingClientRect().bottom) {
                if (self.productLimit <= productData.length) {
                    self.imgRenderer(undefined, curVal, undefined);
                    setTimeout(function(){
                        // $('#play-products').length && self.heightSync($('#play-products'));
                    },1000)
                }
            }
        },
        renderEventsDateFromAPI: function(ele) {
            var self = this;
            //if ($(this.el).data("height-sync") && !PLAYAEM.isMobile) this.heightSync();

            if ($('#play-products').is(":visible")) {
                localStorage.setItem("filterCategory", "");
                var getProducts = "/bin/getProductLandingGrid";
                var currentPagePath = $('#currentPagePath').val();
                var obj = {
                    "async": false,
                    "type": "get",
                    //"url": "//" + window.location.host + "/products.json",
                    "params": "",
                    "url": window.location.protocol + "//" + window.location.host + getProducts + "?currentPagePath=" + currentPagePath
                }

                PLAYAEM.requestAPICall(obj, function(response) {
                    if (!response) {
                        console.log("Err : Upcoming Events API failed..");

                        return false;
                    } else {
                        var productBlock = document.querySelector("#play-products");
                        var productsJson = response;
                        self.productData = productsJson;
                        if (localStorage.getItem("filterCategory") != '') {
                            $('.nav-carousel-slides li a').removeClass('active');
                            var currentCategory = localStorage.getItem("filterCategory");
                            $('.nav-carousel-slides li a[data-category=' + currentCategory + ']').addClass('active');
                        }
                        self.imgRenderer(productsJson, productBlock, undefined);
                        self.htmlLazyload();
                        //$('#play-products').length && self.heightSync($('#play-products'));

                    }

                })



            }
        },
        resizeBind: function() {
            var self = this;
            window.onresize = function(event) {
                if (!PLAYAEM.isMobile) {
                    setTimeout(function() {
                        if ($('#play-products').is(":visible")) {
                            self.heightSyncLabel('#play-products .product-list li');
                            //$('#play-products').length && self.heightSync($('#play-products'));
                        }
                    }, 500);
                }
            };
        },
        showRetailersList: function(ele, evt) {
            $('#interstitialRetailerModal').modal('show');
            var getRetailerList = "/bin/getRetailerList";
            var pathVal = $(ele).attr('data-path');

            var obj = {
                "type": "get",
                "url": window.location.protocol + "//" + window.location.host + getRetailerList + "?pagePath=" + pathVal
            }
            PLAYAEM.requestAPICall(obj, function(response) {
                if (!response) {
                    console.log("Err : Upcoming Events API failed..");
                    $('#interstitialRetailerModal .play-retailers .gallery-tile ul').empty();
                    return false;
                } else {
                    var retailerImgCont = ''
                    var retailerTarget = '';
                    var srText='';
					var alwaysEnglish = $('#retailerIntAlwaysEnglish').val();
                    for (var i = 0; i < response.retailers.length; i++) {

                        if (response.retailers[i].retailerTarget == "sameWindow"){
                            retailerTarget = '_self';}
                        if (response.retailers[i].retailerTarget == "tabWindow"){
                            retailerTarget = '_blank';
                        srText='<span class="sr-only"> Opens in new window </span>';}
                        if (response.retailers[i].retailerTarget == "newWindow")
                        	{
                        	srText='<span class="sr-only"> Opens in a new tab </span>';
                            retailerImgCont = retailerImgCont + "<li><a class='interstitial-load' data-tracking-id='Retailer Interstitial|"+ alwaysEnglish + "'|' "+ response.retailers[i].retailLogoAlt + "'|' href='javascript:void(0);' data-url='" + response.retailers[i].retailerUrl + "' onclick=window.open(this.dataset.url,'PLAY',width=500,height=500); ><img src='" + response.retailers[i].retailerLogoSrc + "' alt='" + response.retailers[i].retailLogoAlt + "'  >'" + srText + "'</a></li>";
                    }
                            else{
                            retailerImgCont = retailerImgCont + '<li><a class="interstitial-load" data-tracking-id="Retailer Interstitial|'+ alwaysEnglish + '|' + response.retailers[i].retailLogoAlt + '|" href=' + response.retailers[i].retailerUrl + ' target=' + retailerTarget + '><img src=' + response.retailers[i].retailerLogoSrc + ' alt=' + response.retailers[i].retailLogoAlt + '  >' + srText + '</a></li>';
                        }
                    }
                    $('#interstitialRetailerModal .play-retailers .gallery-tile ul').empty().append(retailerImgCont);
                }


            });
            var trackingVal = $(ele).attr('data-retailer-id');
            if (trackingVal != null) {
                PLAYAEM.getTrackingValues(ele, evt, trackingVal);
            }
        },
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || isInitTriggered) return;
            isInitTriggered = true;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.renderEventsDateFromAPI();
            this.resizeBind();
        }
    }
    playLanding.init();
    PLAYAEM.playLanding = playLanding;
    document.addEventListener('DOMContentLoaded', function() {
        if (!isInitTriggered) {
            playLanding.init();
        }
        /*if (!PLAYAEM.isMobile) {
            self.heightSyncLabel(elem);
        }*/
    }, false);
}(window, PLAYAEM));
