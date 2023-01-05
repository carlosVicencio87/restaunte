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

public class AdapterDetallePedido extends RecyclerView.Adapter<AdapterDetallePedido.ViewHolderRecycler>{
    private ArrayList<ListaPedidosRecycler> detalleRecycler;
    AdapterDetallePedido.ViewHolderRecycler viewholderDetallePedidos;
    private  RecyclerView recyclerView;
    private Context context;
    private String id,nombre,cantidad,total,precio2,extra,nota_mesero,mecero_asignado,fecha_entrega,fecha_final;
    private TextView aceptar_pedido;
    private AdapterDetallePedido activity;
    private LinearLayout caja_mecero_asignado;


    public AdapterDetallePedido(ArrayList<ListaPedidosRecycler> pedidosrecycler )
    {
        this.detalleRecycler =pedidosrecycler;
    }
    @Override
    public AdapterDetallePedido.ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item4,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new AdapterDetallePedido.ViewHolderRecycler(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetallePedido.ViewHolderRecycler holder, int position) {
        viewholderDetallePedidos =holder;

        nombre=detalleRecycler.get(position).getNombre();
        cantidad=detalleRecycler.get(position).getCantidad();
        total=detalleRecycler.get(position).getTotal();
        precio2=detalleRecycler.get(position).getPrecio2();
        extra=detalleRecycler.get(position).getExtras();
        nota_mesero=detalleRecycler.get(position).getNota_mesero();
        /*fecha_entrega= pedidosrecycler.get(position).getFecha_entrega();
        fecha_final= pedidosrecycler.get(position).getFecha_final();*/



        holder.name.setText(nombre);
        holder.details.setText(cantidad);
        holder.totl.setText(total);
        holder.price2.setText(precio2);
        holder.extr.setText(extra);
        holder.note_meser.setText(nota_mesero);

        /*holder.acept_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicion=holder.getAdapterPosition();
                id = detalleRecycler.get(posicion).getId();
                Log.e("id","2"+id);
                ((Estacion)context).aceptar_pedido(id);

            }
        });*/

        Log.e("meceero","1"+mecero_asignado);


        //holder.list_pedidos.setText(lista_pedidos);
    }

    @Override
    public int getItemCount(){
        return detalleRecycler.size();

    }
    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView id_food,mes,comand,price,date_star,mecer_asigned,date_entrega,date_end,acept_pedido,rech_pedido
                ,name,details,price2,extr,note_meser,totl;
        LinearLayout box_mecero_asignado,box_decisiones,box_preparando,view_detalle_pedido;


        public ViewHolderRecycler(View itemView) {
            super(itemView);
            id_food =(TextView)itemView.findViewById(R.id.id);
            mes =(TextView)itemView.findViewById(R.id.mesa);
            comand =(TextView)itemView.findViewById(R.id.comanda);
            price =(TextView)itemView.findViewById(R.id.precio);
            date_star =(TextView)itemView.findViewById(R.id.fecha_ingreso);
            date_entrega =(TextView)itemView.findViewById(R.id.fecha_entrega);
            date_end =(TextView)itemView.findViewById(R.id.fecha_final);
            mecer_asigned =(TextView)itemView.findViewById(R.id.mecero_asignado);
            box_mecero_asignado = (LinearLayout) itemView.findViewById(R.id.caja_mecero_asignado);
            box_decisiones = (LinearLayout) itemView.findViewById(R.id.caja_decisiones);
            box_preparando = (LinearLayout) itemView.findViewById(R.id.caja_preparando);
            view_detalle_pedido=itemView.findViewById(R.id.ver_detalle_pedido);
            name=itemView.findViewById(R.id.nombre);
            details=itemView.findViewById(R.id.cantidad);
            price2=itemView.findViewById(R.id.precio2);
            extr=itemView.findViewById(R.id.extras);
            note_meser=itemView.findViewById(R.id.nota_mesero);
            totl=itemView.findViewById(R.id.total);
        }
    }

}
