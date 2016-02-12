package br.com.fiap.daa.fiapfood.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by geisy_000 on 2/9/2016.
 */
public class DB extends SQLiteOpenHelper {

    public static final String TABLE_RESTAURANT = "restaurants";
    public static final String DB_NAME = "fipfood.db";
    public static final int DB_VERSION = 1;

    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ");
        sql.append(TABLE_RESTAURANT);
        sql.append("(");
        sql.append(RestaurantDAO.ID);
        sql.append(" integer primary key autoincrement, ");
        sql.append(RestaurantDAO.NAME);
        sql.append(" text unique, ");
        sql.append(RestaurantDAO.PHONE);
        sql.append(" text, ");
        sql.append(RestaurantDAO.PICTURE);
        sql.append(" text, ");
        sql.append(RestaurantDAO.TYPE);
        sql.append(" text, ");
        sql.append(RestaurantDAO.PRICE);
        sql.append(" real, ");
        sql.append(RestaurantDAO.LAT);
        sql.append(" text, ");
        sql.append(RestaurantDAO.LON);
        sql.append(" text, ");
        sql.append(RestaurantDAO.DESCRIPTION);
        sql.append(" text");
        sql.append(" )");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // recreate db
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        onCreate(db);
    }
}
