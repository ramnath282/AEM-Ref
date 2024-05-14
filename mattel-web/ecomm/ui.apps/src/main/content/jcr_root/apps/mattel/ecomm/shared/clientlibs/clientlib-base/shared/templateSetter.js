/* fn.loadTemplate("#templateId", "#containerId", dataObj, "replace/append choice"); */
export class handleBarTemplate {
    constructor() {        
    }

    loadTemplate(templateId,container,data, action) {
        let source = $(templateId).html();
        let template = Handlebars.compile(source);
        let output = template(data);
        action === "replace" ? $(container).html(output) : $(container).append(output);
    }
}