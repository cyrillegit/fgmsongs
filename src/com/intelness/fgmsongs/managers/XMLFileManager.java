package com.intelness.fgmsongs.managers;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.SparseArray;

import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * manager xml file , parse song, parse verse, store in internal memory, format
 * 
 * @author McCyrille
 * @version 1.0
 * @since 2015-01-25
 */
public class XMLFileManager {

    private static final String NUMBER       = "number";
    private static final String TITLE        = "title";
    private static final String SECOND       = "second";
    private static final String AUTHOR       = "author";
    private static final String CREATED_DATE = "created_date";
    // private static final String TAG = "XMLFileManager";

    public static final String  SONG         = "song";
    public static final String  VERSE        = "verse";

    private List<Song>          songs;
    private Song                song;
    private List<Verse>         verses;
    private Verse               verse;
    private String              text;
    private Context             context;

    // private SparseArray<Verse> verses;

    public XMLFileManager( Context context ) {
        this.context = context;
        // this.verses = verses;
    }

    public XMLFileManager() {

    }

    public XMLFileManager( String string ) {
        if ( string.equals( SONG ) ) {
            songs = new ArrayList<Song>();
        } else if ( string.equals( VERSE ) ) {
            verses = new ArrayList<Verse>();
        }
    }

    /**
     * format string into xml file alike
     * 
     * @return string xml-formatted
     */
    public String format( SparseArray<Verse> verses ) {
        // SparseArray<Verse> verses = verses;
        String xmlString = "";
        if ( verses.size() <= 0 ) {
            return xmlString;
        }
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element songElement = document.createElement( SONG );
            document.appendChild( songElement );

            for ( int i = 0; i < verses.size(); i++ ) {
                Element verseElement = document.createElement( VERSE );
                songElement.appendChild( verseElement );

                verseElement.appendChild( document.createTextNode( FGMSongsUtils.StringsReplaceNewLine( verses.get( i )
                        .getStrophe() ) ) );
            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Properties outFormat = new Properties();
            outFormat.setProperty( OutputKeys.INDENT, "yes" );
            outFormat.setProperty( OutputKeys.METHOD, "xml" );
            outFormat.setProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
            outFormat.setProperty( OutputKeys.VERSION, "1.0" );
            outFormat.setProperty( OutputKeys.ENCODING, "UTF-8" );
            transformer.setOutputProperties( outFormat );
            DOMSource domSource = new DOMSource( document.getDocumentElement() );
            OutputStream output = new ByteArrayOutputStream();
            StreamResult result = new StreamResult( output );
            transformer.transform( domSource, result );
            xmlString = output.toString();

        } catch ( ParserConfigurationException e ) {
        } catch ( TransformerConfigurationException e ) {
        } catch ( TransformerException e ) {

            e.printStackTrace();
        }
        return xmlString;
    }

    /**
     * store xml file in internal storage
     * 
     * @param filename
     *            name of the file
     * @param xmlString
     *            content of the file
     */
    public void store( String filename, String xmlString ) {
        try {
            FileOutputStream fileout = context.openFileOutput( filename, Context.MODE_PRIVATE );
            OutputStreamWriter outputWriter = new OutputStreamWriter( fileout );
            outputWriter.write( xmlString );
            outputWriter.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * get infos about the song
     * 
     * @param is
     *            the file input stream
     * @return infos about the song
     */
    public List<Song> parseSong( InputStream is ) {
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
                    // Log.i( TAG, "text : " + text );

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

    /**
     * get the verses of a song
     * 
     * @param is
     *            the file input stream
     * @return list of verses of a song
     */
    public List<Verse> parseVerse( InputStream is ) {
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
                    if ( tagName.equalsIgnoreCase( VERSE ) ) {
                        // // create a new instance of verse
                        verse = new Verse();
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if ( tagName.equalsIgnoreCase( VERSE ) ) {
                        // add verse object to the list
                        verse.setStrophe( text );
                        verses.add( verse );
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
        return verses;
    }
}
