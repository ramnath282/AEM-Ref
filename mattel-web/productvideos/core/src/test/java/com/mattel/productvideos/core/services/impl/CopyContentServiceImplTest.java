package com.mattel.productvideos.core.services.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.i18n.ResourceBundleProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.mattel.productvideos.core.constants.Constants;

@RunWith(MockitoJUnitRunner.class)
public class CopyContentServiceImplTest {

	@InjectMocks
	CopyContentServiceImpl copyContentServiceImpl;

	@Mock
	MultifieldReader multifieldReader;

	@Mock
	ResourceBundleProvider resourceBundleProvider;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Session session;

	String jwRootPath = "/content/mobile-apps/JurassicWorldFacts";

	@Test
	public void testCopyContentForAsset() throws RepositoryException {
		Resource jwRootResource = Mockito.mock(Resource.class);
		String payLoadPath = "/content/dam/digital-fragments/jurassic-world/test-cf-folder/test-cf-folder";
		String payloadType = "asset";
		Mockito.when(resourceResolver.resolve("/content/mobile-apps/JurassicWorldFacts/jcr:content/localeList"))
				.thenReturn(jwRootResource);
		Mockito.when(jwRootResource.getPath())
				.thenReturn("/content/mobile-apps/JurassicWorldFacts/jcr:content/localeList");
		Node node = Mockito.mock(Node.class);
		Mockito.when(jwRootResource.adaptTo(Node.class)).thenReturn(node);

		LinkedHashMap<String, ValueMap> localeMap = new LinkedHashMap<>();

		ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
		valueMap.put("locale", "de");
		localeMap.put("item0", valueMap);

		Mockito.when(multifieldReader.propertyReader(node, resourceResolver)).thenReturn(localeMap);
		Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

		Resource payloadPageResource = Mockito.mock(Resource.class);
		Mockito.when(resourceResolver.resolve("/content/dam/digital-fragments/jurassic-world/test-cf-folder"))
				.thenReturn(payloadPageResource);
		Node payloadParentNode = Mockito.mock(Node.class);
		Mockito.when(payloadPageResource.adaptTo(Node.class)).thenReturn(payloadParentNode);
		Mockito.when(payloadParentNode.hasNode("de")).thenReturn(false);
		Node localeFolder = Mockito.mock(Node.class);
		Mockito.when(payloadParentNode.addNode("de", Constants.SLING_FOLDER)).thenReturn(localeFolder);
		Node jcrContentFolder = Mockito.mock(Node.class);
		;
		Mockito.when(localeFolder.addNode("jcr:content", JcrConstants.NT_UNSTRUCTURED)).thenReturn(jcrContentFolder);
		Workspace workspace = Mockito.mock(Workspace.class);
		Mockito.when(session.getWorkspace()).thenReturn(workspace);
		Resource masterNodeResource = Mockito.mock(Resource.class);
		Mockito.when(resourceResolver.resolve(
				"/content/dam/digital-fragments/jurassic-world/test-cf-folder/de/test-cf-folder/jcr:content/data/master"))
				.thenReturn(masterNodeResource);
		Node masterNode = Mockito.mock(Node.class);
		Mockito.when(masterNodeResource.adaptTo(Node.class)).thenReturn(masterNode);
		Property property = Mockito.mock(Property.class);
		Value propertyValue = Mockito.mock(Value.class);
		Mockito.when(masterNode.getProperty("description")).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(propertyValue);
		Mockito.when(propertyValue.getString()).thenReturn("Test");
		copyContentServiceImpl.copyContent(jwRootPath, payLoadPath, payloadType, resourceResolver);
	}

	@Test
	public void testCopyContentForPage() throws RepositoryException {
		Resource jwRootResource = Mockito.mock(Resource.class);
		String payloadType = "content";
		String payLoadPath = "/content/mobile-apps/JurassicWorldFacts/language_masters/en/gallery/9999";
		Mockito.when(resourceResolver.resolve("/content/mobile-apps/JurassicWorldFacts/jcr:content/localeList"))
				.thenReturn(jwRootResource);
		Mockito.when(jwRootResource.getPath())
				.thenReturn("/content/mobile-apps/JurassicWorldFacts/jcr:content/localeList");
		Node node = Mockito.mock(Node.class);
		Mockito.when(jwRootResource.adaptTo(Node.class)).thenReturn(node);

		LinkedHashMap<String, ValueMap> localeMap = new LinkedHashMap<>();

		ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
		valueMap.put("locale", "de");
		localeMap.put("item0", valueMap);

		Mockito.when(multifieldReader.propertyReader(node, resourceResolver)).thenReturn(localeMap);
		Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

		Resource payloadPageResource = Mockito.mock(Resource.class);
		Mockito.when(resourceResolver.resolve("/content/mobile-apps/JurassicWorldFacts/language_masters/en/gallery"))
				.thenReturn(payloadPageResource);
		Page pageParent = Mockito.mock(Page.class);
		Mockito.when(payloadPageResource.adaptTo(Page.class)).thenReturn(pageParent);
		Page absoluteParent = Mockito.mock(Page.class);
		Mockito.when(pageParent.getAbsoluteParent(3)).thenReturn(absoluteParent);
		Mockito.when(pageParent.getName()).thenReturn("gallery");
		Mockito.when(absoluteParent.hasChild("de")).thenReturn(true);
		Mockito.when(absoluteParent.getPath()).thenReturn("/content/mobile-apps/JurassicWorldFacts/language_masters");
		Workspace workspace = Mockito.mock(Workspace.class);
		Mockito.when(session.getWorkspace()).thenReturn(workspace);
		Resource videoComponentResource = Mockito.mock(Resource.class);
		Mockito.when(resourceResolver.resolve(
				"/content/mobile-apps/JurassicWorldFacts/language_masters/de/gallery/9999/jcr:content/root/videocomponentapi"))
				.thenReturn(videoComponentResource);
		Node videoComponentNode = Mockito.mock(Node.class);
		Mockito.when(videoComponentResource.adaptTo(Node.class)).thenReturn(videoComponentNode);
		Property property = Mockito.mock(Property.class);
		Value propertyValue = Mockito.mock(Value.class);
		Mockito.when(videoComponentNode.hasProperty("fragmentPath")).thenReturn(true);
		Mockito.when(videoComponentNode.getProperty("fragmentPath")).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(propertyValue);
		Mockito.when(propertyValue.getString()).thenReturn("/content/dam/digital-fragments/jurassic-world/dinosaur-cards/test-fragment/test-fragment");

		copyContentServiceImpl.copyContent(jwRootPath, payLoadPath, payloadType, resourceResolver);
	}

}
