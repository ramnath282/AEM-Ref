package com.mattel.ecomm.core.filters;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.engine.EngineConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Filter.class,
property = {
        Constants.SERVICE_DESCRIPTION + "=Demo to filter incoming requests",
        EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
        Constants.SERVICE_RANKING + ":Integer=1101",
        EngineConstants.SLING_FILTER_PATTERN +"=/content/.*/productfinder/.*.html"
})
public class ProductResourceFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResourceFilter.class);	

    @Reference
    PropertyReaderService propertyReaderService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Called once when the Filter is initially registered.
        // Usually, do nothing
	}	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException {
		LOGGER.info("Getting time in milliseconds doFilter Started {}",ZonedDateTime.now().toInstant().toEpochMilli());
		LOGGER.info("ProductResourceFilter doFilter Started");		
		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;		
		try
		{
			String requestUrl = slingRequest.getRequestURL().toString();
			LOGGER.debug("Product Detail Page requestUrl value {}",requestUrl); 
			String customSelector = getCustomSelector(requestUrl);
			LOGGER.debug("Product Detail Page Selector Value {}",customSelector);			
			if(StringUtils.isNotBlank(customSelector))
			{
				ResourceResolver resourceResolver = slingRequest.getResourceResolver();
				String resourceUrl = requestUrl.replace(Constant.SLASH + customSelector, "");
				LOGGER.debug("Product Detail Page resourceUrl {}",resourceUrl);
		        String[] selectors = slingRequest.getRequestPathInfo().getSelectors();
		        if(selectors.length >= 1) {
					String clientKey = Constant.FISHER_PRICE_ACRONYM + Constant.UNDERSCORE + selectors[0];
					LOGGER.debug("clinetkey {}",clientKey);
					PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
					Page page = null;
					if (Objects.nonNull(pageManager)) {
						String resourcePath = slingRequest.getRequestPathInfo().getResourcePath();
						resourcePath = resourcePath.replace(Constant.SLASH + customSelector, "");
						resourcePath = resourcePath.replace(Constant.PERIOD_PLACEHOLDER + selectors[0], "");
						resourcePath = resourcePath.replace(Constant.DOTHTML, "");
						LOGGER.debug("Product Detail Page ResourcePath value {}",resourcePath);
						page = pageManager.getContainingPage(resourcePath);
						if (Objects.nonNull(page)) {
							performRedirection(request, response, resourceResolver, page, requestUrl, customSelector, clientKey);
						}
					}
		        }
			}
			else {
				LOGGER.info("Getting time in milliseconds product with no selector Started {}",ZonedDateTime.now().toInstant().toEpochMilli());
				chain.doFilter(request, response);
			}
		} catch(Exception e)
		{
			LOGGER.error("exception caught in ProductResourceFilter {}",e);
		}
		finally {
			LOGGER.info("ProductResourceFilter Finally block is completed");
		}
	}
	
	/**
     * This method is used to perform redirection to either static PDP, dynamic PDP or error page
     * @param request ServletRequest
     * @param response ServletResponse
     * @param resourceResolver ResourceResolver
     * @param page Page
     * @param requestUrl String
     * @param customSelector String
     * @param clientKey String
     * 
     * */
	private void performRedirection(ServletRequest request, ServletResponse response, ResourceResolver resourceResolver, Page page, String requestUrl, String customSelector, String clientKey)
			throws IOException, ServletException {
		try {
			LOGGER.info("performRedirection method Started");		
			String productPath = page.getAbsoluteParent(5).getPath();
			String prodResPath = productPath+ Constant.SLASH + customSelector;
			org.apache.sling.api.resource.Resource selectorResource = resourceResolver.getResource(prodResPath);
			LOGGER.debug("product resource path {}",prodResPath);
			if (Objects.nonNull(selectorResource)) {
				LOGGER.info("Getting time in milliseconds selectorResource Started {}",ZonedDateTime.now().toInstant().toEpochMilli());	
				String staticPDPPath = prodResPath + Constant.DOTHTML;
				LOGGER.debug("redirecting to static PDP with URL {}",staticPDPPath);					
				request.getRequestDispatcher(staticPDPPath).forward(request, response);
			} else {
				LOGGER.info("Getting time in milliseconds non selectorResource Started {}",ZonedDateTime.now().toInstant().toEpochMilli());
				String psblCommerceProdPath = generatePossibleCommerceProdPath(customSelector, clientKey);
				LOGGER.debug("psblCommerceProdPath is {}",psblCommerceProdPath);
				org.apache.sling.api.resource.Resource psblCommerceProdResource = resourceResolver.getResource(psblCommerceProdPath);
				if (Objects.nonNull(psblCommerceProdResource)) {
					LOGGER.info("Getting time in milliseconds psblCommerceProdResource Started {}",ZonedDateTime.now().toInstant().toEpochMilli());	
					String dynamicPDPPath = productPath + Constant.SLASH + Constant.PRODUCT_FINDER + "." + customSelector + Constant.DOTHTML;
					LOGGER.debug("redirecting to static PDP with URL {}",dynamicPDPPath);					
					request.getRequestDispatcher(dynamicPDPPath).forward(request, response);
				} else {
					String errorPagePath = propertyReaderService.getSiteErrorPage(clientKey);
					LOGGER.error("product not present for the URL {}",requestUrl);
					LOGGER.debug("redirecting to error page {}",errorPagePath);
					redirectToErrorPage(errorPagePath, request, response);
				}						
			}
		} catch(Exception e)
		{
			LOGGER.error("exception caught in performRedirection{}",e);
		}
		finally {
			LOGGER.info("performRedirection Finally block is completed");
		}
	}
	
	/**
     * This method is used to fetch the "productName-SKUID" string from the URL ("content/../productfinder/productName-SKUID.en-us.html")
     * @param requestUrl String
     * @return custSelector "productName-SKUID" String
     * */
	private String getCustomSelector(String requestUrl) {
		LOGGER.info("getCustomSelector method started");
		String custSelector = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(requestUrl) && requestUrl.contains(Constant.PRODUCT_FINDER) && requestUrl.endsWith(Constant.DOTHTML) && !requestUrl.contains("jcr:content") ) {
			int lastIndex = requestUrl.lastIndexOf(Constant.SLASH);
			int htmlIndex = requestUrl.lastIndexOf(Constant.DOTHTML);
			String strAfterLastSlash = requestUrl.substring(lastIndex+1, htmlIndex);
			String[] productNameWithSelectors = strAfterLastSlash.split("\\.");
			custSelector = productNameWithSelectors[0];
		}
		LOGGER.debug("custSelector {}",custSelector);
		LOGGER.info("getCustomSelector method ended");	
		return custSelector;
	}
	
	/**
     * This method is used to generate the possible product commerce path  
     * @param selector "productName-SKUID"
     * @param clientKey clientKey
     * @return possibleCommerceProdPath Possible product commerce path 
     * */
	private String generatePossibleCommerceProdPath(String selector, String clientKey) {
		LOGGER.info("generatePossibleCommerceProdPath method started");
		String possibleCommerceProdPath = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(clientKey) && StringUtils.isNotBlank(selector)) {
			int lastHyphenIndex = selector.lastIndexOf('-');
			String skuID = selector.substring(lastHyphenIndex + 1);
			LOGGER.debug("skuID is {}",skuID);
			possibleCommerceProdPath = propertyReaderService.getProductPath(clientKey) + Constant.SLASH_PRODUCT_UNDERSCORE + skuID.toUpperCase() ;
		}
		LOGGER.debug("possibleCommerceProdPath is {}",possibleCommerceProdPath);
		LOGGER.info("generatePossibleCommerceProdPath method ended");
		return possibleCommerceProdPath;
	}

    /**
     * This method is used to redirect/forward the request to error page in case failure scenarios
     * @param errorPage Error Page path
     * @param request Servlet Request
     * @param response Servlet Response
     * */
    private void redirectToErrorPage(String errorPagePath, ServletRequest request, ServletResponse response) throws ServletException, IOException {
        LOGGER.info("Redirecting to error page");
        request.getRequestDispatcher(errorPagePath).forward(request,response);
    }


	@Override
	public void destroy() {
		 // Called once when the Filter is unloaded.
        // Usually, do nothing
	}

}
