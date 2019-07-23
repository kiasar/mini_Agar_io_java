package board;

import View.GameObjectID;
import circle.*;
import player.Player;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Made by Peyman on 7/30/2016.
 */
public class GameMap {
    private static HashMap<Player, Arrow> arrowHashMap = new HashMap<>();
    private static HashMap<Player, GameCartesian> cartesianHashMap = new HashMap<>();

    private final int x;
    private final int y;
    private LinkedList<Player> players = new LinkedList<>();
    private LinkedList<KillerCircle> killerCircles = new LinkedList<>();
    private LinkedList<Food> foods = new LinkedList<>();

    public GameMap(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //:::::::::::::::::::::::::::: Getter and Setter :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public static HashMap<Player, Arrow> getArrowHashMap() {
        return arrowHashMap;
    }

    public static HashMap<Player, GameCartesian> getCartesianHashMap() {
        return cartesianHashMap;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public LinkedList<KillerCircle> getKillerCircles() {
        return killerCircles;
    }

    public void setKillerCircles(LinkedList<KillerCircle> killerCircles) {
        this.killerCircles = killerCircles;
    }

    public LinkedList<Food> getFoods() {
        return foods;
    }

    public void setFoods(LinkedList<Food> foods) {
        this.foods = foods;
    }

    public int getSize() {
        return x * y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    //::::::::::::::::::::::::::::: Methods ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public void addPlayer(Player player) {
        if (players.contains(player)) players.remove(player);
        for (int i = 0; i < player.getEaterCircles().size(); i++) {
            player.getEaterCircles().getFirst().die();
        }
        GameCartesian gameCartesian = MTCartesian();
        if (gameCartesian != null)
            player.add(new EaterCircle(0, gameCartesian));
        players.add(player);
        arrowHashMap.put(player, Arrow.STILL);
    }

    public void addKiller() {
        double random = Math.random();
        random = 8 * EaterCircle.getMinArea() + random * 40 * EaterCircle.getMinArea();
        GameCartesian gameCartesian = MTCartesian();
        if (gameCartesian != null)
            killerCircles.add(new KillerCircle(random, gameCartesian));
    }

    public void addFood() {
        if (lowDensity()) {
            GameCartesian gameCartesian = MTCartesian();
            if (gameCartesian != null)
                foods.add(new Food(gameCartesian));
        }
    }

    private boolean lowDensity() {
        double sum = 0d;
        for (Player player : players) {
            for (EaterCircle eaterCircle : player.getEaterCircles()) {
                sum = sum + eaterCircle.getArea();
            }
        }

        for (KillerCircle killerCircle : killerCircles) {
            sum = sum + killerCircle.getArea();
        }


        sum = ((double) foods.size()) * 1000 / (getSize() - sum);

        return sum < 0.32;
    }

    public void addExt(boolean good) {
        GameCartesian gameCartesian = MTCartesian();
        if (gameCartesian != null)
            if (good) {
                double r = Math.random();
                if (r < 0.33)
                    foods.add(new Unify(gameCartesian));
                else if(r<0.66)
                    foods.add(new SpeedUp(gameCartesian));
                else
                    foods.add(new GodMode(gameCartesian));
            } else {
                double r = Math.random();
                if (r < 0.5)
                    foods.add(new Slicer(gameCartesian));
                else
                    foods.add(new Destroyer(gameCartesian));
            }
    }

    public void squeeze() {
        players.forEach(Player::squeeze);
    }

    public GameCartesian MTCartesian() {

        GameCartesian retn = null;
        boolean boo = true;
        int counter = 0;
        while (boo) {
            if (counter++ == 500) {
                System.out.println("ja nist");
                return null;
            }

            boo = false;
            retn = new GameCartesian(Math.random() * x, Math.random() * y);
            double val = Math.sqrt(EaterCircle.getMinArea());

            for (Player player : players) {
                for (EaterCircle eaterCircle : player.getEaterCircles()) {
                    if (eaterCircle.getPoint().distance(retn) <= eaterCircle.getRadius() + val) boo = true;
                }
            }

            for (KillerCircle killerCircle : killerCircles) {
                if (killerCircle.getPoint().distance(retn) <= killerCircle.getRadius() / 2 + val) boo = true;
            }
        }
        return retn;
    }


    public void eatFood() {
        for (Player player : players) {
            for (EaterCircle eaterCircle : player.getEaterCircles()) {
                double radius = eaterCircle.getRadius();

                for (int i = 0; i < GameObjectID.ids.size(); i++) {
                    Eater circle = GameObjectID.ids.get(i).item;
                    if (!(circle instanceof Food)) continue;

                    GameCartesian point1 = eaterCircle.getPoint();
                    GameCartesian point2 = circle.getPoint();

                    if (point1.distance(point2) <= radius && eaterCircle.getArea() > circle.getArea()) {
                        if (circle instanceof BadExt) ((BadExt) circle).exec(player);
                        if (circle instanceof Unify) eaterCircle.getPlayer().unify++;
                        if (circle instanceof SpeedUp) eaterCircle.getPlayer().speedUp++;
                        if (circle instanceof GodMode) eaterCircle.getPlayer().godMode++;
                        eaterCircle.eat(circle);
                        foods.remove(circle);
                        i--;
                    }
                }
            }
        }
    }

    public void eat() {
        for (Player player : players) {
            for (int i = 0; i < player.getEaterCircles().size(); i++) {
                EaterCircle eaterCircle = player.getEaterCircles().get(i);
                double radius = eaterCircle.getRadius();

                for (Player player1 : players) {
                    for (int j = 0; j < player1.getEaterCircles().size(); j++) {
                        EaterCircle circle = player1.getEaterCircles().get(j);
                        if (circle == eaterCircle) continue;

                        GameCartesian point1 = eaterCircle.getPoint();
                        GameCartesian point2 = circle.getPoint();

                        double k = circle.getPlayer() == eaterCircle.getPlayer() ? -1 : EaterCircle.getMinArea();

                        if (point1.distance(point2) <= radius && eaterCircle.getArea() >= circle.getArea() + k) {
                            if (circle.getPlayer() == eaterCircle.getPlayer() && (!circle.canEat || !eaterCircle.canEat))
                                continue;
                            if (circle.getPlayer() != eaterCircle.getPlayer() && circle.getPlayer().godModeActive)
                                continue;
                            eaterCircle.eat(circle);
                            j--;
                        }
                    }
                }
            }
        }
    }


    public void getPoison() throws Exception {
        loop:
        for (int j = 0; j < killerCircles.size(); j++) {
            KillerCircle killerCircle = killerCircles.get(j);
            for (Player player : players) {
                for (int i = 0; i < player.getEaterCircles().size(); i++) {
                    EaterCircle eaterCircle = player.getEaterCircles().get(i);
                    GameCartesian point1 = eaterCircle.getPoint();
                    GameCartesian point2 = killerCircle.getPoint();

                    if (point1.distance(point2) <= eaterCircle.getRadius()
                            && eaterCircle.getArea() > killerCircle.getArea()) {
                        killerCircle.eat(eaterCircle);
                        killerCircles.remove(killerCircle);
                        j--;
                        continue loop;
                    }
                }
            }
        }
    }

    public void move() {
        for (Player player : players) {
            player.move();
            for (EaterCircle eaterCircle : player.getEaterCircles()) {
                GameCartesian gameIntCartesian = cartesianHashMap.get(player);
                if (gameIntCartesian == null) continue;
                eaterCircle.move(gameIntCartesian);
                eaterCircle.move(gameIntCartesian);
                eaterCircle.move(gameIntCartesian);
                eaterCircle.move(gameIntCartesian);
                eaterCircle.move(gameIntCartesian);
            }
        }
    }

}
