package study.task2;

public class StringBuilderClient {

  public static void use() {
    SimpleStringBuilder builder = new SimpleStringBuilder();

    builder.append("Hello");     // Hello
    builder.append(", ");        // Hello,
    builder.append("world");     // Hello, world
    builder.undo();              // Hello,
    builder.append("AQA!");      // Hello, AQA!

    System.out.println("Результат:");
    System.out.println(builder);

    builder.clear();
  }

}
