package com.example.recycleretro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.ViewHolder>{
    ArrayList<String> name;
    ArrayList<String> email;
    ArrayList<String> id;
    Context context;
    public CustomAdapter(Context context, ArrayList<String> name, ArrayList<String> email, ArrayList<String> id) {
        this.context=context;
        this.name=name;
        this.email=email;
        this.id=id;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(name.get(position));
        holder.id.setText(id.get(position));
        holder.email.setText(email.get(position));

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
            email=itemView.findViewById(R.id.email);
        }
    }
}
