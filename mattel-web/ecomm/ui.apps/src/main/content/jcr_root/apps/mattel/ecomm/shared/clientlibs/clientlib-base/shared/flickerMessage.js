let $toastElem = $(".toast-container");
const newClass = "active";
let delayTimeout = 2000;

// message supported types = [info, success, warning, error]

let timeSet;

export const exceptionHandler = (messageType, content,dynamicClass,timeout) => {
    delayTimeout = timeout || delayTimeout;
    const newEle = `<div role="alert" class="toast-message toast-${messageType}"><div class="toast-text"><span class="info-icon"></span>${content}</div></div>`
    if (checkIfError(arguments)) return;
    if(!$toastElem.length){
        $toastElem = $(".toast-container");
    }
    $toastElem
        .html(newEle)
        .addClass(`${newClass} ${dynamicClass || ''}`);
    const $curElem = $toastElem;
    window.clearTimeout(timeSet);
    timeSet = window.setTimeout(() => {
        $curElem.removeClass(`${newClass}`);
        window.setTimeout(()=> {
            $curElem.html('').removeClass(`${dynamicClass || ''}`);
        },200);
    }, delayTimeout);
};

const checkIfError = params => {
    if (!params[0] || !params[1]) { 
        return true;
    }
}
export default {};