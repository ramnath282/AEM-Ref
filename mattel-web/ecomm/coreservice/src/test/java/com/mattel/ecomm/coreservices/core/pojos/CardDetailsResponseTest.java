package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CardDetailsResponseTest {
    private final String addressId = "12345";

    private final String cardName = "John Doe";

    private final String cardType = "VISA";

    private final String createDate = "Tue May 13 2018 23:30:00.789";

    private final String creditCardid = "XXXXXX-1111-XXXX-2222";

    private final String defaultTag = "true";

    private final String expMonth = "02";

    private final String expYear = "2020";

    private final String field1 = "testField1";

    private final String field2 = "testField2";

    private final String field3 = "testField3";

    private final String hashCode = "287437";

    private final String lastUser = "John";

    private final String maskAccount = "XXXXXXXXX";

    private final String token = "ryye#=84837";

    private final String usersId = "11111";
    private CardDetailsResponse cardDetailsResponse;

    @Before
    public void createCardDetailsResponse() throws Exception {
        cardDetailsResponse = new CardDetailsResponse();
        cardDetailsResponse.setAddressId(addressId);
        cardDetailsResponse.setCardName(cardName);
        cardDetailsResponse.setCardType(cardType);
        cardDetailsResponse.setCreateDate(createDate);
        cardDetailsResponse.setCreditCardid(creditCardid);
        cardDetailsResponse.setDefaultTag(defaultTag);
        cardDetailsResponse.setExpMonth(expMonth);
        cardDetailsResponse.setExpYear(expYear);
        cardDetailsResponse.setField1(field1);
        cardDetailsResponse.setField2(field2);
        cardDetailsResponse.setField3(field3);
        cardDetailsResponse.setHashCode(hashCode);
        cardDetailsResponse.setLastUser(lastUser);
        cardDetailsResponse.setMaskAccount(maskAccount);
        cardDetailsResponse.setToken(token);
        cardDetailsResponse.setUsersId(usersId);
    }

    @Test
    public void testGetMaskAccount() throws Exception {
        Assert.assertEquals(maskAccount, cardDetailsResponse.getMaskAccount());
    }

    @Test
    public void testGetLastUser() throws Exception {
        Assert.assertEquals(lastUser, cardDetailsResponse.getLastUser());
    }

    @Test
    public void testGetUsersId() throws Exception {
        Assert.assertEquals(usersId, cardDetailsResponse.getUsersId());
    }

    @Test
    public void testGetExpMonth() throws Exception {
        Assert.assertEquals(expMonth, cardDetailsResponse.getExpMonth());
    }

    @Test
    public void testGetCreditCardid() throws Exception {
        Assert.assertEquals(creditCardid, cardDetailsResponse.getCreditCardid());
    }

    @Test
    public void testGetCardType() throws Exception {
        Assert.assertEquals(cardType, cardDetailsResponse.getCardType());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        Assert.assertEquals(createDate, cardDetailsResponse.getCreateDate());
    }

    @Test
    public void testGetDefaultTag() throws Exception {
        Assert.assertEquals(defaultTag, cardDetailsResponse.getDefaultTag());
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals(addressId, cardDetailsResponse.getAddressId());
    }

    @Test
    public void testGetField1() throws Exception {
        Assert.assertEquals(field1, cardDetailsResponse.getField1());
    }

    @Test
    public void testGetField2() throws Exception {
        Assert.assertEquals(field2, cardDetailsResponse.getField2());
    }

    @Test
    public void testGetToken() throws Exception {
        Assert.assertEquals(token, cardDetailsResponse.getToken());
    }

    @Test
    public void testGetField3() throws Exception {
        Assert.assertEquals(field3, cardDetailsResponse.getField3());
    }

    @Test
    public void testGetExpYear() throws Exception {
        Assert.assertEquals(expYear, cardDetailsResponse.getExpYear());
    }

    @Test
    public void testGetHashCode() throws Exception {
        Assert.assertEquals(hashCode, cardDetailsResponse.getHashCode());
    }

    @Test
    public void testGetCardName() throws Exception {
        Assert.assertEquals(cardName, cardDetailsResponse.getCardName());
    }
}
