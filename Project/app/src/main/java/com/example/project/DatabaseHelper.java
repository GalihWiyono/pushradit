package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.ModelCart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NAMA_TABLE = "MENU";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAMA = "NAMA";
    public static final String COLUMN_HARGA = "HARGA";
    public static final String NAMA_TABLE_2 = "AddToCart";
    public static final String COLUMN_QUANTITY = "COLUMN_QUANTITY";
    public static final String COLUMN_TOTALHARGA = "COLUMN_TOTALHARGA";
    public static final String NAMA_TABLE_3 = "HISTORY";
    public static final String COLUMN_ALLITEM = "COLUMN_ALLITEM";
    public static final String COLUMN_DATE = "COLUMN_DATE";


    public DatabaseHelper(Context context) {
        super(context, "makanan.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNewTable = "CREATE TABLE " + NAMA_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAMA + " TEXT, " + COLUMN_HARGA + " TEXT);";
        String newTable = "CREATE TABLE " + NAMA_TABLE_2 + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAMA + " TEXT, " + COLUMN_HARGA + " TEXT, " + COLUMN_QUANTITY + " INT, " + COLUMN_TOTALHARGA + " TEXT);";
        String history = "CREATE TABLE " + NAMA_TABLE_3 + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ALLITEM + " TEXT, " + COLUMN_TOTALHARGA +" TEXT, " + COLUMN_DATE + " TEXT);";
        db.execSQL(createNewTable);
        db.execSQL(newTable);
        db.execSQL(history);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //untuk menghitung total semua harga di cart
    public double totalHarga() {
        double finalharga = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NAMA_TABLE_2;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                int menuId = cursor.getInt(0);
                String namaMenu = cursor.getString(1);
                double hargaMenu = cursor.getDouble(2);
                int quantity = cursor.getInt(3);
                double totalHarga = cursor.getDouble(4);
                finalharga = finalharga + totalHarga;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return finalharga;
    }

    //untuk menambahkan item ke cart
    public boolean AddToCart(ModelCart cart) {
        long insert;
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        String query = "SELECT * FROM " + NAMA_TABLE_2 + " WHERE " + COLUMN_NAMA + " LIKE " + "'" + cart.getNama() + "'";
        Cursor cursor = db2.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            int menuId = cursor.getInt(0);
            String namaMenu = cursor.getString(1);
            double hargaMenu = cursor.getDouble(2);
            int quantity = cursor.getInt(3);
            double totalharga = cursor.getDouble(4);

            int banyakItem = quantity + cart.getQuantity();
            double nextHarga = totalharga + cart.getTotalHarga();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_QUANTITY, banyakItem);
            cv.put(COLUMN_TOTALHARGA, nextHarga);
            insert = db.update(NAMA_TABLE_2, cv, COLUMN_ID + "=" + menuId, null);
        } else {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAMA, cart.getNama());
            cv.put(COLUMN_HARGA, cart.getHarga());
            cv.put(COLUMN_QUANTITY, cart.getQuantity());
            cv.put(COLUMN_TOTALHARGA, cart.getTotalHarga());
            insert = db.insert(NAMA_TABLE_2, null, cv);
        }
        cursor.close();
        db.close();
        if(insert == -1 ) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updatePerItem(ModelCart modelCart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUANTITY, modelCart.getQuantity());
        cv.put(COLUMN_TOTALHARGA, modelCart.getTotalHarga());
        int update = db.update(NAMA_TABLE_2, cv, COLUMN_ID + "=" + modelCart.getId(), null);
        if(update == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deletePerItem(ModelCart modelCart) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(NAMA_TABLE_2, COLUMN_ID + "=" + modelCart.getId(), null);
        if(delete == -1) {
            return false;
        } else {
            return true;
        }

    }

    //delete data ketika tombol checkout
    public boolean deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + NAMA_TABLE_2;
        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    //mengambil semua data dari table cart untuk di tampilkan di listview
    public List<ModelCart> retriveData() {
        List<ModelCart> list = new ArrayList<>();
        String retrieve = "SELECT * FROM " + NAMA_TABLE_2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(retrieve, null);
        if(cursor.moveToFirst()) {
            do {
                int menuId = cursor.getInt(0);
                String namaMenu = cursor.getString(1);
                double hargaMenu = cursor.getDouble(2);
                int quantity = cursor.getInt(3);
                double totalharga = cursor.getDouble(4);
                ModelCart modelCart = new ModelCart(menuId, namaMenu, hargaMenu, quantity, totalharga);
                list.add(modelCart);
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        db.close();
        return list;

    }

    //untuk menambahkan menu
    public boolean AddMenu(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMA, dataModel.getNama_makanan());
        cv.put(COLUMN_HARGA, dataModel.getHarga_makanan());
        long insert = db.insert(NAMA_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //untuk mengambil semua menu yang ada di table menu
    public List<DataModel> retrieveAll() {
        List<DataModel> list = new ArrayList<>();
        String retrieve = "SELECT * FROM " + NAMA_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(retrieve, null);

        if(cursor.moveToFirst()) {
            do {
                int makananID = cursor.getInt(0);
                String namaMakanan = cursor.getString(1);
                double hargaMakanan = cursor.getDouble(2);
                DataModel newMakanan = new DataModel(makananID, namaMakanan, hargaMakanan);
                list.add(newMakanan);
            } while (cursor.moveToNext());
        } else {
        }
        cursor.close();
        db.close();
        return list;
    }

    //untuk mendelete sebuah menu di tabel menu
    public boolean deleteMenu(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + NAMA_TABLE + " WHERE " + COLUMN_ID + " = " + dataModel.getId();

        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    //untuk mencari menu dari input user
    public List<DataModel> searchMenu(String search) {
        List<DataModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NAMA_TABLE + " WHERE " + COLUMN_NAMA + " LIKE " + "'%" + search + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                int makananID = cursor.getInt(0);
                String namaMakanan = cursor.getString(1);
                double hargaMakanan = cursor.getDouble(2);
                DataModel newMakanan = new DataModel(makananID, namaMakanan, hargaMakanan);
                list.add(newMakanan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    //untuk mengupdate menu yang ada di tabel menu
    public boolean updateMenu(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAMA, dataModel.getNama_makanan());
        cv.put(COLUMN_HARGA, dataModel.getHarga_makanan());
        int update = db.update(NAMA_TABLE, cv, COLUMN_ID + "=" + dataModel.getId(), null);
        if ( update == -1 ) {
            return false;
        } else {
            return true;
        }
    }

    //untuk mengambil data berdasarkan ID
    public List<DataModel> retrieveByID(DataModel dataModel) {
        List<DataModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + NAMA_TABLE + " WHERE " + COLUMN_ID + " = " + dataModel.getId();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                int makananID = cursor.getInt(0);
                String namaMakanan = cursor.getString(1);
                double hargaMakanan = cursor.getDouble(2);
                DataModel newMakanan = new DataModel(makananID, namaMakanan, hargaMakanan);
                list.add(newMakanan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    //mengambil data ke History
    public String retrieveCustomCart() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + NAMA_TABLE_2;
        Cursor cursor = db.rawQuery(query, null);
        String all = "";
        if ( cursor.moveToFirst() ) {
            do {
                int id = cursor.getInt(0);
                String nama = cursor.getString(1);
                double harga = cursor.getDouble(2);
                int quantity = cursor.getInt(3);
                double totalharga = cursor.getDouble(4);

                all = all + nama + " @"+quantity+" "+totalharga+"\n";
            } while (cursor.moveToNext() );
        }
        cursor.close();
        db.close();
        return all;
    }
    public boolean Addhistory(HistoryModel historyModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ALLITEM, historyModel.getAllitem());
        cv.put(COLUMN_TOTALHARGA, historyModel.getTotalharga());
        cv.put(COLUMN_DATE, historyModel.getTanggal());
        long insert = db.insert(NAMA_TABLE_3, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<HistoryModel> retrieveHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<HistoryModel> list = new ArrayList<>();
        String query = " SELECT * FROM " + NAMA_TABLE_3;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())  {
            do {
                int id = cursor.getInt(0);
                String allitem = cursor.getString(1);
                double totalharga = cursor.getDouble(2);
                String tanggal = cursor.getString(3);

                HistoryModel historyModel = new HistoryModel(id, allitem, totalharga, tanggal);
                list.add(historyModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<HistoryModel> retrieveHistoryByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<HistoryModel> list = new ArrayList<>();
        String query = " SELECT * FROM " + NAMA_TABLE_3 + " WHERE " + COLUMN_DATE + " LIKE " + "'%" + date + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())  {
            do {
                int id = cursor.getInt(0);
                String allitem = cursor.getString(1);
                double totalharga = cursor.getDouble(2);
                String tanggal = cursor.getString(3);

                HistoryModel historyModel = new HistoryModel(id, allitem, totalharga, tanggal);
                list.add(historyModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

}
