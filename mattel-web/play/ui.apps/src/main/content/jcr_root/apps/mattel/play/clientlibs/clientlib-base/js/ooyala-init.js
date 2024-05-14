/**
 * ooyala-init.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    var pluginLoaded = false,
        pluginCheckCnt=0,
        self;
    var ooyalaPlayer = {
        OOReady: function () {
            if (typeof OO === "undefined") return;
            OO.ready(function () {
                //player ready
                pluginLoaded = true;
            });
        },
        init: function (divId,cb) {
            var videoId= $('#'+divId).data('videoId'),
                player;
            if(!videoId){
                console.log("data video id attribute not found/empty..");
                return;
            }
            self.waitForElement(function () {
                player = self.startOoyalaPlayer(divId, videoId);
                typeof cb=="function" && cb(player);
            });
        },
        update: function (player, videoId) {
            player.setEmbedCode(videoId);
            setTimeout(function () { player.play() }, 10);
        },
        waitForElement: function (callBack) {
            var self = this;
            window.setTimeout(function () {
                if (pluginLoaded) {
                    callBack(true);
                } else {
                    pluginCheckCnt++;
                    if(pluginCheckCnt>8){
                        console.log("Ooyala Plugin not available here.. ");
                        return;
                    }
                    console.log("Ooyala Plugin Loading..Check Count "+pluginCheckCnt);
                    self.waitForElement(callBack);
                }
            }, 500)
        },
        isScrolledIntoView: function (elem) {
            var x = elem.offsetLeft,
                y = elem.offsetTop,
                w = elem.offsetWidth,
                h = elem.offsetHeight,
                r = x + w, //right
                b = y + h, //bottom
                visibleX, visibleY;

            visibleX = Math.max(0, Math.min(w, window.pageXOffset + window.innerWidth - x, r - window.pageXOffset));
            visibleY = Math.max(0, Math.min(h, window.pageYOffset + window.innerHeight - y, b - window.pageYOffset));

            return visibleX * visibleY / (w * h);
        },
        startOoyalaPlayer: function (divId, externalId) {
            if (!pluginLoaded) {
                console.log("ooyala plugins not loaded..");
                return;
            }
            var contentId = (externalId.length >= 32 || externalId.indexOf('extId:') != -1) ? externalId : "extId:" + externalId,
                player = OO.Player.create(divId, contentId, PLAYAEM.ooParams);
            return player;
        }
    };
    self = ooyalaPlayer;
    self.OOReady();
    PLAYAEM.ooyalaPlayer = ooyalaPlayer;
}(window, PLAYAEM));