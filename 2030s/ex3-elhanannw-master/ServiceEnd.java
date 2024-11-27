/**
* @author elhanannw
*/
class ServiceEnd extends Event {
  private Customer customer;
  private Counter counter;
  private CoffeeShop shop;

  public ServiceEnd(double time, Customer customer, 
      Counter counter, CoffeeShop shop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.shop = shop;
  }

  @Override
  public Event[] simulate() {
    counter.makeAvailable();
    if (counter.checkCounterQEmpty() && shop.checkShopQEmpty()) { 
      return new Event [] { 
          new Departure(this.getTime(), customer)
          };
    } else if (counter.checkCounterQEmpty() && !shop.checkShopQEmpty()) {
      Customer nextShopCustomer = shop.leaveShopQ();
      return new Event[] {
          new Departure(this.getTime(), customer),
          new ServiceBegin(this.getTime(), nextShopCustomer, counter, shop)
          };
    } else if (!counter.checkCounterQEmpty() && shop.checkShopQEmpty()) {
      Customer nextCounterCustomer = counter.leaveCounterQ(); 
      return new Event[] {
          new Departure(this.getTime(), customer),
          new ServiceBegin(this.getTime(), nextCounterCustomer, counter, shop)
          };
    } else if (!counter.checkCounterQEmpty() && !shop.checkShopQEmpty()) {
      Customer nextShopCustomer = shop.leaveShopQ();
      Customer nextCounterCustomer = counter.leaveCounterQ();
      return new Event[] {
          new Departure(this.getTime(), customer),
          new ServiceBegin(this.getTime(), nextCounterCustomer, counter, shop),
          new JoinCounterQueue(0.05 + this.getTime(), nextShopCustomer, counter)
          };
    }
    return new Event[] {};
  }

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " served " 
        + customer.orderType() + " (by" + counter.toString() + ")";
    return super.toString() + str;
  } 

}
