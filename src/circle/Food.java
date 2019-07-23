package circle;

/**
 * Made by Peyman on 8/2/2016.
 */
public class Food extends Eater{
    private static final double area2 = EaterCircle.getMinArea()/2;

    public Food( GameCartesian centerPoint) {
        super(area2, centerPoint);
    }

    public double getRadius() {
        return Math.sqrt(area / Math.PI);
    }

    public static double getArea2() {
        return area2;
    }

    @Override
    public void setPoint(GameCartesian point) throws Exception {
        this.point=point;
    }

    public void die() {
        super.die();
    }
/////////////////////////////////
    @Override
    public void eat(Eater circle) throws Exception {}

    @Override
    public void setArea(double area) throws Exception {this.area=area;}
}