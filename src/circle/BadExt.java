package circle;

import player.Player;

/**
 * Created by ali-user on 8/4/2016.
 */
public abstract class BadExt extends Extension{
    public BadExt(GameCartesian centerPoint) {
        super(centerPoint);
    }
    public abstract void exec(Player player);
}
