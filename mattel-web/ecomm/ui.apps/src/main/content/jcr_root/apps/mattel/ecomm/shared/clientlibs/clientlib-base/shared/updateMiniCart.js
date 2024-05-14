import ajaxRequest from '../shared/ajaxbinding';
export const updateMiniCart = (options) => {
    request.ajaxCall(options)
        .then(data => {
            if (parseInt(data.cartQuantity) > 0) {
                $("#minishopcart_total").addClass("total-bag-count");
                $("#minishopcart_total").html(parseInt(data.cartQuantity));
                options.finalCall == undefined && $(".root").removeClass("request-pending");
            }
        })
        .catch(error => {
            $(".root").removeClass("request-pending");
            console.log(error)
        });
}
export const updateMiniCartQty = (miniCartQty) => {
    if (parseInt(miniCartQty) > 0) {
        $("#minishopcart_total").addClass("total-bag-count");
        $("#minishopcart_total").html(parseInt(miniCartQty));
        $(".root").removeClass("request-pending");
    }
    $(".root").removeClass("request-pending");
}
let request = new ajaxRequest();