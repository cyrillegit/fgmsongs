package com.intelness.fgmsongs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDAO {

    protected SQLiteDatabase  db              = null;
    protected DatabaseHandler databaseHandler = null;

    public DatabaseDAO( Context context ) {
        this.databaseHandler = new DatabaseHandler( context, DatabaseHandler.DATABASE_NAME, null,
                DatabaseHandler.DATABASE_VERSION );
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public SQLiteDatabase open() {
        db = databaseHandler.getWritableDatabase();
        return db;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
