package snru.thongmee.thanakorn.snrurun;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] userStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Blind Widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);



        myManage = new MyManage(MainActivity.this);

        //Test Add User
        //myManage.addUser("ธนากร กิวล์", "guil", "00000", "4");

        //Delete All SQLite
        deleteAllSQLite();

        MySynchronize mySynchronize = new MySynchronize();
        mySynchronize.execute();

        //Show Logo
        Picasso.with(MainActivity.this)
                .load("http://swiftcodingthai.com/snru/image/logo_snru.png")
                .resize(200,250)
                .into(imageView);

    }//main method

    public void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();//.trim คือตัดช่องว่างหน้า-หลัง
        passwordString = passwordEditText.getText().toString().trim();

        //check space
        if (userString.equals("") || passwordString.equals("")){

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกให้ครบทุกช่อง");

        } else {

            checkUser();

        }

    }   // clickSignIn

    private void checkUser() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString +  "'", null);
            cursor.moveToFirst();
            userStrings = new String[cursor.getColumnCount()];

            for (int i=0;i<cursor.getColumnCount();i++) {
                userStrings[i] = cursor.getString(i);
            }

            //check password
            if (passwordString.equals(userStrings[3])) {
                Toast.makeText(this, "ยินดีต้อนรับ" + userStrings[1], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("User", userString);
                startActivity(intent);
                finish();
            } else {
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this, "Password false", "Please Try Again Password false");
            }


        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ไม่มี USER นี้", "ไม่มี"+userString+"ในฐานข้อมูลของเรา");

        }

    }   //chechUser


    //Creat Inner Class

    public class MySynchronize extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("swiftcodingthai.com/snru/get_user.php").build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {
                return null;
            }
           // return null;
        }  //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Snru", "JSON==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(myManage.column_name);
                    String strUser = jsonObject.getString(myManage.column_user);
                    String strPassword = jsonObject.getString(myManage.column_password);
                    String strAvata = jsonObject.getString(myManage.column_avata);

                    myManage.addUser(strName, strUser, strPassword, strAvata);

                }   //for

            } catch (Exception e) {
                e.printStackTrace();
            }
        }//onPost


    }//   //MySyn Class



    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);


    }

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUp.class));
    }

}//main class นี่คือ คลาสหลัก
