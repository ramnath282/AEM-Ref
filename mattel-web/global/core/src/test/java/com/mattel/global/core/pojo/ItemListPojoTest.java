package com.mattel.global.core.pojo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemListPojoTest {
  ItemListPojo itemListPojo;

  @Before
  public void setup() {
    itemListPojo = new ItemListPojo();
    itemListPojo.setAttachmentDetails(new ArrayList<AttachmentDetailsPojo>());
    itemListPojo.setAttachmentZip("attachmentZip");
    itemListPojo.setContent("content");
    itemListPojo.setDescription("description");
    itemListPojo.setImageUrl("imageUrl");
    itemListPojo.setLink("link");
    itemListPojo.setMediaContent("mediaContent");
    itemListPojo.setPubDate("pubDate");
    itemListPojo.setSubtitle("subTitle");
    itemListPojo.setTitle("title");
  }

  @Test
  public void testToVerifyItemListPojo() {
    itemListPojo.toString();
    Assert.assertEquals("description", itemListPojo.getDescription());
    Assert.assertNotNull(itemListPojo.getAttachmentDetails());
    Assert.assertEquals("attachmentZip", itemListPojo.getAttachmentZip());
    Assert.assertEquals("content", itemListPojo.getContent());
    Assert.assertEquals("imageUrl", itemListPojo.getImageUrl());
    Assert.assertEquals("link", itemListPojo.getLink());
    Assert.assertEquals("mediaContent", itemListPojo.getMediaContent());
    Assert.assertEquals("subTitle", itemListPojo.getSubtitle());
    Assert.assertEquals("title", itemListPojo.getTitle());
    Assert.assertEquals("pubDate", itemListPojo.getPubDate());

  }

}
