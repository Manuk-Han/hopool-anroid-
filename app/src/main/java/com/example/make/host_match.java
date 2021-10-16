package com.example.make;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.make.service.RequestService;
import com.example.make.service.dto.UserListDTO;
import com.example.make.service.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class host_match extends AppCompatActivity {

    private RequestService service;
    List<Integer> ul=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_matched);

        Button re_match=(Button)findViewById(R.id.re_match);
        re_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long Id=getPreferenceLong("hostId");
                host(Id);
                re_match.setText("새로고침");
            }
        });

        ScrollView user_list=(ScrollView)findViewById(R.id.user_list);

        Button match_ok=(Button)findViewById(R.id.matching_ok);
    }


    private void makeBtn(List<UserListDTO.passengers> u_list){
        LinearLayout layout = (LinearLayout) findViewById(R.id.sv);
        layout.removeAllViews();
        ul.clear();

        for(int i=0;i<u_list.size();i++){
            Button myButton = new Button(this);
            myButton.setText("유저 : "+u_list.get(i).getList().getName()+"       출발지 : "+u_list.get(i).getStartNode());
            myButton.setId((int) (u_list.get(i).getId()+0));

            myButton.setBackground(this.getResources().getDrawable(R.drawable.btn_f));
            myButton.setWidth(50);
            myButton.setHeight(20);

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ul.contains(myButton.getId())){
                        myButton.setBackgroundResource(R.drawable.btn_s);
                        ul.add(myButton.getId());
                    }
                    else{
                        myButton.setBackgroundResource(R.drawable.btn_f);
                        ul.remove(Integer.valueOf(myButton.getId()));
                    }
                }
            });

            TextView tx=new TextView(this);
            tx.setWidth(50);
            tx.setHeight(5);

            layout.addView(myButton);
            layout.addView(tx);
        }
    }

    private void host(Long HostId) {
        service = RetrofitManager.getInstance();

        service.listPassenger(HostId).enqueue(new Callback<UserListDTO>() {
            @Override
            public void onResponse(Call<UserListDTO> call, Response<UserListDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.toString());
                    if (response.body() != null) {
                        if (response.body().getStatus() == 201) {
                            Toast.makeText(getApplicationContext(), "새로고침",Toast.LENGTH_SHORT).show();

                            makeBtn(response.body().getU_list());
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "조회실패",Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserListDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "인터넷 연결 문제2",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Long getPreferenceLong(String hostID){
        SharedPreferences pref = getSharedPreferences(hostID, MODE_PRIVATE);
        return pref.getLong(hostID, 0);
    }
}
