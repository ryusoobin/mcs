package com.example.mcs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UsageHistoryActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usage_history);

        TextView Txtcheckin = (TextView)findViewById(R.id.Txtcheckin); //이걸 textview로 받을지 listview로 받을지
        TextView Txtcheckout = (TextView)findViewById(R.id.Txtcheckout); //고민 중

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String checkin = bundle.getString("presentDate");
        Txtcheckin.setText(checkin);

        //String name = bundle.getString("name");
        //mypage_name.setText(name);

        ImageButton usage_back = (ImageButton) findViewById(R.id.usage_back);
        usage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMypage.class);
                startActivity(intent);
            }
        });
    }
}
