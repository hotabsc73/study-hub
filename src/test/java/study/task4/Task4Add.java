package study.task4;

import com.study.dto.WorkSheet;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Task4Add {

  public static void test() {
    System.out.println("\n");
    System.out.println("Нужно вывести дни, в которые работает больше одного человека:");
    var denSheet = new WorkSheet("Денис", List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY));
    var benSheet = new WorkSheet("Бен", List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY));
    var lisSheet = new WorkSheet("Лиза", List.of(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));
    var sheets = List.of(denSheet, benSheet, lisSheet);

    var results = sheets.stream()
        .map(WorkSheet::workDays)
        .flatMap(List::stream)
        .map(DayOfWeek::name)
        .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
    results.entrySet().stream()
        .filter(item -> item.getValue() > 1)
        .map(Entry::getKey)
        .forEach(System.out::println);
  }

}
