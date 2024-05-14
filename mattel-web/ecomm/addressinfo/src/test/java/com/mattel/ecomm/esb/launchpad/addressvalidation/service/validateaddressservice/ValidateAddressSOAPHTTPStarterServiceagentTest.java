package com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidateAddressSOAPHTTPStarterServiceagentTest {

  private ValidateAddressSOAPHTTPStarterServiceagent validateAddressSOAPHTTPStarterServiceagent;

  private final static QName VALIDATEADDRESSSOAPHTTPSTARTERSERVICEAGENT_QNAME = new QName(
      "http://www.mattel.com/esb/launchpad/addressvalidation/service/validateaddressservice",
      "ValidateAddressSOAPHTTPStarter.serviceagent");

  @Mock
  WebServiceFeature features;

  @Test
  public void ValidateAddressServiceagentTest() {
    validateAddressSOAPHTTPStarterServiceagent = new ValidateAddressSOAPHTTPStarterServiceagent();

    Assert.assertEquals(VALIDATEADDRESSSOAPHTTPSTARTERSERVICEAGENT_QNAME,
        validateAddressSOAPHTTPStarterServiceagent.getServiceName());

    URL url = ValidateAddressSOAPHTTPStarterServiceagent.class
        .getResource("ValidateAddressSOAPHTTPStarter.wsdl");

    validateAddressSOAPHTTPStarterServiceagent = new ValidateAddressSOAPHTTPStarterServiceagent(
        url);
    
    Assert.assertNotNull(validateAddressSOAPHTTPStarterServiceagent.getWSDLDocumentLocation());

    validateAddressSOAPHTTPStarterServiceagent = new ValidateAddressSOAPHTTPStarterServiceagent(url,
        ValidateAddressSOAPHTTPStarterServiceagentTest.VALIDATEADDRESSSOAPHTTPSTARTERSERVICEAGENT_QNAME);

    Assert.assertEquals(VALIDATEADDRESSSOAPHTTPSTARTERSERVICEAGENT_QNAME,
        validateAddressSOAPHTTPStarterServiceagent.getServiceName());

    ValidateAddressServiceInterface validateAddressServiceInterfaceEndpoint = validateAddressSOAPHTTPStarterServiceagent
        .getValidateAddressServiceInterfaceEndpoint();

    Assert.assertNotNull(validateAddressServiceInterfaceEndpoint);
  }
}
