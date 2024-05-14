package com.mattel.fisherprice.core.services;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.pojos.TagsPojo;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(service = RelatedArticleService.class, immediate = true)
public class RelatedArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelatedArticleService.class);
    @Reference
    ResourceResolverFactory resolverFactory;

    /**
     * This method provides all the data related to a tag.
     *
     * @param primaryTags takes input as primary tag form page properties
     * @return returns tag data
     */

    public List<TagsPojo> getTagRelatedData(String[] primaryTags, Locale locale) {
        LOGGER.info("start of setTagRelatedData() Method");
        List<TagsPojo> tagsPojoList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
        ResourceResolver resolver = null;
        if (null != resolverFactory) {
            LOGGER.debug("resolver factory in getTagRelatedData is not null");
            try {
                resolver = resolverFactory.getServiceResourceResolver(map);
                TagManager tagManager = resolver.adaptTo(TagManager.class);
                createtagPojoList(primaryTags, tagManager, tagsPojoList, locale);
            } catch (org.apache.sling.api.resource.LoginException e) {
                LOGGER.error("Exception caused in setArticleImagePath", e);
            } finally {
                LOGGER.info("start of finally in setArticleImagePath() Method");
                if (resolver != null && resolver.isLive()) {
                    resolver.close();
                }
                LOGGER.info("End of finall in setArticleImagePath() Method");

            }
        }
        LOGGER.info("End of setTagRelatedData() Method");
        return tagsPojoList;
    }

    private void createtagPojoList(String[] primaryTags, TagManager tagManager, List<TagsPojo> tagsPojoList, Locale locale) {
        if (null != primaryTags && null != tagManager) {
            Arrays.stream(primaryTags).forEach(primaryTag -> {
                TagsPojo tagsPojo = new TagsPojo();
                Tag tag = tagManager.resolve(primaryTag);
                LOGGER.debug("Tag Name Is {}", tag);
                LOGGER.debug("tagNameSpace is {}", primaryTag);
                if (null != tag) {
                    String tagTitle = tag.getTitle();
                    String tagID = tag.getLocalTagID();
                    String tagName = tag.getName();
                    String localeBasedTitle = tag.getTitle(locale);
                    tagsPojo.setTagTitle(tagTitle);
                    tagsPojo.setTagID(tagID);
                    tagsPojo.setTagName(tagName);
                    tagsPojo.setLocaleBasedTitle(localeBasedTitle);
                    tagsPojoList.add(tagsPojo);
                }
            });
        }
    }
}
