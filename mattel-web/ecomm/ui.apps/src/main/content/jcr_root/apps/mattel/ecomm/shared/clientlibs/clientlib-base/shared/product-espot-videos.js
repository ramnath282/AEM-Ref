import {
	scene7PlayerInit,
	isScrolledIntoView,
	onScrollPlayerActions,
	deluxePlayerInit
} from './_videoPlayer';

const playersArr = [];
const checkVideoType = elem => {
	let $elem = $(elem).find('.aem-video-player');
	if(!$elem.length) return;
	let divId = $elem.attr('id'),
		datasets = $elem[0].dataset,
		videoId = datasets.videoId,
		videoType = datasets.videoType || 'ooyala',
		videoIndex = datasets.videoIndex || 0;
	if(!divId || !videoId) return;
	switch (videoType) {
		case 'scene7':
			scene7PlayerInit(divId, videoId, videoIndex, function (player) {
				$(elem).addClass('active');
			}, datasets.playOn);
			break;
		case 'youtube':
			// not started
			break;
		case 'deluxe':
			if(playersArr[videoIndex]){
				// if(!playersArr[videoIndex].paused()) playersArr[videoIndex].pause();
				return;
			}
			playersArr[videoIndex] = "temp";
			deluxePlayerInit(divId, videoId, function (player) {
				playersArr[videoIndex] = player;
				if ($(`#sub-${divId}`).length) {
					$(`#sub-${divId}`).css({
						"width": "100%",
						"padding-top": '0',
						"height": "100%"
					});
				}
				if(datasets.playOn){
					player.play();
				}
			})
			break;

	}
};
let self, deluxeApiLoaded = false, deluxeCheckCnt=0, cnt=0;
const playerNewActions = function() {
	self = this;
	this.bindPlayerActionEvent();
};
playerNewActions.prototype = {
	bindPlayerActionEvent : function() {
		window.global.eventBindingInst.bindLooping({
			'click .video-poster-image img': 'playVideo'
		},this);
		$(document).on('hide.bs.modal','#VideoPlayInModal', function () {
            const $player = $("#VideoPlayInModal:first").find('.aem-video-player');
            if (!$player.is(':empty')) { $player.empty();}
        });
	},
	playVideo : function(elem) {
		const ele = elem.closest(".video-poster-image"),
			dataset = ele.dataset;
		if(dataset.action == "inline"){
			self.playInline(ele, dataset);
		} else if(dataset.action == "modal"){
			self.playModal(dataset);
		}
	},
	playInline : function(elem, dataset){
		const {videoType} = dataset,
			videoElem = elem.previousElementSibling;
		if(videoType == "deluxe"){
			$(videoElem).find("video")[0].play();
		} else if (videoElem.tagName == "IFRAME"){
			videoElem.src=  videoElem.src.replace("autoplay=0","autoplay=1"); // autoplay and start video
		} else if (videoElem.tagName == "VIDEO"){
			videoElem.play();
		}
		elem.style.display = "none";
	},
	playModal : function(dataset){
		const {videoType, videoId} = dataset,
			$modalEle = $("#VideoPlayInModal:first"),
			$videoElem = $modalEle.find('.aem-video-player');
		if(!$videoElem .length) return;
		$videoElem.attr({'data-video-type': videoType,'data-video-id': videoId, "data-play-On": true, "data-auto-play": true, "id": `aem-video-modal-${cnt++}`});
		if (!$videoElem.is(':empty')) { $videoElem.empty();}
		if(videoType == "deluxe" || videoType == "scene7"){
			delete playersArr[0];
			checkVideoType($modalEle);
		} else if(videoType == "youtube"){
			$videoElem.append(`<iframe width="100%" height="100%" src="${videoId}?autoplay=1" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen allow='autoplay'></iframe>`)
		}
		$modalEle.modal("show");
	}
};

export const renderVideoPlayer = ele => {
	$gridContainer = $(ele).find('.player-wrapper:not(.active)');
	$gridVideoContainer = $(ele).find('.player-wrapper.playing');
	let $playerEle;
	if (!$gridContainer.length && !$gridVideoContainer.length) {
		return;
	}
	if(!bindPlayerActionBool){
		const playerNewActionsInit = new playerNewActions();
		bindPlayerActionBool = true;
	}
	for (let i = 0; i < $gridContainer.length; i++) {
		if (isScrolledIntoView($gridContainer[i]) > 0.5) {
			checkVideoType($gridContainer[i]);
		}
	}
	if ($gridVideoContainer.length && !isScrolledIntoView($gridVideoContainer[0])) {
		$playerEle = $gridVideoContainer.find('.aem-video-player');
		onScrollPlayerActions($playerEle.data('videoId'),undefined,$playerEle.data("videoIndex") && playersArr[$playerEle.data("videoIndex")]);
	}
};

let $gridContainer,
	bindPlayerActionBool = false,
	$gridVideoContainer;

export default {};