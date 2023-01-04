package com.creathor.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Estacion extends AppCompatActivity {

    private ExecutorService executorService;

    private LinearLayout caja_lista_pedidos_recycler,caja_pedir_pedidos,caja_lista_pedidos,caja_pedidos_espera_l,
            caja_recycler_pedidos,caja_lista_espera_recycler;
    private ConstraintLayout caja_asignar_mecero,caja_confirmar_mecero,caja_velo_mecero,
            caja_detalle_pedido;
    private TextView pedir_pedidos,confirmar_mecero,confirmar_no,confirmar_si,texto_asignador;
    public ArrayList<SpinnerModel> listaMeceros = new ArrayList<>();
    private AdapterMeceros adapterMeceros;
    private String seleccion_mecero,selector_pedidos,strCadena,
            id_pedido_actual,id_encontrada,comanda_encontrada,mesa_encontrada,precio_encontrado,
            fecha_encontrada,contenido_encontrado, id_negocio,idSesion,meseroAsignado,id_mesero,strcontenido,strIdPedido,
            strFecha_ingreso,strMesa,strPrecio,strComanda;
    private Estacion activity;
    private RecyclerView lista_pedidos_recycler,lista_espera_recycler,meceros_disponibles,recycler_detalle_pedido;
    private AdapterListaPedidos adapterListaPedidos;
    private AdapterListaPedidosESPERA adapterListaEspera;
    private AdapterDetallePedido adapterDetallePedido;
    private ArrayList <ListaPedidosRecyclerCorta>listaPedidosRecyclerCortas;
    private ArrayList <ListaPedidosRecycler> listaPedidosRecyclers, listaPedidosAsignados,listaDetallesRecycler;
    private ArrayList <ListaMeserosDisponibles> listaMeserosDisponibles;
    private AdapterMeserosDisponibles adapterMeserosDisponibles;
    private JSONArray json_pedido,json_pedido_espera,json_meseros_disponibles,json_contenido_pedido;
    private Context context;
    private static String SERVIDOR_CONTROLADOR;
    private SharedPreferences id_SesionSher,idSher,nombreMeseroSher,id_meseroSher;
    private SharedPreferences.Editor editorNombreMesero;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_estacion);

        activity = this;
        context=this;
        SERVIDOR_CONTROLADOR = new Servidor().getIplocal();

        caja_lista_pedidos_recycler=findViewById(R.id.caja_lista_pedidos_recycler);
        meceros_disponibles=findViewById(R.id.meceros_disponibles);
        lista_pedidos_recycler=findViewById(R.id.lista_pedidos_recycler);
        caja_asignar_mecero=findViewById(R.id.caja_asignar_mecero);
        confirmar_mecero=findViewById(R.id.confirmar_mecero);
        caja_confirmar_mecero=findViewById(R.id.caja_confirmar_mecero);
        caja_velo_mecero=findViewById(R.id.caja_velo_mecero);
        confirmar_no=findViewById(R.id.confirmar_no);
        confirmar_si=findViewById(R.id.confirmar_si);
        texto_asignador=findViewById(R.id.texto_asignador);
        lista_espera_recycler=findViewById(R.id.lista_espera_recycler);
        executorService= Executors.newSingleThreadExecutor();

        caja_lista_pedidos=findViewById(R.id.caja_lista_pedidos);
        caja_pedidos_espera_l=findViewById(R.id.caja_pedidos_espera_l);
        caja_recycler_pedidos=findViewById(R.id.caja_recycler_pedidos);
        Log.e("meseros2",""+editorNombreMesero);
        caja_lista_espera_recycler=findViewById(R.id.caja_lista_espera_recycler);
        caja_detalle_pedido=findViewById(R.id.caja_detalle_pedido);
        recycler_detalle_pedido=findViewById(R.id.recycler_detalle_pedido);

       /* checkModel=modeloSHER.getString("modelo","no");*/
        // pedir_pedidos_meceros();

        listaPedidosRecyclerCortas=new ArrayList<>();
        lista_pedidos_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this, LinearLayoutManager.VERTICAL, false));
        listaPedidosAsignados=new ArrayList<>();
        lista_espera_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        listaMeserosDisponibles=new ArrayList<>();
        meceros_disponibles.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        listaDetallesRecycler=new ArrayList<>();
        recycler_detalle_pedido.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        id_SesionSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        idSesion= id_SesionSher.getString("idSesion","no hay");
        idSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        id_negocio = idSher.getString("idSesion","no hay");

        pedir_pedidos();
        pedir_pedidos_espera();




        confirmar_mecero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreMeseroSher=getSharedPreferences("meserosDisponibles",context.MODE_PRIVATE);
                Log.e("mesero1",""+nombreMeseroSher);
                meseroAsignado=nombreMeseroSher.getString("meserosNombres","nohaymesero");
                id_meseroSher=getSharedPreferences("meserosDisponibles",context.MODE_PRIVATE);
                id_mesero=id_meseroSher.getString("idMesero","nohaymesero");

                Log.e("mesero2",""+meseroAsignado);
                caja_velo_mecero.setVisibility(view.VISIBLE);
                caja_confirmar_mecero.setVisibility(View.VISIBLE);
                texto_asignador.setText("desea asingar a "+" "+meseroAsignado+" "+"a esta mesa");
            }
        });
        confirmar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_velo_mecero.setVisibility(view.GONE);
                caja_confirmar_mecero.setVisibility(View.GONE);
            }
        });
        confirmar_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<listaPedidosRecyclers.size();i++){
                    String id_tmp=listaPedidosRecyclers.get(i).getId();

                    if(id_tmp.equals(id_pedido_actual)){

                        Log.e("id_encontrada","en ciclo "+i);
                        listaPedidosRecyclers.get(i).setMecero_asignado(meseroAsignado);
                        listaPedidosRecyclers.get(i).setId_mesero(id_mesero);

                        id_encontrada=listaPedidosRecyclers.get(i).getId();
                        mesa_encontrada=listaPedidosRecyclers.get(i).getMesa();
                        comanda_encontrada=listaPedidosRecyclers.get(i).getComanda();
                        precio_encontrado=listaPedidosRecyclers.get(i).getPrecio();
                        fecha_encontrada=listaPedidosRecyclers.get(i).getFecha_ingreso();
                        contenido_encontrado=listaPedidosRecyclers.get(i).getContenido();


                        Log.e("id",""+id_encontrada);
                        Log.e("mesa",""+mesa_encontrada);
                        Log.e("comanda",""+comanda_encontrada);
                        Log.e("precio",""+precio_encontrado);
                        Log.e("fecha",""+fecha_encontrada);

                        Log.e("mesero",""+meseroAsignado);
                        Log.e("id_mesero",""+id_mesero);

/*
                        listaPedidosAsignados.add( new ListaPedidosRecycler(id_encontrada,mesa_encontrada,comanda_encontrada,precio_encontrado,fecha_encontrada,meseroAsignado,id_mesero,contenido_encontrado));
                        Log.e("listaNueva",""+comanda_encontrada);*/
                        listaPedidosRecyclers.remove(i);
                        caja_asignar_mecero.setVisibility(View.GONE);
                        caja_confirmar_mecero.setVisibility(View.GONE);
                        caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                actualizar_pedido_cliente();
                                Log.e("entnedi_señor_calamardo","y esto igual");
                            }
                        });
                    }
                }
             /*   lista_pedidos_recycler.setAdapter(adapterListaPedidos);
                adapterListaEspera=new AdapterListaPedidosESPERA(listaPedidosAsignados);
                lista_espera_recycler.setAdapter(adapterListaEspera);*/

            }
        });


        caja_pedidos_espera_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.GONE);
                caja_lista_espera_recycler.setVisibility(View.VISIBLE);

            }
        });
        caja_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.VISIBLE);
                caja_lista_espera_recycler.setVisibility(View.GONE);
            }
        });

    }
    public void pedir_pedidos()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosEstacion.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String limpio=response;
                        Log.e("jsonObject:",""+response);
                        Log.e("jsonObject2222:",""+limpio);
                        try {

                            json_pedido=new JSONArray(response);
                            for (int i=0;i<json_pedido.length();i++){
                                JSONObject jsonObject = json_pedido.getJSONObject(i);
                                //Log.e("nombreMovies", String.valueOf(jsonObject));
                                strIdPedido = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");

                             /*   strcontenido=jsonObject.getString("contenido");*/
                                listaPedidosRecyclerCortas.add(new ListaPedidosRecyclerCorta(strIdPedido,strMesa,strComanda,strPrecio,strFecha_ingreso));
                                Log.e("pedidos",strIdPedido);
                            }
                            adapterListaPedidos=new AdapterListaPedidos(listaPedidosRecyclerCortas);
                            lista_pedidos_recycler.setAdapter(adapterListaPedidos);
                            //recycler_movies.scrollToPosition(0);
                            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
                            //bloquearMenu();
                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonapedidos:",""+json_pedido);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", id_negocio);
                map.put("idSesion",idSesion);
                return map;
            }
        };
        requestQueue.add(request);
    }
    public void pedir_pedidos_espera()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosEstacionEspera.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        String limpio=response;
                       /* Log.e("jsonObject:",""+response);
                        Log.e("jsonObject2222:",""+limpio);*/
                        try {

                            json_pedido_espera=new JSONArray(response);
                            for (int i=0;i<json_pedido_espera.length();i++){
                                JSONObject jsonObject = json_pedido_espera.getJSONObject(i);

                                //Log.e("nombreMovies", String.valueOf(jsonObject));

                                String strId = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                                String strMecero=jsonObject.getString("meseroAsignado");
                                String strid_mesero=jsonObject.getString("id_mesero");
                                String strContenidp=jsonObject.getString("contenido");
                                String strFecha_entrega = jsonObject.getString("fecha_entrega");
                                String strFecha_final = jsonObject.getString("fecha_final");
                              /*  listaPedidosAsignados.add( new ListaPedidosRecycler(strId,strMesa,strComanda,strPrecio,strFecha_ingreso,strMecero,strid_mesero,strContenidp));

*/
                                Log.e("pedidos",strComanda);
                            }

                        /*    adapterListaEspera=new AdapterListaPedidosESPERA(listaPedidosAsignados);
                            lista_espera_recycler.setAdapter(adapterListaEspera);*/

                            //recycler_movies.scrollToPosition(0);



                            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
                            //bloquearMenu();


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonapedidos:",""+json_pedido);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }



                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", strIdPedido);
                map.put("idSesion",idSesion);
                return map;
            }
        };
        requestQueue.add(request);
    }


    /*public void setListaPedidos()
    {
        listaPedidosRecyclers.clear();
        String coy[] = {"papa a las francesa","milagruesa de caballo",
                "pito entomatados","nalguitas de topo fritas","huevos a l gusto",};
        for (int i=0; i<coy.length;i++)
        {
            ListaPedidosRecycler listaPedidoRecycler = new ListaPedidosRecycler(coy[i]);

            selector_pedidos= listaPedidoRecycler.getId();

            listaPedidosRecyclers.add(listaPedidoRecycler);
            Log.e("tipomodel",""+selector_pedidos);


        }
    }*/
    public void setListaMeceros()
    {  RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"meserosDisponibles.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        String limpio=response;
                        Log.e("jsonObject:",""+response);
                        Log.e("jsonObject:",""+limpio);
                        JSONArray jsonArray = null;
                        try {

                            json_meseros_disponibles=new JSONArray(response);
                            for (int i=0;i<json_meseros_disponibles.length();i++){
                                JSONObject jsonObject = json_meseros_disponibles.getJSONObject(i);
                                Log.e("jsonObject2:",""+jsonObject);

                                //Log.e("nombreMovies", String.valueOf(jsonObject));

                                String strId = jsonObject.getString("id");
                                String strNombre = jsonObject.getString("nombre");
                                Log.e("idObject:",""+strId);
                                Log.e("jsonObject2:",""+strNombre);

                                listaMeserosDisponibles.add(new ListaMeserosDisponibles(strId,strNombre));

                                Log.e("Nombresher",""+meseroAsignado);

                            }

                             adapterMeserosDisponibles=new AdapterMeserosDisponibles(listaMeserosDisponibles);
                            meceros_disponibles.setAdapter(adapterMeserosDisponibles);


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonaraa:",""+json_pedido);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }



                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", id_negocio);
                map.put("idSesion",idSesion);
                return map;
            }
        };
        requestQueue.add(request);


    }

    public void aceptar_pedido(String id_pedido){
        id_pedido_actual=id_pedido;
        setListaMeceros();
        caja_asignar_mecero.setVisibility(View.VISIBLE);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        Log.e("id_activyty",""+id_pedido);

    }
    public void mostrarDetalle(String id_pedido,String fecha_ingreso,String mesa,String precio, String comanda){
        strIdPedido=id_pedido;
        strFecha_ingreso=fecha_ingreso;
        strMesa=mesa;
        strPrecio=precio;
        strComanda=comanda;

        caja_detalle_pedido.setVisibility(View.VISIBLE);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        pedir_contenido_pedido();
        /*try {
            for (int i=0;i<json_pedido.length();i++){
                JSONObject jsonObject = json_pedido.getJSONObject(i);

                //Log.e("nombreMovies", String.valueOf(jsonObject));

                String strId = jsonObject.getString("id");
                String strMesa = jsonObject.getString("mesa");
                String strComanda = jsonObject.getString("comanda");
                String strPrecio= jsonObject.getString("precio");
                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                String strMecero=jsonObject.getString("meseroAsignado");
                String strid_mesero=jsonObject.getString("id_mesero");

                String strFecha_entrega = jsonObject.getString("fecha_entrega");
                String strFecha_final = jsonObject.getString("fecha_final");
                strcontenido=jsonObject.getString("contenido");

                listaDetallesRecycler.add(new ListaPedidosRecycler(strId,strMesa,strComanda,strPrecio,strFecha_ingreso,strMecero,strid_mesero,strcontenido));

                Log.e("pedidos",strComanda);
            }

            adapterDetallePedido=new AdapterDetallePedido(listaDetallesRecycler);
            recycler_detalle_pedido.setAdapter(adapterDetallePedido);
            //recycler_movies.scrollToPosition(0);



            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
            //bloquearMenu();


        } catch (JSONException e) {
            Log.e("errorRespuestaMovies", String.valueOf(e));
        }*/

    }
    public  void pedir_contenido_pedido(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"contenido_pedido.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            json_contenido_pedido=new JSONArray(response);
                            for (int i=0;i<json_contenido_pedido.length();i++){
                                JSONObject jsonObject = json_contenido_pedido.getJSONObject(i);
                                Log.e("jsonObjectcotenid",""+jsonObject);
                                String strNombre = jsonObject.getString("nombre");
                                String strCantidad = jsonObject.getString("cantidad");
                                String strTotal = jsonObject.getString("total");
                                String strPrecio2 = jsonObject.getString("precio2");
                                String strExtras = jsonObject.getString("extras");
                                String strNota_mesero=jsonObject.getString("nota_mesero");
                                Log.e("jsonObject2:",""+strNombre);

                                listaMeserosDisponibles.add(new ListaMeserosDisponibles(strId,strNombre));

                                Log.e("Nombresher",""+meseroAsignado);

                            }

                            adapterMeserosDisponibles=new AdapterMeserosDisponibles(listaMeserosDisponibles);
                            meceros_disponibles.setAdapter(adapterMeserosDisponibles);


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonaraa:",""+json_pedido);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errorDeContenido:",error + "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                Log.e("id?contenido",strIdPedido);
                map.put("id",strIdPedido);
                map.put("idSesion",idSesion);

                return map;
            }
        };
        requestQueue.add(request);
    }
    public void actualizar_pedido_cliente()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"actualizar_pedido_cliente.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("respuesta4:",response + "sal");
                        if(response.equals("success")){
                            //Intent intent = new Intent(Registro.this,RegistroExitoso.class);
                            //startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respuesta4Error:",error + "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put("id_negocio",id_negocio);
                map.put("id",id_encontrada);
                map.put("fecha_ingreso",fecha_encontrada);
                map.put("meseroAsignado",meseroAsignado);
                map.put("id_mesero",id_mesero);

                return map;
            }
        };
        requestQueue.add(request);
    }
}
