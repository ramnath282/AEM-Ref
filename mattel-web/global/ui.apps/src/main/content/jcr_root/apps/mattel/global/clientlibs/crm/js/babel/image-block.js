const GemCRMImageBlock = function () {
    self = this;
    this.el = '.imageblock-txt-wrapper';
    this.init();
};
GemCRMImageBlock.prototype = {
    heightSync(elem) {
        let max = -1;
        const $heightElem = $(elem).find(".tile-content");
        $(elem).find('img').imagesLoaded(() => { // image ready
            _.each($heightElem, el => {
                const height = $(el).innerHeight();
                max = height > max ? height : max;
            });
            $heightElem.css('height', `${max}px`);
        });
        return;
    },
    init() {
        if (!$(this.el).length) return;
        this.heightSync($(".imgtxt-list li"));
    }
};
let self;
const evtBinding = window.global.eventBindingInst;
const gemCRMImageBlockInit = new GemCRMImageBlock();