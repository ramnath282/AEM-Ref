package com.mattel.global.core.sitemap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BaseSiteMapConfigTest {
    BaseSiteMapConfig baseSiteMapConfig;

    @Before
    public void setup() throws Exception {
        baseSiteMapConfig = new BaseSiteMapConfig();
    }

    @Test
    public void testBaseSiteMapConfig() {
        baseSiteMapConfig.toString();
    }

}
