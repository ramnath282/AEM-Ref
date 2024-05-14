package com.mattel.global.core.pojo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.JsonNode;

@RunWith(PowerMockRunner.class)
public class ItemNodeTest {
	@InjectMocks
	ItemNode itemNode;
	
	JsonNode jsonNode;

	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		jsonNode = Mockito.mock(JsonNode.class);
		Map<String, String> properties = new HashMap<>();
		properties.put("key1", "value1");
		ItemNode itemNode1 = new ItemNode("guid", jsonNode);
		MemberModifier.field(ItemNode.class, "properties").set(itemNode1, properties);
	}

	@Test
	public void testToVerifyItemListPojo() {
		itemNode.hashCode();
		itemNode.toString();
	}
	
	@Test
	public void testSetters(){
		Map<String, String> properties = new HashMap<>();
		properties.put("key1", "value1");
		itemNode.setGuid("guid");
		itemNode.setJsonNode(jsonNode);
		itemNode.setProperties(properties);
		itemNode.setProperty("key1", "value1");
		itemNode.getGuid();
		itemNode.getJsonNode();
		itemNode.getProperties();
	}
	
	@Test
	public void testEquals(){
	    Object obj = null;
        itemNode.equals(obj);
	}
}
