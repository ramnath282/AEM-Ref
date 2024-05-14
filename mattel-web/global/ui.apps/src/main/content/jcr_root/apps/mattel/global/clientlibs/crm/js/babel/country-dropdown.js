const GemCRMCountryDropdown = function () {
    self = this;
    this.el = '#countryDropdown';
    this.init();
};
GemCRMCountryDropdown.prototype = {
    selectCountry(ele, eve) {
        const lochref = $(this.el).find(":selected").val();
        const countryName = $(this.el).find(":selected").text();
        populateCountryDropDownData(countryName);
        window.open(`//${lochref}`);
    },
    init() {
        const self = this;
        if (!$(this.el).length) return;
        setTimeout(() => {
            $(".country-drop-down div[role='listbox']").attr({
                "aria-selected": "true",
                "aria-label": "country dropdown"
            });
            $(".country-drop-down button[data-id='countryDropdown']").removeAttr("role");
            $(".country-drop-down div[role='combobox']").attr({
                "aria-expanded": "false",
                "aria-controls": "countryComboBootstrap"
            });
            $("ul.dropdown-menu").attr("id", "countryComboBootstrap");
        }, 5000);
        $('#countryDropdown').on('hidden.bs.select', e => {
            $(".country-drop-down div[role='combobox']").attr("aria-expanded", "false");
        });
        $('#countryDropdown').on('shown.bs.select', e => {
            $('.popover_window').hide();
            $(".country-drop-down div[role='combobox']").attr("aria-expanded", "true");
        });
        $('.country-drop-down').on('click', '.dropdown-menu li a', e => {
            self.selectCountry();
        });
        const url = window.location.host + window.location.pathname;
        $(".country-drop-down .selectpicker").selectpicker('val', url);
    }
}; 
let self;
const evtBinding = window.global.eventBindingInst;
const gemCRMCountryDropdownInit = new GemCRMCountryDropdown();