package circle;

import player.Player;

/**
 * Created by ali-user on 8/4/2016.
 */
public abstract class Extension extends Food {
    public Extension(GameCartesian centerPoint) {
        super(centerPoint);
        try {
            this.setArea(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
