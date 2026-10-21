package study.task5;

import com.study.dto.ProductInfo;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class DataAggregator {

  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  public static ProductInfo aggregateProductInfo(String productName) {
    CompletableFuture<BigDecimal> fetchPrice = CompletableFuture
        .supplyAsync(DataAggregator::fetchPrice)
        .exceptionally(ex -> BigDecimal.ZERO);
    CompletableFuture<String> fetchDescription = CompletableFuture
        .supplyAsync(DataAggregator::fetchDescription)
        .exceptionally(ex -> "Нет данных");
    CompletableFuture<Double> fetchRating = CompletableFuture
        .supplyAsync(DataAggregator::fetchRating)
        .exceptionally(ex -> 0.0);

    CompletableFuture<Void> results = CompletableFuture.allOf(fetchPrice, fetchDescription, fetchRating);
    results.join();

    return new ProductInfo(productName, fetchPrice.join(), fetchDescription.join(), fetchRating.join());
  }

  private static BigDecimal fetchPrice() {
    task();
    return new BigDecimal("899.99");
  }

  private static String fetchDescription() {
    task();
    return "Описание";
  }

  private static double fetchRating() {
    task();
    return 4.7;
  }

  private static void task() {
    try {
      Thread.sleep(RANDOM.nextInt(1000, 3000 + 1));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
    if (RANDOM.nextDouble() < 0.2) {
      throw new RuntimeException("сработала вероятность 20%");
    }
  }

}
