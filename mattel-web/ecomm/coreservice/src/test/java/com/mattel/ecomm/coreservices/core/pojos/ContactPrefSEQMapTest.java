
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactPrefSEQMapTest {

    private ContactPrefSEQMap impl = null;
    private List<String> contpreffrq;
    private List<String> contprefloy;
    private List<String> contprefcat;
    private List<String> contprefctl;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ContactPrefSEQMap();
        contpreffrq = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefFrq(contpreffrq);
        contprefloy = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefLoy(contprefloy);
        contprefcat = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefCat(contprefcat);
        contprefctl = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefCtl(contprefctl);
        impl.toString();
    }

    @Test
    public void testGetContPrefLoy()
        throws Exception
    {
        Assert.assertEquals(contprefloy, impl.getContPrefLoy());
    }

    @Test
    public void testGetContPrefCat()
        throws Exception
    {
        Assert.assertEquals(contprefcat, impl.getContPrefCat());
    }

    @Test
    public void testGetContPrefFrq()
        throws Exception
    {
        Assert.assertEquals(contpreffrq, impl.getContPrefFrq());
    }

    @Test
    public void testGetContPrefCtl()
        throws Exception
    {
        Assert.assertEquals(contprefctl, impl.getContPrefCtl());
    }

}
