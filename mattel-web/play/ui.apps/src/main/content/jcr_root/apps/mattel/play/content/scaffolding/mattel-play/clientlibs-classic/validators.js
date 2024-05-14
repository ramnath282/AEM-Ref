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
 * ExtJS-based validators (Classic UI)
 * ==========================================================================================
 */
jQuery(function ($) {

    /*
     * Prices
     */
    CQ.Ext.form.VTypes.price = function(value, field) {
        return value.length == 0 || !isNaN(parseFloat(value));
    };

    CQ.Ext.form.VTypes.priceText = CQ.I18n.getMessage("Must be a valid price.");
    CQ.Ext.form.VTypes.priceMask = /[\d\.]/;

    /*
     * We.Retail SKUs
     */
    CQ.Ext.form.VTypes.sku = function(value, field) {
        return value.length == 0 || value.length >= 5;
    };

    CQ.Ext.form.VTypes.skuText = CQ.I18n.getMessage("Mattel Play SKUs must be at least 5 characters.");

})(document, Granite.$, Granite);
