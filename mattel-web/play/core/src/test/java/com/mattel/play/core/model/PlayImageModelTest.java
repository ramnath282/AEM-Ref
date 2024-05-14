package com.mattel.play.core.model;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.play.core.pojos.TilePojo;

public class PlayImageModelTest {

	PlayImageModel playImageModel;
	Resource resource;
	TilePojo tilePojo = new TilePojo();
	ResourceResolver resolver;
	String detailpageMapping = "";
	Node detailNode;
	Property property;
	
	
	@Before
	public void setUp() throws RepositoryException {
		playImageModel = new PlayImageModel();
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		detailNode = Mockito.mock(Node.class);
		property = Mockito.mock(Property.class);
		playImageModel.setResource(resource);
		playImageModel.setTilePojo(tilePojo);
		playImageModel.setDetailpageMapping(detailpageMapping);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(detailpageMapping + com.mattel.play.core.constants.Constants.JCR_CONTENT)).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(detailNode);
		Mockito.when(detailNode.getProperty("")).thenReturn(property);
		Mockito.when(detailNode.getProperty("").toString()).thenReturn(null);
		Mockito.when(detailNode.getProperty("jcr:title")).thenReturn(property);
		Mockito.when(detailNode.getProperty("jcr:title").toString()).thenReturn(detailpageMapping);
		Mockito.when(resolver.getResource(detailpageMapping + com.mattel.play.core.constants.Constants.JCR_CONTENT_ROOT)).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(detailNode);
		Mockito.when(detailNode.hasNode("")).thenReturn(true);
		Mockito.when(detailNode.getNode("")).thenReturn(detailNode);
		//Mockito.when(detailNode.hasNode("character")).thenReturn(true);
		Mockito.when(detailNode.getNode("character")).thenReturn(detailNode);
		Mockito.when(detailNode.getParent()).thenReturn(detailNode);
		Mockito.when(detailNode.hasNode("game")).thenReturn(true);
		Mockito.when(detailNode.getNode("game")).thenReturn(detailNode);
		Mockito.when(detailNode.getProperty("tileThumbnail")).thenReturn(property);
		Mockito.when(detailNode.getProperty("tileThumbnail").getString()).thenReturn(detailpageMapping);
		Mockito.when(detailNode.getProperty("tileAltTxt")).thenReturn(property);
		Mockito.when(detailNode.getProperty("tileAltTxt").getString()).thenReturn(detailpageMapping);
		
		
	}
	
	
	
	@Test
	public void init() throws RepositoryException {
		Mockito.when(detailNode.hasNode("character")).thenReturn(true);
		playImageModel.init();
	}
	@Test
	public void initwhencharacterfalse() throws RepositoryException {
		Mockito.when(detailNode.hasNode("")).thenReturn(true);
		playImageModel.init();
	}
	@Test
	public void getTilePojo() {
		playImageModel.getTilePojo();
	}
	@Test
	public void getTileThumbnail() {
		playImageModel.getTileThumbnail();
	}
	@Test
	public void setTileThumbnail() {
		playImageModel.setTileThumbnail("");
	}
	@Test
	public void getTileAltTxt() {
		playImageModel.getTileAltTxt();
	}
	@Test
	public void setTileAltTxt() {
		playImageModel.setTileAltTxt(detailpageMapping);
	}
	
}
