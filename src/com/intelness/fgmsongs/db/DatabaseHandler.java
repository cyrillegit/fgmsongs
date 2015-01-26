package com.intelness.fgmsongs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intelness.fgmsongs.beans.SongDAO;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG                  = "DatabaseHandler";

    public static final int     DATABASE_VERSION     = 1;
    public static final String  DATABASE_NAME        = "Song.db";

    public static final String  SONG_TABLE_ENTITIES  = SongDAO.KEY_ID + " INTEGER PRIMARY KEY,"
                                                             + SongDAO.KEY_NUMBER + " TEXT,"
                                                             + SongDAO.KEY_TITLE + " TEXT,"
                                                             + SongDAO.KEY_SECOND + " TEXT,"
                                                             + SongDAO.KEY_AUTHOR + " TEXT,"
                                                             + SongDAO.KEY_CREATED_DATE + " TEXT";

    public static final String  CREATE_SONG_TABLE_EN = "CREATE TABLE " + SongDAO.TABLE_SONG[0] + "("
                                                             + SONG_TABLE_ENTITIES + ")";

    public static final String  CREATE_SONG_TABLE_FR = "CREATE TABLE " + SongDAO.TABLE_SONG[1] + "("
                                                             + SONG_TABLE_ENTITIES + ")";

    public static final String  DROP_SONG_TABLE_EN   = "DROP TABLE IF EXISTS " + SongDAO.TABLE_SONG[0];
    public static final String  DROP_SONG_TABLE_FR   = "DROP TABLE IF EXISTS " + SongDAO.TABLE_SONG[1];

    public DatabaseHandler( Context context, String dbName, CursorFactory factory, int version ) {
        super( context, dbName, null, version );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.i( TAG, CREATE_SONG_TABLE_EN );
        Log.i( TAG, CREATE_SONG_TABLE_FR );

        db.execSQL( CREATE_SONG_TABLE_EN );
        db.execSQL( CREATE_SONG_TABLE_FR );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( DROP_SONG_TABLE_EN );
        db.execSQL( DROP_SONG_TABLE_FR );
        onCreate( db );
    }

}
