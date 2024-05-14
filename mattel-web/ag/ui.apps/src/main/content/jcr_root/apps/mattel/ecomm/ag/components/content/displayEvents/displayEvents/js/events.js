/**
 * events.js
 * Version 1.0
 */
//  Handlebars compile cmd ---> handlebars events.tmpl -f events.tmpl.js
(function (global, AGAEM, apiConfig) {
    var events = {
        el: '.events-module',
        bindingEventsConfig: function () {
            var events = {
                "click .events-update-action": "updateUpcomingEventsContainer",
                "click .events-module .see-more-data": "showScheduleDescription"
            };
            return events;
        },
        showScheduleDescription : function (ele, evt) {
            var parent = $(ele).parent().parent();
            var html = $(parent).find(".schedule-description").html();//"<p>"+ $(".events-module .schedule-description").eq(index).html() +"</p>";
            $("#seeMoreEventInfo .modal-body").html(html);
            $("#seeMoreEventInfo").modal("show");
            evt.preventDefault();
        },
        updateUpcomingEventsContainer: function (ele, evt) {
            var $targetEle = $('#upcoming-events'),
                textMore = $(ele).data('textMore'),
                textLess = $(ele).data('textLess'),
                dataCount = events.eventsResponseCnt,
                dataLoadCnt = $targetEle.data('load'),
                dataLoadedCnt = parseInt($targetEle.attr('data-loaded') || dataLoadCnt),
                dataNextLoad = $targetEle.data('nextLoad'),
                isCompleted = $targetEle.hasClass('data-loaded'),i;

            if (dataNextLoad == undefined || dataNextLoad == -1) {
                dataNextLoad = dataCount - dataLoadCnt;
            }
            if (!isCompleted) {
                for (i = dataLoadedCnt; i < (dataLoadedCnt + dataNextLoad); i++) {
                    $targetEle.find('>div:eq(' + (i) + ')').show();
                    $targetEle.find('>div:eq(' + (dataLoadedCnt) + ')').attr('tabindex', '-1').focus();
                }
                $targetEle.attr('data-loaded', (dataLoadedCnt + dataNextLoad));
                if (dataCount <= (dataLoadedCnt + dataNextLoad)) {
                    console.log("Data Loaded");
                    $targetEle.addClass("data-loaded");
                    $(ele).text(textLess);
                }
            } else {
                for (i = dataLoadedCnt - 1; i >= (dataLoadedCnt - dataNextLoad); i--) {
                    $targetEle.find('>div:eq(' + (i) + ')').hide();
                    $targetEle.find('>div:eq(' + (i) + ')').removeAttr('tabindex');
                }
                $targetEle.attr('data-loaded', (dataLoadedCnt - dataNextLoad));
                if (dataLoadCnt == (dataLoadedCnt - dataNextLoad)) {
                    $targetEle.removeClass("data-loaded");
                    $(ele).text(textMore);
                }
            }
        },
        renderEventsDateFromAPI: function (ele) {
            var ajaxSettings = apiConfig['events'];
            AGAEM.requestAPICall(ajaxSettings, function (response) {
                if (!response) {
                    console.log("Err : Upcoming Events API failed..");
                    return false;
                }
                var template = Handlebars.templates['upcoming-events.tmpl'];
                events.eventsResponseCnt = response.length;
                context = {
                    items: response
                }
                if (response.length > 2) $('.events-update-action').removeClass('hide')
                ele.html(template(context)).removeClass('loading');
            });
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.events) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            var $upcomingEventsElem = $(this.el).find("#upcoming-events");
            if ($upcomingEventsElem.length) this.renderEventsDateFromAPI($upcomingEventsElem);
        }
    }
    events.init();
    AGAEM.events = events;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            events.init();
        }
    }, false);
}(window, AGAEM, AGAEM.apiConfig));