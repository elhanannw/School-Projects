/**
* @author elhanannw
*/
class ServiceBegin extends Event {
  private Customer customer;
  private Counter counter;
  private Queue queue;

  public ServiceBegin(double time, Customer customer, 
      Counter counter, Queue queue) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.queue = queue;
  }

  @Override
  public Event[] simulate() {
    double endTime = this.getTime() + customer.getServiceTime();
    return new Event[] { 
      new ServiceEnd(endTime, customer, counter, queue)
    };
  } 

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " ordered " 
        + customer.orderType() + " (by" + counter.toString() + ")";
    return super.toString() + str;
  }

}


