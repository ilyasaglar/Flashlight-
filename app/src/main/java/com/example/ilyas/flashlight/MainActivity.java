package com.example.ilyas.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;

public class MainActivity extends AppCompatActivity {

    Global global;
    boolean isTorchOn = false;
    private AdView mAdView;
    TextView kapali;
    public static Context context;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        MobileAds.initialize(this, "ca-app-pub-3958354860620549/9465193534");    //banner reklam

        mAdView = (AdView) findViewById(R.id.adView); //Reklamın layoutda tanımladığımız idsini alıyoruz ve load ediyoruz.
       // AdRequest adRequest = new AdRequest.Builder().build().isTestDevice()
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        kapali= findViewById(R.id.kapali);
        global = new Global();

        startService(new Intent(this, ShakeDetectService.class));
    }

    /*
    public void SensorGelen(String Gelen) {

        try{
            if(Gelen != null){
                button.setBackgroundResource(R.drawable.btn_off);
                kapali.setText("Light On / Fener Açık"+"");

            }else{
                kapali.setText("Light Off / Fener Kapalı"+"");


            }


        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }



    }
*/
    public void toggle(View view) {
        button = (Button) view;
        if (button.getText().equals("Switch On")) {
            button.setText(R.string.switch_off_text);
            button.setBackgroundResource(R.drawable.btn_off);
            kapali.setText("Light On / Fener Açık");
            torchToggle("on");
        } else {
            button.setText(R.string.switch_on_text);
            button.setBackgroundResource(R.drawable.btn_on);
            kapali.setText("Light Off / Fener Kapalı");
            torchToggle("off");
        }

    }

    private void torchToggle(String command) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null; // Usually back camera is at 0 position.
            try {
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0];
                }
                if (camManager != null) {
                    if (command.equals("on")) {
                        camManager.setTorchMode(cameraId, true);   // Turn ON
                        isTorchOn = true;
                    } else {
                        camManager.setTorchMode(cameraId, false);  // Turn OFF
                        isTorchOn = false;
                    }
                }
            } catch (CameraAccessException e) {
                e.getMessage();
            }
        }
    }

    public void goToSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }




}
