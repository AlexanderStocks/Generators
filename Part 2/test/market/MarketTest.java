package market;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import agents.BookManufacturer;
import agents.Consumer;
import agents.RawCottonSupplier;
import agents.RawPaperSupplier;
import agents.RecycleCenter;
import domain.Agent;
import domain.producttypes.Product;
import domain.producttypes.RawMaterial.Origin;
import goods.Book;
import goods.RawCotton;
import goods.RawPaper;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import testutils.MockAgent;

public class MarketTest {

  private Market market;
  private Agent agent0, agent1;
  private RawPaper newPaper1, newPaper2, recycledPaper1, recycledPaper2;
  private RawCotton newCotton1, newCotton2, recycledCotton1, recycledCotton2;

  @Before
  public void setup() {
    market = new MarketImpl();

    newPaper1 = new RawPaper(Origin.NEW);
    newPaper2 = new RawPaper(Origin.NEW);
    recycledPaper1 = new RawPaper(Origin.RECYCLED);
    recycledPaper2 = new RawPaper(Origin.RECYCLED);

    newCotton1 = new RawCotton(Origin.NEW);
    newCotton2 = new RawCotton(Origin.NEW);
    recycledCotton1 = new RawCotton(Origin.RECYCLED);
    recycledCotton2 = new RawCotton(Origin.RECYCLED);

    agent0 = new MockAgent(market);
    agent1 = new MockAgent(market);
  }

  @Test
  public void marketHasInitiallyNoGoods() {
    assertTrue(market.buyRawPaper().isEmpty());
    assertTrue(market.buyRawCotton().isEmpty());
    assertTrue(market.buyBook().isEmpty());
    assertTrue(market.collectDisposedGood().isEmpty());
  }

  @Test
  public void productsDoNotGetLost() {
    marketHasInitiallyNoGoods();

    market.sellRawPaper(newPaper1, agent0);
    market.sellRawPaper(recycledPaper2, agent1);
    market.sellRawPaper(newPaper2, agent1);
    market.sellRawPaper(recycledPaper1, agent0);

    Set<RawPaper> boughtSet = new HashSet<>();

    Optional<RawPaper> bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    boughtSet.add(bought.get());

    bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    boughtSet.add(bought.get());

    bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    boughtSet.add(bought.get());

    bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    boughtSet.add(bought.get());

    bought = market.buyRawPaper();
    assertFalse(bought.isPresent());

    assertEquals(Set.of(newPaper1, newPaper2, recycledPaper1, recycledPaper2), boughtSet);
  }

  @Test
  public void trashDoesNotGetLost() {
    marketHasInitiallyNoGoods();

    var book1 = new Book(newPaper1, recycledCotton1, newCotton1);
    var book2 = new Book(newPaper2, recycledCotton2, newCotton2);
    market.disposeBook(book1, agent0);
    market.disposeBook(book2, agent1);

    Set<Product> boughtSet = new HashSet<>();

    Optional<Product> bought = market.collectDisposedGood();
    assertTrue(bought.isPresent());
    boughtSet.add(bought.get());

    bought = market.collectDisposedGood();
    assertTrue(bought.isPresent());
    boughtSet.add(bought.get());

    bought = market.collectDisposedGood();
    assertFalse(bought.isPresent());

    assertEquals(Set.of(book1, book2), boughtSet);
  }

  @Test
  public void recycledRawMaterialsFirstAndThenAgentsPriority() {
    marketHasInitiallyNoGoods();

    market.sellRawPaper(newPaper1, agent0);
    market.sellRawPaper(recycledPaper2, agent1);
    market.sellRawPaper(newPaper2, agent1);
    market.sellRawPaper(recycledPaper1, agent0);

    Optional<RawPaper> bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    assertEquals(recycledPaper2, bought.get());

    bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    assertEquals(recycledPaper1, bought.get());

    bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    assertEquals(newPaper2, bought.get());

    bought = market.buyRawPaper();
    assertTrue(bought.isPresent());
    assertEquals(newPaper1, bought.get());

    bought = market.buyRawPaper();
    assertFalse(bought.isPresent());
  }

  @Test
  public void lifeCycle() {
    final var paperSupplier = new RawPaperSupplier(1, market);
    final var cottonSupplier = new RawCottonSupplier(1, market);
    final var bookManufacturer = new BookManufacturer(1, market);
    final var consumer = new Consumer(1, market);
    final var recycleCenter = new RecycleCenter(1, market);

    marketHasInitiallyNoGoods();

    // New raw materials
    paperSupplier.doAction();
    cottonSupplier.doAction();
    cottonSupplier.doAction();

    // Manufacturing, consume, recycle
    bookManufacturer.doAction();
    consumer.doAction();
    recycleCenter.doAction();

    // Second manufacturing with all recycled materials (no new materials added)
    bookManufacturer.doAction();
    consumer.doAction();
    recycleCenter.doAction();

    // The market is empty
    marketHasInitiallyNoGoods();
  }
}
