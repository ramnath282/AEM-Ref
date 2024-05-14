package com.mattel.fisherprice.core.listeners;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.fisherprice.core.constants.Constants;

/**
 * @author CTS
 *
 */
@Component(immediate = true, service = EventListener.class)
public class ArticleDetailListener implements EventListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDetailListener.class);
  private static final String LANGUAGE_MASTER_PATH = "/content/fisher-price/language-masters";
  private static final String ARTICLE_TEMPLATE_PATH = "/conf/fisher-price/settings/wcm/templates/article-details-page";
  private static final String[] NODE_TYPES = { "cq:Page" };
  private static final String MAX_ARTICLE_ID = "maxArticleId";
  private static final String ARTICLE_ID = "articleId";
  private Session adminSession;

  @Reference
  org.apache.sling.jcr.api.SlingRepository repository;

  @Activate
  public void activate(ComponentContext context) throws Exception {
    LOGGER.info("Article Detail Listener -> Activate");
    try {
      adminSession = repository.loginService("readwriteservice", null);
      adminSession.getWorkspace().getObservationManager().addEventListener(this, // handler
          Event.NODE_ADDED, // binary combination of
                            // event types
          ArticleDetailListener.LANGUAGE_MASTER_PATH, // path
          true, // is Deep?
          null, // uuids filter
          ArticleDetailListener.NODE_TYPES, // nodetypes filter
          false);

    } catch (RepositoryException e) {
      LOGGER.error("unable to register session", e);
      throw new Exception(e);
    }
  }

  @Deactivate
  public void deactivate() {
    LOGGER.info("Article Detail Listener -> Desctivate");
    if (adminSession != null) {
      LOGGER.debug("Admin session present");
      adminSession.logout();
      LOGGER.debug("Admin session loggedout");
    }
  }

  public void onEvent(EventIterator eventIterator) {
    LOGGER.info("Article Detail Listener -> onEvent");
    try {
      while (eventIterator.hasNext()) {
        Event event = eventIterator.nextEvent();
        LOGGER.debug("something has been added : {}", event.getPath());
        setArticleId(event.getPath());
      }
    } catch (RepositoryException e) {
      LOGGER.error("Error while treating events", e);
    }
  }

  /**
   * adding dynamically generated article id to newly created article
   * 
   * @param articlePath
   *          path of newly created article
   */
  private void setArticleId(String articlePath) {
    LOGGER.info("Article Detail Listener -> setPageProperties");
    Node pageNode;
    try {
      pageNode = adminSession.getNode(articlePath);
      if (null != pageNode && checkArticleTemplate(pageNode)) {
        Node langMasterNode = adminSession
            .getNode(LANGUAGE_MASTER_PATH + Constants.SLASH + Constants.JCR_CONTENT);
        long maxArticleID = getMaxArticleId(langMasterNode);
        LOGGER.debug("Max Article Id : {}", maxArticleID);
        pageNode.setProperty(ArticleDetailListener.ARTICLE_ID, maxArticleID + 1);
        langMasterNode.setProperty(ArticleDetailListener.MAX_ARTICLE_ID, maxArticleID + 1);
        adminSession.save();
      }
    } catch (RepositoryException e) {
      LOGGER.error("Error while treating node", e);
    }
  }

  /**
   * check whether the newly created page is created from article detail page
   * component
   * 
   * @param pageNode
   * @return
   */
  private boolean checkArticleTemplate(Node pageNode) {
    LOGGER.info("checkArticleTemplate -> START");
    boolean articleTemplateFlag = false;
    try {
      Property templateProp = pageNode.getProperty("cq:template");
      if (null != templateProp) {
        String templatePath = templateProp.getValue().getString();
        if (ArticleDetailListener.ARTICLE_TEMPLATE_PATH.equals(templatePath)) {
          articleTemplateFlag = true;
          LOGGER.debug("Page is created from article-detail-page template");
        }
      }
    } catch (RepositoryException e) {
      LOGGER.error("Error while getting template property", e);
    }
    return articleTemplateFlag;
  }

  /**
   * get maxArticle id from the node
   * 
   * @param langMasterNode
   *          language master node (can be passed any node)
   * @return
   */
  private long getMaxArticleId(Node langMasterNode) {
    LOGGER.info("getMaxArticleId -> START");
    long maxArticleId = 0;
    try {
      Property maxArticleIdProp = langMasterNode.getProperty(ArticleDetailListener.MAX_ARTICLE_ID);
      maxArticleId = (null != maxArticleIdProp) ? maxArticleIdProp.getValue().getLong() : 0;
    } catch (RepositoryException e) {
      LOGGER.error("Error while getting articles node", e);
    }
    return maxArticleId;
  }

}
