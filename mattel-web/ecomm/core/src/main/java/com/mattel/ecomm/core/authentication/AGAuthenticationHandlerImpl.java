package com.mattel.ecomm.core.authentication;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattel.ecomm.core.services.ProxyService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.auth.core.AuthConstants;
import org.apache.sling.auth.core.spi.AuthenticationFeedbackHandler;
import org.apache.sling.auth.core.spi.AuthenticationHandler;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.auth.core.spi.DefaultAuthenticationFeedbackHandler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@Component(service = AuthenticationHandler.class, immediate = true, property = {"path=/test"})
@Designate(ocd = AGAuthenticationHandlerImpl.Config.class)
@ServiceRanking(60000)
public class AGAuthenticationHandlerImpl extends DefaultAuthenticationFeedbackHandler
        implements AuthenticationHandler, AuthenticationFeedbackHandler, AGAuthenticationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AGAuthenticationHandlerImpl.class);
    private static final String AGAUTHHANDLER = "AGAuthenticationHandler";

    @Reference
    private PropertyReaderService propertyReaderService;
    @Reference
    private ResourceResolverFactory resolverFactory;
    @Reference
    private SlingRepository repository;
    @Reference
    private ProxyService proxyService;

    // Cookie Names
    private String wcActivepointer = "WC_ACTIVEPOINTER";
    private String wcAuthentication = "WC_AUTHENTICATION_";
    private String wcSessionEstablished = "WC_SESSION_ESTABLISHED";
    private String wcUseractivity = "WC_USERACTIVITY_";
    private String wcPersistant = "WC_PERSISTANT";
    private String loginpage;
    private String dummyUserId;
    private String encryptCode;
    private String service = "readwriteservice";
    private String serviceUser = "ecommserviceuser";
    private String responsebody = "responsebody";
    private List<String> mandatoryCookies = Arrays.asList(wcActivepointer, wcAuthentication,
        wcSessionEstablished, wcUseractivity, wcPersistant);

    private AuthenticationInfo createAuthenticationInfo() {
        AuthenticationInfo authenticationInfo = new AuthenticationInfo("CUST_AUTH");
        String dummyPass = encryptCode;
        if(dummyPass != null) {
            authenticationInfo = new AuthenticationInfo(HttpServletRequest.FORM_AUTH, dummyUserId, dummyPass.toCharArray());
            if (!authenticationInfo.isEmpty()) {
                authenticationInfo.put(AuthConstants.AUTH_INFO_LOGIN, new Object());
            }
        }
        return authenticationInfo;
    }

    private boolean checkForValidCookies(HttpServletRequest httpServletRequest) {
      AGAuthenticationHandlerImpl.LOGGER.debug("checkForValidCookies");
      final Cookie[] cookies = httpServletRequest.getCookies();

      if (Objects.isNull(cookies) || cookies.length == 0) {
        return false;
      }

      final List<String> cookieList = new ArrayList<>();
      boolean sessionEstablished = false;
      AGAuthenticationHandlerImpl.LOGGER.debug("Authentication Handler :: {}", cookies.length);

      for (final Cookie cookie : cookies) {
        AGAuthenticationHandlerImpl.LOGGER.debug("Authentication for valid cookie :: {}",
            cookie.getName());
        if (wcSessionEstablished.equals(cookie.getName())) {
          sessionEstablished = true;
        }
      }

      if (sessionEstablished) {
        for (final Cookie cookie : cookies) {
          AGAuthenticationHandlerImpl.LOGGER.debug("cookies in second for loop :: {}",
              cookie.getName());
          if (mandatoryCookies.contains(cookie.getName())) {
            cookieList.add(cookie.getName());
          }
        }

        return true;
      }

      return false;
    }

    private void removeCookies(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(wcSessionEstablished)
                        || cookie.getName().contains(wcAuthentication)
                        || cookie.getName().equals(wcActivepointer)
                        || cookie.getName().contains(wcActivepointer)) {
                    cookie.setMaxAge(0);
                    // remove cookie
                    httpServletResponse.addCookie(cookie);
                }
            }
        }
    }

    private boolean checkForValidSession(HttpServletRequest httpServletRequest) {
        long startTime = System.currentTimeMillis();
        String storeId;
        String domainKey;
        String storeKey;

        try (ResourceResolver resolver = getServiceUserForAG()) {
                Resource resource = resolver.getResource(httpServletRequest.getRequestURI().replace(".html", ""));
                AGAuthenticationHandlerImpl.LOGGER.debug("resource {}", resource);
                if (resource != null) {
                    InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
                    storeKey = inheritanceValueMap.getInherited("siteKey", String.class);
                    domainKey = inheritanceValueMap.getInherited("siteKey", String.class);
                    if (!StringUtils.isEmpty(storeKey)) {
                        storeId = propertyReaderService.getStoreId(storeKey);
                        AGAuthenticationHandlerImpl.LOGGER.debug("storeKey is {}", storeKey);
                        AGAuthenticationHandlerImpl.LOGGER.debug("storeId is {}", storeId);
                        AGAuthenticationHandlerImpl.LOGGER.debug("domainKey is {}", domainKey);

                        final Cookie[] cookies = httpServletRequest.getCookies();
                        final Map<String, Object> requestMap = new HashMap<>();
                        requestMap.put(Constant.STORE_KEY, storeId);
                        requestMap.put(Constant.DOMAIN_KEY, domainKey);
                        requestMap.put(Constant.REQUEST_PARAM_GET_USER_TYPE, Constant.LOGGED_IN_USER_TYPE);
                        requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies);

                        Map<String, Object> responseMap = proxyService.sessionService(requestMap, Constant.SESSION_STATUS_SERVICE);
                        if (responseMap != null && responseMap.containsKey(responsebody)) {
                            Gson gson = new Gson();
                            final Type mapType = new TypeToken<Map<String, Object>>() {
                            }.getType();
                            final Map<String, Object> responseBody = gson
                                    .fromJson(responseMap.get(responsebody).toString(), mapType);
                            Predicate<Map<String, Object>> noErrors =  map -> Objects.isNull(map.get("errors")) && Objects.isNull(map.get("errorKey"));

                            if (noErrors.test(responseBody) && "R".equalsIgnoreCase(responseBody.get("type").toString()))
                                return true;
                        }
                    }
                } else {
                    AGAuthenticationHandlerImpl.LOGGER.debug("no resource found {}",resource);
                }
        } catch (LoginException e) {
            AGAuthenticationHandlerImpl.LOGGER
                    .error(String.format("Encountered error in %s:", AGAuthenticationHandlerImpl.AGAUTHHANDLER), e);
            AGAuthenticationHandlerImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, AGAuthenticationHandlerImpl.AGAUTHHANDLER,
                    System.currentTimeMillis() - startTime);
        }

        return false;
    }


    @Override
    public AuthenticationInfo extractCredentials(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.currentTimeMillis();
        try {
            if (checkForValidCookies(httpServletRequest)) {
                AGAuthenticationHandlerImpl.LOGGER.debug("Cookies are set");
                if (checkForValidSession(httpServletRequest)) {
                    AGAuthenticationHandlerImpl.LOGGER.debug("Valid session");
                    return createAuthenticationInfo();
                } else {
                    removeCookies(httpServletRequest, httpServletResponse);
                    failedLogin(httpServletResponse);
                }
            } else {
                removeCookies(httpServletRequest, httpServletResponse);
                failedLogin(httpServletResponse);
            }
        } catch (IOException e) {
            AGAuthenticationHandlerImpl.LOGGER
                    .error(String.format("Encountered error in %s:", AGAuthenticationHandlerImpl.AGAUTHHANDLER), e);
            AGAuthenticationHandlerImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, AGAuthenticationHandlerImpl.AGAUTHHANDLER,
                    System.currentTimeMillis() - startTime);
        }

        long endTime = System.currentTimeMillis();
        LOGGER.debug(Constant.EXECUTION_TIME_LOG, "handleWebModuleServices", endTime - startTime);
        return null;
    }

    private void failedLogin(HttpServletResponse httpServletResponse) throws IOException {
        AGAuthenticationHandlerImpl.LOGGER.debug("Failed Login");
        httpServletResponse.sendRedirect(loginpage);
        httpServletResponse.flushBuffer();
    }

    private ResourceResolver getServiceUserForAG() throws LoginException {
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, service);
        map.put(ResourceResolverFactory.USER, serviceUser);
        return resolverFactory.getServiceResourceResolver(map);
    }

    @Override
    public boolean requestCredentials(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse){
        AGAuthenticationHandlerImpl.LOGGER.debug("requestCredentials");
        return false;
    }

    @Override
    public void dropCredentials(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        AGAuthenticationHandlerImpl.LOGGER.debug("dropCredentials");
    }

    protected void bindRepository(SlingRepository paramSlingRepository) {
        this.repository = paramSlingRepository;
    }

    protected void unbindRepository(SlingRepository paramSlingRepository) {
        if (this.repository == paramSlingRepository) {
            this.repository = null;
        }
    }

    @Activate
    public void activate(final Config config) {
        loginpage = config.failurePage();
        dummyUserId = config.dummyUser();
        encryptCode = config.encryptCode();
        AGAuthenticationHandlerImpl.LOGGER.debug("loginpage set to {}",loginpage);
    }

    @ObjectClassDefinition(name = "AG AuthHandler Configurations", description = "Configure path and redirection paths for AG Auth Handler")
    public @interface Config {
        @AttributeDefinition(name = "path", description = "Repository paths for which this authentication handler should be used by Sling." +
                " If this is empty, the authentication handler will be disabled. (path)")
        String[] path();

        @AttributeDefinition(name = "Authentication Failure Page", description = "Please enter the page for authentication failure" +
                "http://domain/content/ag/en/shop/home.html")
        String failurePage() default "/content/ag/en/shop/home.html";

        @AttributeDefinition(name = "DummyUser", description = "Please enter the name of dummy user")
        String dummyUser() default "ag-cug-user";

        @AttributeDefinition(name = "encryptcode", description = "Please enter the encryption code for the dummy user")
        String encryptCode();
    }
}