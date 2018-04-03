package top.jplayer.lianzhong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import top.jplayer.baseprolibrary.live.service.StartService;
import top.jplayer.baseprolibrary.live.service.WhiteService;
import top.jplayer.baseprolibrary.ui.SampleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(view ->
                startActivity(new Intent(this, SampleActivity.class))
        );
        findViewById(R.id.btn1).setOnClickListener(view ->
                startActivity(new Intent(this, SecondActivity.class))
        );
        findViewById(R.id.btn2).setOnClickListener(view ->
                startActivity(new Intent(this, ThreeActivity.class))
        );
        findViewById(R.id.btn3).setOnClickListener(view ->
                startService(new Intent(getApplicationContext(), WhiteService.class))
        );
        findViewById(R.id.btn4).setOnClickListener(view ->
                stopService(new Intent(getApplicationContext(), WhiteService.class))
        );
        findViewById(R.id.btn5).setOnClickListener(view ->
                startService(new Intent(getApplicationContext(), StartService.class))
        );
    }
}
