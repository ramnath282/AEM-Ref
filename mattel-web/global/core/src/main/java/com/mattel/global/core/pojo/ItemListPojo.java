package com.mattel.global.core.pojo;

import java.util.List;

public class ItemListPojo {
  private String pubDate;
  private String imageUrl;
  private String title;
  private String subtitle;
  private String content;
  private String description;
  private String mediaContent;
  private String attachmentZip;
  private String link;
  private List<AttachmentDetailsPojo> attachmentDetails;

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getPubDate() {
    return pubDate;
  }

  public void setPubDate(String pubDate) {
    this.pubDate = pubDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<AttachmentDetailsPojo> getAttachmentDetails() {
    return attachmentDetails;
  }

  public void setAttachmentDetails(List<AttachmentDetailsPojo> attachmentDetails) {
    this.attachmentDetails = attachmentDetails;
  }

  public String getMediaContent() {
    return mediaContent;
  }

  public void setMediaContent(String mediaContent) {
    this.mediaContent = mediaContent;
  }

  public String getAttachmentZip() {
    return attachmentZip;
  }

  public void setAttachmentZip(String attachmentZip) {
    this.attachmentZip = attachmentZip;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override
  public String toString() {
    return "ItemListPojo [pubDate=" + pubDate + ", imageUrl=" + imageUrl + ", title=" + title
            + ", subtitle=" + subtitle + ", content=" + content + ", description=" + description
            + ", mediaContent=" + mediaContent + ", attachmentZip=" + attachmentZip + ", link=" + link
            + ", attachmentDetailsPojo=" + attachmentDetails + "]";
  }

}
