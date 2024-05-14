package com.mattel.global.core.transformers.extension;

import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.services.UrlShorteningService;
import com.mattel.global.core.utils.PropertyReaderUtils;

/**
 * An HTML transformer factory which would be used in the XF rendering pipeline in order to render
 * requests made to experience fragments with ".atoffer.html" links.
 *
 * This uses concepts from the Sling SAX rewriter pipeline documentation, found here:
 * https://sling.apache.org/documentation/bundles/output-rewriting-pipelines-org-apache-sling-rewriter.html
 *
 * This is used by the "Export to Target" feature in order to generate the HTML output of an offer.
 *
 * After Experience Fragments does all the processing it needs, the Sling SAX Pipeline will also
 * call the ExtendedTargetHtmlTransformer of this project.
 */
@Component(service = TransformerFactory.class, property = {
    "pipeline.type=xf-target-offer-extension" }, immediate = true)
public class ExtendedTargetHtmlTransformerFactory implements TransformerFactory {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ExtendedTargetHtmlTransformerFactory.class);

  @Reference
  PropertyReaderUtils propertyReaderUtils;
  
  @Reference
  UrlShorteningService urlShorteningService;

  @Override
  public Transformer createTransformer() {
    ExtendedTargetHtmlTransformerFactory.LOGGER
        .info("########### Intializing html transformer factory");
    return new ExtendedTargetHtmlTransformer(propertyReaderUtils, urlShorteningService);
  }
}
