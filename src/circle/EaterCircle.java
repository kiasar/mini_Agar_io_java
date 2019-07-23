package circle;

import View.AnimationMake;
import board.Arrow;
import board.Cartesian;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import player.Player;

/**
 * Made by Peyman on 7/22/2016.
 */
public class EaterCircle extends Eater implements Comparable<EaterCircle> {

    private static int maxSpeed = 20; // FIXME: 8/4/2016 
    private static int minArea = 1000;

    private Player player;
    public boolean canEat = true;

    public EaterCircle(double area, GameCartesian centerPoint) {
        super(area > EaterCircle.minArea ? area : EaterCircle.minArea, centerPoint);
    }

    public EaterCircle(double area, int x, int y) {
        super(area > EaterCircle.minArea ? area : EaterCircle.minArea, x, y);
    }


    //:::::::::::::::::::::::::::: Getter and Setter :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public static int getMaxSpeed() {
        return maxSpeed;
    }

    public static void setMaxSpeed(int maxSpeed) throws Exception {
        if (maxSpeed <= 0) throw new Exception("Max Speed is negative");
        EaterCircle.maxSpeed = maxSpeed;
    }

    public static int getMinArea() {
        return minArea;
    }

    public static void setMinArea(int minArea) throws Exception {
        if (minArea <= 0) throw new Exception("Min Area is negative");
        EaterCircle.minArea = minArea;
    }

    public void setLocation(double x, double y) {
        point.changeDirection(x, y);
    }

    @Override
    public void setArea(double area) throws Exception {
        if (area <= EaterCircle.minArea) throw new Exception("Area is lower than Min Area");
        this.area = area;
    }

    @Override
    public void setPoint(GameCartesian point) {
        this.point = point;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (player.getEaterCircles().size() > 0)
            ((AnimationMake) id.shape.getChildren().get(0)).img = ((AnimationMake) player.getEaterCircles().get(0).id.shape.getChildren().get(0)).img;
        this.player = player;
        cantEat();
    }

    //::::::::::::::::::::::::::::: Methods ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


    @Override
    public void eat(Eater circle) {
        double x = circle.area;
        circle.die();
        area = area + x;
    }

    public void half() throws Exception {
        if (area / 2 < EaterCircle.minArea) throw new Exception("Half under the Min Area");
        this.area = this.area / 2;
    }

    public void cantEat() {
        canEat = false;
        int rand1 = (int) (Math.random() * 30000 + 30000);
        int rand2 = (int) (Math.random() * 60000 + 80000);
        Timeline t = new Timeline(new KeyFrame(new Duration((getPlayer().getPoisoned().contains(this)) ? rand2 : rand1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                canEat = true;
            }
        }));
        t.setCycleCount(1);
        t.play();
    }

    private GameCartesian checkMove(double x, double y) {
        double i = 0.5;

        double disX = this.point.getX() + x;
        double disY = this.point.getY() + y;

//        EaterCircle minEaterCircle = null;

        for (EaterCircle eaterCircle : this.player.getEaterCircles()) {
            if (eaterCircle != this && (!eaterCircle.canEat || !this.canEat)) {
                double v = eaterCircle.point.distance(disX, disY) - (this.getRadius() + eaterCircle.getRadius());
                if (v < -2) {
                    double dis = eaterCircle.point.distance(this.point);
                    double v1 = this.point.getX() - eaterCircle.point.getX();
                    double v2 = this.point.getY() - eaterCircle.point.getY();
                    return new GameCartesian(v1 * i / dis, v2 * i / dis);
                }
            }
        }

        return new GameCartesian(x, y);

//        if (min < -2) {
//            CartesianLine line = CartesianLine.makeLine(this.point, temp.point);
//            Cartesian[] points = line.circleIntersection(minEaterCircle.point, minEaterCircle.getRadius());
//            System.out.println(points[0] + "   " + points[1]);
//            Cartesian intersectPoint = this.point.distance(points[0]) < this.point.distance(points[0]) ? points[0] : points[1];
//
//            double v = this.getRadius() + minEaterCircle.getRadius();
//            double adjacent = Math.sqrt(v * v - line.dotDistance(minEaterCircle.point));
//
//            Cartesian nearestDot = line.nearestDot(minEaterCircle.point);
//            double moveLength = adjacent - intersectPoint.distance(nearestDot);
//
//            Cartesian vector = intersectPoint.vector(this.point);
//            this.point.changeDirection(intersectPoint.getX() + vector.getX() * moveLength
//                    , intersectPoint.getY() + vector.getY() * moveLength);
//
//            return new GameCartesian(-x, -y);
//
//        }
//

//
//
    }

    private GameCartesian checkMove(Cartesian cartesian) {
        return checkMove(cartesian.getX(), cartesian.getY());
    }

    public void move(Arrow arrow, double radius2) {
        double x = arrow.getX() * maxSpeed / (1.5 * Math.pow(getRadius() / 5 + radius2, 2.3 / 3)); // FIXME: 8/4/2016
        double y = arrow.getY() * maxSpeed / (1.5 * Math.pow(getRadius() / 5 + radius2, 2.3 / 3));// FIXME: 8/4/2016
//        System.out.println(checkMove(x, y));
        point.move(checkMove(x, y));
    }

    public void moveAll(Arrow arrow, double radius2) {
        double x = arrow.getX() * maxSpeed * ((getPlayer().speedUpActive) ? 2 : 1) / (1.5 * Math.pow(getRadius() / 5 + radius2, 2.0 / 3)); // FIXME: 8/4/2016
        double y = arrow.getY() * maxSpeed * ((getPlayer().speedUpActive) ? 2 : 1) / (1.5 * Math.pow(getRadius() / 5 + radius2, 2.0 / 3));// FIXME: 8/4/2016
//        System.out.println(checkMove(x, y));
        point.move(x, y);
    }

    public void move(GameCartesian cartesian) {
        GameCartesian vector = point.vector(cartesian);
        double pow = 0.7 * Math.pow(area, 1.3 / 3); // FIXME: 8/4/2016
        point.move(checkMove(vector.getX() * maxSpeed* ((getPlayer().speedUpActive) ? 2 : 1) / pow, vector.getY() * maxSpeed* ((getPlayer().speedUpActive) ? 2 : 1) / pow)); // FIXME: 8/4/2016
    }

    public void moveAll(GameCartesian cartesian) {
        GameCartesian vector = point.vector(cartesian);
        double pow = 1.5 * Math.pow(area, 1.3 / 3); // FIXME: 8/4/2016
        point.move(vector.getX() * maxSpeed * ((getPlayer().speedUpActive) ? 2 : 1) / pow, vector.getY() * maxSpeed * ((getPlayer().speedUpActive) ? 2 : 1) / pow); // FIXME: 8/4/2016
    }

    public void moveBack(Arrow arrow) throws CloneNotSupportedException {
        point.move(checkMove(-0.5 * arrow.getX() * maxSpeed / area, -0.5 * arrow.getY() * maxSpeed / area)); // FIXME: 8/4/2016 
    }

    public void moveBack(GameCartesian cartesian) {
        GameCartesian vector = point.vector(cartesian);

        point.move(-0.5 * vector.getX() * maxSpeed / area, -0.5 * vector.getY() * maxSpeed / area);
    }

    public EaterCircle divHalf() throws Exception {
        if (area / 2 > EaterCircle.minArea) {
            half();
            return clone();
        }
        return null;
    }


    public EaterCircle[] divPart() throws Exception {
        half();
        EaterCircle partCircle = clone();
        partCircle.half();
        partCircle.half();
        partCircle.half();
        EaterCircle[] eaterCircles = new EaterCircle[8];
        for (int i = 0; i < eaterCircles.length; i++) {
            eaterCircles[i] = partCircle.clone();
        }
        partCircle.die();
        return eaterCircles;
    }

    @Override
    public void die() {
        super.die();
        player.getEaterCircles().remove(this);
        player = null;
    }

    @Override
    protected EaterCircle clone() {
        GameCartesian cartesian = new GameCartesian(point.getX(), point.getY());
        EaterCircle eaterCircle = new EaterCircle(area, cartesian);
        eaterCircle.setPlayer(this.player);
        return eaterCircle;
    }

    @Override
    public int compareTo(EaterCircle o) {
        return area == o.area ? 0 : area > o.area ? 1 : -1;
    }


}



