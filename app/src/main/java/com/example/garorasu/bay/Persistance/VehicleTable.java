package com.example.garorasu.bay.Persistance;

/**
 * Created by garorasu on 3/12/16.
 */

import android.provider.BaseColumns;


public interface VehicleTable {
    String NAME = "vehicle";

    String COLUMN_ID = BaseColumns._ID;
    String COLUMN_VID = "vid";
    String COLUMN_IN_TIME = "inTime";
    String COLUMN_OUT_TIME = "outTime";
    String COLUMN_TYPE = "type";
    String COLUMN_IN_IMG = "inImage";
    String COLUMN_OUT_IMG = "outImage";
    String COLUMN_OCP = "ocp";
    String COLUMN_FEE = "fee";

    String[] PROJECTION = new String[]{COLUMN_ID,COLUMN_VID,COLUMN_IN_TIME,COLUMN_OUT_TIME,
            COLUMN_TYPE,COLUMN_IN_IMG,COLUMN_OUT_IMG,COLUMN_OCP,COLUMN_FEE};

    String CREATE = "CREATE TABLE " + NAME + "("
            + COLUMN_ID + "LONG PRIMARY KEY, "
            + COLUMN_VID + "TEXT NOT NULL, "
            + COLUMN_IN_TIME + "DATETIME NOT NULL, "
            + COLUMN_OUT_TIME + "LONG PRIMARY KEY, "
            + COLUMN_TYPE + "INTEGER, "
            + COLUMN_IN_IMG + "BOOLEAN NOT NULL, "
            + COLUMN_OUT_IMG + "BOOLEAN, "
            + COLUMN_OCP + "BOOLEAN NOT NULL, "
            + COLUMN_FEE + "DOUBLE); ";
}
