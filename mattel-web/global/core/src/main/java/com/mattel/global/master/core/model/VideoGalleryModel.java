package com.mattel.global.master.core.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.master.core.pojos.VideoTilePojo;
import com.mattel.global.master.core.services.VideoGalleryService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoGalleryModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoGalleryModel.class);

	@Inject
	private String videoSourcePath;
	@Inject
	private VideoGalleryService videoGalleryService;
	@Inject
	private String[] manualVideos;

	@Inject
	private String category;

	@Inject
	private String showMoreText;

	@Inject
	private String showLessText;

	@Inject
	private String videoDetailPageUrl;

	@Self
  private Resource resource;

	private List<VideoTilePojo> videoManualList = new LinkedList<>();
	private List<VideoTilePojo> videoByDateList = new LinkedList<>();
	private List<VideoTilePojo> videoByCategoryList = new LinkedList<>();

	@PostConstruct
	protected void init() {
		LOGGER.info("VideoGalleryModel init() -> started");

		LOGGER.info("VideoGalleryModel init() -> ended");
	}

	/**
	 * Method to fetch the video details when orderBy option is "manual"
	 *
	 * @return List<VideoTilePojo>:videoManualList
	 * 			the list of videos
	 */
	public List<VideoTilePojo> getVideoManualList() {
		LOGGER.info("getVideoManualList() method -> started");
		videoManualList.clear();
		if (Objects.nonNull(manualVideos)) {
			videoManualList = videoGalleryService.getManualVideos(manualVideos);
			videoManualList = checkVideoUrl(videoManualList, resource);
			LOGGER.debug("videoManualList is {}",videoManualList);
		}
		LOGGER.info("getVideoManualList() method -> ended");
		return videoManualList;
	}

	/**
	 * Method to fetch the video details when orderBy option is "by date"
	 *
	 * @return List<VideoTilePojo>:videoByDateList
	 * 			the list of videos
	 */
	public List<VideoTilePojo> getVideoByDateList() {
		LOGGER.info("getVideoByDateList() method -> started");
		videoByDateList = videoGalleryService.getVideosByDateCategory(videoSourcePath, StringUtils.EMPTY);
		videoByDateList = checkVideoUrl(videoByDateList,resource);
		LOGGER.debug("videoByDateList is {}",videoByDateList);
		LOGGER.info("getVideoByDateList() method -> ended");
		return videoByDateList;
	}

	/**
	 * Method to fetch the video details when orderBy option is "category"
	 *
	 * @return List<VideoTilePojo>:videoByCategoryList
	 * 			the list of videos
	 */
	public List<VideoTilePojo> getVideoByCategoryList() {
		LOGGER.info("getVideoByCategoryList() method -> started");
		if (Objects.nonNull(category) && Objects.nonNull(videoSourcePath)) {
			String videoGalleryCategoryID = videoGalleryService.getCategoryId(category);
			LOGGER.debug("videoGalleryCategoryID is {}",videoGalleryCategoryID);
			videoByCategoryList = videoGalleryService.getVideosByDateCategory(videoSourcePath, videoGalleryCategoryID);
			videoByCategoryList = checkVideoUrl(videoByCategoryList, resource);
			LOGGER.debug("videoByCategoryList is {}",videoByCategoryList);
		}
		LOGGER.info("getVideoByCategoryList() method -> ended");
		return videoByCategoryList;
	}

	public void setVideoManualList(List<VideoTilePojo> videoManualList) {
		this.videoManualList = videoManualList;
	}

	public void setVideoByDateList(List<VideoTilePojo> videoByDateList) {
		this.videoByDateList = videoByDateList;
	}

	public void setVideoByCategoryList(List<VideoTilePojo> videoByCategoryList) {
		this.videoByCategoryList = videoByCategoryList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getShowMoreText() {
		return GlobalUtils.removeTags(showMoreText, Constants.REMOVE_TAGS,
				Constants.EMPTY_ARRAY_STRING);
	}

	public String getShowLessText() {
		return GlobalUtils.removeTags(showLessText, Constants.REMOVE_TAGS,
				Constants.EMPTY_ARRAY_STRING);
	}

  public List<VideoTilePojo> checkVideoUrl(List<VideoTilePojo> videoList, Resource resource) {
      String videoUrl = StringUtils.isNotBlank(videoDetailPageUrl) ? videoDetailPageUrl : "";
      videoList.forEach(videoTile -> videoTile
          .setVideoUrl(GlobalUtils.checkLink(videoUrl, resource) + Constants.VIDEO_URL));
      return videoList;
    }
}
