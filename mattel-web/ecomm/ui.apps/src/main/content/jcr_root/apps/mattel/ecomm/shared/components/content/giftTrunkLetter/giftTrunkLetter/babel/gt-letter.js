import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import { getCookie } from '../shared/browserCookie';

class gtLetter {
      constructor() {
            self = this;
            evtBinding.bindLooping(this.bindingEventsConfig(), this);
      }
      bindingEventsConfig() {
            let eventsArr = {};
            return eventsArr;
      }
      init() { }
      resetContent(ele) {
         
            $('.gt-ltr-edit').html($('.gt-default-ltr-holder').html());
      }
}

var letterGbl = {};
$(document).ready(function () {
      var ltrCont = $('.messageContent p');
      $(ltrCont).each(function (index) {
            if ($(this).html() == "&nbsp;") {
                  $(this).html(" ");
            }
      });

      if (localStorage.getItem('letterEdited') == 'Y') {
            var str = localStorage.getItem('letterContentPersistant');
            var lines = str.split(/\r?\n/);
            var htmlContent = "";
            for (var i = 0; i < lines.length; i++) {
                  htmlContent += '<p>' + lines[i] + '</p>';
            }
            $('.messageContent').html(htmlContent);
      }

      letterGbl.errShow = $('#editLetter .modal-footer span');
      letterGbl.errShowXs = $('#editLetter .dialog-modal-footer span');
      letterGbl.defaultDisplay = $('.gt-default-ltr-holder span');
      loadDefaultLetterFormat();

      $(document).on('click', '#openModal', function (event) {
            $('.theCount').hide();
			formatPara();
      });

      letterGbl.charLengthLimit = parseInt($('#messageBox').attr('data-charLength'));
      letterGbl.editedText = '';
      letterGbl.linesUsed = $('#linesUsed');
      letterGbl.linesUsedXs = $('#linesUsed-xs');
});

$(document).on('keyup', '#messageBox', function (e) {
      $(letterGbl.errShow).css('display', 'none');
      $(letterGbl.errShowXs).css('display', 'none');

      var editedstr = $('#messageBox').val().length,
            newLines = ($('#messageBox').val().match(/\n/g) || []).length,
            charLimit = editedstr - newLines,
            remainingChars = letterGbl.charLengthLimit - charLimit,
            lineLength = 0,
            lineCount = 1,
            lastSpace = 0,
            lines = parseInt($('#messageBox').attr('data-lineLength')),
            maxchars = 50;

      $('.theCount').show();

      if (remainingChars < 0) {
            $('#charcount, #charcount-xs').text(remainingChars).css('color', 'red');
            $(letterGbl.errShow).eq(0).css('display', 'block');
            $(letterGbl.errShowXs).eq(0).css('display', 'block');
            $("#acceptBtn, #acceptBtn-xs").addClass('disabled').attr('disabled', true);
      } else {
            $("#acceptBtn, #acceptBtn-xs").removeClass('disabled').attr('disabled', false);
            $('#charcount, #charcount-xs').text(remainingChars).css('color', 'black');//display the no. of charecters
      }

      for (var i = 0; i < $('#messageBox').val().length; i++) {
            if ($('#messageBox').val().charAt(i) === "\n") {
                  lineCount++;
                  lineLength = 0;
            } else {
                  lineLength++;
                  if ($('#messageBox').val().charAt(i) === " ") {
                        lastSpace = i;
                  }
                  if (lineLength > maxchars) {
                        lineCount++;
                        if (i === lastSpace || lastSpace === 0) {
                              lineLength = 1;
                        } else {
                              lineLength = i - lastSpace;
                        }
                        if (lineLength > maxchars) {
                              lineLength = 1;
                        }
                  } //end if line length exceeded
            } //end if newline
      } //end for loop

      $("#linesUsed, #linesUsed-xs").text(lines - lineCount).css('color', '');
      if (lineCount <= lines) {
            if (remainingChars >= 0) {
                  $("#acceptBtn, #acceptBtn-xs").removeClass('disabled').attr('disabled', false);
            }
      }

      if (lineCount > lines) {
            $("#linesUsed, #linesUsed-xs").css('color', 'red');
            $(letterGbl.errShow).eq(0).css('display', 'block');
            $(letterGbl.errShowXs).eq(0).css('display', 'block');
            $("#acceptBtn, #acceptBtn-xs").addClass('disabled').attr('disabled', true);
            return false;
      }

      validationCheck($('#messageBox').val());
      var str = $(this).val();
      var lines_etr = str.split(/\r?\n/);
      var html = "";
      for (var a = 0; a < lines_etr.length; a++) {
            html += '<p>' + lines_etr[a] + '</p>';
      }
      letterGbl.editedText = html;

});

var htmlString = "";
$(document).on('click', '#acceptBtn, #acceptBtn-xs', function (event) {
      event.preventDefault();
      var editedstr = $('#messageBox').val();
      var lines = editedstr.split(/\r?\n/);
      var html = "";
      for (var i = 0; i < lines.length; i++) {
            html += '<p>' + lines[i] + '</p>';
      }
      letterGbl.editedText = html;
      profanityCheck(letterGbl.editedText, event);
});

$(document).on('click', '.modal-header .close', function (e) {
      $(letterGbl.errShow).css('display', 'none');
      $(letterGbl.errShowXs).css('display', 'none');
      formatPara();
});

$('#editLetter').on('hidden.bs.modal', function (e) {
      $(letterGbl.errShow).css('display', 'none');
      $(letterGbl.errShowXs).css('display', 'none');
      formatPara();
});

$(document).on('keydown', '#acceptBtn', function (e) {
      if (e.keyCode == 9) {
            $(this).parents('#editLetter').find("#modalClose").focus();
      }
});

function formatPara() {
      var temp = "";
      var msgText = $('.messageContent:first p');
      $(msgText).each(function (index) {
            temp += $(this).text() + '\n';
      });
      var trimTemp = temp.trim();
      $('#messageBox').val(trimTemp);
      $("#messageBox").get(0).setSelectionRange(0,0);
}

function validationCheck(str) {
      if (!$('#acceptBtn .modal-footer button').hasClass('disabled') ||
            !$('#acceptBtn-xs .dialog-modal-footer button').hasClass('disabled')) {
            var editLetterRegex = new RegExp("^[ A-Za-z_!&'’+.?,-]*$");
            str = $('#messageBox').val();
            if (!letterGbl.errShow.eq(0).is(':visible') || !letterGbl.errShowXs.eq(0).is(':visible')) {
                  var str1 = str.replace(/\n/g, " ");
                  var str2 = str1.replace(/<br\s*\/?>/gi, ' ');
                  var str3 = $.trim(str2);
                  if (!editLetterRegex.test(str3)) {
                        $(letterGbl.errShow).eq(1).css('display', 'block');
                        $(letterGbl.errShowXs).eq(1).css('display', 'block');
                        $("#acceptBtn, #acceptBtn-xs").addClass('disabled').attr('disabled', true);
                        event.preventDefault();
                        return false;
                  }
                  else {
                        $(letterGbl.errShow).eq(1).css('display', 'none');
                        $(letterGbl.errShowXs).eq(1).css('display', 'none');
                  }

            }

      }
}
function profanityCheck(editedstr, event) {
      if (!$('#acceptBtn .modal-footer button').hasClass('disabled') ||
            !$('#acceptBtn-xs .dialog-modal-footer button').hasClass('disabled')) {
            $(letterGbl.errShow).eq(1).css('display', 'none');
            $(letterGbl.errShowXs).eq(1).css('display', 'none');
            let ajaxOption = apiConfigInst.getApiConfig("profanityCheck").startCheck;
            ajaxOption.data = JSON.stringify({ "giftMsgText": editedstr, "validateType": "giftMsg" });
            request.ajaxCall(ajaxOption).then(data => {
                  if (data.hasOwnProperty("errorCode") || data.isNameValid) {
                        profanitySuccess(editedstr);
                  }
                  else {
                        $(letterGbl.errShow).eq(2).css('display', 'block');
                        $(letterGbl.errShowXs).eq(2).css('display', 'block');
                        $("#acceptBtn, #acceptBtn-xs").addClass('disabled').attr('disabled', true);
                        return false;
                  }
            })
                  .fail(function (err) {
                        console.log("WCS Failed" + err);
                        profanitySuccess(editedstr);
                  })
                  .catch(error => {
                        console.log("Profanity catch");
                        profanitySuccess(editedstr);
                  });
      }
}

function profanitySuccess(editedstr) {
      $('.messageContent').html(editedstr);
      $('#editLetter').modal('hide');
      $("#addToBagbutton").removeClass("disabled");
      $("#buyNowbutton").removeClass("disabled");
      htmlString = "";
      var msg = $('.messageContent:first p');
      $(msg).each(function (index) {
            var textValue = this.innerText;
            htmlString += formattedText(textValue) + "\n";
      });
      localStorage.setItem("letterContent", htmlString.trim());
      localStorage.setItem("letterContentPersistant", $('.messageContent').html());
      /** Js for summary Page */
      //$('.gt-bundle-selection-wrapper').siblings('.bundle-footer').css('display', 'block');
      $('#submitLetter').parents('.letter').find('.selected').css('display', 'table');
      $('#submitLetter').parents('.letter').find('.option-details').removeClass('open').addClass('close');
      $('.gt-ltr-left').hide();
      $('.gt-product-doll-image').show();
      $('#submitLetter').parents('.option').removeClass("gtFlowIndex");
      $('#submitLetter').parents('.option').addClass("choiceSelected");

      let finalLetterMsg = $('.messageContent:first p').html();
      let authoredLetter = $('.gt-ltr-authored p').html();

      if (authoredLetter == finalLetterMsg) {
            localStorage.setItem("letterEdited", "N");
      } else {
            localStorage.setItem("letterEdited", "Y");
      }
	  moveToIncludeExtras();
}

$(document).on('click', '#submitLetter', function () {
      htmlString = "";
      var msg = $('.messageContent:first p');
      $(msg).each(function (index) {
            var textValue = this.innerText;
            htmlString += formattedText(textValue) + "\n";
      });
      localStorage.setItem("letterContent", htmlString);
      let finalLetterMsg = $('.messageContent:first p').html();
      let authoredLetter = $('.gt-ltr-authored p').html();

      if (authoredLetter == finalLetterMsg) {
            localStorage.setItem("letterEdited", "N");
      } else {
            localStorage.setItem("letterEdited", "Y");
      }
	   moveToIncludeExtras();
});
function moveToIncludeExtras()
{
	let extrasTop = $('.include-extras').offset().top - 60;
	  console.log("into letter"+extrasTop);
		$('html, body').animate({
		  scrollTop: extrasTop
		}, 600);
}
function formattedText(text) {
      var maxLength = 50;

      /**
      * Main function which will initiate iterative calls to splitAtLine based on 
       * non breaking word index (nbIndex)
      */
      function splitText(text) {
            var char = text.charAt(maxLength);
            if (char == "") {
                  return splitAtLine(text, maxLength)
            } else {
                  var nbIndex = findNBLine(text);
                  if (nbIndex > 0)
                        return splitAtLine(text, nbIndex);
                  else
                        return '';
            }
      }

      /**
      * Splits the @param{text} into 2 parts at @param {maxLength} and joins them with \n
      */
      function splitAtLine(text, maxLength) {
            let textpart1 = text.slice(0, maxLength);
            let textpart2 = text.slice(maxLength);
            if (maxLength < text.length) {
                  textpart2 = splitText(text.slice(maxLength));
            }
            return (textpart1.trim() + "\n" + textpart2.trim());
      }

      /**Returns non breaking word index*/
      function findNBLine(text) {
            var nbIndex;
            for (var i = maxLength; i >= 0; i--) {
                  if (text.charAt(i) == " ") {
                        nbIndex = i;
                        break;
                  }
            }
            return nbIndex;
      }
      var result = splitText(text);
      return result.trim();
}

$(document).on('click', '#openModal', function (event) {
      $('#openModal').blur();
      $("#messageBox").focus();
});

function loadDefaultLetterFormat(ele) {
     
      $.each(letterGbl.defaultDisplay, (idx, obx) => {
            $('.gt-default-ltr-holder span.' + $(obx).attr('class')).html(getCookie($(obx).attr('class')));
            $('.gt-ltr-authored span.' + $(obx).attr('class')).html(getCookie($(obx).attr('class')));
            $('.gt-ltr-edit span.' + $(obx).attr('class')).html(getCookie($(obx).attr('class')));
            $('.gt-ltr-personalize span.' + $(obx).attr('class')).html(getCookie($(obx).attr('class')));

      });
}

let self;
const evtBinding = new eventBinding();
const gtLetterInstance = new gtLetter();
const apiConfigInst = new apiConfig();
const request = new ajaxRequest();
gtLetterInstance.init();
