package com.project.findatruck;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.project.findatruck.controllers.MainController;
import com.project.findatruck.database.DAO.LocalizacaoDAO;
import com.project.findatruck.models.Localizacoes;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    FloatingActionButton fab;

    private final int TAG_CODE_PERMISSION_LOCATION=1;

    MainController controller;
    LatLng latLng;
    boolean confirmed = false;
    LocalizacaoDAO dao;
    Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    address = addressList.get(0);
                    latLng = new LatLng(address.getLatitude(),address.getLongitude());

                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {


                    // instantiate the location manager, note you will need to request permissions in your manifest
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    // get the last know location from your location manager.
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    // now get the lat/lon from the location and do something with it.
                    //nowDoSomethingWith(location.getLatitude(), location.getLongitude());

                    Log.i("LOCATION: ", location.toString());

                    if(location != null){
                        LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "erro de permissao", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION },
                            TAG_CODE_PERMISSION_LOCATION);
                }
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        dao = new LocalizacaoDAO(getBaseContext());
        ArrayList<Localizacoes> locList = new ArrayList<Localizacoes>();
        locList = dao.getLocalizacoes();

        Log.i("LOCLIST: ", locList.toString());

        for (Iterator<Localizacoes> i = locList.iterator(); i.hasNext();) {
            //String item = i.next();
            //System.out.println(item);
            Log.i("A:", i.next().toString());
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng point) {

                Geocoder geocoder = new Geocoder(MainActivity.this);
                List<Address> addressList = null;

                try{
                    addressList = geocoder.getFromLocation(point.latitude,point.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                address = addressList.get(0);
                Log.i("ENDERECO: ",address.getAddressLine(0));
                latLng = new LatLng(address.getLatitude(),address.getLongitude());

                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirmação")
                    .setMessage("Salvar este Food Truck?")
                    .setIcon(R.drawable.foodtruckicon4)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            controller = new MainController();
                            confirmed = controller.cadastrarEvento(latLng); //chama o controller
                            if(confirmed == true){


                                boolean success = dao.salvar(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), "Jonatas");

                                if(success){
                                    // create marker
                                    MarkerOptions marker = new MarkerOptions();

                                    //marker.position(new LatLng(address.getLatitude(), address.getLongitude())).title(location);
                                    marker.position(new LatLng(address.getLatitude(), address.getLongitude())).title(address.getAddressLine(0));
                                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.foodtruckicon4));


                                    map.addMarker(marker);
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                                    Toast.makeText(MainActivity.this, "Food Truck Cadastrado!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }})
                    .setNegativeButton(R.string.no, null).show();
            }
        });
    }

    public void getMyLocation(View view){

    }

    @Override
    public void onClick(View view) {

    }
}
