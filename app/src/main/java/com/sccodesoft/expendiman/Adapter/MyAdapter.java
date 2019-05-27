package com.sccodesoft.expendiman.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sccodesoft.expendiman.Model.LogItem;
import com.sccodesoft.expendiman.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Myclass> {

    Context context;
    ArrayList<LogItem> al;

    public MyAdapter(Context context, ArrayList<LogItem> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.logitem_row,viewGroup,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass myclass, int i) {
        LogItem logItem=al.get(i);

        myclass.tvId.setText(logItem.getId());
        myclass.tvName.setText(logItem.getName());
        myclass.tvAmount.setText(logItem.getAmount());
        myclass.tvtype.setText(logItem.getType());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class Myclass extends RecyclerView.ViewHolder
    {

        TextView tvId, tvName, tvAmount,tvtype;
        public Myclass(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.lid);
            tvName = itemView.findViewById(R.id.lName);
            tvAmount = itemView.findViewById(R.id.lAmount);
            tvtype = itemView.findViewById(R.id.lType);
        }
    }
}
