/**
 * accordion.js
 * Version 1.0
 * 
 */

(function(global, PLAYAEM) {
    var isInitTriggered = false;
    var self,
		playAccordion = {
        el: '.accordion-component',
        bindingEventsConfig: function() {
            var events = {
                "click .arrow": "expandAccordian",
            }
            return events;
        },
        expandAccordian: function (ele, evt) {
            var thisComp = $(ele).parents(self.el),
                $ele = $(ele),
                $accordionContent = thisComp.find(".accordion-content");
            $accordionContent.removeClass("active");
            $ele.parent().next().addClass('active').slideToggle('fast');
            $accordionContent.not('.active').slideUp('fast');
            thisComp.find('.accordion').find('.accordion-title').removeClass("parent-accordion-expanded");
            if ($ele.hasClass('arrowUp')) {
                $ele.removeClass('arrowUp').addClass('arrowDown');
                $ele.attr("aria-expanded", "false");
            } else {
                thisComp.find('.accordion').find('.arrow').removeClass('arrowUp').addClass('arrowDown');
                thisComp.find('.accordion').find('.arrow').attr("aria-expanded", "false");
                $ele.removeClass('arrowDown').addClass('arrowUp');
                $ele.attr("aria-expanded", "true");
                $ele.parent().addClass("parent-accordion-expanded");
            }
        },
        render: function() {
            
        },
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || isInitTriggered) return;
            isInitTriggered = true;
            self = this;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    playAccordion.init();
    PLAYAEM.playAccordion = playAccordion;
    document.addEventListener('DOMContentLoaded', function() {
        if (!isInitTriggered) {
            playAccordion.init();
        }
    }, false);
}(window, PLAYAEM));