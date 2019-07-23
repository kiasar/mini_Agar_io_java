package circle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import player.Player;

/**
 * Created by ali-user on 8/4/2016.
 */
public class GodMode extends GoodExt{

    public GodMode(GameCartesian centerPoint) {
        super(centerPoint);
    }

    public static void exec(Player player) {
        player.godModeActive=true;
        Timeline t=new Timeline(new KeyFrame(new Duration(10000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.godModeActive=false;
            }
        }));
        t.setCycleCount(1);
        t.play();
    }
}
