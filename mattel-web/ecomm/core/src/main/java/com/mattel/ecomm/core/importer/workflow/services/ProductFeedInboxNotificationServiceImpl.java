package com.mattel.ecomm.core.importer.workflow.services;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.taskmanagement.TaskManager;
import com.adobe.granite.taskmanagement.TaskManagerException;
import com.adobe.granite.workflow.exec.InboxItem.Priority;
import com.mattel.ecomm.core.importer.workflow.interfaces.ProductFeedInboxNotificationService;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ProductFeedInboxNotificationService.class)
@Designate(ocd = ProductFeedInboxNotificationServiceImpl.Config.class)
public class ProductFeedInboxNotificationServiceImpl
    implements ProductFeedInboxNotificationService {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ProductFeedInboxNotificationServiceImpl.class);
  private String defaultTaskAssignee;

  @Override
  public Task createNotificationTask(String taskName, String contentPath, String currentAssignee,
      String description, String instructions, Priority priority,
      ResourceResolver resourceResolver) {
    try {
      final TaskManager taskManager = resourceResolver.adaptTo(TaskManager.class);

      if (Objects.nonNull(taskManager)) {
        final Task newTask = taskManager.getTaskManagerFactory().newTask("Notification");

        newTask.setName(taskName);
        newTask.setContentPath(contentPath);

        if (!StringUtils.isEmpty(currentAssignee)) {
          newTask.setCurrentAssignee(currentAssignee);
        } else {
          newTask.setCurrentAssignee(defaultTaskAssignee);
        }

        newTask.setDescription(description);
        newTask.setInstructions(instructions);
        newTask.setPriority(priority);
        taskManager.createTask(newTask);
        return newTask;
      } else {
        ProductFeedInboxNotificationServiceImpl.LOGGER
            .error("Unable to send notification, task manager unavailable");
      }
    } catch (final TaskManagerException | RuntimeException e) {
      ProductFeedInboxNotificationServiceImpl.LOGGER.error(String.format(
          "Unable to create notification task, TaskName: %s, ContentPath: %s, Description: %s, Priority: %s",
          taskName, contentPath, description, priority), e);
    }

    return null;
  }

  @ObjectClassDefinition(name = "Product inbox notification service", description = "This service sends notification to AEM inbox")
  public @interface Config {
    @AttributeDefinition(name = "Default assignee group", description = "Default assignee group to which this notification would be send")
    String defaultTaskAssignee() default "content-authors";
  }

  @Activate
  public void activate(Config config) {
    defaultTaskAssignee = config.defaultTaskAssignee();
  }
}
