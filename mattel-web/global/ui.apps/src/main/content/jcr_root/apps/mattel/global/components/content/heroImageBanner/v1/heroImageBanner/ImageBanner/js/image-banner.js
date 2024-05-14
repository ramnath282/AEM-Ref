$(document).ready(function(){


function setCookie (cname, cvalue, exdays=90) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    if(cname=="MATTEL_VISITOR_STATUS") {
        if(getCookie("MATTEL_VISITOR_STATUS")) {
            expires =  getCookie("MATTEL_VISITOR_STATUS_EXPIRY");
        } else {
            expires = d.toUTCString();
            document.cookie = "MATTEL_VISITOR_STATUS_EXPIRY" + "=" + expires + ";" + expires + ";path=/";
        }
    }
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";    
}

$(document).on("click", ".heroImageBanner a.btn-oval", function () {
    	setCookie("gt-product-doll-id",$('.product-info-wrapper.parent').attr('data-partnumber'));
   	    setCookie("gt-product-doll-pricing",$('.product-price .current_price').text());
    	setCookie("gt-product-doll-character",$('.hide.PDP_Product_character').attr('data-character'));
    	sessionStorage.setItem("pdp","true");
    	localStorage.setItem("gt-product-doll-name",$('.pdpproduct .product-info-wrapper h1').text());
    	setCookie("gt-product-age",$('.pdpproduct .product-info-wrapper .age-specification').text());
		setCookie("trunk-large-pricerange",$('.gt-largetrunkpricerange.hidden').text());
		setCookie("trunk-pricerange",$('.gt-smalltrunkpricerange.hidden').text());
		var imageURL= $('.product-wrapper .gt-image.hidden').text();
    	var finalImageURL= imageURL.substr(0, imageURL.lastIndexOf('/')) + "/" + $('.product-wrapper .gt-thumbnail.hidden').text();
		if($('.hide.PDP_Product_character').attr('data-character')=='Truly Baby')
        {
            setCookie("gt-product-doll-image",  imageURL);

        } else {

			setCookie("gt-product-doll-image",  finalImageURL);
        }


    });



});