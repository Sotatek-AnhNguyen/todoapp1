package com.example.nguye.todoapp.Connec;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.nguye.todoapp.Activity.MainActivity;
import com.example.nguye.todoapp.Model.Todo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by nguye on 04/05/2018.
 */

public class MyJsonText extends AsyncTask<String, JSONObject, ArrayList<Todo>> {
    private ArrayList<Todo> arrData = new ArrayList<>();
    private Context context;
    private Handler handler;
    private String authorization;

    public MyJsonText(Context context, Handler handler, String s) {
        this.context = context;
        this.handler = handler;
        authorization = s;
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1){
            stringBuilder.append((char)cp);
        }
        return stringBuilder.toString();
    }


    @Override
    protected ArrayList<Todo> doInBackground(String... strings) {
        String url = strings[0];
        try {
            URL urlcn = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlcn.openConnection();
            urlConnection.setRequestProperty("Authorization",authorization);
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestMethod("GET");

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader rd = new BufferedReader(inputStreamReader);
            String jsonText = readAll(rd);
            try {
                JSONObject jsonObject = new JSONObject(jsonText);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++){
                    String completed = jsonArray.getJSONObject(i).getString("completed");
                    String id = jsonArray.getJSONObject(i).getString("_id");
                    String owner = jsonArray.getJSONObject(i).getString("owner");
                    String title = jsonArray.getJSONObject(i).getString("title");
                    String created = jsonArray.getJSONObject(i).getString("created");
                    String update = jsonArray.getJSONObject(i).getString("updated");
                    Todo todo = new Todo(completed, id, owner, title, created, update);
                    arrData.add(todo);
                }
                if (arrData.size() == 9){

                }
                publishProgress(jsonObject);
                return arrData;
            } catch (JSONException e) {
                e.printStackTrace();
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

    @Override
    protected void onProgressUpdate(JSONObject... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(ArrayList<Todo> todos) {
        super.onPostExecute(todos);
        if (todos == null){
            Log.e("l","loii");
        }else{
            Message message = new Message();
            message.what = MainActivity.What_New;
            message.obj = todos;
            handler.sendMessage(message);
        }
    }

}
