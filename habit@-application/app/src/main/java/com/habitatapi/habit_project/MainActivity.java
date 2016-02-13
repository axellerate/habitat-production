package com.habitatapi.habit_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.habitatapi.habit_project.registration.RegisterOne;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSignIn(View view){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void onClickRegister(View view){
        Intent i = new Intent(MainActivity.this, RegisterOne.class);
        startActivity(i);
    }
}
