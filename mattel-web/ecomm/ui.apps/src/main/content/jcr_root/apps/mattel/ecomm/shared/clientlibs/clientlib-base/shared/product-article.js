import { getDeviceName } from './constant';
import eventBinding from './eventBinding';
import { handleBarTemplate } from './templateSetter';
export const articleDatasets = document.querySelector('#article-grid-list') != null ? document.querySelector('#article-grid-list').dataset : 0;
const config = {
	el: '#article-grid-list',
	deviceName: getDeviceName(),
	desktopTextLine: 2,
	tabletTextLine: 2,
	mobileTextLine: 3,
	defaultLoad: 7,
	defaultLoadCnt: 0,
	nextLoad: 4,
    pageNo: 0,
	lazyScroll: true,
};

export const clearStoredData = () => {
	self.articleResonse = '';
	config.pageNo = 0;
};

export const renderArticleTemplate = (generalResponse, res) => {
	let gridContainer = '#article-grid-list';
	clearStoredData();
	self.articleResonse = res;
	renderTemp(
		'#articleGridTemp',
		gridContainer,
		res,
		'replace',
		generalResponse
	);
	checkImageLoaded(gridContainer);
	contentLoading = false;
};
const renderTemp = (templateId, container, data, action, generalResponse) => {
	 $.each(data, (key,val)=> {
		let aemImageUrl = $("#aemImageUrl").attr("data-default-img");
		if(val.imageLink != undefined){
			if(val.imageLink.indexOf("http") == -1 ) {
				val.imageLink = 'https://mattel-sites-stage64.adobecqms.net'+val.imageLink;
			}else {
				val.imageLink = val.imageLink;
			}
		}else{
			val.imageLink = 'https://mattel-sites-stage64.adobecqms.net'+aemImageUrl;
		}
	});
	let source = $(templateId).html();
	let template = Handlebars.compile(source);
	let output = template(data);
	$(container).html(output);
	let socialIconHtml = $("#socialIconUl").html();
	for(let i = 0; i < data.length; i++){		
		$(".social-icons-container ul").eq(i).html(socialIconHtml);
	}
	$(".article-title-header").removeClass("hide");
	$(".no-result-article-title").addClass("hide");
	if(generalResponse.total == 0 || generalResponse.query == ''){
		$(".no-result-article-title").removeClass("hide");
		$(".article-title-header").addClass("hide");
	}

	if(data.length == 0){
		$(".view-all-articles").addClass("hide");
		$(".no-result-article-title").addClass("hide");
		$(".article-title-header").addClass("hide");
	}
};
const sliceObject = (res) => {
	if (!res.length) return;

	return { obj: self.articleResonse.slice(0, 4).map(result => result)};
};
const checkImageLoaded = elem => {
	let $closestEle,
		$imageElem = $(elem).find('.product-image:not(.image-loaded)');
	if (!$imageElem.length) {
		return;
	}
	$imageElem
		.imagesLoaded()
		.progress((instance, image) => {
			$closestEle = $(image.img).closest('.product-image');
			$closestEle.addClass('image-loaded');
			if (!image.isLoaded) {
				$closestEle.addClass('broken-image');
				$('#article-grid-list li a.broken-image img').attr('src','//mattel.scene7.com/is/image/Mattel/ag_fallback_image?fmt=png-alpha&qlt=70&wid=1000');
			}
		})
		.done((instance, image) => {
		});
};

export class articleActions {
	constructor(config) {
		evtBinding.bindLooping(this.bindingEventsConfig(), this);
	}
	bindingEventsConfig() {
		let eventsArr = {

		};
		return eventsArr;
	}
}
let contentLoading = false;
let dataLoaded = false;
let onScrollLoadCnt;
let parentConfig = {};
const handleBarTemplateInst = new handleBarTemplate();
const evtBinding = new eventBinding();