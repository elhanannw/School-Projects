/**
 * This class encapsulates the arrival event in the coffee shop
 **/
class Arrival extends Event {
  private int customerId;
  private double serviceTime;
  private boolean[] available;


  public Arrival(double time, int customerId, 
      double serviceTime, boolean[] available) {
    super(time);
    this.customerId = customerId;
    this.serviceTime = serviceTime;
    this.available = available;
  }


  @Override
  public Event[] simulate() {
    int counter = -1;
    for (int i = 0; i < this.available.length; i += 1) {
      if (this.available[i]) {
        counter = i;
        break;
      }
    }
    if (counter == -1) {
      // If no such counter can be found, the customer
      // should depart.
      return new Event[] { 
        new Departure(this.getTime(), this.customerId)
      };
    } else {
      // Else, the customer should go the the first 
      // available counter and get served.
      return new Event[] { 
        new ServiceBegin(this.getTime(), this.customerId, 
            this.serviceTime, this.available, counter)
      };
    }
  }

  @Override
  public String toString() {
    return super.toString() + ": Customer " + this.customerId + " arrives";
  }



}
