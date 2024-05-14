package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.dam.api.Asset;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class DownloadDocumentsModelTest {

	@InjectMocks
	private DownloadDocumentsModel downloadDocumentsModel;
	@Mock
	private Resource resource;
	@Mock
	private ResourceResolver resolver;
	Node documentDetailList;
	MultifieldReader multifieldReader;
	Map<String, ValueMap> multifieldProperty;
	ValueMap valueMap;
	Map.Entry<String, ValueMap> entry;
	String docLink = "documentLink";
	Asset document;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		MemberModifier.field(DownloadDocumentsModel.class, "resource").set(downloadDocumentsModel, resource);
		documentDetailList = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Entry.class);
		document = Mockito.mock(Asset.class);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("document", valueMap);
		multifieldProperty.put("document", valueMap);
		multifieldProperty.put("document", valueMap);
		downloadDocumentsModel.setDocumentDetailList(documentDetailList);
		downloadDocumentsModel.setMultifieldReader(multifieldReader);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(multifieldReader.propertyReader(documentDetailList)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue().get("documentTitle", String.class)).thenReturn("title");
		Mockito.when(entry.getValue().get("documentLink", String.class)).thenReturn(docLink);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(docLink)).thenReturn(resource);
		Mockito.when(resource.adaptTo(Asset.class)).thenReturn(document);
		Mockito.when(document.getMetadataValue("dc:format")).thenReturn("application/pdf");
		Mockito.when(document.getMetadataValue("dc:format").split("/")[1].toUpperCase()).thenReturn("application/pdf");
		Mockito.when(document.getMetadataValue("dam:size")).thenReturn("2667007");
	}

	@Test
	public void init() {
		downloadDocumentsModel.init();
	}

	@Test
	public void getDownloadDocumentList() {
		downloadDocumentsModel.getDownloadDocumentList();
	}
}
