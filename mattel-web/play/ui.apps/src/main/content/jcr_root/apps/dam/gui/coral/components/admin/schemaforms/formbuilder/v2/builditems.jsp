<%--

  ADOBE CONFIDENTIAL
  __________________

   Copyright 2013 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.

--%>
<%@ page session="false"
         contentType="text/html"
         pageEncoding="utf-8"
         import="com.adobe.granite.ui.components.formbuilder.FormResourceManager,
                 com.day.cq.commons.LabeledResource,
                 com.adobe.granite.ui.components.Config,
                 com.day.cq.i18n.I18n,
                 java.util.HashMap,
                 org.apache.sling.api.resource.Resource,
                 org.apache.sling.api.resource.ValueMap,
                 java.util.ArrayList,
                 java.util.Iterator,
                 com.adobe.granite.confmgr.Conf,
                 com.day.cq.dam.commons.util.DamConfigurationConstants,
                 java.util.List,
                 javax.jcr.Node"
        %><%
%><%@include file="/libs/granite/ui/global.jsp" %><%
%><cq:defineObjects /><%
	Config cfg = new Config(resource);
    String sfx = slingRequest.getRequestPathInfo().getSuffix();
    sfx = sfx == null ? "" : sfx;

%>
<div class="editor-right">
    <coral-tabview>
  		<coral-tablist target="coral-demo-panel-1">
            <coral-tab id="tab-add" href="#" data-target="#field-add"><%= i18n.get("Build Form") %></coral-tab>
            <coral-tab id="tab-edit" href="#" data-target="#field-edit"><%= i18n.get("Settings") %></coral-tab>
            <% if(resource.getPath().contains("metadataschemaeditor")) { %>
            <coral-tab id="tab-edit-rules" href="#" data-target="#field-rules"><%= i18n.get("Rules") %></coral-tab>
            <% } %>
        </coral-tablist>
		<coral-panelstack>
	        <coral-panel id="field-add">
				<ul id="formbuilder-field-templates">
	            <%
	                FormResourceManager formResourceManager = sling.getService(FormResourceManager.class);
	                Resource fieldTemplateResource = formResourceManager.getFormFieldResource(resource);
	            %>
	                <li class="field" data-fieldtype="section">
	                    <div class="formbuilder-template-title"><coral-icon icon="viewSingle" size="M"></coral-icon><span><%= i18n.get("Section Header") %></span>
	                    </div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/sectionfield" />
                    	</script>
	                </li>

	                <li class="field" data-fieldtype="text">
	                    <div class="formbuilder-template-title"><coral-icon icon="text" size="M"></coral-icon><span><%= i18n.get("Single Line Text") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/textfield" />
                    </script>
	                </li>

	                <li class="field" data-fieldtype="text">
	                    <div class="formbuilder-template-title"><coral-icon icon="text" size="M"></coral-icon><span><%= i18n.get("Multi Value Text") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/mvtextfield" />
                    </script>
	                </li>


	                <li class="field" data-fieldtype="number">
	                    <div class="formbuilder-template-title"><coral-icon icon="dashboard" size="M"></coral-icon><span><%= i18n.get("Number","form builder option") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/numberfield" />
                    </script>
	                </li>

	                <%-- <% Resource checkboxResource = formResourceManager.getCheckboxFieldResource(resource); %>
	                <li class="field" data-fieldtype="checkbox">
                        <div class="formbuilder-template-title"><coral-icon icon="select" size="M"></coral-icon><span><%= i18n.get("Checkbox") %></span></div>
                        <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= checkboxResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/checkboxfield" />
                        </script>
	                </li>
					--%>
	                <%
                        HashMap<String, Object> values = new HashMap<String, Object>();
                        values.put("sling.resolutionPath", "Field Label");
                        values.put("fieldLabel", "Default Value");
                        values.put("value", "");
                        Resource dateFieldResource = formResourceManager.getDefaultPropertyFieldResource(resource, values);
                    %>
	                <li class="field" data-fieldtype="datepicker">
	                    <div class="formbuilder-template-title"><coral-icon icon="calendar" size="M"></coral-icon><span><%= i18n.get("Date") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= dateFieldResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/datepickerfield" />
                    </script>
	                </li>

	                <li class="field" data-fieldtype="dropdown">
	                    <div class="formbuilder-template-title"><coral-icon icon="dropdown" size="M"></coral-icon><span><%= i18n.get("Dropdown") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/dropdownfield" />
                    </script>
	                    <script id="dropdown-option-template" type="text/x-handlebars-template">
                        <sling:include resource="<%= formResourceManager.getDropdownOptionResource(fieldTemplateResource) %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/dropdownfield/dropdownitem" />
                    </script>
	                </li>

	                <li class="field" data-fieldtype="text">
	                    <div class="formbuilder-template-title"><coral-icon icon="tag" size="M"></coral-icon><span><%= i18n.get("Standard Tags") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/tagsfield" />
                    </script>
	                </li>

	                <li class="field" data-fieldtype="text">
	                    <div class="formbuilder-template-title"><coral-icon icon="tag" size="M"></coral-icon><span><%= i18n.get("Smart Tags") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                            <sling:include resource="<%= fieldTemplateResource %>"
                                           resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/autotagsfield" />
	                    </script>
	                </li>

	                <% Resource hiddenFieldResource = formResourceManager.getHiddenFieldResource(resource); %>
	                <li class="field" data-fieldtype="text">
	                    <div class="formbuilder-template-title"><coral-icon icon="viewSingle" size="M"></coral-icon><span><%= i18n.get("Hidden Field") %></span>
	                    </div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= hiddenFieldResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/hiddenfield" />
                    </script>
	                </li>

	                <li class="field" data-fieldtype="reference">
	                    <div class="formbuilder-template-title"><coral-icon icon="link" size="M"></coral-icon><span><%= i18n.get("Asset Referenced By") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/referencefield" />
                    </script>
	                </li>

	                 <li class="field" data-fieldtype="reference">
	                    <div class="formbuilder-template-title"><coral-icon icon="link" size="M"></coral-icon><span><%= i18n.get("Asset Referencing") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/referencingfield" />
                    </script>
	                </li>
	                <li class="field" data-fieldtype="reference">
	                    <div class="formbuilder-template-title"><coral-icon icon="link" size="M"></coral-icon><span><%= i18n.get("Product References") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/pimfield" />
                    </script>
	                </li>
	                <li  class="field" data-fieldtype="reference">
	                	<div class="formbuilder-template-title"><coral-icon icon="star" size="M"></coral-icon><span><%= i18n.get("Asset Rating") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        	<sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/assetratingfield" />
                    	</script>
	                </li>

	                <% /** we only want the youtube field for video assets */ %>
	                <% if (sfx.indexOf("video") >= 0) { %>
	                <li class="field" data-fieldtype="text">
	                    <div class="formbuilder-template-title"><coral-icon icon="link" size="M"></coral-icon><span><%= i18n.get("YouTube URL List") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        <sling:include resource="<%= fieldTemplateResource %>"
                                       resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/youtubeurllist" />
                    </script>
	                </li>
	                <% } %>
                    <li id="contextual-metadata-schema-form-field" class="field" data-fieldtype="contextualmetadata">
                        <div class="formbuilder-template-title"><coral-icon icon="dropdown" size="M"></coral-icon><span><%= i18n.get("Contextual Metadata") %></span></div>
                        <script class="field-properties" type="text/x-handlebars-template">
                            <sling:include resource="<%= fieldTemplateResource %>"
                                           resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/contextualmetadatafield" />
                        </script>
                    </li>
					<li  class="field" data-fieldtype="reference">
	                	<div class="formbuilder-template-title"><coral-icon icon="star" size="M"></coral-icon><span><%= i18n.get("RTE Field") %></span></div>
	                    <script class="field-properties" type="text/x-handlebars-template">
                        	<sling:include resource="<%= fieldTemplateResource %>"
                            resourceType="/apps/dam/gui/coral/components/admin/schemaforms/formbuilder/formfields/v2/rtefield" />
                    	</script>
	                </li>
                </ul>
	        </coral-panel>
            <coral-panel id="formbuilder-tab-name" class="tab-form-settings">
	            <div id="tab-name">
	                <sling:include resource="<%= fieldTemplateResource %>"
	                               resourceType="dam/gui/coral/components/admin/schemaforms/formbuilder/tabname" />
	            </div>
	            <div class="placeholder">
                    <i><%= i18n.get("Select a metadata schema editor field to edit settings") %></i>
                </div>
	        </coral-panel>
            <% if(resource.getPath().contains("metadataschemaeditor")) { %>
            <coral-panel id="formbuilder-tab-name" class="tab-form-rules">
                <div id="tab-name">
                    <div class="placeholder">
		                <span><%= i18n.get("Select a metadata schema editor field to edit rules.") %></span>
                    </div>
	            </div>
	        </coral-panel>
            <% } %>
	    </coral-panelstack>
	</coral-tabview>
</div>
