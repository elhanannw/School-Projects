class JoinQueue extends Event {
  private Customer customer;
  private Queue queue;

  public JoinQueue(double time, Customer customer, Queue queue) {
    super(time);
    this.customer = customer;
    this.queue = queue;
  }

  @Override
  public Event[] simulate() {
    queue.enq(this.customer);
    return new Event[] {};
  }

  @Override 
  public String toString() {
    String str = ": " + customer.toString() + " joined queue " 
        + queue.toString();
    return super.toString() + str;
  } 

}
