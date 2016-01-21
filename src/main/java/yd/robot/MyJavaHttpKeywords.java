package yd.robot;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * This is an example for a Keyword Library for the Robot Framework.
 */
public class MyJavaHttpKeywords {

  /** This means the same instance of this class is used throughout
   *  the lifecycle of a Robot Framework test execution.
   */
  public static final String ROBOT_LIBRARY_SCOPE = "TEST SUITE";

  /** The Functionality to be tested */
  private CloseableHttpAsyncClient httpclient;
  private HttpResponse response;

  public void openHttpClient() {
    httpclient = HttpAsyncClients.createDefault();
    httpclient.start();
  }

  public void sendGetRequestTo(String url) throws ExecutionException, InterruptedException {
    HttpGet request = new HttpGet(url);
    Future<HttpResponse> future = httpclient.execute(request, null);
    response = future.get();
  }

  public void responseShouldHaveStatus(int status) throws Exception {
    int resp_status = response.getStatusLine().getStatusCode();
    if (status != resp_status) {
      throw new Exception(String.format("Expect status to be %d, but got %d", status, resp_status));
    }
  }

  public void closeHttpClient() throws IOException {
    httpclient.close();
  }
}


