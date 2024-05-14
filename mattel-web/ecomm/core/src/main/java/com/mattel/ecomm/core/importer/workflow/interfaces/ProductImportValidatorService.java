package com.mattel.ecomm.core.importer.workflow.interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.mattel.ecomm.coreservices.core.interfaces.DataImportValidatorService;

public interface ProductImportValidatorService extends DataImportValidatorService<JsonNode> {
    List<String> fieldToValidate();
    boolean isMandatory(String fieldName);
}
