const config = {
	windowHeight: 500,
	windowWidth: 500,
	sameWindow: 'self',
	newTab: 'newTab',
	newWindow: 'blank',
}


class elementLinkUrl {
	constructor() {
		self = this;
	}

	init() {
		window.global.eventBindingInst.bindLooping({
			"click .link-element": "linkElement",
		}, self);
	}

	linkElement(el,event) {
        if($(event.target).hasClass("slick-arrow") || $(event.target).parents().hasClass("slick-dots") || $(event.target).hasClass("btn-cta-item")) {
            return;
        }
		const devlink = $(el).attr('data-element-link');
		const openWindow = $(el).attr('data-open-window');
		const setWidth = `width=${config.windowWidth},height=${config.windowHeight}`;

		if (openWindow == config.sameWindow) {
			window.open(devlink, "_self");
		} else if (openWindow == config.newTab) {
			window.open(devlink, '_blank');
		} else if (openWindow == config.newWindow) {
			window.open(devlink, " ", setWidth);
		} else {
			window.open(devlink, "_blank");
		}
	}
}

let self;
window.global.elementlinkAttr = window.global.elementlinkAttr || new elementLinkUrl();
window.global.elementlinkAttr.init();
