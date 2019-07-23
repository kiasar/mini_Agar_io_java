package board;

/**
 * Made by Peyman on 7/22/2016.
 */
public class Cartesian {
    protected double x;
    protected double y;

    public Cartesian(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //:::::::::::::::::::::::::::: Getter and Setter :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public static double distance(Cartesian cartesian1, Cartesian cartesian2) {
        return Math.sqrt(Math.pow(cartesian1.getX() - cartesian2.getX(), 2)
                + Math.pow(cartesian1.getY() - cartesian2.getY(), 2));
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }


    //::::::::::::::::::::::::::::: Methods ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void changeDirection(int x, int y) {
        setX(x);
        setY(y);
    }

    public void changeDirection(double x, double y) {
        setX(x);
        setY(y);
    }

    public void changeDirection(Cartesian cartesian) {
        setX(cartesian.x);
        setY(cartesian.y);
    }

    public void move(double x, double y) {
        setX(this.x + x);
        setY(this.y + y);
    }

    public void move(Cartesian cartesian) {
        setX(this.x + cartesian.x);
        setY(this.y + cartesian.y);
    }

    public void scale(Cartesian center, double scale) {
        setX(this.x + (this.x - center.x) * scale);
        setY(this.x + (this.y - center.y) * scale);
    }

    public double distance(Cartesian cartesian) {
        return Cartesian.distance(this, cartesian);
    }

    public double distance(double x, double y) {
        return Cartesian.distance(this.x, this.y, x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cartesian)) return false;

        Cartesian that = (Cartesian) o;

        return getX() == that.getX() && getY() == that.getY();

    }

    @Override
    protected Cartesian clone() throws CloneNotSupportedException {
        return new Cartesian(this.x, this.y);
    }
}
