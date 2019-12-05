package com.project.findatruck.controllers;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.project.findatruck.models.MainModel;

public class MainController {


    public boolean cadastrarEvento(LatLng latLng){
        Log.i("Controller", latLng.toString());
        MainModel model = new MainModel();

        return true;
    }

}
