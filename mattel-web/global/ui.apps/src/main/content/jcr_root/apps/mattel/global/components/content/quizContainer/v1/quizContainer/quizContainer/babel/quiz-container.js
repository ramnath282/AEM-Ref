const config = {
		sAndPLink: $("#snpAccountUrl").val()
};
	class miniQuiz {
		constructor() {
				self = this;
				window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
		}
		bindingEventsConfig() {
			let eventsArr = { 
					'click .mq-ans-cta .mqbtnprev': 'prevSlide',
					'click .mq-ans-cta .mqbtnnext': 'nextSlide',
			};
			return eventsArr;
		}

		init(){
			setTimeout(() => {
				self.setBackground('quiz', $('.mq-wrapper')); 
				self.setEqualHeight(1, $('.mq-wrapper'));
			},1000);
		}
						
		prevSlide(ele, evt) {
			evt.preventDefault();
			let currentPage = $(ele).parents("section").data('screen-index');
			if (currentPage > 1 ) {
				currentPage--;
				self.setActiveSlide(currentPage, $(ele).parents("section"), $(ele).parents('.mq-wrapper'));
			}	
		}
	
		nextSlide(ele, evt) {
			evt.preventDefault();
			let currentPage = $(ele).parents("section").data('screen-index');
			if (currentPage <= $(ele).parents('.mq-wrapper').find('.quizQuestion').length) {
				currentPage++;
				self.setActiveSlide(currentPage, $(ele).parents("section"), $(ele).parents('.mq-wrapper'));
			}
		}
		
		setActiveSlide(newPage, slideObj, quizWrapper, isSelfCall) {
			isSelfCall ? self.isSelfCall = isSelfCall : false;
			let quizSlide = $(quizWrapper).find('.quizQuestion'),
					quizSlideCount = $(quizWrapper).find('.quizQuestion').length,
					recommCount = $(quizWrapper).find('.mq-recomm').data('recommendation-count');

			$(quizWrapper).find('section').removeClass('active');	
			$(quizWrapper).find('section'+`[data-screen-index=${newPage}]`).addClass('active');
			self.setEqualHeight(newPage,quizWrapper);

			if(newPage > quizSlideCount)
			{
				self.setBackground('thanks', quizWrapper); 
				$(quizWrapper).find('.mq-thankyou').addClass('active');
				let ansStr = '',
						ansStrGroup = '',
						quesStr = '';

				quizSlide.each((idx, elem) => { 
					$('ul', elem).each((quesIdx, quesObj) => {
						
						$('li', quesObj).each((ansIdx, ansObj) => {
							if($(ansObj).find('[type="'+$(ansObj).parents('section').data('input-type')+'"]').is(':checked')) {
								(ansStr == '') ? ansStr = $(ansObj).find('[type="'+$(ansObj).parents('section').data('input-type')+'"]:checked').val() : ansStr = ansStr + '|' +$(ansObj).find('[type="'+$(ansObj).parents('section').data('input-type')+'"]:checked').val()
							}
						});
						if(ansStr!='') {
							(ansStrGroup == '') ? ansStrGroup = 'q' + (idx+1) + '=' + ansStr : ansStrGroup = ansStrGroup + ';q' + (idx+1) + '=' + ansStr;
							quesStr = quesStr + ';x' + (idx+1) + '=' + $(quesObj).data('question-key');
							ansStr = '';
						}
					});
				});
				var snpUrl = config.sAndPLink + '?count=3;' + ansStrGroup + quesStr;
				if(ansStrGroup.trim() === "" || isSelfCall) {
					snpUrl = config.sAndPLink + 'do=popular;count=3';
				}
				$(".mqViewAll").unbind( "click" );
				$(quizWrapper).on('click','.mqViewAll',function(e){
					e.preventDefault();
					let url = $(e.target).data('target-href') + '?searchTerm=&directQuery=true&filterCount='+ quizSlide.length + '&' + ansStrGroup + quesStr;
					if(ansStrGroup.trim() === "" || self.isSelfCall) {						
						url = $(e.target).data('target-href') + '?searchTerm=null&directQuery=true&filterCount='+ quizSlide.length;
					}					
					window.location.href = url;
				});
				$.ajax({
					"type": 'get',
					"accept": 'application/json',
					"crossDomain": true,
					"dataType": 'json',
					"url": snpUrl
				}).done(response => {
					if(response.resultsets[0].results.length == 0) {
						self.setActiveSlide(quizSlideCount+1, "", quizWrapper, true)
						return;
					}
					let responseData = response.resultsets[0].results,
							handleBarPLPList = '#'+$(quizWrapper).find('.mq-recomm-block ul').attr('id'),
							hbtID = '#mqPLPTemplate'+$(quizWrapper).find('.mq-recomm').data('hbt-id');
					window.global.handleBarsHelperInst.callRegisterHelper('changeToHyphen');
					window.global.handleBarTemplateInst.loadTemplate(hbtID, handleBarPLPList, responseData, '');
					setTimeout(() => {
						self.generateBadges(quizWrapper);
						self.mapCurrency(quizWrapper);
					},800);
					
					if (window.addAnalyticsToMiniQuizRecommendations) {
						window.addAnalyticsToMiniQuizRecommendations();
					}					
				}).fail(err => { })
			}
		}

		generateBadges(quizWrapper) {
			if($(quizWrapper).find('.product-badge').length > 0) {
				let currentPagePath = $('#currentPagePathForAnalytics').val(),
					getProducts = "/bin/getProductBadge";
				
				$.ajax({
					"type": 'get',
					"accept": 'application/json',
					"crossDomain": true,
					"dataType": 'json',
					//"url": 'mockjson/badge.json'
					"url": window.location.protocol + "//" + window.location.host + getProducts + "?currentPath=" + currentPagePath +"&plp=plppage"
				}).done(response => {
					if(response.productBageList !=''){
						const listOfProducts = $(quizWrapper).find('.product-badge');
						
						$.each(response.productBageList, function(badgeIdx, badgeObj){
							let badgeDisplayValue = badgeObj.badgeDisplayValue,
							 badgeColour = badgeObj.badgeColour,
							 badgeTitle = badgeObj.badge,
							 badgeIcon = badgeObj.badgeIcon,
							 textColour = badgeObj.textColour;
							
							for(var j=0; j< listOfProducts.length; j++) {
								let product = listOfProducts.eq(j);
								if(badgeTitle == product.html()) {
									product.css({"color":textColour,"background-color":badgeColour,"border":"1px solid "+badgeColour}).text(badgeDisplayValue);
									product.prepend("<img src='"+badgeIcon+"' alt='"+badgeTitle+"' />");
								}
							}
						});
					}		
				}).fail(err => { })	
			}
		}

		mapCurrency(quizWrapper) {
			if($(quizWrapper).find('.price-currency').length > 0) {
				let getCurrencyMapList = "/bin/getCurrencyMapList";
				$.ajax({
					"type": 'get',
					"accept": 'application/json',
					"crossDomain": true,
					"dataType": 'json',
					//"url": 'mockjson/currency.json'
					"url": window.location.protocol + "//" + window.location.host + getCurrencyMapList
				}).done(response => {
					if(response.currencyMap !='') {
						$.each(response.currencyMap, function(currIdx, currObj){
							$(quizWrapper).find(".price-currency").each((priceIdx, priceObj) => {
								if($(priceObj).data('currency') == currObj.currencyType) {
									$(priceObj).html(currObj.currencySymbol);
								}
							});
						});	
					}
				}).fail(err => { })	
			}
	 }

		setEqualHeight(screenIdx,wrapObj) {
			if(screenIdx==1) {
				$(wrapObj).each((wrapIdx,wrapperObj) => {
					self.iterateForHeight(wrapperObj,screenIdx);
				});
			}
			else {
				self.iterateForHeight(wrapObj,screenIdx);
			}
		}

			iterateForHeight(primeObj,screenIdx) {
				let highestBox = 0;
				$(primeObj).find('section'+`[data-screen-index=${screenIdx}]`+' ul li').each((elemIdx,elemLI) => {
					if($(elemLI).height() > highestBox){  
						highestBox = $(elemLI).height();
					}
				});
				$(primeObj).find('section'+`[data-screen-index=${screenIdx}]`+' ul li').height(highestBox);
			}

		setBackground(slideFor, quizWrapper) {
			$(quizWrapper).each((wrapIdx,wrapObj) => {
				if(slideFor == 'quiz') {	
					switch ($(wrapObj).data('question-background-option')) {
						case 'color':
							$(wrapObj).css('background-color', (($(wrapObj).data('question-bg-color'))?$(wrapObj).data('question-bg-color'):''));
						break;
						case 'image':
							$(wrapObj).find('.mq-dyn-bg-quiz').addClass('active');
						break;
						case 'tileImage':
							$(wrapObj).css('background-image', "url("+(($(wrapObj).data('question-tile'))?$(wrapObj).data('question-tile'):'')+")").addClass((($(wrapObj).data('question-tile-option'))?$(wrapObj).data('question-tile-option'):null))
						break;
						default:
						break;
					}
				}
				else if(slideFor == 'thanks') { 
					switch ($(wrapObj).data('result-background-option')) {
						case 'color':
							$(wrapObj).find('.mq-dyn-bg-quiz').removeClass('active');
							$(wrapObj).css({'background-image' : 'none', 'background-color' : (($(wrapObj).data('result-bg-color'))?$(wrapObj).data('result-bg-color'):'')});
						break;
						case 'image':
							$(wrapObj).css({'background-image' : 'none', 'background-color' : 'transparent'});
							$(wrapObj).find('.mq-dyn-bg-quiz').removeClass('active');
							$(wrapObj).find('.mq-dyn-bg-result').addClass('active');
						break;
						case 'tileImage':
							$(wrapObj).find('.mq-dyn-bg-quiz').removeClass('active');
							$(wrapObj)
									.css({'background-color' : 'transparent', 'background-image' : "url("+(($(wrapObj).data('result-tile'))?$(wrapObj).data('result-tile'):'')+")"})
									.addClass((($(wrapObj).data('result-tile-option'))?$(wrapObj).data('result-tile-option'):null))
						break;
						default:
						break;
					}
				}
			});
		}
	}
	let self;
	window.global.miniquiz = window.global.miniquiz || new miniQuiz();
	window.global.miniquiz.init();
