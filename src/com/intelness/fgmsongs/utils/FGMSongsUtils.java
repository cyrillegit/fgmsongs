package com.intelness.fgmsongs.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;
import android.util.SparseArray;

import com.intelness.fgmsongs.R;
import com.intelness.fgmsongs.beans.Song;

/**
 * utils functions
 * 
 * @author McCyrille
 * @version 1.0
 */
public class FGMSongsUtils {

    public static final String   FR                       = "fr";
    public static final String   EN                       = "en";
    public static final String   CUSTOM                   = "custom_";
    public static final String   NEW_LINE                 = "\n";
    public static final String   SPLIT                    = "##";
    public static final String   SPACE                    = " ";
    public static final int      MIN_CHAR                 = 3;
    public static final String[] ALPHABET                 = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                                                          "L", "M",
                                                          "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                                                          "Z" };
    public static final String[] LANGUAGES                = { "English", "French" };
    public static final String[] LOCALE                   = { "en", "fr" };
    public static final String   PREFERENCES              = "Preferences";
    public static final String   LANGUAGE                 = "language";
    public static final String   LAST_CUSTOM_NUMBER_SONG  = "lastCustomNumberSong";
    public static final int      FIRST_CUSTOM_NUMBER_SONG = 75;
    public static final String   XML_EXTENSION            = ".xml";
    private static final String  TAG                      = "FGMSongsUtils";
    private static final String  DATE_TIME_PATTERN        = "yyyy-MM-dd' 'HH:mm:ss";

    /**
     * put back to line in a string by the separator ##
     * 
     * @param string
     * @return
     */
    public static String StringsWithNewLine( String string ) {

        String[] splitString = string.split( FGMSongsUtils.SPLIT );
        String newString = "";
        for ( int i = 0; i < splitString.length; i++ ) {
            if ( i == splitString.length - 1 ) {
                newString += splitString[i].trim();
            } else {
                newString += splitString[i].trim() + System.getProperty( "line.separator" );
            }
        }
        return newString;
    }

    /**
     * replace \n by ##
     * 
     * @param string
     *            string1\n string2
     * @return string1## string2
     */
    public static String StringsReplaceNewLine( String string ) {
        return string.trim().replace( NEW_LINE, SPLIT );
    }

    /**
     * return the index of a song knowing its number
     * 
     * @param number
     *            the number of the song
     * @param songs
     *            the list of all the songs
     * @return index of the song
     */
    public static int getIndexByNumber( int number, List<Song> songs ) {
        for ( Song song : songs ) {
            if ( song.getNumber() == number ) {
                return songs.indexOf( song );
            }
        }
        return 0;
    }

    /**
     * get the title and the second line of songs
     * 
     * @param songs
     *            list of songs
     * @return array of title of songs
     */
    public static ArrayList<String> getAllTitleSongs( List<Song> songs ) {

        ArrayList<String> titleSongs = new ArrayList<String>();
        for ( Song s : songs ) {
            titleSongs.add( s.getTitle().trim() );
            titleSongs.add( s.getSecond().trim() );
        }
        return titleSongs;
    }

    /**
     * get all the editable songs
     * 
     * @param songs
     *            list of all the songs
     * @return list of editable songs
     */
    public static ArrayList<Song> getEditableSongs( List<Song> songs ) {
        ArrayList<Song> editableSongs = new ArrayList<Song>();
        if ( songs.isEmpty() || songs == null ) {
            return null;
        }
        for ( Song song : songs ) {
            if ( song.getNumber() >= FIRST_CUSTOM_NUMBER_SONG ) {
                editableSongs.add( song );
            }
        }
        return editableSongs;
    }

    /**
     * get all songs that the title or the second line contain a certain string
     * 
     * @param string
     *            to search into list of songs songs songs
     * @param songs
     *            list of all songs
     * @return list of song that contains the string in the title or the second
     *         line
     */
    public static ArrayList<Song> getSongsWithTitleContainingString( String string, List<Song> songs ) {
        ArrayList<Song> aSongs = new ArrayList<Song>();
        ArrayList<String> stringToSearch = new ArrayList<String>();
        String[] splitString = string.split( SPACE );
        boolean isASongNotContained = true;

        // get all words with more than 3 characters
        // for ( int i = 0; i < splitString.length; i++ ) {
        // if ( splitString[i].trim().length() > MIN_CHAR ) {
        // stringToSearch.add( splitString[i] );
        // }
        // }

        for ( String str : splitString ) {
            if ( str.trim().length() > MIN_CHAR ) {
                stringToSearch.add( str );
            }
        }

        // get the song that title or second line contains the whole string
        for ( Song s : songs ) {
            if ( s.getTitle().contains( string ) || s.getSecond().contains( string ) ) {
                aSongs.add( s );
            }
        }

        // get all songs that title or second line contain one of the string to
        // search
        for ( Song song : songs ) {
            for ( String str : stringToSearch ) {
                if ( song.getTitle().contains( str ) || song.getSecond().contains( str ) ) {
                    // don't duplicate songs
                    for ( Song asong : aSongs ) {
                        if ( asong.getNumber() == song.getNumber() ) {
                            // aSongs.add( song );
                            isASongNotContained &= false;
                        }
                    }
                    if ( isASongNotContained ) {
                        Log.i( TAG, "asongs size : " + aSongs.size() + " title : " + song.getTitle() );
                        aSongs.add( song );
                    } else {
                        isASongNotContained = true;
                    }
                }
            }
        }
        return aSongs;
    }

    /**
     * sort the songs alphabetically
     * 
     * @param songs
     *            all the songs
     * @return songs sorted alphabetically
     */
    public static ArrayList<Song> sortSongsAlphabetically( List<Song> songs ) {

        SparseArray<ArrayList<Song>> sortedSongs = new SparseArray<ArrayList<Song>>();
        ArrayList<Song> sortedSongsAlphabetically = new ArrayList<Song>();

        // put the alphabet in the SparseArray
        for ( int i = 0; i < ALPHABET.length; i++ ) {
            ArrayList<Song> aSong = new ArrayList<Song>();
            aSong.add( new Song( 0, 0, ALPHABET[i], "", "", "" ) );
            sortedSongs.put( i, aSong );
        }

        // gather the song that starts with the same letter
        for ( Song song : songs ) {
            for ( int i = 0; i < ALPHABET.length; i++ ) {
                if ( song.getTitle().startsWith( ALPHABET[i] ) ) {
                    ArrayList<Song> s = new ArrayList<Song>();
                    s = sortedSongs.get( i );
                    s.add( song );
                    sortedSongs.put( i, s );
                }
            }
        }

        // put the sorted lst in an arraylist
        for ( int i = 0; i < ALPHABET.length; i++ ) {
            for ( int k = 0; k < sortedSongs.get( i ).size(); k++ ) {
                sortedSongsAlphabetically.add( sortedSongs.get( i ).get( k ) );
                // Log.i( TAG, "sorted : " + sortedSongs.get( i ).get( k
                // ).getTitle() );
            }
        }

        return sortedSongsAlphabetically;
    }

    /**
     * get the current date time
     * 
     * @return the string of the current date time
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat( DATE_TIME_PATTERN, Locale.ENGLISH );
        return sdf.format( new Date() );
    }

    /**
     * get the file containing the song according to the language and the number
     * of teh song
     * 
     * @param number
     *            of the song
     * @param language
     *            of the song
     * @return resource of the file of the song
     */
    public static int getFileByNumber( int number, String language ) {
        int fileId = 0;
        switch ( number ) {
        case 1:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_1;
            }
            break;
        case 2:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_2;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_2;
            }
            break;
        case 3:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_3;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_3;
            }
            break;
        case 4:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_4;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_4;
            }
            break;
        case 5:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_5;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_5;
            }
            break;
        case 6:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_6;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_6;
            }
            break;
        case 7:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_7;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_7;
            }
            break;
        case 8:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_8;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_8;
            }
            break;
        case 9:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_9;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_9;
            }
            break;
        case 10:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_10;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_10;
            }
            break;
        case 11:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_11;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_11;
            }
            break;
        case 12:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_12;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_12;
            }
            break;
        case 13:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_13;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_13;
            }
            break;

        case 14:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_14;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_14;
            }
            break;
        case 15:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_15;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_15;
            }
            break;
        case 16:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_16;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_16;
            }
            break;
        case 17:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_17;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_17;
            }
            break;
        case 18:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_18;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_18;
            }
            break;
        case 19:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_19;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_19;
            }
            break;
        case 20:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_20;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_20;
            }
            break;
        case 21:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_21;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_21;
            }
            break;
        case 22:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_22;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_22;
            }
            break;
        case 23:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_23;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_23;
            }
            break;
        case 24:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_24;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_24;
            }
            break;
        case 25:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_25;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_25;
            }
            break;
        case 26:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_26;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_26;
            }
            break;
        case 27:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_27;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_27;
            }
            break;
        case 28:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_28;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_28;
            }
            break;
        case 29:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_29;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_29;
            }
            break;
        case 30:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_30;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_30;
            }
            break;
        case 31:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_31;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_31;
            }
            break;
        case 32:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_32;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_32;
            }
            break;
        case 33:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_33;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_33;
            }
            break;
        case 34:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_34;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_34;
            }
            break;
        case 35:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_35;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_35;
            }
            break;
        case 36:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_36;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_36;
            }
            break;
        case 37:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_37;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_37;
            }
            break;
        case 38:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_38;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_38;
            }
            break;
        case 39:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_39;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_39;
            }
            break;

        case 40:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_40;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_40;
            }
            break;
        case 41:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_41;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_41;
            }
            break;
        case 42:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_42;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_42;
            }
            break;
        case 43:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_43;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_43;
            }
            break;
        case 44:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_44;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_44;
            }
            break;
        case 45:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_45;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_45;
            }
            break;
        case 46:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_46;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_46;
            }
            break;
        case 47:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_47;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_47;
            }
            break;
        case 48:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_48;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_48;
            }
            break;
        case 49:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_49;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_49;
            }
            break;
        case 50:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_50;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_50;
            }
            break;
        case 51:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_51;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_51;
            }
            break;
        case 52:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_52;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_52;
            }
            break;
        case 53:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_53;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_53;
            }
            break;
        case 54:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_54;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_54;
            }
            break;
        case 55:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_55;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_55;
            }
            break;
        case 56:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_56;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_56;
            }
            break;
        case 57:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_57;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_57;
            }
            break;
        case 58:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_58;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_58;
            }
            break;
        case 59:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_59;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_59;
            }
            break;
        case 60:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_60;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_60;
            }
            break;
        case 61:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_61;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_61;
            }
            break;
        case 62:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_62;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_62;
            }
            break;
        case 63:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_63;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_63;
            }
            break;
        case 64:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_64;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_64;
            }
            break;
        case 65:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_65;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_65;
            }
            break;

        case 66:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_66;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_66;
            }
            break;
        case 67:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_67;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_67;
            }
            break;
        case 68:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_68;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_68;
            }
            break;
        case 69:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_69;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_69;
            }
            break;
        case 70:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_70;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_70;
            }
            break;
        case 71:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_71;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_71;
            }
            break;
        case 72:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_72;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_72;
            }
            break;
        case 73:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_73;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_73;
            }
            break;
        case 74:
            if ( language.equals( EN ) ) {
                fileId = R.raw.en_74;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_74;
            }
            break;

        default:
            fileId = -1;
            break;
        }
        return fileId;
    }
}
