package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;

/**
 * The InfiniteList class that may contain an arbitrary number of
 * elements.  The class is a generic class.
 * 
 * @author Elhanan Neriah Wong (16A)
 * @param <T> The type to be stored in the InfiniteList.
 */
public class InfiniteList<T> {
  /**
   * Lazily-evaluated head of list, contains first element.
   */
  private final Lazy<Maybe<T>> head;

  /**
   * Lazily-evaluated tail of list, remainder of InfiniteList.
   */
  private final Lazy<InfiniteList<T>> tail;
  
  /**
   * Marks end of InfiniteList.
   */
  private static final InfiniteList<?> SENTINEL = (InfiniteList<?>) new Sentinel();

  /**
   * Private constructor to set all fields null.
   */
  private InfiniteList() {
    this.head = null;
    this.tail = null;
  }

  /**
   * Private constructor given head and tail.
   * 
   * @param head The Lazy instance containing the head of the InfiniteList.
   * @param tail The Lazy instance to produce the tail of the InfiniteList.
   */
  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Generates an InfiniteList.  Given a producer that produces
   * a value x, generate the list as [x, x, x, ...]
   * 
   * @param <T> The type to be stored in the InfiniteList.
   * @param prod The producer to produce the value in the InfiniteList.
   * @return The created InfiniteList.
   */
  public static <T> InfiniteList<T> generate(Producer<T> prod) {
    return new InfiniteList<>(
          Lazy.of(() -> Maybe.some(prod.produce())),
          Lazy.of(() -> InfiniteList.generate(prod))
        );
  }

  /**
   * Generate an InfiniteList.  Given x and a lambda f, 
   * generate the list as [x, f(x), f(f(x)), f(f(f(x))), ...]
   * 
   * @param <T> The type to be stored in the InfiniteList.
   * @param init The first element.
   * @param next The transformation function on the element.
   * @return The created InfiniteList.
   */
  public static <T> InfiniteList<T> iterate(T init,
      Transformer<? super T, ? extends T> next) {
    return new InfiniteList<>(
          Lazy.of(Maybe.some(init)),
          Lazy.of(() -> InfiniteList.iterate(next.transform(init), next))
        );
  }

  /**
   * Generate an InfiniteList.  This is an empty InfiniteList.
   * 
   * @param <T> The type to be stored in the InfiniteList.
   * @return The created InfiniteList.
   */
  public static <T> InfiniteList<T> sentinel() {
    // Sentinel acts as generic end marker and has no
    // type-dependent data, hence it is safe.
    @SuppressWarnings("unchecked")
    InfiniteList<T> res = (InfiniteList<T>) SENTINEL;
    return res;
  }

  /**
   * Lazily search for the first element of the InfiniteList.
   * Then return the value of the first element of the InfiniteList.
   * 
   * @return the head of the InfiniteList.
   */
  public T head() {
    return this.head.get().orElse(() -> this.tail.get().head());
  }

  /**
   * Lazily search for the first element of the InfiniteList.
   * Then return the tail of the first element of the InfiniteList.
   * 
   * @return the tail of the InfiniteList.
   */
  public InfiniteList<T> tail() {
    return this.head.get()
               .map(x -> this.tail.get())
               .orElse(() -> this.tail.get().tail());
  }

  /**
   * Transform each element in the InfiniteList using
   * the given Transformer and return the resulting InfiniteList.
   * 
   * @param <U> The type of the resulting InfiniteList.
   * @param fn  The Transformer to transform 
   *            the element of the InfiniteList.
   * @return    A lazily evaluated InfiniteList with each
   *            element transformed using fn.
   */
  public <U> InfiniteList<U> map(Transformer<? super T, ? extends U> fn) {
    return new InfiniteList<>(
          this.head.map(mHead -> mHead.map(fn)),
          this.tail.map(mTail -> mTail.map(fn))
        );
  }

  /**
   * Check each element of the InfiniteList and filter out
   * elements that evaluate to `false` using the given
   * BooleanCondition.
   * 
   * @param pred The predicate to check element.
   * @return     A lazily evaluated InfiniteList with element
   *             failing the check removed.
   */
  public InfiniteList<T> filter(BooleanCondition<? super T> pred) {
    return new InfiniteList<>(
          this.head.map(mHead -> mHead.filter(pred)),
          this.tail.map(mTail -> mTail.filter(pred))
        );
  }

  /**
   * Limits InfiniteList to contain at most n elements.
   * Elements that are filtered out are not counted towards limit.
   *
   * @param n The maximum number of elements included in the list
   * @return     A new InfiniteList that contains at most n elements
   */
  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return InfiniteList.sentinel(); // return sentinel if n <= 0
    }

    return new InfiniteList<>(
        this.head,
        Lazy.of(() -> this.head.get()
                          // If head present, decrement n and limit tail 
                          // with (n - 1)
                          .map(x -> this.tail.get().limit(n - 1))
                          // If head empty, do not decrement n, limit tail
                          // with n
                          .orElse(() -> this.tail.get().limit(n)))
        );
  }

  /**
   * Collects and converts elements of InfiniteList into a list.
   *
   * @return A new List containing the elements of the InfiniteList
   * in order.
   */
  public List<T> toList() {
    List<T> list = new ArrayList<>();

    return this.head.get()
               .map(x -> {
                 list.add(x);
                 list.addAll(this.tail.get().toList());
                 return list;
               })
               .orElse(() -> this.tail.get().toList());
  }

  /**
   * Returns new InfiniteList that is truncated as soon
   * as BooleanCondition evaluates false.
   *
   * @param pred the BooleanCondition to evaluate each element.
   * @return An InfiniteList truncated according to pred.
   * */
  public InfiniteList<T> takeWhile(BooleanCondition<? super T> pred) {
    Lazy<Boolean> curr = Lazy.of(() -> pred.test(this.head()));
    return new InfiniteList<>(
        Lazy.of(() -> curr.get() 
          ? Maybe.some(this.head()) : Maybe.none()),
        Lazy.of(() -> curr.get() 
          ? this.tail().takeWhile(pred) : sentinel())
      );
  }

  /**
   * Performs a right fold on this InfiniteList.
   * Method applies combiner function to each element, starting 
   * from rightmost. 
   * 
   * @param <U> Type of accumulated result.
   * @param id Initial value.
   * @param acc Combiner function, combines each element. 
   * @return Result of folding list right to left.
   * */
  public <U> U foldRight(U id, Combiner<? super T, U, U> acc) {
    U y = this.tail.get().foldRight(id, acc);
    return this.head.get()
               .map(x -> acc.combine(x, y))
               .orElse(() -> y);
  }

  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }

  /**
   *  Determines if this InfiniteList instance is sentinel,
   *  representing end of list.
   *
   *  @return True if instance sentinel, else false.
   * */
  public boolean isSentinel() {
    return false;
  }

  /**
   * A nested static class that represents the end of the list.
   * The class contains nothing and performs no operation.
   */
  private static class Sentinel extends InfiniteList<Object> {
    
    /**
     * Default constructor for Sentinel class
     */
    public Sentinel() {
    }

    @Override
    public Object head() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public InfiniteList<Object> tail() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public <R> InfiniteList<R> map(Transformer<Object, ? extends R> mapper) {
      return InfiniteList.<R>sentinel();
    }

    @Override
    public InfiniteList<Object> filter(BooleanCondition<Object> predicate) {
      return InfiniteList.<Object>sentinel();
    }

    @Override
    public InfiniteList<Object> limit(long n) {
      return this; // Return self as Sentinel represents end of list.
    }

    @Override
    public List<Object> toList() {
      return new ArrayList<>(); // Sentinel represents end of list, return empty list.
    }

    @Override
    public InfiniteList<Object> takeWhile(BooleanCondition<Object> pred) {
      return this; // Sentinel remains the end of list. 
    }

    @Override
    public <U> U foldRight(U id, Combiner<Object, U, U> acc) {
      return id; // Return initial value id, no elements in Sentinel.
    }

    @Override
    public String toString() {
      return "~";
    }

    @Override
    public boolean isSentinel() {
      return true;
    }
  }
}
