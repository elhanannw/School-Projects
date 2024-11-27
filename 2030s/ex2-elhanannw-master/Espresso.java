/**
* @author elhanannw
*/
class Espresso extends Order {

  public Espresso(int orderId) {
    super(orderId);
  }

  @Override
  public String toString() {
    String str = "Coffee Espresso";
    return str;
  }

}
