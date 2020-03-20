package sandbox;

/*
public class MyFirstProgram{
	public static void main (String[] args) {
		System.out.println("Hello Java!");
	}
}
*/

public class MyFirstProgram {
    public static void main(String[] args) {
        Point point1 = new Point(1, 0);
        Point point2 = new Point(11, 0);
        System.out.println("Расстояние = " + point1.distance(point2));

    }


}


