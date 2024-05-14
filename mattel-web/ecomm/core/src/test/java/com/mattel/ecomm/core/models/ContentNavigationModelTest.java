package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
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

import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ContentNavigationService;
import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.ecomm.core.services.ContentNavigationServiceImpl;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NavigationUtil.class, EcommHelper.class })
public class ContentNavigationModelTest {
	
	@InjectMocks
	private ContentNavigationModel contentNavigationModel;
	@Mock
	private Resource resource;
	ValueMap nodeValues;
	NavigationUtil navigationService;
	ContentNavigationService contentNavigationServiceImpl;
	SiteNavigationPojo primaryNavPojo;
	List<SiteNavigationPojo> primaryNavLinks = new LinkedList<>();
	List<PromoImagePojo> imageSectionList = new LinkedList<>();
	PromoImagePojo promoImagePojo;
	
	@Before
	public void setup() throws Exception {
		primaryNavPojo = new SiteNavigationPojo();
		promoImagePojo = new PromoImagePojo();
		navigationService = Mockito.mock(NavigationUtil.class);
		contentNavigationServiceImpl =  Mockito.mock(ContentNavigationServiceImpl.class);
		MemberModifier.field(ContentNavigationModel.class, "resource").set(contentNavigationModel, resource);
		nodeValues = Mockito.mock(ValueMap.class);
		nodeValues.put("displayShopByValue", true);
		imageSectionList.add(promoImagePojo);
		primaryNavLinks.add(primaryNavPojo);
		contentNavigationModel.setNavigationService(navigationService);
		contentNavigationModel.setContentNavigationServiceImpl(contentNavigationServiceImpl);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(nodeValues);
		Mockito.when(nodeValues.containsKey("displayShopByValue")).thenReturn(true);
		Mockito.when(nodeValues.get("displayShopByValue", Boolean.class)).thenReturn(true);
		Mockito.when(navigationService.getTemplateVariationType(primaryNavPojo, "templateType", "aeForPrimaryNavTitle",true,"device")).thenReturn(primaryNavPojo);
		Mockito.when(contentNavigationServiceImpl.fetchPromoImageListByTemplateType(Mockito.any(Resource.class), Mockito.any(GlobalNavigationPojo.class))).thenReturn(imageSectionList);
	}
	
	@Test
	public void init() throws RepositoryException {
		contentNavigationModel.init();
	}
	
	@Test
	public void getPrimaryNavLinks() {
		contentNavigationModel.getPrimaryNavLinks();
	}
	
	@Test
	public void getImageSectionList() {
		contentNavigationModel.getImageSectionList();
	}
	
	@Test
	public void getImageAlignmentType() {
		contentNavigationModel.getImageAlignmentType();
	}
}
