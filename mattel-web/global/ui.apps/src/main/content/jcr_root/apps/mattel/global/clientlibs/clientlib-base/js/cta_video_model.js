$(document).ready(function() {
    var deluxeApiLoaded = false, deluxeCheckCnt=0;
    $(".cta_model_video a").each(function(){
        var videoType = $(this).data("videoType");
        if(videoType == undefined){
            var videoSrc = $(this).attr("href");
            var setVideoType = videoSrc.indexOf(".pdf") !== -1 ? "pdf" : (videoSrc.indexOf("youtube") != -1 ? "youtube" : (videoSrc.indexOf("scene7") != -1 ? "scene7" : ""));
            $(this).attr("data-video-type", setVideoType);
            videoType = setVideoType;
        }
        if(videoType == "youtube"){
            loadAPI();
        } else if(videoType == "deluxe" && !deluxeApiLoaded){
            var initDeluxePlayer = window.global.initDeluxePlayer;
            if (!initDeluxePlayer) {
                console.log("%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.", "background: red; color:white");
                return;
            } else{
                initDeluxePlayer(undefined, true);
                deluxeApiLoaded = true;
            }
        }
    });
    var playersArr = [];
    var youtubeVideoId;
    $(".cta_model_video a").on("click", function(evt, cb) {
        var videoSrc = $(this).attr("href");
        var videoType = $(this).data("videoType");
        if(videoType == "pdf"){
            if($(window).width() >= 768) {
                window.open(videoSrc, "", "width=500,height=500");
            } else {
                window.open(videoSrc, "_blank");
            }     
            evt.preventDefault();   
            return;
        }
        if ($("#VideoTargetModal").length == 0) {
			var htmlModalPopup = '<div class="modal" id="VideoTargetModal" tabindex="-1" role="dialog" aria-labelledby="VideoTargetModalLabel"><div class="modal-dialog modal-lg" role="document"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><div class="embed-responsive embed-responsive-16by9"><video controls="true" class="vr-iframe embed-responsive-item html5video" id="VideoTargetModalLabel"><source  data-src="" src="" ></video></div></div></div></div></div>';
            if (videoType == "youtube") {
                videoType = "youtube";
                youtubeVideoId = $(this).attr("href").split('/').pop();
                htmlModalPopup = '<div class="modal" id="VideoTargetModal" tabindex="-1" role="dialog" aria-labelledby="VideoTargetModalLabel"><div class="modal-dialog modal-lg" role="document"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><div class="embed-responsive embed-responsive-16by9"><iframe class="vr-iframe embed-responsive-item" id="VideoTargetModalLabel" data-src="" src="" scrolling="no"></iframe><div id="player"></div></div></div></div></div></div>';
            } if (videoType == "deluxe") {
                youtubeVideoId = videoSrc;
                htmlModalPopup = '<div class="modal" id="VideoTargetModal" tabindex="-1" role="dialog" aria-labelledby="VideoTargetModalLabel"><div class="modal-dialog modal-lg" role="document"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"><div class="embed-responsive embed-responsive-16by9"><div id="video-player-container-1" class="aem-video-player"></div></div></div></div></div></div>';
            }
			$(".root").append(htmlModalPopup);

        }
        evt.preventDefault();
        $("#VideoTargetModal").attr({"data-video-type": videoType, "data-video-id": videoSrc}).modal("show");
        if (videoType == "youtube" || videoType == "scene7") {
            $("#VideoTargetModalLabel").attr({
                "src": videoSrc,
                "data-src": videoSrc
            });
        }
    });

    $('body').on('hidden.bs.modal', '#VideoTargetModal', function() {
        $("#VideoTargetModal").remove();
        $("#VideoTargetModalLabel").attr({
            "src": "",
            "data-src": ""
        });

    });
     $('body').on('show.bs.modal', '#VideoTargetModal', function() {
        let videoType = this.dataset.videoType;
        let videoId = this.dataset.videoId;
         if (videoType == "youtube"){
             moadlyoutube.onYouTubeIframeAPIReady();
         } else if (videoType == "scene7"){
         	sceneVideoTrack();
         } else if (videoType == "deluxe"){
             if(playersArr[videoType]){
                playersArr[videoType].dispose();
             }
             waitForDeluxeJS(function (callBack) {
                callBack && window.global.createDeluxePlayer && window.global.createDeluxePlayer("video-player-container-1", videoId, function (player) {
                    if ($("#sub-video-player-container-1").length) {
                        $("#sub-video-player-container-1").css({
                            "width": '100%',
                            "padding-top": '56.25%',
                            "height": "auto"
                        });
                    }
                    playersArr[videoType] = player;
                    player.play();
                });
            });
         }

     });
     function waitForDeluxeJS(callBack) {
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
                waitForDeluxeJS(callBack);
            }, 500);
        }
    }
     function loadAPI(){
        var tag = document.createElement('script');
        tag.src = "https://www.youtube.com/iframe_api";
        var firstScriptTag = document.getElementsByTagName('script')[0];
        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
        window.player=null;
     }
    function sceneVideoTrack(){
		
		clearPreviousData();
        var video_name = '';
		if($(this).attr('src')){
			video_name = $(this).attr('src').split('/').pop();
			if (video_name.indexOf('.') != -1) {
				video_name = $(this).attr('src').split('/').pop().split('.')[0];
			}
		} else if($(this).attr('href')) {
			video_name = $(this).attr('href').split('/').pop();
			if (video_name.indexOf('.') != -1) {
				video_name = $(this).attr('href').split('/').pop().split('.')[0];
			}
		}
	    utag_data.event_action = "video clicks";
        utag_data.event_action_type = "click";
        utag_data.event_detail = video_name;
		utag_data.video_id = "scene7-" + video_name;
        utag_data.video_length = this.duration;
        utag_data.video_platform = "scene7";
			
        $("video#VideoTargetModalLabel").bind('play', function () {                
            utag_data.event_detail_sub = "play";
            utag.link(utag_data);
    	});

        $("video#VideoTargetModalLabel").bind('pause', function () {                          
            utag_data.event_detail_sub = "pause";           
            utag.link(utag_data);
        });
    }
    var moadlyoutube = {
        onYouTubeIframeAPIReady:function onYouTubeIframeAPIReady() {
        window.player = new YT.Player('player', {
          videoId: youtubeVideoId,
          events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
          }
        });
      }
    };

      function onPlayerReady(event) {
          	utag_data.event_action = "video clicks";
			utag_data.event_action_type = "click";
			utag_data.event_detail = youtubeVideoId;
			utag_data.event_detail_sub = "YouTube_video_start";
			utag_data.video_id = "YouTube-" + youtubeVideoId;
			utag_data.video_length = event.target.getDuration();
			utag_data.video_platform = "YouTube";
			utag.link(utag_data);
      }

      function onPlayerStateChange(event) {
		  
		utag_data.event_action = "video clicks";
		utag_data.event_action_type = "click";
		utag_data.event_detail = youtubeVideoId;
		utag_data.video_id = "YouTube-" + youtubeVideoId;
		utag_data.video_length = event.target.getDuration();
		utag_data.video_platform = "YouTube";
		
        if (event.data == YT.PlayerState.PLAYING) {
           	utag_data.event_detail_sub = "YouTube_video_play";		
        }else if (event.data == YT.PlayerState.ENDED) {      
			utag_data.event_detail_sub = "YouTube_video_complete";			
        }else  if (event.data == YT.PlayerState.PAUSED) {         
			utag_data.event_detail_sub = "YouTube_video_pause";			
        }
		utag.link(utag_data);
      }

});
