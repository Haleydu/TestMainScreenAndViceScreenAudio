package com.example.testaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainScreenActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.SurfaceView1);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        surfaceHolder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {

            }

            public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
            {
                Uri uri = Uri.parse("/storage/emulated/0/1.mkv");
                mMediaPlayer = MediaPlayer.create(MainScreenActivity.this,uri,paramAnonymousSurfaceHolder);
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);
            }

            public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder) {
                mMediaPlayer.stop();
            }
        });
    }
}
