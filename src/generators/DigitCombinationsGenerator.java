package generators;

import java.util.*;
import java.util.stream.StreamSupport;

public class DigitCombinationsGenerator implements StringGenerator {

  private int index = 0;

  private String previousString = "";
  private final String[] digits = {"1", "2", "3", "4"};

  public static void main(String[] args) {

  }

  @Override
  public String next() {

    if (index == 0) {
      index++;
      return previousString;
    } else if (index == 1) {
      previousString = "1";
      index++;
      return previousString;
    } else {
      for (int i = previousString.length() - 1; i >= 0; i--) {
        if (i == 0 && previousString.charAt(i) == '4') {
          previousString = "1" + previousString.substring(1);
        } else {
          if (previousString.charAt(i) == '4') { // change if have time
            previousString = previousString.substring(0, previousString.length() - 1) + "1";
          } else {
              previousString =
                previousString.substring(0, Math.max(i - 1, 0))
                  + (previousString.charAt(i) + 1)
                  + previousString.substring(i);
            }
          }
        }
      }
      index++;
    return previousString;
  }


  @Override
  public boolean hasNext() {
    return true;
  }
}
