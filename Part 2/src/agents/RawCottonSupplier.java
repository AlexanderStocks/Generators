package agents;

import domain.Agent;
import domain.producttypes.RawMaterial;
import goods.RawCotton;
import market.Market;

public class RawCottonSupplier extends Agent {

  public RawCottonSupplier(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    market.sellRawCotton(new RawCotton(RawMaterial.Origin.NEW), this);
  }
}
