package com.habitatapi.habit_project.cars;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeep on 2015-03-27.
 */
public class VehicleIdAsyncTask extends AsyncTask<String, Void, List<Car>> {

    List<Car> vehicle;
    Spinner s4;
    Context parent;
    public VehicleIdAsyncTask(Spinner s, Context c)
    {
        parent = c;
        s4 = s;
    }

    @Override
    protected List<Car> doInBackground(String... arg0)
    {
        List<Car> s;
        Log.i("Arg0", arg0[0]);
        try {
            s = new VehicleIdParser().parse(arg0[0]);
            return s;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(List<Car> result)
    {
        List<String> model_type = new ArrayList();

        for(Car c:result)
        {
            model_type.add(c.getCarType());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent, android.R.layout.simple_list_item_1, model_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicle = result;
        s4.setAdapter(adapter);
    }

    public List<Car> getVehicle() {
        return vehicle;
    }
}
