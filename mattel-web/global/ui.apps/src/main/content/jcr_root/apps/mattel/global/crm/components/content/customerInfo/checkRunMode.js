"use strict";
use(function () {
 var myRunmode = this.runmode;
 var isValidRunmode = false; 
 var slingSettingsService = sling.getService(Packages.org.apache.sling.settings.SlingSettingsService);
 var  runModes = slingSettingsService.getRunModes().toString();
 if (runModes && myRunmode) {
  isValidRunmode = (runModes.indexOf(myRunmode) >= 0);
 }
    return {
        hasRunmode : isValidRunmode
    };  
});