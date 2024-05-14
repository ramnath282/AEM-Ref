(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['upcoming-events.tmpl'] = template({"1":function(container,depth0,helpers,partials,data) {
    var stack1, alias1=container.lambda, alias2=container.escapeExpression;
    var reservationLabel = $('#reservationLabel').val();
    // var reservationURL = $('#reservationURL').attr('data-reservationURL');
    if (depth0 !=null && depth0.eventTitle){
    var dataTracking ="Click-Event|click|Upcoming-Events|Make-a-reservation|"+ depth0.eventTitle.replace(/ /g, '-');
    }
    var reservationHtml;
    if(depth0 !=null && depth0.eventZomatoUrl)
    {

        reservationHtml = " <button class=\"btn theme-btn track-events-reservation reservation-btn\" title=\"Make a reservation\" data-tracking-events = "+dataTracking+" onclick=\"NEXTWIDGET.widget.show('"+
        depth0.eventZomatoUrl+"',false);\">"+(reservationLabel?reservationLabel:'Make a reservation')+"</button>"
    }
    var seeMoreHtml='';
    if(depth0 !=null && depth0.scheduleDescription && depth0.scheduleDescription.trim()!='')
    {
      console.log(depth0.scheduleDescription);
      seeMoreHtml = "<a href='#' class='see-more-data'>See more event details.</a>";
    }

  return "    <div class=\"row\">\r\n        <div class=\"col-xs-12 col-lg-2 event-date mb-15\">\r\n            <span class=\"d-block\"><strong>"
    + alias2(alias1((depth0 != null ? depth0.eventDate : depth0), depth0))
    + "</strong></span>\r\n            <span>"
    + alias2(alias1((depth0 != null ? depth0.eventStartTime : depth0), depth0))
    + " - "
    + alias2(alias1((depth0 != null ? depth0.eventEndTime : depth0), depth0))
    + "</span>\r\n        </div>\r\n        <div class=\"col-xs-12 col-lg-10\">\r\n            <h3>"
    + ((stack1 = alias1((depth0 != null ? depth0.eventTitle : depth0), depth0)) != null ? stack1 : "")
    + "</h3>\r\n            <p>"
    + ((stack1 = alias1((depth0 != null ? depth0.eventDescription : depth0), depth0)) != null ? stack1 : "")
    + "</p>\r\n            <p class=\"info\">"
    + ((stack1 = alias1((depth0 != null ? depth0.reservationDetails : depth0), depth0)) != null ? stack1 : "")
    + " "+seeMoreHtml+" </p>\r\n <div class='schedule-description hide'>"
    + ((stack1 = alias1((depth0 != null ? depth0.scheduleDescription : depth0), depth0)) != null ? stack1 : "")
    + "</div>\r\n   " +(reservationHtml?reservationHtml:'')+  "  </div>\r\n    </div>\r\n";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers.each.call(depth0 != null ? depth0 : (container.nullContext || {}),(depth0 != null ? depth0.items : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "");
},"useData":true});
})();