package com.mattel.ecomm.coreservices.core.pojos;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChannelTest {
    private Map<String, String> channelIdentifer = new HashMap<>();

    private Map<String, String> description = new HashMap<>();

    private String userData = "testData";
    private Channel channel;

    @Before
    public void createChannel() throws Exception {
        channel = new Channel();
        channel.setChannelIdentifer(channelIdentifer);
        channel.setDescription(description);
        channel.setUserData(userData);
    }

    @Test
    public void testGetChannelIdentifer() throws Exception {
        Assert.assertEquals(channelIdentifer, channel.getChannelIdentifer());
    }

    @Test
    public void testGetUserData() throws Exception {
        Assert.assertEquals(userData, channel.getUserData());
    }

    @Test
    public void testGetDescription() throws Exception {
        Assert.assertEquals(description, channel.getDescription());
    }
}
