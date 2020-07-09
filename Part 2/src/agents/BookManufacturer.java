package agents;

import domain.Agent;
import goods.Book;
import goods.RawCotton;
import goods.RawPaper;
import market.Market;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookManufacturer extends Agent {

  public BookManufacturer(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }



  @Override
  public void doAction() {
    RawPaper rawPaper = null;
    List<RawCotton> cottonList = new ArrayList<>(2);
    while (rawPaper == null) {
      Optional<RawPaper> attemptedPaper = market.buyRawPaper();
      if (attemptedPaper.isPresent()) {
        rawPaper = attemptedPaper.get();
      }
    }
    while (cottonList.size() < 2) {
      Optional<RawCotton> attemptedPaper = market.buyRawCotton();
      attemptedPaper.ifPresent(cottonList::add);
    }

    market.sellBook(new Book(rawPaper, cottonList.get(0), cottonList.get(1)), this);
  }
}
