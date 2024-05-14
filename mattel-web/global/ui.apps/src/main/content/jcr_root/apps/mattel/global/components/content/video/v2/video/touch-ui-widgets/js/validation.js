(function(a, b) {
    b.on("dialog-ready", function() {
        0 < a(".video-dialog").length && (setTimeout(function() {
            "ooyala" == a(".video-selection").val() ? (a(".youtube-options-section").hide(), a(".ooyala-options-section").show(),$(".video-dialog coral-tab")[1].show()) : (a(".youtube-options-section").show(), a(".ooyala-options-section").hide(), $(".video-dialog coral-tab")[1].hide())
        }, 300), a(".video-selection").change(function() {
            "ooyala" == a(".video-selection").val() ? (a(".youtube-options-section").hide(), a(".ooyala-options-section").show(),$(".video-dialog coral-tab")[1].show()) : (a(".youtube-options-section").show(), a(".ooyala-options-section").hide(), $(".video-dialog coral-tab")[1].hide())
        }))
    })
})($,
    $(document));