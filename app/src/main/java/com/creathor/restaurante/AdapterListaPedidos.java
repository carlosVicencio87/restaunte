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

public class AdapterListaPedidos extends RecyclerView.Adapter<AdapterListaPedidos.ViewHolderRecycler>{
    private ArrayList<ListaPedidosRecyclerCorta> pedidosrecycler;
    ViewHolderRecycler viewholderListaPedidos;
    private  RecyclerView recyclerView;
    private Context context;
    private String id,mesa,comanda,precio,fecha_ingreso,mecero_asignado,fecha_entrega,fecha_final;
    private TextView aceptar_pedido;
    private AdapterListaPedidos activity;
    private  LinearLayout caja_mecero_asignado;


    public AdapterListaPedidos(ArrayList<ListaPedidosRecyclerCorta> pedidosrecycler )
    {
        this.pedidosrecycler =pedidosrecycler;
    }
    @Override
    public ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new ViewHolderRecycler(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaPedidos.ViewHolderRecycler holder, int position) {
        viewholderListaPedidos =holder;
        id = pedidosrecycler.get(position).getId();
        mesa= pedidosrecycler.get(position).getMesa();
        comanda= pedidosrecycler.get(position).getComanda();
        precio= pedidosrecycler.get(position).getPrecio();
        fecha_ingreso= pedidosrecycler.get(position).getFecha_ingreso();

        /*fecha_entrega= pedidosrecycler.get(position).getFecha_entrega();
        fecha_final= pedidosrecycler.get(position).getFecha_final();*/




        holder.id_food.setText(id);
        holder.mes.setText(mesa);
        holder.comand.setText(comanda);
        holder.price.setText(precio);
        holder.date_star.setText(fecha_ingreso);
      /*  holder.date_entrega.setText(fecha_entrega);
        holder.date_end.setText(fecha_final);*/

    /*    if (!mecero_asignado.equals("")){

            holder.box_decisiones.setVisibility(View.GONE);
            holder.box_preparando.setVisibility(View.VISIBLE);
            holder.box_mecero_asignado.setVisibility(View.VISIBLE);
            holder.mecer_asigned.setText(mecero_asignado);

            Log.e("ya_esta_mecero","2"+mecero_asignado);

        }
        else{
           holder.mecer_asigned.setVisibility(View.GONE);
            holder.box_decisiones.setVisibility(View.VISIBLE);
            holder.box_preparando.setVisibility(View.GONE);

        }
*/
        holder.acept_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicion=holder.getAdapterPosition();
                id = pedidosrecycler.get(posicion).getId();
                Log.e("id","2"+id);
                ((Estacion)context).aceptar_pedido(id);

            }
        });
        holder.view_detalle_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posiTion2=holder.getAdapterPosition();
                id = pedidosrecycler.get(posiTion2).getId();
                fecha_ingreso=pedidosrecycler.get(posiTion2).getFecha_ingreso();
                mesa=pedidosrecycler.get(posiTion2).getMesa();
                precio=pedidosrecycler.get(posiTion2).getPrecio();
                comanda=pedidosrecycler.get(posiTion2).getComanda();

                Log.e("id","2"+id);
                ((Estacion)context).mostrarDetalle(id,fecha_ingreso,mesa,precio,comanda);

            }
        });
            Log.e("meceero","1"+mecero_asignado);


        //holder.list_pedidos.setText(lista_pedidos);
    }

    @Override
    public int getItemCount(){
        return pedidosrecycler.size();

    }
    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView id_food,mes,comand,price,date_star,mecer_asigned,acept_pedido,rech_pedido;
        LinearLayout box_mecero_asignado,box_decisiones,box_preparando,view_detalle_pedido;


        public ViewHolderRecycler(View itemView) {
            super(itemView);
            id_food =(TextView)itemView.findViewById(R.id.id);
            acept_pedido =(TextView)itemView.findViewById(R.id.aceptar_pedido);
            rech_pedido =(TextView)itemView.findViewById(R.id.rechazar_pedido);
            mes =(TextView)itemView.findViewById(R.id.mesa);
            comand =(TextView)itemView.findViewById(R.id.comanda);
            price =(TextView)itemView.findViewById(R.id.precio);
            date_star =(TextView)itemView.findViewById(R.id.fecha_ingreso);
            mecer_asigned =(TextView)itemView.findViewById(R.id.mecero_asignado);
            box_mecero_asignado = (LinearLayout) itemView.findViewById(R.id.caja_mecero_asignado);
            box_decisiones = (LinearLayout) itemView.findViewById(R.id.caja_decisiones);
            box_preparando = (LinearLayout) itemView.findViewById(R.id.caja_preparando);
            view_detalle_pedido=itemView.findViewById(R.id.ver_detalle_pedido);


        }
    }

}