package generators;

import java.util.*;
import java.util.stream.StreamSupport;

public class DigitCombinationsGenerator implements StringGenerator {

  private StringBuilder digits;
  private boolean firstTime;

  public DigitCombinationsGenerator() {
    this.digits = new StringBuilder();
    this.firstTime = true;
  }

  @Override
  public String next() {
    if (firstTime) {
      firstTime = false;
      return "";
    }
    if (digits.length() == 0) {
      digits.append('1');
      return "1";
    }
    if (digits.charAt(digits.length() - 1) == '4') {
      goLeft(digits.length() - 1);
    } else {
      char lastChar = digits.charAt(digits.length() - 1);
      digits.setCharAt(digits.length() - 1, (char) (lastChar + 1));
    }
    return digits.toString();
  }

  private void goLeft(int index) {
    digits.setCharAt(index, '1');
    if (index == 0) {
      digits.insert(0, "1");
    } else if (digits.charAt(index - 1) != '4'){
      char lastChar = digits.charAt(index - 1);
      digits.setCharAt(index - 1, (char) (lastChar + 1));
    } else {
      goLeft(index - 1);
    }
  }


  @Override
  public boolean hasNext() {
    return true;
  }
}
