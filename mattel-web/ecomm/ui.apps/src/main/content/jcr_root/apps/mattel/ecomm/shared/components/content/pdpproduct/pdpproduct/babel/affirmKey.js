var _affirm_config = {
       public_api_key: $('.affirm_public_key').text().trim(),
       script: $('.affirm_url').text().trim()
};
(function(l, g, m, e, a, f, b) {
    var d, c = l[m] || {},
        h = document.createElement(f),
        n = document.getElementsByTagName(f)[0],
        k = function(a, b, c) {
            return function() {
                a[b]._.push([c, arguments])
            }
        };
    c[e] = k(c, e, "set");
    d = c[e];
    c[a] = {};
    c[a]._ = [];
    d._ = [];
    c[a][b] = k(c, a, b);
    a = 0;
    for (b = "set add save post open empty reset on off trigger ready setProduct".split(" "); a < b.length; a++) d[b[a]] = k(c, e, b[a]);
    a = 0;
    for (b = ["get", "token", "url", "items"]; a < b.length; a++) d[b[a]] = function() {};
    h.async = !0;
    h.src = g[f];
    n.parentNode.insertBefore(h, n);
    delete g[f];
    d(g);
    l[m] = c
})(window, _affirm_config, "affirm", "checkout", "ui", "script", "ready");
