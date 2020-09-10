package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DirectoryChooserController {
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
            FXMLLoader loader = new FXMLLoader(DirectoryChooserController.class
                    .getResource("/screenWithMusic.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
                ScreenWithMusicController controller = loader.getController();
                controller.setDirWithMusic(dirWithMusic);
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root, 1000, 700));
                stage1.setMinHeight(800);
                stage1.setMaxHeight(1100);
                stage1.setMinWidth(1000);
                stage1.setMaxHeight(1400);
                stage1.setTitle("Music Player | polinadelaet");
                stage1.getIcons().add(new Image("picture.jpg"));
                stage1.show();
        });
    }
}
