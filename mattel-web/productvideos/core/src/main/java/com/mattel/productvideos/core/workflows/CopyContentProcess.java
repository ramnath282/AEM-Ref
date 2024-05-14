package com.mattel.productvideos.core.workflows;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.mattel.productvideos.core.services.impl.CopyContentServiceImpl;
import com.mattel.productvideos.core.utils.CopyContentWorkflowUtils;

/**
 * @author CTS
 *
 */
@Component(property = { Constants.SERVICE_DESCRIPTION + "=Jurassic World Copy Content Process",
		Constants.SERVICE_VENDOR + "=Adobe Systems", "process.label" + "= Jurassic World Copy Content Process" })
public class CopyContentProcess implements WorkflowProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(CopyContentProcess.class);
	private static final String JW_ROOT_PATH = "jwrootpath";

	@Reference
	private CopyContentServiceImpl copyCFServiceImpl;

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		CopyContentProcess.LOGGER.info("CopyContentProcess -> Start");

		String contentPath = workItem.getWorkflowData().getPayload().toString();
		CopyContentProcess.LOGGER.debug("payload path : {}", contentPath);

		String processArgs = metaDataMap.get("PROCESS_ARGS", "string");
		Map<String, String> argsMap = CopyContentWorkflowUtils.getProcessArgsList(processArgs);

		ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);

		if (Objects.nonNull(resourceResolver) && Objects.nonNull(argsMap) && StringUtils.isNotEmpty(contentPath)) {

			String contentType = contentPath.contains("/content/dam") ? "asset" : "content";
			CopyContentProcess.LOGGER.debug("content type : {}", contentType);

			String jwRootPath = argsMap.get(CopyContentProcess.JW_ROOT_PATH);
			copyCFServiceImpl.copyContent(jwRootPath, contentPath, contentType, resourceResolver);
		}

		CopyContentProcess.LOGGER.info("CopyContentProcess -> Stop");
	}

}
