package com.onur.bitirme;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;


import com.google.android.gms.location.LocationListener;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.onur.bitirme.Database.BOYLAM;
import static com.onur.bitirme.Database.ENLEM;
import static com.onur.bitirme.Database.TABLE_NAME;
import static com.onur.bitirme.Database.TARIH;


public class KonumActivity extends Activity implements LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double fusedLatitude = 0.0;
    private double fusedLongitude = 0.0;
    private TextView enlem, boylam, txt1, txt2, tarih;
    private Button btnKaydet,btnGoster;
private EditText enlem1,boylam1,tarih1;
    Date today = Calendar.getInstance().getTime();
    public java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("d-MMMM-yyyy EEEE h:mm");

    private String enlemm,boylamm;
    Database veritabani;
    public  double a;
    public double b ;
    private LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum);
        txt1 = (TextView) findViewById(R.id.txtEnlem);
        txt2 = (TextView) findViewById(R.id.txtBoylam);
        btnKaydet = (Button) findViewById(R.id.button3);
        btnGoster= (Button) findViewById(R.id.button4);
        enlem = (TextView) findViewById(R.id.textView);
        boylam = (TextView) findViewById(R.id.textView2);
        tarih = (TextView) findViewById(R.id.textView3);

        veritabani=new Database(getApplicationContext());
        if (checkPlayServices()) {
            startFusedLocation();
            registerRequestUpdate(this);
        }

        btnGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KonumActivity.this,MapActivity.class));

            }
        });
        btnKaydet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                SQLiteDatabase db = veritabani.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ENLEM,Double.toString(getFusedLatitude()));
                cv.put(BOYLAM,Double.toString(getFusedLongitude()));
                cv.put(TARIH,getDateTime());
                db.insertOrThrow(TABLE_NAME,null,cv);
                Toast.makeText(getApplicationContext(),"Konum Eklendi", Toast.LENGTH_LONG).show();


            }
        });
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    // check if google play services is installed on the device
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(),
                        "Bu cihaz desteklenmektedir. Lütfen Google Play hizmetlerini indirin!", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Bu cihaz desteklenmemektedir!!", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    public void startFusedLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }

                        @Override
                        public void onConnected(Bundle connectionHint) {

                        }
                    }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                        @Override
                        public void onConnectionFailed(ConnectionResult result) {

                        }
                    }).build();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void registerRequestUpdate(final LocationListener listener) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // every second

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!isGoogleApiClientConnected()) {
                        mGoogleApiClient.connect();
                    }
                    registerRequestUpdate(listener);
                }
            }
        }, 1000);
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
        setFusedLatitude(location.getLatitude());
        setFusedLongitude(location.getLongitude());

        //Toast.makeText(getApplicationContext(), "Yeni lokasyon bulundu!", Toast.LENGTH_LONG).show();
       /* SQLiteDatabase db = veritabani.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ENLEM,x);
        cv.put(BOYLAM,y);
        cv.put(TARIH,"aa");
        db.insertOrThrow(TABLE_NAME,null,cv);
        Toast.makeText(getApplicationContext(),"Konum Eklendi", Toast.LENGTH_LONG).show();*/
        enlem.setText("  "+ getFusedLatitude());
        boylam.setText("  "+ getFusedLongitude());


    }

    public void setFusedLatitude(double lat) {
        fusedLatitude = lat;
    }

    public void setFusedLongitude(double lon) {
        fusedLongitude = lon;
    }

    public  double getFusedLatitude() {
        return fusedLatitude;
    }

    public double getFusedLongitude() {
        return fusedLongitude;
    }

}
