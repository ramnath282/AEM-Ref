package com.mattel.global.core.model.v1;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.model.ButtonDetails;
import com.mattel.global.core.utils.GlobalUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class CardModelTest {

	@InjectMocks
	private CardModel cardModel;

	@Mock
	private Resource buttonList;

	@Mock
	private Resource resource;

	@Before
	public void setup() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(CardModel.class, "title").set(cardModel, "title");
		MemberModifier.field(CardModel.class, "titleTag").set(cardModel, "titleTag");
		MemberModifier.field(CardModel.class, "description").set(cardModel, "description");
		MemberModifier.field(CardModel.class, "cardVariant").set(cardModel, "cardVariant");
		MemberModifier.field(CardModel.class, "position").set(cardModel, "position");
		MemberModifier.field(CardModel.class, "textAlign").set(cardModel, "textAlign");
		MemberModifier.field(CardModel.class, "smallImage").set(cardModel, "smallImage");
		MemberModifier.field(CardModel.class, "image").set(cardModel, "image");
		MemberModifier.field(CardModel.class, "imgAltText").set(cardModel, "imgAltText");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInit() {
		PowerMockito.mockStatic(GlobalUtils.class);
		List<ButtonDetails> buttonDetailslist = Mockito.mock(List.class);
		Mockito.when(GlobalUtils.buttonDetails(buttonList, resource)).thenReturn(buttonDetailslist);
		cardModel.init();
	}

	@Test
	public void testGetterSetter() {
		cardModel.getButtonList();
		cardModel.getButtonDetailslist();
		assertEquals("title", cardModel.getTitle());
		assertEquals("titleTag", cardModel.getTitleTag());
		assertEquals("<p>description</p>", cardModel.getDescription());
		assertEquals("cardVariant", cardModel.getCardVariant());
		assertEquals("position", cardModel.getPosition());
		assertEquals("textAlign", cardModel.getTextAlign());
		assertEquals("smallImage", cardModel.getSmallImage());
		assertEquals("image", cardModel.getImage());
		assertEquals("imgAltText", cardModel.getImgAltText());
	}

}
