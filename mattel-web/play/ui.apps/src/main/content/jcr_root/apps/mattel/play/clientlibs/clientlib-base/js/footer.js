(function (global, PLAYAEM) {

    'use strict';


    var playFooter = {

        el: '.footer-links-accordion',

        bindingEventsConfig: function () {

            var events = {

                "click .accordion-action": "expandAccordion",

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

       

        ariaExpandedAdded: function(ele,isMobileOnly){

            if(!ele){

                console.log("Aria expanded attribute element undefined..");

                return;

            }

            if(isMobileOnly && !PLAYAEM.isMobile) return;

            _.each(ele,function(item){

                $(item).attr("aria-expanded",false);

            });

        },

        init: function () {

            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.playFooter) return;

            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);

            this.ariaExpandedAdded($(".footer-links-accordion .accordion-action a"),true);

        }

    }

    playFooter.init();

    PLAYAEM.playFooter = playFooter;

    document.addEventListener('DOMContentLoaded', function () {
    	 playFooter.init();
       
     }, false);

}(window, PLAYAEM));
