$.fn.AEMDotDot = typeof $.fn.dotdotdot == 'function' && $.fn.dotdotdot;
const getLineHeight = element => {
	const lineHeight = window.getComputedStyle(element)['line-height'];
	if (lineHeight === 'normal') {
		return 1.16 * parseFloat(window.getComputedStyle(element)['font-size']);
	} else {
		return parseFloat(lineHeight);
	}
};

const truncateRender = (truncateElement, truncateText, lines) => {
	truncateElement.innerHTML = truncateText;
	const lineHeight = getLineHeight(truncateElement);
	let $parentEle = $(truncateElement).parent('.grid-title-wrapper');
	if(!$parentEle.length){
		$parentEle = $(truncateElement).parent('span');
	}
	if (lines * lineHeight < $(truncateElement).height()) {
		$parentEle.css({ height: lines * lineHeight+5 });
		if (typeof $.fn.AEMDotDot == "function") {
			$parentEle.trigger('destroy.dot').AEMDotDot({ watch: window });
		} else {
			console.log('warn: ellipsis js framework not found..');
		}
	}
};

export const truncateInit = (truncateElement, lines) => {
	const $elem = $(truncateElement);
	const textContent = $elem[0].textContent;
	if (!$elem.attr('data-title')) {
		$elem.attr('data-title', textContent);
	}
	$elem.css('height', 'auto').removeClass('dot-ellipsis');
	truncateRender($elem[0], $elem.attr('data-title'), lines);
};

export default {};