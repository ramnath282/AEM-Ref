package com.mattel.productvideos.core.pojos;

import javax.jcr.RepositoryException;

import org.junit.Test;

public class ResultTest {
    
    
    Result res;
    
    @Test
    public void test() throws RepositoryException {
        res = new Result("title", "ImageURL", "VideoURL", "description", "publish_date", "featured_video","videoDuration","videoId");
    }
}
