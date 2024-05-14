package com.mattel.ecomm.orderhistory.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.OrderHistoryDetail;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.OrderDetails;
import com.mattel.ecomm.coreservices.core.pojos.OrderHistoryDetailRequest;
import com.mattel.ecomm.coreservices.core.pojos.OrderHistoryDetailResponse;
import com.mattel.ecomm.coreservices.core.pojos.OrderItem;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = OrderHistoryDetail.class)
public class OrderHistoryDetailImpl implements OrderHistoryDetail {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryDetailImpl.class);
    private String orderHistoryDetailEndPoint;
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(OrderHistoryDetailResponse.class);
    @Reference
    private PropertyReaderService propertyReaderService;

    private static final Map<String, String> STATUS_MAP = new HashMap<>();

    static {
        OrderHistoryDetailImpl.STATUS_MAP.put("OSWS_ITEM_INPROGRESS_STATUS", "In Progress");
        OrderHistoryDetailImpl.STATUS_MAP.put("OSWS_ITEM_CANCELLED_STATUS", "Cancelled");
        OrderHistoryDetailImpl.STATUS_MAP.put("OSWS_ITEM_CS_STATUS", "Call Customer Service");
        OrderHistoryDetailImpl.STATUS_MAP.put("OSWS_ITEM_BACK-ORDER_STATUS", "Backordered");
        OrderHistoryDetailImpl.STATUS_MAP.put("OSWS_ITEM_SHIPPED_STATUS", "Shipped");
        OrderHistoryDetailImpl.STATUS_MAP.put("OSWS_ITEM_RETURN_STATUS", "Returned");
        OrderHistoryDetailImpl.STATUS_MAP.put("ITEM_BACK_ORDER_STATUS_1", "Backorder");
        OrderHistoryDetailImpl.STATUS_MAP.put("ITEM_BACK_ORDER_STATUS_2", "backordered");
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(Constant.END_POINT_URL_KEY).toString(), reqCookies, null, httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();
            OrderHistoryDetailImpl.LOGGER.debug("Status is {}", status);
            OrderHistoryDetailResponse orderHistoryDetailResponse = null;
            final boolean isError = isError(status);
            if (null != httpResponse.getEntity() && !isError) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                    final String domain = (String) dataMap.get("domain");
                    OrderHistoryDetailImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                    final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                            httpClientContext.getCookieStore().getCookies());
                    orderHistoryDetailResponse = OrderHistoryDetailImpl.RESP_READER.readValue(inputStream);
                    orderHistoryDetailResponse.setCookieList(cookieList);
                }
            } else {
                final long endTime = System.currentTimeMillis();
                OrderHistoryDetailImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Recent order History Detail execute",
                        endTime - startTime);
                generalExceptionHandling(status);
            }

            final long endTime = System.currentTimeMillis();
            OrderHistoryDetailImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Execute of Order History Detail",
                    endTime - startTime);
            return orderHistoryDetailResponse;
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            OrderHistoryDetailImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Execute of Order History Detail",
                    endTime - startTime);
            OrderHistoryDetailImpl.LOGGER.error("IO Exception {}", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    @Override
    public OrderHistoryDetailResponse getOrderHistoryDetail(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        OrderHistoryDetailImpl.LOGGER.info("getOrderHistory Starts");
        final OrderHistoryDetailRequest orderHistoryDetailRequest = null;
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String orderId = (String) requestMap.get(Constant.REQUEST_ORDERID);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final String endPointUrl = orderHistoryDetailEndPoint.replaceAll("STORE_ID", storeId).replaceAll("ORDER_ID",
                orderId);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
        final long endTime = System.currentTimeMillis();
        OrderHistoryDetailImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getOrderHistoryDetail", endTime - startTime);
        final OrderHistoryDetailResponse response = (OrderHistoryDetailResponse) execute(orderHistoryDetailRequest,
                dataMap);
        return formatResponce(response);
    }

    private OrderHistoryDetailResponse formatResponce(OrderHistoryDetailResponse response) {
        final long startTime = System.currentTimeMillis();
        final OrderDetails orderDetails = response.getOrderDetails();
        if (null != orderDetails) {
            final List<OrderItem> orderItems = orderDetails.getOrderItems();
            if (null != orderItems) {
                orderItems.forEach(item -> {
                    if (StringUtils.isNotBlank(item.getStatus())) {
                        item.setStatus(OrderHistoryDetailImpl.STATUS_MAP.get(item.getStatus()));
                    }
                });
                orderDetails.setOrderItems(orderItems);
            }
            response.setOrderDetails(orderDetails);
        }
        final long endTime = System.currentTimeMillis();
        OrderHistoryDetailImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "formatResponce", endTime - startTime);
        return response;
    }

    @Activate
    public void activate(final Config config) {
        orderHistoryDetailEndPoint = config.endPoint();
    }

    @ObjectClassDefinition(name = "WCS OrderHistoryDetail Configurations", description = "OrderHistory Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "OrderHistoryDetail End Point", description = "Please Enter the Order History Detail End point in the format"
                + "http://domain/restendpoint/${storeId}/@history?updateCookies=true")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/orderHistoryDetailsDataBean/getOrderHistoryDetail?updateCookies=true&orderId=ORDER_ID";
    }
}
