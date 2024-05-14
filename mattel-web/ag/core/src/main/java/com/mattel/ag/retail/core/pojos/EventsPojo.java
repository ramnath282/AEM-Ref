package com.mattel.ag.retail.core.pojos;

import java.util.Date;

public class EventsPojo {
  private String eventDate;
  private String startTime;
  private String endTime;
  private String eventTitle;
  private String eventDescription;
  private String eventInfo;
  private Date date;
  private String zomatoUrl;
  private String tagTitle;
  private String tagNameSpace;
  private Date time;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getEventDate() {
    return eventDate;
  }

  public void setEventDate(String eventDate) {
    this.eventDate = eventDate;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getEventDescription() {
    return eventDescription;
  }

  public void setEventDescription(String eventDescription) {
    this.eventDescription = eventDescription;
  }

  public String getZomatoUrl() {
    return zomatoUrl;
  }

  public void setZomatoUrl(String zomatoUrl) {
    this.zomatoUrl = zomatoUrl;
  }

  public String getEventTitle() {
    return eventTitle;
  }

  public void setEventTitle(String eventTitle) {
    this.eventTitle = eventTitle;
  }

  public String getEventInfo() {
    return eventInfo;
  }

  public void setEventInfo(String eventInfo) {
    this.eventInfo = eventInfo;
  }

  public void setTagTitle(String tagTitle) {
    this.tagTitle = tagTitle;
  }

  public String getTagTitle() {
    return tagTitle;
  }

  public void setTagNameSpace(String tagNameSpace) {
    this.tagNameSpace = tagNameSpace;
  }

  public String getTagNameSpace() {
    return tagNameSpace;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Date getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "EventsPojo{" +
        "eventDate='" + eventDate + '\'' +
        ", startTime='" + startTime + '\'' +
        ", endTime='" + endTime + '\'' +
        ", eventTitle='" + eventTitle + '\'' +
        ", eventDescription='" + eventDescription + '\'' +
        ", eventInfo='" + eventInfo + '\'' +
        ", date=" + date +
        ", zomatoUrl='" + zomatoUrl + '\'' +
        '}';
  }


}
