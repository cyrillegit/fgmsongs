package com.intelness.fgmsongs.beans;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.intelness.fgmsongs.db.DatabaseDAO;

/**
 * manage song into database (add, delete, update, select)
 * 
 * @author McCyrille
 * @version 1.0
 */
public class SongDAO extends DatabaseDAO {

    public static final String TABLE_SONG       = "song";
    public static final String KEY_ID           = "id";
    public static final String KEY_NUMBER       = "number";
    public static final String KEY_TITLE        = "title";
    public static final String KEY_SECOND       = "second";
    public static final String KEY_AUTHOR       = "author";
    public static final String KEY_CREATED_DATE = "createdDate";

    public SongDAO( Context context ) {
        super( context );
    }

    /**
     * add a song into db
     * 
     * @param song
     */
    public void addSong( Song song ) {
        SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put( KEY_NUMBER, song.getNumber() );
        values.put( KEY_TITLE, song.getTitle() );
        values.put( KEY_SECOND, song.getSecond() );
        values.put( KEY_AUTHOR, song.getAuthor() );
        values.put( KEY_CREATED_DATE, song.getCreatedDate().toString() );

        db.insert( TABLE_SONG, null, values );
    }

    /**
     * get a song knowing the id
     * 
     * @param id
     *            of the song
     * @return the song
     */
    public Song getSongById( int id ) {

        SQLiteDatabase db = open();

        Cursor cursor = db.query( TABLE_SONG,
                new String[] { KEY_ID,
                        KEY_NUMBER,
                        KEY_TITLE,
                        KEY_SECOND,
                        KEY_AUTHOR,
                        KEY_CREATED_DATE },
                KEY_ID + "=?",
                new String[] { String.valueOf( id ) },
                null,
                null,
                null,
                null );

        if ( cursor != null ) {
            cursor.moveToFirst();
        }

        Song song = new Song( Integer.parseInt( cursor.getString( 0 ) ),
                Integer.parseInt( cursor.getString( 1 ) ),
                cursor.getString( 2 ),
                cursor.getString( 3 ),
                cursor.getString( 4 ),
                cursor.getString( 5 ) );

        cursor.close();
        return song;
    }

    /**
     * get a song knowing the number of the song
     * 
     * @param number
     *            of the song
     * @return the song
     */
    public Song getSongByNumber( int number ) {

        SQLiteDatabase db = open();

        Cursor cursor = db.query( TABLE_SONG,
                new String[] { KEY_ID,
                        KEY_NUMBER,
                        KEY_TITLE,
                        KEY_SECOND,
                        KEY_AUTHOR,
                        KEY_CREATED_DATE },
                KEY_NUMBER + "=?",
                new String[] { String.valueOf( number ) },
                null,
                null,
                null,
                null );

        if ( cursor != null ) {
            cursor.moveToFirst();
        }

        Song song = new Song( Integer.parseInt( cursor.getString( 0 ) ),
                Integer.parseInt( cursor.getString( 1 ) ),
                cursor.getString( 2 ),
                cursor.getString( 3 ),
                cursor.getString( 4 ),
                cursor.getString( 5 ) );

        cursor.close();
        return song;
    }

    /**
     * get all the songs in the db
     * 
     * @return a list of songs
     */
    public List<Song> getAllSongs() {
        List<Song> songList = new ArrayList<Song>();

        String selectQuery = "SELECT * FROM " + TABLE_SONG;
        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery( selectQuery, null );

        // Looping through all row and adding song to list
        if ( cursor.moveToFirst() ) {
            do {
                Song song = new Song();
                song.setId( Integer.parseInt( cursor.getString( 0 ) ) );
                song.setNumber( Integer.parseInt( cursor.getString( 1 ) ) );
                song.setTitle( cursor.getString( 2 ) );
                song.setSecond( cursor.getString( 3 ) );
                song.setAuthor( cursor.getString( 4 ) );
                song.setCreatedDate( cursor.getString( 5 ) );

                // Adding songs to list
                songList.add( song );
            } while ( cursor.moveToNext() );
        }

        return songList;
    }

    /**
     * get the number of song in the db
     * 
     * @return the number of songs
     */
    public int getNumberOfSongs() {
        SQLiteDatabase db = open();
        if ( db == null ) {
            return -1;
        }
        int numRows = (int) DatabaseUtils.queryNumEntries( db, TABLE_SONG );
        return numRows;
    }

    /**
     * update a song into db
     * 
     * @param song
     *            to update into the db
     * @return number of rows affected by the update
     */
    public int updateSong( Song song ) {
        SQLiteDatabase db = open();

        ContentValues values = new ContentValues();
        values.put( KEY_NUMBER, song.getNumber() );
        values.put( KEY_TITLE, song.getTitle() );
        values.put( KEY_AUTHOR, song.getAuthor() );
        values.put( KEY_CREATED_DATE, song.getCreatedDate() );

        return db.update( TABLE_SONG, values, KEY_ID + " = ?",
                new String[] { String.valueOf( song.getId() ) } );
    }

    /**
     * delete a song into db
     * 
     * @param song
     *            to delete
     */
    public void deleteDevinette( Song song ) {
        SQLiteDatabase db = open();
        db.delete( TABLE_SONG, KEY_ID + " = ?", new String[] { String.valueOf( song.getId() ) } );
    }
}
