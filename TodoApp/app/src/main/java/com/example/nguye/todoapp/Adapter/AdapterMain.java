package com.example.nguye.todoapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguye.todoapp.Activity.UpdateActivity;
import com.example.nguye.todoapp.Model.Todo;
import com.example.nguye.todoapp.R;

import java.util.ArrayList;

/**
 * Created by nguye on 08/05/2018.
 */

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.RcvHolder>{
    private LayoutInflater inflater;
    private ArrayList<Todo> arrData;
    private Context context;
    private String token;

    public AdapterMain(Context context, ArrayList<Todo> arrData, String token) {
        this.arrData = arrData;
        this.context = context;
        this.token = token;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RcvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.activity_item, parent, false);
        RcvHolder rcvHolder = new RcvHolder(v);
        return rcvHolder;
    }

    @Override
    public void onBindViewHolder(RcvHolder holder, int position) {
        holder.mTvTitle.setText(arrData.get(position).getmTitle());
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }


    public class RcvHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle;

        public RcvHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Todo todo = arrData.get(getPosition());
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("titleUpdate", todo.getmTitle());
                    intent.putExtra("id", todo.getmId());
                    intent.putExtra("au", token);
                    context.startActivity(intent);

                }
            });
        }
    }
}
