package com.mattel.global.core.pojo;

import com.mattel.global.core.constants.Constant;

public class ResponseBody {
    private String content;
    private String contentType = Constant.CONTENT_TYPE_APPLICATION_JSON;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "ResponseBody [content=" + content + ", contentType=" + contentType + "]";
    }
}
