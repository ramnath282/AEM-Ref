package com.mattel.global.core.sitemap;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.pim.common.Csv;
import com.day.cq.commons.Externalizer;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

@Component(service = GenericSiteMapService.class)
public class GenericSiteMapService {
  private static final String CHANGE_FREQUENCY_PROPERTY = "changefreq";
  private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
  private static final List<String> EXCLUDE_PROP_VAL_TYPES = Arrays.asList("yes", "true", "y", "1");
  private static final List<String> FOLDER_TYPES = Arrays.asList("sling:Folder", "sling:OrderedFolder", "nt:folder");
  private static final String LAST_MODIFIED_PROPERTY = "lastmod";
  private static final String LOCATION_PROPERTY = "loc";
  private static final Logger LOGGER = LoggerFactory.getLogger(GenericSiteMapService.class);
  private static final String NS = "http://www.sitemaps.org/schemas/sitemap/0.9";
  private static final String PRIORITY_PROPERTY = "priority";
  @Reference
  private Externalizer externalizer;

  /**
   * This is main method
   * 
   * @param writer
   *            Writer
   * @param resourceResolver
   *            Resource Resolver
   * @param siteConfig
   *            siteConfig object
   * @throws IOException
   */
  public void buildSiteMap(Writer writer, final ResourceResolver resourceResolver, final SiteConfig siteConfig)
      throws IOException {
    final Resource resource = resourceResolver.getResource(siteConfig.getRootPath());
    LOGGER.debug("siteConfig value is : {}", siteConfig);
    LOGGER.debug("site root path configured is: {}", siteConfig.getRootPath());
    final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

    if (Objects.nonNull(resource) && Objects.nonNull(pageManager)) {
      final Page page = pageManager.getContainingPage(resource);

      if (Objects.nonNull(page)) {
        final XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        try {
          final XMLStreamWriter stream = outputFactory.createXMLStreamWriter(writer);
          stream.writeStartDocument("1.0");
          stream.writeStartElement("", "urlset", GenericSiteMapService.NS);
          stream.writeNamespace("", GenericSiteMapService.NS);
          if (siteConfig.isUseTreeAlgorithm()) {
        	LOGGER.debug("Traversing the pages using Tree Algorithm");
            // for the root page and iterate through child pages
            // recursively and set the properties for all of them
            writePageTree(page, stream, resourceResolver, siteConfig);
          } else {
        	LOGGER.debug("Traversing the pages using Flattened Hierarchy");
            // sets properties for the root page
            write(page, stream, resourceResolver, siteConfig);
            // Get all child pages as flattened hierarchy and set
            // the properties for all of them
            for (final Iterator<Page> children = page.listChildren(new PageFilter(false, true),
                true); children.hasNext();) {
              write(children.next(), stream, resourceResolver, siteConfig);
            }
          }
			try {
				loadDynamicPages(resourceResolver, stream, siteConfig);
			} catch (Exception e) {
				LOGGER.error("Exception occured while product sitemap generation", e);
			}

          stream.writeEndElement();
          stream.writeEndDocument();
        } catch (final XMLStreamException e) {
          throw new IOException(e);
        }
      }
    }
  }

  /**
   * This method build the final URL for custom URL patterns. appends custom
   * string at the start or end of the URL. for e.g. appending "/shop/p" at
   * start of URL
   * 
   * @param config
   *            DynamicSiteMapPageConfig object
   * @param url
   *            URL string
   * @return
   */
  private StringBuilder buildUrl(DynamicSiteMapPageConfig config, final String url) {
    final StringBuilder sb = new StringBuilder();
    Optional.ofNullable(config.getStartsWith()).filter(StringUtils::isNotBlank).ifPresent(sb::append);
    Optional.ofNullable(url).filter(StringUtils::isNotBlank).map(v -> v.startsWith("/") ? v : "/" + v)
        .ifPresent(sb::append);
    Optional.ofNullable(config.getEndsWith()).filter(StringUtils::isNotBlank).ifPresent(sb::append);

    if (config.isRemoveTrailingSlash()) {
      sb.replace(sb.length() - 1, sb.length(), StringUtils.EMPTY);
    }

    if (config.isIncludeHtmlPrefix()) {
      sb.append(".html");
    }
    return sb;
  }

  /**
   * This method returns the string representation of the property value
   * 
   * @param n
   *            Node
   * @param property
   *            property
   * @param defaultValue
   *            default Value
   * @return
   */
  private String getPropertyVal(Node n, String property, String defaultValue) {
    try {
      return n.getProperty(property).getString();
    } catch (final RepositoryException e) {
      GenericSiteMapService.LOGGER.error("Unable to fetch property value", e);
      return defaultValue;
    }
  }

  /**
   * This method checks if page is hidden from sitemap
   * 
   * @param page
   *            page
   * @param sc
   *            SiteConfig object
   * @return isHidden flag
   */
  private boolean isHidden(final Page page, SiteConfig sc) {
    return page.getProperties().get(Optional.ofNullable(sc.getPageExcludeProperty()).orElse("hideInSitemap"),
        false);
  }

  /**
   * This method checks if page is skipped from sitemap
   * 
   * @param page
   *            page
   * @param sc
   *            SiteConfig object
   * @return isSkipped flag
   */
  private boolean isSkipped(final Page page, SiteConfig sc) {
    return page.getProperties().get(Optional.ofNullable(sc.getPageSkipProperty()).orElse("skipInSitemap"), false);
  }
  
  
  /**
   * @param page
   * 			page
   * @return
   * 			isRedirectURLPresent flag
   */
  private boolean checkAdditionSkipProperty(final Page page, SiteConfig sc){
	  String redirectPropValue = page.getProperties().get(Optional.ofNullable(sc.getAdditionalSkipProperty()).orElse("cq:redirectTarget"), String.class);
	  LOGGER.debug("pagepath : {}", page.getPath());
	  LOGGER.debug("redirectPropValue : {}", redirectPropValue);
	  return StringUtils.isNotBlank(redirectPropValue);
  }

  /**
   * This method checks if page is stopped from sitemap
   * 
   * @param page
   *            page
   * @param sc
   *            SiteConfig object
   * @return isStopped flag
   */
  private boolean isStopped(final Page page, SiteConfig sc) {
    return page.getProperties().get(Optional.ofNullable(sc.getPageStopProperty()).orElse("stopInSitemap"), false);
  }

  /**
   * This method sets the URL object properties for dynamic pages such as PDP
   * or pages mentioned in the CSV file.
   * 
   * @param resourceResolver
   *            Resource Resolver
   * @param stream
   *            XML Stream Writer
   * @param siteConfig
   *            siteConfig object
   */
  private void loadDynamicPages(ResourceResolver resourceResolver, XMLStreamWriter stream, SiteConfig siteConfig) {
	LOGGER.info("loadDynamicPages -> start");
    Optional.ofNullable(siteConfig.getDynamicPageConfigs()).ifPresent(configs -> {
      for (final DynamicSiteMapPageConfig config : configs) {
        Optional.ofNullable(config.getDataSourceType())
            .ifPresent(dataSourceType -> Optional.ofNullable(config.getRepoPath()).ifPresent(repoPath -> {
              repoPath = repoPath.replace("{site_key}", siteConfig.getSiteKey());
              repoPath = repoPath.replace("{site_name}", siteConfig.getSiteName());
              repoPath = repoPath.replace("{locale}", siteConfig.getLocale());
              LOGGER.debug("repoPath is: {}", repoPath);
              if (DataSourceType.REPO.equals(dataSourceType)) {
                LOGGER.debug("DataSourceType is : REPO");
                processNodes(repoPath, resourceResolver, stream, config, siteConfig);
              } else if (DataSourceType.CSV.equals(dataSourceType)) {
                LOGGER.debug("DataSourceType is : CSV");
                processFile(repoPath, resourceResolver, stream, config, siteConfig);
              }
            }));
      }
    });
    LOGGER.info("loadDynamicPages -> end");
  }

  /**
   * This method reads the CSV file and writes the sitemap url elements for
   * each row
   * 
   * @param repoPath
   *            repopath for Dynamic SiteMap Pages
   * @param resourceResolver
   *            resource Resolver
   * @param stream
   *            XML Stream Writer
   * @param config
   *            DynamicSiteMapPageConfig object
   * @param siteConfig
   *            siteConfig object
   */
  private void processFile(String repoPath, ResourceResolver resourceResolver, XMLStreamWriter stream,
      DynamicSiteMapPageConfig config, SiteConfig siteConfig) {
    GenericSiteMapService.LOGGER.debug("Processing file config : {}", config);
    final Resource rootResource = Optional.ofNullable(Optional.ofNullable(resourceResolver.getResource(repoPath))
        .orElse(resourceResolver.getResource(siteConfig.getRootPath() + repoPath))).orElse(null);

    if (Objects.nonNull(rootResource) && Objects.nonNull(rootResource.adaptTo(Asset.class))) {
      final Asset asset = rootResource.adaptTo(Asset.class);
      try (InputStream in = asset.getOriginal().getStream()) {
        final Csv csv = new Csv();
        final Iterator<String[]> lines = csv.read(in, StandardCharsets.UTF_8.toString());

        final List<PageUrl> urls = StreamSupport.stream(Spliterators.spliteratorUnknownSize(lines, 0), false)
            .skip(1).map(this::readLine).filter(Objects::nonNull).collect(Collectors.toList());

        for (final PageUrl pageUrl : urls) {
          stream.writeStartElement(GenericSiteMapService.NS, "url");
          final String priority = Optional.ofNullable(pageUrl.getDefaultPriority())
              .orElse(siteConfig.getDefaultPriority());
          final String frequency = Optional.ofNullable(pageUrl.getDefaultFrequency())
              .orElse(siteConfig.getDefaultFrequency());
          final String lastModified = Optional.ofNullable(pageUrl.getDefaultLastModified())
              .orElse(StringUtils.EMPTY);

          if (StringUtils.isNotBlank(lastModified)) {
            writeElement(stream, GenericSiteMapService.LAST_MODIFIED_PROPERTY, lastModified);
          }

          final String loc = externalizer.externalLink(resourceResolver, siteConfig.getExternalizerDomain(),
              pageUrl.getUrl().replaceAll("//", "/"));

          writeElement(stream, GenericSiteMapService.LOCATION_PROPERTY, loc);

          if (StringUtils.isNotBlank(priority)) {
            writeElement(stream, GenericSiteMapService.PRIORITY_PROPERTY, priority);
          }

          if (StringUtils.isNotBlank(frequency)) {
            writeElement(stream, GenericSiteMapService.CHANGE_FREQUENCY_PROPERTY, frequency);
          }

          stream.writeEndElement();
        }
      } catch (IOException | XMLStreamException e) {
        GenericSiteMapService.LOGGER.error("Unable to save data from csv file in site map", e);
      }
    }
  }

  /**
   * This method checks the repo path and iterates all the nodes inside this
   * repo path
   * 
   * @param repoPath
   *            repopath for Dynamic SiteMap Pages
   * @param resolver
   *            Resource Resolver
   * @param stream
   *            XML Stream Writer
   * @param config
   *            DynamicSiteMapPageConfig object
   * @param siteConfig
   *            siteConfig object
   */
  private void processNodes(String repoPath, ResourceResolver resolver, XMLStreamWriter stream,
      DynamicSiteMapPageConfig config, SiteConfig siteConfig) {
    final Node rootNode = Optional
        .ofNullable(Optional.ofNullable(resolver.getResource(repoPath))
            .orElse(resolver.getResource(siteConfig.getRootPath() + repoPath)))
        .map(r -> r.adaptTo(Node.class)).orElse(null);

    Optional.ofNullable(rootNode).ifPresent(r -> {
      try {
        readNode(resolver, stream, config, siteConfig, rootNode);
      } catch (final RepositoryException | XMLStreamException e) {
        GenericSiteMapService.LOGGER.error("Unable to save dynamic pages in sitemap", e);
      }
    });
  }

  /**
   * This method reads each row/line of CSV file and sets the properties
   * 
   * @param arr
   * @return
   */
  private PageUrl readLine(String[] arr) {
    final PageUrl pageUrl = new PageUrl(false, StringUtils.EMPTY);

    if (arr.length >= 1) {
      pageUrl.setUrl(arr[0]);
    } else {
      return null;
    }

    if (arr.length >= 2) {
      pageUrl.setDefaultPriority(arr[1]);
    }

    if (arr.length >= 3) {
      pageUrl.setDefaultFrequency(arr[2]);
    }

    if (arr.length >= 4) {
      pageUrl.setDefaultLastModified(arr[3]);
    }

    return pageUrl;
  }

  /**
   * This method validates the repo path and iterates over all the nodes and
   * sets the properties
   * 
   * @param resolver
   *            Resource Resolver
   * @param stream
   *            XML Stream Writer
   * @param config
   *            DynamicSiteMapPageConfig object
   * @param siteConfig
   *            siteConfig object
   * @param rootNode
   *            root Node
   * @throws RepositoryException
   * @throws ValueFormatException
   * @throws PathNotFoundException
   * @throws XMLStreamException
   */
  @SuppressWarnings("unchecked")
  private void readNode(ResourceResolver resolver, XMLStreamWriter stream, DynamicSiteMapPageConfig config,
      SiteConfig siteConfig, final Node rootNode) throws RepositoryException, XMLStreamException {
    final NodeIterator iterator = rootNode.getNodes();
    final String priority = Optional.ofNullable(config.getDefaultPriority())
        .orElse(siteConfig.getDefaultPriority());
    final String frequency = Optional.ofNullable(config.getDefaultFrequency())
        .orElse(siteConfig.getDefaultFrequency());
    final List<String> excludeNodes = Optional.ofNullable(config.getExcludeNodes()).orElse(Arrays.asList());
    final String excludeProperty = Optional.ofNullable(config.getExcludeProperty()).orElse("noIndexTag");
    final String isActiveProperty = Optional.ofNullable(config.getIsActiveProperty()).orElse("active");
    final boolean isIncludeLastModified = config.isIncludeLastModified() || siteConfig.isIncludeLastModified();
    final String lastModPropName = Optional.ofNullable(config.getLastModifiedProperty()).orElse("jcr:lastModified");
    final Stream<Node> nodes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
    LOGGER.debug(
        "defaut props from cofig JSON are- priority:{}, frequency:{}, excludeProperty:{}, isIncludeLastModified:{}, lastModPropName:{}, excludeNodes:{}",
        new Object[] { priority, frequency, excludeProperty, isIncludeLastModified, lastModPropName,
            excludeNodes });

    nodes.filter(node -> {
      try {
        return !GenericSiteMapService.FOLDER_TYPES.contains(node.getProperty("jcr:primaryType").getString());
      } catch (final RepositoryException e1) {
        GenericSiteMapService.LOGGER.error("Node primary type not found", e1);
        return false;
      }
    }).forEach(node -> {
      try {
        final String nodeName = node.getName();
        final String url = Optional.ofNullable(node.hasProperty(config.getNodePrimaryId())).filter(x -> x)
            .map(x -> getPropertyVal(node, config.getNodePrimaryId(), nodeName)).orElse(nodeName);
        final String exPropVal = Optional.ofNullable(node.hasProperty(excludeProperty)).filter(x -> x)
            .map(x -> getPropertyVal(node, excludeProperty, "false")).orElse("false");
        final String activePropVal = Optional.ofNullable(node.hasProperty(isActiveProperty)).filter(x -> x)
            .map(x -> getPropertyVal(node, isActiveProperty, "true")).orElse("true");

        if (!excludeNodes.contains(url)
            && !GenericSiteMapService.EXCLUDE_PROP_VAL_TYPES.contains(exPropVal.toLowerCase())
            && GenericSiteMapService.EXCLUDE_PROP_VAL_TYPES.contains(activePropVal.toLowerCase())) {
          final StringBuilder sb = buildUrl(config, url);
          final String loc = externalizer.externalLink(resolver, siteConfig.getExternalizerDomain(),
              sb.toString().replaceAll("//", "/"));

          stream.writeStartElement(GenericSiteMapService.NS, "url");
          writeElement(stream, GenericSiteMapService.LOCATION_PROPERTY, loc);
          readOtherValues(stream, priority, frequency, isIncludeLastModified, lastModPropName, node);
          stream.writeEndElement();
        }
      } catch (final Exception e) {
        GenericSiteMapService.LOGGER.error("Error While reading the node", e);
      }
    });
  }

  /**
   * This method sets all the properties such as lastModified, frequency,
   * priority for each of the nodes.
   * 
   * @param stream
   *            XML Stream Writer
   * @param priority
   *            priority attribute
   * @param frequency
   *            frequency attribute
   * @param isIncludeLastModified
   *            isIncludeLastModified attribute
   * @param lastModPropName
   *            last Modified Property Name
   * @param node
   *            Node
   * @throws RepositoryException
   * @throws XMLStreamException
   * @throws PathNotFoundException
   */
  private void readOtherValues(XMLStreamWriter stream, final String priority, final String frequency,
      final boolean isIncludeLastModified, final String lastModPropName, Node node)
      throws RepositoryException, XMLStreamException {
    if (node.hasProperty(lastModPropName) && isIncludeLastModified
        && Objects.nonNull(node.getProperty(lastModPropName))) {
      final Calendar cal = node.getProperty(lastModPropName).getDate();
      if (Objects.nonNull(cal)) {
        writeElement(stream, GenericSiteMapService.LAST_MODIFIED_PROPERTY,
            GenericSiteMapService.DATE_FORMAT.format(cal));
      }
    }

    if (StringUtils.isNotBlank(priority)) {
      writeElement(stream, GenericSiteMapService.PRIORITY_PROPERTY, priority);
    }

    if (StringUtils.isNotBlank(frequency)) {
      writeElement(stream, GenericSiteMapService.CHANGE_FREQUENCY_PROPERTY, frequency);
    }
  }

  /**
   * This method reads the properties from either current page or parent page
   * based on flag includeInheritance (true or false)
   * 
   * @param page
   *            Root Page
   * @param stream
   *            XML Stream Writer
   * @param siteConfig
   *            siteConfig object
   * @param frequency
   *            frequency attribute
   * @param priority
   *            priority attribute
   * @throws XMLStreamException
   */
  private void setInheritanceValueMap(Page page, XMLStreamWriter stream, SiteConfig siteConfig, String frequency,
      String priority) throws XMLStreamException {
    if (siteConfig.isIncludeInheritance()) {
      final HierarchyNodeInheritanceValueMap hierarchyNodeInheritanceValueMap = new HierarchyNodeInheritanceValueMap(
          page.getContentResource());
      writeFirstPropertyValue(stream, GenericSiteMapService.CHANGE_FREQUENCY_PROPERTY,
          siteConfig.getChangeFreqProperties(), hierarchyNodeInheritanceValueMap, frequency);
      writeFirstPropertyValue(stream, GenericSiteMapService.PRIORITY_PROPERTY, siteConfig.getPriorityProperties(),
          hierarchyNodeInheritanceValueMap, priority);
    } else {
      final ValueMap properties = page.getProperties();
      writeFirstPropertyValue(stream, GenericSiteMapService.CHANGE_FREQUENCY_PROPERTY,
          siteConfig.getChangeFreqProperties(), properties, frequency);
      writeFirstPropertyValue(stream, GenericSiteMapService.PRIORITY_PROPERTY, siteConfig.getPriorityProperties(),
          properties, priority);
    }
  }

  /**
   * This method sets the PageURL object based on what kind URL is configured
   * for the page. Vanity, recirectURL etc
   * 
   * @param page
   *            Root Page
   * @param pagePath
   *            page path
   * @param sc
   *            SiteConfig object
   * @return
   */
  private PageUrl setPagePath(Page page, String pagePath, SiteConfig sc) {
    final PageUrl pageUrl = new PageUrl(false, pagePath);

    if (StringUtils.isNotBlank(page.getVanityUrl())) {
      final String vanityUrl = page.getVanityUrl();
      final List<BasePredicate> predicates = sc.getPredicates();

      for (final BasePredicate predicate : predicates) {
        if (predicate.test(page)) {
          final PageUrl overridden = new PageUrl(true, predicate.transformUrl(page));
          overridden.setDefaultPriority(predicate.getOverridePriority());
          overridden.setDefaultFrequency(predicate.getOverrideFrequency());
          overridden.setIncludeLastModified(predicate.getOverrideIsLastModified());
          return overridden;
        }
      }

      return new PageUrl(false, vanityUrl);
    } else if (!sc.isExtensionlessUrls()) {
      return new PageUrl(false, String.format("%s.html", page.getPath()));
    } else if (sc.isRemoveTrailingSlash()) {
      return new PageUrl(false, String.format("%s", page.getPath()));
    }

    return pageUrl;
  }

  /**
   * This method iterates the pages recursively and sets the properties of
   * individual URL object of respective page
   * 
   * @param page
   *            Root Page
   * @param stream
   *            XML Stream Writer
   * @param resolver
   *            Resource Resolver
   * @param sc
   *            SiteConfig object
   * @throws XMLStreamException
   */
  private void writePageTree(Page page, XMLStreamWriter stream, ResourceResolver resolver, SiteConfig sc)
      throws XMLStreamException {
    if (isHidden(page, sc)) {
      return;
    }
    if (isStopped(page, sc)) {
      extractElement(page, stream, resolver, sc);
    } else if (isSkipped(page, sc) || checkAdditionSkipProperty(page, sc)) {
      if (page.listChildren().hasNext()) {
        for (final Iterator<Page> children = page.listChildren(); children.hasNext();) {
          writePageTree(children.next(), stream, resolver, sc);
        }
      }
    } else {
      extractElement(page, stream, resolver, sc);
      if (page.listChildren().hasNext()) {
        for (final Iterator<Page> children = page.listChildren(); children.hasNext();) {
          writePageTree(children.next(), stream, resolver, sc);
        }
      }
    }
  }

  /**
   * This method sets the properties of individual URL object of respective
   * page
   * 
   * @param page
   *            Root Page
   * @param stream
   *            XML Stream Writer
   * @param resolver
   *            Resource Resolver
   * @param sc
   *            SiteConfig object
   * @throws XMLStreamException
   */
  private void write(Page page, XMLStreamWriter stream, ResourceResolver resolver, SiteConfig sc)
      throws XMLStreamException {
    if (isHidden(page, sc)) {
      return;
    }
    extractElement(page, stream, resolver, sc);
  }

  /**
   * @param page
   * @param stream
   * @param resolver
   * @param sc
   * @throws XMLStreamException
   */
  private void extractElement(Page page, XMLStreamWriter stream, ResourceResolver resolver, SiteConfig sc)
      throws XMLStreamException {
    stream.writeStartElement(GenericSiteMapService.NS, "url");
    String pagePath = page.getPath();

    if (null != resolver) {
      pagePath = resolver.map(pagePath);
    }

    final PageUrl pageUrl = setPagePath(page, pagePath, sc);
    final String frequency = Optional.ofNullable(pageUrl.getDefaultFrequency()).orElse(sc.getDefaultFrequency());
    final String priority = Optional.ofNullable(pageUrl.getDefaultPriority()).orElse(sc.getDefaultPriority());
    final String loc = externalizer.externalLink(resolver, sc.getExternalizerDomain(),
        pageUrl.getUrl().startsWith("/") ? pageUrl.getUrl() : "/" + pageUrl.getUrl());

    writeElement(stream, GenericSiteMapService.LOCATION_PROPERTY, loc);

    if (sc.isIncludeLastModified() || pageUrl.isIncludeLastModified()) {
      final Calendar cal = page.getLastModified();
      if (cal != null) {
        writeElement(stream, GenericSiteMapService.LAST_MODIFIED_PROPERTY,
            GenericSiteMapService.DATE_FORMAT.format(cal));
      }
    }

    setInheritanceValueMap(page, stream, sc, frequency, priority);
    stream.writeEndElement();
  }

  /**
   * The method writes the element of the individual page to the sitemap xml
   * 
   * @param stream
   *            XMLStreamWriter
   * @param elementName
   *            sitmap element name
   * @param text
   *            sitmap element name
   * @throws XMLStreamException
   */
  private void writeElement(final XMLStreamWriter stream, final String elementName, final String text)
      throws XMLStreamException {
    stream.writeStartElement(GenericSiteMapService.NS, elementName);
    stream.writeCharacters(text);
    stream.writeEndElement();
  }

  /**
   * This method reads the first element of the property config array when
   * Inheritance is enabled. Considers default value as well
   * 
   * @param stream
   *            XML Stream Writer
   * @param elementName
   *            element Name
   * @param propertyNames
   *            property Names
   * @param properties
   *            properties
   * @param defaultValue
   *            defaultValue
   * @throws XMLStreamException
   */
  private void writeFirstPropertyValue(final XMLStreamWriter stream, final String elementName,
      final List<String> propertyNames, final InheritanceValueMap properties, String defaultValue)
      throws XMLStreamException {
    boolean found = false;

    for (final String prop : propertyNames) {
      String value = properties.get(prop, String.class);
      if (value == null) {
        value = properties.getInherited(prop, String.class);
      }
      if (value != null) {
        found = true;
        writeElement(stream, elementName, value);
        break;
      }
    }

    if (StringUtils.isNotBlank(defaultValue) && !found) {
      writeElement(stream, elementName, defaultValue);
    }
  }

  /**
   * This method reads the first element of the property config array when
   * Inheritance is disabled. does consider default value
   * 
   * @param stream
   *            XML Stream Writer
   * @param elementName
   *            element Name
   * @param propertyNames
   *            property Names
   * @param properties
   *            properties
   * @param defaultValue
   * @throws XMLStreamException
   */
  private void writeFirstPropertyValue(final XMLStreamWriter stream, final String elementName,
      final List<String> propertyNames, final ValueMap properties, String defaultValue)
      throws XMLStreamException {
    boolean found = false;

    for (final String prop : propertyNames) {
      final String value = properties.get(prop, String.class);
      if (value != null) {
        found = true;
        writeElement(stream, elementName, value);
        break;
      }
    }

    if (StringUtils.isNotBlank(defaultValue) && !found) {
      writeElement(stream, elementName, defaultValue);
    }
  }
}
