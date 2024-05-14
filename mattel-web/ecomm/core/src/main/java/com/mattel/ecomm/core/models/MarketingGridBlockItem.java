package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MarketingGridBlockItem {

    @Inject
    private String headline;

    @Inject
    private String description;

    @Inject
    private String smallImage;

    @Inject
    private String largeImage;

    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }
}
