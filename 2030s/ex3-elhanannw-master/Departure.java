class Departure extends Event {
  private Customer customer;

  public Departure(double time, Customer customer) {
    super(time);
    this.customer = customer;
  }

  @Override
  public Event[] simulate() {
    return new Event[] {};  
  }

  @Override
  public String toString() {
    String str = ": " + customer.toString() + " departed";
    return super.toString() + str;
  }

}
