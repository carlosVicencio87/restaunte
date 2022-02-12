package com.creathor.restaurante;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterMeceros extends ArrayAdapter<String> {
    private Activity activity;
    private ArrayList data;
    public Resources res;
    SpinnerModel spinnerModel=null;
    LayoutInflater inflater;
    private Context context;
    public AdapterMeceros(Estacion activitySpinner, int textViewResourceId, ArrayList objects, Resources resLocal)
    {
        super(activitySpinner, textViewResourceId, objects);
        activity = activitySpinner;
        data = objects;
        res = resLocal;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position,convertView,parent);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position,convertView,parent);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        //Agregar Layout y agregar id de elemento en layout
        View row = inflater.inflate(R.layout.meceros_disponibles,parent,false);
        spinnerModel = null;
        spinnerModel= (SpinnerModel) data.get(position);
        TextView label = (TextView) row.findViewById(R.id.mecerosDisponibles);
        //ImageView iconoTipo = (ImageView) row.findViewById(R.id.imagenSpinner);
        if (position==0)
        {
            label.setText("Meceros Disponible");
            //label.setTextColor(context.getApplicationContext().getResources().getColor(R.color.black));
        }
        else
        {
            label.setText(spinnerModel.dameNombre());
            label.setGravity(Gravity.CENTER);
            //iconoTipo.setImageResource(res.getIdentifier("com.solidary.solidary:drawable/"+spinnerModel.dameImagen(),null,null));
        }
        return row;
    }
}