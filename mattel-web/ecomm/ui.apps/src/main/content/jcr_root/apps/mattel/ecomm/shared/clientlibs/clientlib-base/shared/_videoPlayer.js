
let deluxeCheckCnt = 0,
videoIndex = 0;

export const setDynamicId = elem => {
if (!elem.length) {
    return;
}
elem.each(function (indx, item) {
    videoIndex++;
    item.setAttribute('id', 'video-container-' + (videoIndex));
    item.setAttribute('data-video-index', videoIndex);
});
};
export const isScrolledIntoView = elem => {
let x = elem.offsetLeft,
    y = elem.offsetTop,
    w = elem.offsetWidth,
    h = elem.offsetHeight,
    r = x + w, //right
    b = y + h, //bottom
    visibleX,
    visibleY;

visibleX = Math.max(0, Math.min(w, window.pageXOffset + window.innerWidth - x, r - window.pageXOffset));
visibleY = Math.max(0, Math.min(h, window.pageYOffset + window.innerHeight - y, b - window.pageYOffset));

return (visibleX * visibleY) / (w * h);
};

const deluxePlayerActions = player => {
let $ele;
player.on("playing", event => {
    $ele = $(event.target).closest(".aem-video-player").parent();
    $ele.addClass('playing');
    
});
player.on("pause", event => {
    $ele = $(event.target).closest(".aem-video-player").parent();
    $ele.removeClass('playing');
});
};
const waitForDeluxeJS = callBack => {
if (typeof videojs == "function") {
    callBack(true);
}
else {
    window.setTimeout( () =>{
        deluxeCheckCnt++;
        if (deluxeCheckCnt > 12) {
            callBack(false);
            return;
        }
        waitForDeluxeJS(callBack);
    }, 500);
}
};

export const deluxePlayerInit = (divId, videoId, cb) => {
const {deluxeConfigs,initDeluxePlayer,createDeluxePlayer} = window.global;
if(!initDeluxePlayer){
    console.log(`%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.`, "background: red; color:white");
    return;
}
if ($(`#sub-${divId}`).length) return;
if (!$(deluxeConfigs.el).length) {
    deluxeConfigs.el+=",.aem-video-player";
}
waitForDeluxeJS((bool)=>{
    if(!bool || !$(`#${divId}`).length){
        $(`#${divId}`).addClass("player-not-found");
        return;
    }
    createDeluxePlayer(divId, videoId,(player)=>{
        if ($(`#sub-${divId}`).length) {
            $(`#sub-${divId}`).css({
                "width": '100%',
                "padding-top": '56.25%',
                "height": "auto"
            });
        }
        deluxePlayerActions(player);
        cb(player);
    });
});
};
export const onScrollPlayerActions = (videoId, isPlaying, player) => {
let $videoActionElem = $('.grid-lists').find(`.aem-video-player[data-video-id="${videoId}"]`);
let $playingElements;
if (!$videoActionElem.length) return;
let videoType = $videoActionElem.attr('data-video-type');
switch (videoType) {
    case 'scene7':
        $playingElements = $('.grid-lists').find('.player-wrapper.playing video');
        if (isPlaying == 'play') {
            $videoActionElem.parents('.player-wrapper').addClass('playing');
        } else if ($playingElements.length) {
            $videoActionElem.parents('.player-wrapper').removeClass('playing');
            $playingElements[0].pause();
        }
        break;
    case 'deluxe':
        if(!player.paused()){
            player.pause();
        }
        break;
}
};
export const html5PlayerInit = (divId, videoId, videoIndex, cb) => {
if (!videoId) {
    return;
}
let video = document.createElement("video");

video.src = videoId;
//   video.autoplay = true;
video.volume = 0;
$(`#${divId}`).append(video);
cb(true);
};

export const youtubePlayerInit = (divId, videoId, videoIndex, cb) => {
let player = new YT.Player(divId, {
    videoId: videoId,
    events: {
        //   'onReady': onPlayerReady,
        //   'onStateChange': onPlayerStateChange
    }
});
cb(player);
};
export const scene7PlayerInit = (divId, videoId, videoIndex, cb, enableAutoPlay) => {
if (typeof s7viewers == "undefined" || typeof s7viewers.VideoViewer == "undefined") {
    return;
}
let videoserverurl = "https://mattel.scene7.com/is/content/";
let player = new s7viewers.VideoViewer({
    "containerId": divId,
    "params": {
        "asset": videoId.replace(videoserverurl, ''),
        "serverurl": "https://s7d1.scene7.com/is/image/",
        "videoserverurl": videoserverurl
    },
    handlers: {
        "trackEvent": function (objID, compClass, instName, timeStamp, eventInfo) {
            let actionName = eventInfo.split(',')[0];
            if (actionName == "PLAY") {
                onScrollPlayerActions(videoId, 'play');
            } else if (actionName == "PAUSE") {
                onScrollPlayerActions(videoId, 'pause');
            }
        }
    },
    autoplay: enableAutoPlay ? 1 : 0
}).init();
playerArr[videoIndex] = player;
typeof cb == 'function' && cb(player);
};

let playerArr = []
// OOReady();
export default {};
