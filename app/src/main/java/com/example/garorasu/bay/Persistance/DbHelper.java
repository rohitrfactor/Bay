package com.example.garorasu.bay.Persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.garorasu.bay.Model.Vehicle;

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
            return 0;
        }
        catch(Exception e){return -1;}
    }

}
