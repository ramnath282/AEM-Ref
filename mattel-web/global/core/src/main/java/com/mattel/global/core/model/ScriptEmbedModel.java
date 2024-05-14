package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS
 */
@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL) public class ScriptEmbedModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEmbedModel.class);

    @Inject
    private Resource resource;

    @Inject
    private String[] scriptPaths;
    @Inject
    private String usableNetScriptId;
    @Inject
    private String usableNetScriptPath;

    private List<String> scriptPathsList = new ArrayList<>();

    @PostConstruct protected void init() {
        LOGGER.info("Start of init method of Script Embed Model");
        if (Objects.nonNull(resource) && Objects.nonNull(scriptPaths)) {
            scriptPathsList = Arrays.asList(scriptPaths);
            LOGGER.debug("Script path list is {}", scriptPathsList);
        }
        LOGGER.info("End of init method of ScriptEmbedModel ");
    }
    public List<String> getScriptPathsList() {
        return scriptPathsList;
    }
    public String getUsableNetScriptId() {
        return usableNetScriptId;
    }
    public String getUsableNetScriptPath() {
        return usableNetScriptPath;
    }
}
