/**
* @author elhanannw
*/
class Counter implements Comparable<Counter> {
  private int id;
  private boolean available;
  private static int lastId = 0;
  private CoffeeQueue<Customer> counterQ;

  public Counter(boolean available, int maxQueueLength) {
    this.id = lastId;
    this.available = available;
    counterQ = new CoffeeQueue<>(maxQueueLength);
    lastId++;
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void makeBusy() {
    this.available = false;
  }

  public void makeAvailable() {
    this.available = true;
  }
  
  public boolean checkCounterQFull() {
    return counterQ.isFull();
  }

  public boolean checkCounterQEmpty() {
    return counterQ.isEmpty();
  }

  public void enterCounterQ(Customer customer) {
    counterQ.enq(customer);
  }

  public Customer leaveCounterQ() {
    Customer nextCustomer = (Customer) counterQ.deq();
    return nextCustomer;
  }

  @Override
  public int compareTo(Counter counter) {
    return counterQ.compareTo(counter.counterQ);
  }

  @Override
  public String toString() {
    String str = " B" + this.id + " " + this.counterQ.toString();
    return str;
  }

}
