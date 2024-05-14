package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.Filter;
import com.day.cq.search.PredicateGroup;
import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PredicateGroup.class, EcommConfigurationUtils.class,EcommHelper.class })
public class DeleteNodeServiceImplTest {

	@InjectMocks
	DeleteNodeServiceImpl deleteNodeServiceImpl;
	
	  private String RESOURCE_TYPE_PROPERTY = "sling:resourceType";
	  private String REPLICATION_ACTION_PROPERTY = "cq:lastReplicationAction";
	  private String REPLICATION_ACTION_ACTIVATE = "Activate";
	  private String PLP_RESOURCE_TYPE = "mattel/ecomm/ag/components/structure/ecomm-plp-page";
	  private String BACK_TO_TOP_RESOURCETYPE = "mattel/ecomm/ag/components/content/backToTop";
	  private String ECOMM_EXPFRG_RESOURCETYPE = "cq/experience-fragments/editor/components/experiencefragment";
	  private String FRAGMENT_PATH_PROPERTY = "fragmentPath";
	  private String FRAGMENT_PATH_PROPERTY_VALUE = Constant.CONTENT
	      + "/experience-fragments/ag/shop/add-on-modal/master";
	  private static final String ENABLE_DELETION_TRUE = "true";
	  
	  @Mock
	  GetResourceResolver getResourceResolver;
	  @Mock
	  ResourceResolver resourceResolver;
	  @Mock
	  Resource resource;
	  @Mock
	  Node node;
	  @Mock
	  Page page;
	  @Mock
	  ValueMap valuemap;
	  @Mock
	  Iterator<Page> plpRootPageItr;
	  @Mock
	  Filter<Page> plpResourceTypeFilter;

	@Before
	public void setUp() throws RepositoryException {
	    
	}

	@Test
	public void getCategoryPagesJsonTest() throws IllegalArgumentException, IllegalAccessException {
	   MemberModifier.field(DeleteNodeServiceImpl.class, "enableDeletion").set(deleteNodeServiceImpl, true);
       Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
       Mockito.when(resourceResolver.resolve(Mockito.anyString())).thenReturn(resource);
       Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
       Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
       valuemap.put(RESOURCE_TYPE_PROPERTY, PLP_RESOURCE_TYPE);
       Mockito.when(page.getProperties()).thenReturn(valuemap);
       Mockito.when(valuemap.get(RESOURCE_TYPE_PROPERTY,"")).thenReturn(PLP_RESOURCE_TYPE);
       
       Mockito.when(page.listChildren()).thenReturn(plpRootPageItr);
       //Mockito.when(page.getProperties().get(RESOURCE_TYPE_PROPERTY, "").equals(PLP_RESOURCE_TYPE)).thenReturn(true);
       deleteNodeServiceImpl.deleteNodes("");
	}

}
