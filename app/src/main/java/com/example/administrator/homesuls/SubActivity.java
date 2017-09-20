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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by SJ on 2017-06-30.
 */

public class SubActivity extends AppCompatActivity {
    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        c_soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        click_Sound = c_soundPool.load(this, R.raw.clicksound, 1);    //버튼클릭 소리


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_navigate_before_white_24dp);

//        String[] items = {"item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10", "item11", "item12", "item13", "item14", "item15", "item16"};

//        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ArrayAdapter<CharSequence> Adapter;
        Adapter = ArrayAdapter.createFromResource(this, R.array.list, android.R.layout.simple_list_item_1);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(Adapter);
        //목록 클릭시
        listView.setOnItemClickListener(mItemClickListener);
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

    //클릭 이벤트
    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SubActivity.this, ItemView.class);
            intent.putExtra("입력한 position", String.valueOf(position));
            startActivity(intent);
        }
    };
}
