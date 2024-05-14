/**
 * grid.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var grid = {
        el: '.grid-module',
        bindingEventsConfig: function () {
            var events = {
                "click .accordion-grid .action-text.accordion": "expandDetail",
            }
            return events;
        },
        addEmptyElement : function(elem){
            elem = $(elem).find('.col-3').length && $(elem).find('.col-3') || $(elem).find('.col-2-md');
            if(AGAEM.isMobile || !elem.length) return;
            var positionCount = $(elem).hasClass('col-3') ? 3 : 2;
            $(elem).find('.empty-block').remove();
            var $elem = $(elem).find('>li');
            var placeToPosition = window.innerWidth >= 1024  ? positionCount : 2;
            for(var i = 0; i < $elem.length; i+=placeToPosition) {
                $elem.eq(i+(placeToPosition-1)).after("<li class='empty-block'></li>");
              }
        },
        expandDetail: function (ele, evt) {
            var $expandEle = $(ele).next('.accordion-content'),
                expandText = $(ele).data('text-expand'),
                collapseText = $(ele).data('text-collapse')
            if (!$expandEle.length) {
                console.log("War: Accordion Element not found ..class name " + ele);
                return;
            }
            if (!$(ele).closest('.tile-action').hasClass('active')) {
                $(ele).closest('.tile-action').addClass('active');
                $(ele).text(expandText).attr('aria-expanded','true');
                $expandEle.slideDown();
            } else {
                $(ele).closest('.tile-action').removeClass('active');
                $(ele).text(collapseText).attr('aria-expanded','false')
                $expandEle.slideUp();
            }
        },
        heightSync : function(){
            var $heightSyncElem = $(this.el + '[data-height-sync]');
            _.each($heightSyncElem, function (item) {
                var max = -1;
                if ($(item).find('img').length && typeof $.fn.imagesLoaded=="function") {
                    $(item).find('img').imagesLoaded(function(){ // image ready
                        _.each($(item).find($(item).data('height-sync')), function (el) {
                            //var res_btn = $('.reservation-btn').length>0 ? parseInt($('.reservation-btn').outerHeight()) : 0;
                            //var height = parseInt($(el).outerHeight())+ res_btn;
                            var height = $(el).outerHeight();
                            max = height > max ? height : max;
                        });
                        $(item).find($(item).data('height-sync')).css('height', max + 'px');
                    });
                    return;
                }
                _.each($(item).find($(item).data('height-sync')), function (el) {
                    //var res_btn = $('.reservation-btn').length>0 ? parseInt($('.reservation-btn').outerHeight()) : 0;
                    //var height = parseInt($(el).outerHeight())+ res_btn;
                    var height = $(el).outerHeight();
                    max = height > max ? height : max;
                });
                $(item).find($(item).data('height-sync')).css('height',max+'px');
                
            });
        },
        pageLoadActions: function(){
            if ($('[data-toggle="popover"]').length) $('[data-toggle="popover"]').popover({html: true});
        },
        render : function(){
            var self = this;
            if ($(this.el).data("height-sync") && !AGAEM.isMobile) this.heightSync();  
            if ($(this.el).hasClass("accordion-grid")){
                $(window).on("load resize", function () {
                    if ($(self.el).data("height-sync") && !AGAEM.isMobile) self.heightSync();
                    if ($(self.el + '.accordion-grid').length == 1) {
                        self.addEmptyElement($(self.el + '.accordion-grid'));
                    } else if ($(self.el + '.accordion-grid').length > 1) {
                        _.each($(self.el + '.accordion-grid'), function (item) {
                            self.addEmptyElement(item);
                        })
                    }
                })
            } 
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.grid) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    grid.init();
    AGAEM.grid = grid;
    document.addEventListener('DOMContentLoaded', function () {
        AGAEM.pageLoadActions();
        if (!AGAEM.isDependencyLoaded) {
            grid.init();
        }
    }, false);
}(window, AGAEM));

