package org.classes;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    @Test
    void testDefaultConstructor() {
        Album album = new Album();
        assertNotNull(album.getMd());
        assertNotNull(album.getCreator());
        assertNotNull(album.getSongList());
        assertEquals(0, album.getSongList().size());
    }

    @Test
    void testParameterizedConstructor() {
        Metadata metadata = new Metadata();
        User user = new User();
        List<Song> songList = new ArrayList<>();
        songList.add(new Song());

        Album album = new Album(metadata, user, songList);

        assertEquals(metadata, album.getMd());
        assertEquals(user, album.getCreator());
        assertEquals(songList, album.getSongList());
    }

    @Test
    void testComputeTotalLength() {
        Album testAlbum = new Album();
        Metadata testMetadata1 = new Metadata("TestSong1",1.4);
        Song testSong1 = new Song(testMetadata1);
        testAlbum.addTrack(testSong1);

        testAlbum.computeTotalLength();
        assertEquals(1.4, testAlbum.getMd().getLength());

        Metadata testMetadata2 = new Metadata("TestSong2", 3.18);
        Song testSong2 = new Song(testMetadata2);
        testAlbum.addTrack(testSong2);

        testAlbum.computeTotalLength();
        assertEquals(4.58, testAlbum.getMd().getLength());

        Metadata testMetadata3 = new Metadata("TestSong3", 2.14);
        Song testSong3 = new Song(testMetadata3);
        testAlbum.addTrack(testSong3);

        testAlbum.computeTotalLength();
        assertEquals(7.12, testAlbum.getMd().getLength());
    }
}