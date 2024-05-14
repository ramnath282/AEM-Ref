import eventBinding from '../shared/eventBinding';
import {ooPlayerInit,isScrolledIntoView, setDynamicId} from "../shared/_videoPlayer";

let self,$gridContainer,
  $gridVideoContainer,
  $videoActionElem,
  playerArr = [];

class contactNav {
    constructor() {
        self = this;
        this.ele = ".plp-banner-sec";
    }
    init() {    
        if(this.ele.length) {
                eventBindingInst.bindLooping({
                "click #plpbanner .arrow-btn": "expandBanner",
                },self);
        }
        this.charCount();
        this.bindScroll();
    }
    expandBanner (el, evt) {
        evt.preventDefault();
        if($(el).hasClass('open')){
          $(el).attr('aria-expanded', 'false');
          $(el).find('span.sr-only').html('hide content');
        }else{          
          $(el).attr('aria-expanded', 'true');
          $(el).find('span.sr-only').html('show content');
        }
        $(el).toggleClass('open');
        var banner_sec = $(self.ele).find('.plp-banner-extend');
        if($(el).hasClass('open')){
          $(banner_sec).show();
          $('.fa-chevron-up').removeClass('hide');
          $('.fa-chevron-down').addClass('hide');
        } else {
          $(banner_sec).hide();
          $('.fa-chevron-up').addClass('hide');
          $('.fa-chevron-down').removeClass('hide');
        }
    }
    charCount(el) {
       setDynamicId($(".aem-video-player"));
        $(".plp-banner-content p").text(function (i, text) {
            return text.length > 120 ? text.substr(0, text.lastIndexOf(' ', 117)) + '...' : text;
        });
    }
    pauseOtherVideos(elem,curVideoIndx){
    if(!elem) return;
    let videoIndx;
    _.each(elem,function(item){
      videoIndx = $(item).attr('data-video-index');
      videoIndx != curVideoIndx && playerArr[videoIndx].isPlaying() && playerArr[videoIndx].pause();
    });
  }
  checkPlaying(videoId, isPlaying) {
    $videoActionElem = $(self.ele).find('.aem-video-player[data-video-id=' + videoId + ']');
    let $playingElements;
    if (!$videoActionElem.length) return;
    let videoIndx = $videoActionElem.attr('data-video-index');
    if (isPlaying == "play") {
      $videoActionElem.parents('.player-wrapper').addClass('playing');
      $playingElements = $(self.ele).find('.player-wrapper.playing');
      if($playingElements.length>1){
        self.pauseOtherVideos($playingElements.find('.aem-video-player'),videoIndx);
      }
    } else {
      $videoActionElem.parents('.player-wrapper').removeClass('playing');
      playerArr[videoIndx].isPlaying() && playerArr[videoIndx].pause();
    }
  }
  playerActionBindings(player) {
    player.mb.subscribe(OO.EVENTS.PLAYING, 'playing', function (event) {
      self.checkPlaying(this.mb._interceptArgs.setEmbedCode[0], 'play');
    });
    player.mb.subscribe(OO.EVENTS.PAUSED, 'paused', function (event) {
      self.checkPlaying(this.mb._interceptArgs.setEmbedCode[0], 'pause');
    });
  }
  renderVideoPlayer() {
    $gridContainer = $(self.ele).find('.player-wrapper:not(.active)');
    $gridVideoContainer = $(self.ele).find('.player-wrapper.playing');
    if ($gridContainer.length) {
      for (let i = 0; i < $gridContainer.length; i++) {
          let $videoElem = $($gridContainer[i]),
            divId = $videoElem.find(".aem-video-player").attr('id'),
            videoId = $videoElem.find(".aem-video-player").data('videoId'),
            videoIndex = $videoElem.find(".aem-video-player").data('videoIndex') || 0;
          ooPlayerInit(divId, videoId, videoIndex, function (player) {
              playerArr[videoIndex] = player;
              self.playerActionBindings(playerArr[videoIndex]);
              $videoElem.addClass('active');
            });
      }
    }
    if ($gridVideoContainer.length && !isScrolledIntoView($gridVideoContainer[0])) {
      let $videoElem = $($gridVideoContainer[0]),
        videoId = $videoElem.find(".aem-video-player").data('videoId') || 0;
      self.checkPlaying(videoId);
    }
  }
  bindScroll() {
    self.renderVideoPlayer();
  }
}
let eventBindingInst = new eventBinding(),
    contactNavInstance = new contactNav();
contactNavInstance.init();