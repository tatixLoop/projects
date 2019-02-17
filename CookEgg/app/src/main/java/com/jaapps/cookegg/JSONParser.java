package com.jaapps.cookegg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
/**
 * Created by jithin on 10/9/18.
 */

public class JSONParser {



    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";


    HttpURLConnection connect;
    URL urlObj;

    // constructor
    public JSONParser() {

    }

    public void cancelReq()
    {
        if (connect != null)
        {
            connect.disconnect();
        }
    }
    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
                                      String extraParam) {

        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + extraParam;
                Log.d("JKS","URL executed using POST is "+url);

                urlObj = new URL(url);
                connect = (HttpURLConnection) urlObj.openConnection();
                is = connect.getInputStream();

            }else if(method == "GET"){
                // request method is GET
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + extraParam;
                Log.d("JKS","URL executed using GET is "+url);

                urlObj = new URL(url);
                connect = (HttpURLConnection) urlObj.openConnection();
                is = connect.getInputStream();

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}
