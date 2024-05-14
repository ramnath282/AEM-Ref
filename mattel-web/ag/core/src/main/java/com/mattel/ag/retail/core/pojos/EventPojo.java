package com.mattel.ag.retail.core.pojos;

import java.util.List;

public class EventPojo extends BaseEventPojo {
	private String keywords;
	private List<LocationDetailsPojo> locationDetails;

	public List<LocationDetailsPojo> getLocationDetails() {
		return locationDetails;
	}

	public void setLocationDetails(List<LocationDetailsPojo> locationDetails) {
		this.locationDetails = locationDetails;
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

    @Override
    public String toString() {
        return "EventPojo [keywords=" + keywords + ", locationDetails=" + locationDetails + ", toString()="
                + super.toString() + "]";
    }
}
