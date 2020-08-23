package controller;

import java.io.File;
import java.nio.channels.SelectionKey;
import java.util.List;

import AudioWorker.AudioWorker;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class Controller {

    private List<File> musicList;
    private final AudioWorker audioWorker = new AudioWorker();
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean isMusicPlay = false;
    private int index = 0;
    private File dirWithMusic;
    private String songNames;
    private ImageView pause;
    private ImageView play;

    @FXML
    private Text songName;

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
    void initialize() {

        //musicList = audioWorker.loadAudioFiles(dirWithMusic);
        //media = new Media(musicList.get(index).toURI().toString());
        //mediaPlayer = new MediaPlayer(media);
        try {


            pause = new ImageView(new Image("/icons/pause.jpg"));
            play = new ImageView(new Image("/icons/play.jpg"));
            pause.setFitWidth(110);
            pause.setFitHeight(110);
            play.setFitWidth(110);
            play.setFitHeight(110);


            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    songName.setText(listView.getSelectionModel().getSelectedItem().getName());
                    play();
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

                index++;
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

                index--;
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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDirWithMusic(File dirWithMusic) {
        this.dirWithMusic = dirWithMusic;
        musicList = audioWorker.loadAudioFiles(dirWithMusic);
        //musicList.get(0).getName().replace(musicList.get(0).getName().length()-4, musicList.get(0).getName().length()-1);
        //StringBuffer stringBuffer = new StringBuffer(musicList.get(0).getName());
        //stringBuffer.delete(musicList.get(0).getName().length()-4, musicList.get(0).getName().length()-1);
        //System.out.println(stringBuffer.delete(musicList.get(0).getName().length()-4, musicList.get(0).getName().length()-1));
        //musicList.stream().map(x -> new StringBuffer(x.getName()).delete(x.getName().length()-4, x.getName().length()-1))
          //      .forEach(listView.getItems().addAll());
        listView.getItems().addAll(musicList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList list = listView.getSelectionModel().getSelectedItems();
        System.out.println(list.get(0));
        for (Object item: list) {
            songName.setText((String) item);
        }

        media = new Media(musicList.get(index).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    private void play(){
        if (isMusicPlay) {
            mediaPlayer.pause();
            isMusicPlay = false;
            playPauseButton.setGraphic(play);
            return;
        }

        mediaPlayer.play();
        isMusicPlay = true;
        playPauseButton.setGraphic(pause);
    }
}
