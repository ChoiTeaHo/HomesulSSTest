package com.example.administrator.homesuls;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2017-07-30.
 */

public class DialogActivity extends AppCompatActivity {




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.7); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.2);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        final AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        ImageButton themebutton = (ImageButton) findViewById(R.id.themeButton);
        ImageButton healthListViewbutton = (ImageButton) findViewById(R.id.healthListViewButton);
        final ImageButton soundButton = (ImageButton) findViewById(R.id.soundButton);



        themebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DialogActivity.this,MainActivity.class );
                startActivity(intent);
            }
        });

//================================건강백서 인텐트=================================================

        healthListViewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DialogActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });


//===============================Sound on off 작업중=================================================

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //현재 볼륨 가져오기

                if (volume > 0) {  //볼륨이 0 이상 일때 볼룸을 끄는 역할
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume - 15, AudioManager.FLAG_PLAY_SOUND);

                    soundButton.setImageResource(R.drawable.volume_mute_white_24dp); //볼륨이미지 꺼지게 교체

                } else if (volume < 15) { //볼륨이 15이하 일때 볼륨을 키는 역할
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume + 5, AudioManager.FLAG_PLAY_SOUND);

                    soundButton.setImageResource(R.drawable.volume_up_white_24dp); //볼륨이미지 켜지게 교체
                } else {
                }


            }
        });





    }

}
