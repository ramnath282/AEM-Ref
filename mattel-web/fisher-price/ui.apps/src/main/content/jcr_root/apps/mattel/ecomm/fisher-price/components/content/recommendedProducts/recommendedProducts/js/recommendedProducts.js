/**
 * FP Recommended product.js
 * Version 1.0
 
(function(global, PLAYAEM) {
    var recommendedProduct = {
        el: '.recommended-products',
        bindingEventsConfig: function() {
            var events = {
                      //  "click #selectAllBrands": "emailPrefSelectAllbrands",
            }
            return events;
        },

         loadData:function(){
            $('.fp-recommend-widget').each(function(){
                $(this).owlCarousel({
                loop:false,
                nav:true,
                responsiveClass:true,
                lazyLoad:true,
                touchDrag:true,
                pullDrag:true,
                autoplay:false,
                responsive:{
                    0:{
                        items: 2,
                        margin: 20
                    },
                    600:{
                        items: 3,
                        margin: 40
                    },
                    992:{
                        items: 4,
                        margin: 40
                    },
                    1200:{
                        items: 5,
                        margin: 40
                    }
                }
            });
             })
           },

        render: function() {
            this.loadData();
        },

        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }

    recommendedProduct.init();
    PLAYAEM.recommendedProduct = recommendedProduct;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            recommendedProduct.init();
        }

    }, false);
}(window, PLAYAEM));   */
