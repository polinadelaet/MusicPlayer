package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import AudioWorker.AudioWorker;
import domain.Song;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

    private List<Song> songList;
    private final AudioWorker audioWorker = new AudioWorker();
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean isMusicPlay = false;
    private int index = 0;
    private File dirWithMusic;
    private String songNames;
    private ImageView pause;
    private ImageView play;
    private ObservableList<Song> list;
    private Song nowPlaying;
    private Duration duration;
    private static double MIN_CHANGE = 0.5;

    @FXML
    private Text songName;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider mediaSlider;

    @FXML
    private Button nameDirWithAudio;

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
            pause.setFitWidth(110);
            pause.setFitHeight(110);
            play.setFitWidth(110);
            play.setFitHeight(110);

            volumeSlider.setMin(0);
            volumeSlider.setMax(1);
            volumeSlider.setValue(0.3);

            //mediaSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());

            volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    mediaPlayer.setVolume(volumeSlider.getValue());
                }
            });


           mediaSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event) {
                   mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));
               }
           });

           mediaSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event) {
                   mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));
               }
           });

            titleSongTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    nowPlaying = titleSongTable.getSelectionModel().getSelectedItem();
                    songName.setText(nowPlaying.getName());
                    play(nowPlaying);
                }
            });


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

                nowPlaying = list.get(list.indexOf(nowPlaying) + 1);
                play(nowPlaying);
                songName.setText(nowPlaying.getName());

            });

            mainScreen.setOnAction(event -> {
                try {
                    mainScreen.getScene().getWindow().hide();
                    URL url = new File("src/main/java/mainScreen.fxml").toURI().toURL();
                    FXMLLoader loader = new FXMLLoader(url);

                    Parent root = loader.load();
                    MainController controller = loader.getController();

                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root));

                    stage1.setTitle("Music Player | polinadelaet");

                    //primaryStage.setScene(new Scene(root, 1400, 1000));
                    stage1.setResizable(false);
                    //primaryStage.setMinHeight(1000);
                    //primaryStage.setMinWidth(1000);
                    stage1.getIcons().add(new Image("picture.jpg"));
                    stage1.show();
                } catch (IOException e) {}
            });

            previousButton.setOnAction(event -> {
                if (isMusicPlay = true) {
                    mediaPlayer.stop();
                    isMusicPlay = false;
                }
                try {
                    nowPlaying = list.get(list.indexOf(nowPlaying) - 1);
                    play(nowPlaying);
                    songName.setText(nowPlaying.getName());
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDirWithMusic(File dirWithMusic) {
        this.dirWithMusic = dirWithMusic;
        songList = audioWorker.loadAudioFiles(dirWithMusic);

        list = FXCollections.observableArrayList(songList);
        titleSongTable.setItems(list);
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getStringPropertyName());

        media = new Media(list.get(index).getSongFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    private void play(Song song){

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

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                mediaSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());

                Duration time = new Duration(mediaPlayer.getTotalDuration().toMillis());
                Duration timeNow = new Duration(newValue.toMillis());

                System.out.println(mediaSlider.getValue());
                System.out.println("_______" + timeNow);

                double secondsEndTime = time.toSeconds() % 60;
                double minutesEndTime =  time.toSeconds() / 60;
                timeLabel.setText(String.format("%.1s", minutesEndTime) + ":" + String.format("%.2s", secondsEndTime));
                double secondsTimeNow = timeNow.toSeconds() % 60;
                double minutesTimeNow =  timeNow.toSeconds() / 60;

                if ((timeNow.toSeconds() % 60) < 10) {

                    String s = "0" + (timeNow.toSeconds() % 60);
                    timeNowLabel.setText(String.format("%.1s", minutesTimeNow) + ":" + String.format("%.2s", s));
                } else {

                    mediaSlider.setValue(newValue.toSeconds());

                    System.out.println("sec = " + secondsEndTime);
                    System.out.println("min = " + minutesEndTime);

                    timeNowLabel.setText(String.format("%.1s", minutesTimeNow) + ":" + String.format("%.2s", secondsTimeNow));
                    timeLabel.setText(String.format("%.1s", minutesEndTime) + ":" + String.format("%.2s", secondsEndTime));
                }
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                isMusicPlay = false;
                nowPlaying = list.get(list.indexOf(nowPlaying) + 1);
                play(nowPlaying);
                songName.setText(nowPlaying.getName());
            }
        });
    }
}
