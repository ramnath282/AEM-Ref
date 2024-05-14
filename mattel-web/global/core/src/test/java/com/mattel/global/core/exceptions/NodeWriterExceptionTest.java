package com.mattel.global.core.exceptions;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class NodeWriterExceptionTest {

  @Test
  public void testNodeWriterException() {
    final NodeWriterException nodeWriterException = new NodeWriterException();

    Assert.assertNotNull(nodeWriterException);
  }

  @Test
  public void testNodeWriterExceptionStringThrowable() {
    final IOException ioException = new IOException();
    final NodeWriterException nodeWriterException = new NodeWriterException("Unable to read stream",
        ioException);

    Assert.assertNotNull(nodeWriterException);
    Assert.assertEquals("Unable to read stream", nodeWriterException.getMessage());
    Assert.assertEquals(ioException, nodeWriterException.getCause());
  }

  @Test
  public void testNodeWriterExceptionString() {
    final NodeWriterException nodeWriterException = new NodeWriterException(
        "Unable to read stream");

    Assert.assertNotNull(nodeWriterException);
    Assert.assertEquals("Unable to read stream", nodeWriterException.getMessage());
  }

  @Test
  public void testNodeWriterExceptionThrowable() {
    final IOException ioException = new IOException();
    final NodeWriterException nodeWriterException = new NodeWriterException(ioException);

    Assert.assertNotNull(nodeWriterException);
    Assert.assertEquals(ioException, nodeWriterException.getCause());
  }
}
