const config = {
    el: '.news-search',
    typeHint: true,
    typeHighlight: true,
    typeMinLength: parseInt(document.getElementById("characterLimit").value),
    typeMaxLength: 5,
    searchList: 5,
    DisMaxItem: document.getElementById("suggestionLimit").value,
    typeOrder: "asn",
    typeDelay: 300,
    globalData: 0,
    globalSearch: '',
    typeCache: true,
    sAndPLink: document.getElementById("typeAheadUrl").value,
    searchUrl: $("#searchResultPageLink").val()
}

class searchProduct {

    constructor(){
        self = this;
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(),self);
    }

    getApiConfig(name) {
        var listingComponentInfo = {
            "newsPredictiveSearch":  {
                "type": 'get',
                "accept": 'application/json',
                "crossDomain": true,
                "url": config.sAndPLink + '?query=',
			},
        }
        return listingComponentInfo[name];
    }

    bindingEventsConfig() {
        var events = {
            "focus #searchInput_field": "defaultDisplay",
            "blur #searchInput_field": "removeFocus",
            "keyup #searchInput_field": "checkinputCharacter",
            "click .search-icon":"searchIconClick",
            "click .search-input-field .close-icon":"closeIconClick",
            "click .tt-suggestion": "searchSubmit",
        }
        return events;
    }

    
    searchData() {
        const apiconfig = self.getApiConfig('newsPredictiveSearch');
        $.ajax(apiconfig)
        .done(data => {
            if(data && data.length && (data.length > 0)) {
                var dataArr = data.replace(/[\(\)\"\[\]{}]/g, "").split(',');
                var result = dataArr.map(function (el) {
                    return el.trim();
                })
                    $('.typeahead').typeahead({
                    hint: config.typeHint,
                    highlight: config.typeHighlight,
                    minLength: config.typeMinLength,
                    maxLength: config.typeMaxLength,
                    maxItem: config.searchList,    
                    items: config.searchList,
                    sorter : config.typeOrder,
                    delay: config.typeDelay,
                    cache: false
                }, {
                    name: 'result',
                    source:self.substringMatcher(result),
                    limit:config.DisMaxItem
                    }).on('keyup', function(el, evt) {
                        var txt =  $(el.target).val().toLowerCase();
                        window._satellite.cookie.set("searchType","user-entered-search", 0);
                        if (el.which == 13) {
                            self.searchSubmit('', evt);
                         }
                    });
             }
           
        })
        .fail(err => {
          console.log(err);
        })
    }

    searchSubmit(ele, evt) {
        if ($(ele).hasClass("tt-suggestion")){
            window._satellite.cookie.set("searchType","typeahead", 0);
        }
        let searchKeyword = $("#searchInput_field").val();
        let redirectURL = config.searchUrl + '?searchTerm=' + searchKeyword;
        window.location = redirectURL;
    }

    searchIconClick() {
        $(".search-input-field .input-group").removeClass("hide");
	$("#searchInput_field").focus();
        $(".overlay").removeClass("hide");
	$("#searchInput_field").val('');
    }
    closeIconClick(el) {
        $(".search-input-field .input-group").addClass("hide");
        $("#searchInput_field").val('');
        $(".tt-dataset-result").empty();
        if(window.innerWidth > 1024) {
            $(".overlay").addClass("hide");
        }
        if(window.innerWidth <= 1024) {
            $(".search-input-field .input-group").css("border-bottom","1px solid #000");
            $(el).hide();
        }
    }
    checkinputCharacter(el) {
        if($(el).val().length){
            $('.close-icon').show();
        }
        else{
            $('.close-icon').hide();
        }
    }
    removeFocus(){
        if(window.innerWidth <= 1024) {
            $('.news-search').find('.search-input-field').removeClass('search-focus');
        }
    }
    defaultDisplay(el){
        if(window.innerWidth > 1024) {
            $(".overlay").removeClass("hide");
        }
        if(window.innerWidth <= 800) {
            $(".search-input-field .input-group").css("border-bottom","2px solid #000");
        }
        if(window.innerWidth <= 1024) {
            $('.news-search').find('.search-input-field').addClass('search-focus');
        }
    }

    substringMatcher(strs) {
        return function findMatches(q, cb) {
            var matches, substrRegex;
            // an array that will be populated with substring matches
            matches = [];
            // regex used if a string contains the substring `q`
            substrRegex = new RegExp(q, 'i');
            // contains the substring `q`, add it to the `matches` array
            $.each(strs, function(i, str) {
                if (substrRegex.test(str)) {
                    matches.push(str);
                }
            });
            if(matches.length){
                cb(matches);
            }else{
                const noSearchStr = 'Oh no! No search Results for: "' + q + '"';
                matches.push(noSearchStr);
                cb(matches);
            }
        };
    }

    render(){
        this.searchData();
	$('body').click(function(evt) {
            if(evt.target.id == "searchInput_field"){
                return;
            }
            else{
                $("#search-input-field .tt-menu").hide();
                $(".tt-dataset-result").empty();
                $(".search-input-field .input-group").addClass("hide");
                if(window.innerWidth > 1024) {
                    $(".overlay").addClass("hide");
                }
            }
         });
    }
    init(){
        self = this;
        this.render();
   }

};

let self;
window.global.SearchProductInstance = window.global.SearchProductInstance || new searchProduct();
window.global.SearchProductInstance.init();
