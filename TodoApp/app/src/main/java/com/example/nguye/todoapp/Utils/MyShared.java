package com.example.nguye.todoapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nguye on 11/05/2018.
 */

public class MyShared {
    private SharedPreferences sharedPreferences;
    private Context context;
    public static final String NAME = "MyShared";
    public static final String KEY_USER_NAME = "UserName";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_TOCKEN = "token";

    public MyShared(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void putData(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getData(String key){
        String value = sharedPreferences.getString(key,"");
        return value;
    }
}
