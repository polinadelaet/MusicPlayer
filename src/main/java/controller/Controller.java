package controller;

import java.io.File;
import java.util.List;

import AudioWorker.AudioWorker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Controller {

    private List<File> musicList;
    private final AudioWorker audioWorker = new AudioWorker();
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean isMusicPlay = false;
    private int index = 0;
    private File dirWithMusic;

    @FXML
    private Button nameDirWithAudio;

    @FXML
    private Button previousButton;

    @FXML
    private Button playPauseButton;

    @FXML
    private Button nextButton;

    @FXML
    void initialize() {

        //musicList = audioWorker.loadAudioFiles(dirWithMusic);
        //media = new Media(musicList.get(index).toURI().toString());
        //mediaPlayer = new MediaPlayer(media);

        ImageView pause = new ImageView(new Image("/icons/pause.jpg"));
        ImageView play = new ImageView(new Image("/icons/play.jpg"));
        pause.setFitWidth(110);
        pause.setFitHeight(110);
        play.setFitWidth(110);
        play.setFitHeight(110);

        playPauseButton.setOnAction(event -> {

            if (isMusicPlay) {
                mediaPlayer.pause();
                isMusicPlay = false;
                playPauseButton.setGraphic(play);
                return;
            }

            mediaPlayer.play();
            isMusicPlay = true;
            playPauseButton.setGraphic(pause);
        });

        nextButton.setOnAction(event -> {

            if (isMusicPlay = true) {
                mediaPlayer.stop();
                isMusicPlay = false;
            }

            index ++;
            try {
                media = new Media(musicList.get(index).toURI().toString());
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            isMusicPlay = true;
            playPauseButton.setGraphic(pause);
        });

        previousButton.setOnAction(event -> {
            if (isMusicPlay = true) {
                mediaPlayer.stop();
                isMusicPlay = false;
            }

            index --;
            try {
                media = new Media(musicList.get(index).toURI().toString());
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            isMusicPlay = true;
            playPauseButton.setGraphic(pause);
        });

        nameDirWithAudio.setOnAction(event -> {
            //DirectoryChooser directoryChooser = new DirectoryChooser();
            //Stage stage = (Stage) nameDirWithAudio.getScene().getWindow();
            //dirWithMusic = directoryChooser.showDialog(stage);
            //musicList = audioWorker.loadAudioFiles(nameDirWithAudio);
            //media = new Media(musicList.get(index).toURI().toString());
            //mediaPlayer = new MediaPlayer(media);
        });

    }

    public void setDirWithMusic(File dirWithMusic) {
        this.dirWithMusic = dirWithMusic;
        musicList = audioWorker.loadAudioFiles(dirWithMusic);
        media = new Media(musicList.get(index).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }
}
