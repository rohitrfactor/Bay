package com.example.garorasu.bay.Persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.garorasu.bay.Model.Vehicle;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by garorasu on 3/12/16.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = "carsManager";

    private static DbHelper sInstance;

    public static synchronized  DbHelper getInstance(Context context) {
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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /* no-op */
        // Drop older table if existed
       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VehicleTable.NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public int addVehicle(Vehicle vehicle){
        // Create and/or open the database for writing
        int status = -2;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
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
        {   status = -1;
            Log.d(TAG, "Error while adding vehicle"+e);
        }
        finally{
            db.endTransaction();
            db.close();
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
            Log.d(TAG, "Error while vehicle exit");
        }finally{
            db.endTransaction();
            db.close();
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
            Log.d(TAG, "Error while deleting all data");
        }finally{
            db.endTransaction();
            db.close();
            return status;
        }
    }
    public Vehicle getVehicleByUid(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(VehicleTable.NAME, VehicleTable.PROJECTION, VehicleTable.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return vehicleFromCursor(cursor);
    }
    public List<Vehicle> getAllVehicles(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + VehicleTable.NAME;
        return getVehicles(selectQuery);
    }
    public List<Vehicle> getParkedVehicles(){
        // Select All vehicles where parking ocp boolean is true
        String selectQuery = "SELECT  * FROM " + VehicleTable.NAME + " WHERE " + VehicleTable.COLUMN_OCP + " = ?";
        return getVehicles(selectQuery,new String[] {"true"});
    }

    public List<Vehicle> getVehicleByVid(String vid){
        String selectQuery = "SELECT * FROM " + VehicleTable.NAME + " WHERE " + VehicleTable.COLUMN_VID + " = ?";
        return getVehicles(selectQuery,new String[] {vid});
    }
    public List<Vehicle> getVehicleByVidForExit(String vid){
        String selectQuery = "SELECT * FROM " + VehicleTable.NAME + " WHERE "
                + VehicleTable.COLUMN_VID + " =  ?" + " AND "
                + VehicleTable.COLUMN_OCP + " = ?";
        return getVehicles(selectQuery,new String[] {vid,"true"});
    }

    private List<Vehicle> getVehicles(String query,String[] parameter){
        List<Vehicle> mListVehicles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, parameter);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Vehicle vehicle = vehicleFromCursor(cursor);
                    mListVehicles.add(vehicle);
                } while(cursor.moveToNext());
            }
        }catch(Exception e){
            Log.d(TAG, "Error while trying to get vehicles from database"+e);
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return mListVehicles;
    }
    private List<Vehicle> getVehicles(String query){
        List<Vehicle> mListVehicles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Vehicle vehicle = vehicleFromCursor(cursor);
                    mListVehicles.add(vehicle);
                } while(cursor.moveToNext());
            }
        }catch(Exception e){
            Log.d(TAG, "Error while trying to get vehicles from database"+e);
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return mListVehicles;
    }


    private Vehicle vehicleFromCursor(Cursor cursor){
        DateFormat dateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date inTime = null;
        Date outTime = null;
        if(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_IN_TIME))!=null){
        try {
            inTime = dateFormat.parse(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_IN_TIME)));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while parsing in time date"+e);}
        }
        if(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OUT_TIME))!=null){
        try {
            outTime = dateFormat.parse(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OUT_TIME)));
        } catch (ParseException e) {

            e.printStackTrace();
            Log.d(TAG, "Error while parsing out time date"+e);}
        }
        System.out.println("Occupancy of vehicle "+cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_VID))+" is : "+cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OCP)));
        Vehicle vehicle = new Vehicle(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_ID))),
                cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_VID)),
                inTime,
                outTime,
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_TYPE))),
                Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_IN_IMG))),
                Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OUT_IMG))),
                Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_OCP))),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(VehicleTable.COLUMN_FEE))));
        return vehicle;
    }
}
