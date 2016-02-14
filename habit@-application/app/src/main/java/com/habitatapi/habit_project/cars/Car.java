package com.habitatapi.habit_project.cars;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sandeep on 2015-03-15.
 */
public class Car implements Parcelable {
    private int carId;
    private String carType, make, model, year, emissions;

    public Car(){}

    public Car(int c, String ct)
    {
        this.carId = c;
        this.carType = ct;
    }

    protected Car(Parcel in) {
        carId = in.readInt();
        carType = in.readString();
        make = in.readString();
        model = in.readString();
        year = in.readString();
        emissions = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getCarId() {
        return carId;
    }

    public String getCarType() {
        return carType;
    }

    public void setMake(String _make){
        make = _make;
    }

    public void setModel(String _model){
        model = _model;
    }

    public void setYear(String _year){
        year = _year;
    }

    public void setEmissions(String _emissions){
        emissions = _emissions;
    }

    public String getMake(){
        return make;
    }

    public String getModel(){
        return model;
    }

    public String getYear(){
        return year;
    }

    public String getEmissions(){
        return emissions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeStringArray(new String[] {""+ this.getCarId() + "",
                this.carType});
    }
}