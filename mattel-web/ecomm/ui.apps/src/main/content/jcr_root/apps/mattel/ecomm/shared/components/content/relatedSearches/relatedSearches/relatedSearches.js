/**
 * FP Related SEO Search.js
 * Version 1.0
 */
(function(global, PLAYAEM) {
    var fpRelatedSearch = {
        el: '.related-search',
        bindingEventsConfig: function() {
            var events = {
                      //  "click #selectAllBrands": "emailPrefSelectAllbrands",
            }
            return events;
        },
        apiConfig: function (name) {
            var obj = {
    
                "SearchnPromote": { 
                    "url": 'https://sp1004f9de.guided.ss-omtrdc.net/', 
                    "body": "",
                    "type": "get",                    
                }
            }
            return obj[name];
        },
        ajaxCall: function (obj, cb) {
            $.ajax({
                url: obj.url,
                data: obj.body || '',
                type: obj.type,
                headers:obj.headers,
                beforeSend: function (xhr) {
                    if (obj.beforeSend) {
                        xhr.setRequestHeader('Authorization', obj.beforeSend);
                    }
                },
                success: function (data) {
                    if (typeof cb == "function") cb(data);
                },
                error: function (xhr, textStatus, errorThrown) {
                    if (typeof cb == "function") cb(false, xhr.responseJSON || errorThrown);
                }
            });
        },
      
        loadData:function(){
           this.ajaxCall(this.apiConfig("SearchnPromote"), function(res, err) {
               var result = res.resultsets[0].results;
               $.each(result, function(i){
              $('#relatedSearch').append('<li><a href="'+result[i].userManualLink+'">'+result[i].subcategory+'</a></li>');     
              })

               
           });
           },

        render: function() { 
            this.loadData();
        },

        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    
    fpRelatedSearch.init();
    PLAYAEM.fpRelatedSearch = fpRelatedSearch;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            fpRelatedSearch.init();
        }
        /* fpRelatedSearch.textTruncate();
        fpRelatedSearch.tooltipWidth(); */
    }, false);
}(window, PLAYAEM));
