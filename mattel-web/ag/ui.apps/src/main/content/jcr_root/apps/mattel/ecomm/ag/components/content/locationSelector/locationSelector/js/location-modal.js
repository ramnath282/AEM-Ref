/**
 * locationModal.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var locationModal = {
        el: '#selectLocationModal',
        initializeModal: function () {
            $('#first-link').focus(function () {
                $("a.select-location:last").focus();
            });
            $('#last-link').focus(function () {
                $(".close").focus();
            });
            $('button.theme-btn-primary').click(function () {
                setTimeout(function () {
                    $(".close").focus();
                }, 100);
            });
            $('button.location-dropdown').click(function () {
                setTimeout(function () {
                    $(".close").focus();
                }, 100);
            });
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.locationModal) return;
            this.initializeModal();
        }
    }
    locationModal.init();
    AGAEM.locationModal = locationModal;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            locationModal.init();
        }
    }, false);
}(window, AGAEM));