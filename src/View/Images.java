package View;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by ali on 2/2/2016.
 */
public class Images {

    private static Image[] food = new Image[10];
    private static Image[] killer = new Image[15];
    private static ArrayList<Image[]> agar = new ArrayList<>();
    private static Image slicer = new Image("res/ext/2.png");
    private static Image destroyer = new Image("res/ext/1.png");
    private static Image godmode = new Image("res/ext/3.png");
    private static Image speedup = new Image("res/ext/4.png");
    private static Image unify = new Image("res/ext/5.png");
    private static Image pointer = new Image("res/mouse.png");
    private static Image back1 = new Image("res/back/Colorful-Circle-Fractal.png");
    private static Image back2 = new Image("res/back/F85GHXH.jpg");

    static {
        for (int i = 0; i < 10; i++) {
            food[i] = new Image("res/food/" + (i + 1) + ".png");
        }
        for (int i = 0; i < 15; i++) {
            String s = (i < 10) ? "0" : "";
            killer[i] = new Image("res/killer/120px-Gearstar_ani" + s + i + ".png");
        }
        agar.add(new Image[96]);
        agar.add(new Image[50]);
        agar.add(new Image[50]);
        agar.add(new Image[98]);
        agar.add(new Image[98]);
        agar.add(new Image[48]);
        agar.add(new Image[50]);
        agar.add(new Image[50]);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < agar.get(i).length; j++) {
                String str = (i == 0) ? "" : " (" + (i + 1) + ")";
                String str2 = (j < 10) ? "0" : "";
                agar.get(i)[j] = new Image("res/eater/agar" + (i + 1) + "/giphy" + str + str2 + j + ".png");
            }
        }
    }


    public static Image getFood() {
        int r = (int) (Math.random() * 10);
        r = r == 10 ? 9 : r;
        return food[r];
    }

    public static Image[] getKiller() {
        return killer;
    }

    public static Image[] getAgar() {
        int r = (int) (Math.random() * 8);
        r = r == 8 ? 7 : r;
        return agar.get(r);
    }

    public static Image getSlicer() {
        return slicer;
    }

    public static Image getDestroyer() {
        return destroyer;
    }

    public static Image getGodmode() {
        return godmode;
    }

    public static Image getSpeedup() {
        return speedup;
    }

    public static Image getUnify() {
        return unify;
    }

    public static Image getPointer() {
        return pointer;
    }

    public static Image getBack1() {
        return back1;
    }

    public static Image getBack2() {
        return back2;
    }
}
