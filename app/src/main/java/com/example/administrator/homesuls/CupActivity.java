package com.example.administrator.homesuls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class CupActivity extends AppCompatActivity {


    ImageButton cup1,cup2;

    Intent returnIntent = new Intent();
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup);

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
