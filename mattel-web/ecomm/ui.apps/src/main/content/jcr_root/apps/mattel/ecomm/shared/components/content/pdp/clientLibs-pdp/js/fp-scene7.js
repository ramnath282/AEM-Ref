
(function () {
			
    var imgTxt,zoomTxt,mediaSet, container, zoomView, swatches, zoomInButton, zoomOutButton, zoomResetButton, skuID;
    var bodyLan = $('html')[0].lang;
    // Initialize the SDK  
    s7sdk.Util.init();

      var scImage = {
    US: 'Images',
    DE: 'Bilder',
    IT: 'Immagini',
    FR: 'Images',
    ES: 'Im&#225;genes'
   }

   var scZoom = {
    US: 'Double click to zoom',
    DE: 'Doppelklicken zum Vergr&#246;&#223;ern',
    IT: 'Doppio clic per ingrandire',
    FR: 'Double-cliquez pour zoomer',
    ES: 'Haga doble clic para ampliar'
   }
        
     switch(bodyLan) {
    
      case  "en-US":
      imgTxt = scImage.US;
      break;
      case  "en-GB":
      imgTxt = scImage.US;
      break;
      case  "de-DE":
      imgTxt = scImage.DE;
      break;
      case  "it-IT":
      imgTxt = scImage.IT;
      break;
      case  "fr-FR":
      imgTxt = scImage.FR;
      break;
      case  "es-ES":
      imgTxt = scImage.ES;
      break;
      default:
      imgTxt = '';
    
    }
       
    switch(bodyLan) {
     
        case  "en-US":
        zoomTxt = scZoom.US;
        break;
        case  "en-GB":
        zoomTxt = scZoom.US;
        break;
        case  "de-DE":
        zoomTxt = scZoom.DE;
        break;
        case  "it-IT":
        zoomTxt = scZoom.IT;
        break;
        case  "fr-FR":
        zoomTxt = scZoom.FR;
        break;
        case  "es-ES":
        zoomTxt = scZoom.ES;
        break;
      
        default:
        zoomTxt = '';
      
      }

    skuID = document.getElementById('scene7SKU').value;

    var params = new s7sdk.ParameterManager();

    /* Event handler for s7sdk.Event.SDK_READY dispatched by ParameterManager to initialize various components of this viewer. */
    function initViewer() {

        /* Modifiers can be added directly to ParameterManager instance */
        params.push("asset", "Mattel/"+skuID);
        params.push("serverurl", "https://s7d9.scene7.com/is/image");
        params.push("contenturl", "https://s7d9.scene7.com/skins/");
        params.push("videoserverurl", "https://s7d9.scene7.com/is/content/");

        /* Modifiers added directly based on screen dimension to ParameterManager instance */
        params.push("Swatches.scrolltransition","1.5");
        params.push("Swatches.scrollstep","2,2");
        params.push("ZoomView.frametransition","slide");
        
        if($(window).width() > 991) {
            params.push("Swatches.tmblayout", "1,0");
            params.push("Swatches.orientation", "1");
        } 
        else if($(window).width() < 992) {	
            params.push("Swatches.tmblayout", "0,1");
            params.push("Swatches.orientation", "0");
            if($(window).width() < 768) {	
                params.push("Swatches.align","center,top");
            } else {
                params.push("Swatches.align","center,bottom");
            }
        }

        /* Create components and assign event listeners */
        $('#s7viewerCont').empty();
        container = new s7sdk.common.Container("s7viewerCont", params, "s7viewer");
        container.addEventListener(s7sdk.event.ResizeEvent.COMPONENT_RESIZE, containerResize, false);
        container.addEventListener(s7sdk.event.ResizeEvent.WINDOW_RESIZE, windowResize, false);
        zoomView = new s7sdk.image.ZoomView("s7viewer", params, "myZoomView");
        mediaSet = new s7sdk.set.MediaSet(null, params, "mediaSet");
        mediaSet.addEventListener(s7sdk.event.AssetEvent.NOTF_SET_PARSED, onSetParsed, false);
        swatches = new s7sdk.set.Swatches("s7viewer", params, "mySwatches");  
        swatches.addEventListener(s7sdk.event.AssetEvent.SWATCH_SELECTED_EVENT, swatchSelected, false);
        zoomView.addEventListener(s7sdk.event.AssetEvent.ASSET_CHANGED, largeImageSelected, false);

        swatches.addEventListener(s7sdk.event.SwatchEvent.SWATCH_PAGE_CHANGE, removeSwatch, false);

        resizeViewer(container.getWidth(), container.getHeight());                 
        if($('#s7viewerCont').hasClass('fp')) {
            zoomInButton  = new s7sdk.common.ZoomInButton("s7viewer", params, "zoomInBtn");
            zoomOutButton = new s7sdk.common.ZoomOutButton("s7viewer", params, "zoomOutBtn");
            zoomResetButton = new s7sdk.common.ZoomResetButton("s7viewer", params, "zoomResetBtn");

            zoomInButton.addEventListener("click", function() { zoomView.zoomIn(); });
            zoomOutButton.addEventListener("click", function() { zoomView.zoomOut(); });
            zoomResetButton.addEventListener("click", function() { zoomView.zoomReset(); });

            $('#s7viewerCont').append('<span id="imgCount"></span>');
            $('#s7viewerCont').append('<span id="zoomMsg"> '+zoomTxt+'</span>');
            }
    } 


    /* Event handler for the s7sdk.event.AssetEvent.NOTF_SET_PARSED event dispatched by MediaSet to assign the asset to the Swatches when parsing is complete. */
    function onSetParsed(e) {
        var mediasetDesc = e.s7event.asset; 
        swatches.setMediaSet(mediasetDesc);
        swatches.selectSwatch(0, true);
        if(e.s7event.asset.items.length ==1) {
            $('.s7swatches').hide().prev().css('left',0);
        }
        if($('#s7viewerCont').hasClass('fp')) {
            $('#imgCount').html(imgTxt +' '+ (e.s7event.frame+1)+'/'+e.s7event.asset.items.length);
        }
    }

    function largeImageSelected(event) {
        swatches.selectSwatch(event.s7event.frame, true);
        if($('#s7viewerCont').hasClass('fp')) {
            $('#imgCount').html(imgTxt +' '+  (event.s7event.frame+1)+'/'+event.s7event.asset.parent.items.length);
        }
    }

    /* Event handler for s7sdk.event.AssetEvent.SWATCH_SELECTED_EVENT events dispatched by Swatches to switch the image in the ZoomView when a different swatch is selected. */
    function swatchSelected(event) {    
        zoomView.setItem(event.s7event.asset);
        if($('#s7viewerCont').hasClass('fp')) {
            $('#imgCount').html(imgTxt +' '+  (event.s7event.frame+1)+'/'+event.s7event.asset.parent.items.length);
        }
    }

    /* Add event handler for the s7sdk.Event.SDK_READY event dispatched by the ParameterManager when all modifiers are processed and it is safe to initialize the viewer. */
    params.addEventListener(s7sdk.Event.SDK_READY, initViewer, false);
    
    /* Event handler for s7sdk.event.ResizeEvent.COMPONENT_RESIZE events dispatched by Container to resize various view components included in this viewer. */
    function containerResize(event) {
        resizeViewer(event.s7event.w, event.s7event.h);
    }

    /* Event handler on browser resize and orientation change from hand held devices */
    function windowResize(event) { 
        zoomView.resize(container.getWidth(), container.getHeight());
        // if(event.s7event.w > 992) {
        // 	swatches.resize(swatches.getWidth(), container.getHeight());
        // }
        // else if(event.s7event.w < 992) {
        // 	swatches.resize(container.getWidth(), swatches.getHeight());
        // }

        // if(event.s7event.w > 991) {
        // 	params.push("Swatches.tmblayout", "1,0");
        // 	params.push("Swatches.orientation", "1");
        // } 
        // else if(event.s7event.w < 992) {	
        // 	params.push("Swatches.tmblayout", "0,1");
        // 	params.push("Swatches.orientation", "0");
        // 	if(event.s7event.w < 768) {	
        // 		params.push("Swatches.align","center,top");
        // 	} else {
        // 		params.push("Swatches.align","center,bottom");
        // 	}
        // }
    }

    /* Event handler for s7sdk.event.ResizeEvent.COMPONENT_RESIZE events dispatched by Container to resize various view components included in this viewer. */
    function resizeViewer(width, height) {
        zoomView.resize(width, height);
        if($(window).width() > 992) {
            swatches.resize(swatches.getWidth(), height);
        }
        else if($(window).width() < 992) {
            swatches.resize(width, swatches.getHeight());
        }    
    }

    /* Initiate configuration initialization of ParameterManager. */
    params.init();
    //params.push("asset", "Mattel/16BUN08_Viewer");

    function removeSwatch (evt) {
        //params.push("asset", "Mattel/16BUN08_Viewer");
        //params.init();
        //swatches.dispose();
        //params.push("asset", "Mattel/16BUN08_Viewer");
        //params.init();
    }

    $('#btn').on('click', function(){
        //zoomView.dispose();
        //mediaSet.dispose();
        //swatches.dispose();
        //chk();
        //params.unload();
        //skuID = '16bun08';
        // $('#s7viewerCont').empty();
        // mediaSet = null;
        // container = null; 
        // zoomView = null;
        // swatches = null; 
        // //zoomInButton, zoomOutButton, zoomResetButton, skuID;
        //$('#s7viewerCont').empty();
        //params.push("asset", "Mattel/16BUN08_Viewer");
        //params.removeEventListener(s7sdk.Event.SDK_READY);
        //params.addEventListener(s7sdk.Event.SDK_READY, initViewer, false);
        //params.init();
        //container.dispose();
        //params.dispose();
        //$('#s7viewerCont').empty();
        skuID = '16BUN08';
        //s7sdk.Util.init();
        params.initViewer();
        //params.addEventListener(s7sdk.Event.SDK_READY, initViewer, false);
        params.init();
        //container.dispose();
        //params.push("asset", "Mattel/16BUN08_Viewer");
    });

}());  
