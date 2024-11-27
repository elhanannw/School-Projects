/**
 * CS2030S Ex 0: Point.java
 * Semester 1, 2024/25
 *
 * <p>The Point class encapsulates a point on a 2D plane.
 *
 * @author elhanannw
 */

class Point {
  private double x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }

  public double getDistSqr(Point p) {
    double dX = this.x - p.x;
    double dY = this.y - p.y;
    return ((dX * dX) + (dY * dY));
  }
  




}
