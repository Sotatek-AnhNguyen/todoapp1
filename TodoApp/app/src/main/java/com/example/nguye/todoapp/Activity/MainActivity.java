package com.example.nguye.todoapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nguye.todoapp.Adapter.AdapterMain;
import com.example.nguye.todoapp.Connec.MyJsonText;
import com.example.nguye.todoapp.Model.Todo;
import com.example.nguye.todoapp.R;
import com.example.nguye.todoapp.Utils.MyShared;

import java.util.ArrayList;
import java.util.Collection;


public class MainActivity extends AppCompatActivity {
    public static final int What_New = 1;
    public static final int CODE = 1;
    private RecyclerView mRcvMain;
    private AdapterMain adapterMain;
    private ArrayList<Todo> arrData;
    private ArrayList<Todo> arrHan;
    private String authorization;
    private Intent intentC;
    private MyShared myShared;
    private int sizeArrData;
    private int page = 1;
    private boolean loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myShared = new MyShared(this);
        arrData = new ArrayList<>();
        arrHan = new ArrayList<>();
        intentC = getIntent();
        authorization = intentC.getStringExtra("token");
        loadData = true;
        new MyJsonText(this, handler, authorization).execute("https://uetcc-todo-app.herokuapp.com/todos");
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        arrData.clear();
        sizeArrData = arrData.size();
        page = 1;
        loadData = true;
        new MyJsonText(this, handler, authorization).execute("https://uetcc-todo-app.herokuapp.com/todos");
    }

    private void initView() {
        mRcvMain = findViewById(R.id.mRcvMain);
        adapterMain = new AdapterMain(this, arrData, authorization);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcvMain.setLayoutManager(layoutManager);
        mRcvMain.setAdapter(adapterMain);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == What_New){
                arrHan.clear();
                arrHan.addAll((Collection<? extends Todo>) msg.obj);
                arrData.addAll(arrHan);
                if ((arrData.size()) % 10 == 0 && arrData.size() > 0 && arrData.size()!= sizeArrData){
                    page++;
                    String url = "https://uetcc-todo-app.herokuapp.com/todos?page=" + page;
                    new MyJsonText(getApplicationContext(), handler, authorization).execute(url);
                    sizeArrData = arrData.size();

                }
                Log.e("size handler", sizeArrData+"");
                Log.e("size arr", arrData.size()+"");
                adapterMain.notifyDataSetChanged();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuapp, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mMnCreate:
                Intent intent = new Intent(this, CreateATodoActivity.class);
                intent.putExtra("authorization",authorization);
                startActivityForResult(intent, CODE);
                break;
            case R.id.mMnLogout:
                myShared.putData(MyShared.KEY_TOCKEN, "");
                myShared.putData(MyShared.KEY_USER_NAME, "");
                myShared.putData(MyShared.KEY_PASSWORD, "");
                Intent intenLog = new Intent(this, LoginActivity.class);
                startActivity(intenLog);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE){
            if (resultCode == RESULT_OK){

            }
        }
    }
}
