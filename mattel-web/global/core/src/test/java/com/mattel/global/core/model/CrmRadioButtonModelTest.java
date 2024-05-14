package com.mattel.global.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class CrmRadioButtonModelTest {
	@InjectMocks
	private CrmRadioButtonModel crmRadioButtonModel;
	MultifieldReader multifieldReader;
	Node radioButtonMulti;
	Map<String, ValueMap> multifieldProperty;
	ValueMap valueMap;
	Map.Entry<String, ValueMap> entry;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws IllegalAccessException {
		multifieldReader = Mockito.mock(MultifieldReader.class);
		entry = Mockito.mock(Entry.class);
		valueMap = Mockito.mock(ValueMap.class);
		radioButtonMulti = Mockito.mock(Node.class);
		crmRadioButtonModel.setRadioButtonMulti(radioButtonMulti);;;
		crmRadioButtonModel.setMultifieldReader(multifieldReader);
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("document", valueMap);
		multifieldProperty.put("document", valueMap);
		Mockito.when(entry.getValue()).thenReturn(valueMap);

	}

	@Test
	public void test() {
		Mockito.when(multifieldReader.propertyReader(radioButtonMulti)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue().get("dropDownKey", String.class)).thenReturn("dropDownKey");
		Mockito.when(entry.getValue().get("dropDownValue", String.class)).thenReturn("dropDownValue");
		crmRadioButtonModel.init();
		Assert.assertNotNull(crmRadioButtonModel.getRadioButtonMulti());
		Assert.assertNotNull(crmRadioButtonModel.getMultifieldReader());
		Assert.assertNotNull(crmRadioButtonModel.getKeyList());
	}

}
