package com.mattel.productvideos.core.workflows;

import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
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
import com.adobe.granite.workflow.model.WorkflowNode;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils;

@Component(service = { WorkflowProcess.class }, property = { "process.label=Generate ID" })
public class GenerateId implements WorkflowProcess {
  private static final Logger LOGGER = LoggerFactory.getLogger(GenerateId.class);

  Session session;

  @Reference
  private ProductVideosPropertyUtils productVideosPropertyUtils;

  public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap args) throws WorkflowException {
    try {
      LOGGER.info("Start of execute method");
      WorkflowNode myNode = item.getNode();
      String myTitle = myNode.getTitle();
      LOGGER.debug("The title of workflow is: {} ", myTitle);
      WorkflowData workflowData = item.getWorkflowData();
      String path = workflowData.getPayload().toString();
      idGenerator(path, wfsession);
      LOGGER.info("End of execute method");
    } catch (Exception e) {
      LOGGER.error("Error Occured in execute method : " + e);
    }
  }

  /**
   * 
   * @param path
   * @param wfsession
   */
  private void idGenerator(String path, WorkflowSession wfsession) {
    LOGGER.info("Start of idGenerator method");
    try {
      session = wfsession.adaptTo(Session.class);
      if (Objects.nonNull(session) && Objects.nonNull(session.getRootNode())) {
        LOGGER.debug("payload path is: {}", path);
        String finalPath = path.substring(1);
        LOGGER.debug("finalPath is {} : ", finalPath);
        Node root = session.getRootNode();
        String rootAssetsPath = productVideosPropertyUtils.getRootAssetsPath();
        LOGGER.debug("rootAssetsPath from config is: {}",rootAssetsPath);
        setGeneratedId(finalPath, root, rootAssetsPath);
      }
    } catch (Exception e) {
      LOGGER.error("Error Occured in idGenerator method : " + e);
    }
    LOGGER.info("End of idGenerator method");
  }

  /**
   * This method sets the generated in the Asset Node
   * 
   * @param finalPath
   * @param root
   * @param iteratorPath
   * @throws RepositoryException
  */
  private void setGeneratedId(String finalPath, Node root, String rootAssetsPath) 
     throws RepositoryException {
    LOGGER.info("Start of setGeneratedId method");
    long iteratorValue;
    Property propertyIterator;
    if(StringUtils.isNotBlank(rootAssetsPath) && root.hasNode(rootAssetsPath.substring(1)) && root.hasNode(finalPath)){
        Node dataPathNode = root.getNode(finalPath);
        String iteratorPath = rootAssetsPath.substring(1);
        Node iteratorNode = root.getNode(iteratorPath);
        if (iteratorNode.hasProperty(Constants.ITERATOR)) {
          propertyIterator = iteratorNode.getProperty(Constants.ITERATOR);
          iteratorValue = propertyIterator.getLong();
          LOGGER.debug("THE VALUE OF Iterator IS: {}", iteratorValue);
        } else {
          iteratorNode.setProperty(Constants.ITERATOR, 100000L);
          propertyIterator = iteratorNode.getProperty(Constants.ITERATOR);
          iteratorValue = propertyIterator.getLong();
        }
        if (dataPathNode.hasProperty(Constants.CONTENT_ID)) {
          Property propcontentId = dataPathNode.getProperty(Constants.CONTENT_ID);
          propcontentId.setValue(iteratorValue);
          propertyIterator.setValue(iteratorValue + 1L);
          LOGGER.debug("THE VALUE OF contentId is set to: {} ", iteratorValue);
        } else {
          dataPathNode.setProperty(Constants.CONTENT_ID, iteratorValue);
          propertyIterator.setValue(iteratorValue + 1L);
          LOGGER.debug("**** THE ContentId prop is set");
        }
    }
    LOGGER.info("End of setGeneratedId method");
  }
  
}
