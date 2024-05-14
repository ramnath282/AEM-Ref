package com.mattel.ecomm.core.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.interfaces.CategoryPagesDetailsService;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = CategoryPagesDetailsService.class)
public class CategoryPagesDetailsServiceImpl implements CategoryPagesDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryPagesDetailsServiceImpl.class);
    private static final String PAGE_RANKING = "pageRanking";
    
    @Reference
    GetResourceResolver getResourceResolver;

   
    ResourceResolver resourceResolver ;
    private boolean isResourceFound;
    
    @Override
    public JSONObject getCategoryPagesJson(String categoryRootPagePath, String productgridResourcePath, String agPlpTemplatePath) {
        
        LOGGER.info("Start of Category Page JSON method");
        List<Page> pageList;
        JSONObject validCategoryPageJson = new JSONObject();
        resourceResolver = getResourceResolver.getResourceResolver();
        pageList= new ArrayList<>();
        if (Objects.nonNull(resourceResolver)) {
            try {
                JSONArray jsonArray = new JSONArray();
                PageManager pageMgr = resourceResolver.adaptTo(PageManager.class);
                if (Objects.nonNull(pageMgr)) {
                    Page categoryRootPage = pageMgr.getPage(categoryRootPagePath);
                    if (Objects.nonNull(categoryRootPage)) {
                        pageList = getCategoryPages(categoryRootPage, productgridResourcePath, agPlpTemplatePath,pageList);
                    }
                }
                if(!pageList.isEmpty()){
                LOGGER.debug("Size of ResourceList {}",pageList.size());
                prepareJsonFromResults(categoryRootPagePath, validCategoryPageJson, jsonArray, pageList);
                }
            } catch (JSONException e) {
                LOGGER.error("Exception Occured {}", e);
            } finally {
                if (resourceResolver.isLive()) {
                    resourceResolver.close();
                }
            }
        }
        LOGGER.info("End of Category Page JSON method");
        return validCategoryPageJson;
    }

    @Override
    public void getCategories(final SlingHttpServletRequest request,
        final SlingHttpServletResponse response, int parentDepth, String productgridResourcePath, String plpTemplatePath) throws IOException {
      LOGGER.info("Get Category Method Called");
      String pagePath = request.getParameter("pagePath");
      LOGGER.debug("current page path is {}",pagePath);
      ResourceResolver reqResourceResolver = request.getResourceResolver();
      PageManager pgMgr = reqResourceResolver.adaptTo(PageManager.class);
      if(Objects.nonNull(pgMgr) && StringUtils.isNotBlank(pagePath)){
          Page currentPage = pgMgr.getPage(pagePath);
          if(Objects.nonNull(currentPage)){
              Page categoryRootPage = currentPage.getAbsoluteParent(parentDepth);
              if(Objects.nonNull(categoryRootPage)){
                  String categoryRootPagePath = categoryRootPage.getPath();
                  if(StringUtils.isNotBlank(categoryRootPagePath)){
                      LOGGER.debug("category Root page path is {}",categoryRootPagePath);
                      JSONObject jsonArray = getCategoryPagesJson(categoryRootPagePath, productgridResourcePath,plpTemplatePath);
                      response.getWriter().write(jsonArray.toString());
                  }
              }
          }
      }
    }

    private List<Page> getCategoryPages(Page categoryPage, String productgridResourcePath, String agPlpTemplatePath,List<Page> pageList) {
        LOGGER.info("CategoryPagesDetails getCategoryPages method Start");
        List<Page> pages = pageList;
        if (Objects.nonNull(categoryPage.getTemplate())) {
            String pageTemplatePath = categoryPage.getTemplate().getPath();
            LOGGER.debug("{} Template path", pageTemplatePath);
            if (pageTemplatePath.equalsIgnoreCase(agPlpTemplatePath)) {
                Resource categoryResource = categoryPage.getContentResource();
                if (Objects.nonNull(categoryResource)) {
                    isResourceFound = false;
                    LOGGER.debug("{} sent to validateCategoryPages method", categoryResource.getPath());
                    validateCategoryPages(categoryResource, productgridResourcePath,pages);
                }
            }
        }
        Iterator<Page> childrenList = categoryPage.listChildren();
        while (Objects.nonNull(childrenList) && childrenList.hasNext()) {
            Page childResource = childrenList.next();
            getCategoryPages(childResource, productgridResourcePath, agPlpTemplatePath,pages);
        }
        
        LOGGER.info("CategoryPagesDetails getCategoryPages method End");
        return pages;
    }

    private List<Page> validateCategoryPages(Resource res, String productgridResourcePath,List<Page> pageList) {
        LOGGER.info("CategoryPagesDetails validateCategoryPages method Start");
        Iterator<Resource> childrenList = res.listChildren();
        while (childrenList.hasNext() && !isResourceFound) {
            Resource childResource = childrenList.next();
            LOGGER.debug("checking resource type at {}",childResource.getPath());
            if (childResource.isResourceType(productgridResourcePath)) {
                PageManager pageMgr = resourceResolver.adaptTo(PageManager.class);
                if (Objects.nonNull(pageMgr)) {
                    Page validCategoryPage = pageMgr.getContainingPage(childResource);
                    pageList.add(validCategoryPage);
                    LOGGER.debug("{} added to resourceList", validCategoryPage.getPath());
                    isResourceFound = true;
                    return pageList;
                }
            }
            validateCategoryPages(childResource, productgridResourcePath,pageList);

        }
        LOGGER.info("CategoryPagesDetails validateCategoryPages method End");
        return pageList;
    }    

    /**
     * @param categoryRootPagePath root category page path.
     * @param validCategoryPageJson final category JSON
     * @param resourceResolver resource resolver
     * @param jsonArray 
     * @param pageList of valid category page list
     * @throws JSONException JSON Exception
     */
    private void prepareJsonFromResults(String categoryRootPagePath, JSONObject validCategoryPageJson, JSONArray jsonArray, List<Page> pageList)
            throws JSONException {
      
            for (Page page : pageList) {
                JSONObject categoryJson = new JSONObject();
                if (Objects.nonNull(page)) {
                    categoryJson.put("url", EcomUtil.getPlpPageLink(page.getPath(), page.getContentResource()));
                    categoryJson.put("title", page.getTitle());
                    categoryJson.put("categoryid",
                            page.getPath().replaceAll(categoryRootPagePath + Constant.SLASH, ""));
                    if (page.getProperties().containsKey(PAGE_RANKING)) {
                        categoryJson.put(PAGE_RANKING, page.getProperties().get(PAGE_RANKING, Integer.class));
                    }
                    if (page.getProperties().containsKey(Constant.HIDE_IN_NAV)) {
                        categoryJson.put(Constant.HIDE_IN_NAV, page.getProperties().get(Constant.HIDE_IN_NAV, Boolean.class));
                    }
                    if (page.getProperties().containsKey(Constant.SKIP_IN_NAV)) {
                      categoryJson.put(Constant.SKIP_IN_NAV, page.getProperties().get(Constant.SKIP_IN_NAV, Boolean.class));
                    }
                    if (page.getProperties().containsKey(Constant.STOP_IN_NAV)) {
                      categoryJson.put(Constant.STOP_IN_NAV, page.getProperties().get(Constant.STOP_IN_NAV, Boolean.class));
                    }
                    jsonArray.put(categoryJson);
                }
                LOGGER.debug("JSON Array is {}", jsonArray);
            }
            validCategoryPageJson.put("category", jsonArray);
        
    }
}
