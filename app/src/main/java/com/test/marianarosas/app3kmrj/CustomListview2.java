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

public class CustomListview2 extends ArrayAdapter<CursoDesc> {

    private ArrayList<CursoDesc> datos;
    private Activity context;

    public CustomListview2(Activity  context, ArrayList<CursoDesc> datos) {


        super(context, R.layout.listview_layout2, datos);

        this.context=context;
        this.datos=datos;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View r =convertView;
        CustomListview2.ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview_layout2,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder=(CustomListview2.ViewHolder) r.getTag();
        }




        viewHolder.tv21.setText(datos.get(position).getNombre());
        viewHolder.tv22.setText(datos.get(position).getNombre()+" : "+datos.get(position).getDescripcion());




        return r;

    }


    class ViewHolder{
        TextView tv21;
        TextView tv22;



        ViewHolder( View view){

           tv21= view.findViewById(R.id.tv21);
            tv22= view.findViewById(R.id.tv22);




        }
    }


}
