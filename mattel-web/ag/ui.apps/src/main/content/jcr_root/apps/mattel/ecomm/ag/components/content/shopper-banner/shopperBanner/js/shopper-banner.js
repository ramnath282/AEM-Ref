/**
 * Banner.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var banner = {
        el: '.hero-banner',
        bannerBackground: function () {
            var bannerBgndElement = $(this.el + '[data-src]');
            _.each(bannerBgndElement, function (item) {
                var image = new Image();
                image.src = $(item).data("src");
                image.onload = function () {
                    $(item).css({
                        'background-image': "url(" + image.src + ")",
                        'height': image.naturalHeight
                    });

                }
            });
        },
        pageLoadActions: function(){
            if ($(this.el + '[data-src]').length) this.bannerBackground();
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length) return;

        }
    }
    banner.init();
    document.addEventListener('DOMContentLoaded', function () {
        banner.pageLoadActions();
        if (!AGAEM.isDependencyLoaded) {
            banner.init();
        }
    }, false);
    // Fix for - After travelling back in Firefox history, JavaScript won't run
    window.onunload = function(){};
}(window, AGAEM));
