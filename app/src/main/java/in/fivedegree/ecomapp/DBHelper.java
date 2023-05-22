package in.fivedegree.ecomapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kotlin.reflect.TypeOfKt;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB = "EComDB";


    public DBHelper(Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "price TEXT, " +
                "category TEXT, " +
                "description TEXT, " +
                "rate TEXT, " +
                "ratecount TEXT, " +
                "image TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void addProducts(String id, String title, String price, String category, String desc, String imgUrl, String rate, String ratecount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("title", title);
        values.put("price", price);
        values.put("category", category);
        values.put("description", desc);
        values.put("image", imgUrl);
        values.put("rate", rate);
        values.put("ratecount", ratecount);

        db.insertWithOnConflict("products", null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public ArrayList<ProductModel> getAllProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products", null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.image = cursor.getString(7);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }
}
