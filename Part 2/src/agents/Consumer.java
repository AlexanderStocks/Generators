package agents;

import domain.Agent;
import goods.Book;
import market.Market;

import java.util.Optional;

public class Consumer extends Agent {

  public Consumer(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    Optional<Book> book = market.buyBook();
    if (book.isPresent()) {
      think();
      market.disposeBook(book.get(), this);
    }
  }
}
