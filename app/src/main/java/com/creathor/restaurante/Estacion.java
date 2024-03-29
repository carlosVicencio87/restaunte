package com.creathor.restaurante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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
            caja_recycler_pedidos,caja_lista_espera_recycler,caja_lista_cocina_recycler,caja_pedidos_espera_cocina;
    private ConstraintLayout caja_asignar_mecero,caja_confirmar_mecero,caja_velo_mecero,
            caja_detalle_pedido,caja_detalle_pedido_espera,caja_detalle_pedido_cocina;
    private TextView pedir_pedidos,confirmar_mecero,confirmar_no,confirmar_si,texto_asignador,fecha_ingresoView,mesaView,precio2View,estadoView,comandaView,
            asignar_meseroView,fecha_ingreso_espera,mesa_espera,precio2_espera,
            estado_espera,comanda_espera,mecero_asignado_espera,fecha_ingreso_cocina,mesa_cocina,precio2_cocina,estado_cocina,comanda_cocina,mecero_asignado_cocina;
    public ArrayList<SpinnerModel> listaMeceros = new ArrayList<>();
    private AdapterMeceros adapterMeceros;
    private String seleccion_mecero,selector_pedidos,strCadena,
            id_pedido_actual, strIdPedido,comanda_encontrada,mesa_encontrada,precio_encontrado,
            fecha_encontrada,contenido_encontrado, id_negocio,idSesion, fireCode, strMecero_asignado,id_mesero,
            strcontenido,strFecha_ingreso,strMesa,strPrecio,strComanda,Strid_mesero,strEstado,adonde_vas,tituloNotificacion,contenidoNotificacion;
    private Estacion activity;
    private RecyclerView lista_pedidos_recycler,lista_espera_recycler,meceros_disponibles,
            recycler_detalle_pedido,recycler_detalle_pedido_espera,cocina_espera_recycler,recycler_detalle_pedido_cocina;
    private AdapterListaPedidos adapterListaPedidos;
    private AdapterListaPedidosESPERA adapterListaEspera;
    private AdapterDetallePedido adapterDetallePedido;
    private AdapterDetallePedidoEspera adapterDetallePedidoEspera;
    private AdapterListaPedidosCocina adapterListaPedidosCocina;
    private ArrayList <ListaPedidosRecyclerCorta>listaPedidosRecyclerCortas,listaPedidosAsignados,listaPedidosCocina;
    private ArrayList <ListaPedidosRecycler> listaPedidosRecyclers, listaPedidosRecyclersEspera;
    private ArrayList <ListaMeserosDisponibles> listaMeserosDisponibles;
    private ArrayList <ListaPedidoContenidoCocina>listaPedidoContenidoCocinas;
    private AdapterDetallePedidoCocina adapterDetallePedidoCocina;
    private AdapterMeserosDisponibles adapterMeserosDisponibles;
    private JSONArray json_pedido,json_pedido_espera,json_meseros_disponibles,json_contenido_pedido,json_pedido_cocina;
    private Context context;
    private static String SERVIDOR_CONTROLADOR;
    private SharedPreferences id_SesionSher,idSher,nombreMeseroSher,id_meseroSher,firecoSher;
    private SharedPreferences.Editor editorNombreMesero;
    private String CANALCOMUNICACION="CanalCRreathor";
    private String channel_nombre="CapitanesCanal";
    private String channel_description="01";
    private int cuenta,cuentaAnterior;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_estacion);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        String strToken=token;
                        // Log and toast
                        Log.e("TOKEN",strToken);
                        System.out.println(token);
                        Toast.makeText(Estacion.this, "el token de registro es"+token, Toast.LENGTH_SHORT).show();
                    }
                });

        activity = this;
        context = this;
        SERVIDOR_CONTROLADOR = new Servidor().getIplocal();

        caja_lista_pedidos_recycler = findViewById(R.id.caja_lista_pedidos_recycler);
        meceros_disponibles = findViewById(R.id.meceros_disponibles);
        lista_pedidos_recycler = findViewById(R.id.lista_pedidos_recycler);
        caja_asignar_mecero = findViewById(R.id.caja_asignar_mecero);
        confirmar_mecero = findViewById(R.id.confirmar_mecero);
        caja_confirmar_mecero = findViewById(R.id.caja_confirmar_mecero);
        caja_velo_mecero = findViewById(R.id.caja_velo_mecero);
        confirmar_no = findViewById(R.id.confirmar_no);
        confirmar_si = findViewById(R.id.confirmar_si);
        texto_asignador = findViewById(R.id.texto_asignador);
        lista_espera_recycler = findViewById(R.id.lista_espera_recycler);
        executorService = Executors.newSingleThreadExecutor();

        caja_lista_pedidos = findViewById(R.id.caja_lista_pedidos);
        caja_pedidos_espera_l = findViewById(R.id.caja_pedidos_espera_l);
        caja_recycler_pedidos = findViewById(R.id.caja_recycler_pedidos);
        Log.e("meseros2", "" + editorNombreMesero);
        caja_lista_espera_recycler = findViewById(R.id.caja_lista_espera_recycler);
        caja_detalle_pedido = findViewById(R.id.caja_detalle_pedido);
        recycler_detalle_pedido = findViewById(R.id.recycler_detalle_pedido);
        recycler_detalle_pedido_espera=findViewById(R.id.recycler_detalle_pedido_espera);
        recycler_detalle_pedido_cocina=findViewById(R.id.recycler_detalle_pedido_cocina);
        fecha_ingresoView = findViewById(R.id.fecha_ingreso);
        precio2View = findViewById(R.id.precio2);
        mesaView = findViewById(R.id.mesa);
        estadoView = findViewById(R.id.estado);
        comandaView = findViewById(R.id.comanda);
        asignar_meseroView = findViewById(R.id.asignar_meseroView);


        caja_detalle_pedido_espera=findViewById(R.id.caja_detalle_pedido_espera);
        fecha_ingreso_espera=findViewById(R.id.fecha_ingreso_espera);
        mesa_espera=findViewById(R.id.mesa_espera);
        precio2_espera=findViewById(R.id.precio2_espera);
        estado_espera=findViewById(R.id.estado_espera);
        comanda_espera=findViewById(R.id.comanda_espera);
        mecero_asignado_espera=findViewById(R.id.mecero_asignado_espera);
        caja_lista_cocina_recycler=findViewById(R.id.caja_lista_cocina_recycler);
        caja_pedidos_espera_cocina=findViewById(R.id.caja_pedidos_espera_cocina);
        cocina_espera_recycler=findViewById(R.id.cocina_espera_recycler);
        caja_detalle_pedido_cocina=findViewById(R.id.caja_detalle_pedido_cocina);

        fecha_ingreso_cocina=findViewById(R.id.fecha_ingreso_cocina);
        mesa_cocina=findViewById(R.id.mesa_cocina);
        precio2_cocina=findViewById(R.id.precio2_cocina);
        estado_cocina=findViewById(R.id.estado_cocina);
        comanda_cocina=findViewById(R.id.comanda_cocina);
        mecero_asignado_cocina=findViewById(R.id.mecero_asignado_cocina);
       /* checkModel=modeloSHER.getString("modelo","no");*/
        // pedir_pedidos_meceros();

        listaPedidosRecyclerCortas=new ArrayList<>();
        lista_pedidos_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this, LinearLayoutManager.VERTICAL, false));
        listaPedidosAsignados=new ArrayList<>();
        lista_espera_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        listaMeserosDisponibles=new ArrayList<>();
        meceros_disponibles.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        listaPedidosRecyclers=new ArrayList<>();
        recycler_detalle_pedido.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
        listaPedidosRecyclersEspera=new ArrayList<>();
        recycler_detalle_pedido_espera.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
        listaPedidosCocina=new ArrayList<>();
        cocina_espera_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
        listaPedidoContenidoCocinas=new ArrayList<>();
        recycler_detalle_pedido_cocina.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
        id_SesionSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        idSesion= id_SesionSher.getString("idSesion","no hay");
        idSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        id_negocio = idSher.getString("id","no hay");
        firecoSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        fireCode =firecoSher.getString("fireCode","no hay");
        Log.e("fireCode", fireCode);

        adonde_vas="principal";
        tituloNotificacion="Tienes un nuevo pedido";
        contenidoNotificacion="Has recibido un pedido de la mesa 4";
        Log.e("adonde",adonde_vas);

        cuentaAnterior=0;
        pedir_pedidos();


        asignar_meseroView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaMeserosDisponibles=new ArrayList<>();
                setListaMeceros();
                adonde_vas="meseros2";
                Log.e("adonde",adonde_vas);
                caja_asignar_mecero.setVisibility(View.VISIBLE);
                caja_lista_pedidos_recycler.setVisibility(View.GONE);

            }
        });
        confirmar_mecero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreMeseroSher=getSharedPreferences("meserosDisponibles",context.MODE_PRIVATE);
                Log.e("mesero1",""+nombreMeseroSher);
                strMecero_asignado =nombreMeseroSher.getString("meserosNombres","nohaymesero");
                id_meseroSher=getSharedPreferences("meserosDisponibles",context.MODE_PRIVATE);
                id_mesero=id_meseroSher.getString("idMesero","nohaymesero");

                Log.e("mesero2",""+ strMecero_asignado);
                caja_velo_mecero.setVisibility(view.VISIBLE);
                caja_confirmar_mecero.setVisibility(View.VISIBLE);
                texto_asignador.setText("desea asingar a "+" "+ strMecero_asignado +" "+"a esta mesa");
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
                Log.e("si","si hay decision");

                for (int i=0;i<listaPedidosRecyclerCortas.size();i++){
                    String id_tmp=listaPedidosRecyclerCortas.get(i).getId();
                    Log.e("si",""+id_tmp);

                    if(id_tmp.equals(strIdPedido)){

                        Log.e("id_encontrada","en ciclo "+i);
                        listaPedidosRecyclerCortas.get(i).setMecero_asignado(strMecero_asignado);
                        listaPedidosRecyclerCortas.get(i).setId_mesero(id_mesero);

                        strIdPedido =listaPedidosRecyclerCortas.get(i).getId();
                        mesa_encontrada=listaPedidosRecyclerCortas.get(i).getMesa();
                        comanda_encontrada=listaPedidosRecyclerCortas.get(i).getComanda();
                        precio_encontrado=listaPedidosRecyclerCortas.get(i).getPrecio();
                        fecha_encontrada=listaPedidosRecyclerCortas.get(i).getFecha_ingreso();


                        Log.e("id",""+ strIdPedido);
                        Log.e("mesa",""+mesa_encontrada);
                        Log.e("comanda",""+comanda_encontrada);
                        Log.e("precio",""+precio_encontrado);
                        Log.e("fecha",""+fecha_encontrada);

                        Log.e("mesero",""+ strMecero_asignado);
                        Log.e("id_mesero",""+id_mesero);

                        listaPedidosRecyclerCortas.add( new ListaPedidosRecyclerCorta(strIdPedido,mesa_encontrada,comanda_encontrada,precio_encontrado,fecha_encontrada, strMecero_asignado,id_mesero,contenido_encontrado));
                        Log.e("listaNueva",""+comanda_encontrada);
                        caja_asignar_mecero.setVisibility(View.GONE);
                        caja_confirmar_mecero.setVisibility(View.GONE);
                        caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
                        listaPedidosRecyclerCortas.remove(i);

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                actualizar_pedido_cliente();
                                Log.e("entnedi_señor_calamardo","y esto igual");
                            }
                        });
                    }
                }
           /*     lista_pedidos_recycler.setAdapter(adapterListaPedidos);
                adapterListaEspera=new AdapterListaPedidosESPERA(listaPedidosAsignados);
                lista_espera_recycler.setAdapter(adapterListaEspera);
*/
            }
        });


        caja_pedidos_espera_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaPedidosAsignados.clear();
                caja_recycler_pedidos.setVisibility(View.GONE);
                caja_lista_espera_recycler.setVisibility(View.VISIBLE);
                caja_lista_cocina_recycler.setVisibility(View.GONE);

                pedir_pedidos_espera();
                adonde_vas="pedidoAsignado";
                Log.e("adonde",adonde_vas);



            }
        });
        caja_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.VISIBLE);
                caja_lista_espera_recycler.setVisibility(View.GONE);
                caja_lista_cocina_recycler.setVisibility(View.GONE);
                pedir_pedidos();
                adonde_vas="principal";
                Log.e("adonde",adonde_vas);


            }
        });
        caja_pedidos_espera_cocina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaPedidosCocina.clear();
                listaPedidosCocina.clear();

                caja_recycler_pedidos.setVisibility(View.GONE);
                caja_lista_espera_recycler.setVisibility(View.GONE);
                caja_lista_cocina_recycler.setVisibility(View.VISIBLE);
                pedir_pedidos_cocina();
                adonde_vas="pedidoCocina";

            }
        });



    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        NotificationCompat.Builder constructorNotificacion = new NotificationCompat.Builder(this, CANALCOMUNICACION)
                .setSmallIcon(R.drawable.merkav_vertical)
                .setContentTitle(tituloNotificacion)
                .setContentText(contenidoNotificacion)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canalCreathor = new NotificationChannel(CANALCOMUNICACION, "canalComunicacion", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager nM = getSystemService(NotificationManager.class);
            nM.createNotificationChannel(canalCreathor);
            nM.notify(0,constructorNotificacion.build());
        }
    }

    public void pedir_pedidos()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosEstacion.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        listaPedidosRecyclerCortas.clear();
                        String limpio=response;
                        Log.e("jsonObject:",""+response);
                        Log.e("jsonObject2222:",""+limpio);
                        try {

                            json_pedido=new JSONArray(response);


                            for (int i=0;i<json_pedido.length();i++){
                                JSONObject jsonObject = json_pedido.getJSONObject(i);
                                 cuenta=json_pedido.length();
                                Log.e("cuentas:","00"+cuenta);
                                Log.e("cuentasA:","00"+cuentaAnterior);
                                if (cuenta!=(cuentaAnterior)){
                                    Log.e("cuentas:","00"+cuenta);
                                    Log.e("cuentasA:","00"+cuentaAnterior);
                                    cuentaAnterior=cuenta;
                                    createNotificationChannel();
                                    Log.e("cuentasA:","00"+cuentaAnterior);


                                }

                                Log.e("nombreMovies", String.valueOf(json_pedido.length()));
                                strIdPedido = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                                String strMeseroAsignado = jsonObject.getString("meseroAsignado");
                                String strId_mesero= jsonObject.getString("id_mesero");
                                String strEstado= jsonObject.getString("estado");

                             /*   strcontenido=jsonObject.getString("contenido");*/
                                listaPedidosRecyclerCortas.add(new ListaPedidosRecyclerCorta(strIdPedido,strMesa,strComanda,strPrecio,strFecha_ingreso,strMeseroAsignado,strId_mesero,strEstado));
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
                map.put("fireCode",fireCode);

                Log.e("idSESION",idSesion);
                Log.e("fireCode", fireCode);
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
                       Log.e("jsonObjectEspera:",""+response);
                        Log.e("jsonObject3333:",""+limpio);
                        try {

                            json_pedido_espera=new JSONArray(response);
                            for (int i=0;i<json_pedido_espera.length();i++){
                                JSONObject jsonObject = json_pedido_espera.getJSONObject(i);

                                Log.e("pedidoEspera", String.valueOf(jsonObject));

                                String strId = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                                String strid_mesero=jsonObject.getString("id_mesero");
                                String strMecero=jsonObject.getString("meseroAsignado");
                                String strEstado=jsonObject.getString("estado");
                                String strContenido=jsonObject.getString("contenido");
                                String strFecha_entrega = jsonObject.getString("fecha_entrega");
                                String strFecha_final = jsonObject.getString("fecha_final");
                               listaPedidosAsignados.add( new ListaPedidosRecyclerCorta(strId,strMesa,strComanda,strPrecio,strFecha_ingreso,strMecero,strid_mesero,strEstado));

                                Log.e("pedidos",strFecha_ingreso);
                                Log.e("contenido",strMesa);
                                Log.e("meseroAsignado",strPrecio);
                                Log.e("meseroAsignado",strMecero);
                                Log.e("meseroAsignado",strComanda);

                            }

                            adapterListaEspera=new AdapterListaPedidosESPERA(listaPedidosAsignados);
                            lista_espera_recycler.setAdapter(adapterListaEspera);

                            //recycler_movies.scrollToPosition(0);



                            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
                            //bloquearMenu();


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonapedidos3333:",""+json_pedido_espera);
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
                map.put("fireCode",fireCode);
                Log.e("fireCode", fireCode);

                return map;
            }
        };
        requestQueue.add(request);
    }
    public void pedir_pedidos_cocina()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosEstacionCocina.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        String limpio=response;
                        Log.e("jsonObjectCocina:",""+response);
                        Log.e("jsonObjectCocina3333:",""+limpio);
                        try {

                            json_pedido_cocina=new JSONArray(response);
                            for (int i=0;i<json_pedido_cocina.length();i++){
                                JSONObject jsonObject = json_pedido_cocina.getJSONObject(i);

                                Log.e("pedidoEspera", String.valueOf(jsonObject));

                                String strId = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                                String strid_mesero=jsonObject.getString("id_mesero");
                                String strMecero=jsonObject.getString("meseroAsignado");
                                String strEstado=jsonObject.getString("estado");
                                String strContenido=jsonObject.getString("contenido");
                                String strFecha_entrega = jsonObject.getString("fecha_entrega");
                                String strFecha_final = jsonObject.getString("fecha_final");
                                listaPedidosCocina.add( new ListaPedidosRecyclerCorta(strId,strMesa,strComanda,strPrecio,strFecha_ingreso,strMecero,strid_mesero,strEstado));

                                Log.e("pedidos",strFecha_ingreso);
                                Log.e("contenido",strMesa);
                                Log.e("meseroAsignado",strPrecio);
                                Log.e("meseroAsignado",strMecero);
                                Log.e("meseroAsignado",strComanda);

                            }

                            adapterListaPedidosCocina=new AdapterListaPedidosCocina(listaPedidosCocina);
                            cocina_espera_recycler.setAdapter(adapterListaPedidosCocina);

                            //recycler_movies.scrollToPosition(0);



                            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
                            //bloquearMenu();


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonapedidos3333:",""+json_pedido_espera);
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
                map.put("fireCode",fireCode);
                Log.e("fireCode", fireCode);

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

                                Log.e("Nombresher",""+ strMecero_asignado);

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
        strIdPedido=id_pedido;
        listaMeserosDisponibles=new ArrayList<>();
        setListaMeceros();
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        caja_asignar_mecero.setVisibility(View.VISIBLE);
        adonde_vas="meseros";
        Log.e("adonde",adonde_vas);
        Log.e("id_activyty",""+id_pedido);

    }
    public void mostrarDetalleCocina(String id_pedido,String fecha_ingreso,String mesa,String precio, String comanda, String mecero_asignado, String id_mesero,String estado){
        listaPedidoContenidoCocinas.clear();
        strIdPedido=id_pedido;
        strFecha_ingreso=fecha_ingreso;
        strMesa=mesa;
        strPrecio=precio;
        strComanda=comanda;
        strMecero_asignado=mecero_asignado;
        strEstado=estado;
        Strid_mesero=id_mesero;

        Log.e("cocina",strIdPedido);
        Log.e("cocina",strFecha_ingreso);
        Log.e("cocina",strMesa);
        Log.e("cocina",strPrecio);
        Log.e("cocina",strComanda);
        Log.e("cocina",strMecero_asignado);
        Log.e("cocina",strEstado);
        Log.e("cocina",Strid_mesero);

        fecha_ingreso_cocina.setText(strFecha_ingreso);
        mesa_cocina.setText(strMesa);
        precio2_cocina.setText(strPrecio);
        estado_cocina.setText(strEstado);
        comanda_cocina.setText(strComanda);
        mecero_asignado_cocina.setText(strMecero_asignado);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        caja_detalle_pedido.setVisibility(View.GONE);
        caja_detalle_pedido_cocina.setVisibility(View.VISIBLE);
        adonde_vas="detalleCocina";
        pedir_contenido_pedido_cocina();
    }
    public void mostrarDetalle(String id_pedido,String fecha_ingreso,String mesa,String precio, String comanda, String mecero_asignado, String id_mesero,String estado){
        listaPedidosRecyclers.clear();
        strIdPedido=id_pedido;
        strFecha_ingreso=fecha_ingreso;
        strMesa=mesa;
        strPrecio=precio;
        strComanda=comanda;
        strMecero_asignado=mecero_asignado;
        strEstado=estado;
        Strid_mesero=id_mesero;

        fecha_ingresoView.setText(strFecha_ingreso);
        mesaView.setText(strMesa);
        precio2View.setText(strPrecio);
        estadoView.setText(strEstado);
        comandaView.setText(strComanda);
        caja_detalle_pedido_cocina.setVisibility(View.GONE);

        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        caja_detalle_pedido.setVisibility(View.VISIBLE);
        adonde_vas="detalle";
        Log.e("adonde",adonde_vas);

        pedir_contenido_pedido();

    }
    public void mostrarDetalleEspera(String id_pedido,String fecha_ingreso,String mesa,String precio, String comanda, String mecero_asignado, String id_mesero,String estado){
        /*listaPedidosRecyclersEspera.clear();*/
        strIdPedido=id_pedido;
        strFecha_ingreso=fecha_ingreso;
        strMesa=mesa;
        strPrecio=precio;
        strComanda=comanda;
        strMecero_asignado=mecero_asignado;
        strEstado=estado;

        Log.e("estado",strIdPedido);
        Log.e("estado",strFecha_ingreso);
        Log.e("estado",strMesa);
        Log.e("estado",strComanda);
        Log.e("estado",strMecero_asignado);
        Log.e("estado",strEstado);

        Strid_mesero=id_mesero;

        adonde_vas="detalleEspera";
        Log.e("adondevas",adonde_vas);
        fecha_ingreso_espera.setText(strFecha_ingreso);
        mesa_espera.setText(strMesa);
        precio2_espera.setText(strPrecio);
        estado_espera.setText(strEstado);
        comanda_espera.setText(strComanda);
        mecero_asignado_espera.setText(strMecero_asignado);
        caja_detalle_pedido_cocina.setVisibility(View.GONE);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        caja_lista_espera_recycler.setVisibility(View.VISIBLE);

        pedir_contenido_pedido_espera();

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
                                String strContenido = jsonObject.getString("contenido");
                                JSONArray arregloObjeto=new JSONArray(strContenido);
                                for (int i2=0;i2<arregloObjeto.length();i2++){
                                    Log.e("objetoContenido",""+strContenido);
                                    JSONObject jsonObjectContenido2=arregloObjeto.getJSONObject(i2);
                                    String strNombre = jsonObjectContenido2.getString("nombre");
                                    String strCantidad = jsonObjectContenido2.getString("cantidad");
                                    String strTotal = jsonObjectContenido2.getString("total");
                                    String strPrecio2 = jsonObjectContenido2.getString("precio");
                                    String strExtras = jsonObjectContenido2.getString("extras");
                                    Log.e("nombreCont:",""+strExtras);



                                    String strNota_mesero=jsonObjectContenido2.getString("nota_mesero");
                                    Log.e("nombreCont:",""+strNombre);
                                    Log.e("cantidaCant:",""+strCantidad);
                                    Log.e("totalCant:",""+strTotal);
                                    Log.e("preciCant:",""+strPrecio2);
                                    Log.e("extrasCant:",""+strExtras);
                                    Log.e("notaCant:",""+strNota_mesero);

                                    listaPedidosRecyclers.add(new ListaPedidosRecycler(strNombre,strCantidad,strTotal,strPrecio2,strExtras,strNota_mesero,strMecero_asignado,Strid_mesero,strIdPedido));

                                    Log.e("Nombresher",""+ strMecero_asignado);
                                }


                            }

                            adapterDetallePedido=new AdapterDetallePedido(listaPedidosRecyclers);
                            recycler_detalle_pedido.setAdapter(adapterDetallePedido);


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonPedidos:",""+json_pedido);
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

                Log.e("idcontenido",strIdPedido);
                map.put("id",strIdPedido);
                map.put("idSesion",idSesion);
                map.put("fireCode",fireCode);


                return map;
            }
        };
        requestQueue.add(request);
    }
    public  void pedir_contenido_pedido_espera(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"contenido_pedido.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            listaPedidosRecyclersEspera.clear();
                            json_contenido_pedido=new JSONArray(response);
                            for (int i=0;i<json_contenido_pedido.length();i++){
                                JSONObject jsonObject = json_contenido_pedido.getJSONObject(i);
                                Log.e("jsonObjectcotenidEspera",""+jsonObject);
                                String strContenido = jsonObject.getString("contenido");
                                JSONArray arregloObjeto=new JSONArray(strContenido);
                                for (int i2=0;i2<arregloObjeto.length();i2++){
                                    Log.e("objetoContenido",""+strContenido);
                                    JSONObject jsonObjectContenido2=arregloObjeto.getJSONObject(i2);
                                    String strNombre = jsonObjectContenido2.getString("nombre");
                                    String strCantidad = jsonObjectContenido2.getString("cantidad");
                                    String strTotal = jsonObjectContenido2.getString("total");
                                    String strPrecio2 = jsonObjectContenido2.getString("precio");
                                    String strExtras = jsonObjectContenido2.getString("extras");
                                    String strNota_mesero=jsonObjectContenido2.getString("nota_mesero");


                                    listaPedidosRecyclersEspera.add(new ListaPedidosRecycler(strNombre,strCantidad,strTotal,strPrecio2,strExtras,strNota_mesero,strMecero_asignado,Strid_mesero,strIdPedido));

                                    Log.e("Nombresher",""+ strMecero_asignado);
                                }


                            }

                            adapterDetallePedidoEspera=new AdapterDetallePedidoEspera(listaPedidosRecyclersEspera);
                            recycler_detalle_pedido_espera.setAdapter(adapterDetallePedidoEspera);
                            caja_detalle_pedido_espera.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonPedidos:",""+json_pedido);
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

                Log.e("idcontenido",strIdPedido);
                map.put("id",strIdPedido);
                map.put("idSesion",idSesion);
                map.put("fireCode",fireCode);


                return map;
            }
        };
        requestQueue.add(request);
    }
    public  void pedir_contenido_pedido_cocina(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"contenido_pedido.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            json_contenido_pedido=new JSONArray(response);
                            for (int i=0;i<json_contenido_pedido.length();i++){
                                JSONObject jsonObject = json_contenido_pedido.getJSONObject(i);
                                Log.e("jsonObjectcotenidEspera",""+jsonObject);
                                String strContenido = jsonObject.getString("contenido");
                                JSONArray arregloObjeto=new JSONArray(strContenido);
                                for (int i2=0;i2<arregloObjeto.length();i2++){
                                    Log.e("objetoContenido",""+strContenido);
                                    JSONObject jsonObjectContenido2=arregloObjeto.getJSONObject(i2);
                                    String strNombre = jsonObjectContenido2.getString("nombre");
                                    String strCantidad = jsonObjectContenido2.getString("cantidad");
                                    String strTotal = jsonObjectContenido2.getString("total");
                                    String strPrecio2 = jsonObjectContenido2.getString("precio");
                                    String strExtras = jsonObjectContenido2.getString("extras");
                                    String strNota_mesero=jsonObjectContenido2.getString("nota_mesero");
                                    String strEstatus=jsonObjectContenido2.getString("estatus");

                                    Log.e("nombreCocina:",""+strNombre);
                                    Log.e("cantidadCOcina:",""+strCantidad);
                                    Log.e("totalCOcina:",""+strTotal);
                                    Log.e("precioCOCINA:",""+strPrecio2);
                                    Log.e("extrasCocina:",""+strExtras);
                                    Log.e("notaCOcina:",""+strNota_mesero);
                                    Log.e("estatusCOcina:",""+strEstatus);

                                    listaPedidoContenidoCocinas.add(new ListaPedidoContenidoCocina(strNombre,strCantidad,strTotal,strPrecio2,strExtras,strNota_mesero,strMecero_asignado,Strid_mesero,strIdPedido,strEstatus));

                                    Log.e("Nombresher",""+ strMecero_asignado);
                                }


                            }

                            adapterDetallePedidoCocina=new AdapterDetallePedidoCocina(listaPedidoContenidoCocinas);
                            recycler_detalle_pedido_cocina.setAdapter(adapterDetallePedidoCocina);


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonPedidos:",""+json_pedido);
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

                Log.e("idcontenido",strIdPedido);
                map.put("id",strIdPedido);
                map.put("idSesion",idSesion);
                map.put("fireCode",fireCode);

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
                        pedir_pedidos();

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
                map.put("id", strIdPedido);
                map.put("fecha_ingreso",fecha_encontrada);
                map.put("meseroAsignado", strMecero_asignado);
                map.put("id_mesero",id_mesero);

                return map;
            }
        };
        requestQueue.add(request);
    }
    public void onBackPressed(){

        if (adonde_vas=="pedidoAsignado"){
            caja_recycler_pedidos.setVisibility(View.VISIBLE);
            caja_lista_espera_recycler.setVisibility(View.GONE);
            adonde_vas="principal";
            Log.e("adonde",adonde_vas);
        }
        if (adonde_vas=="detalle"){
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
            caja_detalle_pedido.setVisibility(View.GONE);
            adonde_vas="principal";
            Log.e("adonde",adonde_vas);
        }
        if (adonde_vas=="meseros"){
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
            caja_asignar_mecero.setVisibility(View.GONE);
            adonde_vas="principal";
            Log.e("adonde",adonde_vas);
        }
        if (adonde_vas=="meseros2"){
            caja_asignar_mecero.setVisibility(View.GONE);
            caja_detalle_pedido.setVisibility(View.VISIBLE);
            adonde_vas="detalle";
            Log.e("adonde",adonde_vas);
        }
        if (adonde_vas=="detalleEspera"){
            caja_detalle_pedido_espera.setVisibility(View.GONE);
            caja_lista_espera_recycler.setVisibility(View.VISIBLE);
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);

            adonde_vas="pedidoAsignado";
            Log.e("adonde",adonde_vas);
        }
        if(adonde_vas=="detalleCocina"){
            caja_detalle_pedido_cocina.setVisibility(View.GONE);
            caja_pedidos_espera_cocina.setVisibility(View.VISIBLE);
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);

        }
        if(adonde_vas=="pedidoCocina"){
            caja_recycler_pedidos.setVisibility(View.GONE);
            caja_lista_espera_recycler.setVisibility(View.VISIBLE);
            caja_lista_cocina_recycler.setVisibility(View.GONE);
            adonde_vas="pedidoAsignado";

        }
    }
}
