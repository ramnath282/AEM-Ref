package com.mattel.global.core.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.PropertyReaderUtils;
import com.mattel.global.master.core.model.UrlShorteningConfig;

/**
 * Service to shorten urls based on config present in
 * {@link PropertyReaderUtils#getShortneningConfigs()}. For e.g if config is
 * [corporate_en:from#/content/mattel/mattel-corporate/us/en-us/home|to#/en-us|removeHtmlSuffix#true]
 * Url http://localhost:4502/content/mattel/mattel-corporate/us/en-us/home.html
 * will be transformed to http://localhost:4502/en-us
 */
@Component(service = UrlShorteningService.class, immediate = true)
public class UrlShorteningService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UrlShorteningService.class);
  @Reference
  PropertyReaderUtils propertyReaderUtils;

  /**
   * Shorten the url.
   *
   * @param url
   *          The url to shorten.
   * @param urlShorteningConfig
   *          The url shortening config.
   * @return The shortened url.
   */
  public String transform(String url, UrlShorteningConfig urlShorteningConfig) {
    String newUrl = new StringBuilder(url).toString();
    final String from = urlShorteningConfig.getFrom();
    final String to = urlShorteningConfig.getTo();

    if (StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to) && newUrl.contains(from)) {
      newUrl = newUrl.replace(from, to);

      if (urlShorteningConfig.isRemoveHtmlSuffix()) {
        newUrl = newUrl.replace(".html", StringUtils.EMPTY);
      }
    }

    UrlShorteningService.LOGGER.debug("Transform method, url before: {}, shortening conf : {}, url after : {}",
        new Object[] { url, urlShorteningConfig, newUrl });
    return newUrl;
  }

  /**
   * Shorten the url.
   *
   * @param siteKey
   *          The site key for e.g "corporate_en", using which the shortening
   *          config is retrieved.
   * @param url
   *          The url to shorten.
   * @return The shortened url.
   */
  public String transform(String siteKey, String url) {
    final UrlShorteningConfig urlShorteningConfig = propertyReaderUtils.getShortneningConfig(siteKey);

    if (Objects.nonNull(urlShorteningConfig)) {
      return transform(url, urlShorteningConfig);
    }

    return url;
  }

  /**
   * Shorten the url.
   *
   * @param siteKey
   *          The site key for e.g "corporate_en", using which the shortening
   *          config is retrieved.
   * @param urls
   *          The List of urls to shorten.
   * @return The List of shortened urls.
   */
  public List<String> transform(String siteKey, List<String> urls) {
    final UrlShorteningConfig urlShorteningConfig = propertyReaderUtils.getShortneningConfig(siteKey);

    if (Objects.nonNull(urlShorteningConfig)) {
      return urls.stream() //
          .filter(StringUtils::isNotBlank) //
          .map(url -> transform(url, urlShorteningConfig)) //
          .collect(Collectors.toList()); //
    }

    return urls;
  }

  /**
   * Shorten the url.
   * 
   * @param siteKeyToUrl
   *          Map containing site key to urls mapping. The urls in the map would
   *          be shortened.
   * @return Map containing site key to shortened urls mapping.
   */
  public Map<String, String> transform(Map<String, String> siteKeyToUrl) {
    final Map<String, String> newSiteKeyToUrl = new HashMap<>();

    siteKeyToUrl.keySet().stream().forEach(k -> {
      final String url = siteKeyToUrl.get(k);

      newSiteKeyToUrl.put(k, transform(k, url));
    });

    if (siteKeyToUrl.size() == newSiteKeyToUrl.size()) {
      return newSiteKeyToUrl;
    }

    return siteKeyToUrl;
  }
}
