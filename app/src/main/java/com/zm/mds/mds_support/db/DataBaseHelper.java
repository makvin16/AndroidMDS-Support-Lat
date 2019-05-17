package com.zm.mds.mds_support.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zm.mds.mds_support.model.Report;
import com.zm.mds.mds_support.model.Shop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by moska on 09.10.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    /**primary**/
    private static final String TAG = DataBaseHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "DB_MDS_SUPPORT";
    /**table**/
    private static final String TABLE_SHOP = "table_shop";
    private static final String TABLE_REPORT = "table_report";
    private static final String TABLE_EMAIL = "table_email";
    /**shop**/
    private static final String SHOP_ID = "ID";
    private static final String SHOP_ADDRESS = "address";
    private static final String SHOP_PASSWORD = "password";
    private static final String SHOP_NAME = "name";
    private static final String SHOP_CITY = "city";
    private static final String SHOP_PHONE = "phone";
    private static final String SHOP_SHEDULE = "shedule";
    private static final String SHOP_LATITUDE = "latitude";
    private static final String SHOP_LONGITUDE = "longitude";
    private static final String SHOP_ORG_ID = "org_id";

    private static final String CREATE_TABLE_SHOP = "CREATE TABLE "
            + TABLE_SHOP + "("
            + SHOP_ID + " INTEGER PRIMARY KEY,"
            + SHOP_ADDRESS + " TEXT,"
            + SHOP_PASSWORD + " TEXT,"
            + SHOP_NAME + " TEXT,"
            + SHOP_CITY + " TEXT,"
            + SHOP_PHONE + " TEXT,"
            + SHOP_SHEDULE + " TEXT,"
            + SHOP_LATITUDE + " TEXT,"
            + SHOP_LONGITUDE + " TEXT,"
            + SHOP_ORG_ID + " INTEGER"
            + ")";

    /**report**/
    private static final String REPORT_ID = "ID";
    private static final String REPORT_NAME = "name";
    private static final String REPORT_PERCENTAGE = "percentage";
    private static final String REPORT_SUM = "sum";
    private static final String REPORT_PAYMENT = "payment";
    private static final String REPORT_DATE = "date";
    private static final String REPORT_TIME = "time";
    private static final String REPORT_NUMBER = "number";
    private static final String REPORT_DATE_OF_BIRTH = "birth";
    private static final String REPORT_PHONE = "phone";
    private static final String REPORT_EMAIL = "email";

    private static final String CREATE_TABLE_REPORT = "CREATE TABLE "
            + TABLE_REPORT + "("
            + REPORT_ID + " INTEGER PRIMARY KEY,"
            + REPORT_NAME + " TEXT,"
            + REPORT_PERCENTAGE + " INTEGER,"
            + REPORT_SUM + " REAL,"
            + REPORT_PAYMENT + " REAL,"
            + REPORT_DATE + " TEXT,"
            + REPORT_TIME + " TEXT,"
            + REPORT_NUMBER + " TEXT,"
            + REPORT_DATE_OF_BIRTH + " TEXT,"
            + REPORT_PHONE + " TEXT,"
            + REPORT_EMAIL + " TEXT"
            + ")";

    /**email**/
    private static final String EMAIL_ID = "ID";
    private static final String EMAIL_MAIL = "mail";

    private static final String CREATE_TABLE_EMAIL = "CREATE TABLE "
            + TABLE_EMAIL + "("
            + EMAIL_ID + " INTEGER PRIMARY KEY,"
            + EMAIL_MAIL + " TEXT"
            + ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOP);
        db.execSQL(CREATE_TABLE_REPORT);
        db.execSQL(CREATE_TABLE_EMAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAIL);
        onCreate(db);
    }

    public long createShop(Shop shop) {
        clearShop();
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SHOP_ADDRESS, shop.getAddress());
        values.put(SHOP_PASSWORD, shop.getPassword());
        values.put(SHOP_NAME, shop.getName());
        values.put(SHOP_CITY, shop.getCity());
        values.put(SHOP_PHONE, shop.getPhone());
        values.put(SHOP_SHEDULE, shop.getShedule());
        values.put(SHOP_LATITUDE, shop.getLatitude());
        values.put(SHOP_LONGITUDE, shop.getLongitude());
        values.put(SHOP_ORG_ID, shop.getOrganizationId());
        long shop_id = database.insert(TABLE_SHOP, null, values);
        database.close();
        return shop_id;
    }

    public long createReport(Report report) {
        SQLiteDatabase database = this.getWritableDatabase();
        Log.d(TAG, report.getFullName());
        Log.d(TAG, report.getSum()+"");
        Log.d(TAG, report.getPayment()+"");
        Log.d(TAG, report.getDate());
        Log.d(TAG, report.getTime());
        Log.d(TAG, report.getNumber());
        Log.d(TAG, report.getDateOfBirth());
        Log.d(TAG, report.getPhone());
        Log.d(TAG, report.getEmail());

        ContentValues values = new ContentValues();
        values.put(REPORT_NAME, report.getFullName());
        values.put(REPORT_PERCENTAGE, report.getPercentage());
        values.put(REPORT_SUM, report.getSum());
        values.put(REPORT_PAYMENT, report.getPayment());
        values.put(REPORT_DATE, report.getDate());
        values.put(REPORT_TIME, report.getTime());
        values.put(REPORT_NUMBER, report.getNumber());
        values.put(REPORT_DATE_OF_BIRTH, report.getDateOfBirth());
        values.put(REPORT_PHONE, report.getPhone());
        values.put(REPORT_EMAIL, report.getEmail());
        long report_id = database.insert(TABLE_REPORT, null, values);
        database.close();
        return report_id;
    }

    public long createEmail(String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL_MAIL, email);
        long email_id = database.insert(TABLE_EMAIL, null, values);
        database.close();
        return email_id;
    }

    public Shop getShop() {
        Shop shop = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SHOP;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            Log.d(TAG, "address " + cursor.getString(cursor.getColumnIndex(SHOP_ADDRESS)));
            Log.d(TAG, "password " + cursor.getString(cursor.getColumnIndex(SHOP_PASSWORD)));
            Log.d(TAG, "name " + cursor.getString(cursor.getColumnIndex(SHOP_NAME)));
            Log.d(TAG, "city " + cursor.getString(cursor.getColumnIndex(SHOP_CITY)));
            Log.d(TAG, "phone " + cursor.getString(cursor.getColumnIndex(SHOP_PHONE)));
            Log.d(TAG, "shedule " + cursor.getString(cursor.getColumnIndex(SHOP_SHEDULE)));
            Log.d(TAG, "lat " + cursor.getString(cursor.getColumnIndex(SHOP_LATITUDE)));
            Log.d(TAG, "lon " + cursor.getString(cursor.getColumnIndex(SHOP_LONGITUDE)));
            Log.d(TAG, "id " + cursor.getString(cursor.getColumnIndex(SHOP_ORG_ID)));

            shop = new Shop();
            shop.setAddress(cursor.getString(cursor.getColumnIndex(SHOP_ADDRESS)));
            shop.setPassword(cursor.getString(cursor.getColumnIndex(SHOP_PASSWORD)));
            shop.setName(cursor.getString(cursor.getColumnIndex(SHOP_NAME)));
            shop.setCity(cursor.getString(cursor.getColumnIndex(SHOP_CITY)));
            shop.setPhone(cursor.getString(cursor.getColumnIndex(SHOP_PHONE)));
            shop.setShedule(cursor.getString(cursor.getColumnIndex(SHOP_SHEDULE)));
            shop.setLatitude(cursor.getString(cursor.getColumnIndex(SHOP_LATITUDE)));
            shop.setLongitude(cursor.getString(cursor.getColumnIndex(SHOP_LONGITUDE)));
            shop.setOrganizationId(cursor.getString(cursor.getColumnIndex(SHOP_ORG_ID)));
        }
        return shop;
    }

    public ArrayList<Report> getReport() {
        ArrayList<Report> arrReport = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REPORT;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Report report = new Report();
                report.setFullName(cursor.getString(cursor.getColumnIndex(REPORT_NAME)));
                report.setPercentage(cursor.getInt(cursor.getColumnIndex(REPORT_PERCENTAGE)));
                report.setSum(cursor.getDouble(cursor.getColumnIndex(REPORT_SUM)));
                report.setPayment(cursor.getDouble(cursor.getColumnIndex(REPORT_PAYMENT)));
                report.setDate(cursor.getString(cursor.getColumnIndex(REPORT_DATE)));
                report.setTime(cursor.getString(cursor.getColumnIndex(REPORT_TIME)));
                report.setNumber(cursor.getString(cursor.getColumnIndex(REPORT_NUMBER)));
                report.setDateOfBirth(cursor.getString(cursor.getColumnIndex(REPORT_DATE_OF_BIRTH)));
                report.setPhone(cursor.getString(cursor.getColumnIndex(REPORT_PHONE)));
                report.setEmail(cursor.getString(cursor.getColumnIndex(REPORT_EMAIL)));

                arrReport.add(report);
            } while (cursor.moveToNext());
        }
        return arrReport;
    }

    public ArrayList<Report> getReport(String date) {
        ArrayList<Report> arrReport = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REPORT + " WHERE " + REPORT_DATE + " = " + "'" + date + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Report report = new Report();
                report.setFullName(cursor.getString(cursor.getColumnIndex(REPORT_NAME)));
                report.setPercentage(cursor.getInt(cursor.getColumnIndex(REPORT_PERCENTAGE)));
                report.setSum(cursor.getDouble(cursor.getColumnIndex(REPORT_SUM)));
                report.setPayment(cursor.getDouble(cursor.getColumnIndex(REPORT_PAYMENT)));
                report.setDate(cursor.getString(cursor.getColumnIndex(REPORT_DATE)));
                report.setTime(cursor.getString(cursor.getColumnIndex(REPORT_TIME)));
                report.setNumber(cursor.getString(cursor.getColumnIndex(REPORT_NUMBER)));

                arrReport.add(report);
            } while (cursor.moveToNext());
        }
        return arrReport;
    }

    public ArrayList<String> getEmail() {
        ArrayList<String> arrEmail = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMAIL;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                String email = cursor.getString(cursor.getColumnIndex(EMAIL_MAIL));
                arrEmail.add(email);
            } while (cursor.moveToNext());
        }
        return arrEmail;
    }

    public int getCountShop() {
        return getCount(TABLE_SHOP);
    }

    public int getCountReport() {
        return getCount(TABLE_REPORT);
    }

    public int getCountEmail() { return getCount(TABLE_EMAIL); }

    public void clearShop() {
        clear(TABLE_SHOP);
    }

    public void clearReport() {
        clear(TABLE_REPORT);
    }

    public void clearEmail() { clear(TABLE_EMAIL);}

    private int getCount(String table) {
        String countQuery = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    private void clear(String table) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(table, null, null);
    }
}
