package in.fivedegree.ecomapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
                "price INTEGER, " +
                "category TEXT, " +
                "description TEXT, " +
                "rate INTEGER, " +
                "ratecount DOUBLES, " +
                "image TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS cart (" +
                "id INTEGER PRIMARY KEY, " +
                "title TEXT, " +
                "price INTEGER, " +
                "qty INTEGER, " +
                "image TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserToken TEXT, " +
                "productid TEXT, " +
                "orderstatus TEXT, " +
                "paymode TEXT, " +
                "total DOUBLES, " +
                "qty TEXT," +
                "transactionId TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    public void addProducts(String id, String title, double price, String category, String desc, String imgUrl, double rate, int ratecount){
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

    public ArrayList<ProductModel> getSingleProduct(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE id = " + id, null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.category = cursor.getString(3);
            model.description = cursor.getString(4);
            model.rating = new RatingModel();
            model.rating.rate = cursor.getString(5);
            model.rating.count = cursor.getString(6);
            model.image = cursor.getString(7);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }

    public ArrayList<ProductModel> getSearchProduct(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE title LIKE '%" + title + "%'" , null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.image = cursor.getString(7);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }

    public ArrayList<ProductModel> getProductLowToHigh(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products ORDER BY price ", null);

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

    public ArrayList<ProductModel> getProductHighToLow(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products ORDER BY price DESC", null);

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

    public ArrayList<ProductModel> getHighestRated(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products ORDER BY rate DESC", null);

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

    public ArrayList<ProductModel> getMostPopular(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products ORDER BY ratecount DESC", null);

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

    public ArrayList<ProductModel> getLeastPopular(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products ORDER BY ratecount", null);

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

    public ArrayList<ProductModel> getPriceFilteredProducts(double min, double max){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE price >= " + min + " AND price <= "+max , null);

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

    public ArrayList<ProductModel> getElectronicsProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE category = 'electronics'" , null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.image = cursor.getString(7);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }

    public ArrayList<ProductModel> getJeweleryProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE category = 'jewelery'" , null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.image = cursor.getString(7);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }

    public ArrayList<ProductModel> getMenClothingProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE category LIKE 'men%'" , null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.image = cursor.getString(7);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }

    public ArrayList<ProductModel> getWomenCLothingProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE category LIKE 'women%'" , null);

        ArrayList<ProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            ProductModel model = new ProductModel();
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.image = cursor.getString(7);
            fetchProductArr.add(model);
        }

        return fetchProductArr;
    }


    public void addProductsToCart(String id, String title, double price, String img, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM cart WHERE id = " + id , null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE cart SET qty = qty + 1  WHERE id = " + id;
            db.execSQL(updateQuery);
        } else {
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("title", title);
            values.put("price", price);
            values.put("image", img);
            values.put("qty", qty);

            db.insertWithOnConflict("cart", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public ArrayList<CartProductModel> getAllCartProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM cart", null);
        ArrayList<CartProductModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            CartProductModel model = new CartProductModel();
            model.id = cursor.getString(0);
            model.image = cursor.getString(4);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.qty = cursor.getString(3);
            fetchProductArr.add(model);
        }
        return fetchProductArr;
    }

    public void deleteProductFromCart(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart","id = " + id,null);

    }

    public void increaseQty(String id, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE cart SET qty = " + qty + " WHERE id = " + id;
        db.execSQL(updateQuery);
    }

    public void decreaseQty(String id, int qty) {
        if (qty > 0){
            SQLiteDatabase db = this.getWritableDatabase();
            String updateQuery = "UPDATE cart SET qty = " + qty + " WHERE id = " + id;
            db.execSQL(updateQuery);
        }
    }

    public Boolean placeOrder(String token, String productId, String orderstatus, String paymode, double total, String qty, String transactionId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserToken", token);
        values.put("productid", productId);
        values.put("orderstatus", orderstatus);
        values.put("paymode", paymode);
        values.put("total", total);
        values.put("qty", qty);
        values.put("transactionId", transactionId);

        long placed = db.insertWithOnConflict("orders", null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return placed != -1;
    }

    public void emptyCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from cart");
    }

    public ArrayList<OrdersModel> getAllOrders(String token){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM orders WHERE UserToken = '" + token + "'", null);
        ArrayList<OrdersModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            OrdersModel model = new OrdersModel();
            model.id = cursor.getString(0);
            model.userToken = cursor.getString(1);
            model.productId = cursor.getString(2);
            model.orderStatus = cursor.getString(3);
            model.payMode = cursor.getString(4);
            model.cost = Double.parseDouble(cursor.getString(5));
            model.qty = cursor.getString(6);
            model.transactionId = cursor.getString(7);
            fetchProductArr.add(model);
        }
        return fetchProductArr;
    }

    public ArrayList<OrdersModel> getSingleOrder(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM orders WHERE id = '" + id + "'", null);
        ArrayList<OrdersModel> fetchProductArr = new ArrayList<>();
        while (cursor.moveToNext()){
            OrdersModel model = new OrdersModel();
            model.id = cursor.getString(0);
            model.userToken = cursor.getString(1);
            model.productId = cursor.getString(2);
            model.orderStatus = cursor.getString(3);
            model.payMode = cursor.getString(4);
            model.cost = Double.parseDouble(cursor.getString(5));
            model.qty = cursor.getString(6);
            model.transactionId = cursor.getString(7);
            fetchProductArr.add(model);
        }
        return fetchProductArr;
    }

    public CartProductModel getOrderProduct(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM products WHERE id = '" + id + "'", null);
        CartProductModel model = new CartProductModel();
        while (cursor.moveToNext()){
            model.id = cursor.getString(0);
            model.title = cursor.getString(1);
            model.price = cursor.getString(2);
            model.image = cursor.getString(7);
        }
        return model;
    }
}


