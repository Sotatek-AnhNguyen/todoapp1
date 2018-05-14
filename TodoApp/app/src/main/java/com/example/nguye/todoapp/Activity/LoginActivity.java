package com.example.nguye.todoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguye.todoapp.Connec.ConnecLogin;
import com.example.nguye.todoapp.Model.Account;
import com.example.nguye.todoapp.R;
import com.example.nguye.todoapp.Utils.MyShared;

/**
 * Created by nguye on 10/05/2018.
 */

public class LoginActivity extends AppCompatActivity {
    public static final int WHAT_N = 1;
    public static final int CODE_LOG = 1;
    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private Button mBtLogin;
    private TextView mTvRegister;
    private CheckBox mCbSave;
    private MyShared myShared;
    private  String userName;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myShared = new MyShared(this);
        checkLogin();
        initView();
    }

    private void initView() {
        mEdtUsername = findViewById(R.id.mEdtUserName);
        mEdtPassword = findViewById(R.id.mEdtPass);
        mBtLogin = findViewById(R.id.mBtLogin);
        mTvRegister = findViewById(R.id.mTvRegist);
        mCbSave = findViewById(R.id.mCbSave);

        if (!myShared.getData("mailsave").isEmpty() || !myShared.getData("passsave").isEmpty()){
            mEdtUsername.setText(myShared.getData("mailsave"));
            mEdtPassword.setText(myShared.getData("passsave"));
        }

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = mEdtUsername.getText().toString();
                password = mEdtPassword.getText().toString();
                if (userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Bạn cần điền đầy đủ để đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                myShared.putData("mailsave", userName);
                myShared.putData("passsave", password);
                new ConnecLogin(userName, password, "", "login", handler, getApplicationContext()).execute("https://uetcc-todo-app.herokuapp.com/login");
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentR = new Intent(getApplication(), RegisterActivity.class);
                startActivityForResult(intentR, CODE_LOG);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_N){
                Account account = (Account) msg.obj;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("token",account.getAccessToken());
                if (mCbSave.isChecked()){
                    myShared.putData(MyShared.KEY_TOCKEN, account.getAccessToken());
                    myShared.putData(MyShared.KEY_USER_NAME, userName);
                    myShared.putData(MyShared.KEY_PASSWORD, password);
                }
                startActivity(intent);
            }
        }
    };

    public void checkLogin(){
        String email = myShared.getData(MyShared.KEY_USER_NAME);
        String pass = myShared.getData(MyShared.KEY_PASSWORD);
        String tok = myShared.getData(MyShared.KEY_TOCKEN);
        if (!email.isEmpty() && !pass.isEmpty() && !tok.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("token",tok);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LOG){
            if (resultCode == RESULT_OK){
                mEdtUsername.setText(data.getStringExtra("emailRe"));
                mEdtPassword.setText(data.getStringExtra("passRe"));
            }
        }
    }
}
