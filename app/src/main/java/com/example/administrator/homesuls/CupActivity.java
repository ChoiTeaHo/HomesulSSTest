package com.example.administrator.homesuls;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class CupActivity extends AppCompatActivity {


    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드


    ImageButton cup1,cup2;

    Intent returnIntent = new Intent();
    int a=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup);




        c_soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        click_Sound = c_soundPool.load(this, R.raw.clicksound, 1);    //버튼클릭 소리
//============================================툴바==========================================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_navigate_before_white_24dp); // 뒤로가기버튼 아이콘
//===========================================================================================================


        cup1 = (ImageButton) findViewById(R.id.cup1);
        cup2 = (ImageButton) findViewById(R.id.cup2);

        cup1.setOnClickListener(listener);
        cup2.setOnClickListener(listener);




    }





//=========================================툴바백버튼 뒤로가기 이벤트==========================================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                1, //오른쪽 볼륨 float
                1, //우선순위 int
                0, //반복회수 int -1:무한반복, 0:반복안함
                1); //재생속도 float 0.5(절반속도)~2.0(2배속)

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//===========================================================================================================






    ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                    1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                    1, //오른쪽 볼륨 float
                    1, //우선순위 int
                    0, //반복회수 int -1:무한반복, 0:반복안함
                    1); //재생속도 float 0.5(절반속도)~2.0(2배속)

            switch (v.getId()){
                case R.id.cup1:
                    a=10;
                    returnIntent.putExtra("선택한 이미지", String.valueOf(a));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    break;


                case R.id.cup2:
                    a=11;
                    returnIntent.putExtra("선택한 이미지", String.valueOf(a));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                    break;

            }
        }
    };

}
