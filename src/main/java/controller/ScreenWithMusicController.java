package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import AudioWorker.AudioWorker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScreenWithMusicController {

    private List<Song> songList;
    private final AudioWorker audioWorker = new AudioWorker();
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean isMusicPlay = false;
    private int index = 0;
    private File dirWithMusic;
    private ImageView pause;
    private ImageView play;
    private ObservableList<Song> list;
    private Song nowPlaying;
    private boolean flag;

    @FXML
    private Text songName;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider mediaSlider;

    @FXML
    private Button previousButton;

    @FXML
    private Button playPauseButton;

    @FXML
    private Button nextButton;

    @FXML
    private Text timeNowLabel;

    @FXML
    private Text timeLabel;

    @FXML
    private ListView<File> listView;

    @FXML
    private TableView<Song> titleSongTable;

    @FXML
    private TableColumn<Song, String> titleColumn;

    @FXML
    private Button mainScreen;

    @FXML
    void initialize() {
        try {
            pause = new ImageView(new Image("/icons/pause.jpg"));
            play = new ImageView(new Image("/icons/play.jpg"));
            pause.setFitWidth(100);
            pause.setFitHeight(100);
            play.setFitWidth(100);
            play.setFitHeight(100);
            volumeSlider.setMin(0);
            volumeSlider.setMax(1);
            volumeSlider.setValue(0.3);
            flag = true;

            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(volumeSlider.getValue()));

            mediaSlider.setOnMouseClicked(event -> mediaPlayer.seek(Duration.seconds(mediaSlider.getValue())));

            mediaSlider.setOnMousePressed(event -> mediaPlayer.seek(Duration.seconds(mediaSlider.getValue())));

            titleSongTable.setOnMouseClicked(event -> playSongFromList());

            playPauseButton.setOnAction(event -> playOrPauseButtonAction());

            nextButton.setOnAction(event -> playNextSong());

            mainScreen.setOnAction((event) -> chooseAnotherFolder());

            previousButton.setOnAction(event -> playPreviousSong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDirWithMusic(File dirWithMusic) {
        this.dirWithMusic = dirWithMusic;
        songList = audioWorker.loadAudioFiles(dirWithMusic);
        list = FXCollections.observableArrayList(songList);
        titleSongTable.setItems(list);
        titleColumn.setCellValueFactory(cellData -> getStringProperty(cellData.getValue().getName()));
        media = new Media(list.get(index).getSongFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    private void pause() {
        mediaPlayer.pause();
        isMusicPlay = false;
        playPauseButton.setGraphic(play);
    }

    private void play(Song song) {
        if (isMusicPlay = true) {
            mediaPlayer.stop();
            isMusicPlay = false;
        }
        media = new Media(song.getSongFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.3);
        isMusicPlay = true;
        playPauseButton.setGraphic(pause);
        songName.setText(song.getName());

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            mediaSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            Duration time = new Duration(mediaPlayer.getTotalDuration().toMillis());
            double secondsEndTime = time.toSeconds() % 60;
            double minutesEndTime =  time.toSeconds() / 60;
            timeLabel.setText(String.format("%.1s", minutesEndTime) + ":" + String.format("%.2s", secondsEndTime));
            Duration timeNow = new Duration(newValue.toMillis());
            double secondsTimeNow = timeNow.toSeconds() % 60;
            double minutesTimeNow =  timeNow.toSeconds() / 60;

            if ((timeNow.toSeconds() % 60) < 10) {
                String s = "0" + (timeNow.toSeconds() % 60);
                timeNowLabel.setText(String.format("%.1s", minutesTimeNow) + ":" + String.format("%.2s", s));
                mediaSlider.setValue(newValue.toSeconds());
            } else {
                mediaSlider.setValue(newValue.toSeconds());
                timeNowLabel.setText(String.format("%.1s", minutesTimeNow) + ":" + String.format("%.2s", secondsTimeNow));
            }
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            isMusicPlay = false;
            nowPlaying = list.get(list.indexOf(nowPlaying) + 1);
            play(nowPlaying);
            songName.setText(nowPlaying.getName());
        });
    }

    private void playSongFromList() {
        timeLabel.setText(String.format("%.1s", 00) + ":" + String.format("%.2s", 00));
        flag = false;
        mediaSlider.setValue(0);
        nowPlaying = titleSongTable.getSelectionModel().getSelectedItem();
        songName.setText(nowPlaying.getName());
        play(nowPlaying);
    }

    private void playOrPauseButtonAction() {
        if (isMusicPlay) {
            pause();
            return;
        }
        if (flag) {return;}
        mediaPlayer.play();
        isMusicPlay = true;
        playPauseButton.setGraphic(pause);
    }

    private void playNextSong() {
        if (isMusicPlay = true) {
            mediaPlayer.stop();
            isMusicPlay = false;
        }
        try {
            nowPlaying = list.get(list.indexOf(nowPlaying) + 1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        play(nowPlaying);
    }

    private void playPreviousSong() {
        if (isMusicPlay = true) {
            mediaPlayer.stop();
            isMusicPlay = false;
        }
        try {
            nowPlaying = list.get(list.indexOf(nowPlaying) - 1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        play(nowPlaying);
    }

    private void chooseAnotherFolder() {
        mediaPlayer.stop();
        mainScreen.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("directoryChooserScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DirectoryChooserController controller = loader.getController();
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root));
        stage1.setTitle("Music Player | polinadelaet");
        stage1.setResizable(false);
        stage1.getIcons().add(new Image("picture.jpg"));
        stage1.show();
    }

    private StringProperty getStringProperty(String string) {
        return new SimpleStringProperty(string);
    }
}
