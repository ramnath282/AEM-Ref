const config = {
    el: '.list',
    lg_only: window.matchMedia('(min-width: 1024px) and (max-width: 1199px)'),
    md_only: window.matchMedia('(min-width: 768px) and (max-width: 1023px)'),
    sm_only: window.matchMedia('(min-width: 480px) and (max-width: 767px)'),
    xs_only: window.matchMedia('(min-width: 320px) and (max-width: 479px)'),
    listRow: 0,
    deviceListCounts: 0,
    listDefault: '.list-default',
    showMoreBtn: '.showMoreBtn',
    showLessBtn: '.active',
    activeClass: 'active',
    viewAllBtn: '.viewAllBtn',
    showMoreActive: 'show-more-active',
    showLessActive: 'show-less-active',
}
class listContainer {
    constructor() {
        self = this;
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
    }
    bindingEventsConfig() {
        const events = {};
        return events;
    }
    listCountFn(ele, evt) {
        $(".list-container").each(function(index, item) {
            let listContainer = $(item),
            listDefaulttWrapper = listContainer.find(config.listDefault),
            listContentLength = listDefaulttWrapper.children(`li`).length,
            isMobileScroll = listDefaulttWrapper.attr("data-is-mobile-scroll");
            if($(item).parent().hasClass("with-see-more")) {                     
                let viewAll = listContainer.find(config.viewAllBtn),
                    showMore = listContainer.find(config.showMoreBtn),                    
                    listCountObj = listDefaulttWrapper.attr("data-list-count"),
                    listRowCountObj = listDefaulttWrapper.attr("data-list-row-count"),
                    dataListRowDisplayObj = listDefaulttWrapper.attr("data-list-row-display"),
                    isRowTrueList = listDefaulttWrapper.attr("data-is-row-list"),                    
                    listCounts = jQuery.parseJSON(listCountObj),
                    listRowCounts = jQuery.parseJSON(listRowCountObj),
                    dataListRowDisplays = jQuery.parseJSON(dataListRowDisplayObj),
                    initialListWithRowCount = self.ListWithRow(listCounts, dataListRowDisplays),
                    initialdeviceListCounts = self.deviceListCountsFn(listCounts),
                    moretext = listContainer.find('.showMoreBtn .btn-cta-item').attr('data-text-collapse'),
                    lesstext = listContainer.find('.showMoreBtn .btn-cta-item').attr('data-text-expand');

                let dataSeeMore = listDefaulttWrapper.attr("data-list-see-more"),
                    initialListWithRow = self.ListWithRow(listCounts, listRowCounts);

                if (isRowTrueList == "false") {
                    listContainer.find(`li:lt(${initialdeviceListCounts})`).show();
                    showMore.css("display", "block");
                    showMore.find('.btn-cta-item').click(e => {
                        (typeof showMoreLessClick === 'function')?showMoreLessClick(this):'';
                        if (!listContainer.children(config.showMore).hasClass(config.activeClass)) {
                            const elm = listContainer.find(`.list-default li:lt(${listContentLength})`);
                            elm.show();
                            viewAll.show();
                            showMore.addClass(config.activeClass);
                            showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                        } else {
                            self.collaps(listCounts, listContainer, listRowCounts, isRowTrueList, showMore, moretext);
                            viewAll.hide();
                        }
                    });
                } else {
                    listContainer.find(`li:lt(${initialListWithRow})`).show();
                    showMore.css("display", "block");
                    showMore.find('.btn-cta-item').click(e => {
                        (typeof showMoreLessClick === 'function')?showMoreLessClick(this):'';
                        if (!listContainer.children(config.showMore).hasClass(config.activeClass)) {
                            initialListWithRow = (initialListWithRow + parseInt(initialListWithRowCount) <= listContentLength) ? initialListWithRow + parseInt(initialListWithRowCount) : listContentLength;
                            const showMoreFocus = initialListWithRow - initialListWithRowCount + 1;
                            viewAll.show();
                            listContainer.find(`.list-default li:lt(${initialListWithRow})`).show();
                            listContainer.find(`.list-default li:nth-child(${showMoreFocus}) a`).focus();
                            if (initialListWithRow == listContentLength) {
                                showMore.addClass(config.activeClass);
                                showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                                initialListWithRow = self.ListWithRow(listCounts, listRowCounts);
                            }
                            if (dataSeeMore != null) {
                                dataSeeMore--;
                                if (dataSeeMore == 0) {
                                    showMore.addClass(config.activeClass);
                                    showMore.find('.btn-cta-item').text(lesstext).addClass(config.showLessActive).removeClass(config.showMoreActive);
                                    listContainer.find(`.list-default li:lt(${listContentLength})`).show();
                                    initialListWithRow = self.ListWithRow(listCounts, listRowCounts);
                                }
                            }
                        } else {
                            self.collaps(listCounts, listContainer, listRowCounts, isRowTrueList, showMore, moretext);
                            viewAll.hide();
                            dataSeeMore = listDefaulttWrapper.attr("data-list-see-more");
                        }
                    });
                }
                if (listContentLength <= initialListWithRow) {
                    showMore.hide();
                }
            }
            if (isMobileScroll == "true") {
                if (config.xs_only.matches) {
                    listContainer.find(`li:lt(${listContentLength})`).show();
                    listContainer.find(config.listDefault).addClass("withSlider");
                    listContainer.find('.withSlider').slick({
                        dots: true,
                        infinite: true,
                        speed: 300,
                        slidesToShow: 1,
                        adaptiveHeight: true
                    });
                    listContainer.children('.showMoreBtn').hide();
                }
            }
        });
    }
    collaps(listCounts, listContainer, listRowCounts, isRowTrueList, showMore, moretext) {
        const initialdeviceListCounts = self.deviceListCountsFn(listCounts),
            initialListWithRow = self.ListWithRow(listCounts, listRowCounts);
        if (isRowTrueList == "false") {
            listContainer.find('.list-default li').not(`:lt(${initialdeviceListCounts})`).hide();
        } else {
            listContainer.find('.list-default li').not(`:lt(${initialListWithRow})`).hide();
        }
        showMore.removeClass(config.activeClass);
        showMore.find('.btn-cta-item').text(moretext).removeClass(config.showLessActive).addClass(config.showMoreActive);
        const scrollPos = listContainer.find(config.listDefault).offset().top;
        $(window).scrollTop(scrollPos);
    }
    deviceListCountsFn(listCounts) {
        switch (true) {
            case config.lg_only.matches:
                config.deviceListCounts = listCounts.lg;
                break;
            case config.md_only.matches:
                config.deviceListCounts = listCounts.md;
                break;
            case config.sm_only.matches:
                config.deviceListCounts = listCounts.sm;
                break;
            case config.xs_only.matches:
                config.deviceListCounts = listCounts.xs;
                break;
            default:
                config.deviceListCounts = listCounts.xl;
                break;
        }
        return config.deviceListCounts;
    }
    ListWithRow(listCounts, listRowCounts) {
        switch (true) {
            case config.lg_only.matches:
                config.listRow = listCounts.lg * listRowCounts.lg;
                break;
            case config.md_only.matches:
                config.listRow = listCounts.md * listRowCounts.md;
                break;
            case config.sm_only.matches:
                config.listRow = listCounts.sm * listRowCounts.sm;
                break;
            case config.xs_only.matches:
                config.listRow = listCounts.xs * listRowCounts.xs;
                break;
            default:
                config.listRow = listCounts.xl * listRowCounts.xl;
                break;
        }
        return config.listRow;
    }
    render() {
        this.listCountFn();
    }
    init() {
        self = this;
        $(document).ready(function(){
            self.render();
        });
    }
}
let self;
window.global.listContainerInstance = window.global.listContainerInstance || new listContainer();
window.global.listContainerInstance.init();