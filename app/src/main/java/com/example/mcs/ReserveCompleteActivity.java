package com.example.mcs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReserveCompleteActivity extends Activity {
    private static String IP_ADDRESS = "10.0.2.2:81";
    private static String TAG = "db";

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_complete);

        TextView reserveSeat,presentDate;
        reserveSeat = (TextView)findViewById(R.id.reserveSeat);
        presentDate = (TextView)findViewById(R.id.presentDate);

        String seat;
        Intent intent = getIntent();
        seat = intent.getStringExtra("selectseat");

        reserveSeat.setText(seat);
        presentDate.setText(getTime());

        Button BtnCheck1 = (Button)findViewById(R.id.BtnCheck1);
        BtnCheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reserveseat1 = reserveSeat.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/seatinsert.php", getTime());

                Intent intent = new Intent(getApplicationContext(), MainMypage.class);
                intent.putExtra("reserveseat",reserveseat1);
                startActivity(intent);
            }
        });
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReserveCompleteActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            //String id = (String)params[1];
            String check_in = (String)params[1];
            //String check_out = (String)params[3];
            //String present_use = (String)params[4];
            //String seat_num = (String)params[2];
            //seat_num = selectnum.getText().toString();
            // String member_id = (String)params[6];

            String serverURL = (String)params[0];
            String postParameters = "check_in=" + check_in;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}