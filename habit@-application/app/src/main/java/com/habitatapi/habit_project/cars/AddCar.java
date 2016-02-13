package com.habitatapi.habit_project.cars;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AddCar extends AsyncTask<CarAdd, Void, String> {

    public String url = "https://projectearthspirit.appspot.com/_ah/api/cars/v1/addcar";

    @Override
    protected String doInBackground(CarAdd... param) {
        JSONObject jsonobj = new JSONObject();
        CarAdd params = param[0];
        try {
            jsonobj.put("year", params.getYear());
            jsonobj.put("make", params.getMake());
            jsonobj.put("model", params.getModel());
            jsonobj.put("carId", params.getCarId());
            jsonobj.put("carType", params.getCarType());
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(url);
            StringEntity se = new StringEntity(jsonobj.toString());
            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
            httppostreq.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(httppostreq);

            Log.i("Json", jsonobj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}