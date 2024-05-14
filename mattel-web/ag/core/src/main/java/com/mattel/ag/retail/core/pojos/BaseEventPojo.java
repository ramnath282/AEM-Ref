package com.mattel.ag.retail.core.pojos;

public class BaseEventPojo {
    private String eventId;
    private String eventTitle;
    private String eventDescription;
    private String minAge;
    private String reservationRequired;
    private String eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private String scheduleDescription;
    private String additionalDateInfo;
    
    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getScheduleDescription() {
        return scheduleDescription;
    }

    public void setScheduleDescription(String scheduleDescription) {
        this.scheduleDescription = scheduleDescription;
    }

    public String getAdditionalDateInfo() {
        return additionalDateInfo;
    }

    public void setAdditionalDateInfo(String additionalDateInfo) {
        this.additionalDateInfo = additionalDateInfo;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getReservationRequired() {
        return reservationRequired;
    }

    public void setReservationRequired(String reservationRequired) {
        this.reservationRequired = reservationRequired;
    }

    @Override
    public String toString() {
        return "BaseEventPojo [eventId=" + eventId + ", eventTitle=" + eventTitle + ", eventDescription="
                + eventDescription + ", minAge=" + minAge + ", reservationRequired=" + reservationRequired + "]";
    }
}
