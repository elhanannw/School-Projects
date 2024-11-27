/**
 * @author elhanannw
 **/
class ServiceBegin extends Event {
  private int customerId;
  private double serviceTime;
  private boolean[] available;
  public int counterId;

  public ServiceBegin(double time, int customerId, 
      double serviceTime, boolean[] available, int counterId) {
    super(time);
    this.customerId = customerId;
    this.serviceTime = serviceTime;
    this.available = available;
    this.counterId = counterId;
  }


  @Override
  public Event[] simulate() {
    this.available[this.counterId] = false;
    double endTime = this.getTime() + this.serviceTime;
    return new Event[] { 
      new ServiceEnd(endTime, this.customerId, 
          this.available, this.counterId)
    };
  } 


  @Override
  public String toString() {
    return super.toString() + ": Customer " + this.customerId + " service begin (by Counter "
      + this.counterId + ")";

  }
}


