package com.example.testaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import static android.app.ActivityOptions.makeBasic;

public class ViceScreenActivity extends AppCompatActivity {
    private static final String TAG = ViceScreenActivity.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.SurfaceView);

        //handler.postDelayed(runnable,5000);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMain2Activity();
            }
        });

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
        surfaceHolder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {

            }

            public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder) {
                Uri uri = Uri.parse("/storage/emulated/0/1.mkv");
                mMediaPlayer = MediaPlayer.create(ViceScreenActivity.this,uri,paramAnonymousSurfaceHolder);
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);
            }

            public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder) {
                mMediaPlayer.stop();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startMain2Activity();
        }
    };
    private void startMain2Activity(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        ComponentName cn = new ComponentName("com.example.testaudio", "com.example.testaudio.Main2Activity");
        intent.setComponent(cn);
        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            options = makeBasic().setLaunchDisplayId(0);
        }
        startActivity(intent, options.toBundle());
    }
}
