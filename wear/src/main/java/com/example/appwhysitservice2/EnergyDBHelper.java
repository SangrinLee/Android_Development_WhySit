package com.example.appwhysitservice2;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class EnergyDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    public static final int DATABASE_VERSION = 22;
    public static final String DATABASE_NAME = "energy.db";

    private static final String SQL_CREATE_ENTRIES = "Create table whysit (mac string, time string, activity string, accelx float, accely float, accelz float, gyrox float, gyroy float, gyroz float, gravx float, gravy float, gravz float, magx float, magy float, magz float, sound float, orient_pitch float, orient_roll float, orient_yaw float, rotationx float, rotationy float, rotationz float, linear_acc float, step_detect float);";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS whysit";
    private static EnergyDBHelper instance = null;

    public static EnergyDBHelper getInstance(Context context) {
        if (instance == null) instance = new EnergyDBHelper(context);
        return instance;
    }

    public class EnergyEntry implements BaseColumns {
        public static final String TABLE_NAME = "entries";
        public static final String COLUMN_NAME_ENERGY = "energy";
        public static final String COLUMN_NAME_TIME = "date";
    }

    private EnergyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static void enterEnergy(String id, String time, String type, String a1, String a2, String a3, String b1, String b2, String b3, String c1, String c2, String c3, String d1, String d2, String d3, String aud, String e1, String e2, String e3, String f1, String f2, String f3, String g1, String h1, Context context) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        String sql = "INSERT INTO whysit"
                + " VALUES (\"" + id + "\",\"" + time + "\",\"" + type + "\"," + a1 + "," + a2 + "," + a3 + "," + b1 + "," + b2 + "," + b3 + "," + c1 + "," + c2 + "," + c3 + "," + d1 + "," + d2 + "," + d3 + "," + aud
                + "," + e1 + "," + e2 + "," + e3 + "," + f1 + "," + f2 + "," + f3 + "," + g1 + "," + h1 + ");";
        db.execSQL(sql);

    }

    public static void deleteEnergy(Context context) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        String sql = "delete from whysit;";
        Log.v("QUERY = ", sql);
        db.execSQL(sql);
    }

    public static void addEnergy(Context context) {

        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        String sql = "Create table whysit (mac string, time string, activity string, accelx float, accely float, accelz float, gyrox float, gyroy float, gyroz float, gravx float, gravy float, gravz float, magx float, magy float, magz float, sound float, orient_pitch float, orient_roll float, orient_yaw float, rotationx float, rotationy float, rotationz float, linear_acc float, step_detect float);";
        db.execSQL(sql);
    }


    public static Cursor getLatest60Entries(Context context) {
        // TODO: query for the most recent 60 entries and return the cursor
        SQLiteDatabase db = getInstance(context).getReadableDatabase();

        String sql = "select * from whysit;";

        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
}
