package com.mattel.productvideos.core.utils;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.productvideos.core.constants.Constants;

@RunWith(PowerMockRunner.class)
public class ProductVideosUtilTest {

	@InjectMocks
	ProductVideosUtil productVideosUtil;

	@Mock
	Node node;

	@Test
	public void testGetPropertyValue() throws RepositoryException {
		Mockito.when(node.hasProperty("propertyName")).thenReturn(true);
		Property property = Mockito.mock(Property.class);
		Mockito.when(node.getProperty("propertyName")).thenReturn(property);
		Value propValue = Mockito.mock(Value.class);
		Mockito.when(property.getValue()).thenReturn(propValue);
		Mockito.when(propValue.toString()).thenReturn("propertyValue");
		productVideosUtil.getPropertyValue(node, "propertyName");
	}

// Changes

	@Test(expected = NullPointerException.class)
	public void test_NullException_When_NodeProperty_Is_Null() throws RepositoryException {
		Mockito.when(node.hasProperty(null)).thenReturn(true);
		productVideosUtil.getPropertyValue(null, "propertyName");
	}

	@Test
	public void test_RepositoryException() throws RepositoryException {
		Mockito.when(node.hasNode(null)).thenThrow(RepositoryException.class);
		productVideosUtil.getPropertyValue(node, "propertyName");
	}

	// A Negative Test Case
	@Test
	public void test_areSecene7PropsPresent() throws RepositoryException {
		Node assetNode = Mockito.mock(Node.class);
		Property property = Mockito.mock(Property.class);
		Mockito.when(assetNode.hasProperty(Constants.DYNAMIC_MEDIA_FILE)).thenReturn(true);
		Mockito.when(assetNode.hasProperty(Constants.DYNAMIC_MEDIA_DOMAIN)).thenReturn(true);
		Mockito.when(assetNode.hasProperty(Constants.DYNAMIC_MEDIA_TYPE)).thenReturn(true);
		Mockito.when(assetNode.hasProperty(Constants.DYNAMIC_MEDIA_FILE_NAME)).thenReturn(true);
		Mockito.when(assetNode.hasProperty(Constants.DYNAMIC_MEDIA_FOLDER)).thenReturn(false);
		String assetScene7URL = productVideosUtil.getAssetScene7URL(assetNode);
		Assert.assertEquals("", assetScene7URL);
		//System.out.println("assetScene7URL: " + assetScene7URL);
	}

	// To check for RepositoryException and Other nested ifs
}