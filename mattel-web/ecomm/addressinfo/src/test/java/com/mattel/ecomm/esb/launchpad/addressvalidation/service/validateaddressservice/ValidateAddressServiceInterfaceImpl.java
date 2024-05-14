package com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice;

import javax.jws.WebService;

import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.RowResponseType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.RowsResponseType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.ValidateAddressRequestType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.ValidateAddressResponseType;
import com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice.ValidateAddressServiceInterface;

/**
 * Mock web service.
 */
@WebService(endpointInterface = "com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice.ValidateAddressServiceInterface")
public class ValidateAddressServiceInterfaceImpl implements ValidateAddressServiceInterface {
    @Override
    public ValidateAddressResponseType validateAddress(ValidateAddressRequestType validateAddressInput) {
        final ValidateAddressResponseType addressResponseType = new ValidateAddressResponseType();
        final RowsResponseType rowsResponseType = new RowsResponseType();
        final RowResponseType rowResponseType = new RowResponseType();
        final String addressLine1 = validateAddressInput.getRows().getRow().get(0).getAddressLine1();

        if ("1653 Anderson Rd".equals(addressLine1)) {
            rowResponseType.setAddressLine1("1653 Anderson Rd Apt 2");
            rowResponseType.setCity("Mc Lean");
            rowResponseType.setCountry("United States Of America");
            rowResponseType.setPostalCode("22102-1653");
            rowResponseType.setStateProvince("VA");
        } else {
            rowResponseType.setStatus("F");
            rowResponseType.setStatusCode("UnableToValidate");
            rowResponseType.setStatusDescription("Address Not Found");
        }

        rowsResponseType.getRow().add(rowResponseType);
        addressResponseType.setRows(rowsResponseType);
        return addressResponseType;
    }
}
