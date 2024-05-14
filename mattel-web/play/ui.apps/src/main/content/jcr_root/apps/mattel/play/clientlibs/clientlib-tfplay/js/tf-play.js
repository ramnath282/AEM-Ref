(function() { 
  var isDevice = /Android|webOS|iPhone|iPad|iPod|Opera Mini/i.test(navigator.userAgent);
  
  var gameUrlSwitcher = function gameUrlSwitcher(element) {
    if (isDevice) {
      var iframeSrc = element.find(".game_frame").attr("src");
      if (iframeSrc) window.location = iframeSrc;
    }
  };
  
  var init = function init() {
    var $body = $('body');
  
    if ($body.hasClass("game-page")) {
      var $ele = $("#pagename-lookout,#pagename-matching-pairs,#pagename-design-your-engine,#pagename-steam-team-relay");
      $ele.length && gameUrlSwitcher($ele);
    }
  };
  
  $(document).ready(function () {
    init();
  });
})();