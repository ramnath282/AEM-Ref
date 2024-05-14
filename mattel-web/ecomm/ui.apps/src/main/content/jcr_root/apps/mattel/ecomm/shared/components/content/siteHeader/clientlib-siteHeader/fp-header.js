/**
 * FP Header.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    var self;
    var header = {
        el: 'header',
        headerScrollStart: 100,
        bindingEventsConfig: function () {
            var events = {
                "click .hamburger": "showNavigation",
                "click .header-close-menu": "fnMenuClose",
                "click .hamburger-menu": "showNavigationV2",
                "click .primary-nav ul li a": "closeMenu",				
                "click .nav-search .input-group": "navSearchBox",
				"keypress  .nav-search .input-group": "navSearchBox",
				"focusout .nav-search .input-group": "navSearchBoxFocusOut",
                "click .close-div": "closeNavSearch",
                "click .close-x": "clearNavSearch",				
				"click .play-backtotop": "backToTopAction",
                "click .show-sub-nav": "showSublevel",
				"click .search-menu": "showNavigationSearch",
                'click .menu-sliding-div-mask': 'hideMobileOverlay',
                'mouseover .primary-nav ul li' : 'maskLayer',
                'mouseout .primary-nav ul li' : 'maskLayerout',
                "click [data-analytics-tracking-id]":"getNavTrackingValues",
		  "click .mobarrow-icon": "mobileRecallSlideUp",
          "click .signup-close": "removeRecallMar",
          "click #pricespider-link-button": "buyNowClick",
          "click .readmore": "readMoreClick",
          "click .star-rating a": "starLinkClick"
            }
            return events;
        },
        maskLayer: function(ele){
            if ($(window).width() >= 992) {
                $('.mask').css('display','block');
            }
        },
       maskLayerout: function(ele){
           if ($(window).width() >= 992) {
                $('.mask').css('display','none');
           }
        },
        hideMobileOverlay: function (ele, evt) {
            $('.hamburger-menu')[0].click();
            $('#primary-nav').removeClass('menu-hide');
        },
         buyNowClick: function (ele, evt) {
             var mobileView = window.matchMedia("(max-width: 767px)");
             var tabView = window.matchMedia("(min-width: 768px) and (max-width: 1024px)")
             var height;
             if(mobileView.matches){
                 height = $("#pricespider-div").offset().top - $('.mobile-viewMenu').outerHeight();
             } else if(tabView.matches){
                height = $("#pricespider-div").offset().top -$("header").outerHeight();
             }else{
                if($(".newsletter-sign-up").is(":visible")) {
                    height = $("#pricespider-div").offset().top - $("header").height()-$(".newsletter-sign-up").outerHeight();
                 } else {
                    height = $("#pricespider-div").offset().top -$("header").height();
                 }
             }
             jQuery(window).scrollTop(height);
        },
        readMoreClick: function (ele, evt) {
            var height;
		  if (navigator.userAgent.match(/Android/i) || navigator.userAgent.match(/webOS/i) || navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/Windows Phone/i)) {
			if($("header").hasClass("sticky-border")) {
				height = $('#CTAlink').offset().top - $("header").height();
			}
			else {
				height = $('#CTAlink').offset().top - $("header").height() - $(".mobile-viewMenu").height();
			}
		  } else {
			height = $('#CTAlink').offset().top - $("header").height();
		  }

		    jQuery(window).scrollTop(height); 
        },
        starLinkClick: function() {
            var height;
            if (navigator.userAgent.match(/Android/i) || navigator.userAgent.match(/webOS/i) || navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/Windows Phone/i)) {
                if($('#bvReviewHeading').is(":visible")) {
                    if($("header").hasClass("sticky-border")) {
                        height = $('#bvReviewHeading').offset().top - $("header").height();
                    } else {
                        height = $('#bvReviewHeading').offset().top - $("header").height() - $(".headerAnnouncement").height();
                    }
                    jQuery(window).scrollTop(height); 
                }
            } else {
                if($('#bvReviewHeading').is(":visible")) {
                    height = $('#bvReviewHeading').offset().top - $("header").height();
                    jQuery(window).scrollTop(height); 
                } 
            }

	    },
        showNavigation: function (el, evt) {
            $('.primary-nav').fadeToggle(500, function () {
                ($(this).is(':visible')) ? $('.hamburger').addClass('open'): $('.hamburger').removeClass('open')

            });
        },
        showNavigationV2: function(el, evt){
            $('#primary-nav').removeClass('menu-hide');
            if($(el).hasClass('collapsed')){
            $('body').css({'overflow': 'visible', 'position':'static'});
            window.scrollTo(0,10);  
        }
		else{
                $('body').css({'overflow': 'hidden', 'position':'fixed'});
            }
                 $('#primary-nav-bar').toggleClass('active').show();
				
                if($('.sub-header-wrapper').attr('aria-expanded')==='true'){ 

                    $('.sub-header-wrapper').attr('aria-hidden','false');
                    $('.sub-header-content').attr('aria-hidden','false');
                    $('.close-icon').attr('aria-hidden','false');
					$('.header-close-menu').attr('aria-hidden','false');
                }
                else{

                    $('.sub-header-wrapper').attr('aria-hidden','true');
                    $('.sub-header-content').attr('aria-hidden','true');
                    $('.close-icon').attr('aria-hidden','true');
					$('.header-close-menu').attr('aria-hidden','true');
                }

        },
		showNavigationSearch: function() {
			$('.hamburger-menu').trigger('click');
        },
	
        closeMenu: function (el, evt) {
            if ($('.hamburger').hasClass('open')) {
                $('.hamburger')[0].click();
            }
        },
        fnMenuClose:function (el, evt) {
               $('#primary-nav-bar').removeClass('active');
			   $('body').css({'overflow': 'visible', 'position':'static'});
        },
        backToTopAction: function (ele, evt) {
            $("body,html").animate({
                scrollTop: 0
            }, 800);
            return  !1;
        },
        showSublevel: function (ele, evt) {
            $(ele).siblings('ul').slideToggle(100, 'linear', function () {
                if ($(ele).siblings('ul').is(':visible')) {
                    $(ele).text('-');
                } else {
                    $(ele).text('+');
                }
            });
        },
         onChangeInput: function() {
            $(".input-group input").on('input', function(){
                if ($(this).val()) {$('.close-x').addClass("icon-show");} else {$('.close-x').removeClass("icon-show");}
            }) 
        },
    	navSearchBox: function (ele, evt) {
            $('.nav-search').addClass('popup');
            $('.close-x').remove();
            $('.overlay').remove();
            $('.input-group').append('<button class="close-icon close-x" type="button"></button>')
            if ($(window).width() >= 1199) {
            $('.nav-search').after('<div class="overlay"> <button type="button" class="close-icon close-div" aria-label="Close"></button></div>');
            }
			
			$('.nav-search.popup .input-group input').css({"border":"1px solid #979797","border-left":"0"});
			$('.nav-search.popup .input-group .input-group-addon').css({"border":"1px solid #979797","border-right":"0"});
        return false; 
    },
	
	
	navSearchBoxFocusOut: function (ele, evt) {
		$('.nav-search.popup .input-group input').css({"border":"1px solid #BFBFBF","border-left":"0"});
		$('.nav-search.popup .input-group .input-group-addon').css({"border":"1px solid #BFBFBF","border-right":"0"});
			
	},
	
	closeNavSearch: function(ele,evt) {
        $('.close-x').remove();
        $('.overlay').remove();
        $('.nav-search').find('input').val('');
        $('.nav-search').removeClass('popup');
        return false;
    },
    clearNavSearch:function(ele,evt) {
        $('.close-x').hide();
        $('.nav-search').find('input').val('');
      
        return false
    },
        windowResize: function () {
            $(window).resize(function () {
                if ($(window).width() >= 1200) {
                    $('.primary-nav>ul>li').each(function (idx, obj) {
                        if ($(obj).children().length === 3) {
                            $(obj).find('ul').removeAttr('style');
                        }
                    });
					
                }
				self.inputWidth();
                self.recallMarginSet();
            	self.scrollHeader();
                self.RenderHeader();
            });
        },
        scrollHeader: function () {
            var isDesktop = $(window).width() >= 1025;
		var isTab = $(window).width() <= 1024;
            $(window).scroll(function () {
                var sticky = $('header'),
                    scroll = $(window).scrollTop();
                if (scroll >= ($(".global-header-wrapper").innerHeight() || 0)) sticky.addClass("scroll-sticky");
                else sticky.removeClass("scroll-sticky");                
                    var stickyHeight = $('.scroll-sticky').outerHeight();                
				   if($('#pricespider-div').length >=1) {
						var elementOffset = $('#pricespider-div').offset().top + 100;
				   
                    if ($(this).scrollTop() > (elementOffset-stickyHeight)) {
                    $('.brand-logo').addClass('non-selecter');
                    $('#navbar').show();
                    $('.scroll-sticky').addClass('sticky-border');
                    isDesktop && $('#primary-nav-bar').hide();
		    isTab && $('.mobile-viewMenu').hide();
                } else {     
                    $('.brand-logo').removeClass('non-selecter');               
                    $('#navbar').hide();
		 
                    isDesktop && $('#primary-nav-bar').show();
		   isTab &&  $('.mobile-viewMenu').show();
                    $('.scroll-sticky').removeClass('sticky-border');
                }
			}
 
            });
        },
        pageLoadActions: function () {
            var topHeight;
            $('.primary-nav>ul>li>a').each(function (idx, obj) {
                if ($('[data-link-attribute="' + $(obj).data('linkName') + '"]').length && !$(obj).hasClass("active-loaded")) {
                    $(obj).addClass("active-loaded");
                    $(obj).attr('href', '#' + $(obj).data('linkName')).on('click', function () {
                        $('.primary-nav').find('li>a.active').removeClass('active');
                        $(obj).addClass('active');
                        topHeight = $('header').height();
                        $('html, body').animate({
                            scrollTop: ($($(this).attr('href')).offset().top) - topHeight
                        }, 500);
                    });
                }
            });
        },
        setHashScroll: function () {
            var hashVal = window.location.hash;
            if (!hashVal || !$(hashVal).length || self.hashCheckCnt > 1) return;
            self.hashCheckCnt++;
            $('html, body').animate({
                scrollTop: ($(hashVal).offset().top) - ($('header').height() || 0)
            }, 500);
            return;
        },
        getNavTrackingValues: function (elem, evt, trackingData) {
            var trackingVal;
            if (trackingData != undefined)
                trackingVal = trackingData;

            else
                trackingVal = $(elem).data("analyticsTrackingId");

            if (_.isEmpty(trackingVal)) {
                console.log("Warn: Tracking value should not be empty.. ");
                return;
            }
            var valArr = trackingVal.split('|');
            var obj = {
                event_name: valArr[0] || '', 
                event_type: 'click', 
                item_category1: valArr[1].toLowerCase() || 'no-value', 
                item_category2: valArr[2].toLowerCase() || 'no-value', 
                item_category3: valArr[3].toLowerCase() || 'no-value',
                location_name: valArr[4].toLowerCase() || ''
            }
            var camelCaseName = camelize(valArr[0]);
            var evtName = camelCaseName.replace(/-/g, "");

            typeof sendNavToAnalytics == "function" && sendNavToAnalytics(obj, (evtName && evtName.toLowerCase() == "click" && elem.tagName == "A") ? "button" : evtName);

        },
        RenderHeader: function () {
            var path = '/bin/getNavigation',
                currentPath = $('#headerNodePath').val(),
                deviceType = '';
            if (window.innerWidth > 1199) {
                deviceType = 'desktop';
            } else {
                deviceType = 'mobile';
            }
            var obj = {
                "type": "get",
                "url": window.location.protocol + "//" + window.location.host + path + "?currentPath=" + currentPath + "&deviceType=" + deviceType
            }
            PLAYAEM.requestAPICall(obj, function(response) {
                if (response != '') {
                    var data = response,
                        elem = $('#primary-category-data'),
                        templateId = _.template(
                            $('#primary-category-template')
                            .html()
                            .trim()
                        ),
                        templateCollection;

                    var newArr = [];
                    var menuName , pageLevelParam;
                    _.map(data.navigationLinks, function (item) {
                        pageLevelParam = item.categoryNavLinks[0];
                        menuName = pageLevelParam.pageName;
                        item.className = pageLevelParam.subLinkClass;
                        newArr[menuName] = item;
                    });

                    templateCollection = templateId({
                        primaryNav: newArr,
                        menuNames: _.keys(newArr),
                        deviceName: deviceType,
                    });
                    elem.empty();
                    elem.append(templateCollection);
                    if (deviceType == 'mobile') {
			    $(".sub-header-content").addClass('mobileSignUp');
                        $('.newsletter-sign-up').insertAfter('.sub-header-content .nav-search');
                    } else{
                        self.wrapLis();
						
                    }
					self.inputWidth();
					self.renderLink();
                }
            });
        },
        truncateBreadCrumb: function () {
            if (PLAYAEM.isMobile) {
                $(".bc-truncate").each(function(i){
                    let len=$(this).text().length;
                    if(len>35)
                    {
                    $(this).text($(this).text().substr(0,35)+'...');
                    }
                });
            }
        },
		inputWidth: function(){
			
			var navWidth = $('.primary-nav').outerWidth(),
			 brandLogo = $('.brand-logo img').outerWidth(),
			 navKids = $('.nav-kids').outerWidth(),
             winWidth = $(window).width(),
             total, resizeValue;
				if(window.innerWidth > 1024 && window.innerWidth <= 1800) {
					if(navWidth >= 640) {
						total = (navWidth + brandLogo + navKids);
						resizeValue = ((winWidth - total) - 60);
						$('.nav-search').outerWidth(resizeValue+'px');
					}
				} else if ( window.innerWidth > 1800) {
				if(navWidth >= 640) {
					var leftRightGap =  $(window).width() - 1800;
					total = (navWidth + brandLogo + navKids + leftRightGap);
					resizeValue = ((winWidth - total) - 60 );
					$('.nav-search').outerWidth(resizeValue+'px');
				}
			}

	},
      removeRecallMar: function() {
        var headerHeight =  $('header').outerHeight();
        $('.recall-component').css({'margin-top':headerHeight});
    
     },
    recallMarginSet:function(){
        var newsLetterHeight =  $('.newsletter-sign-up').outerHeight();
        var headerHeight =  $('header').outerHeight();
        var marginTop = newsLetterHeight + headerHeight;
		var isTab = $(window).width() <= 1024;
        if(isTab){
       $('.recall-component').css({'margin-top':headerHeight});
             }
        else
               {
                setTimeout(function(){
                    if($('.newsletter-sign-up').is(':visible')) {                
                        $('.recall-component').css({'margin-top':marginTop});
                    } else {                
                        $('.recall-component').css({'margin-top':headerHeight});
                    }
                                 },1000);
               }
    },
	renderLink: function(){
				 $('.primary-nav ul li  a').each(function(){
					 var LinkHref = $(this).attr('href') || $(this).attr('data-url');
					if( LinkHref =='' || LinkHref == '#' || LinkHref == undefined ) {
						$(this).css({'cursor':'default'});
					}
				 })
	 },
	 mobileRecallSlideUp:function(ele) {
     	$(ele).toggleClass('arrowDown');
     	$('.mobile-container').toggleClass('active');
	 if($(ele).attr('aria-expanded')==='true'){
     		$(ele).attr('aria-expanded','false');
     	}
     	else{
     		$(ele).attr('aria-expanded','true');
     	}

 },	
		
        wrapLis: function(){
            var liEle = $(".subMenu-container .shop-by-brands .column-3");
            for(var i = 0; i < liEle.length; i+=6) {
                liEle.slice(i, i+6).wrapAll("<li class='wrapped-ul'><ul></ul></li>");
            }
        },
        init: function() {
            
	        if (!PLAYAEM.isDependencyLoaded || !$(this.el).length ) return;
            self = this;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.hashCheckCnt = 0;
            this.pageLoadActions();
            this.RenderHeader();
            this.truncateBreadCrumb();
            
			$('.grid-sort').removeClass('open');  
	    this.recallMarginSet();
            this.windowResize();
            this.scrollHeader();
            this.onChangeInput(); 
	        $(".category-container").find('.search-result').text().trim().replace("?", "<sup>&reg;</sup>");

     }
    }
    header.init();
    PLAYAEM.header = header;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            header.init();
        }
        header.pageLoadActions();
        header.setHashScroll();
    }, false);
}(window, PLAYAEM));
