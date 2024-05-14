package com.mattel.productvideos.core.workflows;

import java.util.Collections;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.asset.api.Asset;
import com.adobe.granite.asset.api.AssetManager;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.adobe.granite.workflow.model.WorkflowNode;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils;

@Component(service = { WorkflowProcess.class }, property = { "process.label=Relate Assets" })
public class RelateAssets implements WorkflowProcess {
  private static final Logger LOGGER = LoggerFactory.getLogger(RelateAssets.class);

  Session session;

  @Reference
  private ResourceResolverFactory resolverFactory;

  @Reference
  private ProductVideosPropertyUtils productVideosPropertyUtils;

  public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap args) throws WorkflowException {
    try {
      LOGGER.info("Start of Execute method");
      WorkflowNode myNode = item.getNode();
      String myTitle = myNode.getTitle();
      LOGGER.debug("The title of workflow is {}", myTitle);
      WorkflowData workflowData = item.getWorkflowData();
      String path = workflowData.getPayload().toString();
      relatingAssets(path, wfsession);
      LOGGER.info("End of Execute method");
    } catch (Exception e) {
      LOGGER.error("**** Error in relate assets: ", e);
    }
  }

  /**
   * This method prepares the query based on brand, Asset Name and Language
   * and call the method to process the query results
   * 
   * @param payLoadAsset
   * @param wfsession
   */
  private void relatingAssets(String payLoadAsset, WorkflowSession wfsession) {
    LOGGER.info("Start of relatingAssets method");
    try {
      String languagevalue = "";
      String brand = "Jurassic World";
      String brandName = "";
      this.session = wfsession.adaptTo(Session.class);
      ResourceResolver resourceResolver = resolverFactory
          .getResourceResolver(Collections.singletonMap("user.jcr.session", this.session));
      AssetManager assetMgr = resourceResolver.adaptTo(AssetManager.class);
      String dataPath = payLoadAsset.replaceFirst("/", "");

      if (Objects.nonNull(session)) {
        Node root = session.getRootNode();
        LOGGER.debug("datapath", dataPath);
        Node dataPathNode = root.getNode(dataPath);
        QueryManager queryManager = this.session.getWorkspace().getQueryManager();
        String finalQuery = "SELECT * FROM [nt:unstructured] AS s WHERE   ISDESCENDANTNODE (s, '"
            + productVideosPropertyUtils.getRootAssetsPath() + "') ";
        LOGGER.debug("finalQuery is: {}", finalQuery);
        StringBuilder sql2Query = new StringBuilder(finalQuery);
        if (dataPathNode.hasProperty("dc:brand")) {
          Property brandproperty = dataPathNode.getProperty("dc:brand");
          brandName = brandproperty.getValue().toString();
        }
        if (brandName.equals(brand)) {
          languagevalue = updateQueryWithNameAndLanguage(languagevalue, dataPathNode, sql2Query);
          Query query = queryManager.createQuery(sql2Query.toString(), "JCR-SQL2");
          QueryResult result = query.execute();
          iterateAndProcessResults(payLoadAsset, languagevalue, assetMgr, result);
        }
      }
    } catch (RepositoryException | LoginException e) {
      LOGGER.error("Exception occured in relatingAssets method: {}", e);
    }
    LOGGER.info("End of relatingAssets method");
  }

  /**
   * @param languagevalue
   * @param dataPathNode
   * @param sql2Query
   * @return
   * @throws RepositoryException
   * @throws PathNotFoundException
   * @throws ValueFormatException
   */
  private String updateQueryWithNameAndLanguage(String languagevalue, Node dataPathNode, StringBuilder sql2Query)
      throws RepositoryException {
    LOGGER.info("Start of updateQueryWithNameAndLanguage method");
    String assetTitle;
    if (dataPathNode.hasProperty("dc:assetName")) {
      Property assetTitleproperty = dataPathNode.getProperty("dc:assetName");
      assetTitle = assetTitleproperty.getValue().toString();
      sql2Query.append(Constants.AND_TEXT).append(" [dc:assetName]=").append("'").append(assetTitle).append("'");
    }
    if (dataPathNode.hasProperty("dc:languageIsoCode")) {
      Property languagevalueproperty = dataPathNode.getProperty("dc:languageIsoCode");
      languagevalue = languagevalueproperty.getValue().toString();
      if (languagevalue.equals(Constants.LANG_ENGLISH)) {
        sql2Query.append(Constants.AND_TEXT).append(" [dc:languageIsoCode]<>").append("'").append(languagevalue)
            .append("'");
      } else {
        String languageval = Constants.LANG_ENGLISH;
        sql2Query.append(Constants.AND_TEXT).append(" [dc:languageIsoCode]=").append("'").append(languageval)
            .append("'");
      }
    }
    LOGGER.info("End of updateQueryWithNameAndLanguage method");
    return languagevalue;
  }

  /**
   * This method processes the query results and sets the Asset node
   * properties according to the results.
   * 
   * @param payLoadAsset
   * @param languagevalue
   * @param relatedAsset
   * @param assetMgr
   * @param result
   * @throws RepositoryException
   */
  private void iterateAndProcessResults(String payLoadAsset, String languagevalue, AssetManager assetMgr,
      QueryResult result) throws RepositoryException {
    LOGGER.info("Start of iterateAndProcessResults method");
    Asset asset;
    String relatedAsset = "";
    NodeIterator nIt = result.getNodes();
    payLoadAsset = payLoadAsset.replace("/jcr:content/metadata", "");
    while (nIt.hasNext()) {
      try {
        Node nodeObj = nIt.nextNode();
        LOGGER.debug(nodeObj.getPath());
        relatedAsset = nodeObj.getPath();
        relatedAsset = relatedAsset.replace("/jcr:content/metadata", "");
        LOGGER.debug("relatedAsset is : {}", relatedAsset);
        if (assetMgr.assetExists(relatedAsset) && assetMgr.assetExists(payLoadAsset)) {
          if (languagevalue.equals(Constants.LANG_ENGLISH)) {
            asset = assetMgr.getAsset(payLoadAsset);
            asset.addRelation("derived", relatedAsset);
          } else {
            asset = assetMgr.getAsset(relatedAsset);
            asset.addRelation("derived", payLoadAsset);
          }
          LOGGER.debug("relation created");
        }
      } catch (Exception e) {
        LOGGER.error("Exception occured in iterateAndProcessResults method: {} ", e);
      }
      LOGGER.info("End of iterateAndProcessResults method");
    }
  }
}
