const config = {
    el: '.grid-container',
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
}
class gridContainer {
    constructor() {
        self = this;
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
    }
    bindingEventsConfig() {
        const events = {};
        return events;
    }    
    gridCount(ele, evt) {
        $(".grid-container").each((index, item) => {
            let gridContainer = $(item),
            gridContentLength = gridContainer.find('li').length,
            gridDefaultWrapper = gridContainer.find(config.gridDefault),    
            isMobileScroll = gridDefaultWrapper.attr("data-is-mobile-scroll");
            if($(item).hasClass("with-see-more")) {                
                let viewAll = gridContainer.find(config.viewAllBtn),
                    showMore = gridContainer.find(config.showMoreBtn),                                   
                    gridCountObj = gridDefaultWrapper.attr("data-grid-count"),
                    gridListRowCountObj = gridDefaultWrapper.attr("data-grid-row-count"),
                    dataGridRowDisplayObj = gridDefaultWrapper.attr("data-grid-row-display"),
                    isRowTrue = gridDefaultWrapper.attr("data-is-row"),
                    
                    gridCounts = jQuery.parseJSON(gridCountObj),
                    gridListRowCounts = jQuery.parseJSON(gridListRowCountObj),
                    dataGridRowDisplays = jQuery.parseJSON(dataGridRowDisplayObj),
                    initialGridWithRowCount = self.gridWithRow(gridCounts, dataGridRowDisplays),
                    initialdeviceGridCounts = self.deviceGridCountsFn(gridCounts),
                    moretext = gridContainer.find('.showMoreBtn .btn-cta-item').attr('data-text-collapse'),
                    lesstext = gridContainer.find('.showMoreBtn .btn-cta-item').attr('data-text-expand');

                let dataSeeMore = gridDefaultWrapper.attr("data-grid-see-more"),
                    initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);

                if (isRowTrue == "false") {
                    gridContainer.find(`li:lt(${initialdeviceGridCounts})`).show();
                    showMore.css("display", "block");
                    showMore.find('.btn-cta-item').click(e => {
                        if (!gridContainer.children(config.showMore).hasClass(config.activeClass)) {
                            const elm = gridContainer.find(`li:lt(${gridContentLength})`);
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
                    gridContainer.find(`li:lt(${initialGridWithRow})`).show();
                    showMore.css("display", "block");
                    showMore.find('.btn-cta-item').click(e => {
                        if (!gridContainer.children(config.showMore).hasClass(config.activeClass)) {
                            initialGridWithRow = (initialGridWithRow + parseInt(initialGridWithRowCount) <= gridContentLength) ? initialGridWithRow + parseInt(initialGridWithRowCount) : gridContentLength;
                            viewAll.show();
                            gridContainer.find(`.grid-default li:lt(${initialGridWithRow})`).show();
                            // ***********************
                            if (dataSeeMore != null) {
                                dataSeeMore--;
                                if (dataSeeMore == 0) {
                                    showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                                    showMore.addClass(config.activeClass);
                                    gridContainer.find(`.grid-default li:lt(${gridContentLength})`).show();
                                    initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
                                }
                            }
                            if (initialGridWithRow == gridContentLength) {
                                showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                                showMore.addClass(config.activeClass);   
                                initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
                            }
                        } else {
                            dataSeeMore = gridDefaultWrapper.attr("data-grid-see-more");
                            self.collaps(gridCounts, gridContainer, gridListRowCounts, isRowTrue, showMore, moretext);
                            viewAll.hide(); 
                        }

                    });
                }
                if (gridContentLength <= initialGridWithRow) {
                    showMore.hide();
                }
                //************************** */
            }
            if (isMobileScroll == "true") {
                if (config.xs_only.matches) {
                    gridContainer.children(config.gridDefault).find(`li:lt(${gridContentLength})`).show();
                    gridContainer.children(config.gridDefault).addClass("withSlider");
                    gridContainer.children('.withSlider').slick({
                        dots: true,
                        infinite: true,
                        speed: 300,
                        slidesToShow: 1,
                        adaptiveHeight: true
                    });
                    gridContainer.children('.showMoreBtn').hide();
                }
            }
        });
    }
    collaps(gridCounts, gridContainer, gridListRowCounts, isRowTrue, showMore, moretext) {
        const initialdeviceGridCounts = self.deviceGridCountsFn(gridCounts),
            initialGridWithRow = self.gridWithRow(gridCounts, gridListRowCounts);
        /************** */
        if (isRowTrue == "false") {
            gridContainer.find('.grid-default li').not(`:lt(${initialdeviceGridCounts})`).hide();
        } else {
            gridContainer.find('.grid-default li').not(`:lt(${initialGridWithRow})`).hide();
        }
        const scrollPos = gridContainer.find(config.gridDefault).offset().top;
        $(window).scrollTop(scrollPos);
        showMore.removeClass(config.activeClass);
        showMore.find('.btn-cta-item').text(moretext).removeClass(config.showLessActive).addClass(config.showMoreActive);   
    }
    gridWithRow(gridCounts, gridListRowCounts) {
        switch (true) {
            case config.xs_only.matches:
                config.gridRow = gridCounts.xs * gridListRowCounts.xs;
                break;
            case config.sm_only.matches:
                config.gridRow = gridCounts.sm * gridListRowCounts.sm;
                break;
            case config.md_only.matches:
                config.gridRow = gridCounts.md * gridListRowCounts.md;
                break;
            case config.lg_only.matches:
                config.gridRow = gridCounts.lg * gridListRowCounts.lg;
                break;
            default:
                config.gridRow = gridCounts.xl * gridListRowCounts.xl;
                break;
        }
        return config.gridRow;
    }
    deviceGridCountsFn(gridCounts) {
        switch (true) {
            case config.xs_only.matches:
                config.deviceGridCounts = gridCounts.xs;
                break;
            case config.sm_only.matches:
                config.deviceGridCounts = gridCounts.sm;
                break;
            case config.md_only.matches:
                config.deviceGridCounts = gridCounts.md;
                break;
            case config.lg_only.matches:
                config.deviceGridCounts = gridCounts.lg;
                break;
            default:
                config.deviceGridCounts = gridCounts.xl;
                break;
        }
        return config.deviceGridCounts;
    } 
    /***************** */
    render() {
        $(document).ready(function(){
            self.gridCount();
        })
    }
    init() {
        self = this;
        self.render();
        $(".grid-container .grid-content .ctaItem").each(function() {
            if($(this).html().trim() == "") {
                $(this).addClass("hide");
            }            
        });   
    }
}
let self;
window.global.gridContainerInstance = window.global.gridContainerInstance || new gridContainer();
window.global.gridContainerInstance.init();