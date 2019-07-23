package board;

//import org.jetbrains.annotations.Contract;

/**
 * Made by Peyman on 8/4/2016.
 */
public class CartesianLine {

    // Ax + By = C
    public double xCoefficient;
    public double yCoefficient;
    public double intercept;

    public CartesianLine(double xCoefficient, double yCoefficient, double intercept) {
        this.xCoefficient = xCoefficient;
        this.yCoefficient = yCoefficient;
        this.intercept = intercept;
    }

    public CartesianLine(double angle, double intercept) {
        this((Math.abs(angle - 90) < 0.01 || Math.abs(angle - 270) < 0.01) ? 1 : Math.tan(angle)
                , (Math.abs(angle - 90) < 0.01 || Math.abs(angle - 270) < 0.01) ? 0 : 1, intercept);
    }

//    @Contract("_, _ -> ! null")
    public static CartesianLine makeLine(Cartesian point1, Cartesian point2) {
        double xCoefficient = point1.getY() - point2.getY();
        double yCoefficient = point2.getX() - point1.getX();
        double intercept = point1.getY() * point2.getX() - point2.getY() * point1.getX();


        return new CartesianLine(xCoefficient, yCoefficient, intercept);
    }

    public double dotDistance(Cartesian cartesian) {
        return Math.abs(xCoefficient * cartesian.getX() + yCoefficient * cartesian.getY() - intercept)
                / Math.sqrt(xCoefficient * xCoefficient + yCoefficient * yCoefficient);
    }

    public Cartesian nearestDot(Cartesian cartesian) {
        double dx = xCoefficient;
        double dy = yCoefficient;
        double dd = -1 * intercept;
        double dr = dx * dx + dy * dy;

        double x = (dy * (dy * cartesian.getX() - dx * cartesian.getY()) - dx * dd) / dr;
        double y = (dx * (dx * cartesian.getY() - dy * cartesian.getX()) - dy * dd) / dr;

        return new Cartesian(x, y);
    }

    public Cartesian[] circleIntersection(Cartesian center, double radius) {
        double dx = xCoefficient;
        double dy = -1 * yCoefficient;
        double dr = Math.sqrt(dx * dx + dy * dy);
        double dd = -1 * intercept;

        System.out.println(radius+" "+dr+" "+dd);
        double delta = radius * radius * dr * dr - dd * dd;
        if (delta < 0) return null;
        delta = Math.sqrt(delta);

        Cartesian[] points = new Cartesian[2];

        double temp1 = sign(dr) * dx * delta;
        double temp2 = Math.abs(dr) * delta;

        Cartesian point1 = new Cartesian((dd * dy + temp1) / (dr * dr), (-1 * dd * dx + temp2) / (dr * dr));
        Cartesian point2 = new Cartesian((dd * dy - temp1) / (dr * dr), (-1 * dd * dx - temp2) / (dr * dr));
        points[0] = point1;
        points[1] = point2;

        return points;
    }

//    @Contract(pure = true)
    private int sign(double a) {
        if (a < 0) return -1;
        return 1;
    }


}
