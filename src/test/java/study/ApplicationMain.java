package study;

import com.study.dto.TestLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import study.task2.StringBuilderClient;
import study.task2.TestLogParser;

public class ApplicationMain {

  public static void main(String[] args) {

    System.out.println("call java-core-task-2");
    TestPlanScanner.buildPlan(Checklist.class);

    System.out.println("\n");
    System.out.println("call java-core-task-1");
    printTestsLog();

    System.out.println("\n");
    System.out.println("call java-core-task-1-2");
    StringBuilderClient.use();

  }

  private static void printTestsLog() {
    Path path = Path.of("src/test/resources/run.log");
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      TestLog result = TestLogParser.parse(lines);
      printResult(result);
    } catch (IOException e) {
      System.out.println("#warn: " + e);
    }
  }

  private static void printResult(TestLog result) {
    System.out.println("Результаты:");
    result.testResults().forEach(System.out::println);
    System.out.println("Битые строки:");
    result.errors().forEach(System.out::println);
    System.out.println("Первый упавший: ");
    result.firstFailed().ifPresentOrElse(
        System.out::println,
        () -> System.out.println("\tпадений нет")
    );
  }

}
