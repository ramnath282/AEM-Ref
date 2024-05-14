package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EcommHelper.class})
public class GetProductTypeTest {

  @InjectMocks
  GetProductType getProductType;
  private String clientIdentifier = "A2B345BDF";
  private String skewId = "5678";

  @Mock
  private GetResourceResolver getResourceResolver;
  @Mock
  private PropertyReaderService propertyReaderService;
  @Mock
  private QueryBuilder queryBuilder;
  @Mock
  private ResourceResolver resourceResolver;
  private String productPath="/content/AG/en/ecommn/doll/barbieDoll";
  private static final String PRODUCT_TYPE = "product_type";
  private static final String PART_NUMBER = "partNumber";
  @Mock
  private Resource resource;
  @Mock
  private ValueMap valueMap;
  @Mock
  private Query query;
  @Mock
  private SearchResult result;
  @Mock
  Hit hit;
  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(EcommHelper.class);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(propertyReaderService.getProductPath(clientIdentifier)).thenReturn(productPath);
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resource.getValueMap()).thenReturn(valueMap);
    Mockito.when(valueMap.get(PRODUCT_TYPE, String.class)).thenReturn("doll");
    Mockito.when(valueMap.get(PART_NUMBER, String.class)).thenReturn("1234");
    Mockito.when(resourceResolver.isLive()).thenReturn(true);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetProductType() {
    Map<String, String> propertyMap = getProductType.getProductType(skewId, clientIdentifier);
    Assert.assertNotNull(propertyMap);
    Assert.assertEquals("doll",propertyMap.get(PRODUCT_TYPE));
    Assert.assertEquals("1234",propertyMap.get(PART_NUMBER));

  }

  @Test
  public void testGetProductTypeDatasource() throws RepositoryException {
    Mockito.when(queryBuilder.createQuery(Mockito.any(),Mockito.any())).thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    List<Hit> hits=new ArrayList<>();
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenReturn(valueMap);
    Set<String> productTypes = getProductType.getProductTypeDatasource("AG");
    Assert.assertNotNull(productTypes);
  }
  @Test
  public void testGetProductTypeDatasource_WithRepositoryException() throws RepositoryException {
    Mockito.when(queryBuilder.createQuery(Mockito.any(),Mockito.any())).thenReturn(query);
    Mockito.when(query.getResult()).thenReturn(result);
    List<Hit> hits=new ArrayList<>();
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getProperties()).thenThrow(RepositoryException.class);
    getProductType.getProductTypeDatasource("AG");
  }

}
