package com.mattel.global.master.core.model;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

import javax.inject.Inject;


@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BaseAccordionModel {

    @Inject
    @Via("resource")
    private String title;

    @Inject
    @Via("resource")
    private String description;

    @Inject
    @Via("resource")
    private String subTitle;

    public String getTitle() {
        return GlobalUtils.removeTags(title, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
    }

    public String getDescription() {
        return GlobalUtils.removeTags(description, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
    }

    public String getSubTitle() {
        return GlobalUtils.removeTags(subTitle, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
    }

    public String getSectionTitleAnalytics() {
        return StringUtils.isNotBlank(title) ? title.replaceAll("\\<.*?\\>", "") : StringUtils.EMPTY;
    }
}
