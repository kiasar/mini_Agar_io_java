package circle;

import board.Cartesian;

/**
 * Made by Peyman on 7/22/2016.
 */
public class GameCartesian extends Cartesian {

    public GameCartesian(double x, double y) {
        super(x, y);
    }

    public static GameCartesian vector(GameCartesian cartesian1, GameCartesian cartesian2) {
        double distance = distance(cartesian1, cartesian2);
        if(Math.abs(distance)<=1)return new GameCartesian(0,0);
        double xVector = (cartesian2.getX() - cartesian1.getX()) / distance;
        double yVector = (cartesian2.getY() - cartesian1.getY()) / distance;
        return new GameCartesian(xVector, yVector);
    }

    public GameCartesian vector(GameCartesian cartesian) {
        return GameCartesian.vector(this, cartesian);
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
}
