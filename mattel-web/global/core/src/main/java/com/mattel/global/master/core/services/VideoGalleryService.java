package com.mattel.global.master.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.global.core.services.GetResourceResolver;
import com.mattel.global.master.core.constants.Constants;
import com.mattel.global.master.core.pojos.VideoTilePojo;

@Component(service = VideoGalleryService.class, immediate = true)
public class VideoGalleryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoGalleryService.class);

	@Reference
	QueryBuilder queryBuilder;

	List<VideoTilePojo> masterListForVideoTile;

	@Reference
	GetResourceResolver getResourceResolver;

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	/**
	 * Method to fetch video details ordered by date and filtered by category if required
	 *
	 * @param path:path
	 * 			base path for videos
	 * @param selectedCategoryTag:selectedCategoryTag
	 * 			categoryTag to filter the videos by
	 * @return List<VideoTilePojo>:filteredList
	 * 			the list of videos
	 */
	public List<VideoTilePojo> getVideosByDateCategory(String path, String selectedCategoryTag) {
		LOGGER.info("getVideosByDateCategory() method of class VideoGalleryService -> started");
		List<VideoTilePojo> filteredList = new ArrayList<>();
		Session session = null;
		try (ResourceResolver resolver = getResourceResolver.getResourceResolver()) {
			session = resolver.adaptTo(Session.class);
			String orderbyProp = Constants.JCR_LAST_MODIFIED;
			LOGGER.debug("orderbyProp is {}",orderbyProp);
			Map<String, String> querymap = getVideosQueryMap(path, selectedCategoryTag, orderbyProp);
			Query videosQuery = queryBuilder.createQuery(PredicateGroup.create(querymap), session);
			LOGGER.debug("pageQuery is {}",videosQuery);
			if (Objects.nonNull(videosQuery)) {
				LOGGER.debug("pageQuery is not null");
				SearchResult result = videosQuery.getResult();
				filteredList = fetchVideoAssetResult(result, resolver);
				LOGGER.debug("filteredList is {}",filteredList);
			}
		} catch ( RepositoryException e) {
			LOGGER.error(" RepositoryException Exception occurred in getVideosByDate() method of class VideoGalleryService {} ", e);
		}
		LOGGER.info("getVideosByDateCategory() method of class VideoGalleryService -> ended");
		return filteredList;
	}

	/**
	 * Method to get the QUERYMAP to fetch video details; either having a particular category or all videos inside a path ordered by date
	 *
	 * @param path:path
	 * 			base path for videos
	 * @param selectedCategoryTag:selectedCategoryTag
	 * 			categoryTag to filter the videos by
	 * @param orderbyProp:orderbyProp
	 * 			property to order the videos by
	 * @return Map<String, String>:querymap
	 * 			querymap
	 */
	private Map<String, String> getVideosQueryMap(String path, String selectedCategoryTag, String orderbyProp) {
		LOGGER.info("getVideosQueryMap() method of class VideoGalleryService -> started");
		Map<String, String> querymap = new HashMap<>();
		querymap.put("path", path);
		querymap.put("type", "dam:Asset");
		if ("dc:videoPublishDate".equalsIgnoreCase(orderbyProp)) {
			querymap.put("orderby", "@jcr:content/metadata/dc:videoPublishDate");
		} else {
			querymap.put("orderby", "@jcr:content/jcr:lastModified");
		}
		querymap.put("orderby.sort", "desc");
		querymap.put("p.limit", "5000");
		if (StringUtils.isNotBlank(selectedCategoryTag)) {
			querymap.put("1_property", "jcr:content/metadata/dc:tags");
			querymap.put("1_property.value", selectedCategoryTag);
		}
		LOGGER.debug("querymap is {}",querymap);
		LOGGER.info("getVideosQueryMap() method of class VideoGalleryService -> ended");
		return querymap;
	}

	/**
	 * Method to fetch video details from the result of query
	 *
	 * @param result:result
	 * 			SearchResult object from the query
	 * @param resolver:resResolver
	 * 			ResourceResolver object
	 * @return List<VideoTilePojo>:filteredList
	 * 			the list of videos
	 */
	private List<VideoTilePojo> fetchVideoAssetResult(SearchResult result, ResourceResolver resolver)
			throws RepositoryException {
		LOGGER.info("fetchVideoAssetResult() method of class VideoGalleryService -> started");
		List<VideoTilePojo> filteredList = new ArrayList<>();
		if (Objects.nonNull(result)) {
			LOGGER.debug("result is not null");
			for (Hit hit : result.getHits()) {
				Resource resource = resolver.getResource(hit.getPath());
				if (Objects.nonNull(resource)) {
					VideoTilePojo videoDetail = prepareVideoTile(resource, resolver);
					filteredList.add(videoDetail);
				}
			}
			Iterator<Resource> resources = result.getResources();
			if (resources.hasNext()) {
				ResourceResolver leakingResResolver = resources.next().getResourceResolver();
				if (leakingResResolver.isLive()) {
					leakingResResolver.close();
				}
			}

		}
		LOGGER.debug("filteredList is {}",filteredList);
		LOGGER.info("fetchVideoAssetResult() method of class VideoGalleryService -> ended");
		return filteredList;
	}

	/**
	 * Method to prepare a VideoTilePojo from a video asset resource
	 *
	 * @param resolver:assetResource
	 * 			Resource object for video asset
	 * @return VideoTilePojo:videoDetail
	 * 			VideoTilePojo object containing details of video asset
	 */
	VideoTilePojo prepareVideoTile(Resource assetResource, ResourceResolver resolver) {
		LOGGER.info("prepareVideoTile() method of class VideoGalleryService -> started");
		VideoTilePojo videoDetail = null;
		StringBuilder category = new StringBuilder();
		String categories = null;
		try {
			if (Objects.nonNull(assetResource)) {
				LOGGER.debug("assetResource is not null");
				Resource metadataRes = resolver.getResource(assetResource, "jcr:content/metadata");
				videoDetail = new VideoTilePojo();
				String tag = getAssetMetadataValue(assetResource, "dc:tags");
				LOGGER.debug("tag is {}",tag);
				if (Objects.nonNull(tag)) {
					LOGGER.debug("tag is not null");
					String[] tags = tag.split(",");
					LOGGER.debug("tags is {}",tags);
					List<String> videoTags = new ArrayList<>();
					for (String vTag : tags) {
						LOGGER.debug("vTag is {}",vTag);
						videoTags.add(vTag.trim());
						String[] categorylist = vTag.split("/");
						LOGGER.debug("categorylist is {}",categorylist);
						String tempCategory = "";
						for (int i = 0; i < categorylist.length; i++) {
							tempCategory = categorylist[categorylist.length - 1] + "/";
						}
						LOGGER.debug("tempCategory is {}",tempCategory);
						category = category.append(tempCategory);
						LOGGER.debug("category is {}",category);
						int len = category.length();
						LOGGER.debug("len is {}",len);
						categories = category.substring(0, len - 1);
						LOGGER.debug("categories is {}",categories);
					}
					videoDetail.setVideoTags(videoTags);
				}
				videoDetail.setVideoThumbnail(assetResource.getPath());
				videoDetail.setVideoCategory(categories);
				String videotitle = getAssetMetadataValue(metadataRes, "dc:title");
				LOGGER.debug("videotitle is {}",videotitle);
				videoDetail.setVideoTitle(videotitle);
				String videoAlt = getAssetMetadataValue(metadataRes, "dc:alttext");
				LOGGER.debug("videoAlt is {}",videoAlt);
				videoDetail.setThumbnailAltTxt(videoAlt);
				String videoDesc = getAssetMetadataValue(metadataRes, "dc:description");
				LOGGER.debug("videoDesc is {}",videoDesc);
				videoDetail.setVideoDesc(videoDesc);
				String ooyalaId = getAssetMetadataValue(metadataRes, "dc:ooyalaID");
				LOGGER.debug("ooyalaId is {}",ooyalaId);
				videoDetail.setVideoId(ooyalaId);
				String seoUrl = getAssetMetadataValue(metadataRes, "dc:seoUrl");
				LOGGER.debug("seoUrl is {}",seoUrl);
				videoDetail.setSeoUrl(seoUrl);
				String seoTitle = getAssetMetadataValue(metadataRes, "dc:seoTitle");
				LOGGER.debug("seoTitle is {}",seoTitle);
				videoDetail.setSeotitle(seoTitle);
				String metaDesc = getAssetMetadataValue(metadataRes, "dc:metaDesc");
				LOGGER.debug("metaDesc is {}",metaDesc);
				videoDetail.setMetaDesc(metaDesc);
				String keywords = getAssetMetadataValue(metadataRes, "dc:metaKeywords");
				LOGGER.debug("keywords is {}",keywords);
				videoDetail.setMetaKeywords(keywords);
				String videoName = getAssetMetadataValue(metadataRes, "dc:name");
				LOGGER.debug("videoName is {}",videoName);
				videoDetail.setVideoName(videoName);
				String alwaysEnglish = getAssetMetadataValue(metadataRes, "dc:alwaysEnglish");
				LOGGER.debug("alwaysEnglish is {}",alwaysEnglish);
				alwaysEnglish = alwaysEnglish.replace("|", "-");
				LOGGER.debug("refactored alwaysEnglish is {}",alwaysEnglish);
				videoDetail.setAlwaysEnglish(alwaysEnglish);
			}
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException Occured in prepareVideoTile() method of class VideoGalleryService {} ", e);
		}
		LOGGER.debug("videoDetail is {}",videoDetail);
		LOGGER.info("prepareVideoTile() method of class VideoGalleryService -> ended");
		return videoDetail;
	}

	/**
	 * Method to get value of a metaProperty of asset
	 *
	 * @param metadataResource:assetResource
	 * 			Resource object for video asset
	 * @param metaProperty:metaProperty
	 * 			name of metaProperty of asset
	 * @return String:propertyValue
	 * 			value of a metaProperty
	 */
	private String getAssetMetadataValue(Resource metadataResource, String metaProperty) throws RepositoryException {
		LOGGER.info("getAssetMetadataValue() method of class VideoGalleryService -> started");
		String propertyValue = StringUtils.EMPTY;
		if (Objects.nonNull(metadataResource)) {
			LOGGER.debug("metadataResource is not null");
			Node metadataNode = metadataResource.adaptTo(Node.class);
			if (Objects.nonNull(metadataNode) && metadataNode.hasProperty(metaProperty)) {
				LOGGER.debug("metadataNode is not null");
				if (metadataNode.getProperty(metaProperty).isMultiple()) {
					Property property = metadataNode.getProperty(metaProperty);
					LOGGER.debug("property is {}",property);
					Value[] values = property.getValues();
					LOGGER.debug("values is {}",values);
					if (Objects.nonNull(values)) {
						LOGGER.debug("values is not null");
						propertyValue = Arrays.toString(values).replace("[", "").replace("]", "");
						LOGGER.debug("propertyValue is {}",propertyValue);
					}
				} else {
					propertyValue = metadataNode.getProperty(metaProperty).getString();
					LOGGER.debug("propertyValue is {}",propertyValue);
				}
			}
		}
		LOGGER.info("getAssetMetadataValue() method of class VideoGalleryService -> ended");
		return propertyValue;
	}

	/**
	 * Method to fetch video details of manually given paths for video assets
	 *
	 * @param plpVideos:plpVideos
	 * 			list of video asset paths
	 * @return List<VideoTilePojo>:filteredList
	 * 			the list of videos
	 */
	public List<VideoTilePojo> getManualVideos(String[] plpVideos) {
		LOGGER.info("getManualVideos() method of class VideoGalleryService -> started");
		List<VideoTilePojo> videoList = new LinkedList<>();
		try(ResourceResolver resolver = getResourceResolver.getResourceResolver()) {
			if (Objects.nonNull(plpVideos)) {
				LOGGER.debug("plpVideos is not null");
				videoList.clear();
				for (String videoItem : plpVideos) {
					VideoTilePojo videoDetails = null;
					String videoThumbnailPath = videoItem;
					LOGGER.debug("videoThumbnailPath is {}", videoThumbnailPath);
					Resource authoredVideoResource = resolver.resolve(videoThumbnailPath);
					videoDetails = prepareVideoTile(authoredVideoResource, resolver);
					LOGGER.debug("videoDetails is {}", videoDetails);
					videoList.add(videoDetails);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in getManualVideos() method of class VideoGalleryService {} ", e);
		}
		LOGGER.debug("videoList is {}", videoList);
		LOGGER.info("getManualVideos() method of class VideoGalleryService -> ended");
		return videoList;
	}

	/**
	 * Method to get the categoryId of the category selected
	 *
	 * @param category:cateogry
	 * @return String:categoryID
	 */
	public String getCategoryId(String category) {
		String categoryID = StringUtils.EMPTY;
		try(ResourceResolver resolver = getResourceResolver.getResourceResolver()) {
			TagManager tagManager = resolver.adaptTo(TagManager.class);
			if(Objects.nonNull(tagManager)) {
				Tag categoryTag = tagManager.resolve(category);
				LOGGER.debug("categoryTag is {}",categoryTag);
				categoryID = categoryTag.getTagID();
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured in getCategoryId() method of class VideoGalleryService {} ", e);
		}
		LOGGER.debug("categoryID is {}",categoryID);
		return categoryID;
	}
}
