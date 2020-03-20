package sandbox;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point point1) {
        return Math.sqrt(Math.pow((point1.x - this.x), 2) + Math.pow((point1.y - this.y), 2));
    }



}