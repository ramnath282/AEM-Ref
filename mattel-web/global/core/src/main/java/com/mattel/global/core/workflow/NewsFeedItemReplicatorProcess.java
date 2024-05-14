package com.mattel.global.core.workflow;

import java.util.Objects;
import java.util.Optional;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.mattel.global.core.services.GetResourceResolver;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.utils.PropertyReaderUtils;

@Component(service = WorkflowProcess.class, property = { "process.label=News Feed Item Replicator Step" })
public class NewsFeedItemReplicatorProcess implements WorkflowProcess {

  private static final String UNABLE_TO_REPLICATE_NEWS_CF_NODE = "Unable to replicate News Content Fragment";
  private static final String PROCESS_ARGS = "PROCESS_ARGS";
  private static final String ACTIVATE = "Activate";

  private static final Logger LOGGER = LoggerFactory.getLogger(NewsFeedItemReplicatorProcess.class);

  @Reference
  private GetResourceResolver getResourceResolver;
  
  @Reference
  private PropertyReaderUtils propertyReaderUtils;

  @Reference
  private Replicator replicator;

  @Override
  public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
      throws WorkflowException {
    LOGGER.info("Start of execute method of NewsFeedItemReplicatorProcess");
    final WorkflowData workflowData = workItem.getWorkflowData();
    final String cfPayloadPath = workflowData.getPayload().toString();
    LOGGER.debug("Payload path is: {}", cfPayloadPath);
    final ResourceResolver resourceResolver = getResourceResolver.getResourceResolver();
    Session session = null;
    if (metaDataMap.containsKey(PROCESS_ARGS)) {
      LOGGER.debug("workflow metadata for key PROCESS_ARGS and value {}",
          metaDataMap.get(PROCESS_ARGS, String.class));
      String[] processArguments = metaDataMap.get(PROCESS_ARGS, String.class).split(",");
      if (processArguments.length > 2) {
        String operationType;
        int connectionTimeout;
        String siteKey;
        operationType = StringUtils.isNotBlank(processArguments[0]) ? processArguments[0] : ACTIVATE;
        connectionTimeout = Objects.nonNull(Integer.parseInt(processArguments[1]))
            ? Integer.parseInt(processArguments[1]) : 10000;
        siteKey = StringUtils.isNotBlank(processArguments[2]) ? processArguments[2] : StringUtils.EMPTY;
        LOGGER.debug("connectionTimeout is: {} and siteKey is: {}", connectionTimeout,siteKey);
        processNewsFeedItem(cfPayloadPath, resourceResolver, session, operationType, connectionTimeout,siteKey);
      }
    }
    LOGGER.info("End of execute method of NewsFeedItemReplicatorProcess");
  }

  /**
   * This method activates the CF and triggers S&P indexing
   * 
   * @param cfPayloadPath
   * @param resourceResolver
   * @param session
   * @param operationType
   * @param connectionTimeout
   */
  private void processNewsFeedItem(final String cfPayloadPath, final ResourceResolver resourceResolver,
      Session session, String operationType, int connectionTimeout, String siteKey) {
    LOGGER.info("Start of processNewsFeedItem method of NewsFeedItemReplicatorProcess");
    try {
      if (StringUtils.isNotBlank(cfPayloadPath)) {
        session = resourceResolver.adaptTo(Session.class);
        if (StringUtils.equals(operationType, ACTIVATE)) {
          replicator.replicate(session, ReplicationActionType.ACTIVATE, cfPayloadPath);
          LOGGER.debug("operationType is: {}, activating {}", operationType, cfPayloadPath);
        } else {
          replicator.replicate(session, ReplicationActionType.DEACTIVATE, cfPayloadPath);
          LOGGER.debug("operationType is: {}, deactivating {}", operationType, cfPayloadPath);
        }
        // CF activated now trigger S&P Indexing end point
        if(StringUtils.isNotBlank(siteKey)){
        	GlobalUtils.triggerSnpIndexing(connectionTimeout, siteKey,propertyReaderUtils);
        }
      } else {
        NewsFeedItemReplicatorProcess.LOGGER.error("Unable to determine workflow status");
      }
    } catch (final ReplicationException e) {
      NewsFeedItemReplicatorProcess.LOGGER.error(NewsFeedItemReplicatorProcess.UNABLE_TO_REPLICATE_NEWS_CF_NODE,
          e);
    } finally {
      Optional.ofNullable(session).ifPresent(s -> {
        if (s.isLive()) {
          s.logout();
        }
      });
      Optional.ofNullable(resourceResolver).ifPresent(ResourceResolver::close);
    }
    LOGGER.info("End of processNewsFeedItem method of NewsFeedItemReplicatorProcess");
  }

}