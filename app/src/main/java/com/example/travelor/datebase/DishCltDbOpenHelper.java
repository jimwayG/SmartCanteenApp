package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelor.bean.Dish;


import java.util.ArrayList;
import java.util.List;

public class DishCltDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DishCollectSQLite.db";
    private static final String TABLE_NAME_NOTE = "Dish_Collect";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, " +
            "name text UNIQUE, window text, location text, images text, introduce text, price text, same text, category text)";


    public DishCltDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion <= 2){//当数据库版本小于版本2时，就要升级下面的所有字段
            db.execSQL("drop table if exists " + TABLE_NAME_NOTE);
            onCreate(db);
        }
    }

    public long insertData(Dish dish) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", dish.getName());
        values.put("window", dish.getWindow());
        values.put("location", dish.getLocation());
        values.put("images", dish.getImages());
        values.put("introduce", dish.getIntroduce());
        values.put("price", dish.getPrice());
        values.put("same", dish.getSame());
        values.put("category", dish.getCategory());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    public int deleteFromDbByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_NOTE, "name = ?", new String[]{name}); // 创建包含单个元素的字符串数组
    }

    @SuppressLint("Range")
    public List<Dish> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<Dish> dishList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Dish dish = new Dish();
                dish.setName(cursor.getString(cursor.getColumnIndex("name")));
                dish.setWindow(cursor.getString(cursor.getColumnIndex("window")));
                dish.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                dish.setImages(cursor.getString(cursor.getColumnIndex("images")));
                dish.setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
                dish.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                dish.setSame(cursor.getString(cursor.getColumnIndex("same")));
                dish.setCategory(cursor.getString(cursor.getColumnIndex("category")));


                dishList.add(dish);
            }
            cursor.close();
        }

        return dishList;
    }

}
