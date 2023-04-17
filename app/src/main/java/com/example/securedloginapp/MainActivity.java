package com.example.securedloginapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {


    private TextInputLayout username;
    private TextInputLayout password;
    private MaterialButton login_BTN_Connect;
    private MaterialButton login_BTN_Camera;
    private CheckBox cboxNotARobot;
    private boolean tookPhoto = false;
    private Bitmap imageBitmap;

    private final static int GET_IMAGE_CAPTURE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();


        login_BTN_Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUsername() && checkPassword()) {
                    if (isConnected() && tookPhoto && approvedVolume() && isAirplaneModeOn()) {
                        movePage();
                    }
                }
            }
        });

        login_BTN_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, GET_IMAGE_CAPTURE);
            }
        });
    }

    private boolean approvedVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolumePercentage = 100 * currentVolume / maxVolume;
        if (currentVolumePercentage <= 50)
            return true;
        return false;
    }


    //return true if enabled
    private boolean isAirplaneModeOn() {

        return Settings.System.getInt(this.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

    }

    private boolean isConnected() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }

    private void movePage() {
//        Intent i = new Intent(getApplicationContext(), DestinationActivity.class);
//        i.putExtra("BitmapImage", this.imageBitmap);
//        startActivity(i);
    }

    private boolean checkPassword() {
//        return Validator.PatternPassword(this.password.getEditText().getText().toString());
        return true;
    }

    private boolean checkUsername() {
//        return Validator.PatternUsername(this.username.getEditText().getText().toString());
        return true;
    }

    private void findViews() {
        login_BTN_Connect = findViewById(R.id.login_BTN_Connect);
        login_BTN_Camera = findViewById(R.id.login_BTN_Camera);
        username = findViewById(R.id.login_TXT_username);
        password = findViewById(R.id.login_TXT_password);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            this.imageBitmap = (Bitmap) extras.get("data");
            this.tookPhoto = true;
        }
    }


}