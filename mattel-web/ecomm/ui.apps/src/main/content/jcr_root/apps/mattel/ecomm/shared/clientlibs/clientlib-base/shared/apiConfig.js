import { getCookie } from './browserCookie';

export default class apiConfig {
    constructor() {
        self = this;
        this.apiDomain = 'https://mdev.americangirl.com:443/wcs/resources/store';
        this.langId = document.getElementById('langId') != null ? document.getElementById('langId').value : '-1';
        this.catalogId =
            document.getElementById('catalogId') != null ? document.getElementById('catalogId').value : '10601';
        this.storeName =
            document.getElementById('siteKey') != null ? (document.getElementById('siteKey').value || "ag_en") : 'ag_en';
        this.domainName =
            document.getElementById('siteKey') != null ? (document.getElementById('siteKey').value || "ag_en") : 'ag_en';
        this.partno = $("#partnumber").val();

        this.config = {
            orders: {
                url: `//${window.location.host}/bin/requesthandler.web.recentOrderHistory.json?storeId=${
                    this.storeName
                }&domainId=${this.domainName}`,
                type: 'get',
                cache: false,
            },
            pdpproduct: function() {
                return {
                    url: `//${window.location.host}/bin/requesthandler.web.pdpproduct.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}&partnumber=${this.pdpPartNumber}`,
                    type: 'get',
                    accept: 'application/json',
                    crossDomain: true,
                    cache: false,
                };
            },
            productAPI: {
                productApiAssociation: function(partnum) {
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productavailability.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partnum}&partial=associations`,
                        type: 'get',
                        accept: 'application/json',
                        contentType: 'application/json',
                        cache: false
                    }
                },
                productAPICall:function(partnum){
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productavailability.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partnum}`,
                        type: 'get',
                        accept: 'application/json',
                        contentType: 'application/json',
                        cache: false
                    }
                }
            },
            gtAddProductToCart: {
                url: `//${window.location.host}/bin/requesthandler.web.gtaddtrunkservice.json?storeId=${
                    self.storeName
                }&domainId=${this.domainName}`,
                type: 'post',
                accept: 'application/json',
                contentType: 'application/json',
            },
            dhAddItemToCart: {
                url: `//${window.location.host}/bin/requesthandler.web.dhadditemaddon.json?storeId=${
                    self.storeName
                }&domainId=${this.domainName}`,
                type: 'post',
                accept: 'application/json',
                contentType: 'application/json',
            },
            gtTNSEmail: {
                url: `//${window.location.host}/bin/requesthandler.web.gtemailtnsservice.json?storeId=${
                    self.storeName
                }&domainId=${this.domainName}`,
                type: 'post',
                accept: 'application/json',
                contentType: 'application/json',
            },
            productInterest: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.productinterest.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'get',
                    cache: false,
                },
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updateProductInterest.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'put',
                },
            },
            giftTrunk: {
				getGiftTrunk: function(partnumber) {
					return {
						url:`//${window.location.host}/bin/requesthandler.web.productavailability.json?partnumber=${partnumber}&storeId=${
                            self.storeName
                        }&domainId=${self.domainName}`,
					   type:'get',
					   accept: 'application/json',
					   contentType: 'application/json'
					}
				}
			},
            personalInfo: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.compositePersonalInfoService.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'get',
                    cache: false,
                },
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatepersonalinfo.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'put',
                },
                updatePassword: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatepassword.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json',
                },
            },
            childInfo: {
                submit: {
                    url: `//${window.location.host}/bin/requesthandler.web.addchildinformation.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'POST',
                    accept: 'application/json',
                    contentType: 'application/json',
                },
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatechildInformation.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'PUT',
                    accept: 'application/json',
                    contentType: 'application/json',
                },
                delete: {
                    url: `//${window.location.host}/bin/requesthandler.web.deletechildInformation.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'delete',
                },
            },
            defaultAccSummary: {
                url: `//${window.location.host}/bin/requesthandler.web.compositemyaccountservice.json?storeId=${
                    this.storeName
                }&domainId=${this.domainName}`,
                type: 'get',
                cache: false,
            },
            addressDefault: {
                url: '//' + window.location.host + '/mockjson/default-address.json',
                type: 'get',
                cache: false,
            },
            errorCodes: {
                url: `//${
                    window.location.host
                }/content/dam/ag-dam/ag-global-dam/parent-site-dam/shop/mockjsons/errorapis.json`,
                type: 'get'
            },
            productInventoryStatus: {
                url: `//${
                    window.location.host
                }/content/dam/ag-dam/ag-global-dam/parent-site-dam/shop/mockjsons/productInventoryStatus.json`,
                type: 'get'
            },
            addressBook: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.addressinfo.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'get',
                    cache: false,
                },
                add: {
                    url: `//${window.location.host}/bin/requesthandler.web.adddefaultaddressservice.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'post',
                    contentType: 'application/json',
                },

                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatedefaultaddressservice.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'put',
                },
                delete: {
                    url: `//${window.location.host}/bin/requesthandler.web.deletedefaultaddressservice.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'delete',
                },
                addStandardization: {
                    url: `//${window.location.host}/bin/requesthandler.web.validateaddress.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'post',
                    contentType: 'application/json',
                },
            },
            shippingMethod: {
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatedefaultshippingservice.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'put',
                },
            },
            contactPreference: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.contactpreference.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'get',
                    cache: false,
                },
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatecontactpreference.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'put',
                },
            },
            paymentInfo: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.paymentinfo.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'get',
                    cache: false,
                },
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatepaymentinfo.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json',
                },
                delete: {
                    url: `//${window.location.host}/bin/requesthandler.web.updatepaymentinfo.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json',
                },
            },
            deletePersonalInfoAddress: {
                url: '//' + window.location.host + '/mockjson/delete-personal-info-address.json',
                type: 'get',
                cache: false,
            },
            getGeoDetection: {
                url: '//geodetection.svc.mattelcloud.com/api/geoipcode',
                type: 'get',
                cache: false,
            },
            orderDetails: {
                url: `//${window.location.host}/bin/requesthandler.web.orderHistoryDetail.json?storeId=${
                    this.storeName
                }&domainId=${this.domainName}`,
                type: 'get',
                params: '',
                contentType: 'application/json'
            },
            pdpListPrice: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.pdplistprice.json?storeId=${this.storeName}&domainId=${this.domainName}&partnumber=`,
                    type: 'get',
                    cache: false,
                },
            },
            pdpOfferPrice: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.pdpofferprice.json?storeId=${this.storeName}&domainId=${this.domainName}&partnumber=${this.partno}`,
                    type: 'get',
                    cache: false,
                },
            },
            storeAvailability: {
                update: {
                    url: `//${window.location.host}/bin/requesthandler.web.storeavailability.json?storeId=${this.storeName}&domainId=${this.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json',
                },
            },
            dollRegistration: {
                get: {
                    type: 'get',
                    cache: false,
                    accept: 'application/json',
                    url: `//${window.location.host}/bin/requesthandler.web.dollregistration.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    contentType: 'application/json',
                },
                delete: function() {
                    return {
                        type: 'POST',
                        accept: 'application/json',
                        url: `//${window.location.host}/bin/requesthandler.web.updatedollregistration.json?storeId=${
                            self.storeName
                        }&domainId=${self.domainName}`,
                        contentType: 'application/json',
                        data: JSON.stringify({
                            productID: this.productId,
                            operation: 'remove',
                        }),
                    };
                },
                add: function() {
                    return {
                        type: 'POST',
                        accept: 'application/json',
                        url: `//${window.location.host}/bin/requesthandler.web.updatedollregistration.json?storeId=${
                            self.storeName
                        }&domainId=${self.domainName}`,
                        contentType: 'application/json',
                        data: JSON.stringify({
                            whatDoll: this.productLine.value,
                            whereDoll: this.retailer.value,
                            whenDoll: `${this.purchasedMM.value}/${this.purchasedDD.value}/${this.purchasedYY.value}`,
                            whoDoll: this.recipient.value,
                            whyDoll: this.reason.value,
                        }),
                    };
                },
            },
            logoGiftCard: {
                check: {
                    accept: 'application/json',
                    type: 'POST',
                    url: `//${window.location.host}/bin/requesthandler.web.checkgiftcardmessagevalidity.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    contentType: 'application/json'
                },
                add: {
                    accept: 'application/json',
                    type: 'POST',
                    url: `//${window.location.host}/bin/requesthandler.web.addgiftcardservice.json?storeId=${
                        this.storeName
                    }&domainId=${this.domainName}`,
                    contentType: 'application/json'
                },
                getProductAPI: function(partNum) {
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productavailability.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partNum}`,
                        type: 'get',
                        accept: 'application/json',
                        contentType: 'application/json',
                        cache:false
                    }
                },
                getInventory: function(partNum) {
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productavailability.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partNum}&partial=variants`,
                        type: 'get',
                        accept: 'application/json',
                        contentType: 'application/json'
                    }
                }
            },
            signOut: {
                url: `//${window.location.host}/bin/requesthandler.web.logoff.json?storeId=${
                                                         self.storeName
                                }&domainId=${self.domainName}`,
                type: 'get',
                cache: false
            },
            loginPayload: function() {
                let pageURL = window.location.href;
                return {
                    type: 'POST',
                    accept: 'application/json',
                    url: `//${window.location.host}/bin/requesthandler.web.login.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                        logonPassword: this.password,
                        logonId: this.email,
                        redirectURL: location.search ? pageURL.replace("mode=auth", "") : pageURL
                    }),
                };
            },
            forgotPassword: function() {
                return {
                    type: 'POST',
                    accept: 'application/json',
                    url: `//${window.location.host}/bin/requesthandler.web.emailvalidationcode.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                        challengeAnswer: '-',
                        catalogId: self.catalogId,
                        langId: self.langId,
                        userNameDisplay: this.emailId.value,
                        userName: `${this.emailId.value}`,
                    }),
                };
            },
            createNewPassword: function() {
                return {
                    type: 'POST',
                    accept: 'application/json',
                    url: `//${window.location.host}/bin/requesthandler.web.resetpassword.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                        storeId: self.storeId,
                        catalogId: self.catalogId,
                        langId: self.langId,
                        URL: 'LogonForm',
                        logonId: getCookie('MATTEL_USERID'),
                        validationCode: this.validationCode.value,
                        logonPassword: this.password.value,
                        logonPasswordVerify: this.verifyPassword.value,
                    }),
                };
            },
            findMembership: function() {
                return {
                    type: 'POST',
                    accept: 'application/x-www-form-urlencoded',
                    url: `${self.apiDomain}/${
                        self.storeId
                    }/servlet/MattelCheckLoyaltyAccount?responseFormat=json&updateCookies=true`,
                    contentType: 'application/x-www-form-urlencoded',
                    data: JSON.stringify({
                        catalogId: self.catalogId,
                        langId: self.langId,
                        emailId: this.emailId.value,
                        membership_id: this.member_id.value,
                    }),
                };
            },
            registerPayload: function() {
                return {
                    type: 'POST',
                    accept: 'application/json',
                    crossDomain: true,
                    url: `//${window.location.host}/bin/requesthandler.web.registration.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                        firstName: this.firstName.value || '',
                        lastName: this.lastName.value || '',
                        email1: this.emailId.value.toLowerCase() || '',
                        logonIdDisplay: this.emailId.value.toLowerCase() || '',
                        logonIdVerifyDisplay: this.verifyEmail.value.toLowerCase() || '',
                        logonId: this.emailId.value.toLowerCase() || '',
                        logonIdVerify: this.verifyEmail.value.toLowerCase() || '',
                        logonPassword: this.password.value || '',
                        logonPasswordVerify: this.verifyPassword.value || '',
                        birth_month: this.childBirthMonth.value || '',
                        birth_date: this.childBirthDay.value || '',
                        birth_year: this.childBirthYear.value || '',
                        phone1: this.PhoneNumber.value || '',
                        receiveEmail: this.emailOptin.checked ? true : false,
                        demographicField5: this.termConditions.checked ? 'on' : 'off',
                        sourceName: 'WebCreateAccount',
                        catalogId: self.catalogId,
                    }),
                };
            },
            checkMembershipId: function() {
                return {
                    type: 'POST',
                    accept: 'application/json',
                    crossDomain: true,
                    contentType: 'application/json',
                    url: `//${window.location.host}/bin/requesthandler.web.linkrewardmember.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    data: JSON.stringify({
                        emailId: this.emailId.value.toLowerCase(),
                        membershipId: this.member_id.value,
                    }),
                };
            },
            registerMembershipId: function(form, rewardData) {
                return {
                    type: 'POST',
                    accept: 'application/json',
                    crossDomain: true,
                    url: `//${window.location.host}/bin/requesthandler.web.registration.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    contentType: 'application/json',
                    data: JSON.stringify({
                        firstName: rewardData.firstName ? rewardData.firstName : '',
                        lastName: rewardData.lastName ? rewardData.lastName : '',
                        email1: form.emailId ? form.emailId.value : '',
                        logonIdDisplay: form.emailId ? form.emailId.value : '',
                        logonIdVerifyDisplay: form.emailId ? form.emailId.value : '',
                        logonId: form.emailId ? form.emailId.value : '',
                        logonIdVerify: form.emailId ? form.emailId.value : '',
                        logonPassword: form.password ? form.password.value : '',
                        logonPasswordVerify: form.verifyPassword ? form.verifyPassword.value : '',
                        birth_month: form.childBirthMonth ? form.childBirthMonth.value : '',
                        birth_date: form.childBirthDay ? form.childBirthDay.value : '',
                        birth_year: form.childBirthYear ? form.childBirthYear.value : '',
                        registrationFromRewards: true,
                        rewardsEmailId: form.emailId ? form.emailId.value : '',
                        phone1: rewardData.PhoneNumber ? rewardData.PhoneNumber : '',
                        receiveEmail: form.rewardsSendMeEmail ? form.rewardsSendMeEmail.checked : false,
                        catalogId: self.catalogId,
                        usersId: rewardData.usersId ? rewardData.usersId : '',
                    }),
                };
            },
            getGuestIdentity: {
                url: `//${window.location.host}/bin/requesthandler.web.guestidentity.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                type: 'post',
            },
            getMiniCart: {
                url: `${$("#shopify-domain").val() || 'https://dev-shop.americangirl.com'}/cart.json`,
                dataType: 'jsonp',
                type: 'get',
                accept: 'application/json',
                contentType: 'application/json'
            },

            products: {
                getSAndP: function() {
                    let url = $('#snpAccountUrl').val() || '//stage-sp1004f984.guided.ss-omtrdc.net/';
                    let resultPagecategory = self.getParamValueFromURL('searchCategory');
                    if (resultPagecategory == "article") {
                        url = $("#snpArticleAccountUrl").val() || url;
                    }
                    let params =
                        url.indexOf('?') == -1 ?
                        this.queryString :
                        this.queryString.replace('??', '&').replace('?', '&');

                    let FisherPriceparams =
                        url.indexOf('??') ? this.queryString.replace('??', '?') : this.queryString;

                    return {
                        type: 'get',
                        accept: 'application/json',
                        crossDomain: true,
                        url: $('#isFPPage').val() == "true" ? `${url}${FisherPriceparams}` : `${url}${params}`,
                    };
                },
                getESpot: function() {
                    return {
                        type: 'get',
                        accept: 'application/json',
                        crossDomain: true,
                        url: `//${window.location.host}/bin/getproductgridpromobannerconfig?currentPagePath=${this.queryString}`,
                    };
                },
                getCategoryList: function() {
                    return {
                        type: 'get',
                        accept: 'application/json',
                        crossDomain: true,
                        url: $('#isFPPage').val() == "true" ? `//${window.location.host}/bin/getAllFPCategories?pagePath=${this.queryString}` : `//${window.location.host}/bin/getAllCategories?pagePath=${this.queryString}`,
                    };
                },
            },
            searchResult: {
                getRelatedProducts: function() {
                    let url = $('#snpAccountUrl').val() || '//stage-sp1004f984.guided.ss-omtrdc.net/';
                    let resultPagecategory = self.getParamValueFromURL('searchCategory') || "product";
                    if (resultPagecategory == "product") {
                        url = $("#snpArticleAccountUrl").val() || url;
                        return {
                            type: 'get',
                            accept: 'application/json',
                            crossDomain: true,
                            url: `${url}?do=related&i=1&count=${this.itemsToShow}&q=${this.queryString}&search=${this.categoryName}`,
                        };
                    } else {
                        return {
                            type: 'get',
                            accept: 'application/json',
                            crossDomain: true,
                            url: `${url}&do=related&i=1&count=${this.itemsToShow}&q=${this.queryString}&search=${this.categoryName}`,
                        };
                    }
                },
            },
            quickView: {
                details: function(partNumber) {
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productwrapper.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partNumber}`,
                        type: 'get',
                        cache: false
                    }
                },
                swatchInventory: function(partNumber) {
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productavailability.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partNumber}&partial=variants&view=core`,
                        type: 'get',
                        accept: 'application/json',
                        contentType: 'application/json',
                        cache: false
                    }
                },
                offerPrice: {
                    url: `//${window.location.host}/bin/requesthandler.web.pdpofferprice.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}&partnumber=`,
                    type: 'get'
                },
                getProductType: function(partNumber) {
                    return {
                        url: `//${window.location.host}/bin/requesthandler.web.productwrapper.json?storeId=${self.storeName}&domainId=${self.domainName}&partnumber=${partNumber}&partial=core`,
                        type: 'get',
                        accept: 'application/json',
                        contentType: 'application/json',
                        cache: false
                    }
                }

            },
            skuCompPrice: {
                get: {
                    url: `//${window.location.host}/bin/requesthandler.web.collectiveofferprice.json?storeId=${
                        self.storeName}&domainId=${self.domainName}`,
                    type: 'POST',
                    accept: 'application/json',
                    contentType: 'application/json'
                },
                swatchInventory: {
                    url: `//${window.location.host}/bin/requesthandler.web.collectiveinventoryservice.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json'
                }
            },
            addToBag: {
                addProductToBag: {
                    url: `${$("#shopify-domain").val() || 'https://dev-shop.americangirl.com'}/cart/add.json`,
                    dataType: 'jsonp',
                    type: 'post',
                },
                addProductToBagPackage: {
                    url: `//${window.location.host}/bin/requesthandler.web.addpackagetocart.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json'
                },
                addAddons: {
                    url: `//${window.location.host}/bin/requesthandler.web.addproductaddonstocarts.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json'
                },
                addOns: {
                    url: `//${window.location.host}/bin/requesthandler.web.findproductaddons.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    type: 'get',
                    accept: 'application/json',
                    contentType: 'application/json',
                    cache: false
                },
                getAddToCartPageDetails: {
                    url: `//${window.location.host}/bin/requesthandler.web.pdpproduct.json?storeId=${
                            self.storeName
                        }&domainId=${self.domainName}`,
                    type: 'get',
                    cache: false
                },
                getProductAddOns: {
                    url: `//${window.location.host}/bin/requesthandler.web.findproductaddons.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}`,
                    type: 'get',
                    accept: 'application/json',
                    contentType: 'application/json',
                    cache: false
                }
            },

            profanityCheck: {
                startCheck: {
                    accept: 'application/json',
                    type: 'POST',
                    url: `//${window.location.host}/bin/requesthandler.web.checkgiftcardmessagevalidity.json?storeId=${
                    this.storeName
                }&domainId=${this.domainName}`,
                    contentType: 'application/json'
                }
            },
            fedex: {
                url: `//${window.location.host}/bin/fedex?storeId=${self.storeName}&domainId=${self.domainName}`,
                type: 'POST',
                contentType: 'application/json'
            },
            employeeValidation: function() {
                return {
                    type: 'GET',
                    accept: 'application/json',
                    url: `//${window.location.host}/bin/requesthandler.web.validateemployee.json?storeId=${
                        self.storeName
                    }&domainId=${self.domainName}&employeeId=${this.employeeId}&catalogId=${self.catalogId}&userId=${this.userId}`,
                    contentType: 'application/json',
                };
            },
            submitEmailLightbox: {
                url: $("#consumerinfoApiDetails").attr("data-api-url"),
                type: 'POST',
                contentType: 'application/json',
                headers: { 'api_key': $("#consumerinfoApiDetails").attr("data-api-key") }
            },
            crmHeader: {
                consumerLoyality: function() {
                    return {
                        type: 'POST',
                        accept: 'application/json',
                        contentType: 'application/json',
                        url: `//${window.location.host}/bin/requesthandler.web.customerdetails.json?storeId=${self.storeName}&domainId=${self.domainName}`,
                        cache: false
                    }
                },
                updatePromoCode: {
                    url: `${$("#shopify-domain").val() || 'https://dev-shop.americangirl.com'}/cart/update.json`,
                    dataType: 'jsonp',
                    type: 'post',
                    accept: 'application/json',
                    contentType: 'application/json'
                },
            },
            cartProductMiniCarousel: function() {
                let cartUrl = $('#snpAccountUrl').val();
                let partNumbers = window.global.browserCookie.getCookie("products_in_cart");
                return {
                    type: 'get',
                    accept: 'application/json',
                    crossDomain: true,
                    url: cartUrl + '&PartNumber=' + partNumbers
                };
            }
        };
    }

    getApiConfig(reqConfig) {
        return self.config[reqConfig];
    }
    getParamValueFromURL(attrName) {
        const urlParam = urlName => {
            urlName = urlName.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            let regexS = "[\\?&]" + urlName + "=([^&#]*)";
            let regex = new RegExp(regexS);
            let results = regex.exec(window.location.search);
            if (results) return results[1];
            else return results;
        };
        return urlParam(attrName);
    }
}
let self;