/**
 * events-management.js
 * Version 1.0
 */

(function (global, AGAEM, apiConfig) {
    var eventsManagement = {
        el: '.event-management-container',
        locationCount: 1,
        dateCount: 1,
        createJson: "",
        citiesArr: "",
        bindingEventsConfig: function () {
            var events = {
                "click .event-management-container .delete-event": "deleteEvent",
                "click .event-management-container .update-event": "updateEvent",
                "click .event-management-container #addStore": "loadLocation",
                "click .event-management-container .add-date": "addDate",
                "click .event-management-container .delete-date": "deleteDate",
                "click .event-management-container .delete-location": "deleteLocation",
                "click .event-management-container .toggle-label": "toggleElement",
                "click .event-management-container .search-events": "displayEvents",
                "click .event-management-container .custom-pricing-option-val": "customPricing",
                "click .event-management-container .custom-pricing-option": "customPricingFocus",
                "click .event-management-container .calender-icon": "calendarFocus",
                "click .event-management-container #successConfirmOk": "closeSuccessModal",
                "click .event-management-container #cancelEvent": "resetTool",
                "click .event-management-container #saveEvent": "submitEvent"
            };
            return events;
        },
        calendarFocus : function(ele,eve) {
            $(ele).prev('.datepicker').focus();
        },
        customPricing : function(ele,eve) {
            $(ele).prev('.custom-pricing-option').trigger("click");
        },
        customPricingFocus : function(ele,eve) {
            $(ele).next('.custom-pricing-option-val').focus();
        },
        closeSuccessModal : function() {
            var self = AGAEM.eventsManagement;
            $(self.el).find('#SuccessModal').modal("hide");
        },
        resetTool : function(ele,eve) {
            var self = AGAEM.eventsManagement;
            $(self.el).find('#SuccessModal').modal('hide');
            $(self.el).find(".admin-event-tab").trigger("click");
            $(self.el).find(".create-event-tab").text("Create Event");
            $(self.el).find("#saveEvent").val("Save Event");
            self.locationCount = 1;
            self.dateCount = 1;
            $(self.el).find(".location-wrapper").empty();
            self.loadEventTemplate(self.createJson);
            $(self.el).find(".search-events-val").val("");
            $(self.el).find(".events-list-load").empty();
            // self.loadTemplateData(".events-list-load","#displayEventsScript","replace", {items: []});
            window.scrollTo(0, 0);
        },
        toggleElement : function(ele, eve) {
            var self = AGAEM.eventsManagement;
            var index = $(self.el).find(".toggle-label").index(ele);
            $(ele).toggleClass("active",1000);
            $(self.el).find(".collapsible").eq(index).toggleClass("hide",1000);
        },
        updateEvent : function(ele,evt) {
            var self = AGAEM.eventsManagement;
            var ajaxSettings = $.extend({}, apiConfig['updateEvent']);
            ajaxSettings.body = {"eventId": $(ele).attr("data-event")}
            $(self.el).find(".create-event-form-wrapper").addClass("data-loading");
            self.requestAPICall(ajaxSettings, function (response) {
                if (!response) {
                    console.log("Err :   Events API failed..");
                    return false;
                }
                $(self.el).find(".create-event-tab").trigger("click").text("Update Event");
                $(self.el).find("#saveEvent").val("Update Event");
                self.locationCount = 1;
                self.dateCount = 1;
                $(self.el).find(".location-wrapper").empty();
                self.loadEventTemplate(response);
                $(self.el).find(".create-event-form-wrapper").removeClass("data-loading");
            });
        },
        deleteLocation : function(ele,evt) {
            var self = AGAEM.eventsManagement;
            var index = $(self.el).find(".delete-location").index(evt.currentTarget);
            $(self.el).find(".location-details").eq(index).remove();
            evt.preventDefault();
        },
        deleteDate : function(ele,evt) {
            var self = AGAEM.eventsManagement;
            var index = $(self.el).find(".delete-date").index(evt.currentTarget);
            $(self.el).find(".date-details").eq(index).remove();
            evt.preventDefault();
        },
        addDate : function(ele,evt) {
            var self = AGAEM.eventsManagement;
            var index =  $(self.el).find(".add-date").index(evt.currentTarget);
            var target =  $(self.el).find(".time-fields-wrapper").eq(index);
            self.loadTemplateData(target,"#dateTimeTemplate", "append", {"item": {"id": self.dateCount}, "dateData": self.createJson.locationDetails[0].locationDateDetails[0]});
            self.dateCount = self.dateCount+1;
            evt.preventDefault();
        },
        loadLocation : function(ele,eve,data) {
            var self = AGAEM.eventsManagement;
            var _this = this;
            if(self) {
                _this = self;
            }
            var tmplData = "";
            data ? tmplData = data : tmplData = self.createJson.locationDetails;
            $.each(tmplData, function(k,v){
                _this.loadTemplateData($(self.el).find(".location-wrapper"),"#locationTemplate", "append", {"item": {"id":_this.locationCount}, "tmplData": v, "cities": _this.citiesArr});
                var locCount = $(self.el).find(".location-details").length;
                $.each(v.locationDateDetails, function(k1,v1){
                    self.loadTemplateData($(self.el).find(".time-fields-wrapper").eq(locCount-1),"#dateTimeTemplate", "append", {"item": {"id": _this.dateCount}, "dateData":v1});
                    self.dateCount = self.dateCount+1;
                });
                _this.locationCount = _this.locationCount+1;
            });
        },
        loadTemplateData : function (target, template, action, data){
            var elementTemplate = _.template($(template).html());
            if(action=="append") {
                $(target).append(elementTemplate(data));
            } else {
                $(target).html(elementTemplate(data));
            }
           this.loadDatePicker();
        },
        deleteEvent: function(ele,evt){
            var self = AGAEM.eventsManagement;
            var ajaxSettings = $.extend({}, apiConfig['deleteEvent']);
            var eventNodePath = $(ele).attr("data-event");
            self.confirmDialog(function(){
                ajaxSettings.body = {"eventNodePath":eventNodePath};
                self.requestAPICall(ajaxSettings, function (response) {
                    if (!response) {
                        console.log("Err :   Events API failed..");
                        $(self.el).find("#deleteSuccess .modal-title").html("Fail!");
                        $(self.el).find("#deleteSuccess .modal-body").html("<p>Something went wrong, Please try again later!</p>");
                        return false;
                    } else {
                        $(self.el).find("#deleteSuccess .modal-title").html("Success!");
                        $(self.el).find("#deleteSuccess .modal-body").html(response.deleteResponce);
                        $(ele).parents("tr").remove();
                        evt.preventDefault();
                    }
                    $(self.el).find('#deleteSuccess').modal('show');
                });
            });
        },
        confirmDialog: function(onConfirm){
            var self = this;
            var fClose = function(){
                modal.modal("hide");
            };
            var modal = $(self.el).find("#confirmModal");
            modal.modal("show");
            $(self.el).find("#confirmOk").unbind().one('click', onConfirm).one('click', fClose);
            $(self.el).find("#confirmCancel").unbind().one("click", fClose);
        },
        createEvent : function() {
            var self = this;
            var ajaxSettings = $.extend({}, apiConfig['createEvent']);
            self.requestAPICall(ajaxSettings, function (response) {
                if (!response) {
                    console.log("Err :   Events API failed..");
                    return false;
                }
                self.createJson = response;
                self.loadEventTemplate(self.createJson);
            });
        },
        loadEventTemplate : function(data) {
            var self = this;
            $(self.el).find(".primary-input-val").each(function(){
                var type = $(this).attr("type");
                var key = $(this).attr("data-key");
                if(type == "text" || type == "number") {
                   $(this).val(data[key]);
                } else if(type == "checkbox") {
                    data[key] == "true" ? $(this).attr("checked",true) : $(this).removeAttr("checked");
                } else {
                    $(this).val(data[key]);
                }
            });
            self.loadLocation("","",data.locationDetails);
        },
        requestAPICall: function (obj, cb) {
            var ajaxSettings = {
                type: obj.type,
                url: obj.url,
                contentType: 'application/json',
                data: obj.body ? JSON.stringify(obj.body) : '',
                success: function (response) {
                    if(typeof cb == "function") {
                        cb(response)
                    }
                },
                error: function (errrLog) {
                    if (typeof cb == 'function') {
                        cb(false);
                        console.log(obj.methodName + "API Error!", "error");
                    }
                }
            }

            if(obj.type.toLowerCase() == "get" ){
                ajaxSettings.cache = false;
            }
            /*var self = this;*/
            return $.ajax(ajaxSettings);
        },
        displayEvents: function(){
            var self = AGAEM.eventsManagement;
            var searchObj = {};
            $(self.el).find(".search-events-val").each(function(){
                searchObj = self.getInputValues(this,searchObj);
            });
            var params = "", emptyCnt = 0;
            $.each(searchObj,function(k1,v1){
                if(v1.trim() != "" && v1.trim() != "Select City") {
                    if(params==""){
                        params = k1+"="+v1;
                    }else {
                        params = params+"&"+k1+"="+v1;
                    }
                }else{
                    emptyCnt++;
                }
            });
            if(emptyCnt < 5){
                $(self.el).find(".events-list-load").addClass("data-loading");

                var ajaxSettings = $.extend({}, apiConfig['searchEvent']);
                ajaxSettings.url = ajaxSettings.url+"?"+params;
                self.requestAPICall(ajaxSettings, function (response) {
                    if (!response) {
                        console.log("Err :   Events API failed..");
                        return false;
                    }
                    self.loadTemplateData(".events-list-load","#displayEventsScript","replace", {items: response});
                    $(self.el).find(".events-list-load").removeClass("data-loading");
                });
            }else{
                $(self.el).find(".events-list-load").empty();
                $(self.el).find('#isSearchValidModal').modal('show');
            }
        },
        loadMonthsAndCities: function(){
            this.loadTemplateData(".cities-load","#cityDropdownScript","replace");
        },
        loadCitiesCall : function () {
            var self = this;
            var ajaxSettings = $.extend({}, apiConfig['loadCities']);
            self.requestAPICall(ajaxSettings, function (response) {
                if (!response) {
                    console.log("Err :   Events API failed..");
                    return false;
                }
                self.citiesArr = response.locations;
                self.createEvent();
                self.loadMonthsAndCities();
            });
        },
        getTime : function(value,part) {
            var v = value.split(":");
            return v[part];
        },
        getInputValues : function(ele,obj) {
            var type = $(ele).attr("type");
            var key = $(ele).attr("data-key");
            switch (type) {
                case "text":
                    obj[key] = $(ele).val();
                    break;
                case "number":
                    obj[key] = $(ele).val();
                    break;
                case "checkbox":
                    obj[key] = $(ele).is(":checked");
                    break;
                case "radio":
                    if($(ele).is(":checked")) {
                        if($(ele).hasClass("custom-pricing-option")) {
                            obj[key] = $(ele).next().val();
                        } else {
                            obj[key] = $(ele).val();
                        }
                    }
                    break;
                default:
                    if($(ele).hasClass("select-location")) {
                        obj[key] = $(ele).find("option:selected").text();
                        obj["storeTag"] = $(ele).val();
                    } else {
                        obj[key] = $(ele).val();
                    }
            }
            return obj;
        },
        submitEvent : function (ele, eve) {
            var self = AGAEM.eventsManagement;
            var obj = {};
            $(self.el).find(".primary-input-val").each(function(){
                obj = self.getInputValues(this,obj);
            });
            obj.locationDetails = [];
            $(self.el).find(".location-details").each(function(index){
                var loc = {};
                loc.locationDateDetails=[];
                $(this).find(".location-input-val").each(function(){
                    loc = self.getInputValues(this,loc);
                });
                $(this).find(".date-details").each(function(){
                    var dateObj ={};
                    $(this).find(".datedetail-input-val").each(function(){
                        dateObj = self.getInputValues(this,dateObj);
                    });
                    var startTimeArr = [], endTimeArray = [];
                    $(this).find(".startdate-input-val").each(function(){
                        if($(this).val().length == 1) {
                            startTimeArr.push(0+$(this).val());
                        } else {
                            startTimeArr.push($(this).val());
                        }
                    });
                    $(this).find(".enddate-input-val").each(function(){
                        if($(this).val().length == 1) {
                            endTimeArray.push(0+$(this).val());
                        } else {
                            endTimeArray.push($(this).val());
                        }
                    });
                    dateObj["eventStartTime"] = startTimeArr.join(":");
                    dateObj["eventEndTime"] = endTimeArray.join(":");
                    loc.locationDateDetails.push(dateObj);
                });
                obj.locationDetails.push(loc);
            });

            if($(self.el).find("#eventForm")[0].checkValidity()){
                var ajaxSettings = $.extend({}, apiConfig['saveEvent']);
                var evenStatus ="created!";
                if($(ele).val() == "Update Event") {
                    ajaxSettings = apiConfig['setEvent'];
                    evenStatus ="updated!";
                }
                ajaxSettings.body = obj;
                self.requestAPICall(ajaxSettings, function (response) {
                    if (!response) {
                        console.log("Err :   Events API failed..");
                        $(self.el).find("#SuccessModal .modal-title").html("Fail!");
                        $(self.el).find("#SuccessModal .modal-body").html("<p>Something went wrong, Please try again later!</p>");
                        return false;
                    } else {
                        $(self.el).find("#SuccessModal .modal-title").html("Success!");
                        $(self.el).find("#SuccessModal .modal-body").html("<p>Event ID:<strong>"+ response.permanentEventId  +"</strong> has "+evenStatus+"</p>");
                    }
                    $(self.el).find('#SuccessModal').modal('show');
                    self.createEvent();
                });
                event.preventDefault();
            } else {
                var posArr=[];
                $(self.el).find("#eventForm :required").each(function(index){
                    if(!$(this).is(':valid')) {
                        posArr.push($(this).position().top);
                    }
                });
                setTimeout(function(){
                    $('html, body').animate({
                        scrollTop: posArr[1]-120
                    }, 200);
                },200);
            }
        },
        loadDatePicker: function(){
            var date_input= $('input.datepicker');
            var options={
                format: 'mm-dd-yyyy',
                todayHighlight: true,
                orientation: "right",
                autoclose: true,
            };
            date_input.datepicker(options);
        },
        render: function() {
            var self = this;
            this.loadCitiesCall();
            $(self.el).find('#SuccessModal').on('hidden.bs.modal', function () {
                self.resetTool();
		})
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.eventsManagement) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    eventsManagement.init();
    AGAEM.eventsManagement = eventsManagement;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            eventsManagement.init();
        }
    }, false);
}(window, AGAEM, AGAEM.apiConfig));