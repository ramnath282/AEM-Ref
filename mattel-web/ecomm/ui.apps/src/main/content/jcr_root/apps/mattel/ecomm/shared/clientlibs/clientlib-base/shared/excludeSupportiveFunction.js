
export const excludeSupportObj = {

};


excludeSupportObj.getParameterByName = (name) => {
    let pageUrl = window.location.href, url = decodeURIComponent(pageUrl);
    name = name.replace(/[\[\]]/g, '\\$&');
    let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}
excludeSupportObj.checkExcludeKeywordAsParameter = (cont) => {
  let excludeKeywords = cont.hasClass("email-lightboxpopup-comp") ? cont.find("#excludekeywords").val() : cont.attr("data-excludeKeywords");
  let excludeKeyArr = excludeKeywords && excludeKeywords != "" ? excludeKeywords.split(",") : [];
  let flag = false;
  
  if(excludeKeyArr.length > 0){
      for(let i = 0; i < excludeKeyArr.length; i++){
          if(excludeSupportObj.getParameterByName(excludeKeyArr[i]) != null){
              flag = true;
          }
      }
  }
  return flag;
}
excludeSupportObj.checkExcludeKeywordAsPageName = (cont) => {
  let currentPage = location.pathname.split('/').slice(-1)[0],
      excludeKeywords = cont.hasClass("email-lightboxpopup-comp") ? cont.find("#excludekeywords").val() : cont.attr("data-excludeKeywords");
  let excludeKeyArr = excludeKeywords && excludeKeywords != "" ? excludeKeywords.split(",") : [];
  let flag = false;
  if(currentPage != "" && excludeKeyArr.length > 0){
      for(let i = 0; i < excludeKeyArr.length; i++){
          if(currentPage.indexOf(excludeKeyArr[i]) > -1){
              flag = true;
          }
      }
  }
  return flag;
}
excludeSupportObj.checkExcludePages = (cont) => {
    let flag = false;
    let excludePages = cont.hasClass("email-lightboxpopup-comp") ? cont.find("#excludePages").val() : cont.attr("data-excludePages");
    let excludePageArr = excludePages && excludePages != "" ? excludePages.split(",") : [];
    if(cont.hasClass("entry-modal-container")){
        let urlPage = location.pathname,
            urlPath = urlPage.split('.html')[0];
        if(urlPath != "" && excludePageArr.length > 0){
          for(let i = 0; i < excludePageArr.length; i++){
              if(urlPath == excludePageArr[i]){
                  flag = true;
              }
          }
      }
    }
    if(cont.hasClass("email-lightboxpopup-comp")){
      let urlPath = window.location.pathname;
      if(urlPath != "" && excludePageArr.length > 0){
        for(let i = 0; i < excludePageArr.length; i++){
            if(urlPath.indexOf(excludePageArr[i]) > -1){
                flag = true;
            }
        }
    }
  }
    return flag;
}