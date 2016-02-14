package com.habitatapi.habit_project.user;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Sandeep on 2016-02-13.
 */
public class UserAsyncTask extends AsyncTask<User, Void, String> {

    public String url = "https://habitatapi.appspot.com/_ah/api/users/1.00/create";
    @Override
    protected String doInBackground(User... users) {
        JSONObject jsonobj = new JSONObject();
        String token = "";
        try {
            for(User user:users) {
                jsonobj.put("email", user.getEmail());
                jsonobj.put("password", user.getPassword());
                jsonobj.put("first_name", user.getFirstName());
                jsonobj.put("last_name", user.getLastName());
                Log.i("Json", jsonobj.toString());
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppostreq = new HttpPost(url);
                StringEntity se = new StringEntity(jsonobj.toString());
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                httppostreq.setEntity(se);
                HttpResponse httpresponse = httpclient.execute(httppostreq);
                String responseStr = EntityUtils.toString(httpresponse.getEntity());
                JSONObject resp = new JSONObject(responseStr);
                token = resp.getString("token");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
