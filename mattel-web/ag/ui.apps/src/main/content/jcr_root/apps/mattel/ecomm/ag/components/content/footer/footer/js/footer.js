/**
 * footer.js
 * Version 1.0
 */
(function (global, AGAEM) {
    'use strict';
    var count= 0,
        AGFooter = {
        el: '.global-footer',
        bindingEventsConfig: function () {
            var events = {
                "click .global-footer .accordion-action": "expandAccordion",
                "click #footer-control-link": "expandFooter"
            }
            return events;
        },
        expandAccordion: function(elem,evt){
            evt.preventDefault();
            $(elem).siblings('.accordion-container li:first-child').focus();
            $(elem).siblings('.accordion-container').slideToggle()
            $(elem).toggleClass('expand-in');
            $(elem).find('a').attr("aria-expanded", $(elem).hasClass("expand-in") ? true:  false);
        },
        expandFooter: function(elem, evt){
            evt.preventDefault();
            var viewlessText = $(elem).attr("data-viewless"),
                viewMoreText = $(elem).attr("data-viewmore");
                var readmoreState = $(elem).attr("aria-expanded");
                readmoreState = readmoreState == 'false' ? false : true;
                $(elem).attr("aria-expanded", !readmoreState);
            if(!$(elem).hasClass("active")){
                if(!count){
                    $(".secondary-footer").show();
                    count++;
                }
                $(".secondary-footer").slideDown(function() {
                    $(elem).text(viewlessText).addClass("active");
                    $('html, body').animate({scrollTop: $("#footer-control-link").offset().top}, 500);
                });
            } else{
                $(".secondary-footer").slideUp(function() {
                    $(elem).text(viewMoreText).removeClass("active");
                    $('html, body').animate({scrollTop: $("#footer-control-link").offset().top}, 500);
                });
            }
        },
        ariaExpandedAdded: function(ele,isMobileOnly){
            if(!ele){
                console.log("Aria expanded attribute element undefined..");
                return;
            }
            if(isMobileOnly && !AGAEM.isMobile) return;
            _.each(ele,function(item){
                $(item).attr("aria-expanded",false);
            });
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.AGFooter) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            this.ariaExpandedAdded($(".global-footer .accordion-action a"),true);
        }
    }
    AGFooter.init();
    AGAEM.AGFooter = AGFooter;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            AGFooter.init();
        }
    }, false);
}(window, AGAEM));
