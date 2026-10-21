package study.task5;

import com.study.dto.TestResult;
import com.study.enums.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ParallelRunner {

  private static final List<Status> STATUSES = Arrays.stream(Status.values()).toList();
  private static final int COUNT_POOL = 4;
  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  public static void run() {
    ExecutorService executor = Executors.newFixedThreadPool(COUNT_POOL);
    try {
      List<Callable<TestResult>> tasks = new ArrayList<>();
      int i = 0;
      while (i < 8) {
        tasks.add(generate());
        i++;
      }
      long ms1 = System.nanoTime();
      List<Future<TestResult>> futures = executor.invokeAll(tasks);
      long ms2 = System.nanoTime();
      long actualRunTime = TimeUnit.NANOSECONDS.toMillis(ms2 - ms1);

      List<TestResult> results = futures.stream()
          .map(future -> {
            try {
              return future.get();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              throw new RuntimeException(e);
            } catch (ExecutionException e) {
              throw new RuntimeException(e);
            }
          }).toList();

      var durationMsAll = new AtomicLong();
      Map<String, Long> statistics = results.stream()
          .peek(result -> durationMsAll.addAndGet(result.durationMs()))
          .collect(Collectors.groupingBy(t -> t.status().name(), Collectors.counting()));
      long expectedRunTime = durationMsAll.get();

      System.out.printf("Прошло: %d, упало: %d, игнорировано: %d",
          statistics.getOrDefault(Status.PASSED.name(), 0L),
          statistics.getOrDefault(Status.FAILED.name(), 0L),
          statistics.getOrDefault(Status.SKIPPED.name(), 0L)
      );

      System.out.printf("\nСуммарное время тестов: %d ms", expectedRunTime);
      System.out.printf("\nРеальное время прогона: %d ms", actualRunTime);
      System.out.printf("\nПараллельность сэкономила: %d ms", expectedRunTime - actualRunTime);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println(e);
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      executor.shutdown();
    }
  }

  private static Callable<TestResult> fakeTest(String name, long durationMs, Status status) {
    return () -> {
      Thread.sleep(durationMs);
      return new TestResult(name, status, durationMs);
    };
  }

  private static Callable<TestResult> generate() {
    return fakeTest("test" + RANDOM.nextInt(), RANDOM.nextLong(300, 500 + 1),
        STATUSES.get(RANDOM.nextInt(STATUSES.size())));
  }

}
