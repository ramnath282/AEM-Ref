package com.mattel.ecomm.registration.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.RewardsMembershipService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.RewardsMembershipRequest;
import com.mattel.ecomm.coreservices.core.pojos.RewardsMembershipResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component(service = RewardsMembershipService.class)
@Designate(ocd = RewardsMembershipServiceImpl.Config.class)
public class RewardsMembershipServiceImpl implements RewardsMembershipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsMembershipServiceImpl.class);
    private static final String REWARDS_SERVICE = "rewardsMembershipService";
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(RewardsMembershipResponse.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(RewardsMembershipRequest.class);

    @Reference
    PropertyReaderService propertyReaderService;
    private String findRewardsEndpoint;

    @Override
    public RewardsMembershipResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final RewardsMembershipRequest rewardsMembershipRequest = (RewardsMembershipRequest) baseRequest;
            final Map<String, String> requestParameters = new HashMap<>();
            final HttpResponse httpResponse;
            final int status;

            requestParameters.put("emailId", rewardsMembershipRequest.getEmailId());
            requestParameters.put("membership_id", rewardsMembershipRequest.getMembershipId());
            httpResponse = HttpRequestHandler.get(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(), null,
                    null, requestParameters, null);
            status = httpResponse.getStatusLine().getStatusCode();

            RewardsMembershipServiceImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final RewardsMembershipResponse rewardsMembershipResponse = RESP_READER.readValue(inputStream);

                    RewardsMembershipServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                            System.currentTimeMillis() - startTime);
                    return rewardsMembershipResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();

                RewardsMembershipServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (IOException | URISyntaxException e) {
            final long endTime = System.currentTimeMillis();

            RewardsMembershipServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                    endTime - startTime);
            RewardsMembershipServiceImpl.LOGGER.error("Encountered error:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        return null;
    }

    @Override
    public RewardsMembershipResponse find(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final RewardsMembershipRequest registrationRequest = REQ_READER
                    .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final Map<String, Object> dataMap = new HashMap<>();
            final String endPointUrl = findRewardsEndpoint.replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
            final long endTime;

            dataMap.put("domain", domain);
            dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            RewardsMembershipServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    RewardsMembershipServiceImpl.REWARDS_SERVICE, endTime - startTime);
            return execute(registrationRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            RewardsMembershipServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    RewardsMembershipServiceImpl.REWARDS_SERVICE, endTime - startTime);
            RewardsMembershipServiceImpl.LOGGER.error("Error encountered: ", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "WCS RewardsMembership Service Configurations", description = "RewardsMembership Service Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "RewardsMembership Service End Point", description = "Please Enter the Registration Service End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xuser/findRewardsAccount";
    }

    @Activate
    public void activate(final Config config) {
        findRewardsEndpoint = config.endPoint();
    }
}
