package com.creathor.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Login extends AppCompatActivity {

    private ExecutorService executorService;
    private EditText usuario,contrasena;
    private TextView ingresar,recuperarContra,mensaje;
    private String valUsuario,valContra,correo_final;
    private static String SERVIDOR_CONTROLADOR;
    private int check=0;
    private SharedPreferences datosUsuario;
    private SharedPreferences.Editor editor;
    private boolean correo_exitoso,contrasena_exitoso;
   // private  JSONArray json_datos_usuario;
    private  JSONObject json_datos_usuario;
    private  String strInicio,strUsuario,strId,stridSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        SERVIDOR_CONTROLADOR = new Servidor().getIplocal();
        datosUsuario = getSharedPreferences("Usuario",this.MODE_PRIVATE);
        editor=datosUsuario.edit();
        executorService= Executors.newSingleThreadExecutor();

        usuario=findViewById(R.id.usuario);
        contrasena =findViewById(R.id.contrasena);
        ingresar= findViewById(R.id.ingresar);
        recuperarContra =findViewById(R.id.recuperarContra);
        mensaje =findViewById(R.id.mensaje);

        checkSesion();

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valUsuario =usuario.getText().toString();
                valContra=contrasena.getText().toString();
                Log.e("datocorreo", valUsuario);
                Log.e("datocontra",valContra );
                if(!valUsuario.trim().equals("")){
                    if(!valContra.trim().equals("")){



                        recuperarContra.setVisibility(View.GONE);
                        ingresar.setVisibility(View.GONE);
                        mensaje.setText("Iniciando sesión ...");
                        mensaje.setVisibility(View.VISIBLE);
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                hacerPeticion();
                                Intent intent = new Intent(Login.this, Estacion.class);
                                startActivity(intent);
                                Log.e("entnedi_señor_calamardo","y esto igual");
                            }
                        });


                    }
                    else {
                        Toast.makeText(getApplicationContext(), "La contrasena es necesario.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "El correo es necesario.", Toast.LENGTH_LONG).show();
                }

            }

        });
        recuperarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intento2= new Intent( Login.this,Recuperar_Contra.class);
                //startActivity(intento2);
            }
        });


    }


    public void hacerPeticion()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"iniciarSesion.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("respuesta:",response);
                        if (response.equals("no_existe")) {
                            recuperarContra.setVisibility(View.VISIBLE);
                            ingresar.setVisibility(View.VISIBLE);
                            mensaje.setText("El teléfono o correo es incorrecto.");
                        }
                        else
                        {
                                try {

                                    //json_datos_usuario=new JSONArray(response);
                                    json_datos_usuario=new JSONObject(response);
                                    strId = json_datos_usuario.getString("id");
                                    String strUsuario = json_datos_usuario.getString("usuario");
                                    //String strContra = json_datos_usuario.getString("contra");
                                    String strTipoUsuario = json_datos_usuario.getString("tipoUsuario");
                                    //String strActivo=json_datos_usuario.getString("activo");
                                    String strFirecode=json_datos_usuario.getString("fireCode");
                                    stridSesion=json_datos_usuario.getString("idSesion");
                                    Log.e("id_sesion",""+stridSesion);
                                    Log.e("lala",""+json_datos_usuario);
                                    editor.putString("id",strId);
                                    editor.putString("usuario",strUsuario);
                                    //editor.putString("contra",strContra);
                                    editor.putString("tipoUsuario",strTipoUsuario);
                                    //editor.putString("activo",strActivo);
                                    editor.putString("fireCode",strFirecode);
                                    editor.putString("idSesion",stridSesion);

                                    editor.apply();

                                    datosUsuario.getString("usuario","no hay");
                                    String prefedatosus=datosUsuario.getString("idSesion","no hay");
                                    Log.e("comprobacion",""+prefedatosus);

                                    Log.e("1",""+strUsuario);
                                   // Log.e("2",strContra);
                                    Log.e("2",strTipoUsuario);
                                   // Log.e("3",strActivo);
                                    Log.e("4",strFirecode);
                                    Log.e("5",stridSesion);
                                    /*for (int i=0;i<json_datos_usuario.length();i++){
                                        JSONObject jsonObject = json_datos_usuario.getJSONObject(i);
                                        //Log.e("nombreMovies", String.valueOf(jsonObject));
                                        String strId = jsonObject.getString("id");
                                        String strUsuario = jsonObject.getString("usuario");
                                        String strContra = jsonObject.getString("contra");
                                        String strTipoUsuario = jsonObject.getString("tipoUsuario");
                                        String strActivo=jsonObject.getString("activo");
                                        String strFirecode=jsonObject.getString("fireCode");
                                        String stridSesion=jsonObject.getString("idSesion");

                                        editor.putString("id",strId);
                                        editor.putString("usuario",strUsuario);
                                        editor.putString("contra",strContra);
                                        editor.putString("tipoUsuario",strTipoUsuario);
                                        editor.putString("activo",strActivo);
                                        editor.putString("fireCode",strFirecode);
                                        editor.putString("idSesion",stridSesion);

                                        editor.apply();
                                        Log.e("1",""+strUsuario);
                                        Log.e("2",strContra);
                                        Log.e("2",strTipoUsuario);
                                        Log.e("3",strActivo);
                                        Log.e("4",strFirecode);
                                        Log.e("5",stridSesion);

                                    }*/
                                }
                                catch (JSONException e) {
                                    Log.e("errorRespuesta", String.valueOf(e));
                                }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "error", "error: " +error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("usuario", valUsuario);
                map.put("contra",valContra);
                return map;
            }
        };
        requestQueue.add(request);
    }
    private void checkSesion() {
        strInicio = datosUsuario.getString("id_sesion", "no");

        Log.e("inicio",""+strInicio);
        if (!strInicio.equals("no"))
        {

            Log.e("idsesion_main",strInicio);
            Intent agenda= new Intent(Login.this, Estacion.class);
            startActivity(agenda);
        }
    }
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

}