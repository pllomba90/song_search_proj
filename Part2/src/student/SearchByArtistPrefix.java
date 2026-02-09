/**
 * File: SearchByArtistPrefix.java 
 *****************************************************************************
 *                       Revision History
 *****************************************************************************
 * 02/02/26 Pete Lombardo Completed search
 * 8/2015 Anne Applin - Added formatting and JavaDoc 
 * 2015 - Bob Boothe - starting code  
 *****************************************************************************

 */

package student;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
/**
 * Search by Artist Prefix searches the artists in the song database 
 * for artists that begin with the input String
 * @author Bob Booth 
 */

public class SearchByArtistPrefix {
    // keep a local direct reference to the song array
    private Song[] songs;  

    /**
     * constructor initializes the property. [Done]
     * @param sc a SongCollection object
     */
    public SearchByArtistPrefix(SongCollection sc) {
        songs = sc.getAllSongs();
    }

    /**
     * find all songs matching artist prefix uses binary search should operate
     * in time log n + k (# matches)
     * converts artistPrefix to lowercase and creates a Song object with 
     * artist prefix as the artist in order to have a Song to compare.
     * walks back to find the first "beginsWith" match, then walks forward
     * adding to the arrayList until it finds the last match.
     *Exception in thread "main" java.lang.ExceptionInInitializerError
Caused by: java.lang.RuntimeException: Uncompilable code - cannot find symbol
  symbol:   class Song
  location: class student.SearchByArtistPrefix
	at student.SearchByArtistPrefix.<clini
     * @param artistPrefix all or part of the artist's name
     * @return an array of songs by artists with substrings that match 
     *    the prefix
     */
    public Song[] search(String artistPrefix) {
       
        Song.CmpArtist cmp = new Song.CmpArtist();
        Song dummySong = new Song(artistPrefix, "", "");
        ArrayList<Song> matches = new ArrayList<>();
        
        int index = Arrays.binarySearch(songs, dummySong, cmp);
        
       
        int binSearchCmps = cmp.getCmpCnt();

        if (index < 0) {
            index = -(index + 1);
        }

        int loopCmps = 0;
        String prefixLower = artistPrefix.toLowerCase();

        while (index > 0) {
            loopCmps++;
            if (songs[index - 1].getArtist().toLowerCase().startsWith(prefixLower)) {
                index--;
            } else {
                break;
            }
        }

        while (index < songs.length) {
            loopCmps++;
            if (songs[index].getArtist().toLowerCase().startsWith(prefixLower)) {
                matches.add(songs[index]);
                index++;
            } else {
                break;
            }
        }


        int totalCmps = binSearchCmps + loopCmps;
    
        double theoretical = Math.log(songs.length) / Math.log(2);

        System.out.println("Binary search comparisons: " + binSearchCmps);
        System.out.println("Loop comparisons: " + loopCmps);
        System.out.println("Total comparisons: " + totalCmps);
        System.out.printf("Theoretical complexity (log2 N): %.2f%n", theoretical);

        return matches.toArray(new Song[0]);
    }

    /**
     * testing method for this unit
     * @param args  command line arguments set in Project Properties - 
     * the first argument is the data file name and the second is the partial 
     * artist name, e.g. be which should return beatles, beach boys, bee gees,
     * etc.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("usage: prog songfile [search string]");
            return;
        }

        SongCollection sc = new SongCollection(args[0]);
        SearchByArtistPrefix sbap = new SearchByArtistPrefix(sc);

        if (args.length > 1) {
            System.out.println("searching for: " + args[1]);
            Song[] byArtistResult = sbap.search(args[1]);

            Stream.of(sc).limit(10).forEach(System.out::println);
        }
    }
}
