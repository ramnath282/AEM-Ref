package com.mattel.ecomm.core.filters;

import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.interfaces.GetTemplateTypeService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@Component(service = Filter.class, property = {
        "sling.filter.scope=" + EngineConstants.FILTER_SCOPE_REQUEST,
        "sling.filter.pattern="+ "(.*)(\\.[a-z_]{2,}\\.(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+\\.html)$"

})

public class ProductDetailPageFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailPageFilter.class);
    @Reference
    GetProductTypeService getProductType;
    @Reference
    GetTemplateTypeService getTemplateType;
    @Reference
    PropertyReaderService propertyReaderService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Called once when the Filter is initially registered.
        // Usually, do nothing

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        LOGGER.info("Product Detail Page Filter Starts");
        SlingHttpServletRequest slingHttpServletRequest = (SlingHttpServletRequest) request;
        LOGGER.debug("Request path is {}",slingHttpServletRequest.getRequestPathInfo().getResourcePath());
        String[] selectors = slingHttpServletRequest.getRequestPathInfo().getSelectors();
        if(selectors.length >= 2){
            LOGGER.info("Selectors are {}", selectors);
            String [] splitRequestpath = slingHttpServletRequest.getRequestPathInfo().getResourcePath().split(Constant.PERIOD_ESC_DELIMITER);
            String requestPathWithoutSelector = splitRequestpath[0];
            LOGGER.debug("Request path without Selector is {}", requestPathWithoutSelector);
            String[] requestPathWithoutSelectorSplit = requestPathWithoutSelector.split(Constant.SLASH);
            String partNumber = requestPathWithoutSelectorSplit[requestPathWithoutSelectorSplit.length-1];
            LOGGER.debug("Part Number is {}", partNumber);
            String skuId = selectors[1];
            LOGGER.debug("sku id is {}",skuId);
            String client = selectors[0];
            String errorPage = propertyReaderService.getSiteErrorPage(client);
            String shopHomePage = propertyReaderService.getShopHomePage(client);
            LOGGER.debug("errorPage is {} and Shop Home Page is {}",errorPage,shopHomePage);
            Map<String, String> productPropertiesMap = getProductType.getProductType(partNumber, client);
            if(productPropertiesMap.size() > 0){
                String contentPage = getTemplateType.getTemplatePath(productPropertiesMap.get(Constant.PRODUCT_TYPE),client);
                String activeFlag = productPropertiesMap.get(Constant.ACTIVE);
                if(StringUtils.isNotBlank(contentPage)){
                   if(!"false".equals(activeFlag)){
                       LOGGER.debug("Active Flag is not false, Redirecting to PDP page");
                       String internalRedirect = contentPage+Constant.PERIOD_PLACEHOLDER+client+Constant.PERIOD_PLACEHOLDER+productPropertiesMap.get(Constant.PRODUCT_PART_NUMBER)+Constant.PERIOD_PLACEHOLDER+partNumber+Constant.DOT_HTML;
                       LOGGER.debug("Final Internal Redirect is {}", internalRedirect);
                       request.getRequestDispatcher(internalRedirect).forward(request,response);
                   } else {
                       LOGGER.debug("Active Flag is set to false, Redirecting it to Shop Home Page");
                       HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                       httpServletResponse.sendRedirect(shopHomePage);
                   }
                } else {
                    redirectToErrorPage(errorPage,request,response);
                }
            } else {
                redirectToErrorPage(errorPage,request,response);
            }
        }
        long endTime = System.currentTimeMillis();
        LOGGER.debug("Complete Time taken is {}", endTime-startTime);
    }

    /**
     * This method is used to redirect/forward the request to error page in case failure scenarios
     * @param errorPage Error Page path
     * @param request Servlet Request
     * @param response Servlet Response
     * */
    private void redirectToErrorPage(String errorPage, ServletRequest request, ServletResponse response) throws ServletException, IOException {
        LOGGER.debug("Redirecting to error page");
        String internalRedirect = errorPage;
        request.getRequestDispatcher(internalRedirect).forward(request,response);
    }

    @Override
    public void destroy() {
        // Called once when the Filter is unloaded.
        // Usually, do nothing

    }
}
