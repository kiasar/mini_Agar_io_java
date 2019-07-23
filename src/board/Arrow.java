package board;

/**
 * Made by Peyman on 7/23/2016.
 */
public class Arrow {
    public static Arrow STILL = new Arrow(0, 0);
    public static Arrow N = new Arrow(0, -1);
    public static Arrow S = new Arrow(0, 1);
    public static Arrow E = new Arrow(1, 0);
    public static Arrow W = new Arrow(-1, 0);
    public static Arrow NE = new Arrow(1, -1);
    public static Arrow NW = new Arrow(-1, -1);
    public static Arrow SE = new Arrow(1, 1);
    public static Arrow SW = new Arrow(-1, 1);
    public static Arrow MOUSE = new Arrow(Double.NaN, Double.NaN);
    private double x;
    private double y;

    public Arrow(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) throws Exception {
        if (this == MOUSE) this.x = x;
        else throw new Exception("not MOUSE");
    }

    public double getY() {
        return y;
    }

    public void setY(double y) throws Exception {
        if (this == MOUSE) this.y = y;
        else throw new Exception("not MOUSE");
    }
}
