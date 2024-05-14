package com.mattel.global.core.exceptions;

/**
 * Custom exception thrown when unable to write AEM node data in custom writer
 * stream (XML or JSON).
 */
public class NodeWriterException extends Exception {
  private static final long serialVersionUID = -2007905458393057664L;

  public NodeWriterException() {
    super();
  }

  public NodeWriterException(String message, Throwable cause) {
    super(message, cause);
  }

  public NodeWriterException(String message) {
    super(message);
  }

  public NodeWriterException(Throwable cause) {
    super(cause);
  }
}
