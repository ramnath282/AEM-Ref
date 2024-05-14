package com.mattel.ecomm.core.importer.workflow.interfaces;

import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.granite.taskmanagement.Task;
import com.adobe.granite.workflow.exec.InboxItem.Priority;

public interface ProductFeedInboxNotificationService {
    Task createNotificationTask(String taskName, String contentPath, String currentAssignee, String description,
            String instructions, Priority priority, ResourceResolver resourceResolver);
}
