/**
 * video-gallery-player.js for Single pLayer
 * Version 1.0
 */
(function(global, PLAYAEM, ooyala) {

    // 'use strict';
    var imageTextvideoplayer = {
        el: '[id="image-text-video-gallery-player-component"]',
        /*gridEl: '#player-gallery-thumbnail-datas',
        bindingEventsConfig: function() {
            var events = {
                "click .play-list li": "thumbnailAction",
                "click #play-list-datas li": "thumbnailAction",
                "click .video-player-container .slide-btn": "videoSlide"
            };
            return events;
        },*/
       
        
        createSinglePlayer: function(playerId) {
            var ele = $("#" + playerId)[0],
                dataAttr = ele.dataset,
                indx = dataAttr.elemIndex || 0,
                videoId = dataAttr.videoId,
                isAutoplay = dataAttr.autoplay || false,
                trackingVal;
            if (videoId == undefined) {
                console.log("video attribute not found. Element Id is: " + playerId);
                return;
            }
            ooyala.waitForElement(function(pluginLoaded) {
                if (pluginLoaded) {
                    self.singlePlayer[indx] = ooyala.playerCreate(playerId, videoId);
                    self.singlePlayer[indx].mb.subscribe(OO.EVENTS.PLAYING, 'playing', function(event) {
                        trackingVal = "Video Text Section|About Alpha Training Blue|Play|Video Player";
                        PLAYAEM.getTrackingValues('', '', trackingVal);
                        $('.oo-play-pause').attr('aria-label', 'Pause');
                    });
                    self.singlePlayer[indx].mb.subscribe(OO.EVENTS.PAUSED, 'paused', function(event) {
                        trackingVal = "Video Text Section|About Alpha Training Blue|Pause|Video Player";
                        PLAYAEM.getTrackingValues('', '', trackingVal);
                        $('.oo-play-pause').attr('aria-label', 'Play');
                    });
                }
                if (!PLAYAEM.isTouchDevicePlay() && isAutoplay && !self.singlePlayer[indx].isPlaying() && self.isScrolledIntoView(ele) > 0) {
                    setTimeout(function() {
                        self.singlePlayer[indx].play();
                    }, 10);
                }
            });
        },
        
        initSinglePlayer: function() {
            var $targetElem = $(".single-video-player");
            if (!$targetElem.length) return;
            var elemId;
            self.singlePlayer = self.singlePlayer || [];
            _.each($targetElem, function(item, indx) {
                elemId = 'single-player-' + indx;
                $(item).attr({
                    'data-elem-index': indx,
                    'id': elemId
                });
                self.createSinglePlayer(elemId);
            });
        },
        
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.imageTextvideoplayer) return;
            self = this;
            self.featuredPlayers = [];
           /* PLAYAEM.bindLooping(this.bindingEventsConfig(), this); */
            self.initSinglePlayer();
        }
    };

    imageTextvideoplayer.init();
    PLAYAEM.imageTextvideoplayer = imageTextvideoplayer


    
}(window, PLAYAEM, function() {

    var pluginLoaded = false,
        pluginCheckCnt = 0;
    var ooyalaPlayer = {
        isManuallyPaused: [],
        ooVideoSetters: function() {
            if (typeof OO === "undefined") return;
            OO.ready(function() {
                //player ready
                pluginLoaded = true;
            });
        },
        playerEmbedCode: function(_player, _contentid) {
            _player.setEmbedCode(_contentid);
            !isIOSDevice && _player.play();
        },
        waitForElement: function(callBack) {
            var self = this;
            window.setTimeout(function() {
                if (pluginLoaded) {
                    callBack(true);
                } else {
                    pluginCheckCnt++;
                    if (pluginCheckCnt > 8) {
                        console.log("Ooyala Plugin not available here.. ");
                        return;
                    }
                    self.waitForElement(callBack);
                }
            }, 500)
        },
        playerCreate: function(elementId, externalId) {
            var elemId = elementId || "main-player-container",
                contentId = (externalId.length >= 32 || externalId.indexOf('extId:') != -1) ? externalId : "extId:" + externalId,
                player = OO.Player.create(elemId, contentId, PLAYAEM.ooParams);
            return player;
        },
        startOoyalaPlayer: function(externalId, elemId) {
            var self = this;
            var $videoElem,
                videoIndex;
            if (!pluginLoaded) {
                console.log("ooyala plugins not loaded..");
                return;
            }
            var contentId = (externalId.length >= 32 || externalId.indexOf('extId:') != -1) ? externalId : "extId:" + externalId;
            var player = self.playerCreate(elemId, contentId);

            player.mb.subscribe(OO.EVENTS.PLAYING, 'playing', function(event) {
                var trackingVal = "video Gallery section|" + this.mb._interceptArgs.contentTreeFetched[0].title + "|play|video Player";
                PLAYAEM.getTrackingValues('', '', trackingVal);
                $('.oo-play-pause').attr('aria-label', 'Pause');
            });
            player.mb.subscribe(OO.EVENTS.PAUSED, 'paused', function(evt, player, id) {
                var trackingVal = "video Gallery section|" + this.mb._interceptArgs.contentTreeFetched[0].title + "|pause|video Player";
                PLAYAEM.getTrackingValues('', '', trackingVal);
                $('.oo-play-pause').attr('aria-label', 'Play');
                $videoElem = pageList.find('.video-player-container .featured-promo[data-video-id=' + this.mb._interceptArgs.setEmbedCode[0] + ']');
                videoIndex = $videoElem.length && $videoElem.data('videoIndex');
                self.isManuallyPaused[videoIndex] = true;
            });
            player.mb.subscribe(OO.EVENTS.PLAYED, 'completed', function(event) {
                PLAYAEM.imageTextvideoplayer.enableAutoplay();
                PLAYAEM.verticalListHeight();

            });

            return player;

        }

    };

    ooyalaPlayer.ooVideoSetters();

    return ooyalaPlayer;

}()));
