package com.example.administrator.homesuls;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class ChoiceActivity extends AppCompatActivity {

    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);


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


//==========================================================================================================


        LinearLayout choicelinear1 = (LinearLayout)findViewById(R.id.chocieBtn1);
        LinearLayout choicelinear2 = (LinearLayout)findViewById(R.id.chocieBtn2);



      /*  Button choiceButton1 = (Button) findViewById(choiceButton1);
        Button choiceButton2 = (Button) findViewById(R.id.choiceButton2);
        Button choiceButton3 = (Button) findViewById(R.id.choiceButton3);*/








        choicelinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound();

                Intent intent = new Intent(ChoiceActivity.this, ThemeActivity.class);
                //        startActivityForResult(intent, 1);
                intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });




        choicelinear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound();

                Intent intent = new Intent(ChoiceActivity.this, CupActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });



     /*   choiceButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound();


                AlertDialog.Builder alert = new AlertDialog.Builder(ChoiceActivity.this);
                alert.setTitle("Cheerluv Team");
                alert.setMessage( "개발중 입니다." + '\n' +
                        "입력인식 검사(o)" + '\n' +
                        "음성인식 검사(o)" + '\n' +
                        "자이로센서 검사(x) " );
                alert.setPositiveButton( "닫기", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int which) {
                        dialog.dismiss(); //닫기
                    }
                });
                alert.show();
            }
        });
*/







    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sound();

                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }



    public  void sound(){
        c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                1, //오른쪽 볼륨 float
                1, //우선순위 int
                0, //반복회수 int -1:무한반복, 0:반복안함
                1); //재생속도 float 0.5(절반속도)~2.0(2배속)
    }

    @Override
    public void onBackPressed() {
        sound();
        super.onBackPressed();
    }
}
