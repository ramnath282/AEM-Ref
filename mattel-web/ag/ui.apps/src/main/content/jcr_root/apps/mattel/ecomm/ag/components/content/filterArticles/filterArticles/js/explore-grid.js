/**
 * featuredDatas.js
 * Version 1.0
 */
//  Handlebars compile cmd ---> handlebars featuredDatas.tmpl -f featuredDatas.tmpl.js
(function (global, AGAEM) {
    'use strict';

    var pagePath = $('#pagePath').val();
    var urlPath =$('#pagePath').length >0 ?  pagePath.split("/").join("!") +".json": "";
    var dataLoaded = false,
        loading = false,
        storageName = "featuredGrid",
        filterClicked = true,
        featuredDatas = {
            el: '.explore-articles-datas',
            gridEl: '#explore-grid-datas',
            bindingEventsConfig: function () {
                var events = {
                    "click .events-update-action": "updateUpcomingEventsContainer",
                    "click .filter-accordion": "filterAccordion",
                    "click .filter-tags-list li a:not(.active)": "filterAction",
                    // "click #explore-grid-datas .social-icons li a": "leavingModal",
                    // "click .explore-articles-datas .social-media-action": "triggerAddthisModal",
                    "click .clear-filter-wrapper .clear-filter": "clearFilter",
                    "click .explore-articles-datas .show-more": "loadMoreAction"
                };
                return events;
            },
            apiConfig: function (apiKey) {
                var obj = {
                    "featuredGrid": {
                        "url": "//" + window.location.host + "/explore/data/filteredArticles."+urlPath,
                        "type": "get"
                    }
                };
                return obj[apiKey];
            },
            leavingModal: function (ele, evt) {
                var self = AGAEM.featuredDatas;
                var $targetModal = $("#exitPageModal");
                var $targetTitle = $(".social-media-action");
                var socialName = $(ele).data("name");
                var socialEnglishText = $(ele).data("nonTranslate");
                var addThisClasName = self.addThisSocialClassNames(socialEnglishText);
                var addThisParentId = $(ele).parents(".list").find(".at-resp-share-element").attr("id");
                if (!socialName || !$targetModal.length) {
                    console.log("Leaving modal element not found..");
                    return;
                }
                $targetTitle.attr("href", "#" + addThisParentId + " ." + addThisClasName);
                $targetTitle.find('span').html(socialName);
                $targetModal.modal("show");
                evt.preventDefault();
            },
            addThisSocialClassNames: function (socialName) {
                var className = "";
                switch (socialName) {
                    case "facebook":
                        className = "at-svc-facebook";
                        break;
                    case "twitter":
                        className = "at-svc-twitter";
                        break;
                    case "pinterest":
                        className = "at-svc-pinterest_share";
                        break;
                }
                return className;
            },
            triggerAddthisModal: function (ele, evt) {
                evt.preventDefault();
                var addThisCall = $(ele).attr("href");
                $(addThisCall)[0].click();
                $("#exitPageModal").modal("hide");
            },
            filterAccordion: function (ele) {
                var self = AGAEM.featuredDatas;
                var $targetEle = $(self.el).find(".filter-tags");
                $(ele).toggleClass("active");
                $targetEle.toggleClass("active");
                $(ele).attr("aria-expanded", $(ele).hasClass("active") ? false : true);
            },
            clearFilter: function (ele) {
                var self = AGAEM.featuredDatas;
                $(ele).parents(".clear-filter-wrapper").addClass("hidden");
                $(self.gridEl).html('').removeClass("success no-result").addClass("data-loading");
                // $(".filter-tags-list li").removeClass("active");
                self.pageReset();
                self.APICallback();
            },
            pageReset: function () {
                this.page = 0;
            },
            isDataNotFound: function (obj) {
                var self = this;
                if (obj.length == 0) {
                    $(self.gridEl).addClass('no-result').removeClass('success data-loading next-data-loading');
                    $(".show-more").addClass("hidden");
                    return false;
                }
                return true;
            },
            addNameToClearFilter: function (filterName) {
                var $wrapper = $(".clear-filter-wrapper"),
                    $elem = $wrapper.find("#filtered-name");
                if (!$elem.length) {
                    console.log("clear filter element not found..");
                    return;
                }
                $elem.html(filterName);
                $wrapper.removeClass("hidden");
            },
            filterAction: function (ele) {
                var self = AGAEM.featuredDatas;
                var filterName = $(ele).closest("li").data("filter");
                var activeClass = "active";
                if (_.isEmpty(self.gridAPIDatas)) {
                    console.log("Grid API datas not found..");
                    return;
                }
                $(".filter-tags-list li a").removeClass(activeClass);
                $(ele).addClass("active");
                if (filterName && filterName.toLowerCase() == "all") {
                    self.clearFilter();
                    return;
                }
                self.filteredDatas = self.filterObjects(self.gridAPIDatas, filterName);
                $(self.gridEl).html('').addClass("data-loading").removeClass("success no-result");
                if (!self.isDataNotFound(self.filteredDatas)) {
                    console.log("No Datas found when passing this filter name :" + filterName);
                    return;
                }
                self.ajaxCollection(self.filteredDatas, filterClicked);
                self.getCollDatas(self.filteredDatas, filterClicked);
            },
            filterObjects: function (obj, uniqueId) {
                var arr = [], i,
                    filterKeyName = $(this.gridEl).data("filterKeyName") || "tagID",
                    primaryTag, secondaryTag;
                    for (i = 0; i < obj.length; i++) {
                        primaryTag = obj[i]["primaryTag"][0] && obj[i]["primaryTag"][0][filterKeyName];
                        secondaryTag =obj[i]["secondaryTag"] ? obj[i]["secondaryTag"][0] && obj[i]["secondaryTag"][0][filterKeyName] : false;
                        if (uniqueId == primaryTag || uniqueId == secondaryTag) {
                            arr.push(obj[i]);
                        }
                    }
                return arr;
            },
            count: function () {
                return this.models.length;
            },
            triggerAddthis: function () {
                typeof addthis.layers.refresh == "function" && addthis.layers.refresh();
            },
            initAddthis: function () {
                var scriptSrc = $("#addthis-url").val();
                if (!scriptSrc) {
                    console.log("AddThis script src not found..");
                    return;
                }
                if (window.addthis) {
                    window.addthis = null;
                }
                $.getScript(scriptSrc, function () {
                    typeof addthis != "undefined" && addthis.init();
                });
            },
            getCollDatas: function (obj, bool) {
                var start, end, ret = [], self = this, $elem = $(self.gridEl), $showMoreBtn = $(".show-more");
                if (typeof obj == "undefined") {
                    console.log("Fn: getcollDatas, object is undefined..");
                    return;
                }
                if (self.initial_count === 'all') { _.each(obj, function (m) { ret.push(m); }); }
                else {
                    if (self.page === 0) {
                        start = 0;
                        end = self.initial_count - 1;
                    } else {
                        if (bool) {
                            self.filPage = start = (obj.length < self.initial_count) ? self.filPage + obj.length : self.initial_count + 1;
                        }
                        else {
                            start = (self.initial_count) + ((self.page - 1) * self.paged_count);
                        }
                        end = start + self.paged_count - 1;
                    }
                    if (end >= self.count() - 1) { end = self.count() - 1; }
                    if (start >= self.count()) { // start has extended past the length... find not loaded
                        /*n = self.paged_count;*/
                        dataLoaded = true;
                    } else {
                        if (bool && self.page == 0) { self.filPage = start = 0; }
                        for (var i = start, m; i < end + 1; i++) {
                            m = obj[i];
                            if (!_.isUndefined(m)) {
                                ret.push(m);
                            }
                        }
                        dataLoaded = false;
                    }
                }
                if (start + ret.length >= self.count()) {
                    $elem.addClass("success");
                    $showMoreBtn.addClass("hidden");
                    console.log("Data Loaded Sucessfully..")
                }
                if (dataLoaded) {
                    return;
                }
                self.page++;
                if ($elem.find("li").length) {
                    $elem.addClass("next-data-loading");
                }
                self.templateBind(ret, self.page == 1 ? filterClicked : undefined, undefined, start + ret.length >= self.count());
            },
            heightSync: function (elem) {
                var max = -1;
                var $heightElem = $(elem).find(".tile-content");
                $heightElem.css("height", "auto");
                $(elem).find('img').imagesLoaded(function () { // image ready
                    _.each($heightElem, function (el) {
                        var height = $(el).height();
                        max = height > max ? height : max;
                    });
                    $heightElem.css('height', max + 'px');
                });
                return;
            },
            templateBind: function (obj, filterClick, dataEmptyMsg, dataLoaded) {
                var self = this,
                    elem = $(this.gridEl),
                    templateId = _.template($("#explore-grid-template").html().trim()),
                    templateCollection,
                    $focusElem;

                templateCollection = templateId({
                    'items': obj
                });
                setTimeout(function () {
                    (filterClick ? elem.html(dataEmptyMsg || templateCollection) : elem.append(templateCollection));
                    $focusElem = $(elem).find(".list[data-index=" + $(templateCollection).first("li").data("index") + "] a:first")
                    self.page > 1 && $focusElem.length && $focusElem.focus();
                    $(elem).find('img').imagesLoaded().progress(function (elem, cur) {
                        console.log(elem, cur)
                    }).done(function () {
                        if (!AGAEM.isMobile) {
                            self.heightSync(elem);
                        }
                        self.templateLoadedCallback(elem, filterClick, dataLoaded);
                    }).fail(function () {
                        console.log('all images loaded, at least one is broken');
                        self.templateLoadedCallback(elem, filterClick, dataLoaded);
                    });
                }, 500);
            },
            templateLoadedCallback: function (elem, filterClick, dataLoaded) {               
                $(elem).removeClass("data-loading no-result next-data-loading");
                loading = false;
                if (!dataLoaded) $(".show-more").removeClass("hidden");
            },
            ajaxCollection: function (obj, filterClick) {
                var el = this.gridEl
                this.initial = $(el).data('initialLoad');
                this.initial_count = this.initial_count || this.initial || 0;
                this.paged_count = 0;
                this.curt_count_obj = {};
                this.page = !filterClick ? this.page || 0 : 0;
                this.paged_count = $(el).data('nextLoad');
                this.models = obj;
                this.filPage = this.filPage || 0;
                this.remItem = this.remItem || 0;
            },
            loadMore: function (curVal) {
                var self = this,
                    obj = $(".filter-tags-list li:not([data-filter='all']) a").hasClass("active") ? self.filteredDatas : self.gridAPIDatas;
                if (!loading && obj.length && window.innerHeight > curVal.getBoundingClientRect().bottom) {
                    loading = true;
                    self.getCollDatas(obj);
                }
            },
            loadMoreAction: function (ele, evt) {
                var self = AGAEM.featuredDatas,
                    obj = $(".filter-tags-list li:not([data-filter='all']) a").hasClass("active") ? self.filteredDatas : self.gridAPIDatas;
                if (!loading && obj.length) {
                    loading = true;
                    $(ele).addClass("hidden");
                    self.getCollDatas(obj);
                }
            },
            props: function (object) {
                var obj = _.map(object, function (element, indx) {
                    return _.extend({}, element, { index: indx });
                });
                return obj;
            },
            APIFilter: function(array){
                var filteredArray = _.filter(array,function(obj) {
                    return obj.displayhomepage == "false";
               });
               return filteredArray;
            },
            APICallback: function (res) {
                var self = this;
                self.gridAPIDatas = self.gridAPIDatas || res;
                self.ajaxCollection(self.gridAPIDatas);
                self.getCollDatas(self.gridAPIDatas);
            },
            APIFails: function () {
                var $gridEle = $(this.gridEl),
                    failsMsg = $gridEle.data("fails");
                if (!failsMsg) {
                    console.log("API data fails message not found..");
                    return;
                }
                $gridEle.removeClass("data-loading").addClass("data-fails");
            },
            gridAPICall: function () {
                var self = this,
                    config = self.apiConfig('featuredGrid'),
                    sessionStorage = AGAEM.getObjectStorage(storageName) != null ? AGAEM.compareStorageDate(AGAEM.getObjectStorage(storageName), storageName) : 0,
                    mappedres = "", apiFilterOnLoad= "";
                if (sessionStorage && sessionStorage != false) {
                    self.APICallback(sessionStorage);
                    return;
                }
                
                $.ajax({
                    type: config.type,
                    url: config.url,
                    contentType: 'application/json',
                    success: function (res) {
                    	 if (!res) {
                             console.log("Featured datas API call fails..")
                             self.APIFails();
                             return;
                         }
                         apiFilterOnLoad = self.APIFilter(res);
                         mappedres = self.props(apiFilterOnLoad);
                         self.APICallback(mappedres);
                         AGAEM.setObjectStorage(storageName, { 'obj': mappedres, 'timestamp': AGAEM.storageExpiryDate() });
                    }
                });
              
            },
            init: function () {
                if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.featuredDatas) return;
                AGAEM.bindLooping(this.bindingEventsConfig(), this);
                this.gridAPICall();
            }
        };
    featuredDatas.init();
    AGAEM.featuredDatas = featuredDatas;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            featuredDatas.init();
        }
    }, false);
    $(function () {
        // lazy load event triggers in page ready
        // $(window).scroll(function () {
        //     var parentContainer = document.querySelector("#explore-grid-datas");
        //     AGAEM.featuredDatas.loadMore(parentContainer);
        // });

        $(window).on('resize', function () {
            var win = $(this); //this = window
            if (win.width() >= 767) {
                AGAEM.featuredDatas.heightSync(AGAEM.featuredDatas.gridEl);
            }
        })
    });
}(window, AGAEM));
