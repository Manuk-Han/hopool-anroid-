package com.example.make;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.make.service.RequestService;
import com.example.make.service.dto.HostDTO;
import com.example.make.service.dto.MakeDTO;
import com.example.make.service.dto.UserDTO;
import com.example.make.service.dto.UserListDTO;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class host extends AppCompatActivity {

    private RequestService service;
    public final String PREFERENCE = "aaa";
    private TextView ps;
    static private Long Id;

    private Boolean node[]= {false,false,false,false,false,false}, ud=true;
    Button timeSetBtn;

    private TextView pn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host);

        ps=(TextView)findViewById(R.id.host_start);
        Button s_change=(Button) findViewById(R.id.s_change);
        s_change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!ud) {
                    ps.setText("방향  |         아산  ⇨  천안");
                    ud=true;
                }
                else{
                    ps.setText("방향  |         천안  ⇨  아산");
                    ud=false;
                }
            }
        });

        Button n0=(Button)findViewById(R.id.node0),n1=(Button)findViewById(R.id.node1),
                n2=(Button)findViewById(R.id.node2),n3=(Button)findViewById(R.id.node3),
                n4=(Button)findViewById(R.id.node4),n5=(Button)findViewById(R.id.node5);

        n0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!node[0]) {
                    node[0]=true;
                    n0.setTextColor(Color.parseColor("#405EF8"));
                }
                else{
                    node[0]=false;
                   n0.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        n1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!node[1]) {
                    node[1]=true;
                    n1.setTextColor(Color.parseColor("#405EF8"));
                }
                else{
                    node[1]=false;
                    n1.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        n2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!node[2]) {
                    node[2]=true;
                    n2.setTextColor(Color.parseColor("#405EF8"));
                }
                else{
                    node[2]=false;
                    n2.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        n3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!node[3]) {
                    node[3]=true;
                    n3.setTextColor(Color.parseColor("#405EF8"));
                }
                else{
                    node[3]=false;
                    n3.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        n4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!node[4]) {
                    node[4]=true;
                    n4.setTextColor(Color.parseColor("#405EF8"));
                }
                else{
                    node[4]=false;
                    n4.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
        n5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!node[5]) {
                    node[5]=true;
                    n5.setTextColor(Color.parseColor("#405EF8"));
                }
                else{
                    node[5]=false;
                    n5.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });

        timeSetBtn=(Button)findViewById(R.id.host_stime);
        timeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment df=new host.time();
                df.show(getSupportFragmentManager(),"timePicker");
            }
        });

        Button ok=(Button)findViewById(R.id.make_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service = RetrofitManager.getInstance();
                SharedPreferences pref=getSharedPreferences(PREFERENCE,MODE_PRIVATE);

                int m=0;
                String route = "";
                for(int i=0;i<node.length;i++){
                    if(node[i]) {
                        route+=i;
                        m=i;
                        break;
                    }
                }
                for(int i=m+1;i<node.length;i++){
                    if(node[i]) route+=","+i;
                }

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String today=sdf.format(new Date());
                Button btn=(Button)findViewById(R.id.host_stime);

                host(Integer.parseInt(pref.getString(PREFERENCE,"")),ud,
                        route,today+" "+btn.getText());
            }
        });
    }

    private void host(int hostUserId, Boolean ud,String route,String departureTime) {

        service = RetrofitManager.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        map.put("hostUserId", hostUserId);
        map.put("direction", ud);
        map.put("route",route);
        map.put("departureTime",departureTime);

        service.host(map).enqueue(new Callback<HostDTO>() {
            @Override
            public void onResponse(Call<HostDTO> call, Response<HostDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.toString());
                    if (response.body() != null) {
                        if (response.body().getStatus() == 201) {
                            Toast.makeText(getApplicationContext(), "매칭 시작",Toast.LENGTH_SHORT).show();
                            Id=response.body().getHost_id();
                            host_re(Id);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "등록 실패",Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<HostDTO> call, Throwable t) {
                Log.e("NETWORK_ERROR", t.getMessage());
                Toast.makeText(getApplicationContext(), "인터넷 연결 문제2",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void host_re(Long Id) {
        setPreference("hostId",Id);

        Intent intent = new Intent(getApplicationContext(),host_match.class);
        startActivity(intent);
    }

    public static class time extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        Button btn;
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c=Calendar.getInstance();
            int hour=c.get(Calendar.HOUR_OF_DAY);
            int min=c.get(Calendar.MINUTE);
            btn=getActivity().findViewById(R.id.host_stime);

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

    public void setPreference(String hostID, Long Id){
        SharedPreferences pref = getSharedPreferences(hostID, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(String.valueOf(hostID), Id);
        editor.commit();
    }
}
