import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates a parallel computation.
 * Uses CompletableFuture.
 *
 * @author: elhanannw
 * @version: CS2030S AY24/25 Semester 1, Ex 8
 */
public class Parallel extends Computation {
  /** Data source. */
  private Source source;

  /**
   * Constructor for this parallel computation.
   * Initializing data source.
   * 
   * @throws FileNotFoundException If the file not found.
   */
  public Parallel() throws FileNotFoundException {
    this.source = new Source();
  }

  /**
   * Running the computation in parallel.
   * The order should be kept.
   * 
   * @param town The town to be computed.
   * @return The string containing minimum price
   *         for each type for each block within town.
   */
  @Override
  public String run(String town) {
    String[] blocks = this.source.findBlock(town);

    // Only CompletableFuture<String> instances stored in array
    // hence it is safe.
    @SuppressWarnings("unchecked")
    CompletableFuture<String>[] futures = 
    (CompletableFuture<String>[]) new CompletableFuture<?>[blocks.length];

    for (int i = 0; i < blocks.length; i++) {
      futures[i] = processBlockAsync(town, blocks[i]);
    }

    CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures);
    StringBuilder resFuture = new StringBuilder();
    allFutures.join();

    for (CompletableFuture<String> future : futures) {
      resFuture.append(future.join());
    }
    return resFuture.toString();
  }

  /**
   * Process the price for each block in parallel.
   * Find the type, and for each type, find the minimum price.
   * The order should be kept.
   * 
   * @param town The town to be computed.
   * @param block The block to be computed.
   * @return The string containing minimum price
   *         for each type within block within town.
   */
  private CompletableFuture<String> processBlockAsync(String town, String block) {
    return CompletableFuture.supplyAsync(() -> {
      String[] types = this.source.findTypeInBlock(town, block);
     
      // Only CompletableFuture<String> instances stored in array
      // hence it is safe.
      @SuppressWarnings("unchecked")
      CompletableFuture<String>[] typeFutures = 
      (CompletableFuture<String>[]) new CompletableFuture<?>[types.length];
      StringBuilder blockRes = new StringBuilder(town + ", " + block + "\n");

      for (int i = 0; i < types.length; i++) {
        String type = types[i];
        typeFutures[i] = CompletableFuture.supplyAsync(() -> 
            "  " + type + ": " + this.source.findMinPrice(town, block, type) +
            "\n");
      }

      CompletableFuture<Void> allTypes = CompletableFuture.allOf(typeFutures);
      allTypes.join();

      for (CompletableFuture<String> typeFuture : typeFutures) {
        blockRes.append(typeFuture.join());
      }

      return blockRes.toString();
    });
  }


  /**
   * The program read a single string indicating the town from
   * standard input.  Then it initializes the computation and
   * run the computation with the given town.  If the source data
   * is not found, the program catch the error and displays an
   * error message indicating file not found.
   * 
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    try {
      Parallel rent = new Parallel();
      System.out.println(rent.run(sc.next()));
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found!");
    } finally {
      sc.close();
    }
  }
}
