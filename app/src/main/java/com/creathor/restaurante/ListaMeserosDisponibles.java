package com.creathor.restaurante;

import androidx.appcompat.app.AppCompatActivity;

public class ListaMeserosDisponibles extends AppCompatActivity {

    private String id_mesero, nombre_mesero;



    public String getId_mesero() {
        return id_mesero;
    }
    public String getNombre_mesero() {
        return nombre_mesero;
    }

   /* public String getFecha_entrega() { return fecha_entrega; }
    public String getFecha_final() { return fecha_final;}*/



    public void setId_mesero(String id_mesero) {
        this.id_mesero = id_mesero;
    }
    public void setNombre_mesero(String nombre_mesero) {
        this.nombre_mesero = nombre_mesero;
    }

   /* public void setFecha_entrega(String fecha_entrega) { this.fecha_entrega = fecha_entrega;}
    public void setFecha_final(String fecha_final) {this.fecha_final = fecha_final;}*/



    public ListaMeserosDisponibles(String id_meser, String name_meser){

        this.id_mesero =id_meser;
        this.nombre_mesero =name_meser;


    }
}