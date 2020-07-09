package generators;

public class PairGenerator<S, T> implements DataGenerator<Pair<S, T>> {

  private DataGenerator<S> sDataGenerator;

  private DataGenerator<T> tDataGenerator;

  public PairGenerator (DataGenerator<S> sDataGenerator, DataGenerator<T> tDataGenerator) {
    this.sDataGenerator = sDataGenerator;
    this.tDataGenerator = tDataGenerator;
  }


  @Override
  public Pair<S, T> next() {
    return new Pair<>(sDataGenerator.next(), tDataGenerator.next());
  }

  @Override
  public boolean hasNext() {
    return sDataGenerator.hasNext() && tDataGenerator.hasNext();
  }
}
