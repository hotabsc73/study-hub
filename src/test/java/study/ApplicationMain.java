package study;

import com.study.dto.TestLog;
import com.study.dto.TestResult;
import com.study.enums.Status;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import study.task2.StringBuilderClient;
import study.task2.TestLogParser;
import study.task3.FlakyDetector;
import study.task3.FrequencyTest;
import study.task4.Task4;
import study.task4.Task4Add;

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

    System.out.println("\n");
    System.out.println("call java-core-task-3");
    List<String> run1 = List.of(
        "LoginTest|PASSED",
        "CreateTaskTest|FAILED",
        "CommentTest|FAILED",
        "LogoutTest|PASSED"
    );
    List<String> run2 = List.of(
        "LoginTest|PASSED",
        "CreateTaskTest|PASSED",
        "CommentTest|FAILED",
        "StatsTest|PASSED"
    );
    System.out.println("Flaky-кандидаты: " + FlakyDetector.flaky(run1, run2));
    System.out.println("Прогон 2, сводка: " + FlakyDetector.statusSummary(run2));
    String[] words = {"up", "down", "up", "up", "left"};
    Integer[] numbers = {3, 1, 3, 3, 7, 1};
    System.out.println("frequency: " + FrequencyTest.frequency(words));
    System.out.println("frequency: " + FrequencyTest.frequency(numbers));

    System.out.println("call java-core-task-4");
    List<TestResult> run4 = List.of(
        new TestResult("LoginTest", Status.PASSED, 120),
        new TestResult("CreateTaskTest", Status.PASSED, 940),
        new TestResult("UpdateStatusTest", Status.FAILED, 2100),
        new TestResult("CommentTest", Status.PASSED, 380),
        new TestResult("DeleteTaskTest", Status.FAILED, 760),
        new TestResult("StatsTest", Status.SKIPPED, 0),
        new TestResult("LogoutTest", Status.PASSED, 90)
    );
    Task4.printSummary(run4);
    Task4Add.test();
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
