import eventBinding from '../shared/eventBinding';

class accordion {
    constructor() {
        self = this;
        this.element = ".accordion-comp";
        self.openDiv();
		self.indexVal();
        console.log("Accordion constructor");
        $('.accordion-comp p').filter(function(){
            return this.innerHTML == ' ';
        }).remove();
    }
    openDiv() {
        $("div.accordion-section").eq(0).find("div.dropmenu").addClass("in");
    }
	 indexVal(){
        $(self.element).parent().css( "z-index", "1" );
    }
    initVideo(){
        let $ele = $(".accordion-section .aem-video-player");
        if(!$ele.length) return;
        let videoId = $ele.data('videoId') || '';
        if(videoId.indexOf("scene7")!=-1){
            $ele.html(`<video class="accordion-video-container" controls><source src="${videoId}" type="video/mp4" /></video>`)
        } else if(videoId.length<=32){
            const {initDeluxePlayer,createDeluxePlayer} = window.global;
            if(!initDeluxePlayer){
                console.log(`%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.`, "background: red; color:white");
                return;
            }
            initDeluxePlayer((cb) => {
                createDeluxePlayer("accordion-video-player-1",videoId,(player)=>{
                    if ($(`#sub-accordion-video-player-1`).length) {
                        $(`#sub-accordion-video-player-1`).css({
                            "width": '100%',
                            "padding-top": '56.25%',
                            "height": "auto"
                        });
                    }
                });
            }, true);
        }
    }
    init() {
        self.initVideo();
            eventBindingInst.bindLooping({
            "click .accordion-section .accordion-title": "btnCollapse",
            "click .accordion-section .accordion-title a": "btnCollapse"
            },self);
                    
    }
    btnCollapse(el,evt){
        evt.preventDefault();
        setTimeout(function(){$(window).scroll()}, 200)
        if ($('.dropmenu').is(':visible') || $('.dropmenu').hasClass('show')) {
                $(".dropmenu").removeClass('show');
                $(".accordion-title").children().attr('aria-expanded',false);
                $(el).parent().removeClass('active');   

        }
        if($(el).next('.dropmenu').is(':visible')){
            $(el).next(".dropmenu").collapse('hide');
            $(el).children().attr('aria-expanded',false);
            $(el).parent().removeClass('active');
            
        } else{
            $(el).next(".dropmenu").collapse('toggle');
            $(el).children().attr('aria-expanded',true);
            $(el).parent().addClass('active');            
        }        
    }
    
}

let self,
    eventBindingInst = new eventBinding(),
    accord = new accordion();
    accord.init();