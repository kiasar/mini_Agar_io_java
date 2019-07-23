package View;

import java.io.Serializable;

/**
 * Created by ali on 1/6/2016.
 */
public class Sender implements Serializable{
    int player_id;
    double x;
    double y;
    boolean toHalf;

    private Sender(int player_id, double x, double y, boolean toHalf) {
        this.player_id = player_id;
        this.x = x;
        this.y = y;
        this.toHalf = toHalf;
    }

    public static Sender sendHalf(int player_id){
        return new Sender(player_id,0,0,true);
    }

    public static Sender sendArrow(int player_id,double x,double y){
        return new Sender(player_id,x,y,false);
    }

}
