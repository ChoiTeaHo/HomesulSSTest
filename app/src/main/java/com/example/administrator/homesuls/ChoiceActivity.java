package com.example.administrator.homesuls;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

//============================================툴바==========================================================

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_navigate_before_white_24dp); // 뒤로가기버튼 아이콘


//==========================================================================================================




        ImageButton img1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton img2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton img3 = (ImageButton) findViewById(R.id.imageButton3);








        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this, ThemeActivity.class);
                //        startActivityForResult(intent, 1);
                intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChoiceActivity.this, "선택함", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChoiceActivity.this, CupActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ChoiceActivity.this);
                alert.setTitle("Cheerluv Team");
                alert.setMessage( "개발중 입니다." );
                alert.setPositiveButton( "닫기", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int which) {
                        dialog.dismiss(); //닫기
                    }
                });
                alert.show();
            }
        });








    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
