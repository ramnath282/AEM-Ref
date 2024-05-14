(function(document, $) {
	"use strict";
    $(document).on("foundation-contentloaded", function(e) {
		switchShowHideHandler($(".switch-showhide-mobile-settings", e.target));
	});

	$(document).on("change", ".switch-showhide-mobile-settings", function(e) {
		switchShowHideHandler($(this));
	});

	function switchShowHideHandler(el) {
		el.each(function(i, element) {
			if ($(element).is("coral-switch")) {
				Coral.commons.ready(element, function(component) {
					showHide(component, element);
					component.on("change", function() {
                        var switchField = $("coral-switch[name='" + component.name + "']");
						showHide(switchField, switchField);
					});
				});
			} else {
				var component = $(element).data("checkbox");
				if (component) {
					showHide(component, element);
				}
			}
		});
	}


	function showHide(component, element) {
		var target = $(element).data("switchshowhidetarget");
		var $target;
		if ($(target).parent(".coral-Form-fieldwrapper").length) {
			$target = $(target).parents(".coral-Form-fieldwrapper");
			$target.hide();
			if (component.checked) {
				$target.show();
			}
		}
		else if ($(target).parents(".coral3-Panel").length) {
			$target = $(target);
			$target.each(function() {
				var id = $(this).parents(':eq(1)').attr("aria-labelledby");
				var $tabTarget = $("#" + id);
				$tabTarget.hide();
				$(this).hide();
				if (component.checked) {
					$(this).show();
					$tabTarget.show();
				}
			});
		}
	}

})(document, Granite.$);
