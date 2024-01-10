package org.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetadataTest {

    @Test
    void testDefaultConstructor() {
        Metadata metadata = new Metadata();

        assertNull(metadata.getName());
        assertNull(metadata.getDateOfCreation());
        assertNull(metadata.getDescription());
        assertEquals(0, metadata.getLength());
    }

    @Test
    void testMetadataConstructorForAlbumOrPlaylist() {
        String name = "AlbumName";
        String dateOfCreation = "2023-01-01";
        String description = "AlbumDescription";

        Metadata metadata = new Metadata(name, dateOfCreation, description);

        assertEquals(name, metadata.getName());
        assertEquals(dateOfCreation, metadata.getDateOfCreation());
        assertEquals(description, metadata.getDescription());
        assertEquals(0.0, metadata.getLength());
    }

    @Test
    void testMetadataConstructorForArtist() {
        String name = "ArtistName";
        String description = "ArtistDescription";

        Metadata metadata = new Metadata(name, description);

        assertEquals(name, metadata.getName());
        assertNull(metadata.getDateOfCreation());
        assertEquals(description, metadata.getDescription());
        assertEquals(0.0, metadata.getLength());
    }

    @Test
    void testMetadataConstructorForSong() {
        String name = "SongName";
        double length = 4.5;

        Metadata metadata = new Metadata(name, length);

        assertEquals(name, metadata.getName());
        assertNull(metadata.getDateOfCreation());
        assertNull(metadata.getDescription());
        assertEquals(length, metadata.getLength());
    }

    @Test
    void testFormattedLength() {

        Metadata testMetadata = new Metadata();

        String formattedLength1 = testMetadata.formattedLength(3.3);
        String formattedLength2 = testMetadata.formattedLength(7.08);
        String formattedLength3 = testMetadata.formattedLength(9.52);
        String formattedLength4 = testMetadata.formattedLength(2);

        assertEquals("3:30", formattedLength1);
        assertEquals("7:08", formattedLength2);
        assertEquals("9:52", formattedLength3);
        assertEquals("2:00", formattedLength4);
    }
}
