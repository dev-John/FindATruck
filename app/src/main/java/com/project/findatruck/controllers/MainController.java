package com.project.findatruck.controllers;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MainController {


    public boolean cadastrarEvento(LatLng latLng){
        Log.i("Controller", latLng.toString());
        return true;
    }

}
