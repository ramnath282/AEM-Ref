/**
 * game_component.js
 * Version 1.0
 * 
 */

(function(global, PLAYAEM) {
    var gamePlayArea = {
        el: '.game-player-area-content',
        bindingEventsConfig: function() {
            if (PLAYAEM.isLoaded){
	            return;
            }	
        },
        render: function() {
            var desktopWidth = $('.game-player-area-container').data('width');
            var desktopHeight = $('.game-player-area-container').data('height');
            /*var mobileWidth = $('.game-player-area-container').data('mwidth'); 
            var mobileHeight = $('.game-player-area-container').data('mheight');*/
            if(desktopWidth != 0 || desktopHeight !=0 ){
                $('.game-player-area-container').css('width', desktopWidth);
                $('.game-player-area-container').css('height', desktopHeight);
            }
            //Responsive for mobile devices
            
            if (PLAYAEM.isMobile){
            	 $('.game-player-area-container').css('width', 315);
                 $('.game-player-area-container').css('height', 238);
             /*   if(mobileWidth != 0 || mobileHeight !=0 ){
                    $('.game-player-area-container').css('width', mobileWidth);
                    $('.game-player-area-container').css('height', mobileHeight);
                }*/
            }
            // Find all iframes
            var $iframes = $( ".game_frame" );
            $iframes.each(function () {
                $( this ).data( "ratio", this.height / this.width )
                // Remove the hardcoded width & height attributes
                .removeAttr( "width" )
                .removeAttr( "height" );
            });

            // Resize the iframes when the window is resized
            $( window ).resize( function () {
                $iframes.each( function() {
                // Get the parent container & width
                    var width = $( this ).parent().width();

                    $( this ).width( width ).height( width * $( this ).data( "ratio" ) );
                });
                // Resize to fix all iframes on page load.
            }).resize();
        },


        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.retailers) return;
            //PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    gamePlayArea.init();
    PLAYAEM.gamePlayArea = gamePlayArea;
    document.addEventListener('DOMContentLoaded', function() {
        if (!PLAYAEM.isDependencyLoaded) {
            gamePlayArea.init();
        }
    }, false);
}(window, PLAYAEM));
