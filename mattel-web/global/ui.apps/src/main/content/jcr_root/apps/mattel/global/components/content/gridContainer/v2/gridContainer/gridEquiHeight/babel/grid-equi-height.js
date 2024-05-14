const getDeviceName = window.global.deviceName;
const config = {
    deviceName: typeof getDeviceName == "function" ? getDeviceName() : "desktop",
    gridCol: (el) => {
        return {
            desktop: $(el).hasClass("grid-xl-2") ? 2 : ($(el).hasClass("grid-xl-3") ? 3 : ($(el).hasClass("grid-xl-4") ? 4 : 1)),
            tablet: $(el).hasClass("grid-lg-2") ? 2 : ($(el).hasClass("grid-lg-3") ? 3 : ($(el).hasClass("grid-lg-4") ? 4 : 1)),
            tabletPortrait: $(el).hasClass("grid-md-2") ? 2 : ($(el).hasClass("grid-md-3") ? 3 : ($(el).hasClass("grid-md-4") ? 4 : 1)),
            mobile: $(el).hasClass("grid-sm-2") ? 2 : ($(el).hasClass("grid-sm-3") ? 3 : ($(el).hasClass("grid-sm-4") ? 4 : 1)),
            mobilePortrait: $(el).hasClass("grid-xs-2") ? 2 : ($(el).hasClass("grid-xs-3") ? 3 : ($(el).hasClass("grid-xs-4") ? 4 : 1)),
        }
    },
}

const gridContainer = (browserResize) => {
    const elem = browserResize ? $(".gridContainer .grid-container ul.grid-default") : $(".gridContainer .grid-container ul.grid-default").filter(":not(.loaded)");
    if (!elem.length) return;
    _.each(elem, (item) => {
        let gridCols = config.gridCol(item);
        getColHeight(item, gridCols, browserResize);
        $(item).addClass("loaded");
    });
};

const bindingResize = () => {
    let resizedDeviceName;
    if (!$(".gridContainer").length) return;
    $(window).resize(
        _.debounce(() => {
            resizedDeviceName = getDeviceName();
            if (config.deviceName != resizedDeviceName) {
                config.deviceName = resizedDeviceName;
                gridContainer(true);
                const resizeBrowser = false;
            }
        }, 500)
    );
};

const getColHeight = (elem, col, triggerResize) => {
    if (config.deviceName == "mobilePortrait" && !triggerResize) return;
    const gridColumn = col[`${config.deviceName}`];
    const $gridItem = triggerResize ? $(elem).find('.grid-content') : $(elem).find('.grid-content:not(.loaded)');
    const totalGrid = $gridItem.length;
    if (!totalGrid) return;
    const isImageExist = $gridItem.find('img').length;
    let totalRows = Math.ceil(totalGrid / gridColumn),
        maxHeight, rowEle, applyChildEle;
    $gridItem.addClass('loaded');
    for (let index = 0; index < totalRows; index++) {
        rowEle = $gridItem.slice((index * gridColumn), (gridColumn + (index * gridColumn)));
        if (isImageExist) {
            isGridImageLoaded(rowEle, index, (res) => {
                applyMaxHeight(res, gridColumn);
            });
        } else {
            applyMaxHeight(rowEle, gridColumn);
        }
    }
};

const isGridImageLoaded = (elem, indx, cb) => {
    let $imgEle = $(elem).find('img');
    if (!$imgEle.length) {
        cb(false);
        return;
    }
    $imgEle.imagesLoaded().done(() => {
        cb($(elem), indx);
    });
};

const applyMaxHeight = (elem, gridColumn) => {
    let applyChildEle = elem.find('.desktop-bg').length ? elem.find('.desktop-bg') : elem,
        maxHeight;
    applyChildEle.css('min-height', "auto");
    if (gridColumn > 1) {
        maxHeight = Math.max.apply(null, elem.map(function() {
            return $(this).height();
        }).get());
        applyChildEle.css('min-height', maxHeight);
    }
};

gridContainer();
bindingResize();

document.addEventListener('DOMContentLoaded', function() {
    gridContainer()
}, false);