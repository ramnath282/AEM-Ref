package com.mattel.ecomm.esb.launchpad.addressvalidation.service.validateaddressservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddressValidation;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.ErrorResponse;
import com.mattel.ecomm.coreservices.core.pojos.ValidateAddressRequest;
import com.mattel.ecomm.coreservices.core.pojos.ValidateAddressResponse;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.RowRequestType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.RowResponseType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.RowsRequestType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.ValidateAddressRequestType;
import com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1.ValidateAddressResponseType;

@Component(service = AddressValidation.class)
@Designate(ocd = AddressValidationImpl.Config.class)
public class AddressValidationImpl implements AddressValidation {
    private static final String _ERR_ADDRESS_NOT_FOUND = "_ERR_ADDRESS_NOT_FOUND";
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressValidationImpl.class);
    private static final String VALIDATE_ADDRESS_SERVICE = "validateAddressService";
    private static final String ADD_VALIDATION_ERROR_KEY = "_ERR_ADDRESS_VALIDATION_SERVICE_DOWN";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        AddressValidationImpl.MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private ValidateAddressServiceInterface validateAddressServiceInterface;

    public AddressValidationImpl() {

    }

    public AddressValidationImpl(ValidateAddressServiceInterface validateAddressServiceInterface) {
        super();
        this.validateAddressServiceInterface = validateAddressServiceInterface;
    }

    @Override
    public ValidateAddressResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try {
            final ValidateAddressRequest validateAddressRequest = (ValidateAddressRequest) baseRequest;
            final ValidateAddressRequestType validateAddressInput = new ValidateAddressRequestType();
            final RowsRequestType rowsRequestType = new RowsRequestType();
            final List<RowRequestType> rows = rowsRequestType.getRow();
            final RowRequestType row = new RowRequestType();
            final ValidateAddressResponseType validateAddressResponseType;

            row.setAddressLine1(validateAddressRequest.getAddressLine1());
            row.setAddressLine2(validateAddressRequest.getAddressLine2());
            row.setAddressLine3(validateAddressRequest.getAddressLine3());
            row.setAddressLine4(validateAddressRequest.getAddressLine4());
            row.setCity(validateAddressRequest.getCity());
            row.setCountry(validateAddressRequest.getCountry());
            row.setFirmName(validateAddressRequest.getOrganizationUnitName());
            row.setPostalCode(validateAddressRequest.getZipCode());
            row.setStateProvince(validateAddressRequest.getState());
            rows.add(row);
            validateAddressInput.setRows(rowsRequestType);
            validateAddressResponseType = this.validateAddressServiceInterface.validateAddress(validateAddressInput);

            if (null != validateAddressResponseType && null != validateAddressResponseType.getRows()
                    && null != validateAddressResponseType.getRows().getRow()
                    && !validateAddressResponseType.getRows().getRow().isEmpty()) {
                final List<RowResponseType> rowResponseTypes = validateAddressResponseType.getRows().getRow();
                final RowResponseType rowResponseType = rowResponseTypes.get(0);
                final ValidateAddressResponse validateAddressResponse = new ValidateAddressResponse();

                if ("F".equals(rowResponseType.getStatus())
                        || "UnableToValidate".equals(rowResponseType.getStatusCode())
                        || "Address Not Found".equals(rowResponseType.getStatusDescription())) {
                    final List<ErrorResponse> errorResponses = new ArrayList<>();
                    final ErrorResponse errorResponse = new ErrorResponse();

                    errorResponse.setErrorCode(AddressValidationImpl._ERR_ADDRESS_NOT_FOUND);
                    errorResponse.setErrorKey(AddressValidationImpl._ERR_ADDRESS_NOT_FOUND);
                    errorResponse.setErrorMessage(rowResponseType.getStatusDescription());
                    errorResponses.add(errorResponse);
                    validateAddressResponse.setErrors(errorResponses);
                } else {
                    validateAddressResponse.setAddressLine1(rowResponseType.getAddressLine1());
                    validateAddressResponse.setAddressLine2(rowResponseType.getAddressLine2());
                    validateAddressResponse.setAddressLine3(rowResponseType.getAddressLine3());
                    validateAddressResponse.setAddressLine4(rowResponseType.getAddressLine4());
                    validateAddressResponse.setCity(rowResponseType.getCity());
                    validateAddressResponse.setCountry(rowResponseType.getCountry());
                    validateAddressResponse.setOrganizationUnitName(rowResponseType.getFirmName());
                    validateAddressResponse.setZipCode(rowResponseType.getPostalCode());
                    validateAddressResponse.setState(rowResponseType.getStateProvince());
                }

                return validateAddressResponse;
            } else {
                final long endTime = System.currentTimeMillis();

                AddressValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        endTime - startTime);
                throw new ServiceException(AddressValidationImpl._ERR_ADDRESS_NOT_FOUND, "Address Not Found");
            }
        } catch (final Exception e) {
            final long endTime = System.currentTimeMillis();

            AddressValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                    endTime - startTime);
            AddressValidationImpl.LOGGER.error("Encountered error:", e);
            throw new ServiceException(AddressValidationImpl.ADD_VALIDATION_ERROR_KEY, "Unknown exception Occured");
        }
    }

    @Override
    public ValidateAddressResponse verify(Map<String, Object> requestMap) throws ServiceException {
        if (isWebClientEnabled()) {
            final long startTime = System.currentTimeMillis();

            try {
                final ValidateAddressRequest validateAddressRequest = AddressValidationImpl.MAPPER
                        .readValue(requestMap.get(Constant.REQUEST_BODY).toString(), ValidateAddressRequest.class);

                return execute(validateAddressRequest, null);
            } catch (final IOException io) {
                final long endTime = System.currentTimeMillis();

                AddressValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                        AddressValidationImpl.VALIDATE_ADDRESS_SERVICE, endTime - startTime);
                AddressValidationImpl.LOGGER.error("Error encountered: ", io);
                throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
            }
        } else {
            throw new ServiceException(Constant.IO_ERROR_KEY, "Validate Address Service is currently down");
        }
    }

    @ObjectClassDefinition(name = "WCS AddressValidation SOAP Configurations", description = "AddressValidation SOAP Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "AddressValidation SOAP End Point", description = "Please Enter the AddressValidation SOAP End point in the format")
        String endPoint() default "https://esb-esg-stg-ext.mattel.com/service/validateaddress";
    }

    @Activate
    public void activate(final Config config) {
        try {
            final String soapEndPoint = config.endPoint();
            final ValidateAddressSOAPHTTPStarterServiceagent serviceagent = new ValidateAddressSOAPHTTPStarterServiceagent();
            final ValidateAddressServiceInterface validateAddressServiceImpl = serviceagent
                    .getValidateAddressServiceInterfaceEndpoint();
            final BindingProvider bindingProvider = (BindingProvider) validateAddressServiceImpl;

            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, soapEndPoint);
            this.validateAddressServiceInterface = validateAddressServiceImpl;
        } catch (final Exception e) {
            AddressValidationImpl.LOGGER.error("Unable to initalize address validation soap web client", e);
        }
    }

    private boolean isWebClientEnabled() {
        return null != this.validateAddressServiceInterface;
    }
}
