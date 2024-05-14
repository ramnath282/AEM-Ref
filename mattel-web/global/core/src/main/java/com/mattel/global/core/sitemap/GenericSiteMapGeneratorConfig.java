package com.mattel.global.core.sitemap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.global.core.services.GetResourceResolver;

@Component(service = GenericSiteMapGeneratorConfig.class)
@Designate(ocd = GenericSiteMapGeneratorConfig.Config.class)
public class GenericSiteMapGeneratorConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(GenericSiteMapGeneratorConfig.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final StringBuilder schemaFilePath = new StringBuilder();
  private final Map<String, SiteConfig> siteKeyToConfig = new ConcurrentHashMap<>();
  private final Map<String, SiteConfig> rootPathToConfig = new ConcurrentHashMap<>();
  @Reference
  private GetResourceResolver getResourceResolver;

  public void setGetResourceResolver(GetResourceResolver getResourceResolver) {
    this.getResourceResolver = getResourceResolver;
  }

  @ObjectClassDefinition(name = "Generic SiteMap Generator Configuration", //
      description = "Containing site specific site map configuration")
  public @interface Config {
    @AttributeDefinition(name = "Path of configuration schema json", //
        description = "Path of configuration schema json contatining site specific"
            + " site map configuration")
    String schemaPath() default "/content/dam/mattel/global/sitemap/sitemapconfig.json";
  }

  @Activate
  public void activate(Config config) {
    schemaFilePath.append(config.schemaPath());

    GenericSiteMapGeneratorConfig.LOGGER
        .info("Building generic site map schema config from path : {}", schemaFilePath);

    try (ResourceResolver resourceResolver = getResourceResolver.getResourceResolver()) {
      final Resource resource = resourceResolver.getResource(schemaFilePath.toString());
      InputStream is = null;

      if (Objects.nonNull(resource)) {
    	  final Asset asset = resource.adaptTo(Asset.class);
    	GenericSiteMapGeneratorConfig.LOGGER
          .debug("Building generic site map resource is not null and path is : {}", resource.getPath());
        if (Objects.nonNull(asset)) {
          is = asset.getOriginal().getStream();
        }
      }

      try (InputStream in = Optional.ofNullable(is)
          .orElse(getClass().getResourceAsStream("site_map_config.json"))) {
        final SiteMapGeneratorSchema siteMapGeneratorSchema = GenericSiteMapGeneratorConfig.MAPPER
            .readValue(in, SiteMapGeneratorSchema.class);
        final Map<String, SiteConfig> schema = siteMapGeneratorSchema.getSchema();
        siteKeyToConfig.putAll(schema);

        schema.keySet().stream().forEach(sk -> {
          final SiteConfig sc = siteKeyToConfig.get(sk);
          final int index = sk.indexOf('_');
          sc.setSiteKey(sk);
          sc.setSiteName(sk.substring(0, index));
          sc.setLocale(sk.substring(index + 1, sk.length()));
          final List<Map<String, String>> otherLocales = sc.getOtherLocales();
          buildRules(sc);
          rootPathToConfig.put(sc.getRootPath(), sc);
          if(Objects.nonNull(otherLocales) && !otherLocales.isEmpty()){
        	  otherLocales.stream().forEach(map -> populateOtherLocalConfig(sc, map));
          }
        });
        GenericSiteMapGeneratorConfig.LOGGER.info("Site map config initialized successfully");
      }
    } catch (final Exception io) {
      GenericSiteMapGeneratorConfig.LOGGER.error("Error while building site map schema config", io);
    }
  }

  /**
   * @param sc
   */
  private void buildRules(final SiteConfig sc) {
    final List<RewriteVanityRule> rules = sc.getRewriteVanityRules();
    final List<BasePredicate> predicates = new ArrayList<>();

    for (final RewriteVanityRule rule : rules) {
      setRewriteRules(sc, predicates, rule);
    }

    sc.setPredicates(predicates);
  }

  /**
   * @param sc
   * @param predicates
   * @param rule
   */
  private void setRewriteRules(final SiteConfig sc, final List<BasePredicate> predicates,
      final RewriteVanityRule rule) {
    Optional.ofNullable(rule.getForRegexPattern()).filter(StringUtils::isNotEmpty)
        .ifPresent(str -> predicates.add(new PatternPredicate(sc, rule)));
    Optional.ofNullable(rule.getForPageHierarchy()).filter(StringUtils::isNotEmpty)
        .ifPresent(str -> {
          str = str.replace("{site_key}", sc.getSiteKey());
          str = str.replace("{site_name}", sc.getSiteName());
          str = str.replace("{locale}", sc.getLocale());

          RewriteVanityRule vanityRule = new RewriteVanityRule(rule); 
          vanityRule.setForPageHierarchy(str);
          predicates.add(new PageHierarchyPredicate(sc, vanityRule));
        });
    Optional.ofNullable(rule.getForVanityUrl()).filter(StringUtils::isNotEmpty)
        .ifPresent(str -> predicates.add(new ExactMatchPredicate(sc, rule)));
  }

  /**
   * @param rule
   * @return
   */
  @SuppressWarnings("unused")
  private Consumer<? super String> compileAndSetPattern(RewriteVanityRule rule) {
    return regex -> {
      try {
        rule.setPattern(Pattern.compile(regex));
      } catch (final PatternSyntaxException pse) {
        GenericSiteMapGeneratorConfig.LOGGER
            .error("Error while regex pattern site map schema config", pse);
      }
    };
  }

  /**
   * @param sc
   * @param map
   */
  private void populateOtherLocalConfig(SiteConfig sc, Map<String, String> map) {
    final String siteKey = map.get("siteKey");
    final String rootPath = map.get("rootPath");

    final int index = siteKey.indexOf('_');
    SiteConfig clone = new SiteConfig(sc);
    clone.setSiteKey(siteKey);
    clone.setSiteName(siteKey.substring(0, index));
    clone.setLocale(siteKey.substring(index + 1, siteKey.length()));
    Optional.ofNullable(rootPath).ifPresent(clone::setRootPath);
    buildRules(clone);
    Optional.ofNullable(siteKey).ifPresent(sk -> siteKeyToConfig.put(sk, clone));
    Optional.ofNullable(rootPath).ifPresent(rp -> rootPathToConfig.put(rp, clone));
	
    
  }

  public SiteConfig getSiteMapConfig(String siteKey) {
    return siteKeyToConfig.get(siteKey);
  }

  public SiteConfig getSiteMapConfigByRootPath(String rootPath) {
    return rootPathToConfig.get(rootPath);
  }

  public SiteConfig getSiteMapConfigByRelPath(String path) {
    final Set<String> rootPaths = rootPathToConfig.keySet();

    for (final String rootPath : rootPaths) {
      if (path.startsWith(rootPath)) {
        return rootPathToConfig.get(rootPath);
      }
    }

    return null;
  }
}
