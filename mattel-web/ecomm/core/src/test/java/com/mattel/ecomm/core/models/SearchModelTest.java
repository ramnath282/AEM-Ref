package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.pojos.TopResultsPojo;

@RunWith(PowerMockRunner.class)
public class SearchModelTest {
	@InjectMocks
	private SearchModel searchModel;
	@Mock
	private Resource resource;
	List<Node> topResultsList;
	List<TopResultsPojo> topResultList;
	Node node;
	String currentPath;

	@Before
	public void setup() throws IllegalAccessException {
		MemberModifier.field(SearchModel.class, "resource").set(searchModel, resource);
		topResultList = new LinkedList<>();
		topResultsList = new LinkedList<>();
		node = Mockito.mock(Node.class);
		searchModel.setTopResultsList(topResultsList);
		topResultsList.add(0, node);
		Mockito.when(resource.getPath()).thenReturn("/content");
	}

	@Test
	public void init() {
		searchModel.init();
	}

	@Test
	public void getTopResultList() {
		searchModel.getTopResultList();
	}

	@Test
	public void getViewMoreLink() {
		searchModel.getViewMoreLink();
	}

	@Test
	public void setTopResultList() {
		searchModel.setTopResultList(topResultList);
	}
}
