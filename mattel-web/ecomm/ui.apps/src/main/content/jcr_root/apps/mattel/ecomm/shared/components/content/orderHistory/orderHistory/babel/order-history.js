import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import pagination from '../shared/pagination';
import {
    dateFormat
} from '../shared/dateFormat';
import {
    handleBarTemplate
} from '../shared/templateSetter';

class orderInfo {
    constructor() {
        self = this;
        self.element = ".my-order-summary";
        self.rowPerPage = $(self.element).find("[data-max-lines]").attr("data-max-lines")!="" ? $(self.element).find("[data-max-lines]").attr("data-max-lines") : 10;
        self.orderSummaryLimit = 5;        
        eventBindingInst.bindLooping(this.bindingEventsConfig(), self);
        self.isOrderHomePage = $(self.element).find("#serchByOrderId").length;
    }
    init() {
        if (this.element.length) {
            self.render();
        }
    }
    bindingEventsConfig() {
        let eventsArr = {
            "keypress .page-link": "paginationAction",
            "click .page-link": "paginationAction",
            "change #searchOrder": "filterDataOnDateChange",
            "keydown #serchByOrderId": "initiateFilterDataByOrderId",
            "keyup #serchByOrderId": "numberValidation",
            "click .search-icon": "initiateFilterDataByOrderId"            
        }
        return eventsArr;
    }
    selectOrderYear(){
        let date = new Date();
            for (let i = 0; i < 10; i++) {
                var option = "<option value=" + parseInt(date.getFullYear() - i) + ">" + parseInt(date.getFullYear() - i) + "</option>";
                $('[id*=searchOrder]').append(option);
            }        
    }
    numberValidation(el, evt){
        $(el).val($(el).val().replace(/[^\d].+/, ""));
        if($(el).val().length===0){
            self.filterDataByOrderId($(el).val());
        }
        if (evt.keyCode < 48 || evt.keyCode > 57){
            if(evt.keyCode != 8 && evt.keyCode != 9 && evt.keyCode != 46 && evt.keyCode != 37 && evt.keyCode != 39 && evt.keyCode != 96 && (evt.keyCode != 17 && evt.keyCode != 67) && (evt.keyCode != 17 && evt.keyCode != 86) && (evt.keyCode < 97 || evt.keyCode > 105) && (evt.keyCode !=16 && evt.keyCode != 9)){
                evt.preventDefault();
            }
        }  
    }
    paginationAction(elem, evt) {
        evt.preventDefault();
        const container = "#orderSummary table";
        const currentPage = $("#pagination .page-link.current").data('page');
        const pageNo = $(elem).hasClass("page-first") ? currentPage-1 : ($(elem).hasClass("page-next") ? currentPage+1 : $(elem).data("page"));
        $(container).addClass("request-pending");
        setTimeout(() =>{
            self.renderData(pageNo);
            $(container).removeClass("request-pending");
        },300)

    }
    renderData(pageNo, calledFrom) {
        pageNo = pageNo || 1;
        const container = "#orderSummary";
        const paginationContainer = "#pagination";
        let filteredData = self.responseData;
        const obj = filteredData.length && filteredData.slice(this.rowPerPage * (pageNo-1), self.rowPerPage * pageNo).map(result => result);
        handleBarTemplateInst.loadTemplate("#orderListTemp", container, obj, "replace");
        if(!filteredData.length){
          return;
        }
        const totalCount = filteredData.length; 
        const pageSize = Math.ceil(totalCount / self.rowPerPage);
        if(totalCount>this.rowPerPage){
            createPagination.init(document.getElementById('pagination'), {
                size: pageSize, // pages size 
                page: pageNo, // selected page
                step: 3 // pages before and after current
            });
            if (pageNo == 1) {
                $(paginationContainer).find(".page-first").addClass('disabled');
            } else if (pageSize == pageNo) {
                $(paginationContainer).find(".page-next").addClass('disabled');
            }
        }
    }
    render() {
        const container = ".my-order-summary";
        $(container).addClass("request-pending");    
        ajaxRequestInst.ajaxCall(apiConfigInst.getApiConfig("orders"))
            .then(data => {
                let orderDetails = data.orderHistoryList;
                const { Order } = orderDetails;
                self.totalCnt = orderDetails.recordSetTotal;
                $.each(Order, (key, val) => {
                    val['grandTotal'] = parseFloat(val['grandTotal']).toFixed(2);
                    if(val['orderType'].toLowerCase()==('Catalog Request (Web Catalog Request)').toLowerCase()){
                        val['orderType'] = 'Catalog Request';
                    }
                    val['placedDate1'] = val['placedDate'];
                    val['placedDate'] = newTime.formatToNewDate(new Date(val['placedDate']));
                    if(val.orderStatus.toLowerCase() == "held") {
                        val.orderStatus = "RECEIVED";
                    }
                    if(val.orderStatus.toLowerCase() == "open") {
                        val.orderStatus = "IN PROGRESS";
                    }
                    if(val.orderStatus.toLowerCase() == "complete order") {
                        val.orderStatus = "CLOSED";
                    }
                });
                self.orderListArr = Order;
                self.responseData = Order;
                if(self.isOrderHomePage){
                    self.filterDataOnDateChange();
                } else{
                    const dataObj = Order.slice(0, self.orderSummaryLimit).map(result => result);
                    handleBarTemplateInst.loadTemplate("#orderListTemp", "#orderSummary", dataObj, "replace");
                }
                this.populateDrodownVal();
                $(container).removeClass("request-pending");
                
            })
            .catch(error => {
                console.log(error);
                $(container).removeClass("request-pending");
            })
    }    
    populateDrodownVal(){
        let date = new Date();        
        let firstYearDayMs = new Date().setFullYear(new Date().getFullYear(), 0, 1),
            currDayMs = new Date().getTime(),
            monthDiff = (firstYearDayMs - currDayMs) / 1000;
            monthDiff /= (60 * 60 * 24 * 7 * 4);
        if(monthDiff > 6){
            // current year
            for (let i = 0; i < 10; i++) {
                let option = "<option value=" + parseInt(date.getFullYear() - i) + ">" + parseInt(date.getFullYear() - i) + "</option>"
                $('[id*=searchOrder]').append(option);
            }
        }else{
            // previous year
            for (let i = 1; i < 10; i++) {
                let option = "<option value=" + parseInt(date.getFullYear() - i) + ">" + parseInt(date.getFullYear() - i) + "</option>"
                $('[id*=searchOrder]').append(option);
            }
        }
    }
    filterDataOnDateChange(){
        let curVal = $.trim($(self.element).find("#searchOrder").val());
        let startDate = new Date();
        let endDate = new Date();
        if(curVal == ""){
            let now = new Date();
            endDate = new Date();
            startDate = new Date(now.setMonth(now.getMonth() - 6));
        }else{
            endDate = new Date(curVal+"-12-31");
            startDate = new Date(curVal+"-01-01");
        }

        let resultOrderData = self.orderListArr.filter(function (a) {
            let placedDate = a.placedDate1 || {};
            placedDate = new Date(placedDate);
            return (placedDate >= startDate && placedDate <= endDate);
        });
        self.responseData = resultOrderData;
        self.renderData(1,"dateChange");
    }
    initiateFilterDataByOrderId(elem, evt){        
        let keyCode = evt.keyCode;
        let searchVal = $.trim($(self.element).find("#serchByOrderId").val());
        self.numberValidation(elem, evt)
        if(keyCode == 13){                    
            self.filterDataByOrderId(searchVal);
        }else if(keyCode == undefined){            
            self.filterDataByOrderId(searchVal);
        }
        evt.preventDefault();
    }
    filterDataByOrderId(searchVal){
        let curVal = searchVal;
        $(self.element).find("#searchOrder").val("").change();
        if(curVal != ""){
            let resultOrderData = self.orderListArr.filter(function (a) {
            let orderId = a.orderId || {};
                return (orderId == curVal);
            });
             self.responseData = resultOrderData;
            self.renderData(1,"byOrderId");
        }else{            
            self.renderData();
        }        
    }
}

let self;
let eventBindingInst = new eventBinding();
let apiConfigInst = new apiConfig();
let newTime = new dateFormat();
let ajaxRequestInst = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let orderHistoryInstance = new orderInfo();
let createPagination = new pagination();
orderHistoryInstance.init();