package controller;

import java.io.File;
import java.util.List;

import AudioWorker.AudioWorker;
import domain.Song;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
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
    private ListView<File> listView;

    @FXML
    private TableView<Song> titleSongTable;

    @FXML
    private TableColumn<Song, String> titleColumn;

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

            mediaSlider.setMin(0);
            mediaSlider.setMax(1);
            mediaSlider.setValue(0.3);

            volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    mediaPlayer.setVolume(volumeSlider.getValue());
                }
            });

            mediaSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    //mediaPlayer.seek(duration.multiply(mediaSlider.getValue()));
                    mediaPlayer.seek(Duration.seconds(mediaSlider.getValue()));
                    System.out.println(mediaSlider.getValue());
                }
            });





            mediaSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
                if (! isChanging) {
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
                try {
                    //media = new Media(list.get(index).getSongFile().toURI().toString());

                    nowPlaying = list.get(list.indexOf(nowPlaying) + 1);
                    play(nowPlaying);
                    songName.setText(nowPlaying.getName());
                    //media = new Media(list.get(index).getSongFile().toURI().toString());
                } catch (ArrayIndexOutOfBoundsException e) {
                    return;
                }
                //mediaPlayer = new MediaPlayer(media);
                //mediaPlayer.play();
                //isMusicPlay = true;
                //playPauseButton.setGraphic(pause);
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

        mediaPlayer.setVolume(0.3);
        if (isMusicPlay = true) {
            mediaPlayer.stop();
            isMusicPlay = false;
        }


        duration = mediaPlayer.getCurrentTime();
        System.out.println(duration);
        media = new Media(song.getSongFile().toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.play();
        isMusicPlay = true;
        playPauseButton.setGraphic(pause);
    }
}
