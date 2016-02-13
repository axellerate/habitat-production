package com.habitatapi.habit_project.cars;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by sandeep on 2015-03-14.
 */
public class CarAsyncTask extends AsyncTask<String, Void, List<String>> {
    private Spinner s4;
    private Context parent;

    public CarAsyncTask(Spinner s, Context c)
    {
        s4 = s;
        parent = c;

    }

    @Override
    protected List<String> doInBackground(String... arg0)
    {
        List<String> s;
        Log.i("Arg0", arg0[0]);
        try {
            s = new FuelEconomyParser().parse(arg0[0]);
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

    @Override
    protected void onPostExecute(List<String> result)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent, android.R.layout.simple_list_item_1, result);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s4.setAdapter(adapter);
    }
}
