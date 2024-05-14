package com.mattel.global.core.services;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.global.core.exceptions.NodeWriterException;
import com.mattel.global.core.interfaces.XmlNodeWriterService;
import com.mattel.global.master.core.constants.Constants;

/**
 * Service to fetch Corporate news article node data in XML format.
 */
@Component(service = CorporateXmlNodeWriterService.class)
public class CorporateXmlNodeWriterService implements XmlNodeWriterService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CorporateXmlNodeWriterService.class);
  private static final String INTERNAL_MASTER_NODE = "/jcr:content/data/master";
  private static final List<String> SIMPLE_FIELDS = Arrays.asList("title", "link", "pubDate",
      "mediaContent");
  private static final List<String> PAGE_SIMPLE_FIELDS = Arrays.asList("jcr:title", "mediaContent");
  private static final List<String> PAGE_CDATA_FIELDS = Arrays.asList("jcr:description");
  private static final List<String> CDATA_FIELDS = Arrays.asList(/* "content", */ "description");
  private static final List<String> JSON_FIELDS = Arrays.asList(Constants.CATEGORIES);
  private static final String ERROR_WRITE = "Unable to write property : %s in xml";
  private static final String ERROR_WRITE_VALUE = CorporateXmlNodeWriterService.ERROR_WRITE
      + ", property value was : %s";
  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * Reading node data for News Articles Repo path and writing the node
   * properties to xml
   */
  @Override
  public void readNode(String path, String rootNewsPagePath,ResourceResolver resolver, Writer writer)
      throws NodeWriterException {
    final XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();

    CorporateXmlNodeWriterService.LOGGER
        .info("readNode method, XML output factory initiated for fragment path : {} "
                + "and Page Path: {}", path, rootNewsPagePath);

    try {
      final XMLStreamWriter stream = outputFactory.createXMLStreamWriter(writer);
      final Resource res = resolver.getResource(path);
      final Resource rootPageRes = resolver.getResource(rootNewsPagePath);

      stream.writeStartElement("channel");
      writeSimpleElement("title", "News Releases - Mattel Inc.", stream);
      writeSimpleElement("language", "en-us", stream);

      if (Objects.nonNull(res)) {
        CorporateXmlNodeWriterService.LOGGER
            .debug("readNode method,  path resolved successfully : {}", path);
        traverseNodes(res, resolver, stream);
      }
      if(Objects.nonNull(rootPageRes)){
        CorporateXmlNodeWriterService.LOGGER
          .debug("readNode method,  path resolved successfully : {}", rootNewsPagePath);
        traversePageNodes(rootPageRes, resolver, stream);
      }
      stream.writeEndElement();
      stream.writeEndDocument();
      CorporateXmlNodeWriterService.LOGGER.info("readNode method, xml generated successfully");
    } catch (XMLStreamException e) {
      throw new NodeWriterException(e);
    }
  }

  /**
   * Traverse the root node for News Article Data. Each of child node is read
   * and data is stored in XML stream.
   *
   * @param rootNode
   *          The {@link Resource} instance for News Article Data root node.
   * @param resolver
   *          The {@link ResourceResolver request resolver} instance.
   * @param stream
   *          The {@link XMLStreamWriter xml stream} to write news article data
   *          in.
   * @throws RepositoryException
   */
  private void traverseNodes(Resource rootNode, ResourceResolver resolver, XMLStreamWriter stream)
       {
    final Iterator<Resource> iterator = rootNode.listChildren();

    CorporateXmlNodeWriterService.LOGGER
        .debug("traverseNodes method, interating all the child nodes for node : {}", rootNode);

    while (iterator.hasNext()) {
      final Resource cfResource = iterator.next();
      final Resource resource = resolver
          .getResource(cfResource.getPath() + CorporateXmlNodeWriterService.INTERNAL_MASTER_NODE);

      Optional.ofNullable(resource).map(r -> r.adaptTo(Node.class)).filter(Objects::nonNull)
          .ifPresent(node -> {
            try {
              stream.writeStartElement("item");
              CorporateXmlNodeWriterService.SIMPLE_FIELDS.stream().filter(nodeFilter(node))
                  .forEach(writeSimpleNode(stream, node));
              CorporateXmlNodeWriterService.CDATA_FIELDS.stream().filter(nodeFilter(node))
                  .forEach(writeCdataNode(stream, node));
              CorporateXmlNodeWriterService.JSON_FIELDS.stream().filter(nodeFilter(node))
                  .forEach(writeJsonNode(stream, node));
              stream.writeEndElement();
            } catch (final XMLStreamException e) {
              CorporateXmlNodeWriterService.LOGGER.error("Unable to write node data", e);
            }
          });
    }
  }
  
  /**
   * Traverse the root page path for News Article Data. Each of child page is read
   * and data is stored in XML stream.
   *
   * @param rootNode
   *          The {@link Resource} instance for News Article root page node.
   * @param resolver
   *          The {@link ResourceResolver request resolver} instance.
   * @param stream
   *          The {@link XMLStreamWriter xml stream} to write news article data
   *          in.
   * @throws RepositoryException
   */
  private void traversePageNodes(Resource rootRes, ResourceResolver resolver, XMLStreamWriter stream)
       {
    CorporateXmlNodeWriterService.LOGGER.info("Start of traversePageNodes method");
    CorporateXmlNodeWriterService.LOGGER
    .debug("traverseNodes method, interating all the child pages for resource : {}", rootRes);
    final PageManager pgMgr = resolver.adaptTo(PageManager.class);
    if(Objects.nonNull(pgMgr) && Objects.nonNull(pgMgr.getContainingPage(rootRes))) {
        Page rootPage = pgMgr.getContainingPage(rootRes);
        for (final Iterator<Page> children = rootPage.listChildren(new PageFilter(false, true),
                true); children.hasNext();) {
            final Page page = children.next();
            final Resource pageRes =  page.getContentResource();
            
            Optional.ofNullable(pageRes).map(r -> r.adaptTo(Node.class)).filter(Objects::nonNull)
            .ifPresent(node -> {
              try {
                if(StringUtils.equalsIgnoreCase(Constants.NEWS_ARTICLE_PAGE, pageRes.getResourceType())
                        && node.hasProperty(Constants.PUB_DATE)){
                    stream.writeStartElement("item");
                    CorporateXmlNodeWriterService.PAGE_SIMPLE_FIELDS.stream().filter(nodeFilter(node))
                        .forEach(writeSimpleNode(stream, node));
                    CorporateXmlNodeWriterService.PAGE_CDATA_FIELDS.stream().filter(nodeFilter(node))
                        .forEach(writeCdataNode(stream, node));
                    writePageLink(stream,page);
                    writeCategories(stream,pageRes);
                    writePubDate(stream,pageRes);
                    stream.writeEndElement();
                }
                
              } catch (final XMLStreamException | RepositoryException e) {
                CorporateXmlNodeWriterService.LOGGER.error("Unable to write node data", e);
              }
            });
        }
    }
    CorporateXmlNodeWriterService.LOGGER.info("End of traversePageNodes method");
  }

  /**
   * This method sets the XML link property by reading the page
   * name and appending it in front of /news/ URL string
   * @param stream XML Stream Writer
   * @param page Page
   */
  private void writePageLink(XMLStreamWriter stream, Page page) {
    CorporateXmlNodeWriterService.LOGGER.debug("Start of writePageLink method");
    final String pagelink = Constants.SLASH_NEWS_PAGE.concat(page.getName());
    CorporateXmlNodeWriterService.LOGGER
    .debug("pagelink value for current page is: {}",pagelink);
    try {
        writeSimpleElement("link", pagelink, stream);
      } catch (final XMLStreamException e) {
        CorporateXmlNodeWriterService.LOGGER
            .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE_VALUE, "link", pagelink), e);
      }
    CorporateXmlNodeWriterService.LOGGER.debug("End of writePageLink method");
  }

  /**
   * This method reads the tags authored in the page properties and
   * sets the category title for each of them in the XML element
   * @param stream XML Stream Writer
   * @param pageRes Page Content Resource
   */
  private void writeCategories(XMLStreamWriter stream, Resource pageRes) {
    CorporateXmlNodeWriterService.LOGGER.debug("Start of writeCategories method");
    String[] tagArr = pageRes.getValueMap().get("cq:tags", String[].class);
    TagManager tagManager = pageRes.getResourceResolver().adaptTo(TagManager.class);
    try {
        if(Objects.nonNull(tagArr) && tagArr.length > 0 && Objects.nonNull(tagManager)){
            stream.writeStartElement(Constants.CATEGORIES);
                Arrays.asList(tagArr).stream().filter(StringUtils::isNotBlank).map(tagManager::resolve)
                        .filter(Objects::nonNull).map(Tag::getTitle).filter(StringUtils::isNotBlank)
                        .forEach(tagTitle -> {
                            try {
                                CorporateXmlNodeWriterService.LOGGER.debug
                                ("tagTitle value is: {}",tagTitle);
                                writeSimpleElement("category", tagTitle, stream);
                            } catch (XMLStreamException e) {
                                CorporateXmlNodeWriterService.LOGGER.error(String.format(
                                        CorporateXmlNodeWriterService.ERROR_WRITE_VALUE, "category", tagTitle), e);
                            }
                        });
            stream.writeEndElement();
        }
    } catch (XMLStreamException e) {
        CorporateXmlNodeWriterService.LOGGER
        .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE, Constants.CATEGORIES), e);
    }
    CorporateXmlNodeWriterService.LOGGER.debug("End of writeCategories method");
  }

  /**
   * This method reads the custom pubDate property from page properties
   * and converts it to specific date format and sets the xml item
   * attribute
   * @param stream XML Stream Writer
   * @param pageRes Page Content Resource
   */
  private void writePubDate(XMLStreamWriter stream, Resource pageRes) {
     CorporateXmlNodeWriterService.LOGGER.debug("Start of writePubDate method");
     Date newsPubDate = pageRes.getValueMap().get(Constants.PUB_DATE, Date.class);
     if(Objects.nonNull(newsPubDate)){
         final DateFormat formatter = new SimpleDateFormat(Constants.NEWS_DATE_FORMAT);
         formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
         String dateString = formatter.format(newsPubDate);
         try {
             CorporateXmlNodeWriterService.LOGGER.debug
             ("Final formatted date string is: {}",dateString);
             writeSimpleElement(Constants.PUB_DATE, dateString, stream);
           } catch (final XMLStreamException e) {
             CorporateXmlNodeWriterService.LOGGER
                 .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE_VALUE, Constants.PUB_DATE, dateString), e);
           }
     }
     CorporateXmlNodeWriterService.LOGGER.debug("End of writePubDate method");
  }

 private Consumer<? super String> writeJsonNode(XMLStreamWriter stream, Node node) {
    return s -> {
      try {
        final Property p = node.getProperty(s);
        final Value[] categoriesData = p.getValues();

        stream.writeStartElement(s);

        for (final Value value : categoriesData) {
          final String categoryData = value.getString();

          if (StringUtils.isBlank(categoryData) || "null".equals(categoryData)) {
            continue;
          }

          if (categoryData.contains("[")) {
            extractMultipleValues(stream, categoryData);
          } else {
            extraSingleValues(stream, categoryData);
          }
        }
        stream.writeEndElement();
      } catch (RepositoryException | XMLStreamException | IOException e) {
        CorporateXmlNodeWriterService.LOGGER
            .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE, s), e);
      }
    };
  }

  /**
   * Default utility method to write multiple category data in XML stream.
   *
   * @param stream
   *          The XML stream to write to.
   * @param categoryData
   *          The multi-valued category data.
   * @throws XMLStreamException
   */
  private void extractMultipleValues(XMLStreamWriter stream, final String categoryData)
      throws IOException {
    final TypeReference<Map<String, List<String>>> type = new TypeReference<Map<String, List<String>>>() {
    };
    final Map<String, List<String>> categories = CorporateXmlNodeWriterService.MAPPER
        .readValue(categoryData, type);

    if (Objects.nonNull(categories)) {
      categories.keySet().stream().filter(k -> Objects.nonNull(categories.get(k)))
          .filter(k -> !k.contains("/")).forEach(k -> categories.get(k).stream()
              .filter(StringUtils::isNotBlank).forEach(v -> writeKeys(k, v, stream)));
    }
  }

  /**
   * Default utility method to write single valued category data in XML stream.
   *
   * @param stream
   *          The XML stream to write to.
   * @param categoryData
   *          The single-valued category data.
   * @throws XMLStreamException
   */
  private void extraSingleValues(XMLStreamWriter stream, final String categoryData)
      throws IOException {
    final TypeReference<Map<String, String>> type = new TypeReference<Map<String, String>>() {
    };
    final Map<String, String> categories = CorporateXmlNodeWriterService.MAPPER
        .readValue(categoryData, type);

    if (Objects.nonNull(categories)) {
      categories.keySet().stream().filter(k -> StringUtils.isNotBlank(categories.get(k)))
          .filter(k -> !k.contains("/")).forEach(k -> writeKeys(k, categories.get(k), stream));
    }
  }

  /**
   * Default utility method to write property of type text in XML stream.
   *
   * @param key
   *          The property name
   * @param value
   *          The property value
   * @param stream
   *          The XML stream to write to.
   * @throws XMLStreamException
   */
  private void writeKeys(String key, String value, XMLStreamWriter stream) {
    try {
      writeSimpleElement(key, value, stream);
    } catch (final XMLStreamException e) {
      CorporateXmlNodeWriterService.LOGGER
          .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE_VALUE, key, value), e);

    }
  }

  /**
   * Default utility method to write property of type text in XML stream.
   *
   * @param stream
   *          The XML stream to write to.
   * @param node
   *          The {@link Node a single news article node}
   * @throws XMLStreamException
   */
  private Consumer<? super String> writeSimpleNode(XMLStreamWriter stream, Node node) {
    return s -> {
      try {
        final Property p = node.getProperty(s);
        s = s.contains("jcr:") ? s.replace("jcr:", "") : s ;
        writeSimpleElement(s, p.getString(), stream);
      } catch (RepositoryException | XMLStreamException e) {
        CorporateXmlNodeWriterService.LOGGER
            .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE, s), e);
      }
    };
  }

  /**
   * Default utility method to write property of type cdata in XML stream.
   *
   * @param stream
   *          The XML stream to write to.
   * @param node
   *          The {@link Node a single news article node}
   * @throws XMLStreamException
   */
  private Consumer<? super String> writeCdataNode(XMLStreamWriter stream, Node node) {
    return s -> {
      try {
        final Property cdata = node.getProperty(s);
        final String value = cdata.getString();
        s = s.contains("jcr:") ? s.replace("jcr:", "") : s ;
        writeSimpleCdataElement(s, value, stream);
      } catch (RepositoryException | XMLStreamException e) {
        CorporateXmlNodeWriterService.LOGGER
            .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE, s), e);
      }
    };
  }

  /**
   * Checks if node has a property.
   *
   * @param node
   *          The {@link Node a single news article node}
   * @return True if property is present.
   */
  private Predicate<? super String> nodeFilter(Node node) {
    return t -> {
      try {
        return node.hasProperty(t);
      } catch (final RepositoryException e) {
        CorporateXmlNodeWriterService.LOGGER
            .error(String.format(CorporateXmlNodeWriterService.ERROR_WRITE, t), e);
        return false;
      }
    };
  }
}
