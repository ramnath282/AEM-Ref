package com.mattel.ecomm.core.importer.workflow.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.importer.workflow.services.ProductImportValidatorServiceImpl.MandatoryFieldValidator;
import com.mattel.ecomm.core.importer.workflow.services.ProductImportValidatorServiceImpl.TypeValidator;
import com.mattel.ecomm.core.importer.workflow.services.ProductImportValidatorServiceImpl.ValidationException;
import com.mattel.ecomm.core.services.GetResourceResolver;

@RunWith(MockitoJUnitRunner.class)
public class ProductImportValidatorServiceImplTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  @InjectMocks
  private ProductImportValidatorServiceImpl impl;
  @Mock
  private GetResourceResolver getResourceResolver;

  @Before
  public void setUp() throws IOException, RepositoryException {
    try (InputStream is = getClass().getResourceAsStream("product_feed_schema.json")) {
      final ProductImportValidatorServiceImpl.Config config = Mockito
          .mock(ProductImportValidatorServiceImpl.Config.class);
      final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
      final Resource resource = Mockito.mock(Resource.class);
      final Node node = Mockito.mock(Node.class);
      final Property property = Mockito.mock(Property.class);
      final Binary binary = Mockito.mock(Binary.class);

      Mockito.when(config.schemaPath())
          .thenReturn("/content/dam/ag/productfeedvalidator/schema.json");
      Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
      Mockito.when(binary.getStream()).thenReturn(is);
      Mockito.when(property.getBinary()).thenReturn(binary);
      Mockito.when(node.getNode("jcr:content")).thenReturn(node);
      Mockito.when(node.getProperty("jcr:data")).thenReturn(property);
      // Mockito.doNothing().when(resourceResolver).close();
      Mockito.when(resourceResolver.getResource("/content/dam/ag/productfeedvalidator/schema.json"))
          .thenReturn(resource);
      Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
      impl.activate(config);
      Assert.assertNotNull(impl);
    }
  }

  @After
  public void tearDown() {
    impl.deactivate();
  }

  @Test
  public void testIsMandatory() {
    Assert.assertTrue(impl.isMandatory("pdpLink"));
  }

  @Test
  public void testFieldsToValidate() {
    final List<String> fields = new ArrayList<>(impl.fieldToValidate());
    final List<String> expected = Arrays.asList("partNumber", "pdpLink", "product_type",
        "productName");

    Collections.sort(fields);
    Collections.sort(expected);
    Assert.assertEquals(expected, fields);
  }

  @Test
  public void testValidatePdpLinkValid() throws JsonProcessingException, IOException {
    Assert
        .assertTrue(impl
            .validate("pdpLink",
                ProductImportValidatorServiceImplTest.OBJECT_MAPPER.createObjectNode()
                    .put("pdpLink", "willa-doll-fgd39").get("pdpLink"),
                new HashMap<>())
            .isSuccess());
  }

  @Test
  public void testValidatePdpLinkMissing() throws JsonProcessingException, IOException {
    Assert
        .assertFalse(impl
            .validate("pdpLink", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
                .createObjectNode().put("pdpLink", "").get("pdpLink"), new HashMap<>())
            .isSuccess());
  }

  @Test
  public void testValidatePdpLinkInvalidChar() throws JsonProcessingException, IOException {
    Assert
        .assertFalse(impl
            .validate("pdpLink",
                ProductImportValidatorServiceImplTest.OBJECT_MAPPER.createObjectNode()
                    .put("pdpLink", "willa-doll:fgd39").get("pdpLink"),
                new HashMap<>())
            .isSuccess());
  }

  @Test
  public void testValidatePartNumberValid() throws JsonProcessingException, IOException {
    Assert.assertTrue(impl
        .validate("partNumber", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("partNumber", "fgd39").get("partNumber"), new HashMap<>())
        .isSuccess());
  }

  @Test
  public void testValidatePartNumberMissing() throws JsonProcessingException, IOException {
    Assert.assertFalse(impl
        .validate("partNumber", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("partNumber", "").get("partNumber"), new HashMap<>())
        .isSuccess());
  }

  @Test
  public void testValidatePartNumberInvalidChar() throws JsonProcessingException, IOException {
    Assert.assertFalse(impl
        .validate("partNumber", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("partNumber", "fgd:39").get("partNumber"), new HashMap<>())
        .isSuccess());
  }

  @Test
  public void testValidateProductTypeValid() throws JsonProcessingException, IOException {
    Assert
        .assertTrue(impl
            .validate("product_type",
                ProductImportValidatorServiceImplTest.OBJECT_MAPPER.createObjectNode()
                    .put("product_type", "ItemBean").get("product_type"),
                new HashMap<>())
            .isSuccess());
  }

  @Test
  public void testValidateProductTypeMissing() throws JsonProcessingException, IOException {
    Assert.assertFalse(impl
        .validate("product_type", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("product_type", "").get("product_type"), new HashMap<>())
        .isSuccess());
  }

  @Test
  public void testValidateProductName() throws JsonProcessingException, IOException {
    Assert
        .assertTrue(impl
            .validate("productName",
                ProductImportValidatorServiceImplTest.OBJECT_MAPPER.createObjectNode()
                    .put("productName", "Willa Doll").get("productName"),
                new HashMap<>())
            .isSuccess());
  }

  @Test
  public void testValidateProductNameMissing() throws JsonProcessingException, IOException {
    Assert.assertFalse(impl
        .validate("productName", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("productName", "").get("productName"), new HashMap<>())
        .isSuccess());
  }

  @Test(expected = ValidationException.class)
  public void testMandatoryValidator0() throws ValidationException {
    final MandatoryFieldValidator mandatoryFieldValidator = impl.new MandatoryFieldValidator();

    mandatoryFieldValidator.isOk("pdpLink", null);
  }

  @Test
  public void testTypeValidator0() throws ValidationException {
    final TypeValidator typeValidator = impl.new TypeValidator("INTEGER");

    Assert.assertTrue(
        typeValidator.isOk("productNumber", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("productNumber", 1234567).get("productNumber")));
  }

  @Test
  public void testTypeValidator1() throws ValidationException {
    final TypeValidator typeValidator = impl.new TypeValidator("BOOLEAN");

    Assert.assertTrue(
        typeValidator.isOk("isAvailaible", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("isAvailaible", true).get("isAvailaible")));
  }

  @Test
  public void testTypeValidator2() throws ValidationException {
    final TypeValidator typeValidator = impl.new TypeValidator("DOUBLE");

    Assert
        .assertTrue(typeValidator.isOk("price", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("price", 12.08d).get("price")));
  }

  @Test
  public void testTypeValidator3() throws ValidationException {
    final TypeValidator typeValidator = impl.new TypeValidator("FLOAT");

    Assert
        .assertTrue(typeValidator.isOk("price", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
            .createObjectNode().put("price", 12.08f).get("price")));
  }

  @Test
  public void testTypeValidator4() throws ValidationException {
    final TypeValidator typeValidator = impl.new TypeValidator("DEFAULT");

    Assert.assertTrue(typeValidator.isOk("xyz", ProductImportValidatorServiceImplTest.OBJECT_MAPPER
        .createObjectNode().put("xyz", "xyz").get("xyz")));
  }

  @Test
  public void testValidationException0() {
    final ValidationException validationException = impl.new ValidationException();

    Assert.assertNotNull(validationException);
  }

  @Test
  public void testValidationException1() {
    final Throwable th = new Throwable("Invalid field value");
    final ValidationException validationException = impl.new ValidationException(th);

    Assert.assertEquals(th, validationException.getCause());
  }

  @Test
  public void testValidationException2() {
    final Throwable th = new Throwable("Invalid field value");
    final ValidationException validationException = impl.new ValidationException(
        "Unknown Exception", th);

    Assert.assertEquals(th, validationException.getCause());
    Assert.assertEquals("Unknown Exception", validationException.getMessage());
  }
}
