package com.mattel.ecomm.coreservices.core.enums;

public enum DataImporterErrorCode {
    ASSERT_MANAGER_CREATE_ASSET_FAILURE("Unable to create asset"), //
    ASSERT_MANAGER_UNAVAILABLE("Asset Manager is currently unavailable"), //
    INVALID_DAM_PATH("Invalid dam path"), //
    NO_MULTIPARTDATA("Request is not of type mutlipart"),
    NO_MULTIPART_FILE_FOUND("No file uploaded in the request"), //
    REQUEST_BODY_EMPTY("Request Body is Empty"), //
    REQUEST_BODY_NULL("No request body found"), //
    NO_FILE_DATA("No data available in remote file"), //
    INTERNAL_ERROR("Internal Error");

    private String errorMessage;

    private DataImporterErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
