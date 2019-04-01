package com.onur.bitirme;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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

public class MainActivity extends AppCompatActivity {
    Button btnGiris,btnKayit,btnSifre,btnVerify;
    EditText email,sifre;
    TextView txtemail,txtsifre;
    private ProgressDialog progress;
    private static final String TAG = "EmailPassword";
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGiris = (Button) findViewById(R.id.btngiris);
        btnKayit = (Button) findViewById(R.id.btnKayit);
        btnSifre = (Button) findViewById(R.id.btnSifre);
        // btnVerify = (Button) findViewById(R.id.btnVerify);
        email = (EditText) findViewById(R.id.email);
        sifre = (EditText) findViewById(R.id.sifre);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txtsifre = (TextView) findViewById(R.id.txtsifre);
        auth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;
        myIntent = new Intent(MainActivity.this,AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,30*60*1000,pendingIntent);

         /* Retrieve a PendingIntent that will perform a broadcast */
      /*  Intent alarmIntent = new Intent(MainActivity.this, Alarm.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                AlarmManager.INTERVAL_HALF_HOUR,
                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);*/


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

        btnSifre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_email_dialog);
                dialog.setTitle("Şifre Yenileme");
                dialog.setCancelable(true);

                final EditText userEmail = (EditText) dialog.findViewById(R.id.newEmail);

                Button btnReset = (Button) dialog.findViewById(R.id.btnReset);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputEmail = userEmail.getText().toString().trim();

                        if(TextUtils.isEmpty(inputEmail)){
                            Toast.makeText(getApplicationContext(),"Email Adresinizi Giriniz!", Toast.LENGTH_LONG).show();
                        }else {
                            auth.sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Email Adresinize Şifre Yenileme Maili Gönderildi!",Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Email Gönderme Başarısız!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }});

        btnKayit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,KayitActivity.class));

            }});
        btnGiris.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inputEmail = email.getText().toString().trim();
                String inputPassword = sifre.getText().toString().trim();

                if(TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(getApplicationContext(),"Email Adresinizi Giriniz!", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(inputPassword)){
                    Toast.makeText(getApplicationContext(),"Şifrenizi Giriniz(En Az 6 Karakter)!", Toast.LENGTH_LONG).show();
                } else if(inputPassword.length() < 6){
                    Toast.makeText(getApplicationContext(),"Şifreniz Çok Kısa Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();
                } else {

                    //Sign in user
                    auth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(),"Giriş Başarısız! Tekrar Deneyiniz...", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }            }
        });}



}
