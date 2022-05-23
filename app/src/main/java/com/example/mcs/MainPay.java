package com.example.mcs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainPay extends Activity {
    private static String IP_ADDRESS = "10.0.2.2:81";
    private static String TAG = "db";

    public void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.pay_main);
        Button BtnBuy = (Button)findViewById(R.id.BtnBuy);

        TextView selecttime = (TextView)findViewById(R.id.selecttime);
        Button[] seatnumButtons = new Button[5];
        Integer[] seatnumBtnIDs = { R.id.BtnTimeTicket1,R.id.BtnTimeTicket3,R.id.BtnTimeTicket6,R.id.BtnTimeTicket9,R.id.BtnTimeTicket12};
        //Integer [] selecttimes = {900,10800,21600,32400,43200};


        Button BtnTimeTicket1,BtnTimeTicket3, BtnTimeTicket6, BtnTimeTicket9,BtnTimeTicket12;

        final boolean[] selected = {false};
        BtnTimeTicket1 = findViewById(R.id.BtnTimeTicket1);
        BtnTimeTicket3 = findViewById(R.id.BtnTimeTicket3);
        BtnTimeTicket6 = findViewById(R.id.BtnTimeTicket6);
        BtnTimeTicket9 = findViewById(R.id.BtnTimeTicket9);
        BtnTimeTicket12 = findViewById(R.id.BtnTimeTicket12);

        //Button[] numButtons = new Button[5];
        //Integer[] numBtnIDs = { R.id.BtnTimeTicket1,R.id.BtnTimeTicket3,R.id.BtnTimeTicket6,R.id.BtnTimeTicket9,R.id.BtnTimeTicket12 };

        //final int[] time = new int[]{1,3,6,9,12};

        BtnTimeTicket1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //15분 = 900초

                //time[1] = 900;

            }
        });

        BtnTimeTicket3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //3시간 = 10,800

                //time[3] = 10800;

            }
        });

        BtnTimeTicket6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//6시간 = 21,600

                //time[6] = 21600;

            }
        });

        BtnTimeTicket9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//9시간 = 32,400

                //time[9] = 32400;

            }
        });

        BtnTimeTicket12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //12시간 = 43,200

                //time[12] = 43200;

            }
        });

        for(int i=0;i<seatnumButtons.length;i++){
            seatnumButtons[i] = (Button)findViewById(seatnumBtnIDs[i]);

            final int index;
            index = i;

            seatnumButtons[index].setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    if (index == 0) {
                        BtnTimeTicket1.setSelected(true);
                        BtnTimeTicket3.setSelected(false);
                        BtnTimeTicket6.setSelected(false);
                        BtnTimeTicket9.setSelected(false);
                        BtnTimeTicket12.setSelected(false);
                        selecttime.setText("900"); //버튼 번호를 받아와 띄움
                        selecttime.setTextColor(Color.WHITE);
                        //Toast.makeText(getApplicationContext(),"15분을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                    } else if (index == 1) {
                        BtnTimeTicket3.setSelected(true);
                        BtnTimeTicket1.setSelected(false);
                        BtnTimeTicket6.setSelected(false);
                        BtnTimeTicket9.setSelected(false);
                        BtnTimeTicket12.setSelected(false);
                        selecttime.setText("10800"); //버튼 번호를 받아와 띄움
                        selecttime.setTextColor(Color.WHITE);
                        //Toast.makeText(getApplicationContext(),"3시간을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                    } else if (index == 2) {
                        BtnTimeTicket6.setSelected(true);
                        BtnTimeTicket3.setSelected(false);
                        BtnTimeTicket1.setSelected(false);
                        BtnTimeTicket9.setSelected(false);
                        BtnTimeTicket12.setSelected(false);
                        selecttime.setText("21600"); //버튼 번호를 받아와 띄움
                        selecttime.setTextColor(Color.WHITE);
                        //Toast.makeText(getApplicationContext(),"6시간을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                    } else if (index == 3) {
                        BtnTimeTicket1.setSelected(false);
                        BtnTimeTicket3.setSelected(false);
                        BtnTimeTicket6.setSelected(false);
                        BtnTimeTicket9.setSelected(true);
                        BtnTimeTicket12.setSelected(false);
                        selecttime.setText("32400"); //버튼 번호를 받아와 띄움
                        selecttime.setTextColor(Color.WHITE);
                        //Toast.makeText(getApplicationContext(),"9시간을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                    } else if (index == 4) {
                        BtnTimeTicket1.setSelected(false);
                        BtnTimeTicket3.setSelected(false);
                        BtnTimeTicket6.setSelected(false);
                        BtnTimeTicket9.setSelected(false);
                        BtnTimeTicket12.setSelected(true);
                        selecttime.setText("43200"); //버튼 번호를 받아와 띄움
                        selecttime.setTextColor(Color.WHITE);
                        //Toast.makeText(getApplicationContext(),"12시간을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        BtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selecttime.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "결제시간을 선택하세요", Toast.LENGTH_SHORT).show();
                } else {
                    String time1 = selecttime.getText().toString();

                    int time = Integer.parseInt(time1);

                    int day = time / (60 * 60 * 24);  // day *
                    int hour = time % (60 * 60 * 24) / (60 * 60);
                    int minute = time % (60 * 60) / 60;
                    int second = time % 60;
                    InsertData task = new InsertData();

                    task.execute("http://" + IP_ADDRESS + "/memberinsert.php", time1);

                    Intent intent = new Intent(MainPay.this, PayCompleteActivity.class);
                    intent.putExtra("reservetime", day + "일 " + hour + "시간 " + minute + "분" + second + "초");
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "결제완료", Toast.LENGTH_SHORT).show();


                    Log.d(TAG, "POST response  - " + time);
                }
            }
        });

        ImageButton BtnHome = (ImageButton) findViewById(R.id.BtnHome);
        BtnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton BtnUser = (ImageButton) findViewById(R.id.BtnUser);
        BtnUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainLogin.class);
                startActivity(intent);
            }
        });

        ImageButton BtnReservation = (ImageButton) findViewById(R.id.BtnReservation);
        BtnReservation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainReservation.class);
                startActivity(intent);
            }
        });

        ImageButton BtnEct = (ImageButton) findViewById(R.id.BtnEct);
        BtnEct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainEct.class);
                startActivity(intent);
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainPay.this,
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
            // String check_in = (String)params[2];
            //String check_out = (String)params[3];
            //String present_use = (String)params[4];
            String time = (String) params[1];
            //boolean present_use = true;
            //String member_id = (String)params[6];

            String serverURL = (String) params[0];
            String postParameters = "time=" + time;


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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
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
