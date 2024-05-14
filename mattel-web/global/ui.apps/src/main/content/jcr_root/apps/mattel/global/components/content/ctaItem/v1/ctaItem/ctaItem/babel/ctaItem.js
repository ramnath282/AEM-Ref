const config = {
    windowHeight:600,
    windowWidth:600,
    carouselctaUrl:null
 }
 
class ctaItemModal {

    constructor(){
        self = this;
    }

    init() {
        window.global.eventBindingInst.bindLooping({
            "click .cta-interstitial .modal-anchor": "interstitialModal2",
            "click .close": "closeInterstitialModal",
            "click .go-back-btn": "closeInterstitialModal",
            "click .continue-btn": "continueInterstitialModal",
            "click .interstitial-modal": "OutInterstitialModal",
            "click .modal-dialog":"preventClickContainer",
            
        },self);
        $(document).ready(function(){
            $("#interstitialModal").on("shown.bs.modal", f => {
                self.tagetUrl = $(f.relatedTarget).data("url");
            });
            $("#interstitialModal").on("hidden.bs.modal", f => {
                self.fromctaitem = false;              
            });
            $('.modal-anchor').each(function(index,value){
                let lightboxLength = $(this).parents('.ctaItem').find('.lightBoxContainer').length;
                if(lightboxLength){
                    $(this).addClass("has-light-box").attr("modal-name", `.light-box-container${index}`);
                    let node = $(this).next().find('.lightBoxContainer').addClass(`light-box-container${index}`)
                    
                    $(".root.responsivegrid").find('.aem-Grid').eq(0).append(node);
                    $(this).parents('.ctaItem').find('.lightBoxContainer').remove();
                }
                index++;
            });
        });
    }

   	interstitialModal2(el){
        self.fromctaitem = true;
        $("body").addClass("modal-open");
        let $el = $(el);
       const container = $el.closest(".cta-button-container").find(".gallery-tile").find("ul");
       const items = $(el).closest(".cta-button-container").find(".hidden-cta").children(); 
        container.append(items);
        if($el.hasClass("has-light-box")) {
            let modalClass = $el.attr("modal-name");
            $(modalClass).find(".modal").modal('show');
        } else {
            if($el.parents('.carouselContainer').length && $el.parents('.grid-column-one').length){
                self.fromCarouselctaitem = true;
                config.carouselctaUrl = $el.attr("data-url");
                let customThemeClass = ["dark-theme", "light-theme", "modal-text-center", "modal-text-left", "modal-text-right"];
                let modalthemeClass = $(el).parents(".ctaContainer").length ? $(el).parents(".ctaContainer").attr('class').split(' '): '';
                let themeclassArr=[];
                for(var i in customThemeClass){
                        if(modalthemeClass.indexOf(customThemeClass[i]) > -1){
                            themeclassArr.push(customThemeClass[i])
                        }
                }
                let themeClass = themeclassArr.join(" ").toString();  
                $(el).blur();
                if(!$('.duplicateModalContainer').length){
                    $('body').append('<div class="duplicateModalContainer"></div>');
                }
                $('.duplicateModalContainer').html('');
                var modalHtml = $el.parents(".cta-interstitial").find('.interstitialmodal-global').html();
                $('.duplicateModalContainer').html(modalHtml);
                $('.duplicateModalContainer .modal').addClass(themeClass).show();
            }
            else{
                $el.parents(".cta-interstitial").find(".modal:first").show()
            }            
            $el.parents('.cta-interstitial').find('.continue-btn').removeAttr("href");
        }
        
     }

     closeInterstitialModal(el) {
        self.fromctaitem, self.fromCarouselctaitem = false;
        $("body").removeClass("modal-open"); 
        let $el = $(el);
        $el.parents('.interstitial-modal:first').hide();
  
     }

     continueInterstitialModal(el,evt) {
        evt.preventDefault();
        let $el = $(el);
        const setWidth = `width=${config.windowWidth},height=${config.windowHeight}`;

        const windowOption = $el.attr('data-interstitial-window');
        let continueUrl = null;
        if(self.fromctaitem){
            continueUrl = $el.parents('.cta-interstitial').children('.modal-anchor').attr("data-url");
        }
        if(self.fromctaitem && self.fromCarouselctaitem){
            continueUrl = config.carouselctaUrl;
        }
        if(!continueUrl) {
            continueUrl = self.tagetUrl;
        }

        switch(windowOption) {
  
         case "newTab":
         window.open(continueUrl,'_blank');
         break;
         
         case "newWindow":
         window.open(continueUrl," ",setWidth);
         break;
         
         default:
         window.open(continueUrl,"_self");
   
        }
    }

     OutInterstitialModal(el){
        let $el = $(el);
        $el.hide();
        $('body').removeClass('modal-open');
    }
    preventClickContainer(curEle, el){
           el.stopPropagation();
     }

}

let self;
window.global.ctaItemModalBox= window.global.ctaItemModalBox || new ctaItemModal();
window.global.ctaItemModalBox.init();
