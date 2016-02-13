package com.habitatapi.habit_project.cars;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 15-04-07.
 */

public class CarAdd implements Serializable {
    private String year, make, model, carId, carType;

    public CarAdd(ArrayList<String> car) {
        year = car.get(0);
        make = car.get(1);
        model = car.get(2);
        carType = car.get(3);
        carId = car.get(4);
    }

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarType() {
        return carType;
    }

}