package com.example.administrator.homesuls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-30.
 */

public class DialogActivity extends AppCompatActivity {
    AudioManager mAudioManager;

    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


        c_soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            AudioAttributes audio = new AudioAttributes.Builder()
                    . setContentType ( AudioAttributes . CONTENT_TYPE_MUSIC )
                    . setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                    . setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            new SoundPool.Builder().setAudioAttributes(audio).setMaxStreams(5).build();
            click_Sound = c_soundPool.load(this, R.raw.clicksound, 1);
            c_soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    //롤리팝버전부터
                }
            });
        }




        final Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.7); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.2);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        SeekBar seek = (SeekBar)findViewById(R.id.seekvolume);
        seek.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seek.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        seek.setOnSeekBarChangeListener(mOnSeek);



        ImageButton themebutton = (ImageButton) findViewById(R.id.themeButton);
        ImageButton healthListViewbutton = (ImageButton) findViewById(R.id.healthListViewButton);
        ImageButton helpbButton = (ImageButton) findViewById(R.id.helpButton);
/*
        final ImageButton soundButton = (ImageButton) findViewById(R.id.soundButton);
*/


//================================테마 인텐트=================================================


        themebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)

                Intent intent = new Intent(DialogActivity.this, ChoiceActivity.class);
                //        startActivityForResult(intent, 1);
                intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });

//================================건강백서 인텐트=================================================

        healthListViewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)

                Intent intent = new Intent(DialogActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });



//=================================헬프 인텐트=====================================================
        helpbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog();
            }
        });


//=================================================================================================



//===============================Sound on off 작업중=================================================







/*        soundButton.setOnClickListener(new View.OnClickListener() {
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
        });*/


    }

    SeekBar.OnSeekBarChangeListener mOnSeek =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                            1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                            1, //오른쪽 볼륨 float
                            1, //우선순위 int
                            0, //반복회수 int -1:무한반복, 0:반복안함
                            1); //재생속도 float 0.5(절반속도)~2.0(2배속)
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }









    public void helpDialog(){

        final ScrollView set_TextView = new ScrollView(getApplicationContext());
        TextView ready_Textview= new TextView(getApplicationContext());
        ready_Textview.setText("   Copyright 2017. \n   CheerluvTeam(layup3@naver.com) all rights reserved. ");
        ready_Textview.setTextSize(10);  //텍스트 사이즈 크기 지정
        set_TextView.addView(ready_Textview);



        new AlertDialog.Builder(this).setView(set_TextView)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(DialogActivity.this.getString(R.string.exit)) //혼술집
                .setMessage('\n' + "버전 0.0.1" + '\n' + "Cheerluv Team 개발" + '\n' + '\n' + '\n'+ '\n'+ '\n' +
                        "변경사항" + '\n' +  '\n' +
                        "#0.0.1" + '\n'+
                        "Can Cup 을 추가하였습니다." + '\n' + '\n' + '\n' + '\n' )
                .setPositiveButton(DialogActivity.this.getString(R.string.lookyes),
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick( DialogInterface dialog, int which )
                            {
                                c_soundPool.play(click_Sound,1,1,1,0,1);
                                finish();
                            }
                        }
                ).show();
        c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                1, //오른쪽 볼륨 float
                1, //우선순위 int
                0, //반복회수 int -1:무한반복, 0:반복안함
                1); //재생속도 float 0.5(절반속도)~2.0(2배속)

        }


    @Override
    public void onBackPressed() {
        c_soundPool.play(click_Sound,1,1,1,0,1);
        super.onBackPressed();
    }
}



