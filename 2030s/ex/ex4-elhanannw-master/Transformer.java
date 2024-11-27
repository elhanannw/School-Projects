/**
 * The Transformer interface that can transform a type T
 * to type U.
 *
 * @author A0308536B Elhanan Neriah Wong
 * @version CS2030S AY24/25 Semester 1
 */
public interface Transformer<T, U> {

  public abstract U transform(T thing);

}
