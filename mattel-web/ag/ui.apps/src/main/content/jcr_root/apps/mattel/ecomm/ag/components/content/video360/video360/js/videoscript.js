(function (global) {
    var videoIndex = 0,
        deluxeCheckCnt = 0,
        players = [],
        vrModal = {
            el: '.video-360-modal',
            deluxeVideoON: function deluxeVideoON(curEle) {
                var $videoEle = curEle.find(".aem-video-player");

                if (!$videoEle.attr('id')) {
                    videoIndex++;
                    $videoEle.attr({
                        'id': "video-modal-container-".concat(videoIndex),
                        'data-video-index': videoIndex
                    });
                }

                var divId = $videoEle.attr('id');
                if ($("#sub-".concat(divId)).length) return;
                this.waitForDeluxeJS(function (callBack) {
                    callBack && window.global.createDeluxePlayer && window.global.createDeluxePlayer(divId, $videoEle.data('videoId'), function (player) {
                        if ($("#sub-".concat(divId)).length) {
                            $("#sub-".concat(divId)).css({
                                "width": '100%',
                                "padding-top": '56.25%',
                                "height": "auto"
                            });
                        }

                        players[videoIndex] = player;
                        player.play();
                    });
                });
            },
            deluxeVideoOFF: function deluxeVideoOFF(curEle) {
                var playerIndex = curEle.find(".aem-video-player").data("videoIndex");

                if (players[playerIndex] && !players[playerIndex].paused()) {
                    players[playerIndex].pause();
                }
            },
            scene7VideoON: function scene7VideoON(curEle) {
                var $iframe = curEle.find('.vr-iframe');
                $iframe.attr('src', $iframe.data('src'));
            },
            scene7VideoOFF: function scene7VideoOFF(curEle) {
                curEle.find('iframe').attr('src', '');
            },
            waitForDeluxeJS: function waitForDeluxeJS(callBack) {
                var self = this;

                if (typeof videojs == "function") {
                    callBack(true);
                } else {
                    window.setTimeout(function () {
                        deluxeCheckCnt++;

                        if (deluxeCheckCnt > 12) {
                            callBack(false);
                            console.log("%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.", "background: red; color:white");
                            return;
                        }

                        self.waitForDeluxeJS(callBack);
                    }, 500);
                }
            },
            bindModalEvents: function bindModalEvents() {
                var $targetEle;
                var videoType;
                var self = this;
                $(this.el).on('shown.bs.modal', function (evt) {
                    $targetEle = $(evt.relatedTarget).closest(".video360").find(".video-360-modal");
                    videoType = $targetEle.data('videoType') || "iframe";

                    if (videoType == "iframe") {
                        self.scene7VideoON($targetEle);
                    } else if (videoType == "deluxe") {
                        self.deluxeVideoON($targetEle);
                    }

                    $('body').toggleClass('vr-modal-open');
                }).on('hidden.bs.modal', function (evt) {
                    $targetEle = $(evt.currentTarget);
                    videoType = $targetEle.data('videoType') || "iframe";

                    if (videoType == "iframe") {
                        self.scene7VideoOFF($targetEle);
                    } else if (videoType == "deluxe") {
                        self.deluxeVideoOFF($targetEle);
                    }

                    $('body').toggleClass('vr-modal-open');
                });
            },
            initializeModal: function initializeModal() {
                var $ele = $(this.el).not(".modal-initiated");
                if (!$ele.length) return;
                var videoType = $ele.data('videoType') || "iframe";

                if (videoType == "iframe") {
                    $ele.find('iframe').attr('src', '');
                } else if (videoType == "deluxe") {
                    var initDeluxePlayer = window.global.initDeluxePlayer;

                    if (!initDeluxePlayer) {
                        console.log("%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.", "background: red; color:white");
                        return;
                    }

                    initDeluxePlayer(undefined, true);
                }
            },
            init: function init() {
                if (!$(this.el).length) return;
                this.initializeModal();
                this.bindModalEvents();
                $(this.el).addClass("modal-initiated");
            }
        };
    vrModal.init();
    document.addEventListener('DOMContentLoaded', function () {
        vrModal.initializeModal();
    }, false);
})(window);