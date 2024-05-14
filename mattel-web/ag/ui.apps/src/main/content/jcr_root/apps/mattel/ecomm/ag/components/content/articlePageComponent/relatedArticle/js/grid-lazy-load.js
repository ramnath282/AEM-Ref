/**
* events.js
* Version 1.0
*/
//  Handlebars compile cmd ---> handlebars articles.tmpl -f articles.tmpl.js
(function (global, AGAEM, apiConfig) {
    var self,
    articles = {
        el: '.grid-lazyload-module .article-list',
        eleList: '.grid-lazyload-module .article-list .list',
        showMoreBtn : '.article-show-action',
        heightSyncEl : '.tile-clickable',
        data : '',
        limit: 12,
        bindingEventsConfig: function () {
            var events = {
                'click .article-show-action': 'moreAction'
            };
            return events;
        },
        renderEventsDateFromAPI: function() {
            var ajaxSettings = apiConfig['articles'];
            AGAEM.requestAPICall(ajaxSettings, function (response) {
                if (response) {
                    self.data = response;
                    if(response.length > 0) {
                        if(response.length <= self.limit) {
                            $(self.showMoreBtn).hide();
                        }
                        self.loadArticles();
                    } else{
                        $(self.showMoreBtn).hide();
                    }
                } else {
                    console.log("Err : Articles API failed..");
                    return false;
                }
            });
        },
        moreAction : function(){ 
            self.loadArticles(true);
        },
        loadArticles : function(flag) {            
            var template = Handlebars.templates['articles.tmpl'];
            var avlTiles = $(self.el).find('.tile-content').length;
            if(avlTiles < self.data.length) {
                var context = {
                    items: self.data.slice(avlTiles,avlTiles + self.limit)
                }
                $(self.el).append(template(context));
                $(self.heightSyncEl).removeAttr("tabindex");
                if(flag) {
                    var lastTile =  $(self.heightSyncEl).eq(avlTiles);
                    $("html").animate({ scrollTop: lastTile.position().top }, 600);
                    $(self.heightSyncEl).eq(avlTiles).attr("tabindex",-1).focus();
                    var totalTiles = $(self.el).find('.tile-content').length;
                    if(totalTiles == self.data.length) {
                        $(self.showMoreBtn).hide();
                    }
                }
            } 
            $(self.el).find("img").imagesLoaded(function (elem, cur) {
                self.applyHeight();
            });
        },
        lastElementVisible : function(){
            var totalTiles = $(self.el).find('.tile-content').length;
            if(totalTiles == self.data.length) {
                return false;
            }
            var element = $("footer");
            var win = $(window);
            var viewport = {
                top : win.scrollTop(),
                left : win.scrollLeft()
            };
            viewport.right = viewport.left + win.width();
            viewport.bottom = viewport.top + win.height();
            var bounds = element.offset();
            bounds.right = bounds.left + element.outerWidth();
            bounds.bottom = bounds.top + element.outerHeight();
            return (!(viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom));
        },
        applyHeight : function(){
            $(self.heightSyncEl).css("height","auto");
            if($(window).width() >= 768){
                var heightArr =[]; 
                $(self.heightSyncEl).each(function(){
                    heightArr.push($(this).outerHeight());
                });
                heightArr.sort();
                $(self.heightSyncEl).css("height",heightArr[heightArr.length-1]);
            }
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            this.renderEventsDateFromAPI();
            self = this;

            var iScrollPos = 0;
            $(window).on("resize load scroll", function(e) {
                if($(self.showMoreBtn).length == 0 && self.data.length > self.limit) {
                    var iCurScrollPos = $(this).scrollTop();
                    if (iCurScrollPos > iScrollPos) {
                        if(self.lastElementVisible()){
                            self.loadArticles(true);
                            iScrollPos = iCurScrollPos;
                        }
                    }
                } else if(e.type="resize") {
                    setTimeout(function(){
                        self.applyHeight();
                    }, 700);
                }
            });
        }
    }
    AGAEM.articles = articles;
    document.addEventListener('DOMContentLoaded', function () {
        if (AGAEM.isDependencyLoaded) {
            articles.init();
        }
    }, false);
}(window, AGAEM, AGAEM.apiConfig));
