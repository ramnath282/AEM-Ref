export const trackingGridScroll = params => {
	if (typeof utag == 'undefined') {
		console.log('utag js to be added.');
		return;
	}
	let config = {
		event_action: 'products scroll tracking',
		event_action_type: 'scroll',
		event_detail: params.categoryName, //'Product Category',
		event_detail_sub: `plp${params.columnVal}:${params.viewedCnt}:${params.totalCnt}`,
		location_name: 'products scroll',
	};
	console.log(config);

	utag.link(config);
};
export default {};
