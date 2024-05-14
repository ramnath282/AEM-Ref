(function ($, $document) {
    "use strict";
    function adjustLayoutHeight(){
        $(".customer-info-tabs").css("width", "785px");
        $(".customer-info-tabs").css("margin-left", "50px");
    }
    $document.on("dialog-ready", function() {
        adjustLayoutHeight();
    });
    $(document).on("click", ".cq-dialog-submit", function (e) {
        var ui = $(window).adaptTo("foundation-ui");
        var childGender = $('.gender-label').val();
        var genderBoy = $('.gender-boy').val();
        var genderGirl = $('.gender-girl').val();
        var genderExpecting = $('.gender-expecting').val();
        var dobLabel = $('.dob-label').val();
        var relationshipLabel = $('.relationship-label').val();
        var relationMom = $('.relation-mom').val();
        var relationDad = $('.relation-dad').val();
        var relationgrandParent = $('.relation-grand-parent').val();
        var relationFriend = $('.relation-friend').val();
        if (relationshipLabel!='' && relationMom=='' && relationDad==''&& relationFriend=='' && relationgrandParent==''){
            ui.alert("Error","Please enter atleast relationship");
            return false;
        }
        if (dobLabel != '' && childGender==''){
            ui.alert("Error","Please enter child gender label");
            return false;
        }
        if (childGender!='' && genderBoy=='' && genderGirl=='' && genderExpecting==''){
            ui.alert("Error","Please enter atleast one gender");
            return false;
        }
    });
})($, $(document));