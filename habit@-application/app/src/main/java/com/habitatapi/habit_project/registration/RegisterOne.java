package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.habitatapi.habit_project.R;
import com.habitatapi.habit_project.user.User;

public class RegisterOne extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
    }

    public void onClickRegisterOneNext(View view) {
        EditText emailText = (EditText) findViewById(R.id.email_register);
        EditText passwordText = (EditText) findViewById(R.id.password_register);
        EditText retypePasswordText = (EditText) findViewById(R.id.retype_password_register);
        EditText firstNameText = (EditText) findViewById(R.id.first_name);
        EditText lastNameText = (EditText) findViewById(R.id.last_name);

        if (passwordText.getText().toString().equals(retypePasswordText.getText().toString())) {

            Intent i = new Intent(RegisterOne.this, RegisterTwo.class);
            startActivity(i);
        }
    }
}
