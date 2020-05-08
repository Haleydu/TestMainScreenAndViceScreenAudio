package com.example.testaudio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private AudioManager audioManager;
    private AudioDeviceInfo adi;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("getExternalStorageDirectory:", Environment.getExternalStorageDirectory().getAbsolutePath());

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                ComponentName cn = new ComponentName("com.example.testaudio", "com.example.testaudio.Main2Activity");
                intent.setComponent(cn);
                ActivityOptions options = ActivityOptions.makeBasic().setLaunchDisplayId(0);
                startActivity(intent, options.toBundle());

                //finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int disId = 0;
                Display display = getDisplayStartWidth(MainActivity.this, "HDMI");
                if(display != null) {
                    DisplayManager mDisplayService = (DisplayManager) MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
                    if(mDisplayService != null) {
                        Display[] displays = mDisplayService.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
                        Log.d(TAG, "displays= " + displays);
                        if (displays.length > 0) {
                            Display presentationDisplay = displays[0];
                            disId= displays[0].getDisplayId();
                            Log.d(TAG,"displays= "+disId+", presentationDisplay= "+presentationDisplay);
                        }
                    }
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    ComponentName cn = new ComponentName("com.example.testaudio", "com.example.testaudio.Main3Activity");
                    intent.setComponent(cn);
                    ActivityOptions options = ActivityOptions.makeBasic().setLaunchDisplayId(disId);
                    startActivity(intent, options.toBundle());

                    //finish();
                }
            }
        });
    }


    public static Display getDisplayStartWidth(Context mcontext, String nameprefix){
        Display finddisplay = null;
        DisplayManager manager = (DisplayManager) mcontext.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = manager.getDisplays();

        if(displays.length > 1) {
            for(Display display:displays){
                Log.d("SysUtil","display.getName():"+display.getName());
                if(display.getName().startsWith(nameprefix)){
                    finddisplay = display;
                    break;
                }
            }
        }

        return finddisplay;
    }
}
