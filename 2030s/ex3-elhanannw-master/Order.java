/**
* @author elhanannw
*/
class Order {
  private int orderId;
  private String orderType; 
  private String orderCustomisation;

  public Order(int orderId, String orderCustomisation) {
    this.orderId = orderId;
    this.orderCustomisation = orderCustomisation;
    if (this.orderId == 0) {
      Espresso espresso = new Espresso();
      orderType = espresso.toString();
    } else if (this.orderId == 1) {
      Latte latte = new Latte();
      orderType = latte.toString();
    }
  }

  public int getOrder() {
    return this.orderId;
  }

  @Override
  public String toString() {
    String str = "(" + this.orderCustomisation + ")" 
        + " " + orderType;
    return str;
  }

} 
