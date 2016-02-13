package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.habitatapi.habit_project.R;

public class RegisterTwo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
    }

    public void onClickNextRegisterTwo(View view){
        Intent i = new Intent(RegisterTwo.this, RegisterCars.class);
        startActivity(i);
    }
}
