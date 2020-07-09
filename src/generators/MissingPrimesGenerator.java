package generators;

public class MissingPrimesGenerator implements IntegerGenerator{

  private int previous = 0;



  @Override
  public Integer next() {
    if (previous <= 0) {
      previous = 1;
    } else {
      previous++;
      while ((previous % 3 == 0) || (previous % 7 == 0)){
        if ((previous % 3 == 0) && (previous % 7 == 0)) {
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
