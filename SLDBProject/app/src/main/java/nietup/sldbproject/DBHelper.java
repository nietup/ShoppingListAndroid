package nietup.sldbproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by nietup on 11/2/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sl.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TOBUY_TABLE_NAME = "to_buy";
    public static final String TOBUY_COLUMN_ID = "id";
    public static final String TOBUY_COLUMN_NAME = "name";
    public static final String TOBUY_COLUMN_CATEGORY = "category";
    public static final String TOBUY_COLUMN_DEADLINE = "deadline";
    public static final String TOBUY_COLUMN_PRICE = "price";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TOBUY_TABLE_NAME + "(" +
                        TOBUY_COLUMN_ID + " integer primary key, " +
                        TOBUY_COLUMN_NAME + " text, " +
                        TOBUY_COLUMN_CATEGORY + " text, " +
                        TOBUY_COLUMN_DEADLINE + " date, " +
                        TOBUY_COLUMN_PRICE + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOBUY_TABLE_NAME);
        onCreate(db);
    }

    public void insertItem(String name, String category, String deadline, int price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TOBUY_COLUMN_NAME, name);
        cv.put(TOBUY_COLUMN_CATEGORY, category);
        cv.put(TOBUY_COLUMN_DEADLINE, deadline);
        cv.put(TOBUY_COLUMN_PRICE, price);
        db.insert(TOBUY_TABLE_NAME, null, cv);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("select * from " + TOBUY_TABLE_NAME + " where id=" + id, null);
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();

        return (int) DatabaseUtils.queryNumEntries(db, TOBUY_TABLE_NAME);
    }

//    public boolean updateItem(int id, String name, String category, String deadline, int price) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

    public Integer deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TOBUY_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllItems() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery("select * from " + TOBUY_TABLE_NAME, null);
//        res.moveToFirst();

//        while(!res.isAfterLast()) {
        while (res.moveToNext()) {
            array_list.add(res.getString(res.getColumnIndex(TOBUY_COLUMN_NAME)));
//            res.moveToNext();
        }

        return array_list;
    }
}
