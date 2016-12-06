package com.example.garorasu.bay.Model;

import java.util.Date;

/**
 * Created by garorasu on 3/12/16.
 */

public class Vehicle {
    int uid;
    String vid;
    Date inTime;
    Date outTime;
    int type; //{"2":"Two-wheeler","3":"Three-wheeler","4":"Four-wheeler","0":"Other"}
    boolean inImg;
    boolean outImg;
    boolean ocp;
    double fee;
    public Vehicle(){
        this.inTime = null;
        this.outTime = null;
        this.inImg = false;
        this.outImg = false;
        this.ocp = false;
        this.fee = 0;
    };

    public Vehicle(int uid,String vid,Date inTime,Date outTime,int type,boolean inImg,
                   boolean outImg,boolean ocp,double fee){
        this.uid = uid;
        this.vid = vid;
        this.inTime = inTime;
        this.outTime = outTime;
        this.type = type;
        this.inImg = inImg;
        this.outImg = outImg;
        this.ocp = ocp;
        this.fee = fee;
    };
    public Vehicle(String vid,Date inTime,Date outTime,int type,boolean inImg,
                   boolean outImg,boolean ocp,double fee){
        this.vid = vid;
        this.inTime = inTime;
        this.outTime = outTime;
        this.type = type;
        this.inImg = inImg;
        this.outImg = outImg;
        this.ocp = ocp;
        this.fee = fee;
    }

    public void vehicleIn(String vid,Date inTime,int type,boolean inImg){
        this.vid = vid;
        this.inTime = inTime;
        this.type = type;
        this.inImg = inImg;
        this.ocp = true;
        this.fee = 0;
    }
    public int vehicleOut(Date outTime,double fee,boolean outImg){
        try{
            this.outTime = outTime;
            this.outImg = outImg;
            this.ocp = false;
            this.fee = fee;
            return 0; // successful
        }
        catch (Exception e){
            return -1; // not successful
        }
    }
    public String getUid() {return String.valueOf(uid);}
    public String getVid(){
        return vid;
    }
    public String getInTime(){
        if(inTime != null)
        {return inTime.toString();}
        else
            return null;
    }
    public String getOutTime(){
        if(outTime != null){
        return outTime.toString();}
        else
            return null;
    }
    public String getType(){
        return String.valueOf(type);
    }
    public String isInImage(){
        return String.valueOf(inImg);
    }
    public String isOutImage(){
        return String.valueOf(outImg);
    }
    public String isOcp(){
        return String.valueOf(ocp);
    }
    public String getFee(){
        return String.valueOf(fee);
    }
}
