/**
* @author elhanannw
*/
class CoffeeShop {
  private Counter[] counters;

  public CoffeeShop(Counter[] counters) {
    this.counters = counters;
  }
  
  public Counter getAvailableCounter() {
    for (int i = 0; i < counters.length; i += 1) {
      if (counters[i].isAvailable()) {
        return counters[i];
      }
    }
    return counters[0];
  }

}
