import eventBinding from './eventBinding';
import { handleBarTemplate } from './templateSetter';

const isFPPage = $('#isFPPage').val() == 'true';

const datasets = isFPPage ? 'false' : document.getElementById('product-category-list').dataset;
const config = {
	el: '#product-category-list',
	parentEl: '#product-category-nav',
	defaultLoad: datasets.initialLoad || 10,
	activeCategory: window.location.pathname,
	categoryType: datasets.categoryUrl || document.querySelector('.grid-lists').dataset.categorytype,
	categoryIds: []
};

export const renderCategoryTemplate = res => {
	if ($(config.el).find('li').length || !res.length) {
		return;
	}
	const filterObj = getFilterActiveCategory(res);
	if (filterObj.length <= 1) {
		return;
	}
	const checkSkipCategory = skipCategoryData(filterObj);
	const displayObj = getSortedList(checkSkipCategory);
	handleBarTemplateInst.loadTemplate('#productCategoryTemp', config.el, displayObj);
	$(config.parentEl).removeClass('hide');
	hideNavigation();
	seeMoreToggle(true, true); // arg2 => onload
	categoryNavToggle();
};

const getSortedList = res => {
	return _.sortBy(_.sortBy(res, 'pageRanking'), 'parentElem');

};

const skipCategoryData = (filteredDatas) => {
	let categoryId, subNavCategories;
	let finalObj = [];
	_.filter(filteredDatas, (data) => {
		if (data.skipnav) {
			if (data.parentElem) {
				$("#categoryNav").addClass("hide-parent-nav");
			} else {
				categoryId = data.categoryid.split('/');
				subNavCategories = config.categoryIds[categoryId[categoryId.length - 1]] || [];
				if (subNavCategories.length) {
					_.map(subNavCategories, (subNav) => {
						subNav.skipNavChild = true;
					});
					finalObj = finalObj.concat(subNavCategories);
				}
			}
			data.skipNav = true;
		}
		finalObj.push(data);
	});
	return finalObj;
}

const getFilterActiveCategory = res => {
	if (!isFPPage) {
		let categoryArr = [],
			checkIndex,
			findCategoryName = config.categoryType.split('/'),
			slashLength = findCategoryName.length;
		let parentName = slashLength > 2 ? findCategoryName[slashLength - 2] : findCategoryName[slashLength - 1],
			newParentName = '',
			subParentElem = false,
			subParentIndex;
		_.find(res, item => {
			if (!newParentName && slashLength > 1) {
				categoryArr = item.categoryid.split('/');
				subParentIndex = categoryArr.indexOf(parentName);
				if (subParentIndex != -1 && subParentIndex < categoryArr.length - 1 && categoryArr[0] == findCategoryName[0]) {
					subParentElem = true;
					newParentName = categoryArr[subParentIndex];
				}
			}
		});
		newParentName = newParentName || findCategoryName[0];
		return _.filter(res, data => {
			categoryArr = data.categoryid.split('/');
			checkIndex = categoryArr[0] == findCategoryName[0] && categoryArr.indexOf(newParentName) != -1;
			if (categoryArr[categoryArr.length - 1] == newParentName && categoryArr[0] == findCategoryName[0]) {
				data.parentElem = true;
				// delete data.pageRanking;
				if (subParentElem) {
					data.subParentElem = true;
				}
			}
			if (data.url == config.activeCategory || ((slashLength == 1 || newParentName != findCategoryName[0]) && data.categoryid == config.categoryType)) {
				data.active = true;
				$('.active-name-mob')
					.removeClass('hide')
					.find('.active-text')
					.html(data.title);
			}
			let mapCategory = categoryArr.length == 1 || categoryArr.length == 2 ? categoryArr[0] : categoryArr[categoryArr.length - 2];
			if (config.categoryIds.hasOwnProperty(mapCategory)) {
				config.categoryIds[mapCategory].push(data);
			} else {
				config.categoryIds[mapCategory] = new Array(data);
			}

			// config.categoryIds[categoryArr[categoryArr.length - 1]].push(data);
			return (
				checkIndex &&
				(categoryArr.indexOf(newParentName) + 1 >=
					(categoryArr.length > 1 ? categoryArr.length - 1 : categoryArr.length) ||
					categoryArr[categoryArr.length - 1] == newParentName)
			);
		});
	}
};

const categoryNavToggle = () => {
	const $el = $(config.el).closest('#categoryNav');
	if (window.innerWidth > 991) {
		$el.removeClass('collapse');
		return;
	}
	$el.addClass('collapse');
};
const hideNavigation = () => {
	if ($("#categoryNav ul li:eq(0)").attr('data-hide-nav') == 'true') {
		$(config.parentEl).addClass('hide');
	}
	else {
		$('#categoryNav ul li').each(function () {
			if ($(this).attr('data-hide-nav') == 'true') {
				$(this).addClass('hide');
			}
		});
	}
};
const seeMoreToggle = (bool, onload) => {
	const $ele = $(config.el).find('>li').not('.hide'),
		$eleLen = $ele.length;
	if ($eleLen <= config.defaultLoad) {
		return;
	}
	const $displayElem = $ele.slice(config.defaultLoad, $eleLen);
	if (bool) {
		onload ? $displayElem.hide() : $displayElem.hide('fast');
		$('.see-options.link').removeClass('hide');
		$('.see-less.link').addClass('hide');
		return;
	}
	$displayElem.show('fast');
	$('.see-options.link').addClass('hide');
	$('.see-less.link').removeClass('hide');
};

class categoryActions {
	constructor() {
		this.filterResize();
		evtBinding.bindLooping(this.bindingEventsConfig(), this);
	}
	bindingEventsConfig() {
		let eventsArr = {
			'click #categoryNav a.link': 'seeMoreToggle',
			'keydown #categoryNav a.link': 'seeMoreToggle',
			'click .nav-header button.active-name-mob': 'seeCategories',
			'keydown .nav-header button.active-name-mob': 'seeCategories',
			'click .category-aside-section .doll-subhead': 'selectCurrentCategory',
			"keydown .see-options.link": "removeFocusSeemore",
			"keydown .see-less.link": "removeFocusSeemore"
		};
		return eventsArr;
	}
	seeMoreToggle(ele, evt) {
		evt.preventDefault();
		if (evt.keyCode == 13 || evt.keyCode == undefined) {
			seeMoreToggle(!$(ele).hasClass('see-options'));
		}
	}
	seeCategories(ele, evt) {
		evt.preventDefault();
		let loadCount;
		const $ele = $(config.el).find('>li'),
			$eleLen = $ele.length;
		if ($eleLen <= config.defaultLoad) {
			return;
		}
		loadCount = (parseInt(config.defaultLoad) + 1);
		const $displayElem = $ele.slice(loadCount, $eleLen);
		$displayElem.hide('fast');
	}
	// Related to ADA functionalities focus out from see more/see less
	removeFocusSeemore(el, evt) {
		if ($('#isFPPage').val() != "true") {
			let keyCode = evt.keyCode;
			if (!(evt.shiftKey) && (keyCode == 9)) {
				$(".filter-grid .products-list-item:first-child h2 a").focus();
			}
			if ((evt.shiftKey) && (keyCode == 9)) {
				$(".doll_list li:last-child a").focus();

			}
		}
	}
	selectCurrentCategory(el, evt) {
		$('.doll-subhead').removeAttr('aria-current');
		$(el).attr('aria-current', 'page');
	}
	filterResize() {
		$(window).on('resize', categoryNavToggle);
	}
}

const handleBarTemplateInst = new handleBarTemplate();
const evtBinding = new eventBinding();
const categoryAction = new categoryActions();
