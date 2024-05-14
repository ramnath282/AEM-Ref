package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MarketingGridModel {

    @Inject
    private String title;

    @Inject
    private String subTitle;

    @Inject
    private List<MarketingGridBlockItem> blockItems;

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public List<MarketingGridBlockItem> getBlockItems() {
        return blockItems;
    }

}
