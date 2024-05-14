export class countryListFetch {
    constructor() {        
    }
    fetchCountryList(){
        const countryList = {
            "countries": [
                {
                    "code": "US",
                    "displayName": "United States",
                    "callingCode": "+1",
                    "states": [
                        {
                            "code": "",
                            "displayName": "Select State/Province"
                        },
                        {
                        "code": "AL",
                        "displayName": "Alabama"
                        },
                        {
                        "code": "AK",
                        "displayName": "Alaska"
                        },
                        {
                        "code": "AS",
                        "displayName": "American Samoa"
                        },
                        {
                        "code": "AZ",
                        "displayName": "Arizona"
                        },
                        {
                        "code": "AR",
                        "displayName": "Arkansas"
                        },
                        {
                        "code": "AA",
                        "displayName": "Armed Forces Americas"
                        },
                        {
                        "code": "AE",
                        "displayName": "Armed Forces Europe"
                        },
                        {
                        "code": "AP",
                        "displayName": "Armed Forces Pacific"
                        },
                        {
                        "code": "CA",
                        "displayName": "California"
                        },
                        {
                        "code": "CO",
                        "displayName": "Colorado"
                        },
                        {
                        "code": "CT",
                        "displayName": "Connecticut"
                        },
                        {
                        "code": "DE",
                        "displayName": "Delaware"
                        },
                        {
                        "code": "DC",
                        "displayName": "District of Columbia"
                        },
                        {
                        "code": "FL",
                        "displayName": "Florida"
                        },
                        {
                        "code": "GA",
                        "displayName": "Georgia"
                        },
                        {
                        "code": "GU",
                        "displayName": "Guam"
                        },
                        {
                        "code": "HI",
                        "displayName": "Hawaii"
                        },
                        {
                        "code": "ID",
                        "displayName": "Idaho"
                        },
                        {
                        "code": "IL",
                        "displayName": "Illinois"
                        },
                        {
                        "code": "IN",
                        "displayName": "Indiana"
                        },
                        {
                        "code": "IA",
                        "displayName": "Iowa"
                        },
                        {
                        "code": "KS",
                        "displayName": "Kansas"
                        },
                        {
                        "code": "KY",
                        "displayName": "Kentucky"
                        },
                        {
                        "code": "LA",
                        "displayName": "Louisiana"
                        },
                        {
                        "code": "ME",
                        "displayName": "Maine"
                        },
                        {
                        "code": "MH",
                        "displayName": "Marshall Islands"
                        },
                        {
                        "code": "MD",
                        "displayName": "Maryland"
                        },
                        {
                        "code": "MA",
                        "displayName": "Massachusetts"
                        },
                        {
                        "code": "MI",
                        "displayName": "Michigan"
                        },
                        {
                        "code": "FM",
                        "displayName": "Micronesia"
                        },
                        {
                        "code": "MN",
                        "displayName": "Minnesota"
                        },
                        {
                        "code": "MS",
                        "displayName": "Mississippi"
                        },
                        {
                        "code": "MO",
                        "displayName": "Missouri"
                        },
                        {
                        "code": "MT",
                        "displayName": "Montana"
                        },
                        {
                        "code": "NE",
                        "displayName": "Nebraska"
                        },
                        {
                        "code": "NV",
                        "displayName": "Nevada"
                        },
                        {
                        "code": "NH",
                        "displayName": "New Hampshire"
                        },
                        {
                        "code": "NJ",
                        "displayName": "New Jersey"
                        },
                        {
                        "code": "NM",
                        "displayName": "New Mexico"
                        },
                        {
                        "code": "NY",
                        "displayName": "New York"
                        },
                        {
                        "code": "NC",
                        "displayName": "North Carolina"
                        },
                        {
                        "code": "ND",
                        "displayName": "North Dakota"
                        },
                        {
                        "code": "MP",
                        "displayName": "Northern Mariana Islands"
                        },
                        {
                        "code": "OH",
                        "displayName": "Ohio"
                        },
                        {
                        "code": "OK",
                        "displayName": "Oklahoma"
                        },
                        {
                        "code": "OR",
                        "displayName": "Oregon"
                        },
                        {
                        "code": "PW",
                        "displayName": "Palau"
                        },
                        {
                        "code": "PA",
                        "displayName": "Pennsylvania"
                        },
                        {
                        "code": "PR",
                        "displayName": "Puerto Rico"
                        },
                        {
                        "code": "RI",
                        "displayName": "Rhode Island"
                        },
                        {
                        "code": "SC",
                        "displayName": "South Carolina"
                        },
                        {
                        "code": "SD",
                        "displayName": "South Dakota"
                        },
                        {
                        "code": "TN",
                        "displayName": "Tennessee"
                        },
                        {
                        "code": "TX",
                        "displayName": "Texas"
                        },
                        {
                        "code": "VI",
                        "displayName": "U.S. Virgin Islands"
                        },
                        {
                        "code": "UT",
                        "displayName": "Utah"
                        },
                        {
                        "code": "VT",
                        "displayName": "Vermont"
                        },
                        {
                        "code": "VA",
                        "displayName": "Virginia"
                        },
                        {
                        "code": "WA",
                        "displayName": "Washington"
                        },
                        {
                        "code": "WV",
                        "displayName": "West Virginia"
                        },
                        {
                        "code": "WI",
                        "displayName": "Wisconsin"
                        },
                        {
                        "code": "WY",
                        "displayName": "Wyoming"
                        }
                    ]
                },
                {
                    "code": "CA",
                    "displayName": "Canada",
                    "callingCode": "+1",
                    "states": [
                        {
                        "code": "",
                        "displayName": "Select State/Province"
                        },
                        {
                        "code": "AB",
                        "displayName": "Alberta"
                        },
                        {
                        "code": "BC",
                        "displayName": "British Columbia"
                        },
                        {
                        "code": "MB",
                        "displayName": "Manitoba"
                        },
                        {
                        "code": "NB",
                        "displayName": "New Brunswick"
                        },
                        {
                        "code": "NL",
                        "displayName": "Newfoundland"
                        },
                        {
                        "code": "NT",
                        "displayName": "Northwest Territory"
                        },
                        {
                        "code": "NS",
                        "displayName": "Nova Scotia"
                        },
                        {
                        "code": "NU",
                        "displayName": "Nunavut"
                        },
                        {
                        "code": "ON",
                        "displayName": "Ontario"
                        },
                        {
                        "code": "PE",
                        "displayName": "Prince Edward Island"
                        },
                        {
                        "code": "QC",
                        "displayName": "Quebec"
                        },
                        {
                        "code": "SK",
                        "displayName": "Saskatchewan"
                        },
                        {
                        "code": "YT",
                        "displayName": "Yukon"
                        }
                    ]
                },
                {
                "code": "AF",
                "displayName": "Afghanistan",
                "callingCode": "+93",
                "states": []
                },
                {
                "code": "AL",
                "displayName": "Albania",
                "callingCode": "+355",
                "states": []
                },
                {
                "code": "DZ",
                "displayName": "Algeria",
                "callingCode": "+213",
                "states": []
                },
                {
                "code": "AS",
                "displayName": "American Samoa",
                "callingCode": "+684",
                "states": []
                },
                {
                "code": "AD",
                "displayName": "Andorra",
                "callingCode": "+376",
                "states": []
                },
                {
                "code": "AO",
                "displayName": "Angola",
                "callingCode": "+244",
                "states": []
                },
                {
                "code": "AI",
                "displayName": "Anguilla",
                "callingCode": "+1-264",
                "states": []
                },
                {
                "code": "AQ",
                "displayName": "Antarctica",
                "callingCode": "+672",
                "states": []
                },
                {
                "code": "AG",
                "displayName": "Antigua and Barbuda",
                "callingCode": "+1-268",
                "states": []
                },
                {
                "code": "AR",
                "displayName": "Argentina",
                "callingCode": "+54",
                "states": []
                },
                {
                "code": "AM",
                "displayName": "Armenia",
                "callingCode": "+374",
                "states": []
                },
                {
                "code": "AW",
                "displayName": "Aruba",
                "callingCode": "+297",
                "states": []
                },
                {
                "code": "AU",
                "displayName": "Australia",
                "callingCode": "+61",
                "states": []
                },
                {
                "code": "AT",
                "displayName": "Austria",
                "callingCode": "+43",
                "states": []
                },
                {
                "code": "AZ",
                "displayName": "Azerbaijan",
                "callingCode": "+994",
                "states": []
                },
                {
                "code": "BS",
                "displayName": "Bahamas",
                "callingCode": "+1-242",
                "states": []
                },
                {
                "code": "BH",
                "displayName": "Bahrain",
                "callingCode": "+973",
                "states": []
                },
                {
                "code": "BD",
                "displayName": "Bangladesh",
                "callingCode": "+880",
                "states": []
                },
                {
                "code": "BB",
                "displayName": "Barbados",
                "callingCode": "+1-246",
                "states": []
                },
                {
                "code": "BY",
                "displayName": "Belarus",
                "callingCode": "+375",
                "states": []
                },
                {
                "code": "BE",
                "displayName": "Belgium",
                "callingCode": "+32",
                "states": []
                },
                {
                "code": "BZ",
                "displayName": "Belize",
                "callingCode": "+501",
                "states": []
                },
                {
                "code": "BJ",
                "displayName": "Benin",
                "callingCode": "+229",
                "states": []
                },
                {
                "code": "BM",
                "displayName": "Bermuda",
                "callingCode": "+1-441",
                "states": []
                },
                {
                "code": "BT",
                "displayName": "Bhutan",
                "callingCode": "+975",
                "states": []
                },
                {
                "code": "BO",
                "displayName": "Bolivia",
                "callingCode": "+591",
                "states": []
                },
                {
                "code": "BQ-BO",
                "displayName": "Bonaire",
                "callingCode": "+599",
                "states": []
                },
                {
                "code": "BA",
                "displayName": "Bosnia and Herzegovina",
                "callingCode": "+387",
                "states": []
                },
                {
                "code": "BW",
                "displayName": "Botswana",
                "callingCode": "+267",
                "states": []
                },
                {
                "code": "BV",
                "displayName": "Bouvet Island",
                "callingCode": "",
                "states": []
                },
                {
                "code": "BR",
                "displayName": "Brazil",
                "callingCode": "+55",
                "states": []
                },
                {
                "code": "VG",
                "displayName": "British Indian Ocean Territory",
                "callingCode": "+1-284",
                "states": []
                },
                {
                "code": "BN",
                "displayName": "Brunei",
                "callingCode": "+673",
                "states": []
                },
                {
                "code": "BG",
                "displayName": "Bulgaria",
                "callingCode": "+359",
                "states": []
                },
                {
                "code": "BF",
                "displayName": "Burkina Faso",
                "callingCode": "+226",
                "states": []
                },
                {
                "code": "BI",
                "displayName": "Burundi",
                "callingCode": "+257",
                "states": []
                },
                {
                "code": "KH",
                "displayName": "Cambodia",
                "callingCode": "+855",
                "states": []
                },
                {
                "code": "CM",
                "displayName": "Cameroon",
                "callingCode": "+237",
                "states": []
                },
                
                {
                "code": "CV",
                "displayName": "Cape Verde",
                "callingCode": "+238",
                "states": []
                },
                {
                "code": "KY",
                "displayName": "Cayman Islands",
                "callingCode": "+1-345",
                "states": []
                },
                {
                "code": "CF",
                "displayName": "Central African Republic",
                "callingCode": "+236",
                "states": []
                },
                {
                "code": "TD",
                "displayName": "Chad",
                "callingCode": "+235",
                "states": []
                },
                {
                "code": "CL",
                "displayName": "Chile",
                "callingCode": "+56",
                "states": []
                },
                {
                "code": "CN",
                "displayName": "China",
                "callingCode": "+86",
                "states": []
                },
                {
                "code": "CX",
                "displayName": "Christmas Island",
                "callingCode": "+61-8",
                "states": []
                },
                {
                "code": "CC",
                "displayName": "Cocos Islands",
                "callingCode": "+61",
                "states": []
                },
                {
                "code": "CO",
                "displayName": "Colombia",
                "callingCode": "+57",
                "states": []
                },
                {
                "code": "KM",
                "displayName": "Comoros",
                "callingCode": "+269",
                "states": []
                },
                {
                "code": "CG",
                "displayName": "Congo",
                "callingCode": "+242",
                "states": []
                },
                {
                "code": "CD",
                "displayName": "Congo, Democratic Republic of the",
                "callingCode": "+243",
                "states": []
                },
                {
                "code": "CK",
                "displayName": "Cook Islands",
                "callingCode": "+682",
                "states": []
                },
                {
                "code": "CR",
                "displayName": "Costa Rica",
                "callingCode": "+506",
                "states": []
                },
                {
                "code": "CI",
                "displayName": "Cote d&#039;Ivoire",
                "callingCode": "+225",
                "states": []
                },
                {
                "code": "HR",
                "displayName": "Croatia",
                "callingCode": "+385",
                "states": []
                },
                {
                "code": "CU",
                "displayName": "Cuba",
                "callingCode": "+53",
                "states": []
                },
                {
                "code": "CW",
                "displayName": "Curacao",
                "callingCode": "+599",
                "states": []
                },
                {
                "code": "CY",
                "displayName": "Cyprus",
                "callingCode": "+357",
                "states": []
                },
                {
                "code": "CZ",
                "displayName": "Czech Republic",
                "callingCode": "+420",
                "states": []
                },
                {
                "code": "DK",
                "displayName": "Denmark",
                "callingCode": "+45",
                "states": []
                },
                {
                "code": "DJ",
                "displayName": "Djibouti",
                "callingCode": "+253",
                "states": []
                },
                {
                "code": "DM",
                "displayName": "Dominica",
                "callingCode": "+1-767",
                "states": []
                },
                {
                "code": "DO",
                "displayName": "Dominican Republic",
                "callingCode": "+1-809",
                "states": []
                },
                {
                "code": "TL",
                "displayName": "East Timor",
                "callingCode": "+1",
                "states": []
                },
                {
                "code": "EC",
                "displayName": "Ecuador",
                "callingCode": "+593",
                "states": []
                },
                {
                "code": "EG",
                "displayName": "Egypt",
                "callingCode": "+20",
                "states": []
                },
                {
                "code": "SV",
                "displayName": "El Salvador",
                "callingCode": "+503",
                "states": []
                },
                {
                "code": "GQ",
                "displayName": "Equatorial Guinea",
                "callingCode": "+240",
                "states": []
                },
                {
                "code": "ER",
                "displayName": "Eritria",
                "callingCode": "+291",
                "states": []
                },
                {
                "code": "EE",
                "displayName": "Estonia",
                "callingCode": "+372",
                "states": []
                },
                {
                "code": "ET",
                "displayName": "Ethiopia",
                "callingCode": "+251",
                "states": []
                },
                {
                "code": "FK",
                "displayName": "Falkland Islands",
                "callingCode": "+500",
                "states": []
                },
                {
                "code": "FO",
                "displayName": "Faroe Islands",
                "callingCode": "+298",
                "states": []
                },
                {
                "code": "FJ",
                "displayName": "Fiji",
                "callingCode": "+679",
                "states": []
                },
                {
                "code": "FI",
                "displayName": "Finland",
                "callingCode": "+358",
                "states": []
                },
                {
                "code": "FR",
                "displayName": "France",
                "callingCode": "+33",
                "states": []
                },
                {
                "code": "GF",
                "displayName": "French Guiana",
                "callingCode": "+594",
                "states": []
                },
                {
                "code": "PF",
                "displayName": "French Polynesia",
                "callingCode": "+689",
                "states": []
                },
                {
                "code": "TF",
                "displayName": "French Southern Territories",
                "callingCode": "",
                "states": []
                },
                {
                "code": "GA",
                "displayName": "Gabon",
                "callingCode": "+241",
                "states": []
                },
                {
                "code": "GM",
                "displayName": "Gambia",
                "callingCode": "+220",
                "states": []
                },
                {
                "code": "GE",
                "displayName": "Georgia",
                "callingCode": "+995",
                "states": []
                },
                {
                "code": "DE",
                "displayName": "Germany",
                "callingCode": "+49",
                "states": []
                },
                {
                "code": "GH",
                "displayName": "Ghana",
                "callingCode": "+233",
                "states": []
                },
                {
                "code": "GI",
                "displayName": "Gibralter",
                "callingCode": "+350",
                "states": []
                },
                {
                "code": "GR",
                "displayName": "Greece",
                "callingCode": "+30",
                "states": []
                },
                {
                "code": "GL",
                "displayName": "Greenland",
                "callingCode": "+299",
                "states": []
                },
                {
                "code": "GD",
                "displayName": "Grenada",
                "callingCode": "+1-473",
                "states": []
                },
                {
                "code": "GP",
                "displayName": "Guadeloupe",
                "callingCode": "+590",
                "states": []
                },
                {
                "code": "GU",
                "displayName": "Guam",
                "callingCode": "+1-671",
                "states": []
                },
                {
                "code": "GT",
                "displayName": "Guatemala",
                "callingCode": "+502",
                "states": []
                },
                {
                "code": "GN",
                "displayName": "Guinea",
                "callingCode": "+224",
                "states": []
                },
                {
                "code": "GW",
                "displayName": "Guinea Bissau",
                "callingCode": "+245",
                "states": []
                },
                {
                "code": "GY",
                "displayName": "Guyana",
                "callingCode": "+592",
                "states": []
                },
                {
                "code": "HT",
                "displayName": "Haiti",
                "callingCode": "+509",
                "states": []
                },
                {
                "code": "HM",
                "displayName": "Heard Island and Mcdonald Islands",
                "callingCode": "",
                "states": []
                },
                {
                "code": "HN",
                "displayName": "Honduras",
                "callingCode": "+504",
                "states": []
                },
                {
                "code": "HK",
                "displayName": "Hong Kong",
                "callingCode": "+852",
                "states": []
                },
                {
                "code": "HU",
                "displayName": "Hungary",
                "callingCode": "+36",
                "states": []
                },
                {
                "code": "IS",
                "displayName": "Iceland",
                "callingCode": "+354",
                "states": []
                },
                {
                "code": "IN",
                "displayName": "India",
                "callingCode": "+91",
                "states": []
                },
                {
                "code": "ID",
                "displayName": "Indonesia",
                "callingCode": "+62",
                "states": []
                },
                {
                "code": "IR",
                "displayName": "Iran",
                "callingCode": "+98",
                "states": []
                },
                {
                "code": "IQ",
                "displayName": "Iraq",
                "callingCode": "+964",
                "states": []
                },
                {
                "code": "IE",
                "displayName": "Ireland",
                "callingCode": "+353",
                "states": []
                },
                {
                "code": "IL",
                "displayName": "Israel",
                "callingCode": "+972",
                "states": []
                },
                {
                "code": "IT",
                "displayName": "Italy",
                "callingCode": "+39",
                "states": []
                },
                {
                "code": "JM",
                "displayName": "Jamaica",
                "callingCode": "+1-876",
                "states": []
                },
                {
                "code": "JP",
                "displayName": "Japan",
                "callingCode": "+81",
                "states": [
                    {
                    "code": "Aichi",
                    "displayName": "Aichi"
                    },
                    {
                    "code": "Akita",
                    "displayName": "Akita"
                    },
                    {
                    "code": "Aomori",
                    "displayName": "Aomori"
                    },
                    {
                    "code": "Chiba",
                    "displayName": "Chiba"
                    },
                    {
                    "code": "Ehime",
                    "displayName": "Ehime"
                    },
                    {
                    "code": "Fukui",
                    "displayName": "Fukui"
                    },
                    {
                    "code": "Fukuoka",
                    "displayName": "Fukuoka"
                    },
                    {
                    "code": "Fukushima",
                    "displayName": "Fukushima"
                    },
                    {
                    "code": "Gifu",
                    "displayName": "Gifu"
                    },
                    {
                    "code": "Gunma",
                    "displayName": "Gunma"
                    },
                    {
                    "code": "Hiroshima",
                    "displayName": "Hiroshima"
                    },
                    {
                    "code": "Hokkaido",
                    "displayName": "Hokkaido"
                    },
                    {
                    "code": "Hyogo",
                    "displayName": "Hyogo"
                    },
                    {
                    "code": "Ibaraki",
                    "displayName": "Ibaraki"
                    },
                    {
                    "code": "Ishikawa",
                    "displayName": "Ishikawa"
                    },
                    {
                    "code": "Iwate",
                    "displayName": "Iwate"
                    },
                    {
                    "code": "Kagawa",
                    "displayName": "Kagawa"
                    },
                    {
                    "code": "Kagoshima",
                    "displayName": "Kagoshima"
                    },
                    {
                    "code": "Kanagawa",
                    "displayName": "Kanagawa"
                    },
                    {
                    "code": "Kochi",
                    "displayName": "Kochi"
                    },
                    {
                    "code": "Kumamoto",
                    "displayName": "Kumamoto"
                    },
                    {
                    "code": "Kyoto",
                    "displayName": "Kyoto"
                    },
                    {
                    "code": "Mie",
                    "displayName": "Mie"
                    },
                    {
                    "code": "Miyagi",
                    "displayName": "Miyagi"
                    },
                    {
                    "code": "Miyazaki",
                    "displayName": "Miyazaki"
                    },
                    {
                    "code": "Nagano",
                    "displayName": "Nagano"
                    },
                    {
                    "code": "Nagasaki",
                    "displayName": "Nagasaki"
                    },
                    {
                    "code": "Nara",
                    "displayName": "Nara"
                    },
                    {
                    "code": "Niigata",
                    "displayName": "Niigata"
                    },
                    {
                    "code": "Oita",
                    "displayName": "Oita"
                    },
                    {
                    "code": "Okayama",
                    "displayName": "Okayama"
                    },
                    {
                    "code": "Okinawa",
                    "displayName": "Okinawa"
                    },
                    {
                    "code": "Osaka",
                    "displayName": "Osaka"
                    },
                    {
                    "code": "Saga",
                    "displayName": "Saga"
                    },
                    {
                    "code": "Saitama",
                    "displayName": "Saitama"
                    },
                    {
                    "code": "Shiga",
                    "displayName": "Shiga"
                    },
                    {
                    "code": "Shimane",
                    "displayName": "Shimane"
                    },
                    {
                    "code": "Shizuoka",
                    "displayName": "Shizuoka"
                    },
                    {
                    "code": "Tochigi",
                    "displayName": "Tochigi"
                    },
                    {
                    "code": "Tokushima",
                    "displayName": "Tokushima"
                    },
                    {
                    "code": "Tokyo",
                    "displayName": "Tokyo"
                    },
                    {
                    "code": "Tottori",
                    "displayName": "Tottori"
                    },
                    {
                    "code": "Toyama",
                    "displayName": "Toyama"
                    },
                    {
                    "code": "Wakayama",
                    "displayName": "Wakayama"
                    },
                    {
                    "code": "Yamagata",
                    "displayName": "Yamagata"
                    },
                    {
                    "code": "Yamaguchi",
                    "displayName": "Yamaguchi"
                    },
                    {
                    "code": "Yamanashi",
                    "displayName": "Yamanashi"
                    }
                ]
                },
                {
                "code": "JO",
                "displayName": "Jordan",
                "callingCode": "+962",
                "states": []
                },
                {
                "code": "KZ",
                "displayName": "Kazakhstan",
                "callingCode": "+7-6",
                "states": []
                },
                {
                "code": "KE",
                "displayName": "Kenya",
                "callingCode": "+254",
                "states": []
                },
                {
                "code": "KI",
                "displayName": "Kiribati",
                "callingCode": "+686",
                "states": []
                },
                {
                "code": "KP",
                "displayName": "Korea, North",
                "callingCode": "+850",
                "states": []
                },
                {
                "code": "KR",
                "displayName": "Korea, South",
                "callingCode": "+82",
                "states": []
                },
                {
                "code": "KW",
                "displayName": "Kuwait",
                "callingCode": "+965",
                "states": []
                },
                {
                "code": "KG",
                "displayName": "Kyrgyzstan",
                "callingCode": "+996",
                "states": []
                },
                {
                "code": "LA",
                "displayName": "Laos",
                "callingCode": "+856",
                "states": []
                },
                {
                "code": "LV",
                "displayName": "Latvia",
                "callingCode": "+371",
                "states": []
                },
                {
                "code": "LB",
                "displayName": "Lebanon",
                "callingCode": "+961",
                "states": []
                },
                {
                "code": "LS",
                "displayName": "Lesotho",
                "callingCode": "+266",
                "states": []
                },
                {
                "code": "LR",
                "displayName": "Liberia",
                "callingCode": "+231",
                "states": []
                },
                {
                "code": "LY",
                "displayName": "Libya",
                "callingCode": "+218",
                "states": []
                },
                {
                "code": "LI",
                "displayName": "Liechtenstein",
                "callingCode": "+423",
                "states": []
                },
                {
                "code": "LT",
                "displayName": "Lithuania",
                "callingCode": "+370",
                "states": []
                },
                {
                "code": "LU",
                "displayName": "Luxembourg",
                "callingCode": "+352",
                "states": []
                },
                {
                "code": "MO",
                "displayName": "Macau",
                "callingCode": "+853",
                "states": []
                },
                {
                "code": "MK",
                "displayName": "Macedonia",
                "callingCode": "+389",
                "states": []
                },
                {
                "code": "MG",
                "displayName": "Madagascar",
                "callingCode": "+261",
                "states": []
                },
                {
                "code": "MW",
                "displayName": "Malawi",
                "callingCode": "+265",
                "states": []
                },
                {
                "code": "MY",
                "displayName": "Malaysia",
                "callingCode": "+60",
                "states": []
                },
                {
                "code": "MV",
                "displayName": "Maldives",
                "callingCode": "+960",
                "states": []
                },
                {
                "code": "ML",
                "displayName": "Mali",
                "callingCode": "+223",
                "states": []
                },
                {
                "code": "MT",
                "displayName": "Malta",
                "callingCode": "+356",
                "states": []
                },
                {
                "code": "MQ",
                "displayName": "Martinique",
                "callingCode": "+596",
                "states": []
                },
                {
                "code": "MR",
                "displayName": "Mauritania",
                "callingCode": "+222",
                "states": []
                },
                {
                "code": "MU",
                "displayName": "Mauritius",
                "callingCode": "+230",
                "states": []
                },
                {
                "code": "YT",
                "displayName": "Mayotte",
                "callingCode": "+262",
                "states": []
                },
                {
                "code": "MX",
                "displayName": "Mexico",
                "callingCode": "+52",
                "states": []
                },
                {
                "code": "MD",
                "displayName": "Moldova",
                "callingCode": "+373",
                "states": []
                },
                {
                "code": "MC",
                "displayName": "Monaco",
                "callingCode": "+377",
                "states": []
                },
                {
                "code": "MN",
                "displayName": "Mongolia",
                "callingCode": "+976",
                "states": []
                },
                {
                "code": "ME",
                "displayName": "Montenegro",
                "callingCode": "+382",
                "states": []
                },
                {
                "code": "MS",
                "displayName": "Montserrat",
                "callingCode": "+1-664",
                "states": []
                },
                {
                "code": "MA",
                "displayName": "Morocco",
                "callingCode": "+212",
                "states": []
                },
                {
                "code": "MZ",
                "displayName": "Mozambique",
                "callingCode": "+258",
                "states": []
                },
                {
                "code": "MM",
                "displayName": "Myanmar",
                "callingCode": "+95",
                "states": []
                },
                {
                "code": "NA",
                "displayName": "Namibia",
                "callingCode": "+264",
                "states": []
                },
                {
                "code": "NR",
                "displayName": "Nauru",
                "callingCode": "+674",
                "states": []
                },
                {
                "code": "NP",
                "displayName": "Nepal",
                "callingCode": "+977",
                "states": []
                },
                {
                "code": "NL",
                "displayName": "Netherlands",
                "callingCode": "+31",
                "states": []
                },
                {
                "code": "AN",
                "displayName": "Netherlands Antilles",
                "callingCode": "+599",
                "states": []
                },
                {
                "code": "NC",
                "displayName": "New Caledonia",
                "callingCode": "+687",
                "states": []
                },
                {
                "code": "NZ",
                "displayName": "New Zealand",
                "callingCode": "+64",
                "states": []
                },
                {
                "code": "NI",
                "displayName": "Nicaragua",
                "callingCode": "+505",
                "states": []
                },
                {
                "code": "NE",
                "displayName": "Niger",
                "callingCode": "+227",
                "states": []
                },
                {
                "code": "NG",
                "displayName": "Nigeria",
                "callingCode": "+234",
                "states": []
                },
                {
                "code": "NU",
                "displayName": "Niue",
                "callingCode": "+683",
                "states": []
                },
                {
                "code": "NF",
                "displayName": "Norfolk Island",
                "callingCode": "+672",
                "states": []
                },
                {
                "code": "MP",
                "displayName": "Northern Mariana Islands",
                "callingCode": "+1-670",
                "states": []
                },
                {
                "code": "NO",
                "displayName": "Norway",
                "callingCode": "+47",
                "states": []
                },
                {
                "code": "OM",
                "displayName": "Oman",
                "callingCode": "+968",
                "states": []
                },
                {
                "code": "PK",
                "displayName": "Pakistan",
                "callingCode": "+92",
                "states": []
                },
                {
                "code": "PW",
                "displayName": "Palau",
                "callingCode": "+680",
                "states": []
                },
                {
                "code": "PS",
                "displayName": "Palestinian Territory",
                "callingCode": "+970",
                "states": []
                },
                {
                "code": "PA",
                "displayName": "Panama",
                "callingCode": "+507",
                "states": []
                },
                {
                "code": "PG",
                "displayName": "Papua New Guinea",
                "callingCode": "+675",
                "states": []
                },
                {
                "code": "PY",
                "displayName": "Paraguay",
                "callingCode": "+595",
                "states": []
                },
                {
                "code": "PE",
                "displayName": "Peru",
                "callingCode": "+51",
                "states": []
                },
                {
                "code": "PH",
                "displayName": "Philippines",
                "callingCode": "+63",
                "states": []
                },
                {
                "code": "PN",
                "displayName": "Pitcairn",
                "callingCode": "+872",
                "states": []
                },
                {
                "code": "PL",
                "displayName": "Poland",
                "callingCode": "+48",
                "states": []
                },
                {
                "code": "PT",
                "displayName": "Portugal",
                "callingCode": "+351",
                "states": []
                },
                {
                "code": "QA",
                "displayName": "Qatar",
                "callingCode": "+974",
                "states": []
                },
                {
                "code": "RE",
                "displayName": "Reunion",
                "callingCode": "+262",
                "states": []
                },
                {
                "code": "RO",
                "displayName": "Romania",
                "callingCode": "+40",
                "states": []
                },
                {
                "code": "RU",
                "displayName": "Russian Federation",
                "callingCode": "+7",
                "states": []
                },
                {
                "code": "RW",
                "displayName": "Rwanda",
                "callingCode": "+250",
                "states": []
                },
                {
                "code": "BQ-SA",
                "displayName": "Saba",
                "callingCode": "+599",
                "states": []
                },
                {
                "code": "SH",
                "displayName": "Saint Helena",
                "callingCode": "+290",
                "states": []
                },
                {
                "code": "KN",
                "displayName": "Saint Kitts and Nevis",
                "callingCode": "+1-869",
                "states": []
                },
                {
                "code": "LC",
                "displayName": "Saint Lucia",
                "callingCode": "+1-758",
                "states": []
                },
                {
                "code": "PM",
                "displayName": "Saint Pierre and Miquelon",
                "callingCode": "+508",
                "states": []
                },
                {
                "code": "VC",
                "displayName": "Saint Vincent and the Grenadines",
                "callingCode": "+1-784",
                "states": []
                },
                {
                "code": "WS",
                "displayName": "Samoa",
                "callingCode": "+685",
                "states": []
                },
                {
                "code": "SM",
                "displayName": "San Marino",
                "callingCode": "+378",
                "states": []
                },
                {
                "code": "ST",
                "displayName": "Sao Tome and Principe",
                "callingCode": "+239",
                "states": []
                },
                {
                "code": "SA",
                "displayName": "Saudi Arabia",
                "callingCode": "+966",
                "states": []
                },
                {
                "code": "SN",
                "displayName": "Senegal",
                "callingCode": "+221",
                "states": []
                },
                {
                "code": "RS",
                "displayName": "Serbia",
                "callingCode": "+381",
                "states": []
                },
                {
                "code": "SC",
                "displayName": "Seychelles",
                "callingCode": "+248",
                "states": []
                },
                {
                "code": "SL",
                "displayName": "Sierra Leone",
                "callingCode": "+232",
                "states": []
                },
                {
                "code": "SG",
                "displayName": "Singapore",
                "callingCode": "+65",
                "states": []
                },
                {
                "code": "SK",
                "displayName": "Slovakia",
                "callingCode": "+421",
                "states": []
                },
                {
                "code": "SI",
                "displayName": "Slovenia",
                "callingCode": "+386",
                "states": []
                },
                {
                "code": "SB",
                "displayName": "Solomon Islands",
                "callingCode": "+677",
                "states": []
                },
                {
                "code": "SO",
                "displayName": "Somalia",
                "callingCode": "+252",
                "states": []
                },
                {
                "code": "ZA",
                "displayName": "South Africa",
                "callingCode": "+27",
                "states": []
                },
                {
                "code": "GS",
                "displayName": "South Georgia and the South Sandwich Islands",
                "callingCode": "",
                "states": []
                },
                {
                "code": "ES",
                "displayName": "Spain",
                "callingCode": "+34",
                "states": []
                },
                {
                "code": "LK",
                "displayName": "Sri Lanka",
                "callingCode": "+94",
                "states": []
                },
                {
                "code": "BQ-SE",
                "displayName": "St Eustatius",
                "callingCode": "+599",
                "states": []
                },
                {
                "code": "SX",
                "displayName": "St Maarten",
                "callingCode": "+1",
                "states": []
                },
                {
                "code": "SD",
                "displayName": "Sudan",
                "callingCode": "+249",
                "states": []
                },
                {
                "code": "SR",
                "displayName": "Suriname",
                "callingCode": "+597",
                "states": []
                },
                {
                "code": "SJ",
                "displayName": "Svalbard and Jan Mayen",
                "callingCode": "+79",
                "states": []
                },
                {
                "code": "SZ",
                "displayName": "Swaziland",
                "callingCode": "+268",
                "states": []
                },
                {
                "code": "SE",
                "displayName": "Sweden",
                "callingCode": "+46",
                "states": []
                },
                {
                "code": "CH",
                "displayName": "Switzerland",
                "callingCode": "+41",
                "states": []
                },
                {
                "code": "SY",
                "displayName": "Syrian Arab Republic (Syria)",
                "callingCode": "+963",
                "states": []
                },
                {
                "code": "TW",
                "displayName": "Taiwan",
                "callingCode": "+886",
                "states": []
                },
                {
                "code": "TJ",
                "displayName": "Tajikistan",
                "callingCode": "+992",
                "states": []
                },
                {
                "code": "TZ",
                "displayName": "Tanzania",
                "callingCode": "+255",
                "states": []
                },
                {
                "code": "TH",
                "displayName": "Thailand",
                "callingCode": "+66",
                "states": []
                },
                {
                "code": "TG",
                "displayName": "Togo",
                "callingCode": "+228",
                "states": []
                },
                {
                "code": "TK",
                "displayName": "Tokelau",
                "callingCode": "+690",
                "states": []
                },
                {
                "code": "TO",
                "displayName": "Tonga",
                "callingCode": "+676",
                "states": []
                },
                {
                "code": "TT",
                "displayName": "Trinidad and Tobago",
                "callingCode": "+1-868",
                "states": []
                },
                {
                "code": "TN",
                "displayName": "Tunisia",
                "callingCode": "+216",
                "states": []
                },
                {
                "code": "TR",
                "displayName": "Turkey",
                "callingCode": "+90",
                "states": []
                },
                {
                "code": "TM",
                "displayName": "Turkmenistan",
                "callingCode": "+993",
                "states": []
                },
                {
                "code": "TC",
                "displayName": "Turks and Caicos Islands",
                "callingCode": "+1-649",
                "states": []
                },
                {
                "code": "TV",
                "displayName": "Tuvalu",
                "callingCode": "+688",
                "states": []
                },
                {
                "code": "UG",
                "displayName": "Uganda",
                "callingCode": "+256",
                "states": []
                },
                {
                "code": "UA",
                "displayName": "Ukraine",
                "callingCode": "+380",
                "states": []
                },
                {
                "code": "AE",
                "displayName": "United Arab Emirates",
                "callingCode": "+971",
                "states": []
                },
                {
                "code": "GB",
                "displayName": "United Kingdom",
                "callingCode": "+44",
                "states": []
                },
                
                {
                "code": "UM",
                "displayName": "United States Minor Outlying Islands",
                "callingCode": "+808",
                "states": []
                },
                {
                "code": "UY",
                "displayName": "Uruguay",
                "callingCode": "+598",
                "states": []
                },
                {
                "code": "UZ",
                "displayName": "Uzbekistan",
                "callingCode": "+998",
                "states": []
                },
                {
                "code": "VU",
                "displayName": "Vanuatu",
                "callingCode": "+678",
                "states": []
                },
                {
                "code": "VA",
                "displayName": "Vatican City",
                "callingCode": "+379",
                "states": []
                },
                {
                "code": "VE",
                "displayName": "Venezuela",
                "callingCode": "+58",
                "states": []
                },
                {
                "code": "VN",
                "displayName": "Vietnam",
                "callingCode": "+84",
                "states": []
                },
                {
                "code": "VI",
                "displayName": "Virgin Islands, US",
                "callingCode": "+1-340",
                "states": []
                },
                {
                "code": "WF",
                "displayName": "Wallis and Futuna",
                "callingCode": "+681",
                "states": []
                },
                {
                "code": "EH",
                "displayName": "Western Sahara",
                "callingCode": "+212",
                "states": []
                },
                {
                "code": "YE",
                "displayName": "Yemen ",
                "callingCode": "+967",
                "states": []
                },
                {
                "code": "YU",
                "displayName": "Yugoslavia",
                "callingCode": "+381",
                "states": []
                },
                {
                "code": "ZM",
                "displayName": "Zambia",
                "callingCode": "+260",
                "states": []
                },
                {
                "code": "ZW",
                "displayName": "Zimbabwe",
                "callingCode": "+263",
                "states": []
                }
            ]
        };

        return countryList;
    }    
}