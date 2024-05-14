export const getDeviceName = () => {
    let deviceName;
    if(window.innerWidth <= 767){
        deviceName = "mobile"
    } else if(window.innerWidth <= 1200){
        deviceName = "tablet"
    } else{
        deviceName = "desktop"
    }
    return deviceName;
}
export default {
    hasTouch: document.documentElement.ontouchstart !== undefined,
    isDevice: /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent),
    onlyDesktop: window.innerWidth <= 1024,
    isMobile: window.innerWidth <= 767,
    isMediumScreen: window.innerWidth <= 991,
    errorStorageName: 'errorList',
    geoAPIStorageName: 'countryName',
    guestCookieName: 'WC_AUTHENTICATION_',
    miniCartCookieName: 'MATTEL_CUSTOMER_SEGMENT',
    unknownUserCookieName: 'WC_AUTHENTICATION_-1002'
};