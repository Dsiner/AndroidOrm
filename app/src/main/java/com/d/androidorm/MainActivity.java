package com.d.androidorm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.d.androidorm.activity.OrmActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.btn_jump_greendao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrmActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_jump_sqlite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrmActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_jump_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrmActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_jump_ormlite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrmActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_jump_litepal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrmActivity.class);
                intent.putExtra("type", 4);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_jump_realm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrmActivity.class);
                intent.putExtra("type", 5);
                startActivity(intent);
            }
        });
    }
}
