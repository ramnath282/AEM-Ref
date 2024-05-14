package com.mattel.ecomm.core.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPProductComponent;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.Product;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;
import com.mattel.ecomm.coreservices.core.pojos.ProductAttributes;

@RunWith(PowerMockRunner.class)
public class ProductUIAdapterTest {
    private Product product = new Product();

    @Before
    public void setUp() {

        product.setBuyable("1");
        product.setAffirmIneligible("Y");
        List<ProductAssociation> associations = new ArrayList<>();
        ProductAssociation productAssociation = new ProductAssociation();
        productAssociation.setPartNumber("abc123");
        productAssociation.setName("doll");
        associations.add(productAssociation);
        product.setAssociations(associations);
        Map<String, Object> descripitiveAttributes = new HashMap<>();
        Map<String, Object> definingAttributes = new HashMap<>();
        descripitiveAttributes.put("DisclaimerCopy", "DisclaimerCopy");
        descripitiveAttributes.put("ReleaseDateWeb", "09/07/2019");
        descripitiveAttributes.put("MarketingCallout", "MarketingCallout");
        descripitiveAttributes.put(Constant.DISABLE_QUICK_VIEW, "true");
        definingAttributes.put("ClothingSize", "Large");
        ProductAttributes productAttributes = new ProductAttributes();
        productAttributes.setDefiningAttributes(definingAttributes);
        productAttributes.setDescripitiveAttributes(descripitiveAttributes);
        product.setAttributes(productAttributes);
        product.setAuxDescription1("AuxDescription1");
        product.setAuxDescription2("AuxDescription2");
        product.setAvailability("Unavailable");
        product.setBackorderDate("2019-09-10:10:10:10.10");
        product.setCanonicalCat("canonicalCat");
        ChildProduct childProduct = new ChildProduct();
        childProduct.setName("childProductName");
        childProduct.setPartNumber("childPartNumber");
        List<ChildProduct> childProducts = new ArrayList<>();
        childProducts.add(childProduct);
        product.setChildProducts(childProducts);
        product.setCode("code");
        product.setChildProducts(childProducts);
        product.setChildProducts(childProducts);
        product.setNewOverrideFlag("N");
        PDPProductComponent pdpProductComponent = new PDPProductComponent();
        pdpProductComponent.setAffirmIneligible("N");
        List<PDPProductComponent> components = new ArrayList<>();
        components.add(pdpProductComponent);
        product.setComponents(components);
        product.setCustSegExcl("custSegExcl");
        product.setDescription("description");
        product.setFullimage("DFN61DS_Viewer");
        List<Map<String, String>> giftCardMessages = new ArrayList<>();
        giftCardMessages.add(createMap("giftCardMessages", "giftCardMessages"));
        giftCardMessages.add(createMap("LINE1", "LINE1"));
        giftCardMessages.add(createMap("LINE2", "LINE2"));
        giftCardMessages.add(createMap("LINE3", "LINE3"));
        giftCardMessages.add(createMap("sequence", "sequence"));
        product.setGiftCardMessages(giftCardMessages);
        product.setImageLink("imageLink");
        product.setInvStatus("noLongerAvailable");
        product.setLanguage("language");
        product.setPartNumber("frl93");
        Map<String, Price> price = new HashMap<>();
        Price priceObj = new Price();
        priceObj.setCurrency("Euro");
        price.put("listPrice", priceObj);
        product.setPrice(price);
        product.setProductStatus("product_status");
        product.setProductType("GiftCard");
        product.setSeoMetaDescription("seo_metaDescription");
        product.setThumbnail("thumbnail");
    }

    private Map<String, String> createMap(String s1, String s2) {
        Map<String, String> giftCardMsg = new HashMap<>();
        giftCardMsg.put(s1, s2);
        return giftCardMsg;
    }

    @Test
    public void testProductUIAdapterGiftCard() {
        PDPProductUIResponse pdpProductUIResponse = new PDPProductUIResponse();
        pdpProductUIResponse = ProductUIAdapter.transformProductToGiftCard(product);
        Assert.assertNotNull(pdpProductUIResponse);
    }

    @Test
    public void testProductUIAdapterSingleSku() {
        PDPProductUIResponse pdpProductUIResponse = new PDPProductUIResponse();
        List<String> experienceFragmentPaths = new ArrayList<>();
        experienceFragmentPaths.add("/content/experienceFragment");
        pdpProductUIResponse = ProductUIAdapter.transformProductToSignleSKU(product, experienceFragmentPaths);
        Assert.assertNotNull(pdpProductUIResponse);
    }
}
