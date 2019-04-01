package com.onur.bitirme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class KonumListele extends Activity {
    int id;
    TextView t1,t2,t3,t4;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> konum_liste;
    String enlemler[];
    String boylamlar[];
    String tarihler[];
    int konum_idler[];
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_listele);
        btn=(Button)findViewById(R.id.button5);

        Database db = new Database(getApplicationContext());

         konum_liste= db.konumlar();
        if(konum_liste.size()==0){
        Toast.makeText(getApplicationContext(), "Henüz Konum Eklenmemiş.", Toast.LENGTH_LONG).show();

}
    else{
        enlemler = new String[konum_liste.size()];
        boylamlar= new String[konum_liste.size()];
            tarihler=new String[konum_liste.size()];
        konum_idler = new int[konum_liste.size()];
        for(int i=0;i<konum_liste.size();i++){

            tarihler[i]= konum_liste.get(i).get("tarih");
            enlemler[i]= konum_liste.get(i).get("enlem");

            konum_idler[i] = Integer.parseInt(konum_liste.get(i).get("id"));
    }
            lv = (ListView) findViewById(R.id.listview1);
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtenlem,tarihler);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Intent intent = new Intent(getApplicationContext(), KonumDetay.class);
                    intent.putExtra("id", (int)konum_idler[arg2]);
                    startActivity(intent);

                }		     });

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsKonumAnaliz.class);
                intent.putExtra("enlem",enlemler);
                intent.putExtra("boylam",boylamlar);
                startActivity(intent);
            }
        });



    }}
