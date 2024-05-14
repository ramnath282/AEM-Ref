import { dateFormat } from './dateFormat';
export class handleBarsHelper {
    constructor() {
        self = this;
        Handlebars.registerHelper('print', function(something) {
            console.log(something);
        });
        Handlebars.registerHelper('if_equal', function(a, b, opts) {
            if (a == b) {
                return opts.fn(this)
            } else {
                return opts.inverse(this)
            }
        });

        Handlebars.registerHelper('ifEqualsPrice', function(arg1, arg2, options) {
            return parseFloat(arg1) == parseFloat(arg2) ? options.fn(this) : options.inverse(this);
        });
    }
    getArticleSocialShare(selector) {
        self.socialElem = $("#socialIconUl").html() || '';
        if (!self.socialElem) {
            $("body").append(`<ul id="socialIconUl" class="social-icons hide"><li><a class="facebook track-social-share" data-tracking-social="facebook" data-link-attr="Facebook" data-componentname="ArticleGrid" aria-label="facebook opens a new window" data-toggle="modal" data-target="#exitPageModal" href="https://www.facebook.com/americangirl#fb" data-url="/content/ag/en/shop/search-results.html" data-title="Social" data-description="SocialMedia"></a></li><li><a class="twitter track-social-share" data-tracking-social="twitter" data-link-attr="Twitter" data-componentname="ArticleGrid" aria-label="twitter opens a new window" data-toggle="modal" data-target="#exitPageModal" href="https://twitter.com/american_girl" data-url="/content/ag/en/shop/search-results.html" data-title="Social" data-description="SocialMedia"></a></li><li><a class="pinterest track-social-share" data-tracking-social="pinterest" data-link-attr="Pinterest" data-componentname="ArticleGrid" aria-label="pinterest opens a new window" data-toggle="modal" data-target="#exitPageModal" href="https://www.pinterest.com/agofficial" data-url="/content/ag/en/shop/search-results.html" data-title="Social" data-description="SocialMedia"> </a></li></ul>`);
            self.socialElem = $("#socialIconUl").html();
        }
        Handlebars.registerHelper('socialElements', function(value, options) {
            return self.socialElem;
        });
        Handlebars.registerHelper('relatedSocialElements', function(value, options) {
            $("#socialIconUl li a").attr({
                "data-url": value.url || "",
                "data-title": value.title || "",
                "data-description": value.desc || "",
                "data-media": value.imageLink || ""
            });
            return $("#socialIconUl").html() || '';
        })
    }
    checkIFConditions(selector) {
        Handlebars.registerHelper(selector, (arg1, arg2, options) => {
            if (selector == 'ifEquals') {
                return arg1 == arg2 ? options.fn(this) : options.inverse(this);
            } else if (selector == 'ifNotEquals') {
                return arg1 != arg2 ? options.fn(this) : options.inverse(this);
            } else if (selector == 'checkIndexExist') {
                return arg1.indexOf(arg2) != -1 ? options.fn(this) : false;
            } else if (selector == 'dynamicKeyVal') {
                if (arg2 != '' && arg1 != '') {
                    return typeof arg1[arg2] == "object" ? arg1[arg2][0] : arg1[arg2];
                } else {
                    return 0;
                }
            } else if (selector == 'greaterThan') {
                return parseInt(arg1) > parseInt(arg2) ? options.fn(this) : options.inverse(this);
            } else if (selector == 'lessThan') {
                return parseInt(arg1) < parseInt(arg2) ? options.fn(this) : options.inverse(this);
            } else if (selector == 'parseFloat') {
                if (arg2 == '' && arg1 != '') {
                    return parseFloat(arg1 || 0).toFixed(2);
                } else if (arg2 != '' && arg1 != '') {
                    return parseFloat(arg1[arg2] || 0).toFixed(2);
                } else {
                    return parseFloat(0).toFixed(2);
                }
            } else if (selector == 'isLessThanMonths') {
                if (arg1 != '' && changeDateFormat.comparePastMonths(arg1, arg2 || 6)) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}
			}
			else if (selector == 'each_upto') {
				if (!value || value.length == 0)
					return options.inverse(this);
				var result = [];
				for (var i = 0; i < value && i < ary.length; ++i)
					result.push(options.fn(ary[i]));
				return result.join('');
			}
			else if (selector == 'ifShareCatalog') {
                return (arg1 == arg2) ? options.fn(this) : options.inverse(this);     
			}  else if (selector == "finalActualPrice"){
				if (arg2 != '' && arg1 != '') {
					return  parseInt(arg1) > parseInt(arg2) ? "$"+parseFloat(arg1).toFixed(2) : null;
				} 
				return null;
			}
		});
	}
    callRegisterHelper(selector, eachData, obj) {
        Handlebars.registerHelper(selector, (value, options) => {
            if (selector == 'setChecked') {
                let $el = $(options.fn(this));
                if (value == $el.val()) {
                    return $el.attr('checked', 'checked')[0].outerHTML;
                } else {
                    return options.fn(this);
                }
            } else if (selector == 'radioCheck') {
                if (value == options) {
                    return 'checked';
                } else {
                    return '';
                }
            } else if (selector == 'addressSelector') {
                let html = [];
                if (eachData.length > 0) {
                    $.each(eachData, (key, value) => {
                        if (obj.addressLine && value.addressLine[0] == obj.addressLine[0]) {
                            html.push('<option value="' + value.addressId + '" selected>' + value.addressLine[0] + '</option>');
                        } else {
                            html.push('<option value="' + value.addressId + '">' + value.addressLine[0] + '</option>');
                        }
                    });
                } else {
                    html.push('<option value="">No Addresses to select</option>');
                }
                return html.join("");
            } else if (selector == 'countrySelector' || selector == 'stateSelector') {
                let html = '';
                $.each(eachData, (key, value) => {
                    if (value.code == obj) {
                        html = html + '<option value="' + value.code + '" selected>' + value.displayName + '</option>';
                    } else {
                        html = html + '<option value="' + value.code + '" >' + value.displayName + '</option>';
                    }
                });
                return html;
            } else if (selector == 'changeToHyphen') {
                if (typeof value == 'string' && value != '') {
                    return value.replace('.', '-');
                } else {
                    return 0;
                }
            } else if (selector == 'getSelectedTitle') {
                if (typeof value == 'object') {
                    let getLabel = '';
                    _.filter(value, function(item) {
                        if (item.selected) {
                            getLabel = item.label;
                        }
                    });
                    return getLabel;
                } else {
                    return 0;
                }
            } else if (selector == 'roundedValue') {
                if (value != '') {
                    return Math.ceil(value);
                } else {
                    return 0;
                }
            } else if (selector == 'getMMDD') {
                if (value != '') {
                    return changeDateFormat.getOnlyMMDDD(value);
                } else {
                    return 0;
                }
			}
			else if (selector == 'getMMDDYYYY') {
                if (value != '') {
                    return changeDateFormat.formatToNewDate(new Date(value));
                } else {
                    return 0;
                }
            } else if (selector == 'forLoop') {
                let accum = '';
                for (let i = 0; i < value; ++i) accum += options.fn(i);
                return accum;
            } else if (selector == 'getDeluxePoster') {
                if (value != '') {
                    let idInString = value.toString();
                    return `https://video.mattel.com/assets/images/${value}/${(idInString.length >= 32 || idInString.startsWith("6")) ? "poster" : value}.jpg`;
                } else {
                    return 0;
                }
            } else {
                let html = '';
                $.each(eachData, (key, value) => {
                    value = value < 10 ? '0' + value : value;
                    if (value == obj) {
                        html = html + '<option value="' + value + '" selected>' + value + '</option>';
                    } else {
                        html = html + '<option value="' + value + '" >' + value + '</option>';
                    }
                });
                return html;
            }
        });
    }
}
let self;
const changeDateFormat = new dateFormat();
