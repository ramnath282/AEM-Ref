package com.mattel.ecomm.core.importer.workflow.services;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.taskmanagement.TaskManagerFactory;
import com.adobe.granite.workflow.exec.InboxItem.Priority;

@RunWith(MockitoJUnitRunner.class)
public class ProductFeedInboxNotificationServiceImplTest {
  private ProductFeedInboxNotificationServiceImpl impl;

  @Before
  public void setUp() throws Exception {
    impl = new ProductFeedInboxNotificationServiceImpl();
  }

  @Test
  public void testCreateNotificationTask() throws TaskManagerException {
    final ProductFeedInboxNotificationServiceImpl.Config config = Mockito
        .mock(ProductFeedInboxNotificationServiceImpl.Config.class);
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    final TaskManager taskManager = Mockito.mock(TaskManager.class);
    final TaskManagerFactory taskManagerFactory = Mockito.mock(TaskManagerFactory.class);
    final Task task = Mockito.mock(Task.class);

    Mockito.doNothing().when(task).setName("Product feed file failed validation");
    Mockito.doNothing().when(task).setContentPath("/content/dam/ag/product/file1.json");
    Mockito.doNothing().when(task).setDescription("Missing manatory parameter pdpLink");
    Mockito.doNothing().when(task).setInstructions(null);
    Mockito.doNothing().when(task).setPriority(Priority.MEDIUM);
    Mockito.when(taskManagerFactory.newTask("Notification")).thenReturn(task);
    Mockito.when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);
    Mockito.when(taskManager.createTask(task)).thenReturn(task);
    Mockito.when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(taskManager);
    Mockito.when(config.defaultTaskAssignee()).thenReturn("content-authors");
    impl.activate(config);
    Assert.assertEquals(task,
        impl.createNotificationTask("Product feed file failed validation",
            "/content/dam/ag/product/file1.json", "ag-ecomm-users",
            "Missing manatory parameter pdpLink", null, Priority.MEDIUM, resourceResolver));
  }

  @Test
  public void testCreateNotificationTaskWithDefaultAsignee() throws TaskManagerException {
    final ProductFeedInboxNotificationServiceImpl.Config config = Mockito
        .mock(ProductFeedInboxNotificationServiceImpl.Config.class);
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    final TaskManager taskManager = Mockito.mock(TaskManager.class);
    final TaskManagerFactory taskManagerFactory = Mockito.mock(TaskManagerFactory.class);
    final Task task = Mockito.mock(Task.class);

    Mockito.doNothing().when(task).setName("Product feed file failed validation");
    Mockito.doNothing().when(task).setContentPath("/content/dam/ag/product/file1.json");
    Mockito.doNothing().when(task).setCurrentAssignee("content-authors");
    Mockito.doNothing().when(task).setDescription("Missing manatory parameter pdpLink");
    Mockito.doNothing().when(task).setInstructions(null);
    Mockito.doNothing().when(task).setPriority(Priority.MEDIUM);
    Mockito.when(taskManagerFactory.newTask("Notification")).thenReturn(task);
    Mockito.when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);
    Mockito.when(taskManager.createTask(task)).thenReturn(task);
    Mockito.when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(taskManager);
    Mockito.when(config.defaultTaskAssignee()).thenReturn("content-authors");
    impl.activate(config);
    Assert.assertEquals(task,
        impl.createNotificationTask("Product feed file failed validation",
            "/content/dam/ag/product/file1.json", null, "Missing manatory parameter pdpLink", null,
            Priority.MEDIUM, resourceResolver));
  }

  @Test
  public void testCreateNotificationTaskForResourceResolverNotAvailable()
      throws TaskManagerException {
    final ProductFeedInboxNotificationServiceImpl.Config config = Mockito
        .mock(ProductFeedInboxNotificationServiceImpl.Config.class);
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);

    Mockito.when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(null);
    Mockito.when(config.defaultTaskAssignee()).thenReturn("content-authors");
    impl.activate(config);
    Assert.assertNull(impl.createNotificationTask("Product feed file failed validation",
        "/content/dam/ag/product/file1.json", "ag-ecomm-users",
        "Missing manatory parameter pdpLink", null, Priority.MEDIUM, resourceResolver));
  }

  @Test
  public void testCreateNotificationTaskForTaskManagerException() throws TaskManagerException {
    final ProductFeedInboxNotificationServiceImpl.Config config = Mockito
        .mock(ProductFeedInboxNotificationServiceImpl.Config.class);
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    final TaskManager taskManager = Mockito.mock(TaskManager.class);
    final TaskManagerFactory taskManagerFactory = Mockito.mock(TaskManagerFactory.class);

    Mockito.when(taskManagerFactory.newTask("Notification"))
        .thenThrow(new TaskManagerException("Unable to create task"));
    Mockito.when(taskManager.getTaskManagerFactory()).thenReturn(taskManagerFactory);
    Mockito.when(resourceResolver.adaptTo(TaskManager.class)).thenReturn(taskManager);
    Mockito.when(config.defaultTaskAssignee()).thenReturn("content-authors");
    impl.activate(config);
    Assert.assertNull(impl.createNotificationTask("Product feed file failed validation",
        "/content/dam/ag/product/file1.json", "ag-ecomm-users",
        "Missing manatory parameter pdpLink", null, Priority.MEDIUM, resourceResolver));
  }
}
