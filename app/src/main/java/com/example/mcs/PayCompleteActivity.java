package com.example.mcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PayCompleteActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_complete);

        TextView reserveTime;
        reserveTime = (TextView)findViewById(R.id.reserveTime);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String time = bundle.getString("reservetime");

        reserveTime.setText(time);

        Button BtnCheck = (Button)findViewById(R.id.BtnCheck);
        BtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMypage.class);
                intent.putExtra("selecttime",time);
                startActivity(intent);
            }
        });
    }
}