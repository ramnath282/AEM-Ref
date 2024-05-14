package com.mattel.global.core.interfaces;

import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.pojo.ConsumerPreferenceResponse;

import java.util.Map;

public interface ConsumerPreferenceService extends BaseService{
    ConsumerPreferenceResponse updateEmailPreference(Map<String, Object> requestMap) throws ServiceException;
    ConsumerPreferenceResponse fetchEmailPreference(Map<String, Object> requestMap) throws ServiceException;
}
