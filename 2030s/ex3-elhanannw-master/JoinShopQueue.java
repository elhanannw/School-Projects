class JoinShopQueue extends Event {
  private Customer customer;
  private CoffeeShop shop;

  public JoinShopQueue(double time, Customer customer, CoffeeShop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  @Override
  public Event[] simulate() {
    shop.enterShopQ(this.customer);
    return new Event[] {};
  }

  @Override 
  public String toString() {
    String str = ": " + customer.toString() + " joined queue" 
        + shop.toString();
    return super.toString() + str;
  } 

}
