package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BuyList.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDB = "CREATE TABLE buylist("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,"+
                "category TEXT,"+
                "describe TEXT,"+
                "cre_date TEXT)";
        db.execSQL(sqlCreateDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long addItem(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("describe", i.getDesc());
        values.put("cre_date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("buylist",null, values);
    }


    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("buylist",
                null, null, null,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String describe = rs.getString(3);
            String cre_date = rs.getString(4);
            list.add(new Item(id,title,cre_date,describe,category));
        }
        return list;
    }

    public Item getItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("buylist",
                null, whereClause, whereArgs,
                null, null, null);
        if (rs != null && rs.moveToFirst()) {
            String title = rs.getString(1);
            String category = rs.getString(2);
            String describe = rs.getString(3);
            String cre_date = rs.getString(4);
            rs.close();
            return new Item(id,title,category,describe,cre_date);
        }
        return null;
    }

    public int updateItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("describe", i.getDesc());
        values.put("cre_date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("buylist",
                values, whereClause, whereArgs);
    }

    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("buylist",
                whereClause, whereArgs);
    }

    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "cre_date = ?";
        String[] whereArgs = {date.trim()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("buylist",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String describe = rs.getString(3);
            String cre_date = rs.getString(4);
            list.add(new Item(id,title,cre_date,describe,category));
        }
        return list;
    }

    public List<Item> getByMonth(String month) {
        List<Item> list = new ArrayList<>();
        String whereClause = "cre_date LIKE ?";
        String[] whereArgs = { "%"+month.trim()+"%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("buylist",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String describe = rs.getString(3);
            String cre_date = rs.getString(4);
            list.add(new Item(id,title,cre_date,describe,category));
        }
        return list;
    }

    public ArrayList<PieEntry> getPieChart(String date) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String sql = String.format("SELECT category,SUM(describe) FROM buylist WHERE cre_date LIKE ? GROUP BY category");
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.rawQuery(sql,new String[] { "%"+date });
        while ((rs != null) && (rs.moveToNext())) {
           String category = rs.getString(0);
           int value = rs.getInt(1);
           PieEntry pieEntry = new PieEntry(value,category);
           pieEntries.add(pieEntry);
        }
        return pieEntries;
    }


}
