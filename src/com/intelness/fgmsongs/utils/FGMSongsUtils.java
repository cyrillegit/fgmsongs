package com.intelness.fgmsongs.utils;

import com.intelness.fgmsongs.R;

public class FGMSongsUtils {

    public static final String FR    = "fr";
    public static final String EN    = "en";
    public static final String SPLIT = "##";

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
                newString += splitString[i];
            } else {
                newString += splitString[i] + System.getProperty( "line.separator" );
            }
        }
        return newString;
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
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_1;
            }
            break;
        case 2:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_2;
            }
            break;
        case 3:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_3;
            }
            break;
        case 4:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_4;
            }
            break;
        case 5:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_5;
            }
            break;
        case 6:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_6;
            }
            break;
        case 7:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_7;
            }
            break;
        case 8:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_8;
            }
            break;
        case 9:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_9;
            }
            break;
        case 10:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_10;
            }
            break;
        case 11:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_1;
            }
            break;
        case 12:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_1;
            }
            break;
        case 13:
            if ( language.equals( EN ) ) {
                // fileId = R.raw.en_1;
            } else if ( language.equals( FR ) ) {
                fileId = R.raw.fr_1;
            }
            break;

        default:
            fileId = -1;
            break;
        }
        return fileId;
    }
}
