package com.mattel.global.core.interfaces;

import java.io.Writer;

import javax.xml.stream.XMLStreamWriter;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.global.core.exceptions.NodeWriterException;

public class XmlNodeWriterServiceTest {
  private final XmlNodeWriterService impl = new XmlNodeWriterService() {
    @Override
    public void readNode(String path, String rootPagePath, ResourceResolver resolver, Writer writer)
        throws NodeWriterException {
    }
  };

  @Test
  public void testWriteSimpleElement() throws Exception {
    final XMLStreamWriter xmlStreamWriter = Mockito.mock(XMLStreamWriter.class);
    Mockito.doNothing().when(xmlStreamWriter).writeStartElement("name");
    Mockito.doNothing().when(xmlStreamWriter).writeCharacters("john");
    Mockito.doNothing().when(xmlStreamWriter).writeEndElement();

    impl.writeSimpleElement("name", "john", xmlStreamWriter);
  }

  @Test
  public void testWriteSimpleCdataElement() throws Exception {
    final XMLStreamWriter xmlStreamWriter = Mockito.mock(XMLStreamWriter.class);
    Mockito.doNothing().when(xmlStreamWriter).writeStartElement("content");
    Mockito.doNothing().when(xmlStreamWriter).writeCData("<p>Hello John</p>");
    Mockito.doNothing().when(xmlStreamWriter).writeEndElement();

    impl.writeSimpleCdataElement("content", "<p>Hello John</p>", xmlStreamWriter);
  }
}
