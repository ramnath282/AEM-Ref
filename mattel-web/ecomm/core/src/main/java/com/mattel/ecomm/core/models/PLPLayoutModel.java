package com.mattel.ecomm.core.models;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PLPLayoutModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(PLPLayoutModel.class);

	@Inject
	private String categoryId;
	
	@Self
	Resource resource;
	
	private String relCategoryPath;

	@PostConstruct
	protected void init() {
		LOGGER.info("PLPLayoutModel Init Start");
		long startTime = System.currentTimeMillis();
		LOGGER.debug("Category Id :{}", categoryId);
		
		if(null!=resource){
			ResourceResolver resolver = resource.getResourceResolver();
			PageManager pgMgr = resolver.adaptTo(PageManager.class);
			if(null!=pgMgr){
				Page currentpage = pgMgr.getContainingPage(resource);
				if(Objects.nonNull(currentpage)){
				    String currentPagePath = currentpage.getPath();
				    Page rootCategoryPage = currentpage.getAbsoluteParent(4);
				    if(currentPagePath.contains("fisher-price")) {
				    	rootCategoryPage = currentpage.getAbsoluteParent(5);
				    }
				    if(Objects.nonNull(rootCategoryPage)){
				        relCategoryPath = currentPagePath.replaceAll(rootCategoryPage.getPath()+Constant.SLASH, "");
				    }
				}
			}
		}
		long endTime = System.currentTimeMillis();
		LOGGER.info(Constant.EXECUTION_TIME_LOG, "PLPLayoutModel Init end", endTime - startTime);
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getRelCategoryPath() {
		return relCategoryPath;
	}

	public void setRelCategoryPath(String relCategoryPath) {
		this.relCategoryPath = relCategoryPath;
	}
}
