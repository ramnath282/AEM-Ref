import {
    deluxeInit,
    configuration,
    createPlayer,
    updatePlayer
} from '../../mattelVideoPlayerConfig/shared/videoPlayer_Config';

window.MattelVideoPlayer = {};
MattelVideoPlayer.ooPlayerInstances = {};
MattelVideoPlayer.ooyalaApiLoaded = false;

(() => {
    MattelVideoPlayer.playerScenarios = {
        thumbnailNreload: false,
        thumbnailNonreload: false,
        singlePlayer: false,
        multiplayer: false
    };
    let haveVideoToLoad = false;
    let videoContainers = [];
    let generatedExtId = "";
    let completedVideo = false;
    let wrapperJSInitialized = false;

    // creating and binding ooyala plugins like css,js
    function renderOoyalaApi() {
        videoContainers = document.getElementsByClassName("ooyala-video-player");
        MattelVideoPlayer.ooPlayerInstances.players = [];
        // $('head').append('<link rel="stylesheet" type="text/css" href="//static.mattel.com/videoplayer/src/js/v4/ooyala-overridden.css">');
    }

    /**
     For each player in the page, setting the scenario based on the following player Tag Element ID
     @divId: (passed as parameter)player Tag Element ID
     @Functionality: Detects and sets the player scenario for further player playback.
    **/
    function scenarioDetector(divId, indx) {
        MattelVideoPlayer.playerScenarios = {
            thumbnailNreload: ($(`#${divId}`).attr('data-contains-thumbnailNreload') != null) ? $(`#${divId}`).attr('data-contains-thumbnailNreload') : undefined,
            thumbnailNonreload: ($(`#${divId}`).attr('data-contains-thumbnailNonreload') != null) ? $(`#${divId}`).attr('data-contains-thumbnailNonreload') : undefined,
            singlePlayer: ($(`#${divId}`).attr('data-singlePlayer') != null) ? $(`#${divId}`).attr('data-singlePlayer') : undefined,
            multiplayer: ($(`#${divId}`).attr('data-multiplePlayer') != null) ? $(`#${divId}`).attr('data-multiplePlayer') : undefined,
            customPlayer: ($(`#${divId}`).attr('data-customPlayer') != null) ? $(`#${divId}`).attr('data-customPlayer') : undefined,
            slidingPlayerOnPage: ($('[data-slidertype ="flexSlider"]').length) ? $('[data-slidertype ="flexSlider"]') : (($('[data-slidertype ="bxSlider"]').length) ? $('[data-slidertype ="bxSlider"]') : undefined)

        }
        scenarioBasedConfig(divId, indx);
        eventBinders();
    }

    // Based on the detected scenario, do some config on detected scenarios
    function scenarioBasedConfig(divId, indx) {
        if (MattelVideoPlayer.playerScenarios.thumbnailNonreload) {
            /**
            This block prevents the default behavior of anchor tag, as the scenario set as THUMBNAIL-NON-RELOAD 
            (i.e., loads the video without reloading the page, A fallback to prevent default behavior)
            **/
            MattelVideoPlayer.startVideoPlayer(divId, $(`#${divId}`).data('videoId'), indx);
            const listEl = $('.thumbnail-video-list .video-list-item a');
            if (listEl != null) {
                // binding click event based on the jquery version
                if (typeof $.fn.live == "function") {
                    listEl.live('click', e => {
                        /** ---for jquery < 1.7 --**/
                        e.preventDefault();
                    })
                } else if (typeof $.fn.on == "function") {
                    /** ---for jquery >= 1.7 --**/
                    listEl.on('click', e => {
                        e.preventDefault();
                    })
                }
            }
        } else if (MattelVideoPlayer.playerScenarios.thumbnailNreload) {
            MattelVideoPlayer.startVideoPlayer(divId, $(`#${divId}`).data('videoId'), indx);
        } else if (MattelVideoPlayer.playerScenarios.customPlayer) {
            MattelVideoPlayer.startVideoPlayer(divId, $(`#${divId}`).data('videoId'), indx, true);
        } else {
            (haveVideoToLoad) ? MattelVideoPlayer.startVideoPlayer(divId, generatedExtId, indx): MattelVideoPlayer.startVideoPlayer(divId, $(`#${divId}`).data('videoId'), indx);
        }
    }

    // slider Overlay for navigations 
    MattelVideoPlayer.playerNavigationControl = currentSlider => {
        $(MattelVideoPlayer.ooPlayerInstances.players).each((index, item) => {
            item.pause();
        });
        if (typeof(currentSlider) == "undefined") return false;
        MattelVideoPlayer.ooPlayerInstances.players[currentSlider].play();
    }

    // Pre-configures and generates the unique ooyala player ID for each player in the page
    function ooVideoSetters() {
        if ($('[ooyala-video-slider]').length > 0) {
            MattelVideoPlayer.ooPlayerInstances = {};
            MattelVideoPlayer.ooPlayerInstances.players = []
            playerLoading();
        } else if ($('[ooyala-Overlay]').hasClass('activeOverlay')) {
            playerLoading();
        } else if (!$('div[ooyala-Overlay]').length) {
            playerLoading();
        }
    }

    // Generates and sets the unique id attribute for player containers
    function playerLoading() {

        for (let i = 0; i < videoContainers.length; i++) {
            videoContainers[i].setAttribute('id', `video-container-${i + 1}`);
            if (!haveVideoToLoad) {
                scenarioDetector(videoContainers[i].getAttribute('id'), i);
            } else {
                scenarioDetector(videoContainers[i].getAttribute('id'), i);
                break;
            }
        }
    }

    // Binds the events for the thumbnails if available in the page
    function eventBinders() {

        /** This block helps in handling events for "thumbnailNonreload" scenarios 
            (i.e., clicking on thumbnail, loads appropriate video in the player)
        **/
        if (typeof $.fn.live == "function") {
            if (MattelVideoPlayer.playerScenarios.thumbnailNonreload) {
                $('.thumbnail-video-list .video-list-item').live('click', function(ev) {
                    playerBinding(this);
                    $('.video-list-item').removeClass('oo-thumbnail-active');
                    $(ev.currentTarget).addClass('oo-thumbnail-active');
                })
            }
            $('.replay[op-control-replay]').live('click', ev => {
                MattelVideoPlayer.ooPlayerInstances.players[0].play();
            });
        } else if (typeof $.fn.on == "function") {
            /** ---for jquery >= 1.7 --**/
            if (MattelVideoPlayer.playerScenarios.thumbnailNonreload) {
                $(document).on('click', '.thumbnail-video-list .video-list-item,.video-gallery-slides .video-gallery-slide', function(ev) {
                    playerBinding(this);
                    $('.video-list-item').removeClass('oo-thumbnail-active');
                    $(ev.currentTarget).addClass('oo-thumbnail-active');
                })
            }
            $(document).on('click', '.replay[op-control-replay]', ev => {
                MattelVideoPlayer.ooPlayerInstances.players[0].play();
            });
        }
    }

    // Binds the thumbnail events for the thumbnails non-relaoding page 
    function playerBinding(curEle) {
        const externalId = $(curEle).data('videoId'),
            contentId = externalId,
            playerClass = document.getElementsByClassName("ooyala-video-player");
        let playerIndex;

        if (MattelVideoPlayer.ooPlayerInstances.players.length == 1) {
            MattelVideoPlayer.playerEmbedCode(MattelVideoPlayer.ooPlayerInstances.players[0], contentId);
            $('[data-contains-thumbnailnonreload=true]').data("videoId", contentId);
            return false;
        }

        for (let j = 0; j < playerClass.length; j++) {
            for (let i = 0; i < curEle.parentElement.children.length; i++) {
                if (playerClass[j].getAttribute('data-video-id') == curEle.parentElement.children[i].getAttribute('data-video-id')) {
                    const splittedVal = playerClass[j].getAttribute('id').split("-");
                    playerIndex = (playerClass[j].getAttribute('id').split("-")[splittedVal.length - 1]) - 1;
                    MattelVideoPlayer.playerEmbedCode(MattelVideoPlayer.ooPlayerInstances.players[playerIndex], contentId);
                    break;
                }
            }
        }
    }

    function html5PlayerCorrections(player, index) {
        const hostName = location.hostname;
        if ((hostName.indexOf("kids.barbie.com") != -1 || hostName.indexOf("play.barbie.com") != -1) && !MattelVideoPlayer.ooPlayerInstances.players[index].binding) {
            MattelVideoPlayer.ooPlayerInstances.players[index].catalog = {
                getVideo: function(videoId, cb) {
                    cb(undefined, videoId.toString().replace("ref:", ""));
                },
                load: function(videoId) {
                    MattelVideoPlayer.playerEmbedCode(MattelVideoPlayer.ooPlayerInstances.players[index], videoId);
                }
            };
            MattelVideoPlayer.ooPlayerInstances.players[index].binding = true;
        }
    }

    MattelVideoPlayer.playerEmbedCode = (_player, _contentid) => {
        updatePlayer(_player, _contentid);
    };

    MattelVideoPlayer.isPlaying = (video) => {
        if (!video) return false;
        return !!(video.currentTime() > 0 && !video.paused() && !video.ended() && video.readyState() > 2);
    };

    // Instantiates ooyala Player
    MattelVideoPlayer.startVideoPlayer = (divId, externalId, playerIndex, isCustomPlayer, cb) => {
        let playingVideo = false,
            previousEvent = '',
            playerLen = MattelVideoPlayer.ooPlayerInstances.players.length;
        if (playerIndex == undefined) {
            playerIndex = playerLen + 1;
        }
        return createPlayer(divId, externalId, (player) => {
            $("#sub-" + divId).css({
                "width": '100%',
                "padding-top": '56.25%',
                "height": 'auto'
            });
            updatePlayerStyle("#sub-" + divId);
            //console.log(player);
            /** tracking code for player ready/play **/
            player.on("playing", event => {
                if (previousEvent != event) {
                    if (!playingVideo) {
                        playingVideo = true;
                        completedVideo = true;
                    } else {
                        // OOEventTracking('Play',MattelVideoPlayer.ooPlayerInstances.players[0].getCurrentItemTitle());
                    }
                }
                previousEvent = event;
            });

            /** tracking code for player paused **/
            player.on("pause", event => {
                if (playingVideo && previousEvent != event && previousEvent != 'played') {
                    previousEvent = event;
                }

            });

            /** tracking code for player end/completed **/
            player.on("ended", event => {
                if (MattelVideoPlayer.playerScenarios.thumbnailNonreload && previousEvent != event && completedVideo) {
                    completedVideo = false;
                    MattelVideoPlayer.ooPlayerInstances.players[0].pause();
                    /** --- calling the function for autoplaying next video --- **/
                    const curId = $('[data-contains-thumbnailnonreload=true]').data('video-id');
                    if (!curId) {
                        return;
                    }
                    const curEle = $(`.thumbnail-video-list .video-list-item[data-video-id="${(curId.indexOf('extId') != -1) ? curId.split(':')[1] : curId}"]`);
                    if (($(curEle).next() && $(curEle).length) > 0) {
                        $(curEle).next().click();
                    } else {
                        $('.thumbnail-video-list .video-list-item:first-child').click();
                    }
                }
                playingVideo = false;
                previousEvent = event;
            });
            if (!playerIndex) {
                playerIndex = 0;
            }
            MattelVideoPlayer.ooPlayerInstances.players[playerIndex] = player;
            player.html5Player && html5PlayerCorrections(player, playerIndex);
            typeof cb == "function" && cb(player);
        }, isCustomPlayer);
    }
    const updatePlayerStyle = divId => {
        let brandName = getBrandName();
        if (brandName === "allaboardforglobalgoals") {
            $(divId).css('z-index', '1');
        }
    };
    // Destroy the ooyala player 
    MattelVideoPlayer.destroyVideos = player => {
        if (player) {
            player.dispose();
        } else {
            $(MattelVideoPlayer.ooPlayerInstances.players).each((index, item) => {
                item.dispose();
            });
        }
        MattelVideoPlayer.ooyalaApiLoaded = false;
    }

    /*** Slider Actions Begins***/
    MattelVideoPlayer.sliderActions = {};
    MattelVideoPlayer.sliderActions = {
            'findVideoPos': function(self, sliderType) {
                if (sliderType != "bxslider") { //flexslider
                    this.nthVideo = -1; // Detects the slide where the video resides.
                    this.elementId = "";
                    const that = this;
                    $(self.selector).each((key, item) => {
                        if (!$(item).hasClass('clone')) {
                            if ($(item).find('.player-wrapper').length) {
                                that.nthVideo++;
                                that.elementId = $(item).find('.ooyala-video-player').attr('id');
                            }
                            if ($(item).hasClass('flex-active-slide')) {
                                return false;
                            }
                        }
                    })
                } else if (sliderType == "bxslider") { //bxslider
                    //not required as of now
                }
            },
            'beforeAction': function(slider, self, previousSlide, currentSlide, sliderType) {
                if (sliderType != "bxslider") { //flexslider
                    // chk whether the current slide contains video or not.
                    const slideHasVideo = ($('.flex-active-slide').find('.player-wrapper').length) ? true : false;
                    MattelVideoPlayer.sliderActions.findVideoPos(self, sliderType);
                    console.log(this.nthVideo);
                    if (slideHasVideo) { // if video found in current slide, pause it.
                        const x = this.elementId;
                        const y = MattelVideoPlayer.ooPlayerInstances.players.filter((v, k) => {
                            return (x == v.getElementId())
                        });
                        if (y.length > 0) {
                            y[0].pause();
                        }
                        //MattelVideoPlayer.ooPlayerInstances.players[this.nthVideo].pause();
                        slider.play();
                    }
                } else if (sliderType == "bxslider") { //bxslider
                    //not required as of now   
                }
            },
            'afterAction': function(slider, self, previousSlide, currentSlide, sliderType) {
                if (sliderType != "bxslider") { //flexslider
                    // chk whether the current slide contains video or not.
                    const slideHasVideo = ($('.flex-active-slide').find('.player-wrapper').length) ? true : false;
                    MattelVideoPlayer.sliderActions.findVideoPos(self, sliderType);
                    if (slideHasVideo) { // if video found in current slide, play video and pause slider.
                        //MattelVideoPlayer.ooPlayerInstances.players[this.nthVideo].play();
                        const x = this.elementId;
                        const y = MattelVideoPlayer.ooPlayerInstances.players.filter((v, k) => {
                            return (x == v.getElementId())
                        });
                        if (y.length > 0 && !y[0].isPlaying()) {
                            y[0].play();
                            slider.pause();
                            //slider.play();
                            //setTimeout(function(){
                            //slider.pause();
                            //$('[data-slidertype ="flexSlider"]').flexslider('pause')
                            //},1000);
                        }



                    } else { // if video is not in current slider play slider
                        slider.play();
                    }
                } else if (sliderType == "bxslider") { //bxslider
                    $('.bx-active-slide').removeClass('bx-active-slide');
                    self.parent().find('> li').eq(currentSlide).addClass('bx-active-slide')
                    if (self.parent().find('li').eq(currentSlide).find('.player-wrapper').length) {
                        MattelVideoPlayer.ooPlayerInstances.players.forEach((player, indx) => {
                            if (self.find('.ooyala-video-player').attr('id') == player.elementId) {
                                player.play();
                            } else {
                                player.pause();
                            }
                            player.on("ended", event => {
                                $(self).parentsUntil('[data-slidertype="bxSlider"]').find('.bx-next').trigger('click');
                            });
                        })
                    }
                }
            },
            'startAction': function(slider, self, previousSlide, currentSlide, sliderType) {
                if (sliderType != "bxslider") { //flexslider
                    // 1. Initially pause the slider
                    slider.pause();
                    // Go through all the player instances 
                    MattelVideoPlayer.ooPlayerInstances.players.forEach((player, indx) => {
                        MattelVideoPlayer.ooPlayerInstances.players[0].play();
                        // Handle Video Pause Event 
                        player.on("pause", event => {
                            slider.play();
                            $('[data-slidertype ="flexSlider"]').play();
                        });
                        // Handle Video Completed Playing Event 
                        player.on("ended", event => {
                            $('[data-slidertype ="flexSlider"]').flexslider(((slider.currentSlide + 1) < slider.pagingCount) ? slider.currentSlide + 1 : 0);
                            slider.play();
                        });
                        player.on("playing", event => {
                            // slider.pause();
                        });
                    });
                } else if (sliderType == "bxslider") { //bxslider  
                    $(self).parent().find('li').eq(1).addClass('bx-active-slide')
                    $(self).parent().find('> li').addClass('slide')
                    $(self).parent().find('.bx-clone').removeClass('slide')
                        // Go through all the player instances 
                    MattelVideoPlayer.ooPlayerInstances.players.forEach((player, indx) => {
                        MattelVideoPlayer.ooPlayerInstances.players[0].play();
                        // Handle Video Pause Event
                        player.on("pause", event => {
                            slider.startAuto();
                        });
                        // Handle Video Completed Playing Event
                        player.on("ended", event => {
                            //slider.goToNextSlide();
                        });
                        // Handle the player ready to play Event
                        player.on("loadedmetadata", event => {
                            slider.stopAuto();
                        });
                        player.on("playing", event => {
                            slider.stopAuto();
                        });
                    });
                }
            }
        }
        /*** Slider Actions Ends***/

    // initiate the functionality to be triggered on page load
    const windowOnLoad = () => {
        if (MattelVideoPlayer.ooyalaApiLoaded) {
            const getHiddenVideoId = document.getElementById('charComponentUri');
            if (getHiddenVideoId != null) {
                const videoListItem = document.getElementsByClassName('video-list-item');
                if (getHiddenVideoId.value == "") {
                    generatedExtId = videoListItem[0].getAttribute('data-video-id');
                } else {
                    for (let i = 0; i < videoListItem.length; i++) {
                        if (videoListItem[i].getAttribute('videoTcmId') == getHiddenVideoId.value) {
                            generatedExtId = videoListItem[i].getAttribute('data-video-id');
                            break;
                        }
                    }
                }
                /**  
                    Flagging this variable for thumbnail reload
                    (i.e, clicking thumbnail, reloads the page and play the clicked video on load)
                **/
                haveVideoToLoad = true;
            }
            ooVideoSetters();
        }
    }
    const getBrandName = () => {
        return (location.host.match(/([^.]+)\.\w{2,3}(?:\.\w{2})?$/) || [])[1];
    };
    const getBrandConfig = (brandName) => {
        let obj = {};
        switch (brandName) {
            case 'bobthebuilder':
                obj = {
                    playerId: "default",
                    accountId: "6058004139001"
                }
                break;
            case 'thomasandfriends':
            case 'allaboardforglobalgoals':
                obj = {
                    playerId: "default",
                    accountId: "6058004139001"
                }
                break;
            case 'fisher-price':
                obj = {
                    playerId: "default",
                    accountId: "6057994541001"
                }
                break;
            default:
                obj = {
                    playerId: "eWOMBjeN",
                    accountId: "6057994534001"
                }
                break;
        }
        if ($("#video-playlist").val() == "true") {
            obj.playerId = "leG4nss9G";
        }
        return obj;
    };

    const wrapperJSInit = () => {
        deluxeInit((status) => {
            if (status) {
                wrapperJSInitialized = true;
                MattelVideoPlayer.init();
            }
        });
    };
    // ooyala API initialization
    MattelVideoPlayer.init = () => {
        if (!MattelVideoPlayer.ooyalaApiLoaded && !configuration.isBrightCovePlayer) {
            MattelVideoPlayer.ooyalaApiLoaded = true;
            renderOoyalaApi();
            windowOnLoad();
        }
    }
    configuration(getBrandConfig(
        getBrandName()
    ));
    // configuration({
    //     playerId: "default",
    //     accountId: "6057994542001"
    // })
    if (!wrapperJSInitialized) {
        wrapperJSInit();
    }
    $(() => {
        if (!wrapperJSInitialized) {
            wrapperJSInit();
        }
        if (!MattelVideoPlayer.ooyalaApiLoaded) {
            MattelVideoPlayer.init();
            MattelVideoPlayer.ooyalaApiLoaded = true;
        }
    });
})();