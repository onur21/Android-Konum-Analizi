package com.onur.bitirme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KayitActivity extends AppCompatActivity {
    TextView txtAd,txtSoyad,txtEmail,txtSifre;
    EditText ad,soyad,email,sifre;
    Button kayit;
    DatabaseReference mReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progress;
    private List<Kullanici> kullanıcılar;


    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        txtAd=(TextView) findViewById(R.id.txtAd);
        txtSoyad=(TextView)findViewById(R.id.txtSoyad);
        txtEmail=(TextView)findViewById(R.id.txtEmail);
        txtSifre=(TextView)findViewById(R.id.txtSifre);
        ad=(EditText)findViewById(R.id.ad);
        soyad=(EditText)findViewById(R.id.soyad);
        email=(EditText)findViewById(R.id.email);
        sifre=(EditText)findViewById(R.id.sifre);
        kayit=(Button)findViewById(R.id.kayit);
        auth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        mReference = FirebaseDatabase.getInstance().getReference("users");
        kullanıcılar = new ArrayList<>();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };
        kayit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inputEmail = email.getText().toString().trim();
                String inputPassword = sifre.getText().toString().trim();

                if(TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(getApplicationContext(), "Email Adresinizi Giriniz!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(inputPassword)) {
                    Toast.makeText(getApplicationContext(), "Şifrenizi Giriniz(En Az 6 Karakter)!", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Şifreniz Çok Kısa Tekrar Deneyiniz!", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Create user
                    auth.createUserWithEmailAndPassword(inputEmail,inputPassword)
                            .addOnCompleteListener(KayitActivity.this,new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(KayitActivity.this, "Yeni Üyelik Başarılı! Lütfen Giriş Yapınız!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(KayitActivity.this,MainActivity.class));
                                        progress.dismiss();
                                        // Write a message to the database
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference();


                                        myRef.setValue(email.getText().toString(),sifre.getText().toString());
                                        kullaniciOlustur();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Yeni Üyelik Başarısız! Tekrar Deneyiniz..", Toast.LENGTH_SHORT).show();
                                        progress.dismiss();
                                        startActivity(new Intent(KayitActivity.this,KayitActivity.class));

                                    }
                                }
                            });
                }
                progress.setMessage("Kullanıcı Kaydediliyor...");
                progress.show();
            }});


    }
    private void kullaniciOlustur() {
        String id = mReference.push().getKey();
        Kullanici kullanici = new Kullanici(id,ad.getText().toString(),soyad.getText().toString(),email.getText().toString());
        mReference.child(id).setValue(kullanici);
        ad.setText("");
        soyad.setText("");
        email.setText("");

        Toast.makeText(getApplicationContext(),"Yeni Kullanıcı Eklendi",Toast.LENGTH_SHORT).show();
        /*Map<String, String> yeniUser = new HashMap<String, String>();
        yeniUser.put("ad", ad.getText().toString());
        yeniUser.put("soyad", soyad.getText().toString());
        yeniUser.put("email",email.getText().toString());
        DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference( "users" );
        String userId = databaseReference.push().getKey();
        databaseReference.child(userId).setValue(yeniUser);
        /*databaseReference.child("users")
                .child(auth.getCurrentUser().getUid())
                .setValue(yeniUser);*/
    }
}
