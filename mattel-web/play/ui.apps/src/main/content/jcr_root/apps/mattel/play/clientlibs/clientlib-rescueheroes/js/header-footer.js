
function createCookie(e,t,n){if(n){var o=new Date;o.setTime(o.getTime()+24*n*60*60*1e3);var i="; expires="+o.toGMTString()}else i="";document.cookie=e+"="+t+i+"; path=/"}function readCookie(e){for(var t=e+"=",n=document.cookie.split(";"),o=0;o<n.length;o++){for(var i=n[o];" "==i.charAt(0);)i=i.substring(1,i.length);if(0==i.indexOf(t))return i.substring(t.length,i.length)}return null}function eraseCookie(e){createCookie(e,"",-1)}

var device,locale = $("#locale").val();

function reposition() {
    var e = $(this),
        t = e.find(".modal-dialog");
    e.css("display", "block"), t.css("margin-top", Math.max(0, ($(window).height() - t.height()) / 2))
}

function deviceType() {
    Modernizr.mq("all and (max-width: 640px)") ? (device = "mobile", window.fp.device = "mobile") : Modernizr.mq("all and (max-width: 1023px) and (min-width: 641px)") ? (device = "tablet", window.fp.device = "tablet") : Modernizr.mq("all and (min-width: 1024px)") && (device = "desktop", window.fp.device = "desktop")
}

function footerAccordion() {
    $(".footer-tiles .accordion-toggle h5").on("click", function() {
        event.preventDefault(), $(this).toggleClass("shown")
    })
}

function searchPage(e) {
    "/en_us" !== localInfo.toLowerCase() ? window.location.href = localInfo + "/search/index.html?Ntt=" + e : window.location.href = window.fp.oneStore.searchPageURL + e
}
if (function() {
        var t;
        (window.fp = window.fp || {}, window.fp.playerModalOpened = !1, window.fp.isModalOpened = !1,  $(".search-box .close-btn").on("click", function() {
            $("input.input-search").val("")
        }), $("#main-top-header .search-icon, .go-btn").on("click", function(e) {
            "desktop" === window.fp.device && "" != $("#main-top-header .input-search.tt-input").val() || $("#searchBox .search-box").hasClass("active") && "" != $("#main-top-header .input-search.tt-input").val() ? searchPage($("#main-top-header .input-search.tt-input").val()) : (e.stopPropagation(), $("#searchBox .search-box").toggleClass("active"), $("#searchBox .search-box").hasClass("active") && $(".main-navigation").hasClass("mmactive") && $(".navbar-header a").trigger("click", ["search"]), $("#main-top-header .input-search.tt-input").focus())
        }), Modernizr.mq("all and (max-width: 640px)")) ? ((t = $("footer .accordion-toggle")).attr("data-toggle", "collapse"), t.each(function(e) {
            t.eq(e).attr("href", "#" + t.eq(e).next().attr("id"))
        }),  $(".hide-wrapper").on("click", function() {
            $(this).parent().hide(), $(this).parent().prev().children().find(".tile-container .tiles").hide(), $(this).parent().prev().children().find(".tile-container:last-child").hide(), $(this).parent().prev().children().find(".tile-container .title-wrapper").removeClass("expand")
        })) : ((t = $("footer .accordion-toggle")).attr("href", "javascript:void();"), t.removeAttr("data-toggle"), t.next().css("height", "auto"));
         if(window.fp && window.fp.oneStore && window.fp.oneStore.welcomeURL) {
            var o, n, i;
            $(function() {
                var e, a;
                $(".cart").length && ".user".length && (o = $("#user-template").html(), n = $("#cart-template").html(), i = $("#main-top-header"), e = window.fp.oneStore.welcomeURL, a = function(e) {
                    ! function(e, t) {
                        if (t.data("cartInfo", e), r(e, "loggedIn")[0] && "true" === r(e, "loggedIn")[0]) {
                            window.fp.oneStore.SignoutMsg += r(e, "welcomeMsg")[0];
                            var a = o.format(window.fp.oneStore.SignoutMsg)
                        } else var a = o.format(window.fp.oneStore.welcomeMsg);
                        if (t.find(".user-info").append(a), r(e, "cartCounter")[0]) var i = n.format(r(e, "cartCounter")[0].split(";")[0]);
                        else var i = n.format("0");
                        t.find(".shop-icon").append(i)
                    }(e, i)
                }, $.ajax({
                    cache: !1,
                    url: e,
                    type: "GET",
                    xhrFields: {
                        withCredentials: !0
                    },
                    error: function(e, t) {
                        null != a && a({})
                    },
                    success: function(e) {
                        null != a && e && (a(e), e)
                    }
                }))
            })
        }
        String.prototype.format = function() {
            var a = arguments;
            return this.replace(/{(\d+)}/g, function(e, t) {
                return a[t] !== null ? a[t] : e
            })
        };
        var r = function(e, t) {
            var a = [];
            for (var i in e) e.hasOwnProperty(i) && ("object" == typeof e[i] ? a = a.concat(r(e[i], t)) : i == t && a.push(e[i]));
            return a
        };
        $(".igg-email .igg-email-submit").on("click", function(e) {
            e.preventDefault();
            var t, a, i = $(".igg-enter-email").val();
            "" != i && /^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\.){1,2}[A-Za-z]{2,4}$/.test(i) ? (t = "/en_us/Registration/IndigogoEmailRegistration", a = i, $.ajax({
                cache: !1,
                url: t,
                type: "POST",
                data: {
                    emailadr: a
                },
                error: function(e) {},
                success: function(e) {
                    -1 == e ? ($("#Email-modal .modal-body .email-modal-text1").html("Thanks for signing up!"), $("#Email-modal .modal-body .email-modal-text2").html("You'll be hearing from us soon."), $("#Email-modal").modal("show")) : 0 == e && ($("#Email-modal .modal-body .email-modal-text1").html("This Email address is already registered!"), $("#Email-modal .modal-body .email-modal-text2").html("Please try again with different email address."), $("#Email-modal").modal("show"))
                }
            })) : ($("#Email-modal .modal-body .email-modal-text1").html("Oops! Something&rsquo;s wrong."), $("#Email-modal .modal-body .email-modal-text2").html("Please check your email address and try again."), $("#Email-modal").modal("show"))
        }), $('.footer-tile-section a[href^="/shop"]').each(function() {
            $(this).attr("href", "http://fisher-price.mattel.com" + $(this).attr("href"))
        }), 1 <= $(".igo_boxbody").length && $(".igo_content .igo_content_content_author").each(function(e, t) {
            "" == $(t).find(".igo_content_content_author_value").text() && $(t).addClass("hide")
        })
    }(), $(document).ready(function() {
         deviceType(); footerAccordion();
    }), $(window).resize(function() {
        var t;
        (deviceType(), footerAccordion(), Modernizr.mq("all and (max-width: 640px)")) ? ((t = $("footer .accordion-toggle")).attr("data-toggle", "collapse"), t.each(function(e) {
            t.eq(e).attr("href", "#" + t.eq(e).next().attr("id"))
        }), $(".hide-wrapper").on("click", function() {
            $(this).parent().hide(), $(this).parent().prev().children().find(".tile-container .tiles").hide(), $(this).parent().prev().children().find(".tile-container:last-child").hide(), $(this).parent().prev().children().find(".tile-container .title-wrapper").removeClass("expand")
        })) : ((t = $("footer .accordion-toggle")).attr("href", "javascript:void();"), t.removeAttr("data-toggle"), t.next().css("height", "auto"));
       
    }), $.fn.equalize = function() {
        var e = 0;
        this.each(function() {
            $(this).height() > e && (e = $(this).height())
        }), this.height(e)
    }, $.fn.setheight = function() {
        this.height($(this).find("img").height())
    }, $.fn.setminheight = function() {
        var e = 0,
            t = 0;
        this.each(function() {
            $(this).height() > e && (e = $(this).height())
        }), $(this).css("min-height", e + 72), this.find(".row").each(function() {
            $(this).height() > t && (t = $(this).height())
        }), $(this).find(".row").css("min-height", t)
    }, "/en_us" == localInfo.toLowerCase() && 0 < $("#main-top-header .input-search,.search-result-wrapper .input-search").length && $("#main-top-header .input-search,.search-result-wrapper .input-search").typeahead({
        hint: !0,
        highlight: !0,
        minLength: 2
    }, {
        name: "autoComplete",
        displayKey: "q",
        display: "dq",
        source: function(e, t, a) {
            $.get(localInfo + "/Search/GetSearchSuggestions", {
                q: e
            }, function(e) {
                (e = JSON.parse(e)).response && e.response.suggestions && a(e.response.suggestions)
            })
        },
        templates: {
            suggestion: function(e) {
                return "<div onclick = \"searchPage('" + e.q + "')\">" + e.dq + "</div>"
            }
        }
    }), $("#main-top-header .input-search,.search-result-wrapper .input-search").on("keyup keypress", function(e) {
        if (13 == e.which) {
            var t = $(this).val();
            "/en_us" !== localInfo.toLowerCase() ? window.location.href = localInfo + "/search/index.html?Ntt=" + t : window.location.href = window.fp.oneStore.searchPageURL + t
        }
    }), "/en_us" == localInfo.toLowerCase()) {
    function isEmpty(e) {
        return !$.trim(e)
    }

    function isValidEmailAddress(e) {
        return /^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\.){1,2}[A-Za-z]{2,4}$/.test(e)
    }

    function GetStarted(e) {
        var t = "",
            a = !0,
            i = "#emailError";
        "true" == e ? (t = $("#emailMobile").val(), i += "Mobile") : t = $("#emailDesktop").val(), isEmpty(t) ? ("true" == e && $("#email-pop-up").modal("hide"), a = !1, $(i).text("Please enter your Email Address")) : 0 == isValidEmailAddress(t) && ("true" == e && $("#email-pop-up").modal("hide"), a = !1, $(i).text("Please enter a valid Email Address")), a ? client.renderSignup(t) : ($(i).removeClass("hidden"), $(i).closest(".form-group").removeClass("has-error").addClass("has-error"))
    }
}
