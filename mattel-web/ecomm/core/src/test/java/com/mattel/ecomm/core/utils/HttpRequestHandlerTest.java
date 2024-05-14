package com.mattel.ecomm.core.utils;

import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;

/** Integration test case for  {@link HttpRequestHandlerTest}*/
public class HttpRequestHandlerTest {
  @Test(expected = IOException.class, timeout=20000)
  public void testConnectionTimeout() throws IOException {
    final long connectionTimeout = 5000;
    final long upperBound = 15000;
    String endPointUrl = "https://localhost:port/test";
    final MockWebServer mockWebServer = new MockWebServer();
    final CountDownLatch countDownLatch = new CountDownLatch(1);

    final Dispatcher dispatcher = new Dispatcher() {
      @Override
      public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        return new MockResponse();
      }

      @Override
      public MockResponse peek() {
        try {
          countDownLatch.await(upperBound, TimeUnit.MILLISECONDS);
        } catch (final InterruptedException e) {
          e.printStackTrace();
        }
        return new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START);
      }
    };

    final int port = ThreadLocalRandom.current().nextInt(11111, 12111);
    mockWebServer.setDispatcher(dispatcher);
    mockWebServer.start(port);

    endPointUrl = endPointUrl.replace("port", String.valueOf(port));
    final long st = System.currentTimeMillis();
    try {

      final CloseableHttpClient client = HttpRequestHandler.createCustom((int) connectionTimeout);
      HttpRequestHandler.get(client, endPointUrl, null, null, null);
    } finally {
      final long delta = System.currentTimeMillis() - st;
      countDownLatch.countDown();
      mockWebServer.shutdown();
      mockWebServer.close();
      Assert.assertTrue(delta >= 5000 && delta <= 15000);
    }
  }
}
