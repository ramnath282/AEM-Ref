package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.master.core.model.BackgroundTabModel;

@RunWith(PowerMockRunner.class)
public class BackgroundTabModelTest {

	@InjectMocks
	private BackgroundTabModel backgroundTabModel;

	@Before
	public void setup() throws Exception {
		MemberModifier.field(BackgroundTabModel.class, "backgroundOption").set(backgroundTabModel, "backgroundOption");
		MemberModifier.field(BackgroundTabModel.class, "backgroundColor").set(backgroundTabModel, "backgroundColor");
		MemberModifier.field(BackgroundTabModel.class, "customMobile").set(backgroundTabModel, "customMobile");
		MemberModifier.field(BackgroundTabModel.class, "tileImage").set(backgroundTabModel, "tileImage");
		MemberModifier.field(BackgroundTabModel.class, "imageLarge").set(backgroundTabModel, "imageLarge");
		MemberModifier.field(BackgroundTabModel.class, "tileOption").set(backgroundTabModel, "tileOption");
		MemberModifier.field(BackgroundTabModel.class, "imagebg").set(backgroundTabModel, "imagebg");
	}

	@Test
	public void testGettersSetters() {
		assertEquals("backgroundOption", backgroundTabModel.getBackgroundOption());
		assertEquals("backgroundColor", backgroundTabModel.getBackgroundColor());
		assertEquals("customMobile", backgroundTabModel.getCustomMobile());
		assertEquals("tileImage", backgroundTabModel.getTileImage());
		assertEquals("imageLarge", backgroundTabModel.getImageLarge());
		assertEquals("tileOption", backgroundTabModel.getTileOption());
		assertEquals("imagebg", backgroundTabModel.getImagebg());
	}
}
