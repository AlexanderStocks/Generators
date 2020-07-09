package agents;

import domain.Agent;
import domain.producttypes.RawMaterial;
import goods.RawPaper;
import market.Market;

public class RawPaperSupplier extends Agent {

  public RawPaperSupplier(int thinkingTimeInMillis, Market market) {
    super(thinkingTimeInMillis, market);
  }

  @Override
  public void doAction() {
    market.sellRawPaper(new RawPaper(RawMaterial.Origin.NEW), this);
  }
}
