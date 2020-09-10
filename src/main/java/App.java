import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("/directoryChooserScreen.fxml"));
        primaryStage.setTitle("Music Player | polinadelaet");
        primaryStage.setScene(new Scene(root, 585, 380));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("picture.jpg"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
