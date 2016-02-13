package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.habitatapi.habit_project.R;

public class RegisterOne extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
    }

    public void onClickRegisterOneNext(View view){
        Intent i = new Intent(RegisterOne.this, RegisterTwo.class);
        startActivity(i);
    }
}
