import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class App extends Application {

    private Media media;
    private MediaPlayer mediaPlayer;
    private String audioPath = "D:/music/";

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            URL url = new File("src/main/java/mainScreen.fxml").toURI().toURL();
            //URL url = new File("src/main/java/screenWithMusic.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            primaryStage.setTitle("Music Player | polinadelaet");
            primaryStage.setScene(new Scene(root, 600, 400));
            //primaryStage.setScene(new Scene(root, 1400, 1000));
            primaryStage.setResizable(false);
            //primaryStage.setMinHeight(1000);
            //primaryStage.setMinWidth(1000);
            primaryStage.getIcons().add(new Image("picture.jpg"));
            primaryStage.show();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
