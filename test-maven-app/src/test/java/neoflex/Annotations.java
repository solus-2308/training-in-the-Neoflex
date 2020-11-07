package neoflex;

import org.testng.annotations.*;

public class Annotations {

  @Parameters({"username", "city"})
  @Test
  public void test(@Optional("<name>") String username, @Optional("<city>") String city) {
    TestContent content = new TestContent();
    boolean condition1 = (username.equals(content.getUsername()));
    boolean condition2 = (city.equals(content.getCity()));
    System.out.println("\nRomka == "+ username + " ? " + condition1);
    System.out.println("Penza == "+ city + " ? " + condition2);
    System.out.println("the test is over");
  }
  
  @AfterSuite
  public void reportReady() {
    System.out.println("\nthe report is ready :)\n");
  }
}

