/*
 * ADOBE CONFIDENTIAL
 *
 * Copyright 2012 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 *
 */
(function(factory) {
    "use strict";

    if (typeof module === "object" && module.exports) {
        module.exports = factory(require("jquery"));
    } else {
        var g = window.Granite = window.Granite || {};
        g.TouchIndicator = factory(window.jQuery);
    }
}(function($) {
    "use strict";

    var CSS = {
        "visibility": "hidden",
        // fixed would be better, but flickers on ipad while scrolling
        "position": "absolute",
        "width": "30px",
        "height": "30px",
        "-webkit-border-radius": "20px",
        "border-radius": "20px",
        "border": "5px solid orange",
        "-webkit-user-select": "none",
        "user-select": "none",
        "opacity": "0.5",
        "z-index": "2000",
        "pointer-events": "none"
    };

    var used = {};

    var unused = [];

    /**
     * Implements the "Adobe Dynamic Touch Indicator" that tracks touch events and displays a visual indicator for
     * screen sharing and presentation purposes.
     *
     * To enable it, call <code>Granite.TouchIndicator.init()</code> e.g. on document ready:
     * <pre><code>
     * Granite.$(document).ready(function() {
     *     Granite.TouchIndicator.init();
     * });
     * </code></pre>
     *
     * AdobePatentID="2631US01"
     */
    return {
        debugWithMouse: false,

        init: function() {
            var self = this;
            var NS = ".touchindicator";

            $(document).on("touchstart" + NS + "touchmove" + NS + "touchend" + NS, function(e) {
                var touches = e.originalEvent.touches;
                self.update(touches);
                return true;
            });

            if (this.debugWithMouse) {
                $(document).on("mousemove" + NS, function(e) {
                    e.identifer = "fake";
                    self.update([ e ]);
                    return true;
                });
            }
        },

        update: function(touches) {
            // go over all touch events present in the array
            var retained = {};
            for (var i = 0; i < touches.length; i++) {
                var touch = touches[i];
                var id = touch.identifier;

                // check if we already have a indicator with the correct id
                var indicator = used[id];
                if (!indicator) {
                    // if not, check if we have an unused one
                    indicator = unused.pop();

                    // if not, create a new one and append it to the dom
                    if (!indicator) {
                        indicator = $(document.createElement("div")).css(CSS);
                        $("body").append(indicator);
                    }
                }

                retained[id] = indicator;
                indicator.offset({
                    left: touch.pageX - 20,
                    top: touch.pageY - 20
                });
                indicator.css("visibility", "visible");
            }

            // now hide all unused ones and stuff them in the unused array
            for (id in used) {
                if (used.hasOwnProperty(id) && !retained[id]) {
                    indicator = used[id];
                    indicator.css("visibility", "hidden");
                    unused.push(indicator);
                }
            }
            used = retained;
        }
    };
}));
