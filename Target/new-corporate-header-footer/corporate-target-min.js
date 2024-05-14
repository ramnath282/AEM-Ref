/*last-update-13th-may-2020*/
var jquery_new_version;
function camelize(a) {
    return a.replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, function(c, b) {
        if (+c === 0) {
            return ""
        }
        return b == 0 ? c.toLowerCase() : c.toUpperCase()
    })
}
function loadAEMFiles() {
    /*Plugin1 ! jQuery v3.3.1 | (c) JS Foundation and other contributors | jquery.org/license */
    ! function(e, t) {
        "use strict";
        "object" == typeof module && "object" == typeof module.exports ? module.exports = e.document ? t(e, !0) : function(e) {
            if (!e.document) throw new Error("jQuery requires a window with a document");
            return t(e)
        } : t(e)
    }("undefined" != typeof window ? window : this, function(e, t) {
        "use strict";
        var n = [],
            r = e.document,
            i = Object.getPrototypeOf,
            o = n.slice,
            a = n.concat,
            s = n.push,
            u = n.indexOf,
            l = {},
            c = l.toString,
            f = l.hasOwnProperty,
            p = f.toString,
            d = p.call(Object),
            h = {},
            g = function e(t) {
                return "function" == typeof t && "number" != typeof t.nodeType
            },
            y = function e(t) {
                return null != t && t === t.window
            },
            v = {
                type: !0,
                src: !0,
                noModule: !0
            };

        function m(e, t, n) {
            var i, o = (t = t || r).createElement("script");
            if (o.text = e, n)
                for (i in v) n[i] && (o[i] = n[i]);
            t.head.appendChild(o).parentNode.removeChild(o)
        }

        function x(e) {
            return null == e ? e + "" : "object" == typeof e || "function" == typeof e ? l[c.call(e)] || "object" : typeof e
        }
        var b = "3.3.1",
            w = function(e, t) {
                return new w.fn.init(e, t)
            },
            T = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
        w.fn = w.prototype = {
            jquery: "3.3.1",
            constructor: w,
            length: 0,
            toArray: function() {
                return o.call(this)
            },
            get: function(e) {
                return null == e ? o.call(this) : e < 0 ? this[e + this.length] : this[e]
            },
            pushStack: function(e) {
                var t = w.merge(this.constructor(), e);
                return t.prevObject = this, t
            },
            each: function(e) {
                return w.each(this, e)
            },
            map: function(e) {
                return this.pushStack(w.map(this, function(t, n) {
                    return e.call(t, n, t)
                }))
            },
            slice: function() {
                return this.pushStack(o.apply(this, arguments))
            },
            first: function() {
                return this.eq(0)
            },
            last: function() {
                return this.eq(-1)
            },
            eq: function(e) {
                var t = this.length,
                    n = +e + (e < 0 ? t : 0);
                return this.pushStack(n >= 0 && n < t ? [this[n]] : [])
            },
            end: function() {
                return this.prevObject || this.constructor()
            },
            push: s,
            sort: n.sort,
            splice: n.splice
        }, w.extend = w.fn.extend = function() {
            var e, t, n, r, i, o, a = arguments[0] || {},
                s = 1,
                u = arguments.length,
                l = !1;
            for ("boolean" == typeof a && (l = a, a = arguments[s] || {}, s++), "object" == typeof a || g(a) || (a = {}), s === u && (a = this, s--); s < u; s++)
                if (null != (e = arguments[s]))
                    for (t in e) n = a[t], a !== (r = e[t]) && (l && r && (w.isPlainObject(r) || (i = Array.isArray(r))) ? (i ? (i = !1, o = n && Array.isArray(n) ? n : []) : o = n && w.isPlainObject(n) ? n : {}, a[t] = w.extend(l, o, r)) : void 0 !== r && (a[t] = r));
            return a
        }, w.extend({
            expando: "jQuery" + ("3.3.1" + Math.random()).replace(/\D/g, ""),
            isReady: !0,
            error: function(e) {
                throw new Error(e)
            },
            noop: function() {},
            isPlainObject: function(e) {
                var t, n;
                return !(!e || "[object Object]" !== c.call(e)) && (!(t = i(e)) || "function" == typeof(n = f.call(t, "constructor") && t.constructor) && p.call(n) === d)
            },
            isEmptyObject: function(e) {
                var t;
                for (t in e) return !1;
                return !0
            },
            globalEval: function(e) {
                m(e)
            },
            each: function(e, t) {
                var n, r = 0;
                if (C(e)) {
                    for (n = e.length; r < n; r++)
                        if (!1 === t.call(e[r], r, e[r])) break
                } else
                    for (r in e)
                        if (!1 === t.call(e[r], r, e[r])) break;
                return e
            },
            trim: function(e) {
                return null == e ? "" : (e + "").replace(T, "")
            },
            makeArray: function(e, t) {
                var n = t || [];
                return null != e && (C(Object(e)) ? w.merge(n, "string" == typeof e ? [e] : e) : s.call(n, e)), n
            },
            inArray: function(e, t, n) {
                return null == t ? -1 : u.call(t, e, n)
            },
            merge: function(e, t) {
                for (var n = +t.length, r = 0, i = e.length; r < n; r++) e[i++] = t[r];
                return e.length = i, e
            },
            grep: function(e, t, n) {
                for (var r, i = [], o = 0, a = e.length, s = !n; o < a; o++)(r = !t(e[o], o)) !== s && i.push(e[o]);
                return i
            },
            map: function(e, t, n) {
                var r, i, o = 0,
                    s = [];
                if (C(e))
                    for (r = e.length; o < r; o++) null != (i = t(e[o], o, n)) && s.push(i);
                else
                    for (o in e) null != (i = t(e[o], o, n)) && s.push(i);
                return a.apply([], s)
            },
            guid: 1,
            support: h
        }), "function" == typeof Symbol && (w.fn[Symbol.iterator] = n[Symbol.iterator]), w.each("Boolean Number String Function Array Date RegExp Object Error Symbol".split(" "), function(e, t) {
            l["[object " + t + "]"] = t.toLowerCase()
        });

        function C(e) {
            var t = !!e && "length" in e && e.length,
                n = x(e);
            return !g(e) && !y(e) && ("array" === n || 0 === t || "number" == typeof t && t > 0 && t - 1 in e)
        }
        var E = function(e) {
            var t, n, r, i, o, a, s, u, l, c, f, p, d, h, g, y, v, m, x, b = "sizzle" + 1 * new Date,
                w = e.document,
                T = 0,
                C = 0,
                E = ae(),
                k = ae(),
                S = ae(),
                D = function(e, t) {
                    return e === t && (f = !0), 0
                },
                N = {}.hasOwnProperty,
                A = [],
                j = A.pop,
                q = A.push,
                L = A.push,
                H = A.slice,
                O = function(e, t) {
                    for (var n = 0, r = e.length; n < r; n++)
                        if (e[n] === t) return n;
                    return -1
                },
                P = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
                M = "[\\x20\\t\\r\\n\\f]",
                R = "(?:\\\\.|[\\w-]|[^\0-\\xa0])+",
                I = "\\[" + M + "*(" + R + ")(?:" + M + "*([*^$|!~]?=)" + M + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + R + "))|)" + M + "*\\]",
                W = ":(" + R + ")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|" + I + ")*)|.*)\\)|)",
                $ = new RegExp(M + "+", "g"),
                B = new RegExp("^" + M + "+|((?:^|[^\\\\])(?:\\\\.)*)" + M + "+$", "g"),
                F = new RegExp("^" + M + "*," + M + "*"),
                _ = new RegExp("^" + M + "*([>+~]|" + M + ")" + M + "*"),
                z = new RegExp("=" + M + "*([^\\]'\"]*?)" + M + "*\\]", "g"),
                X = new RegExp(W),
                U = new RegExp("^" + R + "$"),
                V = {
                    ID: new RegExp("^#(" + R + ")"),
                    CLASS: new RegExp("^\\.(" + R + ")"),
                    TAG: new RegExp("^(" + R + "|[*])"),
                    ATTR: new RegExp("^" + I),
                    PSEUDO: new RegExp("^" + W),
                    CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + M + "*(even|odd|(([+-]|)(\\d*)n|)" + M + "*(?:([+-]|)" + M + "*(\\d+)|))" + M + "*\\)|)", "i"),
                    bool: new RegExp("^(?:" + P + ")$", "i"),
                    needsContext: new RegExp("^" + M + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + M + "*((?:-\\d)?\\d*)" + M + "*\\)|)(?=[^-]|$)", "i")
                },
                G = /^(?:input|select|textarea|button)$/i,
                Y = /^h\d$/i,
                Q = /^[^{]+\{\s*\[native \w/,
                J = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,
                K = /[+~]/,
                Z = new RegExp("\\\\([\\da-f]{1,6}" + M + "?|(" + M + ")|.)", "ig"),
                ee = function(e, t, n) {
                    var r = "0x" + t - 65536;
                    return r !== r || n ? t : r < 0 ? String.fromCharCode(r + 65536) : String.fromCharCode(r >> 10 | 55296, 1023 & r | 56320)
                },
                te = /([\0-\x1f\x7f]|^-?\d)|^-$|[^\0-\x1f\x7f-\uFFFF\w-]/g,
                ne = function(e, t) {
                    return t ? "\0" === e ? "\ufffd" : e.slice(0, -1) + "\\" + e.charCodeAt(e.length - 1).toString(16) + " " : "\\" + e
                },
                re = function() {
                    p()
                },
                ie = me(function(e) {
                    return !0 === e.disabled && ("form" in e || "label" in e)
                }, {
                    dir: "parentNode",
                    next: "legend"
                });
            try {
                L.apply(A = H.call(w.childNodes), w.childNodes), A[w.childNodes.length].nodeType
            } catch (e) {
                L = {
                    apply: A.length ? function(e, t) {
                        q.apply(e, H.call(t))
                    } : function(e, t) {
                        var n = e.length,
                            r = 0;
                        while (e[n++] = t[r++]);
                        e.length = n - 1
                    }
                }
            }

            function oe(e, t, r, i) {
                var o, s, l, c, f, h, v, m = t && t.ownerDocument,
                    T = t ? t.nodeType : 9;
                if (r = r || [], "string" != typeof e || !e || 1 !== T && 9 !== T && 11 !== T) return r;
                if (!i && ((t ? t.ownerDocument || t : w) !== d && p(t), t = t || d, g)) {
                    if (11 !== T && (f = J.exec(e)))
                        if (o = f[1]) {
                            if (9 === T) {
                                if (!(l = t.getElementById(o))) return r;
                                if (l.id === o) return r.push(l), r
                            } else if (m && (l = m.getElementById(o)) && x(t, l) && l.id === o) return r.push(l), r
                        } else {
                            if (f[2]) return L.apply(r, t.getElementsByTagName(e)), r;
                            if ((o = f[3]) && n.getElementsByClassName && t.getElementsByClassName) return L.apply(r, t.getElementsByClassName(o)), r
                        }
                    if (n.qsa && !S[e + " "] && (!y || !y.test(e))) {
                        if (1 !== T) m = t, v = e;
                        else if ("object" !== t.nodeName.toLowerCase()) {
                            (c = t.getAttribute("id")) ? c = c.replace(te, ne): t.setAttribute("id", c = b), s = (h = a(e)).length;
                            while (s--) h[s] = "#" + c + " " + ve(h[s]);
                            v = h.join(","), m = K.test(e) && ge(t.parentNode) || t
                        }
                        if (v) try {
                            return L.apply(r, m.querySelectorAll(v)), r
                        } catch (e) {} finally {
                            c === b && t.removeAttribute("id")
                        }
                    }
                }
                return u(e.replace(B, "$1"), t, r, i)
            }

            function ae() {
                var e = [];

                function t(n, i) {
                    return e.push(n + " ") > r.cacheLength && delete t[e.shift()], t[n + " "] = i
                }
                return t
            }

            function se(e) {
                return e[b] = !0, e
            }

            function ue(e) {
                var t = d.createElement("fieldset");
                try {
                    return !!e(t)
                } catch (e) {
                    return !1
                } finally {
                    t.parentNode && t.parentNode.removeChild(t), t = null
                }
            }

            function le(e, t) {
                var n = e.split("|"),
                    i = n.length;
                while (i--) r.attrHandle[n[i]] = t
            }

            function ce(e, t) {
                var n = t && e,
                    r = n && 1 === e.nodeType && 1 === t.nodeType && e.sourceIndex - t.sourceIndex;
                if (r) return r;
                if (n)
                    while (n = n.nextSibling)
                        if (n === t) return -1;
                return e ? 1 : -1
            }

            function fe(e) {
                return function(t) {
                    return "input" === t.nodeName.toLowerCase() && t.type === e
                }
            }

            function pe(e) {
                return function(t) {
                    var n = t.nodeName.toLowerCase();
                    return ("input" === n || "button" === n) && t.type === e
                }
            }

            function de(e) {
                return function(t) {
                    return "form" in t ? t.parentNode && !1 === t.disabled ? "label" in t ? "label" in t.parentNode ? t.parentNode.disabled === e : t.disabled === e : t.isDisabled === e || t.isDisabled !== !e && ie(t) === e : t.disabled === e : "label" in t && t.disabled === e
                }
            }

            function he(e) {
                return se(function(t) {
                    return t = +t, se(function(n, r) {
                        var i, o = e([], n.length, t),
                            a = o.length;
                        while (a--) n[i = o[a]] && (n[i] = !(r[i] = n[i]))
                    })
                })
            }

            function ge(e) {
                return e && "undefined" != typeof e.getElementsByTagName && e
            }
            n = oe.support = {}, o = oe.isXML = function(e) {
               var t = e && (e.ownerDocument || e).documentElement;
                return !!t && "HTML" !== t.nodeName
            }, p = oe.setDocument = function(e) {
                var t, i, a = e ? e.ownerDocument || e : w;
                return a !== d && 9 === a.nodeType && a.documentElement ? (d = a, h = d.documentElement, g = !o(d), w !== d && (i = d.defaultView) && i.top !== i && (i.addEventListener ? i.addEventListener("unload", re, !1) : i.attachEvent && i.attachEvent("onunload", re)), n.attributes = ue(function(e) {
                    return e.className = "i", !e.getAttribute("className")
                }), n.getElementsByTagName = ue(function(e) {
                    return e.appendChild(d.createComment("")), !e.getElementsByTagName("*").length
                }), n.getElementsByClassName = Q.test(d.getElementsByClassName), n.getById = ue(function(e) {
                    return h.appendChild(e).id = b, !d.getElementsByName || !d.getElementsByName(b).length
                }), n.getById ? (r.filter.ID = function(e) {
                    var t = e.replace(Z, ee);
                    return function(e) {
                        return e.getAttribute("id") === t
                    }
                }, r.find.ID = function(e, t) {
                    if ("undefined" != typeof t.getElementById && g) {
                        var n = t.getElementById(e);
                        return n ? [n] : []
                    }
                }) : (r.filter.ID = function(e) {
                    var t = e.replace(Z, ee);
                    return function(e) {
                        var n = "undefined" != typeof e.getAttributeNode && e.getAttributeNode("id");
                        return n && n.value === t
                    }
                }, r.find.ID = function(e, t) {
                    if ("undefined" != typeof t.getElementById && g) {
                        var n, r, i, o = t.getElementById(e);
                        if (o) {
                            if ((n = o.getAttributeNode("id")) && n.value === e) return [o];
                            i = t.getElementsByName(e), r = 0;
                            while (o = i[r++])
                                if ((n = o.getAttributeNode("id")) && n.value === e) return [o]
                        }
                        return []
                    }
                }), r.find.TAG = n.getElementsByTagName ? function(e, t) {
                    return "undefined" != typeof t.getElementsByTagName ? t.getElementsByTagName(e) : n.qsa ? t.querySelectorAll(e) : void 0
                } : function(e, t) {
                    var n, r = [],
                        i = 0,
                        o = t.getElementsByTagName(e);
                    if ("*" === e) {
                        while (n = o[i++]) 1 === n.nodeType && r.push(n);
                        return r
                    }
                    return o
                }, r.find.CLASS = n.getElementsByClassName && function(e, t) {
                    if ("undefined" != typeof t.getElementsByClassName && g) return t.getElementsByClassName(e)
                }, v = [], y = [], (n.qsa = Q.test(d.querySelectorAll)) && (ue(function(e) {
                    h.appendChild(e).innerHTML = "<a id='" + b + "'></a><select id='" + b + "-\r\\' msallowcapture=''><option selected=''></option></select>", e.querySelectorAll("[msallowcapture^='']").length && y.push("[*^$]=" + M + "*(?:''|\"\")"), e.querySelectorAll("[selected]").length || y.push("\\[" + M + "*(?:value|" + P + ")"), e.querySelectorAll("[id~=" + b + "-]").length || y.push("~="), e.querySelectorAll(":checked").length || y.push(":checked"), e.querySelectorAll("a#" + b + "+*").length || y.push(".#.+[+~]")
                }), ue(function(e) {
                    e.innerHTML = "<a href='' disabled='disabled'></a><select disabled='disabled'><option/></select>";
                    var t = d.createElement("input");
                    t.setAttribute("type", "hidden"), e.appendChild(t).setAttribute("name", "D"), e.querySelectorAll("[name=d]").length && y.push("name" + M + "*[*^$|!~]?="), 2 !== e.querySelectorAll(":enabled").length && y.push(":enabled", ":disabled"), h.appendChild(e).disabled = !0, 2 !== e.querySelectorAll(":disabled").length && y.push(":enabled", ":disabled"), e.querySelectorAll("*,:x"), y.push(",.*:")
                })), (n.matchesSelector = Q.test(m = h.matches || h.webkitMatchesSelector || h.mozMatchesSelector || h.oMatchesSelector || h.msMatchesSelector)) && ue(function(e) {
                    n.disconnectedMatch = m.call(e, "*"), m.call(e, "[s!='']:x"), v.push("!=", W)
                }), y = y.length && new RegExp(y.join("|")), v = v.length && new RegExp(v.join("|")), t = Q.test(h.compareDocumentPosition), x = t || Q.test(h.contains) ? function(e, t) {
                    var n = 9 === e.nodeType ? e.documentElement : e,
                        r = t && t.parentNode;
                    return e === r || !(!r || 1 !== r.nodeType || !(n.contains ? n.contains(r) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(r)))
                } : function(e, t) {
                    if (t)
                        while (t = t.parentNode)
                            if (t === e) return !0;
                    return !1
                }, D = t ? function(e, t) {
                    if (e === t) return f = !0, 0;
                    var r = !e.compareDocumentPosition - !t.compareDocumentPosition;
                    return r || (1 & (r = (e.ownerDocument || e) === (t.ownerDocument || t) ? e.compareDocumentPosition(t) : 1) || !n.sortDetached && t.compareDocumentPosition(e) === r ? e === d || e.ownerDocument === w && x(w, e) ? -1 : t === d || t.ownerDocument === w && x(w, t) ? 1 : c ? O(c, e) - O(c, t) : 0 : 4 & r ? -1 : 1)
                } : function(e, t) {
                    if (e === t) return f = !0, 0;
                    var n, r = 0,
                        i = e.parentNode,
                        o = t.parentNode,
                        a = [e],
                        s = [t];
                    if (!i || !o) return e === d ? -1 : t === d ? 1 : i ? -1 : o ? 1 : c ? O(c, e) - O(c, t) : 0;
                    if (i === o) return ce(e, t);
                    n = e;
                    while (n = n.parentNode) a.unshift(n);
                    n = t;
                    while (n = n.parentNode) s.unshift(n);
                    while (a[r] === s[r]) r++;
                    return r ? ce(a[r], s[r]) : a[r] === w ? -1 : s[r] === w ? 1 : 0
                }, d) : d
            }, oe.matches = function(e, t) {
                return oe(e, null, null, t)
            }, oe.matchesSelector = function(e, t) {
                if ((e.ownerDocument || e) !== d && p(e), t = t.replace(z, "='$1']"), n.matchesSelector && g && !S[t + " "] && (!v || !v.test(t)) && (!y || !y.test(t))) try {
                    var r = m.call(e, t);
                    if (r || n.disconnectedMatch || e.document && 11 !== e.document.nodeType) return r
                } catch (e) {}
                return oe(t, d, null, [e]).length > 0
            }, oe.contains = function(e, t) {
                return (e.ownerDocument || e) !== d && p(e), x(e, t)
            }, oe.attr = function(e, t) {
                (e.ownerDocument || e) !== d && p(e);
                var i = r.attrHandle[t.toLowerCase()],
                    o = i && N.call(r.attrHandle, t.toLowerCase()) ? i(e, t, !g) : void 0;
                return void 0 !== o ? o : n.attributes || !g ? e.getAttribute(t) : (o = e.getAttributeNode(t)) && o.specified ? o.value : null
            }, oe.escape = function(e) {
                return (e + "").replace(te, ne)
            }, oe.error = function(e) {
                throw new Error("Syntax error, unrecognized expression: " + e)
            }, oe.uniqueSort = function(e) {
                var t, r = [],
                    i = 0,
                    o = 0;
                if (f = !n.detectDuplicates, c = !n.sortStable && e.slice(0), e.sort(D), f) {
                    while (t = e[o++]) t === e[o] && (i = r.push(o));
                    while (i--) e.splice(r[i], 1)
                }
                return c = null, e
            }, i = oe.getText = function(e) {
                var t, n = "",
                    r = 0,
                    o = e.nodeType;
                if (o) {
                    if (1 === o || 9 === o || 11 === o) {
                        if ("string" == typeof e.textContent) return e.textContent;
                        for (e = e.firstChild; e; e = e.nextSibling) n += i(e)
                    } else if (3 === o || 4 === o) return e.nodeValue
                } else
                    while (t = e[r++]) n += i(t);
                return n
            }, (r = oe.selectors = {
                cacheLength: 50,
                createPseudo: se,
                match: V,
                attrHandle: {},
                find: {},
                relative: {
                    ">": {
                        dir: "parentNode",
                        first: !0
                    },
                    " ": {
                        dir: "parentNode"
                    },
                    "+": {
                        dir: "previousSibling",
                        first: !0
                    },
                    "~": {
                        dir: "previousSibling"
                    }
                },
                preFilter: {
                    ATTR: function(e) {
                        return e[1] = e[1].replace(Z, ee), e[3] = (e[3] || e[4] || e[5] || "").replace(Z, ee), "~=" === e[2] && (e[3] = " " + e[3] + " "), e.slice(0, 4)
                    },
                    CHILD: function(e) {
                        return e[1] = e[1].toLowerCase(), "nth" === e[1].slice(0, 3) ? (e[3] || oe.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && oe.error(e[0]), e
                    },
                    PSEUDO: function(e) {
                        var t, n = !e[6] && e[2];
                        return V.CHILD.test(e[0]) ? null : (e[3] ? e[2] = e[4] || e[5] || "" : n && X.test(n) && (t = a(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
                    }
                },
                filter: {
                    TAG: function(e) {
                        var t = e.replace(Z, ee).toLowerCase();
                        return "*" === e ? function() {
                            return !0
                        } : function(e) {
                            return e.nodeName && e.nodeName.toLowerCase() === t
                        }
                    },
                    CLASS: function(e) {
                        var t = E[e + " "];
                        return t || (t = new RegExp("(^|" + M + ")" + e + "(" + M + "|$)")) && E(e, function(e) {
                            return t.test("string" == typeof e.className && e.className || "undefined" != typeof e.getAttribute && e.getAttribute("class") || "")
                        })
                    },
                    ATTR: function(e, t, n) {
                        return function(r) {
                            var i = oe.attr(r, e);
                            return null == i ? "!=" === t : !t || (i += "", "=" === t ? i === n : "!=" === t ? i !== n : "^=" === t ? n && 0 === i.indexOf(n) : "*=" === t ? n && i.indexOf(n) > -1 : "$=" === t ? n && i.slice(-n.length) === n : "~=" === t ? (" " + i.replace($, " ") + " ").indexOf(n) > -1 : "|=" === t && (i === n || i.slice(0, n.length + 1) === n + "-"))
                        }
                    },
                    CHILD: function(e, t, n, r, i) {
                        var o = "nth" !== e.slice(0, 3),
                            a = "last" !== e.slice(-4),
                            s = "of-type" === t;
                        return 1 === r && 0 === i ? function(e) {
                            return !!e.parentNode
                        } : function(t, n, u) {
                            var l, c, f, p, d, h, g = o !== a ? "nextSibling" : "previousSibling",
                                y = t.parentNode,
                                v = s && t.nodeName.toLowerCase(),
                                m = !u && !s,
                                x = !1;
                            if (y) {
                                if (o) {
                                    while (g) {
                                        p = t;
                                        while (p = p[g])
                                            if (s ? p.nodeName.toLowerCase() === v : 1 === p.nodeType) return !1;
                                        h = g = "only" === e && !h && "nextSibling"
                                    }
                                    return !0
                                }
                                if (h = [a ? y.firstChild : y.lastChild], a && m) {
                                    x = (d = (l = (c = (f = (p = y)[b] || (p[b] = {}))[p.uniqueID] || (f[p.uniqueID] = {}))[e] || [])[0] === T && l[1]) && l[2], p = d && y.childNodes[d];
                                    while (p = ++d && p && p[g] || (x = d = 0) || h.pop())
                                        if (1 === p.nodeType && ++x && p === t) {
                                            c[e] = [T, d, x];
                                            break
                                        }
                                } else if (m && (x = d = (l = (c = (f = (p = t)[b] || (p[b] = {}))[p.uniqueID] || (f[p.uniqueID] = {}))[e] || [])[0] === T && l[1]), !1 === x)
                                    while (p = ++d && p && p[g] || (x = d = 0) || h.pop())
                                        if ((s ? p.nodeName.toLowerCase() === v : 1 === p.nodeType) && ++x && (m && ((c = (f = p[b] || (p[b] = {}))[p.uniqueID] || (f[p.uniqueID] = {}))[e] = [T, x]), p === t)) break;
                                return (x -= i) === r || x % r == 0 && x / r >= 0
                            }
                        }
                    },
                    PSEUDO: function(e, t) {
                        var n, i = r.pseudos[e] || r.setFilters[e.toLowerCase()] || oe.error("unsupported pseudo: " + e);
                        return i[b] ? i(t) : i.length > 1 ? (n = [e, e, "", t], r.setFilters.hasOwnProperty(e.toLowerCase()) ? se(function(e, n) {
                            var r, o = i(e, t),
                                a = o.length;
                            while (a--) e[r = O(e, o[a])] = !(n[r] = o[a])
                        }) : function(e) {
                            return i(e, 0, n)
                        }) : i
                    }
                },
                pseudos: {
                    not: se(function(e) {
                        var t = [],
                            n = [],
                            r = s(e.replace(B, "$1"));
                        return r[b] ? se(function(e, t, n, i) {
                            var o, a = r(e, null, i, []),
                                s = e.length;
                            while (s--)(o = a[s]) && (e[s] = !(t[s] = o))
                        }) : function(e, i, o) {
                            return t[0] = e, r(t, null, o, n), t[0] = null, !n.pop()
                        }
                    }),
                    has: se(function(e) {
                        return function(t) {
                            return oe(e, t).length > 0
                        }
                    }),
                    contains: se(function(e) {
                        return e = e.replace(Z, ee),
                            function(t) {
                                return (t.textContent || t.innerText || i(t)).indexOf(e) > -1
                            }
                    }),
                    lang: se(function(e) {
                        return U.test(e || "") || oe.error("unsupported lang: " + e), e = e.replace(Z, ee).toLowerCase(),
                            function(t) {
                                var n;
                                do {
                                   if (n = g ? t.lang : t.getAttribute("xml:lang") || t.getAttribute("lang")) return (n = n.toLowerCase()) === e || 0 === n.indexOf(e + "-")
                                } while ((t = t.parentNode) && 1 === t.nodeType);
                               return !1
                            }
                    }),
                    target: function(t) {
                        var n = e.location && e.location.hash;
                        return n && n.slice(1) === t.id
                    },
                    root: function(e) {
                        return e === h
                    },
                    focus: function(e) {
                        return e === d.activeElement && (!d.hasFocus || d.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
                    },
                    enabled: de(!1),
                    disabled: de(!0),
                    checked: function(e) {
                        var t = e.nodeName.toLowerCase();
                        return "input" === t && !!e.checked || "option" === t && !!e.selected
                    },
                    selected: function(e) {
                        return e.parentNode && e.parentNode.selectedIndex, !0 === e.selected
                    },
                   empty: function(e) {
                        for (e = e.firstChild; e; e = e.nextSibling)
                            if (e.nodeType < 6) return !1;
                        return !0
                    },
                    parent: function(e) {
                        return !r.pseudos.empty(e)
                    },
                    header: function(e) {
                        return Y.test(e.nodeName)
                    },
                    input: function(e) {
                        return G.test(e.nodeName)
                    },
                    button: function(e) {
                        var t = e.nodeName.toLowerCase();
                        return "input" === t && "button" === e.type || "button" === t
                    },
                    text: function(e) {
                        var t;
                        return "input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || "text" === t.toLowerCase())
                    },
                    first: he(function() {
                        return [0]
                    }),
                    last: he(function(e, t) {
                        return [t - 1]
                    }),
                    eq: he(function(e, t, n) {
                        return [n < 0 ? n + t : n]
                    }),
                    even: he(function(e, t) {
                        for (var n = 0; n < t; n += 2) e.push(n);
                        return e
                    }),
                    odd: he(function(e, t) {
                        for (var n = 1; n < t; n += 2) e.push(n);
                        return e
                    }),
                    lt: he(function(e, t, n) {
                        for (var r = n < 0 ? n + t : n; --r >= 0;) e.push(r);
                        return e
                    }),
                    gt: he(function(e, t, n) {
                        for (var r = n < 0 ? n + t : n; ++r < t;) e.push(r);
                        return e
                    })
                }
            }).pseudos.nth = r.pseudos.eq;
            for (t in {
                    radio: !0,
                    checkbox: !0,
                    file: !0,
                    password: !0,
                    image: !0
                }) r.pseudos[t] = fe(t);
            for (t in {
                    submit: !0,
                    reset: !0
                }) r.pseudos[t] = pe(t);

            function ye() {}
            ye.prototype = r.filters = r.pseudos, r.setFilters = new ye, a = oe.tokenize = function(e, t) {
                var n, i, o, a, s, u, l, c = k[e + " "];
                if (c) return t ? 0 : c.slice(0);
                s = e, u = [], l = r.preFilter;
                while (s) {
                    n && !(i = F.exec(s)) || (i && (s = s.slice(i[0].length) || s), u.push(o = [])), n = !1, (i = _.exec(s)) && (n = i.shift(), o.push({
                        value: n,
                        type: i[0].replace(B, " ")
                    }), s = s.slice(n.length));
                    for (a in r.filter) !(i = V[a].exec(s)) || l[a] && !(i = l[a](i)) || (n = i.shift(), o.push({
                        value: n,
                        type: a,
                        matches: i
                    }), s = s.slice(n.length));
                    if (!n) break
                }
                return t ? s.length : s ? oe.error(e) : k(e, u).slice(0)
            };

            function ve(e) {
                for (var t = 0, n = e.length, r = ""; t < n; t++) r += e[t].value;
                return r
            }

            function me(e, t, n) {
                var r = t.dir,
                    i = t.next,
                    o = i || r,
                    a = n && "parentNode" === o,
                    s = C++;
                return t.first ? function(t, n, i) {
                    while (t = t[r])
                        if (1 === t.nodeType || a) return e(t, n, i);
                    return !1
                } : function(t, n, u) {
                    var l, c, f, p = [T, s];
                    if (u) {
                        while (t = t[r])
                            if ((1 === t.nodeType || a) && e(t, n, u)) return !0
                    } else
                        while (t = t[r])
                            if (1 === t.nodeType || a)
                                if (f = t[b] || (t[b] = {}), c = f[t.uniqueID] || (f[t.uniqueID] = {}), i && i === t.nodeName.toLowerCase()) t = t[r] || t;
                                else {
                                    if ((l = c[o]) && l[0] === T && l[1] === s) return p[2] = l[2];
                                    if (c[o] = p, p[2] = e(t, n, u)) return !0
                                } return !1
                }
            }

            function xe(e) {
                return e.length > 1 ? function(t, n, r) {
                    var i = e.length;
                    while (i--)
                        if (!e[i](t, n, r)) return !1;
                    return !0
                } : e[0]
            }

            function be(e, t, n) {
                for (var r = 0, i = t.length; r < i; r++) oe(e, t[r], n);
                return n
            }

            function we(e, t, n, r, i) {
                for (var o, a = [], s = 0, u = e.length, l = null != t; s < u; s++)(o = e[s]) && (n && !n(o, r, i) || (a.push(o), l && t.push(s)));
                return a
            }

            function Te(e, t, n, r, i, o) {
                return r && !r[b] && (r = Te(r)), i && !i[b] && (i = Te(i, o)), se(function(o, a, s, u) {
                    var l, c, f, p = [],
                        d = [],
                        h = a.length,
                        g = o || be(t || "*", s.nodeType ? [s] : s, []),
                        y = !e || !o && t ? g : we(g, p, e, s, u),
                        v = n ? i || (o ? e : h || r) ? [] : a : y;
                    if (n && n(y, v, s, u), r) {
                        l = we(v, d), r(l, [], s, u), c = l.length;
                        while (c--)(f = l[c]) && (v[d[c]] = !(y[d[c]] = f))
                    }
                    if (o) {
                        if (i || e) {
                            if (i) {
                                l = [], c = v.length;
                                while (c--)(f = v[c]) && l.push(y[c] = f);
                                i(null, v = [], l, u)
                            }
                            c = v.length;
                            while (c--)(f = v[c]) && (l = i ? O(o, f) : p[c]) > -1 && (o[l] = !(a[l] = f))
                        }
                    } else v = we(v === a ? v.splice(h, v.length) : v), i ? i(null, a, v, u) : L.apply(a, v)
                })
            }

            function Ce(e) {
                for (var t, n, i, o = e.length, a = r.relative[e[0].type], s = a || r.relative[" "], u = a ? 1 : 0, c = me(function(e) {
                        return e === t
                    }, s, !0), f = me(function(e) {
                        return O(t, e) > -1
                    }, s, !0), p = [function(e, n, r) {
                        var i = !a && (r || n !== l) || ((t = n).nodeType ? c(e, n, r) : f(e, n, r));
                        return t = null, i
                    }]; u < o; u++)
                    if (n = r.relative[e[u].type]) p = [me(xe(p), n)];
                    else {
                        if ((n = r.filter[e[u].type].apply(null, e[u].matches))[b]) {
                            for (i = ++u; i < o; i++)
                                if (r.relative[e[i].type]) break;
                            return Te(u > 1 && xe(p), u > 1 && ve(e.slice(0, u - 1).concat({
                                value: " " === e[u - 2].type ? "*" : ""
                            })).replace(B, "$1"), n, u < i && Ce(e.slice(u, i)), i < o && Ce(e = e.slice(i)), i < o && ve(e))
                        }
                        p.push(n)
                    }
                return xe(p)
            }

            function Ee(e, t) {
                var n = t.length > 0,
                    i = e.length > 0,
                    o = function(o, a, s, u, c) {
                        var f, h, y, v = 0,
                            m = "0",
                            x = o && [],
                            b = [],
                            w = l,
                            C = o || i && r.find.TAG("*", c),
                            E = T += null == w ? 1 : Math.random() || .1,
                            k = C.length;
                        for (c && (l = a === d || a || c); m !== k && null != (f = C[m]); m++) {
                            if (i && f) {
                                h = 0, a || f.ownerDocument === d || (p(f), s = !g);
                                while (y = e[h++])
                                    if (y(f, a || d, s)) {
                                        u.push(f);
                                        break
                                    }
                                c && (T = E)
                            }
                            n && ((f = !y && f) && v--, o && x.push(f))
                        }
                        if (v += m, n && m !== v) {
                            h = 0;
                            while (y = t[h++]) y(x, b, a, s);
                            if (o) {
                                if (v > 0)
                                    while (m--) x[m] || b[m] || (b[m] = j.call(u));
                                b = we(b)
                            }
                            L.apply(u, b), c && !o && b.length > 0 && v + t.length > 1 && oe.uniqueSort(u)
                        }
                        return c && (T = E, l = w), x
                    };
                return n ? se(o) : o
            }
            return s = oe.compile = function(e, t) {
                var n, r = [],
                    i = [],
                    o = S[e + " "];
                if (!o) {
                    t || (t = a(e)), n = t.length;
                    while (n--)(o = Ce(t[n]))[b] ? r.push(o) : i.push(o);
                    (o = S(e, Ee(i, r))).selector = e
                }
                return o
            }, u = oe.select = function(e, t, n, i) {
                var o, u, l, c, f, p = "function" == typeof e && e,
                    d = !i && a(e = p.selector || e);
                if (n = n || [], 1 === d.length) {
                    if ((u = d[0] = d[0].slice(0)).length > 2 && "ID" === (l = u[0]).type && 9 === t.nodeType && g && r.relative[u[1].type]) {
                        if (!(t = (r.find.ID(l.matches[0].replace(Z, ee), t) || [])[0])) return n;
                        p && (t = t.parentNode), e = e.slice(u.shift().value.length)
                    }
                   o = V.needsContext.test(e) ? 0 : u.length;
                    while (o--) {
                        if (l = u[o], r.relative[c = l.type]) break;
                        if ((f = r.find[c]) && (i = f(l.matches[0].replace(Z, ee), K.test(u[0].type) && ge(t.parentNode) || t))) {
                            if (u.splice(o, 1), !(e = i.length && ve(u))) return L.apply(n, i), n;
                            break
                        }
                    }
                }
                return (p || s(e, d))(i, t, !g, n, !t || K.test(e) && ge(t.parentNode) || t), n
            }, n.sortStable = b.split("").sort(D).join("") === b, n.detectDuplicates = !!f, p(), n.sortDetached = ue(function(e) {
                return 1 & e.compareDocumentPosition(d.createElement("fieldset"))
            }), ue(function(e) {
                return e.innerHTML = "<a href='#'></a>", "#" === e.firstChild.getAttribute("href")
            }) || le("type|href|height|width", function(e, t, n) {
                if (!n) return e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
            }), n.attributes && ue(function(e) {
                return e.innerHTML = "<input/>", e.firstChild.setAttribute("value", ""), "" === e.firstChild.getAttribute("value")
            }) || le("value", function(e, t, n) {
                if (!n && "input" === e.nodeName.toLowerCase()) return e.defaultValue
            }), ue(function(e) {
                return null == e.getAttribute("disabled")
            }) || le(P, function(e, t, n) {
                var r;
                if (!n) return !0 === e[t] ? t.toLowerCase() : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
            }), oe
        }(e);
        w.find = E, w.expr = E.selectors, w.expr[":"] = w.expr.pseudos, w.uniqueSort = w.unique = E.uniqueSort, w.text = E.getText, w.isXMLDoc = E.isXML, w.contains = E.contains, w.escapeSelector = E.escape;
        var k = function(e, t, n) {
                var r = [],
                    i = void 0 !== n;
                while ((e = e[t]) && 9 !== e.nodeType)
                    if (1 === e.nodeType) {
                        if (i && w(e).is(n)) break;
                        r.push(e)
                    }
                return r
            },
            S = function(e, t) {
                for (var n = []; e; e = e.nextSibling) 1 === e.nodeType && e !== t && n.push(e);
                return n
            },
            D = w.expr.match.needsContext;

        function N(e, t) {
            return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
        }
        var A = /^<([a-z][^\/\0>:\x20\t\r\n\f]*)[\x20\t\r\n\f]*\/?>(?:<\/\1>|)$/i;

        function j(e, t, n) {
            return g(t) ? w.grep(e, function(e, r) {
                return !!t.call(e, r, e) !== n
            }) : t.nodeType ? w.grep(e, function(e) {
                return e === t !== n
            }) : "string" != typeof t ? w.grep(e, function(e) {
                return u.call(t, e) > -1 !== n
            }) : w.filter(t, e, n)
        }
        w.filter = function(e, t, n) {
            var r = t[0];
            return n && (e = ":not(" + e + ")"), 1 === t.length && 1 === r.nodeType ? w.find.matchesSelector(r, e) ? [r] : [] : w.find.matches(e, w.grep(t, function(e) {
                return 1 === e.nodeType
            }))
        }, w.fn.extend({
            find: function(e) {
                var t, n, r = this.length,
                    i = this;
                if ("string" != typeof e) return this.pushStack(w(e).filter(function() {
                    for (t = 0; t < r; t++)
                        if (w.contains(i[t], this)) return !0
                }));
                for (n = this.pushStack([]), t = 0; t < r; t++) w.find(e, i[t], n);
                return r > 1 ? w.uniqueSort(n) : n
            },
            filter: function(e) {
                return this.pushStack(j(this, e || [], !1))
            },
            not: function(e) {
                return this.pushStack(j(this, e || [], !0))
            },
            is: function(e) {
                return !!j(this, "string" == typeof e && D.test(e) ? w(e) : e || [], !1).length
            }
        });
        var q, L = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/;
        (w.fn.init = function(e, t, n) {
            var i, o;
            if (!e) return this;
            if (n = n || q, "string" == typeof e) {
                if (!(i = "<" === e[0] && ">" === e[e.length - 1] && e.length >= 3 ? [null, e, null] : L.exec(e)) || !i[1] && t) return !t || t.jquery ? (t || n).find(e) : this.constructor(t).find(e);
                if (i[1]) {
                    if (t = t instanceof w ? t[0] : t, w.merge(this, w.parseHTML(i[1], t && t.nodeType ? t.ownerDocument || t : r, !0)), A.test(i[1]) && w.isPlainObject(t))
                        for (i in t) g(this[i]) ? this[i](t[i]) : this.attr(i, t[i]);
                    return this
                }
                return (o = r.getElementById(i[2])) && (this[0] = o, this.length = 1), this
            }
            return e.nodeType ? (this[0] = e, this.length = 1, this) : g(e) ? void 0 !== n.ready ? n.ready(e) : e(w) : w.makeArray(e, this)
        }).prototype = w.fn, q = w(r);
        var H = /^(?:parents|prev(?:Until|All))/,
            O = {
                children: !0,
                contents: !0,
                next: !0,
                prev: !0
            };
        w.fn.extend({
            has: function(e) {
                var t = w(e, this),
                    n = t.length;
                return this.filter(function() {
                    for (var e = 0; e < n; e++)
                        if (w.contains(this, t[e])) return !0
                })
            },
            closest: function(e, t) {
                var n, r = 0,
                    i = this.length,
                    o = [],
                    a = "string" != typeof e && w(e);
                if (!D.test(e))
                    for (; r < i; r++)
                        for (n = this[r]; n && n !== t; n = n.parentNode)
                            if (n.nodeType < 11 && (a ? a.index(n) > -1 : 1 === n.nodeType && w.find.matchesSelector(n, e))) {
                                o.push(n);
                                break
                            }
                return this.pushStack(o.length > 1 ? w.uniqueSort(o) : o)
            },
            index: function(e) {
                return e ? "string" == typeof e ? u.call(w(e), this[0]) : u.call(this, e.jquery ? e[0] : e) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
            },
            add: function(e, t) {
                return this.pushStack(w.uniqueSort(w.merge(this.get(), w(e, t))))
            },
            addBack: function(e) {
                return this.add(null == e ? this.prevObject : this.prevObject.filter(e))
            }
        });

        function P(e, t) {
            while ((e = e[t]) && 1 !== e.nodeType);
            return e
        }
        w.each({
            parent: function(e) {
                var t = e.parentNode;
                return t && 11 !== t.nodeType ? t : null
            },
            parents: function(e) {
                return k(e, "parentNode")
            },
            parentsUntil: function(e, t, n) {
                return k(e, "parentNode", n)
            },
            next: function(e) {
                return P(e, "nextSibling")
            },
            prev: function(e) {
                return P(e, "previousSibling")
            },
            nextAll: function(e) {
                return k(e, "nextSibling")
            },
            prevAll: function(e) {
                return k(e, "previousSibling")
            },
            nextUntil: function(e, t, n) {
                return k(e, "nextSibling", n)
            },
            prevUntil: function(e, t, n) {
                return k(e, "previousSibling", n)
            },
            siblings: function(e) {
                return S((e.parentNode || {}).firstChild, e)
            },
            children: function(e) {
                return S(e.firstChild)
            },
            contents: function(e) {
                return N(e, "iframe") ? e.contentDocument : (N(e, "template") && (e = e.content || e), w.merge([], e.childNodes))
            }
        }, function(e, t) {
            w.fn[e] = function(n, r) {
                var i = w.map(this, t, n);
                return "Until" !== e.slice(-5) && (r = n), r && "string" == typeof r && (i = w.filter(r, i)), this.length > 1 && (O[e] || w.uniqueSort(i), H.test(e) && i.reverse()), this.pushStack(i)
            }
        });
        var M = /[^\x20\t\r\n\f]+/g;

        function R(e) {
            var t = {};
            return w.each(e.match(M) || [], function(e, n) {
                t[n] = !0
            }), t
        }
        w.Callbacks = function(e) {
            e = "string" == typeof e ? R(e) : w.extend({}, e);
            var t, n, r, i, o = [],
                a = [],
                s = -1,
                u = function() {
                    for (i = i || e.once, r = t = !0; a.length; s = -1) {
                        n = a.shift();
                        while (++s < o.length) !1 === o[s].apply(n[0], n[1]) && e.stopOnFalse && (s = o.length, n = !1)
                    }
                    e.memory || (n = !1), t = !1, i && (o = n ? [] : "")
                },
                l = {
                    add: function() {
                        return o && (n && !t && (s = o.length - 1, a.push(n)), function t(n) {
                            w.each(n, function(n, r) {
                                g(r) ? e.unique && l.has(r) || o.push(r) : r && r.length && "string" !== x(r) && t(r)
                            })
                        }(arguments), n && !t && u()), this
                    },
                    remove: function() {
                        return w.each(arguments, function(e, t) {
                            var n;
                            while ((n = w.inArray(t, o, n)) > -1) o.splice(n, 1), n <= s && s--
                        }), this
                    },
                    has: function(e) {
                        return e ? w.inArray(e, o) > -1 : o.length > 0
                    },
                    empty: function() {
                        return o && (o = []), this
                    },
                    disable: function() {
                        return i = a = [], o = n = "", this
                    },
                    disabled: function() {
                        return !o
                    },
                    lock: function() {
                        return i = a = [], n || t || (o = n = ""), this
                    },
                    locked: function() {
                        return !!i
                    },
                    fireWith: function(e, n) {
                        return i || (n = [e, (n = n || []).slice ? n.slice() : n], a.push(n), t || u()), this
                    },
                    fire: function() {
                        return l.fireWith(this, arguments), this
                    },
                    fired: function() {
                        return !!r
                    }
                };
            return l
        };

        function I(e) {
            return e
        }

        function W(e) {
            throw e
        }

        function $(e, t, n, r) {
            var i;
            try {
                e && g(i = e.promise) ? i.call(e).done(t).fail(n) : e && g(i = e.then) ? i.call(e, t, n) : t.apply(void 0, [e].slice(r))
            } catch (e) {
                n.apply(void 0, [e])
            }
        }
        w.extend({
            Deferred: function(t) {
                var n = [
                        ["notify", "progress", w.Callbacks("memory"), w.Callbacks("memory"), 2],
                        ["resolve", "done", w.Callbacks("once memory"), w.Callbacks("once memory"), 0, "resolved"],
                        ["reject", "fail", w.Callbacks("once memory"), w.Callbacks("once memory"), 1, "rejected"]
                    ],
                    r = "pending",
                    i = {
                        state: function() {
                            return r
                        },
                        always: function() {
                            return o.done(arguments).fail(arguments), this
                        },
                        "catch": function(e) {
                            return i.then(null, e)
                        },
                        pipe: function() {
                            var e = arguments;
                            return w.Deferred(function(t) {
                                w.each(n, function(n, r) {
                                    var i = g(e[r[4]]) && e[r[4]];
                                    o[r[1]](function() {
                                        var e = i && i.apply(this, arguments);
                                        e && g(e.promise) ? e.promise().progress(t.notify).done(t.resolve).fail(t.reject) : t[r[0] + "With"](this, i ? [e] : arguments)
                                    })
                                }), e = null
                            }).promise()
                        },
                        then: function(t, r, i) {
                            var o = 0;

                            function a(t, n, r, i) {
                                return function() {
                                    var s = this,
                                        u = arguments,
                                        l = function() {
                                            var e, l;
                                            if (!(t < o)) {
                                                if ((e = r.apply(s, u)) === n.promise()) throw new TypeError("Thenable self-resolution");
                                                l = e && ("object" == typeof e || "function" == typeof e) && e.then, g(l) ? i ? l.call(e, a(o, n, I, i), a(o, n, W, i)) : (o++, l.call(e, a(o, n, I, i), a(o, n, W, i), a(o, n, I, n.notifyWith))) : (r !== I && (s = void 0, u = [e]), (i || n.resolveWith)(s, u))
                                            }
                                        },
                                        c = i ? l : function() {
                                            try {
                                                l()
                                            } catch (e) {
                                                w.Deferred.exceptionHook && w.Deferred.exceptionHook(e, c.stackTrace), t + 1 >= o && (r !== W && (s = void 0, u = [e]), n.rejectWith(s, u))
                                            }
                                        };
                                    t ? c() : (w.Deferred.getStackHook && (c.stackTrace = w.Deferred.getStackHook()), e.setTimeout(c))
                                }
                            }
                            return w.Deferred(function(e) {
                                n[0][3].add(a(0, e, g(i) ? i : I, e.notifyWith)), n[1][3].add(a(0, e, g(t) ? t : I)), n[2][3].add(a(0, e, g(r) ? r : W))
                            }).promise()
                        },
                        promise: function(e) {
                            return null != e ? w.extend(e, i) : i
                        }
                    },
                    o = {};
                return w.each(n, function(e, t) {
                    var a = t[2],
                        s = t[5];
                    i[t[1]] = a.add, s && a.add(function() {
                        r = s
                    }, n[3 - e][2].disable, n[3 - e][3].disable, n[0][2].lock, n[0][3].lock), a.add(t[3].fire), o[t[0]] = function() {
                        return o[t[0] + "With"](this === o ? void 0 : this, arguments), this
                    }, o[t[0] + "With"] = a.fireWith
                }), i.promise(o), t && t.call(o, o), o
            },
            when: function(e) {
                var t = arguments.length,
                    n = t,
                    r = Array(n),
                    i = o.call(arguments),
                    a = w.Deferred(),
                    s = function(e) {
                        return function(n) {
                            r[e] = this, i[e] = arguments.length > 1 ? o.call(arguments) : n, --t || a.resolveWith(r, i)
                        }
                    };
                if (t <= 1 && ($(e, a.done(s(n)).resolve, a.reject, !t), "pending" === a.state() || g(i[n] && i[n].then))) return a.then();
                while (n--) $(i[n], s(n), a.reject);
                return a.promise()
            }
        });
        var B = /^(Eval|Internal|Range|Reference|Syntax|Type|URI)Error$/;
        w.Deferred.exceptionHook = function(t, n) {
            e.console && e.console.warn && t && B.test(t.name) && e.console.warn("jQuery.Deferred exception: " + t.message, t.stack, n)
        }, w.readyException = function(t) {
            e.setTimeout(function() {
                throw t
            })
        };
        var F = w.Deferred();
        w.fn.ready = function(e) {
            return F.then(e)["catch"](function(e) {
               w.readyException(e)
            }), this
        }, w.extend({
            isReady: !1,
            readyWait: 1,
            ready: function(e) {
                (!0 === e ? --w.readyWait : w.isReady) || (w.isReady = !0, !0 !== e && --w.readyWait > 0 || F.resolveWith(r, [w]))
            }
        }), w.ready.then = F.then;

        function _() {
            r.removeEventListener("DOMContentLoaded", _), e.removeEventListener("load", _), w.ready()
        }
        "complete" === r.readyState || "loading" !== r.readyState && !r.documentElement.doScroll ? e.setTimeout(w.ready) : (r.addEventListener("DOMContentLoaded", _), e.addEventListener("load", _));
        var z = function(e, t, n, r, i, o, a) {
                var s = 0,
                    u = e.length,
                    l = null == n;
                if ("object" === x(n)) {
                    i = !0;
                    for (s in n) z(e, t, s, n[s], !0, o, a)
                } else if (void 0 !== r && (i = !0, g(r) || (a = !0), l && (a ? (t.call(e, r), t = null) : (l = t, t = function(e, t, n) {
                        return l.call(w(e), n)
                    })), t))
                    for (; s < u; s++) t(e[s], n, a ? r : r.call(e[s], s, t(e[s], n)));
                return i ? e : l ? t.call(e) : u ? t(e[0], n) : o
            },
            X = /^-ms-/,
            U = /-([a-z])/g;

        function V(e, t) {
            return t.toUpperCase()
        }

        function G(e) {
            return e.replace(X, "ms-").replace(U, V)
        }
        var Y = function(e) {
            return 1 === e.nodeType || 9 === e.nodeType || !+e.nodeType
        };

        function Q() {
            this.expando = w.expando + Q.uid++
        }
        Q.uid = 1, Q.prototype = {
            cache: function(e) {
                var t = e[this.expando];
                return t || (t = {}, Y(e) && (e.nodeType ? e[this.expando] = t : Object.defineProperty(e, this.expando, {
                    value: t,
                    configurable: !0
                }))), t
            },
            set: function(e, t, n) {
                var r, i = this.cache(e);
                if ("string" == typeof t) i[G(t)] = n;
                else
                    for (r in t) i[G(r)] = t[r];
                return i
            },
            get: function(e, t) {
                return void 0 === t ? this.cache(e) : e[this.expando] && e[this.expando][G(t)]
            },
            access: function(e, t, n) {
                return void 0 === t || t && "string" == typeof t && void 0 === n ? this.get(e, t) : (this.set(e, t, n), void 0 !== n ? n : t)
            },
            remove: function(e, t) {
                var n, r = e[this.expando];
                if (void 0 !== r) {
                    if (void 0 !== t) {
                        n = (t = Array.isArray(t) ? t.map(G) : (t = G(t)) in r ? [t] : t.match(M) || []).length;
                        while (n--) delete r[t[n]]
                    }(void 0 === t || w.isEmptyObject(r)) && (e.nodeType ? e[this.expando] = void 0 : delete e[this.expando])
                }
            },
            hasData: function(e) {
                var t = e[this.expando];
                return void 0 !== t && !w.isEmptyObject(t)
            }
        };
        var J = new Q,
            K = new Q,
            Z = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/,
            ee = /[A-Z]/g;

        function te(e) {
            return "true" === e || "false" !== e && ("null" === e ? null : e === +e + "" ? +e : Z.test(e) ? JSON.parse(e) : e)
        }

        function ne(e, t, n) {
            var r;
            if (void 0 === n && 1 === e.nodeType)
                if (r = "data-" + t.replace(ee, "-$&").toLowerCase(), "string" == typeof(n = e.getAttribute(r))) {
                    try {
                        n = te(n)
                    } catch (e) {}
                    K.set(e, t, n)
                } else n = void 0;
            return n
        }
        w.extend({
            hasData: function(e) {
                return K.hasData(e) || J.hasData(e)
            },
            data: function(e, t, n) {
                return K.access(e, t, n)
            },
            removeData: function(e, t) {
                K.remove(e, t)
            },
            _data: function(e, t, n) {
                return J.access(e, t, n)
            },
            _removeData: function(e, t) {
                J.remove(e, t)
            }
        }), w.fn.extend({
            data: function(e, t) {
                var n, r, i, o = this[0],
                    a = o && o.attributes;
                if (void 0 === e) {
                    if (this.length && (i = K.get(o), 1 === o.nodeType && !J.get(o, "hasDataAttrs"))) {
                        n = a.length;
                        while (n--) a[n] && 0 === (r = a[n].name).indexOf("data-") && (r = G(r.slice(5)), ne(o, r, i[r]));
                        J.set(o, "hasDataAttrs", !0)
                    }
                    return i
                }
                return "object" == typeof e ? this.each(function() {
                    K.set(this, e)
                }) : z(this, function(t) {
                    var n;
                    if (o && void 0 === t) {
                        if (void 0 !== (n = K.get(o, e))) return n;
                        if (void 0 !== (n = ne(o, e))) return n
                    } else this.each(function() {
                        K.set(this, e, t)
                    })
                }, null, t, arguments.length > 1, null, !0)
            },
            removeData: function(e) {
                return this.each(function() {
                    K.remove(this, e)
                })
            }
        }), w.extend({
            queue: function(e, t, n) {
                var r;
                if (e) return t = (t || "fx") + "queue", r = J.get(e, t), n && (!r || Array.isArray(n) ? r = J.access(e, t, w.makeArray(n)) : r.push(n)), r || []
            },
            dequeue: function(e, t) {
                t = t || "fx";
                var n = w.queue(e, t),
                    r = n.length,
                    i = n.shift(),
                    o = w._queueHooks(e, t),
                    a = function() {
                        w.dequeue(e, t)
                    };
                "inprogress" === i && (i = n.shift(), r--), i && ("fx" === t && n.unshift("inprogress"), delete o.stop, i.call(e, a, o)), !r && o && o.empty.fire()
            },
            _queueHooks: function(e, t) {
                var n = t + "queueHooks";
                return J.get(e, n) || J.access(e, n, {
                    empty: w.Callbacks("once memory").add(function() {
                        J.remove(e, [t + "queue", n])
                    })
                })
            }
        }), w.fn.extend({
            queue: function(e, t) {
                var n = 2;
                return "string" != typeof e && (t = e, e = "fx", n--), arguments.length < n ? w.queue(this[0], e) : void 0 === t ? this : this.each(function() {
                    var n = w.queue(this, e, t);
                    w._queueHooks(this, e), "fx" === e && "inprogress" !== n[0] && w.dequeue(this, e)
               })
            },
            dequeue: function(e) {
                return this.each(function() {
                    w.dequeue(this, e)
                })
            },
            clearQueue: function(e) {
                return this.queue(e || "fx", [])
            },
            promise: function(e, t) {
                var n, r = 1,
                    i = w.Deferred(),
                    o = this,
                    a = this.length,
                    s = function() {
                        --r || i.resolveWith(o, [o])
                    };
                "string" != typeof e && (t = e, e = void 0), e = e || "fx";
                while (a--)(n = J.get(o[a], e + "queueHooks")) && n.empty && (r++, n.empty.add(s));
                return s(), i.promise(t)
            }
        });
        var re = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source,
            ie = new RegExp("^(?:([+-])=|)(" + re + ")([a-z%]*)$", "i"),
            oe = ["Top", "Right", "Bottom", "Left"],
            ae = function(e, t) {
                return "none" === (e = t || e).style.display || "" === e.style.display && w.contains(e.ownerDocument, e) && "none" === w.css(e, "display")
            },
            se = function(e, t, n, r) {
                var i, o, a = {};
                for (o in t) a[o] = e.style[o], e.style[o] = t[o];
                i = n.apply(e, r || []);
                for (o in t) e.style[o] = a[o];
                return i
            };

        function ue(e, t, n, r) {
            var i, o, a = 20,
                s = r ? function() {
                    return r.cur()
                } : function() {
                    return w.css(e, t, "")
                },
                u = s(),
                l = n && n[3] || (w.cssNumber[t] ? "" : "px"),
                c = (w.cssNumber[t] || "px" !== l && +u) && ie.exec(w.css(e, t));
            if (c && c[3] !== l) {
                u /= 2, l = l || c[3], c = +u || 1;
                while (a--) w.style(e, t, c + l), (1 - o) * (1 - (o = s() / u || .5)) <= 0 && (a = 0), c /= o;
                c *= 2, w.style(e, t, c + l), n = n || []
            }
            return n && (c = +c || +u || 0, i = n[1] ? c + (n[1] + 1) * n[2] : +n[2], r && (r.unit = l, r.start = c, r.end = i)), i
        }
        var le = {};

        function ce(e) {
            var t, n = e.ownerDocument,
                r = e.nodeName,
                i = le[r];
            return i || (t = n.body.appendChild(n.createElement(r)), i = w.css(t, "display"), t.parentNode.removeChild(t), "none" === i && (i = "block"), le[r] = i, i)
        }

        function fe(e, t) {
            for (var n, r, i = [], o = 0, a = e.length; o < a; o++)(r = e[o]).style && (n = r.style.display, t ? ("none" === n && (i[o] = J.get(r, "display") || null, i[o] || (r.style.display = "")), "" === r.style.display && ae(r) && (i[o] = ce(r))) : "none" !== n && (i[o] = "none", J.set(r, "display", n)));
            for (o = 0; o < a; o++) null != i[o] && (e[o].style.display = i[o]);
            return e
        }
        w.fn.extend({
            show: function() {
                return fe(this, !0)
            },
            hide: function() {
                return fe(this)
            },
            toggle: function(e) {
                return "boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function() {
                    ae(this) ? w(this).show() : w(this).hide()
                })
            }
        });
        var pe = /^(?:checkbox|radio)$/i,
            de = /<([a-z][^\/\0>\x20\t\r\n\f]+)/i,
            he = /^$|^module$|\/(?:java|ecma)script/i,
            ge = {
                option: [1, "<select multiple='multiple'>", "</select>"],
                thead: [1, "<table>", "</table>"],
                col: [2, "<table><colgroup>", "</colgroup></table>"],
                tr: [2, "<table><tbody>", "</tbody></table>"],
                td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
                _default: [0, "", ""]
            };
        ge.optgroup = ge.option, ge.tbody = ge.tfoot = ge.colgroup = ge.caption = ge.thead, ge.th = ge.td;

        function ye(e, t) {
            var n;
            return n = "undefined" != typeof e.getElementsByTagName ? e.getElementsByTagName(t || "*") : "undefined" != typeof e.querySelectorAll ? e.querySelectorAll(t || "*") : [], void 0 === t || t && N(e, t) ? w.merge([e], n) : n
        }

        function ve(e, t) {
            for (var n = 0, r = e.length; n < r; n++) J.set(e[n], "globalEval", !t || J.get(t[n], "globalEval"))
        }
        var me = /<|&#?\w+;/;

        function xe(e, t, n, r, i) {
            for (var o, a, s, u, l, c, f = t.createDocumentFragment(), p = [], d = 0, h = e.length; d < h; d++)
                if ((o = e[d]) || 0 === o)
                    if ("object" === x(o)) w.merge(p, o.nodeType ? [o] : o);
                    else if (me.test(o)) {
                a = a || f.appendChild(t.createElement("div")), s = (de.exec(o) || ["", ""])[1].toLowerCase(), u = ge[s] || ge._default, a.innerHTML = u[1] + w.htmlPrefilter(o) + u[2], c = u[0];
                while (c--) a = a.lastChild;
                w.merge(p, a.childNodes), (a = f.firstChild).textContent = ""
            } else p.push(t.createTextNode(o));
            f.textContent = "", d = 0;
            while (o = p[d++])
                if (r && w.inArray(o, r) > -1) i && i.push(o);
                else if (l = w.contains(o.ownerDocument, o), a = ye(f.appendChild(o), "script"), l && ve(a), n) {
                c = 0;
                while (o = a[c++]) he.test(o.type || "") && n.push(o)
            }
            return f
        }! function() {
            var e = r.createDocumentFragment().appendChild(r.createElement("div")),
                t = r.createElement("input");
            t.setAttribute("type", "radio"), t.setAttribute("checked", "checked"), t.setAttribute("name", "t"), e.appendChild(t), h.checkClone = e.cloneNode(!0).cloneNode(!0).lastChild.checked, e.innerHTML = "<textarea>x</textarea>", h.noCloneChecked = !!e.cloneNode(!0).lastChild.defaultValue
        }();
        var be = r.documentElement,
            we = /^key/,
            Te = /^(?:mouse|pointer|contextmenu|drag|drop)|click/,
            Ce = /^([^.]*)(?:\.(.+)|)/;

        function Ee() {
            return !0
        }

        function ke() {
            return !1
        }

        function Se() {
            try {
                return r.activeElement
            } catch (e) {}
        }

        function De(e, t, n, r, i, o) {
            var a, s;
            if ("object" == typeof t) {
                "string" != typeof n && (r = r || n, n = void 0);
                for (s in t) De(e, s, n, r, t[s], o);
                return e
            }
            if (null == r && null == i ? (i = n, r = n = void 0) : null == i && ("string" == typeof n ? (i = r, r = void 0) : (i = r, r = n, n = void 0)), !1 === i) i = ke;
            else if (!i) return e;
            return 1 === o && (a = i, (i = function(e) {
                return w().off(e), a.apply(this, arguments)
            }).guid = a.guid || (a.guid = w.guid++)), e.each(function() {
                w.event.add(this, t, i, r, n)
            })
        }
        w.event = {
            global: {},
            add: function(e, t, n, r, i) {
                var o, a, s, u, l, c, f, p, d, h, g, y = J.get(e);
                if (y) {
                    n.handler && (n = (o = n).handler, i = o.selector), i && w.find.matchesSelector(be, i), n.guid || (n.guid = w.guid++), (u = y.events) || (u = y.events = {}), (a = y.handle) || (a = y.handle = function(t) {
                        return "undefined" != typeof w && w.event.triggered !== t.type ? w.event.dispatch.apply(e, arguments) : void 0
                    }), l = (t = (t || "").match(M) || [""]).length;
                    while (l--) d = g = (s = Ce.exec(t[l]) || [])[1], h = (s[2] || "").split(".").sort(), d && (f = w.event.special[d] || {}, d = (i ? f.delegateType : f.bindType) || d, f = w.event.special[d] || {}, c = w.extend({
                        type: d,
                        origType: g,
                        data: r,
                        handler: n,
                        guid: n.guid,
                        selector: i,
                        needsContext: i && w.expr.match.needsContext.test(i),
                        namespace: h.join(".")
                    }, o), (p = u[d]) || ((p = u[d] = []).delegateCount = 0, f.setup && !1 !== f.setup.call(e, r, h, a) || e.addEventListener && e.addEventListener(d, a)), f.add && (f.add.call(e, c), c.handler.guid || (c.handler.guid = n.guid)), i ? p.splice(p.delegateCount++, 0, c) : p.push(c), w.event.global[d] = !0)
                }
            },
            remove: function(e, t, n, r, i) {
                var o, a, s, u, l, c, f, p, d, h, g, y = J.hasData(e) && J.get(e);
                if (y && (u = y.events)) {
                    l = (t = (t || "").match(M) || [""]).length;
                    while (l--)
                        if (s = Ce.exec(t[l]) || [], d = g = s[1], h = (s[2] || "").split(".").sort(), d) {
                            f = w.event.special[d] || {}, p = u[d = (r ? f.delegateType : f.bindType) || d] || [], s = s[2] && new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)"), a = o = p.length;
                            while (o--) c = p[o], !i && g !== c.origType || n && n.guid !== c.guid || s && !s.test(c.namespace) || r && r !== c.selector && ("**" !== r || !c.selector) || (p.splice(o, 1), c.selector && p.delegateCount--, f.remove && f.remove.call(e, c));
                            a && !p.length && (f.teardown && !1 !== f.teardown.call(e, h, y.handle) || w.removeEvent(e, d, y.handle), delete u[d])
                        } else
                            for (d in u) w.event.remove(e, d + t[l], n, r, !0);
                    w.isEmptyObject(u) && J.remove(e, "handle events")
                }
            },
            dispatch: function(e) {
                var t = w.event.fix(e),
                    n, r, i, o, a, s, u = new Array(arguments.length),
                    l = (J.get(this, "events") || {})[t.type] || [],
                    c = w.event.special[t.type] || {};
                for (u[0] = t, n = 1; n < arguments.length; n++) u[n] = arguments[n];
                if (t.delegateTarget = this, !c.preDispatch || !1 !== c.preDispatch.call(this, t)) {
                    s = w.event.handlers.call(this, t, l), n = 0;
                    while ((o = s[n++]) && !t.isPropagationStopped()) {
                        t.currentTarget = o.elem, r = 0;
                        while ((a = o.handlers[r++]) && !t.isImmediatePropagationStopped()) t.rnamespace && !t.rnamespace.test(a.namespace) || (t.handleObj = a, t.data = a.data, void 0 !== (i = ((w.event.special[a.origType] || {}).handle || a.handler).apply(o.elem, u)) && !1 === (t.result = i) && (t.preventDefault(), t.stopPropagation()))
                    }
                    return c.postDispatch && c.postDispatch.call(this, t), t.result
                }
            },
            handlers: function(e, t) {
                var n, r, i, o, a, s = [],
                    u = t.delegateCount,
                    l = e.target;
                if (u && l.nodeType && !("click" === e.type && e.button >= 1))
                    for (; l !== this; l = l.parentNode || this)
                        if (1 === l.nodeType && ("click" !== e.type || !0 !== l.disabled)) {
                            for (o = [], a = {}, n = 0; n < u; n++) void 0 === a[i = (r = t[n]).selector + " "] && (a[i] = r.needsContext ? w(i, this).index(l) > -1 : w.find(i, this, null, [l]).length), a[i] && o.push(r);
                            o.length && s.push({
                                elem: l,
                                handlers: o
                            })
                        }
                return l = this, u < t.length && s.push({
                    elem: l,
                    handlers: t.slice(u)
                }), s
            },
            addProp: function(e, t) {
                Object.defineProperty(w.Event.prototype, e, {
                    enumerable: !0,
                    configurable: !0,
                    get: g(t) ? function() {
                        if (this.originalEvent) return t(this.originalEvent)
                    } : function() {
                        if (this.originalEvent) return this.originalEvent[e]
                    },
                    set: function(t) {
                        Object.defineProperty(this, e, {
                            enumerable: !0,
                            configurable: !0,
                            writable: !0,
                            value: t
                        })
                    }
                })
            },
            fix: function(e) {
                return e[w.expando] ? e : new w.Event(e)
            },
            special: {
                load: {
                    noBubble: !0
                },
                focus: {
                    trigger: function() {
                        if (this !== Se() && this.focus) return this.focus(), !1
                    },
                    delegateType: "focusin"
                },
                blur: {
                    trigger: function() {
                        if (this === Se() && this.blur) return this.blur(), !1
                    },
                    delegateType: "focusout"
                },
                click: {
                    trigger: function() {
                        if ("checkbox" === this.type && this.click && N(this, "input")) return this.click(), !1
                    },
                    _default: function(e) {
                        return N(e.target, "a")
                    }
                },
                beforeunload: {
                    postDispatch: function(e) {
                        void 0 !== e.result && e.originalEvent && (e.originalEvent.returnValue = e.result)
                    }
                }
            }
        }, w.removeEvent = function(e, t, n) {
            e.removeEventListener && e.removeEventListener(t, n)
        }, w.Event = function(e, t) {
            if (!(this instanceof w.Event)) return new w.Event(e, t);
            e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || void 0 === e.defaultPrevented && !1 === e.returnValue ? Ee : ke, this.target = e.target && 3 === e.target.nodeType ? e.target.parentNode : e.target, this.currentTarget = e.currentTarget, this.relatedTarget = e.relatedTarget) : this.type = e, t && w.extend(this, t), this.timeStamp = e && e.timeStamp || Date.now(), this[w.expando] = !0
        }, w.Event.prototype = {
            constructor: w.Event,
            isDefaultPrevented: ke,
            isPropagationStopped: ke,
            isImmediatePropagationStopped: ke,
            isSimulated: !1,
            preventDefault: function() {
                var e = this.originalEvent;
                this.isDefaultPrevented = Ee, e && !this.isSimulated && e.preventDefault()
            },
            stopPropagation: function() {
                var e = this.originalEvent;
                this.isPropagationStopped = Ee, e && !this.isSimulated && e.stopPropagation()
            },
            stopImmediatePropagation: function() {
                var e = this.originalEvent;
                this.isImmediatePropagationStopped = Ee, e && !this.isSimulated && e.stopImmediatePropagation(), this.stopPropagation()
            }
        }, w.each({
            altKey: !0,
            bubbles: !0,
            cancelable: !0,
            changedTouches: !0,
            ctrlKey: !0,
            detail: !0,
            eventPhase: !0,
            metaKey: !0,
            pageX: !0,
            pageY: !0,
            shiftKey: !0,
            view: !0,
            "char": !0,
            charCode: !0,
            key: !0,
            keyCode: !0,
            button: !0,
            buttons: !0,
            clientX: !0,
            clientY: !0,
            offsetX: !0,
            offsetY: !0,
            pointerId: !0,
            pointerType: !0,
            screenX: !0,
            screenY: !0,
            targetTouches: !0,
            toElement: !0,
            touches: !0,
            which: function(e) {
                var t = e.button;
                return null == e.which && we.test(e.type) ? null != e.charCode ? e.charCode : e.keyCode : !e.which && void 0 !== t && Te.test(e.type) ? 1 & t ? 1 : 2 & t ? 3 : 4 & t ? 2 : 0 : e.which
            }
        }, w.event.addProp), w.each({
            mouseenter: "mouseover",
            mouseleave: "mouseout",
            pointerenter: "pointerover",
            pointerleave: "pointerout"
        }, function(e, t) {
            w.event.special[e] = {
                delegateType: t,
                bindType: t,
                handle: function(e) {
                    var n, r = this,
                        i = e.relatedTarget,
                        o = e.handleObj;
                    return i && (i === r || w.contains(r, i)) || (e.type = o.origType, n = o.handler.apply(this, arguments), e.type = t), n
                }
            }
        }), w.fn.extend({
            on: function(e, t, n, r) {
                return De(this, e, t, n, r)
            },
            one: function(e, t, n, r) {
                return De(this, e, t, n, r, 1)
            },
            off: function(e, t, n) {
                var r, i;
                if (e && e.preventDefault && e.handleObj) return r = e.handleObj, w(e.delegateTarget).off(r.namespace ? r.origType + "." + r.namespace : r.origType, r.selector, r.handler), this;
                if ("object" == typeof e) {
                    for (i in e) this.off(i, t, e[i]);
                    return this
                }
                return !1 !== t && "function" != typeof t || (n = t, t = void 0), !1 === n && (n = ke), this.each(function() {
                    w.event.remove(this, e, n, t)
                })
            }
        });
        var Ne = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([a-z][^\/\0>\x20\t\r\n\f]*)[^>]*)\/>/gi,
            Ae = /<script|<style|<link/i,
            je = /checked\s*(?:[^=]|=\s*.checked.)/i,
            qe = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g;

        function Le(e, t) {
            return N(e, "table") && N(11 !== t.nodeType ? t : t.firstChild, "tr") ? w(e).children("tbody")[0] || e : e
        }

        function He(e) {
            return e.type = (null !== e.getAttribute("type")) + "/" + e.type, e
        }

        function Oe(e) {
            return "true/" === (e.type || "").slice(0, 5) ? e.type = e.type.slice(5) : e.removeAttribute("type"), e
        }

        function Pe(e, t) {
            var n, r, i, o, a, s, u, l;
            if (1 === t.nodeType) {
                if (J.hasData(e) && (o = J.access(e), a = J.set(t, o), l = o.events)) {
                    delete a.handle, a.events = {};
                    for (i in l)
                        for (n = 0, r = l[i].length; n < r; n++) w.event.add(t, i, l[i][n])
                }
                K.hasData(e) && (s = K.access(e), u = w.extend({}, s), K.set(t, u))
            }
        }

        function Me(e, t) {
            var n = t.nodeName.toLowerCase();
            "input" === n && pe.test(e.type) ? t.checked = e.checked : "input" !== n && "textarea" !== n || (t.defaultValue = e.defaultValue)
        }

        function Re(e, t, n, r) {
            t = a.apply([], t);
            var i, o, s, u, l, c, f = 0,
                p = e.length,
                d = p - 1,
                y = t[0],
                v = g(y);
            if (v || p > 1 && "string" == typeof y && !h.checkClone && je.test(y)) return e.each(function(i) {
                var o = e.eq(i);
                v && (t[0] = y.call(this, i, o.html())), Re(o, t, n, r)
            });
            if (p && (i = xe(t, e[0].ownerDocument, !1, e, r), o = i.firstChild, 1 === i.childNodes.length && (i = o), o || r)) {
                for (u = (s = w.map(ye(i, "script"), He)).length; f < p; f++) l = i, f !== d && (l = w.clone(l, !0, !0), u && w.merge(s, ye(l, "script"))), n.call(e[f], l, f);
                if (u)
                    for (c = s[s.length - 1].ownerDocument, w.map(s, Oe), f = 0; f < u; f++) l = s[f], he.test(l.type || "") && !J.access(l, "globalEval") && w.contains(c, l) && (l.src && "module" !== (l.type || "").toLowerCase() ? w._evalUrl && w._evalUrl(l.src) : m(l.textContent.replace(qe, ""), c, l))
            }
            return e
        }

        function Ie(e, t, n) {
            for (var r, i = t ? w.filter(t, e) : e, o = 0; null != (r = i[o]); o++) n || 1 !== r.nodeType || w.cleanData(ye(r)), r.parentNode && (n && w.contains(r.ownerDocument, r) && ve(ye(r, "script")), r.parentNode.removeChild(r));
            return e
        }
        w.extend({
            htmlPrefilter: function(e) {
                return e.replace(Ne, "<$1></$2>")
            },
            clone: function(e, t, n) {
                var r, i, o, a, s = e.cloneNode(!0),
                    u = w.contains(e.ownerDocument, e);
                if (!(h.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || w.isXMLDoc(e)))
                    for (a = ye(s), r = 0, i = (o = ye(e)).length; r < i; r++) Me(o[r], a[r]);
                if (t)
                    if (n)
                        for (o = o || ye(e), a = a || ye(s), r = 0, i = o.length; r < i; r++) Pe(o[r], a[r]);
                    else Pe(e, s);
                return (a = ye(s, "script")).length > 0 && ve(a, !u && ye(e, "script")), s
            },
            cleanData: function(e) {
                for (var t, n, r, i = w.event.special, o = 0; void 0 !== (n = e[o]); o++)
                    if (Y(n)) {
                        if (t = n[J.expando]) {
                            if (t.events)
                                for (r in t.events) i[r] ? w.event.remove(n, r) : w.removeEvent(n, r, t.handle);
                            n[J.expando] = void 0
                        }
                        n[K.expando] && (n[K.expando] = void 0)
                    }
            }
        }), w.fn.extend({
            detach: function(e) {
                return Ie(this, e, !0)
            },
            remove: function(e) {
                return Ie(this, e)
            },
            text: function(e) {
                return z(this, function(e) {
                    return void 0 === e ? w.text(this) : this.empty().each(function() {
                        1 !== this.nodeType && 11 !== this.nodeType && 9 !== this.nodeType || (this.textContent = e)
                    })
                }, null, e, arguments.length)
            },
            append: function() {
                return Re(this, arguments, function(e) {
                    1 !== this.nodeType && 11 !== this.nodeType && 9 !== this.nodeType || Le(this, e).appendChild(e)
                })
            },
            prepend: function() {
                return Re(this, arguments, function(e) {
                    if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                        var t = Le(this, e);
                        t.insertBefore(e, t.firstChild)
                    }
                })
            },
            before: function() {
                return Re(this, arguments, function(e) {
                    this.parentNode && this.parentNode.insertBefore(e, this)
                })
            },
            after: function() {
                return Re(this, arguments, function(e) {
                    this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
                })
            },
            empty: function() {
                for (var e, t = 0; null != (e = this[t]); t++) 1 === e.nodeType && (w.cleanData(ye(e, !1)), e.textContent = "");
                return this
            },
            clone: function(e, t) {
                return e = null != e && e, t = null == t ? e : t, this.map(function() {
                    return w.clone(this, e, t)
                })
            },
            html: function(e) {
                return z(this, function(e) {
                    var t = this[0] || {},
                        n = 0,
                        r = this.length;
                    if (void 0 === e && 1 === t.nodeType) return t.innerHTML;
                    if ("string" == typeof e && !Ae.test(e) && !ge[(de.exec(e) || ["", ""])[1].toLowerCase()]) {
                        e = w.htmlPrefilter(e);
                        try {
                            for (; n < r; n++) 1 === (t = this[n] || {}).nodeType && (w.cleanData(ye(t, !1)), t.innerHTML = e);
                            t = 0
                        } catch (e) {}
                    }
                    t && this.empty().append(e)
                }, null, e, arguments.length)
            },
            replaceWith: function() {
                var e = [];
                return Re(this, arguments, function(t) {
                    var n = this.parentNode;
                    w.inArray(this, e) < 0 && (w.cleanData(ye(this)), n && n.replaceChild(t, this))
                }, e)
            }
        }), w.each({
            appendTo: "append",
            prependTo: "prepend",
            insertBefore: "before",
            insertAfter: "after",
            replaceAll: "replaceWith"
        }, function(e, t) {
            w.fn[e] = function(e) {
                for (var n, r = [], i = w(e), o = i.length - 1, a = 0; a <= o; a++) n = a === o ? this : this.clone(!0), w(i[a])[t](n), s.apply(r, n.get());
                return this.pushStack(r)
            }
        });
        var We = new RegExp("^(" + re + ")(?!px)[a-z%]+$", "i"),
            $e = function(t) {
                var n = t.ownerDocument.defaultView;
                return n && n.opener || (n = e), n.getComputedStyle(t)
            },
            Be = new RegExp(oe.join("|"), "i");
        ! function() {
            function t() {
                if (c) {
                    l.style.cssText = "position:absolute;left:-11111px;width:60px;margin-top:1px;padding:0;border:0", c.style.cssText = "position:relative;display:block;box-sizing:border-box;overflow:scroll;margin:auto;border:1px;padding:1px;width:60%;top:1%", be.appendChild(l).appendChild(c);
                    var t = e.getComputedStyle(c);
                    i = "1%" !== t.top, u = 12 === n(t.marginLeft), c.style.right = "60%", s = 36 === n(t.right), o = 36 === n(t.width), c.style.position = "absolute", a = 36 === c.offsetWidth || "absolute", be.removeChild(l), c = null
                }
            }

            function n(e) {
                return Math.round(parseFloat(e))
            }
            var i, o, a, s, u, l = r.createElement("div"),
                c = r.createElement("div");
            c.style && (c.style.backgroundClip = "content-box", c.cloneNode(!0).style.backgroundClip = "", h.clearCloneStyle = "content-box" === c.style.backgroundClip, w.extend(h, {
                boxSizingReliable: function() {
                    return t(), o
                },
                pixelBoxStyles: function() {
                    return t(), s
                },
                pixelPosition: function() {
                    return t(), i
                },
                reliableMarginLeft: function() {
                    return t(), u
                },
                scrollboxSize: function() {
                    return t(), a
                }
            }))
        }();

        function Fe(e, t, n) {
            var r, i, o, a, s = e.style;
            return (n = n || $e(e)) && ("" !== (a = n.getPropertyValue(t) || n[t]) || w.contains(e.ownerDocument, e) || (a = w.style(e, t)), !h.pixelBoxStyles() && We.test(a) && Be.test(t) && (r = s.width, i = s.minWidth, o = s.maxWidth, s.minWidth = s.maxWidth = s.width = a, a = n.width, s.width = r, s.minWidth = i, s.maxWidth = o)), void 0 !== a ? a + "" : a
        }

        function _e(e, t) {
            return {
                get: function() {
                    if (!e()) return (this.get = t).apply(this, arguments);
                    delete this.get
                }
            }
        }
        var ze = /^(none|table(?!-c[ea]).+)/,
            Xe = /^--/,
            Ue = {
                position: "absolute",
                visibility: "hidden",
                display: "block"
            },
            Ve = {
                letterSpacing: "0",
                fontWeight: "400"
            },
            Ge = ["Webkit", "Moz", "ms"],
            Ye = r.createElement("div").style;

        function Qe(e) {
            if (e in Ye) return e;
            var t = e[0].toUpperCase() + e.slice(1),
                n = Ge.length;
            while (n--)
                if ((e = Ge[n] + t) in Ye) return e
        }

        function Je(e) {
            var t = w.cssProps[e];
            return t || (t = w.cssProps[e] = Qe(e) || e), t
        }

        function Ke(e, t, n) {
            var r = ie.exec(t);
            return r ? Math.max(0, r[2] - (n || 0)) + (r[3] || "px") : t
        }

        function Ze(e, t, n, r, i, o) {
            var a = "width" === t ? 1 : 0,
                s = 0,
                u = 0;
            if (n === (r ? "border" : "content")) return 0;
            for (; a < 4; a += 2) "margin" === n && (u += w.css(e, n + oe[a], !0, i)), r ? ("content" === n && (u -= w.css(e, "padding" + oe[a], !0, i)), "margin" !== n && (u -= w.css(e, "border" + oe[a] + "Width", !0, i))) : (u += w.css(e, "padding" + oe[a], !0, i), "padding" !== n ? u += w.css(e, "border" + oe[a] + "Width", !0, i) : s += w.css(e, "border" + oe[a] + "Width", !0, i));
            return !r && o >= 0 && (u += Math.max(0, Math.ceil(e["offset" + t[0].toUpperCase() + t.slice(1)] - o - u - s - .5))), u
        }

        function et(e, t, n) {
            var r = $e(e),
                i = Fe(e, t, r),
                o = "border-box" === w.css(e, "boxSizing", !1, r),
                a = o;
            if (We.test(i)) {
                if (!n) return i;
                i = "auto"
            }
            return a = a && (h.boxSizingReliable() || i === e.style[t]), ("auto" === i || !parseFloat(i) && "inline" === w.css(e, "display", !1, r)) && (i = e["offset" + t[0].toUpperCase() + t.slice(1)], a = !0), (i = parseFloat(i) || 0) + Ze(e, t, n || (o ? "border" : "content"), a, r, i) + "px"
        }
        w.extend({
            cssHooks: {
                opacity: {
                    get: function(e, t) {
                        if (t) {
                            var n = Fe(e, "opacity");
                            return "" === n ? "1" : n
                        }
                    }
                }
            },
            cssNumber: {
                animationIterationCount: !0,
                columnCount: !0,
                fillOpacity: !0,
                flexGrow: !0,
                flexShrink: !0,
                fontWeight: !0,
                lineHeight: !0,
                opacity: !0,
                order: !0,
                orphans: !0,
                widows: !0,
                zIndex: !0,
                zoom: !0
            },
            cssProps: {},
            style: function(e, t, n, r) {
                if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
                    var i, o, a, s = G(t),
                        u = Xe.test(t),
                        l = e.style;
                    if (u || (t = Je(s)), a = w.cssHooks[t] || w.cssHooks[s], void 0 === n) return a && "get" in a && void 0 !== (i = a.get(e, !1, r)) ? i : l[t];
                    "string" == (o = typeof n) && (i = ie.exec(n)) && i[1] && (n = ue(e, t, i), o = "number"), null != n && n === n && ("number" === o && (n += i && i[3] || (w.cssNumber[s] ? "" : "px")), h.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (l[t] = "inherit"), a && "set" in a && void 0 === (n = a.set(e, n, r)) || (u ? l.setProperty(t, n) : l[t] = n))
                }
            },
            css: function(e, t, n, r) {
                var i, o, a, s = G(t);
                return Xe.test(t) || (t = Je(s)), (a = w.cssHooks[t] || w.cssHooks[s]) && "get" in a && (i = a.get(e, !0, n)), void 0 === i && (i = Fe(e, t, r)), "normal" === i && t in Ve && (i = Ve[t]), "" === n || n ? (o = parseFloat(i), !0 === n || isFinite(o) ? o || 0 : i) : i
            }
        }), w.each(["height", "width"], function(e, t) {
            w.cssHooks[t] = {
                get: function(e, n, r) {
                    if (n) return !ze.test(w.css(e, "display")) || e.getClientRects().length && e.getBoundingClientRect().width ? et(e, t, r) : se(e, Ue, function() {
                        return et(e, t, r)
                    })
                },
                set: function(e, n, r) {
                    var i, o = $e(e),
                        a = "border-box" === w.css(e, "boxSizing", !1, o),
                        s = r && Ze(e, t, r, a, o);
                    return a && h.scrollboxSize() === o.position && (s -= Math.ceil(e["offset" + t[0].toUpperCase() + t.slice(1)] - parseFloat(o[t]) - Ze(e, t, "border", !1, o) - .5)), s && (i = ie.exec(n)) && "px" !== (i[3] || "px") && (e.style[t] = n, n = w.css(e, t)), Ke(e, n, s)
                }
            }
        }), w.cssHooks.marginLeft = _e(h.reliableMarginLeft, function(e, t) {
            if (t) return (parseFloat(Fe(e, "marginLeft")) || e.getBoundingClientRect().left - se(e, {
                marginLeft: 0
            }, function() {
                return e.getBoundingClientRect().left
            })) + "px"
        }), w.each({
            margin: "",
            padding: "",
            border: "Width"
        }, function(e, t) {
            w.cssHooks[e + t] = {
                expand: function(n) {
                    for (var r = 0, i = {}, o = "string" == typeof n ? n.split(" ") : [n]; r < 4; r++) i[e + oe[r] + t] = o[r] || o[r - 2] || o[0];
                    return i
                }
            }, "margin" !== e && (w.cssHooks[e + t].set = Ke)
        }), w.fn.extend({
            css: function(e, t) {
                return z(this, function(e, t, n) {
                    var r, i, o = {},
                        a = 0;
                    if (Array.isArray(t)) {
                        for (r = $e(e), i = t.length; a < i; a++) o[t[a]] = w.css(e, t[a], !1, r);
                        return o
                    }
                    return void 0 !== n ? w.style(e, t, n) : w.css(e, t)
                }, e, t, arguments.length > 1)
            }
        });

        function tt(e, t, n, r, i) {
            return new tt.prototype.init(e, t, n, r, i)
        }
        w.Tween = tt, tt.prototype = {
            constructor: tt,
            init: function(e, t, n, r, i, o) {
                this.elem = e, this.prop = n, this.easing = i || w.easing._default, this.options = t, this.start = this.now = this.cur(), this.end = r, this.unit = o || (w.cssNumber[n] ? "" : "px")
            },
            cur: function() {
                var e = tt.propHooks[this.prop];
                return e && e.get ? e.get(this) : tt.propHooks._default.get(this)
            },
            run: function(e) {
                var t, n = tt.propHooks[this.prop];
                return this.options.duration ? this.pos = t = w.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : this.pos = t = e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : tt.propHooks._default.set(this), this
            }
        }, tt.prototype.init.prototype = tt.prototype, tt.propHooks = {
            _default: {
                get: function(e) {
                    var t;
                    return 1 !== e.elem.nodeType || null != e.elem[e.prop] && null == e.elem.style[e.prop] ? e.elem[e.prop] : (t = w.css(e.elem, e.prop, "")) && "auto" !== t ? t : 0
                },
                set: function(e) {
                    w.fx.step[e.prop] ? w.fx.step[e.prop](e) : 1 !== e.elem.nodeType || null == e.elem.style[w.cssProps[e.prop]] && !w.cssHooks[e.prop] ? e.elem[e.prop] = e.now : w.style(e.elem, e.prop, e.now + e.unit)
                }
            }
        }, tt.propHooks.scrollTop = tt.propHooks.scrollLeft = {
            set: function(e) {
                e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
            }
        }, w.easing = {
            linear: function(e) {
                return e
            },
            swing: function(e) {
                return .5 - Math.cos(e * Math.PI) / 2
            },
            _default: "swing"
        }, w.fx = tt.prototype.init, w.fx.step = {};
        var nt, rt, it = /^(?:toggle|show|hide)$/,
            ot = /queueHooks$/;

        function at() {
            rt && (!1 === r.hidden && e.requestAnimationFrame ? e.requestAnimationFrame(at) : e.setTimeout(at, w.fx.interval), w.fx.tick())
        }

        function st() {
            return e.setTimeout(function() {
                nt = void 0
            }), nt = Date.now()
        }

        function ut(e, t) {
            var n, r = 0,
                i = {
                    height: e
                };
            for (t = t ? 1 : 0; r < 4; r += 2 - t) i["margin" + (n = oe[r])] = i["padding" + n] = e;
            return t && (i.opacity = i.width = e), i
        }

        function lt(e, t, n) {
            for (var r, i = (pt.tweeners[t] || []).concat(pt.tweeners["*"]), o = 0, a = i.length; o < a; o++)
                if (r = i[o].call(n, t, e)) return r
        }

        function ct(e, t, n) {
            var r, i, o, a, s, u, l, c, f = "width" in t || "height" in t,
                p = this,
                d = {},
               h = e.style,
                g = e.nodeType && ae(e),
                y = J.get(e, "fxshow");
            n.queue || (null == (a = w._queueHooks(e, "fx")).unqueued && (a.unqueued = 0, s = a.empty.fire, a.empty.fire = function() {
                a.unqueued || s()
            }), a.unqueued++, p.always(function() {
                p.always(function() {
                    a.unqueued--, w.queue(e, "fx").length || a.empty.fire()
                })
            }));
            for (r in t)
                if (i = t[r], it.test(i)) {
                    if (delete t[r], o = o || "toggle" === i, i === (g ? "hide" : "show")) {
                        if ("show" !== i || !y || void 0 === y[r]) continue;
                        g = !0
                    }
                    d[r] = y && y[r] || w.style(e, r)
                }
            if ((u = !w.isEmptyObject(t)) || !w.isEmptyObject(d)) {
                f && 1 === e.nodeType && (n.overflow = [h.overflow, h.overflowX, h.overflowY], null == (l = y && y.display) && (l = J.get(e, "display")), "none" === (c = w.css(e, "display")) && (l ? c = l : (fe([e], !0), l = e.style.display || l, c = w.css(e, "display"), fe([e]))), ("inline" === c || "inline-block" === c && null != l) && "none" === w.css(e, "float") && (u || (p.done(function() {
                    h.display = l
                }), null == l && (c = h.display, l = "none" === c ? "" : c)), h.display = "inline-block")), n.overflow && (h.overflow = "hidden", p.always(function() {
                    h.overflow = n.overflow[0], h.overflowX = n.overflow[1], h.overflowY = n.overflow[2]
                })), u = !1;
               for (r in d) u || (y ? "hidden" in y && (g = y.hidden) : y = J.access(e, "fxshow", {
                    display: l
                }), o && (y.hidden = !g), g && fe([e], !0), p.done(function() {
                    g || fe([e]), J.remove(e, "fxshow");
                    for (r in d) w.style(e, r, d[r])
                })), u = lt(g ? y[r] : 0, r, p), r in y || (y[r] = u.start, g && (u.end = u.start, u.start = 0))
            }
        }

        function ft(e, t) {
            var n, r, i, o, a;
            for (n in e)
                if (r = G(n), i = t[r], o = e[n], Array.isArray(o) && (i = o[1], o = e[n] = o[0]), n !== r && (e[r] = o, delete e[n]), (a = w.cssHooks[r]) && "expand" in a) {
                    o = a.expand(o), delete e[r];
                    for (n in o) n in e || (e[n] = o[n], t[n] = i)
                } else t[r] = i
        }

        function pt(e, t, n) {
            var r, i, o = 0,
                a = pt.prefilters.length,
                s = w.Deferred().always(function() {
                    delete u.elem
                }),
                u = function() {
                    if (i) return !1;
                    for (var t = nt || st(), n = Math.max(0, l.startTime + l.duration - t), r = 1 - (n / l.duration || 0), o = 0, a = l.tweens.length; o < a; o++) l.tweens[o].run(r);
                    return s.notifyWith(e, [l, r, n]), r < 1 && a ? n : (a || s.notifyWith(e, [l, 1, 0]), s.resolveWith(e, [l]), !1)
                },
                l = s.promise({
                    elem: e,
                    props: w.extend({}, t),
                    opts: w.extend(!0, {
                        specialEasing: {},
                        easing: w.easing._default
                    }, n),
                    originalProperties: t,
                    originalOptions: n,
                    startTime: nt || st(),
                    duration: n.duration,
                    tweens: [],
                    createTween: function(t, n) {
                        var r = w.Tween(e, l.opts, t, n, l.opts.specialEasing[t] || l.opts.easing);
                        return l.tweens.push(r), r
                    },
                    stop: function(t) {
                        var n = 0,
                            r = t ? l.tweens.length : 0;
                        if (i) return this;
                        for (i = !0; n < r; n++) l.tweens[n].run(1);
                        return t ? (s.notifyWith(e, [l, 1, 0]), s.resolveWith(e, [l, t])) : s.rejectWith(e, [l, t]), this
                    }
                }),
                c = l.props;
            for (ft(c, l.opts.specialEasing); o < a; o++)
                if (r = pt.prefilters[o].call(l, e, c, l.opts)) return g(r.stop) && (w._queueHooks(l.elem, l.opts.queue).stop = r.stop.bind(r)), r;
            return w.map(c, lt, l), g(l.opts.start) && l.opts.start.call(e, l), l.progress(l.opts.progress).done(l.opts.done, l.opts.complete).fail(l.opts.fail).always(l.opts.always), w.fx.timer(w.extend(u, {
                elem: e,
                anim: l,
                queue: l.opts.queue
            })), l
        }
        w.Animation = w.extend(pt, {
                tweeners: {
                    "*": [function(e, t) {
                        var n = this.createTween(e, t);
                        return ue(n.elem, e, ie.exec(t), n), n
                    }]
                },
                tweener: function(e, t) {
                    g(e) ? (t = e, e = ["*"]) : e = e.match(M);
                    for (var n, r = 0, i = e.length; r < i; r++) n = e[r], pt.tweeners[n] = pt.tweeners[n] || [], pt.tweeners[n].unshift(t)
                },
                prefilters: [ct],
                prefilter: function(e, t) {
                    t ? pt.prefilters.unshift(e) : pt.prefilters.push(e)
                }
            }), w.speed = function(e, t, n) {
                var r = e && "object" == typeof e ? w.extend({}, e) : {
                    complete: n || !n && t || g(e) && e,
                    duration: e,
                    easing: n && t || t && !g(t) && t
                };
                return w.fx.off ? r.duration = 0 : "number" != typeof r.duration && (r.duration in w.fx.speeds ? r.duration = w.fx.speeds[r.duration] : r.duration = w.fx.speeds._default), null != r.queue && !0 !== r.queue || (r.queue = "fx"), r.old = r.complete, r.complete = function() {
                    g(r.old) && r.old.call(this), r.queue && w.dequeue(this, r.queue)
                }, r
            }, w.fn.extend({
                fadeTo: function(e, t, n, r) {
                    return this.filter(ae).css("opacity", 0).show().end().animate({
                        opacity: t
                    }, e, n, r)
                },
                animate: function(e, t, n, r) {
                    var i = w.isEmptyObject(e),
                        o = w.speed(t, n, r),
                        a = function() {
                            var t = pt(this, w.extend({}, e), o);
                            (i || J.get(this, "finish")) && t.stop(!0)
                        };
                    return a.finish = a, i || !1 === o.queue ? this.each(a) : this.queue(o.queue, a)
                },
                stop: function(e, t, n) {
                    var r = function(e) {
                        var t = e.stop;
                        delete e.stop, t(n)
                    };
                    return "string" != typeof e && (n = t, t = e, e = void 0), t && !1 !== e && this.queue(e || "fx", []), this.each(function() {
                        var t = !0,
                            i = null != e && e + "queueHooks",
                            o = w.timers,
                            a = J.get(this);
                        if (i) a[i] && a[i].stop && r(a[i]);
                        else
                            for (i in a) a[i] && a[i].stop && ot.test(i) && r(a[i]);
                        for (i = o.length; i--;) o[i].elem !== this || null != e && o[i].queue !== e || (o[i].anim.stop(n), t = !1, o.splice(i, 1));
                        !t && n || w.dequeue(this, e)
                    })
                },
                finish: function(e) {
                    return !1 !== e && (e = e || "fx"), this.each(function() {
                        var t, n = J.get(this),
                            r = n[e + "queue"],
                            i = n[e + "queueHooks"],
                            o = w.timers,
                            a = r ? r.length : 0;
                        for (n.finish = !0, w.queue(this, e, []), i && i.stop && i.stop.call(this, !0), t = o.length; t--;) o[t].elem === this && o[t].queue === e && (o[t].anim.stop(!0), o.splice(t, 1));
                        for (t = 0; t < a; t++) r[t] && r[t].finish && r[t].finish.call(this);
                        delete n.finish
                    })
                }
            }), w.each(["toggle", "show", "hide"], function(e, t) {
                var n = w.fn[t];
                w.fn[t] = function(e, r, i) {
                    return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(ut(t, !0), e, r, i)
                }
            }), w.each({
                slideDown: ut("show"),
                slideUp: ut("hide"),
                slideToggle: ut("toggle"),
                fadeIn: {
                    opacity: "show"
                },
                fadeOut: {
                    opacity: "hide"
                },
                fadeToggle: {
                    opacity: "toggle"
                }
            }, function(e, t) {
                w.fn[e] = function(e, n, r) {
                    return this.animate(t, e, n, r)
                }
            }), w.timers = [], w.fx.tick = function() {
                var e, t = 0,
                    n = w.timers;
                for (nt = Date.now(); t < n.length; t++)(e = n[t])() || n[t] !== e || n.splice(t--, 1);
                n.length || w.fx.stop(), nt = void 0
            }, w.fx.timer = function(e) {
                w.timers.push(e), w.fx.start()
            }, w.fx.interval = 13, w.fx.start = function() {
                rt || (rt = !0, at())
            }, w.fx.stop = function() {
                rt = null
            }, w.fx.speeds = {
                slow: 600,
                fast: 200,
                _default: 400
            }, w.fn.delay = function(t, n) {
                return t = w.fx ? w.fx.speeds[t] || t : t, n = n || "fx", this.queue(n, function(n, r) {
                    var i = e.setTimeout(n, t);
                    r.stop = function() {
                        e.clearTimeout(i)
                    }
                })
            },
            function() {
                var e = r.createElement("input"),
                    t = r.createElement("select").appendChild(r.createElement("option"));
                e.type = "checkbox", h.checkOn = "" !== e.value, h.optSelected = t.selected, (e = r.createElement("input")).value = "t", e.type = "radio", h.radioValue = "t" === e.value
            }();
        var dt, ht = w.expr.attrHandle;
        w.fn.extend({
            attr: function(e, t) {
                return z(this, w.attr, e, t, arguments.length > 1)
            },
            removeAttr: function(e) {
                return this.each(function() {
                    w.removeAttr(this, e)
                })
            }
        }), w.extend({
            attr: function(e, t, n) {
                var r, i, o = e.nodeType;
                if (3 !== o && 8 !== o && 2 !== o) return "undefined" == typeof e.getAttribute ? w.prop(e, t, n) : (1 === o && w.isXMLDoc(e) || (i = w.attrHooks[t.toLowerCase()] || (w.expr.match.bool.test(t) ? dt : void 0)), void 0 !== n ? null === n ? void w.removeAttr(e, t) : i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : (e.setAttribute(t, n + ""), n) : i && "get" in i && null !== (r = i.get(e, t)) ? r : null == (r = w.find.attr(e, t)) ? void 0 : r)
            },
            attrHooks: {
                type: {
                    set: function(e, t) {
                        if (!h.radioValue && "radio" === t && N(e, "input")) {
                            var n = e.value;
                            return e.setAttribute("type", t), n && (e.value = n), t
                        }
                    }
                }
            },
            removeAttr: function(e, t) {
                var n, r = 0,
                    i = t && t.match(M);
                if (i && 1 === e.nodeType)
                   while (n = i[r++]) e.removeAttribute(n)
            }
        }), dt = {
            set: function(e, t, n) {
                return !1 === t ? w.removeAttr(e, n) : e.setAttribute(n, n), n
            }
        }, w.each(w.expr.match.bool.source.match(/\w+/g), function(e, t) {
            var n = ht[t] || w.find.attr;
            ht[t] = function(e, t, r) {
                var i, o, a = t.toLowerCase();
                return r || (o = ht[a], ht[a] = i, i = null != n(e, t, r) ? a : null, ht[a] = o), i
            }
        });
        var gt = /^(?:input|select|textarea|button)$/i,
            yt = /^(?:a|area)$/i;
        w.fn.extend({
            prop: function(e, t) {
                return z(this, w.prop, e, t, arguments.length > 1)
            },
            removeProp: function(e) {
                return this.each(function() {
                    delete this[w.propFix[e] || e]
                })
            }
        }), w.extend({
            prop: function(e, t, n) {
                var r, i, o = e.nodeType;
                if (3 !== o && 8 !== o && 2 !== o) return 1 === o && w.isXMLDoc(e) || (t = w.propFix[t] || t, i = w.propHooks[t]), void 0 !== n ? i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : e[t] = n : i && "get" in i && null !== (r = i.get(e, t)) ? r : e[t]
            },
            propHooks: {
                tabIndex: {
                    get: function(e) {
                        var t = w.find.attr(e, "tabindex");
                        return t ? parseInt(t, 10) : gt.test(e.nodeName) || yt.test(e.nodeName) && e.href ? 0 : -1
                    }
                }
            },
            propFix: {
                "for": "htmlFor",
                "class": "className"
            }
        }), h.optSelected || (w.propHooks.selected = {
            get: function(e) {
                var t = e.parentNode;
                return t && t.parentNode && t.parentNode.selectedIndex, null
            },
            set: function(e) {
                var t = e.parentNode;
                t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex)
            }
        }), w.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function() {
            w.propFix[this.toLowerCase()] = this
        });

        function vt(e) {
            return (e.match(M) || []).join(" ")
        }

        function mt(e) {
            return e.getAttribute && e.getAttribute("class") || ""
        }

        function xt(e) {
            return Array.isArray(e) ? e : "string" == typeof e ? e.match(M) || [] : []
        }
        w.fn.extend({
            addClass: function(e) {
                var t, n, r, i, o, a, s, u = 0;
                if (g(e)) return this.each(function(t) {
                    w(this).addClass(e.call(this, t, mt(this)))
                });
                if ((t = xt(e)).length)
                    while (n = this[u++])
                        if (i = mt(n), r = 1 === n.nodeType && " " + vt(i) + " ") {
                            a = 0;
                            while (o = t[a++]) r.indexOf(" " + o + " ") < 0 && (r += o + " ");
                            i !== (s = vt(r)) && n.setAttribute("class", s)
                        }
                return this
            },
            removeClass: function(e) {
                var t, n, r, i, o, a, s, u = 0;
                if (g(e)) return this.each(function(t) {
                    w(this).removeClass(e.call(this, t, mt(this)))
                });
                if (!arguments.length) return this.attr("class", "");
                if ((t = xt(e)).length)
                    while (n = this[u++])
                        if (i = mt(n), r = 1 === n.nodeType && " " + vt(i) + " ") {
                            a = 0;
                            while (o = t[a++])
                                while (r.indexOf(" " + o + " ") > -1) r = r.replace(" " + o + " ", " ");
                            i !== (s = vt(r)) && n.setAttribute("class", s)
                        }
                return this
            },
            toggleClass: function(e, t) {
                var n = typeof e,
                    r = "string" === n || Array.isArray(e);
                return "boolean" == typeof t && r ? t ? this.addClass(e) : this.removeClass(e) : g(e) ? this.each(function(n) {
                    w(this).toggleClass(e.call(this, n, mt(this), t), t)
                }) : this.each(function() {
                    var t, i, o, a;
                    if (r) {
                        i = 0, o = w(this), a = xt(e);
                        while (t = a[i++]) o.hasClass(t) ? o.removeClass(t) : o.addClass(t)
                    } else void 0 !== e && "boolean" !== n || ((t = mt(this)) && J.set(this, "__className__", t), this.setAttribute && this.setAttribute("class", t || !1 === e ? "" : J.get(this, "__className__") || ""))
                })
            },
            hasClass: function(e) {
                var t, n, r = 0;
                t = " " + e + " ";
                while (n = this[r++])
                    if (1 === n.nodeType && (" " + vt(mt(n)) + " ").indexOf(t) > -1) return !0;
                return !1
            }
        });
        var bt = /\r/g;
        w.fn.extend({
            val: function(e) {
                var t, n, r, i = this[0]; {
                    if (arguments.length) return r = g(e), this.each(function(n) {
                        var i;
                        1 === this.nodeType && (null == (i = r ? e.call(this, n, w(this).val()) : e) ? i = "" : "number" == typeof i ? i += "" : Array.isArray(i) && (i = w.map(i, function(e) {
                            return null == e ? "" : e + ""
                        })), (t = w.valHooks[this.type] || w.valHooks[this.nodeName.toLowerCase()]) && "set" in t && void 0 !== t.set(this, i, "value") || (this.value = i))
                    });
                    if (i) return (t = w.valHooks[i.type] || w.valHooks[i.nodeName.toLowerCase()]) && "get" in t && void 0 !== (n = t.get(i, "value")) ? n : "string" == typeof(n = i.value) ? n.replace(bt, "") : null == n ? "" : n
                }
            }
        }), w.extend({
            valHooks: {
                option: {
                    get: function(e) {
                        var t = w.find.attr(e, "value");
                        return null != t ? t : vt(w.text(e))
                    }
                },
                select: {
                    get: function(e) {
                        var t, n, r, i = e.options,
                            o = e.selectedIndex,
                            a = "select-one" === e.type,
                            s = a ? null : [],
                            u = a ? o + 1 : i.length;
                        for (r = o < 0 ? u : a ? o : 0; r < u; r++)
                            if (((n = i[r]).selected || r === o) && !n.disabled && (!n.parentNode.disabled || !N(n.parentNode, "optgroup"))) {
                                if (t = w(n).val(), a) return t;
                                s.push(t)
                            }
                        return s
                    },
                    set: function(e, t) {
                        var n, r, i = e.options,
                            o = w.makeArray(t),
                            a = i.length;
                        while (a--)((r = i[a]).selected = w.inArray(w.valHooks.option.get(r), o) > -1) && (n = !0);
                        return n || (e.selectedIndex = -1), o
                    }
                }
            }
        }), w.each(["radio", "checkbox"], function() {
            w.valHooks[this] = {
                set: function(e, t) {
                    if (Array.isArray(t)) return e.checked = w.inArray(w(e).val(), t) > -1
                }
            }, h.checkOn || (w.valHooks[this].get = function(e) {
                return null === e.getAttribute("value") ? "on" : e.value
            })
        }), h.focusin = "onfocusin" in e;
        var wt = /^(?:focusinfocus|focusoutblur)$/,
            Tt = function(e) {
                e.stopPropagation()
            };
        w.extend(w.event, {
            trigger: function(t, n, i, o) {
                var a, s, u, l, c, p, d, h, v = [i || r],
                    m = f.call(t, "type") ? t.type : t,
                    x = f.call(t, "namespace") ? t.namespace.split(".") : [];
                if (s = h = u = i = i || r, 3 !== i.nodeType && 8 !== i.nodeType && !wt.test(m + w.event.triggered) && (m.indexOf(".") > -1 && (m = (x = m.split(".")).shift(), x.sort()), c = m.indexOf(":") < 0 && "on" + m, t = t[w.expando] ? t : new w.Event(m, "object" == typeof t && t), t.isTrigger = o ? 2 : 3, t.namespace = x.join("."), t.rnamespace = t.namespace ? new RegExp("(^|\\.)" + x.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, t.result = void 0, t.target || (t.target = i), n = null == n ? [t] : w.makeArray(n, [t]), d = w.event.special[m] || {}, o || !d.trigger || !1 !== d.trigger.apply(i, n))) {
                    if (!o && !d.noBubble && !y(i)) {
                        for (l = d.delegateType || m, wt.test(l + m) || (s = s.parentNode); s; s = s.parentNode) v.push(s), u = s;
                        u === (i.ownerDocument || r) && v.push(u.defaultView || u.parentWindow || e)
                    }
                    a = 0;
                    while ((s = v[a++]) && !t.isPropagationStopped()) h = s, t.type = a > 1 ? l : d.bindType || m, (p = (J.get(s, "events") || {})[t.type] && J.get(s, "handle")) && p.apply(s, n), (p = c && s[c]) && p.apply && Y(s) && (t.result = p.apply(s, n), !1 === t.result && t.preventDefault());
                    return t.type = m, o || t.isDefaultPrevented() || d._default && !1 !== d._default.apply(v.pop(), n) || !Y(i) || c && g(i[m]) && !y(i) && ((u = i[c]) && (i[c] = null), w.event.triggered = m, t.isPropagationStopped() && h.addEventListener(m, Tt), i[m](), t.isPropagationStopped() && h.removeEventListener(m, Tt), w.event.triggered = void 0, u && (i[c] = u)), t.result
                }
            },
            simulate: function(e, t, n) {
                var r = w.extend(new w.Event, n, {
                    type: e,
                    isSimulated: !0
                });
                w.event.trigger(r, null, t)
            }
        }), w.fn.extend({
            trigger: function(e, t) {
                return this.each(function() {
                    w.event.trigger(e, t, this)
                })
            },
            triggerHandler: function(e, t) {
                var n = this[0];
                if (n) return w.event.trigger(e, t, n, !0)
            }
        }), h.focusin || w.each({
            focus: "focusin",
            blur: "focusout"
        }, function(e, t) {
            var n = function(e) {
                w.event.simulate(t, e.target, w.event.fix(e))
            };
            w.event.special[t] = {
                setup: function() {
                    var r = this.ownerDocument || this,
                        i = J.access(r, t);
                    i || r.addEventListener(e, n, !0), J.access(r, t, (i || 0) + 1)
                },
                teardown: function() {
                    var r = this.ownerDocument || this,
                        i = J.access(r, t) - 1;
                    i ? J.access(r, t, i) : (r.removeEventListener(e, n, !0), J.remove(r, t))
                }
            }
        });
        var Ct = e.location,
            Et = Date.now(),
            kt = /\?/;
        w.parseXML = function(t) {
            var n;
            if (!t || "string" != typeof t) return null;
            try {
                n = (new e.DOMParser).parseFromString(t, "text/xml")
            } catch (e) {
                n = void 0
            }
            return n && !n.getElementsByTagName("parsererror").length || w.error("Invalid XML: " + t), n
        };
        var St = /\[\]$/,
            Dt = /\r?\n/g,
            Nt = /^(?:submit|button|image|reset|file)$/i,
            At = /^(?:input|select|textarea|keygen)/i;

        function jt(e, t, n, r) {
            var i;
            if (Array.isArray(t)) w.each(t, function(t, i) {
                n || St.test(e) ? r(e, i) : jt(e + "[" + ("object" == typeof i && null != i ? t : "") + "]", i, n, r)
            });
            else if (n || "object" !== x(t)) r(e, t);
            else
                for (i in t) jt(e + "[" + i + "]", t[i], n, r)
        }
        w.param = function(e, t) {
            var n, r = [],
                i = function(e, t) {
                    var n = g(t) ? t() : t;
                    r[r.length] = encodeURIComponent(e) + "=" + encodeURIComponent(null == n ? "" : n)
                };
            if (Array.isArray(e) || e.jquery && !w.isPlainObject(e)) w.each(e, function() {
                i(this.name, this.value)
            });
            else
                for (n in e) jt(n, e[n], t, i);
            return r.join("&")
        }, w.fn.extend({
            serialize: function() {
                return w.param(this.serializeArray())
            },
            serializeArray: function() {
                return this.map(function() {
                    var e = w.prop(this, "elements");
                    return e ? w.makeArray(e) : this
                }).filter(function() {
                    var e = this.type;
                    return this.name && !w(this).is(":disabled") && At.test(this.nodeName) && !Nt.test(e) && (this.checked || !pe.test(e))
                }).map(function(e, t) {
                    var n = w(this).val();
                    return null == n ? null : Array.isArray(n) ? w.map(n, function(e) {
                        return {
                            name: t.name,
                            value: e.replace(Dt, "\r\n")
                        }
                    }) : {
                        name: t.name,
                        value: n.replace(Dt, "\r\n")
                    }
                }).get()
            }
       });
        var qt = /%20/g,
            Lt = /#.*$/,
            Ht = /([?&])_=[^&]*/,
            Ot = /^(.*?):[ \t]*([^\r\n]*)$/gm,
            Pt = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/,
            Mt = /^(?:GET|HEAD)$/,
            Rt = /^\/\//,
            It = {},
            Wt = {},
            $t = "*/".concat("*"),
            Bt = r.createElement("a");
        Bt.href = Ct.href;

        function Ft(e) {
            return function(t, n) {
                "string" != typeof t && (n = t, t = "*");
                var r, i = 0,
                    o = t.toLowerCase().match(M) || [];
                if (g(n))
                    while (r = o[i++]) "+" === r[0] ? (r = r.slice(1) || "*", (e[r] = e[r] || []).unshift(n)) : (e[r] = e[r] || []).push(n)
            }
        }

        function _t(e, t, n, r) {
            var i = {},
                o = e === Wt;

            function a(s) {
                var u;
                return i[s] = !0, w.each(e[s] || [], function(e, s) {
                    var l = s(t, n, r);
                    return "string" != typeof l || o || i[l] ? o ? !(u = l) : void 0 : (t.dataTypes.unshift(l), a(l), !1)
                }), u
            }
            return a(t.dataTypes[0]) || !i["*"] && a("*")
        }

        function zt(e, t) {
            var n, r, i = w.ajaxSettings.flatOptions || {};
            for (n in t) void 0 !== t[n] && ((i[n] ? e : r || (r = {}))[n] = t[n]);
            return r && w.extend(!0, e, r), e
        }

        function Xt(e, t, n) {
            var r, i, o, a, s = e.contents,
                u = e.dataTypes;
            while ("*" === u[0]) u.shift(), void 0 === r && (r = e.mimeType || t.getResponseHeader("Content-Type"));
            if (r)
                for (i in s)
                    if (s[i] && s[i].test(r)) {
                        u.unshift(i);
                        break
                    }
            if (u[0] in n) o = u[0];
            else {
                for (i in n) {
                    if (!u[0] || e.converters[i + " " + u[0]]) {
                        o = i;
                        break
                    }
                    a || (a = i)
                }
                o = o || a
            }
            if (o) return o !== u[0] && u.unshift(o), n[o]
        }

        function Ut(e, t, n, r) {
            var i, o, a, s, u, l = {},
                c = e.dataTypes.slice();
            if (c[1])
                for (a in e.converters) l[a.toLowerCase()] = e.converters[a];
            o = c.shift();
            while (o)
                if (e.responseFields[o] && (n[e.responseFields[o]] = t), !u && r && e.dataFilter && (t = e.dataFilter(t, e.dataType)), u = o, o = c.shift())
                    if ("*" === o) o = u;
                    else if ("*" !== u && u !== o) {
                if (!(a = l[u + " " + o] || l["* " + o]))
                    for (i in l)
                        if ((s = i.split(" "))[1] === o && (a = l[u + " " + s[0]] || l["* " + s[0]])) {
                            !0 === a ? a = l[i] : !0 !== l[i] && (o = s[0], c.unshift(s[1]));
                            break
                        }
                if (!0 !== a)
                    if (a && e["throws"]) t = a(t);
                    else try {
                        t = a(t)
                    } catch (e) {
                        return {
                            state: "parsererror",
                            error: a ? e : "No conversion from " + u + " to " + o
                        }
                    }
            }
            return {
                state: "success",
                data: t
            }
        }
        w.extend({
            active: 0,
            lastModified: {},
            etag: {},
            ajaxSettings: {
                url: Ct.href,
                type: "GET",
                isLocal: Pt.test(Ct.protocol),
                global: !0,
                processData: !0,
                async: !0,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                accepts: {
                    "*": $t,
                    text: "text/plain",
                    html: "text/html",
                    xml: "application/xml, text/xml",
                    json: "application/json, text/javascript"
                },
                contents: {
                    xml: /\bxml\b/,
                    html: /\bhtml/,
                    json: /\bjson\b/
                },
                responseFields: {
                    xml: "responseXML",
                    text: "responseText",
                    json: "responseJSON"
                },
                converters: {
                    "* text": String,
                    "text html": !0,
                    "text json": JSON.parse,
                    "text xml": w.parseXML
                },
                flatOptions: {
                    url: !0,
                    context: !0
                }
            },
            ajaxSetup: function(e, t) {
                return t ? zt(zt(e, w.ajaxSettings), t) : zt(w.ajaxSettings, e)
            },
            ajaxPrefilter: Ft(It),
            ajaxTransport: Ft(Wt),
            ajax: function(t, n) {
                "object" == typeof t && (n = t, t = void 0), n = n || {};
                var i, o, a, s, u, l, c, f, p, d, h = w.ajaxSetup({}, n),
                    g = h.context || h,
                    y = h.context && (g.nodeType || g.jquery) ? w(g) : w.event,
                    v = w.Deferred(),
                    m = w.Callbacks("once memory"),
                    x = h.statusCode || {},
                    b = {},
                    T = {},
                    C = "canceled",
                    E = {
                        readyState: 0,
                        getResponseHeader: function(e) {
                            var t;
                            if (c) {
                                if (!s) {
                                    s = {};
                                    while (t = Ot.exec(a)) s[t[1].toLowerCase()] = t[2]
                                }
                                t = s[e.toLowerCase()]
                            }
                            return null == t ? null : t
                        },
                        getAllResponseHeaders: function() {
                            return c ? a : null
                        },
                        setRequestHeader: function(e, t) {
                            return null == c && (e = T[e.toLowerCase()] = T[e.toLowerCase()] || e, b[e] = t), this
                        },
                        overrideMimeType: function(e) {
                            return null == c && (h.mimeType = e), this
                        },
                        statusCode: function(e) {
                            var t;
                            if (e)
                                if (c) E.always(e[E.status]);
                                else
                                    for (t in e) x[t] = [x[t], e[t]];
                            return this
                        },
                        abort: function(e) {
                            var t = e || C;
                            return i && i.abort(t), k(0, t), this
                        }
                    };
                if (v.promise(E), h.url = ((t || h.url || Ct.href) + "").replace(Rt, Ct.protocol + "//"), h.type = n.method || n.type || h.method || h.type, h.dataTypes = (h.dataType || "*").toLowerCase().match(M) || [""], null == h.crossDomain) {
                    l = r.createElement("a");
                    try {
                        l.href = h.url, l.href = l.href, h.crossDomain = Bt.protocol + "//" + Bt.host != l.protocol + "//" + l.host
                    } catch (e) {
                        h.crossDomain = !0
                    }
                }
                if (h.data && h.processData && "string" != typeof h.data && (h.data = w.param(h.data, h.traditional)), _t(It, h, n, E), c) return E;
                (f = w.event && h.global) && 0 == w.active++ && w.event.trigger("ajaxStart"), h.type = h.type.toUpperCase(), h.hasContent = !Mt.test(h.type), o = h.url.replace(Lt, ""), h.hasContent ? h.data && h.processData && 0 === (h.contentType || "").indexOf("application/x-www-form-urlencoded") && (h.data = h.data.replace(qt, "+")) : (d = h.url.slice(o.length), h.data && (h.processData || "string" == typeof h.data) && (o += (kt.test(o) ? "&" : "?") + h.data, delete h.data), !1 === h.cache && (o = o.replace(Ht, "$1"), d = (kt.test(o) ? "&" : "?") + "_=" + Et++ + d), h.url = o + d), h.ifModified && (w.lastModified[o] && E.setRequestHeader("If-Modified-Since", w.lastModified[o]), w.etag[o] && E.setRequestHeader("If-None-Match", w.etag[o])), (h.data && h.hasContent && !1 !== h.contentType || n.contentType) && E.setRequestHeader("Content-Type", h.contentType), E.setRequestHeader("Accept", h.dataTypes[0] && h.accepts[h.dataTypes[0]] ? h.accepts[h.dataTypes[0]] + ("*" !== h.dataTypes[0] ? ", " + $t + "; q=0.01" : "") : h.accepts["*"]);
                for (p in h.headers) E.setRequestHeader(p, h.headers[p]);
                if (h.beforeSend && (!1 === h.beforeSend.call(g, E, h) || c)) return E.abort();
                if (C = "abort", m.add(h.complete), E.done(h.success), E.fail(h.error), i = _t(Wt, h, n, E)) {
                    if (E.readyState = 1, f && y.trigger("ajaxSend", [E, h]), c) return E;
                    h.async && h.timeout > 0 && (u = e.setTimeout(function() {
                        E.abort("timeout")
                    }, h.timeout));
                    try {
                        c = !1, i.send(b, k)
                    } catch (e) {
                        if (c) throw e;
                        k(-1, e)
                    }
                } else k(-1, "No Transport");

                function k(t, n, r, s) {
                    var l, p, d, b, T, C = n;
                    c || (c = !0, u && e.clearTimeout(u), i = void 0, a = s || "", E.readyState = t > 0 ? 4 : 0, l = t >= 200 && t < 300 || 304 === t, r && (b = Xt(h, E, r)), b = Ut(h, b, E, l), l ? (h.ifModified && ((T = E.getResponseHeader("Last-Modified")) && (w.lastModified[o] = T), (T = E.getResponseHeader("etag")) && (w.etag[o] = T)), 204 === t || "HEAD" === h.type ? C = "nocontent" : 304 === t ? C = "notmodified" : (C = b.state, p = b.data, l = !(d = b.error))) : (d = C, !t && C || (C = "error", t < 0 && (t = 0))), E.status = t, E.statusText = (n || C) + "", l ? v.resolveWith(g, [p, C, E]) : v.rejectWith(g, [E, C, d]), E.statusCode(x), x = void 0, f && y.trigger(l ? "ajaxSuccess" : "ajaxError", [E, h, l ? p : d]), m.fireWith(g, [E, C]), f && (y.trigger("ajaxComplete", [E, h]), --w.active || w.event.trigger("ajaxStop")))
                }
                return E
            },
            getJSON: function(e, t, n) {
                return w.get(e, t, n, "json")
            },
            getScript: function(e, t) {
                return w.get(e, void 0, t, "script")
            }
        }), w.each(["get", "post"], function(e, t) {
            w[t] = function(e, n, r, i) {
                return g(n) && (i = i || r, r = n, n = void 0), w.ajax(w.extend({
                    url: e,
                    type: t,
                    dataType: i,
                    data: n,
                    success: r
                }, w.isPlainObject(e) && e))
            }
        }), w._evalUrl = function(e) {
            return w.ajax({
                url: e,
                type: "GET",
                dataType: "script",
                cache: !0,
                async: !1,
                global: !1,
                "throws": !0
            })
        }, w.fn.extend({
            wrapAll: function(e) {
                var t;
                return this[0] && (g(e) && (e = e.call(this[0])), t = w(e, this[0].ownerDocument).eq(0).clone(!0), this[0].parentNode && t.insertBefore(this[0]), t.map(function() {
                    var e = this;
                    while (e.firstElementChild) e = e.firstElementChild;
                    return e
                }).append(this)), this
           },
            wrapInner: function(e) {
                return g(e) ? this.each(function(t) {
                    w(this).wrapInner(e.call(this, t))
                }) : this.each(function() {
                    var t = w(this),
                        n = t.contents();
                    n.length ? n.wrapAll(e) : t.append(e)
                })
            },
            wrap: function(e) {
                var t = g(e);
                return this.each(function(n) {
                    w(this).wrapAll(t ? e.call(this, n) : e)
                })
            },
            unwrap: function(e) {
                return this.parent(e).not("body").each(function() {
                    w(this).replaceWith(this.childNodes)
                }), this
            }
        }), w.expr.pseudos.hidden = function(e) {
            return !w.expr.pseudos.visible(e)
        }, w.expr.pseudos.visible = function(e) {
            return !!(e.offsetWidth || e.offsetHeight || e.getClientRects().length)
        }, w.ajaxSettings.xhr = function() {
            try {
                return new e.XMLHttpRequest
            } catch (e) {}
        };
        var Vt = {
                0: 200,
                1223: 204
            },
            Gt = w.ajaxSettings.xhr();
        h.cors = !!Gt && "withCredentials" in Gt, h.ajax = Gt = !!Gt, w.ajaxTransport(function(t) {
            var n, r;
            if (h.cors || Gt && !t.crossDomain) return {
                send: function(i, o) {
                    var a, s = t.xhr();
                    if (s.open(t.type, t.url, t.async, t.username, t.password), t.xhrFields)
                        for (a in t.xhrFields) s[a] = t.xhrFields[a];
                    t.mimeType && s.overrideMimeType && s.overrideMimeType(t.mimeType), t.crossDomain || i["X-Requested-With"] || (i["X-Requested-With"] = "XMLHttpRequest");
                    for (a in i) s.setRequestHeader(a, i[a]);
                    n = function(e) {
                        return function() {
                            n && (n = r = s.onload = s.onerror = s.onabort = s.ontimeout = s.onreadystatechange = null, "abort" === e ? s.abort() : "error" === e ? "number" != typeof s.status ? o(0, "error") : o(s.status, s.statusText) : o(Vt[s.status] || s.status, s.statusText, "text" !== (s.responseType || "text") || "string" != typeof s.responseText ? {
                                binary: s.response
                            } : {
                                text: s.responseText
                            }, s.getAllResponseHeaders()))
                        }
                    }, s.onload = n(), r = s.onerror = s.ontimeout = n("error"), void 0 !== s.onabort ? s.onabort = r : s.onreadystatechange = function() {
                        4 === s.readyState && e.setTimeout(function() {
                            n && r()
                        })
                    }, n = n("abort");
                    try {
                        s.send(t.hasContent && t.data || null)
                    } catch (e) {
                        if (n) throw e
                    }
                },
                abort: function() {
                    n && n()
                }
            }
        }), w.ajaxPrefilter(function(e) {
            e.crossDomain && (e.contents.script = !1)
        }), w.ajaxSetup({
            accepts: {
                script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"
            },
            contents: {
                script: /\b(?:java|ecma)script\b/
            },
            converters: {
                "text script": function(e) {
                    return w.globalEval(e), e
                }
            }
        }), w.ajaxPrefilter("script", function(e) {
            void 0 === e.cache && (e.cache = !1), e.crossDomain && (e.type = "GET")
        }), w.ajaxTransport("script", function(e) {
            if (e.crossDomain) {
                var t, n;
                return {
                    send: function(i, o) {
                        t = w("<script>").prop({
                            charset: e.scriptCharset,
                            src: e.url
                        }).on("load error", n = function(e) {
                            t.remove(), n = null, e && o("error" === e.type ? 404 : 200, e.type)
                        }), r.head.appendChild(t[0])
                    },
                    abort: function() {
                        n && n()
                    }
                }
            }
        });
        var Yt = [],
            Qt = /(=)\?(?=&|$)|\?\?/;
        w.ajaxSetup({
            jsonp: "callback",
            jsonpCallback: function() {
                var e = Yt.pop() || w.expando + "_" + Et++;
                return this[e] = !0, e
            }
        }), w.ajaxPrefilter("json jsonp", function(t, n, r) {
            var i, o, a, s = !1 !== t.jsonp && (Qt.test(t.url) ? "url" : "string" == typeof t.data && 0 === (t.contentType || "").indexOf("application/x-www-form-urlencoded") && Qt.test(t.data) && "data");
            if (s || "jsonp" === t.dataTypes[0]) return i = t.jsonpCallback = g(t.jsonpCallback) ? t.jsonpCallback() : t.jsonpCallback, s ? t[s] = t[s].replace(Qt, "$1" + i) : !1 !== t.jsonp && (t.url += (kt.test(t.url) ? "&" : "?") + t.jsonp + "=" + i), t.converters["script json"] = function() {
                return a || w.error(i + " was not called"), a[0]
            }, t.dataTypes[0] = "json", o = e[i], e[i] = function() {
                a = arguments
            }, r.always(function() {
                void 0 === o ? w(e).removeProp(i) : e[i] = o, t[i] && (t.jsonpCallback = n.jsonpCallback, Yt.push(i)), a && g(o) && o(a[0]), a = o = void 0
            }), "script"
        }), h.createHTMLDocument = function() {
            var e = r.implementation.createHTMLDocument("").body;
            return e.innerHTML = "<form></form><form></form>", 2 === e.childNodes.length
        }(), w.parseHTML = function(e, t, n) {
            if ("string" != typeof e) return [];
            "boolean" == typeof t && (n = t, t = !1);
            var i, o, a;
            return t || (h.createHTMLDocument ? ((i = (t = r.implementation.createHTMLDocument("")).createElement("base")).href = r.location.href, t.head.appendChild(i)) : t = r), o = A.exec(e), a = !n && [], o ? [t.createElement(o[1])] : (o = xe([e], t, a), a && a.length && w(a).remove(), w.merge([], o.childNodes))
        }, w.fn.load = function(e, t, n) {
            var r, i, o, a = this,
                s = e.indexOf(" ");
            return s > -1 && (r = vt(e.slice(s)), e = e.slice(0, s)), g(t) ? (n = t, t = void 0) : t && "object" == typeof t && (i = "POST"), a.length > 0 && w.ajax({
                url: e,
                type: i || "GET",
                dataType: "html",
                data: t
            }).done(function(e) {
                o = arguments, a.html(r ? w("<div>").append(w.parseHTML(e)).find(r) : e)
            }).always(n && function(e, t) {
                a.each(function() {
                    n.apply(this, o || [e.responseText, t, e])
                })
            }), this
        }, w.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function(e, t) {
            w.fn[t] = function(e) {
                return this.on(t, e)
            }
        }), w.expr.pseudos.animated = function(e) {
            return w.grep(w.timers, function(t) {
                return e === t.elem
            }).length
        }, w.offset = {
            setOffset: function(e, t, n) {
                var r, i, o, a, s, u, l, c = w.css(e, "position"),
                    f = w(e),
                    p = {};
                "static" === c && (e.style.position = "relative"), s = f.offset(), o = w.css(e, "top"), u = w.css(e, "left"), (l = ("absolute" === c || "fixed" === c) && (o + u).indexOf("auto") > -1) ? (a = (r = f.position()).top, i = r.left) : (a = parseFloat(o) || 0, i = parseFloat(u) || 0), g(t) && (t = t.call(e, n, w.extend({}, s))), null != t.top && (p.top = t.top - s.top + a), null != t.left && (p.left = t.left - s.left + i), "using" in t ? t.using.call(e, p) : f.css(p)
            }
        }, w.fn.extend({
            offset: function(e) {
                if (arguments.length) return void 0 === e ? this : this.each(function(t) {
                    w.offset.setOffset(this, e, t)
                });
                var t, n, r = this[0];
                if (r) return r.getClientRects().length ? (t = r.getBoundingClientRect(), n = r.ownerDocument.defaultView, {
                    top: t.top + n.pageYOffset,
                    left: t.left + n.pageXOffset
                }) : {
                    top: 0,
                    left: 0
                }
            },
            position: function() {
                if (this[0]) {
                    var e, t, n, r = this[0],
                        i = {
                            top: 0,
                            left: 0
                        };
                    if ("fixed" === w.css(r, "position")) t = r.getBoundingClientRect();
                    else {
                        t = this.offset(), n = r.ownerDocument, e = r.offsetParent || n.documentElement;
                        while (e && (e === n.body || e === n.documentElement) && "static" === w.css(e, "position")) e = e.parentNode;
                        e && e !== r && 1 === e.nodeType && ((i = w(e).offset()).top += w.css(e, "borderTopWidth", !0), i.left += w.css(e, "borderLeftWidth", !0))
                    }
                    return {
                        top: t.top - i.top - w.css(r, "marginTop", !0),
                        left: t.left - i.left - w.css(r, "marginLeft", !0)
                    }
                }
            },
            offsetParent: function() {
                return this.map(function() {
                    var e = this.offsetParent;
                    while (e && "static" === w.css(e, "position")) e = e.offsetParent;
                    return e || be
                })
            }
        }), w.each({
            scrollLeft: "pageXOffset",
            scrollTop: "pageYOffset"
        }, function(e, t) {
            var n = "pageYOffset" === t;
            w.fn[e] = function(r) {
                return z(this, function(e, r, i) {
                    var o;
                    if (y(e) ? o = e : 9 === e.nodeType && (o = e.defaultView), void 0 === i) return o ? o[t] : e[r];
                    o ? o.scrollTo(n ? o.pageXOffset : i, n ? i : o.pageYOffset) : e[r] = i
                }, e, r, arguments.length)
            }
        }), w.each(["top", "left"], function(e, t) {
            w.cssHooks[t] = _e(h.pixelPosition, function(e, n) {
                if (n) return n = Fe(e, t), We.test(n) ? w(e).position()[t] + "px" : n
            })
        }), w.each({
            Height: "height",
            Width: "width"
        }, function(e, t) {
            w.each({
                padding: "inner" + e,
                content: t,
                "": "outer" + e
            }, function(n, r) {
                w.fn[r] = function(i, o) {
                    var a = arguments.length && (n || "boolean" != typeof i),
                        s = n || (!0 === i || !0 === o ? "margin" : "border");
                    return z(this, function(t, n, i) {
                        var o;
                        return y(t) ? 0 === r.indexOf("outer") ? t["inner" + e] : t.document.documentElement["client" + e] : 9 === t.nodeType ? (o = t.documentElement, Math.max(t.body["scroll" + e], o["scroll" + e], t.body["offset" + e], o["offset" + e], o["client" + e])) : void 0 === i ? w.css(t, n, s) : w.style(t, n, i, s)
                    }, t, a ? i : void 0, a)
                }
            })
        }), w.each("blur focus focusin focusout resize scroll click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup contextmenu".split(" "), function(e, t) {
            w.fn[t] = function(e, n) {
                return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
            }
        }), w.fn.extend({
            hover: function(e, t) {
                return this.mouseenter(e).mouseleave(t || e)
            }
        }), w.fn.extend({
            bind: function(e, t, n) {
                return this.on(e, null, t, n)
            },
            unbind: function(e, t) {
                return this.off(e, null, t)
            },
            delegate: function(e, t, n, r) {
                return this.on(t, e, n, r)
            },
            undelegate: function(e, t, n) {
                return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
            }
        }), w.proxy = function(e, t) {
            var n, r, i;
            if ("string" == typeof t && (n = e[t], t = e, e = n), g(e)) return r = o.call(arguments, 2), i = function() {
                return e.apply(t || this, r.concat(o.call(arguments)))
            }, i.guid = e.guid = e.guid || w.guid++, i
        }, w.holdReady = function(e) {
            e ? w.readyWait++ : w.ready(!0)
        }, w.isArray = Array.isArray, w.parseJSON = JSON.parse, w.nodeName = N, w.isFunction = g, w.isWindow = y, w.camelCase = G, w.type = x, w.now = Date.now, w.isNumeric = function(e) {
            var t = w.type(e);
           return ("number" === t || "string" === t) && !isNaN(e - parseFloat(e))
        }, "function" == typeof define && define.amd && define("jquery", [], function() {
            return w
        });
        var Jt = e.jQuery,
            Kt = e.$;
        return w.noConflict = function(t) {
            return e.$ === w && (e.$ = Kt), t && e.jQuery === w && (e.jQuery = Jt), w
        }, t || (e.jQuery = e.$ = w), w
    });

    // Plugin2 bootstrap
    /*!
     * Bootstrap v3.3.7 (http://getbootstrap.com)
     * Copyright 2011-2016 Twitter, Inc.
     * Licensed under the MIT license
     */
    if ("undefined" == typeof jQuery) throw new Error("Bootstrap's JavaScript requires jQuery"); + function(a) { "use strict"; var b = a.fn.jquery.split(" ")[0].split("."); if (b[0] < 2 && b[1] < 9 || 1 == b[0] && 9 == b[1] && b[2] < 1 || b[0] > 3) throw new Error("Bootstrap's JavaScript requires jQuery version 1.9.1 or higher, but lower than version 4") }(jQuery), + function(a) {
        "use strict";

        function b() {
            var a = document.createElement("bootstrap"),
                b = { WebkitTransition: "webkitTransitionEnd", MozTransition: "transitionend", OTransition: "oTransitionEnd otransitionend", transition: "transitionend" };
            for (var c in b)
                if (void 0 !== a.style[c]) return { end: b[c] };
            return !1
        }
        a.fn.emulateTransitionEnd = function(b) {
            var c = !1,
                d = this;
            a(this).one("bsTransitionEnd", function() { c = !0 });
            var e = function() { c || a(d).trigger(a.support.transition.end) };
            return setTimeout(e, b), this
        }, a(function() { a.support.transition = b(), a.support.transition && (a.event.special.bsTransitionEnd = { bindType: a.support.transition.end, delegateType: a.support.transition.end, handle: function(b) { if (a(b.target).is(this)) return b.handleObj.handler.apply(this, arguments) } }) })
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var c = a(this),
                    e = c.data("bs.alert");
                e || c.data("bs.alert", e = new d(this)), "string" == typeof b && e[b].call(c)
            })
        }
        var c = '[data-dismiss="alert"]',
            d = function(b) { a(b).on("click", c, this.close) };
        d.VERSION = "3.3.7", d.TRANSITION_DURATION = 150, d.prototype.close = function(b) {
            function c() { g.detach().trigger("closed.bs.alert").remove() }
            var e = a(this),
                f = e.attr("data-target");
            f || (f = e.attr("href"), f = f && f.replace(/.*(?=#[^\s]*$)/, ""));
            var g = a("#" === f ? [] : f);
            b && b.preventDefault(), g.length || (g = e.closest(".alert")), g.trigger(b = a.Event("close.bs.alert")), b.isDefaultPrevented() || (g.removeClass("in"), a.support.transition && g.hasClass("fade") ? g.one("bsTransitionEnd", c).emulateTransitionEnd(d.TRANSITION_DURATION) : c())
        };
        var e = a.fn.alert;
        a.fn.alert = b, a.fn.alert.Constructor = d, a.fn.alert.noConflict = function() { return a.fn.alert = e, this }, a(document).on("click.bs.alert.data-api", c, d.prototype.close)
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.button"),
                    f = "object" == typeof b && b;
                e || d.data("bs.button", e = new c(this, f)), "toggle" == b ? e.toggle() : b && e.setState(b)
            })
        }
        var c = function(b, d) { this.$element = a(b), this.options = a.extend({}, c.DEFAULTS, d), this.isLoading = !1 };
        c.VERSION = "3.3.7", c.DEFAULTS = { loadingText: "loading..." }, c.prototype.setState = function(b) {
            var c = "disabled",
                d = this.$element,
                e = d.is("input") ? "val" : "html",
                f = d.data();
            b += "Text", null == f.resetText && d.data("resetText", d[e]()), setTimeout(a.proxy(function() { d[e](null == f[b] ? this.options[b] : f[b]), "loadingText" == b ? (this.isLoading = !0, d.addClass(c).attr(c, c).prop(c, !0)) : this.isLoading && (this.isLoading = !1, d.removeClass(c).removeAttr(c).prop(c, !1)) }, this), 0)
        }, c.prototype.toggle = function() {
            var a = !0,
                b = this.$element.closest('[data-toggle="buttons"]');
            if (b.length) { var c = this.$element.find("input"); "radio" == c.prop("type") ? (c.prop("checked") && (a = !1), b.find(".active").removeClass("active"), this.$element.addClass("active")) : "checkbox" == c.prop("type") && (c.prop("checked") !== this.$element.hasClass("active") && (a = !1), this.$element.toggleClass("active")), c.prop("checked", this.$element.hasClass("active")), a && c.trigger("change") } else this.$element.attr("aria-pressed", !this.$element.hasClass("active")), this.$element.toggleClass("active")
        };
        var d = a.fn.button;
        a.fn.button = b, a.fn.button.Constructor = c, a.fn.button.noConflict = function() { return a.fn.button = d, this }, a(document).on("click.bs.button.data-api", '[data-toggle^="button"]', function(c) {
            var d = a(c.target).closest(".btn");
            b.call(d, "toggle"), a(c.target).is('input[type="radio"], input[type="checkbox"]') || (c.preventDefault(), d.is("input,button") ? d.trigger("focus") : d.find("input:visible,button:visible").first().trigger("focus"))
        }).on("focus.bs.button.data-api blur.bs.button.data-api", '[data-toggle^="button"]', function(b) { a(b.target).closest(".btn").toggleClass("focus", /^focus(in)?$/.test(b.type)) })
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.carousel"),
                    f = a.extend({}, c.DEFAULTS, d.data(), "object" == typeof b && b),
                    g = "string" == typeof b ? b : f.slide;
                e || d.data("bs.carousel", e = new c(this, f)), "number" == typeof b ? e.to(b) : g ? e[g]() : f.interval && e.pause().cycle()
            })
        }
        var c = function(b, c) { this.$element = a(b), this.$indicators = this.$element.find(".carousel-indicators"), this.options = c, this.paused = null, this.sliding = null, this.interval = null, this.$active = null, this.$items = null, this.options.keyboard && this.$element.on("keydown.bs.carousel", a.proxy(this.keydown, this)), "hover" == this.options.pause && !("ontouchstart" in document.documentElement) && this.$element.on("mouseenter.bs.carousel", a.proxy(this.pause, this)).on("mouseleave.bs.carousel", a.proxy(this.cycle, this)) };
        c.VERSION = "3.3.7", c.TRANSITION_DURATION = 600, c.DEFAULTS = { interval: 5e3, pause: "hover", wrap: !0, keyboard: !0 }, c.prototype.keydown = function(a) {
            if (!/input|textarea/i.test(a.target.tagName)) {
                switch (a.which) {
                    case 37:
                        this.prev();
                        break;
                    case 39:
                        this.next();
                        break;
                    default:
                        return
                }
                a.preventDefault()
            }
        }, c.prototype.cycle = function(b) { return b || (this.paused = !1), this.interval && clearInterval(this.interval), this.options.interval && !this.paused && (this.interval = setInterval(a.proxy(this.next, this), this.options.interval)), this }, c.prototype.getItemIndex = function(a) { return this.$items = a.parent().children(".item"), this.$items.index(a || this.$active) }, c.prototype.getItemForDirection = function(a, b) {
            var c = this.getItemIndex(b),
                d = "prev" == a && 0 === c || "next" == a && c == this.$items.length - 1;
            if (d && !this.options.wrap) return b;
            var e = "prev" == a ? -1 : 1,
                f = (c + e) % this.$items.length;
            return this.$items.eq(f)
        }, c.prototype.to = function(a) {
            var b = this,
                c = this.getItemIndex(this.$active = this.$element.find(".item.active"));
            if (!(a > this.$items.length - 1 || a < 0)) return this.sliding ? this.$element.one("slid.bs.carousel", function() { b.to(a) }) : c == a ? this.pause().cycle() : this.slide(a > c ? "next" : "prev", this.$items.eq(a))
        }, c.prototype.pause = function(b) { return b || (this.paused = !0), this.$element.find(".next, .prev").length && a.support.transition && (this.$element.trigger(a.support.transition.end), this.cycle(!0)), this.interval = clearInterval(this.interval), this }, c.prototype.next = function() { if (!this.sliding) return this.slide("next") }, c.prototype.prev = function() { if (!this.sliding) return this.slide("prev") }, c.prototype.slide = function(b, d) {
            var e = this.$element.find(".item.active"),
                f = d || this.getItemForDirection(b, e),
                g = this.interval,
                h = "next" == b ? "left" : "right",
                i = this;
            if (f.hasClass("active")) return this.sliding = !1;
            var j = f[0],
                k = a.Event("slide.bs.carousel", { relatedTarget: j, direction: h });
            if (this.$element.trigger(k), !k.isDefaultPrevented()) {
                if (this.sliding = !0, g && this.pause(), this.$indicators.length) {
                    this.$indicators.find(".active").removeClass("active");
                    var l = a(this.$indicators.children()[this.getItemIndex(f)]);
                    l && l.addClass("active")
                }
                var m = a.Event("slid.bs.carousel", { relatedTarget: j, direction: h });
                return a.support.transition && this.$element.hasClass("slide") ? (f.addClass(b), f[0].offsetWidth, e.addClass(h), f.addClass(h), e.one("bsTransitionEnd", function() { f.removeClass([b, h].join(" ")).addClass("active"), e.removeClass(["active", h].join(" ")), i.sliding = !1, setTimeout(function() { i.$element.trigger(m) }, 0) }).emulateTransitionEnd(c.TRANSITION_DURATION)) : (e.removeClass("active"), f.addClass("active"), this.sliding = !1, this.$element.trigger(m)), g && this.cycle(), this
            }
        };
        var d = a.fn.carousel;
        a.fn.carousel = b, a.fn.carousel.Constructor = c, a.fn.carousel.noConflict = function() { return a.fn.carousel = d, this };
        var e = function(c) {
            var d, e = a(this),
                f = a(e.attr("data-target") || (d = e.attr("href")) && d.replace(/.*(?=#[^\s]+$)/, ""));
            if (f.hasClass("carousel")) {
                var g = a.extend({}, f.data(), e.data()),
                    h = e.attr("data-slide-to");
                h && (g.interval = !1), b.call(f, g), h && f.data("bs.carousel").to(h), c.preventDefault()
            }
        };
        a(document).on("click.bs.carousel.data-api", "[data-slide]", e).on("click.bs.carousel.data-api", "[data-slide-to]", e), a(window).on("load", function() {
            a('[data-ride="carousel"]').each(function() {
                var c = a(this);
                b.call(c, c.data())
            })
        })
    }(jQuery), + function(a) {
        "use strict";

        function b(b) { var c, d = b.attr("data-target") || (c = b.attr("href")) && c.replace(/.*(?=#[^\s]+$)/, ""); return a(d) }

        function c(b) {
            return this.each(function() {
                var c = a(this),
                    e = c.data("bs.collapse"),
                    f = a.extend({}, d.DEFAULTS, c.data(), "object" == typeof b && b);
                !e && f.toggle && /show|hide/.test(b) && (f.toggle = !1), e || c.data("bs.collapse", e = new d(this, f)), "string" == typeof b && e[b]()
            })
        }
        var d = function(b, c) { this.$element = a(b), this.options = a.extend({}, d.DEFAULTS, c), this.$trigger = a('[data-toggle="collapse"][href="#' + b.id + '"],[data-toggle="collapse"][data-target="#' + b.id + '"]'), this.transitioning = null, this.options.parent ? this.$parent = this.getParent() : this.addAriaAndCollapsedClass(this.$element, this.$trigger), this.options.toggle && this.toggle() };
        d.VERSION = "3.3.7", d.TRANSITION_DURATION = 350, d.DEFAULTS = { toggle: !0 }, d.prototype.dimension = function() { var a = this.$element.hasClass("width"); return a ? "width" : "height" }, d.prototype.show = function() {
            if (!this.transitioning && !this.$element.hasClass("in")) {
                var b, e = this.$parent && this.$parent.children(".panel").children(".in, .collapsing");
                if (!(e && e.length && (b = e.data("bs.collapse"), b && b.transitioning))) {
                    var f = a.Event("show.bs.collapse");
                    if (this.$element.trigger(f), !f.isDefaultPrevented()) {
                        e && e.length && (c.call(e, "hide"), b || e.data("bs.collapse", null));
                        var g = this.dimension();
                        this.$element.removeClass("collapse").addClass("collapsing")[g](0).attr("aria-expanded", !0), this.$trigger.removeClass("collapsed").attr("aria-expanded", !0), this.transitioning = 1;
                        var h = function() { this.$element.removeClass("collapsing").addClass("collapse in")[g](""), this.transitioning = 0, this.$element.trigger("shown.bs.collapse") };
                        if (!a.support.transition) return h.call(this);
                        var i = a.camelCase(["scroll", g].join("-"));
                        this.$element.one("bsTransitionEnd", a.proxy(h, this)).emulateTransitionEnd(d.TRANSITION_DURATION)[g](this.$element[0][i])
                    }
                }
            }
        }, d.prototype.hide = function() {
            if (!this.transitioning && this.$element.hasClass("in")) {
                var b = a.Event("hide.bs.collapse");
                if (this.$element.trigger(b), !b.isDefaultPrevented()) {
                    var c = this.dimension();
                    this.$element[c](this.$element[c]())[0].offsetHeight, this.$element.addClass("collapsing").removeClass("collapse in").attr("aria-expanded", !1), this.$trigger.addClass("collapsed").attr("aria-expanded", !1), this.transitioning = 1;
                    var e = function() { this.transitioning = 0, this.$element.removeClass("collapsing").addClass("collapse").trigger("hidden.bs.collapse") };
                    return a.support.transition ? void this.$element[c](0).one("bsTransitionEnd", a.proxy(e, this)).emulateTransitionEnd(d.TRANSITION_DURATION) : e.call(this)
                }
            }
        }, d.prototype.toggle = function() { this[this.$element.hasClass("in") ? "hide" : "show"]() }, d.prototype.getParent = function() {
            return a(this.options.parent).find('[data-toggle="collapse"][data-parent="' + this.options.parent + '"]').each(a.proxy(function(c, d) {
                var e = a(d);
                this.addAriaAndCollapsedClass(b(e), e)
            }, this)).end()
        }, d.prototype.addAriaAndCollapsedClass = function(a, b) {
            var c = a.hasClass("in");
            a.attr("aria-expanded", c), b.toggleClass("collapsed", !c).attr("aria-expanded", c)
        };
        var e = a.fn.collapse;
        a.fn.collapse = c, a.fn.collapse.Constructor = d, a.fn.collapse.noConflict = function() { return a.fn.collapse = e, this }, a(document).on("click.bs.collapse.data-api", '[data-toggle="collapse"]', function(d) {
            var e = a(this);
            e.attr("data-target") || d.preventDefault();
            var f = b(e),
                g = f.data("bs.collapse"),
                h = g ? "toggle" : e.data();
            c.call(f, h)
        })
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            var c = b.attr("data-target");
            c || (c = b.attr("href"), c = c && /#[A-Za-z]/.test(c) && c.replace(/.*(?=#[^\s]*$)/, ""));
            var d = c && a(c);
            return d && d.length ? d : b.parent()
        }

        function c(c) {
            c && 3 === c.which || (a(e).remove(), a(f).each(function() {
                var d = a(this),
                    e = b(d),
                    f = { relatedTarget: this };
                e.hasClass("open") && (c && "click" == c.type && /input|textarea/i.test(c.target.tagName) && a.contains(e[0], c.target) || (e.trigger(c = a.Event("hide.bs.dropdown", f)), c.isDefaultPrevented() || (d.attr("aria-expanded", "false"), e.removeClass("open").trigger(a.Event("hidden.bs.dropdown", f)))))
            }))
        }

        function d(b) {
            return this.each(function() {
                var c = a(this),
                    d = c.data("bs.dropdown");
                d || c.data("bs.dropdown", d = new g(this)), "string" == typeof b && d[b].call(c)
            })
        }
        var e = ".dropdown-backdrop",
            f = '[data-toggle="dropdown"]',
            g = function(b) { a(b).on("click.bs.dropdown", this.toggle) };
        g.VERSION = "3.3.7", g.prototype.toggle = function(d) {
            var e = a(this);
            if (!e.is(".disabled, :disabled")) {
                var f = b(e),
                    g = f.hasClass("open");
                if (c(), !g) {
                    "ontouchstart" in document.documentElement && !f.closest(".navbar-nav").length && a(document.createElement("div")).addClass("dropdown-backdrop").insertAfter(a(this)).on("click", c);
                    var h = { relatedTarget: this };
                    if (f.trigger(d = a.Event("show.bs.dropdown", h)), d.isDefaultPrevented()) return;
                    e.trigger("focus").attr("aria-expanded", "true"), f.toggleClass("open").trigger(a.Event("shown.bs.dropdown", h))
                }
                return !1
            }
        }, g.prototype.keydown = function(c) {
            if (/(38|40|27|32)/.test(c.which) && !/input|textarea/i.test(c.target.tagName)) {
                var d = a(this);
                if (c.preventDefault(), c.stopPropagation(), !d.is(".disabled, :disabled")) {
                    var e = b(d),
                        g = e.hasClass("open");
                    if (!g && 27 != c.which || g && 27 == c.which) return 27 == c.which && e.find(f).trigger("focus"), d.trigger("click");
                    var h = " li:not(.disabled):visible a",
                        i = e.find(".dropdown-menu" + h);
                    if (i.length) {
                        var j = i.index(c.target);
                        38 == c.which && j > 0 && j--, 40 == c.which && j < i.length - 1 && j++, ~j || (j = 0), i.eq(j).trigger("focus")
                    }
                }
            }
        };
        var h = a.fn.dropdown;
        a.fn.dropdown = d, a.fn.dropdown.Constructor = g, a.fn.dropdown.noConflict = function() { return a.fn.dropdown = h, this }, a(document).on("click.bs.dropdown.data-api", c).on("click.bs.dropdown.data-api", ".dropdown form", function(a) { a.stopPropagation() }).on("click.bs.dropdown.data-api", f, g.prototype.toggle).on("keydown.bs.dropdown.data-api", f, g.prototype.keydown).on("keydown.bs.dropdown.data-api", ".dropdown-menu", g.prototype.keydown)
    }(jQuery), + function(a) {
        "use strict";

        function b(b, d) {
            return this.each(function() {
                var e = a(this),
                    f = e.data("bs.modal"),
                    g = a.extend({}, c.DEFAULTS, e.data(), "object" == typeof b && b);
                f || e.data("bs.modal", f = new c(this, g)), "string" == typeof b ? f[b](d) : g.show && f.show(d)
            })
        }
        var c = function(b, c) { this.options = c, this.$body = a(document.body), this.$element = a(b), this.$dialog = this.$element.find(".modal-dialog"), this.$backdrop = null, this.isShown = null, this.originalBodyPad = null, this.scrollbarWidth = 0, this.ignoreBackdropClick = !1, this.options.remote && this.$element.find(".modal-content").load(this.options.remote, a.proxy(function() { this.$element.trigger("loaded.bs.modal") }, this)) };
        c.VERSION = "3.3.7", c.TRANSITION_DURATION = 300, c.BACKDROP_TRANSITION_DURATION = 150, c.DEFAULTS = { backdrop: !0, keyboard: !0, show: !0 }, c.prototype.toggle = function(a) { return this.isShown ? this.hide() : this.show(a) }, c.prototype.show = function(b) {
            var d = this,
                e = a.Event("show.bs.modal", { relatedTarget: b });
            this.$element.trigger(e), this.isShown || e.isDefaultPrevented() || (this.isShown = !0, this.checkScrollbar(), this.setScrollbar(), this.$body.addClass("modal-open"), this.escape(), this.resize(), this.$element.on("click.dismiss.bs.modal", '[data-dismiss="modal"]', a.proxy(this.hide, this)), this.$dialog.on("mousedown.dismiss.bs.modal", function() { d.$element.one("mouseup.dismiss.bs.modal", function(b) { a(b.target).is(d.$element) && (d.ignoreBackdropClick = !0) }) }), this.backdrop(function() {
                var e = a.support.transition && d.$element.hasClass("fade");
                d.$element.parent().length || d.$element.appendTo(d.$body), d.$element.show().scrollTop(0), d.adjustDialog(), e && d.$element[0].offsetWidth, d.$element.addClass("in"), d.enforceFocus();
                var f = a.Event("shown.bs.modal", { relatedTarget: b });
                e ? d.$dialog.one("bsTransitionEnd", function() { d.$element.trigger("focus").trigger(f) }).emulateTransitionEnd(c.TRANSITION_DURATION) : d.$element.trigger("focus").trigger(f)
            }))
        }, c.prototype.hide = function(b) { b && b.preventDefault(), b = a.Event("hide.bs.modal"), this.$element.trigger(b), this.isShown && !b.isDefaultPrevented() && (this.isShown = !1, this.escape(), this.resize(), a(document).off("focusin.bs.modal"), this.$element.removeClass("in").off("click.dismiss.bs.modal").off("mouseup.dismiss.bs.modal"), this.$dialog.off("mousedown.dismiss.bs.modal"), a.support.transition && this.$element.hasClass("fade") ? this.$element.one("bsTransitionEnd", a.proxy(this.hideModal, this)).emulateTransitionEnd(c.TRANSITION_DURATION) : this.hideModal()) }, c.prototype.enforceFocus = function() { a(document).off("focusin.bs.modal").on("focusin.bs.modal", a.proxy(function(a) { document === a.target || this.$element[0] === a.target || this.$element.has(a.target).length || this.$element.trigger("focus") }, this)) }, c.prototype.escape = function() { this.isShown && this.options.keyboard ? this.$element.on("keydown.dismiss.bs.modal", a.proxy(function(a) { 27 == a.which && this.hide() }, this)) : this.isShown || this.$element.off("keydown.dismiss.bs.modal") }, c.prototype.resize = function() { this.isShown ? a(window).on("resize.bs.modal", a.proxy(this.handleUpdate, this)) : a(window).off("resize.bs.modal") }, c.prototype.hideModal = function() {
            var a = this;
            this.$element.hide(), this.backdrop(function() { a.$body.removeClass("modal-open"), a.resetAdjustments(), a.resetScrollbar(), a.$element.trigger("hidden.bs.modal") })
        }, c.prototype.removeBackdrop = function() { this.$backdrop && this.$backdrop.remove(), this.$backdrop = null }, c.prototype.backdrop = function(b) {
            var d = this,
                e = this.$element.hasClass("fade") ? "fade" : "";
            if (this.isShown && this.options.backdrop) {
                var f = a.support.transition && e;
                if (this.$backdrop = a(document.createElement("div")).addClass("modal-backdrop " + e).appendTo(this.$body), this.$element.on("click.dismiss.bs.modal", a.proxy(function(a) { return this.ignoreBackdropClick ? void(this.ignoreBackdropClick = !1) : void(a.target === a.currentTarget && ("static" == this.options.backdrop ? this.$element[0].focus() : this.hide())) }, this)), f && this.$backdrop[0].offsetWidth, this.$backdrop.addClass("in"), !b) return;
                f ? this.$backdrop.one("bsTransitionEnd", b).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION) : b()
            } else if (!this.isShown && this.$backdrop) {
                this.$backdrop.removeClass("in");
                var g = function() { d.removeBackdrop(), b && b() };
                a.support.transition && this.$element.hasClass("fade") ? this.$backdrop.one("bsTransitionEnd", g).emulateTransitionEnd(c.BACKDROP_TRANSITION_DURATION) : g()
            } else b && b()
        }, c.prototype.handleUpdate = function() { this.adjustDialog() }, c.prototype.adjustDialog = function() {
            var a = this.$element[0].scrollHeight > document.documentElement.clientHeight;
            this.$element.css({ paddingLeft: !this.bodyIsOverflowing && a ? this.scrollbarWidth : "", paddingRight: this.bodyIsOverflowing && !a ? this.scrollbarWidth : "" })
        }, c.prototype.resetAdjustments = function() { this.$element.css({ paddingLeft: "", paddingRight: "" }) }, c.prototype.checkScrollbar = function() {
            var a = window.innerWidth;
            if (!a) {
                var b = document.documentElement.getBoundingClientRect();
                a = b.right - Math.abs(b.left)
            }
            this.bodyIsOverflowing = document.body.clientWidth < a, this.scrollbarWidth = this.measureScrollbar()
        }, c.prototype.setScrollbar = function() {
            var a = parseInt(this.$body.css("padding-right") || 0, 10);
            this.originalBodyPad = document.body.style.paddingRight || "", this.bodyIsOverflowing && this.$body.css("padding-right", a + this.scrollbarWidth)
        }, c.prototype.resetScrollbar = function() { this.$body.css("padding-right", this.originalBodyPad) }, c.prototype.measureScrollbar = function() {
            var a = document.createElement("div");
            a.className = "modal-scrollbar-measure", this.$body.append(a);
            var b = a.offsetWidth - a.clientWidth;
            return this.$body[0].removeChild(a), b
        };
        var d = a.fn.modal;
        a.fn.modal = b, a.fn.modal.Constructor = c, a.fn.modal.noConflict = function() { return a.fn.modal = d, this }, a(document).on("click.bs.modal.data-api", '[data-toggle="modal"]', function(c) {
            var d = a(this),
                e = d.attr("href"),
                f = a(d.attr("data-target") || e && e.replace(/.*(?=#[^\s]+$)/, "")),
                g = f.data("bs.modal") ? "toggle" : a.extend({ remote: !/#/.test(e) && e }, f.data(), d.data());
            d.is("a") && c.preventDefault(), f.one("show.bs.modal", function(a) { a.isDefaultPrevented() || f.one("hidden.bs.modal", function() { d.is(":visible") && d.trigger("focus") }) }), b.call(f, g, this)
        })
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.tooltip"),
                    f = "object" == typeof b && b;
                !e && /destroy|hide/.test(b) || (e || d.data("bs.tooltip", e = new c(this, f)), "string" == typeof b && e[b]())
            })
        }
        var c = function(a, b) { this.type = null, this.options = null, this.enabled = null, this.timeout = null, this.hoverState = null, this.$element = null, this.inState = null, this.init("tooltip", a, b) };
        c.VERSION = "3.3.7", c.TRANSITION_DURATION = 150, c.DEFAULTS = { animation: !0, placement: "top", selector: !1, template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>', trigger: "hover focus", title: "", delay: 0, html: !1, container: !1, viewport: { selector: "body", padding: 0 } }, c.prototype.init = function(b, c, d) {
            if (this.enabled = !0, this.type = b, this.$element = a(c), this.options = this.getOptions(d), this.$viewport = this.options.viewport && a(a.isFunction(this.options.viewport) ? this.options.viewport.call(this, this.$element) : this.options.viewport.selector || this.options.viewport), this.inState = { click: !1, hover: !1, focus: !1 }, this.$element[0] instanceof document.constructor && !this.options.selector) throw new Error("`selector` option must be specified when initializing " + this.type + " on the window.document object!");
            for (var e = this.options.trigger.split(" "), f = e.length; f--;) {
                var g = e[f];
                if ("click" == g) this.$element.on("click." + this.type, this.options.selector, a.proxy(this.toggle, this));
                else if ("manual" != g) {
                    var h = "hover" == g ? "mouseenter" : "focusin",
                        i = "hover" == g ? "mouseleave" : "focusout";
                    this.$element.on(h + "." + this.type, this.options.selector, a.proxy(this.enter, this)), this.$element.on(i + "." + this.type, this.options.selector, a.proxy(this.leave, this))
                }
            }
            this.options.selector ? this._options = a.extend({}, this.options, { trigger: "manual", selector: "" }) : this.fixTitle()
        }, c.prototype.getDefaults = function() { return c.DEFAULTS }, c.prototype.getOptions = function(b) { return b = a.extend({}, this.getDefaults(), this.$element.data(), b), b.delay && "number" == typeof b.delay && (b.delay = { show: b.delay, hide: b.delay }), b }, c.prototype.getDelegateOptions = function() {
            var b = {},
                c = this.getDefaults();
            return this._options && a.each(this._options, function(a, d) { c[a] != d && (b[a] = d) }), b
        }, c.prototype.enter = function(b) { var c = b instanceof this.constructor ? b : a(b.currentTarget).data("bs." + this.type); return c || (c = new this.constructor(b.currentTarget, this.getDelegateOptions()), a(b.currentTarget).data("bs." + this.type, c)), b instanceof a.Event && (c.inState["focusin" == b.type ? "focus" : "hover"] = !0), c.tip().hasClass("in") || "in" == c.hoverState ? void(c.hoverState = "in") : (clearTimeout(c.timeout), c.hoverState = "in", c.options.delay && c.options.delay.show ? void(c.timeout = setTimeout(function() { "in" == c.hoverState && c.show() }, c.options.delay.show)) : c.show()) }, c.prototype.isInStateTrue = function() {
            for (var a in this.inState)
                if (this.inState[a]) return !0;
            return !1
        }, c.prototype.leave = function(b) { var c = b instanceof this.constructor ? b : a(b.currentTarget).data("bs." + this.type); if (c || (c = new this.constructor(b.currentTarget, this.getDelegateOptions()), a(b.currentTarget).data("bs." + this.type, c)), b instanceof a.Event && (c.inState["focusout" == b.type ? "focus" : "hover"] = !1), !c.isInStateTrue()) return clearTimeout(c.timeout), c.hoverState = "out", c.options.delay && c.options.delay.hide ? void(c.timeout = setTimeout(function() { "out" == c.hoverState && c.hide() }, c.options.delay.hide)) : c.hide() }, c.prototype.show = function() {
            var b = a.Event("show.bs." + this.type);
            if (this.hasContent() && this.enabled) {
                this.$element.trigger(b);
                var d = a.contains(this.$element[0].ownerDocument.documentElement, this.$element[0]);
                if (b.isDefaultPrevented() || !d) return;
                var e = this,
                    f = this.tip(),
                    g = this.getUID(this.type);
                this.setContent(), f.attr("id", g), this.$element.attr("aria-describedby", g), this.options.animation && f.addClass("fade");
                var h = "function" == typeof this.options.placement ? this.options.placement.call(this, f[0], this.$element[0]) : this.options.placement,
                    i = /\s?auto?\s?/i,
                    j = i.test(h);
                j && (h = h.replace(i, "") || "top"), f.detach().css({ top: 0, left: 0, display: "block" }).addClass(h).data("bs." + this.type, this), this.options.container ? f.appendTo(this.options.container) : f.insertAfter(this.$element), this.$element.trigger("inserted.bs." + this.type);
                var k = this.getPosition(),
                    l = f[0].offsetWidth,
                    m = f[0].offsetHeight;
                if (j) {
                    var n = h,
                        o = this.getPosition(this.$viewport);
                    h = "bottom" == h && k.bottom + m > o.bottom ? "top" : "top" == h && k.top - m < o.top ? "bottom" : "right" == h && k.right + l > o.width ? "left" : "left" == h && k.left - l < o.left ? "right" : h, f.removeClass(n).addClass(h)
                }
                var p = this.getCalculatedOffset(h, k, l, m);
                this.applyPlacement(p, h);
                var q = function() {
                    var a = e.hoverState;
                    e.$element.trigger("shown.bs." + e.type), e.hoverState = null, "out" == a && e.leave(e)
                };
                a.support.transition && this.$tip.hasClass("fade") ? f.one("bsTransitionEnd", q).emulateTransitionEnd(c.TRANSITION_DURATION) : q()
            }
        }, c.prototype.applyPlacement = function(b, c) {
            var d = this.tip(),
                e = d[0].offsetWidth,
                f = d[0].offsetHeight,
                g = parseInt(d.css("margin-top"), 10),
                h = parseInt(d.css("margin-left"), 10);
            isNaN(g) && (g = 0), isNaN(h) && (h = 0), b.top += g, b.left += h, a.offset.setOffset(d[0], a.extend({ using: function(a) { d.css({ top: Math.round(a.top), left: Math.round(a.left) }) } }, b), 0), d.addClass("in");
            var i = d[0].offsetWidth,
                j = d[0].offsetHeight;
            "top" == c && j != f && (b.top = b.top + f - j);
            var k = this.getViewportAdjustedDelta(c, b, i, j);
            k.left ? b.left += k.left : b.top += k.top;
            var l = /top|bottom/.test(c),
                m = l ? 2 * k.left - e + i : 2 * k.top - f + j,
                n = l ? "offsetWidth" : "offsetHeight";
            d.offset(b), this.replaceArrow(m, d[0][n], l)
        }, c.prototype.replaceArrow = function(a, b, c) { this.arrow().css(c ? "left" : "top", 50 * (1 - a / b) + "%").css(c ? "top" : "left", "") }, c.prototype.setContent = function() {
            var a = this.tip(),
                b = this.getTitle();
            a.find(".tooltip-inner")[this.options.html ? "html" : "text"](b), a.removeClass("fade in top bottom left right")
        }, c.prototype.hide = function(b) {
            function d() { "in" != e.hoverState && f.detach(), e.$element && e.$element.removeAttr("aria-describedby").trigger("hidden.bs." + e.type), b && b() }
            var e = this,
                f = a(this.$tip),
                g = a.Event("hide.bs." + this.type);
            if (this.$element.trigger(g), !g.isDefaultPrevented()) return f.removeClass("in"), a.support.transition && f.hasClass("fade") ? f.one("bsTransitionEnd", d).emulateTransitionEnd(c.TRANSITION_DURATION) : d(), this.hoverState = null, this
        }, c.prototype.fixTitle = function() {
            var a = this.$element;
            (a.attr("title") || "string" != typeof a.attr("data-original-title")) && a.attr("data-original-title", a.attr("title") || "").attr("title", "")
        }, c.prototype.hasContent = function() { return this.getTitle() }, c.prototype.getPosition = function(b) {
            b = b || this.$element;
            var c = b[0],
                d = "BODY" == c.tagName,
                e = c.getBoundingClientRect();
            null == e.width && (e = a.extend({}, e, { width: e.right - e.left, height: e.bottom - e.top }));
            var f = window.SVGElement && c instanceof window.SVGElement,
                g = d ? { top: 0, left: 0 } : f ? null : b.offset(),
                h = { scroll: d ? document.documentElement.scrollTop || document.body.scrollTop : b.scrollTop() },
                i = d ? { width: a(window).width(), height: a(window).height() } : null;
            return a.extend({}, e, h, i, g)
        }, c.prototype.getCalculatedOffset = function(a, b, c, d) { return "bottom" == a ? { top: b.top + b.height, left: b.left + b.width / 2 - c / 2 } : "top" == a ? { top: b.top - d, left: b.left + b.width / 2 - c / 2 } : "left" == a ? { top: b.top + b.height / 2 - d / 2, left: b.left - c } : { top: b.top + b.height / 2 - d / 2, left: b.left + b.width } }, c.prototype.getViewportAdjustedDelta = function(a, b, c, d) {
            var e = { top: 0, left: 0 };
            if (!this.$viewport) return e;
            var f = this.options.viewport && this.options.viewport.padding || 0,
                g = this.getPosition(this.$viewport);
            if (/right|left/.test(a)) {
                var h = b.top - f - g.scroll,
                    i = b.top + f - g.scroll + d;
                h < g.top ? e.top = g.top - h : i > g.top + g.height && (e.top = g.top + g.height - i)
            } else {
                var j = b.left - f,
                    k = b.left + f + c;
                j < g.left ? e.left = g.left - j : k > g.right && (e.left = g.left + g.width - k)
            }
            return e
        }, c.prototype.getTitle = function() {
            var a, b = this.$element,
                c = this.options;
            return a = b.attr("data-original-title") || ("function" == typeof c.title ? c.title.call(b[0]) : c.title)
        }, c.prototype.getUID = function(a) { do a += ~~(1e6 * Math.random()); while (document.getElementById(a)); return a }, c.prototype.tip = function() { if (!this.$tip && (this.$tip = a(this.options.template), 1 != this.$tip.length)) throw new Error(this.type + " `template` option must consist of exactly 1 top-level element!"); return this.$tip }, c.prototype.arrow = function() { return this.$arrow = this.$arrow || this.tip().find(".tooltip-arrow") }, c.prototype.enable = function() { this.enabled = !0 }, c.prototype.disable = function() { this.enabled = !1 }, c.prototype.toggleEnabled = function() { this.enabled = !this.enabled }, c.prototype.toggle = function(b) {
            var c = this;
            b && (c = a(b.currentTarget).data("bs." + this.type), c || (c = new this.constructor(b.currentTarget, this.getDelegateOptions()), a(b.currentTarget).data("bs." + this.type, c))), b ? (c.inState.click = !c.inState.click, c.isInStateTrue() ? c.enter(c) : c.leave(c)) : c.tip().hasClass("in") ? c.leave(c) : c.enter(c)
        }, c.prototype.destroy = function() {
            var a = this;
            clearTimeout(this.timeout), this.hide(function() { a.$element.off("." + a.type).removeData("bs." + a.type), a.$tip && a.$tip.detach(), a.$tip = null, a.$arrow = null, a.$viewport = null, a.$element = null })
        };
        var d = a.fn.tooltip;
        a.fn.tooltip = b, a.fn.tooltip.Constructor = c, a.fn.tooltip.noConflict = function() { return a.fn.tooltip = d, this }
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.popover"),
                    f = "object" == typeof b && b;
                !e && /destroy|hide/.test(b) || (e || d.data("bs.popover", e = new c(this, f)), "string" == typeof b && e[b]())
            })
        }
        var c = function(a, b) { this.init("popover", a, b) };
        if (!a.fn.tooltip) throw new Error("Popover requires tooltip.js");
        c.VERSION = "3.3.7", c.DEFAULTS = a.extend({}, a.fn.tooltip.Constructor.DEFAULTS, { placement: "right", trigger: "click", content: "", template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>' }), c.prototype = a.extend({}, a.fn.tooltip.Constructor.prototype), c.prototype.constructor = c, c.prototype.getDefaults = function() { return c.DEFAULTS }, c.prototype.setContent = function() {
            var a = this.tip(),
                b = this.getTitle(),
                c = this.getContent();
            a.find(".popover-title")[this.options.html ? "html" : "text"](b), a.find(".popover-content").children().detach().end()[this.options.html ? "string" == typeof c ? "html" : "append" : "text"](c), a.removeClass("fade top bottom left right in"), a.find(".popover-title").html() || a.find(".popover-title").hide()
        }, c.prototype.hasContent = function() { return this.getTitle() || this.getContent() }, c.prototype.getContent = function() {
            var a = this.$element,
                b = this.options;
            return a.attr("data-content") || ("function" == typeof b.content ? b.content.call(a[0]) : b.content)
        }, c.prototype.arrow = function() { return this.$arrow = this.$arrow || this.tip().find(".arrow") };
        var d = a.fn.popover;
        a.fn.popover = b, a.fn.popover.Constructor = c, a.fn.popover.noConflict = function() { return a.fn.popover = d, this }
    }(jQuery), + function(a) {
        "use strict";

        function b(c, d) { this.$body = a(document.body), this.$scrollElement = a(a(c).is(document.body) ? window : c), this.options = a.extend({}, b.DEFAULTS, d), this.selector = (this.options.target || "") + " .nav li > a", this.offsets = [], this.targets = [], this.activeTarget = null, this.scrollHeight = 0, this.$scrollElement.on("scroll.bs.scrollspy", a.proxy(this.process, this)), this.refresh(), this.process() }

        function c(c) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.scrollspy"),
                    f = "object" == typeof c && c;
                e || d.data("bs.scrollspy", e = new b(this, f)), "string" == typeof c && e[c]()
            })
        }
        b.VERSION = "3.3.7", b.DEFAULTS = { offset: 10 }, b.prototype.getScrollHeight = function() { return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight) }, b.prototype.refresh = function() {
            var b = this,
                c = "offset",
                d = 0;
            this.offsets = [], this.targets = [], this.scrollHeight = this.getScrollHeight(), a.isWindow(this.$scrollElement[0]) || (c = "position", d = this.$scrollElement.scrollTop()), this.$body.find(this.selector).map(function() {
                var b = a(this),
                    e = b.data("target") || b.attr("href"),
                    f = /^#./.test(e) && a(e);
                return f && f.length && f.is(":visible") && [
                    [f[c]().top + d, e]
                ] || null
            }).sort(function(a, b) { return a[0] - b[0] }).each(function() { b.offsets.push(this[0]), b.targets.push(this[1]) })
        }, b.prototype.process = function() {
            var a, b = this.$scrollElement.scrollTop() + this.options.offset,
                c = this.getScrollHeight(),
                d = this.options.offset + c - this.$scrollElement.height(),
                e = this.offsets,
                f = this.targets,
                g = this.activeTarget;
            if (this.scrollHeight != c && this.refresh(), b >= d) return g != (a = f[f.length - 1]) && this.activate(a);
            if (g && b < e[0]) return this.activeTarget = null, this.clear();
            for (a = e.length; a--;) g != f[a] && b >= e[a] && (void 0 === e[a + 1] || b < e[a + 1]) && this.activate(f[a])
        }, b.prototype.activate = function(b) {
            this.activeTarget = b, this.clear();
            var c = this.selector + '[data-target="' + b + '"],' + this.selector + '[href="' + b + '"]',
                d = a(c).parents("li").addClass("active");
            d.parent(".dropdown-menu").length && (d = d.closest("li.dropdown").addClass("active")), d.trigger("activate.bs.scrollspy")
        }, b.prototype.clear = function() { a(this.selector).parentsUntil(this.options.target, ".active").removeClass("active") };
        var d = a.fn.scrollspy;
        a.fn.scrollspy = c, a.fn.scrollspy.Constructor = b, a.fn.scrollspy.noConflict = function() { return a.fn.scrollspy = d, this }, a(window).on("load.bs.scrollspy.data-api", function() {
            a('[data-spy="scroll"]').each(function() {
                var b = a(this);
                c.call(b, b.data())
            })
        })
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.tab");
                e || d.data("bs.tab", e = new c(this)), "string" == typeof b && e[b]()
            })
        }
        var c = function(b) { this.element = a(b) };
        c.VERSION = "3.3.7", c.TRANSITION_DURATION = 150, c.prototype.show = function() {
            var b = this.element,
                c = b.closest("ul:not(.dropdown-menu)"),
                d = b.data("target");
            if (d || (d = b.attr("href"), d = d && d.replace(/.*(?=#[^\s]*$)/, "")), !b.parent("li").hasClass("active")) {
                var e = c.find(".active:last a"),
                    f = a.Event("hide.bs.tab", { relatedTarget: b[0] }),
                    g = a.Event("show.bs.tab", { relatedTarget: e[0] });
                if (e.trigger(f), b.trigger(g), !g.isDefaultPrevented() && !f.isDefaultPrevented()) {
                    var h = a(d);
                    this.activate(b.closest("li"), c), this.activate(h, h.parent(), function() { e.trigger({ type: "hidden.bs.tab", relatedTarget: b[0] }), b.trigger({ type: "shown.bs.tab", relatedTarget: e[0] }) })
                }
            }
        }, c.prototype.activate = function(b, d, e) {
            function f() { g.removeClass("active").find("> .dropdown-menu > .active").removeClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !1), b.addClass("active").find('[data-toggle="tab"]').attr("aria-expanded", !0), h ? (b[0].offsetWidth, b.addClass("in")) : b.removeClass("fade"), b.parent(".dropdown-menu").length && b.closest("li.dropdown").addClass("active").end().find('[data-toggle="tab"]').attr("aria-expanded", !0), e && e() }
            var g = d.find("> .active"),
                h = e && a.support.transition && (g.length && g.hasClass("fade") || !!d.find("> .fade").length);
            g.length && h ? g.one("bsTransitionEnd", f).emulateTransitionEnd(c.TRANSITION_DURATION) : f(), g.removeClass("in")
        };
        var d = a.fn.tab;
        a.fn.tab = b, a.fn.tab.Constructor = c, a.fn.tab.noConflict = function() { return a.fn.tab = d, this };
        var e = function(c) { c.preventDefault(), b.call(a(this), "show") };
        a(document).on("click.bs.tab.data-api", '[data-toggle="tab"]', e).on("click.bs.tab.data-api", '[data-toggle="pill"]', e)
    }(jQuery), + function(a) {
        "use strict";

        function b(b) {
            return this.each(function() {
                var d = a(this),
                    e = d.data("bs.affix"),
                    f = "object" == typeof b && b;
                e || d.data("bs.affix", e = new c(this, f)), "string" == typeof b && e[b]()
            })
        }
        var c = function(b, d) { this.options = a.extend({}, c.DEFAULTS, d), this.$target = a(this.options.target).on("scroll.bs.affix.data-api", a.proxy(this.checkPosition, this)).on("click.bs.affix.data-api", a.proxy(this.checkPositionWithEventLoop, this)), this.$element = a(b), this.affixed = null, this.unpin = null, this.pinnedOffset = null, this.checkPosition() };
        c.VERSION = "3.3.7", c.RESET = "affix affix-top affix-bottom", c.DEFAULTS = { offset: 0, target: window }, c.prototype.getState = function(a, b, c, d) {
            var e = this.$target.scrollTop(),
                f = this.$element.offset(),
                g = this.$target.height();
            if (null != c && "top" == this.affixed) return e < c && "top";
            if ("bottom" == this.affixed) return null != c ? !(e + this.unpin <= f.top) && "bottom" : !(e + g <= a - d) && "bottom";
            var h = null == this.affixed,
                i = h ? e : f.top,
                j = h ? g : b;
            return null != c && e <= c ? "top" : null != d && i + j >= a - d && "bottom"
        }, c.prototype.getPinnedOffset = function() {
            if (this.pinnedOffset) return this.pinnedOffset;
            this.$element.removeClass(c.RESET).addClass("affix");
            var a = this.$target.scrollTop(),
                b = this.$element.offset();
            return this.pinnedOffset = b.top - a
        }, c.prototype.checkPositionWithEventLoop = function() { setTimeout(a.proxy(this.checkPosition, this), 1) }, c.prototype.checkPosition = function() {
            if (this.$element.is(":visible")) {
                var b = this.$element.height(),
                    d = this.options.offset,
                    e = d.top,
                    f = d.bottom,
                    g = Math.max(a(document).height(), a(document.body).height());
                "object" != typeof d && (f = e = d), "function" == typeof e && (e = d.top(this.$element)), "function" == typeof f && (f = d.bottom(this.$element));
                var h = this.getState(g, b, e, f);
                if (this.affixed != h) {
                    null != this.unpin && this.$element.css("top", "");
                    var i = "affix" + (h ? "-" + h : ""),
                        j = a.Event(i + ".bs.affix");
                    if (this.$element.trigger(j), j.isDefaultPrevented()) return;
                    this.affixed = h, this.unpin = "bottom" == h ? this.getPinnedOffset() : null, this.$element.removeClass(c.RESET).addClass(i).trigger(i.replace("affix", "affixed") + ".bs.affix")
                }
                "bottom" == h && this.$element.offset({ top: g - b - f })
            }
        };
        var d = a.fn.affix;
        a.fn.affix = b, a.fn.affix.Constructor = c, a.fn.affix.noConflict = function() { return a.fn.affix = d, this }, a(window).on("load", function() {
            a('[data-spy="affix"]').each(function() {
                var c = a(this),
                    d = c.data();
                d.offset = d.offset || {}, null != d.offsetBottom && (d.offset.bottom = d.offsetBottom), null != d.offsetTop && (d.offset.top = d.offsetTop), b.call(c, d)
            })
        })
    }(jQuery);

    // Plugin3 underscore.js
    //     Underscore.js 1.9.0
//     http://underscorejs.org
//     (c) 2009-2018 Jeremy Ashkenas, DocumentCloud and Investigative Reporters & Editors
//     Underscore may be freely distributed under the MIT license.
!function(){var n="object"==typeof self&&self.self===self&&self||"object"==typeof global&&global.global===global&&global||this||{},r=n._,e=Array.prototype,o=Object.prototype,s="undefined"!=typeof Symbol?Symbol.prototype:null,u=e.push,c=e.slice,p=o.toString,i=o.hasOwnProperty,t=Array.isArray,a=Object.keys,l=Object.create,f=function(){},h=function(n){return n instanceof h?n:this instanceof h?void(this._wrapped=n):new h(n)};"undefined"==typeof exports||exports.nodeType?n._=h:("undefined"!=typeof module&&!module.nodeType&&module.exports&&(exports=module.exports=h),exports._=h),h.VERSION="1.9.0";var v,y=function(u,i,n){if(void 0===i)return u;switch(null==n?3:n){case 1:return function(n){return u.call(i,n)};case 3:return function(n,r,t){return u.call(i,n,r,t)};case 4:return function(n,r,t,e){return u.call(i,n,r,t,e)}}return function(){return u.apply(i,arguments)}},d=function(n,r,t){return h.iteratee!==v?h.iteratee(n,r):null==n?h.identity:h.isFunction(n)?y(n,r,t):h.isObject(n)&&!h.isArray(n)?h.matcher(n):h.property(n)};h.iteratee=v=function(n,r){return d(n,r,1/0)};var g=function(u,i){return i=null==i?u.length-1:+i,function(){for(var n=Math.max(arguments.length-i,0),r=Array(n),t=0;t<n;t++)r[t]=arguments[t+i];switch(i){case 0:return u.call(this,r);case 1:return u.call(this,arguments[0],r);case 2:return u.call(this,arguments[0],arguments[1],r)}var e=Array(i+1);for(t=0;t<i;t++)e[t]=arguments[t];return e[i]=r,u.apply(this,e)}},m=function(n){if(!h.isObject(n))return{};if(l)return l(n);f.prototype=n;var r=new f;return f.prototype=null,r},b=function(r){return function(n){return null==n?void 0:n[r]}},j=function(n,r){for(var t=r.length,e=0;e<t;e++){if(null==n)return;n=n[r[e]]}return t?n:void 0},x=Math.pow(2,53)-1,_=b("length"),A=function(n){var r=_(n);return"number"==typeof r&&0<=r&&r<=x};h.each=h.forEach=function(n,r,t){var e,u;if(r=y(r,t),A(n))for(e=0,u=n.length;e<u;e++)r(n[e],e,n);else{var i=h.keys(n);for(e=0,u=i.length;e<u;e++)r(n[i[e]],i[e],n)}return n},h.map=h.collect=function(n,r,t){r=d(r,t);for(var e=!A(n)&&h.keys(n),u=(e||n).length,i=Array(u),o=0;o<u;o++){var a=e?e[o]:o;i[o]=r(n[a],a,n)}return i};var w=function(c){return function(n,r,t,e){var u=3<=arguments.length;return function(n,r,t,e){var u=!A(n)&&h.keys(n),i=(u||n).length,o=0<c?0:i-1;for(e||(t=n[u?u[o]:o],o+=c);0<=o&&o<i;o+=c){var a=u?u[o]:o;t=r(t,n[a],a,n)}return t}(n,y(r,e,4),t,u)}};h.reduce=h.foldl=h.inject=w(1),h.reduceRight=h.foldr=w(-1),h.find=h.detect=function(n,r,t){var e=(A(n)?h.findIndex:h.findKey)(n,r,t);if(void 0!==e&&-1!==e)return n[e]},h.filter=h.select=function(n,e,r){var u=[];return e=d(e,r),h.each(n,function(n,r,t){e(n,r,t)&&u.push(n)}),u},h.reject=function(n,r,t){return h.filter(n,h.negate(d(r)),t)},h.every=h.all=function(n,r,t){r=d(r,t);for(var e=!A(n)&&h.keys(n),u=(e||n).length,i=0;i<u;i++){var o=e?e[i]:i;if(!r(n[o],o,n))return!1}return!0},h.some=h.any=function(n,r,t){r=d(r,t);for(var e=!A(n)&&h.keys(n),u=(e||n).length,i=0;i<u;i++){var o=e?e[i]:i;if(r(n[o],o,n))return!0}return!1},h.contains=h.includes=h.include=function(n,r,t,e){return A(n)||(n=h.values(n)),("number"!=typeof t||e)&&(t=0),0<=h.indexOf(n,r,t)},h.invoke=g(function(n,t,e){var u,i;return h.isFunction(t)?i=t:h.isArray(t)&&(u=t.slice(0,-1),t=t[t.length-1]),h.map(n,function(n){var r=i;if(!r){if(u&&u.length&&(n=j(n,u)),null==n)return;r=n[t]}return null==r?r:r.apply(n,e)})}),h.pluck=function(n,r){return h.map(n,h.property(r))},h.where=function(n,r){return h.filter(n,h.matcher(r))},h.findWhere=function(n,r){return h.find(n,h.matcher(r))},h.max=function(n,e,r){var t,u,i=-1/0,o=-1/0;if(null==e||"number"==typeof e&&"object"!=typeof n[0]&&null!=n)for(var a=0,c=(n=A(n)?n:h.values(n)).length;a<c;a++)null!=(t=n[a])&&i<t&&(i=t);else e=d(e,r),h.each(n,function(n,r,t){u=e(n,r,t),(o<u||u===-1/0&&i===-1/0)&&(i=n,o=u)});return i},h.min=function(n,e,r){var t,u,i=1/0,o=1/0;if(null==e||"number"==typeof e&&"object"!=typeof n[0]&&null!=n)for(var a=0,c=(n=A(n)?n:h.values(n)).length;a<c;a++)null!=(t=n[a])&&t<i&&(i=t);else e=d(e,r),h.each(n,function(n,r,t){((u=e(n,r,t))<o||u===1/0&&i===1/0)&&(i=n,o=u)});return i},h.shuffle=function(n){return h.sample(n,1/0)},h.sample=function(n,r,t){if(null==r||t)return A(n)||(n=h.values(n)),n[h.random(n.length-1)];var e=A(n)?h.clone(n):h.values(n),u=_(e);r=Math.max(Math.min(r,u),0);for(var i=u-1,o=0;o<r;o++){var a=h.random(o,i),c=e[o];e[o]=e[a],e[a]=c}return e.slice(0,r)},h.sortBy=function(n,e,r){var u=0;return e=d(e,r),h.pluck(h.map(n,function(n,r,t){return{value:n,index:u++,criteria:e(n,r,t)}}).sort(function(n,r){var t=n.criteria,e=r.criteria;if(t!==e){if(e<t||void 0===t)return 1;if(t<e||void 0===e)return-1}return n.index-r.index}),"value")};var O=function(o,r){return function(e,u,n){var i=r?[[],[]]:{};return u=d(u,n),h.each(e,function(n,r){var t=u(n,r,e);o(i,n,t)}),i}};h.groupBy=O(function(n,r,t){h.has(n,t)?n[t].push(r):n[t]=[r]}),h.indexBy=O(function(n,r,t){n[t]=r}),h.countBy=O(function(n,r,t){h.has(n,t)?n[t]++:n[t]=1});var k=/[^\ud800-\udfff]|[\ud800-\udbff][\udc00-\udfff]|[\ud800-\udfff]/g;h.toArray=function(n){return n?h.isArray(n)?c.call(n):h.isString(n)?n.match(k):A(n)?h.map(n,h.identity):h.values(n):[]},h.size=function(n){return null==n?0:A(n)?n.length:h.keys(n).length},h.partition=O(function(n,r,t){n[t?0:1].push(r)},!0),h.first=h.head=h.take=function(n,r,t){if(!(null==n||n.length<1))return null==r||t?n[0]:h.initial(n,n.length-r)},h.initial=function(n,r,t){return c.call(n,0,Math.max(0,n.length-(null==r||t?1:r)))},h.last=function(n,r,t){if(!(null==n||n.length<1))return null==r||t?n[n.length-1]:h.rest(n,Math.max(0,n.length-r))},h.rest=h.tail=h.drop=function(n,r,t){return c.call(n,null==r||t?1:r)},h.compact=function(n){return h.filter(n,Boolean)};var S=function(n,r,t,e){for(var u=(e=e||[]).length,i=0,o=_(n);i<o;i++){var a=n[i];if(A(a)&&(h.isArray(a)||h.isArguments(a)))if(r)for(var c=0,l=a.length;c<l;)e[u++]=a[c++];else S(a,r,t,e),u=e.length;else t||(e[u++]=a)}return e};h.flatten=function(n,r){return S(n,r,!1)},h.without=g(function(n,r){return h.difference(n,r)}),h.uniq=h.unique=function(n,r,t,e){h.isBoolean(r)||(e=t,t=r,r=!1),null!=t&&(t=d(t,e));for(var u=[],i=[],o=0,a=_(n);o<a;o++){var c=n[o],l=t?t(c,o,n):c;r&&!t?(o&&i===l||u.push(c),i=l):t?h.contains(i,l)||(i.push(l),u.push(c)):h.contains(u,c)||u.push(c)}return u},h.union=g(function(n){return h.uniq(S(n,!0,!0))}),h.intersection=function(n){for(var r=[],t=arguments.length,e=0,u=_(n);e<u;e++){var i=n[e];if(!h.contains(r,i)){var o;for(o=1;o<t&&h.contains(arguments[o],i);o++);o===t&&r.push(i)}}return r},h.difference=g(function(n,r){return r=S(r,!0,!0),h.filter(n,function(n){return!h.contains(r,n)})}),h.unzip=function(n){for(var r=n&&h.max(n,_).length||0,t=Array(r),e=0;e<r;e++)t[e]=h.pluck(n,e);return t},h.zip=g(h.unzip),h.object=function(n,r){for(var t={},e=0,u=_(n);e<u;e++)r?t[n[e]]=r[e]:t[n[e][0]]=n[e][1];return t};var M=function(i){return function(n,r,t){r=d(r,t);for(var e=_(n),u=0<i?0:e-1;0<=u&&u<e;u+=i)if(r(n[u],u,n))return u;return-1}};h.findIndex=M(1),h.findLastIndex=M(-1),h.sortedIndex=function(n,r,t,e){for(var u=(t=d(t,e,1))(r),i=0,o=_(n);i<o;){var a=Math.floor((i+o)/2);t(n[a])<u?i=a+1:o=a}return i};var F=function(i,o,a){return function(n,r,t){var e=0,u=_(n);if("number"==typeof t)0<i?e=0<=t?t:Math.max(t+u,e):u=0<=t?Math.min(t+1,u):t+u+1;else if(a&&t&&u)return n[t=a(n,r)]===r?t:-1;if(r!=r)return 0<=(t=o(c.call(n,e,u),h.isNaN))?t+e:-1;for(t=0<i?e:u-1;0<=t&&t<u;t+=i)if(n[t]===r)return t;return-1}};h.indexOf=F(1,h.findIndex,h.sortedIndex),h.lastIndexOf=F(-1,h.findLastIndex),h.range=function(n,r,t){null==r&&(r=n||0,n=0),t||(t=r<n?-1:1);for(var e=Math.max(Math.ceil((r-n)/t),0),u=Array(e),i=0;i<e;i++,n+=t)u[i]=n;return u},h.chunk=function(n,r){if(null==r||r<1)return[];for(var t=[],e=0,u=n.length;e<u;)t.push(c.call(n,e,e+=r));return t};var E=function(n,r,t,e,u){if(!(e instanceof r))return n.apply(t,u);var i=m(n.prototype),o=n.apply(i,u);return h.isObject(o)?o:i};h.bind=g(function(r,t,e){if(!h.isFunction(r))throw new TypeError("Bind must be called on a function");var u=g(function(n){return E(r,u,t,this,e.concat(n))});return u}),h.partial=g(function(u,i){var o=h.partial.placeholder,a=function(){for(var n=0,r=i.length,t=Array(r),e=0;e<r;e++)t[e]=i[e]===o?arguments[n++]:i[e];for(;n<arguments.length;)t.push(arguments[n++]);return E(u,a,this,this,t)};return a}),(h.partial.placeholder=h).bindAll=g(function(n,r){var t=(r=S(r,!1,!1)).length;if(t<1)throw new Error("bindAll must be passed function names");for(;t--;){var e=r[t];n[e]=h.bind(n[e],n)}}),h.memoize=function(e,u){var i=function(n){var r=i.cache,t=""+(u?u.apply(this,arguments):n);return h.has(r,t)||(r[t]=e.apply(this,arguments)),r[t]};return i.cache={},i},h.delay=g(function(n,r,t){return setTimeout(function(){return n.apply(null,t)},r)}),h.defer=h.partial(h.delay,h,1),h.throttle=function(t,e,u){var i,o,a,c,l=0;u||(u={});var f=function(){l=!1===u.leading?0:h.now(),i=null,c=t.apply(o,a),i||(o=a=null)},n=function(){var n=h.now();l||!1!==u.leading||(l=n);var r=e-(n-l);return o=this,a=arguments,r<=0||e<r?(i&&(clearTimeout(i),i=null),l=n,c=t.apply(o,a),i||(o=a=null)):i||!1===u.trailing||(i=setTimeout(f,r)),c};return n.cancel=function(){clearTimeout(i),l=0,i=o=a=null},n},h.debounce=function(t,e,u){var i,o,a=function(n,r){i=null,r&&(o=t.apply(n,r))},n=g(function(n){if(i&&clearTimeout(i),u){var r=!i;i=setTimeout(a,e),r&&(o=t.apply(this,n))}else i=h.delay(a,e,this,n);return o});return n.cancel=function(){clearTimeout(i),i=null},n},h.wrap=function(n,r){return h.partial(r,n)},h.negate=function(n){return function(){return!n.apply(this,arguments)}},h.compose=function(){var t=arguments,e=t.length-1;return function(){for(var n=e,r=t[e].apply(this,arguments);n--;)r=t[n].call(this,r);return r}},h.after=function(n,r){return function(){if(--n<1)return r.apply(this,arguments)}},h.before=function(n,r){var t;return function(){return 0<--n&&(t=r.apply(this,arguments)),n<=1&&(r=null),t}},h.once=h.partial(h.before,2),h.restArguments=g;var N=!{toString:null}.propertyIsEnumerable("toString"),I=["valueOf","isPrototypeOf","toString","propertyIsEnumerable","hasOwnProperty","toLocaleString"],T=function(n,r){var t=I.length,e=n.constructor,u=h.isFunction(e)&&e.prototype||o,i="constructor";for(h.has(n,i)&&!h.contains(r,i)&&r.push(i);t--;)(i=I[t])in n&&n[i]!==u[i]&&!h.contains(r,i)&&r.push(i)};h.keys=function(n){if(!h.isObject(n))return[];if(a)return a(n);var r=[];for(var t in n)h.has(n,t)&&r.push(t);return N&&T(n,r),r},h.allKeys=function(n){if(!h.isObject(n))return[];var r=[];for(var t in n)r.push(t);return N&&T(n,r),r},h.values=function(n){for(var r=h.keys(n),t=r.length,e=Array(t),u=0;u<t;u++)e[u]=n[r[u]];return e},h.mapObject=function(n,r,t){r=d(r,t);for(var e=h.keys(n),u=e.length,i={},o=0;o<u;o++){var a=e[o];i[a]=r(n[a],a,n)}return i},h.pairs=function(n){for(var r=h.keys(n),t=r.length,e=Array(t),u=0;u<t;u++)e[u]=[r[u],n[r[u]]];return e},h.invert=function(n){for(var r={},t=h.keys(n),e=0,u=t.length;e<u;e++)r[n[t[e]]]=t[e];return r},h.functions=h.methods=function(n){var r=[];for(var t in n)h.isFunction(n[t])&&r.push(t);return r.sort()};var B=function(c,l){return function(n){var r=arguments.length;if(l&&(n=Object(n)),r<2||null==n)return n;for(var t=1;t<r;t++)for(var e=arguments[t],u=c(e),i=u.length,o=0;o<i;o++){var a=u[o];l&&void 0!==n[a]||(n[a]=e[a])}return n}};h.extend=B(h.allKeys),h.extendOwn=h.assign=B(h.keys),h.findKey=function(n,r,t){r=d(r,t);for(var e,u=h.keys(n),i=0,o=u.length;i<o;i++)if(r(n[e=u[i]],e,n))return e};var R,q,K=function(n,r,t){return r in t};h.pick=g(function(n,r){var t={},e=r[0];if(null==n)return t;h.isFunction(e)?(1<r.length&&(e=y(e,r[1])),r=h.allKeys(n)):(e=K,r=S(r,!1,!1),n=Object(n));for(var u=0,i=r.length;u<i;u++){var o=r[u],a=n[o];e(a,o,n)&&(t[o]=a)}return t}),h.omit=g(function(n,t){var r,e=t[0];return h.isFunction(e)?(e=h.negate(e),1<t.length&&(r=t[1])):(t=h.map(S(t,!1,!1),String),e=function(n,r){return!h.contains(t,r)}),h.pick(n,e,r)}),h.defaults=B(h.allKeys,!0),h.create=function(n,r){var t=m(n);return r&&h.extendOwn(t,r),t},h.clone=function(n){return h.isObject(n)?h.isArray(n)?n.slice():h.extend({},n):n},h.tap=function(n,r){return r(n),n},h.isMatch=function(n,r){var t=h.keys(r),e=t.length;if(null==n)return!e;for(var u=Object(n),i=0;i<e;i++){var o=t[i];if(r[o]!==u[o]||!(o in u))return!1}return!0},R=function(n,r,t,e){if(n===r)return 0!==n||1/n==1/r;if(null==n||null==r)return!1;if(n!=n)return r!=r;var u=typeof n;return("function"===u||"object"===u||"object"==typeof r)&&q(n,r,t,e)},q=function(n,r,t,e){n instanceof h&&(n=n._wrapped),r instanceof h&&(r=r._wrapped);var u=p.call(n);if(u!==p.call(r))return!1;switch(u){case"[object RegExp]":case"[object String]":return""+n==""+r;case"[object Number]":return+n!=+n?+r!=+r:0==+n?1/+n==1/r:+n==+r;case"[object Date]":case"[object Boolean]":return+n==+r;case"[object Symbol]":return s.valueOf.call(n)===s.valueOf.call(r)}var i="[object Array]"===u;if(!i){if("object"!=typeof n||"object"!=typeof r)return!1;var o=n.constructor,a=r.constructor;if(o!==a&&!(h.isFunction(o)&&o instanceof o&&h.isFunction(a)&&a instanceof a)&&"constructor"in n&&"constructor"in r)return!1}e=e||[];for(var c=(t=t||[]).length;c--;)if(t[c]===n)return e[c]===r;if(t.push(n),e.push(r),i){if((c=n.length)!==r.length)return!1;for(;c--;)if(!R(n[c],r[c],t,e))return!1}else{var l,f=h.keys(n);if(c=f.length,h.keys(r).length!==c)return!1;for(;c--;)if(l=f[c],!h.has(r,l)||!R(n[l],r[l],t,e))return!1}return t.pop(),e.pop(),!0},h.isEqual=function(n,r){return R(n,r)},h.isEmpty=function(n){return null==n||(A(n)&&(h.isArray(n)||h.isString(n)||h.isArguments(n))?0===n.length:0===h.keys(n).length)},h.isElement=function(n){return!(!n||1!==n.nodeType)},h.isArray=t||function(n){return"[object Array]"===p.call(n)},h.isObject=function(n){var r=typeof n;return"function"===r||"object"===r&&!!n},h.each(["Arguments","Function","String","Number","Date","RegExp","Error","Symbol","Map","WeakMap","Set","WeakSet"],function(r){h["is"+r]=function(n){return p.call(n)==="[object "+r+"]"}}),h.isArguments(arguments)||(h.isArguments=function(n){return h.has(n,"callee")});var z=n.document&&n.document.childNodes;"function"!=typeof/./&&"object"!=typeof Int8Array&&"function"!=typeof z&&(h.isFunction=function(n){return"function"==typeof n||!1}),h.isFinite=function(n){return!h.isSymbol(n)&&isFinite(n)&&!isNaN(parseFloat(n))},h.isNaN=function(n){return h.isNumber(n)&&isNaN(n)},h.isBoolean=function(n){return!0===n||!1===n||"[object Boolean]"===p.call(n)},h.isNull=function(n){return null===n},h.isUndefined=function(n){return void 0===n},h.has=function(n,r){if(!h.isArray(r))return null!=n&&i.call(n,r);for(var t=r.length,e=0;e<t;e++){var u=r[e];if(null==n||!i.call(n,u))return!1;n=n[u]}return!!t},h.noConflict=function(){return n._=r,this},h.identity=function(n){return n},h.constant=function(n){return function(){return n}},h.noop=function(){},h.property=function(r){return h.isArray(r)?function(n){return j(n,r)}:b(r)},h.propertyOf=function(r){return null==r?function(){}:function(n){return h.isArray(n)?j(r,n):r[n]}},h.matcher=h.matches=function(r){return r=h.extendOwn({},r),function(n){return h.isMatch(n,r)}},h.times=function(n,r,t){var e=Array(Math.max(0,n));r=y(r,t,1);for(var u=0;u<n;u++)e[u]=r(u);return e},h.random=function(n,r){return null==r&&(r=n,n=0),n+Math.floor(Math.random()*(r-n+1))},h.now=Date.now||function(){return(new Date).getTime()};var D={"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#x27;","`":"&#x60;"},L=h.invert(D),P=function(r){var t=function(n){return r[n]},n="(?:"+h.keys(r).join("|")+")",e=RegExp(n),u=RegExp(n,"g");return function(n){return n=null==n?"":""+n,e.test(n)?n.replace(u,t):n}};h.escape=P(D),h.unescape=P(L),h.result=function(n,r,t){h.isArray(r)||(r=[r]);var e=r.length;if(!e)return h.isFunction(t)?t.call(n):t;for(var u=0;u<e;u++){var i=null==n?void 0:n[r[u]];void 0===i&&(i=t,u=e),n=h.isFunction(i)?i.call(n):i}return n};var W=0;h.uniqueId=function(n){var r=++W+"";return n?n+r:r},h.templateSettings={evaluate:/<%([\s\S]+?)%>/g,interpolate:/<%=([\s\S]+?)%>/g,escape:/<%-([\s\S]+?)%>/g};var C=/(.)^/,J={"'":"'","\\":"\\","\r":"r","\n":"n","\u2028":"u2028","\u2029":"u2029"},U=/\\|'|\r|\n|\u2028|\u2029/g,V=function(n){return"\\"+J[n]};h.template=function(i,n,r){!n&&r&&(n=r),n=h.defaults({},n,h.templateSettings);var t,e=RegExp([(n.escape||C).source,(n.interpolate||C).source,(n.evaluate||C).source].join("|")+"|$","g"),o=0,a="__p+='";i.replace(e,function(n,r,t,e,u){return a+=i.slice(o,u).replace(U,V),o=u+n.length,r?a+="'+\n((__t=("+r+"))==null?'':_.escape(__t))+\n'":t?a+="'+\n((__t=("+t+"))==null?'':__t)+\n'":e&&(a+="';\n"+e+"\n__p+='"),n}),a+="';\n",n.variable||(a="with(obj||{}){\n"+a+"}\n"),a="var __t,__p='',__j=Array.prototype.join,"+"print=function(){__p+=__j.call(arguments,'');};\n"+a+"return __p;\n";try{t=new Function(n.variable||"obj","_",a)}catch(n){throw n.source=a,n}var u=function(n){return t.call(this,n,h)},c=n.variable||"obj";return u.source="function("+c+"){\n"+a+"}",u},h.chain=function(n){var r=h(n);return r._chain=!0,r};var $=function(n,r){return n._chain?h(r).chain():r};h.mixin=function(t){return h.each(h.functions(t),function(n){var r=h[n]=t[n];h.prototype[n]=function(){var n=[this._wrapped];return u.apply(n,arguments),$(this,r.apply(h,n))}}),h},h.mixin(h),h.each(["pop","push","reverse","shift","sort","splice","unshift"],function(r){var t=e[r];h.prototype[r]=function(){var n=this._wrapped;return t.apply(n,arguments),"shift"!==r&&"splice"!==r||0!==n.length||delete n[0],$(this,n)}}),h.each(["concat","join","slice"],function(n){var r=e[n];h.prototype[n]=function(){return $(this,r.apply(this._wrapped,arguments))}}),h.prototype.value=function(){return this._wrapped},h.prototype.valueOf=h.prototype.toJSON=h.prototype.value,h.prototype.toString=function(){return String(this._wrapped)},"function"==typeof define&&define.amd&&define("underscore",[],function(){return h})}();

    // Plugin4 Typeahead.js
    /*!
     * typeahead.js 0.11.1
     * https://github.com/twitter/typeahead.js
     * Copyright 2013-2015 Twitter, Inc. and other contributors; Licensed MIT
     */

    (function(root, factory) {
        if (typeof define === "function" && define.amd) {
            define("bloodhound", ["jquery"], function(a0) {
                return root["Bloodhound"] = factory(a0);
            });
        } else if (typeof exports === "object") {
            module.exports = factory(require("jquery"));
        } else {
            root["Bloodhound"] = factory(jQuery);
        }
    })(this, function($) {
        var _ = function() {
            "use strict";
            return {
                isMsie: function() {
                    return /(msie|trident)/i.test(navigator.userAgent) ? navigator.userAgent.match(/(msie |rv:)(\d+(.\d+)?)/i)[2] : false;
                },
                isBlankString: function(str) {
                    return !str || /^\s*$/.test(str);
                },
                escapeRegExChars: function(str) {
                    return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
                },
                isString: function(obj) {
                    return typeof obj === "string";
                },
                isNumber: function(obj) {
                    return typeof obj === "number";
                },
                isArray: $.isArray,
                isFunction: $.isFunction,
                isObject: $.isPlainObject,
                isUndefined: function(obj) {
                    return typeof obj === "undefined";
                },
                isElement: function(obj) {
                    return !!(obj && obj.nodeType === 1);
                },
                isJQuery: function(obj) {
                    return obj instanceof $;
                },
                toStr: function toStr(s) {
                    return _.isUndefined(s) || s === null ? "" : s + "";
                },
                bind: $.proxy,
                each: function(collection, cb) {
                    $.each(collection, reverseArgs);

                    function reverseArgs(index, value) {
                        return cb(value, index);
                    }
                },
                map: $.map,
                filter: $.grep,
                every: function(obj, test) {
                    var result = true;
                    if (!obj) {
                        return result;
                    }
                    $.each(obj, function(key, val) {
                        if (!(result = test.call(null, val, key, obj))) {
                            return false;
                        }
                    });
                    return !!result;
                },
                some: function(obj, test) {
                    var result = false;
                    if (!obj) {
                        return result;
                    }
                    $.each(obj, function(key, val) {
                        if (result = test.call(null, val, key, obj)) {
                            return false;
                        }
                    });
                    return !!result;
                },
                mixin: $.extend,
                identity: function(x) {
                    return x;
                },
                clone: function(obj) {
                    return $.extend(true, {}, obj);
                },
                getIdGenerator: function() {
                    var counter = 0;
                    return function() {
                        return counter++;
                    };
                },
                templatify: function templatify(obj) {
                    return $.isFunction(obj) ? obj : template;

                    function template() {
                        return String(obj);
                    }
                },
                defer: function(fn) {
                    setTimeout(fn, 0);
                },
                debounce: function(func, wait, immediate) {
                    var timeout, result;
                    return function() {
                        var context = this,
                            args = arguments,
                            later, callNow;
                        later = function() {
                            timeout = null;
                            if (!immediate) {
                                result = func.apply(context, args);
                            }
                        };
                        callNow = immediate && !timeout;
                        clearTimeout(timeout);
                        timeout = setTimeout(later, wait);
                        if (callNow) {
                            result = func.apply(context, args);
                        }
                        return result;
                    };
                },
                throttle: function(func, wait) {
                    var context, args, timeout, result, previous, later;
                    previous = 0;
                    later = function() {
                        previous = new Date();
                        timeout = null;
                        result = func.apply(context, args);
                    };
                    return function() {
                        var now = new Date(),
                            remaining = wait - (now - previous);
                        context = this;
                        args = arguments;
                        if (remaining <= 0) {
                            clearTimeout(timeout);
                            timeout = null;
                            previous = now;
                            result = func.apply(context, args);
                        } else if (!timeout) {
                            timeout = setTimeout(later, remaining);
                        }
                        return result;
                    };
                },
                stringify: function(val) {
                    return _.isString(val) ? val : JSON.stringify(val);
                },
                noop: function() {}
            };
        }();
        var VERSION = "0.11.1";
        var tokenizers = function() {
            "use strict";
            return {
                nonword: nonword,
                whitespace: whitespace,
                obj: {
                    nonword: getObjTokenizer(nonword),
                    whitespace: getObjTokenizer(whitespace)
                }
            };

            function whitespace(str) {
                str = _.toStr(str);
                return str ? str.split(/\s+/) : [];
            }

            function nonword(str) {
                str = _.toStr(str);
                return str ? str.split(/\W+/) : [];
            }

            function getObjTokenizer(tokenizer) {
                return function setKey(keys) {
                    keys = _.isArray(keys) ? keys : [].slice.call(arguments, 0);
                    return function tokenize(o) {
                        var tokens = [];
                        _.each(keys, function(k) {
                            tokens = tokens.concat(tokenizer(_.toStr(o[k])));
                        });
                        return tokens;
                    };
                };
            }
        }();
        var LruCache = function() {
            "use strict";

            function LruCache(maxSize) {
                this.maxSize = _.isNumber(maxSize) ? maxSize : 100;
                this.reset();
                if (this.maxSize <= 0) {
                    this.set = this.get = $.noop;
                }
            }
            _.mixin(LruCache.prototype, {
                set: function set(key, val) {
                    var tailItem = this.list.tail,
                        node;
                    if (this.size >= this.maxSize) {
                        this.list.remove(tailItem);
                        delete this.hash[tailItem.key];
                        this.size--;
                    }
                    if (node = this.hash[key]) {
                        node.val = val;
                        this.list.moveToFront(node);
                    } else {
                        node = new Node(key, val);
                        this.list.add(node);
                        this.hash[key] = node;
                        this.size++;
                    }
                },
                get: function get(key) {
                    var node = this.hash[key];
                    if (node) {
                        this.list.moveToFront(node);
                        return node.val;
                    }
                },
                reset: function reset() {
                    this.size = 0;
                    this.hash = {};
                    this.list = new List();
                }
            });

            function List() {
                this.head = this.tail = null;
            }
            _.mixin(List.prototype, {
                add: function add(node) {
                    if (this.head) {
                        node.next = this.head;
                        this.head.prev = node;
                    }
                    this.head = node;
                   this.tail = this.tail || node;
                },
                remove: function remove(node) {
                    node.prev ? node.prev.next = node.next : this.head = node.next;
                    node.next ? node.next.prev = node.prev : this.tail = node.prev;
                },
                moveToFront: function(node) {
                    this.remove(node);
                    this.add(node);
                }
            });

            function Node(key, val) {
                this.key = key;
                this.val = val;
                this.prev = this.next = null;
            }
            return LruCache;
        }();
        var PersistentStorage = function() {
            "use strict";
            var LOCAL_STORAGE;
            try {
                LOCAL_STORAGE = window.localStorage;
                LOCAL_STORAGE.setItem("~~~", "!");
                LOCAL_STORAGE.removeItem("~~~");
            } catch (err) {
                LOCAL_STORAGE = null;
            }

            function PersistentStorage(namespace, override) {
                this.prefix = ["__", namespace, "__"].join("");
                this.ttlKey = "__ttl__";
                this.keyMatcher = new RegExp("^" + _.escapeRegExChars(this.prefix));
                this.ls = override || LOCAL_STORAGE;
                !this.ls && this._noop();
            }
            _.mixin(PersistentStorage.prototype, {
                _prefix: function(key) {
                    return this.prefix + key;
                },
                _ttlKey: function(key) {
                    return this._prefix(key) + this.ttlKey;
                },
                _noop: function() {
                    this.get = this.set = this.remove = this.clear = this.isExpired = _.noop;
                },
                _safeSet: function(key, val) {
                    try {
                        this.ls.setItem(key, val);
                    } catch (err) {
                        if (err.name === "QuotaExceededError") {
                            this.clear();
                            this._noop();
                        }
                    }
                },
                get: function(key) {
                    if (this.isExpired(key)) {
                        this.remove(key);
                    }
                    return decode(this.ls.getItem(this._prefix(key)));
                },
                set: function(key, val, ttl) {
                    if (_.isNumber(ttl)) {
                        this._safeSet(this._ttlKey(key), encode(now() + ttl));
                    } else {
                        this.ls.removeItem(this._ttlKey(key));
                    }
                    return this._safeSet(this._prefix(key), encode(val));
               },
                remove: function(key) {
                    this.ls.removeItem(this._ttlKey(key));
                    this.ls.removeItem(this._prefix(key));
                    return this;
                },
                clear: function() {
                    var i, keys = gatherMatchingKeys(this.keyMatcher);
                    for (i = keys.length; i--;) {
                        this.remove(keys[i]);
                    }
                    return this;
                },
                isExpired: function(key) {
                    var ttl = decode(this.ls.getItem(this._ttlKey(key)));
                    return _.isNumber(ttl) && now() > ttl ? true : false;
                }
            });
            return PersistentStorage;

            function now() {
                return new Date().getTime();
            }

            function encode(val) {
                return JSON.stringify(_.isUndefined(val) ? null : val);
            }

            function decode(val) {
                return $.parseJSON(val);
            }

            function gatherMatchingKeys(keyMatcher) {
                var i, key, keys = [],
                    len = LOCAL_STORAGE.length;
                for (i = 0; i < len; i++) {
                    if ((key = LOCAL_STORAGE.key(i)).match(keyMatcher)) {
                        keys.push(key.replace(keyMatcher, ""));
                    }
                }
                return keys;
            }
        }();
        var Transport = function() {
            "use strict";
            var pendingRequestsCount = 0,
                pendingRequests = {},
                maxPendingRequests = 6,
                sharedCache = new LruCache(10);

            function Transport(o) {
                o = o || {};
                this.cancelled = false;
                this.lastReq = null;
                this._send = o.transport;
                this._get = o.limiter ? o.limiter(this._get) : this._get;
                this._cache = o.cache === false ? new LruCache(0) : sharedCache;
            }
            Transport.setMaxPendingRequests = function setMaxPendingRequests(num) {
                maxPendingRequests = num;
            };
            Transport.resetCache = function resetCache() {
                sharedCache.reset();
            };
            _.mixin(Transport.prototype, {
                _fingerprint: function fingerprint(o) {
                    o = o || {};
                    return o.url + o.type + $.param(o.data || {});
                },
                _get: function(o, cb) {
                    var that = this,
                        fingerprint, jqXhr;
                    fingerprint = this._fingerprint(o);
                    if (this.cancelled || fingerprint !== this.lastReq) {
                        return;
                    }
                    if (jqXhr = pendingRequests[fingerprint]) {
                        jqXhr.done(done).fail(fail);
                    } else if (pendingRequestsCount < maxPendingRequests) {
                        pendingRequestsCount++;
                        pendingRequests[fingerprint] = this._send(o).done(done).fail(fail).always(always);
                    } else {
                        this.onDeckRequestArgs = [].slice.call(arguments, 0);
                    }

                    function done(resp) {
                        cb(null, resp);
                        that._cache.set(fingerprint, resp);
                    }

                    function fail() {
                        cb(true);
                    }

                    function always() {
                        pendingRequestsCount--;
                        delete pendingRequests[fingerprint];
                        if (that.onDeckRequestArgs) {
                            that._get.apply(that, that.onDeckRequestArgs);
                            that.onDeckRequestArgs = null;
                        }
                    }
                },
                get: function(o, cb) {
                    var resp, fingerprint;
                    cb = cb || $.noop;
                    o = _.isString(o) ? {
                        url: o
                    } : o || {};
                    fingerprint = this._fingerprint(o);
                    this.cancelled = false;
                    this.lastReq = fingerprint;
                    if (resp = this._cache.get(fingerprint)) {
                        cb(null, resp);
                    } else {
                        this._get(o, cb);
                    }
                },
                cancel: function() {
                    this.cancelled = true;
                }
            });
            return Transport;
        }();
        var SearchIndex = window.SearchIndex = function() {
            "use strict";
            var CHILDREN = "c",
                IDS = "i";

            function SearchIndex(o) {
                o = o || {};
                if (!o.datumTokenizer || !o.queryTokenizer) {
                    $.error("datumTokenizer and queryTokenizer are both required");
                }
                this.identify = o.identify || _.stringify;
                this.datumTokenizer = o.datumTokenizer;
                this.queryTokenizer = o.queryTokenizer;
                this.reset();
            }
            _.mixin(SearchIndex.prototype, {
                bootstrap: function bootstrap(o) {
                    this.datums = o.datums;
                    this.trie = o.trie;
                },
                add: function(data) {
                    var that = this;
                    data = _.isArray(data) ? data : [data];
                    _.each(data, function(datum) {
                        var id, tokens;
                        that.datums[id = that.identify(datum)] = datum;
                        tokens = normalizeTokens(that.datumTokenizer(datum));
                        _.each(tokens, function(token) {
                            var node, chars, ch;
                            node = that.trie;
                            chars = token.split("");
                            while (ch = chars.shift()) {
                                node = node[CHILDREN][ch] || (node[CHILDREN][ch] = newNode());
                                node[IDS].push(id);
                            }
                        });
                    });
                },
                get: function get(ids) {
                    var that = this;
                    return _.map(ids, function(id) {
                        return that.datums[id];
                    });
                },
                search: function search(query) {
                    var that = this,
                        tokens, matches;
                    tokens = normalizeTokens(this.queryTokenizer(query));
                    _.each(tokens, function(token) {
                        var node, chars, ch, ids;
                        if (matches && matches.length === 0) {
                            return false;
                        }
                        node = that.trie;
                        chars = token.split("");
                        while (node && (ch = chars.shift())) {
                            node = node[CHILDREN][ch];
                        }
                        if (node && chars.length === 0) {
                            ids = node[IDS].slice(0);
                            matches = matches ? getIntersection(matches, ids) : ids;
                        } else {
                            matches = [];
                            return false;
                        }
                    });
                    return matches ? _.map(unique(matches), function(id) {
                        return that.datums[id];
                    }) : [];
                },
                all: function all() {
                    var values = [];
                    for (var key in this.datums) {
                        values.push(this.datums[key]);
                    }
                    return values;
                },
                reset: function reset() {
                    this.datums = {};
                    this.trie = newNode();
                },
                serialize: function serialize() {
                    return {
                        datums: this.datums,
                        trie: this.trie
                    };
                }
            });
            return SearchIndex;

            function normalizeTokens(tokens) {
                tokens = _.filter(tokens, function(token) {
                    return !!token;
                });
                tokens = _.map(tokens, function(token) {
                    return token.toLowerCase();
                });
                return tokens;
            }

            function newNode() {
                var node = {};
                node[IDS] = [];
                node[CHILDREN] = {};
                return node;
            }

            function unique(array) {
                var seen = {},
                    uniques = [];
                for (var i = 0, len = array.length; i < len; i++) {
                    if (!seen[array[i]]) {
                        seen[array[i]] = true;
                        uniques.push(array[i]);
                    }
                }
                return uniques;
            }

            function getIntersection(arrayA, arrayB) {
                var ai = 0,
                    bi = 0,
                    intersection = [];
                arrayA = arrayA.sort();
                arrayB = arrayB.sort();
                var lenArrayA = arrayA.length,
                    lenArrayB = arrayB.length;
                while (ai < lenArrayA && bi < lenArrayB) {
                    if (arrayA[ai] < arrayB[bi]) {
                        ai++;
                    } else if (arrayA[ai] > arrayB[bi]) {
                        bi++;
                    } else {
                        intersection.push(arrayA[ai]);
                        ai++;
                        bi++;
                    }
                }
                return intersection;
            }
        }();
        var Prefetch = function() {
            "use strict";
            var keys;
            keys = {
                data: "data",
                protocol: "protocol",
                thumbprint: "thumbprint"
            };

            function Prefetch(o) {
                this.url = o.url;
                this.ttl = o.ttl;
                this.cache = o.cache;
                this.prepare = o.prepare;
                this.transform = o.transform;
                this.transport = o.transport;
                this.thumbprint = o.thumbprint;
                this.storage = new PersistentStorage(o.cacheKey);
            }
            _.mixin(Prefetch.prototype, {
                _settings: function settings() {
                    return {
                        url: this.url,
                        type: "GET",
                        dataType: "json"
                    };
                },
                store: function store(data) {
                    if (!this.cache) {
                        return;
                    }
                    this.storage.set(keys.data, data, this.ttl);
                    this.storage.set(keys.protocol, location.protocol, this.ttl);
                    this.storage.set(keys.thumbprint, this.thumbprint, this.ttl);
                },
                fromCache: function fromCache() {
                    var stored = {},
                        isExpired;
                    if (!this.cache) {
                        return null;
                    }
                    stored.data = this.storage.get(keys.data);
                    stored.protocol = this.storage.get(keys.protocol);
                    stored.thumbprint = this.storage.get(keys.thumbprint);
                    isExpired = stored.thumbprint !== this.thumbprint || stored.protocol !== location.protocol;
                    return stored.data && !isExpired ? stored.data : null;
                },
                fromNetwork: function(cb) {
                    var that = this,
                        settings;
                    if (!cb) {
                        return;
                    }
                    settings = this.prepare(this._settings());
                    this.transport(settings).fail(onError).done(onResponse);

                    function onError() {
                        cb(true);
                    }

                    function onResponse(resp) {
                        cb(null, that.transform(resp));
                    }
                },
                clear: function clear() {
                    this.storage.clear();
                    return this;
                }
            });
            return Prefetch;
        }();
        var Remote = function() {
            "use strict";

            function Remote(o) {
                this.url = o.url;
                this.prepare = o.prepare;
                this.transform = o.transform;
                this.transport = new Transport({
                    cache: o.cache,
                    limiter: o.limiter,
                    transport: o.transport
                });
            }
            _.mixin(Remote.prototype, {
                _settings: function settings() {
                    return {
                        url: this.url,
                        type: "GET",
                        dataType: "json"
                    };
                },
                get: function get(query, cb) {
                    var that = this,
                        settings;
                    if (!cb) {
                        return;
                    }
                    query = query || "";
                    settings = this.prepare(query, this._settings());
                    return this.transport.get(settings, onResponse);

                    function onResponse(err, resp) {
                        err ? cb([]) : cb(that.transform(resp));
                    }
                },
                cancelLastRequest: function cancelLastRequest() {
                    this.transport.cancel();
                }
            });
            return Remote;
        }();
        var oParser = function() {
            "use strict";
            return function parse(o) {
                var defaults, sorter;
                defaults = {
                    initialize: true,
                    identify: _.stringify,
                    datumTokenizer: null,
                    queryTokenizer: null,
                    sufficient: 5,
                    sorter: null,
                    local: [],
                    prefetch: null,
                    remote: null
                };
                o = _.mixin(defaults, o || {});
                !o.datumTokenizer && $.error("datumTokenizer is required");
                !o.queryTokenizer && $.error("queryTokenizer is required");
                sorter = o.sorter;
                o.sorter = sorter ? function(x) {
                    return x.sort(sorter);
                } : _.identity;
                o.local = _.isFunction(o.local) ? o.local() : o.local;
                o.prefetch = parsePrefetch(o.prefetch);
                o.remote = parseRemote(o.remote);
                return o;
            };

            function parsePrefetch(o) {
                var defaults;
                if (!o) {
                    return null;
                }
                defaults = {
                    url: null,
                    ttl: 24 * 60 * 60 * 1e3,
                    cache: true,
                    cacheKey: null,
                    thumbprint: "",
                    prepare: _.identity,
                    transform: _.identity,
                    transport: null
                };
                o = _.isString(o) ? {
                    url: o
                } : o;
                o = _.mixin(defaults, o);
                !o.url && $.error("prefetch requires url to be set");
                o.transform = o.filter || o.transform;
                o.cacheKey = o.cacheKey || o.url;
                o.thumbprint = VERSION + o.thumbprint;
                o.transport = o.transport ? callbackToDeferred(o.transport) : $.ajax;
                return o;
            }

            function parseRemote(o) {
                var defaults;
                if (!o) {
                    return;
                }
                defaults = {
                    url: null,
                    cache: true,
                    prepare: null,
                    replace: null,
                    wildcard: null,
                    limiter: null,
                    rateLimitBy: "debounce",
                    rateLimitWait: 300,
                    transform: _.identity,
                    transport: null
                };
                o = _.isString(o) ? {
                    url: o
                } : o;
                o = _.mixin(defaults, o);
                !o.url && $.error("remote requires url to be set");
                o.transform = o.filter || o.transform;
                o.prepare = toRemotePrepare(o);
                o.limiter = toLimiter(o);
                o.transport = o.transport ? callbackToDeferred(o.transport) : $.ajax;
                delete o.replace;
                delete o.wildcard;
                delete o.rateLimitBy;
                delete o.rateLimitWait;
                return o;
            }

            function toRemotePrepare(o) {
                var prepare, replace, wildcard;
                prepare = o.prepare;
                replace = o.replace;
                wildcard = o.wildcard;
                if (prepare) {
                    return prepare;
                }
                if (replace) {
                    prepare = prepareByReplace;
                } else if (o.wildcard) {
                    prepare = prepareByWildcard;
                } else {
                    prepare = idenityPrepare;
                }
                return prepare;

                function prepareByReplace(query, settings) {
                    settings.url = replace(settings.url, query);
                    return settings;
                }

                function prepareByWildcard(query, settings) {
                    settings.url = settings.url.replace(wildcard, encodeURIComponent(query));
                    return settings;
                }

                function idenityPrepare(query, settings) {
                    return settings;
                }
            }

            function toLimiter(o) {
                var limiter, method, wait;
                limiter = o.limiter;
                method = o.rateLimitBy;
                wait = o.rateLimitWait;
                if (!limiter) {
                    limiter = /^throttle$/i.test(method) ? throttle(wait) : debounce(wait);
                }
                return limiter;

                function debounce(wait) {
                    return function debounce(fn) {
                        return _.debounce(fn, wait);
                    };
                }

                function throttle(wait) {
                    return function throttle(fn) {
                        return _.throttle(fn, wait);
                    };
                }
            }

            function callbackToDeferred(fn) {
                return function wrapper(o) {
                    var deferred = $.Deferred();
                    fn(o, onSuccess, onError);
                    return deferred;

                    function onSuccess(resp) {
                        _.defer(function() {
                            deferred.resolve(resp);
                        });
                    }

                    function onError(err) {
                        _.defer(function() {
                            deferred.reject(err);
                        });
                    }
                };
            }
        }();
        var Bloodhound = function() {
            "use strict";
            var old;
            old = window && window.Bloodhound;

            function Bloodhound(o) {
                o = oParser(o);
                this.sorter = o.sorter;
                this.identify = o.identify;
                this.sufficient = o.sufficient;
                this.local = o.local;
                this.remote = o.remote ? new Remote(o.remote) : null;
                this.prefetch = o.prefetch ? new Prefetch(o.prefetch) : null;
                this.index = new SearchIndex({
                    identify: this.identify,
                    datumTokenizer: o.datumTokenizer,
                    queryTokenizer: o.queryTokenizer
                });
                o.initialize !== false && this.initialize();
            }
            Bloodhound.noConflict = function noConflict() {
                window && (window.Bloodhound = old);
                return Bloodhound;
            };
            Bloodhound.tokenizers = tokenizers;
            _.mixin(Bloodhound.prototype, {
                __ttAdapter: function ttAdapter() {
                    var that = this;
                    return this.remote ? withAsync : withoutAsync;

                    function withAsync(query, sync, async) {
                        return that.search(query, sync, async);
                    }

                    function withoutAsync(query, sync) {
                        return that.search(query, sync);
                    }
                },
                _loadPrefetch: function loadPrefetch() {
                    var that = this,
                        deferred, serialized;
                    deferred = $.Deferred();
                    if (!this.prefetch) {
                        deferred.resolve();
                    } else if (serialized = this.prefetch.fromCache()) {
                        this.index.bootstrap(serialized);
                        deferred.resolve();
                    } else {
                        this.prefetch.fromNetwork(done);
                    }
                    return deferred.promise();

                    function done(err, data) {
                        if (err) {
                            return deferred.reject();
                        }
                        that.add(data);
                        that.prefetch.store(that.index.serialize());
                        deferred.resolve();
                    }
                },
                _initialize: function initialize() {
                    var that = this,
                        deferred;
                    this.clear();
                    (this.initPromise = this._loadPrefetch()).done(addLocalToIndex);
                    return this.initPromise;

                    function addLocalToIndex() {
                        that.add(that.local);
                    }
                },
                initialize: function initialize(force) {
                    return !this.initPromise || force ? this._initialize() : this.initPromise;
                },
                add: function add(data) {
                    this.index.add(data);
                    return this;
                },
                get: function get(ids) {
                    ids = _.isArray(ids) ? ids : [].slice.call(arguments);
                    return this.index.get(ids);
                },
                search: function search(query, sync, async) {
                    var that = this,
                        local;
                    local = this.sorter(this.index.search(query));
                    sync(this.remote ? local.slice() : local);
                    if (this.remote && local.length < this.sufficient) {
                        this.remote.get(query, processRemote);
                    } else if (this.remote) {
                        this.remote.cancelLastRequest();
                    }
                    return this;

                    function processRemote(remote) {
                        var nonDuplicates = [];
                        _.each(remote, function(r) {
                            !_.some(local, function(l) {
                                return that.identify(r) === that.identify(l);
                            }) && nonDuplicates.push(r);
                        });
                        async && async(nonDuplicates);
                    }
                },
                all: function all() {
                    return this.index.all();
                },
                clear: function clear() {
                    this.index.reset();
                    return this;
                },
                clearPrefetchCache: function clearPrefetchCache() {
                    this.prefetch && this.prefetch.clear();
                    return this;
                },
                clearRemoteCache: function clearRemoteCache() {
                    Transport.resetCache();
                    return this;
                },
                ttAdapter: function ttAdapter() {
                    return this.__ttAdapter();
                }
            });
            return Bloodhound;
        }();
        return Bloodhound;
    });

    (function(root, factory) {
        if (typeof define === "function" && define.amd) {
            define("typeahead.js", ["jquery"], function(a0) {
                return factory(a0);
            });
        } else if (typeof exports === "object") {
            module.exports = factory(require("jquery"));
        } else {
            factory(jQuery);
        }
    })(this, function($) {
        var _ = function() {
            "use strict";
            return {
                isMsie: function() {
                    return /(msie|trident)/i.test(navigator.userAgent) ? navigator.userAgent.match(/(msie |rv:)(\d+(.\d+)?)/i)[2] : false;
                },
                isBlankString: function(str) {
                    return !str || /^\s*$/.test(str);
                },
                escapeRegExChars: function(str) {
                    return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
                },
                isString: function(obj) {
                    return typeof obj === "string";
                },
                isNumber: function(obj) {
                    return typeof obj === "number";
                },
                isArray: $.isArray,
                isFunction: $.isFunction,
                isObject: $.isPlainObject,
                isUndefined: function(obj) {
                    return typeof obj === "undefined";
                },
                isElement: function(obj) {
                    return !!(obj && obj.nodeType === 1);
                },
                isJQuery: function(obj) {
                    return obj instanceof $;
                },
                toStr: function toStr(s) {
                    return _.isUndefined(s) || s === null ? "" : s + "";
                },
                bind: $.proxy,
                each: function(collection, cb) {
                    $.each(collection, reverseArgs);

                    function reverseArgs(index, value) {
                        return cb(value, index);
                    }
                },
                map: $.map,
                filter: $.grep,
                every: function(obj, test) {
                    var result = true;
                    if (!obj) {
                        return result;
                    }
                    $.each(obj, function(key, val) {
                        if (!(result = test.call(null, val, key, obj))) {
                            return false;
                        }
                    });
                    return !!result;
                },
                some: function(obj, test) {
                    var result = false;
                    if (!obj) {
                        return result;
                    }
                    $.each(obj, function(key, val) {
                        if (result = test.call(null, val, key, obj)) {
                            return false;
                        }
                    });
                    return !!result;
                },
                mixin: $.extend,
                identity: function(x) {
                    return x;
                },
                clone: function(obj) {
                    return $.extend(true, {}, obj);
                },
                getIdGenerator: function() {
                    var counter = 0;
                    return function() {
                        return counter++;
                    };
                },
                templatify: function templatify(obj) {
                    return $.isFunction(obj) ? obj : template;

                    function template() {
                        return String(obj);
                    }
                },
                defer: function(fn) {
                    setTimeout(fn, 0);
                },
                debounce: function(func, wait, immediate) {
                   var timeout, result;
                    return function() {
                        var context = this,
                            args = arguments,
                            later, callNow;
                        later = function() {
                            timeout = null;
                            if (!immediate) {
                                result = func.apply(context, args);
                            }
                        };
                        callNow = immediate && !timeout;
                        clearTimeout(timeout);
                        timeout = setTimeout(later, wait);
                        if (callNow) {
                            result = func.apply(context, args);
                        }
                        return result;
                    };
                },
                throttle: function(func, wait) {
                    var context, args, timeout, result, previous, later;
                    previous = 0;
                    later = function() {
                        previous = new Date();
                        timeout = null;
                        result = func.apply(context, args);
                    };
                    return function() {
                        var now = new Date(),
                            remaining = wait - (now - previous);
                        context = this;
                        args = arguments;
                        if (remaining <= 0) {
                            clearTimeout(timeout);
                            timeout = null;
                            previous = now;
                            result = func.apply(context, args);
                        } else if (!timeout) {
                            timeout = setTimeout(later, remaining);
                        }
                        return result;
                    };
                },
                stringify: function(val) {
                    return _.isString(val) ? val : JSON.stringify(val);
                },
                noop: function() {}
            };
        }();
        var WWW = function() {
            "use strict";
            var defaultClassNames = {
                wrapper: "twitter-typeahead",
                input: "tt-input",
                hint: "tt-hint",
                menu: "tt-menu",
                dataset: "tt-dataset",
                suggestion: "tt-suggestion",
                selectable: "tt-selectable",
                empty: "tt-empty",
                open: "tt-open",
                cursor: "tt-cursor",
                highlight: "tt-highlight"
            };
            return build;

            function build(o) {
                var www, classes;
                classes = _.mixin({}, defaultClassNames, o);
                www = {
                    css: buildCss(),
                    classes: classes,
                    html: buildHtml(classes),
                    selectors: buildSelectors(classes)
                };
                return {
                    css: www.css,
                    html: www.html,
                    classes: www.classes,
                    selectors: www.selectors,
                    mixin: function(o) {
                        _.mixin(o, www);
                    }
                };
            }

            function buildHtml(c) {
                return {
                    wrapper: '<span class="' + c.wrapper + '"></span>',
                    menu: '<div class="' + c.menu + '"></div>'
                };
            }

            function buildSelectors(classes) {
                var selectors = {};
                _.each(classes, function(v, k) {
                    selectors[k] = "." + v;
                });
                return selectors;
            }

            function buildCss() {
                var css = {
                    wrapper: {
                        position: "relative",
                        display: "inline-block"
                    },
                    hint: {
                        position: "absolute",
                        top: "0",
                        left: "0",
                        borderColor: "transparent",
                        boxShadow: "none",
                        opacity: "1"
                    },
                    input: {
                        position: "relative",
                        verticalAlign: "top",
                        backgroundColor: "transparent"
                    },
                    inputWithNoHint: {
                        position: "relative",
                       verticalAlign: "top"
                    },
                    menu: {
                        position: "absolute",
                        top: "100%",
                        left: "0",
                        zIndex: "100",
                        display: "none"
                    },
                    ltr: {
                        left: "0",
                        right: "auto"
                    },
                    rtl: {
                        left: "auto",
                        right: " 0"
                    }
                };
                if (_.isMsie()) {
                    _.mixin(css.input, {
                        backgroundImage: "url(data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7)"
                    });
                }
                return css;
            }
        }();
        var EventBus = function() {
            "use strict";
            var namespace, deprecationMap;
            namespace = "typeahead:";
            deprecationMap = {
                render: "rendered",
                cursorchange: "cursorchanged",
                select: "selected",
                autocomplete: "autocompleted"
            };

            function EventBus(o) {
                if (!o || !o.el) {
                    $.error("EventBus initialized without el");
                }
                this.$el = $(o.el);
            }
            _.mixin(EventBus.prototype, {
                _trigger: function(type, args) {
                    var $e;
                    $e = $.Event(namespace + type);
                    (args = args || []).unshift($e);
                    this.$el.trigger.apply(this.$el, args);
                    return $e;
                },
                before: function(type) {
                    var args, $e;
                    args = [].slice.call(arguments, 1);
                    $e = this._trigger("before" + type, args);
                    return $e.isDefaultPrevented();
                },
                trigger: function(type) {
                    var deprecatedType;
                    this._trigger(type, [].slice.call(arguments, 1));
                    if (deprecatedType = deprecationMap[type]) {
                        this._trigger(deprecatedType, [].slice.call(arguments, 1));
                    }
                }
            });
            return EventBus;
        }();
        var EventEmitter = function() {
            "use strict";
            var splitter = /\s+/,
                nextTick = getNextTick();
            return {
                onSync: onSync,
                onAsync: onAsync,
                off: off,
                trigger: trigger
            };

            function on(method, types, cb, context) {
                var type;
                if (!cb) {
                    return this;
                }
                types = types.split(splitter);
                cb = context ? bindContext(cb, context) : cb;
                this._callbacks = this._callbacks || {};
                while (type = types.shift()) {
                    this._callbacks[type] = this._callbacks[type] || {
                        sync: [],
                        async: []
                    };
                    this._callbacks[type][method].push(cb);
                }
                return this;
            }

            function onAsync(types, cb, context) {
                return on.call(this, "async", types, cb, context);
            }

            function onSync(types, cb, context) {
                return on.call(this, "sync", types, cb, context);
            }

            function off(types) {
                var type;
                if (!this._callbacks) {
                    return this;
                }
                types = types.split(splitter);
                while (type = types.shift()) {
                    delete this._callbacks[type];
                }
                return this;
            }

            function trigger(types) {
                var type, callbacks, args, syncFlush, asyncFlush;
                if (!this._callbacks) {
                    return this;
                }
                types = types.split(splitter);
                args = [].slice.call(arguments, 1);
                while ((type = types.shift()) && (callbacks = this._callbacks[type])) {
                    syncFlush = getFlush(callbacks.sync, this, [type].concat(args));
                    asyncFlush = getFlush(callbacks.async, this, [type].concat(args));
                    syncFlush() && nextTick(asyncFlush);
                }
                return this;
            }

            function getFlush(callbacks, context, args) {
                return flush;

                function flush() {
                    var cancelled;
                    for (var i = 0, len = callbacks.length; !cancelled && i < len; i += 1) {
                        cancelled = callbacks[i].apply(context, args) === false;
                    }
                    return !cancelled;
                }
            }

            function getNextTick() {
                var nextTickFn;
                if (window.setImmediate) {
                    nextTickFn = function nextTickSetImmediate(fn) {
                        setImmediate(function() {
                            fn();
                        });
                    };
                } else {
                    nextTickFn = function nextTickSetTimeout(fn) {
                        setTimeout(function() {
                            fn();
                        }, 0);
                    };
                }
                return nextTickFn;
            }

            function bindContext(fn, context) {
                return fn.bind ? fn.bind(context) : function() {
                    fn.apply(context, [].slice.call(arguments, 0));
                };
            }
        }();
        var highlight = function(doc) {
            "use strict";
            var defaults = {
                node: null,
                pattern: null,
                tagName: "strong",
                className: null,
                wordsOnly: false,
                caseSensitive: false
            };
            return function hightlight(o) {
                var regex;
                o = _.mixin({}, defaults, o);
                if (!o.node || !o.pattern) {
                    return;
                }
                o.pattern = _.isArray(o.pattern) ? o.pattern : [o.pattern];
                regex = getRegex(o.pattern, o.caseSensitive, o.wordsOnly);
                traverse(o.node, hightlightTextNode);

                function hightlightTextNode(textNode) {
                    var match, patternNode, wrapperNode;
                    if (match = regex.exec(textNode.data)) {
                        wrapperNode = doc.createElement(o.tagName);
                        o.className && (wrapperNode.className = o.className);
                        patternNode = textNode.splitText(match.index);
                        patternNode.splitText(match[0].length);
                       wrapperNode.appendChild(patternNode.cloneNode(true));
                        textNode.parentNode.replaceChild(wrapperNode, patternNode);
                    }
                    return !!match;
                }

                function traverse(el, hightlightTextNode) {
                    var childNode, TEXT_NODE_TYPE = 3;
                    for (var i = 0; i < el.childNodes.length; i++) {
                        childNode = el.childNodes[i];
                        if (childNode.nodeType === TEXT_NODE_TYPE) {
                            i += hightlightTextNode(childNode) ? 1 : 0;
                        } else {
                            traverse(childNode, hightlightTextNode);
                        }
                    }
                }
            };

            function getRegex(patterns, caseSensitive, wordsOnly) {
                var escapedPatterns = [],
                    regexStr;
                for (var i = 0, len = patterns.length; i < len; i++) {
                    escapedPatterns.push(_.escapeRegExChars(patterns[i]));
                }
                regexStr = wordsOnly ? "\\b(" + escapedPatterns.join("|") + ")\\b" : "(" + escapedPatterns.join("|") + ")";
                return caseSensitive ? new RegExp(regexStr) : new RegExp(regexStr, "i");
            }
        }(window.document);
        var Input = function() {
            "use strict";
            var specialKeyCodeMap;
            specialKeyCodeMap = {
                9: "tab",
                27: "esc",
                37: "left",
                39: "right",
                13: "enter",
                38: "up",
                40: "down"
            };

            function Input(o, www) {
                o = o || {};
                if (!o.input) {
                    $.error("input is missing");
                }
                www.mixin(this);
                this.$hint = $(o.hint);
                this.$input = $(o.input);
                this.query = this.$input.val();
                this.queryWhenFocused = this.hasFocus() ? this.query : null;
                this.$overflowHelper = buildOverflowHelper(this.$input);
                this._checkLanguageDirection();
                if (this.$hint.length === 0) {
                    this.setHint = this.getHint = this.clearHint = this.clearHintIfInvalid = _.noop;
                }
            }
            Input.normalizeQuery = function(str) {
                return _.toStr(str).replace(/^\s*/g, "").replace(/\s{2,}/g, " ");
            };
            _.mixin(Input.prototype, EventEmitter, {
                _onBlur: function onBlur() {
                    this.resetInputValue();
                    this.trigger("blurred");
                },
                _onFocus: function onFocus() {
                    this.queryWhenFocused = this.query;
                    this.trigger("focused");
                },
                _onKeydown: function onKeydown($e) {
                    var keyName = specialKeyCodeMap[$e.which || $e.keyCode];
                    this._managePreventDefault(keyName, $e);
                    if (keyName && this._shouldTrigger(keyName, $e)) {
                        this.trigger(keyName + "Keyed", $e);
                    }
                },
                _onInput: function onInput() {
                    this._setQuery(this.getInputValue());
                    this.clearHintIfInvalid();
                    this._checkLanguageDirection();
                },
                _managePreventDefault: function managePreventDefault(keyName, $e) {
                    var preventDefault;
                    switch (keyName) {
                        case "up":
                        case "down":
                            preventDefault = !withModifier($e);
                            break;

                        default:
                            preventDefault = false;
                    }
                    preventDefault && $e.preventDefault();
                },
                _shouldTrigger: function shouldTrigger(keyName, $e) {
                    var trigger;
                   switch (keyName) {
                        case "tab":
                            trigger = !withModifier($e);
                            break;

                        default:
                            trigger = true;
                    }
                    return trigger;
                },
                _checkLanguageDirection: function checkLanguageDirection() {
                    var dir = (this.$input.css("direction") || "ltr").toLowerCase();
                    if (this.dir !== dir) {
                        this.dir = dir;
                        this.$hint.attr("dir", dir);
                        this.trigger("langDirChanged", dir);
                    }
                },
                _setQuery: function setQuery(val, silent) {
                    var areEquivalent, hasDifferentWhitespace;
                    areEquivalent = areQueriesEquivalent(val, this.query);
                    hasDifferentWhitespace = areEquivalent ? this.query.length !== val.length : false;
                    this.query = val;
                    if (!silent && !areEquivalent) {
                        this.trigger("queryChanged", this.query);
                    } else if (!silent && hasDifferentWhitespace) {
                        this.trigger("whitespaceChanged", this.query);
                    }
                },
                bind: function() {
                    var that = this,
                        onBlur, onFocus, onKeydown, onInput;
                    onBlur = _.bind(this._onBlur, this);
                    onFocus = _.bind(this._onFocus, this);
                    onKeydown = _.bind(this._onKeydown, this);
                    onInput = _.bind(this._onInput, this);
                    this.$input.on("blur.tt", onBlur).on("focus.tt", onFocus).on("keydown.tt", onKeydown);
                    if (!_.isMsie() || _.isMsie() > 9) {
                        this.$input.on("input.tt", onInput);
                    } else {
                        this.$input.on("keydown.tt keypress.tt cut.tt paste.tt", function($e) {
                            if (specialKeyCodeMap[$e.which || $e.keyCode]) {
                                return;
                            }
                            _.defer(_.bind(that._onInput, that, $e));
                        });
                    }
                    return this;
                },
                focus: function focus() {
                    this.$input.focus();
                },
                blur: function blur() {
                    this.$input.blur();
                },
                getLangDir: function getLangDir() {
                    return this.dir;
                },
                getQuery: function getQuery() {
                   return this.query || "";
                },
                setQuery: function setQuery(val, silent) {
                    this.setInputValue(val);
                    this._setQuery(val, silent);
                },
                hasQueryChangedSinceLastFocus: function hasQueryChangedSinceLastFocus() {
                    return this.query !== this.queryWhenFocused;
                },
                getInputValue: function getInputValue() {
                    return this.$input.val();
                },
                setInputValue: function setInputValue(value) {
                    this.$input.val(value);
                    this.clearHintIfInvalid();
                    this._checkLanguageDirection();
                },
                resetInputValue: function resetInputValue() {
                    this.setInputValue(this.query);
                },
                getHint: function getHint() {
                    return this.$hint.val();
                },
                setHint: function setHint(value) {
                    this.$hint.val(value);
                },
                clearHint: function clearHint() {
                    this.setHint("");
                },
                clearHintIfInvalid: function clearHintIfInvalid() {
                    var val, hint, valIsPrefixOfHint, isValid;
                    val = this.getInputValue();
                    hint = this.getHint();
                    valIsPrefixOfHint = val !== hint && hint.indexOf(val) === 0;
                    isValid = val !== "" && valIsPrefixOfHint && !this.hasOverflow();
                    !isValid && this.clearHint();
                },
                hasFocus: function hasFocus() {
                    return this.$input.is(":focus");
                },
                hasOverflow: function hasOverflow() {
                    var constraint = this.$input.width() - 2;
                    this.$overflowHelper.text(this.getInputValue());
                    return this.$overflowHelper.width() >= constraint;
                },
                isCursorAtEnd: function() {
                    var valueLength, selectionStart, range;
                    valueLength = this.$input.val().length;
                    selectionStart = this.$input[0].selectionStart;
                    if (_.isNumber(selectionStart)) {
                        return selectionStart === valueLength;
                    } else if (document.selection) {
                        range = document.selection.createRange();
                        range.moveStart("character", -valueLength);
                        return valueLength === range.text.length;
                    }
                    return true;
                },
                destroy: function destroy() {
                    this.$hint.off(".tt");
                    this.$input.off(".tt");
                    this.$overflowHelper.remove();
                    this.$hint = this.$input = this.$overflowHelper = $("<div>");
                }
            });
            return Input;

            function buildOverflowHelper($input) {
                return $('<pre aria-hidden="true"></pre>').css({
                    position: "absolute",
                    visibility: "hidden",
                    whiteSpace: "pre",
                    fontFamily: $input.css("font-family"),
                    fontSize: $input.css("font-size"),
                    fontStyle: $input.css("font-style"),
                    fontVariant: $input.css("font-variant"),
                    fontWeight: $input.css("font-weight"),
                    wordSpacing: $input.css("word-spacing"),
                    letterSpacing: $input.css("letter-spacing"),
                    textIndent: $input.css("text-indent"),
                    textRendering: $input.css("text-rendering"),
                    textTransform: $input.css("text-transform")
                }).insertAfter($input);
            }

            function areQueriesEquivalent(a, b) {
                return Input.normalizeQuery(a) === Input.normalizeQuery(b);
            }

            function withModifier($e) {
                return $e.altKey || $e.ctrlKey || $e.metaKey || $e.shiftKey;
            }
        }();
        var Dataset = function() {
            "use strict";
            var keys, nameGenerator;
            keys = {
                val: "tt-selectable-display",
                obj: "tt-selectable-object"
            };
            nameGenerator = _.getIdGenerator();

            function Dataset(o, www) {
                o = o || {};
                o.templates = o.templates || {};
                o.templates.notFound = o.templates.notFound || o.templates.empty;
                if (!o.source) {
                    $.error("missing source");
                }
                if (!o.node) {
                    $.error("missing node");
                }
                if (o.name && !isValidName(o.name)) {
                    $.error("invalid dataset name: " + o.name);
                }
                www.mixin(this);
                this.highlight = !!o.highlight;
                this.name = o.name || nameGenerator();
                this.limit = o.limit || 5;
                this.displayFn = getDisplayFn(o.display || o.displayKey);
                this.templates = getTemplates(o.templates, this.displayFn);
                this.source = o.source.__ttAdapter ? o.source.__ttAdapter() : o.source;
                this.async = _.isUndefined(o.async) ? this.source.length > 2 : !!o.async;
                this._resetLastSuggestion();
                this.$el = $(o.node).addClass(this.classes.dataset).addClass(this.classes.dataset + "-" + this.name);
            }
            Dataset.extractData = function extractData(el) {
                var $el = $(el);
                if ($el.data(keys.obj)) {
                    return {
                        val: $el.data(keys.val) || "",
                        obj: $el.data(keys.obj) || null
                    };
                }
                return null;
            };
            _.mixin(Dataset.prototype, EventEmitter, {
                _overwrite: function overwrite(query, suggestions) {
                    suggestions = suggestions || [];
                    if (suggestions.length) {
                        this._renderSuggestions(query, suggestions);
                    } else if (this.async && this.templates.pending) {
                        this._renderPending(query);
                    } else if (!this.async && this.templates.notFound) {
                        this._renderNotFound(query);
                    } else {
                        this._empty();
                    }
                    this.trigger("rendered", this.name, suggestions, false);
                },
                _append: function append(query, suggestions) {
                    suggestions = suggestions || [];
                    if (suggestions.length && this.$lastSuggestion.length) {
                        this._appendSuggestions(query, suggestions);
                    } else if (suggestions.length) {
                        this._renderSuggestions(query, suggestions);
                    } else if (!this.$lastSuggestion.length && this.templates.notFound) {
                        this._renderNotFound(query);
                    }
                    this.trigger("rendered", this.name, suggestions, true);
                },
                _renderSuggestions: function renderSuggestions(query, suggestions) {
                   var $fragment;
                    $fragment = this._getSuggestionsFragment(query, suggestions);
                    this.$lastSuggestion = $fragment.children().last();
                    this.$el.html($fragment).prepend(this._getHeader(query, suggestions)).append(this._getFooter(query, suggestions));
                },
                _appendSuggestions: function appendSuggestions(query, suggestions) {
                    var $fragment, $lastSuggestion;
                    $fragment = this._getSuggestionsFragment(query, suggestions);
                    $lastSuggestion = $fragment.children().last();
                    this.$lastSuggestion.after($fragment);
                    this.$lastSuggestion = $lastSuggestion;
                },
                _renderPending: function renderPending(query) {
                    var template = this.templates.pending;
                    this._resetLastSuggestion();
                   template && this.$el.html(template({
                        query: query,
                        dataset: this.name
                    }));
                },
                _renderNotFound: function renderNotFound(query) {
                    var template = this.templates.notFound;
                    this._resetLastSuggestion();
                    template && this.$el.html(template({
                        query: query,
                        dataset: this.name
                    }));
                },
                _empty: function empty() {
                    this.$el.empty();
                    this._resetLastSuggestion();
                },
                _getSuggestionsFragment: function getSuggestionsFragment(query, suggestions) {
                    var that = this,
                        fragment;
                    fragment = document.createDocumentFragment();
                    _.each(suggestions, function getSuggestionNode(suggestion) {
                        var $el, context;
                        context = that._injectQuery(query, suggestion);
                        $el = $(that.templates.suggestion(context)).data(keys.obj, suggestion).data(keys.val, that.displayFn(suggestion)).addClass(that.classes.suggestion + " " + that.classes.selectable);
                        fragment.appendChild($el[0]);
                    });
                    this.highlight && highlight({
                        className: this.classes.highlight,
                        node: fragment,
                        pattern: query
                    });
                    return $(fragment);
                },
                _getFooter: function getFooter(query, suggestions) {
                    return this.templates.footer ? this.templates.footer({
                        query: query,
                        suggestions: suggestions,
                        dataset: this.name
                    }) : null;
                },
                _getHeader: function getHeader(query, suggestions) {
                    return this.templates.header ? this.templates.header({
                        query: query,
                        suggestions: suggestions,
                        dataset: this.name
                    }) : null;
                },
                _resetLastSuggestion: function resetLastSuggestion() {
                    this.$lastSuggestion = $();
                },
                _injectQuery: function injectQuery(query, obj) {
                    return _.isObject(obj) ? _.mixin({
                        _query: query
                    }, obj) : obj;
                },
                update: function update(query) {
                    var that = this,
                        canceled = false,
                        syncCalled = false,
                        rendered = 0;
                    this.cancel();
                    this.cancel = function cancel() {
                        canceled = true;
                        that.cancel = $.noop;
                        that.async && that.trigger("asyncCanceled", query);
                    };
                    this.source(query, sync, async);
                    !syncCalled && sync([]);

                    function sync(suggestions) {
                        if (syncCalled) {
                            return;
                        }
                        syncCalled = true;
                        suggestions = (suggestions || []).slice(0, that.limit);
                        rendered = suggestions.length;
                        that._overwrite(query, suggestions);
                        if (rendered < that.limit && that.async) {
                            that.trigger("asyncRequested", query);
                        }
                    }

                    function async(suggestions) {
                        suggestions = suggestions || [];
                        if (!canceled && rendered < that.limit) {
                            that.cancel = $.noop;
                            rendered += suggestions.length;
                            that._append(query, suggestions.slice(0, that.limit - rendered));
                            that.async && that.trigger("asyncReceived", query);
                        }
                    }
                },
                cancel: $.noop,
                clear: function clear() {
                    this._empty();
                    this.cancel();
                    this.trigger("cleared");
                },
                isEmpty: function isEmpty() {
                    return this.$el.is(":empty");
                },
                destroy: function destroy() {
                    this.$el = $("<div>");
                }
            });
            return Dataset;

            function getDisplayFn(display) {
                display = display || _.stringify;
                return _.isFunction(display) ? display : displayFn;

                function displayFn(obj) {
                    return obj[display];
                }
            }

            function getTemplates(templates, displayFn) {
                return {
                    notFound: templates.notFound && _.templatify(templates.notFound),
                    pending: templates.pending && _.templatify(templates.pending),
                    header: templates.header && _.templatify(templates.header),
                    footer: templates.footer && _.templatify(templates.footer),
                    suggestion: templates.suggestion || suggestionTemplate
                };

                function suggestionTemplate(context) {
                    return $("<div>").text(displayFn(context));
                }
            }

            function isValidName(str) {
                return /^[_a-zA-Z0-9-]+$/.test(str);
            }
        }();
        var Menu = function() {
            "use strict";

            function Menu(o, www) {
                var that = this;
                o = o || {};
                if (!o.node) {
                    $.error("node is required");
                }
                www.mixin(this);
                this.$node = $(o.node);
                this.query = null;
                this.datasets = _.map(o.datasets, initializeDataset);

                function initializeDataset(oDataset) {
                    var node = that.$node.find(oDataset.node).first();
                    oDataset.node = node.length ? node : $("<div>").appendTo(that.$node);
                    return new Dataset(oDataset, www);
                }
            }
            _.mixin(Menu.prototype, EventEmitter, {
                _onSelectableClick: function onSelectableClick($e) {
                    this.trigger("selectableClicked", $($e.currentTarget));
                },
                _onRendered: function onRendered(type, dataset, suggestions, async) {
                    this.$node.toggleClass(this.classes.empty, this._allDatasetsEmpty());
                    this.trigger("datasetRendered", dataset, suggestions, async);
                },
                _onCleared: function onCleared() {
                    this.$node.toggleClass(this.classes.empty, this._allDatasetsEmpty());
                    this.trigger("datasetCleared");
                },
                _propagate: function propagate() {
                    this.trigger.apply(this, arguments);
                },
                _allDatasetsEmpty: function allDatasetsEmpty() {
                    return _.every(this.datasets, isDatasetEmpty);

                    function isDatasetEmpty(dataset) {
                        return dataset.isEmpty();
                    }
                },
                _getSelectables: function getSelectables() {
                    return this.$node.find(this.selectors.selectable);
                },
                _removeCursor: function _removeCursor() {
                    var $selectable = this.getActiveSelectable();
                    $selectable && $selectable.removeClass(this.classes.cursor);
                },
                _ensureVisible: function ensureVisible($el) {
                    var elTop, elBottom, nodeScrollTop, nodeHeight;
                    elTop = $el.position().top;
                    elBottom = elTop + $el.outerHeight(true);
                    nodeScrollTop = this.$node.scrollTop();
                    nodeHeight = this.$node.height() + parseInt(this.$node.css("paddingTop"), 10) + parseInt(this.$node.css("paddingBottom"), 10);
                    if (elTop < 0) {
                        this.$node.scrollTop(nodeScrollTop + elTop);
                    } else if (nodeHeight < elBottom) {
                        this.$node.scrollTop(nodeScrollTop + (elBottom - nodeHeight));
                    }
                },
                bind: function() {
                    var that = this,
                        onSelectableClick;
                    onSelectableClick = _.bind(this._onSelectableClick, this);
                    this.$node.on("click.tt", this.selectors.selectable, onSelectableClick);
                    _.each(this.datasets, function(dataset) {
                        dataset.onSync("asyncRequested", that._propagate, that).onSync("asyncCanceled", that._propagate, that).onSync("asyncReceived", that._propagate, that).onSync("rendered", that._onRendered, that).onSync("cleared", that._onCleared, that);
                    });
                    return this;
                },
                isOpen: function isOpen() {
                    return this.$node.hasClass(this.classes.open);
                },
                open: function open() {
                    this.$node.addClass(this.classes.open);
                },
                close: function close() {
                    this.$node.removeClass(this.classes.open);
                    this._removeCursor();
                },
                setLanguageDirection: function setLanguageDirection(dir) {
                    this.$node.attr("dir", dir);
                },
                selectableRelativeToCursor: function selectableRelativeToCursor(delta) {
                    var $selectables, $oldCursor, oldIndex, newIndex;
                    $oldCursor = this.getActiveSelectable();
                    $selectables = this._getSelectables();
                    oldIndex = $oldCursor ? $selectables.index($oldCursor) : -1;
                    newIndex = oldIndex + delta;
                    newIndex = (newIndex + 1) % ($selectables.length + 1) - 1;
                    newIndex = newIndex < -1 ? $selectables.length - 1 : newIndex;
                    return newIndex === -1 ? null : $selectables.eq(newIndex);
                },
                setCursor: function setCursor($selectable) {
                    this._removeCursor();
                    if ($selectable = $selectable && $selectable.first()) {
                        $selectable.addClass(this.classes.cursor);
                        this._ensureVisible($selectable);
                    }
                },
                getSelectableData: function getSelectableData($el) {
                    return $el && $el.length ? Dataset.extractData($el) : null;
                },
                getActiveSelectable: function getActiveSelectable() {
                    var $selectable = this._getSelectables().filter(this.selectors.cursor).first();
                    return $selectable.length ? $selectable : null;
                },
                getTopSelectable: function getTopSelectable() {
                    var $selectable = this._getSelectables().first();
                    return $selectable.length ? $selectable : null;
                },
                update: function update(query) {
                    var isValidUpdate = query !== this.query;
                    if (isValidUpdate) {
                        this.query = query;
                        _.each(this.datasets, updateDataset);
                    }
                    return isValidUpdate;

                    function updateDataset(dataset) {
                        dataset.update(query);
                    }
                },
                empty: function empty() {
                    _.each(this.datasets, clearDataset);
                    this.query = null;
                    this.$node.addClass(this.classes.empty);

                    function clearDataset(dataset) {
                        dataset.clear();
                    }
                },
                destroy: function destroy() {
                    this.$node.off(".tt");
                    this.$node = $("<div>");
                    _.each(this.datasets, destroyDataset);

                    function destroyDataset(dataset) {
                        dataset.destroy();
                    }
                }
            });
            return Menu;
        }();
        var DefaultMenu = function() {
            "use strict";
            var s = Menu.prototype;

            function DefaultMenu() {
                Menu.apply(this, [].slice.call(arguments, 0));
            }
            _.mixin(DefaultMenu.prototype, Menu.prototype, {
                open: function open() {
                    !this._allDatasetsEmpty() && this._show();
                    return s.open.apply(this, [].slice.call(arguments, 0));
                },
                close: function close() {
                    this._hide();
                    return s.close.apply(this, [].slice.call(arguments, 0));
                },
                _onRendered: function onRendered() {
                    if (this._allDatasetsEmpty()) {
                        this._hide();
                    } else {
                        this.isOpen() && this._show();
                    }
                    return s._onRendered.apply(this, [].slice.call(arguments, 0));
                },
                _onCleared: function onCleared() {
                    if (this._allDatasetsEmpty()) {
                        this._hide();
                    } else {
                        this.isOpen() && this._show();
                    }
                    return s._onCleared.apply(this, [].slice.call(arguments, 0));
                },
                setLanguageDirection: function setLanguageDirection(dir) {
                    this.$node.css(dir === "ltr" ? this.css.ltr : this.css.rtl);
                    return s.setLanguageDirection.apply(this, [].slice.call(arguments, 0));
                },
                _hide: function hide() {
                    this.$node.hide();
                },
                _show: function show() {
                    this.$node.css("display", "block");
                }
            });
            return DefaultMenu;
        }();
        var Typeahead = function() {
            "use strict";

            function Typeahead(o, www) {
                var onFocused, onBlurred, onEnterKeyed, onTabKeyed, onEscKeyed, onUpKeyed, onDownKeyed, onLeftKeyed, onRightKeyed, onQueryChanged, onWhitespaceChanged;
                o = o || {};
                if (!o.input) {
                    $.error("missing input");
                }
                if (!o.menu) {
                    $.error("missing menu");
                }
                if (!o.eventBus) {
                    $.error("missing event bus");
                }
                www.mixin(this);
                this.eventBus = o.eventBus;
                this.minLength = _.isNumber(o.minLength) ? o.minLength : 1;
                this.input = o.input;
                this.menu = o.menu;
                this.enabled = true;
                this.active = false;
                this.input.hasFocus() && this.activate();
                this.dir = this.input.getLangDir();
                this._hacks();
                this.menu.bind().onSync("selectableClicked", this._onSelectableClicked, this).onSync("asyncRequested", this._onAsyncRequested, this).onSync("asyncCanceled", this._onAsyncCanceled, this).onSync("asyncReceived", this._onAsyncReceived, this).onSync("datasetRendered", this._onDatasetRendered, this).onSync("datasetCleared", this._onDatasetCleared, this);
                onFocused = c(this, "activate", "open", "_onFocused");
                onBlurred = c(this, "deactivate", "_onBlurred");
                onEnterKeyed = c(this, "isActive", "isOpen", "_onEnterKeyed");
                onTabKeyed = c(this, "isActive", "isOpen", "_onTabKeyed");
                onEscKeyed = c(this, "isActive", "_onEscKeyed");
                onUpKeyed = c(this, "isActive", "open", "_onUpKeyed");
                onDownKeyed = c(this, "isActive", "open", "_onDownKeyed");
                onLeftKeyed = c(this, "isActive", "isOpen", "_onLeftKeyed");
                onRightKeyed = c(this, "isActive", "isOpen", "_onRightKeyed");
                onQueryChanged = c(this, "_openIfActive", "_onQueryChanged");
                onWhitespaceChanged = c(this, "_openIfActive", "_onWhitespaceChanged");
                this.input.bind().onSync("focused", onFocused, this).onSync("blurred", onBlurred, this).onSync("enterKeyed", onEnterKeyed, this).onSync("tabKeyed", onTabKeyed, this).onSync("escKeyed", onEscKeyed, this).onSync("upKeyed", onUpKeyed, this).onSync("downKeyed", onDownKeyed, this).onSync("leftKeyed", onLeftKeyed, this).onSync("rightKeyed", onRightKeyed, this).onSync("queryChanged", onQueryChanged, this).onSync("whitespaceChanged", onWhitespaceChanged, this).onSync("langDirChanged", this._onLangDirChanged, this);
            }
            _.mixin(Typeahead.prototype, {
                _hacks: function hacks() {
                    var $input, $menu;
                    $input = this.input.$input || $("<div>");
                    $menu = this.menu.$node || $("<div>");
                    $input.on("blur.tt", function($e) {
                        var active, isActive, hasActive;
                        active = document.activeElement;
                        isActive = $menu.is(active);
                        hasActive = $menu.has(active).length > 0;
                        if (_.isMsie() && (isActive || hasActive)) {
                            $e.preventDefault();
                            $e.stopImmediatePropagation();
                            _.defer(function() {
                                $input.focus();
                            });
                        }
                    });
                    $menu.on("mousedown.tt", function($e) {
                        $e.preventDefault();
                    });
                },
                _onSelectableClicked: function onSelectableClicked(type, $el) {
                    this.select($el);
                },
                _onDatasetCleared: function onDatasetCleared() {
                    this._updateHint();
                },
                _onDatasetRendered: function onDatasetRendered(type, dataset, suggestions, async) {
                    this._updateHint();
                    this.eventBus.trigger("render", suggestions, async, dataset);
                },
                _onAsyncRequested: function onAsyncRequested(type, dataset, query) {
                    this.eventBus.trigger("asyncrequest", query, dataset);
                },
                _onAsyncCanceled: function onAsyncCanceled(type, dataset, query) {
                    this.eventBus.trigger("asynccancel", query, dataset);
                },
                _onAsyncReceived: function onAsyncReceived(type, dataset, query) {
                    this.eventBus.trigger("asyncreceive", query, dataset);
                },
                _onFocused: function onFocused() {
                    this._minLengthMet() && this.menu.update(this.input.getQuery());
                },
                _onBlurred: function onBlurred() {
                    if (this.input.hasQueryChangedSinceLastFocus()) {
                        this.eventBus.trigger("change", this.input.getQuery());
                    }
                },
                _onEnterKeyed: function onEnterKeyed(type, $e) {
                    var $selectable;
                    if ($selectable = this.menu.getActiveSelectable()) {
                        this.select($selectable) && $e.preventDefault();
                    }
                },
                _onTabKeyed: function onTabKeyed(type, $e) {
                    var $selectable;
                    if ($selectable = this.menu.getActiveSelectable()) {
                        this.select($selectable) && $e.preventDefault();
                    } else if ($selectable = this.menu.getTopSelectable()) {
                        this.autocomplete($selectable) && $e.preventDefault();
                    }
                },
                _onEscKeyed: function onEscKeyed() {
                    this.close();
                },
                _onUpKeyed: function onUpKeyed() {
                    this.moveCursor(-1);
                },
                _onDownKeyed: function onDownKeyed() {
                    this.moveCursor(+1);
                },
                _onLeftKeyed: function onLeftKeyed() {
                    if (this.dir === "rtl" && this.input.isCursorAtEnd()) {
                        this.autocomplete(this.menu.getTopSelectable());
                    }
                },
                _onRightKeyed: function onRightKeyed() {
                    if (this.dir === "ltr" && this.input.isCursorAtEnd()) {
                        this.autocomplete(this.menu.getTopSelectable());
                    }
                },
                _onQueryChanged: function onQueryChanged(e, query) {
                    this._minLengthMet(query) ? this.menu.update(query) : this.menu.empty();
                },
                _onWhitespaceChanged: function onWhitespaceChanged() {
                    this._updateHint();
                },
                _onLangDirChanged: function onLangDirChanged(e, dir) {
                    if (this.dir !== dir) {
                        this.dir = dir;
                        this.menu.setLanguageDirection(dir);
                    }
                },
                _openIfActive: function openIfActive() {
                    this.isActive() && this.open();
                },
                _minLengthMet: function minLengthMet(query) {
                    query = _.isString(query) ? query : this.input.getQuery() || "";
                    return query.length >= this.minLength;
                },
                _updateHint: function updateHint() {
                    var $selectable, data, val, query, escapedQuery, frontMatchRegEx, match;
                    $selectable = this.menu.getTopSelectable();
                    data = this.menu.getSelectableData($selectable);
                    val = this.input.getInputValue();
                    if (data && !_.isBlankString(val) && !this.input.hasOverflow()) {
                        query = Input.normalizeQuery(val);
                        escapedQuery = _.escapeRegExChars(query);
                        frontMatchRegEx = new RegExp("^(?:" + escapedQuery + ")(.+$)", "i");
                        match = frontMatchRegEx.exec(data.val);
                        match && this.input.setHint(val + match[1]);
                    } else {
                        this.input.clearHint();
                    }
                },
                isEnabled: function isEnabled() {
                    return this.enabled;
                },
                enable: function enable() {
                    this.enabled = true;
                },
                disable: function disable() {
                    this.enabled = false;
                },
                isActive: function isActive() {
                    return this.active;
                },
                activate: function activate() {
                    if (this.isActive()) {
                        return true;
                    } else if (!this.isEnabled() || this.eventBus.before("active")) {
                        return false;
                    } else {
                        this.active = true;
                        this.eventBus.trigger("active");
                        return true;
                    }
                },
                deactivate: function deactivate() {
                    if (!this.isActive()) {
                        return true;
                    } else if (this.eventBus.before("idle")) {
                        return false;
                    } else {
                        this.active = false;
                        this.close();
                        this.eventBus.trigger("idle");
                        return true;
                    }
                },
                isOpen: function isOpen() {
                    return this.menu.isOpen();
                },
                open: function open() {
                    if (!this.isOpen() && !this.eventBus.before("open")) {
                        this.menu.open();
                        this._updateHint();
                        this.eventBus.trigger("open");
                    }
                    return this.isOpen();
                },
                close: function close() {
                    if (this.isOpen() && !this.eventBus.before("close")) {
                        this.menu.close();
                        this.input.clearHint();
                        this.input.resetInputValue();
                        this.eventBus.trigger("close");
                    }
                    return !this.isOpen();
                },
                setVal: function setVal(val) {
                    this.input.setQuery(_.toStr(val));
                },
                getVal: function getVal() {
                    return this.input.getQuery();
                },
                select: function select($selectable) {
                    var data = this.menu.getSelectableData($selectable);
                    if (data && !this.eventBus.before("select", data.obj)) {
                        this.input.setQuery(data.val, true);
                        this.eventBus.trigger("select", data.obj);
                        this.close();
                        return true;
                    }
                    return false;
                },
                autocomplete: function autocomplete($selectable) {
                    var query, data, isValid;
                    query = this.input.getQuery();
                    data = this.menu.getSelectableData($selectable);
                    isValid = data && query !== data.val;
                    if (isValid && !this.eventBus.before("autocomplete", data.obj)) {
                        this.input.setQuery(data.val);
                        this.eventBus.trigger("autocomplete", data.obj);
                        return true;
                    }
                    return false;
                },
                moveCursor: function moveCursor(delta) {
                    var query, $candidate, data, payload, cancelMove;
                    query = this.input.getQuery();
                    $candidate = this.menu.selectableRelativeToCursor(delta);
                    data = this.menu.getSelectableData($candidate);
                    payload = data ? data.obj : null;
                    cancelMove = this._minLengthMet() && this.menu.update(query);
                    if (!cancelMove && !this.eventBus.before("cursorchange", payload)) {
                        this.menu.setCursor($candidate);
                        if (data) {
                            this.input.setInputValue(data.val);
                        } else {
                            this.input.resetInputValue();
                            this._updateHint();
                        }
                        this.eventBus.trigger("cursorchange", payload);
                        return true;
                    }
                    return false;
                },
                destroy: function destroy() {
                    this.input.destroy();
                    this.menu.destroy();
                }
            });
            return Typeahead;

            function c(ctx) {
                var methods = [].slice.call(arguments, 1);
                return function() {
                    var args = [].slice.call(arguments);
                    _.each(methods, function(method) {
                        return ctx[method].apply(ctx, args);
                    });
                };
            }
        }();
        (function() {
            "use strict";
            var old, keys, methods;
            old = $.fn.typeahead;
            keys = {
                www: "tt-www",
                attrs: "tt-attrs",
                typeahead: "tt-typeahead"
            };
            methods = {
                initialize: function initialize(o, datasets) {
                    var www;
                    datasets = _.isArray(datasets) ? datasets : [].slice.call(arguments, 1);
                    o = o || {};
                    www = WWW(o.classNames);
                    return this.each(attach);

                    function attach() {
                        var $input, $wrapper, $hint, $menu, defaultHint, defaultMenu, eventBus, input, menu, typeahead, MenuConstructor;
                        _.each(datasets, function(d) {
                            d.highlight = !!o.highlight;
                        });
                        $input = $(this);
                        $wrapper = $(www.html.wrapper);
                        $hint = $elOrNull(o.hint);
                        $menu = $elOrNull(o.menu);
                        defaultHint = o.hint !== false && !$hint;
                        defaultMenu = o.menu !== false && !$menu;
                        defaultHint && ($hint = buildHintFromInput($input, www));
                        defaultMenu && ($menu = $(www.html.menu).css(www.css.menu));
                        $hint && $hint.val("");
                        $input = prepInput($input, www);
                        if (defaultHint || defaultMenu) {
                            $wrapper.css(www.css.wrapper);
                            $input.css(defaultHint ? www.css.input : www.css.inputWithNoHint);
                            $input.wrap($wrapper).parent().prepend(defaultHint ? $hint : null).append(defaultMenu ? $menu : null);
                        }
                        MenuConstructor = defaultMenu ? DefaultMenu : Menu;
                        eventBus = new EventBus({
                            el: $input
                        });
                        input = new Input({
                            hint: $hint,
                            input: $input
                        }, www);
                        menu = new MenuConstructor({
                            node: $menu,
                            datasets: datasets
                        }, www);
                        typeahead = new Typeahead({
                            input: input,
                            menu: menu,
                            eventBus: eventBus,
                            minLength: o.minLength
                        }, www);
                        $input.data(keys.www, www);
                        $input.data(keys.typeahead, typeahead);
                    }
                },
                isEnabled: function isEnabled() {
                    var enabled;
                    ttEach(this.first(), function(t) {
                        enabled = t.isEnabled();
                    });
                    return enabled;
                },
                enable: function enable() {
                    ttEach(this, function(t) {
                        t.enable();
                    });
                    return this;
                },
                disable: function disable() {
                    ttEach(this, function(t) {
                        t.disable();
                    });
                    return this;
                },
                isActive: function isActive() {
                    var active;
                    ttEach(this.first(), function(t) {
                        active = t.isActive();
                    });
                    return active;
                },
                activate: function activate() {
                    ttEach(this, function(t) {
                        t.activate();
                    });
                    return this;
                },
                deactivate: function deactivate() {
                    ttEach(this, function(t) {
                        t.deactivate();
                    });
                    return this;
                },
                isOpen: function isOpen() {
                    var open;
                    ttEach(this.first(), function(t) {
                        open = t.isOpen();
                    });
                    return open;
                },
                open: function open() {
                    ttEach(this, function(t) {
                        t.open();
                    });
                    return this;
                },
                close: function close() {
                    ttEach(this, function(t) {
                        t.close();
                    });
                    return this;
                },
                select: function select(el) {
                    var success = false,
                        $el = $(el);
                    ttEach(this.first(), function(t) {
                        success = t.select($el);
                    });
                    return success;
                },
                autocomplete: function autocomplete(el) {
                    var success = false,
                        $el = $(el);
                    ttEach(this.first(), function(t) {
                        success = t.autocomplete($el);
                    });
                    return success;
                },
                moveCursor: function moveCursoe(delta) {
                    var success = false;
                    ttEach(this.first(), function(t) {
                        success = t.moveCursor(delta);
                    });
                    return success;
                },
                val: function val(newVal) {
                    var query;
                    if (!arguments.length) {
                        ttEach(this.first(), function(t) {
                            query = t.getVal();
                        });
                        return query;
                    } else {
                        ttEach(this, function(t) {
                            t.setVal(newVal);
                        });
                        return this;
                    }
                },
                destroy: function destroy() {
                    ttEach(this, function(typeahead, $input) {
                        revert($input);
                        typeahead.destroy();
                    });
                    return this;
                }
            };
            $.fn.typeahead = function(method) {
                if (methods[method]) {
                    return methods[method].apply(this, [].slice.call(arguments, 1));
                } else {
                    return methods.initialize.apply(this, arguments);
                }
            };
            $.fn.typeahead.noConflict = function noConflict() {
                $.fn.typeahead = old;
                return this;
            };

            function ttEach($els, fn) {
                $els.each(function() {
                    var $input = $(this),
                        typeahead;
                    (typeahead = $input.data(keys.typeahead)) && fn(typeahead, $input);
                });
            }

            function buildHintFromInput($input, www) {
                return $input.clone().addClass(www.classes.hint).removeData().css(www.css.hint).css(getBackgroundStyles($input)).prop("readonly", true).removeAttr("id name placeholder required").attr({
                    autocomplete: "off",
                    spellcheck: "false",
                    tabindex: -1
                });
            }

            function prepInput($input, www) {
                $input.data(keys.attrs, {
                    dir: $input.attr("dir"),
                    autocomplete: $input.attr("autocomplete"),
                    spellcheck: $input.attr("spellcheck"),
                    style: $input.attr("style")
                });
                $input.addClass(www.classes.input).attr({
                    autocomplete: "off",
                    spellcheck: false
                });
                try {
                    !$input.attr("dir") && $input.attr("dir", "auto");
                } catch (e) {}
                return $input;
            }

            function getBackgroundStyles($el) {
                return {
                    backgroundAttachment: $el.css("background-attachment"),
                    backgroundClip: $el.css("background-clip"),
                    backgroundColor: $el.css("background-color"),
                    backgroundImage: $el.css("background-image"),
                    backgroundOrigin: $el.css("background-origin"),
                    backgroundPosition: $el.css("background-position"),
                    backgroundRepeat: $el.css("background-repeat"),
                    backgroundSize: $el.css("background-size")
                };
            }

            function revert($input) {
                var www, $wrapper;
                www = $input.data(keys.www);
                $wrapper = $input.parent().filter(www.selectors.wrapper);
                _.each($input.data(keys.attrs), function(val, key) {
                    _.isUndefined(val) ? $input.removeAttr(key) : $input.attr(key, val);
                });
                $input.removeData(keys.typeahead).removeData(keys.www).removeData(keys.attr).removeClass(www.classes.input);
                if ($wrapper.length) {
                    $input.detach().insertAfter($wrapper);
                    $wrapper.remove();
                }
            }

            function $elOrNull(obj) {
                var isValid, $el;
                isValid = _.isJQuery(obj) || _.isElement(obj);
                $el = isValid ? $(obj).first() : [];
                return $el.length ? $el : null;
            }
        })();
    });
    // Plugin5 Custom JS -Noconflict
    jquery_new_version = $.noConflict(true);
    commenDependency(jquery_new_version);
    if(jquery_new_version('.navigationHeader').length){
        loadSearchFile(jquery_new_version);
    }
    // Plugin6 Custom JS -FOOTER-ACCORDION-SOCIAL-SHARE
    footerInit(jquery_new_version);
    function footerInit($) {
        /*footer-customjs-start */
        !function n(o, i, e) {
            function t(a, d) {
                if (!i[a]) {
                    if (!o[a]) {
                        var c = "function" == typeof require && require;
                        if (!d && c)
                            return c(a, !0);
                        if (r)
                            return r(a, !0);
                        throw new Error("Cannot find module '" + a + "'")
                    }
                    var l = i[a] = {
                        exports: {}
                    };
                    o[a][0].call(l.exports, function(n) {
                        var i = o[a][1][n];
                        return t(i || n)
                    }, l, l.exports, n, o, i, e)
                }
                return i[a].exports
            }
            for (var r = "function" == typeof require && require, a = 0; a < e.length; a++)
                t(e[a]);
            return t
        }({
            1: [function(n, o, i) {
                "use strict";
                var e = window.innerWidth <= 767
                  , t = {
                    el: ".footer-links-accordion",
                    bindingEventsConfig: function() {
                        return {
                            "click .accordion-action:not('.footer-legal')": "expandAccordion"
                        }
                    },
                    expandAccordion: function(n, o) {
                        o.preventDefault(),
                        $(n).siblings(".accordion-container li:first-child").focus(),
                        $(n).siblings(".accordion-container").slideToggle(),
                        $(n).toggleClass("expand-in"),
                        $(n).find("a").attr("aria-expanded", !!$(n).hasClass("expand-in"))
                    },
                    ariaExpandedAdded: function(n, o) {
                        n ? o && !e || _.each(n, function(n) {
                            $(n).attr("aria-expanded", !1)
                        }) : console.log("Aria expanded attribute element undefined..")
                    },
                    init: function() {
                        $(this.el).length && !window.global.globalFooter && (r.bindLooping(this.bindingEventsConfig(), this),
                        this.ariaExpandedAdded($(".footer-links-accordion .accordion-action a"), !0))
                    }
                }
                  , r = window.global.eventBindingInst;
                t.init(),
                window.global.globalFooter = t,
                document.addEventListener("DOMContentLoaded", function() {
                    t.init()
                }, !1),
                $(window).on("load", function() {
                    var n = window.location.hash;
                    n && "#assembly-videos" == n && ($(".accordion-content").show(),
                    $(".arrow").removeClass("arrowDown").addClass("arrowUp"),
                    $("html, body").animate({
                        scrollTop: $("#assembly-videos").offset().top - ($("header").height() || 0)
                    }, "fast"))
                })
            }
            , {}]
        }, {}, [1]);
        
        //----------
        !function e(i, n, a) {
            function o(s, l) {
                if (!n[s]) {
                    if (!i[s]) {
                        var r = "function" == typeof require && require;
                        if (!l && r)
                            return r(s, !0);
                        if (t)
                            return t(s, !0);
                        throw new Error("Cannot find module '" + s + "'")
                    }
                    var c = n[s] = {
                        exports: {}
                    };
                    i[s][0].call(c.exports, function(e) {
                        var n = i[s][1][e];
                        return o(n || e)
                    }, c, c.exports, e, i, n, a)
                }
                return n[s].exports
            }
            for (var t = "function" == typeof require && require, s = 0; s < a.length; s++)
                o(a[s]);
            return o
        }({
            1: [function(e, i, n) {
                "use strict";
                var a = void 0
                  , o = {
                    el: "header",
                    headerScrollStart: 100,
                    bindingEventsConfig: function() {
                        return {
                            "click .hamburger": "showNavigation",
                            "click .primary-nav ul li a": "closeMenu",
                            "click header .overlay": "closeMenu",
                            "click .play-backtotop": "backToTopAction",
                            "click .show-sub-nav": "showSublevel"
                        }
                    },
                    showNavigation: function(e, i) {
                        $(".primary-nav").fadeToggle(250, function() {
                            $(this).is(":visible") ? $(".hamburger").addClass("open") : $(".hamburger").removeClass("open"),
                            $(this).is(":visible") ? $("body").addClass("hamburger-open") : $("body").removeClass("hamburger-open"),
                            $("header").find(".show-sub-nav").removeClass("show-sub-nav-true").text(" ").siblings("ul").hide()
                        }),
                        $(".overlay").removeClass("hide")
                    },
                    closeMenu: function(e, i) {
                        $(".hamburger").hasClass("open") && !$(e).hasClass("show-sub-nav") && ($(".hamburger")[0].click(),
                        $(".overlay").addClass("hide"))
                    },
                    backToTopAction: function(e, i) {
                        $("html,body").animate({
                            scrollTop: 0
                        }, "slow"),
                        $(".navbar-brand").focus()
                    },
                    showSublevel: function(e, i) {
                        $("header").find(".show-sub-nav").not(e).each(function(e, i) {
                            $(i).removeClass("show-sub-nav-true").text(" ").siblings("ul").hide()
                        }),
                        $(e).siblings("ul").slideToggle(100, "linear", function() {
                            $(e).siblings("ul").is(":visible") ? $(e).text(" ").addClass("show-sub-nav-true").attr("aria-expanded", "true") : $(e).text(" ").removeClass("show-sub-nav-true").attr("aria-expanded", "false")
                        })
                    },
                    windowResize: function() {
                        $(window).resize(function() {
                            $(window).width() >= 1025 && $(".primary-nav>ul>li").each(function(e, i) {
                                3 === $(i).children().length && $(i).find("ul").removeAttr("style")
                            })
                        })
                    },
                    scrollHeader: function() {
                        $(window).scroll(function() {
                            var e = $("header");
                            $(window).scrollTop() >= ($(".global-header-wrapper").innerHeight() || 0) ? e.addClass("scroll-sticky") : e.removeClass("scroll-sticky")
                        })
                    },
                    showScrollButton: function(e) {
                        var i = 0
                          , n = $(window).height()
                          , a = $(document).height()
                          , o = n / 2;
                        $(window).scroll(function() {
                            $(this).scrollTop() >= o ? e.fadeIn(this.fadeAnimValue) : e.fadeOut(this.fadeAnimValue);
                            var t = $(this).scrollTop();
                            t > i && ($(this).scrollTop() >= a - n - 10 || $(this).scrollTop() <= a - o) && e.css({
                                WebkitTransition: "opacity 1s ease-in-out",
                                MozTransition: "opacity 1s ease-in-out",
                                MsTransition: "opacity 1s ease-in-out",
                                OTransition: "opacity 1s ease-in-out",
                                transition: "opacity 1s ease-in-out"
                            }),
                            i = t
                        })
                    },
                    backToTopInit: function() {
                        var e = $(".play-backtotop");
                        e.length && this.showScrollButton(e)
                    },
                    pageLoadActions: function() {
                        var e = void 0;
                        $(".primary-nav>ul>li>a").each(function(i, n) {
                            $('[data-link-attribute="' + $(n).data("linkName") + '"]').length && !$(n).hasClass("active-loaded") && ($(n).addClass("active-loaded"),
                            $(n).attr("href", "#" + $(n).data("linkName")).on("click", function() {
                                $(".primary-nav").find("li>a.active").removeClass("active"),
                                $(n).addClass("active"),
                                e = $("header").height(),
                                $("html, body").animate({
                                    scrollTop: $($(this).attr("href")).offset().top - e
                                }, 500)
                            }))
                        })
                    },
                    setHashScroll: function() {
                        var e = window.location.hash;
                        !e || !$(e).length || a.hashCheckCnt > 1 || (a.hashCheckCnt++,
                        $("html, body").animate({
                            scrollTop: $(e).offset().top - ($("header").height() || 0)
                        }, 500))
                    },
                    init: function() {
                        $(this.el).length && !window.global.globalHeader && (a = this,
                        t.bindLooping(this.bindingEventsConfig(), this),
                        this.hashCheckCnt = 0,
                        this.pageLoadActions(),
                        $(".primary-nav>ul>li").each(function(e, i) {
                            2 === $(i).children(":not(input)").length && $(i).prepend('<a href="javascript:void(0)" class="show-sub-nav hidden-lg" aria-label="submenu" role="button" aria-expanded="false">+</a>')
                        }),
                        this.windowResize(),
                        this.scrollHeader())
                    }
                }
                  , t = window.global.eventBindingInst;
                o.init(),
                window.global.globalHeader = o,
                document.addEventListener("DOMContentLoaded", function() {
                    window.global.isDependencyLoaded || o.init(),
                    o.pageLoadActions(),
                    //o.backToTopInit(),
                    o.setHashScroll()
                }, !1)
            }
            , {}]
        }, {}, [1]);
        //------------
        /*----intersitial modal popup start---------*/
        !function t(i, n, e) {
            function o(l, a) {
                if (!n[l]) {
                    if (!i[l]) {
                        var s = "function" == typeof require && require;
                        if (!a && s)
                            return s(l, !0);
                        if (r)
                            return r(l, !0);
                        throw new Error("Cannot find module '" + l + "'")
                    }
                    var u = n[l] = {
                        exports: {}
                    };
                    i[l][0].call(u.exports, function(t) {
                        var n = i[l][1][t];
                        return o(n || t)
                    }, u, u.exports, t, i, n, e)
                }
                return n[l].exports
            }
            for (var r = "function" == typeof require && require, l = 0; l < e.length; l++)
                o(e[l]);
            return o
        }({
            1: [function(t, i, n) {
                "use strict";
                var e = {
                    el: "#interstitialModal,#interstitialRetailerModal,#interstitialGameRetailerModal",
                    bindingEventsConfig: function() {
                        return {
                            "click .continue-btn , .go-back-btn , .interstitial-load": "interstitialModalClose"
                        }
                    },
                    initializeModal: function() {
                        $("#first-link,#interstitial-first-link").focus(function() {
                            $(".modal-body button:last").focus()
                        }),
                        $("#last-link").focus(function() {
                            $(".close").focus()
                        }),
                        $("#interstitialModal").on("shown.bs.modal", function(t) {
                            var i = $(t.relatedTarget).data("url");
                            $(".continue-btn").attr("href", i),
                            setTimeout(function() {
                                $(".close").focus()
                            }, 100)
                        })
                    },
                    interstitialModalClose: function() {
                        $("#interstitialModal, #interstitialRetailerModal , #interstitialGameRetailerModal").modal("hide")
                    },
                    init: function() {
                        $(this.el).length && (o.bindLooping(this.bindingEventsConfig(), this),
                        this.initializeModal())
                    }
                }
                  , o = window.global.eventBindingInst;
                e.init(),
                window.global.interstitialmodal = e,
                document.addEventListener("DOMContentLoaded", function() {
                    e.init()
                }, !1)
            }
            , {}]
        }, {}, [1]);
        
        /*----intersitial modal popup end---------*/
              // Custom JS (common Dependency and corporate-new search)
            
        /*footer-customjs-end */
    }
    // Plugin7 Custom JS -HEADER-SEARCH
    function commenDependency($) {
        function _instanceof(left, right) {
            if (right != null && typeof Symbol !== "undefined" && right[Symbol.hasInstance]) {
                return !!right[Symbol.hasInstance](left);
            } else {
                return left instanceof right;
            }
        }

        function _classCallCheck(instance, Constructor) {
            if (!_instanceof(instance, Constructor)) {
                throw new TypeError("Cannot call a class as a function");
            }
        }

        function _defineProperties(target, props) {
            for (var i = 0; i < props.length; i++) {
                var descriptor = props[i];
                descriptor.enumerable = descriptor.enumerable || false;
                descriptor.configurable = true;
                if ("value" in descriptor) descriptor.writable = true;
                Object.defineProperty(target, descriptor.key, descriptor);
            }
        }

        function _createClass(Constructor, protoProps, staticProps) {
            if (protoProps) _defineProperties(Constructor.prototype, protoProps);
            if (staticProps) _defineProperties(Constructor, staticProps);
            return Constructor;
        }

        var eventBinding = /*#__PURE__*/ function() {
            function eventBinding() {
                _classCallCheck(this, eventBinding);
            }

            _createClass(eventBinding, [{
                key: "bindLooping",
                value: function bindLooping(name, evtObj) {

                    console.log(name, evtObj)
                    var eventSplitter = /(\S+)\s(.*)/,
                        // Regular expression used to split event strings.
                        cb,
                        splitKeys;

                    for (var val in name) {
                        splitKeys = val.match(eventSplitter).slice(1);
                        cb = evtObj && evtObj[name[val]];

                        if (splitKeys && typeof cb == "function") {
                            this.bindingEvent(splitKeys[0], splitKeys[1], cb);
                        } else {
                            console.log("Event Binding failed for " + splitKeys);
                        }
                    }
                }
            }, {
                key: "bindingEvent",
                value: function bindingEvent() {
                    var evtName = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : click;
                    var elem = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : null;
                    var cb = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : false;

                    if (typeof cb === 'function') {
                        $(document).on(evtName, elem, function(evt) {
                            cb(this, evt);
                        });
                    } else {
                        console.log("Error:CB function not found for this element :" + el);
                    }
                }
            }]);

            return eventBinding;
        }();

        window.global = window.global || {};
        window.global.eventBindingInst = new eventBinding();
    }
    function loadSearchFile($) {
        !function e(t, n, i) {
            function r(c, o) {
                if (!n[c]) {
                    if (!t[c]) {
                        var s = "function" == typeof require && require;
                        if (!o && s)
                            return s(c, !0);
                        if (a)
                            return a(c, !0);
                        throw new Error("Cannot find module '" + c + "'")
                    }
                    var u = n[c] = {
                        exports: {}
                    };
                    t[c][0].call(u.exports, function(e) {
                        var n = t[c][1][e];
                        return r(n || e)
                    }, u, u.exports, e, t, n, i)
                }
                return n[c].exports
            }
            for (var a = "function" == typeof require && require, c = 0; c < i.length; c++)
                r(i[c]);
            return r
        }({
            1: [function(e, t, n) {
                "use strict";
                var i = function() {
                    function e(e, t) {
                        for (var n = 0; n < t.length; n++) {
                            var i = t[n];
                            i.enumerable = i.enumerable || !1,
                            i.configurable = !0,
                            "value"in i && (i.writable = !0),
                            Object.defineProperty(e, i.key, i)
                        }
                    }
                    return function(t, n, i) {
                        return n && e(t.prototype, n),
                        i && e(t, i),
                        t
                    }
                }();
                var r = {
                    el: ".news-search",
                    typeHint: !0,
                    typeHighlight: !0,
                    typeMinLength: parseInt(document.getElementById("characterLimit").value),
                    typeMaxLength: 5,
                    searchList: 5,
                    DisMaxItem: document.getElementById("suggestionLimit").value,
                    typeOrder: "asn",
                    typeDelay: 300,
                    globalData: 0,
                    globalSearch: "",
                    typeCache: !0,
                    sAndPLink: document.getElementById("typeAheadUrl").value,
                    searchUrl: $('#shortSearchResultPageLink').val() ? $('#shortSearchResultPageLink').val() : $("#searchResultPageLink").val(),
                    targetsearchUrl: $("#targetedDomain").val()
                }
                  , a = function() {
                    function e() {
                        !function(e, t) {
                            if (!(e instanceof t))
                                throw new TypeError("Cannot call a class as a function")
                        }(this, e),
                        c = this,
                        window.global.eventBindingInst.bindLooping(c.bindingEventsConfig(), c)
                    }
                    return i(e, [{
                        key: "getApiConfig",
                        value: function(e) {
                            return {
                                newsPredictiveSearch: {
                                    type: "get",
                                    accept: "application/json",
                                    crossDomain: !0,
                                    url: r.sAndPLink + "?query="
                                }
                            }[e]
                        }
                    }, {
                        key: "bindingEventsConfig",
                        value: function() {
                            return {
                                "focus #searchInput_field": "defaultDisplay",
                                "blur #searchInput_field": "removeFocus",
                                "keyup #searchInput_field": "checkinputCharacter",
                                "click .search-icon": "searchIconClick",
                                "click .search-input-field .close-icon": "closeIconClick",
                                "click .tt-suggestion": "searchSubmit"
                            }
                        }
                    }, {
                        key: "searchData",
                        value: function() {
                            var e = c.getApiConfig("newsPredictiveSearch");
                            $.ajax(e).done(function(e) {
                                if (e && e.length && e.length > 0) {
                                    var t = e.replace(/[\(\)\"\[\]{}]/g, "").split(",").map(function(e) {
                                        return e.trim()
                                    });
                                    $(".typeahead").typeahead({
                                        hint: r.typeHint,
                                        highlight: r.typeHighlight,
                                        minLength: r.typeMinLength,
                                        maxLength: r.typeMaxLength,
                                        maxItem: r.searchList,
                                        items: r.searchList,
                                        sorter: r.typeOrder,
                                        delay: r.typeDelay,
                                        cache: !1
                                    }, {
                                        name: "result",
                                        source: c.substringMatcher(t),
                                        limit: r.DisMaxItem
                                    }).on("keyup", function(e, t) {
                                        $(e.target).val().toLowerCase();
                                        // window._satellite.cookie.set("searchType", "user-entered-search", 0),
                                        13 == e.which && c.searchSubmit("", t)
                                    })
                                }
                            }).fail(function(e) {
                                console.log(e)
                            })
                        }
                    }, {
                        key: "searchSubmit",
                        value: function(e, t) {
                            // $(e).hasClass("tt-suggestion") && window._satellite.cookie.set("searchType", "typeahead", 0);
                            var n = $("#searchInput_field").val(),
                            i = r.searchUrl + "?searchTerm=" + n;
                            window.location = r.targetsearchUrl+i;
                        }
                    }, {
                        key: "searchIconClick",
                        value: function() {
                            $(".search-input-field .input-group").removeClass("hide"),
                            $("#searchInput_field").focus(),
                            $(".overlay").removeClass("hide"),
                            $("#searchInput_field").val("")
                        }
                    }, {
                        key: "closeIconClick",
                        value: function(e) {
                            $(".search-input-field .input-group").addClass("hide"),
                            $("#searchInput_field").val(""),
                            $(".tt-dataset-result").empty(),
                            window.innerWidth > 1024 && $(".overlay").addClass("hide"),
                            window.innerWidth <= 1024 && ($(".search-input-field .input-group").css("border-bottom", "1px solid #000"),
                            $(e).hide())
                        }
                    }, {
                        key: "checkinputCharacter",
                        value: function(e) {
                            $(e).val().length ? $(".close-icon").show() : $(".close-icon").hide()
                        }
                    }, {
                        key: "removeFocus",
                        value: function() {
                            window.innerWidth <= 1024 && $(".news-search").find(".search-input-field").removeClass("search-focus")
                        }
                    }, {
                        key: "defaultDisplay",
                        value: function(e) {
                            window.innerWidth > 1024 && $(".overlay").removeClass("hide"),
                            window.innerWidth <= 800 && $(".search-input-field .input-group").css("border-bottom", "2px solid #000"),
                            window.innerWidth <= 1024 && $(".news-search").find(".search-input-field").addClass("search-focus")
                        }
                    }, {
                        key: "substringMatcher",
                        value: function(e) {
                            return function(t, n) {
                                var i, r;
                                if (i = [],
                                r = new RegExp(t,"i"),
                                $.each(e, function(e, t) {
                                    r.test(t) && i.push(t)
                                }),
                                i.length)
                                    n(i);
                                else {
                                    var a = 'Oh no! No search Results for: "' + t + '"';
                                    i.push(a),
                                    n(i)
                                }
                            }
                        }
                    }, {
                        key: "render",
                        value: function() {
                            this.searchData(),
                            $('#searchInput_field').attr('placeholder','Search'),
                            $("body").click(function(e) {
                                "searchInput_field" != e.target.id && ($("#search-input-field .tt-menu").hide(),
                                $(".tt-dataset-result").empty(),
                                $(".search-input-field .input-group").addClass("hide"),
                                window.innerWidth > 1024 && $(".overlay").addClass("hide"))
                            })
                        }
                    }, {
                        key: "init",
                        value: function() {
                            c = this,
                            this.render()
                        }
                    }]),
                    e
                }()
                  , c = void 0;
                window.global.SearchProductInstance = window.global.SearchProductInstance || new a,
                window.global.SearchProductInstance.init()
            }
            , {}]
        }, {}, [1]);
        
    }
}
if(document.getElementsByClassName('investerfooter').length){
    document.addEventListener("DOMContentLoaded", function() {
        loadAEMFiles();
    });
}
else{
    loadAEMFiles();
}


