package com.mattel.global.core.utils;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;
import org.apache.commons.lang.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CryptoSupportUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoSupportUtils.class);

    @Reference
    private static CryptoSupport cryptoSupport;

    private static ServiceReference factoryRef;

    private static String encryptedString;
    private static String decryptedString;

    public static String encryptString(String string) {
        LOGGER.info("encryptString method {}",string);
        try {
            BundleContext bundleContext = FrameworkUtil.getBundle(CryptoSupport.class).getBundleContext();

            if(Objects.nonNull(bundleContext)) {
                factoryRef = bundleContext.getServiceReference(CryptoSupport.class.getName());
                cryptoSupport = (CryptoSupport) bundleContext.getService(factoryRef);
            }
            if(StringUtils.isNotEmpty(string) && Objects.nonNull(cryptoSupport)) {
                encryptedString = cryptoSupport.protect(string);
                LOGGER.info("Encryption done {}", encryptedString);
            }
            else {
                LOGGER.info("Encryption Failed cryptosupport is null {}", cryptoSupport);
            }
        } catch (CryptoException e) {
            LOGGER.error("Exception in encryptString in CryptoSupportUtils :",e);
        }
        return encryptedString;
    }

    public static String decryptString(String string) {
        LOGGER.info("decryptString method {}",string);
        try {
            if(StringUtils.isNotEmpty(string) && Objects.nonNull(cryptoSupport)) {
                if(cryptoSupport.isProtected(string)) {
                    decryptedString = cryptoSupport.unprotect(string);
                }
                LOGGER.info("Decryption done {}", decryptedString);
            }
            else {
                LOGGER.info("Decryption Failed cryptosupport is null {}", cryptoSupport);
            }
        } catch (CryptoException e) {
            LOGGER.error("Exception in decryptString in CryptoSupportUtils :",e);
        }
        return decryptedString;
    }
    /**
     * Private Constructor for Sonar issue.
     */
    private CryptoSupportUtils() {
    }
}
