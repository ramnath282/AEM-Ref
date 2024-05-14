<%--
  ADOBE CONFIDENTIAL
  __________________

   Copyright 2012 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%
%><%@ page session="false" contentType="text/html; charset=utf-8"
           import="org.apache.commons.lang.StringUtils" %><%
%><%@ include file="/libs/foundation/global.jsp"%><%

String title = properties.get(NameConstants.PN_TITLE, String.class);
if (title == null || title.equals("")) {
    title = resourcePage.getPageTitle();
}
if (title == null || title.equals("")) {
    title = resourcePage.getTitle();
}
if (title == null || title.equals("")) {
    title = resourcePage.getName();
}

// escape title
title = xssAPI.filterHTML(title);

final String type  = properties.get("type", currentStyle.get("defaultType", "h1"));
final String link  = properties.get("href", String.class);

if (StringUtils.isNotEmpty(link)) { %><a href="<%= xssAPI.getValidHref(link) %>"><% } %>
<cq:text value="<%= StringUtils.isNotEmpty(title) ? title : resource.getName() %>" tagName="<%= type %>" escapeXml="false"/>
<% if (StringUtils.isNotEmpty(link)) { %></a><% } %>
