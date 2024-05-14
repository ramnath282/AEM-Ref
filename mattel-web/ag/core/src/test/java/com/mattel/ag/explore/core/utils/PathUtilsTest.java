package com.mattel.ag.explore.core.utils;

import org.junit.Test;
import org.junit.Assert;

public class PathUtilsTest {

    PathUtils pathUtils;

    @Test
    public void testWithInternalLink() {
        boolean isExternalPath = pathUtils.isExternal("/content/ag/en/explore");
        Assert.assertFalse(isExternalPath);
    }

    @Test
    public void testWithExternalLink() {
        boolean isExternalPath = pathUtils.isExternal("https://www.facebook.com");
        Assert.assertTrue(isExternalPath);
    }

}
