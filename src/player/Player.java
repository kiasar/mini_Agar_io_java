package player;

import board.Arrow;
import board.GameMap;
import circle.*;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Made by Peyman on 7/26/2016.
 */
public class Player {
    private static double shoot = 4;
    private static int number=0;
    private LinkedList<EaterCircle> eaterCircles = new LinkedList<>();
    private LinkedList<EaterCircle> poisoned = new LinkedList<>();
    public int godMode=0;
    public int speedUp=0;
    public int unify=0;
    public boolean godModeActive=false;
    public boolean speedUpActive =false;
    private int player_id;
    public Player() {
        eaterCircles.add(new EaterCircle(0, 0, 0));
        eaterCircles.getFirst().setPlayer(this);
        player_id=number++;
    }

    public void setLocation(double x, double y) {
        GameCartesian center = center();
        GameCartesian cartesian = new GameCartesian(x - center.getX(), y - center.getY());
        eaterCircles.forEach(a -> a.move(cartesian));
    }

    public static double getShoot() {
        return shoot;
    }

    public static void setShoot(int shoot) {
        Player.shoot = shoot;
    }

    public int getPlayer_id(){return player_id;}
    public LinkedList<EaterCircle> getEaterCircles() {
        return eaterCircles;
    }
    public LinkedList<EaterCircle> getPoisoned() {
        return poisoned;
    }

    public void add(EaterCircle eaterCircle) {
        if (eaterCircle != null) {
            eaterCircles.add(eaterCircle);
            eaterCircle.setPlayer(this);
        }
    }

    public void addPoisoned(EaterCircle[] poisonedCircles) {
        poisoned.addAll(new LinkedList<>(Arrays.asList(poisonedCircles)));
        for (EaterCircle poisonedCircle : poisonedCircles) {
            add(poisonedCircle);
            poisonedCircle.setPlayer(this);
        }
    }

    public void move() {
        Arrow arrow = GameMap.getArrowHashMap().get(this);
        double maxArea = maxCircle();
        eaterCircles.forEach(a -> a.moveAll(arrow, maxArea));
    }

    private double maxCircle() {
        double max=0;
        for (EaterCircle circle :eaterCircles) {
            if(circle.getRadius()>max)max=circle.getRadius();
        }
        return max;
    }

    public void move(GameCartesian cartesian) {
        eaterCircles.forEach(a -> a.move(cartesian));
    }

    public void divHalfe() throws Exception {
        Arrow arrow = GameMap.getArrowHashMap().get(this);
        int size=eaterCircles.size();
        for (int i = 0; i < size; i++) {
            EaterCircle temp = eaterCircles.get(i).divHalf();
            if(temp==null)continue;
            temp.canEat=false;
            if(Math.abs(arrow.getX()+arrow.getY())<0.5) arrow=new Arrow(1,0);
            temp.getPoint().move(arrow.getX() * shoot * temp.getRadius(), arrow.getY() * shoot * temp.getRadius());
            add(temp);
        }
    }


    public GameCartesian center() {
        double v = 0;
        double vx = 0;
        double vy = 0;

        for (EaterCircle eaterCircle : eaterCircles) {
            v = v + eaterCircle.getArea();
            vx = vx + eaterCircle.getPoint().getX() * eaterCircle.getArea();
            vy = vy + eaterCircle.getPoint().getY() * eaterCircle.getArea();
        }
        return new GameCartesian(vx / v, vy / v);
    }

    public void squeeze() {
        GameCartesian center = center();
        for (EaterCircle eaterCircle : eaterCircles) {
            eaterCircle.move(center);
        }

        for (EaterCircle poisonedCircle : poisoned) {
            poisonedCircle.moveBack(center);
        }
    }

    public void activeSpeed(){
        if(speedUp>0&&!godModeActive&&!speedUpActive){
        SpeedUp.exec(this);
        speedUp--;
        }
    }

    public void activeUnify(){
        if(unify>0&&!godModeActive&&!speedUpActive){
            Unify.exec(this);
            unify--;
        }
    }

    public void activeGodMode(){
        if(godMode>0&&!godModeActive&&!speedUpActive){
            GodMode.exec(this);
            godMode--;
        }
    }
}
