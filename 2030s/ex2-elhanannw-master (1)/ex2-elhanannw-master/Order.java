class Order {
  private int orderId;
  private String orderType;

  public Order(int orderId) {
    this.orderId = orderId;
    if (this.orderId == 0) {
      orderType = "Coffee Espresso";
    } else if (this.orderId == 1) {
      orderType = "Coffee Latte";
    } 
  }

  public int getOrder() {
    return this.orderId;
  }

  @Override
  public String toString() {
    String str = orderType;
    return str;
  }

} 
