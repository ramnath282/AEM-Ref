package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.play.core.pojos.MattelBrandsPojo;
import com.mattel.play.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class MattelBrandModelTest {

	@InjectMocks
	MattelBrandModel mattelBrandModel;

	@Mock
	private Node brandDetails;

	@Mock
	private MultifieldReader multifieldReader;

	@Mock
	Resource resource;

	@Before
	public void setUp() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(MattelBrandModel.class, "brandDetails").set(mattelBrandModel, brandDetails);
		MemberModifier.field(MattelBrandModel.class, "multifieldReader").set(mattelBrandModel, multifieldReader);
		MemberModifier.field(MattelBrandModel.class, "resource").set(mattelBrandModel, resource);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInit() {
		Map<String, ValueMap> multifieldProperty = Mockito.mock(Map.class);
		Set<Entry<String, ValueMap>> entrySet = Mockito.mock(Set.class);
		Iterator<Entry<String, ValueMap>> propertyItr = Mockito.mock(Iterator.class);
		Entry<String, ValueMap> entry = Mockito.mock(Entry.class);

		Mockito.when(multifieldReader.propertyReader(brandDetails)).thenReturn(multifieldProperty);
		Mockito.when(multifieldProperty.entrySet()).thenReturn(entrySet);
		Mockito.when(entrySet.iterator()).thenReturn(propertyItr);
		Mockito.when(propertyItr.hasNext()).thenReturn(true, false);

		Mockito.when(propertyItr.next()).thenReturn(entry);
		ValueMap value = Mockito.mock(ValueMap.class);
		Mockito.when(entry.getValue()).thenReturn(value);
		mattelBrandModel.init();
	}
	
	@Test
	public void testGetterSetter() throws IllegalArgumentException, IllegalAccessException{
		List<MattelBrandsPojo> brandsList = new ArrayList<>();
		mattelBrandModel.setBrandsList(brandsList);
		Assert.assertEquals(brandsList, mattelBrandModel.getBrandsList());
	}
}
