package com.mattel.global.master.core.model;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Carousel;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CommanCtaModel {

    @Self
    private Carousel carousel;

    @Inject
    @Via("resource")
    private String entrCompClickable;

    public String getEntrCompClickable() { return entrCompClickable; }

    public List<ListItem> getItems() {
        return carousel.getItems();
    }
}
