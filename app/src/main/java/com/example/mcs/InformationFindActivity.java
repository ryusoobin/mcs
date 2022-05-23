package com.example.mcs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class InformationFindActivity extends AppCompatActivity {
    Button BtnFindEmail;
    EditText findEmail;
    ImageButton BtnReturn2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_find);

        BtnReturn2 = (ImageButton)findViewById(R.id.BtnReturn2);
        BtnFindEmail = (Button)findViewById(R.id.BtnFindEmail);
        findEmail = (EditText) findViewById(R.id.findEmail);

        BtnReturn2.setOnClickListener(new View.OnClickListener() { // back 버튼을 누르면 로그인 창으로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationFindActivity.this,MainLogin.class);
                startActivity(intent);
            }
        });

        BtnFindEmail.setOnClickListener(new View.OnClickListener() { // ? 찾기 버튼을 누르면 이메일을 확인하고 이메일로 아이디/비번 정보 보내주기
            public void onClick(View v) {

            }
        });
    }
}