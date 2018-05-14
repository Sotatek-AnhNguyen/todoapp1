package com.example.nguye.todoapp.Connec;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nguye on 11/05/2018.
 */

public class ConnectUpdate extends AsyncTask<String, Void, String> {
    private String authorization;
    private String title;

    public ConnectUpdate(String authorization, String title) {
        this.authorization = authorization;
        this.title = title;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        try {
            String datas = "title="+title;
            URL urlcn = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlcn.openConnection();
            conn.setRequestProperty("Authorization",authorization);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(datas.getBytes());
            os.close();

            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader rd = new BufferedReader(inputStreamReader);
                String jsonText = readAll(rd);

                JSONObject object = new JSONObject(jsonText);
                if (object.has("success")) {
                    Log.e("tra ve",object.getString("success"));
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1){
            stringBuilder.append((char)cp);
        }
        return stringBuilder.toString();
    }
}
