/*******************************************************************************
* ADOBE CONFIDENTIAL
* __________________
*
* Copyright 2017 Adobe Systems Incorporated
* All Rights Reserved.
*
* NOTICE:  All information contained herein is, and remains
* the property of Adobe Systems Incorporated and its suppliers,
* if any.  The intellectual and technical concepts contained
* herein are proprietary to Adobe Systems Incorporated and its
* suppliers and are protected by trade secret or copyright law.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from Adobe Systems Incorporated.
******************************************************************************/

use(function (uri, text) {
    "use strict";
    var index = this.uri.indexOf(this.text);
    var isCloudConf = "true";
    if(index > -1){
        isCloudConf = "false";
    }
    return {
        isCloudConf: isCloudConf,
        uri: this.uri,
        text : this.text
    }
});
