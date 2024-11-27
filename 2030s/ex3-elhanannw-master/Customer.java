/**
* @author elhanannw
*/
class Customer {
  private int id;
  private double arrivalTime;
  private double serviceTime;
  private Order order;

  public Customer(int id, double arrivalTime, double serviceTime, Order order) {
    this.id = id;
    this.arrivalTime = arrivalTime;
    this.serviceTime = serviceTime;
    this.order = order;
  }

  public double getServiceTime() {
    return this.serviceTime;
  }

  public String orderType() {
    return order.toString();
  }

  @Override
  public String toString() {
    String str = "C" + this.id;
    return str;
  }

}
