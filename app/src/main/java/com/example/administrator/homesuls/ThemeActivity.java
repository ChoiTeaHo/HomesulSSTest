package com.example.administrator.homesuls;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Created by SJ on 2017-08-12.
 */

public class ThemeActivity extends AppCompatActivity {
    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드

    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        c_soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        click_Sound = c_soundPool.load(this, R.raw.clicksound, 1);    //버튼클릭 소리


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_navigate_before_white_24dp); // 뒤로가기버튼 아이콘


        flipper = (ViewFlipper)findViewById(R.id.flipper);

        //ViewFlipper가 View를 교체할 때 애니메이션이 적용되도록 설정
        //애니메이션은 안드로이드 시스템이 보유하고 있는  animation 리소스 파일 사용.
        //ViewFlipper의 View가 교체될 때 새로 보여지는 View의 등장 애니메이션
        //AnimationUtils 클래스 : 트윈(Tween) Animation 리소스 파일을 Animation 객체로 만들어 주는 클래스
        //AnimationUtils.loadAnimaion() - 트윈(Tween) Animation 리소스 파일을 Animation 객체로 만들어 주는 메소드
        //첫번째 파라미터 : Context
        //두번재 파라미터 : 트윈(Tween) Animation 리소스 파일(여기서는 안드로이드 시스템의 리소스 파일을 사용
        //                    (왼쪽에서 슬라이딩되며 등장)
        Animation showin = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        //ViewFlipper에게 등장 애니메이션 적용
        flipper.setInAnimation(showin);

        //ViewFlipper의 View가 교체될 때 퇴장하는 View의 애니메이션
        //오른쪽으로 슬라이딩 되면 퇴장하는 애니메이션 리소스 파일 적용.
        //위와 다른 방법으로 애니메이션을 적용해봅니다.
        //첫번째 파라미터 : Context
        //두번재 파라미터 : 트윈(Tween) Animation 리소스 파일(오른쪽으로 슬라이딩되며 퇴장)
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);



    }//절대영역















    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        sound();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void mOnClick(View v) throws InterruptedException {

        Handler mHandler = new Handler();

        final ImageButton previousBtn = (ImageButton) findViewById(R.id.btn_previous);
        final ImageButton nextBtn = (ImageButton) findViewById(R.id.btn_next);
        TextView themeText = (TextView)findViewById(R.id.themeText);

        int child =  flipper.getDisplayedChild();




        switch (v.getId()){



            case R.id.btn_previous:
                flipper.showPrevious();         //이전 View로 교체
                previousBtn.setImageResource(R.drawable.chevron_left_white);
                switch (child){
                    case 0: sound();
                        themeText.setText("세번째 테마");
                        break;
                    case 1: sound();
                        themeText.setText("첫번째 테마");
                        break;
                    case 2: sound();
                        themeText.setText("두번째 테마");
                        break;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        previousBtn.setImageResource(R.drawable.chevron_left);
                    }
                }, 300);
                break;



            case R.id.btn_next:
                flipper.showNext();             //다음 View로 교체
                nextBtn.setImageResource(R.drawable.chevron_right_white);
                switch (child){
                    case 0: sound();
                        themeText.setText("두번째 테마");
                        break;
                    case 1: sound();
                        themeText.setText("세번째 테마");
                        break;
                    case 2: sound();
                        themeText.setText("첫번째 테마");
                        break;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextBtn.setImageResource(R.drawable.chevron_right);
                    }
                }, 300);
                break;



            case R.id.btn_Close:
//                int child = flipper.getDisplayedChild();

                sound();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("선택한 이미지", String.valueOf(child));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;


        }
    }


    public void sound() {
        c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                1, //오른쪽 볼륨 float
                1, //우선순위 int
                0, //반복회수 int -1:무한반복, 0:반복안함
                1); //재생속도 float 0.5(절반속도)~2.0(2배속)
    }

    /*public void onClick(View v) {             // 뒤로가는 버튼
        switch (v.getId()) {
            case btn_Close :
                Intent returnIntent = new Intent();
                returnIntent.putExtra("선택한 이미지", "img1");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            *//*반환할 값이 없다면
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();*//*
        }
    }*/
}
