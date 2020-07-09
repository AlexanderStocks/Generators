package market;

import domain.Agent;
import domain.producttypes.Product;
import goods.Book;
import goods.RawCotton;
import goods.RawPaper;
import java.util.Optional;

public interface Market {

  void sellRawCotton(RawCotton item, Agent agent);

  Optional<RawCotton> buyRawCotton();

  void sellRawPaper(RawPaper item, Agent agent);

  Optional<RawPaper> buyRawPaper();

  void sellBook(Book item, Agent agent);

  Optional<Book> buyBook();

  void disposeBook(Book item, Agent agent);

  Optional<Product> collectDisposedGood();
}
