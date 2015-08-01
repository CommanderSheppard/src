package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private final View viewInstance = new View();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //  Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Try to remember all");
        primaryStage.setScene(viewInstance.createScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
