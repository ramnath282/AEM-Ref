package com.mattel.productvideos.core.utils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils.Config;

@RunWith(PowerMockRunner.class)
public class ProductVideosPropertyUtilTest {

    @InjectMocks
    ProductVideosPropertyUtils productVideosPropertyUtils;
    @Mock
    Node node;
    Config config;

    @Test
    public void testGetSitePath() throws RepositoryException{
        config = Mockito.mock(ProductVideosPropertyUtils.Config.class);
        productVideosPropertyUtils.getSitePath();
    }

    @Test
    public void testGetRootAssetsPath() throws RepositoryException{
        config = Mockito.mock(ProductVideosPropertyUtils.Config.class);
        productVideosPropertyUtils.getRootAssetsPath();
    }

    @Test
    public void testConfig() throws RepositoryException{
        config = Mockito.mock(ProductVideosPropertyUtils.Config.class);
        productVideosPropertyUtils.activate(config);
    }


}
