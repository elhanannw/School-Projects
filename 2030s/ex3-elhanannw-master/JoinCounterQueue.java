class JoinCounterQueue extends Event {
  private Customer customer;
  private Counter counters;

  public JoinCounterQueue(double time, Customer customer, Counter counters) {
    super(time);
    this.customer = customer;
    this.counters = counters;
  }

  @Override
  public Event[] simulate() {
    counters.enterCounterQ(this.customer);
    return new Event[] {};
  }

  @Override 
  public String toString() {
    String str = ": " + customer.toString() + " joined barista queue (at" 
        + counters.toString() + ")";
    return super.toString() + str;
  } 

}
