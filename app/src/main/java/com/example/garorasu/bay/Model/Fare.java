package com.example.garorasu.bay.Model;

/**
 * Created by garorasu on 9/12/16.
 */

public class Fare {
    int twoWheelerBaseFare;
    int twoWheelerBaseHour;
    int twoWheelerRatePerHour;
    int fourWheelerBaseFare;
    int fourWheelerBaseHour;
    int fourWheelerRatePerHour;

    public  Fare(){}
    public Fare(int twoWheelerBaseFare,int twoWheelerBaseHour,int twoWheelerRatePerHour,
                int fourWheelerBaseFare,int fourWheelerBaseHour,int fourWheelerRatePerHour) {
        this.twoWheelerBaseFare = twoWheelerBaseFare;
        this.twoWheelerBaseHour = twoWheelerBaseHour;
        this.twoWheelerRatePerHour = twoWheelerRatePerHour;
        this.fourWheelerBaseFare = fourWheelerBaseFare;
        this.fourWheelerBaseHour =fourWheelerBaseHour;
        this.fourWheelerRatePerHour = fourWheelerRatePerHour;
    }

    public int getTwoWheelerBaseFare() {
        return twoWheelerBaseFare;
    }

    public int getTwoWheelerBaseHour() {
        return twoWheelerBaseHour;
    }

    public int getTwoWheelerRatePerHour() {
        return twoWheelerRatePerHour;
    }

    public int getFourWheelerBaseFare() {
        return fourWheelerBaseFare;
    }

    public int getFourWheelerBaseHour() {
        return fourWheelerBaseHour;
    }

    public int getFourWheelerRatePerHour() {
        return fourWheelerRatePerHour;
    }
}
