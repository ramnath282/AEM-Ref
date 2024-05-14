const interstitialmodal = {
    el: "#interstitialModal,#interstitialRetailerModal,#interstitialGameRetailerModal",
    bindingEventsConfig() {
        const events = {
            "click .continue-btn , .go-back-btn , .interstitial-load": "interstitialModalClose"
        };
        return events
    },
    initializeModal() {
        $("#first-link,#interstitial-first-link").focus(() => {
            $(".modal-body button:last").focus()
        });
        $("#last-link").focus(() => {
            $(".close").focus()
        });
        $("#interstitialModal").on("shown.bs.modal", f => {
            const d = $(f.relatedTarget).data("url");
            $(".continue-btn").attr("href", d);
        })
    },
    interstitialModalClose() {
        $("#interstitialModal, #interstitialRetailerModal , #interstitialGameRetailerModal").modal("hide")
    },
    init() {
        if (!$(this.el).length) {
            return
        }
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.initializeModal()
    }
};
const evtBinding = window.global.eventBindingInst;
interstitialmodal.init();
window.global.interstitialmodal = interstitialmodal;
document.addEventListener('DOMContentLoaded', () => {
    interstitialmodal.init();
}, false)