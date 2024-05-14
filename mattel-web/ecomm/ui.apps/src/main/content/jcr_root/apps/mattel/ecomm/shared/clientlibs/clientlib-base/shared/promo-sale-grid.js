const config = {
	isPromoSaleON: $('#isAuthorMode').val() || false,
	promoDate: sessionStorage.getItem('plpPreviewDate'),
};

// L for date only
// LT for time only
// L LT for date and time
const initDatePicker = activeDate => {
	let previewdate;
	$('#datetimepicker1')
		.datetimepicker({
			defaultDate: activeDate || '',
			format: 'L',
			icons: {
				time: 'far fa-clock',
				date: 'fa fa-calendar',
				up: 'fas fa-chevron-up',
				down: 'fas fa-chevron-down',
				previous: 'fas fa-chevron-left',
				next: 'fas fa-chevron-right',
			},
		})
		.on('dp.change', function(e) {
			previewdate = e.date._d;
			previewdate && sessionStorage.setItem('plpPreviewDate', previewdate);
			if( !e.oldDate || !e.date.isSame(e.oldDate, 'day')){
				$(this).data('DateTimePicker').hide();
			}
		});
	bindPreviewDateEvent();
};

const bindPreviewDateEvent = () => {
	$('#previewpagebutton').click(e => {
		let url = window.location.href;
		if (url.indexOf('?') > -1) {
			url += '&wcmmode=preview';
		} else {
			url += '?wcmmode=preview';
		}
		window.location.href = url;
	});
};

export const promoDateFilter = (res, priceObjName) => {
	if (!config.isPromoSaleON) {
		//Promo sale is off
		return res;
	}
	if (!config.promoDate) {
		//Promo sale is on
		//promo date is not available in session storage
		return res;
	}
	let selectedDate = new Date(config.promoDate);
	let filteredData = filterDateRange(res, selectedDate, priceObjName);
	return filteredData || [];
};

const filterDateRange = (res, selectedDate, priceObjName) => {
	let priceObj,
		newDisplayObj = [],
		displayPrice;
	let newObj = [];
	_.map(res, item => {
		priceObj = item.prices;
		if(!priceObj){
			return; // this condition true if marketting data comes
		}
		displayPrice = priceObj[priceObjName];
		if (typeof displayPrice == 'object') {
			return _.filter(displayPrice, (subItem, index) => {
				if (subItem.start_date && (selectedDate.getTime() >= new Date(subItem.start_date).getTime())) {
					if ((subItem.end_date && (selectedDate.getTime() <= new Date(subItem.end_date).getTime())) || !subItem.end_date) {
						if (displayPrice.length > 1) {
							newDisplayObj.push(subItem);
							if (displayPrice.length > 1) {
								priceObj.displayPrice = _.sortBy(newDisplayObj, function (o) {
									return new Date(o.start_date);
								}).reverse();
								newObj.push(item);
							}
							return;
						}
						newObj.push(item);
					}
				}
			});
		}
	});
	return newObj;
};

const filterPriceRange = (res, selectedDate) => {
	return _.filter(res, item => {
		if (item.start_date && item.end_date) {
			return new Date(item.end_date).getTime() >= selectedDate.getTime();
		} else if (item.start_date && !item.end_date) {
			return new Date(item.start_date).getTime() >= selectedDate.getTime();
		}
	});
};
const getObjectPattern = res => {
	let newArr = [];
	let keyName;
	_.map(res, (item, index) => {
		keyName = Object.keys(item)[0];
		newArr[keyName] = item[keyName];
	});
	return newArr;
};
export const checkAnyFeaturedPrice = (res, priceObjectName) => {
	if(
		res.product_type == "BundleBean" || res.product_type == "GiftCard" ||
		(res.product_type=="PackageBean" && res.itemType == "GIFT_CARD") || 
		(res.DisableQuickView && (res.product_type=="PackageBean" || res.product_type=="ItemBean" || res.product_type=="ProductBean" || res.product_type=="DynamicKitBean"))
	){
		res.disableQuickOption = true;
	}
	if (res.prices && typeof res.prices[priceObjectName] != 'object') {
		res.prices = getObjectPattern(res.prices);
	}
	let filteredData = [];
	let priceObj = res.prices;
	if (typeof priceObj[priceObjectName] == 'object') {
		if (priceObj[priceObjectName].length > 1) {
			let todaysDate = new Date();
			filteredData = _.sortBy(filterPriceRange(priceObj[priceObjectName], todaysDate), item => {
				if (!config.isPromoSaleON) {
					if (item.end_date) item.start_date = item.end_date;
				}
				return new Date(item.start_date).getTime();
			});
		}
		priceObj['displayPrice'] = filteredData.length ? filteredData : priceObj[priceObjectName];
	}
	return res;
};

// only if author mode is ON
if (config.isPromoSaleON) {
	initDatePicker(config.promoDate);
}
