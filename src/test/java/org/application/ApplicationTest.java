package org.application;

import org.classes.Credentials;
import org.classes.Metadata;
import org.classes.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void testFindUser() {
        Credentials credentials1 = new Credentials("user1", "password1");
        Credentials credentials2 = new Credentials("user2", "password2");
        Credentials credentials3 = new Credentials("user3", "password3");
        Credentials credentials4 = new Credentials("user4", "password4");
        Credentials credentials5 = new Credentials("user5", "password5");
        Credentials credentials6 = new Credentials("user6", "password6");
        Credentials credentials7 = new Credentials("user7", "password7");
        Credentials credentials8 = new Credentials("user8", "password8");
        Credentials credentials9 = new Credentials("user9", "password9");

        User user1 = new User(new Metadata(), credentials1);
        User user2 = new User(new Metadata(), credentials2);
        User user3 = new User(new Metadata(), credentials3);
        User user4 = new User(new Metadata(), credentials4);
        User user5 = new User(new Metadata(), credentials5);
        User user6 = new User(new Metadata(), credentials6);
        User user7 = new User(new Metadata(), credentials7);
        User user8 = new User(new Metadata(), credentials8);
        User user9 = new User(new Metadata(), credentials9);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
        userList.add(user7);
        userList.add(user8);
        userList.add(user9);

        User foundUser1 = Application.findUser(userList, "user1", "password1");
        User foundUser2 = Application.findUser(userList, "user2", "password2");
        User foundUser3 = Application.findUser(userList, "user3", "password3");
        User foundUser4 = Application.findUser(userList, "user4", "password4");
        User foundUser5 = Application.findUser(userList, "user5", "password5");
        User foundUser6 = Application.findUser(userList, "user6", "password6");
        User foundUser7 = Application.findUser(userList, "user7", "password7");
        User foundUser8 = Application.findUser(userList, "user8", "password8");
        User foundUser9 = Application.findUser(userList, "user9", "password9");
        User notFoundUser = Application.findUser(userList, "nonexistent_user", "nonexistent_password");

        assertNotNull(foundUser1);
        assertNotNull(foundUser2);
        assertNotNull(foundUser3);
        assertNotNull(foundUser4);
        assertNotNull(foundUser5);
        assertNotNull(foundUser6);
        assertNotNull(foundUser7);
        assertNotNull(foundUser8);
        assertNotNull(foundUser9);

        assertEquals(user1, foundUser1);
        assertEquals(user2, foundUser2);
        assertEquals(user3, foundUser3);
        assertEquals(user4, foundUser4);
        assertEquals(user5, foundUser5);
        assertEquals(user6, foundUser6);
        assertEquals(user7, foundUser7);
        assertEquals(user8, foundUser8);
        assertEquals(user9, foundUser9);

        assertNull(notFoundUser);
    }

    @Test
    void testExtractPlaylistName() {
        String[] inputWords1 = {"add", "PlaylistName"};
        String[] inputWords2 = {"add", "\"Playlist", "Name\""};
        String[] inputWords3 = {"add", "Playlist", "without", "quotes"};

        assertEquals("PlaylistName", Application.extractPlaylistName(inputWords1));
        assertEquals("Playlist Name", Application.extractPlaylistName(inputWords2));
        assertEquals("Playlist", Application.extractPlaylistName(inputWords3));
    }
}