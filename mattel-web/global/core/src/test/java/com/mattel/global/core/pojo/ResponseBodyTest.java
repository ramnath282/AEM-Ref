package com.mattel.global.core.pojo;

import com.mattel.global.core.constants.Constant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ResponseBodyTest {

    private ResponseBody responseBody;

    @Before
    public void setup() {
        responseBody = new ResponseBody();
        responseBody.setContent("content_here");
        responseBody.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
    }

    @Test
    public void test_toString() {
        String strings = "ResponseBody " + "[content=" + responseBody.getContent() + ", contentType="
            + responseBody.getContentType() + "]";
        assertEquals(strings, responseBody.toString());
    }
}