package View;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationMake extends ImageView {
    private final Duration FRAME_TIME = Duration.seconds(0.07);
    private Timeline anime;
    public Image[] img;

    public AnimationMake(Image[] img) {
        this.img=img;
        show();
    }

    public AnimationMake(){
        img=Images.getAgar();
        show();
    }

    public void show() {
        final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
        anime = new Timeline(
                new KeyFrame(FRAME_TIME, event -> {
                    frameCounter.set((frameCounter.get() + 1) % img.length);
                    setImage(img[frameCounter.get()]);
                })
        );
        anime.setCycleCount(Animation.INDEFINITE);
        anime.play();
    }
}
