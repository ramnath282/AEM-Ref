package com.mattel.global.core.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Optional;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.exceptions.NodeWriterException;
import com.mattel.global.core.interfaces.XmlNodeWriterService;
import com.mattel.global.core.utils.PropertyReaderUtils;

/**
 * Generic proxy service to fetch AEM node data in XML format for different
 * applications or sites.
 */
@Component(service = HttpXmlNodeDataService.class)
public class HttpXmlNodeDataService {
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpXmlNodeDataService.class);
  @Reference
  @Optional
  CorporateXmlNodeWriterService corporateXmlNodeWriterService;

  @Reference
  PropertyReaderUtils propertyReaderUtils;

  /**
   * To fetch the AEM node data for given application name and locale and write
   * the properties for each node in XML response stream.
   *
   * @param appName
   *          The application name or sitekey for which to fetch data.
   * @param locale
   *          The locale key. For e.g. "ag_en"
   * @param resolver
   *          The {@link ResourceResolver request resolver} instance.
   * @param response
   *          The {@link SlingHttpServletResponse HTTP response} to write XML
   *          in.
   * @throws IOException
   */
  public void write(String appName, String locale, ResourceResolver resolver,
      SlingHttpServletResponse response) throws IOException {
    HttpXmlNodeDataService.LOGGER.info("Writing node data for application: {}, locale: {}, in xml",
        appName, locale);

    switch (appName) {
      case "corp":
      case "corporate":
        String sitekey= StringUtils.isNotBlank(locale) ? appName+"_"+locale : appName;
        write(propertyReaderUtils.getNodeDataPath(sitekey),propertyReaderUtils.getRootPagePath(sitekey) ,resolver, response,
            corporateXmlNodeWriterService);
        HttpXmlNodeDataService.LOGGER.info(
            "XML for node data generated successfully for application: {}, locale: {}", appName,
            locale);
        break;
      default:
        HttpXmlNodeDataService.LOGGER.error(
            "No Xml generation configuration found for application: {}, locale: {}, unable to write node data",
            appName, locale);
        response.sendError(HttpURLConnection.HTTP_NOT_FOUND);
        break;
    }
  }

  /**
   * Checks if node data path is valid, fetches and writes node data for given
   * node data path.
   *
   * @param nodeDataPath
   *          The path to Corporate news articles.
   * @param resolver
   *          The {@link ResourceResolver request resolver} instance.
   * @param response
   *          The {@link SlingHttpServletResponse HTTP response} to write XML
   *          in.
   * @param xmlNodeWriterService
   *          The {@link CorporateXmlNodeWriterService} to write news article
   *          data in xml.
   * @throws IOException
   */
  public void write(String nodeDataPath, String rootNewsPagePath, ResourceResolver resolver,
      SlingHttpServletResponse response, XmlNodeWriterService xmlNodeWriterService)
      throws IOException {
    HttpXmlNodeDataService.LOGGER.info("Checking validity of corporate news articles node path: {}",
        nodeDataPath);

    if (StringUtils.isNotBlank(nodeDataPath)
            || StringUtils.isNotBlank(rootNewsPagePath)) {
      try {
        HttpXmlNodeDataService.LOGGER.debug(
            "XML to generated for following valid corporate news articles node data path : {}",
            nodeDataPath);
        response.setStatus(HttpURLConnection.HTTP_OK);
        response.setContentType("application/xhtml+xml");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        xmlNodeWriterService.readNode(nodeDataPath,rootNewsPagePath, resolver, response.getWriter());
      } catch (final NodeWriterException e) {
        response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
        HttpXmlNodeDataService.LOGGER.error(
            String.format("Unknown error encountered, xml cannot be generated, for node path: %s",
                nodeDataPath),
            e);
      }
    } else {
      HttpXmlNodeDataService.LOGGER.error("Xml cannot be generated, invalid node path: {}",
          nodeDataPath);
      response.sendError(HttpURLConnection.HTTP_NOT_FOUND);
    }
  }
}
