package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentDetailsPojoTest {
  AttachmentDetailsPojo attachmentDetailsPojo;

  @Before
  public void setup() {
    attachmentDetailsPojo = new AttachmentDetailsPojo();
    attachmentDetailsPojo.setAttachmentDescription("description");
    attachmentDetailsPojo.setAttachmentPdf("pdf");
    attachmentDetailsPojo.setAttachmentTitle("title");
    attachmentDetailsPojo.setAttachmentUrl("url");
    attachmentDetailsPojo.setAttachmentUrlMid("urlMid");
    attachmentDetailsPojo.setAttachmentUrlPrv("preview");
    attachmentDetailsPojo.setAttachmentUrlS("small");
    attachmentDetailsPojo.setAttachmentUrlThmb("thumb");
  }

  @Test
  public void testToVerifyAttachmentDatailPojo() {
    attachmentDetailsPojo.toString();
    Assert.assertEquals("description", attachmentDetailsPojo.getAttachmentDescription());
    Assert.assertEquals("pdf", attachmentDetailsPojo.getAttachmentPdf());
    Assert.assertEquals("title", attachmentDetailsPojo.getAttachmentTitle());
    Assert.assertEquals("url", attachmentDetailsPojo.getAttachmentUrl());
    Assert.assertEquals("urlMid", attachmentDetailsPojo.getAttachmentUrlMid());
    Assert.assertEquals("preview", attachmentDetailsPojo.getAttachmentUrlPrv());
    Assert.assertEquals("small", attachmentDetailsPojo.getAttachmentUrlS());
    Assert.assertEquals("thumb", attachmentDetailsPojo.getAttachmentUrlThmb());
  }

}
