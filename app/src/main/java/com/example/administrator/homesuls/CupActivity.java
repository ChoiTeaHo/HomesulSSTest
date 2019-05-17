package com.example.administrator.homesuls;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class CupActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd videoAd;
    private AdRequest adRequest;
    //public AdView mAdView;
    //public AdRequest adRequest;
    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드


    ImageButton cup1,cup2,cup3,cup4,cup5,cup6;

    Intent returnIntent = new Intent();
    int a = 0;
    int b = 0;

    @Override
    protected void onResume() { //재개
        //mAdView.resume();
        super.onResume();
    }

    @Override
    protected void onPause() { //일시정지
        //mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() { //파괴
        //mAdView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup);


        MobileAds.initialize(this, "ca-app-pub-6571541518480270/7724994350~3838435874"); //앱ID
        videoAd = MobileAds.getRewardedVideoAdInstance(this);
        videoAd.setRewardedVideoAdListener(this);


        /**리워드 테스트전용 코드*/
        //adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("94FB26755CB6CA698ADD5C041D7E4596").build();

        /** 리워드 실제테스트 코드*/
        adRequest = new AdRequest.Builder().build();


        /**배너 테스트전용 코드*/
        /*mAdView = (AdView) findViewById(R.id.adView); //광고테스트용
        adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("94FB26755CB6CA698ADD5C041D7E4596").build();
        mAdView.loadAd(adRequest);*/

        /** 배너 테스트전용 실제 마켓올릴때*/
        //AdRequest adRequest = new AdRequest.Builder().build();
        // mAdView.loadAd(adRequest);




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
        cup3 = (ImageButton) findViewById(R.id.cup3);
        cup4 = (ImageButton) findViewById(R.id.cup4);
        cup5 = (ImageButton) findViewById(R.id.cup5);
        cup6 = (ImageButton) findViewById(R.id.cup6);



        cup1.setOnClickListener(listener);
        cup2.setOnClickListener(listener);
        cup3.setOnClickListener(listener);
        cup4.setOnClickListener(listener);
        cup5.setOnClickListener(listener);
        cup6.setOnClickListener(listener);






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


        try{
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    Thread.sleep(500);
                    return true;
            }

        }catch (InterruptedException e){
            e.printStackTrace();
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

                    try{
                        Thread.sleep(300);
                        finish();
                        break;
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }


                case R.id.cup2:
                    a=11;
                    returnIntent.putExtra("선택한 이미지", String.valueOf(a));
                    setResult(Activity.RESULT_OK, returnIntent);

                    try{
                        finish();
                        Thread.sleep(300);
                        break;

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                case R.id.cup3:
                    a=12;
                    returnIntent.putExtra("선택한 이미지", String.valueOf(a));
                    setResult(Activity.RESULT_OK, returnIntent);

                    try{
                        finish();
                        Thread.sleep(300);
                        break;

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                case R.id.cup4:
                    RewardDialog();
                    if(b==1){
                        Toast.makeText(CupActivity.this, "현재 컵은 개발중입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    break;

                case R.id.cup5:
                    RewardDialog();
                    if(b==1){
                        Toast.makeText(CupActivity.this, "현재 컵은 개발중입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else

                    break;

                case R.id.cup6:
                    RewardDialog(); //광고 호출
                    if(b==1) {
                        Toast.makeText(CupActivity.this, "현재 컵은 개발중입니다.", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(CupActivity.this, "b의 값: " + b, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else

                    break;



            }
        }
    };


    // 리워드 광고는 리워드 (보상) 에 대한 노출 후 동작이 필요하므로 보통 리스너를 구현한다.
    public void onRewardedVideoAdLoaded() {videoAd.show(); } // 광고가 로드되었을 때 호출
    public void onRewardedVideoAdOpened(){} // 광고가 노출되었을 때 호출
    public void onRewardedVideoStarted(){} // 비디오 광고가 시작될 때 호출
    public void onRewardedVideoAdClosed(){} // 광고가 닫혔을 때 호출 (x 버튼)
    public void onRewarded(RewardItem rewardItem){b=1;} // 비디오 광고가 종료되었을 때 보상 반환
    public void onRewardedVideoAdLeftApplication(){} // 광고가 노출되는 어플리케이션을 나갔을 때 호출
    public void onRewardedVideoAdFailedToLoad(int i){b=1;} // 광고 로드에 실패했을 때 호출




    public void RewardDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(this.getString(R.string.exit)) //혼술집
                .setMessage("광고를 시청하시겠습니까?")
                .setPositiveButton(this.getString(R.string.lookyes),
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick( DialogInterface dialog, int which )
                            {
                                //videoAd.loadAd("",adRequest); //테스트코드
                                videoAd.loadAd("ca-app-pub-6571541518480270/7724994350", adRequest);  //리워드 실제마켓 코드   //광고ID
                            }
                        }
                ).setNegativeButton(this.getString(R.string.no), null ).show();
    }



    @Override
    public void onBackPressed() {
        c_soundPool.play(click_Sound,1,1,1,0,1);
        super.onBackPressed();
    }
}
