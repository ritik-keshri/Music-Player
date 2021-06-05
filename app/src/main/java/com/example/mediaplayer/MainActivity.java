package com.example.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button play, pause, forwardSong, backwardSong;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private int currentTime;
    private final int time = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mediaPlayer = MediaPlayer.create(this, R.raw.legends_never_die);

        backwardSong.setOnClickListener(v -> {
            currentTime = mediaPlayer.getCurrentPosition();
            currentTime -= time;
            if(mediaPlayer.getDuration()-currentTime >= 0)
                mediaPlayer.seekTo(currentTime);
            else
                mediaPlayer.seekTo(1000);
        });

        play.setOnClickListener(v -> {
            mediaPlayer.start();

        });

        pause.setOnClickListener(v -> {
            mediaPlayer.pause();
        });

        forwardSong.setOnClickListener(v -> {
            if(mediaPlayer.getDuration()-currentTime >= 0) {
                currentTime = mediaPlayer.getCurrentPosition();
                currentTime += time;
                mediaPlayer.seekTo(currentTime);
            }
        });

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "Plating another Song", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                seekBar.setProgress(0);
            }
        });
    }

    public void init(){
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        forwardSong = findViewById(R.id.forward);
        backwardSong = findViewById(R.id.backward);
        seekBar = findViewById(R.id.seekbar);
    }
}