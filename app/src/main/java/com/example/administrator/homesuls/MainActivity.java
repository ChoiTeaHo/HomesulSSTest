package com.example.administrator.homesuls;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import static com.example.administrator.homesuls.R.id.menuOption_Item;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int so =10;

    String result;
//배경음 파트
    AudioManager soundMode;  //사운드 모드 (무음/소리 등)
    private static MediaPlayer mp; //배경음 재생을 위한 MediaPlayer을 가지는 mp.


//센서 파트
    private SensorManager m_sensorManager; //근접센서를 위한 m_sensorManager.
    private Sensor m_sensor; //근접센서를 위해 m_sonsorManager를 연결받기 위한 m_snsor


//Soundpool파트
    SoundPool m_soundPool; // Sound Pool 을 담는 그릇
    int sojuFlow_Sound; //소주 따르는 소리
    int beerFlow_Sound; // 맥주따르는소리
    int canFlow_Sound; //캔 따르는(따는) 소리

    int cheers_Sound1; //유리 건배소리
    int papercheers_Sound1; //종이컵 건배소리임시
    int cancheers_Sound1; //캔 건배소리

    int click_Sound; //클릭사운드



//병 파트
    ImageView bootle_Img;



//애니메이션 파트
    //플로우와 이펙트 그릇
    ImageView flowcup_Img; // 플로우이미지 Img
    ImageView effectcup_Img; // 이펙트컵 img





    //플로우와 이펙트 디폴트 값 대상.
    AnimationDrawable flowcup_Ani; // 플로우소주컵 ani
    AnimationDrawable effetpapersojucup_Ani; // 이펙트소주컵 ani





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

        beerFlow_Sound = m_soundPool.load(this, R.raw.beerflowcutbgm, 1);   //맥주따르는소리    // 각 재생음악을 미리준비함. this는 현재화면 제어권자 , 1은 우선순위//1은 우선순위
        sojuFlow_Sound = m_soundPool.load(this, R.raw.sojuflowbgm, 1);   //소주따르는소리
        canFlow_Sound = m_soundPool.load(this,R.raw.canflowbgm, 1); //캔따르는(따는)소리

        cheers_Sound1 = m_soundPool.load(this, R.raw.cheerssound, 1);    //소주잔 건배소리
        papercheers_Sound1 = m_soundPool.load(this, R.raw.papercheers_sound, 1); //종이컵 건배소리
        cancheers_Sound1 = m_soundPool.load(this, R.raw.cancheers_sound, 1);

        click_Sound = m_soundPool.load(this, R.raw.clicksound, 1);    //버튼클릭 소리

//=============================================================================================================================================

        //bottle 이미지 명시하기 디폴트 값 == 소주병
        bootle_Img = (ImageView) findViewById(R.id.bootle_Img);



        //플로우컵 명시하기 디폴트값 ani_flowpapersojucup  == 종이소주컵
        flowcup_Img = (ImageView) findViewById(R.id.flowcup_Img);  //플로우컵 객체 생성
        flowcup_Img.setBackgroundResource(R.drawable.ani_flowpapersojucup);  //플로우컵 객체에    flow 종이소주컵애니메이션 넣기
        flowcup_Ani = (AnimationDrawable) flowcup_Img.getBackground(); // 애니메이션 변수에 삽입.
        flowcup_Ani.setOneShot(true);


        //이펙트컵 애니메이션 적용하기.
        effectcup_Img = (ImageView) findViewById(R.id.effectcup_Img);
        effectcup_Img.setBackgroundResource(R.drawable.ani_effectpapersojucup);
        effetpapersojucup_Ani = (AnimationDrawable) effectcup_Img.getBackground();











//====================================================================================================================================
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
//==================================================================================================================================


//=================================================게시판 보틀버튼====================================================================
        bootle_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)

               Intent CIntent = new Intent(MainActivity.this, ChatActivity.class); //게시판실행
                startActivity(CIntent);
            }
        });
//========================================================================================================================================







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
        mp.pause(); //배경음악 일시정지
        m_sensorManager.unregisterListener(this); // 센서리스너 종료
        super.onPause();
    }


    @Override
    protected void onUserLeaveHint() {  //홈버튼을 눌렀을때
/*        m_soundPool.stop(clickSnd);
*//* SoundPool 자원해제. SoundPool 개체에서 사용하는 모든 메모리와 네이티브 리소스를 해제. SoundPool은 더 이상 사용될 수 없고 참조가 null로 설정되어야합니다. *//*
        soundPool.release();
        soundPool = null;*/




        mp.pause(); //배경음악 일시정지
        m_sensorManager.unregisterListener(this); // 센서리스너 종료

        super.onUserLeaveHint();
    }

    @Override
    public void onBackPressed() {  //백버튼을 눌렀을때 (센서리스너를 해제하니 종료가아닌백만눌러도 해제)

        m_soundPool.play(click_Sound,1,1,1,0,1);

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
    public boolean onCreateOptionsMenu(Menu menu) {  //디바이스의 Menu 버튼이 처음 눌러졋을 때 한번 호출되는 메소드
        final MenuInflater inflater = getMenuInflater();   //인플레이터를 사용하여 결합
        inflater.inflate(R.menu.menu_toolbar, menu); //menu 폴더에 정의하고 만들어놓은 menu_toolbar.xml 파일을 inflater, 레퍼런스는 menu를 사용
        return true;
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menuOption_Item).setIcon(R.drawable.ic_crop_original_white_24dp);
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //OptionMenu의 MenuItem 중 하나를 눌렀을 때 자동으로 호출되는 메소드
        if (item.getItemId() == menuOption_Item){  //menu 폴더에 정의하고 만들어놓은 menu_toolbar.xml 안에서 정의한 item 이 menuOption_Item 일때
            m_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                    1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                    1, //오른쪽 볼륨 float
                    1, //우선순위 int
                    0, //반복회수 int -1:무한반복, 0:반복안함
                    1); //재생속도 float 0.5(절반속도)~2.0(2배속)


            try{
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);  // DialogActivity 시작. (  DialogActivity 는 액티비티를 다이얼로그형태로 구현한 액티비티임.)
                startActivityForResult(intent, 1);
                Thread.sleep(300);
            }catch (InterruptedException e){
                e.printStackTrace();
            }



        }
        return true;
    }




    //==================================================센서==================================================================================
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] == 0){   //근접했을때
            Drawable imgttt = effectcup_Img.getBackground(); //투명화를 위한 imgttt 객체. 안에 커지는 애니 넣음 중복막기위해 편법
            imgttt.setAlpha(255); // imgttt 객체에 투명 적용

            Animation scaleanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);//scale.xml 받는 scaleanimation 객체 생성.
            effectcup_Img.startAnimation(scaleanimation); //트윈애니메이션 적용하고 애니메이션 실행 ( 점점 커지게 된다. )
            effetpapersojucup_Ani.setVisible(false, false); //화면에 표시
            effetpapersojucup_Ani.start(); //이펙트 애니메이션 시작


            switch (so) {
                case 10:
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            m_soundPool.play(papercheers_Sound1,  //준비한 soundID 종이컵 효과음
                                    1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                                    1, //오른쪽 볼륨 float
                                    0, //우선순위 int
                                    0, //반복회수 int -1:무한반복, 0:반복안함
                                    1); //재생속도 float 0.5(절반속도)~2.0(2배속)
                        }
                    }, 800);
                    break;
                case 11:
                    if (so == 11) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                m_soundPool.play(cheers_Sound1,  //준비한 soundID beer 따르는 효과음
                                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                                        1, //오른쪽 볼륨 float
                                        0, //우선순위 int
                                        0, //반복회수 int -1:무한반복, 0:반복안함
                                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)
                            }
                        }, 800);
                    }
                    break;

                case 12:
                    if (so == 12) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                m_soundPool.play(cancheers_Sound1,  //준비한 soundID can 따는 효과음
                                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                                        1, //오른쪽 볼륨 float
                                        0, //우선순위 int
                                        0, //반복회수 int -1:무한반복, 0:반복안함
                                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)
                            }
                        }, 800);
                    }
                    break;
            }
            flowcup_Img.setVisibility(View.INVISIBLE);













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

            effectcup_Img.setVisibility(View.INVISIBLE); // 이펙트이미지를 보이지 않도록 숨김.
            effetpapersojucup_Ani.stop(); //이펙트애니메이션 중지.

            Drawable imgttt = effectcup_Img.getBackground();
            imgttt.setAlpha(0); //투명화 시킴.

            Animation t = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bouncescale);  //바운스 트윈애니메이션.
            flowcup_Img.startAnimation(t);

            flowcup_Img.setVisibility(View.VISIBLE);
            flowcup_Ani.start();

            if(so == 10) {
                m_soundPool.play(sojuFlow_Sound,
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)
            }
            if(so == 11){
                m_soundPool.play(beerFlow_Sound,
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)
            }
            if(so == 12){
                m_soundPool.play(canFlow_Sound,
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)
            }



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





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ConstraintLayout linearLayout = (ConstraintLayout) findViewById(R.id.Rela);
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                result = data.getStringExtra("선택한 이미지");
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                switch (result){
                    case "0":
                        linearLayout.setBackgroundResource(R.drawable.examplebackground);  // 테마선택 나무
                        break;
                    case "1":
                        linearLayout.setBackgroundResource(R.drawable.rentedroombackground); //자취방
                        break;
                    case "2":
                        linearLayout.setBackgroundResource(R.drawable.hangangbackground);  //한강
                        break;



                    //10번 부터는 컵/병/이펙트 선택
                    case "10": //종이컵
                        so = 10;
                        effectcup_Img.setBackgroundResource(R.drawable.ani_effectpapersojucup);
                        effetpapersojucup_Ani = (AnimationDrawable) effectcup_Img.getBackground();
                        effetpapersojucup_Ani.setOneShot(true);

                        flowcup_Img.setBackgroundResource(R.drawable.ani_flowpapersojucup);
                        flowcup_Ani = (AnimationDrawable) flowcup_Img.getBackground();
                        flowcup_Ani.setOneShot(true);


                        //soju bottle 설정
                        bootle_Img.setBackgroundResource(R.drawable.sojubottle);
                        ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams)bootle_Img.getLayoutParams(); //RelativeLayout 커지는 맥주의 img 의 크기를 객체화
                        layoutParams10.width = 90; //가로 1000dp로 만들고 설정.
                        bootle_Img.setLayoutParams(layoutParams10);
                        layoutParams10.height = 300; //세로 1800dp로 만들고 설정.
                        bootle_Img.setLayoutParams(layoutParams10);
                        break;




                    case "11": //맥주컵
                        so = 11;
                        effectcup_Img.setBackgroundResource(R.drawable.ani_effectbeernewcup);   //ani_straightjan
                        effetpapersojucup_Ani = (AnimationDrawable) effectcup_Img.getBackground();
                        effetpapersojucup_Ani.setOneShot(true);



          /*              // 준호가만든 새로운 맥주 컵 사이즈문제 확인했습니다.
                        ConstraintLayout.LayoutParams layoutParams12 = (ConstraintLayout.LayoutParams)flowcup_Img.getLayoutParams(); //RelativeLayout 커지는 맥주의 img 의 크기를 객체화
                        layoutParams12.width = 130; //가로 130dp로 만들고 설정.
                        flowcup_Img.setLayoutParams(layoutParams12);
                        layoutParams12.height = 320; //세로 320dp로 만들고 설정.
                        flowcup_Img.setLayoutParams(layoutParams12);*/



                        flowcup_Img.setBackgroundResource(R.drawable.ani_beernewcup);       //ani_beernewcup 사이즈문제 확인했습니다.
                        flowcup_Ani = (AnimationDrawable) flowcup_Img.getBackground();
                        flowcup_Ani.setOneShot(false);




                        //맥주bottle 설정 및 사이즈조절.
                        bootle_Img.setBackgroundResource(R.drawable.beerbottle);
                        ConstraintLayout.LayoutParams layoutParams11 = (ConstraintLayout.LayoutParams)bootle_Img.getLayoutParams(); //RelativeLayout 커지는 맥주의 img 의 크기를 객체화
                        layoutParams11.width = 110; //가로 1000dp로 만들고 설정.
                        bootle_Img.setLayoutParams(layoutParams11);
                        layoutParams11.height = 400; //세로 1800dp로 만들고 설정.
                        bootle_Img.setLayoutParams(layoutParams11);
                        break;


                    //캔컵
                    case "12": //캔컵
                        so = 12;
                        effectcup_Img.setBackgroundResource(R.drawable.ani_effectcancup);
                        effetpapersojucup_Ani = (AnimationDrawable) effectcup_Img.getBackground();
                        effetpapersojucup_Ani.setOneShot(true);

                        flowcup_Img.setBackgroundResource(R.drawable.ani_cannewcup);       //ani_beernewcup 사이즈문제 확인했습니다.
                        flowcup_Ani = (AnimationDrawable) flowcup_Img.getBackground();
                        flowcup_Ani.setOneShot(false);



                        //캔bottle 설정
                        bootle_Img.setBackgroundResource(R.drawable.beerbottle);
                        //bootle_Img.setBackgroundResource(R.drawable.canbottle);
                        ConstraintLayout.LayoutParams layoutParams12 = (ConstraintLayout.LayoutParams)bootle_Img.getLayoutParams(); //RelativeLayout 커지는 맥주의 img 의 크기를 객체화
                        layoutParams12.width = 110; //가로 1000dp로 만들고 설정.
                        bootle_Img.setLayoutParams(layoutParams12);
                        layoutParams12.height = 400; //세로 1800dp로 만들고 설정.
                        bootle_Img.setLayoutParams(layoutParams12);
                    break;
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                //반환값이 없을 경우의 코드
            }
        }
    }













    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {  // (Sensor sensor, int i)에서 int i는 현 기기 정확도를 의미 정확도는 3개로 나뉜다.
    }                                                         //SENSOR_STATUS_ACCURACY_HIGH        정확도 높음
                                                              // SENSOR_STATUS_ACCURACY_MEDIUM  정확도 중간
                                                              //- SENSOR_STATUS_ACCURACY_LOW        정확도 낮음
                                                              //- SENSOR_STATUS_UNRELIABLE               신뢰할 수 없음

//=================================================================================================================================================



}
