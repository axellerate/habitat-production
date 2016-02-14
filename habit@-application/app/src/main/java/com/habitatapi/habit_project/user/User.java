package com.habitatapi.habit_project.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sandeep on 2016-02-13.
 */
public class User {
    public String email;
    public String password;
    public String firstName;
    public String lastName;

    public User(String email, String pwd, String fName, String lName)
    {
        this.email = email;
        this.password=pwd;
        this.firstName = fName;
        this.lastName = lName;
    }
    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

}
