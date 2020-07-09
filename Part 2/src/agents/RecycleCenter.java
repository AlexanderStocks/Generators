package agents;

import domain.Agent;
import domain.producttypes.Product;
import domain.producttypes.RawMaterial;
import goods.RawCotton;
import goods.RawPaper;
import market.Market;

import java.util.Optional;

public class RecycleCenter extends Agent {

  public RecycleCenter(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    Optional<Product> disposedGood = market.collectDisposedGood();
    if (disposedGood.isPresent()) {
      for (RawMaterial material : disposedGood.get().getConstituentMaterials()) {
        if (material.origin == RawMaterial.Origin.NEW) {
          if (material instanceof RawPaper) {
            market.sellRawPaper((RawPaper) material, this);
          } else {
            market.sellRawCotton((RawCotton) material, this);
          }
        }
      }
    }
  }
}
