package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.DownloadAppPojo;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class DownloadAppModelTest {
	@InjectMocks
	private DownloadAppModel downloadAppModel;
	@Mock
	private Resource resource;
	MultifieldReader multifieldReader;
	Node downloadImage;
	Map<String, ValueMap> multifieldProperty;
	ValueMap fieldMap;
	Map.Entry<String, ValueMap> entry;
	List<DownloadAppPojo> downloadImageGalleryList = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IllegalAccessException {
		multifieldReader = Mockito.mock(MultifieldReader.class);
		downloadImage = Mockito.mock(Node.class);
		fieldMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Entry.class);
		downloadAppModel.setDownloadImageGalleryList(downloadImageGalleryList);
		downloadAppModel.setDownloadImage(downloadImage);
		downloadAppModel.setMultifieldReader(multifieldReader);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("download", fieldMap);
		entry.setValue(fieldMap);
		MemberModifier.field(DownloadAppModel.class, "resource").set(downloadAppModel, resource);
		Mockito.when(resource.getPath()).thenReturn("/content/fisher-price/gb/en-gb/home");
		Mockito.when(multifieldReader.propertyReader(downloadImage)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(fieldMap);
		Mockito.when(entry.getValue().get("thumbnailImage", String.class)).thenReturn("/thumbnail.jpg");

	}

	@Test
	public void init() {
		downloadAppModel.init();
	}

	@Test
	public void getDownloadImageGalleryList() {
		downloadAppModel.getDownloadImageGalleryList();
	}
}
