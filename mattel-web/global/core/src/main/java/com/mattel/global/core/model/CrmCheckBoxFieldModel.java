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
import com.mattel.global.core.pojo.CrmCheckFieldPojo;
import com.mattel.global.core.services.MultifieldReader;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CrmCheckBoxFieldModel {
	 private static final Logger LOGGER = LoggerFactory.getLogger(CrmCheckBoxFieldModel.class);
	 List<CrmCheckFieldPojo> keyList = new ArrayList<>();
	 
	 @Inject
	 private Node checkBoxMulti;

	 @Inject
	 private MultifieldReader multifieldReader;

	 @PostConstruct
	    protected void init() {
	        LOGGER.info("CrmCheckBoxFieldModel Init Start");
	        if (!Objects.isNull(checkBoxMulti)) {
	            final Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(checkBoxMulti);
	            for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	                final CrmCheckFieldPojo crmCheckFieldPojo = new CrmCheckFieldPojo();
	                crmCheckFieldPojo.setItemKey(entry.getValue().get("itemKey", String.class));
	                crmCheckFieldPojo.setItemValue(entry.getValue().get("itemValue", String.class));
					LOGGER.debug("CrmCheckBoxFieldModel values {} {}",crmCheckFieldPojo.getItemKey(),crmCheckFieldPojo.getItemValue());
	                keyList.add(crmCheckFieldPojo);
	            }
	        }
	      
	        LOGGER.info("CrmCheckBoxFieldModel Init End");
	    }

	public List<CrmCheckFieldPojo> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<CrmCheckFieldPojo> keyList) {
		this.keyList = keyList;
	}

	public Node getCheckBoxMulti() {
		return checkBoxMulti;
	}

	public void setCheckBoxMulti(Node checkBoxMulti) {
		this.checkBoxMulti = checkBoxMulti;
	}

	public MultifieldReader getMultifieldReader() {
		return multifieldReader;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
	 
	 
}
