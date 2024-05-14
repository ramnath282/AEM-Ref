/**
 * play-landing-lazyload.js
 * Version 1.0
 */
(function(global, PLAYAEM, galleryObj) {
        var playLanding = {
            el: '.play-character-landing-block',
            bindingEventsConfig: function() {
                var events = {
                    "click .nav-carousel-slides li a ": "filterCategory",
                }
                return events;
            },
            characterLimit: 0,
            gameLimit: 0,
            filterCategory: function(ele) {
                var filterData = ele.dataset.category;
                localStorage.setItem("filterCategory", filterData);
                /*var currentUrl = window.location.pathname+"#"+filterData;
			document.location = currentUrl;*/
                $('.nav-carousel-slides li a').removeClass('active');
                $(ele).addClass('active');
                var curVal = document.querySelector("#play-game");
                var clickEvent = true;
                playLanding.imgRenderer(undefined, curVal, clickEvent);
                $('#play-game .gallery-tile h2').empty().append(filterData);
                var filterDataDropDown = $('.nav-carousel-slides li a.active').text();
                $('.category-drpdown-display').empty().append(filterDataDropDown + '<span></span>');
                if (PLAYAEM.isMobile) {
                    $('.nav-categorylist').hide();
                }
            },
            imgRenderer: function(res, ele, clickEvent) {
                var columnLayout;
                var lazyLoadLimit;
                if (ele.id == "play-character-landing") {
                    var characterhtml = '';
                    var characterData = this.characterData.characters || res; //this.pageLoadActions();
                    lazyLoadLimit = parseInt(this.characterData.lazyLoadLimit);
                    columnLayout = this.characterData.columnLayout;
                    for (var i = this.characterLimit; i < this.characterLimit + lazyLoadLimit; i++) {
                        if (characterData[i] != undefined && characterData[i].tilePath != undefined) {
                            characterhtml = "".concat(characterhtml, " <div class=\"lazy-load ").concat(columnLayout, "\"><section aria-label=\"").concat(characterData[i].tileImgAltText, "\"><a href=\"").concat(characterData[i].tilePath, "\"><img src=\"").concat(characterData[i].tileImage, "\" alt=\"").concat(characterData[i].tileImgAltText, "\"><span class=\"charTitle\">").concat(characterData[i].tileTitle, "</span><div class=\"description-tag\">").concat(characterData[i].descLineOne ? "<p>".concat(characterData[i].descLineOne, "</p>") : '').concat(characterData[i].descLineTwo ? "<p>".concat(characterData[i].descLineTwo, "</p>") : '', "</div></a></section></div>");
                        } else if (characterData[i] != undefined && characterData[i].tilePath == undefined) {
                            characterhtml = "".concat(characterhtml, " <div class=\"lazy-load ").concat(columnLayout, "\"><section aria-label=\"").concat(characterData[i].tileImgAltText, "\"><a href=\"javascript:void(0);\"><img src=\"").concat(characterData[i].tileImage, "\" alt=\"").concat(characterData[i].tileImgAltText, "\"><span class=\"charTitle\">").concat(characterData[i].tileTitle, "</span><div class=\"description-tag\">").concat(characterData[i].descLineOne ? "<p>".concat(characterData[i].descLineOne, "</p>") : '').concat(characterData[i].descLineTwo ? "<p>".concat(characterData[i].descLineTwo, "</p>") : '', "</div></a></section></div>");
                        }
                        $('.description-tag p:empty').remove();
                        $('.description-tag:empty').remove();
                    }
                    this.characterLimit = this.characterLimit + lazyLoadLimit;
                    $('#play-character-landing .play-list').append(characterhtml);
                } else if (ele.id == "play-game") {
                    var gamehtml = '';
                    var gameData;
                    var filteredCategory = localStorage.getItem("filterCategory");
                    this.gameLimit = 0;
                    if (filteredCategory != 'All' && filteredCategory != '') {
                        gameData = this.gameData.games.filter(function(i) {
                            for (var x = 0; x < i.tileTags.length; x++) {
                                if (i.tileTags[x].split("/").pop() == filteredCategory) return i.tileTags[x].split("/").pop();
                            }
                        });
                    } else {
                        gameData = this.gameData.games || res; //this.pageLoadActions();
                    }
                    lazyLoadLimit = parseInt(this.gameData.lazyLoadLimit);
                    columnLayout = this.gameData.columnLayout;
                    var titleAlign = this.gameData.titleAlign;
                    if (clickEvent) {
                        for (var j = this.gameLimit; j < this.gameLimit + lazyLoadLimit; j++) {
                            if (gameData[j] != undefined) {
                                gamehtml = gamehtml + '<li class="' + columnLayout + '"><a href=' + gameData[j].tilePath + ' data-tracking-id="Game Thumbnail Section|' + gameData[j].alwaysEnglish + '|' + gameData[j].alwaysEnglish + '|Game Thumbnail" ><div class="gallery-image"><img class="image-container" src=' + gameData[j].tileImage + ' data-src=' + gameData[j].tileImage + ' data-hover=' + gameData[j].hoverOverImg + ' alt="' + gameData[j].tileImgAltText + '"/></div><span style="text-align:' + titleAlign + '">' + gameData[j].tileTitle + '</span></a></li>';
                            }
                        }
                    } else {
                        var limit = $('#play-game li').length;
                        for (var k = limit; k < limit + lazyLoadLimit; k++) {
                            if (gameData[k] != undefined) {
                                gamehtml = gamehtml + '<li  class="' + columnLayout + '"><a href=' + gameData[k].tilePath + ' data-tracking-id="Game Thumbnail Section|' + gameData[k].alwaysEnglish + '|' + gameData[k].alwaysEnglish + '|Game Thumbnail" ><div class="gallery-image"><img class="image-container" src=' + gameData[k].tileImage + ' data-src=' + gameData[k].tileImage + ' data-hover=' + gameData[k].hoverOverImg + ' alt="' + gameData[k].tileImgAltText + '"/></div><span style="text-align:' + titleAlign + '">' + gameData[k].tileTitle + '</span></a></li>';
                            }
                        }
                    }
                    this.gameLimit = this.gameLimit + lazyLoadLimit;
                    if (clickEvent) $('#play-game .play-list').empty().append(gamehtml);
                    else $('#play-game .play-list').append(gamehtml);
                    if ($('#play-game .play-list li').length == 0) $('#play-game .play-list').empty().append('<div class="no-data">No Data Found</div>');
                    $("#play-game .play-list li:nth-child(4n+4)").next().css('clear', 'both');
                }
                galleryObj.imgHoverOver();
                if (ele.id == "play-game") {
                    this.maintainHeight("#" + ele.id);
                }
            },
            maintainHeight: function(elem) {
                //var self = this;
                if (!$("#play-game li").length || $("#play-game li").length > (this.gameData && this.gameData.games.length)) return;
                if (!elem) {
                    var playElem = $("#play-game");
                    $(playElem).find(".gallery-image").css({
                        'height': 'auto',
                        'line-height': 1
                    });
                    $(playElem).find(".gallery-image+span").css('height', 'auto');
                }
                var max = -1,
                    imgMax = max,
                    $heightElem = $(elem).find(".gallery-image+span"),
                    $imgElem,
                    imgHght, height;
                $(elem).find('img').imagesLoaded(function() { // image ready
                    _.each($heightElem, function(el) {
                        $imgElem = $(el).closest("li").find(".gallery-image");
                        imgHght = $imgElem.length ? $imgElem.innerHeight() : 0;
                        height = $(el).innerHeight();
                        max = height > max ? height : max;
                        imgMax = imgHght > imgMax ? imgHght : imgMax;
                    });
                    $heightElem.css('height', max + 'px');
                    imgMax && $(elem).find(".gallery-image").css({
                        'height': imgMax + 'px',
                        'line-height': imgMax + 'px'
                    });
                });
                return;
            },
            heightSync: function() {
                var $heightSyncElem = $(this.el + '[data-height-sync]');
                _.each($heightSyncElem, function(item) {
                    var max = -1;
                    if ($(item).find('img').length && typeof $.fn.imagesLoaded == "function") {
                        $(item).find('img').imagesLoaded(function() {
                            // image ready
                            _.each($(item).find($(item).data('height-sync')), function(el) {
                                var height = $(el).height();
                                max = height > max ? height : max;
                            });
                            //image css Apply
                            $(item).find($(item).data('height-sync')).css({
                                'height': max + 'px',
                                'line-height': max + 'px',
                            });
                        });
                        return;
                    }
                    // Not-scene7-image
                    _.each($(item).find($(item).data('height-sync')), function(el) {
                        var height = $(el).height();
                        max = height > max ? height : max;
                    });
                    // Not-scene7-image-height
                    $(item).find($(item).data('height-sync')).css({
                        'height': max + 'px',
                        'line-height': max + 'px',
                    });
                    console.log($(item), max)
                });
            },
            resizeBind: function() {
                var self = this;
                window.onresize = function(event) {
                    if (!PLAYAEM.isMobile) {
                        setTimeout(function() {
                            self.heightSyncLabel();
                            self.maintainHeight();
                        }, 200);
                    }
                };
            },
            htmlLazyload: function() {
                //var self = this;
                var nowPlayingTxt = $('#now-playing-text').val();
                $(window).scroll(function() {
                    //var self = this;
                    if ($('#play-character-landing').is(":visible")) playLanding.loadMoreCharacters(document.querySelector("#play-character-landing"));
                    if ($('#play-game').is(":visible")) {
                        playLanding.loadMoreGames(document.querySelector("#play-game"));
                        $('#play-game .playing-overlay').remove();
                        var currentPath = window.location.pathname;
                        $('#play-game .play-list li a[href="' + currentPath + '"]').append('<div class="playing-overlay"><span>' + nowPlayingTxt + '</span></div>');
                    }
                });
            },
            loadMoreCharacters: function(curVal) {
                var self = this;
                var characterData = self.characterData.characters;
                if (window.innerHeight > curVal.getBoundingClientRect().bottom) {
                    if (self.characterLimit <= characterData.length) {
                        self.imgRenderer(undefined, curVal, undefined);
                        self.heightSyncLabel();
                    }
                }
            },
            loadMoreGames: function(curVal) {
                var self = this;
                var gameData = self.gameData.games;
                if (window.innerHeight >= curVal.getBoundingClientRect().bottom) {
                    if (self.gameLimit <= gameData.length) {
                        self.imgRenderer(undefined, curVal, undefined);
                    }
                }
            },
            loadCategories: function(res) {
                if (res.categories.length != 0) {
                    var categorieshtml = '';
                    var categoriesData = res.categories;
                    var analyticsGameGrid = $("#analytics-gameGrid").val();
                    for (var y = 0; y <= categoriesData.length; y++) {
                        if (categoriesData[y] != undefined) {
                            if (categoriesData[y].categoryTag[0].split("/") == 'All') categorieshtml = categorieshtml + '<li><a href="javascript:void(0)" class="active game-filter" data-category="' + categoriesData[y].categoryTag[0].split('/').pop() + '" title="' + categoriesData[y].categoryTitle + '" target="_self" data-tracking-id="Category Filter Section|' + analyticsGameGrid + '|' + categoriesData[y].categoryTitle + '|Game Category">' + categoriesData[y].categoryTitle + '</a></li>';
                            else categorieshtml = categorieshtml + '<li><a href="javascript:void(0)" class="game-filter" data-category="' + categoriesData[y].categoryTag[0].split('/').pop() + '" title="' + categoriesData[y].categoryTitle + '" target="_self" data-tracking-id="Category Filter Section|' + analyticsGameGrid + '|' + categoriesData[y].categoryTitle + '|Game Category">' + categoriesData[y].categoryTitle + '</a></li>';
                        }
                    }
                    $('.nav-carousel-slides').append(categorieshtml);
                } else {
                    var sectionAltTitle = res.sectionAltTitle;
                    $('#play-game .gallery-tile h2').text(sectionAltTitle);
                }
            },
            heightSyncLabel: function() {
                var $elem = $("#play-character-landing .lazy-load");
                if (!$elem.length || !$(".character-page #play-character-landing, .category-page #play-character-landing").length || PLAYAEM.isMobile) return;
                var max = -1,
                    //imgMax = max,
                    $heightElem = $elem.find('img+span'),
                    height;

                $heightElem.css('height', 'auto');
                $elem.find('img').imagesLoaded(function() { // image ready
                    _.each($heightElem, function(el) {
                        height = $(el).innerHeight();
                        max = height > max ? height : max;
                    });
                    $heightElem.css('height', max + 'px');
                });
                return;
            },
            renderEventsDateFromAPI: function(ele) {
                var self = this;
                var currentPath;
                var obj;
                if ($(this.el).data("height-sync") && !PLAYAEM.isMobile) this.heightSync();
                if ($('#play-character-landing').is(":visible")) {
                    var getCharacters = "/bin/getCharacterLandingGrid";
                    currentPath = $('#currentPagePath').val();
                    obj = {
                        "async": false,
                        "type": "get",
                        "url": window.location.protocol + "//" + window.location.host + getCharacters + "?currentPath=" + currentPath
                    }
                    PLAYAEM.requestAPICall(obj, function(response) {
                        if (!response) {
                            console.log("Err : Upcoming Events API failed..");
                            return false;
                        } else {
                            var characterBlock = document.querySelector("#play-character-landing");
                            //var charactersJson =	{"characters":[{"charTitle":"SHANI1","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/shani.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"GWEN AND GRISELLE2","charPath2":"/content/mattelweb/play/pollypocket/en/home/characters/category2/gwennGriselle.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"GRADMA POCKET3","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/grandmaPocket.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"PAXTON4","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/paxton.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"NIKOLAS5","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/nikolas.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"PIERCE POCKET6","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/piercePocket.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"MOM &amp; DAD7","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/momnDad.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"POLLY POCKET8","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/pollyPocket.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"LILA9","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/lila.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"SHANI10","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/shani.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"GWEN AND GRISELLE11","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/gwennGriselle.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"GRADMA POCKET12","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/grandmaPocket.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"PAXTON13","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/paxton.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"NIKOLAS14","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/nikolas.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"PIERCE POCKET15","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/piercePocket.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"MOM &amp; DAD16","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/momnDad.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"POLLY POCKET17","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/pollyPocket.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"charTitle":"LILA18","charPath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/lila.html","charImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"}],"lazyLoadLimit":"12"};
                            var charactersJson = response;
                            self.characterData = charactersJson;
                            self.imgRenderer(charactersJson, characterBlock, undefined);
                            self.htmlLazyload();
                            self.heightSyncLabel();
                        }
                    })
                }
                if ($('#play-game').is(":visible")) {
                    localStorage.setItem("filterCategory", "");
                    var getGames = "/bin/getGameLandingGrid";
                    currentPath = $('#currentPath').val();
                    var currentPagePath = $('#currentPagePath').val();
                    obj = {
                        "async": false,
                        "type": "get",
                        "url": window.location.protocol + "//" + window.location.host + getGames + "?currentPath=" + currentPath + "&currentPagePath=" + currentPagePath
                    }
                    PLAYAEM.requestAPICall(obj, function(response) {
                        if (!response) {
                            console.log("Err : Upcoming Events API failed..");
                            return false;
                        } else {
                            var gameBlock = document.querySelector("#play-game");
                            //var gamesJson =	{"categories":[{"title":"All","tag":["mattelweb:playsites/pollypocket/categories/All"]},{"title":"category1","tag":["mattelweb:playsites/pollypocket/categories/category1"]},{"title":"category2","tag":["mattelweb:playsites/pollypocket/categories/category2"]},{"title":"category3","tag":["mattelweb:playsites/pollypocket/categories/category3"]},{"title":"category4","tag":["mattelweb:playsites/pollypocket/categories/category4"]},{"title":"category5","tag":["mattelweb:playsites/pollypocket/categories/category5"]},{"title":"category6","tag":["mattelweb:playsites/pollypocket/categories/category6"]},{"title":"category7","tag":["mattelweb:playsites/pollypocket/categories/category7"]},{"title":"category8","tag":["mattelweb:playsites/pollypocket/categories/category8"]},{"title":"category9","tag":["mattelweb:playsites/pollypocket/categories/category9"]},{"title":"category10","tag":["mattelweb:playsites/pollypocket/categories/category10"]}],"characters":[{"tileTitle":"gameSHANI1","tileTags":["mattelweb:playsites/pollypocket/categories/category1","mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/shani.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameGWEN AND GRISELLE2","tileTags":["mattelweb:playsites/pollypocket/categories/category1"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/gwennGriselle.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameGRADMA POCKET3","tileTags":["mattelweb:playsites/pollypocket/categories/category1"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/grandmaPocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gamePAXTON4","tileTags":["mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/paxton.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameNIKOLAS5","tileTags":["mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/nikolas.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"PIERCE POCKET6","tileTags":["mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/piercePocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameMOM &amp; DAD7","tileTags":["mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/momnDad.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gamePOLLY POCKET8","tileTags":["mattelweb:playsites/pollypocket/categories/category4"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/pollyPocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameLILA9","tileTags":["mattelweb:playsites/pollypocket/categories/category5"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/lila.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameSHANI10","tileTags":["mattelweb:playsites/pollypocket/categories/category6"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/shani.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"GWEN AND GRISELLE11","tileTags":["mattelweb:playsites/pollypocket/categories/category8"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/gwennGriselle.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameGRADMA POCKET12","tileTags":["mattelweb:playsites/pollypocket/categories/category9"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category2/grandmaPocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gamePAXTON13","tileTags":["mattelweb:playsites/pollypocket/categories/category10"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/paxton.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameNIKOLAS14","tileTags":["mattelweb:playsites/pollypocket/categories/category7"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/nikolas.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gamePIERCE POCKET15","tileTags":["mattelweb:playsites/pollypocket/categories/category8"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category3/piercePocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameMOM &amp; DAD16","tileTags":["mattelweb:playsites/pollypocket/categories/category9"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/momnDad.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gamePOLLY POCKET17","tileTags":["mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/pollyPocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameLILA18","tileTags":["mattelweb:playsites/pollypocket/categories/category4"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/lila.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gamePOLLY POCKET19","tileTags":["mattelweb:playsites/pollypocket/categories/category3"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/pollyPocket.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"},{"tileTitle":"gameLILA20","tileTags":["mattelweb:playsites/pollypocket/categories/category2"],"tilePath":"/content/mattelweb/play/pollypocket/en/home/characters/category1/lila.html","tileImage":"https://play.pollypocket.com/sites/mattel_pollypocket/files/2018-04/Polly-Characters-Thumbs-lila-min.jpg"}],"lazyLoadLimit":"5"};
                            var gamesJson = response;
                            self.gameData = gamesJson;
                            self.loadCategories(gamesJson);
                            if (localStorage.getItem("filterCategory") != '') {
                                $('.nav-carousel-slides li a').removeClass('active');
                                var currentCategory = localStorage.getItem("filterCategory");
                                $('.nav-carousel-slides li a[data-category=' + currentCategory + ']').addClass('active');
                            }
                            self.imgRenderer(gamesJson, gameBlock, undefined);
                            self.htmlLazyload();
                            var pathName = window.location.pathname;
                            $('#play-game .play-list li a[href="' + pathName + '"]').closest('li').addClass("active-grid");
                        }
                    })
                }
            },
            init: function() {
                if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.playLandingLazyLoad) return;
                PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
                this.renderEventsDateFromAPI();
                this.resizeBind();
            }
        }
        playLanding.init();
        PLAYAEM.playLandingLazyLoad = playLanding;
        document.addEventListener('DOMContentLoaded', function() {
            if (!PLAYAEM.isDependencyLoaded) {
                playLanding.init();
            }
        }, false);
    }

    (window, PLAYAEM, PLAYAEM.retailers));