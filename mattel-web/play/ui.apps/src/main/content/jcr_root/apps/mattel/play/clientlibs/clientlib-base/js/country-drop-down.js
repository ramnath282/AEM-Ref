/**
 * Gallery.js
 * Version 1.0
 * applied in gallery components like image, character, products..,
 */

(function(global, PLAYAEM) {
    var countryDropDown = {
        el: '.country-drop-down-menu',
        bindingEventsConfig: function() {
            var events = {
                "click .dropdown-active": "dropCountry",
            }
            return events;
        },
        dropCountry: function(ele, evt) {
            $('.menu-child').toggleClass('open');
        },
        render: function() {
            
        },
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$('.country-drop-down-menu').length) {
                return;}
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            
        }
    }
    countryDropDown.init();
    PLAYAEM.countryDropDown = countryDropDown;
    document.addEventListener('DOMContentLoaded', function() {
        if (PLAYAEM.isDependencyLoaded) {
            countryDropDown.init();
        }
    }, false);
}(window, PLAYAEM));
