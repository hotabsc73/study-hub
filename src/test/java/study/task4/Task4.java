package study.task4;

import com.study.dto.TestResult;
import com.study.enums.Status;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task4 {

  public static void printSummary(List<TestResult> run) {
    System.out.println("=== Сводка прогона ===");
    if (run == null || run.isEmpty()) {
      System.out.println("результатов нет");
      return;
    }
    Map<Status, List<String>> t1 = run.stream()
        .collect(Collectors.groupingBy(
            TestResult::status,
            Collectors.mapping(TestResult::name, Collectors.toList())
        ));
    t1.forEach((key, value) ->
        System.out.printf("%s (%d): %s%n", key, value.size(), String.join(", ", value)));

    System.out.printf("Топ-3 долгих: %s", run.stream()
        .sorted(Comparator.comparing(TestResult::durationMs).reversed())
        .map(TestResult::name)
        .limit(3)
        .collect(Collectors.joining(", ")));

    System.out.printf("\nСреднее время прошедших: %.2f ms", run.stream()
        .collect(Collectors.averagingLong(TestResult::durationMs)));
  }

}
