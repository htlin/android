package org.easycomm.db;

import org.easycomm.db.VocabContract.VocabEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VocabDbHelper extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + VocabEntry.TABLE_NAME + " (" +
	    		VocabEntry._ID + " INTEGER PRIMARY KEY," +
	    		VocabEntry.COLUMN_NAME_IS_DIR + INTEGER_TYPE + COMMA_SEP +
	    		VocabEntry.COLUMN_NAME_PARENT_ID + INTEGER_TYPE + COMMA_SEP +
	    		VocabEntry.COLUMN_NAME_DISPLAY_TEXT + TEXT_TYPE + COMMA_SEP +
	    		VocabEntry.COLUMN_NAME_SPEECH_TEXT + TEXT_TYPE + COMMA_SEP +
	    		VocabEntry.COLUMN_NAME_IMAGE_FILE + TEXT_TYPE + COMMA_SEP +
	    		VocabEntry.COLUMN_NAME_POS + INTEGER_TYPE + COMMA_SEP +
	    		"FOREIGN KEY(" + VocabEntry.COLUMN_NAME_PARENT_ID + ") REFERENCES " + VocabEntry.TABLE_NAME + "(" + VocabEntry._ID + ")" +
	    " )";
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + VocabEntry.TABLE_NAME;
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public VocabDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	//TODO
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    
}
