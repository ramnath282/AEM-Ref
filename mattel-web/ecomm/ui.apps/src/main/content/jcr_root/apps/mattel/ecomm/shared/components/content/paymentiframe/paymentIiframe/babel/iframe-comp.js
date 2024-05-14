const iframe = $(".iframe-outerWrapper iframe");
iframe.on('load', function() {
    let iframeHeight = iframe[0].ownerDocument.body.scrollHeight;
    iframeHeight = iframeHeight+100 + "px"
    iframe.height(iframeHeight);
});