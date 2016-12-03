package com.example.garorasu.bay.Persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.garorasu.bay.Model.Vehicle;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by garorasu on 3/12/16.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "carsManager";

    private static DbHelper sInstance;

    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    //private constructor to implement singleton pattern
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(VehicleTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /* no-op */
    }

    public int addVehicle(Vehicle vehicle){
        // Create and/or open the database for writing
        int status = -2;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(VehicleTable.COLUMN_ID,vehicle.getUid());
            values.put(VehicleTable.COLUMN_VID,vehicle.getVid());
            values.put(VehicleTable.COLUMN_IN_TIME,vehicle.getInTime());
            values.put(VehicleTable.COLUMN_OUT_TIME,vehicle.getOutTime());
            values.put(VehicleTable.COLUMN_TYPE,vehicle.getType());
            values.put(VehicleTable.COLUMN_IN_IMG,vehicle.isInImage());
            values.put(VehicleTable.COLUMN_OUT_IMG,vehicle.isOutImage());
            values.put(VehicleTable.COLUMN_OCP,vehicle.isOcp());
            values.put(VehicleTable.COLUMN_FEE,vehicle.getFee());
            db.insert(VehicleTable.NAME,null,values);
            db.setTransactionSuccessful();
            status = 0;
        }
        catch(Exception e)
        {status = -1;}
        finally{
            db.endTransaction();
            return status;}
    }
    public int exitVehicle(Vehicle vehicle){
        int status = -2;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(VehicleTable.COLUMN_OUT_TIME,vehicle.getOutTime());
            values.put(VehicleTable.COLUMN_OUT_IMG,vehicle.isOutImage());
            values.put(VehicleTable.COLUMN_OCP,vehicle.isOcp());
            values.put(VehicleTable.COLUMN_FEE,vehicle.getFee());
            //SQL Query to update a row based on primary key
            db.update(VehicleTable.NAME,values, VehicleTable.COLUMN_ID + " = ?",
                    new String[] {String.valueOf(vehicle.getUid())});
            db.setTransactionSuccessful();
            status = 0;
        }
        catch (Exception e){
            status = -1;
        }finally{
            db.endTransaction();
            return status;
        }
    }
    public int deleteAllData(){
        int status = -2;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete(VehicleTable.NAME,null,null);
            db.setTransactionSuccessful();
            status = 0;
        }
        catch (Exception e){
            status = -1;
        }finally{
            db.endTransaction();
            return status;
        }
    }
    public List<Vehicle> getAllVehicles(){
        List<Vehicle> mListVehicles = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + VehicleTable.NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Vehicle vehicle = new Vehicle(
                            Long.parseLong(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_ID))),
                            cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_VID)),
                            Date.valueOf(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_IN_TIME))),
                            Date.valueOf(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OUT_TIME))),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_TYPE))),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_IN_IMG))),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OUT_IMG))),
                            Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OCP))),
                            Double.parseDouble(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_FEE))));
                    mListVehicles.add(vehicle);
                } while(cursor.moveToNext());
            }
        }catch(Exception e){
            Log.d(TAG, "Error while trying to get all Vehicles from database");
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return mListVehicles;
    }

}
