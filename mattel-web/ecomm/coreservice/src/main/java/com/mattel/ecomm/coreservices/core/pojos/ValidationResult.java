package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    boolean success = true;
    List<String> errorMessages = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public List<String> getErrorMessages() {
        return errorMessages;
    }
    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }
}
