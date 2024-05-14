package com.mattel.global.master.core.model;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import javax.inject.Inject;


@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionContainerModel extends BaseAccordionModel{

    @Inject
    @Via("resource")
    private String autoClose;

    @Inject
    @Via("resource")
    private String ctaOpenText;

    @Inject
    @Via("resource")
    private String ctaCloseText;

    public String getAutoClose() { return autoClose; }

    public String getCtaOpenText() { return ctaOpenText; }

    public String getCtaCloseText() { return ctaCloseText; }

}
