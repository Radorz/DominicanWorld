package com.example.dominicanworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Listadapter extends RecyclerView.Adapter<Listadapter.MyViewHolder> implements View.OnClickListener {

    ArrayList<modelo> valor;
    private  View.OnClickListener listener ;
    public Listadapter(Context context,ArrayList<modelo> valor) {

        this.valor = valor;
    }

    @NonNull
    @Override
    public Listadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.rowlist, parent,false);
        v.setOnClickListener((View.OnClickListener) this);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Listadapter.MyViewHolder holder, int position) {
        String name =  valor.get(position).getName();
        int img = valor.get(position).getImage();
        String stringimg =  valor.get(position).getStringimg();
        holder.nombre.setText(name);
        if(stringimg!=""){
            Picasso.get().load(stringimg)
                    .into(holder.img);
        }else {

            /* */
            holder.img.setImageResource(img);
        }
    }

    @Override
    public int getItemCount() {
        return valor.size();
    }


    public void setOnClickListener(View.OnClickListener onclickListener){

        this.listener = onclickListener;

    }

    @Override
    public void onClick(View v) {

        if(listener != null){

            listener.onClick(v);
        }

    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView nombre, ID;
        ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre= itemView.findViewById(R.id.txtnombre);
            img= itemView.findViewById(R.id.iconn);
            ID= itemView.findViewById(R.id.idrow);

        }
    }
}