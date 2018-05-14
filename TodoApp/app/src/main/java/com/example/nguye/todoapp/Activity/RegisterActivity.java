package com.example.nguye.todoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nguye.todoapp.Model.Account;
import com.example.nguye.todoapp.R;
import com.example.nguye.todoapp.Connec.ConnecLogin;

/**
 * Created by nguye on 11/05/2018.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText mEdtEmailRegist;
    private EditText mEdtPassRegist;
    private EditText mEdtName;
    private Button mBtRegist;
    private Button mBtClose;
    private Intent intentRe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intentRe = getIntent();
        initView();
    }

    private void initView() {
        mEdtEmailRegist = findViewById(R.id.mEdtEmailRegist);
        mEdtPassRegist = findViewById(R.id.mEdtPasswordRegist);
        mEdtName = findViewById(R.id.mEdtName);
        mBtRegist = findViewById(R.id.mBtRegister);
        mBtClose = findViewById(R.id.mBtCloser);

        mBtRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEdtEmailRegist.getText().toString();
                String password = mEdtPassRegist.getText().toString();
                String name = mEdtName.getText().toString();
                new ConnecLogin(email, password, name, "re", handler, getApplicationContext()).execute("https://uetcc-todo-app.herokuapp.com/register");
            }
        });

        mBtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LoginActivity.WHAT_N){
                Account account = (Account) msg.obj;
                String email = mEdtEmailRegist.getText().toString();
                String password = mEdtPassRegist.getText().toString();
                String name = mEdtName.getText().toString();
                intentRe.putExtra("emailRe",email);
                intentRe.putExtra("passRe", password);
                intentRe.putExtra("nameRe", name);
                setResult(RESULT_OK, intentRe);
                finish();
            }
        }
    };
}
