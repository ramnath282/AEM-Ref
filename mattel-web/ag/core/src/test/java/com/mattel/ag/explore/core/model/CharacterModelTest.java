package com.mattel.ag.explore.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ag.explore.core.pojos.CharacterPojo;
import com.mattel.ag.explore.core.utils.PathUtils;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PathUtils.class)
public class CharacterModelTest {

	CharacterPojo characterPojo;
	@Mock
	Resource resource;
	public CharacterModel characterModel;
	@Mock
	ValueMap valueMap;
	@Mock
	PathUtils pathUtils;

	@Before
	public void setUp() {
		characterModel = new CharacterModel();
		characterPojo = new CharacterPojo();
		characterPojo.setExternal(true);
		characterPojo.setHeading("heading");
		characterPojo.setImagelink("imagelink");
		resource = Mockito.mock(Resource.class);
		valueMap = Mockito.mock(ValueMap.class);
		pathUtils = Mockito.mock(PathUtils.class);
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
		Mockito.when(valueMap.get("imagelink", String.class)).thenReturn("imagelink");
		Mockito.when(valueMap.get("heading", String.class)).thenReturn("heading");
	}

	@Test
	public void init() {
		characterModel.setResource(resource);
		characterModel.init();
		Assert.assertNotNull(characterModel.getCharacterPojo());
	}

}
