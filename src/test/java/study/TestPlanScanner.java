package study;

import com.study.TestId;
import java.util.Arrays;
import java.util.Comparator;

public class TestPlanScanner {

  public static void buildPlan(Class<?> clazz) {
    System.out.println("План прогона для " + clazz.getSimpleName() + ":");
    boolean isExist = Arrays.stream(clazz.getDeclaredMethods())
        .anyMatch(m -> m.isAnnotationPresent(TestId.class));
    if (!isExist) {
      System.out.println("Кейсов нет");
      return;
    }
    Arrays.stream(clazz.getDeclaredMethods())
        .filter(m -> m.isAnnotationPresent(TestId.class))
        .map(method -> {
          var testId = method.getAnnotation(TestId.class);
          return new TestStep(testId.value(), method.getName(), testId.order());
        })
        .sorted(Comparator.comparing(TestStep::order))
        .map(m -> new StringBuilder().append(m.order()).append(". ")
            .append(m.id()).append(" -> ").append(m.methodName()))
        .forEach(System.out::println);
  }

}
