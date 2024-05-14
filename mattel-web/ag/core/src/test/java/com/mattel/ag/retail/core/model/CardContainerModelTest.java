package com.mattel.ag.retail.core.model;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;

import com.mattel.ag.retail.core.services.CardReaderService;

public class CardContainerModelTest {

	CardContainerModel cardContainerModel;
	
	Resource resource;
	CardReaderService cardReaderService;
	
	@Before
	public void setUp(){
		resource = Mockito.mock(Resource.class);
		cardReaderService = Mockito.mock(CardReaderService.class);
	}
	
	@Test
	public void testInit(){
		cardContainerModel = new CardContainerModel();
		cardContainerModel.setResource(resource);
		cardContainerModel.setCardReaderService(cardReaderService);
		cardContainerModel.init();
	}
	
	@Test
    public void testInitWithAdditionalInformation() throws IllegalAccessException{
	    cardContainerModel = new CardContainerModel();
	    cardContainerModel.setResource(resource);
        cardContainerModel.setCardReaderService(cardReaderService);
	    MemberModifier.field(CardContainerModel.class, "additionalInformation").set(cardContainerModel,
                "additionalInformation for date RESERVATION_DATE");
	    MemberModifier.field(CardContainerModel.class, "dateOffset").set(cardContainerModel,
                24062019);
	    
	    cardContainerModel.init();
	    
	    Assert.assertNotNull(cardContainerModel.getAdditionalInformationWithDate());
	}
	
	@Test
	public void testgetResourcePath(){
		cardContainerModel = new CardContainerModel();
		cardContainerModel.getResourcePaths();
	}
	
	
}
