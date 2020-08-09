import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            URL url = new File("src/main/java/sa.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sa.fxml"));
            //AnchorPane ap = (AnchorPane)FXMLLoader.load(App.class.getResource("sa.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/main/java/sa.fxml"));
            primaryStage.setTitle("xui");
            primaryStage.setScene(new Scene(root, 900, 723));
            primaryStage.show();
        } catch (NullPointerException | IllegalStateException e) {
            e.printStackTrace();
        }

        /*
        Label label = new Label("Hello");               // текстовая метка
        Button button = new Button("Button");// кнопка
        Button button1 = new Button("aye");
        Group group = new Group(button);                // вложенный узел Group
         */


/*
        button.setOnAction(e -> {

            button.setText("You've clicked!");
        });

 */
        //FlowPane root = new FlowPane( group);       // корневой узел

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
