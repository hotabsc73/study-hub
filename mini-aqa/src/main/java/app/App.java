package app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {

  public static void main(String[] args) {
    Properties prop = new Properties();
    try (InputStream input = App.class.getClassLoader().getResourceAsStream("app.properties")) {
      if (input == null) {
        System.out.println("file not found");
        return;
      }
      prop.load(input);
      String value = prop.getProperty("student.name");

      System.out.println(greet(value));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String greet(String name) {
    if (name == null || name.isBlank()) { // todo may be use StringUtils
      return "Привет!";
    }
    return String.format("Привет, %s!", name);
  }
}