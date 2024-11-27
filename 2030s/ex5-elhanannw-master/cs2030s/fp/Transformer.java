/**
 * The Transformer interface that can transform a type T
 * to type U.
 *
 * @author elhanannw
 * @version CS2030S AY24/25 Semester 1
 */

package cs2030s.fp;

public interface Transformer<T, U> {

  public abstract U transform(T thing);

}
