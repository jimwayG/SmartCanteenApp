package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.travelor.bean.Dish;

import java.util.ArrayList;
import java.util.List;
public class DishDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dishSQLite.db";
    private static final String TABLE_NAME_NOTE = "dishes";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, " +
            "name text, window text, location text, images text, introduce text, price text, same text, category text)";


    public DishDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
        //initDb();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        initDb(db);
    }

    private void initDb(SQLiteDatabase db) {
        String[] names = {"韭菜鸡蛋水饺", "汉堡+薯条可乐", "麻婆豆腐", "麻辣小面",
                "芹菜猪肉水饺", "猪肉大葱煎饺", "铁板炒饺子", "云吞面", "虾仁水饺",
                "安格斯牛肉堡", "奥尔良烤肉堡套餐", "至臻全虾堡", "正新汉堡+鸡腿", "藤椒鸡腿中国汉堡",
                "凉拌西兰花", "鱼香茄子", "炒杏鲍菇", "香菇土鸡汤", "卤鸡腿",
                "葱油拌面", "香辣牛肉面", "招牌红烧牛肉米粉", "老坛酸菜米粉", "茄汁酸辣米粉"};
        String[] introduces = {"咸鲜口味，主要食材有：面粉韭菜鸡蛋食用油盐。\n" + "营养成分（500g）：\n" + "蛋白质：约为35-40克\n" + "脂肪：约为15-20克\n" + "能量：约为400-500千卡\n",
                "咸香口味，主要食材有：面包、肉饼、生菜、番茄、奶酪和特制酱料，马铃薯切片\n" + "营养成分（500g）：\n" + "蛋白质：约25克\n" + "脂肪：大约含有35克脂肪\n" + "能量：约950卡路里\n",
                "麻辣口味，主要食材有：豆腐花椒辣椒葱姜蒜猪肉食用油盐。\n" + "营养成分：\n" + "蛋白质：约25-30克\n" + "脂肪：约20-25克）\n" + "能量（千卡）：约300-400千卡\n",
                "麻辣口味，主要食材有：面条、辣椒油、花椒、葱花、香菜等\n" + "营养成分（500g）：\n" + "蛋白质：约10-15克\n" + "脂肪：约10-15克\n" + "能量：约300-400卡路里\n",
                "咸鲜口味，主要食材有：面粉芹菜猪肉食用油盐。\n" + "营养成分（500g）：\n" + "蛋白质：大约25-30克\n" + "脂肪：约10-15克\n" + "能量：大约200-250千卡\n" + "价格：11.99\n",
                "葱香口味，主要食材有：生抽面粉鸡蛋猪肉大葱食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约60-70克\n" +
                        "脂肪：约30-40克（具体取决于猪肉和饺子皮的脂肪含量）\n" +
                        "能量（卡路里，千卡）：约600-700千卡（因为煎饺通常含有一定量的碳水化合物，它们在体内燃烧会产生能量。\n",
                "麻辣咸香口味，主要食材有：生抽面粉鸡蛋猪肉大葱食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约40-50克（取决于饺子内馅的蛋白质含量）\n" +
                        "脂肪：约20-30克（具体取决于炒饺子所使用的油量）\n" +
                        "能量（卡路里，千卡）：约400-500千卡（炒饺子相对于煎饺的能量可能较低，因为不需要额外的油炸）\n",
                "咸鲜口味，主要食材有：面粉生抽鸡蛋猪肉虾仁食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约30-40克（取决于云吞面中面条和馅料的蛋白质含量）\n" +
                        "脂肪：约10-15克（具体取决于配料和烹饪方式）\n" +
                        "能量（卡路里，千卡）：约400-500千卡（取决于面条的能量含量和馅料的烹饪方式）\n",
                "咸鲜口味，主要食材有：面粉生抽鸡蛋猪肉虾仁食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约30-40克（取决于虾仁和面粉的蛋白质含量）\n" +
                        "脂肪：约15-20克（取决于虾仁的脂肪含量以及馄饨皮的油脂含量）\n" +
                        "能量（卡路里，千卡）：约400-500千卡（取决于虾仁和馄饨皮的能量含量）\n",
                "醇香口味，主要食材有： 安格斯牛肉饼、面包、生菜、番茄等\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约25-30克\n" +
                        "脂肪：约15-20克\n" +
                        "能量：能量约350-500卡路里\n",
                "咸香口味，主要食材有：烤肉、鸡排、香鸡柳、面包、生菜等\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约60-80克\n" +
                        "脂肪：约40-50克\n" +
                        "能量：超过1000卡路里\n",
                "鲜美酥脆，主要食材有：全虾堡、脆皮鸡腿、面包等。\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约35-45克\n" +
                        "脂肪：约25-35克\n" +
                        "能量：约700-900卡路里\n",
                "咸香口味，主要食材有：汉堡、炸鸡腿、面包等\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约20-30克\n" +
                        "脂肪：约20-30克\n" +
                        "能量：约600-800卡路里\n",
                "麻辣口味，主要食材有：藤椒鸡腿、面包、蔬菜等\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约30-40克\n" +
                        "脂肪：约15-25克\n" +
                        "能量：约500-700卡路里\n",
                "咸香口味，主要食材有：糖醋西兰花胡萝卜食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约3-4克\n" +
                        "脂肪：约0.5-1克\n" +
                        "能量：约20-30千卡\n",
                "鱼香口味，主要食材有：茄子糖醋葱姜蒜食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约5-8克\n" +
                        "脂肪：约5-8克\n" +
                        "能量：约100-150千卡\n",
                "咸香口味，主要食材有：杏鲍菇辣椒食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约15-20克\n" +
                        "脂肪：约5-8克\n" +
                        "能量：约100-150千卡\n",
                "咸鲜口味，主要食材有：香菇土鸡食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约20-25克\n" +
                        "脂肪：约5-10克\n" +
                        "能量：约150-200千卡\n",
                "咸鲜口味，主要食材有：鸡腿生抽食用油盐。\n" +
                        "营养成分：\n" +
                        "蛋白质：约20-25克\n" +
                        "脂肪：约10-15克\n" +
                        "能量：约200-250千卡\n",
                "清淡，葱油香，主要食材有：面条、葱花、酱油、香油等\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约8-12克\n" +
                        "脂肪：约10-15克\n" +
                        "能量：约250-350卡路里\n",
                "香辣，牛肉的鲜美与辣椒的刺激口味，主要食材有：面条、牛肉、辣椒、香料\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约25-35克\n" +
                        "脂肪：约15-20克\n" +
                        "能量：约450-600卡路里\n",
                "醇厚鲜香，主要食材有：米粉、牛肉、红烧酱汁\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约20-30克\n" +
                        "脂肪：约20-30克\n" +
                        "能量：约500-650卡路里\n",
                "酸辣，酸菜的酸爽与辣味，主要食材有：米粉、酸菜、辣椒\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约15-20克\n" +
                        "脂肪：约10-15克\n" +
                        "能量：约400-500卡路里\n",
                "咸香口味，主要食材有：面包、肉饼、生菜、番茄、奶酪和特制酱料，马铃薯切片\n" +
                        "营养成分（500g）：\n" +
                        "蛋白质：约15-20克\n" +
                        "脂肪：约10-15克\n" +
                        "能量：约450-550卡路里\n"};
        String[] locations = {"荷园一餐厅一层", "荷园一餐厅一层", "荷园二餐厅一层", "荷园一餐厅一层",
                "荷园一餐厅一层","荷园一餐厅一层", "荷园一餐厅一层", "荷园一餐厅二层", "荷园一餐厅二层",
                "荷园一餐厅一层", "荷园一餐厅一层", "荷园一餐厅二层", "荷园一餐厅二层", "荷园二餐厅一层",
                "荷园二餐厅一层","荷园二餐厅一层", "荷园二餐厅一层", "荷园二餐厅二层", "荷园二餐厅二层",
                "柳园二餐厅二层","柳园二餐厅二层", "柳园二餐厅二层", "柳园一餐厅一层", "柳园二餐厅二层"};
        String[] images = {"jzhd1#jcjd1#jcjd2", "hbst1", "kcbd1", "mfmg1",
                "jzhd2", "jzhd3", "jzhd4", "jzhd5", "jzhd6",
                "hbst2", "hbst3", "hbst4", "hbst5", "hbst6",
                "kcbd2", "kcbd3", "kcbd4", "kcbd5", "kcbd6",
                "mfmg2", "mfmg3", "mfmg4", "mfmg5", "mfmg6"};
        String[] window = {"东北饺子馆12号", "李斯麦炸鸡汉堡 11号", "精品小炒19号", "重庆小面 9号",
                "东北饺子馆12号","蛋蛋韩式煎饺17号", "蛋蛋韩式煎饺17号", "小胖墩馄饨水饺22号", "小胖墩馄饨水饺22号",
                "美树炸鸡汉堡4号", "校堡王炸鸡汉堡 3号", "美树炸鸡汉堡4号", "正新鸡排—汉堡 21号", "学长中国汉堡 17号",
                "精品小炒19号", "精品小炒19号", "沙县小吃11号", "沙县小吃11号", "沙县小吃11号",
                "热干面花甲 10号", "米粉牛肉面 11号", "湘妹子湖南米粉牛肉面 12号", "湖南米粉牛肉面 13号", "阿利茄汁面 13号"};
        String[] same = {"韭菜鸡蛋水饺", "汉堡+薯条可乐", "麻婆豆腐", "麻辣小面",
                "芹菜猪肉水饺", "猪肉大葱煎饺", "铁板炒饺子", "云吞面", "虾仁水饺",
                "安格斯牛肉堡", "奥尔良烤肉堡套餐", "至臻全虾堡", "正新汉堡+鸡腿", "藤椒鸡腿中国汉堡",
                "凉拌西兰花", "鱼香茄子", "炒杏鲍菇", "香菇土鸡汤", "卤鸡腿",
                "葱油拌面", "香辣牛肉面", "招牌红烧牛肉米粉", "老坛酸菜米粉", "茄汁酸辣米粉"};
        String[] prices = {"¥10.99", "¥17.49", "¥7.81", "¥15.38",
                "¥11.99","¥13.66", "¥15", "¥16.6", "¥16.5",
                "¥29.9", "¥17", "¥17.4", "¥15", "¥10.6",
                "¥8.72", "¥5.61", "¥2.93", "¥9.81", "4.50",
                "¥14.88", "¥14.49", "¥19.4", "¥12", "¥17.9"};
        String[] category = {"饺子馄饨", "汉堡薯条", "快餐便当", "米粉面馆",
                "饺子馄饨","饺子馄饨","饺子馄饨","饺子馄饨","饺子馄饨",
                "汉堡薯条", "汉堡薯条", "汉堡薯条", "汉堡薯条", "汉堡薯条",
                "快餐便当", "快餐便当", "快餐便当", "快餐便当", "快餐便当",
                "米粉面馆","米粉面馆","米粉面馆","米粉面馆","米粉面馆"};

        for(int i = 0; i < names.length; i++) {
            Dish oneDish = new Dish();
            oneDish.setName(names[i]);
            oneDish.setIntroduce(introduces[i]);
            oneDish.setLocation(locations[i]);
            oneDish.setImages(images[i]);
            oneDish.setWindow(window[i]);
            oneDish.setSame(same[i]);
            oneDish.setPrice(prices[i]);
            oneDish.setCategory(category[i]);

            insertData(db,oneDish);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion <= 7){//当数据库版本小于版本2时，就要升级下面的所有字段
            db.execSQL("drop table if exists " + TABLE_NAME_NOTE);
            onCreate(db);
        }
    }

    public long insertData(SQLiteDatabase db, Dish dish) {
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

    @SuppressLint("Range")
    public List<Dish> queryFromDbByName(String name) {
        if (TextUtils.isEmpty(name)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Dish> dishList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "name like ?", new String[]{"%" + name + "%"}, null, null, null);
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

    @SuppressLint("Range")
    public List<Dish> queryFromDbByCategory(String Category) {
        if (TextUtils.isEmpty(Category)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Dish> dishList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "category = ?", new String[]{Category}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
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
            } while (cursor.moveToNext());
            cursor.close();
        }

        return dishList;
    }

}