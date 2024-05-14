package com.mattel.global.core.interfaces;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/** To write AEM repository nodes in XML file. */
public interface XmlNodeWriterService extends NodeWriteService {
  /**
   * Default utility method to write a simple text element in XML stream.
   *
   * @param name
   *          The name of field to be added in XML.
   * @param value
   *          The text value
   * @param stream
   *          The XML stream to write to.
   * @throws XMLStreamException
   */
  default void writeSimpleElement(String name, String value, XMLStreamWriter stream)
      throws XMLStreamException {
    stream.writeStartElement(name);
    stream.writeCharacters(value);
    stream.writeEndElement();
  }

  /**
   * Default utility method to write a cdata element in XML stream.
   *
   * @param name
   *          The name of field to be added in XML.
   * @param value
   *          The CDATA value
   * @param stream
   *          The XML stream to write to.
   * @throws XMLStreamException
   */
  default void writeSimpleCdataElement(String name, String value, XMLStreamWriter stream)
      throws XMLStreamException {
    stream.writeStartElement(name);
    stream.writeCData(value);
    stream.writeEndElement();
  }
}
