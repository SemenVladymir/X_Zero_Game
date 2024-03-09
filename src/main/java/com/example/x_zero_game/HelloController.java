package com.example.x_zero_game;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

    Client client = new Client();
    String symbol = client.GetSymbol();
    Playerstep step = new Playerstep("", symbol);
    String [][] playfield = new String [3][3];
    Button[][] buttonArray = null;

    @FXML
    private ResourceBundle resources;


    @FXML
    private TextArea PlayerName;

    @FXML
    private Label lstep;


    @FXML
    private GridPane panel;

    public HelloController() throws IOException {
    }


    @FXML
    void TxtChanged(InputMethodEvent event) {
        int [] pos;
        if (Objects.equals(symbol, "0")) {
            lstep.setText("Waiting");
            pos = client.Input();
            Button setbtn = getNodeByRowColumnIndex(pos[0], pos[1], panel);
            setbtn.setText(Objects.equals(symbol, "X") ? "0" : "X");
            setbtn.setDisable(true);
        }
//        if (buttonArray==null)
//            buttonArray = GetButtonsGridPanel();
//        var stepchecking = new JThread("stepchecking");
//        stepchecking.run(client, buttonArray, symbol);
    }

    @FXML
    void btnClick(ActionEvent event) throws IOException {
        if (buttonArray==null)
            buttonArray = GetButtonsGridPanel();
        Alert alert;
        if (PlayerName==null || PlayerName.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.INFORMATION, "You need input your name", ButtonType.OK);
            alert.show();
            return;
        } else if (step.PlayerName.isEmpty()) {
            step.PlayerName = PlayerName.getText();
            client.Send("name:"+PlayerName.getText());
        }
        Button btn = (Button) event.getSource();
        int row = GridPane.getRowIndex(btn) == null ? 0 : GridPane.getRowIndex(btn);
        int colum = GridPane.getColumnIndex(btn) == null ? 0 : GridPane.getColumnIndex(btn);
        buttonArray[row][colum].setText(symbol);
        buttonArray[row][colum].setDisable(true);
        playfield[row][colum] = symbol;
        if (Checking()) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Player " + PlayerName.getText() + " is win!", ButtonType.OK);
            alert.show();
            return;
        }
        lstep.setText("Waiting");
        int [] pos = client.Output(row + "/" + colum);
        lstep.setText("Your step");
        playfield[pos[0]][pos[1]] = Objects.equals(symbol, "X") ?"0":"X";
        buttonArray[pos[0]][pos[1]].setText(Objects.equals(symbol, "X") ?"0":"X");
        buttonArray[pos[0]][pos[1]].setDisable(true);
    }

    @FXML
    void initialize() {
    }

    private boolean Checking(){
        boolean check = false;
        if (Objects.equals(playfield[0][0], playfield[0][1]) && Objects.equals(playfield[0][1], playfield[0][2]) && Objects.equals(playfield[0][1], symbol) ||
                Objects.equals(playfield[1][0], playfield[1][1]) && Objects.equals(playfield[1][1], playfield[1][2]) && Objects.equals(playfield[1][2], symbol) ||
                Objects.equals(playfield[2][0], playfield[2][1]) && Objects.equals(playfield[2][1], playfield[2][2]) && Objects.equals(playfield[2][2], symbol) ||
                Objects.equals(playfield[0][0], playfield[1][0]) && Objects.equals(playfield[1][0], playfield[2][0]) && Objects.equals(playfield[2][0], symbol) ||
                Objects.equals(playfield[0][1], playfield[1][1]) && Objects.equals(playfield[1][1], playfield[2][1]) && Objects.equals(playfield[2][1], symbol) ||
                Objects.equals(playfield[0][2], playfield[1][2]) && Objects.equals(playfield[1][2], playfield[2][2]) && Objects.equals(playfield[2][2], symbol))
            check = true;
        return  check;
    }

    public Button getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if((gridPane.getRowIndex(node) == null ? 0 : GridPane.getRowIndex(node)) == row && (gridPane.getColumnIndex(node) == null ? 0 : GridPane.getColumnIndex(node)) == column) {
                result = node;
                break;
            }
        }
        return (Button) result;
    }

    public Button[][] GetButtonsGridPanel(){
        Button[][] tmp = new Button[3][3];
        for(Node node : panel.getChildren())
        {
            tmp[GridPane.getRowIndex(node)==null?0:GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)==null?0:GridPane.getColumnIndex(node)] = (Button) node;
        }
        return  tmp;
    }

}
