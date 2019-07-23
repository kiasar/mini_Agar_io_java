package View;

import board.GameMap;
import circle.Food;
import circle.KillerCircle;
import player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by ali on 1/6/2016.
 */

public class Network {
    public boolean isServer;
    public Socket socket;
    public ServerSocket serverSocket;
    public ObjectOutputStream output;
    public ObjectInputStream input;

    public Network(boolean isServer, String serverIP, int port) {
        this.isServer = isServer;
        try {
            if (isServer) {
                    serverSocket = new ServerSocket(port,10);
                    socket = serverSocket.accept();
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    sendMap();
            } else {
                socket = new Socket(serverIP, port);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                getMap();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMap() {
        try {
            output.write(Main.g.getX());
            output.write(Main.g.getY());
            output.writeObject(Main.g.getPlayers());
            output.writeObject(Main.g.getKillerCircles());
            output.writeObject(Main.g.getFoods());
            output.writeObject(GameObjectID.ids);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMap() {
        try {
            int x=input.read();
            int y=input.read();
            LinkedList<Player> obj1 = ((LinkedList<Player>) input.readObject());
            LinkedList<KillerCircle> obj2 = ((LinkedList<KillerCircle>) input.readObject());
            LinkedList<Food> obj3 = ((LinkedList<Food>) input.readObject());
            ArrayList<GameObjectID> obj4= ((ArrayList<GameObjectID>) input.readObject());
            Main.g=new GameMap(x,y);
            Main.g.setFoods(obj3);
            Main.g.setKillerCircles(obj2);
            Main.g.setPlayers(obj1);
            GameObjectID.ids=obj4;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send(Sender runnable) {
        try {
            output.writeObject(runnable);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sender receive() {
        Sender sender = null;
        try {
            sender = (Sender) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sender;
    }
}
