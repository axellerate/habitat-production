package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.habitatapi.habit_project.R;
import com.habitatapi.habit_project.user.User;

public class RegisterTwo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
    }

    public void onClickNextRegisterTwo(View view) {
        CheckBox carCheck = (CheckBox) findViewById(R.id.carCheckBox);
        CheckBox bikeCheck = (CheckBox) findViewById(R.id.bikingWalkingCheckBox);
        CheckBox motorCheck = (CheckBox) findViewById(R.id.motorcycleCheckBox);
        if (carCheck.isChecked()) {
            User u = (User) getIntent().getExtras().get("userInfo");
            Intent i = new Intent(RegisterTwo.this, RegisterCars.class);
            i.putExtra("userInfo", u);
            i.putExtra("motor", bikeCheck.isChecked());
            startActivity(i);
        } else if (motorCheck.isChecked()) {
            User u = (User) getIntent().getExtras().get("userInfo");
            Intent i = new Intent(RegisterTwo.this, RegisterMotorcycle.class);
            i.putExtra("userInfo", u);
            startActivity(i);
        } else {
            Intent i = new Intent(RegisterTwo.this, RegisterHome.class);
            startActivity(i);
        }
    }
}
