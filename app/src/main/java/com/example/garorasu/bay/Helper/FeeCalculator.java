package com.example.garorasu.bay.Helper;

import com.example.garorasu.bay.Model.Fare;
import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.Persistance.DbHelper;

import java.util.Date;

/**
 * Created by garorasu on 9/12/16.
 */

public class FeeCalculator {
    public FeeCalculator(){}
    public int hourBetween(Date inTime,Date outTime){
        int hourBetween = (int) Math.ceil((outTime.getTime()-inTime.getTime())/3600000.0);
     return hourBetween;
    }
    public int feeCalculator(int BaseRate,int BaseHour,int RatePerHour,Date inTime,Date outTime){
        return (BaseRate*BaseHour)+((hourBetween(inTime,outTime)-BaseHour)*RatePerHour);
    }
    public int twoWheelerFee(Fare f, Vehicle v,Date outTime){
        int fee = feeCalculator(f.getTwoWheelerBaseFare(),f.getTwoWheelerBaseHour(),f.getTwoWheelerRatePerHour(),
                v.getinDate(),outTime);
        return fee;
    }
    public int fourWheelerFee(Fare f, Vehicle v,Date outTime){
        int fee = feeCalculator(f.getFourWheelerBaseFare(),f.getFourWheelerBaseHour(),f.getFourWheelerRatePerHour(),
                v.getinDate(),outTime);
        return fee;
    }
    public int vehicleFee(Fare f,Vehicle v,Date outTime){
        if(v.getType()=="2"){
            return twoWheelerFee(f,v,outTime);
        }else if(v.getType()=="4"){
            return fourWheelerFee(f,v,outTime);
        }else{
            return twoWheelerFee(f,v,outTime);
        }
    }
}
