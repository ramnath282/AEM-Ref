(function ($) {
    $.fn.inlineStyle = function (prop) {
        return this.prop("style")[prop];
    };
}(jQuery));
(function(){
                var basehtmlFontSize = parseFloat($("html").css("font-size"));
  $('.article-content span').each(function(){
                var currentFs = $(this).inlineStyle("font-size");
                if(currentFs!="") {
                var fs = parseFloat(currentFs)/basehtmlFontSize;
                                $(this).css("font-size",fs+"rem");
                }
  });  
})();
