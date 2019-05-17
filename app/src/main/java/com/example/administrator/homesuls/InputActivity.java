package com.example.administrator.homesuls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InputActivity extends AppCompatActivity {
    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드



    private static String TAG = "phptest_MainActivity";

    private EditText mEditTextName;
    private EditText mEditTextAddress;
    private TextView mTextViewResult;

    //private EditText mEditTextAge; /**추가*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        c_soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        click_Sound = c_soundPool.load(this, R.raw.clicksound, 1);    //버튼클릭 소리
//=======================================툴바 영역=========================================================================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_navigate_before_white_24dp); // 뒤로가기버튼 아이콘
//=========================================================================================================================================


        mEditTextName = (EditText)findViewById(R.id.editText_main_name);  //이름을 입력하는 뷰
        mEditTextAddress = (EditText)findViewById(R.id.editText_main_address);  //주소를 입력하는 뷰
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);  //상태를 알려주는 하단 뷰

        //mEditTextAge = (EditText)findViewById(R.id.editText_main_age);   /**추가*/ //나이를 입력하는 뷰







        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)

                String name = mEditTextName.getText().toString();  // name = 이름 Edit 텍스트에 내용을 객체화시키고 가져와 넣는다.
                String address = mEditTextAddress.getText().toString();  //address = 주소 Edit 텍스트에 내용을 객체화시키고 가져와 넣는다.

                //String age = mEditTextAge.getText().toString(); /**추가*/

                InsertData task = new InsertData();  // InsertData 클래스를 객체화 시킨다 => task 로.
                task.execute(name,address);  /**추가*/ //클래스의 생성자를 실행한다. name, address.


                mEditTextName.setText("");  //이름 비움.
                mEditTextAddress.setText("");  //주소 비움.


                Intent intent = new Intent(InputActivity.this, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();


            }
        });
    }






    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;  //프로그래스 지원안하는 듯.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InputActivity.this,
                    "잠시만 기다려주세요.", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();  //프로그래스 다이얼로그를 닫는다.
            mTextViewResult.setText(result);  //상태 텍스트뷰의 내용을 'result' 로 갱신.
            Log.d(TAG, "POST response  - " + result);  //로그 찍음. POST response - SQL문 처리 성공.
        }



        /** 'doInBackground' 란?
         * 전달 생성자가 ...인 이유는 형태의 제약이 없고자 함이다.

         onPreExecute: 위 doInBackground 가 수행하기전에 '메인 Tread' 에서 실행된다. ex) background 진행 정보를 프로그래스바로 표시할 수 있다.

         doInBackground: 'onPreExecute' 가 끝난 후 호출. 실제 백그라운드 작업을 수행하는데 이때 작업에 필요한 값을
         execute 에 파라미터로 전달 받는데,  이 파라미터의 'Generic Type' 은 <URL, Integer, Long> 에서 첫번째 가 된다.
         또한 doInBackground에서 수행되는 중간 결과 값을 publishProgress 를 호출함으로써 onProgressUpdate에서
         '메인 Thread' 에 전달하여 화면을 갱신한다.
         이때 전달되는 파라미터의 'Generic Type' 은 두번째가 된다.

         OnProgressUpdate: doInBackgorund, 작업 수행 중에 호출되는 PublishProgress 실행 후 '메인 Thread' 에 의해서 실행된다.
         주로 doInBackground 작업 진행정도를 표시하기 위한 프로그레스바 갱신에 사용된다.

         onPostExecute: doInBackground 수행 후 '메인 Thread' 에 의해 실행 된다. onPostExecute의 파라미터는
         doInBackground 의 result 값으로 <URL, Integer, Long> 에서 세번째 값을 갖는다.
         DoInBackground 수행 후 reslut 값을 가지고 필요한 작업을 수행한다.


         */






        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[0];  // 문자열 형태의 name 객체 생성.  (파라미터는 0번)
            String address = (String)params[1];  // 문자열 형태의 address 객체 생성.  (파라미터는 1번)

            //String age = (String)params[2];  /**추가*/

            String serverURL = "http://layup3.cafe24.com/HOMESUL_insert.php";  // 문자열 형태의 serverURL 내용을 태호 PC 로 했다.
            String postParameters = "name=" + name + "&address=" + address;       /**추가*/ // 요청할 파라미터의 정보를 입력했다.
            // 문자열 형태의 postParameters 에 ex) name=Teaho , address=Korea 로 했다.


            try {  //자 시도하자 갱신이여.

                URL url = new URL(serverURL);  // URL 객체에 을 아까 작성한 문자열형태의 url을 넣어준다.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();  //해당 url을 연결시키고 Open 한다.


                httpURLConnection.setReadTimeout(5000);  //타임아웃 시간 5초 설정
                httpURLConnection.setConnectTimeout(5000);  //연결아웃 시간 5초 설정
                httpURLConnection.setRequestMethod("POST");   //GET 은 빼올 수 있다. POST 는 빼올 수 없다.
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);  //응답을 받아야 하기에 True 로 설정.
                httpURLConnection.connect();  //연결한다.


                OutputStream outputStream = httpURLConnection.getOutputStream();  // httpURLConnection.url 에서 응답을 받은 정보를 가지고 있으니 이를 outPutStream 으로 보내야한다.
                outputStream.write(postParameters.getBytes("UTF-8"));  // 보내는 스트림 통로에 작성한다. postParameters (name 과 address 에 대한 값을 UTF-8 로 바꾸고 작성하는 것이다. )

                outputStream.flush();       /*요약하면 control message 와 같이 time sensitive 한 경우 send 혹은 write call 다음에 fflush 를 호출하는 것이
                                            delay 를 줄이는데 좋으며
                                            일반 data transmission 과 같은 경우에는 단순히 send 혹은 write call 만 사용해도 괜찮습니다.
                                            (system 이 자동적으로 언젠가는 데이타를 전송하게 됩니다.)*/

                outputStream.close();       //close 는 기본 원칙입니다.


                int responseStatusCode = httpURLConnection.getResponseCode();  // 응답상태 로그를 찍기위해
                Log.d(TAG, "POST response code - " + responseStatusCode);   //요로캐

                InputStream inputStream;  // 들어오는 InputStream 변수 선언
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {  // 응답상태 == HTTP_OK
                    inputStream = httpURLConnection.getInputStream(); //  InputStream 변수에 방금전에 통로 내용을 저장
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();  //아니면 에러 스트림 처리.
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  //순간읽는 bufferedReader 정의! url 을 한글은 한글자가 2byte 로 구성되어 있어서 InputStreamReader 를 이용해서 char 단위로 처리함.BufferedReader 클래스를 사용하면 readLine()메소드 처럼 한줄 읽기가 가능하다.

                StringBuilder sb = new StringBuilder(); /** String의 경우 문자열의 추가, 삭제, 수정 과정에서 매번 객체를 생성하기 때문에 속도저하와 메모리누적이 발생하게 된다.하지만 StringBuilder의 경우 한개의 객체를 생성하기 때문에 많은 오퍼레이션동작이 발생해도 속도저하 및 메모리 누적이 발생하지 않게 된다. */

                String line = null;

                while((line = bufferedReader.readLine()) != null){  //순간읽는 버퍼리더가 라인("null임") 과 같지 않다면 추가한다. 즉, null 값이 아니면 line 을 추가한다는 것이다.
                    sb.append(line);
                }


                bufferedReader.close(); //close 는 기본 원칙입니다.

                return sb.toString();  //객체 문자열로 변환하고  스트링빌더 객체에 대해 반환


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e); //예외처리 아니라면 애러 로그

                return new String("Error: " + e.getMessage());
            }

        }

    }







//뒤로가기버튼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                c_soundPool.play(click_Sound,  //준비한 soundID 맥주따르는 효과음
                        1, //왼쪽 볼륨 float 0.0(작은소리) ~ 1.0 (큰소리)
                        1, //오른쪽 볼륨 float
                        1, //우선순위 int
                        0, //반복회수 int -1:무한반복, 0:반복안함
                        1); //재생속도 float 0.5(절반속도)~2.0(2배속)
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        c_soundPool.play(click_Sound,1,1,1,0,1);
        super.onBackPressed();
    }
}