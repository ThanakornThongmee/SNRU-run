package snru.thongmee.thanakorn.snrurun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myManage = new MyManage(MainActivity.this);

    }//main method

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUp.class));
    }

}//main class นี่คือ คลาสหลัก
