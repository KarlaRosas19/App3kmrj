package com.test.marianarosas.app3kmrj;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListview extends ArrayAdapter<Cursos>{


    private ArrayList<Cursos> datos;
    private Activity context;

    public CustomListview(Activity  context, ArrayList<Cursos> datos) {


        super(context, R.layout.listview_layout, datos);

        this.context=context;
        this.datos=datos;

    }


    @NonNull
    @Override


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View r=convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) r.getTag();
        }




        viewHolder.tv1.setText(datos.get(position).getNombre());
        viewHolder.tv2.setText(datos.get(position).getSede());
        viewHolder.tv3.setText(datos.get(position).getFechainic()+"---"+datos.get(position).getFechafin());



        return r;

    }


    class ViewHolder{
        TextView tv1;
        TextView  tv2;
        TextView  tv3;

        ViewHolder( View view){

            tv1= view.findViewById(R.id.tv12);
            tv2= view.findViewById(R.id.tv2);
            tv3= view.findViewById(R.id.tv3);



        }
    }
}





