/**
 * Global Footer.js
 * Version 2.0
 */
const count = 0,
    isMobile = window.innerWidth <= 767,
    globalFooter = {
        el: '.footer-links-accordion',
        bindingEventsConfig() {
            const events = {
                "click .accordion-action:not('.footer-legal')": "expandAccordion",
            };
            return events;
        },
        expandAccordion(elem, evt) {
            evt.preventDefault();
            $(elem).siblings('.accordion-container li:first-child').focus();
            $(elem).siblings('.accordion-container').slideToggle()
            $(elem).toggleClass('expand-in');
            $(elem).find('a').attr("aria-expanded", $(elem).hasClass("expand-in") ? true : false);
        },
        ariaExpandedAdded(ele, isMobileOnly) {
            if (!ele) {
                console.log("Aria expanded attribute element undefined..");
                return;
            }
            if (isMobileOnly && !isMobile) return;
            _.each(ele, item => {
                $(item).attr("aria-expanded", false);
            });
        },
        init() {
            if (!$(this.el).length || window.global.globalFooter) return;
            evtBinding.bindLooping(this.bindingEventsConfig(), this);
            this.ariaExpandedAdded($(".footer-links-accordion .accordion-action a"), true);
        }
    };
const evtBinding = window.global.eventBindingInst;
globalFooter.init();
window.global.globalFooter = globalFooter;
document.addEventListener('DOMContentLoaded', () => {
    globalFooter.init();
}, false);
$(window).on("load", () => {
    const hashVal = window.location.hash;
    if (hashVal && (hashVal == "#assembly-videos")) {
        $(".accordion-content").show();
        $(".arrow").removeClass("arrowDown").addClass("arrowUp");
        $('html, body').animate({
            scrollTop: ($("#assembly-videos").offset().top) - ($('header').height() || 0)
        }, "fast");
    }
});