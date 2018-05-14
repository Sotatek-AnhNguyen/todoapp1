package com.example.nguye.todoapp.Connec;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.nguye.todoapp.Activity.LoginActivity;
import com.example.nguye.todoapp.Model.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by nguye on 10/05/2018.
 */

public class ConnecLogin extends AsyncTask<String, Void, Account> {
    private String email;
    private String pass;
    private Handler handler;
    private Context context;
    private String error;
    private String name;
    private String cl;

    public ConnecLogin(String email, String pass, String name, String cl, Handler handler, Context context) {
        this.email = email;
        this.pass = pass;
        this.handler = handler;
        this.context= context;
        this.name = name;
        this.cl = cl;
    }

    @Override
    protected Account doInBackground(String... strings) {
        String url = strings[0];
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("email", email);
            postDataParams.put("password", pass);
            postDataParams.put("name",name);
            URL urlLogin = new URL(url);
            HttpURLConnection conLogin = (HttpURLConnection) urlLogin.openConnection();
            conLogin.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conLogin.setRequestMethod("POST");
            conLogin.setDoOutput(true);
            conLogin.setDoInput(true);
            OutputStream outputStream = conLogin.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            outputStream.close();

            int resCode = conLogin.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conLogin.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String jsontext = readAll(bufferedReader);
                JSONObject jsonObject = new JSONObject(jsontext);
                String success = jsonObject.getString("success");
                if (success.equals("true")) {
                    JSONObject jsonData = new JSONObject(jsonObject.getString("data"));
                    if (cl.equals("login")) {
                        JSONObject jsonuser = new JSONObject(jsonData.getString("user"));

                        String token = jsonData.getString("accessToken");
                        String id = jsonuser.getString("_id");
                        String time = jsonuser.getString("created");
                        String emaila = jsonuser.getString("email");
                        String name = jsonuser.getString("name");
                        Account account = new Account(token, time, id, emaila, name, success);
                        return account;
                    } else {
                        Account ac = new Account("", "", "", "", "", success);
                        return ac;
                    }
                }else {
                    error = jsonObject.getString("message");
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(Account account) {
        super.onPostExecute(account);
        if (account == null){
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        }else{
            Message message = new Message();
            message.what = LoginActivity.WHAT_N;
            message.obj = account;
            handler.sendMessage(message);
        }

    }
}
