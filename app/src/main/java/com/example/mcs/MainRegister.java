package com.example.mcs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainRegister extends AppCompatActivity {
    private static String IP_ADDRESS = "10.0.2.2:81";
    private static String TAG = "phptest";

    private EditText mEditTextName,mEditTextemail;
    private EditText mEditTextpassword;
    private EditText mEditTextusername;

    private Button button_main_insert;
    //private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        mEditTextName = (EditText)findViewById(R.id.editText_main_name);
        mEditTextpassword = (EditText)findViewById(R.id.editText_main_password);
        //mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mEditTextemail = (EditText)findViewById(R.id.editText_email);
        mEditTextusername = (EditText)findViewById(R.id.editText_username);

        ImageButton BtnReturn = (ImageButton) findViewById(R.id.BtnReturn);
        BtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String password = mEditTextpassword.getText().toString();
                String email = mEditTextemail.getText().toString();
                String username = mEditTextusername.getText().toString();
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", name,password,email,username);

                name = mEditTextName.getText().toString();
                mEditTextName.setText("");
                mEditTextpassword.setText("");
                mEditTextemail.setText("");
                mEditTextusername.setText("");

                if (username.getBytes().length <= 0){
                    Toast.makeText(getApplicationContext(), " 이름를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if (name.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), " 아이디을 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if (password.getBytes().length <= 0){
                    Toast.makeText(getApplicationContext(), " 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if (email.getBytes().length <= 0){
                    Toast.makeText(getApplicationContext(), " 이메일를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "id : " + name + " 님의 회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainRegister.this, MainLogin.class);
                    //Intent intent1;
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainRegister.this,
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

            String name = (String)params[1];
            String password = (String)params[2];
            String email = (String)params[3];
            String username = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "name=" + name + "&password=" + password +"&email=" + email +"&username=" + username;


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
