/**
* Brand and Category Listing.js
* Version 1.0
*/
(function(global, PLAYAEM) {
    var brandlisting = {
        el: '.brand-container',
       bindingEventsConfig: function () {
            var events = {               
                      "click .brand-more a":"brandshowMoreData"
            }
            return events;
        },

        brandshowMoreData: function(ele){
                    var moretext = $(ele).attr('data-text-collapse');
                    var lesstext =  $(ele).attr('data-text-expand');
                    
                    $(ele).toggleClass('active');
                    if($(ele).hasClass("less")) {
                        $(ele).removeClass("less");
                        $(ele).html(moretext);
			$(ele).attr('aria-expanded','false');
			$('html, body').animate({
                        scrollTop:($(ele).parents('.brand-container').find('.brandlist').offset().top)-50}, 500);
                    } 
		    else {
                        $(ele).addClass("less");
                        $(ele).html(lesstext);
                        $(ele).attr('aria-expanded','true');
                        $(ele).prepend('<span class="sr-only"> content updated</span>');
                    }
                    $(ele).parent().parent().find('.brandlist').toggleClass('tablist');
					

        },
        
        seeMoreHide:function() {
               var liLength = $('.brand-more').parent().parent().find('.brandlist');
               $(liLength).each(function(){
                   var self = $(this);
                 var li =  $(this).find('li').length;
                if(li <= 4) {
                    self.next().hide();
                }   
               })
            
        },
        render: function() { 
           this.seeMoreHide();
                 
        },

        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    
    brandlisting.init();
    PLAYAEM.brandlisting = brandlisting;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            brandlisting.init();
        }       
    }, false);
}(window, PLAYAEM));
