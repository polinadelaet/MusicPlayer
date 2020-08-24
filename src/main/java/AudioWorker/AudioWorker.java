package AudioWorker;

import domain.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;
import java.util.List;

public final class AudioWorker {

    //private final static String nameDirWithAudio = "D:/music";
    private File[] audioFiles;
    //private Queue<File> musicList;
    private List<Song> songList = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private Media media;

    public List<Song> loadAudioFiles(File file) {


        audioFiles = file.listFiles((dir1, name) -> name.endsWith(".mp3"));

        for (File file1: audioFiles) {

            Song song = new Song(file1);
            songList.add(song);

        }

        return songList;
        /*
        File dirWithAudio = new File(nameDirWithAudio);
        audioFiles = dirWithAudio.listFiles((dir1, name) -> name.endsWith(".mp3"));
        musicList = Arrays.asList(audioFiles);
        return musicList;
         */

    }
}
