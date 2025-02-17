/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright 2013 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/


/* ==========================================================================================
 * jQuery-based validators (Touch-optimized UI)
 * ==========================================================================================
 */
(function(document, $, Granite) {
    "use strict";

    /*
     * Prices
     */
    $.validator.register({
        selector: "form [data-validation='weretail.price']",
        validate: function(el) {
            var v = el.val();

            if (v.length > 0 && (!v.match(/^[\d\.]+$/) || isNaN(parseFloat(v)))) {
                return Granite.I18n.get("Must be a valid price.");
            }
        }
    });

    /*
     * We.Retail SKUs
     */
    $.validator.register({
        selector: "form [data-validation='mattelplay.sku']",
        validate: function(el) {
            var v = el.val();

            if (v.length < 4) {
                return Granite.I18n.get("Mattel Play SKUs must be at least 5 characters.");
            }
        }
    });

    /*
     * We.Retail Currencies
     */
    $.validator.register({
        selector: "form [data-validation='mattelplay.currencyCode']",
        validate: function(el) {
            var v = el.val();

            if (!v.match(/^(USD)|(EUR)|(GBP)|(CHF)|(JPY)$/)) {
                return Granite.I18n.get("Mattel Play supports only USD, EUR, GBP, CHF and JPY")
            }
        }
    })

})(document, Granite.$, Granite);
