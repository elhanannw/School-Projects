/**
* @author elhanannw
*/
class Latte extends Order {

  public Latte(int orderId) {
    super(orderId);
  }

  @Override
  public String toString() {
    String str = "Coffee Latte";
    return str;
  }

} 
