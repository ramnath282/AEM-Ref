
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ObjectFactoryTest {

  private ObjectFactory impl = null;

  private final static QName _UserFields_QNAME = new QName(
      "http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", "user_fields");
  private final static QName _ValidateAddressResponse_QNAME = new QName(
      "http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", "validateAddressResponse");
  private final static QName _ValidateAddressRequest_QNAME = new QName(
      "http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", "validateAddressRequest");

  @Mock
  ValidateAddressRequestType addressRequestType;

  @Mock
  ValidateAddressResponseType addressResponseType;

  @Mock
  UserFieldsType value;
  
  @Before
  public void setUp() throws Exception {
    impl = new ObjectFactory();
    impl.toString();
  }

  @Test
  public void testObjectFactory() {
    Assert.assertNotNull(impl.createUserFieldsType());
    Assert.assertNotNull(impl.createValidateAddressRequestType());
    Assert.assertNotNull(impl.createValidateAddressResponseType());
    Assert.assertNotNull(impl.createRowRequestType());
    Assert.assertNotNull(impl.createRowsResponseType());
    Assert.assertNotNull(impl.createOptionsType());
    Assert.assertNotNull(impl.createRowResponseType());
    Assert.assertNotNull(impl.createRowsRequestType());
    JAXBElement<UserFieldsType> userFieldsType = impl.createUserFields(value);
    Assert.assertNotNull(userFieldsType);
    Assert.assertEquals(_UserFields_QNAME,userFieldsType.getName());
    JAXBElement<ValidateAddressResponseType> validateAddressResponseType = impl.createValidateAddressResponse(addressResponseType);
    Assert.assertNotNull(validateAddressResponseType);
    Assert.assertEquals(_ValidateAddressResponse_QNAME,validateAddressResponseType.getName());
    JAXBElement<ValidateAddressRequestType> validateAddressRequestType = impl.createValidateAddressRequest(addressRequestType);
    Assert.assertNotNull(validateAddressRequestType);
    Assert.assertEquals(_ValidateAddressRequest_QNAME,validateAddressRequestType.getName());
  }
}
