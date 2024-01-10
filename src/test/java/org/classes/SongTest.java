package org.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    @Test
    void testDefaultConstructor() {
        Song song = new Song();

        assertNotNull(song.getSongMD());
        assertNull(song.getSongMD().getName());
        assertNull(song.getSongMD().getDateOfCreation());
        assertNull(song.getSongMD().getDescription());
        assertEquals(0.0, song.getSongMD().getLength());
    }

    @Test
    void testParameterizedConstructor() {
        String name = "SongName";
        double length = 4.5;
        Metadata metadata = new Metadata(name, length);

        Song song = new Song(metadata);

        assertNotNull(song.getSongMD());
        assertEquals(name, song.getSongMD().getName());
        assertNull(song.getSongMD().getDateOfCreation());
        assertNull(song.getSongMD().getDescription());
        assertEquals(length, song.getSongMD().getLength());
    }
}
