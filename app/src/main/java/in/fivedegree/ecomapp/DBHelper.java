package in.fivedegree.ecomapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

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
                "image TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void addProducts(String id, String title, String price, String category, String desc, String imgUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("title", title);
        values.put("price", price);
        values.put("category", category);
        values.put("desc", desc);
        values.put("image", imgUrl);

        db.insert("products", null, values);
    }
}
