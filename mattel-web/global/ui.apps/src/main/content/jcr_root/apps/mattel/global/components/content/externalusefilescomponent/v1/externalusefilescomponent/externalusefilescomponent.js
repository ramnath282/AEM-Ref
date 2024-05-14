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
;
use(function () {
    "use strict";
    var properties = resource.adaptTo(Packages.org.apache.sling.api.resource.ValueMap);
    if (properties == null) {
        return {
            filesToAdd: null,
            jsFilesToAdd: null
        };
    }

   	var filesToAdd = properties['file'] || [];

    var jsFilesToAdd = properties['jsfile'] || [];


    return {
        filesToAdd: filesToAdd,
        jsFilesToAdd: jsFilesToAdd

    }
});