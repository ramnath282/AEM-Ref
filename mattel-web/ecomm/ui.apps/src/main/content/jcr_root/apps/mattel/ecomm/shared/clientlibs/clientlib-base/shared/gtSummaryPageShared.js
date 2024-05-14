/* fn.loadTemplate("#templateId", "#containerId", dataObj, "replace/append choice"); */
export class gtSummaryPageShared {
    constructor() {   
    	self=this;
    }
    

    adjustBundleLoad($ele)
	{
    	
		let selectedSkuId=$ele.attr('data-sku-id');
		let categoryBundleList2LoadCountValue=$('#categoryBundleList2').attr('data-intial-productCount')
		$ele.parents('.option').next().find(".choice").removeClass('selectionMade');
		$ele.parents('.option').next().find("[data-sku-id='" + selectedSkuId +"']").addClass('selectionMade');		
		if($ele.parents('.option').next().find(".choice").not('.hide').not('.selectionMade').length < categoryBundleList2LoadCountValue){
			$ele.parents('.option').next().find(".choice.hide:first").removeClass('hide');			
		}
	}
	optionSelection(ele){
		const $ele = $(ele).closest('.choice'); 
		let choiceValue = $ele.attr('data-choice');
		let offsetValue = $ele.parents('.option').offset().top;

		if($ele.attr('data-choice')	== "Hearing Aid: Yes")
			{
			let hearAidValue = $(ele).attr('data-choice');
			$ele.parents('.option').find('.selected .name').html(hearAidValue);
			}
		else {
			$ele.parents('.option').find('.selected .name').html(choiceValue);
		}
		

				$('html, body').animate({
				scrollTop: offsetValue
				}, 600);

				//Slide-out option-details
				$ele.parents('.option').find('.selected').fadeIn(300);
				$ele.parents('.option-details').css({
					height: 0,
					opacity: 0,
					display: 'none'
				});
				//Remove already selected product in bundle 2			
				//Adjust bundle load
				if($ele.parent().attr("id") == "categoryBundleList1")
				{
					self.adjustBundleLoad($ele);
				}
				//Enable next option
				if($ele.attr("id") == "smallTrunk")
				{
					$ele.parents('.option').next().next().removeClass('disabled');
					$ele.parents('.option').next().removeClass('disabled');
				}    			
				else
				{
					if($ele.parents('.option').next())
					{
						if($ele.parents('.option').next().css('display') == 'none'){
							if($ele.parents('.option').next().next())
								$ele.parents('.option').next().next().removeClass('disabled');
						}
						else{
							$ele.parents('.option').next().removeClass('disabled');
						}
					}
					
				} 

		}
    
}
let self;