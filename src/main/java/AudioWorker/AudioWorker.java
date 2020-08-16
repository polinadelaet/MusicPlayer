package AudioWorker;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public final class AudioWorker {

    //private final static String nameDirWithAudio = "D:/music";
    private File[] audioFiles;
    //private Queue<File> musicList;
    private List<File> musicList;
    private MediaPlayer mediaPlayer;
    private Media media;

    public List<File> loadAudioFiles(File file) {

        audioFiles = file.listFiles((dir1, name) -> name.endsWith(".mp3"));
        musicList = Arrays.asList(audioFiles);
        return musicList;
        /*
        File dirWithAudio = new File(nameDirWithAudio);
        audioFiles = dirWithAudio.listFiles((dir1, name) -> name.endsWith(".mp3"));
        musicList = Arrays.asList(audioFiles);
        return musicList;
         */

    }
}
