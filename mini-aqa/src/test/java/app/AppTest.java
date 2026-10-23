package app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

public class AppTest {

  @Test
  public void greet_happyPath() {
    Properties prop = new Properties();
    try (InputStream input = AppTest.class.getClassLoader().getResourceAsStream("app.properties")) {
      if (input == null) {
        System.out.println("file not found");
        return;
      }
      prop.load(input);
      String value = prop.getProperty("student.name");

      String actual = App.greet(value);

      Assertions.assertEquals("Привет, Katya!", actual);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @ParameterizedTest
  @EmptySource
  @NullSource
  public void greet_whenEmptyOrNull_thenDefault(String name) {
    String actual = App.greet(name);

    Assertions.assertEquals("Привет!", actual);
  }

}
