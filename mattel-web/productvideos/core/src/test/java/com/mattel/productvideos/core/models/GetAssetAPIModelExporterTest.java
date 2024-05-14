package com.mattel.productvideos.core.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.utils.ProductVideosUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProductVideosUtil.class })
public class GetAssetAPIModelExporterTest {

	@InjectMocks
	GetAssetAPIModelExporter getAssetAPIModelExporter;

	@Mock
	Resource resource;

	@Mock
	QueryBuilder queryBuilder;

	@Mock
	ResourceResolver resourceResolver;

	String title = "title";
	String pageTitle = "pagetitle";
	String items = "items";

	@Before
	public void test() throws IllegalArgumentException, IllegalAccessException, RepositoryException {
		PowerMockito.mockStatic(ProductVideosUtil.class);
		MemberModifier.field(GetAssetAPIModelExporter.class, "title").set(getAssetAPIModelExporter, title);
		MemberModifier.field(GetAssetAPIModelExporter.class, "items").set(getAssetAPIModelExporter, items);

		PageManager pageManager = Mockito.mock(PageManager.class);
		Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Page page = Mockito.mock(Page.class);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Session session = Mockito.mock(Session.class);
		Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(page.getPath()).thenReturn("/testpath");
		Mockito.when(page.isValid()).thenReturn(true);

		Query query = Mockito.mock(Query.class);

		Map<String, String> map = new HashMap<>();
		map.put("path", page.getPath());
		map.put("property", Constants.SLING_RESOURCE_TYPE);
		map.put("Property.value", Constants.SLING_RESOURCE_TYPE_VALUE);

		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class)))
				.thenReturn(query);
		SearchResult result = Mockito.mock(SearchResult.class);
		Mockito.when(query.getResult()).thenReturn(result);
		Iterator<Node> nodeItr = Mockito.mock(Iterator.class);
		Mockito.when(result.getNodes()).thenReturn(nodeItr);
		Mockito.when(nodeItr.hasNext()).thenReturn(true, false);
		Node node = Mockito.mock(Node.class);
		Mockito.when(nodeItr.next()).thenReturn(node);
		Mockito.when(node.hasProperty("fragmentPath")).thenReturn(true);
		Property property = Mockito.mock(Property.class);
		Mockito.when(node.getProperty("fragmentPath")).thenReturn(property);
		Value value = Mockito.mock(Value.class);
		Mockito.when(property.getValue()).thenReturn(value);
		String path = "/content/dam/digital-fragments/jurassic-world/9999";
		Mockito.when(value.getString()).thenReturn(path);
		Resource fragmentResource = Mockito.mock(Resource.class);
		Mockito.when(resourceResolver.getResource(path)).thenReturn(fragmentResource);
		ContentFragment fragment = Mockito.mock(ContentFragment.class);
		
		Mockito.when(fragmentResource.adaptTo(ContentFragment.class)).thenReturn(fragment);
		Node rootNode = Mockito.mock(Node.class);
		Mockito.when(session.getRootNode()).thenReturn(rootNode);
		
		ContentElement thumbnailElement = Mockito.mock(ContentElement.class);
		Mockito.when(fragment.getElement("thumbnailImage")).thenReturn(thumbnailElement);
		Mockito.when(thumbnailElement.getContent()).thenReturn("/thumbnailImage_value");
		Mockito.when(rootNode.hasNode("thumbnailImage_value/jcr:content/metadata")).thenReturn(true);
		Node thumnailImageNode = Mockito.mock(Node.class);
		Mockito.when(rootNode.getNode("thumbnailImage_value/jcr:content/metadata")).thenReturn(thumnailImageNode);
		Mockito.when(thumnailImageNode.getPath()).thenReturn("thumbnailImage_value/jcr:content/metadata");

		Mockito.when(thumnailImageNode.hasProperty("dam:scene7File")).thenReturn(true);
		Mockito.when(thumnailImageNode.hasProperty("dam:scene7Domain")).thenReturn(true);
		Mockito.when(thumnailImageNode.hasProperty("dam:scene7Type")).thenReturn(true);
		Mockito.when(thumnailImageNode.hasProperty("dam:scene7Name")).thenReturn(true);
		Mockito.when(thumnailImageNode.hasProperty("dam:scene7Folder")).thenReturn(true);
		Mockito.when(thumnailImageNode.hasProperty("cq:name")).thenReturn(true);

		Mockito.when(thumnailImageNode.getName()).thenReturn("Thumbnail Image Name");
		Mockito.when(ProductVideosUtil.getPropertyValue(thumnailImageNode,Constants.DYNAMIC_MEDIA_FILE)).thenReturn("");
		Mockito.when(ProductVideosUtil.getPropertyValue(thumnailImageNode,Constants.DYNAMIC_MEDIA_DOMAIN)).thenReturn("");
		Mockito.when(ProductVideosUtil.getPropertyValue(thumnailImageNode,Constants.DYNAMIC_MEDIA_FOLDER)).thenReturn("");
		Property scene7TypeProperty = Mockito.mock(Property.class);
		Mockito.when(thumnailImageNode.getProperty("dam:scene7Type")).thenReturn(scene7TypeProperty);
		Value thumnailImageNodeValue = Mockito.mock(Value.class);
		Mockito.when(scene7TypeProperty.getValue()).thenReturn(thumnailImageNodeValue);
		Mockito.when(thumnailImageNodeValue.toString()).thenReturn("Image");
		
		ContentElement videoElement = Mockito.mock(ContentElement.class);
		Mockito.when(fragment.getElement("video")).thenReturn(videoElement);
		Mockito.when(videoElement.getContent()).thenReturn("/video_path");
		
		Mockito.when(rootNode.hasNode("video_path/jcr:content/metadata")).thenReturn(true);
		Node videoNode = Mockito.mock(Node.class);
		Mockito.when(rootNode.getNode("video_path/jcr:content/metadata")).thenReturn(videoNode);
		Mockito.when(videoNode.getPath()).thenReturn("video_path/jcr:content/metadata");
		Node videoAsset = Mockito.mock(Node.class);
		Mockito.when(rootNode.getNode("video_path/jcr:content/metadata")).thenReturn(videoAsset);
		Mockito.when(videoAsset.getPath()).thenReturn("videoassetpath");
		Mockito.when(videoAsset.hasProperty("dc:format")).thenReturn(true);
		Property formatProperty = Mockito.mock(Property.class);
		Mockito.when(videoAsset.getProperty("dc:format")).thenReturn(formatProperty);
		Value formatPropertyValue = Mockito.mock(Value.class);
		Mockito.when(formatProperty.getValue()).thenReturn(formatPropertyValue);
		Mockito.when(formatPropertyValue.toString()).thenReturn("video");
		
		Mockito.when(videoAsset.hasProperty(Constants.CONTENT_NAME)).thenReturn(true);
		Property contentNameProperty = Mockito.mock(Property.class);
		Mockito.when(videoAsset.getProperty(Constants.CONTENT_NAME)).thenReturn(contentNameProperty);
		Value val1 = Mockito.mock(Value.class);
		Value[] propValues = {val1};
		Mockito.when(contentNameProperty.getValues()).thenReturn(propValues);
		
		
		Mockito.when(videoAsset.hasProperty(Constants.BRAND)).thenReturn(true);
		Property brandProperty= Mockito.mock(Property.class);
		Mockito.when(videoAsset.getProperty(Constants.BRAND)).thenReturn(brandProperty);
		Value brandPropertyValue = Mockito.mock(Value.class);
		Mockito.when(brandProperty.getValue()).thenReturn(brandPropertyValue);
		Mockito.when(brandPropertyValue.toString()).thenReturn("jurassic");
		
		Mockito.when(videoAsset.hasProperty(Constants.COLLECTION_NAME)).thenReturn(true);
		Property collectionNameProperty = Mockito.mock(Property.class);
		Mockito.when(videoAsset.getProperty(Constants.COLLECTION_NAME)).thenReturn(collectionNameProperty);
		Value collectionVal1 = Mockito.mock(Value.class);
		Value[] collectionPropertyVals = {collectionVal1};
		Mockito.when(collectionNameProperty.getValues()).thenReturn(collectionPropertyVals);
		
		Mockito.when(ProductVideosUtil.getPropertyValue(videoAsset,Constants.VIDEO_DURATION)).thenReturn("90ms");
		Mockito.when(ProductVideosUtil.getPropertyValue(videoAsset,Constants.CONTENT_ID)).thenReturn("1234");
		
		ContentElement descriptionElement = Mockito.mock(ContentElement.class);
		Mockito.when(fragment.getElement(Constants.DESCRIPTION)).thenReturn(descriptionElement);
		Mockito.when(descriptionElement.getName()).thenReturn("name");
		Mockito.when(descriptionElement.getContent()).thenReturn("content");
		Mockito.when(descriptionElement.getContentType()).thenReturn("content-type");
	}

	@Test
	public void testGetIteams() {
		getAssetAPIModelExporter.getitems();
	}

}
