/**
 * retailer-popup.js
 * Version 1.0
 */
(function (global, CRMAEM) {
    var country_dropdown = {
        el: '#countryDropdown',        
        selectCountry: function (ele, eve) {
            var lochref = $(this.el).find(":selected").val();
            var countryName = $(this.el).find(":selected").text();
            populateCountryDropDownData(countryName);
            window.open("//"+lochref);
        },
        init: function () {
            var self = this;
            if (!CRMAEM.isDependencyLoaded || !$(this.el).length || CRMAEM.country_dropdown) return;            
            setTimeout(function(){
                $(".country-drop-down div[role='listbox']").attr({"aria-selected":"true","aria-label": "country dropdown" });
                $(".country-drop-down button[data-id='countryDropdown']").removeAttr("role");
                $(".country-drop-down div[role='combobox']").attr({"aria-expanded":"false", "aria-controls":"countryComboBootstrap"});
                $("ul.dropdown-menu").attr("id","countryComboBootstrap");
            },5000);
            $('#countryDropdown').on('hidden.bs.select', function (e) {                
                $(".country-drop-down div[role='combobox']").attr("aria-expanded","false");
            });
            $('#countryDropdown').on('shown.bs.select', function (e) {      
                $('.popover_window').hide();          
                $(".country-drop-down div[role='combobox']").attr("aria-expanded","true");
            });
            $('.country-drop-down').on('click', '.dropdown-menu li a', function (e) {                
                self.selectCountry();
            });
			var url = window.location.host + window.location.pathname;
			$(".country-drop-down .selectpicker").selectpicker('val', url); 
        }
    }
    country_dropdown.init();
    CRMAEM.country_dropdown = country_dropdown;
    document.addEventListener('DOMContentLoaded', function () {
        if (!CRMAEM.isDependencyLoaded) {
            country_dropdown.init();
        }
    }, false);
}(window, CRMAEM));
