package com.creathor.restaurante;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterListaPedidosCocina  extends RecyclerView.Adapter<AdapterListaPedidosCocina.ViewHolderRecycler>{
    private ArrayList<ListaPedidosRecyclerCorta> pedidosCocinarecycler;
    AdapterListaPedidosCocina.ViewHolderRecycler viewholderListaPedidosEspera;
    private  RecyclerView recyclerView;
    private Context context;
    private String id,mesa,comanda,precio,fecha_ingreso,mecero_asignado,estado,fecha_final,id_mesero;
    private TextView aceptar_pedido;
    private AdapterListaPedidos activity;


    public AdapterListaPedidosCocina(ArrayList<ListaPedidosRecyclerCorta> pedidosCocinarecycler )
    {
        this.pedidosCocinarecycler =pedidosCocinarecycler;
    }
    @Override
    public AdapterListaPedidosCocina.ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item5,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new AdapterListaPedidosCocina.ViewHolderRecycler(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaPedidosCocina.ViewHolderRecycler holder, int position) {
        viewholderListaPedidosEspera =holder;
        id = pedidosCocinarecycler.get(position).getId();
        mesa= pedidosCocinarecycler.get(position).getMesa();
        comanda= pedidosCocinarecycler.get(position).getComanda();
        precio= pedidosCocinarecycler.get(position).getPrecio();
        fecha_ingreso= pedidosCocinarecycler.get(position).getFecha_ingreso();
        mecero_asignado= pedidosCocinarecycler.get(position).getMecero_asignado();
        id_mesero= pedidosCocinarecycler.get(position).getId_mesero();

        // fecha_final= pedidosrecycler.get(position).getFecha_final();




        holder.id_food.setText(id);
        holder.mes.setText(mesa);
        holder.comand.setText(comanda);
        holder.price.setText(precio);
        holder.date_star.setText(fecha_ingreso);
        holder.mecer_asigned.setText(mecero_asignado);

        //holder.date_end.setText(fecha_final);



        //holder.list_pedidos.setText(lista_pedidos);
        holder.view_detalle_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posiTion2=holder.getAdapterPosition();
                id = pedidosCocinarecycler.get(posiTion2).getId();
                fecha_ingreso= pedidosCocinarecycler.get(posiTion2).getFecha_ingreso();
                mesa= pedidosCocinarecycler.get(posiTion2).getMesa();
                precio= pedidosCocinarecycler.get(posiTion2).getPrecio();
                comanda= pedidosCocinarecycler.get(posiTion2).getComanda();
                mecero_asignado= pedidosCocinarecycler.get(posiTion2).getMecero_asignado();
                estado= pedidosCocinarecycler.get(posiTion2).getEstado();
                Log.e("id","2"+id);
                ((Estacion)context).mostrarDetalleCocina(id,fecha_ingreso,mesa,precio,comanda,mecero_asignado,id_mesero,estado);

            }
        });
    }
    public void pedidos_espera(String id_pedido){

        Log.e("id_activyty",""+id_pedido);
    }


    @Override
    public int getItemCount(){
        return pedidosCocinarecycler.size();
    }
    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView id_food,mes,comand,price,date_star,date_entrega,date_end,acept_pedido,rech_pedido,mecer_asigned,id_meser;
        LinearLayout view_detalle_pedido;


        public ViewHolderRecycler(View itemView) {
            super(itemView);
            date_star =(TextView)itemView.findViewById(R.id.fecha_ingreso);
            mes =(TextView)itemView.findViewById(R.id.mesa);
            price =(TextView)itemView.findViewById(R.id.precio);
            mecer_asigned =(TextView)itemView.findViewById(R.id.mecero_asignado);
            comand =(TextView)itemView.findViewById(R.id.comanda);
            id_food =(TextView)itemView.findViewById(R.id.id);
            date_entrega =(TextView)itemView.findViewById(R.id.fecha_entrega);
            date_end =(TextView)itemView.findViewById(R.id.fecha_final);
            view_detalle_pedido=itemView.findViewById(R.id.ver_detalle_pedido);
        }
    }

}