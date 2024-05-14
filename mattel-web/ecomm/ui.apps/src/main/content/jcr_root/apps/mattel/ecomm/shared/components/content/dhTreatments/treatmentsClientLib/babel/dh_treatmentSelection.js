import {
    DHFormSession,
    helperTextEmpty
 } from '../shared/dh-form-validation';
 ((() => {
         // defining configurations
          const config = {
                 el: '.dh-wrapper .dh-container .dh-treatment-block',
                 treatmentDataStorageName: 'DHTreatment',
                 treatmentObjForSession: {},
                 treatmentsTemplate : '#treatmentsTemplate',
                 treatmentsContainer : '.treatmentsContainer',
                 filteredTreatmentSkusArray : [],
                 dhDollType : '',
                 imageLink : '',
                 dollName : '',
                 dhTreatmentServiceJson : '',
                 helperText:'',
                 helperPopoverText:'',
                 videoLink:'',
                 dhTreatmentDataForSession : {}
             };
     
         class dhTreatmentSelection {
             constructor() {
                 self = this;
                 window.dh.eventBinding.bindLooping(this.bindingEventsConfig(), this);
                 this.DHFormData=JSON.parse(sessionStorage.getItem('DHFormData'));    
             }
             init() {
                 if(this.DHFormData){
                     $('.dh-treatment-block section').addClass('active');
                     self.dollSpecificDataLoad();
                     config.dollName= self.DHFormData.quizData.page1.inputVal;
                     config.dhDollType= self.DHFormData.quizData.page2.inputVal;
                     $(config.el).find('span.dh-doll-name').html(config.dollName);
                     config.imageLink=document.getElementById("scene7url").value || "https://mattel.scene7.com/is/image/Mattel/";
                     config.dhTreatmentServiceJson = JSON.parse(document.getElementById("treatmentDataSet").innerHTML);	
                     DHFormSession('actionType', '', 'get') ? DHFormSession('actionType', '', 'delete'):'';
                     self.filterTreatmentserviceByDollType();
                     helperTextEmpty();
                 }else{
                     let pagePath =  $('.landingPage').data('landingpagepath');
                     if(pagePath){
                         window.location.href = pagePath;
                     }
                 }
             }
           
             bindingEventsConfig() {
                 let eventsArr = {
                     'click .dh-treatment-block .sku-card': 'treatmentSelection',
                     'click .dh-treatment-block .dh-back-btn': 'prevSlide',
                     'click .dh-treatment-block .dh-next-btn': 'nextSlide',
                     
                 };
                 return eventsArr;
             }
             checkabodent(){
                 if(sessionStorage.getItem('DHFormSession') === null){
                     console.log('go to home page');
                 }
                 self.sessionData = DHFormSession('treatment', '', 'get');
                 let retrieveLastPageData = _.keys(self.sessionData);
                 console.log(retrieveLastPageData);
                 self.mappingStoredData(self.sessionData)
             }
             mappingStoredData(obj){
                 let $formEle = $(".dh-treatment-block form");
                 $formEle.find(`[data-value="${obj.skuId}"]`).closest('.sku-card').addClass('active');
                 $formEle.find(`[data-value="${obj.skuId}"]`).closest('.sku-card').find('input[type="radio"]').attr('aria-checked',true);
                 if($('.sku-card.active').length){
                     $(config.el).find('.dh-next-btn').removeClass('disabled').removeAttr('aria-disabled');
                     $(config.el).find('.next-disabled').removeClass('next-disabled');
                 }
             }
             prevSlide()
             {
                 DHFormSession('treatment', '', 'delete');
                 $('.sku-card').find('input[type="radio"]').attr('aria-checked',false);
             }
             nextSlide()
             {
                 let subTreatmentSession = DHFormSession('subTreatmentData', '', 'get');
                 if(subTreatmentSession){
                     DHFormSession('subTreatmentData', '', 'delete');
                 }
                 let specialExtrasSession = DHFormSession('specialExtrasData', '', 'get');
                 if(specialExtrasSession){
                     DHFormSession('specialExtrasData', '', 'delete');
                 }
             }
             
             dollSpecificDataLoad(){
                 let helperDiv=$(config.el).find('.helper-text');
                 config.helperText=self.DHFormData.quizData.page2.treatmentHelper;
                 config.helperPopoverText=self.DHFormData.quizData.page2.treatmentPopover;
                 config.videoLink=self.DHFormData.quizData.page2.treatmentVideo;
                 config.videoType=self.DHFormData.quizData.page2.treatmentVideoType;
                 helperDiv.prepend(config.helperText);
                 helperDiv.attr("data-content",config.helperPopoverText);
                 $(config.el).find('.cta_model_video a').attr({"href":config.videoLink,'data-video-type':config.videoType});
         
             }
             filterTreatmentserviceByDollType()
             {
                 let filteredTreatmentService=[];
                 if(config.dhTreatmentServiceJson != null && typeof config.dhTreatmentServiceJson != 'undefined'){
                     _.each(config.dhTreatmentServiceJson, (item, indx) => {
                             let treatmentAttributes=item.attributes.DHDollType;
                             let wellnessOffertype = item.attributes.DHOfferType;
                            item.treatmentDisplayOrder=item.attributes.TreatmentDisplayOrder[0].value;
                            let thumbnail=item.thumbnail.split("/").pop()
                            item.imageLink=config.imageLink+thumbnail;
                          item.marketingDesc=item.attributes.MarketingDescription[0].value;
                               if(treatmentAttributes != null){
                                 for(let k=0; k<treatmentAttributes.length;k++){	
                                     if(treatmentAttributes[k].value == config.dhDollType && wellnessOffertype[k].value != 'GiftTrunk')
                                     {
                                         filteredTreatmentService.push(item);
                                         config.filteredTreatmentSkusArray.push(item.partNumber);
                                     }
                                 }
                               }
                       });
                 }
                 filteredTreatmentService.sort(self.sortByDisplaySequence);
                 self.populateTreatmentDataTemplate(filteredTreatmentService);
             }
             populateTreatmentDataTemplate(filteredTreatmentService){   	
                self.getTreatmentsPricing((output) => {
                        if(!output) {
                            return;
                        }
                        let priceSkuMap = JSON.parse(sessionStorage.getItem("treatmentPriceData"));
                        _.each(filteredTreatmentService, (item, indx) => {
                            if(priceSkuMap.hasOwnProperty(item.partNumber))
                            {
                                item.targetPrice=priceSkuMap[item.partNumber];
                            }
                        });
                        handleBarTemplateInst.loadTemplate(config.treatmentsTemplate,config.treatmentsContainer, filteredTreatmentService, 'append');
                        self.equalContainerHeight();
                        $(config.el).find('form').removeClass('data-loading');
                        let containerClass='card-column-'+filteredTreatmentService.length;
                        $(config.treatmentsContainer).addClass(containerClass);
                        self.checkabodent();
                    });    
                }
            
             getTreatmentsPricing(cb) { 
                if(config.filteredTreatmentSkusArray.length){
                        let partNumbers =config.filteredTreatmentSkusArray;
                        let priceData = [], count =0; 
                        _.each(partNumbers,(item,index) => {
                           request.ajaxCall({
                               url:`//${window.location.host}/bin/requesthandler.web.productavailability.json?partnumber=${partNumbers[index]}&storeId=ag_en&domainId=ag_en&partial=variants`,
                               type: 'get',
                               accept: 'application/json',
                           }).then(data => {
                               let {variants} = data.product;
                               if(variants.length){
                                   priceData.push({
                                       "price" : cartAPI.getActivePriceData(variants[0].pricing),
                                       "variantid": variants[0].id,
                                       "partnumber" : variants[0].core.variant_parentpartnumber
                                   })
                                   count++;
                               }
                               if(count == partNumbers.length){
                                    let priceSkuMap=new Object();	
                                    if(priceData){   	
                                        _.each(priceData, (item, indx) => {
                                            let partnumber = item.partnumber;
                                            let price = item.price.salePrice;
                                            priceSkuMap[partnumber]=parseFloat(price).toFixed(2);
                                        });
                                    }
                                    sessionStorage.setItem("treatmentPriceData",JSON.stringify(priceSkuMap))
                                    cb(true); 
                               }
                               
                           }).catch(error =>{
                                window.global.errorHandling.PDPAPI(error);
                           });
                    });
               }
            }
            formatPricing(data) {    
                let priceSkuMap=new Object();	
                let VariantData=new Object();
                if(data){   	
                    _.each(data, (item, indx) => {
                        let partnumber = item.partnumber;
                        let price = item.price;
                        let variantID = item.variantid;
                        VariantData[partnumber] = variantID;
                        priceSkuMap[partnumber]=parseFloat(price).toFixed(2);
                    });
                }
                sessionStorage.setItem("treatmentPriceData",JSON.stringify(priceSkuMap))
                sessionStorage.setItem("treatmentVariantData",JSON.stringify(VariantData))
            }	
             sortByDisplaySequence(a,b) {
                 return (parseInt(a.treatmentDisplayOrder) < parseInt(b.treatmentDisplayOrder)) ? -1 : 1; 
             }
             treatmentSelection(ele, evt) {
                 evt.preventDefault();
                 const $ele = $(ele).closest('.sku-card');
                 $('.sku-container').find('input[type="radio"]').attr('aria-checked',false);
                 $ele.find('input[type="radio"]').attr('aria-checked',true);
                 $ele.addClass('active');
                 $ele.siblings('.sku-card').removeClass('active');
                
                 $ele.closest('.main-content-section').siblings('.dh-btn-span').find('.next-disabled').removeClass('next-disabled');
                 $ele.closest('.main-content-section').siblings('.dh-btn-span').find('.dh-next-btn').removeClass('disabled').removeAttr('aria-disabled'); 
                 let $treatmentDataElements= $ele.find('.treatmentSelect');
                 _.each($treatmentDataElements, (item) => {
                          let $ele = $(item);
                          let key = $ele.data('key');
                          let val = $ele.data('value');
                          config.dhTreatmentDataForSession[key]=val;
                      });
                 DHFormSession('treatment', config.dhTreatmentDataForSession, 'set');		
             }
             
             equalContainerHeight() {
                     var maxHeight = 0;
                     $('.sku-container').each(function () {
                         maxHeight = Math.max(maxHeight, $(this).outerHeight());
                     });
                     $('.sku-container').css({ height: maxHeight + 'px' });
                 } 
         }
         let self;
         const apiConfig = window.dh.apiConfig;
         const request = window.dh.ajaxRequest;
         const handleBarTemplateInst= window.dh.handleBarTemplateInst;
         const {cartAPI} = window.global;
         const dhTreatmentSelectionInstance = new dhTreatmentSelection();
         dhTreatmentSelectionInstance.init();
 })());