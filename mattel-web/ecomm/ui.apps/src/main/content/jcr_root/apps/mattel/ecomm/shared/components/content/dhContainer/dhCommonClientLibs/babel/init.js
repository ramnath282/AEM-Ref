import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import {
    handleBarTemplate
} from '../shared/templateSetter';
/*
Syntax:
------
    fn.bindLooping({
        "click [data-tracking-id]": "getTrackingValues"
    });
*/
export class eventBinding {
    bindLooping(name, evtObj) {
        let eventSplitter = /(\S+)\s(.*)/, // Regular expression used to split event strings.
            names = Object.keys(name),
            cb,
            splitKeys;

        for (let _i = 0, _names = names; _i < _names.length; _i++) {
            let val = _names[_i];
            splitKeys = val.match(eventSplitter).slice(1);
            cb = evtObj && evtObj[name[val]];
            if (splitKeys && typeof cb == "function") {
                this.bindingEvent(splitKeys[0], splitKeys[1], cb);
            } else {
                console.log("Event Binding failed for " + splitKeys);
            }
        }
    };
    bindingEvent(evtName = click, elem = null, cb = false) {
        if (typeof cb === 'function') {
            $(document).on(evtName, elem, function(evt) {
                cb(this, evt);
            });
        } else {
            console.log("Error:CB function not found for this element :" + el);
        }
    }
}

export class popover {
    constructor() {
        document.addEventListener("DOMContentLoaded", function() {
            let img,
                imgSrc;
            _.each($('[data-toggle="popover"]'), (item) => {
                imgSrc = $(item).data('img');
                if (imgSrc) {
                    img = new Image();
                    img.src = imgSrc;
                    img.className = "img-fluid";
                }
                $(item).popover({
                    html: true,
                    trigger: window.innerWidth <= 766 ? 'click' : 'focus, hover',
                    content: function() {
                        return img;
                    },
                    placement: 'auto' + (window.innerWidth <= 766 ? $(item).data('positionSm') : $(item).data('positionLg')),
                })

            });
        });
        $(document).on('click', function(e) {
            $('[data-toggle="popover"]:visible').each(function() {
                //the 'is' for buttons that trigger popups
                //the 'has' for icons within a button that triggers a popup
                if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                    (($(this).popover('hide').data('bs.popover') || {}).inState || {}).click = false // fix for BS 3.3.6
                }

            });
        });
    }
    loadPopOver() {

    }
}

export class inputValidations {
    constructor() {}
    letterCapitalize(str) {
        return str[0] ? str[0].toUpperCase() + str.substring(1) : '';
    }
}
if ("ontouchstart" in window || window.DocumentTouch && document instanceof DocumentTouch) {
    $('html').addClass('touch-device');
}

window.dh = window.dh || {};
window.dh = {
    eventBinding: window.dh.eventBinding || new eventBinding(),
    ajaxRequest: window.dh.ajaxRequest || new ajaxRequest(),
    popover: window.dh.popover || new popover(),
    inputValidations: window.dh.inputValidations || new inputValidations(),
    apiConfig: window.dh.apiConfig || new apiConfig().getApiConfig,
    handleBarTemplateInst : window.dh.handleBarTemplateInst || new handleBarTemplate()
}