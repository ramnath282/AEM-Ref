
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MenusTest {

    private Menus impl = null;
    private List<Menus.Item> items;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Menus();
        impl.setName("dummy_string");
        impl.setLabel("dummy_string");
        impl.setType("dummy_string");
        items = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Menus.Item>();
        impl.setItems(items);
        impl.toString();
    }

    @Test
    public void testGetItems()
        throws Exception
    {
        Assert.assertEquals(items, impl.getItems());
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetLabel()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLabel());
    }

    @Test
    public void testGetType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getType());
    }

}
