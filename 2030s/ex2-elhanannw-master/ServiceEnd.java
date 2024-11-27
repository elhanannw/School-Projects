class ServiceEnd extends Event {
  private Customer customer;
  private Counter counter;
  private Queue queue;

  public ServiceEnd(double time, Customer customer, 
      Counter counter, Queue queue) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.queue = queue;
  }

  @Override
  public Event[] simulate() {
    counter.makeAvailable();
    Customer nextCustomer = (Customer) queue.deq();
    if (nextCustomer != null || !queue.isEmpty()) {
      counter.makeBusy();
      return new Event[] { 
        new Departure(this.getTime(), customer),
        new ServiceBegin(this.getTime(), nextCustomer, counter, queue)
      };
    } else { 
      return new Event[] { 
        new Departure(this.getTime(), customer)
      };
    }
  }

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " served " + customer.orderType() + 
        " (by" + counter.toString() + ")";
    return super.toString() + str;
  } 

}
