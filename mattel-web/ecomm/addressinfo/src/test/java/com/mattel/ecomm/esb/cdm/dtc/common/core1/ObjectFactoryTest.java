
package com.mattel.ecomm.esb.cdm.dtc.common.core1;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectFactoryTest {

    private ObjectFactory impl = null;
    Map<String, Object> objectFactoryValues = new HashMap<String, Object>();
    private final static QName _City_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "city");
    private final static QName _LastName_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "lastName");
    private final static QName _Extension_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "extension");
    private final static QName _LongName_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "longName");
    private final static QName _Width_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "width");
    private final static QName _Depth_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "depth");
    private final static QName _Fax_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "fax");
    private final static QName _Type_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "type");
    private final static QName _LifecyclePhase_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "lifecyclePhase");
    private final static QName _Phone_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "phone");
    private final static QName _Priority_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "priority");
    private final static QName _Value_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "value");
    private final static QName _EffectiveEndDate_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "effectiveEndDate");
    private final static QName _Remarks_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "remarks");
    private final static QName _NameLocale_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "nameLocale");
    private final static QName _Address2_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "address2");
    private final static QName _Address3_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "address3");
    private final static QName _Address1_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "address1");
    private final static QName _Quantity_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "quantity");
    private final static QName _Currency_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "currency");
    private final static QName _Address_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "address");
    private final static QName _Height_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "height");
    private final static QName _State_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "state");
    private final static QName _AdddressLocale_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "adddressLocale");
    private final static QName _MailableIndicator_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "mailableIndicator");
    private final static QName _GifteeIndicator_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "gifteeIndicator");
    private final static QName _IntlCallingCode_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "intlCallingCode");
    private final static QName _Title_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "title");
    private final static QName _DeliveryPointValidationCodeText_QNAME = new QName(
            "http://www.mattel.com/esb/cdm/dtc/common/core1", "deliveryPointValidationCodeText");
    private final static QName _Length_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "length");
    private final static QName _EffectiveStartDate_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "effectiveStartDate");
    private final static QName _Contact_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "contact");
    private final static QName _EmailAddress_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "emailAddress");
    private final static QName _Language_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "language");
    private final static QName _PostalCode_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "postalCode");
    private final static QName _Country_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "country");
    private final static QName _Name_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "name");
    private final static QName _FirstName_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "firstName");
    private final static QName _PhoneNumber_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "phoneNumber");
    private final static QName _AreaCode_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1",
            "areaCode");
    private final static QName _Weight_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/common/core1", "weight");

    @Before
    public void setUp() throws Exception {
        impl = new ObjectFactory();

    }

    @Test
    public void testObjectFactory() throws DatatypeConfigurationException {
        final Channel channel = impl.createChannel();
        Assert.assertNotNull(channel);
        final Description description = impl.createDescription();
        Assert.assertNotNull(description);
        final ID id = impl.createID();
        Assert.assertNotNull(id);
        final Category category = impl.createCategory();
        Assert.assertNotNull(category);
        final Status status = impl.createStatus();
        Assert.assertNotNull(status);
        final NameValuePairType nameValuePair = impl.createNameValuePairType();
        Assert.assertNotNull(nameValuePair);

        final JAXBElement<String> cityElement = impl.createCity("ATLANDA");
        Assert.assertEquals(ObjectFactoryTest._City_QNAME, cityElement.getName());
        Assert.assertEquals("ATLANDA", cityElement.getValue());

        final JAXBElement<String> lastName = impl.createLastName("testLastName");
        Assert.assertEquals(ObjectFactoryTest._LastName_QNAME, lastName.getName());
        Assert.assertEquals("testLastName", lastName.getValue());

        final JAXBElement<String> extension = impl.createExtension("TXN");
        Assert.assertEquals(ObjectFactoryTest._Extension_QNAME, extension.getName());
        Assert.assertEquals("TXN", extension.getValue());

        final JAXBElement<String> longName = impl.createLongName("testLongName");
        Assert.assertEquals(ObjectFactoryTest._LongName_QNAME, longName.getName());
        Assert.assertEquals("testLongName", longName.getValue());

        final BigDecimal widthValue = new BigDecimal(200);
        final JAXBElement<BigDecimal> width = impl.createWidth(widthValue);
        Assert.assertEquals(ObjectFactoryTest._Width_QNAME, width.getName());
        Assert.assertEquals(widthValue, width.getValue());

        final BigDecimal depthValue = new BigDecimal(500);
        final JAXBElement<BigDecimal> depth = impl.createDepth(depthValue);
        Assert.assertEquals(ObjectFactoryTest._Depth_QNAME, depth.getName());
        Assert.assertEquals(depthValue, depth.getValue());

        final JAXBElement<String> fax = impl.createFax("500-200-888");
        Assert.assertEquals(ObjectFactoryTest._Fax_QNAME, fax.getName());
        Assert.assertEquals("500-200-888", fax.getValue());

        final JAXBElement<String> type = impl.createType("testType");
        Assert.assertEquals(ObjectFactoryTest._Type_QNAME, type.getName());
        Assert.assertEquals("testType", type.getValue());

        final JAXBElement<String> phase = impl.createLifecyclePhase("start");
        Assert.assertEquals(ObjectFactoryTest._LifecyclePhase_QNAME, phase.getName());
        Assert.assertEquals("start", phase.getValue());

        final JAXBElement<String> phone = impl.createPhone("9998856535");
        Assert.assertEquals(ObjectFactoryTest._Phone_QNAME, phone.getName());
        Assert.assertEquals("9998856535", phone.getValue());

        final Double priorityValue = new Double(2);
        final JAXBElement<Double> priority = impl.createPriority(priorityValue);
        Assert.assertEquals(ObjectFactoryTest._Priority_QNAME, priority.getName());
        Assert.assertEquals(priorityValue, priority.getValue());
        JAXBElement<Double> value = impl.createValue(2D);
        Assert.assertNotNull(value);
        Assert.assertEquals(ObjectFactoryTest._Value_QNAME, value.getName());
        
        final GregorianCalendar calendar = new GregorianCalendar();
        final XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        final JAXBElement<XMLGregorianCalendar> effectiveEndDate = impl.createEffectiveEndDate(date);
        Assert.assertNotNull(effectiveEndDate);
        Assert.assertEquals(ObjectFactoryTest._EffectiveEndDate_QNAME, effectiveEndDate.getName());

        final JAXBElement<String> remarks = impl.createRemarks("testRemarks");
        Assert.assertNotNull(remarks);
        Assert.assertEquals(ObjectFactoryTest._Remarks_QNAME, remarks.getName());

        final JAXBElement<String> locale = impl.createNameLocale("EN");
        Assert.assertNotNull(locale);
        Assert.assertEquals(ObjectFactoryTest._NameLocale_QNAME, locale.getName());

        final JAXBElement<String> address2 = impl.createAddress2("testAddress2");
        Assert.assertNotNull(address2);
        Assert.assertEquals(ObjectFactoryTest._Address2_QNAME, address2.getName());

        final JAXBElement<String> address3 = impl.createAddress3("testAddress3");
        Assert.assertNotNull(address3);
        Assert.assertEquals(ObjectFactoryTest._Address3_QNAME, address3.getName());

        final JAXBElement<String> address1 = impl.createAddress1("testAddress1");
        Assert.assertNotNull(address1);
        Assert.assertEquals(ObjectFactoryTest._Address1_QNAME, address1.getName());

        final JAXBElement<Double> quantity = impl.createQuantity(3D);
        Assert.assertNotNull(quantity);
        Assert.assertEquals(ObjectFactoryTest._Quantity_QNAME, quantity.getName());

        final JAXBElement<String> currency = impl.createCurrency("23");
        Assert.assertNotNull(currency);
        Assert.assertEquals(ObjectFactoryTest._Currency_QNAME, currency.getName());

        final JAXBElement<String> address = impl.createAddress("testAddress");
        Assert.assertNotNull(address);
        Assert.assertEquals(ObjectFactoryTest._Address_QNAME, address.getName());

        final JAXBElement<BigDecimal> height = impl.createHeight(new BigDecimal(2));
        Assert.assertNotNull(height);
        Assert.assertEquals(ObjectFactoryTest._Height_QNAME, height.getName());

        final JAXBElement<String> state = impl.createState("testState");
        Assert.assertNotNull(state);
        Assert.assertEquals(ObjectFactoryTest._State_QNAME, state.getName());

        final JAXBElement<String> addressLocale = impl.createAdddressLocale("testAddress");
        Assert.assertNotNull(addressLocale);
        Assert.assertEquals(ObjectFactoryTest._AdddressLocale_QNAME, addressLocale.getName());

        final JAXBElement<Boolean> mailIndicator = impl.createMailableIndicator(true);
        Assert.assertNotNull(mailIndicator);
        Assert.assertEquals(ObjectFactoryTest._MailableIndicator_QNAME, mailIndicator.getName());

        final JAXBElement<Boolean> gifteeIndicator = impl.createGifteeIndicator(true);
        Assert.assertNotNull(gifteeIndicator);
        Assert.assertEquals(ObjectFactoryTest._GifteeIndicator_QNAME, gifteeIndicator.getName());

        final JAXBElement<Integer> callCode = impl.createIntlCallingCode(1);
        Assert.assertNotNull(callCode);
        Assert.assertEquals(ObjectFactoryTest._IntlCallingCode_QNAME, callCode.getName());

        final JAXBElement<String> title = impl.createTitle("testTitle");
        Assert.assertNotNull(title);
        Assert.assertEquals(ObjectFactoryTest._Title_QNAME, title.getName());

        final JAXBElement<String> deliveryPoint = impl.createDeliveryPointValidationCodeText("deliveryPoint");
        Assert.assertNotNull(deliveryPoint);
        Assert.assertEquals(ObjectFactoryTest._DeliveryPointValidationCodeText_QNAME, deliveryPoint.getName());

        final JAXBElement<BigDecimal> length = impl.createLength(new BigDecimal(2));
        Assert.assertNotNull(length);
        Assert.assertEquals(ObjectFactoryTest._Length_QNAME, length.getName());

        final GregorianCalendar calendarStart = new GregorianCalendar();
        final XMLGregorianCalendar dateStart = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendarStart);
        final JAXBElement<XMLGregorianCalendar> effectiveStartDate = impl.createEffectiveStartDate(dateStart);
        Assert.assertNotNull(effectiveStartDate);
        Assert.assertEquals(ObjectFactoryTest._EffectiveStartDate_QNAME, effectiveStartDate.getName());

        final JAXBElement<String> contact = impl.createContact("testContact");
        Assert.assertNotNull(contact);
        Assert.assertEquals(ObjectFactoryTest._Contact_QNAME, contact.getName());

        final JAXBElement<String> email = impl.createEmailAddress("testmailaddress@test.com");
        Assert.assertNotNull(email);
        Assert.assertEquals(ObjectFactoryTest._EmailAddress_QNAME, email.getName());

        final JAXBElement<String> language = impl.createLanguage("English");
        Assert.assertNotNull(language);
        Assert.assertEquals(ObjectFactoryTest._Language_QNAME, language.getName());

        final JAXBElement<String> postalCode = impl.createPostalCode("123645");
        Assert.assertNotNull(postalCode);
        Assert.assertEquals(ObjectFactoryTest._PostalCode_QNAME, postalCode.getName());

        final JAXBElement<String> country = impl.createCountry("USA");
        Assert.assertNotNull(country);
        Assert.assertEquals(ObjectFactoryTest._Country_QNAME, country.getName());

        final JAXBElement<String> name = impl.createName("testName");
        Assert.assertNotNull(name);
        Assert.assertEquals(ObjectFactoryTest._Name_QNAME, name.getName());

        final JAXBElement<String> firstName = impl.createFirstName("tesFirsttName");
        Assert.assertNotNull(firstName);
        Assert.assertEquals(ObjectFactoryTest._FirstName_QNAME, firstName.getName());

        final JAXBElement<String> phoneNumber = impl.createPhoneNumber("978904856");
        Assert.assertNotNull(phoneNumber);
        Assert.assertEquals(ObjectFactoryTest._PhoneNumber_QNAME, phoneNumber.getName());

        final JAXBElement<Integer> areaCode = impl.createAreaCode(new Integer(923123));
        Assert.assertNotNull(areaCode);
        Assert.assertEquals(ObjectFactoryTest._AreaCode_QNAME, areaCode.getName());

        final JAXBElement<BigDecimal> weight = impl.createWeight(new BigDecimal(200));
        Assert.assertNotNull(weight);
        Assert.assertEquals(ObjectFactoryTest._Weight_QNAME, weight.getName());
    }

}
