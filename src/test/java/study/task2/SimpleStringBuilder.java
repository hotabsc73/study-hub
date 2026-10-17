package study.task2;

import java.util.Stack;

public class SimpleStringBuilder {

  private final Stack<String> history = new Stack<>();

  public void append(String str) {
    history.push(str);
  }

  public void undo() {
    if (!history.isEmpty()) {
      history.pop();
    }
  }

  public void clear() {
    history.clear();
  }

  @Override
  public String toString() {
    return String.join("", history);
  }

}
