if ($(".storemap").length>0) {
var cordinateInfo = $(".map-module");
var coordinates = cordinateInfo.attr("data-coordinate");
var json = JSON.parse(coordinates);
var directionsLabel = cordinateInfo.attr("data-get-directions-label");
var pageTitle = document.title;
var apiKey = cordinateInfo.attr("data-apikey");
var brandName = "";
var address="";
brandName = cordinateInfo.attr("data-brand-name");
$.getScript("https://maps.googleapis.com/maps/api/js?key=" + apiKey + "&callback=initMap");
var centerControl = function(controlDiv, map, marker, lattitude, longitude) {
    var addressSplit = address.split(",");
	var infoContent;
    console.log("Address split 0" + addressSplit);
    // Set CSS for the control interior.
    var controlText = document.createElement('div');
	if (brandName != undefined) {
    controlText.innerHTML = '<div class="store-address" style="cursor: default;font-family: Roboto, Arial, sans-serif;font-size: 12px;box-shadow: rgba(0, 0, 0, 0.298) 0px 1px 4px -1px;margin: 10px;padding: 10px;border: 1px solid rgb(221, 221, 221);background: rgb(255, 255, 255);z-index: 0;position: absolute;left: 0px;top: 0px;"><strong>' +brandName+' '+ pageTitle + '</strong><br>' + address + '<a target=\'_blank\' href=\'https://www.google.com/maps/dir//' + lattitude + ',' + longitude + '/' + '@' + lattitude + ',@' + longitude + ',16z?hl=en-US\'>' + directionsLabel + '</a></div>';
    controlDiv.appendChild(controlText);
    infoContent = '<div><strong>' +brandName+' '+ pageTitle + '</strong><br>' + address + '<br><a target=\'_blank\' href=\'https://www.google.com/maps/dir//' + lattitude + ',' + longitude + '/' + '@' + lattitude + ',@' + longitude + ',16z?hl=en-US\'>' + directionsLabel + '</a></div>';
	} else {
		controlText.innerHTML = '<div class="store-address" style="cursor: default;font-family: Roboto, Arial, sans-serif;font-size: 12px;box-shadow: rgba(0, 0, 0, 0.298) 0px 1px 4px -1px;margin: 10px;padding: 10px;border: 1px solid rgb(221, 221, 221);background: rgb(255, 255, 255);z-index: 0;position: absolute;left: 0px;top: 0px;"><strong>'+pageTitle + '</strong><br>' + address + '<br><a target=\'_blank\' href=\'https://www.google.com/maps/dir//' + lattitude + ',' + longitude + '/' + '@' + lattitude + ',@' + longitude + ',16z?hl=en-US\'>' + directionsLabel + '</a></div>';
    controlDiv.appendChild(controlText);
    infoContent = '<div><strong>' + pageTitle + '</strong><br>' + address + '<br><a target=\'_blank\' href=\'https://www.google.com/maps/dir//' + lattitude + ',' + longitude + '/' + '@' + lattitude + ',@' + longitude + ',16z?hl=en-US\'>' + directionsLabel + '</a></div>';

	}
    var infowindow = new google.maps.InfoWindow({
        content: infoContent
    });
    marker.addListener('click', function() {
        infowindow.open(map, marker);
    });
}
var geocodeLatLng = function(geocoder, map, marker, lattitude, longitude, locationName) {

                address = locationName;
                var centerControlDiv = document.createElement('div');
                new centerControl(centerControlDiv, map, marker, lattitude, longitude);

                centerControlDiv.index = 1;
                map.controls[google.maps.ControlPosition.TOP_LEFT].push(centerControlDiv);


    }

function initMap() {
    var map;
    var marker;
    var currentInfoWindow;
    var bounds = new google.maps.LatLngBounds();
    var geocoder = new google.maps.Geocoder;
    var mapOptions = {
        disableDefaultUI: true
    };
    // Display a map on the page
    map = new google.maps.Map(document.getElementById("map"), mapOptions);
    var multipleInfoContent = [];
    $.each(json, function(i, item) {
        var coordinates = json[i];
        console.log(coordinates.lattitude);
        var position = new google.maps.LatLng(parseFloat(coordinates.lattitude), parseFloat(coordinates.longitude));
        bounds.extend(position);
        marker = new google.maps.Marker({
            position: position,
            map: map,
            title: coordinates.locationName
        });
        // Automatically center the map fitting all markers on the screen
        if (parseInt(json.length) == 1) {
            map.setCenter(position);
            geocodeLatLng(geocoder, map, marker, coordinates.lattitude, coordinates.longitude, coordinates.locationName);

        } else {
            map.fitBounds(bounds);



                   multipleInfoContent.push(coordinates.locationName);
            console.log(multipleInfoContent);
            // Allow each marker to have an info window
            google.maps.event.addListener(marker, 'click', (function(marker, i) {

                var infoWindow = new google.maps.InfoWindow;

                return function() {
                                 if (currentInfoWindow != null) {
        currentInfoWindow.close();
    }
                    var multipleInfoContentSplit = multipleInfoContent[i].split(" ").join("+");
                    infoWindow.setContent(multipleInfoContent[i] + '<br> <a target="_blank" href="https://www.google.com/maps/dir//' + multipleInfoContentSplit + '/@' + coordinates.lattitude + ',' + coordinates.longitude + ',17z">' + directionsLabel + '</a>');
                    infoWindow.open(map, marker);
                    currentInfoWindow = infoWindow;
                }
            })(marker, i));
        }
    })
    // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
    if (parseInt(json.length) == 1) {
    var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
        this.setZoom(14);
        google.maps.event.removeListener(boundsListener);
    });
    }

}
}