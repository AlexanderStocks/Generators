package generators;

import java.util.LinkedList;
import java.util.List;

public class FixedIntegerSequenceGenerator implements IntegerGenerator{

  private int index = 0;

  private final int LIMIT = 19;

  @Override
  public Integer next() {
    if (index > LIMIT) {
      throw new UnsupportedOperationException("Limit reached");
    } else {
      int toReturn = index;
      index++;
      return toReturn;
    }
  }

  @Override
  public boolean hasNext() {
    return index - 1 < LIMIT;
  }
}
