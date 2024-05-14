$(document).ready(function(){
    $(".cta_useInterstitial_tracking").click(function(e){
	$(this).parent().find(".modal-component").modal("show");
    e.preventDefault();
	});
}); 
