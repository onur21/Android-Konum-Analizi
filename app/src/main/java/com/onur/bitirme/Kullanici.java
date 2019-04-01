package com.onur.bitirme;

/**
 * Created by Onur on 10.05.2017.
 */

public class Kullanici {
    private String Id;
    private String kullaniciAd;
    private String kullaniciSoyad;
    private String kullaniciEmail;

    public Kullanici(){

    }


    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getkullaniciAd() {
        return kullaniciAd;
    }

    public void setkullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }

    public String getkullaniciEmail() {
        return kullaniciEmail;
    }

    public void setkullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }
    public String getkullaniciSoyad() {
        return kullaniciSoyad;
    }

    public void setkullaniciSoyad(String kullaniciSoyad) {
        this.kullaniciSoyad = kullaniciSoyad;
    }

    public Kullanici(String Id, String kullaniciAd, String kullaniciSoyad,String kullaniciEmail){

        this.Id = Id;
        this.kullaniciAd = kullaniciAd;
        this.kullaniciSoyad = kullaniciSoyad;
        this.kullaniciEmail = kullaniciEmail;

    }
}
