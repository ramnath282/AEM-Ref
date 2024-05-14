import {
    deluxeInit,
    createPlayer,
    deluxeConfig,
    updatePlayer
} from '../../mattelVideoPlayerConfig/shared/videoPlayer_Config';
window.global = window.global || {};
window.AEMHbs = window.AEMHbs || Handlebars;
// Eventbinding
class eventBinding {
    constructor() {}
    bindLooping(name, evtObj) {
        let eventSplitter = /(\S+)\s(.*)/, // Regular expression used to split event strings.
            cb,
            splitKeys;

        for (let val in name) {
            splitKeys = val.match(eventSplitter).slice(1);
            cb = evtObj && evtObj[name[val]];
            if (splitKeys && typeof cb == "function") {
                this.bindingEvent(splitKeys[0], splitKeys[1], cb);
            } else {
                console.log("Event Binding failed for " + splitKeys);
            }
        }
    }
    bindingEvent(evtName = click, elem = null, cb = false) {
        if (typeof cb === "function") {
            $(document).on(evtName, elem, function(evt) {
                cb(this, evt);
            });
        } else {
            console.log("Error:CB function not found for this element :" + el);
        }
    }
}

// TemplateSetter
class handleBarTemplate {
    constructor() {}

    loadTemplate(templateId, container, data, action) {
        let source = $(templateId).html();
        let template = window.AEMHbs.compile(source);
        let output = template(data);
        action === "replace" ?
            $(container).html(output) :
            $(container).append(output);
    }
}

// DateFormat
class dateFormat {
    constructor() {}
    formatToNewDate(date) {
        let offset = date.getTimezoneOffset();
        let monthNames = [
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
        ];
        date.setMinutes(date.getMinutes() + offset);
        let day = date.getDate();
        let monthIndex = date.getMonth();
        let year = date.getFullYear();
        return monthNames[monthIndex] + " " + day + ", " + year;
    }
    comparePastMonths(val, monthsTill) {
        let todaysDate = new Date();
        let filterDate = new Date(
            todaysDate.setMonth(todaysDate.getMonth() - (monthsTill || 6))
        );
        let selectedDate = new Date(val);
        if (filterDate.getTime() <= selectedDate.getTime()) {
            return true;
        }
        return false;
    }
    getOnlyMMDDD(val) {
            let monthNames = [
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
            ];
            let date = new Date(val.split(" ")[0]);
            return monthNames[date.getMonth()] + " " + date.getDate();
        }
        // date pformat is DD/MM/YYYY
    isValidDate(date) {
        let bits = date.split("/");
        let d = new Date(bits[2], bits[1] - 1, bits[0]);
        return d.getMonth() + 1 == bits[1];
    }
    getYearList(startYear) {
        let currentYear = new Date().getFullYear(),
            years = [];
        startYear = startYear || 1986;

        while (startYear <= currentYear) {
            years.push(startYear++);
        }

        return years;
    }
    compareTwoDates(date1, date2) {
        let flag;
        if (
            new Date(date1.replace(/ /g, "T")) <= new Date(date2.replace(/ /g, "T"))
        ) {
            flag = 2;
        } else {
            flag = 1;
        }
        return flag;
    }
}
const normalize = (str) => {
    return str.replace(/[^\u0000-\u007E]/g, function(a) {
        return a;
    });
};
class handleBarsHelper {
    callRegisterHelper(selector, eachData, obj) {
        window.AEMHbs.registerHelper(selector, (value, options) => {
            if (selector == "getMMDDYYYY") {
                if (value) {
                    return window.global.changeDateFormat.formatToNewDate(new Date(value.toLocaleString()));

                } else {
                    return 0;
                }
            } else if (selector == "MMDDYYYY") {
                if (value) {
                    let months = {
                        "Jan" : "January",
                        "Feb": "February",
                        "Mar" : "March",
                        "Apr" : "April",
                        "May": "May",
                        "Jun" : "June",
                        "Jul" :"July",
                        "Aug" :"August",
                        "Sep" : "September",
                        "Oct" : "October",
                        "Nov" : "November",
                       "Dec" : "December"
                    }
                    let date = value.split(' ');
                    return value ? months[date[2]]+ " " + date[1]+ ", " +date[3] : 0
                 } else {
                    return 0;
                }
            } else if (selector == "indexStartWith1") {
                return parseInt(value) + 1;
            } else if (selector == "getMMDDYYYYSlashFormat") {
                if (value) {
                    let val = value.split("-");
                    return val[1] + '/' + val[0] + '/' + val[2];
                } else {
                    return 0;
                }
            } else if (selector == "convertDateToSlashFormat") {
                if (value) {
                    value = new Date(value);
                    return (("0" + (value.getMonth() + 1)).slice(-2)) +'/' + (("0" + value.getDate()).slice(-2)) + '/' + value.getFullYear();
                } else {
                    return 0;
                }
            } else if (selector == "changeToHyphen") {
                if (typeof value == "string" && value != "") {
                    return value.replace(".", "-");
                } else {
                    return 0;
                }
            } else if (selector == "roundOffValue") {
                if (value != "") {
                    return Math.round(value);
                } else {
                    return 0;
                }
            } else if (selector == 'forLoop') {
                let accum = '';
                for (let i = 0; i < value; ++i) accum += options.fn(i);
                return accum;
            } else if (selector == 'seoFormat') {
                let strVal = value.toString();
                let normalizedVal;
                if (typeof strVal.normalize == "function") {
                    normalizedVal = strVal.normalize('NFD');
                } else {
                    normalizedVal = normalize(strVal);
                }
                return normalizedVal // Change diacritics
                    .replace(/[\u0300-\u036f]/g, '') // Remove illegal characters
                    .replace(/\s+/g, '-') // Change whitespace to dashes
                    .toLowerCase() // Change to lowercase
                    .replace(/&/g, '-and-') // Replace ampersand
                    .replace(/[^a-z0-9\-]/g, '') // Remove anything that is not a letter, number or dash
                    .replace(/-+/g, '-') // Remove duplicate dashes
                    .replace(/^-*/, '') // Remove starting dashes
                    .replace(/-*$/, ''); // Remove trailing dashes
            }
        });
        window.AEMHbs.registerHelper("index_1", (value, options) => {
            return parseInt(value) + 1;
        });
    }
    checkIFConditions(selector) {
        window.AEMHbs.registerHelper(selector, (arg1, arg2, options) => {
            if (selector == 'ifEquals') {
                return arg1 == arg2 ? options.fn(this) : options.inverse(this);
            } else if (selector == 'greaterThan') {
                return parseInt(arg1) > parseInt(arg2) ? options.fn(this) : options.inverse(this);
            } else if (selector == 'ifNotEquals') {
                return arg1 != arg2 ? options.fn(this) : options.inverse(this);
            } else if (selector == 'checkIndexExist') {
                return arg1.indexOf(arg2) != -1 ? options.fn(this) : false;
            } else if (selector == 'isLessThanMonths') {
                if (arg1 != '' && window.global.changeDateFormat.comparePastMonths(arg1, arg2 || 6)) {
					return options.fn(this);
				} else {
					return options.inverse(this);
				}
			} else if (selector == 'parseFloat') {
                if (arg2 == '' && arg1 != '') {
                    return parseFloat(arg1 || 0).toFixed(2);
                } else if (arg2 != '' && arg1 != '') {
                    return parseFloat(arg1[arg2] || 0).toFixed(2);
                } else {
                    return parseFloat(0).toFixed(2);
                }
            } else if (selector == "finalActualPrice"){
				if (arg2 != '' && arg1 != '') {
					return  parseInt(arg1) > parseInt(arg2) ? "$"+parseFloat(arg1).toFixed(2) : null;
				} 
				return null;
			}
        });
        window.AEMHbs.registerHelper('ifEqualsPrice', function(arg1, arg2, options) {
            return parseFloat(arg1) == parseFloat(arg2) ? options.fn(this) : options.inverse(this);
        });
        window.AEMHbs.registerHelper('ifGreater', function(arg1, arg2, options) {
            return parseFloat(arg1) > parseFloat(arg2) ? options.fn(this) : options.inverse(this);
        })
    }
}

class browserCookie {
    setCookie(cname, cvalue, exdays = 90) {
        var d = new Date();
        d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    }
    getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(";");
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == " ") {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }
    deleteCookie(name) {
        document.cookie =
            name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }
}

class firstCTAhandler {
    constructor() {}
    firstCTAshow() {
        const $firstCTA = $(".showFirstCtaOnly");
        _.each($firstCTA, function(item) {
            $(item).find(".ctaItem:first").addClass("first-cta");
        });
        $(".viewAllBtn,.showMoreBtn,.showLessBtn").removeClass("first-cta");
    }
}

class apiConfig {
    constructor() {
        this.config = {
            submitCrmForm: {
                url: $("#consumerinfoApiDetails").attr("data-api-url"),
                type: "POST",
                contentType: "application/json",
                headers: {
                    api_key: $("#consumerinfoApiDetails").attr("data-api-key"),
                },
            },
            getListingData: {
                getSAndP: function() {
                    let url = $("#snpEP").val() || '//stage-sp10056c97.guided.ss-omtrdc.net/';
                    let params =
                        url.indexOf('?') == -1 ?
                        this.queryString :
                        this.queryString.replace('??', '&').replace('?', '&');

                    return {
                        type: 'get',
                        accept: 'application/json',
                        crossDomain: true,
                        url: `${url}${params}`
                    };
                }
            }
        };
    }

    getApiConfig(reqConfig) {
        return this.config[reqConfig];
    }
}

class JSONEncryption {
    constructor() {
        this.secretKey = "MAT_AEM";
    }
    encrypt(data) {
        if (!data) return false;
        data = JSON.stringify(data).split('');
        for (var i = 0, l = data.length; i < l; i++)
            if (data[i] == '{')
                data[i] = '}';
            else if (data[i] == '}')
            data[i] = '{';
        return encodeURI(this.secretKey + data.join(''));
    }
    decrypt(data) {
        if (!data) return false;
        data = decodeURI(data);
        if (this.secretKey && data.indexOf(this.secretKey) != 0)
            throw new Error('object cannot be decrypted');
        data = data.substring(this.secretKey.length).split('');
        for (var i = 0, l = data.length; i < l; i++)
            if (data[i] == '{')
                data[i] = '}';
            else if (data[i] == '}')
            data[i] = '{';
        return JSON.parse(data.join(''));
    }
}
class ajaxRequest {
    ajaxCall(config, resolve, reject) {
        return $.ajax(config)
            .then(function(res) {
                return $.Deferred().resolve(res);
            })
            .fail(function(err) {
                if (err.status == 500) {
                    exceptionHandler("error", "An error has occurred.");
                }
            });
    }
}

const getDeviceName = () => {
    let deviceName;
    if (window.innerWidth <= 480) {
        deviceName = "mobilePortrait"
    } else if (window.innerWidth <= 767) {
        deviceName = "mobile"
    } else if (window.innerWidth <= 980) {
        deviceName = "tabletPortrait"
    } else if (window.innerWidth <= 1200) {
        deviceName = "tablet"
    } else {
        deviceName = "desktop"
    }
    return deviceName;
}

const setStorage = (setName = null, obj = {}) => {
    if (getStorage(setName) != null) deleteStorage(setName);
    sessionStorage.setItem(setName, JSON.stringify(obj));
};

const getStorage = name => JSON.parse(sessionStorage.getItem(name));

const deleteStorage = (name) => {
    window.sessionStorage.removeItem(name);
};

class storageinLocal {
    getExpirationTime(storageExpirationInMin){
        let currentDate = new Date();
        let expireTime = storageExpirationInMin;
        let minutes = Math.round((currentDate.getMinutes() / expireTime)) * expireTime;
        if (currentDate.getMinutes() == minutes) {
            currentDate.setMinutes(currentDate.getMinutes() + 1);
            minutes = Math.round((currentDate.getMinutes() / expireTime)) * expireTime;
        }
        return new Date(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate(),currentDate.getHours(),minutes,0);
    }
    set (setName = null, obj = {}, expireTime) {
        if (this.get(setName) != null) this.delete(setName);
        if(expireTime || expireTime == 0){
            obj['expireTime'] = new Date(new Date().getTime() + (60000 * expireTime))
        } 
        localStorage.setItem(setName, JSON.stringify(obj));
    }
    get (name){
        const data = JSON.parse(localStorage.getItem(name));
        if(data && data.expireTime){
            const isSessionExpired = data.expireTime && new Date(data.expireTime);
            if(isSessionExpired > new Date()){
                return data;
            }
            this.delete(name);
            return false;
        }
        return data;
    } 
    delete (name)  {
        window.localStorage.removeItem(name);
    }
}

const exceptionHandler = (messageType, content) => {
    const checkIfError = (params) => {
        if (!params[0] || !params[1]) {
            return true;
        }
    };

    const newEle = `<div role="alert" class="toast-message toast-${messageType}"><div class="toast-text"><span class="info-icon"></span>${content}</div></div>`;
    if (checkIfError(arguments)) return;

    let $toastElem = $(".toast-container");
    const newClass = "active";
    const delayTimeout = 2000;
    let timeSet;

    if (!$toastElem.length) {
        $toastElem = $(".toast-container");
    }
    $toastElem.html(newEle).addClass(newClass);
    const $curElem = $toastElem;
    window.clearTimeout(timeSet);
    timeSet = window.setTimeout(() => {
        $curElem.removeClass(newClass);
        window.setTimeout(() => {
            $curElem.html("");
        }, 100);
    }, delayTimeout);
};

const getStorageValues = () => {
    const {id, firstName, consumerLoyalty, tags} = window.global.localStorage.get("CUSTOMER_DATA") || {};
    return {
        welcomeMsg: firstName && firstName.trim(), // user name
        SCID: id || window.localStorage.getItem("SCID"), // cutomer ID
        customerSegment: typeof consumerLoyalty == "object" && consumerLoyalty.LoyaltyTierCode, // [silver, gold, berry]
        IAT: window.localStorage.getItem("IAT"), // identity access token
        employeePrice: false //tags && tags.indexOf("Group: Employee")!=-1
    }
};
const clearStorageValues = () => {
    window.global.localStorage.delete("CUSTOMER_DATA"); // loyalty
    window.global.localStorage.delete("SCID"); // customer ID
    window.global.localStorage.delete("IAT"); // identity access token
    window.global.localStorage.delete("CUSTOMER_Error"); // loyalty retry storage
};

const getUserCookie = () => {
    const {welcomeMsg,SCID,customerSegment, IAT, employeePrice} = getStorageValues();
    if (SCID && IAT) {
        if(!customerSegment || !welcomeMsg){
            return {
                userType: "unidentified-user", // need to call loyalty serveice to get the required data
                SCID,
                IAT,
                userName: welcomeMsg || undefined,
            }
        } else{
            return {
                userType: (customerSegment == "SILVER" || customerSegment == "GOLD" || customerSegment == "BERRY") ? "agr-user" : "non-agr-user",
                SCID,
                IAT,
                userName: welcomeMsg,
                customerSegment,
                employeePrice
            }
        }
    }
    return { userType: "guest-user" };
}
const displayUserName = userName =>{
    const $ele = $(".user-info-in-header");
    if (userName) {
        $ele.find(".username").html(userName);
    }
    $ele.addClass(`${userName ? 'user-on' : 'user-off'}`);
}

const updatePromoPencilHeader = userType => {
    let $ele = $(".promo-pencil-container");
    if(!$ele.length) return;
    if(userType == "" || userType == "guest-user" || userType == "non-agr-user") userType = "guest-user";
    else userType = "signed-in-user";
    let $activeElem = $ele.find(`.promo-${userType}`);
    if($activeElem.length){
        $("body").addClass("promo-pencil-on");
        $activeElem.addClass("active");
        $activeElem.find(".slick-initialized").length && $ele.find(".active .slider-content").slick('refresh');
    }
}
const publishLoyaltyData = data =>{
    if(!data){ // data error / failed
        // console.log("Loyalty Data Failed, username / promopecil section won't be updated..");
    }
    let {firstName, userType} = data || {};
    displayUserName(firstName);
    updatePromoPencilHeader(userType || "");
}

const CMFormSubmit = (payload,cb) =>{
    let datasets = $(".cm-api-config").length ? $(".cm-api-config")[0].dataset : {};
    let request = new ajaxRequest();
    if(typeof payload === "object"){
        payload["RequestedBy"] = "AEM";
    }
    request.ajaxCall({
        url: datasets.apiUrl,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        headers: { 'api_key': datasets.apiKey }
    })
    .then(data => {
        let {Status} = data;
        if(Status.statusCode == 200 || Status.statusCode == 201){
            cb("success", data);
            return;
        }
        cb("error", data)
    })
    .catch(err =>{ cb("error", err);
    });
}

const cookieCheckModalSlideDisplay = (ele) =>{
    let el = $(ele);
    let ifCookieCheck = el.data('cookiecheck');
    if(ifCookieCheck){
      let cookieName = el.data('cookiename');
      if(document.cookie.indexOf(cookieName) != -1){
        return false;
      }
      else{
        let expiryDate = el.data('cookieexpirydate');
        let d = new Date();
        d.setTime(d.getTime() + (expiryDate * 60 * 60 * 1000));
        var expires = d.toUTCString();
        document.cookie = cookieName+"="+ "" +"path=/;expires=" + expires;
        return true;
      }
    }
    else{
      return true;
    }
}
let instance = null;
class cartAPI {
    constructor(){
        instance = this;
    }
    callGetCartAPI(config, cb){
        window.global.ajaxRequest.ajaxCall(config)
        .then(data => {
            let childQnty=0;
            _.filter(data.items, item => {
                if(item.properties && (item.properties.type == "child")){
                    childQnty+=_.isNumber(item.quantity) ? item.quantity : 0;
                }
                return childQnty;
            });
            // const childItems = _.countBy(data.items, item => (item.properties && item.properties.type) == "child").true || 0;
            const miniCartQty = parseInt((data.item_count - childQnty) || 0);
            miniCartQty > 0 && $("#minishopcart_total").html(miniCartQty).addClass("total-bag-count");
            typeof cb == "function" && cb(data);
        }).catch(error => {
            typeof cb == "function" && cb(0);
        });
    }
    callPostCartAPI(config, cb){
        window.global.ajaxRequest.ajaxCall(config)
            .then(data => {
                typeof cb == "function" && cb(data);
            }).catch(error => {
                window.global.errorHandling.cartAPI(error);
                typeof cb == "function" && cb(false);
            });
    }
    callVariantIdsAPI() {
        // const checkSessionHasData = getStorage("variantIds") || 0;
        // if (checkSessionHasData) {
        //     instance.availableVariantIds = checkSessionHasData;
        //     return;
        // }
        // window.global.ajaxRequest.ajaxCall({
        //     url: `//${window.location.host}/content/dam/ag-dam/ag-global-dam/parent-site-dam/mock-jsons/mappingVariantId.json`,
        //     type: 'get',
        //     accept: 'application/json',
        //     contentType: 'application/json'
        // }).then(data => {
        //     instance.availableVariantIds = data;
        //     setStorage('variantIds', data);
        // }).catch(error => {

        // });
    }
    getVariantIdOfSKU(ele, partNo) {
        const { variantId } = $(ele)[0].dataset;
        if(variantId){
            return variantId;
        } else if (!variantId && typeof instance.availableVariantIds != "undefined" && instance.availableVariantIds[partNo]) {
            return instance.availableVariantIds[partNo];
        }
        !variantId && console.log(`Variant ID was not found for this part number ${partNo}..`);
        // $(ele).attr("data-variant-id", variantId);
        return variantId;
    }
    formatWithDelimiters(number, precision, thousands, decimal) {
        decimal = typeof decimal == 'undefined' ? '.' : decimal;
        thousands = typeof thousands == 'undefined' ? ',' : thousands;
        if (isNaN(number) || number == null) { return 0; }
        number = (number/100.0).toFixed(typeof precision == 'undefined' ? 2 : precision);
        const parts   = number.split('.'),
            dollars = parts[0].replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1' + thousands),
            cents   = parts[1] ? (decimal + parts[1]) : '';
        return dollars + cents;
    }
    getPriceAttrName(){
        const { customerSegment,employeePrice} = getUserCookie();
        if (employeePrice == true || employeePrice == "true") {
            if (customerSegment) {
                return "employeeLoyalty" //employee_loyalty_price;
            }
            return "employee" //employee_price;
        } else if (customerSegment == "SILVER" || customerSegment == "GOLD" || customerSegment == "BERRY") {
            return "loyalty" //loyalty_price
        }
        return "price"; // sale price
    }
    getActivePriceData(priceObj){
        let {compare_at_price} = priceObj;
        let price = priceObj[instance.getPriceAttrName()];
        if(typeof compare_at_price == "undefined"){
            compare_at_price = price;
        }
        return {
            salePrice: price,
            listPrice: compare_at_price,
            differenceAmount:  parseFloat(compare_at_price - price).toFixed(2),
            inPrecentage: parseInt(Math.floor(((parseInt(compare_at_price) - parseInt(price)) / parseInt(compare_at_price))*100))
        }
    }
    getSwatchesInventory(config, swatchNo, cb){
        window.global.ajaxRequest.ajaxCall(config).then(data => {
            let {variants} = data.product;
            let inventory = variants && _.filter(variants, (item) => {
                return item.core.sku == swatchNo;
            });
            if(inventory && typeof inventory == "object") cb(inventory[0].core);
            else cb(false);
        }) .catch(error => {
            window.global.errorHandling.PDPAPI(error);
            cb(false);
        });
    }
    readInventoryvalueAPI(invRes){
        if(invRes == "Sold Out") {
            return "noLongerAvailable";
        }
        else if(invRes == "Limited Quantities"){
            return "Limited";
        }
        else{
            return invRes;
        }
    }
};

class ErrorHandling {
    getErrorJsonData(errorKey){
        if(this.apiErrorDatas){
            return this.apiErrorDatas[errorKey];
        }
        const checkAPI = getStorage("errorList");
        if(checkAPI) {
            this.apiErrorDatas = checkAPI;
            return checkAPI[errorKey];
         }
    }
    cartAPI(error) {
        let errorMessage;
        if(error.responseJSON && error.responseJSON.description){
            errorMessage = error.responseJSON.description;
        } else if(error.status && !error.status.toString().startsWith("2")){
            errorMessage = this.getErrorJsonData("_ERR_GENERIC_CART_API");
        }
        errorMessage ? exceptionHandler("error", errorMessage) : console.log(`%c CART API Error: ErrorMessage was not found.`, "background: red; color:white; font-weight:bold");
    }
    PDPAPI(error) {
        let errorMessage = this.getErrorJsonData("_ERR_GENERIC_PDP_API");
        if(error.status && !error.status.toString().startsWith("2") && errorMessage){
            exceptionHandler("error", errorMessage);
            return;
        }
        console.log(`%c PDP API Error:ErrorMessage was not found.`, "background: red; color:white; font-weight:bold");
    }
}

window.global = {
    isDependencyLoaded: typeof _ == "function" && typeof $ == "function" ? true : false,
    eventBindingInst: window.global.eventBindingInst || new eventBinding(),
    firstHander: window.global.firstHander || new firstCTAhandler(),
    handleBarTemplateInst: window.global.handleBarTemplateInst || new handleBarTemplate(),
    handleBarsHelperInst: window.global.handleBarsHelperInst || new handleBarsHelper(),
    changeDateFormat: window.global.changeDateFormat || new dateFormat(),
    apiConfig: window.global.apiConfig || new apiConfig(this),
    ajaxRequest: window.global.ajaxRequest || new ajaxRequest(),
    browserCookie: window.global.browserCookie || new browserCookie(),
    deviceName: getDeviceName,
    setStorage: window.global.setStorage || setStorage,
    getStorage: window.global.getStorage || getStorage,
    deleteStorage: window.global.deleteStorage || deleteStorage,
    exceptionHandler: window.global.exceptionHandler || exceptionHandler,
    dataEncryption: window.global.dataEncryption || new JSONEncryption(),
    localStorage: window.global.storageinLocal || new storageinLocal(),
    getUserCookie: window.global.getUserCookie || getUserCookie,
    clearStorageValues: window.global.clearStorageValues || clearStorageValues,
    PublishLoyaltyData: window.global.PublishLoyaltyData || publishLoyaltyData,
    CMFormSubmit: window.global.CMFormSubmit || CMFormSubmit,
    initDeluxePlayer: window.global.initDeluxePlayer || deluxeInit,
    createDeluxePlayer: window.global.createDeluxePlayer || createPlayer,
    updateDeluxePlayer: window.global.updateDeluxePlayer || updatePlayer,
    deluxeConfigs: window.global.deluxeConfigs || deluxeConfig,
    cookieCheckModalSlideDisplay: window.global.cookieCheckModalSlideDisplay || cookieCheckModalSlideDisplay,
    cartAPI : window.global.cartAPI || new cartAPI(),
    errorHandling: window.global.errorHandling || new ErrorHandling()
}
window.global.firstHander.firstCTAshow();
window.global.handleBarsHelperInst.callRegisterHelper("getMMDDYYYY");
window.global.handleBarsHelperInst.callRegisterHelper("MMDDYYYY");
