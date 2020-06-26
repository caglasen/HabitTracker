package com.malik.roomdeneme3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AlarmActivity extends AppCompatActivity {
    private AlarmViewModel alarmViewModel;
    public static final int ADD_ALARM_REQUEST = 1;
    public static final int EDIT_ALARM_REQUEST = 2;

    int i=1;
    final AlarmAdapter adapter=new AlarmAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);



        //initialize and assign Variable   --- burası bottom menu için kod başlangıcıdır
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.menu_alarm);         // buraya tekrar bakarsın

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_habit:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_report:
                        startActivity(new Intent(getApplicationContext(), ReportingMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_note:
                        startActivity(new Intent(getApplicationContext(), NoteActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_alarm:
                        return true;
                    case R.id.menu_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });








        FloatingActionButton buttonAddHobby = findViewById(R.id.button_add_alarm);
        buttonAddHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, AlarmAdd.class);
                intent.putExtra(AlarmAdd.EXTRA_NUM,i+"");
                i++;
                startActivityForResult(intent, ADD_ALARM_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_alarm_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //   final AlarmAdapter adapter = new AlarmAdapter();
        recyclerView.setAdapter(adapter);

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                adapter.setAlarms(alarms);

            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Alarm alarm=adapter.getAlarmAt(viewHolder.getAdapterPosition());
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(AlarmActivity.this, AlertReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, alarm.getPendNum(), intent, 0);

                alarmManager.cancel(pendingIntent);
                alarmViewModel.delete(adapter.getAlarmAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AlarmActivity.this, "Alarm deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AlarmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Alarm alarm) {
                Intent intent = new Intent(AlarmActivity.this, AlarmEdit.class);
                intent.putExtra(AlarmEdit.EXTRA_ID, alarm.getId());
                intent.putExtra(AlarmEdit.EXTRA_NAME, alarm.getName());
                intent.putExtra(AlarmEdit.EXTRA_DATE, alarm.getDate());
                intent.putExtra(AlarmEdit.EXTRA_NUM, alarm.getPendNum());
                intent.putExtra(AlarmEdit.EXTRA_TIME,alarm.getDateTime());


                startActivityForResult(intent, EDIT_ALARM_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ALARM_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(AlarmAdd.EXTRA_NAME);
            String date = data.getStringExtra(AlarmAdd.EXTRA_DATE);
            int pend=data.getIntExtra(AlarmAdd.EXTRA_NUM,-1);
            String time=data.getStringExtra(AlarmAdd.EXTRA_TIME);

            Alarm alarm = new Alarm(name,date,pend,time);
            alarmViewModel.insert(alarm);

            Toast.makeText(this, "Alarm Saved", Toast.LENGTH_LONG).show();

        }
        else if (requestCode == EDIT_ALARM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AlarmEdit.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Alarm can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AlarmEdit.EXTRA_NAME);
            String date = data.getStringExtra(AlarmEdit.EXTRA_DATE);
            int pend=data.getIntExtra(AlarmEdit.EXTRA_NUM,0);
            String time=data.getStringExtra(AlarmEdit.EXTRA_TIME);
            Alarm alarm = new Alarm(name,date,pend,time);
            alarm.setId(id);
            alarmViewModel.update(alarm);
            Toast.makeText(this, "Alarm updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Alarm not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.alarm_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int j;
        switch (item.getItemId()) {
            case R.id.delete_all_alarms:
                for(j=0;j<adapter.getItemCount();j++) {
                    Alarm alarm = adapter.getAlarmAt(j);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(AlarmActivity.this, AlertReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, alarm.getPendNum(), intent, 0);

                    alarmManager.cancel(pendingIntent);
                }
                alarmViewModel.deleteAllAlarms();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

