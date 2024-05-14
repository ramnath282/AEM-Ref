let customerSegment;
export const userCategory = (quickview) => {
    if($('.btn-add-to-bag').length) {
        customerSegment = customerSegment || window.global.getUserCookie().customerSegment;
        let customerType = (customerSegment || '').toLowerCase();
        let customerCata = $('.btn-add-to-bag').attr('data-custsegment') ? $('.btn-add-to-bag').attr('data-custsegment').toLowerCase() : "";
        let enableBtn = false;
        if ( customerType=='' && (customerCata == 'all' || customerCata == 'null' || customerCata == 'n' || customerCata == '')) {
            enableBtn = true;
        } else if (customerCata == 'all' || customerCata == 'null' || customerCata == 'n' || customerCata == '' ){
            enableBtn = true;
        } else if (customerCata == 'silver' &&  (customerType == 'silver' || customerType == 'gold' || customerType == 'berry')){
            enableBtn = true;
        } else if (customerCata == 'gold' && (customerType == 'gold' || customerType == 'berry')){
            enableBtn = true;
        } else if (customerCata == 'berry' && customerType == 'berry'){
            enableBtn = true;
        }
        
        if(enableBtn) {
            if(!quickview) {
                $('.btn-add-to-bag').removeAttr("disabled");
            }            
            $('.btn-add-to-bag').attr("data-permission","true"); 
        } else {
            $('.btn-add-to-bag').attr("disabled", true);  
        }
    }
}
export const isInventoryStatusValid = (inventoryStatus) => {
    if(inventoryStatus == undefined){
        inventoryStatus = "";
    } else if(!inventoryStatus){
        inventoryStatus = "noLongerAvailable";
    }
    return inventoryStatus;
}

