package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.habitatapi.habit_project.R;
import com.habitatapi.habit_project.cars.Car;
import com.habitatapi.habit_project.user.User;

public class RegisterMotorcycle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_motorcycle);
    }

    public void onClickNextRegisterMotorcycle(View view){
        RadioGroup motorRate = (RadioGroup) findViewById(R.id.radio_motor);
        int radioButtonId = motorRate.getCheckedRadioButtonId();
        RadioButton r = (RadioButton)  findViewById(radioButtonId);
        String selectedtext = r.getText().toString();
        Intent i = new Intent(RegisterMotorcycle.this, RegisterHome.class);
        i.putExtra("motorInfo", selectedtext);
        User u = (User) getIntent().getExtras().get("userInfo");
        Car c = (Car) getIntent().getExtras().get("carInfo");
        if(c != null)
        {
            i.putExtra("carInfo", c);
        }
        startActivity(i);
    }
}