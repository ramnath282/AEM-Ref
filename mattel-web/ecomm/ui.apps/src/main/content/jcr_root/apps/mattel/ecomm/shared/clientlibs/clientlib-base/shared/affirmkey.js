export const manageAffirm = (bagSubtotal) => {
	// cached globals
	const $affirmOffer = $('#bagAffirm'); // parent div
	const $affirmPayment = $('#bagAffirmPayments'); // display amount
  
	/**
	 * Init
	 */
	function init() {
	  if (!$affirmOffer) return;
  
	  handleMessageDisplay(bagSubtotal);
	}
  
	/**
	 * Handle Message Displaying
	 * @param {Number} subtotal
	 */
	function handleMessageDisplay(subtotal) {
	  if (subtotal < 50) {
		$affirmOffer.addClass("hide");
	  } else {
		const paymentInstallation = (subtotal / 4).toFixed(2);
		$affirmPayment.html(`$${paymentInstallation}`);
		$affirmOffer.removeClass("hide");
	  }
  
	}
  
	return init();
  }