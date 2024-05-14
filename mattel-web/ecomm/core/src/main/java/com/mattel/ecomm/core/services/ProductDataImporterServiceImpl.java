package com.mattel.ecomm.core.services;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.exec.InboxItem.Priority;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.mattel.ecomm.core.importer.workflow.ProductFeedConstants;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;
import com.mattel.ecomm.core.interfaces.ProductDataImporterService;
import com.mattel.ecomm.coreservices.core.enums.DataImporterErrorCode;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ProductDataImporterResponse;

@Component(service = ProductDataImporterService.class)
@Designate(ocd = ProductDataImporterServiceImpl.Config.class)
public class ProductDataImporterServiceImpl implements ProductDataImporterService {
    private static final String UNABLE_TO_SAVE_PRODUCT_ERROR = "Unable to save product import feed file in repository";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDataImporterServiceImpl.class);
    @Reference
    private GetResourceResolver getResourceResolver;
    @Reference
    private ProductFeedInboxNotificationService productFeedInboxNotificationService;
    private DateFormat df;
    private String clientIdToDamPath;
    private String feedFileNamePrefix;
    private String deleteFeedFileNamePrefix;
    private String feedFileExtension;
    private String feedFileMimeType;
    private String productFeedEndPointUrl;
    private String deleteProductFeedEndPointURL;

    @Override
    public ProductDataImporterResponse readInputData(InputStream requestBody, Map<String, Object> requestAttributes)
            throws ServiceException {
        final ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();

        try {
            String feedType = requestAttributes.get("feedType").toString();
            final AssetManager assetManager;
            if (Objects.isNull(requestBody)) {
                productFeedInboxNotificationService.createNotificationTask("Invalid product import feed", "", null,
                        DataImporterErrorCode.REQUEST_BODY_NULL.getErrorMessage(), "", Priority.MEDIUM,
                        resourceResolver);
                throw new ServiceException(DataImporterErrorCode.REQUEST_BODY_NULL.toString(),
                        DataImporterErrorCode.REQUEST_BODY_NULL.getErrorMessage());
            }
            assetManager = resourceResolver.adaptTo(AssetManager.class);
            if (Objects.isNull(assetManager)) {
                productFeedInboxNotificationService.createNotificationTask(
                        ProductDataImporterServiceImpl.UNABLE_TO_SAVE_PRODUCT_ERROR, "", null,
                        DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.getErrorMessage(), "", Priority.MEDIUM,
                        resourceResolver);
                throw new ServiceException(DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.toString(),
                        DataImporterErrorCode.ASSERT_MANAGER_UNAVAILABLE.getErrorMessage());
            }

            return writeImportFeedToRepository(requestBody, assetManager, resourceResolver,feedType);
        } finally {
            resourceResolver.close();
        }
    }

    /**
     * @param requestBody
     * @param assetManager
     * @param resourceResolver
     * @param feedType 
     * @throws ServiceException
     */
    private ProductDataImporterResponse writeImportFeedToRepository(InputStream requestBody,
            final AssetManager assetManager, ResourceResolver resourceResolver, String feedType) throws ServiceException {
        final String[] clientData = clientIdToDamPath.split("\\:");

        if (Objects.nonNull(clientData) && clientData.length == 2 && !StringUtils.isEmpty(clientData[1])) {
            final ProductDataImporterResponse productDataImporterResponse = new ProductDataImporterResponse();
            final String damPath = clientData[1];
            String fileName=StringUtils.EMPTY;
            if("full".equals(feedType)){
                fileName = new StringBuilder(feedFileNamePrefix).append("_").append(df.format(new Date()))
                        .append(".").append(feedFileExtension).toString();
            } else if("delete".equals(feedType)){
                fileName = new StringBuilder(deleteFeedFileNamePrefix).append("_").append(df.format(new Date()))
                        .append(".").append(feedFileExtension).toString();
            }
            final String damUploadPath = new StringBuilder(damPath).append("/").append(fileName).toString(); // File.separator

            ProductDataImporterServiceImpl.LOGGER.debug("Saving asset in dam : {}", damUploadPath);

            try {
                final Asset fileAsset = assetManager.createAsset(damUploadPath, requestBody, feedFileMimeType, true);

                if (Objects.nonNull(fileAsset)) {
                    productDataImporterResponse.setFilePath(damUploadPath);
                    productDataImporterResponse.setStatus(true);
                } else {
                    productFeedInboxNotificationService.createNotificationTask(
                            ProductDataImporterServiceImpl.UNABLE_TO_SAVE_PRODUCT_ERROR, "", null,
                            DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.getErrorMessage(), "",
                            Priority.MEDIUM, resourceResolver);
                    throw new ServiceException(DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.toString(),
                            DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.getErrorMessage());
                }
            } catch (final Exception e) {
                ProductDataImporterServiceImpl.LOGGER
                        .error(DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.getErrorMessage(), e);
                productFeedInboxNotificationService.createNotificationTask(
                        ProductDataImporterServiceImpl.UNABLE_TO_SAVE_PRODUCT_ERROR, "", null,
                        DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.getErrorMessage(), "",
                        Priority.MEDIUM, resourceResolver);
                throw new ServiceException(DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.toString(),
                        DataImporterErrorCode.ASSERT_MANAGER_CREATE_ASSET_FAILURE.getErrorMessage());
            }

            return productDataImporterResponse;
        } else {
            productFeedInboxNotificationService.createNotificationTask(
                    ProductDataImporterServiceImpl.UNABLE_TO_SAVE_PRODUCT_ERROR, "", null,
                    DataImporterErrorCode.INVALID_DAM_PATH.getErrorMessage(), "", Priority.MEDIUM, resourceResolver);
            throw new ServiceException(DataImporterErrorCode.INVALID_DAM_PATH.toString(),
                    DataImporterErrorCode.INVALID_DAM_PATH.getErrorMessage());
        }
    }
    
    /**
     * This method returns the configured product feed end point URL
     * @return endPointUrl URL object for the configured end point
     * @throws MalformedURLException 
     * */
    public URL getProductFeedEndPointURL() throws MalformedURLException{
    	return getFeedEndointURL(productFeedEndPointUrl);
    }
    
    /**
     * This method returns the configured delete product feed end point URL
     * @return endPointUrl URL object for the configured end point
     * @throws MalformedURLException 
     * */
    public URL getDeleteProductFeedEndPointURL() throws MalformedURLException{
        return getFeedEndointURL(deleteProductFeedEndPointURL);
    }
    
    /**
     * This method returns the configured feed end point URL Object
     * @param feedEndPointURL feed end point URL string
     * @return endPointUrl URL object for the configured end point
     * @throws MalformedURLException 
     * */
    public URL getFeedEndointURL(String feedEndPointURL) throws MalformedURLException{
        ProductDataImporterServiceImpl.LOGGER.info("Start of getFeedEndointURL");
        String sEndPointUrl = StringUtils.isNotBlank(feedEndPointURL) ? feedEndPointURL
                : StringUtils.EMPTY;
        if(StringUtils.isNotBlank(sEndPointUrl)){
            ProductDataImporterServiceImpl.LOGGER.info("End of getFeedEndointURL");
            return new URL(sEndPointUrl);
        }
        ProductDataImporterServiceImpl.LOGGER.info("End of getFeedEndointURL");
        return null;
    }

    @ObjectClassDefinition(name = "Product data feed importer service", description = "This service reads the data feed file and writes to given DAM path, ")
    public @interface Config {
        @AttributeDefinition(name = "Client ID to DAM path mapping", description = "For each Client the DAM path would be different"
                + "formnat=clientId:damPath")
        String clientIdToDamPath() default "ag:/content/dam/ag/productfeed";

        @AttributeDefinition(name = "File extension for feed file", description = "File extension for the feed file to be saved in DAM")
        String fileExtension() default "json";

        @AttributeDefinition(name = "File MIME type", description = "File MIME type for the feed file to be saved in DAM")
        String fileMimeType() default "application/json";

        @AttributeDefinition(name = "File name prefix for feed file", description = "File name prefix for the feed file to be saved in DAM")
        String filePrefix() default "productdata";
        
        @AttributeDefinition(name = "File name prefix for feed file", description = "File name prefix for the feed file to be saved in DAM")
        String deleteFilePrefix() default "delete_productdata";
        
        @AttributeDefinition(name = "End Point URL for Product Feed", description = "Please provide the endpoint url for delta/full product feed")
        String productFeedEndPointUrl();
        
        @AttributeDefinition(name = "End Point URL for Delete Product Feed", description = "Please provide the endpoint url for delete product feed")
        String deleteProductFeedEndPointURL();
    }

    @Activate
    public void activate(Config config) {
        clientIdToDamPath = config.clientIdToDamPath();
        feedFileNamePrefix = config.filePrefix();
        deleteFeedFileNamePrefix = config.deleteFilePrefix();
        feedFileExtension = config.fileExtension();
        feedFileMimeType = config.fileMimeType();
        productFeedEndPointUrl = config.productFeedEndPointUrl();
        deleteProductFeedEndPointURL = config.deleteProductFeedEndPointURL();
        df = new SimpleDateFormat(ProductFeedConstants.FILE_NAME_DATE_TIME_SUFFIX);
    }
}
