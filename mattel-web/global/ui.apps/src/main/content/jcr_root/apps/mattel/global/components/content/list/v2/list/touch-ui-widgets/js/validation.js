(function ($, $document) {
    "use strict";

    $document.on("dialog-ready", function() {
        setTimeout(function(){ initialhide(); }, 50);
    });

    $(document).on('change', '.list-version-2 .cq-dialog-dropdown-showhide', function(){
        var cotentTab = $('coral-dialog-content coral-tablist').children()[4];
        var selectValue = $('.cq-dialog-dropdown-showhide').val();
        contentTabShowHide(selectValue,cotentTab);
    });

    function initialhide(){
      var cotentTab = $('.list-version-2 coral-tablist').children()[4];
      var selectValue = $('.cq-dialog-dropdown-showhide').val();
      contentTabShowHide(selectValue, cotentTab);
   	}

    function contentTabShowHide(selectValue, cotentTab){
      if(selectValue == 'static'){
          $(cotentTab).removeClass('hide');
      }else{
		  $(cotentTab).addClass('hide');
      }
    }

})($, $(document));