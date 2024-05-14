/**
 * character Nav button.js
 * Version 1.0
 */
(function(global, PLAYAEM) {
    var prevCharacter = $('.cd-prev').data('url');
    var nextCharacter = $('.cd-next').data('url');
    var characterNavBtn = {
        el: '.char-detials-comp',
        bindingEventsConfig: function() {
            var events = {
                "click .cd-prev": "navPrev",
                "click .cd-next": "navNext",
            }
            return events;
        },
        navPrev: function(el, evt) {
            location = prevCharacter;
        },
        navNext: function(ele, evt) {
           location = nextCharacter;
        },
        render: function() {
            if(typeof prevCharacter === 'undefined'){
                $('.cd-prev').remove();
            }
            if(typeof nextCharacter === 'undefined'){
                $('.cd-next').remove();
            }
        },
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.characterNavBtn) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    characterNavBtn.init();
    PLAYAEM.characterNavBtn = characterNavBtn;
    
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            characterNavBtn.init();
        }
    }, false);
}(window, PLAYAEM));