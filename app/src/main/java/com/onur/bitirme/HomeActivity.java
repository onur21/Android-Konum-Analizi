package com.onur.bitirme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    Button btnLokasyon,btnGoster,btnListele,btnYon;
    TextView txtAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLokasyon=(Button)findViewById(R.id.lokasyon);
        btnGoster=(Button)findViewById(R.id.button);
        btnListele=(Button)findViewById(R.id.button2);
        btnYon=(Button)findViewById(R.id.button3);
        btnYon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapYonlendirme.class));

            }
        });
        btnListele.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,KonumListele.class));

            }
        });
        btnLokasyon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,KonumActivity.class));
            }
        });
        btnGoster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapActivity.class));




            }
        });
    }
}
