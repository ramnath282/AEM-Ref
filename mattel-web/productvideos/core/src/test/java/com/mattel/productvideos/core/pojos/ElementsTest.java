package com.mattel.productvideos.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElementsTest {

	Elements elements;

	PropertyPojo propertyPojo;

	@Before
	public void setup() {
		elements = new Elements();
		propertyPojo = new PropertyPojo();
		propertyPojo.setTitle("title");
		propertyPojo.setType("type");
		propertyPojo.setValue("value");
		elements.setDescription(propertyPojo);
		elements.setThumbNailImage(propertyPojo);
		elements.setVideo(propertyPojo);
		elements.setVideoDuration(propertyPojo);
		elements.setVideoId(propertyPojo);
	}

	@Test
	public void testGetter() {
		Assert.assertNotNull(elements.getDescription());
		Assert.assertNotNull(elements.getThumbNailImage());
		Assert.assertNotNull(elements.getVideo());
		Assert.assertNotNull(elements.getVideoDuration());
		Assert.assertNotNull(elements.getVideoId());
		Assert.assertNotNull(propertyPojo.getTitle());
		Assert.assertNotNull(propertyPojo.getType());
		Assert.assertNotNull(propertyPojo.getValue());
	}
}
