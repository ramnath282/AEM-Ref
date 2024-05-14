package com.mattel.ag.retail.core.model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.joda.time.field.OffsetDateTimeField;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.retail.core.pojos.FooterLinks;
import com.mattel.ag.retail.core.pojos.StoreBistroHoursPojo;
import com.mattel.ag.retail.core.services.MultifieldReader;

public class StoreBistroHoursModelTest {

	MultifieldReader multifieldReader;
	ValueMap valueMap;
	Map.Entry<String, ValueMap> mapEntry;
	List<FooterLinks> footerLinkPojo;
	Map<String, ValueMap> map = Mockito.mock(Map.class);
	Map.Entry<String, ValueMap> entry;
	Iterator<Object> iterator;
	List<Object> list;
	Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();
	
	StoreBistroHoursModel storeBistroHoursModel;
	Node storeHourDetails;
	Node bistroHourDetails;
	
	StoreBistroHoursPojo storeBistroHoursPojo;
	OffsetDateTime offsetDateTime;
	
	@Before
	public void setup() {
		
		storeBistroHoursModel = new StoreBistroHoursModel();
		multifieldReader = Mockito.mock(MultifieldReader.class);
		storeBistroHoursModel.setMultifieldReader(multifieldReader);
		entry = Mockito.mock(Map.Entry.class);
		valueMap = Mockito.mock(ValueMap.class);
	    mapEntry = Mockito.mock(Map.Entry.class);
	    footerLinkPojo = Mockito.mock(List.class);
	    iterator = Mockito.mock(Iterator.class);
	    list = Mockito.mock(List.class);
	    storeHourDetails = Mockito.mock(Node.class);
	    bistroHourDetails = Mockito.mock(Node.class);
	    storeBistroHoursPojo = Mockito.mock(StoreBistroHoursPojo.class);
	    
	    when(list.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(valueMap.get(Mockito.anyString(), any(String.class))).thenReturn("Any String");
		when(iterator.next()).thenReturn(map);
		when(mapEntry.getValue()).thenReturn(valueMap);
	}

	@Test
	public void testBistroHourDetails() {
		storeBistroHoursModel.setBistroHourDetails(bistroHourDetails);
		String startTime = "2018-08-03T10:00:00.000+05:30";
		String endTime = "2018-09-09T10:00:00.000+05:30";
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.when(entry.getValue().containsKey(Mockito.anyString())).thenReturn(true);
		Mockito.when(entry.getValue().get("bistroStartTime", String.class)).thenReturn(startTime);
		Mockito.when(entry.getValue().get("bistroEndTime", String.class)).thenReturn(endTime);
		storeBistroHoursModel.init();
	}
	@Test
	public void testStoreHourDetails() {
		storeBistroHoursModel.setStoreHourDetails(storeHourDetails);
		String startTime = "2018-08-03T10:00:00.000+05:30";
		String endTime = "2018-09-09T10:00:00.000+05:30";
		Map.Entry<String, ValueMap> entry = Mockito.mock(Map.Entry.class);
		when(entry.getValue()).thenReturn(valueMap);
		entryset.add(entry);
		when(map.entrySet()).thenReturn(entryset);
		when(multifieldReader.propertyReader(any())).thenReturn(map);
		Mockito.when(entry.getValue().containsKey(Mockito.anyString())).thenReturn(true);
		Mockito.when(entry.getValue().get("storeStartTime", String.class)).thenReturn(startTime);
		Mockito.when(entry.getValue().get("storeEndTime", String.class)).thenReturn(endTime);
		storeBistroHoursModel.init();
	}
}