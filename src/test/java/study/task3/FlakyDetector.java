package study.task3;

import com.study.enums.Status;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FlakyDetector {

  public static Set<String> failed(List<String> run) {
    return getTestNameByStatus(run, Status.FAILED);
  }

  public static Set<String> passed(List<String> run) {
    return getTestNameByStatus(run, Status.PASSED);
  }

  public static List<String> flaky(List<String> run1, List<String> run2) {
    Set<String> failedTests = failed(run1);
    Set<String> passedTests = passed(run2);

    failedTests.retainAll(passedTests);

    return failedTests.stream().toList();
  }

  public static Map<String, Long> statusSummary(List<String> run) {
    Map<String, Long> results = new HashMap<>();
    for (String str : run) {
      String[] rows = str.split("\\|");
      results.merge(rows[1], 1L, Long::sum);
    }
    return results;
  }

  private static Set<String> getTestNameByStatus(List<String> run, Status expectedStatus) {
    return run.stream()
        .map(str -> str.split("\\|"))
        .filter(values -> expectedStatus.name().equalsIgnoreCase(values[1]))
        .map(values -> values[0])
        .collect(Collectors.toSet());
  }

}
