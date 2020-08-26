package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MainController {

    private File dirWithMusic;

    @FXML
    private Button nameDirWithAudio;

    @FXML
    void initialize() {

        nameDirWithAudio.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            Stage stage = (Stage) nameDirWithAudio.getScene().getWindow();
            dirWithMusic = directoryChooser.showDialog(stage);
            nameDirWithAudio.getScene().getWindow().hide();

            try {
                URL url = new File("src/main/java/screenWithMusic.fxml").toURI().toURL();

                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                Controller controller = loader.getController();
                controller.setDirWithMusic(dirWithMusic);

                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root));
                stage1.setMinHeight(1000);
                stage1.setMinWidth(700);
                stage1.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*Parent root = loader.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root));
            stage1.showAndWait();
             */
        });
    }

    public File getDirWithMusic() {
        return dirWithMusic;
    }
}
