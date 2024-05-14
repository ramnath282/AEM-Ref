const getDeviceName = window.global.deviceName;
const config = {
    deviceName: typeof getDeviceName == "function" ? getDeviceName() : "desktop",
    gridCol: (el) => {
        return {
            desktop: 2,
            tablet: 2,
            tabletPortrait: 1,
            mobile: 1,
            mobilePortrait: 1,
        }
    },
}

const colCtrlGridContainer = (browserResize) => {
    const elem = browserResize ? $(".columnControl .col-2") : $(".columnControl .col-2").filter(":not(.loaded)");
    if (!elem.length) return;
    _.each(elem, (item) => {
        let gridCols = config.gridCol(item);
        colCtrlGetColHeight(item, gridCols, browserResize);
        $(item).addClass("loaded");
    });
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

const colCtrBindingResize = () => {
    let colCtrlResizedDeviceName;
    if (!$(".columnControl .col-2").length) return;
    $(window).resize(
        _.debounce(() => {
            colCtrlResizedDeviceName = getDeviceName();
            if (config.deviceName != colCtrlResizedDeviceName) {
                config.deviceName = colCtrlResizedDeviceName;
                colCtrlGridContainer(true);
                const resizeBrowser = false;
            }
        }, 500)
    );
};

const applyMaxHeight = (elem, gridColumnEqHt) => {
    let applyChildEle = elem.find(".desktop-bg").length ? elem.find(".desktop-bg") : elem,
        maxHeight;
    applyChildEle.css('height', "auto");
    if (gridColumnEqHt > 1) {
        maxHeight = Math.max.apply(null, elem.map(function() {
            return $(this).height();
        }).get());
        applyChildEle.css('height', maxHeight);
    }
};

const colCtrlGetColHeight = (elem, col, triggerResize) => {
    if (config.deviceName == "mobilePortrait" && !triggerResize) return;
    const $gridItem = triggerResize ? $(elem).find('>div') : $(elem).find('>div:not(.loaded)');
    const gridColumnEqHt = col[`${config.deviceName}`];
    const totalGridEqHt = $gridItem.length;
    if (!totalGridEqHt) return;
    const isImageExist = $gridItem.find('img').length;
    let totalRows = Math.ceil(totalGridEqHt / gridColumnEqHt),
        maxHeight, rowEle, applyChildEle;
    $gridItem.addClass('loaded');
    for (let index = 0; index < totalRows; index++) {
        rowEle = $gridItem.slice((index * gridColumnEqHt), (gridColumnEqHt + (index * gridColumnEqHt)));
        if (isImageExist) {
            isGridImageLoaded(rowEle, index, (res) => {
                applyMaxHeight(res, gridColumnEqHt);
            });
        } else {
            applyMaxHeight(rowEle, gridColumnEqHt);
        }
    }
};

colCtrlGridContainer();
colCtrBindingResize();

document.addEventListener('DOMContentLoaded', function() {
    colCtrlGridContainer()
}, false);