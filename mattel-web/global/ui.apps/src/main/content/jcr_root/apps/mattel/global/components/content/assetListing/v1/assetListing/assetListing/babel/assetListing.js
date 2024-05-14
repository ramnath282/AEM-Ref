const config = {
    el: '.filter-grid',
    lg_only: window.matchMedia('(min-width: 1024px) and (max-width: 1199px)'),
    md_only: window.matchMedia('(min-width: 768px) and (max-width: 1023px)'),
    sm_only: window.matchMedia('(min-width: 480px) and (max-width: 767px)'),
    xs_only: window.matchMedia('(min-width: 320px) and (max-width: 479px)'),
    gridRow: 0,
    deviceGridCounts: 0,
    gridDefault: '.grid-default',
    showMoreBtn: '.showMoreBtn',
    showLessBtn: '.active',
    activeClass: 'active',
    viewAllBtn: '.viewAllBtn',
    showMoreActive: 'show-more-active',
    showLessActive: 'show-less-active',
    isMobile : window.innerWidth <= 767
}
class filterGridContainer {
    constructor() {
        self = this;
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);        
        self.isFiltered = false;
    }
    
    bindingEventsConfig() {
        const events = {
            "click .grid-filter input[type=checkbox]": "getFilterTags",
            "click .grid-filter input[type=radio]": "filterRadioChecked",
            "click .cta-filter" : "showFilter",
            "click .cta-apply" : "hideFilter",
            "click .assetListing .overlay" : "hideFilter",
            "click .grid-filter #clearAllFilter" : "clearFilter",
            "click .grid-filter .accordion-action": "expandAccordion"
        };
        return events;
    }
    clearFilter(ele, evt) {
        $(".grid-filter input[type=checkbox]").prop( "checked", false );
        $(".grid-filter #clearAllFilter").addClass("hide");
        $(config.viewAllBtn).hide();
        $(config.showMoreBtn).hide();
        $(".filter-grid .grid-content").addClass("hide").removeAttr("style").removeClass("filtered");
        self.noFilterData();
        if(ele) {
            $(".category-group .all-radio-btn").prop("checked", true);
        }
    }
    
    showFilter(ele,evt) {
        if(evt) {
            evt.stopPropagation();
        }        
        $('.grid-filter, .assetListing .overlay').addClass("show");
        $("body").addClass("asset-filter-on");
        if(ele != "on") {
            self.isFilterOn = true;
        }        
    }
    hideFilter(ele){
        $('.grid-filter, .assetListing .overlay').removeClass("show");
        $("body").removeClass("asset-filter-on");
        self.isFilterOn = false;
    }
    filterRadioChecked(ele,evt) {
        if($(ele).hasClass("all-radio-btn")) {
            let totalFliter = 0;
            $(".grid-filter input[type=checkbox]").each(function(item){
                if($(this).is(':checked')) {                    
                    totalFliter +=1;                                             
                }
            });
            if(totalFliter == 0) {
                self.clearFilter();
                return false;
            }
        }
        self.getFilterTags();
    }
    getFilterTags(ele, evt) {
        var totalFliter = 0;
        self.catergoryGroup = {};
        self.categoryNames = [];
        $(".grid-filter input[type=checkbox], .grid-filter input[type=radio]").each(function(item){
            if($(this).is(':checked')) {
                if($(this).attr("type") === "checkbox") {
                    totalFliter +=1;
                }
                self.isFiltered = true;
                let parent = $(this).attr("data-parent");
                if(!self.catergoryGroup[parent]) {
                    self.catergoryGroup[parent] = [];
                }
                let val = $(this).val().split(",");
                $.each(val, function(k,v){
                    self.catergoryGroup[parent].push(v);  
                })
                              
            } else {
                $(".filter-grid .grid-content").removeClass("hide filtered").removeAttr("style");
            }
        });
        $.each(self.catergoryGroup, function(k,v){
            self.categoryNames.push(k);
        })
        let tagsArr = self.catergoryGroup[self.categoryNames[0]]  ? self.catergoryGroup[self.categoryNames[0]] : [];

        if(totalFliter > 0) {
            $(".grid-filter #clearAllFilter span").text(totalFliter);
            $(".grid-filter #clearAllFilter").removeClass("hide");
        } else {
            $(".grid-filter #clearAllFilter").addClass("hide");
            self.clearFilter();
            return false;
        }
       
        self.applyFilter(tagsArr)
    }
    applyFilter(tagsArr) {
        
        if(tagsArr.length) {            
            $(".filter-grid .grid-content").addClass("hide").removeAttr("style");
            self.showHideGridItems(tagsArr);
        } else {
            $(".filter-grid .grid-content").removeClass("hide filtered");
            self.isFiltered = false;
            self.gridCount();
        }
    }
    tagValidatation(checkedTag, listTag) {
        let returnFlag;
        if(self.categoryNames.length == 1) {
            returnFlag = listTag.indexOf(checkedTag) != -1 ? true : false;
        } else {
            let flags = [];
            for(let i = 1; i < self.categoryNames.length; i++) {

                let arr = self.catergoryGroup[self.categoryNames[i]];
                for(let k=0; k<arr.length; k++) {
                   var tagMatch = (listTag.indexOf(checkedTag) != -1) && (listTag.indexOf(arr[k]) != -1)
                   flags.push(tagMatch);
                }
            }
            returnFlag = flags.indexOf(true) != -1 ? true : false;
        }
        return returnFlag;
    }
    showHideGridItems(tagsArr) {
        var hiddenEleArr = [];
        $(".filter-grid .grid-content .asset-component").each(function(i){
            hiddenEleArr.push(i);
        });
        $.each(tagsArr, (k,v) =>{      
            let newArray = []      
            $.each(hiddenEleArr, function(k1,v1){
                let listTags = $(".filter-grid .grid-content .asset-component ").eq(v1).data("tags");
                if(self.tagValidatation(v, listTags)){                
                    $(".filter-grid .grid-content").eq(v1).removeClass("hide").addClass("filtered");                    
                } else {
                    newArray.push(v1);
                }
            });
            hiddenEleArr = newArray.slice();
        });

        self.gridCount();
        
    }
    
    isJSON(str) { 
        try { 
            return (JSON.parse(str) && !!str); 
        } catch (e) { 
            return false; 
        } 
    } 
    gridCount(ele, evt) {
        $(".filter-grid.with-see-more").each((index, item) => {
            let gridContainer = $(item),
                viewAll = gridContainer.find(config.viewAllBtn),
                showMore = gridContainer.find(config.showMoreBtn),
                gridDefaultWrapper = gridContainer.find(config.gridDefault),
                gridContentLength = self.isFiltered ? gridContainer.find('li.filtered').length : gridContainer.find('li').length,
                gridCountObj = gridDefaultWrapper.attr("data-grid-count"),
                gridListRowCountObj = gridDefaultWrapper.attr("data-grid-row-count"),
                dataGridRowDisplayObj = !self.isJSON(gridDefaultWrapper.attr("data-grid-row-display")) || gridDefaultWrapper.attr("data-grid-row-display"),
                isRowTrue = gridDefaultWrapper.attr("data-is-row"),
                isMobileScroll = gridDefaultWrapper.attr("data-is-mobile-scroll"),
                gridCounts = jQuery.parseJSON(gridCountObj),
                gridListRowCounts = jQuery.parseJSON(gridListRowCountObj),
                dataGridRowDisplays = jQuery.parseJSON(dataGridRowDisplayObj),
                initialGridWithRowCount = self.gridWithRow(gridCounts, dataGridRowDisplays),
                initialdeviceGridCounts = self.deviceGridCountsFn(gridCounts),
                moretext = gridContainer.find('.showMoreBtn .btn-cta-item').attr('data-text-collapse'),
                lesstext = gridContainer.find('.showMoreBtn .btn-cta-item').attr('data-text-expand');

            let dataSeeMore = gridDefaultWrapper.attr("data-grid-see-more"),
                initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
            viewAll.hide();
            showMore.removeClass(config.activeClass);
            showMore.find('.btn-cta-item').text(moretext).addClass(config.showMoreActive).removeClass(config.showLessActive);
            showMore.css("display", "block");
            showMore.find('.btn-cta-item').unbind("click");

            if (isRowTrue == "false") {
                if(self.isFiltered) {
                    gridContainer.find(`li.filtered:lt(${initialdeviceGridCounts})`).show();
                } else {
                    gridContainer.find(`li:lt(${initialdeviceGridCounts})`).show();
                }
                showMore.find('.btn-cta-item').click(e => {
                    if (!gridContainer.children(config.showMore).hasClass(config.activeClass)) {
                        const elm = self.isFiltered ? gridContainer.find(`li.filtered:lt(${gridContentLength})`) : gridContainer.find(`li:lt(${gridContentLength})`);
                        elm.show();   
                        viewAll.show();                     
                        showMore.addClass(config.activeClass);
                        showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                    } else {
                        self.collaps(gridCounts, gridContainer, gridListRowCounts, isRowTrue, showMore, moretext);                        
                        viewAll.hide();
                    }
                });
            } else {
                if(self.isFiltered) {
                    gridContainer.find(`li.filtered:lt(${initialGridWithRow})`).show();
                } else {
                    gridContainer.find(`li:lt(${initialGridWithRow})`).show();
                }
                
                showMore.find('.btn-cta-item').click(e => {
                    if (!gridContainer.children(config.showMore).hasClass(config.activeClass)) {
                        initialGridWithRow = (initialGridWithRow + parseInt(initialGridWithRowCount) <= gridContentLength) ? initialGridWithRow + parseInt(initialGridWithRowCount) : gridContentLength;
                        viewAll.show();
                        if(self.isFiltered) {
                            gridContainer.find(`li.filtered:lt(${initialGridWithRow})`).show();
                        } else {
                            gridContainer.find(`li:lt(${initialGridWithRow})`).show();
                        }
                       
                        if (initialGridWithRow == gridContentLength) {
                            showMore.addClass(config.activeClass);
                            showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                            initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
                        }
                        if (dataSeeMore != null) {
                            dataSeeMore--;
                            if (dataSeeMore == 0) {
                                showMore.addClass(config.activeClass);
                                showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                                gridContainer.find(`.grid-default li:lt(${gridContentLength})`).show();
                                initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
                            }
                        }
                    } else {
                        self.collaps(gridCounts, gridContainer, gridListRowCounts, isRowTrue, showMore, moretext);                        
                        viewAll.hide();
                        dataSeeMore = gridDefaultWrapper.attr("data-grid-see-more");
                    }

                });
            }
            if (gridContentLength <= initialGridWithRow) {
                showMore.hide();
            }
            if (isMobileScroll == "true") {
                if (config.xs_only.matches) {
                    gridContainer.children(config.gridDefault).find(`li:lt(${gridContentLength})`).show();
                    gridContainer.children(config.gridDefault).addClass("withSlider");
                    gridContainer.children('.withSlider').slick({
                        dots: true,
                        infinite: true,
                        speed: 300,
                        slidesToShow: 2,
                        adaptiveHeight: true
                    });
                    gridContainer.children('.showMoreBtn').hide();
                }
            }
        });
        self.noFilterData();
    }
    noFilterData() {
        var gridFilterHeight = $(".grid-filter").height();  
        if($(".filter-grid .grid-content").length === $(".filter-grid .grid-content.hide").length) {
            $(".asset-listing-container #assetNoFilterData").removeClass("hide").css("margin-top",gridFilterHeight/2);
        } else {
            $(".asset-listing-container #assetNoFilterData").addClass("hide");
        }
    }
    collaps(gridCounts, gridContainer, gridListRowCounts, isRowTrue, showMore, moretext) {
        const initialdeviceGridCounts = self.deviceGridCountsFn(gridCounts),
            initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
        if (isRowTrue == "false") {
            self.isFiltered ? gridContainer.find('.grid-default li.filtered').not(`:lt(${initialdeviceGridCounts})`).hide() : gridContainer.find('.grid-default li').not(`:lt(${initialdeviceGridCounts})`).hide();
        } else {
            self.isFiltered ? gridContainer.find('.grid-default li.filtered').not(`:lt(${initialGridWithRow})`).hide() : gridContainer.find('.grid-default li').not(`:lt(${initialGridWithRow})`).hide();
        }
        showMore.removeClass(config.activeClass);
        showMore.find('.btn-cta-item').text(moretext).removeClass(config.showLessActive).addClass(config.showMoreActive);
        const scrollPos = gridContainer.find(config.gridDefault).offset().top;
        $(window).scrollTop(scrollPos);
    }

    deviceGridCountsFn(gridCounts) {
        switch (true) {
            case config.lg_only.matches:
                config.deviceGridCounts = gridCounts.lg;
                break;
            case config.md_only.matches:
                config.deviceGridCounts = gridCounts.md;
                break;
            case config.sm_only.matches:
                config.deviceGridCounts = gridCounts.sm;
                break;
            case config.xs_only.matches:
                config.deviceGridCounts = gridCounts.xs;
                break;
            default:
                config.deviceGridCounts = gridCounts.xl;
                break;
        }
        return config.deviceGridCounts;
    }
    gridWithRow(gridCounts, gridListRowCounts) {
        switch (true) {
            case config.lg_only.matches:
                config.gridRow = gridCounts.lg * gridListRowCounts.lg;
                break;
            case config.md_only.matches:
                config.gridRow = gridCounts.md * gridListRowCounts.md;
                break;
            case config.sm_only.matches:
                config.gridRow = gridCounts.sm * gridListRowCounts.sm;
                break;
            case config.xs_only.matches:
                config.gridRow = gridCounts.xs * gridListRowCounts.xs;
                break;
            default:
                config.gridRow = gridCounts.xl * gridListRowCounts.xl;
                break;
        }
        return config.gridRow;
    }
    expandAccordion(elem, evt) {
        if(window.innerWidth <= 767) {
            evt.preventDefault();
            $(elem).next(".category-group li:first-child").focus();
            $(elem).next(".category-group").slideToggle();
            $(elem).toggleClass("accordion-expand-in");
            $(elem).find("a").attr("aria-expanded", $(elem).hasClass("accordion-expand-in") ? true : false);
        }
    }
    ariaExpandedAdded(ele, isMobileOnly) {
        if (!ele) {
            console.log("Aria expanded attribute element undefined..");
            return;
        }
        if (isMobileOnly && !(config.isMobile)) return;
        _.each(ele, item => {
            $(item).attr("aria-expanded", false);
        });
    }
    render() {
        $(".grid-filter").length ? self.getFilterTags() : self.gridCount();
        self.isFilterOn = false;   
        self.ariaExpandedAdded($(".grid-filter .accordion-action a"), true);         
    }
    init() {
        self = this;
        self.render();
        $(window).resize(function(){
            if(window.outerWidth >= 768) {
                self.showFilter("on");
            } else {
                if(!self.isFilterOn) {
                    self.hideFilter();
                }                
            }
        });
        $(".filter-grid .ctaItem").each(function() {
            if($(this).text().trim() == "") {
                $(this).addClass("hide");
            }            
        });    
    }
}
let self;
window.global.filterGridContainerInstance = window.global.filterGridContainerInstance || new filterGridContainer();
window.global.filterGridContainerInstance.init();