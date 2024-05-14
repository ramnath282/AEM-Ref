package com.mattel.productvideos.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.pojos.Elements;
import com.mattel.productvideos.core.pojos.Item;
import com.mattel.productvideos.core.pojos.Metadatum;
import com.mattel.productvideos.core.pojos.PropertyPojo;
import com.mattel.productvideos.core.pojos.VideoContent;
import com.mattel.productvideos.core.pojos.VideoJsonMetadata;
import com.mattel.productvideos.core.utils.ProductVideosUtil;

@Model(adaptables = { SlingHttpServletRequest.class }, resourceType = {
    Constants.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = { "json" }, options = {
    @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "false"),
    @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "false") })
public class GetAssetAPIModelExporter {
  private static final Logger LOGGER = LoggerFactory.getLogger(GetAssetAPIModelExporter.class);

  @Self
  @Via("resource")
  private Resource resource;

  @OSGiService
  @Required
  private QueryBuilder queryBuilder;

  @SlingObject
  @Required
  private ResourceResolver resourceResolver;

  @ValueMapValue
  @Named("jcr:title")
  @Required
  private String title;

  @ValueMapValue
  @Optional
  private String pageTitle;

  @Inject
  String items;

  public String getTitle() {
    return StringUtils.defaultIfEmpty(this.pageTitle, this.title);
  }

  @PostConstruct
  private void init() {
    try {
      LOGGER.info("Start of exporter model Init method");

      Page page = this.resourceResolver.adaptTo(PageManager.class).getContainingPage(this.resource);
      Session session = this.resourceResolver.adaptTo(Session.class);
      Map<String, String> map = new HashMap<>();
      if (page.isValid()) {
        map.put("path", page.getPath());
        map.put("property", Constants.SLING_RESOURCE_TYPE);
        map.put("Property.value", Constants.SLING_RESOURCE_TYPE_VALUE);
      }
      Query query = queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));
      SearchResult result = query.getResult();
      Iterator<Node> nodeItr = result.getNodes();
      List<Item> jsonItems = new ArrayList<>();
      while (nodeItr.hasNext()) {
        Node node = nodeItr.next();
        if (node.hasProperty(Constants.CONTENT_FRAGMENT_PATH_PROPERTY)) {
          String path = node.getProperty(Constants.CONTENT_FRAGMENT_PATH_PROPERTY).getValue().getString();
          if (!StringUtils.isEmpty(path)) {
            Resource fragmentResource = this.resourceResolver.getResource(path);
            if (Objects.nonNull(fragmentResource)
                && Objects.nonNull(fragmentResource.adaptTo(ContentFragment.class))) {
              ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
              PropertyPojo desc = new PropertyPojo();
              PropertyPojo thumbnailImg = new PropertyPojo();
              PropertyPojo video = new PropertyPojo();
              PropertyPojo videoDuration = new PropertyPojo();
              PropertyPojo videoId = new PropertyPojo();
              VideoJsonMetadata videoJsonMetadata = new VideoJsonMetadata();

              Node root = session.getRootNode();

              processThumbnailImage(fragment, thumbnailImg, root, videoJsonMetadata);
              processVideo(fragment, video, root, videoJsonMetadata,videoDuration,videoId);
              processDescription(fragment, desc);

              VideoContent videoC = setVideoJsonContent(desc, thumbnailImg, video, videoJsonMetadata,videoDuration,videoId);

              Item item = new Item();
              item.setVideoContent(videoC);
              jsonItems.add(item);
            }
          }
        }
      }
      ObjectMapper objMapper = new ObjectMapper();
      items = objMapper.writeValueAsString(jsonItems);
      LOGGER.info("ITEMS : {}", items);
      LOGGER.info("End of exporter model Init method");
    } catch (RepositoryException | JsonProcessingException e) {
      LOGGER.error("Exception ocuured in init method: ", e);
    }
  }

  /**
   * This method will set the image, video, description and other metadata in
   * VideoContent object
   * 
   * @param desc
   * @param thumbnailImg
   * @param video
   * @param videoJsonMetadata
   * @return
   */
  private VideoContent setVideoJsonContent(PropertyPojo desc, PropertyPojo thumbnailImg, PropertyPojo video,
      VideoJsonMetadata videoJsonMetadata,PropertyPojo videoDuration, PropertyPojo videoId) {
    LOGGER.info("Start of setVideoJsonContent method");
    VideoContent videoC = new VideoContent();
    Elements elem = new Elements();
    elem.setDescription(desc);
    elem.setThumbNailImage(thumbnailImg);
    elem.setVideo(video);
    elem.setVideoDuration(videoDuration);
    elem.setVideoId(videoId);
    List<Metadatum> metadata = new ArrayList<>();
    if (StringUtils.isNotBlank(videoJsonMetadata.getContentName())) {
      Metadatum dinoName = new Metadatum();
      dinoName.setName(Constants.DINONAME);
      dinoName.setValue(videoJsonMetadata.getContentName());
      metadata.add(dinoName);
    }
    if (StringUtils.isNotBlank(videoJsonMetadata.getBrandValue())) {
      Metadatum brand = new Metadatum();
      brand.setName(Constants.BRAND_FIELD);
      brand.setValue(videoJsonMetadata.getBrandValue());
      metadata.add(brand);
    }
    if (StringUtils.isNotBlank(videoJsonMetadata.getCollectionName())) {
      Metadatum collName = new Metadatum();
      collName.setName(Constants.COLLECTION_NAME_FIELD);
      collName.setValue(videoJsonMetadata.getCollectionName());
      metadata.add(collName);
    }
    if (!metadata.isEmpty())
      videoC.setMetadata(metadata);
    videoC.setElements(elem);

    LOGGER.info("End of setVideoJsonContent method");
    return videoC;
  }

  /**
   * This method reads the description value set in content fragment and set
   * the value in desc object
   * 
   * @param fragment
   * @param desc
   */
  private void processDescription(ContentFragment fragment, PropertyPojo desc) {
    LOGGER.info("Start of processDescription method");
    ContentElement descElement = fragment.getElement(Constants.DESCRIPTION);
    if (Objects.nonNull(descElement)) {
      desc.setTitle(descElement.getName());
      desc.setValue(descElement.getContent());
      desc.setType(descElement.getContentType());
    }
    LOGGER.info("End of processDescription method");
  }

  /**
   * This method will read the video path value from content fragment and call
   * respective methods to read scene7 properties and other metadata such as
   * Brand, Content and Collection
   * 
   * @param fragment
   * @param video
   * @param root
   * @param videoJsonMetadata
   * @param videoId 
   * @param videoDuration 
   * @throws RepositoryException
   */
  private void processVideo(ContentFragment fragment, PropertyPojo video, Node root, VideoJsonMetadata videoJsonMetadata, PropertyPojo videoDuration, PropertyPojo videoId)
      throws RepositoryException {
    LOGGER.info("Start of processVideo method");
    ContentElement videoElement = fragment.getElement(Constants.VIDEO);
    String videoAssetUrl = processElementDetail(videoElement);
    if (StringUtils.isNotBlank(videoAssetUrl) && Objects.nonNull(videoElement)) {
      videoAssetUrl = videoAssetUrl.substring(1) + Constants.JCR_CONTENT_METADATA_PATH;
      if (root.hasNode(videoAssetUrl) && Objects.nonNull(root.getNode(videoAssetUrl))) {
        Node videoAsset = root.getNode(videoAssetUrl);
        LOGGER.debug("Video Asset Node path is : {}", videoAsset.getPath());
        setContentBrandAndCollection(videoJsonMetadata, videoAsset);
        videoJsonMetadata.setScene7Path(ProductVideosUtil.getAssetScene7URL(videoAsset));
        setVideoDurationAndId(videoAsset,videoDuration,videoId);

        video.setTitle(videoElement.getName());
        if (StringUtils.isBlank(videoJsonMetadata.getScene7Path())) {
          video.setValue(videoElement.getContent());
        } else {
          video.setValue(videoJsonMetadata.getScene7Path());
        }
        video.setType(videoElement.getContentType());
      }
    }
    LOGGER.info("End of processVideo method");
  }

  /**
   * This method reads the video duration and ID from
   * Asset node and set the JSOn object
   * 
   * @param videoAsset
   * @param videoDuration
   * @param videoId
   */
  private void setVideoDurationAndId(Node videoAsset, PropertyPojo videoDuration, PropertyPojo videoId) {
    LOGGER.info("Start of setVideoDurationAndId method");
    String duration = ProductVideosUtil.getPropertyValue(videoAsset,Constants.VIDEO_DURATION);
    if(StringUtils.isNotBlank(duration)){
      videoDuration.setTitle("videoDuration");
      videoDuration.setValue(duration);
      videoDuration.setType("text/plain");
    }
    
    String uniqueID = ProductVideosUtil.getPropertyValue(videoAsset,Constants.CONTENT_ID);
    if(StringUtils.isNotBlank(uniqueID)){
      videoId.setTitle("videoId");
      videoId.setValue(uniqueID);
      videoId.setType("text/plain");
    }
    LOGGER.debug("Video Duration is: {} and Video ID is: {}",duration,uniqueID);
    LOGGER.info("End of setVideoDurationAndId method");
  }

/**
   * This method will read the thumbnail image from content fragment and call
   * method to read scene7 properties for that image. Sets the captured value
   * in thumbnailImg object
   * 
   * @param fragment
   * @param thumbnailImg
   * @param root
   * @param videoJsonMetadata
   * @throws RepositoryException
   */
  private void processThumbnailImage(ContentFragment fragment, PropertyPojo thumbnailImg, Node root,
      VideoJsonMetadata videoJsonMetadata) throws RepositoryException {
    LOGGER.info("Start of processThumbnailImage method");
    ContentElement thumbnailElement = fragment.getElement("thumbnailImage");
    String thumnailImage = processElementDetail(thumbnailElement);
    if (StringUtils.isNotBlank(thumnailImage) && Objects.nonNull(thumbnailElement)) {
      thumnailImage = thumnailImage.substring(1) + "/jcr:content/metadata";
      if (root.hasNode(thumnailImage) && Objects.nonNull(root.getNode(thumnailImage))) {
        Node thumnailImageNode = root.getNode(thumnailImage);
        LOGGER.debug("Thumnail Image Node path is : {}", thumnailImageNode.getPath());
        videoJsonMetadata.setScene7Path(ProductVideosUtil.getAssetScene7URL(thumnailImageNode));
        thumbnailImg.setTitle(thumbnailElement.getName());
        if (StringUtils.isBlank(videoJsonMetadata.getScene7Path())) {
          thumbnailImg.setValue(thumbnailElement.getContent());
        } else {
          thumbnailImg.setValue(videoJsonMetadata.getScene7Path());
        }
        thumbnailImg.setType(thumbnailElement.getContentType());
      }
    }
    LOGGER.info("End of processThumbnailImage method");
  }
  
  /**
   * This method sets the content, brand and collection values for the asset
   * by reading it from the node
   * 
   * @param contentName
   * @param brandValue
   * @param collectionName
   * @param videoJsonMetadata
   * @param dataPathNode
   * @return
   * @throws RepositoryException
   */
  private void setContentBrandAndCollection(VideoJsonMetadata videoJsonMetadata, Node dataPathNode)
      throws RepositoryException {
    LOGGER.info("Start of setContentBrandAndCollection method");
    if (dataPathNode.hasProperty("dc:format")) {
      Property formatProperty = dataPathNode.getProperty("dc:format");
      String format = formatProperty.getValue().toString();
      if (format.contains("video")) {
        if (dataPathNode.hasProperty(Constants.CONTENT_NAME)) {
          Property contentNameProperty = dataPathNode.getProperty(Constants.CONTENT_NAME);
          Value[] contentNameValue = contentNameProperty.getValues();
          StringBuilder strBuilder = iterateValues(contentNameValue);
          int index = strBuilder.toString().lastIndexOf(';');
          videoJsonMetadata.setContentName(strBuilder.toString().substring(0, index));
          LOGGER.debug("ContentName is : {}", videoJsonMetadata.getContentName());
        }
        if (dataPathNode.hasProperty(Constants.BRAND)) {
          Property brandProperty = dataPathNode.getProperty(Constants.BRAND);
          videoJsonMetadata.setBrandValue(brandProperty.getValue().toString());
          LOGGER.debug("Brand Value is : {}", videoJsonMetadata.getBrandValue());
        }
        if (dataPathNode.hasProperty(Constants.COLLECTION_NAME)) {
          Property collectionNameProperty = dataPathNode.getProperty(Constants.COLLECTION_NAME);
          Value[] collectionValue = collectionNameProperty.getValues();
          StringBuilder strBuilder = iterateValues(collectionValue);
          int indexC = strBuilder.toString().lastIndexOf(';');
          videoJsonMetadata.setCollectionName(strBuilder.toString().substring(0, indexC));
          LOGGER.debug("Collection Name is : {}", videoJsonMetadata.getCollectionName());
        }
      }
    }
    LOGGER.info("End of setContentBrandAndCollection method");
  }

  /**
   * This method iterates over the array of Values and appends the value to
   * StringBuilder
   * 
   * @param contentNameValue
   * @param strBuilder
   * @throws RepositoryException
   */
  private StringBuilder iterateValues(Value[] propValues) throws RepositoryException {
    LOGGER.info("Start of iterateValues method");
    StringBuilder strBuilder = new StringBuilder();
    for (Value v : propValues) {
      LOGGER.debug(v.getString());
      strBuilder.append(v.getString()).append(";");
    }
    LOGGER.info("End of iterateValues method");
    return strBuilder;
  }

  /**
   * processElementDetail method to parse element details from content
   * fragment
   * 
   * @param element
   * @return elementValue
   */
  private String processElementDetail(ContentElement element) {
    LOGGER.info("Start of processElementDetail method");
    String elementValue;
    elementValue = Objects.nonNull(element) ? element.getContent() : StringUtils.EMPTY;
    LOGGER.debug("elementValue:--> {}", elementValue);
    LOGGER.info("End of processElementDetail method");
    return elementValue;
  }
  
  @JsonRawValue
  public String getitems() {
    init();
    return this.items;
  }
}
