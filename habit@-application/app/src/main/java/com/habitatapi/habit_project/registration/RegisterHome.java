package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.habitatapi.habit_project.Dashboard;
import com.habitatapi.habit_project.R;

public class RegisterHome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_home);
    }

    public void onClickNextRegisterHome(View view){
        Intent i = new Intent(RegisterHome.this, Dashboard.class);
        startActivity(i);
    }
}
