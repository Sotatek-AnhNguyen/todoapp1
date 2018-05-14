package com.example.nguye.todoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nguye.todoapp.R;
import com.example.nguye.todoapp.Connec.ConnectDelete;
import com.example.nguye.todoapp.Connec.ConnectUpdate;

/**
 * Created by nguye on 11/05/2018.
 */

public class UpdateActivity extends AppCompatActivity {
    private EditText mEdtUpdate;
    private Button mBtUpdate;
    private Intent intentUp;
    private String id;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createatodo);
        intentUp = getIntent();
        initView();
    }

    private void initView() {
        mEdtUpdate = findViewById(R.id.edtCreate);
        mBtUpdate = findViewById(R.id.mBtCreate);
        mBtUpdate.setText("Update title");
        mEdtUpdate.setText(intentUp.getStringExtra("titleUpdate"));
        id = intentUp.getStringExtra("id");
        token = intentUp.getStringExtra("au");
        mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ti = mEdtUpdate.getText().toString();
                String url = "https://uetcc-todo-app.herokuapp.com/todos/"+id;
                new ConnectUpdate(token, ti).execute(url);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuupdate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                String url = "https://uetcc-todo-app.herokuapp.com/todos/"+id;
                new ConnectDelete(token).execute(url);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
