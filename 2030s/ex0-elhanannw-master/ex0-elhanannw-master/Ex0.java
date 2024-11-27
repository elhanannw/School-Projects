import java.util.Scanner;

/**
 * CS2030S Ex 0: Estimating Pi with Monte Carlo
 * Semester 1, 2024/25
 *
 * number of points and a random seed.  It runs the
 * Monte Carlo simulation with the given argument and print
 * out the estimated pi value.
 *
 * @author Elhanan Neriah Wong
 */

class Ex0 {

  public static double estimatePi(int numOfPoints, int seed) {
    double inCircle = 0;
    RandomPoint.setSeed(seed);
    Circle c = new Circle(new Point(0.5, 0.5), 0.5);
    for (int i = 0; i < numOfPoints; i++) {
      Point p = new RandomPoint(0, 1, 0, 1);
      if (c.contains(p) == true) {
        inCircle++;
      } 
    }
    double pi = 4 * inCircle / numOfPoints;
    return pi;
  } 

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int numOfPoints = sc.nextInt();
    int seed = sc.nextInt();

    double pi = estimatePi(numOfPoints, seed);

    System.out.println(pi);
    sc.close();
  }
}
