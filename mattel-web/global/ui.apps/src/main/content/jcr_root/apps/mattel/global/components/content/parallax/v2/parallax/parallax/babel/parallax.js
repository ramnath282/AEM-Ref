const config = {
    el: '.parallax-container',
}
class parallax {
    constructor() {
        self = this;
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
		AOS.init();
    }
    bindingEventsConfig() {
        const events = {};
        return events;
    }
    init() {	
    }
}
let self;
window.global.parallaxInstance = window.global.parallaxInstance || new parallax();
window.global.parallaxInstance.init();