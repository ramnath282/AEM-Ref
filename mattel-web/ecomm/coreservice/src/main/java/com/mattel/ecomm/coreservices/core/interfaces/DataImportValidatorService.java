package com.mattel.ecomm.coreservices.core.interfaces;

import java.util.Map;

import com.mattel.ecomm.coreservices.core.pojos.ValidationResult;

public interface DataImportValidatorService<O> {
    ValidationResult validate(String fieldName,O fieldValue, Map<String, Object> attributes);
}
