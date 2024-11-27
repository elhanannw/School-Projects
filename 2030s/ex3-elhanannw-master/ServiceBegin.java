/**
* @author elhanannw
*/
class ServiceBegin extends Event {
  private Customer customer;
  private Counter counter;
  private CoffeeShop shop;

  public ServiceBegin(double time, Customer customer, 
      Counter counter, CoffeeShop shop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.shop = shop;
  }

  @Override
  public Event[] simulate() {
    counter.makeBusy();
    double endTime = this.getTime() + customer.getServiceTime();
    return new Event[] { 
      new ServiceEnd(endTime, customer, counter, shop)
    };
  } 

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " ordered " 
        + customer.orderType() + " (by" + counter.toString() + ")";
    return super.toString() + str;
  }

}


