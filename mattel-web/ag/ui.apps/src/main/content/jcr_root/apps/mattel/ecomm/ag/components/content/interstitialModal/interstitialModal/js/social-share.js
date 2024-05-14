/**
 * social-share.js
 * Version 1.0
 */
/*
These data attributes to be added for social icons href element
data-url=""  
data-title=""
data-description="" 
data-media="" 
data-link-attr="facebook" // should not be translated
*/
(function (global, AGAEM) {
    var self,
        socialShare = {
            el: '#addthis_sharing_toolbox',
            socialShareBtn: '',
            socialGoTo: '',
            interstitialPopup: '.social-media-action',
            tileWrapper: '.tile-container',
            bindingEventsConfig: function () {
                var events = {
                    'click .social-icons a': 'socialShareAction'
                };
                return events;
            },
            socialShareAction: function (ele, evt) {
            	$("img").attr("nopin","nopin");
  			   	$(ele).parents("li").find("img").removeAttr("nopin");
                var obj = {
                    title: $(ele).attr("data-title"),
                    description: $(ele).attr("data-description"),
                    imagPath: $(ele).attr("data-media"),
                    url: $(ele).attr("data-url")
                };
                self.socialGoTo = $(ele).attr("data-link-attr");
                if (!self.socialGoTo) {
                    console.log("Err: social data link attribute missing..");
                    return;
                }
                switch (self.socialGoTo.toLowerCase()) {
                    case "facebook":
                        self.socialShareBtn = ".at-svc-facebook";
                        break;
                    case "twitter":
                        self.socialShareBtn = ".at-svc-twitter";
                        break;
                    case "pinterest":
                        self.socialShareBtn = ".at-svc-pinterest_share";
                        break;
                }
                if (!self.socialShareBtn) {
                    console.log("icons not matched with addthis class names..");
                    return;
                }
                addthis.init();
                self.addThisRefresh(obj);
                self.shareContent(ele, evt);
            },
            addThisRefresh: function (obj) {
                    var url = obj.url ? (obj.url.indexOf("http") != -1 ? obj.url : (window.location.hostname + "" + obj.url)) : window.location.href;
                    $(self.el).attr({
                        "data-title": obj.title,
                        "data-description": obj.description,
                        "data-media": obj.imagPath ? (window.location.hostname+""+obj.imagPath) : '',
                        "data-url": url
                    });
            },
            shareContent: function (el, eve) {
                $(self.socialShareBtn)[0].click();
                eve.preventDefault();
            },
            loadSocialDataAttr: function () {
                var imgSrc = "";
                var $socialIcon;
                $(self.tileWrapper).each(function () {
                    imgSrc = $(this).find(".tile-img").length == 0 ? $("#article-hero-img").attr('src') : $(this).find(".tile-img").attr('src');
                    $socialIcon = $(this).find(".social-icons a");
                    //$(this).find(".tile-img").length == 0 ? imgSrc= $("#article-hero-img").attr('src') : imgSrc = $(this).find(".tile-img").attr('src');
                    $socialIcon.attr({
                        "data-media": $socialIcon.attr("data-media") || imgSrc,
                        "data-title": $socialIcon.attr("data-title") || $(this).find(".tile-name").text(),
                        "data-description": $socialIcon.attr("data-description") || $(this).find(".tile-description").text()
                    });
                });
            },
            init: function () {
                if (!AGAEM.isDependencyLoaded || !$(this.el).length) return;
                AGAEM.bindLooping(this.bindingEventsConfig(), this);
                self = this;
                self.loadSocialDataAttr();
            }
        }
    AGAEM.socialShare = socialShare;
    document.addEventListener('DOMContentLoaded', function () {
        if (AGAEM.isDependencyLoaded) {
            socialShare.init();
        }
    }, false);
}(window, AGAEM));
