import {
    inputCheckboxValidation,
    DHFormSession,
    helperTextEmpty
} from '../shared/dh-form-validation';
((() => {
    class dhSubTreatment {
        constructor() {
            self = this;
            window.dh.eventBinding.bindLooping(self.bindingEventsConfig(), self);
            //self.onLoadVal();
        }
        bindingEventsConfig() {
            let eventsArr = {
				'click .dh-sub-treatment-block .dh-btn-span>a': 'prevSlide',
                'change .dh-sub-treatment-block input[type="radio"], .dh-sub-treatment-block input[type="checkbox"]': 'ChkValidation',
                'click .dh-sub-treatment-block .dh-next-btn': 'nextSlide',
                'click .dh-sub-treatment-block input[type="radio"]': 'inputRadioDeselect'
            };
            return eventsArr;
        }
        init(){
            self.onLoadVal();
            helperTextEmpty();
        }
        onLoadVal(){

        }

        nextSlide(ele) {
            let displayObj = self.storingEnteredData(ele);
            DHFormSession('subTreatmentData', displayObj, 'set');
            let specialExtrasDHSession =  DHFormSession('specialExtrasData', '', 'get');
            if(specialExtrasDHSession){
            	DHFormSession('specialExtrasData', '', 'delete');
            }
        }
        
        inputRadioDeselect(ele){
            const $ele = $(ele),
            datasets = $(ele.form).find('.dh-st-option-group')[0].dataset;
            if(datasets.min == 0){
            	$("input[name='"+$(ele).attr("name")+"']:radio").not(ele).removeData("chk");
            	$(ele).data("chk",!$(ele).data("chk"));
            	$(ele).prop("checked",$(ele).data("chk"));
            }
        }
		
		prevSlide(ele) {
            let isBackBtn = $(ele).find(".dh-back-btn").length;
            if(isBackBtn){
                DHFormSession('subTreatmentData', '', 'delete');
            }
        }

        storingEnteredData(ele) {
            
            let $inputEle = $(".dh-sub-treatment-block form");
            let subObj = {};
            if (!$inputEle.length) {
                console.log("input tags not found..");
                return;
            }
            const $ele = $(ele);
            $inputEle = $inputEle.find('input:not([type="hidden"])');
            let filterChk = $inputEle.filter(":checked"),
                inputType = $inputEle.attr("type");
            _.each(filterChk, (item, index) => {
                    let $eles = $(item),
                    pageNo = index+1,
                    inputTitle = $eles.next('label').text();
                if (inputType == "checkbox") {
                        if (filterChk.is(':checked')) {
                            subObj[`subtreatment${pageNo}`] = {
                                skuId: item.value,
                                skuName: inputTitle,
                                type: 'checkbox'
                            }
                        }
                }else if (inputType == "radio") {
					let sku_staus = $inputEle.attr('sku-status');
                    subObj[`subtreatment${pageNo}`] = {
                        skuId: item.value,
                        skuName: inputTitle,
                        type: 'radio',
                        childSku: sku_staus
                    }
                }
  
                // if (inputVal != '') {
                //     obj[`subtreatment${pageNo}`] = {
                //         index: subObj
                //     };
                // }
                return !$ele.hasClass('active');
            });
            return subObj;
        }
        ChkValidation(ele, evt) {
            const $ele = $(ele),
                inputType = $ele.attr('type'),
                datasets = $(ele.form).find('.dh-st-option-group')[0].dataset;
            if (inputType == "checkbox" || inputType == "radio") {
                inputCheckboxValidation($ele, datasets.max, datasets.min);
            }
        }
    }

    let self;
    const dhSubTreatmentInst = new dhSubTreatment();
    dhSubTreatmentInst.init();
})());