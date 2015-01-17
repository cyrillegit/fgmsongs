package com.intelness.fgmsongs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intelness.fgmsongs.beans.SongDAO;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG               = "DatabaseHandler";

    public static final int     DATABASE_VERSION  = 1;
    public static final String  DATABASE_NAME     = "SongDB";
    public static final String  CREATE_SONG_TABLE = "CREATE TABLE " + SongDAO.TABLE_SONG + "("
                                                          + SongDAO.KEY_ID + " INTEGER PRIMARY KEY,"
                                                          + SongDAO.KEY_NUMBER + " TEXT,"
                                                          + SongDAO.KEY_TITLE + " TEXT,"
                                                          + SongDAO.KEY_SECOND + " TEXT,"
                                                          + SongDAO.KEY_AUTHOR + " TEXT,"
                                                          + SongDAO.KEY_CREATED_DATE + " TEXT"
                                                          + ")";
    public static final String  DROP_SONG_TABLE   = "DROP TABLE IF EXISTS " + SongDAO.TABLE_SONG;

    public DatabaseHandler( Context context, String dbName, CursorFactory factory, int version ) {
        super( context, dbName, null, version );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.i( TAG, CREATE_SONG_TABLE );
        db.execSQL( CREATE_SONG_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( DROP_SONG_TABLE );
        onCreate( db );
    }

}
