
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mattel.esb.cdm.dtc.validateaddress.components1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UserFields_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", "user_fields");
    private final static QName _ValidateAddressResponse_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", "validateAddressResponse");
    private final static QName _ValidateAddressRequest_QNAME = new QName("http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", "validateAddressRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mattel.esb.cdm.dtc.validateaddress.components1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserFieldsType }
     * 
     */
    public UserFieldsType createUserFieldsType() {
        return new UserFieldsType();
    }

    /**
     * Create an instance of {@link ValidateAddressRequestType }
     * 
     */
    public ValidateAddressRequestType createValidateAddressRequestType() {
        return new ValidateAddressRequestType();
    }

    /**
     * Create an instance of {@link ValidateAddressResponseType }
     * 
     */
    public ValidateAddressResponseType createValidateAddressResponseType() {
        return new ValidateAddressResponseType();
    }

    /**
     * Create an instance of {@link RowRequestType }
     * 
     */
    public RowRequestType createRowRequestType() {
        return new RowRequestType();
    }

    /**
     * Create an instance of {@link RowsResponseType }
     * 
     */
    public RowsResponseType createRowsResponseType() {
        return new RowsResponseType();
    }

    /**
     * Create an instance of {@link OptionsType }
     * 
     */
    public OptionsType createOptionsType() {
        return new OptionsType();
    }

    /**
     * Create an instance of {@link RowResponseType }
     * 
     */
    public RowResponseType createRowResponseType() {
        return new RowResponseType();
    }

    /**
     * Create an instance of {@link RowsRequestType }
     * 
     */
    public RowsRequestType createRowsRequestType() {
        return new RowsRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserFieldsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", name = "user_fields")
    public JAXBElement<UserFieldsType> createUserFields(UserFieldsType value) {
        return new JAXBElement<UserFieldsType>(_UserFields_QNAME, UserFieldsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateAddressResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", name = "validateAddressResponse")
    public JAXBElement<ValidateAddressResponseType> createValidateAddressResponse(ValidateAddressResponseType value) {
        return new JAXBElement<ValidateAddressResponseType>(_ValidateAddressResponse_QNAME, ValidateAddressResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateAddressRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.mattel.com/esb/cdm/dtc/validateaddress/components1", name = "validateAddressRequest")
    public JAXBElement<ValidateAddressRequestType> createValidateAddressRequest(ValidateAddressRequestType value) {
        return new JAXBElement<ValidateAddressRequestType>(_ValidateAddressRequest_QNAME, ValidateAddressRequestType.class, null, value);
    }

}
