package com.mattel.ecomm.coreservices.core.enums;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.pojos.UpdateContactPreferencesRequest;

public class ResourceMapperTest {
    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(ResourceMapper.getInstance());
    }

    @Test
    public void testGetReaderInstance() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("update_contact_preferences_req.json")) {
            final ObjectReader reader = ResourceMapper.getReaderInstance(UpdateContactPreferencesRequest.class);
            final UpdateContactPreferencesRequest updateContactPreferencesRequest = reader.readValue(is);

            Assert.assertNotNull(updateContactPreferencesRequest);
            Assert.assertEquals("10601", updateContactPreferencesRequest.getCatalogId());
        }
    }

    @Test
    public void testGetWriterInstance() throws Exception {
        final UpdateContactPreferencesRequest updateContactPreferencesRequest = new UpdateContactPreferencesRequest();

        updateContactPreferencesRequest.setCatalogId("10601");
        updateContactPreferencesRequest.setStoreId("10651");
        Assert.assertNotNull(ResourceMapper.getWriterInstance(UpdateContactPreferencesRequest.class)
                .writeValueAsString(updateContactPreferencesRequest));
    }
}
