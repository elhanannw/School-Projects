package cs2030s.fp;

/**
 * This class implements a lazily evaluated value.
 * The value is computed only when needed and the
 * value is not recomputed.
 *
 * @author A0308536B Elhanan Neriah Wong
 * @version CS2030S AY24/25 Semester 1
 */

public class Lazy<T> {
  private Producer<? extends T> producer;
  private Maybe<T> value;
  
  // Initialises with Maybe value
  // Only when value is known
  private Lazy(Maybe<T> value) {
    this.value = value;
    this.producer = null;
  }
  
  // Initialises with Producer value
  // Used when value needs to be lazily computed
  private Lazy(Producer<? extends T> producer) {
    this.producer = producer;
    this.value = Maybe.none();
  }
  
  // Factory method that creates lazy object from known value
  // Wraps in Maybe object and returns a lazy
  public static <T> Lazy<T> of(T v) {
    Maybe<T> out = Maybe.some(v);
    return new Lazy<>(out);
  }
  
  // Supplies value lazily 
  public static <T> Lazy<T> of(Producer<? extends T> s) {
    Producer<? extends T> producer = () -> s.produce();
    return new Lazy<>(producer);
  }
  
  // If value already computed, returns cached value
  // otherwise, uses producer to compute value
  public T get() {
    T thing = this.value.orElse(this.producer);
    this.value = Maybe.some(thing);
    return thing;
  }

  // Lazily applies transformer after getting value
  public <U> Lazy<U> map(Transformer<? super T, ? extends U> f) {
    return new Lazy<>(() -> f.transform(this.get()));
  }
  
  // Lazily applies transformer and flattens
  public <U> Lazy<U> flatMap(Transformer<? super T, 
      ? extends Lazy<? extends U>> f) {
    return new Lazy<>(() -> f.transform(this.get()).get());
  }
  
  // Lazily combines value of both lazy objects
  public <S, R> Lazy<R> combine(Lazy<? extends S> other,
      Combiner<? super T, ? super S, ? extends R> combiner) {
    return new Lazy<>(() -> combiner.combine(this.get(), other.get()));
  }

  // Lazily applies predicate to the value
  public Lazy<Boolean> filter(BooleanCondition<? super T> pred) {
    return new Lazy<>(() -> pred.test(this.get()));
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Lazy<?> lazy) {
      T curr = this.get();
      Object other = lazy.get();

      if (curr == other) {
        return true;
      }
      if (curr == null || other == null) {
        return false;
      }
      return curr.equals(other);
    }
    return false;
  }
  
  // Maps value to string representations
  // if absent return "?"
  @Override
  public String toString() {
    return this.value
      .map(v -> String.valueOf(v))
      .orElse(() -> "?");
  }

}
