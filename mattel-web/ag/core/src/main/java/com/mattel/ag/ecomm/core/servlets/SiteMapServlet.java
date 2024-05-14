package com.mattel.ag.ecomm.core.servlets;

import java.io.IOException;
import java.util.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

@Component(immediate = true, service = Servlet.class, property = { "sling.servlet.extensions=" + "xml",
        "sling.servlet.selectors=" + "sitemap", "sling.servlet.methods="
        + HttpConstants.METHOD_GET, }, configurationPid = "com.mattel.ag.ecomm.core.servlets.SiteMapServlet")
@Designate(ocd = SiteMapServlet.SiteMapConfig.class)

public final class SiteMapServlet extends SlingSafeMethodsServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(SiteMapServlet.class);

    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");

    private static final boolean DEFAULT_INCLUDE_LAST_MODIFIED = false;

    private static final boolean DEFAULT_INCLUDE_INHERITANCE_VALUE = false;

    private static final String DEFAULT_EXTERNALIZER_DOMAIN = "agdomainprod";

    private static final boolean DEFAULT_EXTENSIONLESS_URLS = false;

    private static final boolean DEFAULT_REMOVE_TRAILING_SLASH = false;

    private static final String NS = "http://www.sitemaps.org/schemas/sitemap/0.9";
    
    private static final String CHANGE_FREQUENCY_PROPERTY = "changefreq";
    
    private static final String PRIORITY_PROPERTY= "priority";

    @Reference
    private transient Externalizer externalizer;

    private String externalizerDomain;

    private String productdetailsProperty;

    private String productrootpathProperty;

    private String productdetailsPriority;

    private String productrootpathFrequency;

    private boolean includeInheritValue;

    private boolean includeLastModified;

    private String[] changefreqProperties;

    private String[] priorityProperties;

    private String damAssetProperty;

    private List<String> damAssetTypes;

    private String excludeFromSiteMapProperty;

    private String characterEncoding;

    private boolean extensionlessUrls;

    private boolean removeTrailingSlash;

    @Activate
    protected void activate(SiteMapConfig config) {

        LOG.info("Inside Sitemap activate method");

        this.externalizerDomain = PropertiesUtil.toString(config.externalizerDomain(), DEFAULT_EXTERNALIZER_DOMAIN);
        this.includeLastModified = PropertiesUtil.toBoolean(config.includeLastmod(), DEFAULT_INCLUDE_LAST_MODIFIED);
        this.includeInheritValue = PropertiesUtil.toBoolean(config.includeInherit(),
                DEFAULT_INCLUDE_INHERITANCE_VALUE);
        this.changefreqProperties = PropertiesUtil.toStringArray(config.changeFreqProperties(), new String[0]);
        this.priorityProperties = PropertiesUtil.toStringArray(config.priorityProperties(), new String[0]);
        this.damAssetProperty = PropertiesUtil.toString(config.damassetsProperty(), "");
        this.damAssetTypes = Arrays.asList(PropertiesUtil.toStringArray(config.damassetsTypes(), new String[0]));
        this.excludeFromSiteMapProperty = PropertiesUtil.toString(config.excludeProperty(),
                NameConstants.PN_HIDE_IN_NAV);
        this.characterEncoding = PropertiesUtil.toString(config.characterEncoding(), null);
        this.extensionlessUrls = PropertiesUtil.toBoolean(config.extensionlessUrls(), DEFAULT_EXTENSIONLESS_URLS);
        this.removeTrailingSlash = PropertiesUtil.toBoolean(config.removeSlash(), DEFAULT_REMOVE_TRAILING_SLASH);
        this.productdetailsProperty = PropertiesUtil.toString(config.productsDetails(), "");
        this.productrootpathProperty = PropertiesUtil.toString(config.productsRootPath(), "");
        this.productdetailsPriority = PropertiesUtil.toString(config.productsPriority(), "");
        this.productrootpathFrequency = PropertiesUtil.toString(config.productsFrequency(), "");

        LOG.info("Exiting Sitemap activate method");
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        LOG.info("Inside Sitemap doGet method");

        response.setContentType(request.getResponseContentType());
        if (StringUtils.isNotEmpty(this.characterEncoding)) {
            response.setCharacterEncoding(characterEncoding);
        }
        ResourceResolver resourceResolver = request.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (Objects.nonNull(pageManager)) {
            Page page = pageManager.getContainingPage(request.getResource());
            if (Objects.nonNull(page)) {
                XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
                try {
                    XMLStreamWriter stream = outputFactory.createXMLStreamWriter(response.getWriter());
                    stream.writeStartDocument("1.0");

                    stream.writeStartElement("", "urlset", NS);
                    stream.writeNamespace("", NS);

                    // first do the current page
                    write(page, stream, resourceResolver);

                    for (Iterator<Page> children = page.listChildren(new PageFilter(false, true), true); children
                            .hasNext();) {
                        write(children.next(), stream, resourceResolver);
                    }
                    fetchProductPagePath(request, resourceResolver, stream);
                    getAssetResouce(resourceResolver, page, stream);

                    stream.writeEndElement();

                    stream.writeEndDocument();
                } catch (XMLStreamException e) {
                    throw new IOException(e);
                }
            }
        }
        LOG.info("Exiting Sitemap doGet method");
    }

    private void getAssetResouce(ResourceResolver resourceResolver, Page page, XMLStreamWriter stream)
            throws XMLStreamException {
        if (!damAssetTypes.isEmpty() && damAssetProperty.length() > 0) {
            for (Resource assetFolder : getAssetFolders(page, resourceResolver)) {
                writeAssets(stream, assetFolder, resourceResolver);
            }
        }
    }

    private void fetchProductPagePath(SlingHttpServletRequest request, ResourceResolver resourceResolver,
            XMLStreamWriter stream) throws XMLStreamException {
        LOG.info("Sitemap servlet fetchProductPagePath Start");
        String pagePath = request.getRequestURI();
        if (pagePath.contains("shop") || pagePath.contains("en.sitemap.xml")) {
            Resource productdetails = resourceResolver.getResource(productdetailsProperty);
            if (Objects.nonNull(productdetails)) {
                Iterator<Resource> products = productdetails.listChildren();
                while (products.hasNext()) {
                    Resource r = products.next();
                    String productName = r.getName();
                    String productUrl = productrootpathProperty + "/" + productName;
                    writeProduct(productUrl, stream, productdetailsPriority, productrootpathFrequency,
                            resourceResolver);
                }
            }
        }
        LOG.info("Sitemap servlet fetchProductPagePath End");
    }

    private Collection<Resource> getAssetFolders(Page page, ResourceResolver resolver) {
        List<Resource> allAssetFolders = new ArrayList<>();
        ValueMap properties = page.getProperties();
        String[] configuredAssetFolderPaths = properties.get(damAssetProperty, String[].class);
        if (configuredAssetFolderPaths != null) {
            // Sort to aid in removal of duplicate paths.
            Arrays.sort(configuredAssetFolderPaths);
            String prevPath = "#";
            for (String configuredAssetFolderPath : configuredAssetFolderPaths) {
                // Ensure that this folder is not a child folder of another
                // configured folder, since it will already be included when
                // the parent folder is traversed.
                if (StringUtils.isNotBlank(configuredAssetFolderPath) && !configuredAssetFolderPath.equals(prevPath)
                        && !StringUtils.startsWith(configuredAssetFolderPath, prevPath + "/")) {
                    Resource assetFolder = resolver.getResource(configuredAssetFolderPath);
                    if (assetFolder != null) {
                        prevPath = configuredAssetFolderPath;
                        allAssetFolders.add(assetFolder);
                    }
                }
            }
        }
        return allAssetFolders;
    }

    @SuppressWarnings("squid:S1192")
    private void write(Page page, XMLStreamWriter stream, ResourceResolver resolver) throws XMLStreamException {
        if (isHidden(page)) {
            return;
        }

        stream.writeStartElement(NS, "url");
        String pagePath = page.getPath();
        if (null != resolver) {
            pagePath = resolver.map(pagePath);
        }
        pagePath = setPagePath(page, resolver, pagePath);

        String urlFormat = removeTrailingSlash ? "%s" : "%s/";
        String loc = externalizer.externalLink(resolver, externalizerDomain, String.format(urlFormat, pagePath));
        if (!extensionlessUrls) {
            loc = externalizer.externalLink(resolver, externalizerDomain, String.format("%s.html", pagePath));
        }

        writeElement(stream, "loc", loc);

        if (includeLastModified) {
            Calendar cal = page.getLastModified();
            if (cal != null) {
                writeElement(stream, "lastmod", DATE_FORMAT.format(cal));
            }
        }
        setInheritanceValueMap(page, stream);
        stream.writeEndElement();
    }

    private void setInheritanceValueMap(Page page, XMLStreamWriter stream) throws XMLStreamException {
        if (includeInheritValue) {
            HierarchyNodeInheritanceValueMap hierarchyNodeInheritanceValueMap = new HierarchyNodeInheritanceValueMap(
                    page.getContentResource());
            writeFirstPropertyValue(stream, CHANGE_FREQUENCY_PROPERTY, changefreqProperties, hierarchyNodeInheritanceValueMap);
            writeFirstPropertyValue(stream, PRIORITY_PROPERTY, priorityProperties, hierarchyNodeInheritanceValueMap);
        } else {
            ValueMap properties = page.getProperties();
            writeFirstPropertyValue(stream, CHANGE_FREQUENCY_PROPERTY, changefreqProperties, properties);
            writeFirstPropertyValue(stream, PRIORITY_PROPERTY, priorityProperties, properties);
        }
    }

    private String setPagePath(Page page, ResourceResolver resolver, String pagePath) {
        if(pagePath.startsWith("/content") && null!=resolver){
            if (StringUtils.isNotBlank(page.getVanityUrl())) {
                pagePath = page.getVanityUrl();
            } else if (null != page.getProperties() && page.getProperties().containsKey("cq:redirectTarget")) {
                pagePath = page.getProperties().get("cq:redirectTarget", String.class);
            }
        }
        return pagePath;
    }

    @SuppressWarnings("squid:S1192")
    private void writeProduct(String page, XMLStreamWriter stream, String productdetailsPriority, String productrootpathFrequency,  ResourceResolver resolver) throws XMLStreamException {


        stream.writeStartElement(NS, "url");
        String loc = externalizer.externalLink(resolver, externalizerDomain, page);

        writeElement(stream, "loc", loc);
        writeElement(stream, CHANGE_FREQUENCY_PROPERTY, productrootpathFrequency);
        writeElement(stream, PRIORITY_PROPERTY, productdetailsPriority);

        stream.writeEndElement();
    }

    private boolean isHidden(final Page page) {
        return page.getProperties().get(this.excludeFromSiteMapProperty, false);
    }

    private void writeAsset(Asset asset, XMLStreamWriter stream, ResourceResolver resolver) throws XMLStreamException {
        stream.writeStartElement(NS, "url");

        String loc = externalizer.externalLink(resolver, externalizerDomain, asset.getPath());
        writeElement(stream, "loc", loc);

        if (includeLastModified) {
            long lastModified = asset.getLastModified();
            if (lastModified > 0) {
                writeElement(stream, "lastmod", DATE_FORMAT.format(lastModified));
            }
        }
        
        Resource assetResource= asset.adaptTo(Resource.class);
        if (Objects.nonNull(assetResource)) {
            Resource contentResource = assetResource.getChild(JcrConstants.JCR_CONTENT);
            if (Objects.nonNull(contentResource)) {
                if (includeInheritValue) {
                    HierarchyNodeInheritanceValueMap hierarchyNodeInheritanceValueMap = new HierarchyNodeInheritanceValueMap(
                            contentResource);
                    writeFirstPropertyValue(stream, CHANGE_FREQUENCY_PROPERTY, changefreqProperties,
                            hierarchyNodeInheritanceValueMap);
                    writeFirstPropertyValue(stream, PRIORITY_PROPERTY, priorityProperties, hierarchyNodeInheritanceValueMap);
                } else {
                    ValueMap properties = contentResource.getValueMap();
                    writeFirstPropertyValue(stream, CHANGE_FREQUENCY_PROPERTY, changefreqProperties, properties);
                    writeFirstPropertyValue(stream, PRIORITY_PROPERTY, priorityProperties, properties);
                }
            }
        }
        stream.writeEndElement();
    }

    private void writeAssets(final XMLStreamWriter stream, final Resource assetFolder, final ResourceResolver resolver)
            throws XMLStreamException {
        for (Iterator<Resource> children = assetFolder.listChildren(); children.hasNext();) {
            Resource assetFolderChild = children.next();
            if (assetFolderChild.isResourceType(DamConstants.NT_DAM_ASSET)) {
                Asset asset = assetFolderChild.adaptTo(Asset.class);

                if (Objects.nonNull(asset) && damAssetTypes.contains(asset.getMimeType())) {
                    writeAsset(asset, stream, resolver);
                }
            } else {
                writeAssets(stream, assetFolderChild, resolver);
            }
        }
    }

    private void writeFirstPropertyValue(final XMLStreamWriter stream, final String elementName,
                                         final String[] propertyNames, final ValueMap properties) throws XMLStreamException {
        for (String prop : propertyNames) {
            String value = properties.get(prop, String.class);
            if (value != null) {
                writeElement(stream, elementName, value);
                break;
            }
        }
    }

    private void writeFirstPropertyValue(final XMLStreamWriter stream, final String elementName,
                                         final String[] propertyNames, final InheritanceValueMap properties) throws XMLStreamException {
        for (String prop : propertyNames) {
            String value = properties.get(prop, String.class);
            if (value == null) {
                value = properties.getInherited(prop, String.class);
            }
            if (value != null) {
                writeElement(stream, elementName, value);
                break;
            }
        }
    }

    private void writeElement(final XMLStreamWriter stream, final String elementName, final String text)
            throws XMLStreamException {
        stream.writeStartElement(NS, elementName);
        stream.writeCharacters(text);
        stream.writeEndElement();
    }

    @ObjectClassDefinition(name = "AG - Site Map Configuration", description = "AG - Site Map Configuration")
    public @interface SiteMapConfig {

        @AttributeDefinition(name = "Sling Resource Type", description = "Sling Resource Type for the Home Page component or components.", type = AttributeType.STRING)
        String[] slingServletResourceTypes();

        @AttributeDefinition(name = "Externalizer Domain", description = "Must correspond to a configuration of the Externalizer component.", type = AttributeType.STRING)
        String externalizerDomain();

        @AttributeDefinition(name = "Product details path", description = "Provide the folder path under which products are stored.", type = AttributeType.STRING)
        String productsDetails();

        @AttributeDefinition(name = "Product Root Path", description = "Provide the path of the product with which the sitemap needs to be generated", type = AttributeType.STRING)
        String productsRootPath();

        @AttributeDefinition(name = "Product details Priority", description = "Provide the priority of the product pages with which the sitemap needs to be generated", type = AttributeType.STRING)
        String productsPriority();

        @AttributeDefinition(name = "Product details Frequency", description = "Provide the frequency of the product pages with which the sitemap needs to be generated", type = AttributeType.STRING)
        String productsFrequency();

        @AttributeDefinition(name = "Include Last Modified", description = "If true, the last modified value will be included in the sitemap.", type = AttributeType.BOOLEAN)
        boolean includeLastmod() default false;

        @AttributeDefinition(name = "Change Frequency Properties", description = "The set of JCR property names which will contain the change frequency value.", type = AttributeType.STRING)
        String[] changeFreqProperties();

        @AttributeDefinition(name = "Priority Properties", description = "The set of JCR property names which will contain the priority value.", type = AttributeType.STRING)
        String[] priorityProperties();

        @AttributeDefinition(name = "DAM Folder Property", description = "The JCR property name which will contain DAM folders to include in the sitemap.", type = AttributeType.STRING)
        String damassetsProperty();

        @AttributeDefinition(name = "DAM Asset MIME Types", description = "MIME types allowed for DAM assets.", type = AttributeType.STRING)
        String[] damassetsTypes();

        @AttributeDefinition(name = "Exclude from Sitemap Property", description = "The boolean [cq:Page]/jcr:content property name which indicates if the Page should be hidden from the Sitemap. Default value: hideInNav", type = AttributeType.STRING)
        String excludeProperty();

        @AttributeDefinition(name = "Include Inherit Value", description = "If true searches for the frequency and priority attribute in the current page if null looks in the parent.", type = AttributeType.BOOLEAN)
        boolean includeInherit() default false;

        @AttributeDefinition(name = "Extensionless URLs", description = "If true, page links included in sitemap are generated without .html extension and the path is included with a trailing slash, e.g. /content/geometrixx/en/.", type = AttributeType.BOOLEAN)
        boolean extensionlessUrls() default false;

        @AttributeDefinition(name = "Remove Trailing Slash from Extensionless URLs", description = "Only relevant if Extensionless URLs is selected.  If true, the trailing slash is removed from extensionless page links, e.g. /content/geometrixx/en.", type = AttributeType.BOOLEAN)
        boolean removeSlash() default false;

        @AttributeDefinition(name = "Character Encoding", description = "If not set, the container's default is used (ISO-8859-1 for Jetty)", type = AttributeType.STRING)
        String characterEncoding();


    }

}
