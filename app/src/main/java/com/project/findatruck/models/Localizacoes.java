package com.project.findatruck.models;

import java.io.Serializable;

public class Localizacoes implements Serializable {

    private int id;
    private String latitude;
    private String longitude;
    private String usuario_cadastrante;

    public Localizacoes(){ }

    public Localizacoes(int id, String latitude, String longitude, String usuario_cadastrante) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.usuario_cadastrante = usuario_cadastrante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUsuario_cadastrante() {
        return usuario_cadastrante;
    }

    public void setUsuario_cadastrante(String usuario_cadastrante) {
        this.usuario_cadastrante = usuario_cadastrante;
    }

    @Override
    public boolean equals(Object o){
        return this.id == ((Localizacoes)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }

}
