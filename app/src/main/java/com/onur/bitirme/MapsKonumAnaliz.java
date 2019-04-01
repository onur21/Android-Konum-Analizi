package com.onur.bitirme;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsKonumAnaliz extends FragmentActivity implements OnMapReadyCallback {
    ArrayList<HashMap<String, String>> konum_liste;

    private GoogleMap mMap;
    private String enlem,boylam;
    String enlemler[];
    String boylamlar[];
    Double enlemx;
    Double boylamx;
    String tarihler[];
    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_konum_analiz);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Database db = new Database(getApplicationContext());
        konum_liste= db.konumlar();
        enlemler = new String[konum_liste.size()];
        boylamlar= new String[konum_liste.size()];
        tarihler=new String[konum_liste.size()];
        /*for(int i=0;i<=konum_liste.size();i++){
          /*  enlemler[i]= String.valueOf(Double.parseDouble(konum_liste.get(i).get("enlem")));
            boylamlar[i]=  String.valueOf(Double.parseDouble(konum_liste.get(i).get("boylam")));
            tarihler[i]= konum_liste.get(i).get("tarih");
            LatLng sydney = new LatLng(Double.parseDouble(konum_liste.get(i).get("enlem")),Double.parseDouble(konum_liste.get(i).get("boylam")));
            mMap.addMarker(new MarkerOptions().position(sydney).title("sad"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            Log.i("enlemler", String.valueOf(Double.parseDouble(konum_liste.get(i).get("enlem"))));}*/

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < konum_liste.size(); i++) {
            double lati=Double.parseDouble(konum_liste.get(i).get("enlem"));
            double longLat=Double.parseDouble(konum_liste.get(i).get("boylam"));
            LatLng konum = new LatLng(lati,longLat);

            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(lati,longLat))
                    .title(konum_liste.get(i).get("tarih")));
            float zoomLevel = (float) 10.0; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(konum, zoomLevel));
            //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }



    }
}
