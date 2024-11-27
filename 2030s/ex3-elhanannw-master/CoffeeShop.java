/**
* @author elhanannw
*/
class CoffeeShop {
  private int numOfCounters;
  private Seq<Counter> counters;
  private CoffeeQueue<Customer> shopQ;

  public CoffeeShop(int numOfCounters, int maxCounterQueueLength,
      int maxShopQueueLength) {
    this.numOfCounters = numOfCounters;
    shopQ = new CoffeeQueue<>(maxShopQueueLength);
    this.counters = new Seq<>(numOfCounters);
    for (int i = 0; i < numOfCounters; i++) {
      counters.set(i, new Counter(true, maxCounterQueueLength));
    }
  }

  public Counter getAvailableCounter() {
    for (int i = 0; i < numOfCounters; i += 1) {
      Counter counter = counters.get(i);
      if (counter.isAvailable()) {
        return counter;
      }
    }
    return null;
  }

  public boolean checkAllQFull() {
    if (shortestBaristaCounter() == null && checkShopQFull()) {
      return true;
    }
    return false;
  }

  public Counter shortestBaristaCounter() {
    Counter shortestCounter = counters.min();
    if (shortestCounter.checkCounterQFull() == true) {
      return null;
    } else {
      return shortestCounter;
    }
  }

  public boolean checkShopQFull() {
    return shopQ.isFull();
  }

  public boolean checkShopQEmpty() {
    return shopQ.isEmpty();
  }

  public void enterShopQ(Customer customer) {
    shopQ.enq(customer);
  }

  public Customer leaveShopQ() {
    Customer nextCustomer = (Customer) shopQ.deq();
    return nextCustomer;
  }

  @Override
  public String toString() {
    String str = " " + shopQ.toString();
    return str;
  }

}
