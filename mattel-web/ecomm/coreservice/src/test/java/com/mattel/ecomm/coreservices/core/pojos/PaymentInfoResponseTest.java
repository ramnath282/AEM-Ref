
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentInfoResponseTest {

    private PaymentInfoResponse impl = null;
    private List<Card> cards;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PaymentInfoResponse();
        cards = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Card>();
        impl.setCards(cards);
        impl.toString();
    }

    @Test
    public void testGetCards()
        throws Exception
    {
        Assert.assertEquals(cards, impl.getCards());
    }

}
