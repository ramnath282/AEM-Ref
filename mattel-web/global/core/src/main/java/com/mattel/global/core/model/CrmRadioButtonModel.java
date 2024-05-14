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

import com.mattel.global.core.pojo.CrmRadioButtonPojo;
import com.mattel.global.core.services.MultifieldReader;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CrmRadioButtonModel {
	 private static final Logger LOGGER = LoggerFactory.getLogger(CrmRadioButtonModel.class);
	 List<CrmRadioButtonPojo> keyList = new ArrayList<>();
	 
	 @Inject
	 private Node radioButtonMulti;

	 @Inject
	 private MultifieldReader multifieldReader;

	 @PostConstruct
	    protected void init() {
	        LOGGER.info("CrmRadioButtonModel Init Start");
	        if (!Objects.isNull(radioButtonMulti)) {
	            final Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(radioButtonMulti);
	            for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
	                final CrmRadioButtonPojo crmRadioButtonPojo = new CrmRadioButtonPojo();
	                crmRadioButtonPojo.setRadioKey(entry.getValue().get("radioKey", String.class));
	                crmRadioButtonPojo.setRadioValue(entry.getValue().get("radioValue", String.class));
					LOGGER.debug("CrmRadioButtonModel values {} {}",crmRadioButtonPojo.getRadioKey(),crmRadioButtonPojo.getRadioValue());
	                keyList.add(crmRadioButtonPojo);
	            }
	        }
	      
	        LOGGER.info("CrmRadioButtonModel Init End");
	    }

	public List<CrmRadioButtonPojo> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<CrmRadioButtonPojo> keyList) {
		this.keyList = keyList;
	}

	public Node getRadioButtonMulti() {
		return radioButtonMulti;
	}

	public void setRadioButtonMulti(Node radioButtonMulti) {
		this.radioButtonMulti = radioButtonMulti;
	}

	public MultifieldReader getMultifieldReader() {
		return multifieldReader;
	}

	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
	 
	 
}
