package com.mattel.productvideos.core.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.facets.Bucket;
import com.day.cq.search.facets.Facet;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.pojos.Result;
import com.mattel.productvideos.core.pojos.SearchMetadata;
import com.mattel.productvideos.core.pojos.SearchResultPojo;
import com.mattel.productvideos.core.services.SearchAPIService;
import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils;
import com.mattel.productvideos.core.utils.ProductVideosUtil;

@Component(service = SearchAPIService.class)
public class SearchAPIServiceImpl implements SearchAPIService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SearchAPIServiceImpl.class);

  @Reference
  private QueryBuilder queryBuilder;
  
  @Reference
  private ProductVideosPropertyUtils productVideosPropertyUtils;

  public String getData(Map<String, Object> requestDetailsMap, String parametersCount,
      SlingHttpServletRequest request) {
    LOGGER.info("Start of getData method");
    long count = 0L;
    Query query = null;
    SearchMetadata searchMetadata = new SearchMetadata();
    String jsonOutput = "";
    List<String> cqtags = new ArrayList<>();
    JSONArray featured = new JSONArray();
    JSONArray notFeatured = new JSONArray();
    Map<String, String> map = new HashMap<>();
    int propvalue = 1;
    List<Result> jsonList = new ArrayList<>();

    int limit = Integer.parseInt(requestDetailsMap.get("limit").toString());
    int offset = Integer.parseInt(requestDetailsMap.get("offset").toString());
    try {
      Session session = request.getResourceResolver().adaptTo(Session.class);
      this.queryBuilder = request.getResourceResolver().adaptTo(QueryBuilder.class);
      if (requestDetailsMap.get(Constants.PATH_FIELD).toString().isEmpty()) {
        requestDetailsMap.replace(Constants.PATH_FIELD, productVideosPropertyUtils.getSitePath());
      } else {
        requestDetailsMap.replace(Constants.PATH_FIELD, productVideosPropertyUtils.getSitePath()
            .concat(requestDetailsMap.get(Constants.PATH_FIELD).toString()));
      }
      prepareQueryMap(requestDetailsMap, limit, offset, map, propvalue);
      query = this.queryBuilder.createQuery(PredicateGroup.create(map), session);
      LOGGER.debug("final query is: {}", query);
      SearchResult result = query.getResult();
      if (Objects.isNull(result)) {
        LOGGER.debug("result is null");
      } else {
        LOGGER.debug("facet count : {}", result.getFacets().size());
        processResultFacets(requestDetailsMap, searchMetadata, cqtags, result);
        count = result.getTotalMatches();
        LOGGER.debug("hits count {}", count);
        searchMetadata.setTitle("Search_results");
        if (!requestDetailsMap.get(Constants.KEYWORD_PARAM).toString().isEmpty())
          searchMetadata.setQuery(requestDetailsMap.get(Constants.KEYWORD_PARAM).toString());
        searchMetadata.setPageSize(Integer.toString(limit));
        double pageNumber = (double)(offset + limit) / limit;
        if (pageNumber == 0.0D) {
          pageNumber = 1.0D;
        }
        int pagenum = (int) pageNumber;
        LOGGER.debug("Page number: {}", pagenum);
        searchMetadata.setPageNumber(Integer.toString(pagenum));
        processQueryResults(parametersCount, searchMetadata, featured, notFeatured, jsonList, session, result);
      }

      addFeaturedAndNonFeaturedVideos(parametersCount, searchMetadata, featured, notFeatured, jsonList);
      searchMetadata.setNumberOfHits(Long.toString(count));
      ObjectMapper objMapper = new ObjectMapper();
      jsonOutput = objMapper.writeValueAsString(searchMetadata);
      LOGGER.debug("json output :{}", jsonOutput);
    } catch (Exception e) {
      LOGGER.info("Exception while fetching DAM assets tags " + e.getMessage());
    }
    LOGGER.info("End of getData method");
    return jsonOutput;
  }

  /**
   * @param requestDetailsMap
   * @param searchMetadata
   * @param cqtags
   * @param result
   * @throws RepositoryException
   */
  private void processResultFacets(Map<String, Object> requestDetailsMap, SearchMetadata searchMetadata,
      List<String> cqtags, SearchResult result) throws RepositoryException {
    for (Map.Entry<String, Facet> facet : (Iterable<Map.Entry<String, Facet>>) result.getFacets().entrySet()) {
      String facetValue = getFacetTitle(facet.getKey(), facet.getValue());
      LOGGER.debug("Facet: {}", facetValue);
      List<Bucket> buckets = facet.getValue().getBuckets();
      for (Bucket bucket : buckets) {
        if (facetValue.equals("group.2_group.3_property")) {
          String bucketval = bucket.getValue();
          bucketval = bucketval.replace(Constants.CQ_TAG_INITIAL, "");
          cqtags.add(bucketval);
          String bucketcount = Long.toString(bucket.getCount());
          LOGGER.debug("Bucket: {} : {}", bucketval, bucketcount);
        }
      }
    }
    if (requestDetailsMap.get(Constants.FILTER_FIELD).toString().isEmpty()) {
      LOGGER.debug("cqtags value: {}", cqtags.size());
      searchMetadata.setFacets(cqtags);
    }
  }

  /**
   * @param parametersCount
   * @param searchMetadata
   * @param featured
   * @param notFeatured
   * @param jsonList
   * @param session
   * @param result
   * @throws RepositoryException
   */
  private void processQueryResults(String parametersCount, SearchMetadata searchMetadata, JSONArray featured,
      JSONArray notFeatured, List<Result> jsonList, Session session, SearchResult result)
      throws RepositoryException {
    LOGGER.info("Start of processQueryResults method");
    for (Hit hit : result.getHits()) {
      String assetPagePath = hit.getPath();
      assetPagePath = assetPagePath.substring(1);
      LOGGER.debug("path : {}", assetPagePath);
      Node root = session.getRootNode();
      String assetPageCFPath = assetPagePath + "/root/videocomponentapi";
      LOGGER.debug("assetcontentPath: {}", assetPageCFPath);
      try {
        if (root.hasNode(assetPagePath) && root.hasNode(assetPageCFPath)) {
          SearchResultPojo searchResultPojo = new SearchResultPojo();
          getFragmentData(assetPagePath, assetPageCFPath, root,searchResultPojo);
          if(StringUtils.isNotBlank(searchResultPojo.getThumbnailValue()) && 
                  StringUtils.isNotBlank(searchResultPojo.getVideoValue())){
              setResultPageProperties(parametersCount, searchMetadata, featured, notFeatured, jsonList,searchResultPojo);
          }
        }
      } catch (Exception e) {
        LOGGER.error("node not found " + e.getMessage());
      }
    }
    LOGGER.info("End of processQueryResults method");
  }

  /**
   * @param parametersCount
   * @param searchMetadata
   * @param featured
   * @param notFeatured
   * @param jsonList
   * @param searchResultPojo 
   * @throws JSONException
   */
  private void setResultPageProperties(String parametersCount, SearchMetadata searchMetadata, JSONArray featured,
      JSONArray notFeatured, List<Result> jsonList, SearchResultPojo searchResultPojo) throws JSONException {
    LOGGER.info("Start of setResultPageProperties method");
    if (parametersCount.equals("7")) {
      if (searchResultPojo.getCqtag().equals("Y")) {
        JSONObject featuredObj = new JSONObject();
        setVideoProperties(featured, featuredObj,searchResultPojo);
      } else {
        JSONObject notFeaturedObj = new JSONObject();
        setVideoProperties(notFeatured, notFeaturedObj,searchResultPojo);
      }
    } else {
      String scene7url = searchResultPojo.getScene7Url().isEmpty() ? searchResultPojo.getVideoValue() : searchResultPojo.getScene7Url();
      String scene7imageurl = searchResultPojo.getScene7ImageUrl().isEmpty() ? searchResultPojo.getThumbnailValue() : searchResultPojo.getScene7ImageUrl();
      Result jsonResult = new Result(searchResultPojo.getAssetName(), scene7url, scene7imageurl, searchResultPojo.getDescValue(), 
              searchResultPojo.getPublishedDate(), searchResultPojo.getCqtag(),searchResultPojo.getVideoDuration(),searchResultPojo.getVideoId());
      jsonList.add(jsonResult);
      searchMetadata.setResults(jsonList);
    }
    LOGGER.info("End of setResultPageProperties method");
  }

  /**
   * @param parametersCount
   * @param searchMetadata
   * @param featured
   * @param notFeatured
   * @param jsonList
   * @throws JSONException
   */
  private void addFeaturedAndNonFeaturedVideos(String parametersCount, SearchMetadata searchMetadata,
      JSONArray featured, JSONArray notFeatured, List<Result> jsonList) throws JSONException {
    if (parametersCount.equals("7")) {
      int k;
      for (k = 0; k < featured.length(); k++) {
        JSONObject jsonobjectFeatured = featured.getJSONObject(k);
        Result jResultFeatured = new Result(jsonobjectFeatured.getString(Constants.TITLE_FIELD),
            jsonobjectFeatured.getString(Constants.VIDEOURL_FIELD),
            jsonobjectFeatured.getString(Constants.IMAGEURL_FIELD),
            jsonobjectFeatured.getString(Constants.DESC_FIELD),
            jsonobjectFeatured.getString(Constants.PUBLISHDATE_FIELD),
            jsonobjectFeatured.getString(Constants.FEATURED_FIELD),
            jsonobjectFeatured.getString(Constants.VIDEO_DURATION),
            jsonobjectFeatured.getString(Constants.CONTENT_ID));
        jsonList.add(jResultFeatured);
      }
      for (k = 0; k < notFeatured.length(); k++) {
        JSONObject jsonobjectNFeatured = notFeatured.getJSONObject(k);
        Result jResultNFeatured = new Result(jsonobjectNFeatured.getString(Constants.TITLE_FIELD),
            jsonobjectNFeatured.getString(Constants.VIDEOURL_FIELD),
            jsonobjectNFeatured.getString(Constants.IMAGEURL_FIELD),
            jsonobjectNFeatured.getString(Constants.DESC_FIELD),
            jsonobjectNFeatured.getString(Constants.PUBLISHDATE_FIELD),
            jsonobjectNFeatured.getString(Constants.FEATURED_FIELD),
            jsonobjectNFeatured.getString(Constants.VIDEO_DURATION),
            jsonobjectNFeatured.getString(Constants.CONTENT_ID));
        jsonList.add(jResultNFeatured);
      }
      searchMetadata.setResults(jsonList);
    }
  }

  /**
   * This method will set the video properties in the JSON Object and in the
   * end add that object is the JSON Array
   * 
   * @param featured
   * @param i
   * @param featuredObj
 * @param searchResultPojo 
   * @throws JSONException
   */
  private void setVideoProperties(JSONArray featured, JSONObject featuredObj, SearchResultPojo searchResultPojo) throws JSONException {
    LOGGER.info("Start of setVideoProperties method");
    featuredObj.put(Constants.TITLE_FIELD, searchResultPojo.getAssetName());
    LOGGER.debug("featuredobj title: {}", featuredObj.get(Constants.TITLE_FIELD));
    if (searchResultPojo.getScene7Url().isEmpty()) {
      featuredObj.put(Constants.VIDEOURL_FIELD, searchResultPojo.getVideoValue());
    } else {
      featuredObj.put(Constants.VIDEOURL_FIELD, searchResultPojo.getScene7Url());
    }
    if (searchResultPojo.getScene7ImageUrl().isEmpty()) {
      featuredObj.put(Constants.IMAGEURL_FIELD, searchResultPojo.getThumbnailValue());
    } else {
      featuredObj.put(Constants.IMAGEURL_FIELD, searchResultPojo.getScene7ImageUrl());
    }
    featuredObj.put(Constants.PUBLISHDATE_FIELD, searchResultPojo.getPublishedDate());
    featuredObj.put(Constants.DESC_FIELD, searchResultPojo.getDescValue());
    featuredObj.put(Constants.FEATURED_FIELD, searchResultPojo.getCqtag());
    featuredObj.put(Constants.VIDEO_DURATION, searchResultPojo.getVideoDuration());
    featuredObj.put(Constants.CONTENT_ID, searchResultPojo.getVideoId());
    featured.put(featuredObj);
    LOGGER.info("End of setVideoProperties method");
  }

  /**
   * This method prepares the query map based on the input parameters from the
   * servlet request
   * 
   * @param requestDetailsMap
   * @param limit
   * @param offset
   * @param map
   * @param propvalue
   */
  private void prepareQueryMap(Map<String, Object> requestDetailsMap, int limit, int offset, Map<String, String> map,
      int propvalue) {
    LOGGER.info("End of prepareQueryMap method");
    String keyword = requestDetailsMap.get("keyword").toString();
    String path = requestDetailsMap.get(Constants.PATH_FIELD).toString();
    String filter = requestDetailsMap.get(Constants.FILTER_FIELD).toString();
    String sort = requestDetailsMap.get("sort").toString();
    String sortorder = requestDetailsMap.get("sortorder").toString();
    String text = "";
    List<String> cqTagsFinal = new ArrayList<>();
    if (!keyword.isEmpty()) {
      text = "%" + keyword + "%";
    }
    map.put("path", path);
    map.put("path.self", "false");
    map.put("type", "cq:PageContent");
    map.put("group.1_group.1_property", JcrConstants.JCR_TITLE);
    map.put("group.1_group.1_property.value", keyword);
    map.put("group.1_group.2_property", "root/videocomponentapi/text");
    map.put("group.1_group.2_property.value", text);
    map.put("group.1_group.1_property.operation", "equal");
    map.put("group.1_group.2_property.operation", "like");
    map.put("group.1_group.p.or", "true");
    map.put("group.2_group.3_property", Constants.CQ_TAGS);
    processFilterParams(map, propvalue, filter, cqTagsFinal);
    map.put("group.p.and", "true");
    processSortParams(map, sort, sortorder);
    if (limit > 0 && offset >= 0) {
      map.put("p.offset", Integer.toString(offset));
      map.put("p.limit", Integer.toString(limit));
    } else {
      map.put("p.limit", Integer.toString(limit));
    }
    map.put("p.facets", "true");
    LOGGER.info("End of prepareQueryMap method");
  }

  /**
   * This method processes the input parameters related to sort
   * 
   * @param map
   * @param sort
   * @param sortorder
   */
  private void processSortParams(Map<String, String> map, String sort, String sortorder) {
    LOGGER.info("Start of processSortParams method");
    if (sort.equalsIgnoreCase("N")) {
      LOGGER.debug("sort parameter is new");
      map.put("relativedaterange.property", Constants.LAST_MODIFIED);
      map.put("relativedaterange.lowerBound", "-7d");
      map.put(Constants.CONST_ORDERBY, Constants.ORDERBY_DATE);
      if (sortorder.equalsIgnoreCase("desc"))
        map.put(Constants.CONST_ORDERSORT, sortorder);
    } else if (sort.equalsIgnoreCase("R")) {
      map.put("relativedaterange.property", Constants.LAST_MODIFIED);
      map.put("relativedaterange.lowerBound", "-15d");
      map.put(Constants.CONST_ORDERBY, Constants.ORDERBY_DATE);
      if (sortorder.equalsIgnoreCase("desc"))
        map.put(Constants.CONST_ORDERSORT, sortorder);
    } else if (sort.equalsIgnoreCase("C")) {
      map.put(Constants.CONST_ORDERBY, "@jcr:title");
      if (sortorder.equalsIgnoreCase("desc"))
        map.put(Constants.CONST_ORDERSORT, sortorder);
    } else {
      map.put(Constants.CONST_ORDERBY, Constants.ORDERBY_DATE);
      if (sortorder.equalsIgnoreCase("desc"))
        map.put(Constants.CONST_ORDERSORT, sortorder);
    }
    LOGGER.info("End of processSortParams method");
  }

  /**
   * This method processes the input parameters related to filter
   * 
   * @param requestDetailsMap
   * @param tagValue
   * @param map
   * @param propvalue
   * @param filter
   * @param cqTagsFinal
   */
  private void processFilterParams(Map<String, String> map, int propvalue, String filter, List<String> cqTagsFinal) {
    LOGGER.info("Start of processFilterParams method");
    if (!filter.isEmpty() && filter.contains(",")) {
      String[] cqTagsList = filter.split(",");
      LOGGER.debug("cqTagsList length: {}", cqTagsList.length);
      for (String value : cqTagsList)
        cqTagsFinal.add(Constants.CQ_TAG_INITIAL + value);
      Iterator<String> itr = cqTagsFinal.iterator();
      while (itr.hasNext()) {
        String nextStr = itr.next();
        LOGGER.debug("Cqtags_final: {}", nextStr);
        String prop = "group.2_group.3_property." + propvalue + "_value";
        map.put(prop, nextStr);
        map.put("group.2_group.3_property.and", "true");
        propvalue++;
        LOGGER.debug("prop: {}", prop);
      }
    } else if (!filter.isEmpty() && !filter.contains(",")) {
      String tagValue = Constants.CQ_TAG_INITIAL + filter;
      map.put("group.2_group.3_property.1_value", tagValue);
      LOGGER.info("tagValue: {}", tagValue);
    }
    LOGGER.info("End of processFilterParams method");
  }

  private String getFacetTitle(String key, Facet facet) {
    LOGGER.info("Start of getFacetTitle method");
    List<Bucket> buckets = facet.getBuckets();
    if (!buckets.isEmpty()) {
      Bucket bucket = buckets.get(0);
      String title = bucket.getPredicate().get(Constants.TITLE_FIELD);
      if (StringUtils.isNotBlank(title)) {
        LOGGER.info("End of getFacetTitle method");
        return title;
      }
    }
    LOGGER.info("End of getFacetTitle method");
    return key;
  }

  /**
   * This method reads the fragment values from the authored fragment path in
   * the page and sets it in the respective variables.
 * @param searchResultPojo 
   * 
   * @param assetPath
   */
  private void getFragmentData(String assetPagePath, String assetPageCFPath, Node root, SearchResultPojo searchResultPojo) {
    LOGGER.info("Start of getFragmentData method");
    String fragmentPath = "";
    String fragmentDataPath = "";
    String thumbNailRPath = "";
    String videoRPath = "";
    try {
      Node dataPathNode = root.getNode(assetPageCFPath);
      if (dataPathNode.hasProperty("fragmentPath")) {
        Property fragmentPropty = dataPathNode.getProperty("fragmentPath");
        fragmentPath = fragmentPropty.getValue().toString().concat(Constants.JCR_CONTENT_PATH);
        LOGGER.debug("fragmentPath: {}", fragmentPath);

        Node assetPageNode = root.getNode(assetPagePath);
        fragmentDataPath = fragmentPath.substring(1) + "/data/master";
        Node fragmentDataNode = root.getNode(fragmentDataPath);
        
        if(Objects.nonNull(assetPageNode) && Objects.nonNull(fragmentDataNode)){
            setCqTagFlag(assetPageNode,searchResultPojo);
            LOGGER.debug("cqtag: {}", searchResultPojo.getCqtag());

            // get the description from content fragment node
            searchResultPojo.setDescValue(ProductVideosUtil.getPropertyValue(fragmentDataNode, Constants.CF_DESCRIPTION_PROPERTY));
            LOGGER.debug("descValue: {}", searchResultPojo.getDescValue());

            // get the Thumbnail value from content fragment node. then
            // fetch the scene7 URL
            searchResultPojo.setThumbnailValue(ProductVideosUtil.getPropertyValue(fragmentDataNode, Constants.THUMBNAIL_IMAGE));
            LOGGER.debug("thumbnailValue : {}", searchResultPojo.getThumbnailValue());
            thumbNailRPath = searchResultPojo.getThumbnailValue().substring(1) + Constants.JCR_CONTENT_METADATA_PATH;
            LOGGER.debug("thumbNailRPath : {}", thumbNailRPath);
            if (root.hasNode(thumbNailRPath)) {
              Node scene7ImageNode = root.getNode(thumbNailRPath);
              searchResultPojo.setScene7ImageUrl(ProductVideosUtil.getAssetScene7URL(scene7ImageNode));
            }

            // get the video value from content fragment node. then fetch
            // the scene7 URL for video
            searchResultPojo.setVideoValue(ProductVideosUtil.getPropertyValue(fragmentDataNode, Constants.VIDEO));
            LOGGER.debug("scene7ImageUrl:{} and videoValue: {}",searchResultPojo.getScene7ImageUrl() ,searchResultPojo.getVideoValue());
            videoRPath = searchResultPojo.getVideoValue().substring(1) + "/jcr:content/metadata";
            if (root.hasNode(videoRPath)) {
              Node scene7Node = root.getNode(videoRPath);
              searchResultPojo.setScene7Url(ProductVideosUtil.getAssetScene7URL(scene7Node));
              searchResultPojo.setVideoDuration(ProductVideosUtil.getPropertyValue(scene7Node, Constants.VIDEO_DURATION));
              searchResultPojo.setVideoId(ProductVideosUtil.getPropertyValue(scene7Node, Constants.CONTENT_ID));
            }
            LOGGER.debug("For Video : {} Scene7url is: {}", videoRPath ,searchResultPojo.getScene7Url());
            LOGGER.debug("videoDuration is: {} and videoId is: {}", searchResultPojo.getVideoDuration(),searchResultPojo.getVideoId());

            // get the title from the page properties
            searchResultPojo.setAssetName(ProductVideosUtil.getPropertyValue(assetPageNode, JcrConstants.JCR_TITLE));
            LOGGER.debug("AssetName: {}", searchResultPojo.getAssetName());

            // get the last modified property and set it as publish date
            if (assetPageNode.hasProperty(Constants.LAST_MODIFIED)) {
              Property publishDateProperty = assetPageNode.getProperty(Constants.LAST_MODIFIED);
              Calendar publishDate = publishDateProperty.getDate();
              Date c = publishDate.getTime();
              DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
              searchResultPojo.setPublishedDate(formatter.format(c));
              LOGGER.debug("publishedDate: {}", searchResultPojo.getPublishedDate());
            }  
        }
      }
    } catch (RepositoryException e) {
      LOGGER.error("node not found " + e.getMessage());
    }
    LOGGER.info("End of getFragmentData method");

  }

/**
   * @param assetPageNode
 * @param searchResultPojo 
   * @throws RepositoryException
   * @throws PathNotFoundException
   * @throws ValueFormatException
   */
  private void setCqTagFlag(Node assetPageNode, SearchResultPojo searchResultPojo) throws RepositoryException {
    if (assetPageNode.hasProperty("cq:tags")) {
      Property fragmenttagPropty = assetPageNode.getProperty("cq:tags");
      Value[] tagsValue = fragmenttagPropty.getValues();
      for (Value v : tagsValue) {
        if (v.getString().equalsIgnoreCase("jurassicworldfacts:featured")) {
          LOGGER.debug("authored tag value is: {}", v.getString());
          searchResultPojo.setCqtag("Y");
        } else {
          searchResultPojo.setCqtag("N");
        }
      }
    }
  }

}
