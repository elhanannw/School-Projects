/**
 * This class encapsulates the arrival event in the coffee shop
 **/
class ServiceEnd extends Event {
  private int customerId;
  private boolean[] available;
  public int counterId;

  public ServiceEnd(double time, int customerId, 
      boolean[] available, int counterId) {
    super(time);
    this.customerId = customerId;
    this.counterId = counterId;
    this.available = available;
  }

  @Override
  public Event[] simulate() {
    this.available[this.counterId] = true;
    return new Event[] { 
      new Departure(this.getTime(), this.customerId),
    };
  }

  @Override
  public String toString() {
    return super.toString() + ": Customer " + this.customerId + " service done (by Counter "
      + this.counterId + ")";
  } 


}
