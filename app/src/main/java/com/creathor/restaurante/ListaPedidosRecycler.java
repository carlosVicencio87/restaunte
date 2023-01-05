package com.creathor.restaurante;

import androidx.appcompat.app.AppCompatActivity;

public class ListaPedidosRecycler extends AppCompatActivity {

    private String id,mesa,comanda,precio,fecha_ingreso,fecha_entrega,fecha_final, mecero_asignado,id_mesero,contenido,nombre,cantidad,total,precio2,extras,nota_mesero,estado;



    public String getId() {
        return id;
    }
    public String getMesa() {return mesa;}
    public String getComanda() {return comanda;}
    public String getPrecio() {return precio;}
    public String getFecha_ingreso() { return fecha_ingreso;}
    public String getNombre() { return nombre;}
    public String getCantidad() { return cantidad;}
    public String getTotal() { return total;}
    public String getPrecio2() { return precio2;}
    public String getExtras() { return extras;}
    public String getNota_mesero() { return nota_mesero;}
    public String getMecero_asignado() { return mecero_asignado; }
    public String getId_mesero() { return id_mesero; }
    public String getEstado(){return  estado;}


   /* public String getFecha_entrega() { return fecha_entrega; }
    public String getFecha_final() { return fecha_final;}*/



    public void setId(String id)                               {
        this.id = id;
    }
    public void setMesa(String mesa) {this.mesa = mesa;}
    public void setComanda(String comanda) {this.comanda = comanda;}
    public void setPrecio(String precio) { this.precio = precio; }
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setCantidad(String cantidad) {this.cantidad = cantidad;}
    public void setTotal(String total) {this.total = total;}
    public void setPrecio2(String precio2) {this.precio2 = precio2;}
    public void setExtras(String extras) {this.extras = extras;}
    public void setNota_mesero(String nota_mesero) {this.nota_mesero = nota_mesero;}
    public void setMecero_asignado(String mecero_asignado) { this.mecero_asignado = mecero_asignado;}
    public void setId_mesero(String id_mesero) { this.id_mesero = id_mesero;}
    public void setEstado(String estado) { this.estado = estado;}


   /* public void setFecha_entrega(String fecha_entrega) { this.fecha_entrega = fecha_entrega;}
    public void setFecha_final(String fecha_final) {this.fecha_final = fecha_final;}*/



    public ListaPedidosRecycler(String name, String cant, String totl,String price2,String extr,String note_meser,
                                String id_food,
                                String mecer_asigned,String id_meser){

        this.id =id_food;

        this.mecero_asignado =mecer_asigned;
        this.id_mesero=id_meser;

        this.nombre=name;
        this.cantidad=cant;
        this.total=totl;
        this.precio2 =price2;
        this.extras=extr;
        this.nota_mesero=note_meser;

       /* this.fecha_entrega=date_entrega;
        this.fecha_final=date_end;*/

    }

}