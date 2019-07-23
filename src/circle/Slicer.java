package circle;

import player.Player;

/**
 * Created by ali-user on 8/4/2016.
 */
public class Slicer extends BadExt {
    public Slicer(GameCartesian centerPoint) {
        super(centerPoint);
    }

    @Override
    public void exec(Player player) {
        for (EaterCircle eaterCircle :player.getEaterCircles()) {
            try {
                if(eaterCircle.getArea()>=EaterCircle.getMinArea()*2) eaterCircle.half();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
