package com.mattel.ecomm.searchandpromote.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.SearchAndPromote;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.SearchAndPromoteRequest;
import com.mattel.ecomm.coreservices.core.pojos.SearchAndPromoteResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
@Component(service = SearchAndPromote.class)
@Designate(ocd = SearchAndPromoteImpl.Config.class)
public class SearchAndPromoteImpl implements SearchAndPromote {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchAndPromoteImpl.class);
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String CATEGORY = "category";
    private static final String SEARCH_STRING = "search";
    private static final String SEARCH_PROMOTE_STATUS_SERVICE = "Execute Method of Search and Promote";
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(SearchAndPromoteResponse.class);

    private String searchAndPromoteEndPoint;
    @Override
    public SearchAndPromoteResponse getSearchResults(Map<String, Object> requestMap) throws ServiceException {
        LOGGER.info("Start of get search results method");
        final long startTime = System.currentTimeMillis();
        SearchAndPromoteRequest searchAndPromoteRequest = new SearchAndPromoteRequest();
        searchAndPromoteRequest.setCategory(requestMap.get(CATEGORY).toString());
        searchAndPromoteRequest.setSearchString(requestMap.get(SEARCH_STRING).toString());
        LOGGER.debug("Search and promote request is {}", searchAndPromoteRequest);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(END_POINT_URL_KEY,searchAndPromoteEndPoint);
        final long endTime = System.currentTimeMillis();
        LOGGER.info("End of get search results method");
        LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Search and promote Service",
                endTime - startTime);
        return (SearchAndPromoteResponse)execute(searchAndPromoteRequest,dataMap);
    }

    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            SearchAndPromoteRequest searchAndPromoteRequest = (SearchAndPromoteRequest) baseRequest;
        final HttpResponse httpResponse;

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put(CATEGORY,searchAndPromoteRequest.getCategory());
        requestParameters.put(SEARCH_STRING,searchAndPromoteRequest.getSearchString());
        httpResponse = HttpRequestHandler.get(httpClient,dataMap.get(END_POINT_URL_KEY).toString(),null,
                requestParameters, null);
        int status = httpResponse.getStatusLine().getStatusCode();
        LOGGER.debug("Status is {}", status);
        if (!isError(status)){
            try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                SearchAndPromoteResponse searchAndPromoteResponse = RESP_READER.readValue(inputStream);
            LOGGER.debug("Search and Promote Response is {}", searchAndPromoteResponse);
                final long endTime = System.currentTimeMillis();
                LOGGER.debug(Constant.EXECUTION_TIME_LOG, SEARCH_PROMOTE_STATUS_SERVICE, endTime-startTime);
            return searchAndPromoteResponse;
            }
        } else {
            final long endTime = System.currentTimeMillis();
            LOGGER.debug(Constant.EXECUTION_TIME_LOG, "", endTime-startTime);
            generalExceptionHandling(status);
        }

        } catch (IOException e) {
            LOGGER.error("IO Exception Occured", e);
            final long endTime = System.currentTimeMillis();
            LOGGER.debug(Constant.EXECUTION_TIME_LOG, SEARCH_PROMOTE_STATUS_SERVICE, endTime-startTime);
            throw new ServiceException("Exception Occured", e, Constant.IO_ERROR_KEY);

        }
        return null;
    }
    @ObjectClassDefinition(name = "Search And Promote Configuration")
    public @interface Config{
        @AttributeDefinition(name = "End Point of Search And Promote", description = "Please enter Search and Promote Url")
        String endPoint() default "https://sp1004f984.guided.ss-omtrdc.net";
    }
    @Activate
    public void activate(Config config){
        searchAndPromoteEndPoint = config.endPoint();
    }
}
