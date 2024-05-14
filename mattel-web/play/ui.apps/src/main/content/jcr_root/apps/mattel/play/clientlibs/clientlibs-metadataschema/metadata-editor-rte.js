(function($document, $) {
    "use strict";
 
    $document.on("foundation-contentloaded", initRTE);
 
    function initRTE(){
        var $rteContainer = $document.find(".richtext-container");
        var $valueField = $rteContainer.find("input[type=hidden]");
        
        if(_.isEmpty($rteContainer)){
            return;
        }
 
        CUI.util.plugClass(CUI.RichText, "richEdit", function(rte) {
            CUI.rte.ConfigUtils.loadConfigAndStartEditing(rte, $(this));
        });
 
        handleStartFinish($rteContainer);
 
        styleUI($rteContainer);
        if($("input[name='./jcr:content/metadata/dc:title']").parents().hasClass("aem-assets-metadata-form-column column-3")){
        	$("input[name='./jcr:content/metadata/dc:title']").on("focusout",function(){
        		if($("input[name='./jcr:content/metadata/dc:seoTitle']").val().length == 0)
        		{


					$("input[name='./jcr:content/metadata/dc:seoTitle']").val($("input[name='./jcr:content/metadata/dc:title']").val());
       			}
        	});
            $rteContainer.on("focusout",function() {
				if($("input[name='./jcr:content/metadata/dc:metaDesc']").val().length == 0)
        		{


					$("input[name='./jcr:content/metadata/dc:metaDesc']").val($($valueField.val()).text());
       			}
            });
        }
    }
 
    function handleStartFinish($rteContainer){
        $rteContainer.find(".coral-RichText").each(function() {
            ($(this)).richEdit();
        });
 
        var $valueField = $rteContainer.find("input[type=hidden]");
 
        $rteContainer.each(function() {
            $(this).find(".coral-RichText-editable").empty().append($valueField.val());
        });
 
        $rteContainer.on("editing-finished", ".coral-RichText-editable", function(e, editedContent) {
            $valueField.val(editedContent);
        });
    }
 
    function styleUI($rteContainer){
        var $richTextDiv = $rteContainer.find(".coral-RichText");
 
        $rteContainer.find("[name='./textIsRich']").remove();
 
        $richTextDiv.css("height", "180px").closest(".aem-assets-metadata-form-column").css("width", "80%");
    }
})(Granite.$(document), Granite.$);