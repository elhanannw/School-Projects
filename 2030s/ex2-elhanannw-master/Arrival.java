/**
* @author elhanannw
*/
class Arrival extends Event {
  private Customer customer;
  private CoffeeShop shop;
  private Queue queue;

  public Arrival(double time, Customer customer,
      CoffeeShop shop, Queue queue) {
    super(time);
    this.customer = customer;
    this.shop = shop;
    this.queue = queue;
  } 

  @Override
  public Event[] simulate() {
    Counter counter = shop.getAvailableCounter();
    if (counter.isAvailable() == false) {
      if (!queue.isFull()) {
        return new Event[] {
          new JoinQueue(this.getTime(), customer, queue) 
        };
      } else { 
        return new Event[] { 
          new Departure(this.getTime(), customer) 
        };
      }
    } else {
      counter.makeBusy();
      return new Event[] { 
        new ServiceBegin(this.getTime(), customer, counter, queue)
      };
    }
  }

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " arrives " 
        + queue.toString();
    return super.toString() + str;
  }

}


