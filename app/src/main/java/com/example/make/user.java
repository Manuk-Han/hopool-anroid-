package com.example.make;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.make.service.RequestService;
import com.example.make.service.dto.LoginDTO;
import com.example.make.service.dto.UserDTO;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class user extends AppCompatActivity {

    private RequestService service;
    public final String PREFERENCE = "aaa";

    Button timeSetBtn;
    public int startHour;
    public int startMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);


        timeSetBtn=(Button)findViewById(R.id.s_time);
        timeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment df=new time();
                df.show(getSupportFragmentManager(),"timePicker");
            }
        });

        Button make_ok = (Button) findViewById(R.id.make_ok);
        make_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service = RetrofitManager.getInstance();
                Spinner sps=(Spinner)findViewById(R.id.user_start);
                Spinner spe=(Spinner)findViewById(R.id.user_end);
                SharedPreferences pref=getSharedPreferences(PREFERENCE,MODE_PRIVATE);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String today=sdf.format(new Date());
                Button btn=(Button)findViewById(R.id.s_time);
                passenger(Integer.parseInt(pref.getString(PREFERENCE,"")),sps.getSelectedItemPosition(),spe.getSelectedItemPosition(),
                        today+" "+btn.getText());
            }
        });
    }

    private void passenger (int passengerUserId, int startNode,int endNode,String departureTime) {
        service = RetrofitManager.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        map.put("passengerUserId", passengerUserId);
        map.put("startNode", startNode);
        map.put("endNode",endNode);
        map.put("departureTime",departureTime);
        service.passenger(map).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.toString());
                    if (response.body() != null) {
                        if (response.body().getStatus() == 201) {
                            Toast.makeText(getApplicationContext(),"매칭 시작",Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(getApplicationContext(), "매칭 실패", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("TAG", response.body().getMessage());
                    }
                } else {
                    Log.e("TAG", response.message());
                    Toast.makeText(getApplicationContext(), "매칭 실패2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크 문제발생", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public int getPreferenceInt(String account){
        SharedPreferences pref = getSharedPreferences(account, MODE_PRIVATE);
        return pref.getInt(String.valueOf(account), 0);
    }

    public static class time extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        Button btn;
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c=Calendar.getInstance();
            int hour=c.get(Calendar.HOUR_OF_DAY);
            int min=c.get(Calendar.MINUTE);
            btn=getActivity().findViewById(R.id.s_time);

            return new TimePickerDialog(getActivity(),this,hour,min,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String h=String.format("%02d",hourOfDay);
            String m=String.format("%02d",minute);
            btn.setText(h+":"+m);
        }
    }
}
