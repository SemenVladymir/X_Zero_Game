package com.example.x_zero_game;
import javafx.scene.control.Button;
import java.util.Objects;

public class JThread extends Thread {

    JThread(String name){
        super(name);
    }

    public void run(Client client, Button[][] buttonArray, String symbol){
        while (true) {
            int[] pos = client.Input();
            buttonArray[pos[0]][pos[1]].setText(Objects.equals(symbol, "X") ? "0" : "X");
            buttonArray[pos[0]][pos[1]].setDisable(true);
        }
    }
}
