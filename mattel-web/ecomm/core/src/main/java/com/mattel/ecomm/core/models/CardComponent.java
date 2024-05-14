package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.mattel.ecomm.core.utils.EcomUtil;

import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardComponent {

	@Self
	private Resource resource;
	  
	@Inject
    private String title;

    @Inject
    private String image;

    @Inject
    private String ctaText;

    @Inject
    private String ctaLink;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getCtaText() {
        return ctaText;
    }

    /**
     * Deliver the vanityUrl() over the getPath() if it exists
     * @return link final URL for the authored path
     * */
    public String getCtaLink() {
    	return EcomUtil.checkLink(ctaLink, resource);
    }
}
