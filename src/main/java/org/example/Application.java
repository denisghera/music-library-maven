package org.example;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Application {
    InputDevice id;
    OutputDevice od;

    public Application(InputDevice id, OutputDevice od) {
        this.id = id;
        this.od = od;
    }

    void startApplication(String username, String password) {
        Library library = new Library();
        UserDatabase userDatabase = new UserDatabase();
        library = id.fetchLibrary("src/main/resources/");
        userDatabase = id.fetchUserDatabase("src/main/resources/");

        User foundUser = findUser(userDatabase.userList, username, password);
        if(foundUser == null) {
            System.out.println("No user found!");
        }
        else {
            System.out.println("Welcome to the music library " + foundUser.userMD.name + "!");
            library.printPlaylists();
            library.printAlbums();
        }
    }

    User findUser(List<User> userList, String inputUsername, String inputPassword) {
        for (User user : userList) {
            Credentials userCredentials = user.credentials;
            if (userCredentials.username.equals(inputUsername) && userCredentials.password.equals(inputPassword)) {
                return user;
            }
        }
        return null;
    }
    public enum SortingOrder {
        ASCENDING, DESCENDING
    }
    public void sortAlbums(List<Album> albums, SortingOrder order) {
        Comparator<Album> comparator = (order == SortingOrder.DESCENDING) ?
                Collections.reverseOrder() : null;

        albums.sort(comparator);
    }
    public void sortAlbums(List<Album> albums) {
        sortAlbums(albums, SortingOrder.ASCENDING);
    }
    public void sortSongs(List<Song> songs, SortingOrder order) {
        Comparator<Song> comparator = (order == SortingOrder.DESCENDING) ?
                Collections.reverseOrder() : null;

        songs.sort(comparator);
    }
    public void sortSongs(List<Song> songs) {
        sortSongs(songs, SortingOrder.ASCENDING);
    }
    public void sortAlbumsByName(List<Album> albums, SortingOrder order) {
        Comparator<Album> comparator = Comparator.comparing(Album::getName);
        if (order == SortingOrder.DESCENDING) {
            comparator = comparator.reversed();
        }
        albums.sort(comparator);
    }
    public void sortAlbumsByName(List<Album> albums) {
        sortAlbumsByName(albums, SortingOrder.ASCENDING);
    }
    public void sortSongsByName(List<Song> songs, SortingOrder order) {
        Comparator<Song> comparator = Comparator.comparing(Song::getName);
        if (order == SortingOrder.DESCENDING) {
            comparator = comparator.reversed();
        }
        songs.sort(comparator);
    }
    public void sortSongsByName(List<Song> songs) {
        sortSongsByName(songs, SortingOrder.ASCENDING);
    }
}
