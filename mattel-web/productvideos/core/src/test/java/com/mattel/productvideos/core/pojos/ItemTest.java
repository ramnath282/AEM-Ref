package com.mattel.productvideos.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ItemTest {

	Item item;
	VideoContent videoContent;
	Metadatum metadatum;
	Elements elements;

	@Before
	public void setup() {
		item = new Item();
		videoContent = new VideoContent();

		elements = Mockito.mock(Elements.class);
		
		metadatum = new Metadatum();
		metadatum.setName("name");
		metadatum.setValue("value");
		List<Metadatum> metadata = new ArrayList<Metadatum>();
		metadata.add(metadatum);

		videoContent.setElements(elements);
		videoContent.setMetadata(metadata);

		item.setVideoContent(videoContent);
	}

	@Test
	public void testGetters() {
		Assert.assertNotNull(item.getVideoContent());
		Assert.assertNotNull(videoContent.getElements());
		Assert.assertNotNull(videoContent.getMetadata());
		Assert.assertNotNull(metadatum.getName());
		Assert.assertNotNull(metadatum.getValue());
	}

}
