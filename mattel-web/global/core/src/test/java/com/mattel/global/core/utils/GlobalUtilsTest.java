package com.mattel.global.core.utils;

import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.global.core.model.ButtonDetails;

@RunWith(PowerMockRunner.class) @PrepareForTest({
    PropertyReaderUtils.class }) public class GlobalUtilsTest {

    @Mock Resource resource;

    @Mock ResourceResolver resourceResolver;

    @Mock PageManager pageManager;

    @Mock Page page;

    @Mock ValueMap valueMap;

    String url;

    Node node;

    Property property;

    PropertyReaderUtils propertyReaderUtils;

    CloseableHttpClient httpClient;

    @Before public void setup() {
        node = Mockito.mock(Node.class);
        property = Mockito.mock(Property.class);
        PowerMockito.mockStatic(PropertyReaderUtils.class);
        propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
        httpClient = Mockito.mock(CloseableHttpClient.class);
        url = "/content/mattel/corporate-mattel/en/home";
        Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.map(url)).thenReturn(url);
        Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        Mockito.when(pageManager.getPage(url)).thenReturn(page);

    }

    @SuppressWarnings("unchecked") @Test public void testButtonDetails() {
        Resource buttonList = Mockito.mock(Resource.class);
        Iterator<Resource> childList = Mockito.mock(Iterator.class);
        Mockito.when(buttonList.listChildren()).thenReturn(childList);
        Mockito.when(childList.hasNext()).thenReturn(true, false);
        Resource bd = Mockito.mock(Resource.class);
        Mockito.when(childList.next()).thenReturn(bd);
        ButtonDetails buttonDetail = Mockito.mock(ButtonDetails.class);
        Mockito.when(bd.adaptTo(ButtonDetails.class)).thenReturn(buttonDetail);
        buttonDetail.setCtaLink("/corporate-mattel/en/home");
        buttonDetail.setLink("/corporate-mattel/en/home");
        buttonDetail.setLinkOptions("_blank");
        Mockito.when(buttonDetail.getCtaLink()).thenReturn("/corporate-mattel/en/home");
        Mockito.when(buttonDetail.getLink()).thenReturn("/corporate-mattel/en/home");
        Mockito.when(buttonDetail.getLinkOptions()).thenReturn("_blank");
        GlobalUtils.buttonDetails(buttonList, resource);
    }

    @Test public void textCheckPropertyObject() {
        Object propObj = Mockito.mock(Object.class);
        Mockito.when(propObj.toString()).thenReturn("testprop");
        GlobalUtils.checkPropertyObject(propObj);
    }

    @Test public void testCheckBooleanProperty() {
        ValueMap pageProp = Mockito.mock(ValueMap.class);
        Mockito.when(page.getProperties()).thenReturn(pageProp);
        Mockito.when(pageProp.get("testprop", Boolean.class)).thenReturn(true);
        GlobalUtils.checkBooleanProperty(page, "testprop", true);
    }

    @Test public void testGetParsedDate() {
        GlobalUtils.getParsedDate("yyyy.MM.dd.HH.mm.ss", "05/29/2015 05:50:06");
    }

    /*
     * @SuppressWarnings("unchecked")
     *
     * @Test public void testGetCtaURL(){
     * Mockito.when(resource.hasChildren()).thenReturn(true,false);
     * Iterator<Resource> childResources = Mockito.mock(Iterator.class);
     * Mockito.when(resource.listChildren()).thenReturn(childResources);
     * GlobalUtils.getCtaURL(resource); }
     */

    @SuppressWarnings("unchecked") @Test public void testSsFirstCTA() {
        Resource parentResource = Mockito.mock(Resource.class);
        Resource mainResource = Mockito.mock(Resource.class);
        Mockito.when(parentResource.hasChildren()).thenReturn(true, false);
        Iterator<Resource> childResources = Mockito.mock(Iterator.class);
        Mockito.when(parentResource.listChildren()).thenReturn(childResources);
        GlobalUtils.isFirstCTA(parentResource, mainResource);
    }

    @SuppressWarnings("unchecked") @Test public void testIsOnlyCTA() {
        Mockito.when(resource.hasChildren()).thenReturn(true);
        Iterator<Resource> childList = Mockito.mock(Iterator.class);
        Mockito.when(resource.listChildren()).thenReturn(childList);
        Resource child = Mockito.mock(Resource.class);
        Mockito.when(childList.hasNext()).thenReturn(true, false);
        Mockito.when(childList.next()).thenReturn(child);
        Mockito.when(child.getResourceType())
            .thenReturn("mattel/ecomm/fisher-price/components/content/ctaItem");
        GlobalUtils.isOnlyCTA(resource);
    }

    @Test public void testSetBackgroundPath() {
        Resource imageResource = Mockito.mock(Resource.class);
        Mockito.when(resourceResolver.resolve("imagePath/jcr:content/renditions/original"))
            .thenReturn(imageResource);
        Mockito.when(imageResource.getPath()).thenReturn("backgroundpath");
        GlobalUtils.setBackgroundPath(resourceResolver, "imagePath");
    }

    @SuppressWarnings("unchecked") @Test public void testGetInterstitialDetails() {
        Mockito.when(resource.getValueMap()).thenReturn(valueMap);
        Mockito.when(valueMap.get("interstitialType", StringUtils.EMPTY))
            .thenReturn("INTERSTITIALMODAL");
        Mockito.when(valueMap.get("interstitialSelection", StringUtils.EMPTY))
            .thenReturn("interstitialSelection");
        Resource interstitialResource = Mockito.mock(Resource.class);
        Mockito.when(resourceResolver.getResource("/jcr:content/root"))
            .thenReturn(interstitialResource);
        Mockito.when(interstitialResource.hasChildren()).thenReturn(true, false);
        Iterator<Resource> interstitialChildList = Mockito.mock(Iterator.class);
        Mockito.when(interstitialResource.listChildren()).thenReturn(interstitialChildList);
        GlobalUtils.getInterstitialDetails(resource);
    }

    @Test public void testGetTagDetails() {
        TagManager tagManager = Mockito.mock(TagManager.class);
        Tag tag = Mockito.mock(Tag.class);
        Tag parentTag = Mockito.mock(Tag.class);
        Mockito.when(tagManager.resolve(Mockito.anyString())).thenReturn(tag);
        Mockito.when(tag.getParent()).thenReturn(parentTag);
        Mockito.when(parentTag.getTitle()).thenReturn("parentTag");
        Mockito.when(tag.getTagID()).thenReturn("tagId");
        Mockito.when(tag.getName()).thenReturn("tagName");
        Mockito.when(tag.getTitle()).thenReturn("tagTitle");
        GlobalUtils.getTagDetails(tagManager, "testTag");
    }

    public void textCheckForProperty() throws RepositoryException {
        Node node = Mockito.mock(Node.class);
        Mockito.when(node.hasProperty("property")).thenReturn(true);
        Property prop = Mockito.mock(Property.class);
        Mockito.when(node.getProperty("property")).thenReturn(prop);
        Mockito.when(prop.getString()).thenReturn("prop-value");
        GlobalUtils.checkForProperty(node, "property");
    }

    @Test public void testGetUrl() {
        GlobalUtils.getUrl("linkURL", "linkAltText", resource);
    }

    @Test public void testGetUrlWithLinkURL() {
        GlobalUtils.getUrl("linkURL", resource);
    }

    @Test public void testGetValueMapNodeValue() {
        Mockito.when(valueMap.containsKey("properties")).thenReturn(true);
        Mockito.when(valueMap.get("properties", String.class)).thenReturn("propvalue");
        GlobalUtils.getValueMapNodeValue(valueMap, "properties");
    }

    @Test public void testGetValueMapNodeValueBoolean() {
        Mockito.when(valueMap.containsKey("testkey")).thenReturn(true);
        Mockito.when(valueMap.get("testkey", Boolean.class)).thenReturn(true);
        Assert.assertTrue(GlobalUtils.getValueMapNodeValue(valueMap, "testkey", false));
    }

    @Test public void testGetValueMapNodeValueBooleanKeyNotPresent() {
        Mockito.when(valueMap.containsKey("testkey")).thenReturn(false);
        Assert.assertTrue(GlobalUtils.getValueMapNodeValue(valueMap, "testkey", true));
    }

    @Test public void testRemoveTags() {
        String[] searchList = { "<p>", "</p>" };
        String[] replacementList = { StringUtils.EMPTY, StringUtils.EMPTY };
        GlobalUtils.removeTags("text", searchList, replacementList);
    }

    @Test public void testURLWithVanity() {
        GlobalUtils.checkLink("/content/corporate-mattel/en/home#123", resource);
    }

    @Test public void testCheckForProperty() throws RepositoryException {
        Mockito.when(node.hasProperty("property")).thenReturn(true, false);
        Mockito.when(node.getProperty("property")).thenReturn(property);
        GlobalUtils.checkForProperty(node, "property");
    }

}
