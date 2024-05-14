package com.mattel.ag.retail.core.pojos;

import java.util.Date;

public class DisplayEventPojo extends BaseEventPojo {
    private String locationName;
    private String eventZomatoUrl;
    private String gratuityRequired;
    private String eventDateForSearchResult;
    private String pricingDetails;
    private Date date;
    private Date time;
    private String eventDateDetailNodePath;
    private String reservationDetails;
    private String dateDetailNodePath;
    private String costInfo;

    public String getCostInfo() {
        return costInfo;
    }

    public void setCostInfo(String costInfo) {
        this.costInfo = costInfo;
    }

    public String getEventDateForSearchResult() {
        return eventDateForSearchResult;
    }

    public void setEventDateForSearchResult(String eventDateForSearchResult) {
        this.eventDateForSearchResult = eventDateForSearchResult;
    }

    public String getDateDetailNodePath() {
        return dateDetailNodePath;
    }

    public void setDateDetailNodePath(String dateDetailNodePath) {
        this.dateDetailNodePath = dateDetailNodePath;
    }

    public String getReservationDetails() {
        return reservationDetails;
    }

    public void setReservationDetails(String reservationDetails) {
        this.reservationDetails = reservationDetails;
    }

    public String getEventDateDetailNodePath() {
        return eventDateDetailNodePath;
    }

    public void setEventDateDetailNodePath(String eventDateDetailNodePath) {
        this.eventDateDetailNodePath = eventDateDetailNodePath;
    }

    public String getPricingDetails() {
        return pricingDetails;
    }

    public void setPricingDetails(String pricingDetails) {
        this.pricingDetails = pricingDetails;
    }

    public String getGratuityRequired() {
        return gratuityRequired;
    }

    public void setGratuityRequired(String gratuityRequired) {
        this.gratuityRequired = gratuityRequired;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getEventZomatoUrl() {
        return eventZomatoUrl;
    }

    public void setEventZomatoUrl(String eventZomatoUrl) {
        this.eventZomatoUrl = eventZomatoUrl;
    }

    @Override
    public String toString() {
        return "DisplayEventPojo [locationName=" + locationName + ", eventZomatoUrl=" + eventZomatoUrl
                + ", gratuityRequired=" + gratuityRequired + ", eventDateForSearchResult=" + eventDateForSearchResult
                + ", pricingDetails=" + pricingDetails + ", date=" + date + ", time=" + time
                + ", eventDateDetailNodePath=" + eventDateDetailNodePath + ", reservationDetails=" + reservationDetails
                + ", dateDetailNodePath=" + dateDetailNodePath + ", costInfo=" + costInfo + ", toString()="
                + super.toString() + "]";
    }

}
