package circle;

import player.Player;

/**
 * Created by ali-user on 8/4/2016.
 */
public class Unify extends GoodExt {
    public Unify(GameCartesian centerPoint) {
        super(centerPoint);
    }

    public static void exec(Player player) {
        for (EaterCircle eaterCircle : player.getEaterCircles()) {
            eaterCircle.canEat=true;
        }
    }
}
