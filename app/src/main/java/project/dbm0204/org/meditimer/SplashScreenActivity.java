package project.dbm0204.org.meditimer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dbm0204 on 8/17/17.
 */

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Handler handler = new Handler();
        final Thread Thread_sleep = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(500000000);
                    handler.post(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}};
        Intent intent = new Intent(this,AlarmList.class);
        startActivity(intent);
        finish();
    }
}

