package com.mattel.global.core.services;

import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.xml.stream.XMLStreamWriter;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.global.core.exceptions.NodeWriterException;

public class CorporateXmlNodeWriterServiceTest {

    CorporateXmlNodeWriterService corporateXmlNodeWriterService;
    ResourceResolver resolver;
    Writer writer;
    Resource resource;
    Resource rootPageRes;
    String path = "rootNewsPagePath";
    Page rootPage;
    Page page;
    Resource cfResource;
    Iterator<Resource> iterator;
    Iterator<Page> children;
    PageManager pageManager;
    PageFilter pageFilter;
    Resource res;
    Resource masterDataRes;
    Node node;
    Node node1;
    ValueMap valueMap;
    TagManager tagManager;
    ResourceResolver resourceResolver;
    String [] tagArray = {"news-1","news-2"};
    Resource res1;
    Date date;
    Tag tag;
    String rootNewsPagePath = "/content/mattel/mattel-corporate/us/en-us/home/news/news-details";
    
     Property p;
     Value[] categoriesData;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws IllegalArgumentException, IllegalAccessException, RepositoryException {

        corporateXmlNodeWriterService = new CorporateXmlNodeWriterService();
        p = Mockito.mock(Property.class);
        tag = Mockito.mock(Tag.class);
        
        resolver = Mockito.mock(ResourceResolver.class);
        writer = Mockito.mock(Writer.class);
        resource = Mockito.mock(Resource.class);
        rootPageRes = Mockito.mock(Resource.class);
        cfResource = Mockito.mock(Resource.class);
        iterator = Mockito.mock(Iterator.class);
        rootPage = Mockito.mock(Page.class);
        page = Mockito.mock(Page.class);
        pageManager = Mockito.mock(PageManager.class);
        pageFilter = Mockito.mock(PageFilter.class);
        children = Mockito.mock(Iterator.class);
        res = Mockito.mock(Resource.class);
        res1 = Mockito.mock(Resource.class);
        masterDataRes = Mockito.mock(Resource.class);
        node=Mockito.mock(Node.class);
        node1=Mockito.mock(Node.class);
        valueMap = Mockito.mock(ValueMap.class);
        tagManager = Mockito.mock(TagManager.class);
        resourceResolver = Mockito.mock(ResourceResolver.class);
        date= Mockito.mock(Date.class);
        Mockito.when(resolver.getResource(path)).thenReturn(resource);
        Mockito.when(resolver.getResource(rootNewsPagePath)).thenReturn(rootPageRes);
        Mockito.when(resource.listChildren()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true,false);
        Mockito.when(iterator.next()).thenReturn(cfResource);
        Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        Mockito.when(pageManager.getContainingPage(rootPageRes)).thenReturn(rootPage);
        Mockito.when(resolver.getResource(cfResource.getPath()+"/jcr:content/data/master")).thenReturn(res1); 
        Mockito.when(res1.adaptTo(Node.class)).thenReturn(node1);
        Mockito.when(rootPage.listChildren(Mockito.any(PageFilter.class),Mockito.anyBoolean())).thenReturn(children);
        Mockito.when(children.hasNext()).thenReturn(true,false);
        Mockito.when(children.next()).thenReturn(page);
        Mockito.when(page.getContentResource()).thenReturn(res);
        Mockito.when(page.getName()).thenReturn("my-page");
        Mockito.when(res.adaptTo(Node.class)).thenReturn(node);
        Mockito.when(node.hasProperty("pubDate")).thenReturn(true);
        Mockito.when(node.getProperty(Mockito.anyString())).thenReturn(p);
        Mockito.when(res.getValueMap()).thenReturn(valueMap);
        Mockito.when(valueMap.get("pubDate",Date.class)).thenReturn(date);
        Mockito.when(valueMap.get("cq:tags",String[].class)).thenReturn(tagArray);
        Mockito.when(res.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
        Mockito.when(res.getResourceType()).thenReturn("mattel/informational/components/structure/corporate-news-article-page");
        Mockito.when(tagManager.resolve(tagArray[0])).thenReturn(tag);
        Mockito.when(tag.getTitle()).thenReturn("news-1"); 
    }

    @Test
    public void testInit() throws NodeWriterException {
        corporateXmlNodeWriterService.readNode(path,rootNewsPagePath,resolver,writer);
    }
    
}
