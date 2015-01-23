package com.intelness.fgmsongs.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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

import android.util.SparseArray;

import com.intelness.fgmsongs.beans.Verse;

/**
 * xml formatting of a string
 * 
 * @author McCyrille
 * @version 1.0
 */
public class WriteXMLFile {

    public static final String SONG  = "song";
    public static final String VERSE = "verse";
    private SparseArray<Verse> verses;

    public WriteXMLFile( SparseArray<Verse> verses ) {
        this.verses = verses;
    }

    public WriteXMLFile() {

    }

    /**
     * format string xml alike
     * 
     * @return string xml-formatted
     */
    public String write() {
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
}
