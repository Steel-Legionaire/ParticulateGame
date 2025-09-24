package particulate.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ParticulateApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Click me");
        button.setOnAction(e -> {
            System.out.println("Hello World!");
        });

        StackPane root = new StackPane(button);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JavaFX Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
