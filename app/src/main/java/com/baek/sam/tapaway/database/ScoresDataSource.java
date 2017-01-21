package com.baek.sam.tapaway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Sam Baek on 1/21/2017.
 */

public class ScoresDataSource {
    private SQLiteDatabase database;
    private ScoreSQLiteHelper dbHelper;
    private String[] allColumns = {ScoreSQLiteHelper.COLUMN_NAME, ScoreSQLiteHelper.COLUMN_SCORE};

    public ScoresDataSource(Context context) {
        dbHelper = new ScoreSQLiteHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Score createScore(String name, int score) {
        ContentValues values = new ContentValues();
        values.put(ScoreSQLiteHelper.COLUMN_NAME, name);
        values.put(ScoreSQLiteHelper.COLUMN_SCORE, score);
        long insertId = database.insert(ScoreSQLiteHelper.TABLE_SCORES, null, values);
        return null;
    }
}
