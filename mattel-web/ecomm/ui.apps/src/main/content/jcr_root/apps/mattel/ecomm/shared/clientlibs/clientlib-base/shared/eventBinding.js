/*
Syntax:
------
    fn.bindLooping({
        "click [data-tracking-id]": "getTrackingValues"
    });
*/
export default class eventBinding {
    constructor(){}
    bindLooping(name, evtObj) {
        let eventSplitter = /(\S+)\s(.*)/, // Regular expression used to split event strings.
            cb,
            splitKeys;

        for (let val in name) {
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
            $(document).on(evtName, elem, function (evt) {
                cb(this, evt);
            });
        } else {
            console.log("Error:CB function not found for this element :" + el);
        }
    }
}