(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['articles.tmpl'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression, alias3=depth0 != null ? depth0 : (container.nullContext || {});

  return "    <li class=\"list\">\r\n        <div class=\"tile-container\">\r\n            <div class=\"tile-clickable\">\r\n                <img src="
    + alias2(alias1((depth0 != null ? depth0.articleGridImage : depth0), depth0))
    + " alt=\"\">\r\n                <div class=\"tile-content\">\r\n                    <div class=\"tile-tip\">\r\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.hideArticleDate : depth0),{"name":"if","hash":{},"fn":container.program(2, data, 0),"inverse":container.program(4, data, 0),"data":data})) != null ? stack1 : "")
    + "                        <span class=\"tile-tip-B\">"
    + alias2(alias1(((stack1 = ((stack1 = (depth0 != null ? depth0.primaryTag : depth0)) != null ? stack1["0"] : stack1)) != null ? stack1.tagName : stack1), depth0))
    + "</span>\r\n                    </div>\r\n                    <h3 class=\"tile-name\">"
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "</h3>\r\n                    <div class=\"tile-description\">"
    + alias2(alias1((depth0 != null ? depth0.articleDescription : depth0), depth0))
    + "</div>                                            \r\n                </div>\r\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.vanityUrl : depth0),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.program(8, data, 0),"data":data})) != null ? stack1 : "")
    + "            </div>\r\n            <ul class=\"social-icons\">\r\n                <li>\r\n"
    + ((stack1 = helpers["if"].call(alias3,(depth0 != null ? depth0.vanityUrl : depth0),{"name":"if","hash":{},"fn":container.program(10, data, 0),"inverse":container.program(12, data, 0),"data":data})) != null ? stack1 : "")
    + "                </li>\r\n                <li>\r\n                    <a class=\"twitter track-social-share\" data-tracking-social=\"twitter\" \ data-link-attr=\"Twitter\" data-componentname=\"Related articles\" aria-label=\"twitter opens a new window\" data-toggle=\"modal\" data-target=\"#exitPageModal\" href=\"https://twitter.com/american_girl\" data-title=\""
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "\" data-description=\""
    + alias2(alias1((depth0 != null ? depth0.articleDescription : depth0), depth0))
    + "\" data-media=\""
    + alias2(alias1((depth0 != null ? depth0.articleGridImage : depth0), depth0))
    + "\"></a>\r\n                </li>\r\n                <li>\r\n                    <a class=\"pinterest track-social-share\"  data-tracking-social=\"pinterest\" data-componentname=\"Related articles\" data-link-attr=\"Pinterest\" aria-label=\"pinterest opens a new window\" data-toggle=\"modal\" data-target=\"#exitPageModal\" href=\"http://www.pinterest.com/agofficial\" data-title=\""
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "\" data-description=\""
    + alias2(alias1((depth0 != null ? depth0.articleDescription : depth0), depth0))
    + "\" data-media=\""
    + alias2(alias1((depth0 != null ? depth0.articleGridImage : depth0), depth0))
    + "\"></a>\r\n                </li>\r\n            </ul>   \r\n        </div>\r\n    </li>\r\n";
},"2":function(container,depth0,helpers,partials,data) {
    return "";
},"4":function(container,depth0,helpers,partials,data) {
    return "                            <span class=\"tile-tip-A\">"
    + container.escapeExpression(container.lambda((depth0 != null ? depth0.articleDate : depth0), depth0))
    + "</span>\r\n";
},"6":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                    <a class=\"arrow-right read-more\" data-tracking-related-article = Click-Event|click|Related-Articles|"+depth0.articleTitle.replace(/ /g, '-')+" href=\""
    + alias2(alias1((depth0 != null ? depth0.vanityUrl : depth0), depth0))
    + "\"><span>Read more</span> <span class=\"sr-only\"> "
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "</span> </a>\r\n";
},"8":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                    <a class=\"arrow-right read-more\"  data-tracking-related-article = Click-Event|click|Related-Articles|"+depth0.articleTitle.replace(/ /g, '-')+" href=\""
    + alias2(alias1((depth0 != null ? depth0.pagePath : depth0), depth0))
    + "\"><span>Read more</span> <span class=\"sr-only\"> "
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "</span> </a>\r\n";
},"10":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                        <a class=\"facebook track-social-share\" data-link-attr=\"Facebook\" data-componentname=\"Related articles\" data-tracking-social=\"facebook\" data-url="
    + alias2(alias1((depth0 != null ? depth0.vanityUrl : depth0), depth0))
    + " aria-label=\"facebook opens a new window\" data-toggle=\"modal\" data-target=\"#exitPageModal\" href=\"https://www.facebook.com/americangirl#fb\" data-title=\""
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "\" data-description=\""
    + alias2(alias1((depth0 != null ? depth0.articleDescription : depth0), depth0))
    + "\" data-media=\""
    + alias2(alias1((depth0 != null ? depth0.articleGridImage : depth0), depth0))
    + "\"></a>\r\n";
},"12":function(container,depth0,helpers,partials,data) {
    var alias1=container.lambda, alias2=container.escapeExpression;

  return "                        <a class=\"facebook track-social-share\" data-link-attr=\"Facebook\" data-componentname=\"Related articles\" data-tracking-social=\"facebook\" data-url="
    + alias2(alias1((depth0 != null ? depth0.pagePath : depth0), depth0))
    + " aria-label=\"facebook opens a new window\" data-toggle=\"modal\" data-target=\"#exitPageModal\" href=\"https://www.facebook.com/americangirl#fb\" data-title=\""
    + alias2(alias1((depth0 != null ? depth0.articleTitle : depth0), depth0))
    + "\" data-description=\""
    + alias2(alias1((depth0 != null ? depth0.articleDescription : depth0), depth0))
    + "\" data-media=\""
    + alias2(alias1((depth0 != null ? depth0.articleGridImage : depth0), depth0))
    + "\"></a>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.items : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\r\n";
},"useData":true});
})();