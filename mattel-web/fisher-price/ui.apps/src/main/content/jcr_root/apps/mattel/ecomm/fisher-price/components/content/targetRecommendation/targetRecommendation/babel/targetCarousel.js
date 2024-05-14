const config = {
  recentelementVar: '.target-carousal-component-outer-wrapper',
  badgeApiCallCounter: 0
}

class recentArticlecomponentInfo {
constructor () {
  this.element = config.recentelementVar;
}

init () {
  const targetSelf = this;
  window.addEventListener('resize', () => {
    targetSelf.pickValuesForSlider();
  });
  window.addEventListener('deviceorientation', () => {
    targetSelf.pickValuesForSlider();
  });  
}

pickValuesForSlider(){
  const targetSelf = this;
  $(config.recentelementVar).each(function() {
    const desktopItem = $(this).data('desktop'),
          tabletItem = $(this).data('tablet'),
          mobileItem = $(this).data('mobile'),
          slider= $(this).data('slider');
          $('.target-carousal-component-outer-wrapper').imagesLoaded(function () {
            targetSelf.applyMySlider(desktopItem,tabletItem,mobileItem,slider);
          }); 
  });
}

applyMySlider(desktop,tablet,mobile,slider){
  const targetSelf = this;
  $('.' + slider + " .target-carousal-content").not('.slick-initialized').slick({
    dots: true,
    infinite: false,
    speed: 300,
    slidesToShow: desktop,
    slidesToScroll: desktop,
    responsive: [
      {
        breakpoint: 992,
        settings: {
          slidesToShow: tablet,
          slidesToScroll: tablet,
          dots: true
        }
      },
      {
        breakpoint: 767,
        settings: {
          slidesToShow: mobile,
          slidesToScroll: mobile,
          dots: true
        }
      }
    ]
  });

  setTimeout(() => {
    targetSelf.setarrowHeight(slider);
	if(config.badgeApiCallCounter === 0){
		targetSelf.productBadgeCall();
	  }
  }, 800);
}

productBadgeCall(){
	config.badgeApiCallCounter++;
  const ajaxUrl = window.location.protocol + "//" + window.location.host + "/bin/getProductBadge" + "?currentPath=" + $('#currentPagePathForAnalytics').val() + "&isFull=true";
  $.ajax({url: ajaxUrl, success: function(response){
      $( ".target-carousal-component-container li" ).each(function() {
          var productBadges = $(this).find(".product-badges");
          productBadges.hide();
          var badgeText = productBadges.text();
          var badgeData = response.productBageList;
          $.each(badgeData,function(index,item){
              if(item.badge === badgeText) {
              var icon = item.badgeIcon;
                  productBadges.text(item.badgeDisplayValue);
                  productBadges.css({"color":item.textColour,"background-color":item.badgeColour,"border":"1px solid "+item.badgeColour});
                  productBadges.prepend("<img src="+icon+" alt='' />");
                  productBadges.show();
              }
          });
      });
  }});
}

setarrowHeight(slider){
  let imageHeight = $('.' + slider + " .img-wrapper").find('img').height();
  $('.' + slider + " .slick-arrow").css("top",(imageHeight/2));
}

}
window.global.recentArticlecomponentInstance = window.global.recentArticlecomponentInstance || new recentArticlecomponentInfo();
window.global.recentArticlecomponentInstance.init();