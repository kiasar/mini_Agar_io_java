package View;

import board.Arrow;
import board.GameMap;
import circle.EaterCircle;
import circle.Food;
import circle.GameCartesian;
import circle.KillerCircle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import player.Player;

import java.net.URL;
import java.util.BitSet;

public class Main extends Application {
    static int gamemapX;
    static int gamemapY;
    static boolean isMouse;
    static GameMap g;
    Group root = new Group();
    Group g1 = new Group();
    Group g2 = new Group();
    Group g3 = new Group();
    Group g4 = new Group();
    GridPane mainScene = new GridPane();
    double x;
    double y;
    int foodnum = 20;
    int killernum = 1;
    Player player;
    boolean isServer = true;
    //    Player player2;
    String ip;
    int port;
    Network network;
    int player_id;
    private BitSet keyboardBitSet = new BitSet();
    private EventHandler<KeyEvent> keyPressedEventHandler = event -> {
        keyboardBitSet.set(event.getCode().ordinal(), true);
        checkKey();
    };
    private EventHandler<KeyEvent> keyReleasedEventHandler = event -> {
        keyboardBitSet.set(event.getCode().ordinal(), false);
        checkKey();
    };

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        final Scene scene = new Scene(new Group(), 800, 800);
        scene.setFill(null);
        scene.setFill(new ImagePattern(Images.getBack1()));
        primaryStage.setScene(scene);


        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        NumberTextField textX = new NumberTextField();
        NumberTextField textY = new NumberTextField();
        grid.add(textY, 22, 20);
        grid.add(textX, 10, 20);


        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Mouse", "KeyBoard"));
        cb.getMaxWidth();
        grid.add(cb, 16, 30);


        Button btn = new Button();
        btn.setText("                  \n        Start     \n     the Game     \n                  ");
        btn.setContentDisplay(ContentDisplay.CENTER);
        btn.setStyle("-fx-background-color: royalblue;");
        btn.setOnAction(event -> {
            gamemapY = Integer.parseInt(textX.getText());
            gamemapX = Integer.parseInt(textY.getText());
            isMouse = cb.getValue().equals("Mouse");
            try {
                start2(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            primaryStage.close();

        });
        grid.add(btn, 16, 50);


        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);

        primaryStage.show();
    }

    public void start2(Stage primaryStage) throws Exception {


        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setFullScreen(true);
        GameObjectID.main = this;
        Scene scene = new Scene(root);
        scene.setCursor(new ImageCursor(Images.getPointer()));
        g = new GameMap(gamemapX, gamemapY);
//        for (int i = 0; i < g.getX() / 50; i++) {
//            for (int j = 0; j < g.getY() / 50; j++) {
////                mainScene.add(new Rectangle(50, 50, new Color(1, 1, 1, 1)), i, j);
//            }
//        }
        scene.setFill(new ImagePattern(Images.getBack2()));
        mainScene.setGridLinesVisible(true);
        root.getChildren().add(mainScene);
        root.getChildren().add(g1);
        root.getChildren().add(g2);
        root.getChildren().add(g3);
        root.getChildren().add(g4);
        g.addPlayer(new Player());
        player = g.getPlayers().getFirst();

        final URL resource = getClass().getResource("10 Letters.mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);


        if (isMouse) {
            scene.setOnMouseMoved(event -> {
                x = event.getX();
                y = event.getY();
            });
            scene.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    try {
                        player.divHalfe();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(player.center());
                }
            });
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case DIGIT1:
                        player.activeGodMode();
                        break;
                    case DIGIT2:
                        player.activeSpeed();
                        break;
                    case DIGIT3:
                        player.activeUnify();
                        break;
                    case P:
                        if (mediaPlayer.isAutoPlay()) {
                            mediaPlayer.pause();
                            mediaPlayer.setAutoPlay(false);
                        } else {
                            mediaPlayer.setAutoPlay(true);
                        }
                        break;
                    case F:
                        primaryStage.setFullScreen(true);
                        break;
                    case END:
                        primaryStage.close();

                }
            });
        } else {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
            scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case SPACE:
                        try {
                            player.divHalfe();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case DIGIT1:
                        player.activeGodMode();
                        break;
                    case DIGIT2:
                        player.activeSpeed();
                        break;
                    case DIGIT3:
                        player.activeUnify();
                        break;
                    case P:
                        if (mediaPlayer.isAutoPlay()) {
                            mediaPlayer.pause();
                            mediaPlayer.setAutoPlay(false);
                        } else {
                            mediaPlayer.setAutoPlay(true);
                        }
                        break;
                    case F:
                        primaryStage.setFullScreen(true);
                }
            });
        }
        Timeline extimeline = new Timeline(new KeyFrame(new Duration(5000), event -> {
            double r = Math.random();
            if (r < 0.5) {
                g.addExt(true);
            } else {
                g.addExt(false);
            }
        }));
        extimeline.setCycleCount(Animation.INDEFINITE);
        extimeline.play();
        Timeline killertimeline = new Timeline(new KeyFrame(new Duration(40000), event -> g.addKiller()));
        killertimeline.setCycleCount(Animation.INDEFINITE);
        killertimeline.play();
        Timeline foodtimeline = new Timeline(new KeyFrame(new Duration(400), event -> g.addFood()));
        foodtimeline.setCycleCount(Animation.INDEFINITE);
        foodtimeline.play();
        Timeline t = new Timeline(new KeyFrame(new Duration(1000 / 60d), event -> {// FIXME: 8/10/2016
            g.move();
            g.squeeze();
            g.eatFood();
            g.eat();
            try {
                g.getPoison();
            } catch (Exception e) {
                e.printStackTrace();
            }
            g4.getChildren().clear();
            for (int i = 0; i < player.godMode; i++) {
                ImageView view = new ImageView(Images.getGodmode());
                view.setPreserveRatio(true);
                view.setFitHeight(Math.sqrt(EaterCircle.getMinArea() / Math.PI) * 2);
                g4.getChildren().add(view);
                view.setX(10 + i * 20);
                view.setY(10);
            }
            for (int i = 0; i < player.speedUp; i++) {
                ImageView view = new ImageView(Images.getSpeedup());
                view.setPreserveRatio(true);
                view.setFitHeight(Math.sqrt(EaterCircle.getMinArea() / Math.PI) * 2);
                g4.getChildren().add(view);
                view.setX(10 + i * 20);
                view.setY(50);
            }
            for (int i = 0; i < player.unify; i++) {
                ImageView view = new ImageView(Images.getUnify());
                view.setPreserveRatio(true);
                view.setFitHeight(Math.sqrt(EaterCircle.getMinArea() / Math.PI) * 2);
                g4.getChildren().add(view);
                view.setX(10 + i * 20);
                view.setY(90);
            }
            for (GameObjectID id : GameObjectID.ids) {
                if (id.item instanceof EaterCircle) {
                    if (isMouse) {
                        if (Math.abs(((EaterCircle) id.item).getPlayer().center().getX() - x) < 3 && Math.abs(((EaterCircle) id.item).getPlayer().center().getY() - y) < 3) {
                            GameMap.getArrowHashMap().put(player, Arrow.STILL);
                            GameMap.getCartesianHashMap().put(player, new GameCartesian(x, y));
                        } else {
                            GameCartesian cartesian = player.center().vector(new GameCartesian(x, y));
                            Arrow mouse = new Arrow(cartesian.getX(), cartesian.getY());
                            GameMap.getArrowHashMap().put(player, mouse);
                            GameMap.getCartesianHashMap().put(player, new GameCartesian(x, y));
                        }
                    }
                    ((ImageView) id.shape.getChildren().get(0)).setFitHeight(id.item.getRadius() * 2);
                    id.shape.setTranslateX(id.item.getPoint().getX() - ((ImageView) id.shape.getChildren().get(0)).getFitHeight() / 2);
                    id.shape.setTranslateY(id.item.getPoint().getY() - ((ImageView) id.shape.getChildren().get(0)).getFitHeight() / 2);
                }
            }
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
        for (int i = 0; i < killernum; i++) {
            g.addKiller();
        }
        for (int i = 0; i < foodnum; i++) {
            g.addFood();
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addShape(GameObjectID id) {
        if (id.item instanceof Food)
            g1.getChildren().add(id.shape);
        else if (id.item instanceof KillerCircle)
            g3.getChildren().add(id.shape);
        else
            g2.getChildren().add(id.shape);
        int x = 0;
        if (id.shape.getChildren().get(0) instanceof ImageView) {
            x = (int) ((ImageView) id.shape.getChildren().get(0)).getFitHeight() / 2;
        }
        id.shape.setTranslateX(id.item.getPoint().getX() - x);
        id.shape.setTranslateY(id.item.getPoint().getY() - x);
    }

    public void removeShape(GameObjectID id) {
        if (id.item instanceof Food)
            g1.getChildren().remove(id.shape);
        else if (id.item instanceof KillerCircle)
            g3.getChildren().remove(id.shape);
        else
            g2.getChildren().remove(id.shape);
    }

    private void checkKey() {
        if (keyboardBitSet.get(KeyCode.W.ordinal()) && keyboardBitSet.get(KeyCode.D.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.NE);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX() + 300, player.center().getY() - 300));
            return;
        }
        if (keyboardBitSet.get(KeyCode.W.ordinal()) && keyboardBitSet.get(KeyCode.A.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.NW);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX() - 300, player.center().getY() - 300));
            return;
        }
        if (keyboardBitSet.get(KeyCode.S.ordinal()) && keyboardBitSet.get(KeyCode.D.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.SE);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX() + 300, player.center().getY() + 300));
            return;
        }
        if (keyboardBitSet.get(KeyCode.S.ordinal()) && keyboardBitSet.get(KeyCode.A.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.SW);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX() - 300, player.center().getY() + 300));
            return;
        }
        if (keyboardBitSet.get(KeyCode.W.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.N);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX(), player.center().getY() - 500));
            return;
        }
        if (keyboardBitSet.get(KeyCode.D.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.E);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX() + 500, player.center().getY()));
            return;
        }
        if (keyboardBitSet.get(KeyCode.S.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.S);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX(), player.center().getY() + 500));
            return;
        }
        if (keyboardBitSet.get(KeyCode.A.ordinal())) {
            GameMap.getArrowHashMap().put(player, Arrow.W);
            GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX() - 500, player.center().getY()));
            return;
        }
        GameMap.getArrowHashMap().put(player, Arrow.STILL);
        GameMap.getCartesianHashMap().put(player, new GameCartesian(player.center().getX(), player.center().getY()));
    }


}


class NumberTextField extends TextField {


    public NumberTextField() {
        this.setStyle("-fx-background-color: royalblue;");
        this.setAlignment(Pos.CENTER);

    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);

        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }
}



