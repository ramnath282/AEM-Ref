package com.mattel.global.core.model.v2;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

@PrepareForTest(GlobalUtils.class)
@RunWith(PowerMockRunner.class)
public class CardModelTest {

	@InjectMocks
	private CardModel cardModel;

	@Mock
	private Resource resource;
	
	@Mock
	private Resource ctaResource;

	@Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(GlobalUtils.class);
		MemberModifier.field(CardModel.class, "title").set(cardModel, "title");
		MemberModifier.field(CardModel.class, "subTitle").set(cardModel, "subTitle");
		
		MemberModifier.field(CardModel.class, "resource").set(cardModel, resource);
		MemberModifier.field(CardModel.class, "url").set(cardModel, "url");
		MemberModifier.field(CardModel.class, "cardLinkOption").set(cardModel, "newwindow");

		MemberModifier.field(CardModel.class, "imageAltText").set(cardModel, "imageAltText");
		MemberModifier.field(CardModel.class, "mobileImageAltText").set(cardModel, "mobileImageAltText");
		MemberModifier.field(CardModel.class, "imageAltText").set(cardModel, "imageAltText");
		MemberModifier.field(CardModel.class, "image").set(cardModel, "image");

		MemberModifier.field(CardModel.class, "hoverImage").set(cardModel, "hoverImage");
		MemberModifier.field(CardModel.class, "hoverImageAltText").set(cardModel, "hoverImageAltText");
		MemberModifier.field(CardModel.class, "mobileHoverImage").set(cardModel, "mobileHoverImage");
		MemberModifier.field(CardModel.class, "mobileHoverImageAltText").set(cardModel, "mobileHoverImageAltText");
		
		Mockito.when(GlobalUtils.removeTags("title", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("strippedTitle");
		Mockito.when(GlobalUtils.removeTags("subTitle", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("strippedSubTitle");	}

	@Test
	public void testInit() throws IllegalArgumentException, IllegalAccessException {
		MemberModifier.field(CardModel.class, "entrCompClickable").set(cardModel, true);
		Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(ctaResource);
	    cardModel.init();
		assertEquals("strippedTitle",cardModel.getStrippedTitle());
		assertEquals("strippedSubTitle",cardModel.getStrippedSubTitle());
		Assert.assertNotNull(cardModel.getUrl());
		Assert.assertNotNull(cardModel.getCardLinkOption());
	}
	
	@Test
    public void testGetterSetter() throws IllegalArgumentException, IllegalAccessException {
        MemberModifier.field(CardModel.class, "entrCompClickable").set(cardModel, true);
        Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(ctaResource);
        cardModel.init();
        assertEquals("strippedTitle",cardModel.getStrippedTitle());
        assertEquals("strippedSubTitle",cardModel.getStrippedSubTitle());
        Assert.assertNotNull(cardModel.getUrl());
        Assert.assertNotNull(cardModel.getCardLinkOption());
    }
	
	@Test
	public void testInit_1() throws IllegalArgumentException, IllegalAccessException{
	  MemberModifier.field(CardModel.class, "entrCompClickable").set(cardModel, false);
	  Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(ctaResource);
      cardModel.init();
	}
	
	@Test
    public void testInit_2() throws IllegalArgumentException, IllegalAccessException{
      MemberModifier.field(CardModel.class, "entrCompClickable").set(cardModel, true);
      Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(null);
      cardModel.init();
    }
}
