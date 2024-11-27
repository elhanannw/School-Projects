/**
 * This class encapsulates the arrival event in the coffee shop
 **/
class Departure extends Event {
  private int customerId;

  public Departure(double time, int customerId) {
    super(time);
    this.customerId = customerId;
  }



  @Override
  public Event[] simulate() {
    return new Event[] {};  
  }

  @Override
  public String toString() {
    return super.toString() + ": Customer " + this.customerId + " departed";
  }



}
