package uds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by peter on 5/9/17.
 */
public class Config {

  public static final String URL = "github.url";
  public static final String USERNAME = "github.username";
  public static final String PASSWORD = "github.password";
  public static final String TOKEN = "github.token";

  public static final String FILE = "config.properties";
  private static final Logger log = LoggerFactory.getLogger(Config.class);

  private final Properties p = new Properties();

  public void load() throws IOException {
    InputStream stream = Config.class.getClassLoader().getResourceAsStream(FILE);
    if (stream == null) {
      throw new IOException();
    }
    p.clear();
    p.load(stream);
    log.debug("Loaded config {}", p);
  }

  public String getString(String param) {
    if (p.containsKey(param)) {
      return p.getProperty(param);
    }
    throw new RuntimeException("Property missing: " + param);
  }

  public String getUrl() {
    return getString(URL);
  }

  public String getUsername() {
    return getString(USERNAME);
  }

  public String getPassword() {
    return getString(PASSWORD);
  }

  public String getToken() {
    return getString(TOKEN);
  }


}
