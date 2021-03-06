package model;

import java.io.File;

public final class Song {

    private final String name;
    private final File songFile;

    public Song(File file) {
        StringBuffer stringBuffer = new StringBuffer(file.getName())
                .delete(
                        file.getName().length()-4,
                        file.getName().length()
                );
        this.name = stringBuffer.toString();
        this.songFile = file;
    }

    public String getName() {
        return name;
    }

    public File getSongFile() {
        return songFile;
    }
}
