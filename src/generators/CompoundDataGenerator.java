package generators;

import java.util.List;

public class CompoundDataGenerator<T> implements DataGenerator<T>{

  private List<DataGenerator<T>> generators;

  private DataGenerator<T> currentGenerator;

  private int index = 0;

  public CompoundDataGenerator(List<DataGenerator<T>> generators) {
    this.generators = generators;
    currentGenerator = generators.get(index);
  }


  @Override
  public T next() {
    while (!currentGenerator.hasNext()) {
      index++;
      currentGenerator = generators.get(index);
    }
    return currentGenerator.next();
  }

  @Override
  public boolean hasNext() {
    return !(!currentGenerator.hasNext() && generators.size() == 0);
  }
}
