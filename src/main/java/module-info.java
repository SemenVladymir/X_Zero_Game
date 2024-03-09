module com.example.x_zero_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.x_zero_game to javafx.fxml;
    exports com.example.x_zero_game;
}