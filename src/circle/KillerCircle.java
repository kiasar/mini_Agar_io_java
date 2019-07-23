package circle;

import player.Player;

/**
 * Made by Peyman on 7/28/2016.
 */
public class KillerCircle extends Eater {


    public KillerCircle(double area, GameCartesian centerPoint) {
        super(area > 16 * EaterCircle.getMinArea() ? area : 16 * EaterCircle.getMinArea(), centerPoint);
    }

    public KillerCircle(double area, int x, int y) {
        super(area > 16 * EaterCircle.getMinArea() ? area : 16 * EaterCircle.getMinArea(), x, y);
    }

    @Override
    public void setArea(double area) throws Exception {
        if (area <= 16 * EaterCircle.getMinArea()) throw new Exception("Area is lower than Min Area");
        this.area = area;
    }

    @Override
    public void setPoint(GameCartesian point) throws Exception {
        this.point = point;
    }

    @Override
    public void eat(Eater circle) throws Exception {
        if (circle instanceof EaterCircle) {
            EaterCircle[] temp = ((EaterCircle) circle).divPart();
            for (EaterCircle poisoned : temp) {
                double random = Math.random() * 360;

                double v = circle.getRadius() + Player.getShoot() * poisoned.getRadius();
                poisoned.point.move(Math.cos(random) * v,
                        Math.sin(random) * v);
            }
            ((EaterCircle) circle).getPlayer().addPoisoned(temp);
            this.die();
        }
    }
}
