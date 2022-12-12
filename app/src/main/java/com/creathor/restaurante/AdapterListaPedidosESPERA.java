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

public class AdapterListaPedidosESPERA extends RecyclerView.Adapter<AdapterListaPedidosESPERA.ViewHolderRecycler>{
private ArrayList<ListaPedidosRecycler> pedidosEsperarecycler;
        ViewHolderRecycler viewholderListaPedidosEspera;
private  RecyclerView recyclerView;
private Context context;
private String id,mesa,comanda,precio,fecha_ingreso,mecero_asignado,fecha_final,id_mesero;
private TextView aceptar_pedido;
private AdapterListaPedidos activity;


public AdapterListaPedidosESPERA(ArrayList<ListaPedidosRecycler> pedidosEsperarecycler )
        {
        this.pedidosEsperarecycler =pedidosEsperarecycler;
        }
@Override
public ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new ViewHolderRecycler(vista);

        }

@Override
public void onBindViewHolder(@NonNull AdapterListaPedidosESPERA.ViewHolderRecycler holder, int position) {
        viewholderListaPedidosEspera =holder;
        id = pedidosEsperarecycler.get(position).getId();
        mesa= pedidosEsperarecycler.get(position).getMesa();
        comanda= pedidosEsperarecycler.get(position).getComanda();
        precio= pedidosEsperarecycler.get(position).getPrecio();
        fecha_ingreso= pedidosEsperarecycler.get(position).getFecha_ingreso();
        mecero_asignado= pedidosEsperarecycler.get(position).getMecero_asignado();
        id_mesero= pedidosEsperarecycler.get(position).getId_mesero();

       // fecha_final= pedidosrecycler.get(position).getFecha_final();




        holder.id_food.setText(id);
        holder.mes.setText(mesa);
        holder.comand.setText(comanda);
        holder.price.setText(precio);
        holder.date_star.setText(fecha_ingreso);
        holder.mecer_asigned.setText(mecero_asignado);
        holder.id_meser.setText(id_mesero);

        //holder.date_end.setText(fecha_final);



        //holder.list_pedidos.setText(lista_pedidos);
        }
    public void pedidos_espera(String id_pedido){

        Log.e("id_activyty",""+id_pedido);
    }

@Override
public int getItemCount(){
        return pedidosEsperarecycler.size();

        }
public class ViewHolderRecycler extends RecyclerView.ViewHolder {
    TextView id_food,mes,comand,price,date_star,date_entrega,date_end,acept_pedido,rech_pedido,mecer_asigned,id_meser;


    public ViewHolderRecycler(View itemView) {
        super(itemView);
        id_food =(TextView)itemView.findViewById(R.id.id);
        acept_pedido =(TextView)itemView.findViewById(R.id.aceptar_pedido);
        rech_pedido =(TextView)itemView.findViewById(R.id.rechazar_pedido);
        mes =(TextView)itemView.findViewById(R.id.mesa);
        comand =(TextView)itemView.findViewById(R.id.comanda);
        price =(TextView)itemView.findViewById(R.id.precio);
        date_star =(TextView)itemView.findViewById(R.id.fecha_ingreso);
        date_entrega =(TextView)itemView.findViewById(R.id.fecha_entrega);
        date_end =(TextView)itemView.findViewById(R.id.fecha_final);
        mecer_asigned =(TextView)itemView.findViewById(R.id.mecero_asignado);
        id_meser=itemView.findViewById(R.id.id_mesero);



    }
}

}