/**
 * File: SongCollection.java
 ************************************************************************
 *                     Revision History (newest first)
 ************************************************************************
 *01/28/26 - Pete Lombardo- added Song Collection read in and updated
 * main function.
 * 8.2016 - Anne Applin - formatting and JavaDoc skeletons added
 * 2015 -   Prof. Bob Boothe - Starting code and main for testing
 * 
 ************************************************************************
 */
package student;

import java.util.stream.Stream;

/**
 * SongCollection.java Reads the specified data file and build an array of
 * songs.
 *
 * @author boothe
 */
public class SongCollection {

    private Song[] songs;

    /**
     * Note: in any other language, reading input inside a class is simply not
     * done!! No I/O inside classes because you would normally provide
     * precompiled classes and I/O is OS and Machine dependent and therefore not
     * portable. Java runs on a virtual machine that IS portable. So this is
     * permissable because we are programming in Java and Java runs on a virtual
     * machine not directly on the hardware.
     *
     * @param filename The path and filename to the datafile that we are using
     * must be set in the Project Properties as an argument.
     */
    public SongCollection(String filename) {

        try {
            java.io.File file = new java.io.File(filename);
            java.util.Scanner scanner = new java.util.Scanner(file);

            scanner.useDelimiter("\"");

            java.util.ArrayList<Song> songList = new java.util.ArrayList<>();

            while (scanner.hasNext()) {
                String tagArtist = scanner.next();
                if (scanner.hasNext()) {
                    String artist = scanner.next();
                    scanner.next();
                    String title = scanner.next();
                    scanner.next();
                    String lyrics = scanner.next();

                    songList.add(new Song(artist, title, lyrics));
                }
            }
            scanner.close();

            songs = songList.toArray(new Song[0]);

            java.util.Arrays.sort(songs);

        } catch (java.io.FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

    /**
     * this is used as the data source for building other data structures
     *
     * @return the songs array
     */
    public Song[] getAllSongs() {
        return songs;
    }

    /**
     * unit testing method
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile");
            return;
        }

        SongCollection sc = new SongCollection(args[0]);

        Song[] allSongs = sc.getAllSongs();

        
        System.out.println("Total songs");
        System.out.println(allSongs.length + ", first songs:");

        Stream.of(allSongs).limit(10).forEach(System.out::println);
    }
}
