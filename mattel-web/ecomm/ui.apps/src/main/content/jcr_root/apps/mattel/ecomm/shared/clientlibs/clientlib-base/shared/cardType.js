export class cardType {
    constructor() {        
    }
    fetchCardImageUrl(cardName){
        const cardType = {
            'Mastercard': "../resources/img/mastercard.png",
            'Visa': "../resources/img/visa.png",
            'AMEX': "../resources/img/amex.png",
            'Discover': "../resources/img/discover.png",
            'Diners': "",
            'VisaElectron': ""
        };

        return cardType[cardName];
    }    
}