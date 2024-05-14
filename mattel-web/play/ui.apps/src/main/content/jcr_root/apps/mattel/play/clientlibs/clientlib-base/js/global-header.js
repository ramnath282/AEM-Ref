/**
 * global header.js
 * Version 1.0
 */
(function(global, PLAYAEM) {
    var globalHeader = {
        el: '.global-header',
        bindingEventsConfig: function() {
            var events = {
                "click .gblhdr-list a": "listItemCount",
            }
            return events;
        },
        listItemCount: function(el, evt) {
            
            var listItemCount = $('.gblhdr-list').data('size'); // count of items to be displayed in the dropdown
            var maxcount =  $('.gblhdr-list-item li').length;// maximum items present in the dropdown
            var liHeight = $('.gblhdr-list-item li').height();
            var visibleli = liHeight * listItemCount;
            $('.gblhdr-list-item').css('height', visibleli + 10);

            if (listItemCount < maxcount) {
                $('.gblhdr-list-item').css('overflow-y', 'scroll');
            } else if (listItemCount > maxcount) {
                $('.gblhdr-list-item').css('height', liHeight * maxcount + 10);
                $('.gblhdr-list-item').css('overflow-y', 'none');
            }
        },

        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.globalHeader) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);

        }
    }
    globalHeader.init();
    PLAYAEM.globalHeader = globalHeader;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            globalHeader.init();
        }
    }, false);
}(window, PLAYAEM));