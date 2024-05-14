package com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ValidateAddressResponse;
import com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice.AddressValidationImpl;
import com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice.ValidateAddressServiceInterface;

@RunWith(MockitoJUnitRunner.class)
public class AddressValidationImplTest {
    /** Set a free port */
    private final String endPointPort = "8190";
    private Endpoint endpoint;
    private ValidateAddressServiceInterface validateAddressServiceInterface;
    private AddressValidationImpl impl;

    @Before
    public void setUp() throws Exception {
        validateAddressServiceInterface = buildMockInterface();
        impl = new AddressValidationImpl(validateAddressServiceInterface);
    }

    private ValidateAddressServiceInterface buildMockInterface() {
        try {
            endpoint = Endpoint.publish("http://localhost:" + endPointPort + "/addressValidator",
                    new ValidateAddressServiceInterfaceImpl());
            final URL wsdlDocumentLocation = new URL("http://localhost:" + endPointPort + "/addressValidator?wsdl");
            final String namespaceURI = "http://validateaddressservice.service.addressvalidation.launchpad.esb.ecomm.mattel.com/";
            final String servicePart = "ValidateAddressServiceInterfaceImplService";
            final String portName = "ValidateAddressServiceInterfaceImplPort";
            final QName serviceQN = new QName(namespaceURI, servicePart);
            final QName portQN = new QName(namespaceURI, portName);
            // Creates a service instance
            final Service service = Service.create(wsdlDocumentLocation, serviceQN);

            return service.getPort(portQN, ValidateAddressServiceInterface.class);
        } catch (final Exception e) {
            if (null != endpoint) {
                endpoint.stop();
            }
        }

        return null;
    }

    @Test
    public void testVerifyForSuccess() throws ServiceException, IOException {
        try (final InputStream is1 = getClass().getResourceAsStream("request.json")) {
            final Map<String, Object> requestMap = new HashMap<>();
            ValidateAddressResponse validateAddressResponse;

            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            validateAddressResponse = impl.verify(requestMap);

            Assert.assertNotNull(validateAddressResponse);
            Assert.assertEquals("1653 Anderson Rd Apt 2", validateAddressResponse.getAddressLine1());
            Assert.assertEquals("Mc Lean", validateAddressResponse.getCity());
            Assert.assertEquals("United States Of America", validateAddressResponse.getCountry());
            Assert.assertEquals("22102-1653", validateAddressResponse.getZipCode());
            Assert.assertEquals("VA", validateAddressResponse.getState());
        } finally {
            if (null != endpoint) {
                endpoint.stop();
            }
        }
    }

    @Test
    public void testVerifyForAddressNotFound() throws ServiceException, IOException {
        try (final InputStream is1 = getClass().getResourceAsStream("request_address_invalid.json")) {
            final Map<String, Object> requestMap = new HashMap<>();
            ValidateAddressResponse validateAddressResponse;

            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            validateAddressResponse = impl.verify(requestMap);

            Assert.assertNotNull(validateAddressResponse.getErrors());
            Assert.assertEquals(1, validateAddressResponse.getErrors().size());
            Assert.assertEquals("_ERR_ADDRESS_NOT_FOUND", validateAddressResponse.getErrors().get(0).getErrorKey());
        } finally {
            if (null != endpoint) {
                endpoint.stop();
            }
        }
    }
}
