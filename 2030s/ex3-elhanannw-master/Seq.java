/**
 * The Seq<T> for CS2030S 
 *
 * @author Elhanan Neriah Wong
 * @version CS2030S AY24/25 Semester 1
 */
public class Seq<T extends Comparable<T>> { 
  private T[] array;

  public Seq(int size) {
    /*
     * We only put object of type T inside 
     * safe to cast 'Object[]' to 'T[]'.
     * */
    @SuppressWarnings("unchecked")
    T[] a = (T[]) new Comparable[size];
    this.array = a;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public T min() {
    T minItem = array[0];

    for (int i = 1; i < array.length; i++) {
      if (array[i].compareTo(minItem) < 0) {
        minItem = array[i];
      }
    }
    return minItem; 
  }

  @Override
  public String toString() {
    String out = "[ ";
    for (int i = 0; i < array.length; i++) {
      out = out + i + ":" + array[i];
      if (i != array.length - 1) {
        out = out + ", ";
      }
    }
    return out + " ]";
  }
}
