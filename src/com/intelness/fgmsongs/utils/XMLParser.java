package com.intelness.fgmsongs.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.intelness.fgmsongs.beans.Song;

public class XMLParser {

    private static final String SONG         = "song";
    private static final String NUMBER       = "number";
    private static final String TITLE        = "title";
    private static final String SECOND       = "second";
    private static final String AUTHOR       = "author";
    private static final String CREATED_DATE = "created_date";

    private List<Song>          songs;
    private Song                song;
    private String              text;

    public XMLParser() {
        songs = new ArrayList<Song>();
    }

    public List<Song> parse( InputStream is ) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware( true );
            parser = factory.newPullParser();

            parser.setInput( is, null );

            int eventType = parser.getEventType();
            while ( eventType != XmlPullParser.END_DOCUMENT ) {
                String tagName = parser.getName();

                switch ( eventType ) {
                case XmlPullParser.START_TAG:
                    if ( tagName.equalsIgnoreCase( SONG ) ) {
                        // create a new instance of song
                        song = new Song();
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if ( tagName.equalsIgnoreCase( SONG ) ) {
                        // add song object to the list
                        songs.add( song );
                    } else if ( tagName.equalsIgnoreCase( NUMBER ) ) {
                        song.setNumber( Integer.parseInt( text ) );
                    } else if ( tagName.equalsIgnoreCase( TITLE ) ) {
                        song.setTitle( text );
                    } else if ( tagName.equalsIgnoreCase( SECOND ) ) {
                        song.setSecond( text );
                    } else if ( tagName.equalsIgnoreCase( AUTHOR ) ) {
                        song.setAuthor( text );
                    } else if ( tagName.equalsIgnoreCase( CREATED_DATE ) ) {
                        song.setCreatedDate( text );
                    }
                    break;
                default:
                    break;
                }
                eventType = parser.next();
            }
        } catch ( XmlPullParserException e ) {
            e.printStackTrace();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return songs;
    }

}
