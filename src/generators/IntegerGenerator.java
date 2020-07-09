package generators;

public interface IntegerGenerator extends DataGenerator<Integer> {

  Integer next();

  boolean hasNext();
}
