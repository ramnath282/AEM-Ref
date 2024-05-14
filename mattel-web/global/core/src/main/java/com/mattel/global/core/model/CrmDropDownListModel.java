package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.pojo.CrmDropDownListPojo;
import com.mattel.global.core.services.MultifieldReader;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CrmDropDownListModel {
	 private static final Logger LOGGER = LoggerFactory.getLogger(CrmDropDownListModel.class);
	 List<CrmDropDownListPojo> keyList = new ArrayList<>();
	 
	 @Inject
	 private Node dropDownMulti;

	 @Inject
	 private MultifieldReader multifieldReader;

	 @PostConstruct
	    protected void init() {
	        LOGGER.info("CrmDropDownListModel Init Start");
	        if (!Objects.isNull(dropDownMulti)) {
	            final Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(dropDownMulti);
	            for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	                final CrmDropDownListPojo crmDropDownListPojo = new CrmDropDownListPojo();
	                crmDropDownListPojo.setDropDownKey(entry.getValue().get("dropDownKey", String.class));
	                crmDropDownListPojo.setDropDownValue(entry.getValue().get("dropDownValue", String.class));
					LOGGER.debug("crmDropDownListPojo values {} {}",crmDropDownListPojo.getDropDownKey(),crmDropDownListPojo.getDropDownValue());
	                keyList.add(crmDropDownListPojo);
	            }
	        }
	      
	        LOGGER.info("CrmDropDownListModel Init End");
	    }

	public List<CrmDropDownListPojo> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<CrmDropDownListPojo> keyList) {
		this.keyList = keyList;
	}

	public Node getDropDownMulti() {
		return dropDownMulti;
	}

	public void setDropDownMulti(Node dropDownMulti) {
		this.dropDownMulti = dropDownMulti;
	}

	public MultifieldReader getMultifieldReader() {
		return multifieldReader;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}

	
	 
	 
}
