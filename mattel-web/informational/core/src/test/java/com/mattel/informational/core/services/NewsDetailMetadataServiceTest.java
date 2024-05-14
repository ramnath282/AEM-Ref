package com.mattel.informational.core.services;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.LoginException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.informational.core.services.NewsDetailMetadataService.Config;

@RunWith(PowerMockRunner.class)
public class NewsDetailMetadataServiceTest {
  
  @InjectMocks
  NewsDetailMetadataService newsDetailMetadataService;

  @Mock
  ResourceResolver resolver;

  @Mock
  ResourceResolverFactory resolverFactory;

  @Mock
  Resource resource;

  @Mock
  ContentFragment fragment;
  @Mock
  ContentElement titleElement;

  @Mock
  ContentElement contentElement;
  @Mock
  ContentElement enclosureDetailElement;
  @Mock
  TagManager tagMgr;

  @Mock
  FragmentData fragmentData;
  @Mock
  Tag tag;

  @Mock
  Iterator<Tag> tagItr;

  Config config;
  String[] cfPath = { "corp:/content/dam/corp/" };
  private final String[] selectors = {
      "articledetailspage.american-girl-brings-new-in-store-experiences-to-its-flagship-stores-in-new-york-and-chicago-in-time-for-the-holidays" };
  private final String[] attachments = {
      "{\"attachment_title\":\"Salon at American Girl Place Chicago\",\"attachment_description\":\"Girls can now join their favorite dolls for a day of pampering at the newly-renovated Salon at American Girl Place Chicago\",\"attachment_url\":\"https://s3.amazonaws.com/cms.ipressroom.com/236/files/20199/DHS-1-HR.jpg?Signature=gxHyAIom77O2RCCOVWD392rGM8U%3D&Expires=1574862986&AWSAccessKeyId=AKIAJX7XEOOELCYGIVDQ&versionId=w6gMSloxzQWKEskTFh_FBHPEODEUKoeb&response-content-disposition=attachment\",\"attachment_url_thmb\":\"https://s3.amazonaws.com/cms.ipressroom.com/236/files/20199/5d9f4c042cfac254d3e820cb_DHS-1-HR/DHS-1-HR_thmb.jpg\",\"attachment_url_mid\":\"https://s3.amazonaws.com/cms.ipressroom.com/236/files/20199/5d9f4c042cfac254d3e820cb_DHS-1-HR/DHS-1-HR_mid.jpg\",\"attachment_url_prv\":\"https://s3.amazonaws.com/cms.ipressroom.com/236/files/20199/5d9f4c042cfac254d3e820cb_DHS-1-HR/DHS-1-HR_ee340684-eb78-478d-a198-5d36670ce54c-prv.jpg\",\"attachment_url_s\":\"https://s3.amazonaws.com/cms.ipressroom.com/236/files/20199/5d9f4c042cfac254d3e820cb_DHS-1-HR/DHS-1-HR_s.jpg\"}" };
  private static final String READ_WRITE = "readwriteservice";
  Map<String, Object> map = new HashMap<>();
  Map<String, Object> metaMap;

  @Before
  public void setup() throws Exception {
    Object[] objArry = { "corp:categories/AmericanGirlNews", "corp:categories/CorporateNews" };
    metaMap = new HashMap<String, Object>();
    metaMap.put("cq:tags", objArry);
    map.put(ResourceResolverFactory.SUBSERVICE, READ_WRITE);
    config = Mockito.mock(NewsDetailMetadataService.Config.class);
    resolverFactory = Mockito.mock(ResourceResolverFactory.class);
    resolver = Mockito.mock(ResourceResolver.class);
    resource = Mockito.mock(Resource.class);
    when(config.cfPath()).thenReturn(cfPath);
    MemberModifier.field(NewsDetailMetadataService.class, "resolverFactory").set(newsDetailMetadataService,
        resolverFactory);
    when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
    when(resolver.getResource(ArgumentMatchers.anyString())).thenReturn(resource);
    when(resource.adaptTo(ContentFragment.class)).thenReturn(fragment);
    when(fragment.hasElement("attachments")).thenReturn(true);
    when(resolver.adaptTo(TagManager.class)).thenReturn(tagMgr);
    when(tagMgr.resolve(ArgumentMatchers.anyString())).thenReturn(tag);
    when(tag.listChildren()).thenReturn(tagItr);
    when(tagItr.next()).thenReturn(tag);
    when(tag.getName()).thenReturn("name");
    when(tagItr.hasNext()).thenReturn(true).thenReturn(false);
    when(fragment.getElement("title")).thenReturn(titleElement);
    when(fragment.getElement("content")).thenReturn(contentElement);
    when(titleElement.getContent()).thenReturn("title");
    when(contentElement.getContent()).thenReturn("contentElement");
    when(fragment.getMetaData()).thenReturn(metaMap);

  }

  @Test
  public void testToVerifyValidContentFragment() {

    when(fragment.getElement("attachments")).thenReturn(contentElement);
    when(contentElement.getValue()).thenReturn(fragmentData);
    when(fragmentData.getValue(String[].class)).thenReturn(attachments);

    newsDetailMetadataService.activate(config);
    Assert.assertNotNull(newsDetailMetadataService.getFragmentDetails(selectors, "corp"));
  }
  
  @Test
  public void testToVerifyValidContentFragment_1() {

    when(fragment.getElement("attachments")).thenReturn(contentElement);
    when(contentElement.getValue()).thenReturn(fragmentData);
    when(fragmentData.getValue(String[].class)).thenReturn(attachments);

    newsDetailMetadataService.activate(config);
    Assert.assertNull(newsDetailMetadataService.getFragmentDetails(new String[] {""}, "corp"));
  }

  @Test
  public void testToVerifyContentFragmentWithInvalidPath() {
    when(resolver.getResource(ArgumentMatchers.anyString())).thenReturn(null);
    newsDetailMetadataService.activate(config);
    Assert.assertNull(newsDetailMetadataService.getFragmentDetails(selectors, "corp"));
  }

  @Test
  public void testToVerifyContentFragmentWithInvalidResolver()
      throws LoginException, org.apache.sling.api.resource.LoginException {
    when(resolverFactory.getServiceResourceResolver(map)).thenReturn(null);
    newsDetailMetadataService.activate(config);
    Assert.assertNull(newsDetailMetadataService.getFragmentDetails(selectors, "corp"));
  }

  @Test
  public void testToVerifyContentFragmentWithInvalidFragment() {
    when(resource.adaptTo(ContentFragment.class)).thenReturn(null);
    newsDetailMetadataService.activate(config);
    Assert.assertNull(newsDetailMetadataService.getFragmentDetails(selectors, "corp"));
  }

  @Test
  public void testToVerifyContentFragmentWithInvalidResolverFactory()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(NewsDetailMetadataService.class, "resolverFactory").set(newsDetailMetadataService,
        null);
    newsDetailMetadataService.activate(config);
    Assert.assertNull(newsDetailMetadataService.getFragmentDetails(selectors, "corp"));
  }
  
  @Test
  public void testSetCfPath() {
    newsDetailMetadataService.setCfPath(new String[] {"cfPath"});
  }

}

