class Arrival extends Event {
  private Customer customer;
  private CoffeeShop shop;

  public Arrival(double time, Customer customer,
      CoffeeShop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  } 

  @Override
  public Event[] simulate() {
    Counter availableCounter = shop.getAvailableCounter();
    if (availableCounter == null) {
      Counter shortestCounter = shop.shortestBaristaCounter();
      if (shortestCounter != null) {
        return new Event[] {
          new JoinCounterQueue(this.getTime(), customer, shortestCounter)
        };
      } else if (shortestCounter == null && shop.checkShopQFull() == false) {
        return new Event[] {
          new JoinShopQueue(this.getTime(), customer, shop)
            };
      } else {
        return new Event[] { 
          new Departure(this.getTime(), customer) 
        };
      }
    } else {
      return new Event[] { 
        new ServiceBegin(this.getTime(), customer, availableCounter, shop)
      };
    }
  }

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " arrives" 
        + shop.toString();
    return super.toString() + str;
  }

}


