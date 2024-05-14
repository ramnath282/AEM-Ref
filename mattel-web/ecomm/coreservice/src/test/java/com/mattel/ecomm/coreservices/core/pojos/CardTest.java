package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CardTest {
    private final Address address = Mockito.mock(Address.class);

    private final String addressId = "12345";

    private final String cardName = "John Doe";

    private final String cardType = "VISA";

    private final String createDate = "Tue May 13 2018 23:30:00.789";

    private final String creditCardId = "XXXXXX-1111-XXXX-2222";

    private final String defaultFlag = "true";

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
    private Card card;

    @Before
    public void createCard() throws Exception {
        card = new Card();
        card.setAddress(address);
        card.setAddressId(addressId);
        card.setCardName(cardName);
        card.setCardType(cardType);
        card.setCreateDate(createDate);
        card.setCreditCardId(creditCardId);
        card.setDefaultFlag(defaultFlag);
        card.setExpMonth(expMonth);
        card.setExpYear(expYear);
        card.setField1(field1);
        card.setField2(field2);
        card.setField3(field3);
        card.setHashCode(hashCode);
        card.setLastUser(lastUser);
        card.setMaskAccount(maskAccount);
        card.setToken(token);
        card.setUsersId(usersId);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetMaskAccount() throws Exception {
        Assert.assertEquals(maskAccount, card.getMaskAccount());
    }

    @Test
    public void testGetLastUser() throws Exception {
        Assert.assertEquals(lastUser, card.getLastUser());
    }

    @Test
    public void testGetUsersId() throws Exception {
        Assert.assertEquals(usersId, card.getUsersId());
    }

    @Test
    public void testGetExpMonth() throws Exception {
        Assert.assertEquals(expMonth, card.getExpMonth());
    }

    @Test
    public void testGetCreditCardId() throws Exception {
        Assert.assertEquals(creditCardId, card.getCreditCardId());
    }

    @Test
    public void testGetCardType() throws Exception {
        Assert.assertEquals(cardType, card.getCardType());
    }

    @Test
    public void testGetCreateDate() throws Exception {
        Assert.assertEquals(createDate, card.getCreateDate());
    }

    @Test
    public void testGetDefaultFlag() throws Exception {
        Assert.assertEquals(defaultFlag, card.getDefaultFlag());
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals(addressId, card.getAddressId());
    }

    @Test
    public void testGetAddress() throws Exception {
        Assert.assertEquals(address, card.getAddress());
    }

    @Test
    public void testGetField1() throws Exception {
        Assert.assertEquals(field1, card.getField1());
    }

    @Test
    public void testGetField2() throws Exception {
        Assert.assertEquals(field2, card.getField2());
    }

    @Test
    public void testGetField3() throws Exception {
        Assert.assertEquals(field3, card.getField3());
    }

    @Test
    public void testGetToken() throws Exception {
        Assert.assertEquals(token, card.getToken());
    }

    @Test
    public void testGetExpYear() throws Exception {
        Assert.assertEquals("2020", card.getExpYear());
    }

    @Test
    public void testGetCardName() throws Exception {
        Assert.assertEquals(cardName, card.getCardName());
    }

    @Test
    public void testGetHashCode() throws Exception {
        Assert.assertEquals(hashCode, card.getHashCode());
    }
}
