import ajaxRequest from '../shared/ajaxbinding';
import { checkAnyFeaturedPrice } from "../shared/promo-sale-grid";
import { truncateInit } from '../shared/textTruncate';

const domainConfig = location.protocol + '//' + location.hostname;
const config = {
    carousel: {
        FP: () => {
            return {
                dots: {
					XL: true, // >1200
                    LG: true, // 1024 - 1199
                    MD: true, // 768 - 1023
                    SM: true, // 480 - 767
                    XS: false // < 479
				},
                arrows: true,
                innerArrow: true,
                autoplay: true,
                slideToDisplay: {
                    XL: 4, // >1200
                    LG: 4, // 1024 - 1199
                    MD: 2, // 768 - 1023
                    SM: 2, // 480 - 767
                    XS: 1 // < 479
                }
            }
        },
        AG: () => {
            return {
                dots: {
					XL: true, // >1200
                    LG: true, // 1024 - 1199
                    MD: true, // 768 - 1023
                    SM: true, // 480 - 767
                    XS: false // < 479
				},
                arrows: true,
                innerArrow: true,
                autoplay: true,
                slideToDisplay: {
                    XL: 5, // >1200
                    LG: 4, // 1024 - 1199
                    MD: 2, // 768 - 1023
                    SM: 2, // 480 - 767
                    XS: 1 // < 479
                }
            }
        }
    },
    productAttrs: {
        FP: (ele) => {
            return {
                title: true,
                rating: true,
                promoLabel: true,
                price: true,
                inventoryStatus: true,
                quickView: ele.data('quickviewEnabled'),
                urlPrefix:  domainConfig.substring(domainConfig.length - 1) == '/' ? domainConfig.substring(0, domainConfig.lastIndexOf("/")) : domainConfig
            }
        },
        AG: (ele) => {
            return {
                title: true,
                rating: true,
                promoLabel: true,
                price: true,
                inventoryStatus: true,
                quickView: ele.data('quickviewEnabled'),
                urlPrefix:  (domainConfig.substring(domainConfig.length - 1) == '/' ? domainConfig.substring(0, domainConfig.lastIndexOf("/")) : domainConfig)+"/shop/p/"
            }
        }
    },
    serviceCall: {
        FP: (el) => {
            return `${$("#snpAccountUrl").val()}&${el.data('snpQueryParam')}`;
        },
        AG: (el) => {
            return `${$("#snpAccountUrl").val()}&${el.data('snpQueryParam')}`;
        }
    },
    textTruncate: {
        desktop: 2,
        tabletPortrait: 2,
        tablet: 2,
        mobilePortrait: 3,
        mobile: 3,
    }
};
let self;
const productCarousel = function () {
    self = this;
    this.ele = ".product-carousel-grid:not(.loaded)";
    this.deviceName = window.global.deviceName();
    this.brandName = "AG";
    this.storageName = "ProductCarouselData";
    this.storageExpirationInMin = 15;
    this.clearStorage = true;
    this.init();
};

productCarousel.prototype = {
    bindingHelperFn() {
        handleBarsHelperInst.callRegisterHelper("changeToHyphen");
        handleBarsHelperInst.callRegisterHelper("indexStartWith1");
        handleBarsHelperInst.checkIFConditions("ifEquals");
        handleBarsHelperInst.checkIFConditions("ifNotEquals");
        handleBarsHelperInst.checkIFConditions("greaterThan");
        handleBarsHelperInst.checkIFConditions("isLessThanMonths");
        handleBarsHelperInst.checkIFConditions("ifEqualsPrice");
        handleBarsHelperInst.checkIFConditions("parseFloat");
        handleBarsHelperInst.checkIFConditions("finalActualPrice");
    },
    applyCarousel: function (el) {
        const props = config.carousel[self.brandName](el);
        const { slideToDisplay } = props;
		const { dots } = props;
        const settings = {
            dots: dots.XL || false,
            arrows: props.arrows || false,
            infinite: props.rewind || false,
            speed: props.speed || 300,
            slidesToShow: slideToDisplay.XL || 1,
            slidesToScroll: slideToDisplay.XL || 1,
            centerMode: props.centerMode || false,
            adaptiveHeight: false,
            autoplay: props.autoPlay || false,
            autoplaySpeed: props.autoPlaySpeed || 2000,
            variableWidth: props.autoWidth || false,
            prevArrow: `<button type='button' class='slick-prev'><div class='arrow-image-prev'><img src='/content/dam/ag-dam/ag-global-dam/parent-site-dam/images/ag-theme-icons/slider-arrow-left-2x.png' onmouseover="this.setAttribute('src', '/content/dam/ag-dam/ag-global-dam/parent-site-dam/images/ag-theme-icons/slider-arrow-left-active-2x.png');" onmouseout="this.setAttribute('src', '/content/dam/ag-dam/ag-global-dam/parent-site-dam/images/ag-theme-icons/slider-arrow-left-2x.png');" alt='left-arrow'></div</button>`,
            nextArrow: `<button type='button' class='slick-next'><div class='arrow-image-next'><img src='/content/dam/ag-dam/ag-global-dam/parent-site-dam/images/ag-theme-icons/slider-arrow-right-2x.png' onmouseover="this.setAttribute('src', '/content/dam/ag-dam/ag-global-dam/parent-site-dam/images/ag-theme-icons/slider-arrow-right-active-2x.png');" onmouseout="this.setAttribute('src', '/content/dam/ag-dam/ag-global-dam/parent-site-dam/images/ag-theme-icons/slider-arrow-right-2x.png');" alt='right-arrow'></div></button>`,
            responsive: [{
                    breakpoint: 1200,
                    settings: {
                        slidesToShow: slideToDisplay.LG || slideToDisplay.XL,
                        slidesToScroll: slideToDisplay.LG || slideToDisplay.XL,
						dots: dots.LG || dots.XL
                    }
                },
                {
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: slideToDisplay.MD || slideToDisplay.XL,
                        slidesToScroll: slideToDisplay.MD || slideToDisplay.XL,
						dots: dots.MD || dots.XL
                    }
                },
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: slideToDisplay.SM || slideToDisplay.XL,
                        slidesToScroll: slideToDisplay.SM || slideToDisplay.XL,
						dots: dots.SM || dots.XL
                    }
                },
                {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: slideToDisplay.XS || slideToDisplay.XL,
                        slidesToScroll: slideToDisplay.XS || slideToDisplay.XL,
						dots: dots.XS
                    }
                },
            ]
        }
        $(el).addClass("slider-loaded").slick(settings);
    },
    checkTruncateText: function () {
        const $ele = $(".product-carousel-grid .grid-title").not('.active');
        if (!$ele) {
            return;
        }
        _.each($ele, (item, indx) => {
            $(item).addClass('active');
            $(item).closest('div').css("height", "auto");
            setTimeout(()=>{
                truncateInit(item, config.textTruncate[`${self.deviceName}`]);
            },0)
            if(indx == $ele.length-1){
                $(item).closest("li").find("img").imagesLoaded().done((instance, image) => {
                    let listHeight;
                     _.each($(".product-content-block"),(item)=>{
                        listHeight = $(item).prev("li").height() || 'auto';
                        $(item).css("height",listHeight);
                        $(item).find('.s7responsiveContainer img').css("max-height",listHeight);
                        $(item).find(".fixed-content .s7responsiveContainer img").css("height",listHeight);
                    });
                });
            }
        });
        // product-content-block
    },
    renderHtml: function (data, ele) {
        $.extend(data, config.productAttrs[self.brandName](ele))
        let source = $('#productCarouselTemp').html();
        let template = window.AEMHbs.compile(source);
        $(ele).prepend(template(data)).removeClass("data-loading");
        if(ele.data('applyStack') == true) {
            ele.addClass("grid-default").parent().addClass("grid-container");
        } else {
            self.applyCarousel(ele);
        }
        self.checkTruncateText();
    },
    getActivePriceName() {
        const { customerSegment,employeePrice} = window.global.getUserCookie();
        if (employeePrice == true || employeePrice == "true") {
            if (customerSegment) {
                return "employee_loyalty_price";
            }
            return "employee_price";
        } else if (customerSegment == "SILVER" || customerSegment == "GOLD" || customerSegment == "BERRY") {
            return "loyalty_price";
        } else {
            return "sale_price";
        }
    },
    getSAndPData: function (ele) {
        const $ele = $(ele);
        if(!$ele.length) return;
        $ele.addClass("loaded");
        self.priceObjName = self.getActivePriceName();
        request({
                type: 'get',
                accept: 'application/json',
                crossDomain: true,
                url: config.serviceCall[self.brandName]($ele)
            }).then(data => {
                try {
                    const response = typeof data == "string" ? JSON.parse(data) : data;
                    const { resultsets } = response;
                    _.each(resultsets[0].results, (v) => {
                        let imageLink = v.imageLink;
                        if (imageLink != undefined && imageLink != "") {
                            v.scene7Url = imageLink.substring(0, imageLink.lastIndexOf("/") + 1);
                        }
                        checkAnyFeaturedPrice(v, self.priceObjName);
                    });
                    self.renderHtml(resultsets[0], $ele);
                } catch (error) {
                    console.log(error.message);
                }
            })
            .catch(error => {
                $(".product-carousel-grid").removeClass("data-loading");
                console.log(`S&P service failed.please try again..`);
            });
    },
    browserResize: function () {
        let resizedDeviceName;
        $(window).resize(
            _.debounce(() => {
                resizedDeviceName = window.global.deviceName();
                if (self.deviceName != resizedDeviceName) {
                    self.deviceName = resizedDeviceName;
                    $(".product-carousel-grid .grid-title").removeClass('active')
                    self.checkTruncateText();
                }
            }, 500)
        );
    },
    pageLoadAction: function() {
        _.each($(this.ele),(elem)=>{
            this.getSAndPData(elem);
        });
    },
    init: function () {
        this.bindingHelperFn();
        this.pageLoadAction();
        self.browserResize();
    }
};

const ajaxBinding = new ajaxRequest();
const request = ajaxBinding.ajaxCallWithoutErrorHandling;
const handleBarsHelperInst = window.global.handleBarsHelperInst;

const productCarouselInit = new productCarousel();

document.addEventListener('DOMContentLoaded', function () {
    productCarouselInit.pageLoadAction();
}, false);