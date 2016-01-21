package yd.foo;

import yd.robot.MyJavaHttpKeywords;

public class Foo {
  public static void main(final String[] args) throws Exception {
    MyJavaHttpKeywords jkw = new MyJavaHttpKeywords();
    try {
      jkw.openHttpClient();
      jkw.sendGetRequestTo("http://httpbin.org/get");
      jkw.responseShouldHaveStatus(200);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Shutting down");
      jkw.closeHttpClient();
    }
    System.out.println("Done");
  }
}
