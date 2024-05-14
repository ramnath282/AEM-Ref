package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.global.core.enums.AssetFilterType;
import com.mattel.global.core.pojo.AssetFilterPojo;
import com.mattel.global.core.utils.GlobalUtils;

import com.mattel.global.master.core.constants.Constants;
import com.mattel.global.master.core.model.ResponsiveTabModel;


/**
 * @author CTS
 *
 */
@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AssetListingModel extends ResponsiveTabModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(AssetListingModel.class);

  @Inject
  private String linkURL;

  @Inject
  private String linkText;

  @Inject
  private String showMoreText;

  @Inject
  private String showLessText;

  @Self
  private Resource resource;

  @Inject
  private String image;
  
  @Inject
  private String enableFilter;
  
  @Inject
  private String filterSectionLabel;
  
  @Inject
  private String[] filters;
  
  @Inject
  private String[] radiofilters;
  
  @Inject
  private String enableClearAll;
  
  @Inject
  private String clearAllLabel;
  
  @Inject
  private boolean isAlphabeticallyGroupSort;
 
  @Inject
  private boolean isAlphabeticallyFacetSort;

  
  private String backgroundImagePath;
  
  private List<AssetFilterPojo> assetFilterList;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of init method of AssetListingModel");
    linkURL = GlobalUtils.checkLink(linkURL, resource);
    if (null != resource) {
      ResourceResolver resourceResolver = resource.getResourceResolver();
      backgroundImagePath = GlobalUtils.setBackgroundPath(resourceResolver,image);
      assetFilterList = new ArrayList<>();
      if(Objects.nonNull(filters)){
      	 //fetchAssetFilterList(filters,resourceResolver);
        assetFilterList.addAll(fetchAssetFilterList(filters, resourceResolver, AssetFilterType.CHECKBOX));
      }
      
      if(Objects.nonNull(radiofilters)){
        //fetchAssetFilterList(filters,resourceResolver);
       assetFilterList.addAll(fetchAssetFilterList(radiofilters, resourceResolver, AssetFilterType.RADIOBUTTON));
     }
      
      if (isAlphabeticallyGroupSort) {
        Collections.sort(assetFilterList, Comparator.comparing(AssetFilterPojo::getFilterTitle));
      }
    }
    LOGGER.info("End of init method of AssetListingModel");
  }
  
  private List<AssetFilterPojo> fetchAssetFilterList(String[] filters,
      ResourceResolver resourceResolver, AssetFilterType assetFilterType) {
    LOGGER.info("Start of fetchAssetFilterList method of AssetListingModel, filter : {}, {}", filters, assetFilterType);
    final TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
    
    if (Objects.nonNull(tagManager)) {
      return new ArrayList<>(Arrays.asList(filters).stream().map(filter -> {
        Tag tag = tagManager.resolve(filter);
        AssetFilterPojo assetFilterPojo = new AssetFilterPojo();
        
        Optional.ofNullable(tag).filter(t -> Objects.nonNull(t.listChildren())).ifPresent(t -> {
          List<AssetFilterPojo> children = new ArrayList<>();
          
          LOGGER.info("1st level Tag: {}", tag.getTitle());
          assetFilterPojo.setFilterValue(filter);
          assetFilterPojo.setFilterTitle(tag.getTitle());
          assetFilterPojo.setName(tag.getName());
          assetFilterPojo.setFilterType(assetFilterType.toString());
          assetFilterPojo.setFilterId(AssetFilterPojo.generateFilterId(filter));
          
          final Spliterator<Tag> spliterator = Spliterators.spliteratorUnknownSize(t.listChildren(), 0);
          
          children.addAll(StreamSupport.stream(spliterator, false).map(ct -> {
            AssetFilterPojo childAssetFilterPojo = new AssetFilterPojo();
            
            LOGGER.info("2nd level Tag: {}", ct.getTagID());
            childAssetFilterPojo.setFilterValue(ct.getTagID());
            childAssetFilterPojo.setName(ct.getName());
            childAssetFilterPojo.setFilterTitle(ct.getTitle());
            childAssetFilterPojo.setFilterType(assetFilterType.toString());
            childAssetFilterPojo.setFilterId(AssetFilterPojo.generateFilterId(ct.getTagID()));
            
            return childAssetFilterPojo;
          }).collect(Collectors.toList()));
          
          children = assetFilterType.modifyChildren(children, assetFilterPojo);
          
          if (isAlphabeticallyFacetSort) {
            Collections.sort(children ,Comparator.comparing(AssetFilterPojo::getFilterTitle));
          }
            
          LOGGER.info("2nd level Children: {}", children);
          assetFilterPojo.setChildren(children);
        });
        
       
        return assetFilterPojo;
      }).collect(Collectors.toList()));
      
    }    
    LOGGER.info("End of fetchAssetFilterList method of AssetListingModel");
    return new ArrayList<>();
  }

  /**
   * This method will return the list of AssetFilterPojos which will hold the
   * required values for filters
   * @param resourceResolver Resource Resolver
   * @param filters filters
   * @return filterList filterList
   */
  @SuppressWarnings("unused")
  private List<AssetFilterPojo> fetchAssetFilterList(String[] filters, ResourceResolver resourceResolver) {
	  LOGGER.info("Start of fetchAssetFilterList method of AssetListingModel");
	  TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
	  List<AssetFilterPojo> filterList = new ArrayList<>();
	  if(Objects.nonNull(tagManager)){
		  Arrays.stream(filters).forEach(filter -> {
		  Tag tag = tagManager.resolve(filter);
		  if(Objects.nonNull(tag)){
			  AssetFilterPojo assetFilterPojo = new AssetFilterPojo();
			  assetFilterPojo.setFilterValue(filter);
			  assetFilterPojo.setFilterTitle(tag.getTitle());
			  assetFilterPojo.setFilterId(AssetFilterPojo.generateFilterId(filter));
			  LOGGER.debug("assetFilterPojo is {}",assetFilterPojo);
			  filterList.add(assetFilterPojo);
		  }
		  });
	  }
	  LOGGER.debug("AssetFilterList count is {}",filterList.size());
	  LOGGER.info("Start of fetchAssetFilterList method of AssetListingModel");
	  return filterList;
  }

  /**
   * This method will return the custom tag id needed for filters
   * @param filterTag filter tag authored
   * @return tagId custom tag ID
   */

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

  public String getLinkURL() {
    return linkURL;
  }

  public String getShowMoreText() {
    return GlobalUtils.removeTags(showMoreText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
  }

  public String getShowLessText() {
    return GlobalUtils.removeTags(showLessText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
  }

  public String getLinkText() {
    return GlobalUtils.removeTags(linkText, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
  }

  public static Logger getLogger() {
	return LOGGER;
  }

  public String getEnableFilter() {
	return enableFilter;
  }
  
  public String getFilterSectionLabel() {
	return filterSectionLabel;
  }

  public String[] getFilters() {
	return filters;
  }

  public String getEnableClearAll() {
	return enableClearAll;
  }

  public String getClearAllLabel() {
	return clearAllLabel;
  }

  public List<AssetFilterPojo> getAssetFilterList() {
	return assetFilterList;
  }
  
}
