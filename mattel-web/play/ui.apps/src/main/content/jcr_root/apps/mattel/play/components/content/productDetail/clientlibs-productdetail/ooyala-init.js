/**
 * ooyala-init.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    var brandPlayerIds = document.getElementById("oo-player-id") != null ? document.getElementById("oo-player-id").children[0].value : false,
        pluginLoaded = false,
        pluginCheckCnt=0,
        pCodeVal = document.getElementById("oo-player-pcode") != null ? document.getElementById("oo-player-pcode").value : false,
        playerV4 = "https://player.ooyala.com/static/v4/stable/",
        ooyalaVersion = document.getElementById("oo-player-version") != null ? document.getElementById("oo-player-version").value : '4.32.8',
        // ooyalaVersion = '4.29.14',
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
        startOoyalaPlayer: function (divId, externalId) {
            if (!pluginLoaded) {
                console.log("ooyala plugins not loaded..");
                return;
            }
            if(!pCodeVal){
                console.log("PCode Value not exist..");
                return;
            }
            if(!brandPlayerIds){
                console.log("PlayerIds Value not exist..");
                return;
            }
            var contentId = (externalId.length >= 32 || externalId.indexOf('extId:') != -1) ? externalId : "extId:" + externalId,
                player = OO.Player.create(divId, contentId, {
                    'pcode': pCodeVal,
                    "playerBrandingId": brandPlayerIds,
                    'autoplay': false,
                    'width': '100%',
                    'height': '100%',
                    'wmode': 'transparent',
                    "skin": {
                        // Config contains the configuration setting for player skin. Change to your local config when necessary.
                        "config": playerV4 + ooyalaVersion + "/skin-plugin/skin.json",
                        //Put your player customizations here to override settings in skin.json. The JSON object must match the structure of skin.json
                        "inline": {
                            "startScreen": {
                                "showDescription": false,
                                "showTitle": false,
                                "playIconStyle": {
                                    "color": "white",
                                    "opacity": 1
                                }
                            },
                            "shareScreen": false,
                            "pauseScreen": {
                                "showTitle": false,
                                "showDescription": false
                            }
                        }
                    }
                });
            return player;
        }
    };
    self = ooyalaPlayer;
    self.OOReady();
    PLAYAEM.ooyalaPlayer = ooyalaPlayer;
}(window, PLAYAEM));