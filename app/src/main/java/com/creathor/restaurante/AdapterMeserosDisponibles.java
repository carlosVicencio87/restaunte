package com.creathor.restaurante;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMeserosDisponibles extends RecyclerView.Adapter<AdapterMeserosDisponibles.ViewHolderRecycler>{
private ArrayList<ListaMeserosDisponibles> meserosDisponiblesRecycler;
        ViewHolderRecycler viewholderListaMeserosDisponibles,anterior;
private  RecyclerView recyclerView;
private Context context;
private String id_meseros, nombre_mesero;
private TextView aceptar_pedido;
private AdapterListaPedidos activity;
private SharedPreferences datosMeseros;
private SharedPreferences.Editor editorMeseros;

public AdapterMeserosDisponibles(ArrayList<ListaMeserosDisponibles> meseroDisponiblerecycler )
        {
        this.meserosDisponiblesRecycler =meseroDisponiblerecycler;
        }
@Override
public ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
         datosMeseros= context.getSharedPreferences("meserosDisponibles",context.MODE_PRIVATE);
        editorMeseros=datosMeseros.edit();
        return new ViewHolderRecycler(vista);

        }

@Override
public void onBindViewHolder(@NonNull ViewHolderRecycler holder, int position) {
        viewholderListaMeserosDisponibles =holder;
        id_meseros = meserosDisponiblesRecycler.get(position).getId_mesero();
        nombre_mesero = meserosDisponiblesRecycler.get(position).getNombre_mesero();
        holder.id_meser.setText(id_meseros);
        holder.name_meser.setText(nombre_mesero);

        final String nombreMesero  =  holder.name_meser.getText().toString();
        holder.caja_marco_mesero.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(context,nombreMesero,Toast.LENGTH_SHORT).show();
                editorMeseros.putString("meserosNombres",nombreMesero);
                editorMeseros.apply();
                Log.e("cambio",datosMeseros.getString("meserosNombres","no"));
                Log.e("index", String.valueOf(holder.getAdapterPosition()));

                int posicion_tmp= holder.getAdapterPosition();

                if (anterior!=null)
                {
                    anterior.caja_marco_mesero.setBackgroundResource(R.color.white);
                    anterior.name_meser.setTextColor(context.getResources().getColor(R.color.black));
                }

                Log.e("holdr", String.valueOf(holder));

                holder.caja_marco_mesero.setBackgroundResource(R.color.black);
                holder.name_meser.setTextColor(context.getResources().getColor(R.color.white));


                /*((Estacion)context).definirAlcance(nombreMesero);*/

                anterior=holder;

            }
        });
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
    LinearLayout caja_marco_mesero;

    public ViewHolderRecycler(View itemView) {
        super(itemView);
        id_meser =(TextView)itemView.findViewById(R.id.id_mesero);
        name_meser =(TextView)itemView.findViewById(R.id.nombre_mesero);
        caja_marco_mesero=itemView.findViewById(R.id.caja_marco_mesero);
    }
}

}