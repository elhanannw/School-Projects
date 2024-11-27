/**
 * This class implements Some container.
 *
 * @author A0308536B Elhanan Neriah Wong 
 * @version CS2030S AY24/25 Semester 1
 */ 
public class Some<T> {
  private final T thing;

  private Some(T thing) {
    this.thing = thing;
  }

  public static <T> Some<T> some(T thing) {
    return new Some<>(thing);
  }

  public <U> Some<U> map(Transformer<? super T, ? extends U> transformer) {
    U transformed = transformer.transform(thing);
    return Some.some(transformed);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Some) {
      Some<?> some = (Some<?>) o;
      return this.thing.equals(some.thing);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return "[" + this.thing + "]";
  }

}
