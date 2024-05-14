package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TagPojoTest {
    private TagPojo tagPojo = null;
    
    @Before
	public void setUp() throws Exception {
    	tagPojo = new TagPojo();
    	tagPojo.setParent("parent");
    	tagPojo.setTagID("1001");
    	tagPojo.setTagTitle("title");
    	tagPojo.setTagValue("TagValue");
	}

	@Test
	public void testForParent() {
		Assert.assertEquals("parent", tagPojo.getParent());
	}
	
	@Test
	public void testForTagID() {
		Assert.assertEquals("1001", tagPojo.getTagID());
	}
	
	@Test
	public void testForTagTitle() {
		Assert.assertEquals("title", tagPojo.getTagTitle());
	}
	
	@Test
	public void testForTagValue() {
		Assert.assertEquals("TagValue", tagPojo.getTagValue());
	}

}
