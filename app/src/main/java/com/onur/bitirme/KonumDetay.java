package com.onur.bitirme;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class KonumDetay extends AppCompatActivity {
    Button b1,b2;
    TextView t1,t2,t3,t4;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_detay);


        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);

        t1 = (TextView)findViewById(R.id.enlemi);
        t2 = (TextView)findViewById(R.id.boylami);
        t3 = (TextView)findViewById(R.id.tarihi);
        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);//id değerini integer olarak aldık. Burdaki 0 eğer değer alınmazsa default olrak verilecek değer
        Database db = new Database(getApplicationContext());
        HashMap<String, String> map = db.konumListele(id);//Bu id li row un değerini hashmap e aldık

        t1.setText(map.get("enlem"));
        t2.setText(map.get("boylam"));
        t3.setText(map.get("tarih"));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsData.class);
                intent.putExtra("id", (int)id);
                startActivity(intent);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(KonumDetay.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Konum Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Database db = new Database(getApplicationContext());
                        db.konumSil(id);
                        Toast.makeText(getApplicationContext(), "Konum Başarıyla Silindi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);//bu id li konumu sildik ve Anasayfaya döndük
                        finish();

                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
                });
                alertDialog.show();

            }
        });

    }
}
