package com.mattel.ecomm.coreservices.core.utilities;

import java.net.HttpCookie;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.pojos.CookiePojo;

public class CookieUtils {

	private CookieUtils() {

	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CookieUtils.class);
    private static final DateTimeFormatter COOKIE_EXPIRY_ATTR_DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME;

	/**
	 * This method reads the cookie attributes from cookiepojo and returns the
	 * Cookie Object with all the parameters.
	 * 
	 * @param cookiepojo
	 *            cookiepojo object containing all the cookie related attributes
	 * @return Cookie Cookie Object
	 */
	public static Cookie getCookie(CookiePojo cookiepojo) {
		LOGGER.info("Start of getCookie method");
		long startTime = System.currentTimeMillis();
		Cookie wcsCookie = null;
		if (null != cookiepojo.getCookieName()) {
			wcsCookie = new Cookie(cookiepojo.getCookieName(), cookiepojo.getCookieValue());
			wcsCookie.setDomain(cookiepojo.getDomain());
			wcsCookie.setPath(cookiepojo.getCookiePath());
			if (cookiepojo.isSecure()) {
				wcsCookie.setSecure(true);
			}
			if (cookiepojo.isHttpOnly()) {
				wcsCookie.setComment("; HttpOnly;");
			}
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getCookie", endTime - startTime);
		LOGGER.info("End of getCookie method");
		return wcsCookie;
	}

	/**
	 * This method accepts HeaderElement which contains Cookie information,
	 * reads all the element properties and sets them in CookiePojo object
	 * 
	 * @param headerElement
	 *            HeaderElement containing Cookie information
	 * @return CookiePojo CookiePojo object
	 */
	public static CookiePojo getCookiePojo(HeaderElement headerElement, String domain) {
		LOGGER.info("Start of getCookiePojo method");
		long startTime = System.currentTimeMillis();
		CookiePojo cookiepojo = new CookiePojo();
		LOGGER.debug("Cookie Name retrived from header is {}", headerElement.getName());
		cookiepojo.setCookieName(headerElement.getName());
		LOGGER.debug("Cookie Value retrived from header is {}", headerElement.getValue());
		cookiepojo.setCookieValue(headerElement.getValue());
		cookiepojo.setDomain(domain);
		NameValuePair[] nameValPair = headerElement.getParameters();
		if (null != nameValPair) {
			for (NameValuePair cookiestr : nameValPair) {
				if (StringUtils.equals(Constant.COOKIE_PATH, cookiestr.getName())) {
					cookiepojo.setCookiePath(cookiestr.getValue());
				} else if (StringUtils.equals(Constant.COOKIE_HTTP_ONLY, cookiestr.getName())) {
					cookiepojo.setHttpOnly(true);
				} else if (StringUtils.equals(Constant.COOKIE_SECURE, cookiestr.getName()) && !StringUtils.equalsIgnoreCase(domain, "localhost")) {
					cookiepojo.setSecure(true);
                }
			}
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getCookiePojo", endTime - startTime);
		LOGGER.info("End of getCookiePojo method");
		return cookiepojo;
	}

	/**
	 *
	 * @param cookies
	 *            Array of cookies received from Request
	 * @param cookiesNames
	 *            Array of Valid service cookie names
	 * @return cookieList list of cookie objects
	 */
	public static List<Cookie> getServiceCookieListFromArray(Cookie[] cookies, String[] cookiesNames) {
		LOGGER.info("Start of getServiceCookieListFromArray method");
		long startTime = System.currentTimeMillis();
		List<Cookie> cookieList = new ArrayList<>();
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				boolean isValidServiceCookie = checkValidServiceCookie(cookie.getName(), cookiesNames);
				if (isValidServiceCookie) {
					cookieList.add(cookie);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getCookieListFromArray", endTime - startTime);
		LOGGER.info("End of getServiceCookieListFromArray method");
		return cookieList;
	}

	/**
	 * This method checks Cookies received as part of request/response are valid
	 * ones across the defined set of valid cookie names
	 * 
	 * @param cookieName
	 *            cookie Name
	 * @param validCookiesNames
	 *            Array of valid service cookie names
	 * @return validCookie validCookie flag, true if it is found in defined set
	 *         of valid cookie names
	 */
	public static boolean checkValidServiceCookie(String cookieName, String[] validCookiesNames) {
		boolean validCookie = false;
		validCookie = Arrays.stream(validCookiesNames).anyMatch(cookieName::startsWith);
		return validCookie;
	}

	/**
	 * This method accepts Cookie Enum object from request, filters the cookies
	 * across defined valid service cookie names and returns List of string
	 * representation of Cookie object
	 * 
	 * @param cookieEnum
	 *            Enum containing string representation of all the Cookie
	 *            headers from request
	 * @param cookiesNames
	 *            defined set of valid service cookie names
	 * @return reqCookies List of string representation of Cookie object
	 */
	public static List<String> getRequestCookieList(Cookie[] cookies, String[] cookiesNames) {
		LOGGER.info("Start of getRequestCookieList method");
		long startTime = System.currentTimeMillis();
		List<String> reqCookies = new ArrayList<>();
		if(null!=cookies && cookies.length>0){
			for (Cookie cookie : cookies){
				boolean isValidServiceCookie = checkValidServiceCookie(cookie.getName(), cookiesNames);
				if(isValidServiceCookie){
					HttpCookie httpCookie = new HttpCookie(cookie.getName(), cookie.getValue());
					httpCookie.setDomain(cookie.getDomain());
					httpCookie.setPath(cookie.getPath());
					httpCookie.setSecure(cookie.getSecure());
					if (null!=cookie.getComment() && cookie.getComment().contains(Constant.COOKIE_HTTP_ONLY)) {
						httpCookie.setHttpOnly(true);
					}
					String strHttpCookie = httpCookie.toString().replace(";$", "; ");
					reqCookies.add(strHttpCookie);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getReqCookieListFromArray", endTime - startTime);
		LOGGER.info("End of getRequestCookieList method");
		return reqCookies;
	}
	
	public static void replaceCookieFromList(Cookie oldCookie, Cookie newCookie, List<Cookie> cookieList){
		cookieList.remove(oldCookie);
		cookieList.add(newCookie);
	}

    /**
     * Computes Cookie "MaxAge" attribute from Cookie "Expires" attribute.
     *
     * @param expiryDate
     *          An expiry date for when a cookie gets deleted.
     * @return MaxAge
     *          The time in seconds for when a cookie will be deleted.
     */
    @SuppressWarnings("unused")
    private static int computeCookieMaxAge(final String expiryDate) {
        try {
            final TemporalAccessor temoralAccessor = CookieUtils.COOKIE_EXPIRY_ATTR_DATE_FORMAT.parse(expiryDate);
            final Instant expiryTime = Instant.from(temoralAccessor);

            return (int) Duration.between(Instant.now(), expiryTime).getSeconds();
        } catch (RuntimeException e) {
            LOGGER.error("Unable to parse expiry date", e);
            return -1;
        }
    }

    /**
     * Using cookieStore to build list of {@link Cookie}. CookieStore would store the values of Response
     * Set-Cookie header. 
     *
     * @param cookieNames
     *          Valid cookie names to process.
     * @param domain
     *          Domain of application.
     * @param responseCookies
     *          Cookie received from cookieStore, containing cookie name value pair and cookie attributes.
     * @return {@link List} of {@link Cookie} to be sent to user agent.
     */
    public static List<Cookie> constructCookieList(String[] cookieNames, String domain,
            List<org.apache.http.cookie.Cookie> responseCookies) {
        final List<Cookie> cookies = new ArrayList<>();

        if (null != responseCookies) {
            for (final org.apache.http.cookie.Cookie respCookie : responseCookies) {
                final BasicClientCookie basicClientCookie = (BasicClientCookie) respCookie;
                final boolean isValidServiceCookie = CookieUtils.checkValidServiceCookie(basicClientCookie.getName(),
                        cookieNames);

                if (isValidServiceCookie) {
                    final Cookie wcsCookie = CookieUtils.mapWCSCookie(domain, basicClientCookie);

                    CookieUtils.LOGGER.debug("wcsCookie Name: {}, Value: {}, Comment: {}",
                            new Object[] { wcsCookie.getName(), wcsCookie.getValue(),
                                    Optional.ofNullable(wcsCookie.getComment()).orElse("") });
                    cookies.add(wcsCookie);
                }
            }
        }

        return cookies;
    }

    /**
     * Map WCS Response Set-Cookie to {@link Cookie}.
     */
    private static Cookie mapWCSCookie(String domain, final BasicClientCookie basicClientCookie) {
        final Cookie wcsCookie = new Cookie(basicClientCookie.getName(), basicClientCookie.getValue());

        if (StringUtils.equalsIgnoreCase(basicClientCookie.getName(), "JSESSIONID")) {
            wcsCookie.setPath("/");
        } else {
            wcsCookie.setPath(basicClientCookie.getPath());
        }

        if (!StringUtils.equalsIgnoreCase(domain, "localhost")) {
            wcsCookie.setSecure(basicClientCookie.isSecure());
        }

        if (basicClientCookie.containsAttribute("HttpOnly") || basicClientCookie.containsAttribute("httponly")) {
            wcsCookie.setComment("; HttpOnly;");
        }

        if (!StringUtils.isEmpty(basicClientCookie.getAttribute("expires"))) {
            wcsCookie.setMaxAge(CookieUtils.computeCookieMaxAge(basicClientCookie.getExpiryDate()));
        }

        if (!StringUtils.isEmpty(domain)) {
          wcsCookie.setDomain(domain);
        }

        return wcsCookie;
    }

    /**
     * Computes Cookie "MaxAge" attribute from Cookie "Expires" attribute.
     *
     * @param expiryDate
     *            An expiry date for when a cookie gets deleted.
     * @return MaxAge The time in seconds for when a cookie will be deleted.
     */
    private static int computeCookieMaxAge(final Date expiryDate) {
        try {
            return (int) Duration.between(Instant.now(), expiryDate.toInstant()).getSeconds();
        } catch (final RuntimeException e) {
            CookieUtils.LOGGER.error("Unable to parse expiry date", e);
            return -1;
        }
    }

    public static List<String> buildWCSRequestCookies(Cookie[] requestCookies, String[] validCookiesNames) {
        final List<String> wcsRequestCookies = new ArrayList<>();

        if (null != requestCookies && requestCookies.length > 0){
            for (final Cookie reqCookie : requestCookies) {
                if (CookieUtils.checkValidServiceCookie(reqCookie.getName(), validCookiesNames)) {
                    wcsRequestCookies.add(CookieUtils.buildCookieHeaderValue(reqCookie));
                }
            }
        }

        return wcsRequestCookies;
    }

    /**
     * Checks if the given user is a guest user.
     *
     * @param requestCookies
     *          The incoming user request cookies.
     * @return True, if the incoming user is a guest user.
     */
    public static boolean checkIfGuestUser(final Cookie[] requestCookies) {
      final String[] validCookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final Map<String, String> cookieNameToValue = Arrays.stream(requestCookies)
          .filter(c -> CookieUtils.checkValidServiceCookie(c.getName(), validCookieNames))
          .collect(Collectors.toMap(Cookie::getName, Cookie::getValue,
              (existing, replacement) -> replacement));
      final boolean isAuthCookie = cookieNameToValue.keySet().stream()
          .anyMatch(k -> k.startsWith("WC_AUTHENTICATION_") && !"WC_AUTHENTICATION_-1002".equals(k));

      return !cookieNameToValue.containsKey("MATTEL_WELCOME_MSG") && isAuthCookie;
    }

    /**
     * Build name=value pair to be sent in Cookie header.
     */
    public static String buildCookieHeaderValue(Cookie cookie) {
        return new StringBuilder().append(cookie.getName()).append("=").append(cookie.getValue()).toString();
    }

    public static void updateRequestMap(Map<String, Object> requestMap, List<Cookie> responseCookieList,
        String[] cookieNames) {
      if (null != responseCookieList && !responseCookieList.isEmpty()) {
        Cookie[] reqCookies = CookieUtils.getRequestCookieListFromResponse(requestMap, responseCookieList, cookieNames);
        requestMap.replace(Constant.REQUEST_COOKIES_KEY, reqCookies);
      }
    }

    /**
     * This methods accepts List of Cookies received from response and converts
     * that into List of string representation of those cookie objects
     * 
     * @param responseCookieList
     *            List of Cookies received from response
     * @return reqCookies List of string representation of Cookie object
     */
    public static Cookie[] getRequestCookieListFromResponse(Map<String, Object> requestMap,List<Cookie> responseCookieList,String[] cookieNames) {
      List<Cookie> updatedRespCookieList = responseCookieList;
      LOGGER.info("Start of getRequestCookieListFromResponse method, cookies size : {}", updatedRespCookieList.size());
      long startTime = System.currentTimeMillis();
      updatedRespCookieList = getUpdatedResponseCookieList(requestMap,responseCookieList);
      List<Cookie> reqCookies = new ArrayList<>();
      AtomicInteger atomicInteger = new AtomicInteger(0);
      responseCookieList.forEach(cookie -> {
        boolean isValidServiceCookie = CookieUtils.checkValidServiceCookie(cookie.getName(), cookieNames);
        if(isValidServiceCookie){
          reqCookies.add(cookie);
          atomicInteger.getAndIncrement();
        }
      });
      Cookie[] cookies = reqCookies.toArray(new Cookie[atomicInteger.get()]);
      long endTime = System.currentTimeMillis();
      LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getRequestCookieListFromResponse", endTime - startTime);
      LOGGER.info("End of getRequestCookieListFromResponse method, cookies size: {}", updatedRespCookieList);
      return cookies;
    }
    
    /**
     * This method will replace the Cookies in request map with the cookies received in response map
     * @param requestMap request map of the service
     * @param responseCookieList List of cookies received from response
     * @return the updated cookie list which needs to be sent the next Service of Composite Service
     */
    private  static List<Cookie> getUpdatedResponseCookieList(Map<String, Object> requestMap,
        List<Cookie> responseCookieList) {
      LOGGER.info("Start of getUpdatedResponseCookieList method");
      long startTime = System.currentTimeMillis();
      Cookie[] requestCookies = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      List<Cookie> requestCookieList = Arrays.asList(requestCookies);
      List<Cookie> updatedResponseCookieList = new ArrayList<>(requestCookieList);
      requestCookieList.forEach(reqCookie -> 
        responseCookieList.forEach(cookie -> {
          if(StringUtils.equals(cookie.getName(), reqCookie.getName())){
            CookieUtils.replaceCookieFromList(reqCookie, cookie, updatedResponseCookieList);
          }
        })
      );
      long endTime = System.currentTimeMillis();
      LOGGER.debug(Constant.EXECUTION_TIME_LOG, "getUpdatedResponseCookieList", endTime - startTime);
      LOGGER.info("End of getUpdatedResponseCookieList method");
      return updatedResponseCookieList;
    }
}
