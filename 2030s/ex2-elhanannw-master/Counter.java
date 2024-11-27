class Counter {
  private int id;
  private boolean available;
  private static int lastId = 0;

  public Counter(boolean available) {
    this.id = lastId;
    this.available = available;
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

  @Override
  public String toString() {
    String str = " B" + this.id;
    return str;
  }

}
