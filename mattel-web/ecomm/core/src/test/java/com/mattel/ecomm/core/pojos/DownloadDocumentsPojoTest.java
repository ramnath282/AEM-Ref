package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DownloadDocumentsPojoTest {
	private DownloadDocumentsPojo downloadDocumentsPojo = null;

	@Before
	public void setUp() throws Exception {
		downloadDocumentsPojo = new DownloadDocumentsPojo();
		downloadDocumentsPojo.setDocumentTitle("documentTitle");
		downloadDocumentsPojo.setDocumentLink("documentLink");
		downloadDocumentsPojo.setDocumentType("documentType");
		downloadDocumentsPojo.setDocumentSize("102KB");
	}

	@Test
	public void getDocumentTitle() {
		Assert.assertEquals("documentTitle", downloadDocumentsPojo.getDocumentTitle());
	}

	@Test
	public void getDocumentLink() {
		Assert.assertEquals("documentLink", downloadDocumentsPojo.getDocumentLink());
	}

	@Test
	public void getDocumentType() {
		Assert.assertEquals("documentType", downloadDocumentsPojo.getDocumentType());
	}

	@Test
	public void getDocumentSize() {
		Assert.assertEquals("102KB", downloadDocumentsPojo.getDocumentSize());
	}
}
