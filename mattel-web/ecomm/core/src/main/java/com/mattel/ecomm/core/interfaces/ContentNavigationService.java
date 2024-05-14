package com.mattel.ecomm.core.interfaces;

import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.json.JSONObject;

import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.PromoImagePojo;

public interface ContentNavigationService {
	JSONObject processNavLinks(String deviceType, Resource currentresource);

	List<PromoImagePojo> fetchPromoImageListByTemplateType(Resource resource, GlobalNavigationPojo cnv) throws RepositoryException;
}
