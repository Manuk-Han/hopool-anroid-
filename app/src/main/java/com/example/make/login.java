
package com.example.make;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.make.service.RequestService;
import com.example.make.service.dto.LoginDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class login extends AppCompatActivity {

    public final String PREFERENCE = "aaa";

    private RequestService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        EditText eti=(EditText)findViewById(R.id.login_id);
        EditText etp=(EditText)findViewById(R.id.login_pw);

        Button ok = (Button) findViewById(R.id.login_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service = RetrofitManager.getInstance();
                login(eti.getText().toString(), etp.getText().toString());
            }
        });

        TextView log_make = (TextView) findViewById(R.id.login_make);
        log_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), make.class);
                startActivity(intent);
            }
        });
    }

    private void login (String phone, String password) {
        service = RetrofitManager.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        service.login(map).enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {

                SharedPreferences pref=getSharedPreferences(PREFERENCE,MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();

                if (response.isSuccessful()) {
                    Log.d("TAG", response.toString());
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            editor.putString(PREFERENCE, response.body().getAccount().getId()+"");
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "id와 PW를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("TAG", response.body().getMessage());
                    }
                } else {
                    Log.e("TAG", response.message());
                    Toast.makeText(getApplicationContext(), "id와 PW를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크 문제발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setPreference(int key, int value){
        SharedPreferences pref = getSharedPreferences(String.valueOf(key), MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(String.valueOf(key), value);
        editor.commit();
    }
    public int getPreferenceInt(int key){
        SharedPreferences pref = getSharedPreferences(String.valueOf(key), MODE_PRIVATE);
        return pref.getInt(String.valueOf(key), 0);
    }
}

