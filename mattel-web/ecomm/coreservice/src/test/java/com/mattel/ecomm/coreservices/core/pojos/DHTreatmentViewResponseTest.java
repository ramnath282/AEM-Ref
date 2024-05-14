package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DHTreatmentViewResponseTest {
    @InjectMocks
    DHTreatmentViewResponse treatmentViewResponse;

    @Before
    public void setUp() {
        treatmentViewResponse = new DHTreatmentViewResponse();
        List<DHService> catalogEntryView = new ArrayList<DHService>();
        DHService dHService = new DHService();
        dHService.setBuyable("Y");
        dHService.setName("Doll");
        catalogEntryView.add(dHService);
        treatmentViewResponse.setCatalogEntryView(catalogEntryView);
    }

    @Test
    public void testGetCatalogEntryView() {
        Assert.assertNotNull(treatmentViewResponse.getCatalogEntryView());
    }

    @Test
    public void testToString() {
        Assert.assertNotNull(treatmentViewResponse.toString());
    }
}
