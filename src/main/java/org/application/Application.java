package org.application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.classes.*;
import org.exceptions.AccessDeniedException;
import org.exceptions.ObjectNotFoundException;
import org.exceptions.UnrecognizedInputException;
import org.threads.FindUserThread;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Application {
    InputDevice id;
    OutputDevice od;
    public Library library = new Library();
    public UserDatabase userDatabase = new UserDatabase();
    public HashMap<String,List<Playlist>> playlistsMap = new HashMap<>();
    User connectedUser = new User();

    public Application(InputDevice id, OutputDevice od) {
        this.id = id;
        this.od = od;
    }

    void startApplication(String username, String password) {
        try {
            fetchJSONFromDB("src/main/resources/Library.json", 1);
            fetchJSONFromDB("src/main/resources/UserDatabase.json", 2);
            library = id.fetchLibrary("src/main/resources/");
            userDatabase = id.fetchUserDatabase("src/main/resources/");
            mapPlaylists();
            connectedUser = findUser(userDatabase.userList, username, password);
            if (connectedUser == null) {
                throw new ObjectNotFoundException("User " + username + " not found in database!");
            } else {
                System.out.println("Welcome to the music library " + connectedUser.userMD.name + "!");
                mainMenu();
            }
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    public static User findUser(List<User> userList, String inputUsername, String inputPassword) {
        int numThreads = 1; // Number of threads to use
        int segmentSize = (int) Math.ceil((double) userList.size() / numThreads);

        List<FindUserThread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentSize;
            int end = Math.min((i + 1) * segmentSize, userList.size());

            if (start < end) {
                List<User> segment = userList.subList(start, end);
                FindUserThread thread = new FindUserThread(segment, inputUsername, inputPassword);
                threads.add(thread);
                thread.start();
            }
        }

        for (FindUserThread thread : threads) {
            try {
                thread.join();
                User foundUser = thread.getResultUser();
                if (foundUser != null) {
                    interruptThreadsExcept(threads, thread);
                    return foundUser;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }
    private static void interruptThreadsExcept(List<FindUserThread> threads, Thread exceptThread) {
        for (FindUserThread thread : threads) {
            if (thread != exceptThread) {
                thread.interrupt();
            }
        }
    }
    public void mapPlaylists() {
        for (Playlist playlist : library.playlists) {
            String creatorName = playlist.getCreator().getUserMD().getName();
            boolean creatorExists = false;
            for (User user : userDatabase.userList) {
                if (user.getUserMD().getName().equals(creatorName)) {
                    creatorExists = true;
                    break;
                }
            }
            if (creatorExists) {
                List<Playlist> userPlaylists = playlistsMap.getOrDefault(creatorName, new ArrayList<>());
                userPlaylists.add(playlist);
                playlistsMap.put(creatorName, userPlaylists);
            }
        }
    }
    public void mainMenu() {
        boolean exited = false;
        while(!exited) {
            od.print("");
            od.print("======= GDL Music =======");
            od.print("library | account | exit");
            od.print("=========================");
            od.print("");
            String input = id.read();
            try {
                switch (input) {
                    case "library":
                        libraryMenu();
                        break;
                    case "account":
                        connectedUser.printMetadata();
                        break;
                    case "exit":
                        od.print("Closing application...");
                        od.saveLibrary("src/main/resources/", library);
                        od.saveUserDatabase("src/main/resources/", userDatabase);
                        updateJSONInDB("src/main/resources/Library.json", 1);
                        updateJSONInDB("src/main/resources/UserDatabase.json", 2);
                        exited = true;
                        break;
                    default:
                        throw new UnrecognizedInputException("Unrecognized keyword: " + input + '\n');
                }
            } catch (UnrecognizedInputException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void libraryMenu() {
        boolean exited = false;
        while (!exited) {
            library.printAlbums();
            library.printPlaylists();
            od.print("");
            od.print("================== Library Menu ==================");
            od.print("album/playlist <number>| sort [name] [desc] | back");
            od.print("==================================================");
            od.print("");
            String input = id.read();
            String[] inputWords = input.split(" ");
            String command = inputWords[0];
            try {
                switch (command) {
                    case "album":
                        if (inputWords.length == 2) {
                            try {
                                albumMenu(Integer.parseInt(inputWords[1]) - 1, true);
                            } catch (IndexOutOfBoundsException e) {
                                throw new UnrecognizedInputException("Please select a valid number!");
                            }
                        } else throw new UnrecognizedInputException("Album number not specified!");
                        break;
                    case "playlist":
                        if (inputWords.length == 2) {
                            try {
                                albumMenu(Integer.parseInt(inputWords[1]) - 1, false);
                            } catch (IndexOutOfBoundsException e) {
                                throw new UnrecognizedInputException("Please select a valid number!");
                            }
                        } else throw new UnrecognizedInputException("Playlist number not specified!");
                        break;
                    case "sort":
                        if (inputWords.length == 2) {
                            if (inputWords[1].equals("name")) sortAlbumsByName(library.albums);
                            else if (inputWords[1].equals("desc")) sortAlbums(library.albums, SortingOrder.DESCENDING);
                            else throw new UnrecognizedInputException("Select a valid command!");
                        } else if (inputWords.length == 3) {
                            if (inputWords[1].equals("name") && inputWords[2].equals("desc"))
                                sortAlbumsByName(library.albums, SortingOrder.DESCENDING);
                            else throw new UnrecognizedInputException("Select a valid command!");
                        } else if (inputWords.length == 1) sortAlbums(library.albums);
                        else throw new UnrecognizedInputException("Select a valid command!");
                        break;
                    case "back":
                        exited = true;
                        break;
                    default:
                        throw new UnrecognizedInputException("Unrecognized keyword: " + input + '\n');
                }
            } catch (UnrecognizedInputException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void albumMenu(int accessNumber, boolean isAlbum) {
        boolean exited = false;
        Album accessedAlbum;
        if(isAlbum) accessedAlbum = library.albums.get(accessNumber);
        else accessedAlbum = library.playlists.get(accessNumber);
        while(!exited){
            accessedAlbum.printMetadata();
            accessedAlbum.printTracks();
            od.print("");
            if(isAlbum) {
                od.print("======================== Album Menu ========================");
                od.print("song <number> | sort [name] [desc] | play/pause/stop | back");
                od.print("============================================================");
            } else {
                od.print("======================= Playlist Menu =======================");
                od.print("song <number> | sort [name] [desc] | play/pause/stop | back");
                od.print("=============================================================");
            }
            od.print("");
            String input = id.read();
            String[] inputWords = input.split(" ");
            String command = inputWords[0];
            try {
                switch (command) {
                    case "song":
                        if (inputWords.length == 2) {
                            try {
                                songMenu(accessedAlbum, Integer.parseInt(inputWords[1]) - 1);
                            } catch (IndexOutOfBoundsException e) {
                                throw new UnrecognizedInputException("Please select a valid number!");
                            }
                        } else throw new UnrecognizedInputException("Song number not specified!");
                        break;
                    case "sort":
                        if (inputWords.length == 2) {
                            if (inputWords[1].equals("name")) sortSongsByName(accessedAlbum.songList);
                            else if (inputWords[1].equals("desc"))
                                sortSongs(accessedAlbum.songList, SortingOrder.DESCENDING);
                            else throw new UnrecognizedInputException("Select a valid command!");
                        } else if (inputWords.length == 3) {
                            if (inputWords[1].equals("name") && inputWords[2].equals("desc"))
                                sortSongsByName(accessedAlbum.songList, SortingOrder.DESCENDING);
                            else throw new UnrecognizedInputException("Select a valid command!");
                        } else if (inputWords.length == 1) sortSongs(accessedAlbum.songList);
                        else throw new UnrecognizedInputException("Select a valid command!");
                        break;
                    case "play":
                        accessedAlbum.play();
                        break;
                    case "pause":
                        accessedAlbum.pause();
                        break;
                    case "stop":
                        accessedAlbum.stop();
                        break;
                    case "back":
                        exited = true;
                        break;
                    default:
                        throw new UnrecognizedInputException("Unrecognized keyword: " + input + '\n');
                }
            } catch (UnrecognizedInputException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void songMenu(Album accessedList, int songNumber) {
        boolean exited = false;
        Song accessedSong =  accessedList.songList.get(songNumber);
        while(!exited) {
            accessedSong.printMetadata();
            od.print("");
            od.print("================= Song Menu =================");
            od.print("play/pause/stop | add <playlist_name> | back");
            od.print("=============================================");
            od.print("");
            String input = id.read();
            String[] inputWords = input.split(" ");
            String command = inputWords[0];
            try {
                switch (command) {
                    case "play":
                        accessedSong.play();
                        break;
                    case "pause":
                        accessedSong.pause();
                        break;
                    case "stop":
                        accessedSong.stop();
                        break;
                    case "add":
                        boolean playlistFound = false;
                        String playlistName = extractPlaylistName(inputWords);
                        if (inputWords.length >= 2) {
                            for (Playlist p : library.playlists) {
                                if (p.getName().equals(playlistName)) {
                                    playlistFound = true;
                                    if(p.getCreator().getUserMD().getName().equals(connectedUser.getUserMD().getName())) {
                                        p.addTrack(accessedSong);
                                        od.print(accessedSong.getName() + " added to " + playlistName + '\n');
                                        break;
                                    } else {
                                        throw new AccessDeniedException("You are not the creator of this playlist!");
                                    }
                                }
                            }
                            if (!playlistFound) {
                                throw new ObjectNotFoundException("Playlist with name " + playlistName + " not found in library!");
                            }
                        } else {
                            throw new UnrecognizedInputException("Please specify playlist name!");
                        }
                        break;
                    case "back":
                        exited = true;
                        break;
                    default:
                        throw new UnrecognizedInputException("Unrecognized keyword: " + input + '\n');
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    static String extractPlaylistName(String[] inputWords) {
        StringBuilder playlistName = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 1; i < inputWords.length; i++) {
            String word = inputWords[i];

            if (word.startsWith("\"")) {
                inQuotes = true;
                playlistName.append(word.substring(1));
            } else if (word.endsWith("\"")) {
                inQuotes = false;
                playlistName.append(" ").append(word, 0, word.length() - 1);
                break;
            } else if (!inQuotes) {
                playlistName.append(word);
                break;
            } else {
                playlistName.append(" ").append(word);
            }
        }

        return playlistName.toString();
    }

    public static void updateJSONInDB(String filename, int rowId) {
        StringBuilder jsonContent = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String json = jsonContent.toString();

        String url = "jdbc:jtds:sqlserver://localhost/p3_database";
        String username = "admin";
        String password = "complicated_password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE dbo.main_table SET data = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, json);
            preparedStatement.setInt(2, rowId);
            preparedStatement.executeUpdate();
            //System.out.println(filename + " JSON data updated successfully in row " + rowId);
        } catch (SQLException e) {
            if (e.getErrorCode() == 18456) {
                System.err.println("Authentication failed. Check username and password.");
            } else {
                e.printStackTrace();
            }
        }
    }
    public static void fetchJSONFromDB(String filename, int rowId) {
        String url = "jdbc:jtds:sqlserver://localhost/p3_database";
        String username = "default_user";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT data FROM dbo.main_table WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rowId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String json = resultSet.getString("data");

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                Object jsonObject = objectMapper.readValue(json, Object.class);
                String formattedJson = objectMapper.writeValueAsString(jsonObject);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                    writer.write(formattedJson);
                    //System.out.println("Data fetched from database and written to " + filename + " for row " + rowId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("No data found in the database for row " + rowId);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 18456) {
                System.err.println("Authentication failed. Check username and password.");
            } else {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
