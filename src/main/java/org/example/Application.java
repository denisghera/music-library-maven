package org.example;
import java.util.*;

public class Application {
    InputDevice id;
    OutputDevice od;
    Library library = new Library();
    UserDatabase userDatabase = new UserDatabase();
    HashMap<String,List<Playlist>> playlistsMap = new HashMap<>();
    User connectedUser = new User();

    public Application(InputDevice id, OutputDevice od) {
        this.id = id;
        this.od = od;
    }

    void startApplication(String username, String password) {
        library = id.fetchLibrary("src/main/resources/");
        userDatabase = id.fetchUserDatabase("src/main/resources/");
        mapPlaylists();
        try {
            connectedUser = findUser(userDatabase.userList, username, password);
            if (connectedUser == null) {
                throw new ObjectNotFoundException("User " + username + " not found in database!");
            } else {
                System.out.println("Welcome to the music library " + connectedUser.userMD.name + "!");
                mainMenu();
            }
        } catch (ObjectNotFoundException e) {
            od.print(e.getMessage());
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
                        exited = true;
                        break;
                    default:
                        throw new UnrecognizedInputException("Unrecognized keyword: " + input + '\n');
                }
            } catch (UnrecognizedInputException e) {
                od.print(e.getMessage());
            }
        }
    }

    public void libraryMenu() {
        boolean exited = false;
        while (!exited) {
            for(Album a : library.albums) a.computeTotalLength();
            for(Playlist p : library.playlists) p.computeTotalLength();
            od.print("");
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
                od.print(e.getMessage());
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
                od.print(e.getMessage());
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
                                    p.addTrack(accessedSong);
                                    od.print(accessedSong.getName() + " added to " + playlistName);
                                    playlistFound = true;
                                    break;
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
                od.print(e.getMessage());
            }
        }
    }
    private static String extractPlaylistName(String[] inputWords) {
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
            } else {
                if (inQuotes) {
                    playlistName.append(" ").append(word);
                } else {
                    playlistName.append(word);
                }
            }
        }

        return playlistName.toString();
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
