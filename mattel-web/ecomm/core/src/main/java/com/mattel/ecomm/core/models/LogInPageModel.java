package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import com.mattel.ecomm.core.pojos.LogInPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LogInPageModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInPageModel.class);
    List<LogInPojo> descriptionPointsList = new ArrayList<>();


    @Inject
    private Node descriptionMultifield;
    @Inject
    private MultifieldReader multifieldReader;

    public Node getDescriptionMultifield() {
        return descriptionMultifield;
    }

    public void setDescriptionMultifield(Node descriptionMultifield) {
        this.descriptionMultifield = descriptionMultifield;
    }

    @PostConstruct
    protected void init() {
        LOGGER.debug("LogInPageModel Init Start");
        long startTime = System.currentTimeMillis();

        if (descriptionMultifield != null) {
            Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(descriptionMultifield);
            for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
                LogInPojo descriptionPoints = new LogInPojo();
                LOGGER.debug("Description Point{}", entry.getValue().get("descriptionPoint", String.class));
                descriptionPoints.setDescriptionPointStr(entry.getValue().get("descriptionPoint", String.class));
                descriptionPointsList.add(descriptionPoints);
            }
        }

        long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "init", endTime - startTime);
        LOGGER.info("LogInPageModel Init end");

    }

    public List<LogInPojo> getDescriptionPointsList() {
        return descriptionPointsList;
    }

    public void setDescriptionPointsList(List<LogInPojo> descriptionPointsList) {
        this.descriptionPointsList = descriptionPointsList;
    }
}