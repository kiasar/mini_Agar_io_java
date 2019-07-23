package View;

import circle.*;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by ali-user on 8/2/2016.
 */
public class GameObjectID {
    public Eater item;
    static int NEXT=0;
    int number;
    public static ArrayList<GameObjectID> ids=new ArrayList<>();
    Class type;
    public Group shape;
    public static Main main;

    public static GameObjectID create(Class type,Eater item) {
        GameObjectID id=new GameObjectID(type, NEXT++,item);
        ids.add(id);
        main.addShape(id);
        return id;
    }

    public static void remove(Eater eater){
        ids.remove(eater.id);
        main.removeShape(eater.id);
    }

    private GameObjectID(Class type, int number,Eater item) {
        this.type = type;
        this.number = number;
        this.item=item;
        shape=new Group();
        if(item instanceof Food){
            ImageView view;
            if(item instanceof Slicer) view = new ImageView(Images.getSlicer());
            else if(item instanceof Destroyer) view = new ImageView(Images.getDestroyer());
            else if(item instanceof SpeedUp) view = new ImageView(Images.getSpeedup());
            else if(item instanceof Unify) view = new ImageView(Images.getUnify());
            else if(item instanceof GodMode) view = new ImageView(Images.getGodmode());
            else view = new ImageView(Images.getFood());
            view.setPreserveRatio(true);
            shape.getChildren().add(view);
            view.setFitHeight(item.getRadius()*2);
            if(item instanceof Extension)view.setFitHeight(Math.sqrt(EaterCircle.getMinArea()/Math.PI)*4);
            return;
        }
        if(item instanceof EaterCircle){
            ImageView view;
            if (((EaterCircle) item).getPlayer()!=null && ((EaterCircle) item).getPlayer().getEaterCircles().size()>0){
                view = new AnimationMake(((AnimationMake) ((EaterCircle) item).getPlayer().getEaterCircles().getFirst().id.shape.getChildren().get(0)).img);
            }else{
             view= new AnimationMake();
            }
            view.setPreserveRatio(true);
            view.setFitHeight(item.getRadius()*2);

            shape.getChildren().add(view);
            return;
        }
        if(item instanceof KillerCircle){
            ImageView view= new AnimationMake(Images.getKiller());
            view.setPreserveRatio(true);
            view.setFitHeight(item.getRadius()*2);
            view.setOpacity(0.8);
            shape.getChildren().add(view);
        }
    }

}
