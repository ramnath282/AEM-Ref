import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import { sliderInit } from "../shared/slider-init.js";
import { truncateInit } from '../shared/textTruncate';
import { exceptionHandler } from '../shared/flickerMessage';

const CRMLoyalityHeader = function() {
    self = this;
    this.errorDebuggerON=false; //* this flag used for displaying all custom error messages. Default is false
    dataConfigs = $(".crm-hidden-properties").length ? $(".crm-hidden-properties")[0].dataset : [];
    dataConfigs.clearStorage = false; 
    this.$el = $('.userProfileComponent');
    this.$bodyEl = $('body');
    // this.loggedInCookieName = "MATTEL_WELCOME_MSG"; //* returning user name
    this.storageCookieName = "CUSTOMER_DATA"; //* local storage name
    this.errorActiveClass ="crm-err-msg"; //* this value will be printed in console as a prefix for all error and warning messages
    // this.isUserInSession = cookie.getCookie("WC_SESSION_ESTABLISHED").trim(); //* identifier if user is in-session or not
    this.tierNameConfigs = this.tierNamesConversion(dataConfigs.tierWithPts); //* return all the tier names with the range value
    this.storageExpirationInMin = parseInt(dataConfigs.expirationTime || 0); //* value for setting the expiry time to local storage data
    this.maxErrorCheck = dataConfigs.apiFailureCalls; //* handling 500 errors, api call will be prevented when service is returning 500 errors
    this.maxRedeemCode = parseInt(dataConfigs.maxPromoCodes || 2); //* maximum promo code allowed per order
    this.errorJSONCheckCnt = 0;
    this.getCallCheckCnt = 0;
    deviceName = getDeviceName(); //* return the device names (desktop,tablet,tabletPortrait,mobile and mobilePortrait)
    this.textLimit = {
        loyality:{'desktop':1,'tablet' : 1,'tabletPortrait' : 1,'mobile' : 1,'mobilePortrait' : 1},
        rewards:{'desktop':3,'tablet' : 3,'tabletPortrait' : 3,'mobile' : 3,'mobilePortrait' : 3}
    }; //* truncating the text lines based on this config. This mainly used for promo type description
    this.cartPayload = [];
    this.init.call();
};
CRMLoyalityHeader.prototype = {
    /**
     * getErrorJSONCodes
     * TODO: it will return the error api json data which is globally used in AG.
     */
     getErrorJSONCodes() {
        self.errorJSONCodes = {};
        self.waitForErrorJSONToLoad((data)=>{
            self.errorJSONCodes = data;
            maxCntMsg = data["ERR_MAX_PROMOTION_CODE_ALLOWED"] && data["ERR_MAX_PROMOTION_CODE_ALLOWED"].replace("this.maxPromoCode", self.maxRedeemCode);;
        })
    },
    waitForErrorJSONToLoad(callBack) {
        window.setTimeout(() => {
            let getJSONErrors = window.global.getStorage("errorList");
            if (getJSONErrors) {
                callBack(getJSONErrors);
            } else {
                self.errorJSONCheckCnt++;
                if (self.errorJSONCheckCnt > 20) {
                    warningMessage("ERROR_JSON_NOT_FOUND","Error JSON file not found");
                    return;
                }
                self.waitForErrorJSONToLoad(callBack);
            }
        }, 500);
    },
    /**
     * tierNamesConversion
     * TODO: convert the data attribute value (i.e., SILVER_0,GOLD_250,BERRY_500) to array format
     * @param {tierNamesLists}  nameLists
     */
    tierNamesConversion(nameLists) {
        if (!nameLists) {
            errorHandling('tierConfigMissing', 'tier names not found.');
            return false;
        }
        let objects = [];
        let splitKeys;
        _.each(nameLists.split(","), val => {
            splitKeys = val.split('_');
            // console.log(splitKeys[0])
            objects.push({
                'tierName': splitKeys[0],
                'tierRange': splitKeys[1]
            });
        });
        return objects;
    },
    /**
     * getActiveTierIndex
     * TODO: it will return the index of current tier from array
     * @param {current tier name} activeTierName
     */
    getActiveTierIndex(activeTierName) {
        return _.findIndex(this.tierNameConfigs, val => val.tierName == activeTierName);
    },
    /**
     * tierPointsCalculation
     * TODO: it will return the active tier name / next tier name / completed badges and the progress bar points based on the active tier index
     * @param {getLoyaltyRewards response object} res
     */
    tierPointsCalculation(res) {
        let { LoyaltyTierCode, LoyaltyPointCount, NextTierAmount, NextRewardPoint } = res;
        let updatedObj = {}, activeBadge, nextBadge, completedBadge,lastBadge, berryBadge;
        let activeObjIndex = self.getActiveTierIndex(LoyaltyTierCode);
        if (activeObjIndex == -1) {
            errorHandling('TierNotDefined', 'user badge/tier not defined in our tier config list.');
            return;
        };
        lastBadge = this.tierNameConfigs[this.tierNameConfigs.length-1];
        let finalObj = _.each(this.tierNameConfigs, (obj, indx) => {
            activeBadge = activeObjIndex == indx && true;
            nextBadge = (activeObjIndex + 1) == indx && true;
            completedBadge = (activeObjIndex == indx || activeObjIndex > indx) ? true : false;
            berryBadge = (LoyaltyTierCode == obj.tierName && LoyaltyTierCode == lastBadge.tierName) ? true : false; 
            Object.assign(obj, {
                activeBadge,
                nextBadge,
                completedBadge,
                berryBadge
            });
            if (nextBadge){
                updatedObj['nextBadge'] = obj.tierName;
                updatedObj['completedPts'] = ((parseInt(obj.tierRange) - parseInt(NextTierAmount)) / parseInt(lastBadge.tierRange)) * 100; // in percentage value
            } else if(LoyaltyTierCode == lastBadge.tierName && berryBadge){
                let berryProgressbarRange = 200,
                remainingberryPoints = berryProgressbarRange - NextRewardPoint;
                updatedObj['completedPts'] = (remainingberryPoints / berryProgressbarRange) * 100; // in percentage value
            }
            if (activeBadge) {
                updatedObj['activeBadge'] = obj.tierName;
            }
            return obj;
        });
        updatedObj['tierConfigs'] = finalObj;
        return updatedObj;
    },
    /**
     * TODO: initiating the handlebar helper functions
     */
    bindingHelperFn() {
        handleBarsHelperInst.callRegisterHelper('roundOffValue');
        handleBarsHelperInst.checkIFConditions('ifEquals');
        handleBarsHelperInst.callRegisterHelper('convertDateToSlashFormat');
    },
    /**
     * TODO: it will check the local storage data whether it is expired or not. 
     */
    checkDataAlreadyInStorage() {
        const isDataExists = localStorage.get(self.storageCookieName);
        if (isDataExists) {
            if(self.authenticationId == isDataExists.authenticationId) return isDataExists;
            else { localStorage.delete(self.storageCookieName);return false;}
        }
        return false;
    },
    compareStatusWithGetCart(loyaltyData, cb) {
        if(loyaltyData.ConsumerPromotions) {
            self.callGetCartAPI(res => {
                if(res.attributes && !_.isEmpty(res.attributes.Rewards)){
                    self.cartPayload = _.uniq(_.without(res.attributes.Rewards.replace(/{/g,"").replace(/PromotionID : /g,"").replace(/}/g,"").split(","),''));
                    _.map(loyaltyData.ConsumerPromotions,(item)=>{
                        return item['RedeemedStatus'] = self.cartPayload.indexOf(item.RewardsPromoCode) !=-1 ? 'REDEEMED' : 'AVAILABLE';
                    });
                }
                cb(loyaltyData);
            });
            return;
        }
        cb(loyaltyData);
        return;
    },
    /**
     * TODO: the response will be compiled through handlebar template and append it.
     * @param {getLoyaltyRewards response object} data 
     * @param {template rendering element} ele 
     */
    APICallBack(data, ele) {
        PublishLoyaltyData(data);
        if (!self.$el.length) {
            errorHandling('ElemNotFound', "returning, class name :: userProfileComponent not found");
            return;
        }
        $.extend(data, self.getSchemaFields(data));
        templateInit('#loyalityComponentTemplate', '#loyalityComponentContainer', data, '');
        ele.removeClass('req-loading').find(".enhanced-container").attr('data-active-tier',data.consumerLoyalty.LoyaltyTierCode);
        self.userRewards && self.updateRewardsUserUI(data.ConsumerPromotions);
        self.$bodyEl.addClass("crm-header-on");
    },
    /**
     * TODO: handled the api network errors
     * @param {network error object} error 
     * @param {network status code} statusCode 
     * @param {network error message} errorMessage 
     */
    APINetworkErrorHandling(error) {
        let {status, responseJSON} = error;
        PublishLoyaltyData(0, "data failed");
        let getErrorCount;
        switch (status) {
            case 401:
                errorModal('error', self.errorJSONCodes["ERR_IAT_TOKEN_INVALID"]);
                clearStorageValues();
                errorMessage(`401:${responseJSON && responseJSON.message ? responseJSON.message : ''}`);
                break;
            default:
                getErrorCount = self.handling500ErrorCode('set',false,'getCount');
                errorMessage(`${status}:${responseJSON && responseJSON.message ? responseJSON.message : status == 403 ? 'invalid api_key in is passed in request headers' : ''}`); // note1
                break;
        }
        self.$el.removeClass('user-on');
    },
    /**
     * TODO: The error messages will be displayed to the error modal and the data coming from WCS service with 200 status code
     * @param {wcs error object} errorObj 
     * @param {will be displayed based on the variable name(i.e.,this.errorDebuggerON) which is defined in the config for testing purpose} isDisplayOnPage 
     */
    APIErrorKeyHandling(errorKey) {
        switch (errorKey) {
            case "stale":
                const getErrorCount = self.handling500ErrorCode('set',false,'getCount');
                errorMessage(`200:Stale:${getErrorCount}`);
                PublishLoyaltyData(0, "data failed");
                $(".popupAgRewardSignUp").attr("href",$(".activated-user>a").attr("href"));
                $(".not-activated-user").addClass("user-inactive");
                self.$el.removeClass('user-on');
                break;
            case "pending":
                self.storageExpirationInMin = self.storageExpirationInMin / 10;
                errorMessage(`200:Pending`);
                break;
        }
    },
    /**
     * TODO: this will track the 500 error and return the total error count based on action type
     * @param {action type (i.e., set / get)} action 
     */
    handling500ErrorCode(action, doNotRetry, cbCount){
        const storageName = 'CUSTOMER_ERROR';
        let getStorage = localStorage.get(storageName);
        let getErrorCount = getStorage ? parseInt(getStorage.retryCount || 0) : 0;
        let expiryTime = 5 ;
        if(action === 'delete'){
            if(getStorage){
                localStorage.delete(storageName);
                errorMessage(`200:Retry(${getErrorCount+1}) CAll Success`);
            }
        } else if(getErrorCount >= parseInt(self.maxErrorCheck)){
            return false;
        } else if(action === 'set'){
            getErrorCount++;
            localStorage.set(storageName,{"retryCount":doNotRetry ? self.maxErrorCheck : getErrorCount}, expiryTime);
        }
        return cbCount ? getErrorCount : true;
    },
    /**
     * TODO: This will check and confirm any 500 errors are present or user is in-session and based on that will proceed with the API call. 
     */
    isAllSetForAPICall() {
        const $ele = self.$el.find("#loyalityComponentContainer");
        const isErrorReached = self.handling500ErrorCode('get');
        if(!isErrorReached){
            localStorage.delete(self.storageCookieName);
            $ele.removeClass('req-loading').addClass('no-data');
            self.$el.removeClass('user-on');
            // (!self.isUserInSession || self.isUserInSession == "false") && warningMessage('userSessionDisabled', "User Session Disabled.");
            !isErrorReached && errorHandling('500Error', `Retry:Max call reached`);
            return false;
        }
        return true;
    },
    /**
     * TODO: Binding the click events
     */
    bindRewardsActionEvents(){
        window.global.eventBindingInst.bindLooping({
            "click .apply-promo-code": "redeemPromoCode",
            "click .remove-promo-code": "removePromoCode",
        }, self);
    },
    /**
     * TODO: check the test cases before proceeding the redeem API call
     */
    checkBeforeRedeem(){
        if($("#minishopcart_total").html().trim()==""){
            let tooltipMsg = $(".reward-tooltip:eq(0)").data('content');
            errorModal('info', tooltipMsg);
            return tooltipMsg;
        } else if(self.$el.hasClass('redeem-freezed')){
            errorModal('info', maxCntMsg);
            return maxCntMsg;
        }
        return true; 
    },
    updatePromoCode(type, ele){
        const promoCode = $(ele).data('promoCode')
        if(type== "set"){
            self.cartPayload.push(promoCode);
        } else if (type == "delete"){
            self.cartPayload.pop(promoCode);
        }
        const $parentEle = $(ele).closest(".col-inner-content");
        $parentEle.addClass("req-loading-sm");
        const payload = apiSettings('crmHeader')['updatePromoCode'];
        let val = "";
        const promoCodes = _.uniq(self.cartPayload);
        promoCodes.forEach((item,index) => {val+=`${index == 0 ? '{' : ''}PromotionID : ${item}${index == promoCodes.length - 1 ? '}' : ','}`})
        payload.data = {"attributes": {"Rewards": val || "{}"}};
        request(payload).then(data => { 
            if(!_.isEmpty(data.attributes)){
                self.promoCodeStatus(type,promoCode);
                if(type== "set"){
                    $parentEle.addClass("redeemed-on").removeClass("req-loading-sm");
                    trackingParams("redeem","success", ele);
                }
                else if (type == "delete"){
                    $parentEle.removeClass("redeemed-on").removeClass("req-loading-sm");
                    trackingParams("removeRedeem","success", ele);
                }
                return;
            }
        }).catch(error => {
            window.global.errorHandling.cartAPI(error);
            $parentEle.removeClass("req-loading-sm");
            trackingParams(type== "set" ? "redeem" : "removeRedeem",error.message, ele);
        })
    },
    /**
     * TODO: This will trigger when click on redeem button.
     * @param {redeem element} ele 
     * @param {on click event} evt 
     */
    redeemPromoCode(ele,evt){
        evt.preventDefault();
        let isReadyToRedeem = self.checkBeforeRedeem();
        if(isReadyToRedeem!=true){
            trackingParams("redeem",isReadyToRedeem, ele);
            return;
        }
        self.updatePromoCode("set", ele);
    },
    /**
     * TODO: This will trigger when click on remove redeem (i.e., cross mark icon) button.
     * @param {applied element} ele 
     * @param {on click event} evt 
     */
    removePromoCode(ele,evt){
        evt.preventDefault();
        self.updatePromoCode("delete", ele)
    },
    /**
     * TODO: Applying the carousel when user has more than 3 promo codes
     */
    applyCarouselForRewards(){
        let minItemsToApply = (deviceName == "desktop" || deviceName == "tablet") ? 3 : 2;
        let $curEle = $(".rewards-bar .rewards-content");
        let hasCarousel = $curEle.hasClass('slick-initialized');
        if(!$curEle.length || deviceName == "mobile" || deviceName == "mobilePortrait" || $curEle.find('li').length <= minItemsToApply){
            hasCarousel && $curEle.slick('unslick');
            return;
        } else if (!hasCarousel){
            sliderInit($curEle, {
                "autoPlay": false,
                "arrows": true,
                "dots": true,
                "slideToShow": 3,
                "slideToShowMd":2,
                "slideToScroll": minItemsToApply,
                "rewind": true
            });
            $curEle.slick('refresh');
        }
    },
    /**
     * TODO: binding the tooltip event
     */
    applyTooltip(){
        let $tooltipEle = $('.reward-tooltip');
        if($tooltipEle.length){
            let is_touch_device = ("ontouchstart" in window) || window.DocumentTouch && document instanceof DocumentTouch;
            $tooltipEle.popover({
                trigger: is_touch_device ? "click" : "hover",
                template: '<div class="popover rewards-popover"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div></div>'
            });
        }
    },
    /**
     * TODO: binding the accordion event (i.e., show / hide Rewards)
     */
    applyAccordionForRewards(){
        let $targetEle = self.$el.find(".rewards-btn");
        let $targetContainer=$("#collapsepromoBar");
        $targetEle.on('click',function(){
            let $ele = $(this);
            if(!$ele.hasClass('state-on')){
                $targetContainer.css({'max-height':'1000px','display':'none','overflow':'visible'}).addClass("accordion-in").stop(true).slideDown({duration:400,complete:function(){$ele.addClass('state-on');}});
                $('html, body').stop(true).animate({scrollTop:$ele.data("offsetTop")},400);
            }else{
                $targetContainer.stop(true).slideUp({duration:400,complete:function(){$ele.removeClass('state-on');$targetContainer.removeClass("accordion-in");$(this).css({'display':'flex','max-height':0,'overflow':'hidden'})}});
            }
        });
    },
    /**
     * TODO: updating the UI for promotion users like tot cnt showing in profile icon / showing notification next to user name, etc.
     * @param {ConsumerPromotion Object} promoObj 
     */
    updateRewardsUserUI(promoObj){
        let isAccordionDisplay= promoObj.length> 1;
        $(".activated-user .rewards-total").html(promoObj.length);
        $(".reward-available-txt").removeClass("hide");
        self.applyTooltip();
        self.$el.addClass("rewards-header-on");
        self.bindRewardsActionEvents();
        if(isAccordionDisplay){
            self.applyCarouselForRewards();
            self.applyAccordionForRewards();
        }
        self.textTruncate();
    },
    /**
     * TODO: This will truncate the promo description lines based on the config (config name : this.textLimit)
     */
    textTruncate() {
        const $ele = self.$el.find(`.enhanced-container:not('truncated-active')`);
        if(!$ele.length) return;
        let title;
        _.each($ele, (item, indx) => {
            // $(item).addClass('truncated-active');
            _.each($(item).find(".default-pts"),subItem =>{
                $(subItem).parent('span').css('height', 'auto');
                title = $(subItem).data('title');
                title && $(subItem).html(title);
                truncateInit(subItem, self.textLimit[$(item).data('barName')][deviceName]);
            })
        });
    },
    /**
     * TODO: Binding resize event
     */
    bindingResize() {
        let resizedDeviceName;
        $(window).resize(
            _.debounce(() => {
                resizedDeviceName = getDeviceName();
                if (deviceName != resizedDeviceName) {
                    deviceName = resizedDeviceName;
                    if(self.userRewards){
                        self.applyCarouselForRewards();
                        self.textTruncate();
                    } 
                }
            }, 500)
        );
    },
    /**
     * TODO: The promo code status will be updated when click on redeem / removing promo code. used for updating the local storage
     * @param {action type that is set / get / delete} type 
     * @param {ConsumerPromotion Data} promoData 
     */
    promoCodeStatus(type,promoData) {
        if(type == "get"){
            self.promoCodeLists = {};
            _.each(promoData,(item) => {
                self.promoCodeLists[item.RewardsPromoCode] = item.RedeemedStatus == "REDEEMED" ? "applied" : "redeem";
            });
        } else if(type == "set"){
            self.promoCodeLists[promoData] = "applied";
        } else if(type == "delete"){
            self.promoCodeLists[promoData] = "redeem";
        }
        let isPromoFreezed = (_.keys(self.promoCodeLists).filter(i => self.promoCodeLists[i] === "applied").length >= self.maxRedeemCode)
        return isPromoFreezed ? this.$el.addClass("redeem-freezed") : this.$el.removeClass("redeem-freezed");
    },
    /**
     * TODO: set the schema for reward and loyalty users
     * @param {LoyaltyRewards response object} res 
     */
    getSchemaFields(res) {
        let rewardsObj = (typeof res.ConsumerPromotions !== 'undefined' && res.ConsumerPromotions != null) ? res.ConsumerPromotions : [];
        let schemaName = rewardsObj.length ? "rewards" : "loyality";
        self.userRewards = schemaName=="rewards" && true;
        let schema = [
            {
                loyality: {
                    progressBar: true,
                    userPts: true,
                    nextRewardPts: true,
                    nextBerry: true,
                    recentExpirePromo: false,
                    showRewardsBtn: false
                },
                rewards: {
                    progressBar: true,
                    userPts: false,
                    nextRewardPts: false,
                    nextBerry: false,
                    recentExpirePromo: true,
                    showRewardsBtn: rewardsObj.length>1,
                    maxPromoReached : self.promoCodeStatus('get',rewardsObj)
                }
            }
        ];
        return schema[0][schemaName] || {};
    },
    /**
     * TODO: This will call the getLoyaltyRewards API. 
     * @param {logged in user name} UserName 
     */
    getAPIData() {
        const $ele = self.$el.find("#loyalityComponentContainer");
        const isDataExists = dataConfigs.clearStorage != "true" && self.checkDataAlreadyInStorage();
        if (isDataExists) {
            self.compareStatusWithGetCart(isDataExists, (updatedData) =>{
                self.APICallBack(updatedData, $ele);
            });
            return;
        }
        const payload = apiSettings('crmHeader')['consumerLoyality']();
        payload.data=JSON.stringify({
            "shopifyCustomerID": self.authenticationId,
            "identityAccessToken": self.identityToken
        });
        request(payload).then((data, request) => {
            let responseHeader = request.getResponseHeader('x-token-status');
            if(responseHeader == "stale"){
                self.APIErrorKeyHandling("stale");
                $ele.removeClass('req-loading').addClass('no-data');
                return;
            } else if(responseHeader == "pending"){
                self.APIErrorKeyHandling("pending");
            }
            self.handling500ErrorCode("delete");
            data = typeof data == "object" ? data : JSON.parse(data);
            const { consumerLoyalty } = data;
            if (_.isEmpty(consumerLoyalty)) {
                $ele.removeClass('req-loading').addClass('no-data');
                data.firstName && localStorage.set(self.storageCookieName, data,self.storageExpirationInMin);
                PublishLoyaltyData(data);
                self.$el.removeClass('user-on');
                return;
            }
            data['userName'] = data.firstName;
            data['userType'] = consumerLoyalty.LoyaltyTierCode == "" ? "non-agr-user" : "agr-user";
            data['authenticationId'] = self.authenticationId;
            $.extend(consumerLoyalty, self.tierPointsCalculation(consumerLoyalty));
            if (Object.keys(errors).length) {
                $ele.removeClass('req-loading');
                return;
            }
            localStorage.set(self.storageCookieName, data,self.storageExpirationInMin);
            self.compareStatusWithGetCart(data, (updatedData) =>{
                self.APICallBack(updatedData, $ele);
            });
            typeof setPromoCodeList == "function" && setPromoCodeList();
        }).catch(error => {
            self.APINetworkErrorHandling(error);
            $ele.removeClass('req-loading').addClass('no-data');
        })
    },

    /**
     * TODO: get the user name, authenticationId (i.e., wc_authentication cookie) and the user type (ie., Guest / Non AGR / AGR users)
     */
    checkLoggedInState() {
        let {userType,SCID, IAT} = getUserCookie();
        warningMessage("USERTYPE","USERTYPE:"+userType);
        self.userType = userType;
        self.authenticationId = SCID || false;
        self.identityToken = IAT || false;
        self.$el.addClass(`${(userType == "agr-user" || userType == "unidentified-user") ? 'user-on' : 'user-off'}`);
        if((userType == "agr-user" || userType == "unidentified-user") && self.isAllSetForAPICall()) self.getAPIData(SCID);
        else{
            PublishLoyaltyData(0, "guest-user");
            localStorage.delete(self.storageCookieName);
        }
    },
    waitForGetCart(callBack) {
        window.setTimeout(() => {
            if (!loading) {
                callBack(self.getCartData);
            } else {
                self.getCallCheckCnt++;
                if (self.getCallCheckCnt > 10) {
                    callBack(0);
                    return;
                }
                self.waitForGetCart(callBack);
            }
            console.log(`GetCart call :: retring ${self.getCallCheckCnt}`);
        }, 500);
    },
    callGetCartAPI(cb){
        if(loading){
            self.waitForGetCart(cb);
        } else if (self.getCartData){
            cb(self.getCartData);
        } else {
            loading = true;
            cartAPI.callGetCartAPI(apiSettings("getMiniCart"), (data)=>{
                !data && errorHandling('GetCartError', `Get Cart Service Failed..`);
                self.getCartData = data;
                loading = false;
                typeof cb == "function" && cb(data);
            });
        }
    },
    init() {
        if (Object.keys(errors).length) {
            return;
        }
        self.callGetCartAPI();
        self.getErrorJSONCodes();
        self.bindingHelperFn();
        self.checkLoggedInState();
        self.bindingResize();
    }
}

/**
 * TODO: generic function to display the error messages in modal
 * @param {error type (ie., error,info, warning)} errType 
 * @param {error message to display} msg 
 */
const errorModal = (errType, msg) => {
    if(!errType || !msg) return;
    exceptionHandler(errType, msg,self.errorActiveClass,3000);
};
/**
 * TODO: generic function to display the error messages in console
 * @param {custom key} key 
 * @param {error message to print} message 
 */
const errorHandling = (key, message) => {
    if(key) errors[key] = true;
    console.log(`%c CSVC:${message}`, "background: red; color:white; font-weight:bold");
    return "NA";
};
/**
 * TODO: generic function to display the error messages in console
 * @param {custom key} key 
 * @param {error message to print} message 
 */
 const errorMessage = message => {
    console.log(`%c CSVC:${message}`, "background: red; color:white; font-weight:bold");
    return "NA";
};
/**
 * TODO: generic function to display the warning messages in console
 * @param {custom key} key 
 * @param {error message to print} message 
 */
const warningMessage = (key, message) => {
    console.log(`%c CSVC:${message}`, "background: #333; color:white; font-weight:bold");
    return "NA";
};
/**
 * TODO: tracking all the user actions like redeem / remove,.. 
 */
const trackingParams = (actionName, msg,ele) => {
    msg = (typeof msg == "object" && msg.errors[0]) ? (self.errorJSONCodes[msg.errors[0]['errorKey']] || self.errorJSONCodes[`DYN.${msg.errors[0]['errorKey']}`]) : msg;
    if(actionName == "removeRedeem"){
        typeof removePromoCodeClick == "function" && removePromoCodeClick(ele,msg);
    } else if (actionName == "redeem"){
        typeof trackRedeemClick == "function" && trackRedeemClick(ele, msg);
    }
};
let self, dataConfigs,deviceName,updateStorageOnClose = false, maxCntMsg,loading = false;;
const errors = {};

const ajaxBinding = new ajaxRequest();
const request = ajaxBinding.ajaxCallWithoutErrorHandling;
const errorMessageException = ajaxBinding.errorMessageException;
// const cookieErrors = ajaxBinding.cookieExpiryErrors;
const handleBarsHelperInst = window.global.handleBarsHelperInst;
const templateInit = window.global.handleBarTemplateInst.loadTemplate;
const cookie = window.global.browserCookie;
const apiSettings = new apiConfig().getApiConfig;
const {getUserCookie, localStorage, PublishLoyaltyData, cartAPI, clearStorageValues} = window.global;
const getDeviceName = window.global.deviceName;
const crmHeaderInit = new CRMLoyalityHeader();
