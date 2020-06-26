package com.malik.roomdeneme3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class AlarmEdit extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_ID ="com.example.alarmdeneme2.EXTRA_ID";

    public static final String EXTRA_NAME = "com.example.alarmdeneme2.EXTRA_NAME";

    public static final String EXTRA_DATE = "com.example.alarmdeneme2.EXTRA_DATE";

    public static final String EXTRA_NUM = "com.example.alarmdeneme2.EXTRA_NUM";

    public static final String EXTRA_TIME = "com.example.alarmdeneme2.EXTRA_TIME";


    boolean set;

    private String time;


    private EditText dateT;
    final int[] day = new int[1];
    final int[] month = new int[1];
    final int[] year = new int[1];

    private EditText editTextName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);


        editTextName=findViewById(R.id.Name);
        dateT=findViewById(R.id.dateText);


        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                StringTokenizer st=new StringTokenizer(dateT.getText().toString(),"/");
                day[0] =Integer.parseInt(st.nextToken());
                month[0] =Integer.parseInt(st.nextToken());
                year[0] =Integer.parseInt(st.nextToken());
            }
        });

        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.alarm_ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            time=intent.getStringExtra(EXTRA_TIME);
            pendNum=intent.getIntExtra(EXTRA_NUM,-1);

            setTitle("Edit Alarm");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            dateT.setText(intent.getStringExtra(EXTRA_DATE));

        } else {
            setTitle("Add Alarm");
        }

    }
private int pendNum,hour,min;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour=hourOfDay;
        min=minute;
       /* Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MONTH,month[0]-1);
        c.set(Calendar.DAY_OF_MONTH,day[0]);
        c.set(Calendar.YEAR,year[0]);*/
        set=true;
     //   updateTimeText(c);
       // startAlarm(c);

    }

    private void updateTimeText(Calendar c) {
        time =DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra(EXTRA_NAME,editTextName.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendNum, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendNum, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


    private void saveAlarm() {
        String name = editTextName.getText().toString();
        String date = dateT.getText().toString();

        if (name.trim().isEmpty() || date.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(set) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MONTH, month[0] - 1);
            c.set(Calendar.DAY_OF_MONTH, day[0]);
            c.set(Calendar.YEAR, year[0]);
            startAlarm(c);
            time =DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DATE,date);
        data.putExtra(EXTRA_NUM,pendNum);
        data.putExtra(EXTRA_TIME,time);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_alarm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_alarm:
                saveAlarm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}