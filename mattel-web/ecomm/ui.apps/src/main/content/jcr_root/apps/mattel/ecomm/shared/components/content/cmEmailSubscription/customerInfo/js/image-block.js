/**
 * retailer-popup.js
 * Version 1.0
 */
(function (global, CRMAEM) {
    var image_block = {
        el: '.imageblock-txt-wrapper',
        bindingEventsConfig: function () {
            var events = {
                
            }
            return events;
        },
        heightSync: function (elem) {
            var max = -1;
             var $heightElem = $(elem).find(".tile-content");
            $(elem).find('img').imagesLoaded(function () { // image ready
                _.each($heightElem, function (el) {
                    var height = $(el).innerHeight();
                    max = height > max ? height : max;
                });
                $heightElem.css('height', max + 'px');
            });
            return;
        },
        init: function () {
            if (!CRMAEM.isDependencyLoaded || !$(this.el).length || CRMAEM.image_block) return;
            CRMAEM.bindLooping(this.bindingEventsConfig(), this);
             this.heightSync($(".imgtxt-list li"));
        }
    }
    image_block.init();
    CRMAEM.image_block = image_block;
    document.addEventListener('DOMContentLoaded', function () {
        if (!CRMAEM.isDependencyLoaded) {
            image_block.init();
        }
    }, false);
}(window, CRMAEM));
