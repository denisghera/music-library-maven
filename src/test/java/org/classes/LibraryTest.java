package org.classes;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void testDefaultConstructor() {
        Library library = new Library();

        assertNotNull(library.getAlbums());
        assertNotNull(library.getPlaylists());
        assertEquals(0, library.getAlbums().size());
        assertEquals(0, library.getPlaylists().size());
    }

    @Test
    void testParameterizedConstructor() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album());
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist());

        Library library = new Library(albums, playlists);

        assertEquals(albums, library.getAlbums());
        assertEquals(playlists, library.getPlaylists());
    }
}
