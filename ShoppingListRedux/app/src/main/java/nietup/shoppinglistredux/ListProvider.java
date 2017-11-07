package nietup.shoppinglistredux;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by nietup on 11/7/2017.
 */

public class ListProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "nietup.shoppinglistredux";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_LIST = "list";
    public static final int LIST = 100;
    public static final int LIST_ID = 101;

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LIST).build();
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_LIST;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item" + CONTENT_URI + "/" + PATH_LIST;

    public static Uri buildListItemUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    private DBHelper dbHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_LIST, LIST);
        matcher.addURI(CONTENT_AUTHORITY, PATH_LIST + "/#", LIST_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor ret;
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case LIST:
                ret = db.query(DBHelper.TOBUY_TABLE_NAME, strings, s, strings1, null, null, s1);
            break;
            case LIST_ID:
                final long _id = ContentUris.parseId(uri);
                ret = db.query(DBHelper.TOBUY_TABLE_NAME, strings, DBHelper.TOBUY_COLUMN_ID + " = ?", new String[] {String.valueOf(_id)}, null, null, s1);
            break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        ret.setNotificationUri(getContext().getContentResolver(), uri);
        return ret;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case LIST:
                return CONTENT_TYPE;
            case LIST_ID:
                return CONTENT_ITEM_TYPE;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri ret;

        final long _id = db.insert(DBHelper.TOBUY_TABLE_NAME, null, contentValues);
        if (_id > 0) {
            ret = buildListItemUri(_id);
        }
        else {
            throw new UnsupportedOperationException("Unable to insert, URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ret;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final int rows = dbHelper.getWritableDatabase().delete(DBHelper.TOBUY_TABLE_NAME, s, strings);

        if (rows > 0 || s == null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int rows = db.update(DBHelper.TOBUY_TABLE_NAME, contentValues, s, strings);

        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }
}
