package com.creathor.restaurante;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMeserosDisponibles extends RecyclerView.Adapter<AdapterMeserosDisponibles.ViewHolderRecycler>{
private ArrayList<ListaMeserosDisponibles> meserosDisponiblesRecycler;
        ViewHolderRecycler viewholderListaMeserosDisponibles;
private  RecyclerView recyclerView;
private Context context;
private String id_meseros, nombre_mesero;
private TextView aceptar_pedido;
private AdapterListaPedidos activity;


public AdapterMeserosDisponibles(ArrayList<ListaMeserosDisponibles> meseroDisponiblerecycler )
        {
        this.meserosDisponiblesRecycler =meseroDisponiblerecycler;
        }
@Override
public ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new ViewHolderRecycler(vista);

        }

@Override
public void onBindViewHolder(@NonNull AdapterMeserosDisponibles.ViewHolderRecycler holder, int position) {
        viewholderListaMeserosDisponibles =holder;
        id_meseros = meserosDisponiblesRecycler.get(position).getId_mesero();
        nombre_mesero = meserosDisponiblesRecycler.get(position).getNombre_mesero();





        holder.id_meser.setText(id_meseros);
        holder.name_meser.setText(nombre_mesero);

        //holder.date_end.setText(fecha_final);



        //holder.list_pedidos.setText(lista_pedidos);
        }
public void pedidos_espera(String id_pedido){

        Log.e("id_activyty",""+id_pedido);
        }

@Override
public int getItemCount(){
        return meserosDisponiblesRecycler.size();

        }
public class ViewHolderRecycler extends RecyclerView.ViewHolder {
    TextView id_meser,name_meser;


    public ViewHolderRecycler(View itemView) {
        super(itemView);
        id_meser =(TextView)itemView.findViewById(R.id.id_mesero);
        name_meser =(TextView)itemView.findViewById(R.id.nombre_mesero);

    }
}

}