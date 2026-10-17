package study.task3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrequencyTest {

  public static <T> Map<T, Long> frequency(T[] items) {
    Map<T, Long> results = new HashMap<>();
    Arrays.stream(items)
        .forEach(item -> results.merge(item, 1L, Long::sum));
    return results;
  }

}
