package goods;

import domain.producttypes.Product;
import java.util.Set;

public class Book extends Product {

  public Book(RawPaper paper1, RawCotton cotton1, RawCotton cotton2) {
    super(Set.of(paper1, cotton1, cotton2));
  }
}
