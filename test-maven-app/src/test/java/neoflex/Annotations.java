package neoflex;

import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Annotations {

  private static final Logger logger = LogManager.getLogger();

  @Parameters({"username", "city"})
  @Test
  public void test(@Optional("<name>") String username, @Optional("<city>") String city) {
    TestContent content = new TestContent();
    boolean condition1 = (username.equals(content.getUsername()));
    boolean condition2 = (city.equals(content.getCity()));
    logger.info("Romka == "+ username + " ? " + condition1);
    logger.info("Penza == "+ city + " ? " + condition2);
    logger.info("the test is over");
  }
  
  @AfterSuite
  public void reportReady() {
    logger.info("the report is ready :)");
  }
}

