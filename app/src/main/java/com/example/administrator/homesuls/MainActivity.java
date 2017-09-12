package com.example.administrator.homesuls;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    AudioManager soundMode;  //사운드 모드 (무음/소리 등)
    private static MediaPlayer mp; //배경음 재생을 위한 MediaPlayer을 가지는 mp.



    private SensorManager m_sensorManager; //근접센서를 위한 m_sensorManager.
    private Sensor m_sensor; //근접센서를 위해 m_sonsorManager를 연결받기 위한 m_snsor


//Soundpool파트
    SoundPool m_soundPool; // 맥주따르는 소리를 위한 Soundpool
    int beerFlow_Sound; // 맥주따르는소리

    SoundPool m_cheerssound1; // 건배소리를 위한 Soundpool
    int cheers_Sound1; //유리 건배소리




//애니메이션 파트
    ImageView paperjan_Img; //종이컵img
    AnimationDrawable paperjanAni; //AnimationDrawble를 가진 paperjanAni 변수선언 ( paperjan_Img 를 위해 사용할 것임. )

/*
    ImageView character_Img;  //눈깜빡이는img
    AnimationDrawable characterAni;  //AnimationDrawble를 가진 characterAni 변수선언 ( character_Img 를 위해 사용할 것임. )


    ImageView flowBeer_Img; //맥주따르는img
    AnimationDrawable ani; //AnimationDrawble를 가진 ani 변수선언 ( flowBeer_Img 를 위해 사용할 것임.)
*/

    ImageView star1_Img, star2_Img;


    //=============================================백버튼종료 로직=======================================================================================
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //마지막 액티비티에서 백버튼을 눌렀을 시.
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(this.getString(R.string.exit))
                    .setMessage(this.getString(R.string.exit_message))
                    .setPositiveButton(this.getString(R.string.yes),
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick( DialogInterface dialog, int which )
                                {
                                    moveTaskToBack(true);
                                    finish();
                                }
                            }
                    ).setNegativeButton(this.getString(R.string.no), null ).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    public void clearApplicationCache(java.io.File dir){  //종료시 모든 캐쉬 삭제 코드, removeSessionCookie의 처리방법 변경으로 인해 확실히 구현될지 모르겠음.
        if(dir==null) dir = getCacheDir();
        if(dir==null) return;
        java.io.File[] children = dir.listFiles();
        try{
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();

            for(int i=0;i<children.length;i++)
                if(children[i].isDirectory())
                    clearApplicationCache(children[i]);
                else children[i].delete();
        }
        catch(Exception e){}
    }

//**************************************************************OnCreate영역***********************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //Activity화면꺼짐막기
        setContentView(R.layout.activity_main);





//=============================================모드 사운드==========================================================================================
        soundMode = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);  // 사운드 ON OFF를 위한 현재 오디오 모드확인.



//=============================================배경 사운드==========================================================================================

        mp = MediaPlayer.create(this, R.raw.mainbgm); //배경음을 위해 현재액티비티에 raw파일에 있는 mainbgm 을 mp에 연결
        mp.setLooping(true); //mp 무한반복
        //mp.start(); //시작


//=============================================사운드==========================================================================================
        m_soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        beerFlow_Sound = m_soundPool.load(this, R.raw.beerflowbgm, 1);       // 각 재생음악을 미리준비함. this는 현재화면 제어권자
        //R.raw.cheers는 음악파일
        //1은 우선순위//1은 우선순위

        m_cheerssound1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        cheers_Sound1 = m_soundPool.load(this, R.raw.cheerssound, 1);       // 각 재생음악을 미리준비함. this는 현재화면 제어권자
        //R.raw.cheers는 음악파일
        //1은 우선순위//1은 우선순위

//=============================================================================================================================================




        paperjan_Img = (ImageView) findViewById(R.id.paperjan_Img);
        paperjan_Img.setBackgroundResource(R.drawable.ani_paperjan);
        paperjanAni = (AnimationDrawable) paperjan_Img.getBackground();


/*        character_Img = (ImageView) findViewById(R.id.character_Img); //character_Img 객체에 ImageView를 xml에서 명시받음
        character_Img.setBackgroundResource(R.drawable.ani_maincharacter); //명시받은 character_Img의 백그라운드 리소스를 ani_maincharacter.xml로 지정
        characterAni = (AnimationDrawable) character_Img.getBackground(); // characterAni 애니메이션에 리소스지정한 character_Imgw의 백그라운드를 넣음

        flowBeer_Img = (ImageView) findViewById(R.id.flowBeer_Img); //1은 맥주 따르는 이미지
        flowBeer_Img.setBackgroundResource(R.drawable.ani_beer); //맥주 Frame Animation 파일을 불러와 img(맥주따르는이미지)의 배경리소스를 set하ㅣㅇ바꿔줌.
        ani = (AnimationDrawable) flowBeer_Img.getBackground();
        ani.setOneShot(true);*/




        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);  //센서
        m_sensor = m_sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);  //센서
        //m_sensorManager.registerListener(this, m_sensor, m_sensorManager.SENSOR_DELAY_NORMAL);  //센서등록










//================================================툴바를 위한 코드===================================================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Toolbar를 받아 만들어진 toolbar.
        setSupportActionBar(toolbar); //ActionBar를 set하여 toolbar로
        ActionBar actionBar = getSupportActionBar(); // 툴바로 만들어진 ActionBar를 받아 만들어진 actionBar

        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        //actionBar.setHomeAsUpIndicator(R.drawable.button_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요함.  하지만 Main 에서는 필요없기 때문에 주석처리.






    }  //절대영역




//=====================================================라이프사이클 생명주기============================================================================

    @Override
    public void onResume(){  //다시 돌아오면
        //mp.start(); //배경음악 시작
/*
        ani.start();
*/
/*        m_soundPool.play(beerFlow_Sound,  //준비한 soundID 맥주따르는 효과음
                1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                1, //오른쪽 볼륨 float
                0, //우선순위 int
                0, //반복회수 int -1:무한반복, 0:반복안함
                1); //재생속도 float 0.5(절반속도)~2.0(2배속)*/
        m_sensorManager.registerListener(this, m_sensor, m_sensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }


    @Override
    protected void onPause() {
        m_sensorManager.unregisterListener(this); // 센서리스너 종료
        super.onPause();
    }


    @Override
    protected void onUserLeaveHint() {  //홈버튼을 눌렀을때


        mp.pause(); //배경음악 일시정지
        m_sensorManager.unregisterListener(this); // 센서리스너 종료

        super.onUserLeaveHint();
    }

    @Override
    public void onBackPressed() {  //백버튼을 눌렀을때 (센서리스너를 해제하니 종료가아닌백만눌러도 해제)
        mp.stop(); //배경음악 정지
        m_sensorManager.unregisterListener(this);

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {  //앱이 아얘 정지됬을때
        mp.stop(); //배경음악 정지
        m_sensorManager.unregisterListener(this); // 센서리스너 종료


        super.onDestroy();
        clearApplicationCache(null); //위의 종료시 모든 캐쉬 삭제 코드
        android.os.Process.killProcess(android.os.Process.myPid() );
    }

    @Override
    protected void onStop() {
        m_sensorManager.unregisterListener(this);
        super.onStop();
    }

    //===========================================액티비티에 포커스를 받았을때 실행되는 코드========================================================================================
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus) {  //포커스가 있을때

/*
        characterAni.start();  // 전에 character_Img에 애니메이션xml파일을 넣고 setBackground하여 생성한 characterAni 애니메이션 시작
*/

        }

        else{   //포커스가 떠나면

        }
        super.onWindowFocusChanged(hasFocus);
    }

//==========================================툴바옵션메뉴생성=========================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();   //인플레이터를 사용하여 결합
        inflater.inflate(R.menu.menu_toolbar, menu); //menu 폴더에 정의하고 만들어놓은 menu_toolbar.xml 파일을 inflater, 레퍼런스는 menu를 사용
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuOption_Item){  //menu 폴더에 정의하고 만들어놓은 menu_toolbar.xml 안에서 정의한 item 이 menuOption_Item 일때

            Intent intent = new Intent(MainActivity.this, DialogActivity.class);  // DialogActivity 시작. (  DialogActivity 는 액티비티를 다이얼로그형태로 구현한 액티비티임.)
            startActivity(intent);
        }


        return true;}


//==================================================센서==================================================================================
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] == 0){   //근접했을때


            Animation scaleanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);  //스케일 트윈애니메이션. 점점커지는 맥주 애니메이션효과를 가진 scale.xml 받는 scaleanimation 객체 생성.
            paperjan_Img.startAnimation(scaleanimation); //bigBeer_Img에 적용하고 애니메이션 실행 ( 점점 커지게 된다. )*//**//*
            paperjanAni.setVisible(false, false);
            //paperjan_Img.setVisibility(View.VISIBLE);
            paperjanAni.start();





            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    m_soundPool.play(cheers_Sound1,  //준비한 soundID 맥주따르는 효과음
                            1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                            1, //오른쪽 볼륨 float
                            0, //우선순위 int
                            0, //반복회수 int -1:무한반복, 0:반복안함
                            1); //재생속도 float 0.5(절반속도)~2.0(2배속)

                }
            },800);




/*
            flowBeer_Img.setVisibility(View.INVISIBLE); //flowBeer_Img를 Invisible상태로 만들어 숨겨준다.
*/


/*            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)bigBeer_Img.getLayoutParams(); //RelativeLayout 커지는 맥주의 img 의 크기를 객체화
            layoutParams.width = 1000; //가로 1000dp로 만들고 설정.
            bigBeer_Img.setLayoutParams(layoutParams);
            layoutParams.height = 1800; //세로 1800dp로 만들고 설정.
            bigBeer_Img.setLayoutParams(layoutParams);


            Animation scaleanimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);  //스케일 트윈애니메이션. 점점커지는 맥주 애니메이션효과를 가진 scale.xml 받는 scaleanimation 객체 생성.
            bigBeer_Img.startAnimation(scaleanimation); //bigBeer_Img에 적용하고 애니메이션 실행 ( 점점 커지게 된다. )*/





        }
        else{  //근접하지 않았을때

            paperjan_Img.setVisibility(View.INVISIBLE);

            paperjanAni.stop();
            Animation t = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);  //스케일 트윈애니메이션. 점점커지는 맥주 애니메이션효과를 가진 scale.xml 받는 scaleanimation 객체 생성.
            paperjan_Img.startAnimation(t);


            m_soundPool.play(cheers_Sound1,  //준비한 soundID 맥주따르는 효과음
                    0, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                    0, //오른쪽 볼륨 float
                    1, //우선순위 int
                    0, //반복회수 int -1:무한반복, 0:반복안함
                    1); //재생속도 float 0.5(절반속도)~2.0(2배속)


/*
            flowBeer_Img.setVisibility(View.VISIBLE); //flowBeer_Img를 Visible상태로 만들어 화면에 표시해준다.
            ani.start();*/


/*            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)bigBeer_Img.getLayoutParams();
            layoutParams.width = 0;
            bigBeer_Img.setLayoutParams(layoutParams);
            layoutParams.height =  0;
            bigBeer_Img.setLayoutParams(layoutParams);*/
        }

    }




   /* boolean registerListener(SensorEventListener listener){  //센서리스너 등록 함수()
        m_sensorManager.registerListener(this, m_sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return false;
    }*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {  // (Sensor sensor, int i)에서 int i는 현 기기 정확도를 의미 정확도는 3개로 나뉜다.
    }                                                         //SENSOR_STATUS_ACCURACY_HIGH        정확도 높음
                                                              // SENSOR_STATUS_ACCURACY_MEDIUM  정확도 중간
                                                              //- SENSOR_STATUS_ACCURACY_LOW        정확도 낮음
                                                              //- SENSOR_STATUS_UNRELIABLE               신뢰할 수 없음

//=================================================================================================================================================

}
