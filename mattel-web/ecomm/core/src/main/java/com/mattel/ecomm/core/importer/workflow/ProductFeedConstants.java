package com.mattel.ecomm.core.importer.workflow;

public class ProductFeedConstants {
    public static final String FAILED_NODE_NAME_PREFIX = "failedNode-";
    public static final String FILE_NAME_DATE_TIME_SUFFIX = "yyyyMMddhhmmss";
    public static final String METADATA_KEY_ERRORS = "errors";
    public static final String METADATA_KEY_NODE_PATHS_TO_REPLICATE = "resourcePaths";
    public static final String METADATA_KEY_STATUS = "status";
    public static final String PRODUCT_FEED_JSON_ROOT_NODE = "response";
    public static final String PRODUCT_PRIMARY_KEY = "pdpLink";
    public static final String PRODUCT_IDENTIFIER = "partNumber";
    public static final String PROPERTY_IDENTIFIER = "identifier";
    public static final String STATUS_FAILURE = "FAILURE";
    public static final String STATUS_SUCCESS = "SUCCESS";

    private ProductFeedConstants() {
    }
}
