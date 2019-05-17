package com.example.administrator.homesuls;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.administrator.homesuls.R.id.listView;
import static com.example.administrator.homesuls.R.id.menuOption_Item;

public class ChatActivity extends AppCompatActivity {

    SoundPool c_soundPool; // Sound Pool 을 담는 그릇
    int click_Sound; //클릭사운드



    String myJSON;

    private static final String TAG_RESULTS = "result"; //result == array
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList; //ArrayList를 가지지만 HashMap 까지 포함한 리스트 객체 생성

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        c_soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // 최대 음악파일의 개수, 스트림타입, 음질 기본값0
        click_Sound = c_soundPool.load(this, R.raw.clicksound, 1);    //버튼클릭 소리



        list = (ListView) findViewById(listView);



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
        //list = (ListView) findViewById(listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://layup3.cafe24.com/HOMESUL_connection.php"); /** 태호 PC 서버 */







    }//절대영역










    //=======================================툴바 영역=========================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {         //디바이스의 Menu 버튼이 처음 눌러졋을 때 한번 호출되는 메소드
        final MenuInflater inflater = getMenuInflater();   //인플레이터를 사용하여 결합
        inflater.inflate(R.menu.boardmenu_toolbar, menu);      //menu 폴더에 정의하고 만들어놓은 menu_toolbar.xml 파일을 inflater, 레퍼런스는 menu를 사용

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == menuOption_Item){

            sound();
            Intent Cintent = new Intent(ChatActivity.this, InputActivity.class);
            Cintent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); /*이전 실행액티비티를 다시 띄울때 액티비트를 다시 시작하는 것이다.
                                                                           히스토리 스택에서 해당액티비티를 다시 제일상단으로 재정렬*/
            startActivity(Cintent);
            finish();
        }
        else{
            sound();
            onBackPressed();
        }
        return true;
    }
//===========================================================================================================================================

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);  //제이슨Object 객체 생성 내부에 array 생성자
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {  //배열의 길이만큼 반복한다.
                JSONObject c = peoples.getJSONObject(i);  //제이슨Object 객체 c 생성( 배열을 받은 peoples의 인덱스를 하나씩 참조함!)

                String id = c.getString(TAG_ID);  // php에서 id 값을 (TAG_ID를 통해) id 스트링객체에 넣는다.
                String name = c.getString(TAG_NAME); // php에서 name 값을 (TAG_name를 통해) name 스트링객체에 넣는다.
                String address = c.getString(TAG_ADD); //php에서 address 값을 (TAG_address를 통해) address 스트링객체에 넣는다.



                /** persons 객체생성!! 해쉬맵<키, 벨류>     //빼올때는 String name = map.get("키") */
                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);  //넣는다. Key=> TAG_ID ,  value=> id (php의 id이다.)
                persons.put(TAG_NAME, name);  // -
                persons.put(TAG_ADD, address); // -

                personList.add(persons);  //Array객체와 HashMap 객체를 모두 가진 personList에 추가한다.
            }
            //여기까지 반복문으로 돌아간다




            /** ListAdapter 생성!! 이녀석이 위 해쉬맵을 가진 모든 것을 연결시켜줄 것이다. */
            ListAdapter adapter = new SimpleAdapter( ChatActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID, TAG_NAME, TAG_ADD}, //String 배열에 id, name, address 그릇 생성
                    new int[]{R.id.id, R.id.name, R.id.address}  //position 은 순서대로 id, name, address
            );
            list.setAdapter(adapter); //메인액티비티 listView 내부에 설정한 리스트어댑터 설정


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }





    /** 1. 안드로이드는 'Single Thread GUI model'을 사용한다.
     *  2. 안드로이드 내에서 화면갱신 할 수 있는 'Thread'는 오직 단 하나이다.
     *  3. 화면 갱신을 담당하는 Thread 는 'UI Thread'이며, 'Main thread' 라고도 한다.
     *  4. 화면 갱신 이외에 오래 걸리는 작업이 중첩되어 오류를 발생시킬 수 있다. (ANR 오류)
     *  5. 별도의 'Worker Thread' 를 생성하여 수행하는 것이 좋다.
     *  6. AsyncTask 를 사용하면 작업 중간 중간의 모든 갱신 동작을 간단히 구현할 수 있다.
     */

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                // 'doInBackground' 란?
                //전달 생성자가 ...인 이유는 형태의 제약이 없고자 함이다.
                // onPreExecute: 위 doInBackground 가 수행하기전에 UI Tread에서 실행된다. ex) background 진행 정보를 프로그래스바로 표시할 수 있다.
                /* doInBackground: 'onPreExecute' 가 끝난 후 호출. 실제 백그라운드 작업을 수행하는데 이때 작업에 필요한 값을
                   execute 에 파라미터로 전달 받는데,  이 파라미터의 'Generic Type' 은 <URL, Integer, Long> 에서 첫번째 가 된다.
                   또한 doInBackground에서 수행되는 중간 결과 값을 publishProgress 를 호출함으로써 onProgressUpdate에서
                   UI Thread 에 전달하여 화면을 갱신한다.
                   이때 전달되는 파라미터의 'Generic Type' 은 두번째가 된다. */


                String uri = params[0];  // 문자열 형태의 uri 객체 생성.  (파라미터는 0번)

                BufferedReader bufferedReader = null;    //scanf 처럼 문자열 입력 가능한 BufferedReader 정의!! 순간적으로 읽어들이는 역할을 한다.
                try {
                    URL url = new URL(uri);  //URL 객체 생성 위 파라미터 0번에서 받아온 uri(소문자) 생성자를 갖는다.
                    HttpURLConnection con = (HttpURLConnection) url.openConnection(); //해당 url을 연결시키고 Open 한다.
                    StringBuilder sb = new StringBuilder(); /* String의 경우 문자열의 추가, 삭제, 수정 과정에서 매번 객체를 생성하기 때문에 속도저하와 메모리누적이 발생하게 된다. 하지만 StringBuilder의 경우 한개의 객체를 생성하기 때문에 많은 오퍼레이션동작이 발생해도 속도저하 및 메모리 누적이 발생하지 않게 된다. */

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream())); //순간읽는 bufferedReader 정의! url 을 한글은 한글자가 2byte 로 구성되어 있어서 InputStreamReader 를 이용해서 char 단위로 처리함.BufferedReader 클래스를 사용하면 readLine()메소드 처럼 한줄 읽기가 가능하다.


                    String json; //String'형태의 json 변수 정의.
                    while ((json = bufferedReader.readLine()) != null) { //'While'로 반복된다!! json은 buffer Reader 의 .readLine으로 한줄읽기가 가능해진다. 그것이 널 값이 아니면 성공적으로 수행될 것이다.
                        sb.append(json + "\n"); //한개씩만 받아드리는 StringBulider의 sb객체로 효율적으로 추가시킨다. 그 후 한줄 띄어쓴다.
                    }

                    return sb.toString().trim();  //문자형객체로 변환과 동시에 JAVA의 .trim 으로 앞 뒤의 공백을제거 (중간 제거못한다!!)

                } catch (Exception e) {
                    return null;
                }


            }




            @Override
            protected void onPostExecute(String result) {
                myJSON = result; //result ==> array 데이터이므로 myJSON 에 삽입 (myJSON = String 형)
                showList();

            }






        }
        GetDataJSON g = new GetDataJSON(); //데이터를 get 객체 생성 후
        g.execute(url); // 실행
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

