package uds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by peter on 5/9/17.
 */
public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    Config c = new Config();
    c.load();
    String token = Request.register(c);
    System.out.println(token);
  }
}
