package com.example.travelor.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.example.travelor.bean.Canteen;

import java.util.ArrayList;
import java.util.List;

public class CanteenDbOpenHelper extends SQLiteOpenHelper {

    /* Inner class that defines the table contents */
    public static class CanteenEntry implements BaseColumns {
        public static final String TABLE_NAME = "canteen";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_FLOW_STATE = "flowState";
        public static final String COLUMN_NAME_ANNOUNCEMENT = "announcement";
        public static final String COLUMN_NAME_IMAGE_URL = "imageUrl";
    }

    private static final String DB_NAME = "canteenSQLite.db";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + CanteenEntry.TABLE_NAME +" (\n" +
            "    name VARCHAR(255) PRIMARY KEY,\n" +
            "    flowState VARCHAR(255),\n" +
            "    announcement TEXT,\n" +
            "    imageUrl VARCHAR(255)\n" +
            ");\n";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public CanteenDbOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        fillContent4Test(db);
    }

    private void fillContent4Test(SQLiteDatabase db) {
        db.execSQL("INSERT INTO canteen (name, flowState, imageUrl, announcement) VALUES " +
                "('荷园一餐厅', '空闲', 'https://i.ibb.co/jzjTbGr/he1.jpg', '');");

        db.execSQL("INSERT INTO canteen (name, flowState, imageUrl, announcement) VALUES " +
                "('荷园二餐厅', '空闲', 'https://i.ibb.co/0fL2nrR/he2.jpg', '荷园二食堂将于五月一日休息，届时将暂停营业，请各位同学提前安排好就餐时间。');");

        db.execSQL("INSERT INTO canteen (name, flowState, imageUrl, announcement) VALUES " +
                "('聚英园餐厅', '空闲', 'https://i.ibb.co/2ZnPrCp/jyj.jpg', '');");

        db.execSQL("INSERT INTO canteen (name, flowState, imageUrl, announcement) VALUES " +
                "('风华园餐厅', '空闲', 'https://i.ibb.co/HBW5F7q/fhy.jpg', '');");
    }



    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 读取数据库中所有食堂数据
     * @param context
     * @return 数据库中所有食堂数据的副本
     */
    public static List<Canteen> readAll(Context context) {
        CanteenDbOpenHelper canteenDbOpenHelper = new CanteenDbOpenHelper(context);
        SQLiteDatabase canteenDb = canteenDbOpenHelper.getReadableDatabase();
        Cursor cursor = canteenDb.query(
                CanteenDbOpenHelper.CanteenEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        List<Canteen> result = new ArrayList<>();
        while(cursor.moveToNext()) {
            result.add(
                    new Canteen(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            );
        }
        cursor.close();
        return result;
    }

    /**
     * 更新数据库中餐厅的公告
     * @param context
     * @param canteenName 餐厅名称
     * @param announcement 新公告
     * @return 数据表受影响的行的序号
     */
    public static int updateAnnouncement(Context context, String canteenName, String announcement) {
        CanteenDbOpenHelper canteenDbOpenHelper = new CanteenDbOpenHelper(context);
        SQLiteDatabase canteenDb = canteenDbOpenHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(CanteenEntry.COLUMN_NAME_ANNOUNCEMENT, announcement);

        // Which row to update, based on the title
        String selection = CanteenEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { canteenName };

        return canteenDb.update(
                CanteenDbOpenHelper.CanteenEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

}
