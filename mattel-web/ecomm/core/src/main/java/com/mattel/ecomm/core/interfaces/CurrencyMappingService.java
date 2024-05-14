package com.mattel.ecomm.core.interfaces;

import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;

import com.mattel.ecomm.core.pojos.CurrencyMappingPojo;

public interface CurrencyMappingService {

	String getCurrencyDetails(String currencyType, Resource resource);

	List<CurrencyMappingPojo> getCurrencyDetails(Resource resource) throws RepositoryException;

}
