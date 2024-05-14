package com.mattel.global.core.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FrameworkUtil.class)
public class CryptoSupportUtilsTest {

    @Mock
    CryptoSupport cryptoSupport;
    @Mock
    ServiceReference factoryRef;

    @InjectMocks
    CryptoSupportUtils cryptoSupportUtils;

    @Before
    public void setup() throws IllegalAccessException {
        MemberModifier.field(CryptoSupportUtils.class, "cryptoSupport").set(cryptoSupportUtils, cryptoSupport);
        MemberModifier.field(CryptoSupportUtils.class, "factoryRef").set(cryptoSupportUtils, factoryRef);
    }

    @Test
    public void test_ecryptString() throws CryptoException {
        PowerMockito.mockStatic(FrameworkUtil.class);
        Bundle bundle = Mockito.mock(Bundle.class);
        Mockito.when(FrameworkUtil.getBundle(CryptoSupport.class)).thenReturn(bundle);
        BundleContext bundleContext = Mockito.mock(BundleContext.class);
        Mockito.when(bundle.getBundleContext()).thenReturn(bundleContext);
        Mockito.when(bundleContext.getServiceReference(CryptoSupport.class.getName())).thenReturn(factoryRef);
        Mockito.when(bundleContext.getService(factoryRef)).thenReturn(cryptoSupport);
        Mockito.when(cryptoSupport.protect(Mockito.anyString())).thenReturn("encrypted_string");
        String encryptedString = CryptoSupportUtils.encryptString("string_encrypt");
        Assert.assertEquals("encrypted_string", encryptedString);
    }

    @Test
    public void test_decryptString() throws IllegalAccessException, CryptoException {
        Mockito.when(cryptoSupport.isProtected(Mockito.anyString())).thenReturn(true);
        Mockito.when(cryptoSupport.unprotect(Mockito.anyString())).thenReturn("decrypted_string");
        String decryptedString = CryptoSupportUtils.decryptString("string_decrypt");
        Assert.assertEquals("decrypted_string", decryptedString);
    }
}