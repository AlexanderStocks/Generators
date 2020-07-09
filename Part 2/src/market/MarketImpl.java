package market;

import datastructures.Stock;
import datastructures.StockImpl;
import domain.Agent;
import domain.producttypes.Product;
import domain.producttypes.RawMaterial;
import goods.Book;
import goods.RawCotton;
import goods.RawPaper;
import java.util.Optional;

public class MarketImpl implements Market {

  Stock<RawCotton> newCottonStock = new StockImpl<>();

  Stock<RawCotton> recycledCottonStock = new StockImpl<>();

  Stock<RawPaper> recycledPaperStock = new StockImpl<>();

  Stock<RawPaper> newPaperStock = new StockImpl<>();

  Stock<Book> bookStock = new StockImpl<>();

  Stock<Product> disposedStock = new StockImpl<>();
  @Override
  public void sellRawCotton(RawCotton item, Agent agent) {
    if (item.origin == RawMaterial.Origin.NEW) {
      newCottonStock.push(item, agent);
    } else {
      recycledCottonStock.push(item, agent);
    }
  }

  @Override
  public Optional<RawCotton> buyRawCotton() {
    if (recycledCottonStock.size() > 0) {
      return recycledCottonStock.pop();
    } else {
      return newCottonStock.pop();
    }
  }

  @Override
  public void sellRawPaper(RawPaper item, Agent agent) {
    if (item.origin == RawMaterial.Origin.NEW) {
      newPaperStock.push(item, agent);
    } else {
      recycledPaperStock.push(item, agent);
    }

  }

  @Override
  public Optional<RawPaper> buyRawPaper() {
    if (recycledPaperStock.size() > 0) {
      return recycledPaperStock.pop();
    } else {
      return newPaperStock.pop();
    }
  }

  @Override
  public void sellBook(Book item, Agent agent) {
    bookStock.push(item, agent);
  }

  @Override
  public Optional<Book> buyBook() {
    return bookStock.pop();
  }

  @Override
  public void disposeBook(Book item, Agent agent) {
    disposedStock.push(item, agent);
  }

  @Override
  public Optional<Product> collectDisposedGood() {
    return disposedStock.pop();
  }
}
