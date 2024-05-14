package com.mattel.ecomm.coreservices.core.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

public class DataImporterServiceTest {
  DataImporterService<String, String> impl;

  @Before
  public void setUp() throws Exception {
    impl = new DataImporterService<String, String>() {
      @Override
      public String readInputData(String requestBody, Map<String, Object> requestAttributes)
          throws ServiceException {
        return StringUtils.EMPTY;
      }
    };
  }

  @Test
  public void testReadInputData() throws ServiceException {
    Assert.assertEquals(StringUtils.EMPTY, impl.readInputData(new String(), new HashMap<>()));
  }

  @Test
  public void testToByteArrayInputStream() throws IOException {
    final StringBuilder sb = new StringBuilder("test");

    try (InputStream in = impl.toByteArrayInputStream(sb)) {
      Assert.assertNotNull(in);
    }
  }

  @Test
  public void testWriteDataToStreamBufferedReader() throws IOException {
    final StringReader sr = new StringReader("test");
    final BufferedReader br = new BufferedReader(sr);

    try (InputStream in = impl.writeDataToStream(br)) {
      Assert.assertNotNull(in);
    }
  }

  @Test
  public void testWriteDataToStreamReader() throws IOException {
    final StringReader sr = new StringReader("test");

    try (InputStream in = impl.writeDataToStream(sr)) {
      Assert.assertNotNull(in);
    }
  }

  @Test
  public void testWriteDataToStreamReaderCharArray() throws IOException {
    final StringReader sr = new StringReader("test");

    try (InputStream in = impl.writeDataToStream(sr, new char[10])) {
      Assert.assertNotNull(in);
    }
  }
}
