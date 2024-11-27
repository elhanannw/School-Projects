/**
 * This class implements Maybe container.
 *
 * @author elhanannw
 * @version CS2030S AY24/25 Semester 1
 */

package cs2030s.fp;

public abstract class Maybe<T> {

  // Ensures only one instance of None<T>
  private static final Maybe<?> NONE = new None<>();

  // BooleanCondition Interface consumer, so ? super T
  public abstract Maybe<T> filter(BooleanCondition<? super T> condition);

  public abstract <U> Maybe<U> flatMap(Transformer<? super T, 
      ? extends Maybe<? extends U>> transformer);

  public abstract <U> Maybe<U> map(Transformer<? super T, 
      ? extends U> transformer);

  // Producer Interface producer, so ? extends T
  public abstract T orElse(Producer<? extends T> producer);

  // Consumer Interface consumer, so ? super T
  public abstract void ifPresent(Consumer<? super T> consumer);

  // Factory method for 'some' case
  public static <T> Maybe<T> some(T thing) {
    return new Some<>(thing);
  }

  public static <T> Maybe<T> none() {
    // Safe as None<T> is stateless and does not depend on T
    // Only instances of None created in factory method
    // Safe to cast new None<?> instance to Maybe<T>
    @SuppressWarnings("unchecked")
    Maybe<T> noneOutput = (Maybe<T>) NONE;
    return noneOutput;
  }

  // Factory method
  public static <T> Maybe<T> of(T input) {
    if (input == null) {
      return Maybe.none();
    } else {
      return new Some<T>(input);
    }
  }

  // Concrete class for "Some"
  private static final class Some<T> extends Maybe<T> {
    private final T thing;

    private Some(T thing) {
      this.thing = thing;
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer) {
      U transformed = transformer.transform(thing);
      return Maybe.some(transformed);
    }

    @Override
    public Maybe<T> filter(BooleanCondition<? super T> condition) {
      if (this.thing == null || !condition.test(this.thing)) {
        return Maybe.none();
      }
      return this;
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super T, 
        ? extends Maybe<? extends U>> transformer) {
      // Transformer returns Maybe guaranteed to be subtype of
      // Maybe<? extends U>, result always compatible with Maybe<U>
      // based on how transformer is used, it is safe to suppresswarnings
      @SuppressWarnings("unchecked")
      Maybe<U> result = (Maybe<U>) transformer.transform(this.thing);
      return result;
    }

    @Override
    public T orElse(Producer<? extends T> producer) {
      return this.thing;
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
      consumer.consume(this.thing);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Some<?> some) {
        if (this.thing == null && some.thing == null) {
          return true;
        } 
        if (this.thing == null || some.thing == null) {
          return false;
        }
        return this.thing.equals(some.thing);
      }
      return false;
    }

    @Override
    public String toString() {
      return "[" + this.thing + "]";
    }
  }

  // Concrete class for "None"
  private static final class None<T> extends Maybe<T> {

    private None() {
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformer) {
      return Maybe.none();
    }

    @Override
    public Maybe<T> filter(BooleanCondition<? super T> condition) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super T, 
        ? extends Maybe<? extends U>> transformer) {
      return Maybe.none();
    }

    @Override
    public T orElse(Producer<? extends T> producer) {
      return producer.produce();
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof None<?> none;
    }

    @Override
    public String toString() {
      return "[]";
    } 
  }

}
