package study.task2;

import com.study.dto.Errors;
import com.study.dto.TestLog;
import com.study.dto.TestResult;
import com.study.enums.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestLogParser {

  private static final int COUNT_FILED = 3;
  private static final String REGEX_UNKNOWN_ERROR = "Нет разделителя";
  private static final String STATUS_UNKNOWN_ERROR = "Статус неизвестен: %s";
  private static final String TIME_IS_NOT_NUMBER_ERROR = "Время должно быть числом";
  private static final String NEGATIVE_DURATION_ERROR = "Отрицательная длительность";

  public static TestLog parse(List<String> lines) {
    Map<String, TestResult> results = new HashMap<>(); // todo LinkedHashMap если нужен порядок тестов как в run.log
    List<Errors> errors = new ArrayList<>();

    lines
        .forEach(line -> {
          List<Errors> errorsLocal = new ArrayList<>();
          String[] data = line.split("\\|");
          if (data.length != COUNT_FILED) {
            errorsLocal.add(new Errors(line, REGEX_UNKNOWN_ERROR));
            return;
          }
          if (!Status.isStatus(data[1])) {
            errorsLocal.add(new Errors(line, String.format(STATUS_UNKNOWN_ERROR, data[1])));
          }
          String data2 = data[2];
          if (!isLong(data2)) {
            errorsLocal.add(new Errors(line, TIME_IS_NOT_NUMBER_ERROR));
          } else if (Long.parseLong(data2) <= 0) {
            errorsLocal.add(new Errors(line, NEGATIVE_DURATION_ERROR));
          }
          if (errorsLocal.isEmpty()) {
            results.put(data[0], new TestResult(
                data[0], Status.valueOf(data[1]), Long.parseLong(data2)
            ));
          }
          errors.addAll(errorsLocal);
        });

    List<TestResult> resultsUniq = results.values()
        .stream()
        .distinct()
        .toList();

    Optional<TestResult> firstFailed = getFirstFailed(resultsUniq);

    return new TestLog(resultsUniq, errors, firstFailed);
  }

  private static Optional<TestResult> getFirstFailed(List<TestResult> results) {
    return results.stream()
        .filter(result -> Status.PASSED != result.status())
        .findFirst();
  }

  public static boolean isLong(String str) {
    try {
      Long.parseLong(str);
      return true;
    } catch (NumberFormatException e) {
      System.out.println("#warn: " + e.getMessage());
      return false;
    }
  }
}
