package circle;

import player.Player;

/**
 * Created by ali-user on 8/4/2016.
 */
public class Destroyer extends BadExt {
    public Destroyer(GameCartesian centerPoint) {
        super(centerPoint);
    }

    @Override
    public void exec(Player player) {
        player.speedUp=0;
        player.godMode=0;
        player.unify=0;
    }
}
