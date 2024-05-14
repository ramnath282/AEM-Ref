export const setCookie = (cname, cvalue, exdays=90) =>{
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    if(cname=="MATTEL_VISITOR_STATUS") {
        if(getCookie("MATTEL_VISITOR_STATUS")) {
            expires =  getCookie("MATTEL_VISITOR_STATUS_EXPIRY");
        } else {
            expires = d.toUTCString();
            document.cookie = "MATTEL_VISITOR_STATUS_EXPIRY" + "=" + expires + ";" + expires + ";path=/";
        }
    }
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";    
}

export const getCookie = cname => {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

export const deleteCookie = name =>{
    // document.cookie = name+'=; Max-Age=-99999999;';
    document.cookie = name + "=; expires=Thu, 18 Dec 2013 12:00:00 GMT; path=/";
};

export default {};