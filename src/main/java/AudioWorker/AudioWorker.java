package AudioWorker;

import model.Song;

import java.io.File;
import java.util.*;
import java.util.List;

public final class AudioWorker {

    private File[] audioFiles;
    private List<Song> songList = new ArrayList<>();

    public List<Song> loadAudioFiles(File file) {
        audioFiles = file.listFiles((dir1, name) -> name.endsWith(".mp3"));
        for (File file1: audioFiles) {
            Song song = new Song(file1);
            songList.add(song);
        }
        return songList;
    }
}
