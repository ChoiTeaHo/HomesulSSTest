package com.example.administrator.homesuls;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by SJ on 2017-07-16.
 */

public class ItemView extends AppCompatActivity {
    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemview);












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


        LinearLayout topLL = (LinearLayout)findViewById(R.id.dynamicArea);
        LinearLayout mainLL = (LinearLayout)findViewById(R.id.mainArea);
//  ==============================================툴바==========================================================

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_navigate_before_white_24dp); // 뒤로가기버튼 아이콘
//  =============================================================================================================


//        TextView mText = (TextView)findViewById(R.id.text01);
        TextView toolText = (TextView)findViewById(R.id.toolbar_textview);
        String[] title = getResources().getStringArray(R.array.list);

        String[] items1;
        String[] items2;

        Intent intent = getIntent();
        String position = intent.getStringExtra("입력한 position");
        //클릭 한 항목에 따른 내용
        switch (position){
            /*case "0":
//                Toast.makeText(ItemView.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //mText.setText("00000");
                Intent intents = new Intent(Intent.ACTION_VIEW, Uri.parse("http://naver.com"));
                startActivity(intents);
                break;*/
            case "0":
                sound();
                toolText.setText(title[0]);
//                mainLL.setBackgroundColor(Color.rgb(255,153,204));
                items1 = getResources().getStringArray(R.array.list1_1);
                items2 = getResources().getStringArray(R.array.list1_2);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setTypeface(Typeface.MONOSPACE);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    textView01.setTextColor(Color.BLACK);

                    TextView textView02 = new TextView(this);
                    textView02.setText(items2[i]);
                    textView02.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    textView02.setTextColor(Color.BLACK);

                    topLL.addView(textView01);
                    topLL.addView(textView02);

                }
                break;
            case "1":
                sound();
                toolText.setText(title[1]);
                /*String[] items1 = {"하루 소주 3잔, 주 3회가 적당하다.", "몸이 차면 소주, 열이 많으면 맥주를 마시는 것이 좋습니다."};
                String[] items2 = {"개인에 따라 다르지만 하루 알코올 170g 이상은 간에 치명적입니다. 알코올 170g은 소주 2병 정도에 해당합니다. 전문가들은 적절한 하루 음주량으로 남자는 소주 3잔 정도, 여성은 1.5잔 정도를 권장하고 있습니다.\n",
                        "술 안주는 대개 육류인 경우가 많지만, 육류도 체질에 따라 달리 선택하는 게 좋습니다. 소음인은 닭고기, 소양인은 돼지고기, 태음인은 쇠고기를 택하면 좋습니다./n 두부, 우유 등 고단백 안주를 먹으면 간 기능에 도움이 됩니다. 우유와 치즈는 알코올 흡수를 늦춥니다."};*/
                items1 = getResources().getStringArray(R.array.list2_1);
                items2 = getResources().getStringArray(R.array.list2_2);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setTypeface(Typeface.MONOSPACE);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    textView01.setTextColor(Color.BLACK);

                    TextView textView02 = new TextView(this);
                    textView02.setText(items2[i]);
                    textView02.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    textView02.setTextColor(Color.BLACK);

                    topLL.addView(textView01);
                    topLL.addView(textView02);

                }
                break;
            case "2":
                sound();
                toolText.setText(title[2]);
                items1 = getResources().getStringArray(R.array.list3_1);
                items2 = getResources().getStringArray(R.array.list3_2);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setTypeface(Typeface.MONOSPACE);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    textView01.setTextColor(Color.BLACK);

                    TextView textView02 = new TextView(this);
                    textView02.setText(items2[i]);
                    textView02.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    textView02.setTextColor(Color.BLACK);

                    topLL.addView(textView01);
                    topLL.addView(textView02);

                }
                break;
            case "3":
                sound();
                toolText.setText(title[3]);
                items1 = getResources().getStringArray(R.array.list4_1);
                items2 = getResources().getStringArray(R.array.list4_2);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setTypeface(Typeface.MONOSPACE);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    textView01.setTextColor(Color.BLACK);

                    TextView textView02 = new TextView(this);
                    textView02.setText(items2[i]);
                    textView02.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    textView02.setTextColor(Color.BLACK);

                    topLL.addView(textView01);
                    topLL.addView(textView02);

                }
                break;
            case "4":
                sound();
                toolText.setText(title[4]);
                items1 = getResources().getStringArray(R.array.list5_1);
                items2 = getResources().getStringArray(R.array.list5_2);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    textView01.setTextColor(Color.BLACK);

                    TextView textView02 = new TextView(this);
                    textView02.setText(items2[i]);
                    textView02.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                    textView02.setTextColor(Color.BLACK);

                    topLL.addView(textView01);
                    topLL.addView(textView02);

                }
                break;
            case "5":
                sound();
                toolText.setText(title[5]);
                items1 = getResources().getStringArray(R.array.list6_1);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
                    textView01.setTextColor(Color.BLACK);

                    topLL.addView(textView01);

                }
                break;
            case "6":
                sound();
                toolText.setText(title[6]);
                items1 = getResources().getStringArray(R.array.list7_1);

                for (int i = 0; i<items1.length; i++){
                    TextView textView01 = new TextView(this);
                    textView01.setText(items1[i]);
                    textView01.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
                    textView01.setTextColor(Color.BLACK);

                    topLL.addView(textView01);

                }
                break;
            default:
//                Toast.makeText(ItemView.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
//                mText.setText("453534");
        }
    }

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

    public void sound(){
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
