package uds;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by peter on 5/9/17.
 */
public class Request {

  public static final String ENDPOINT_AUTHORIZATION = "authorizations";
  public static final String REGISTRATION_TOKEN = "token";

  private static final Logger log = LoggerFactory.getLogger(Request.class);


  public static String getData(String url, JSONObject params, @NotNull String token) {
    HttpResponse<String> response;


//    Unirest.setTimeouts(10000, 60000 ); //default
//    Unirest.setTimeouts(30000, 180000 );

    try {
      response = Unirest.post(url)
          .header("content-type", "application/json")
          .header("accept", "application/json")
          .header("authorization", "token " + token)
          .body(params)
          .asString();
    } catch (UnirestException e) {
      log.error("Request failed for '{}': {} with token '{}'", url, params, token, e);
      throw new RuntimeException("Request failed", e);
//      return;
    }
    if (log.isDebugEnabled()) {
      log.debug("Code = " + response.getStatus());
      log.debug("Stat = " + response.getStatusText());
      log.debug("Body = " + response.getBody());
    }
    if (response.getStatus() == 200) {
      return response.getBody();
//      log.debug("Body = " + jr.toString(2));
    }
    throw new RuntimeException("request failed: " + response.getStatus() + ": " + response.getBody());
  }

  public static String register(String url, String username, String password, @Nullable JSONObject params) {
    HttpResponse<String> response;

//    Unirest.setTimeouts(10000, 60000 ); //default
    if (params == null) {
      params = new JSONObject();
      params.put("note", "whatever2");
    }

    try {
      response = Unirest.post(url)
          .header("content-type", "application/json")
          .header("accept", "application/json")
          .basicAuth(username, password)
          .body(params)
          .asString();
    } catch (UnirestException e) {
      log.error("Request failed for '{}': {}", url, params, e);
      throw new RuntimeException("Request failed", e);
    }
    if (log.isDebugEnabled()) {
      log.debug("Code = " + response.getStatus());
      log.debug("Stat = " + response.getStatusText());
      log.debug("Body = " + response.getBody());
    }
    if (response.getStatus() == 201) {
//      log.debug("Body = " + jr.toString(2));
      return response.getBody();
    }
    throw new RuntimeException("request failed: " + response.getStatus() + ": " + response.getBody());
  }

  public static String register(Config config) {
    String res = register(config.getUrl() + ENDPOINT_AUTHORIZATION, config.getUsername(), config.getPassword(), null);
    JSONObject jo = new JSONObject(res);
    log.info(jo.toString(2));
    return jo.getString(REGISTRATION_TOKEN);
  }
}
