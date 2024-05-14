((() => {
    const config = {
        el: ".cardsContainer",
        twoImageEl: ".flexible-grid-component.two-image-grid",
        rowClasses: ["grid-column-one", "grid-column-two", "grid-column-three", "grid-column-four", "grid-column-five", "grid-column-six"],
        mdRow: 2,
        slideToShow: {
            "tablet": 2,
            "tablet-landscape": 2,
            "mobile": 1
        }
    }

    const getDeviceName = () => {
        let deviceName;
        if (window.innerWidth <= 767) {
            deviceName = "mobile"
        } else if (window.innerWidth <= 990) {
            deviceName = "tablet"
        } else if (window.innerWidth <= 1025) {
            deviceName = "tablet-landscape"
        } else if (window.innerWidth <= 1400) {
            deviceName = "desktop"
        } else {
            deviceName = "desktop-landscape"
        }
        return deviceName;
    }

    const equalColumnHeight = elem => {
        let rowCount, gridEle, totalRows, rowEle, headingEle, descEle, isTwoImageGrid;
        let contentHeight, descriptionHeight, headingHeight, maxHeight;
        let $ele = elem.not('.loaded');
        elem.addClass('loaded');
        _.each($ele, val => {
            gridEle = $(val).find('.flexible-grid-component>.inner-section ul>li:not(.active)');
            isTwoImageGrid = $(val).find(config.twoImageEl).find('.slide-content .content');
            rowCount = getRowValue(val) || 0;
            if (rowCount && gridEle.length > 1) {
                totalRows = Math.ceil(gridEle.length / rowCount);
                for (let index = 0; index < totalRows; index++) {
                    isGridImageLoaded(gridEle, index, rowCount, isTwoImageGrid, (rowEle, twoImageGrid) => {
                        headingEle = rowEle.find(".content h2").length ? rowEle.find(".content h2") : rowEle.find(".content h3");
                        descEle = rowEle.find(".content p").length ? rowEle.find(".content p") : '';
                        $(rowEle).css('height', 'auto');
                        $(descEle).css('height', 'auto');
                        $(headingEle).css('height', 'auto');
                        headingHeight = headingEle.length ? Math.max.apply(null, headingEle.map(function () { return $(this).height(); }).get()) : 0;
                        descriptionHeight = descEle.length ? Math.max.apply(null, descEle.map(function () { return $(this).height(); }).get()) : 0;
                        maxHeight = rowEle.length ? Math.max.apply(null, rowEle.map(function () { return $(this).height(); }).get()) : 0;
                        headingEle.length && headingEle.css('height', headingHeight);
                        descEle.length && descEle.css('height', descriptionHeight);
                        if (twoImageGrid.length) {
                            rowEle.find(".slide-content .content").css('height', 'auto');
                            contentHeight = Math.max.apply(null, rowEle.find(".slide-content .content").map(function () { return $(this).outerHeight(); }).get());
                            rowEle.find(".slide-content .content").css('height', contentHeight);
                        }
                        rowEle.css('height', maxHeight);
                    })
                }
            }
        });
    };
    const isGridImageLoaded = (elem, index, rowCount, isTwoImageGrid, cb) => {
        let rowEle = elem.slice((index * rowCount), (rowCount + (index * rowCount)));
        let $imgEle = $(elem).find('img');
        if (!$imgEle.length) {
            cb(rowEle, isTwoImageGrid);
            return;
        }
        $imgEle.imagesLoaded().done(() => {
            cb(rowEle, isTwoImageGrid);
        });
    };
    const getRowValue = elem => {
        if (config.deviceName == "desktop" || config.deviceName == "desktop-landscape") {
            let matchEle;
            let indexValue = 0;
            config.rowClasses.forEach((element, key) => {
                matchEle = $(elem).hasClass(element);
                if (matchEle) {
                    indexValue = key + 1;
                }
            });
            return indexValue;
        }
        return config.mdRow;
    };

    const bindingResize = () => {
        let resizedDeviceName;
        $(window).resize(
            _.debounce(() => {
                resizedDeviceName = getDeviceName();
                // if (config.deviceName != resizedDeviceName) {
                config.deviceName = resizedDeviceName;
                if (config.deviceName != "mobile") {
                    equalColumnHeight($(config.el).removeClass('loaded'));
                    // equalTitleHeight($(config.el).removeClass('content-loaded'));
                }
                // }
            })
        );
    }
    const applySlider = (elem) => {
        let $ele = $(elem);
        if (!$ele.length) {
            return;
        }
        let setting = {
            dots: true,
            arrows: false,
            infinite: false,
            speed: 300,
            slidesToShow: config.slideToShow[config.deviceName] || 1,
            slidesToScroll: 1,
            centerMode: false,
            adaptiveHeight: false,
            autoplay: false,
            autoplaySpeed: 2000,
            variableWidth: false,
            responsive: true
        };
        $ele.imagesLoaded()
            .done((instance, image) => {
                $ele.addClass("slider-loaded").slick(setting);
            });
    }
    const applyMaxWidthToGrid = (elem) => {
        const $ele = $(elem);
        if (!$ele.length) {
            return;
        }
        _.each($ele, (item) => {
            $(item).find('img').imagesLoaded().done((instance) => {
                let imageWidth = instance.images.length && instance.images[0].img.width;
                imageWidth && $(item).next('.content').css('max-width', imageWidth);
            });
        });
    }
    const postReadyCall = () => {
        let $sliderElement = $(config.el).find('[data-xs-slider=true] .inner-section>ul:not(.slider-loaded)');
        let $marketingElement = $(config.el).find(".cardsContainer");
        let $marketingGrid;
        if ($marketingElement.length) {
            $marketingGrid = $marketingElement.find(".flexible-grid-component>.inner-section ul li .ctaGroupComponent");
            $marketingGrid.length && _.each($marketingGrid, function (item) {
                $(item).parent("li").addClass("marketing-text");
            })
        }
        if (config.deviceName != "mobile") {
            equalColumnHeight($(config.el));
            // equalTitleHeight($(config.el));
        }
        if ((config.deviceName == "mobile" || config.deviceName == "tablet" || config.deviceName == "tablet-landscape") && $sliderElement.length) {
            _.each($sliderElement, (item) => {
                applySlider($(item));
            })
        }
        if (config.deviceName == "desktop-landscape") {
            applyMaxWidthToGrid(".cardsContainer.grid-column-two .flexible-grid-component .thumbnail-image");
        }
    }
    const init = () => {
        let $ele = $(config.el);
        if (!$ele.length) {
            return;
        }
        config.deviceName = getDeviceName();
        if (config.deviceName != "mobile") {
            bindingResize();
        }
        postReadyCall();
    };

    init();

    $(() => {
        postReadyCall();
    });
})());