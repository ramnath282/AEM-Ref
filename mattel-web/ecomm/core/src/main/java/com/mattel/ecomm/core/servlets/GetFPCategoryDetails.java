package com.mattel.ecomm.core.servlets;

import com.mattel.ecomm.core.interfaces.CategoryPagesDetailsService;
import com.mattel.ecomm.coreservices.core.constants.Constant;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@SuppressWarnings("serial")
@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=Get Categroy and Sub Categories page JSON",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/getAllFPCategories" })
public class GetFPCategoryDetails extends SlingSafeMethodsServlet {
    private static final int PARENT_DEPTH = 5;
    @Reference
    transient CategoryPagesDetailsService categoryPagesDetailsService;
    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException {
      categoryPagesDetailsService.getCategories(request, response, GetFPCategoryDetails.PARENT_DEPTH,
          Constant.FP_PRODUCTGRID_RESOURCE,Constant.FP_PLP_TEMPLATE_URI);
     }
}
