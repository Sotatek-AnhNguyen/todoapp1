package com.example.nguye.todoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguye.todoapp.R;
import com.example.nguye.todoapp.Connec.ConnecCreate;

/**
 * Created by nguye on 09/05/2018.
 */

public class CreateATodoActivity extends AppCompatActivity {
    private EditText mEditCreate;
    private Button mBtCreate;
    private String authorization;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createatodo);
        intent = getIntent();
        authorization = intent.getStringExtra("authorization");
        initView();
    }

    private void initView() {
        mEditCreate = findViewById(R.id.edtCreate);
        mBtCreate = findViewById(R.id.mBtCreate);
        mBtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEditCreate.getText().toString();
                if (title.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
                    return;
                }
                new ConnecCreate(authorization, title).execute("https://uetcc-todo-app.herokuapp.com/todos");
                intent.putExtra("titlenew", mEditCreate.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
