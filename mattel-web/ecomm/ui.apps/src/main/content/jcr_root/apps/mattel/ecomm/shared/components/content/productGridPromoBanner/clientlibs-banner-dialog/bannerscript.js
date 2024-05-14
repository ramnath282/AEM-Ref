(function($, $document) {
    "use strict";
    $(document).ready(function() {
    $(document).on("change", ".mobilePromoVideoType, .mobilePromoMediaType, .tabletPromoVideoType, .tabletPromoMediaType, .promoVideoType, .promoMediaType", function(e) {
            var currentEle = $(this).closest(".ag-cmp-banner-editor")
            showhide(currentEle);
        });
    });
    $(document).on("foundation-contentloaded", function(e) {
    if ($(".ag-cmp-banner-editor").length > 0){
		if ($(".promoMediaType").find(".coral3-Select-label").text() == 'Image') {
            $("input[name='./desktopImageAltText']").parent().show();
			$("input[name='./desktopPromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
			$("coral-select[name='./desktopVideoType']").closest(".coral-Form-fieldwrapper").hide();
			$("foundation-autocomplete[name='./desktopVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
			$("coral-switch[name='./playVidInModalDesktop']").closest(".coral-Form-fieldwrapper").hide();
			$("coral-switch[name='./autoPlayVideoDesktop']").closest(".coral-Form-fieldwrapper").hide();
			$("coral-fileupload[name='./desktopPromotionalImage']").removeAttr("data-cq-fileupload-required");
        } else if ($(".promoMediaType").find(".coral3-Select-label").text() == 'Video') {
            desktopShowHide();
        }
		$("coral-switch[name='./playVidInModalDesktop']").bind('click', function() {
			var labelfield = $("coral-fileupload[name='./desktopPromotionalImage']").prev();
			var mandetoryChar = labelfield.text();
            if($("coral-switch[name='./playVidInModalDesktop']").prop('checked') === false) {
                $("coral-fileupload[name='./desktopPromotionalImage']").attr('data-cq-fileupload-required','');
				if (mandetoryChar.indexOf("*") <= 0){
					labelfield.text(mandetoryChar + "*");
				}
            }
            else {
                $("coral-fileupload[name='./desktopPromotionalImage']").removeAttr("data-cq-fileupload-required");
				if (mandetoryChar.indexOf("*") >= 0){
					labelfield.text(mandetoryChar.slice(0, -1));
				}
            }
		});
        $(".promoMediaType").find(".coral3-SelectList-item").bind('click', function() {
            var mType=$(this).text();
            if (mType == 'Image') {
                $("input[name='./desktopImageAltText']").parent().show();
                $("input[name='./desktopPromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
				$("coral-select[name='./desktopVideoType']").closest(".coral-Form-fieldwrapper").hide();
				$("foundation-autocomplete[name='./desktopVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
				$("coral-switch[name='./playVidInModalDesktop']").closest(".coral-Form-fieldwrapper").hide();
				$("coral-switch[name='./autoPlayVideoDesktop']").closest(".coral-Form-fieldwrapper").hide();
				$("coral-fileupload[name='./desktopPromotionalImage']").removeAttr("data-cq-fileupload-required");
            } else {
                $("input[name='./desktopImageAltText']").parent().hide();
				$("input[name='./desktopPromoImageUrl']").closest('.coral-Form-fieldwrapper').hide();
				$("coral-select[name='./desktopVideoType']").closest(".coral-Form-fieldwrapper").show();
				$("foundation-autocomplete[name='./desktopVideoUrl']").closest('.coral-Form-fieldwrapper').show();
				$("coral-switch[name='./playVidInModalDesktop']").closest(".coral-Form-fieldwrapper").show();
				$("coral-switch[name='./autoPlayVideoDesktop']").closest(".coral-Form-fieldwrapper").show();
				if($("coral-switch[name='./playVidInModalDesktop']").prop('checked') === true) {
                    $("coral-fileupload[name='./desktopPromotionalImage']").attr('data-cq-fileupload-required','');
                }
                else {
                    $("coral-fileupload[name='./desktopPromotionalImage']").removeAttr("data-cq-fileupload-required");
                }
            }
        })
		$(".promoVideoType").find(".coral3-SelectList-item").bind('click', function() {
			if($(this).text() == "Deluxe") {
				$("foundation-autocomplete[name='./desktopVideoUrl']").parent().children("label").text("Promo Deluxe Platform ID");
			} else {
				$("foundation-autocomplete[name='./desktopVideoUrl']").parent().children("label").text("Promo Video URL");
			}
		})
		if ($(".tabletPromoMediaType").find(".coral3-Select-label").text() == 'Image') {
            $("input[name='./tabletImageAltText']").parent().show();
			$("input[name='./tabletPromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
			$("coral-select[name='./tabletVideoType']").closest(".coral-Form-fieldwrapper").hide();
			$("foundation-autocomplete[name='./tabletVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
			$("coral-switch[name='./playVidInModalTablet']").closest(".coral-Form-fieldwrapper").hide();
			$("coral-switch[name='./autoPlayVideoTablet']").closest(".coral-Form-fieldwrapper").hide();
			$("coral-fileupload[name='./tabletPromotionalImage']").removeAttr("data-cq-fileupload-required");
        } else if ($(".tabletPromoMediaType").find(".coral3-Select-label").text() == 'Video') {
            tabletShowHide();
        }
        $("coral-switch[name='./playVidInModalTablet']").bind('click', function() {
			var labelfield = $("coral-fileupload[name='./tabletPromotionalImage']").prev();
			var mandetoryChar = labelfield.text();
			if($("coral-switch[name='./playVidInModalTablet']").prop('checked') === false) {
				$("coral-fileupload[name='./tabletPromotionalImage']").attr('data-cq-fileupload-required','');
				if (mandetoryChar.indexOf("*") <= 0){
					labelfield.text(mandetoryChar + "*");
				}
			}
			else {
				$("coral-fileupload[name='./tabletPromotionalImage']").removeAttr("data-cq-fileupload-required");
				if (mandetoryChar.indexOf("*") >= 0){
					labelfield.text(mandetoryChar.slice(0, -1));
				}
			}
		});
        $(".tabletPromoMediaType").find(".coral3-SelectList-item").bind('click', function() {
            var mType=$(this).text();
            if (mType == 'Image') {
                $("input[name='./tabletImageAltText']").parent().show();
                $("input[name='./tabletPromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
				$("coral-select[name='./tabletVideoType']").closest(".coral-Form-fieldwrapper").hide();
				$("foundation-autocomplete[name='./tabletVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
				$("coral-switch[name='./playVidInModalTablet']").closest(".coral-Form-fieldwrapper").hide();
				$("coral-switch[name='./autoPlayVideoTablet']").closest(".coral-Form-fieldwrapper").hide();
				$("coral-fileupload[name='./tabletPromotionalImage']").removeAttr("data-cq-fileupload-required");
            } else {
                $("input[name='./tabletImageAltText']").parent().hide();
                $("input[name='./tabletPromoImageUrl']").closest('.coral-Form-fieldwrapper').hide();
				$("coral-select[name='./tabletVideoType']").closest(".coral-Form-fieldwrapper").show();
				$("foundation-autocomplete[name='./tabletVideoUrl']").closest('.coral-Form-fieldwrapper').show();
				$("coral-switch[name='./playVidInModalTablet']").closest(".coral-Form-fieldwrapper").show();
				$("coral-switch[name='./autoPlayVideoTablet']").closest(".coral-Form-fieldwrapper").show();
				if($("coral-switch[name='./playVidInModalTablet']").prop('checked') === true) {
                    $("coral-fileupload[name='./tabletPromotionalImage']").attr('data-cq-fileupload-required','');
                }
                else {
                    $("coral-fileupload[name='./tabletPromotionalImage']").removeAttr("data-cq-fileupload-required");
                }
            }
        })
		$(".tabletPromoVideoType").find(".coral3-SelectList-item").bind('click', function() {
			if($(this).text() == "Deluxe") {
				$("foundation-autocomplete[name='./tabletVideoUrl']").parent().children("label").text("Promo Deluxe Platform ID");
			} else {
				$("foundation-autocomplete[name='./tabletVideoUrl']").parent().children("label").text("Promo Video URL");
			}
		})
		if ($(".mobilePromoMediaType").find(".coral3-Select-label").text() == 'Image') {
            $("input[name='./mobileImageAltText']").parent().show();
			$("input[name='./mobilePromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
			$("coral-select[name='./mobileVideoType']").closest(".coral-Form-fieldwrapper").hide();
			$("foundation-autocomplete[name='./mobileVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
			$("coral-switch[name='./playVidInModalMobile']").closest(".coral-Form-fieldwrapper").hide();
			$("coral-switch[name='./autoPlayVideoMobile']").closest(".coral-Form-fieldwrapper").hide();
			$("coral-fileupload[name='./mobilePromotionalImage']").removeAttr("data-cq-fileupload-required");
        } else if ($(".mobilePromoMediaType").find(".coral3-Select-label").text() == 'Video') {
            mobileShowHide();
        }
        $("coral-switch[name='./playVidInModalMobile']").bind('click', function() {
		var labelfield = $("coral-fileupload[name='./mobilePromotionalImage']").prev();
		var mandetoryChar = labelfield.text();
		if($("coral-switch[name='./playVidInModalMobile']").prop('checked') === false) {
            $("coral-fileupload[name='./mobilePromotionalImage']").attr('data-cq-fileupload-required','');
			if (mandetoryChar.indexOf("*") <= 0){
				labelfield.text(mandetoryChar + "*");
			}
        }
        else {
            $("coral-fileupload[name='./mobilePromotionalImage']").removeAttr("data-cq-fileupload-required");
			if (mandetoryChar.indexOf("*") >= 0){
				labelfield.text(mandetoryChar.slice(0, -1));
			}
        }
		});
        $(".mobilePromoMediaType").find(".coral3-SelectList-item").bind('click', function() {
            var mType=$(this).text();
            if (mType == 'Image') {
                $("input[name='./mobileImageAltText']").parent().show();
                $("input[name='./mobilePromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
				$("coral-select[name='./mobileVideoType']").closest(".coral-Form-fieldwrapper").hide();
				$("foundation-autocomplete[name='./mobileVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
				$("coral-switch[name='./playVidInModalMobile']").closest(".coral-Form-fieldwrapper").hide();
				$("coral-switch[name='./autoPlayVideoMobile']").closest(".coral-Form-fieldwrapper").hide();
				$("coral-fileupload[name='./mobilePromotionalImage']").removeAttr("data-cq-fileupload-required");
            } else {
                $("input[name='./mobileImageAltText']").parent().hide();
                $("input[name='./mobilePromoImageUrl']").closest('.coral-Form-fieldwrapper').hide();
				$("coral-select[name='./mobileVideoType']").closest(".coral-Form-fieldwrapper").show();
				$("foundation-autocomplete[name='./mobileVideoUrl']").closest('.coral-Form-fieldwrapper').show();
				$("coral-switch[name='./playVidInModalMobile']").closest(".coral-Form-fieldwrapper").show();
				$("coral-switch[name='./autoPlayVideoMobile']").closest(".coral-Form-fieldwrapper").show();
				if($("coral-switch[name='./playVidInModalMobile']").prop('checked') === true) {
                    $("coral-fileupload[name='./mobilePromotionalImage']").attr('data-cq-fileupload-required','');
                }
                else {
                    $("coral-fileupload[name='./mobilePromotionalImage']").removeAttr("data-cq-fileupload-required");
                }
            }
        })
		$(".mobilePromoVideoType").find(".coral3-SelectList-item").bind('click', function() {
			if($(this).text() == "Deluxe") {
				$("foundation-autocomplete[name='./mobileVideoUrl']").parent().children("label").text("Promo Deluxe Platform ID");
			} else {
				$("foundation-autocomplete[name='./mobileVideoUrl']").parent().children("label").text("Promo Video URL");
			}
		})
	}
});

function showhide(el) {
	if (el.find(".promoMediaType > .coral3-Select-label").text() == 'Image') {
		$("input[name='./desktopImageAltText']").parent().show();
		$("input[name='./desktopPromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
		$("coral-select[name='./desktopVideoType']").closest(".coral-Form-fieldwrapper").hide();
		$("foundation-autocomplete[name='./desktopVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
		$("coral-switch[name='./playVidInModalDesktop']").closest(".coral-Form-fieldwrapper").hide();
		$("coral-switch[name='./autoPlayVideoDesktop']").closest(".coral-Form-fieldwrapper").hide();
		$("coral-fileupload[name='./desktopPromotionalImage']").removeAttr("data-cq-fileupload-required");
	} else if (el.find(".promoMediaType> .coral3-Select-label").text() == 'Video') {
		desktopShowHide();
	}

	if (el.find(".tabletPromoMediaType > .coral3-Select-label").text() == 'Image') {
		$("input[name='./tabletImageAltText']").parent().show();
		$("input[name='./tabletPromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
		$("coral-select[name='./tabletVideoType']").closest(".coral-Form-fieldwrapper").hide();
		$("foundation-autocomplete[name='./tabletVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
		$("coral-switch[name='./playVidInModalTablet']").closest(".coral-Form-fieldwrapper").hide();
		$("coral-switch[name='./autoPlayVideoTablet']").closest(".coral-Form-fieldwrapper").hide();
		$("coral-fileupload[name='./tabletPromotionalImage']").removeAttr("data-cq-fileupload-required");
	} else if (el.find(".tabletPromoMediaType > .coral3-Select-label").text() == 'Video') {
		tabletShowHide();
	}

	if (el.find(".mobilePromoMediaType > .coral3-Select-label").text() == 'Image') {
		$("input[name='./mobileImageAltText']").parent().show();
		$("input[name='./mobilePromoImageUrl']").closest('.coral-Form-fieldwrapper').show();
		$("coral-select[name='./mobileVideoType']").closest(".coral-Form-fieldwrapper").hide();
		$("foundation-autocomplete[name='./mobileVideoUrl']").closest('.coral-Form-fieldwrapper').hide();
		$("coral-switch[name='./playVidInModalMobile']").closest(".coral-Form-fieldwrapper").hide();
		$("coral-switch[name='./autoPlayVideoMobile']").closest(".coral-Form-fieldwrapper").hide();
		$("coral-fileupload[name='./mobilePromotionalImage']").removeAttr("data-cq-fileupload-required");
	} else if (el.find(".mobilePromoMediaType > .coral3-Select-label").text() == 'Video') {
		mobileShowHide();
	}
}

function mobileShowHide(){
$("input[name='./mobileImageAltText']").parent().hide();
    $("input[name='./mobilePromoImageUrl']").closest('.coral-Form-fieldwrapper').hide();
    $("coral-select[name='./mobileVideoType']").closest(".coral-Form-fieldwrapper").show();
    $("foundation-autocomplete[name='./mobileVideoUrl']").closest('.coral-Form-fieldwrapper').show();
    $("coral-switch[name='./playVidInModalMobile']").closest(".coral-Form-fieldwrapper").show();
    $("coral-switch[name='./autoPlayVideoMobile']").closest(".coral-Form-fieldwrapper").show();
    var labelfield = $("coral-fileupload[name='./mobilePromotionalImage']").prev();
    var mandetoryChar = labelfield.text();
    if($("coral-switch[name='./playVidInModalMobile']").prop('checked') === true) {
    $("coral-fileupload[name='./mobilePromotionalImage']").attr('data-cq-fileupload-required','');
        if (mandetoryChar.indexOf("*") <= 0){
            labelfield.text(mandetoryChar + "*");
        }
    }
    else {
        $("coral-fileupload[name='./mobilePromotionalImage']").removeAttr("data-cq-fileupload-required");
        if (mandetoryChar.indexOf("*") >= 0){
            labelfield.text(mandetoryChar.slice(0, -1));
        }
    }
    if($(".mobilePromoVideoType").find(".coral3-Select-label").text() == 'Deluxe') {
        $("foundation-autocomplete[name='./mobileVideoUrl']").parent().children("label").text("Promo Deluxe Platform ID");
    } else {
        $("foundation-autocomplete[name='./mobileVideoUrl']").parent().children("label").text("Promo Video URL");
    }
}

function tabletShowHide(){
$("input[name='./tabletImageAltText']").parent().hide();
    $("input[name='./tabletPromoImageUrl']").closest('.coral-Form-fieldwrapper').hide();
    $("coral-select[name='./tabletVideoType']").closest(".coral-Form-fieldwrapper").show();
    $("foundation-autocomplete[name='./tabletVideoUrl']").closest('.coral-Form-fieldwrapper').show();
    $("coral-switch[name='./playVidInModalTablet']").closest(".coral-Form-fieldwrapper").show();
    $("coral-switch[name='./autoPlayVideoTablet']").closest(".coral-Form-fieldwrapper").show();

    var labelfield = $("coral-fileupload[name='./tabletPromotionalImage']").prev();
    var mandetoryChar = labelfield.text();
    if($("coral-switch[name='./playVidInModalTablet']").prop('checked') === true) {
        $("coral-fileupload[name='./tabletPromotionalImage']").attr('data-cq-fileupload-required','');
        if (mandetoryChar.indexOf("*") <= 0){
            labelfield.text(mandetoryChar + "*");
        }
    }
    else {
        $("coral-fileupload[name='./tabletPromotionalImage']").removeAttr("data-cq-fileupload-required");
        if (mandetoryChar.indexOf("*") >= 0){
            labelfield.text(mandetoryChar.slice(0, -1));
        }
    }

    if($(".tabletPromoVideoType").find(".coral3-Select-label").text() == 'Deluxe') {
        $("foundation-autocomplete[name='./tabletVideoUrl']").parent().children("label").text("Promo Deluxe Platform ID");
    } else {
        $("foundation-autocomplete[name='./tabletVideoUrl']").parent().children("label").text("Promo Video URL");
    }
}

function desktopShowHide(){
$("input[name='./desktopImageAltText']").parent().hide();
    $("input[name='./desktopPromoImageUrl']").closest('.coral-Form-fieldwrapper').hide();
    $("coral-select[name='./desktopVideoType']").closest(".coral-Form-fieldwrapper").show();
    $("foundation-autocomplete[name='./desktopVideoUrl']").closest('.coral-Form-fieldwrapper').show();
    $("coral-switch[name='./playVidInModalDesktop']").closest(".coral-Form-fieldwrapper").show();
    $("coral-switch[name='./autoPlayVideoDesktop']").closest(".coral-Form-fieldwrapper").show();

    var labelfield = $("coral-fileupload[name='./desktopPromotionalImage']").prev();
    var mandetoryChar = labelfield.text();
    if($("coral-switch[name='./playVidInModalDesktop']").prop('checked') === true) {
        $("coral-fileupload[name='./desktopPromotionalImage']").attr('data-cq-fileupload-required','');
        if (mandetoryChar.indexOf("*") <= 0){
            labelfield.text(mandetoryChar + "*");
        }
    }
    else {
        $("coral-fileupload[name='./desktopPromotionalImage']").removeAttr("data-cq-fileupload-required");
        if (mandetoryChar.indexOf("*") >= 0){
            labelfield.text(mandetoryChar.slice(0, -1));
        }
    }
    if($(".promoVideoType").find(".coral3-Select-label").text() == 'Deluxe') {
        $("foundation-autocomplete[name='./desktopVideoUrl']").parent().children("label").text("Promo Deluxe Platform ID");
    } else {
        $("foundation-autocomplete[name='./desktopVideoUrl']").parent().children("label").text("Promo Video URL");
    }
}
})($, $(document));