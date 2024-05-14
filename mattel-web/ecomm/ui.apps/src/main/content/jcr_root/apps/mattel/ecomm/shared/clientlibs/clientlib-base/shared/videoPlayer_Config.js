// plroms

const playerDataAttrs = $("#video-hidden-properties").length ? $("#video-hidden-properties")[0].dataset : {};
const config = {
    defaultPlayer: playerDataAttrs.playerName || "deluxe",
    player: {
        apiLoaded: false,
        players: []
    },
    error: {},
    isAEMPage: $("body").hasClass("basicpage"),
    isManualTrigger: $("body").hasClass("basicpage") ? false : true,
    dependencies: ["jQuery", "_"],
    el: '.ooyala-video-player,.video-component,.with-video-player,.reusability-video-component,#video-player-component,.video-player-container,.video-wrapper,[med-type="ooyala"],.pdp-img-viewer-holder',
};
let self;
let playerConfigs = {};
const playerAction = (playerName) => {
    switch (playerName) {
        case "brightcove":
            playerConfigs = new brightcovePlayer();
            break;
        case "deluxe":
            playerConfigs = new deluxePlayer();
            break;
    };
};

// Brightcove player
const brightcovePlayer = function() {
    self = this;
    this.apiLoaded = false;
    this.stylesheetUrl = "";
    this.playerProps = {};
    this.players = [];
    this.pluginCheckCnt = 0;
    if (!config.isManualTrigger) {
        this.settings(this.getBrandConfig(
            this.getBrandName()
        ));
        this.init.call();
    }
};
brightcovePlayer.prototype = {
    settings: (obj) => {
        self.playerProps.playerId = self.playerProps.playerId || (obj && obj.playerId);
        self.playerProps.accountId = self.playerProps.accountId || (obj && obj.accountId);
        return {
            playerId: self.playerProps.playerId,
            accountId: self.playerProps.accountId,
        }
    },
    getBrandConfig: brandName => {
        let obj = {};
        switch (brandName) {
            case 'fisher-price':
                obj = {
                    playerId: "default",
                    accountId: "6057994541001"
                }
                break;
            case 'americangirl':
            case 'american-girl-play':
                obj = {
                    playerId: "default",
                    accountId: '6058004226001'
                }
                break;
            case 'fisher-price-kids':
                obj = {
                    playerId: "default",
                    accountId: '6057994542001'
                }
                break;
            default:
                obj = {
                    playerId: "default",
                    accountId: "6057994534001"
                }
                break;
        }
        return obj;
    },
    getBrandName: () => {
        const hiddenPageName = $("#businessSiteName");
        if (hiddenPageName.length) {
            return hiddenPageName.val();
        }
        return window.location.href.indexOf('ag') != -1 ? "americangirl" : (location.host.match(/([^.]+)\.\w{2,3}(?:\.\w{2})?$/) || [])[1];
    },
    updatePlayer: (playerId, videoId) => {
        const videoIdLen = videoId.toString().length;
        const updatedVideoId = videoIdLen >= 32 ? `ref:${videoId}` : videoId;
        let currentPlayer = typeof playerId == "object" ? playerId : self.players[playerId];
        if (!currentPlayer) {
            console.log("%c playerId is not available", "color:red");
            return;
        }
        currentPlayer.catalog.getVideo(updatedVideoId, (error, video) => {
            // Load the video object into the player
            currentPlayer.catalog.load(video);
            currentPlayer.play();
        });
    },
    waitForAPILoad: callBack => {
        window.setTimeout(() => {
            if (typeof videojs !== "undefined") {
                callBack();
            } else {
                self.pluginCheckCnt++;
                if (self.pluginCheckCnt > 20) {
                    console.log("%c BrightCove API not loaded/failed.", "color:red");
                    return;
                }
                self.waitForAPILoad(callBack);
            }
        }, 500);
    },
    createPlayerElement: (obj) => {
        const videoId = obj.videoId ? obj.videoId.toString().length : obj.playerId.toString().length;
        const updatedVideoId = videoId >= 32 ? `ref:${obj.playerId}` : obj.playerId;
        document.getElementById(obj.divId).innerHTML = `<video-js id="sub-${obj.divId}" ${obj.mute ? "muted=true" : ''} data-video-id="${updatedVideoId}"  data-account="${self.settings().accountId}" data-player="${self.settings().playerId}" data-embed="default" class="video-js" controls playsinline></video-js>`;
    },
    createPlaylistElement: (obj) => {
        const videoId = obj.videoId.toString().length;
        const updatedVideoId = videoId >= 32 ? `ref:${obj.playerId}` : obj.playerId;
        document.getElementById(obj.divId).innerHTML = `<div class="vjs-playlist-player-container"><video-js id="sub-${obj.divId}" ${obj.mute ? "muted=true" : ''} data-playlist-id="${updatedVideoId}" data-video-id=""  data-account="${self.settings().accountId}" data-player="${self.settings().playerId}" data-embed="default" class="video-js" controls></video-js></div>`;
    },
    createPlayer: (divId, playerId, cb, isPlaylist) => {
        if (Object.keys(config.error).length) {
            return;
        }
        let dataset = $(`#${divId}`)[0].dataset;
        self.waitForAPILoad(() => {
            let autoplay = dataset.autoplay == "true" ? true : false;
            let mute = dataset.mute == "true" ? 1 : 0;
            // any: The player will first try to call play. If that fails, mute the player and call play.
            const obj = {
                divId,
                playerId,
                autoplay,
                videoId: dataset.videoId,
                mute
            };
            isPlaylist ? self.createPlaylistElement(obj) : self.createPlayerElement(obj);
            bc(`sub-${divId}`);
            let myPlayer = videojs.getPlayer(`sub-${divId}`);
            myPlayer.ready(function() {
                self.players[playerId] = this;
                cb(this);
            });
            myPlayer.on('loadedmetadata', () => {
                if ((obj.autoplay == true || obj.autoplay == "true") && self.isScrolledIntoView($(`#${divId}`))) {
                    myPlayer.play();
                }
            });
        });
    },
    isScrolledIntoView: (elem) => {
        var elementTop = $(elem).offset().top;
        var elementBottom = elementTop + $(elem).outerHeight();
        var viewportTop = $(window).scrollTop() - 200;
        var viewportBottom = viewportTop + $(window).height();

        return elementBottom > viewportTop && elementTop < viewportBottom;
    },
    loadViaRequireJS: (url) => {
        window.require.config({
            'paths': {
                'bc': url
            },
            waitSeconds: 30
        });
        window.require(['bc'], () => {
            self.apiLoaded = true;
        });
    },
    bindPlayerAPI: () => {
        if (typeof videojs == "function") {
            self.apiLoaded = true;
            return;
        }
        let apiUrl = `//players.brightcove.net/${self.settings().accountId}/${self.settings().playerId}_default/index.min.js`;
        jQuery.cachedScript = (url, options) => {
            options = $.extend(options || {}, {
                dataType: "script",
                cache: true,
                url
            });
            return jQuery.ajax(options);
        };

        // Usage
        $.cachedScript(apiUrl).done((script, textStatus) => {
            if (typeof videojs != "function" && typeof define == "function") {
                self.loadViaRequireJS(apiUrl);
            } else {
                self.apiLoaded = true;
            }
        }).fail(err => {
            config.error['Account/Player_ID_Mismatch'] = true;
            console.log("%c BrightCove PlayerId/Account id seems to be incorrect.", "color:red");
        });
        $('head').append(`<link rel="stylesheet" type="text/css" href="${self.stylesheetUrl}">`);
    },
    init: cb => {
        if (Object.keys(config.error).length || !$(config.el).length) {
            typeof cb == "function" && cb(false);
            return;
        }
        self.bindPlayerAPI();
        typeof cb == "function" && cb(true);
    }
};

// Deluxe Player
const deluxePlayer = function() {
    self = this;
    this.apiLoaded = false;
    this.playerTheme = "bc-theme";
    this.pluginCheckCnt = 0;
    this.requireJSCheckCnt = 0;
    this.cdnDomain = playerDataAttrs.hostName || "https://mattel-sites-stage64.adobecqms.net";
    this.folderPath = `${this.cdnDomain}/content/dam/mattel/mattel-global/global/brightcove-legacy`;
    this.apiUrl = `${this.folderPath}/video-hls.min.js`;
    this.cssUrl = `${this.folderPath}/video-hls.min.css`;
    this.blockedAutoPlay = (/iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream) || (!!window.MSInputMethodContext && !!document.documentMode);
    if (!config.isManualTrigger) {
        this.init.call();
    }
    this.videoDomain = "https://video.mattel.com";
};
deluxePlayer.prototype = {
    settings: (videoId) => {
        return {
            src: `${self.videoDomain}/assets/hls/${videoId}/index.m3u8`,
            type: "application/x-mpegURL",
            poster: `${self.videoDomain}/assets/images/${videoId}/${self.getPosterParams(videoId)}.jpg`
        }
    },
    getPosterParams: (videoId) =>{
        let idInString = videoId.toString();
        return (idInString.length >= 32 || idInString.startsWith("6")) ? "poster" : videoId;
    },
    testImage(URL) {
        const tester = new Image();
        tester.onerror = (err) => {
            console.log(`%c ${err.srcElement.src}, poster image not available in dynamic media`, 'color:red');
        }
        tester.src = URL;
    },
    updatePlayer: (playerId, videoId) => {
        let currentPlayer = (typeof playerId == "object" ? playerId : self.players[playerId]);
        let params = self.settings(videoId);
        let { src, poster } = params;
        if (!currentPlayer) {
            console.log("%c playerId is not available", "color:red");
            return;
        }
        currentPlayer.tech_ && currentPlayer.tech_.clearTracks("text");
        currentPlayer.loadTech_(currentPlayer.options_.techOrder[0], null);
        currentPlayer.poster_ = poster;
        currentPlayer.techCall_('setPoster', poster);
        currentPlayer.trigger('posterchange');
        currentPlayer.src(src);
        currentPlayer.load();
        currentPlayer.ready(() => {
            currentPlayer.play();
        });
        currentPlayer.on("error", (e) => {
            e.stopImmediatePropagation();
        })
    },
    createPlayerElement: (divId, obj, cb) => {
        let params = self.settings(obj.videoId);
        let { src, type, poster } = params;
        self.testImage(poster);
        document.getElementById(divId).innerHTML = `<video id="sub-${divId}" 
                ${!obj.controls ?  '' : 'controls'} 
                ${poster ? 'poster='+poster : ''} 
                ${obj.mute ? 'muted' : ''} 
                ${obj.loop ? 'loop autoplay' : ''} 
                preload="auto" 
                crossorigin="anonymous" 
                class="video-js ${self.playerTheme} vjs-default-skin"><source src="${src}" type="${type}">
            </video>`;
        var options = {
            html5: {
                hlsjsConfig: {
                    // Put your hls.js config here
                }
            }
        };
        // setup beforeinitialize hook
        if (typeof videojs.Html5Hlsjs == "function") {
            videojs.Html5Hlsjs.addHook('beforeinitialize', (videojsPlayer, hlsjsInstance) => {
                // here you can interact with hls.js instance and/or video.js playback is initialized
            });
        }
        const player = videojs(`sub-${divId}`, options);
        player.ready(() => {
            obj.autoplay && self.isScrolledIntoView($(player.el_)) && !self.blockedAutoPlay && player.play();
        });
        player.on("error", (e) => {
            $(player.el_).find(".vjs-error-display .vjs-modal-dialog-content").html(`Video ID : <u>${obj.videoId}</u> not migrated yet`);
            e.stopImmediatePropagation();
        });
        player.html5Player = true;
        cb(player);
    },
    waitForAPILoad: callBack => {
        window.setTimeout(() => {
            if (typeof videojs !== "undefined") {
                callBack();
            } else {
                self.pluginCheckCnt++;
                if (self.pluginCheckCnt > 20) {
                    console.log("%c HLS API not loaded/failed.", "color:red");
                    return;
                }
                self.waitForAPILoad(callBack);
            }
        }, 500);
    },
    createPlaylistElement: (obj) => {
        document.getElementById(obj.divId).innerHTML = ``;
    },
    createPlayer: (divId, playerId, cb, isPlaylist) => {
        if (Object.keys(config.error).length) {
            return;
        }
        let dataset = $(`#${divId}`)[0].dataset;
        let autoplay = dataset.autoplay == "true" ? true : false;
        let mute = dataset.mute == "true" ? 1 : 0;
        const obj = {
            divId,
            playerId,
            autoplay,
            videoId: dataset.videoId || playerId,
            mute,
            loop: dataset.loop == "true" ? 1 : 0,
            controls: dataset.controls == "false" ? false : true
        };
        self.waitForAPILoad(() => {
            self.createPlayerElement(divId, obj, (player) => {
                cb(player);
            });
        });
    },
    loadCSSFile: () => {
        $('head').append(`<link rel="stylesheet" type="text/css" href="${self.cssUrl}?123">`);
    },
    isScrolledIntoView: (elem) => {
        var elementTop = $(elem).offset().top;
        var elementBottom = elementTop + $(elem).outerHeight();
        var viewportTop = $(window).scrollTop() - 200;
        var viewportBottom = viewportTop + $(window).height();

        return elementBottom > viewportTop && elementTop < viewportBottom;
    },
    bindPlayerAPI: () => {
        if (typeof videojs == "function") {
            self.apiLoaded = true;
            return;
        }
        jQuery.cachedScript = (url, options) => {
            options = $.extend(options || {}, {
                dataType: "script",
                cache: true,
                url
            });
            return jQuery.ajax(options);
        };
        $.cachedScript(self.apiUrl).done((script, textStatus) => {
            self.apiLoaded = true;
        }).fail(err => {
            config.error['HLSJSNotFound'] = true;
            console.log("%c Deluxe PlayerId framework (HLS) not loaded.", "color:red");
        });
    },
    init: cb => {
        if (Object.keys(config.error).length || !$(config.el).length) {
            typeof cb == "function" && cb(false);
            return;
        }
        self.loadCSSFile();
        self.bindPlayerAPI();
        typeof cb == "function" && cb(true);
    }
};


const playerActionInit = () => {
    playerAction(config.defaultPlayer);
};
playerActionInit();

const init = playerConfigs['init'];
const configuration = playerConfigs['settings'];
const createPlayer = playerConfigs['createPlayer'];
const updatePlayer = playerConfigs['updatePlayer'];

export {
    init,
    configuration,
    createPlayer,
    updatePlayer
};