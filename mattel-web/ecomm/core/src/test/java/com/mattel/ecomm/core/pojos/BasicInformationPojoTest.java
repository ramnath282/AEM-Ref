package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.tagging.Tag;


@RunWith(MockitoJUnitRunner.class)
public class BasicInformationPojoTest {

	BasicInformationPojo basicInformationPojo = null;

	@Test
	public void testGettersSetters() {
		Tag testTag = Mockito.mock(Tag.class);
		List<Tag> testList = new ArrayList<Tag>();
		testList.add(testTag);
		basicInformationPojo = new BasicInformationPojo();
		basicInformationPojo.setDollImage("dollImage");
		basicInformationPojo.setDollDescription("dollDescription");
		basicInformationPojo.setDollsCategories(testList);
		basicInformationPojo.getDollsCategories();
		basicInformationPojo.toString();
		Assert.assertEquals("dollImage", basicInformationPojo.getDollImage());
		Assert.assertEquals("dollDescription", basicInformationPojo.getDollDescription());
	}
}
