package com.mattel.ag.explore.core.utils;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.explore.core.utils.PropertyReaderUtils.Config;

public class PropertyReaderUtilsTest {

    PropertyReaderUtils propertyReaderUtils;
    Config config;

    private static final String FACEBOOK_URL = "https://www.facebook.com";
    private static final String TWITTER_URL = "https://twitter.com";
    private static final String PINTEREST_URL = "http://www.pinterest.com";

    @Before
    public void setUp() {
        propertyReaderUtils = new PropertyReaderUtils();
        config = Mockito.mock(PropertyReaderUtils.Config.class);
        propertyReaderUtils.setArticleImagePath("/content/dam/sightlyMF/e84fe0c6d77c6b12e040cdd76f4d139bx.jpg");
        propertyReaderUtils.setExplorePagePath("/content/ag/en/explore");
    }

    @Test
    public void testPropertyReaderUtil() {
        when(config.facebookUrl()).thenReturn(FACEBOOK_URL);
        when(config.twitterUrl()).thenReturn(TWITTER_URL);
        when(config.pinterestUrl()).thenReturn(PINTEREST_URL);
        when(config.explorePagePath()).thenReturn(propertyReaderUtils.getExplorePagePath());
        when(config.articleImagePath()).thenReturn(propertyReaderUtils.getArticleImagePath());

        propertyReaderUtils.activate(config);

        Assert.assertEquals(FACEBOOK_URL, propertyReaderUtils.getFacebookUrl());
        Assert.assertEquals(PINTEREST_URL, propertyReaderUtils.getPinterestUrl());
        Assert.assertEquals(TWITTER_URL, propertyReaderUtils.getTwitterUrl());

    }

}
