/**
 * FP product page.js
 * Version 1.0
 */
(function(global, PLAYAEM) {
    var fpProductInfoPage = {
        el: '.fpProductinfo-module',
        bindingEventsConfig: function() {
			 var events = {
				"click .age-badges-tooltip .badge-txt": "tooltipDisplay",
				"click .age-badges-tooltip .age-info-icon": "tooltipDisplay",
				"click .age-badges-tooltip .tooltiptext": "tooltipHide",
				"focus .age-badges-tooltip .badge-txt": "tooltipHide",
				"focusout .age-badges-tooltip .tooltiptext": "tooltipHide",
				"focus .age-badges-tooltip .age-info-icon": "tooltipDisplay",
				// "click #starLink": "starLinkClick",
				"click .readmore": "scrollTOTargetDiv",
				
			}
			 return events;
        },
		scrollTOTargetDiv: function(curEle,evt){
			evt.preventDefault();
			var scrollElemId = $(curEle).attr('href');
			var $scrollElem = $(scrollElemId);
			var signUpBarHeight = PLAYAEM.isMobile ? 0 : $(".newsletter-sign-up").height() || 0;
			console.log($scrollElem);
			if($scrollElem.length){				
				$("html,body").animate({'scrollTop': $scrollElem.offset().top-($('header').height()+signUpBarHeight)})
			}
			
		},
		tooltipDisplay: function() {
			$('.age-badges-tooltip .info-tooltip .tooltiptext').toggleClass('active');
		},
		tooltipHide: function(ele) {
			$('.age-badges-tooltip .info-tooltip .tooltiptext').removeClass('active');
		},
	    // starLinkClick: function() {
		//     var height = $('#BVRRContainer').offset().top - $(".scroll-sticky").height();
		//     jQuery(window).scrollTop(height); 
	    // },
		
        textTruncate: function () {
            var self = this;
            $(self.el).each(function (idx, pdpComps) {
                $(pdpComps).each(function (idxx, txtElemCnt) {
                    var productSummaryLength = $('.product-summary',txtElemCnt).text().length;
                    var truncateCount=parseInt($('.product-summary',txtElemCnt).data('truncate'));
                    var str = $('.product-summary',txtElemCnt).text().substr(0,truncateCount);
                    var productSummaryToShow = str.lastIndexOf(" ");
                    if (productSummaryLength > truncateCount) {
                        $('.product-summary',txtElemCnt).text(str.substr(0, productSummaryToShow) + '…');
                    }
            });
        }) 
        },

        tooltipWidth: function(){
            if (PLAYAEM.isMobile) {
                var w = $(window).width();
                var actualWidth = w - 33;
                $('.tooltiptext').css('width', actualWidth);
            }
        },
        productBadge: function() {
        var ifbadgePresent = document.getElementById('productBadge').value;
        if(!ifbadgePresent){
            sessionStorage.removeItem('productBadge');
        }
        var productBadge = sessionStorage.getItem("productBadge");
        var currentPagePath = $('#currentPdpPagePath').val();
            var getProducts = "/bin/getProductBadge";
            var obj = {
                    "async": false,
                    "type": "get",
                    "params": "",
                    "url": window.location.protocol + "//" + window.location.host + getProducts + "?currentPath=" + currentPagePath +"&sessionStorageValue="+productBadge
                }

                PLAYAEM.requestAPICall(obj, function(response) {
                    if (!response) {
                        console.log("Err : Upcoming Events API failed..");
                        return false;
                    } else {
                        var badgeDisplayValue = response.BadgeDisplayValue,
                         badgeColour = response.BadgeColour,
                         badgeTitle = response.Badge,
                         badgeIcon = response.BadgeIcon,
                         textColour = response.textColour;
                         if(badgeDisplayValue !='' && badgeDisplayValue !=undefined) {
                            $('.product-badges').html(badgeDisplayValue);
                            $('.product-badges').css({"color":textColour,"background-color":badgeColour,"border":"1px solid "+badgeColour});
                            $('.fpProductinfo-module.recall-fpProductinfo-module .product-badges').css({color:textColour,"background-color":badgeColour,border:"2px solid "+textColour});
                            $('.product-badges').prepend("<img src='"+badgeIcon+"' alt='"+badgeTitle+"' />");
                            sessionStorage.setItem("productBadge",badgeTitle);
                        }

                    }

                });

    },

        
        render: function() {
        },

        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    
    fpProductInfoPage.init();
    PLAYAEM.fpProductInfoPage = fpProductInfoPage;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            fpProductInfoPage.init();
        }
        fpProductInfoPage.textTruncate();
        fpProductInfoPage.tooltipWidth();
		$('body').on('click',function() {
			$('.age-badges-tooltip .info-tooltip .tooltiptext').removeClass('active');
			
		});
        fpProductInfoPage.productBadge();
		
    }, false);
}(window, PLAYAEM));
