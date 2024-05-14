package com.mattel.ag.retail.core.model;

import static org.junit.Assert.assertSame;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.retail.core.services.GoogleApiConfiguration;
import com.mattel.ag.retail.core.services.MultifieldReader;

public class StoreMapModelTest {

	StoreMapModel storeMapModel;
	
	GoogleApiConfiguration googleApiConfiguration;
	MultifieldReader multifieldReader;
	Node coordinateDetails;
	ValueMap valueMap;
	Set<Map.Entry<String, ValueMap>> entryset;
	Map.Entry<String, ValueMap> entry;
	
	@SuppressWarnings("unchecked")
    @Before
	public void setUp(){
		googleApiConfiguration = Mockito.mock(GoogleApiConfiguration.class);
		coordinateDetails = Mockito.mock(Node.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		valueMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Map.Entry.class);
	}
	
    @SuppressWarnings("unchecked")
    @Test
    public void initCoordinateDetailsNotNll() {
      String key = "Key";
      Object testObject = "TestObject";
      storeMapModel = new StoreMapModel();
      storeMapModel.setGoogleApiConfiguration(googleApiConfiguration);
      storeMapModel.setCoordinateDetails(coordinateDetails);
      LinkedHashMap<String, ValueMap> multifieldProperty = new LinkedHashMap<String, ValueMap>();
      LinkedHashMap<String, Object> vmMap = new LinkedHashMap<String, Object>();
      vmMap.put("key2", testObject);
      ValueMap vm = Mockito.mock(ValueMap.class);
      vm.put("key1", vmMap);
      multifieldProperty.put(key, vm);
      storeMapModel.setMultifieldReader(multifieldReader);
      Mockito.when(multifieldReader.propertyReader(coordinateDetails)).thenReturn(multifieldProperty);
      Mockito.when(entry.getValue()).thenReturn(vm);
      Set<Entry<String, Object>> dummyEntrySet = Mockito.mock(Set.class);
      Iterator<Entry<String, Object>> entrySetItr = Mockito.mock(Iterator.class);
      Entry<String, Object> dummyEntry = Mockito.mock(Entry.class);
      Mockito.when(vm.entrySet()).thenReturn(dummyEntrySet);
      Mockito.when(dummyEntrySet.iterator()).thenReturn(entrySetItr);
      Mockito.when(entrySetItr.hasNext()).thenReturn(true, false);
      Mockito.when(entrySetItr.next()).thenReturn(dummyEntry);
      Mockito.when(dummyEntry.getKey()).thenReturn("dummyKey");
      Mockito.when(dummyEntry.getValue()).thenReturn("dummyValue");
      storeMapModel.init();
    }
	
	@Test
	public void initCoordinateDetailsNll() {
		storeMapModel = new StoreMapModel();
		storeMapModel.setCoordinateDetails(null);
		storeMapModel.setGoogleApiConfiguration(googleApiConfiguration);
		storeMapModel.init();
	}
	
	@Test
	public void getGoogleApiKeyValueTest(){
		storeMapModel = new StoreMapModel();
		storeMapModel.setGoogleApiKeyValue("Value");
		assertSame("Value", storeMapModel.getGoogleApiKeyValue());
	}
	
	@Test
	public void getCoordinateArrayStringTest(){
		storeMapModel = new StoreMapModel();
		storeMapModel.setCoordinateArrayString("Value");
		assertSame("Value", storeMapModel.getCoordinateArrayString());
	}

}
