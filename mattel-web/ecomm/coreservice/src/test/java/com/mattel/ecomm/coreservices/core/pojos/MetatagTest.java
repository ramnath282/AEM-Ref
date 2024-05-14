package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Test;

public class MetatagTest {
    @Test
    public void testGettersAndSetters() throws IllegalAccessException {
        Metatag metaTag = new Metatag("name", "content");
        metaTag.setCharset("charset");
        metaTag.setContent("content");
        metaTag.setHttpEquiv("httpEquiv");
        metaTag.setName("name");
        Assert.assertEquals("charset", metaTag.getCharset());
        Assert.assertEquals("content", metaTag.getContent());
        Assert.assertEquals("httpEquiv", metaTag.getHttpEquiv());
        Assert.assertEquals("name", metaTag.getName());
        metaTag.toString();
    }
}
