export class inputValues {
    constructor() {        
    }

    getValue(ele,obj) {
        let type = $(ele).attr("type");
        let key = $(ele).attr("data-key");
        switch (type) {
            case "text":
                obj[key] = $(ele).val();
                break; 
            case "number":
                obj[key] = $(ele).val();
                break;
            case "checkbox":
                obj[key] = $(ele).is(":checked");
                break;
            case "radio":
                if($(ele).is(":checked")) {
                    if($(ele).hasClass("custom-pricing-option")) {
                        obj[key] = $(ele).next().val();
                    } else {
                        obj[key] = $(ele).val();
                    }                            
                }
                break;
            default:
                if($(ele).hasClass("select-location")) {
                    obj[key] = $(ele).find("option:selected").text();
                    obj["storeTag"] = $(ele).val();
                } else {
                    obj[key] = $(ele).val();
                } 
        }
        return obj;
    }
}