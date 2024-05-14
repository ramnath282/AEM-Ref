/**
 * config.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var currentPageName = $('#currentPageTag').val();
    var pagePath = $('#resourcePath').val();
    var urlPath =$('#resourcePath').length >0 ?  pagePath.split("/").join("!") +".json": "";
    var config = {
        "events": {
            "url": "//"+window.location.host+"/bin/getevents?currentPageName="+currentPageName,
            "type": "get",
            "params": function () {

            }
        },
        "articles": {
            "url": "//"+window.location.host+"/bin/getrelatedarticles."+urlPath,
            "type": "get"
        },
        "featuredGrid": {
            "url": "//" + window.location.host + "/public/src/brands/ag/explore-datas.json",
            "type": "get",
            "params": ""
        },
        
        "deleteEvent": {
            "url": "//" + window.location.host + "/bin/events.delete.json",
            "type": "post",
            "params": ""
        },
        
        "loadCities": {
            "url": "//" + window.location.host + "/bin/events.locations.json",
            "type": "get",
            "params": ""
        },
        
        "createEvent": {
            "url": "//" + window.location.host + "/bin/events.eventid.json",
            "type": "get",
            "params": ""
        },
        
        "saveEvent": {
            "url": "//" + window.location.host + "/bin/events.create.json",
            "type": "post",
            "params": ""
        },
        
        "updateEvent": {
            "url": "//" + window.location.host + "/bin/events.datatoupdate.json",
            "type": "post",
            "params": ""
        },
        
        "setEvent": {
            "url": "//" + window.location.host + "/bin/events.update.json",
            "type": "post",
            "params": ""
        },
        
        "searchEvent": {
            "url": "//" + window.location.host + "/bin/getsearchevents",
            "type": "get",
            "params": ""
        }
       
    };
    AGAEM.apiConfig = config;
}(window, AGAEM));