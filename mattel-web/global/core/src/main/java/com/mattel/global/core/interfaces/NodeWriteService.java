package com.mattel.global.core.interfaces;

import java.io.Writer;

import org.apache.sling.api.resource.ResourceResolver;

import com.mattel.global.core.exceptions.NodeWriterException;

/** To write AEM repository nodes in different content types. E.g. XML or JSON */
public interface NodeWriteService {
  void readNode(String path, String rootNewsPagePath, ResourceResolver resolver, Writer writer) throws NodeWriterException;
}
