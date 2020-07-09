package generators;

public class MissingPrimesGenerator implements IntegerGenerator{

  private int previous = 0;

  private final int THREE = 3;
  private final int SEVEN = 7;


  @Override
  public Integer next() {
    if (previous <= 0) {
      previous = 1;
    } else {
      previous++;
      while ((previous % THREE == 0) || (previous % SEVEN == 0)){
        if ((previous % THREE == 0) && (previous % SEVEN == 0)) {
          break;
        } else {
          previous++;
        }
      }
    }
    return previous;
  }

  @Override
  public boolean hasNext() {
    return true;
  }
}
