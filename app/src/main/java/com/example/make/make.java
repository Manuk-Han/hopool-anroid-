
package com.example.make;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.make.service.RequestService;
import com.example.make.service.dto.LoginDTO;
import com.example.make.service.dto.MakeDTO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class make extends AppCompatActivity {

    private RequestService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make);

        EditText eti=(EditText)findViewById(R.id.make_phone);
        EditText etn=(EditText)findViewById(R.id.make_name);
        EditText etp=(EditText)findViewById(R.id.make_pw);

        Button ok=(Button) findViewById(R.id.make_ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                service = RetrofitManager.getInstance();
                make(eti.getText().toString(),etn.getText().toString(), etp.getText().toString());
            }
        });
    }
    private void make (String phone,String name, String password) {
        service = RetrofitManager.getInstance();
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("name",name);
        map.put("password", password);
        service.make(map).enqueue(new Callback<MakeDTO>() {
            @Override
            public void onResponse(Call<MakeDTO> call, Response<MakeDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.toString());
                    if (response.body() != null) {
                        if (response.body().getStatus() == 201) {
                            Toast.makeText(getApplicationContext(), "가입성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), login.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "가입실패",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("TAG", response.body().getMessage());
                    }
                }
                else {
                    Log.e("TAG", response.message());
                    Toast.makeText(getApplicationContext(), "인터넷 연결 문제",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MakeDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "인터넷 연결 문제",Toast.LENGTH_SHORT).show();
            }
        });
    }
}