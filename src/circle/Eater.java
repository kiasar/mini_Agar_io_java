package circle;

import View.GameObjectID;

/**
 * Made by Peyman on 7/28/2016.
 */
public abstract class Eater {
    protected double area;
    protected GameCartesian point;
    public GameObjectID id;
    public Eater(double area, GameCartesian centerPoint) {
        this.area = area;
        point = centerPoint;
        id=GameObjectID.create(this.getClass(),this);
    }



    public Eater(double area, int x, int y) {
        this(area, new GameCartesian(x, y));
    }

    public double getRadius() {
        return Math.sqrt(area / Math.PI);
    }

    public double getArea() {
        return area;
    }

    abstract public void setArea(double area) throws Exception;

    public GameCartesian getPoint() {
        return point;
    }

    abstract public void setPoint(GameCartesian point) throws Exception;

    public void die() {
        area = 0;
        GameObjectID.remove(this);
    }

    abstract public void eat(Eater circle) throws Exception;

}
