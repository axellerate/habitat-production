package com.habitatapi.habit_project.registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.habitatapi.habit_project.R;
import com.habitatapi.habit_project.cars.AddCar;
import com.habitatapi.habit_project.cars.Car;
import com.habitatapi.habit_project.cars.CarAsyncTask;
import com.habitatapi.habit_project.cars.VehicleIdAsyncTask;
import com.habitatapi.habit_project.user.User;

public class RegisterCars extends Activity {

    Spinner manufacture, model, year, model_type;
    Button nextButton;
    CarAsyncTask lister;
    VehicleIdAsyncTask vid;
    AddCar addCar;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cars);
        year = (Spinner) findViewById(R.id.year_spinner);
        manufacture = (Spinner) findViewById(R.id.manufacturers_spinner);
        model = (Spinner) findViewById(R.id.models_spinner);
        model_type = (Spinner) findViewById(R.id.model_type_spinner);
        nextButton = (Button) findViewById(R.id.next_step_cars);
        lister = new CarAsyncTask(year, this);
        final ProgressDialog prog = new ProgressDialog(this);
        prog.setMessage("Loading...");
        prog.setCancelable(false);
        prog.show();
        lister.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/year");
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                lister = new CarAsyncTask(manufacture, RegisterCars.this);
                lister.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/make?year="+year.getSelectedItem().toString());
                manufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        lister = new CarAsyncTask(model, RegisterCars.this);
                        String s = manufacture.getSelectedItem().toString();
                        String d[] = new String[10];
                        if(s.contains(" "))
                        {
                            d = s.split(" ");
                            s = d[0] +"%20" + d[1];
                        }
                        Log.i("Manufacture", s);
                        lister.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/model?year="+year.getSelectedItem().toString()+"&make="+s);
                        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                vid = new VehicleIdAsyncTask(model_type, RegisterCars.this);
                                String s = manufacture.getSelectedItem().toString();
                                String d[] = new String[10];
                                if(s.contains(" "))
                                {
                                    d = s.split(" ");
                                    s = d[0] +"%20" + d[1];
                                }
                                String s2 = model.getSelectedItem().toString();
                                String d2[] = new String[10];
                                if(s2.contains(" "))
                                {
                                    d2 = s2.split(" ");
                                    s2 = d2[0] +"%20" + d2[1];
                                }
                                Log.i("Model", s2);
                                vid.execute("http://www.fueleconomy.gov/ws/rest/vehicle/menu/options?year="+year.getSelectedItem().toString()+"&make="+ s +"&model="+s2);
                                nextButton.setVisibility(View.VISIBLE);
                                prog.dismiss();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        model.setVisibility(View.INVISIBLE);

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                manufacture.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void onClickNextRegisterCars(View view) {
        User u = (User) getIntent().getExtras().get("userInfo");
        Spinner s4 = (Spinner) findViewById(R.id.model_type_spinner);

        Car selectedCar = new Car();
        for (Car c : vid.getVehicle()) {
            if (c.getCarType().equals(s4.getSelectedItem().toString())) {
                selectedCar = c;
                break;
            }
        }
        if (getIntent().getExtras().getBoolean("motor")) {
            Intent i = new Intent(RegisterCars.this, RegisterMotorcycle.class);
            startActivity(i);
        } else {
            Intent i = new Intent(RegisterCars.this, RegisterHome.class);
            i.putExtra("carInfo", selectedCar);
            startActivity(i);
        }
    }
}
