/**
 * interstitial.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    var interstitial = {
        el: '#interstitialModal,#interstitialRetailerModal,#interstitialGameRetailerModal',
        bindingEventsConfig: function () {
            var events = {
                "click .continue-btn , .go-back-btn , .interstitial-load": "interstitialModalClose",

            }
            return events;
        },
        initializeModal: function () {
            $('#first-link,#interstitial-first-link').focus(function () {
                $(".modal-body button:last").focus();
            });
            $('#last-link').focus(function () {
                $(".close").focus();
            });
            $('#interstitialModal').on('shown.bs.modal', function (e) {
                var relatedTarget = $(e.relatedTarget).data('url');
                $('.continue-btn').attr('href', relatedTarget);


                setTimeout(function () {
                    $(".close").focus();
                }, 100);
            });

        },
        interstitialModalClose: function () {
            $("#interstitialModal, #interstitialRetailerModal , #interstitialGameRetailerModal").modal("hide");
        },
        init: function () {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.initializeModal();
        }
    }
    PLAYAEM.interstitial = interstitial;
    document.addEventListener('DOMContentLoaded', function () {
        interstitial.init();
    }, false);
}(window, PLAYAEM));