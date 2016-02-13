package com.habitatapi.habit_project.cars;

/**
 * Created by sandeep on 2015-03-15.
 */
public class Car {
    private int carId;
    private String carType, make, model, year, emissions;

    public Car(){}

    public Car(int c, String ct)
    {
        this.carId = c;
        this.carType = ct;
    }
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
}