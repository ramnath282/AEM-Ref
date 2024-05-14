/**
 * Gallery.js
 * Version 1.0
 * applied in gallery components like image, character, products..,
 */

(function (global, PLAYAEM) {
    var self,
    	reusaretailers = {
        el: '.reusability-play-tiles-gallery',
        bindingEventsConfig: function () {
            var eventss = {
                "click .reusability-play-tiles-gallery .show-more a": "reasuexpandDetail",
                "click .reusability-play-tiles-gallery .reusability-gallery-tile li a": "categoryName",
                "click .btn-download-interstitial": "showinterstitialData",
                "click .arrow": "expandAccordian",
            }
            return eventss;
        },
        categoryName: function (ele, evt) {
            var categoryName = $(ele).attr('data-category');
            localStorage.setItem("categoryName", categoryName);
        },
        showinterstitialData: function (ele, evt) {
            $('#interstitialGameRetailerModal').modal('show');
            var getDownload = "/bin/getDownloadInterApp";
            var downloadId = $(ele).attr('data-downlodid');
            var pathVal = $(ele).attr('data-path');

            var obj = {
                "type": "get",
                "url": window.location.protocol + "//" + window.location.host + getDownload + "?downloadId=" + downloadId + "&downloadPath=" + pathVal
            }
            PLAYAEM.requestAPICall(obj, function (response) {
                if (!response) {
                    console.log("Err : Upcoming Events API failed..");
                    return false;
                } else {
                    var downloadImgCont = ''
                    var downloadTarget = '';
                    for (var i = 0; i < response.downloadList.length; i++) {
                        if (response.downloadList[i].interstitialTarget == "sameWindow")
                            downloadTarget = '_self';
                        if (response.downloadList[i].interstitialTarget == "newWindow")
                            downloadTarget = '_blank';
                        if (response.downloadList[i].interstitialTarget == "tabWindow")
                            downloadImgCont = downloadImgCont + "<li><a class='interstitial-load' data-url='" + response.downloadList[i].interstitialUrl + "' onclick=window.open(this.dataset.url,'PLAY',width=500,height=500); ><img src='" + response.downloadList[i].interstitialLogoSrc + "' alt='" + response.downloadList[i].interstitialLogoAlt + "'  ></a></li>";
                        else
                            downloadImgCont = downloadImgCont + '<li><a class="interstitial-load" href=' + response.downloadList[i].interstitialUrl + ' target=' + downloadTarget + '><img src=' + response.downloadList[i].interstitialLogoSrc + ' alt=' + response.downloadList[i].interstitialLogoAlt + '  ></a></li>';

                    }
                    $('#interstitialGameRetailerModal .reusability-gallery-tile ul').empty().append(downloadImgCont);
                }


            });
            var trackingVal = $(ele).attr('data-download');
            PLAYAEM.getTrackingValues(ele, evt, trackingVal);
        },
        reasuexpandDetail: function (ele, evt) {
			if($(ele).parents(".play-products").length){
			  self.heightSyncLabel($(ele).parents(".play-products").find("li"));
			} else if($(ele).parents("#video-gallery-player-component").length){
			  self.heightSyncLabel($(ele).parents("#video-gallery-player-component").find("li"));
			}else if($(ele).parents(".articles").length){
				self.heightSyncLabel($(ele).parents(".articles").find("li"));
            }else if($(ele).parents(".fpDownloadapps-module").length){
                self.heightSyncLabel($(ele).parents(".fpDownloadapps-module").find("li"));
			}else if($(ele).parents(".global-causes").length){
                self.heightSyncLabel($(ele).parents(".global-causes").find("li"));
            }else if($(ele).parents(".play-download").length){
                self.heightSyncLabel($(ele).parents(".play-download").find("li"));
            }
        },
        heightSync: function () {
            var $heightSyncElem = $(this.el + '[data-height-sync]');
            _.each($heightSyncElem, function (item) {
                var max = -1;
                if ($(item).find('img').length && typeof $.fn.imagesLoaded == "function") {
                    $(item).find('img').imagesLoaded(function () { // image ready
                        _.each($(item).find($(item).data('height-sync')), function (el) {
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
                _.each($(item).find($(item).data('height-sync')), function (el) {
                    var height = $(el).height();
                    max = height > max ? height : max;
                });
                $(item).find($(item).data('height-sync')).css({
                    'height': max + 'px',
                    'line-height': max + 'px',
                });
                console.log($(item), max)
            });
        },
	    heightSyncLabel: function (elem) {
			var max = -1;
			var $heightElem = $(elem).find(".tile-content");
			$heightElem.css('height', 'auto');
            $(elem).find(".reusability-gallery-image").css('height', 'auto');
			$(elem).find('img').imagesLoaded(function () { // image ready
				_.each($heightElem, function (el) {
					var height = $(el).innerHeight();
					max = height > max ? height : max;
				});
				$heightElem.css('height', max + 'px');
			});
			return;
		},
        imgHoverOver: function () {
            var heightVal = $('#player-thumbnail-datas .reusability-gallery-image').height();
            //assigning height
            $("#player-thumbnail-datas .reusability-gallery-image").css({
                'height': heightVal + "px",
                'line-height': heightVal + "px"
            });
            $(".image-container").mouseover(function () {
                if($(this).data("hover")=="undefined"){
                    $(this).attr('src', $(this).data("src"));
                }else{
                    $(this).attr('src', $(this).data("hover"));
                }

                if($(this).data("hoveralt")=="undefined"){
                    $(this).attr('alt', $(this).data("alt"));
                }else{
                   $(this).attr('alt', $(this).data("hoveralt"));
                }
                }).mouseout(function () {
                    $(this).attr('src', $(this).data("src"));
                    $(this).attr('alt', $(this).data("alt"));
            });

        },
		imgHoverOverMob: function() {
            if (PLAYAEM.isMobile) {
                $(".nav-carousel-slides.char-tiles li a").each(function(idx,liElem){
                    if($('.image-container',liElem).data('mobile-src'))
                    {
                      $('.image-container',liElem).attr('src', $('.image-container',liElem).data('mobile-src'));
                    }
                });
                var $activeStateMob = $(".nav-carousel-slides.char-tiles li a.active");
                if($('.image-container',$activeStateMob).data('mobile-active-src'))
                {
                    $('.image-container',$activeStateMob).attr('src', $('.image-container',$activeStateMob).data('mobile-active-src'));
                }
            }
            else{
                var $activeState = $(".nav-carousel-slides.char-tiles li a.active");
                if($('.image-container',$activeState).data('hover'))
                {
                    $('.image-container',$activeState).attr('src', $('.image-container',$activeState).data('hover')).off('mouseout');
                }
            }
        },
        truncateTitle: function () {
            if (PLAYAEM.isMobile) {
                $(".txt-truncate").each(function(i){
                    var len=$(this).text().length;
                    if(len>65)
                    {
                    $(this).text($(this).text().substr(0,65)+'');
                    }
                });
            }
        },

        pageLoadActions: function () {
            var self = this;
            $(self.el).each(function (idx, playComps) {
                $(playComps).each(function (idxx, indiPlayComp) {
                    var visibleLiCount = $('.reusability-gallery-tile ul li:visible', indiPlayComp).length,
                    maxLiCount = $('.reusability-gallery-tile ul li', indiPlayComp).length;
                    if (maxLiCount > visibleLiCount) {
                        $('.show-more', indiPlayComp).show();
                    } else {
                        $('.show-more', indiPlayComp).hide();
                    }
                });
            })
		this.imgHoverOverMob();
        },
		resizeBind: function(){
			$(window).on("load resize",function(e){
				if (!PLAYAEM.isMobile) {
					setTimeout(function(){
						$(".play-products-hp").length && self.heightSyncLabel($(".play-products li"));
					 	$("#video-gallery-player-component").length && self.heightSyncLabel($(".reusability-play-list li"));
						$(".articles").length && self.heightSyncLabel($("li"));
                        $(".fpDownloadapps-module").length && self.heightSyncLabel($(".fpDownloadapps-module li"));
						$(".global-causes").length && self.heightSyncLabel($("li"));
						$(".reusability-play-tiles-gallery").length && self.heightSyncLabel($(".reusability-play-tiles-gallery li"));
                        $(".play-download").length && self.heightSyncLabel($(".play-download li"));
					},800);
				}
                if (PLAYAEM.isMobile) {
                    $(".fpDownloadapps-module").length && self.heightSyncLabel($("li"));
                }

                setTimeout(function(){
                    if($('.video-rightscroll').length >=1){
                        $('.video-rightscroll .videos-gallery-playlist').height($('.video-rightscroll .featured-promo').height());
                    }
                    //self.pageLoadActions();
                },800);

            });




        },
        expandAccordian : function(){
            setTimeout(function(){
                if($('.youtube').length >=1){
                    if($(window).width() < 767){
                        $('.youtube .reusability-videos-gallery-playlist').css("height","auto");
                    }else{
                        $('.youtube .reusability-videos-gallery-playlist').height($('.youtube .featured-promo').height());
                    }
                }
                self.pageLoadActions();
            },200);
        },
        render: function () {
            /*$('.view-all').hide();*/
            //var self = this;
            if ($(this.el).data("height-sync") && !PLAYAEM.isMobile) this.heightSync();
            var visibleCnt = $(".reusability-play-list li:visible").length;
            $('.reusability-play-list li:nth-child('+visibleCnt+'n+'+visibleCnt+')').next().css('clear', 'both');
            //this.showMoreBtnHover();
        },


        init: function () {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.reusaretailers) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            self= this;
            self.render();
            self.imgHoverOver();
			//self.imgHoverOverMob();
			self.resizeBind();
            self.truncateTitle();

        }
    }
    reusaretailers.init();
    PLAYAEM.reusaretailers = reusaretailers;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            reusaretailers.init();
        }
        reusaretailers.pageLoadActions();
	if (PLAYAEM.isMobile) {
            $(".fpDownloadapps-module").length && self.heightSyncLabel($(".fpDownloadapps-module li"));
            $(".play-download").length && self.heightSyncLabel($(".play-download li"));
        }
    }, false);
}(window, PLAYAEM));
