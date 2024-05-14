package com.mattel.fisherprice.core.utils;

import com.day.cq.commons.jcr.JcrRecursiveRemove;

import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to remove resource.
 */
public class JcrResourceRemove {
  private static final Logger LOGGER = LoggerFactory.getLogger(JcrResourceRemove.class);

  /**
   * Removes <code>node</code> and node children recursively.
   *
   * @param node
   *          Node to delete
   * @param batchSize
   *          Number of nodes to be saved (to avoid growing the transient space too much).
   * @return The count of <code>node</code> removed.
   * @throws RepositoryException
   *           Unable to remove the specified node.
   */
  public static int removeNodeRecursively(Node node, int batchSize) throws RepositoryException {
    final JcrRecursiveRemove jcrRecursiveRemove = new JcrRecursiveRemove();

    JcrResourceRemove.LOGGER.debug("Deleting node at path: {}", node.getPath());
    return jcrRecursiveRemove.removeRecursive(node, batchSize);
  }

  /**
   * Remove node at a given path.
   * Call {@link ResourceResolver#commit} after the to commit the changes.
   *
   * @param path
   *          Path to the node to remove.
   * @param session
   *          JCR session needed to remove the node.
   * @param resourceResolver
   *          To check if specified node is present.
   */
  public static void removeResource(String path, Session session, ResourceResolver resourceResolver)
      throws RepositoryException {
    final Resource resource = resourceResolver.getResource(path);

    if (Objects.nonNull(resource)) {
      JcrResourceRemove.LOGGER.debug("Deleting node at path: {}", path);
      session.removeItem(path);
    } else {
      JcrResourceRemove.LOGGER.debug("Node at {} has already been removed", path);
    }
  }

  private JcrResourceRemove() {
    // no-op
  }
}
